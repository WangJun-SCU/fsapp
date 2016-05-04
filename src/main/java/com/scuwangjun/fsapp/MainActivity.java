package com.scuwangjun.fsapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.brtbeacon.sdk.BRTBeacon;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.BRTRegion;
import com.brtbeacon.sdk.MonitoringListener;
import com.brtbeacon.sdk.RangingListener;
import com.brtbeacon.sdk.ServiceReadyCallback;
import com.brtbeacon.sdk.Utils;
import com.brtbeacon.sdk.service.RangingResult;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.model.Color;
import com.scuwangjun.datas.StaticDatas;
import com.scuwangjun.estimote.BeaconID;
import com.scuwangjun.estimote.EstimoteCloudBeaconDetails;
import com.scuwangjun.estimote.EstimoteCloudBeaconDetailsFactory;
import com.scuwangjun.estimote.ProximityContentManager;
import com.scuwangjun.myinterface.BaseInterface;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MainActivity extends AppCompatActivity implements BaseInterface{

    private ImageButton home2;  //进入到菜单按钮
    private TextView temperature;  //室内温度
    private TextView light;  //室内光度
    private View login;
    private BRTBeaconManager beaconManager;
    private BRTRegion indoorRegion = StaticDatas.INDOOR_REGION;
    private NotificationManager notificationManager;  //消息通知栏通知
    private static final int NOTIFICATION_ID = 123;
    private AlertDialog dialog;
    private BRTBeacon inDoorBeacon;

    private static final String TAG = "MainActivity";

    private static final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();

    static {
        BACKGROUND_COLORS.put(Color.ICY_MARSHMALLOW, android.graphics.Color.rgb(109, 170, 199));
        BACKGROUND_COLORS.put(Color.MINT_COCKTAIL, android.graphics.Color.rgb(155, 186, 160));
    }

    private static final int BACKGROUND_COLOR_NEUTRAL = android.graphics.Color.rgb(160, 169, 172);

    private ProximityContentManager proximityContentManager;

    //操作UI线程，取消“欢迎光临方所”Dialog
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==123)
                dialog.dismiss();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        init();

        //跳转到菜单界面
        home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Home2.class));
            }
        });

        //跳转到登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Login.class));
            }
        });

        //监听设备进出Beacon感应区域
        beaconManager.setMonitoringListener(new MonitoringListener() {

            @Override
            public void onEnteredRegion(BRTRegion arg0, List<BRTBeacon> arg1) {
                //postNotification("进入感应区域");
                closeBeaconMonitor();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("通知")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("欢迎光临方所书店");
                dialog = builder.create();
                //设置透明度
                Window window = dialog.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 0.8f;
                window.setAttributes(lp);
                dialog.show();
                new ThreadCloseAlertDialog().start();

                //显示温度和光度
                inDoorBeacon = arg1.get(0);
                temperature.setText("室内温度："+inDoorBeacon.getTemperature()+"℃");
                light.setText("室内光度："+inDoorBeacon.getLight()+"cd");
                Toast.makeText(MainActivity.this,"距离："+ Utils.computeAccuracy(inDoorBeacon),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onExitedRegion(BRTRegion arg0, List<BRTBeacon> arg1) {
                Log.v("fsLog","离开感应区域");
            }

        });

        //Estimote监听是否靠近某个具体商品
        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                String text;
                Integer backgroundColor;
                if (content != null) {
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    text = beaconDetails.getBeaconName();
                    backgroundColor = BACKGROUND_COLORS.get(beaconDetails.getBeaconColor());
                } else {
                    text = "No beacons in range.";
                    backgroundColor = null;
                }
                Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();
                if(text.equals("ice")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setView(R.layout.bookdialog)
                            .setTitle("临近图书")
                            .create()
                            .show();
                }else if(text.equals("mint")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("临近咖啡厅")
                            .setMessage("欢迎光临方所咖啡厅")
                            .setPositiveButton("去点咖啡", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this,"点咖啡界面",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("不了谢谢", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                }
                /*findViewById(R.id.home_background).setBackgroundColor(
                        backgroundColor != null ? backgroundColor : BACKGROUND_COLOR_NEUTRAL);*/
            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void init() {
        home2 = (ImageButton)findViewById(R.id.home_home2);
        temperature = (TextView)findViewById(R.id.home_wendu);
        light = (TextView)findViewById(R.id.home_guangdu);
        login = findViewById(R.id.home_testlogin);
        beaconManager = new BRTBeaconManager(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        startBeaconMonitor();  //开始进行监听Beacon

        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 50170, 29097),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 60828, 26752),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 60127, 22771)),
                new EstimoteCloudBeaconDetailsFactory());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting Pr oximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping ProximityContentManager content updates");
        proximityContentManager.stopContentUpdates();
    }

    @Override
    protected void onStop() {
       closeBeaconMonitor();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proximityContentManager.destroy();
    }

    //消息推送
    private void postNotification(String msg) {
        Intent notifyIntent = new Intent(MainActivity.this,MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(MainActivity.this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(MainActivity.this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("欢迎光临方所")
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    //开启监听服务
    private void startBeaconMonitor(){
        //开始连接Beacon并进行监听
        beaconManager.connect(new ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startMonitoring(indoorRegion);
                } catch (RemoteException e) {

                    e.printStackTrace();
                }

            }
        });
    }

    //关闭监听服务
    private void closeBeaconMonitor(){
        try {
            // 停止监听
            beaconManager.stopRanging(indoorRegion);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        // 关闭服务
        beaconManager.disconnect();
    }

    //线程--通知主线程关闭Dialog
    class ThreadCloseAlertDialog extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessageDelayed(123,5000);
        }
    }
}
