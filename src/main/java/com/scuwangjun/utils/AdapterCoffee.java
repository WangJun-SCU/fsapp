package com.scuwangjun.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.scuwangjun.datas.DataCoffee;
import com.scuwangjun.fsapp.R;
import com.squareup.picasso.Picasso;

public class AdapterCoffee extends BaseAdapter {
	private Context context;
	private List<DataCoffee> list = new ArrayList<DataCoffee>();
	private DataCoffee dataCoffee;

	public AdapterCoffee(Context context, List<DataCoffee> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int i) {
		return list.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewgroup) {

		ViewHolder viewHolder = null;

		if (view == null) {

			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.coffee_serve_list, null);
			viewHolder.name = (TextView) view
					.findViewById(R.id.coffee_list_name);
			viewHolder.pay = (Button) view.findViewById(R.id.coffee_list_pay);
			viewHolder.image = (ImageView) view
					.findViewById(R.id.coffee_list_image);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		dataCoffee = list.get(i);

		viewHolder.name.setText(dataCoffee.getName());
		Picasso.with(context).load(dataCoffee.getUrl()).into(viewHolder.image);

		return view;
	}

	private class ViewHolder {
		TextView name;
		ImageView image;
		Button pay;
	}
}
