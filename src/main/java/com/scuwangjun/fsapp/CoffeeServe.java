package com.scuwangjun.fsapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.scuwangjun.datas.DataCoffee;
import com.scuwangjun.datas.StaticDatas;
import com.scuwangjun.myinterface.BaseInterface;
import com.scuwangjun.utils.AdapterCoffee;

public class CoffeeServe extends Activity implements BaseInterface,
		OnClickListener {

	private ImageButton back;
	private ListView listView;
	private AdapterCoffee adapterCoffee;
	private List<DataCoffee> listDataCoffees = new ArrayList<DataCoffee>();
	private DataCoffee dataCoffee;

	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coffee_serve);
		init();
		initEvent();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				View view = LayoutInflater.from(CoffeeServe.this).inflate(R.layout.coffe_onsale, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(CoffeeServe.this);
				builder.setView(view);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				ImageButton close = (ImageButton) view.findViewById(R.id.coffee_onsale_close);
				close.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						alertDialog.dismiss();
					}
				});
				TextView priceB = (TextView) view.findViewById(R.id.coffee_onsale_price_b);
				TextView priceN = (TextView) view.findViewById(R.id.coffee_onsale_price_n);
				priceB.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
				priceN.getPaint().setAntiAlias(true);// 抗锯齿
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.coffee_back:
				finish();
				break;
			default:
				break;
		}
	}

	@Override
	public void init() {
		back = (ImageButton) findViewById(R.id.coffee_back);
		listView = (ListView) findViewById(R.id.coffee_list);
		dialog = new ProgressDialog(this);
		dialog.setMessage("正在加载...");
		dialog.show();

		initData();
	}

	public void initEvent() {
		back.setOnClickListener(this);
	}

	private void initData() {
		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringRequest = new StringRequest(
				StaticDatas.urlCoffeeServe, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					JSONArray array = jsonObject.getJSONArray("result");
					for (int i = 0; i < array.length(); i++) {
						JSONObject item = array.getJSONObject(i);
						dataCoffee = new DataCoffee(item.getInt("id"),
								item.getString("name"), item
								.getString("intro"), item
								.getString("url"), item
								.getDouble("price_before"),
								item.getDouble("price_now"));
						listDataCoffees.add(dataCoffee);
					}
					System.out.println("1");
					adapterCoffee = new AdapterCoffee(CoffeeServe.this,
							listDataCoffees);
					System.out.println("2");
					listView.setAdapter(adapterCoffee);
					System.out.println("3");
					dialog.dismiss();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.println("错误信息：" + error.toString());
				dialog.dismiss();
			}
		});

		queue.add(stringRequest);
	}

}

