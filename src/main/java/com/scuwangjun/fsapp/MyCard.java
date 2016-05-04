package com.scuwangjun.fsapp;

import android.support.v7.app.AlertDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.scuwangjun.myinterface.BaseInterface;
import com.scuwangjun.utils.EncodingUtils;

public class MyCard extends AppCompatActivity implements BaseInterface{

    private ImageButton back;
    private ImageView card;
    private View erweimaDialog;
    private ImageView erweima;
    private TextView code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycard);
        init();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = EncodingUtils.createQRCode(code.getText().toString(),700,700,null);
                erweima.setImageBitmap(bitmap);
                makeCode();
            }

        });
    }

    @Override
    public void init() {
        back = (ImageButton)findViewById(R.id.mycard_back);
        card = (ImageView)findViewById(R.id.mycard_card);
        erweimaDialog = LayoutInflater.from(MyCard.this).inflate(R.layout.erweima,null);
        erweima = (ImageView) erweimaDialog.findViewById(R.id.erweima_ig);
        code = (TextView)findViewById(R.id.mycard_code);
    }

    private void makeCode(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MyCard.this);
        builder.setView(erweimaDialog)
                .setTitle("会员卡二维码")
                .create()
                .show();
    }
}
