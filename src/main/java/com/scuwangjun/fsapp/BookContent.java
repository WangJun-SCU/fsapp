package com.scuwangjun.fsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.scuwangjun.myinterface.BaseInterface;
import com.squareup.picasso.Picasso;

public class BookContent extends Activity implements BaseInterface{
	
	private ImageButton close;
	private ImageView image;
	private TextView name;
	private TextView intro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_content);
		init();
		initData();
		
		close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	@Override
	public void init() {
		close = (ImageButton) findViewById(R.id.content_close);
		image = (ImageView) findViewById(R.id.content_image);
		name = (TextView) findViewById(R.id.content_name);
		intro = (TextView) findViewById(R.id.content_intro);
	}
	
	private void initData(){
		String sName = getIntent().getStringExtra("name");
		String sIntro = getIntent().getStringExtra("intro");
		String url = getIntent().getStringExtra("url");
		name.setText(sName);
		intro.setText(sIntro);
		Picasso.with(this).load(url).into(image);
	}

}
