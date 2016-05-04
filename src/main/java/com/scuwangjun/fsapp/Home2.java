package com.scuwangjun.fsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.scuwangjun.myinterface.BaseInterface;

public class Home2 extends AppCompatActivity implements BaseInterface {

    private ImageButton back;
    private Button person;
    private Button book;
    private Button chair;
    private Button coffee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home2);
        init();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home2.this,Person.class);
                startActivity(intent);
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home2.this,BookRecommend.class));
            }
        });
        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home2.this,CoffeeServe.class));
            }
        });
    }

    @Override
    public void init() {
        back = (ImageButton)findViewById(R.id.home2_back);
        person = (Button)findViewById(R.id.home2_person);
        book = (Button)findViewById(R.id.home2_book);;
        chair = (Button)findViewById(R.id.home2_chair);
        coffee = (Button)findViewById(R.id.home2_coffee);
    }
}
