package com.scuwangjun.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scuwangjun.datas.DataBook;
import com.scuwangjun.fsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterBook extends BaseAdapter {
	private Context context;
	private List<DataBook> list = new ArrayList<DataBook>();
	private DataBook dataBook;
	
	public AdapterBook(Context context, List<DataBook> list) {
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
		
		if(view==null){
			
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.book_recommend_list, null);
			viewHolder.name = (TextView) view.findViewById(R.id.book_name);
			viewHolder.intro = (TextView) view.findViewById(R.id.book_intro);
			viewHolder.image = (ImageView) view.findViewById(R.id.book_image);
			
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}
		
		dataBook = list.get(i);
		
		viewHolder.name.setText(dataBook.getName());
		viewHolder.intro.setText(dataBook.getIntro());
		Picasso.with(context).load(dataBook.getUrl()).into(viewHolder.image);
		
		return view;
	}
	
	private class ViewHolder{
		TextView name;
		TextView intro;
		ImageView image;
	}
}
