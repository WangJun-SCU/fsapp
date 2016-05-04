package com.scuwangjun.utils;

import android.app.Application;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.scuwangjun.myinterface.BaseInterface;

import cn.smssdk.SMSSDK;

/**
 * Created by 骏 on 2016/4/30.
 */
public class MyApplication extends Application implements BaseInterface{

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public void init() {
        // 注册应用，初始化，在应用启动Activity或者Application派生类执行
        BRTBeaconManager.registerApp(this, "00000000000000000000000000000000");

        //注册Estimote
        EstimoteSDK.initialize(getApplicationContext(), "scuwjt1-oed", "e417f6cb6a0ef07dbc13cf17c8258207");

    }
}

