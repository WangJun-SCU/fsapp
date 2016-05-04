package com.scuwangjun.fsapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.scuwangjun.datas.DataBook;
import com.scuwangjun.datas.StaticDatas;
import com.scuwangjun.myinterface.BaseInterface;
import com.scuwangjun.utils.AdapterBook;

public class BookRecommend extends Activity implements BaseInterface,
		OnClickListener {

	private ImageButton back;
	private ListView listView;
	private AdapterBook adapterBook;
	
	private ProgressDialog dialog;

	// 用于保存书籍信息的list
	private List<DataBook> listDataBooks = new ArrayList<DataBook>();
	private DataBook dataBook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_recommend);
		init();
		initEvent();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				dataBook = listDataBooks.get(arg2);
				Intent intent = new Intent();
				intent.putExtra("name", dataBook.getName());
				intent.putExtra("intro", dataBook.getIntro());
				intent.putExtra("url", dataBook.getUrl());
				intent.setClass(BookRecommend.this, BookContent.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void init() {
		back = (ImageButton) findViewById(R.id.book_recommed_back);
		listView = (ListView) findViewById(R.id.book_list);

		dialog = new ProgressDialog(this);
		dialog.setMessage("正在加载...");
		dialog.show();

		initData();
	}

	public void initEvent() {
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.book_recommed_back:
				finish();
				break;
			default:
				break;
		}
	}

	// 初始化数据
	private void initData() {
		RequestQueue mQueue = Volley.newRequestQueue(this);
		StringRequest stringRequest = new StringRequest(
				StaticDatas.urlBookRecommend, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				System.out.println("response:" + response);

				try {
					JSONObject jsonObject = new JSONObject(response);
					JSONArray array = jsonObject.getJSONArray("result");
					for (int i = 0; i < array.length(); i++) {
						JSONObject item = array.getJSONObject(i);
						dataBook = new DataBook(item.getInt("id"), item
								.getString("name"), item
								.getString("intro"), item
								.getString("url"));
						listDataBooks.add(dataBook);
					}
					adapterBook = new AdapterBook(BookRecommend.this, listDataBooks);
					listView.setAdapter(adapterBook);
					dialog.dismiss();
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(BookRecommend.this,
						"error:" + error.toString(), Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});

		mQueue.add(stringRequest);
	}

}

