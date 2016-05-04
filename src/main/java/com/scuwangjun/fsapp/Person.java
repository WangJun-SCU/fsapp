package com.scuwangjun.fsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.scuwangjun.myinterface.BaseInterface;

public class Person extends AppCompatActivity implements BaseInterface{

    private ImageButton back;
    private LinearLayout card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personhome);
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
                startActivity(new Intent(Person.this,MyCard.class));
            }
        });
    }

    @Override
    public void init() {
        back = (ImageButton)findViewById(R.id.personhome_back);
        card = (LinearLayout)findViewById(R.id.personhome_card);
    }
}
