package com.example.inventorymanager.adapter;

import java.util.ArrayList;

import com.example.inventorymanager.R;
import com.example.inventorymanager.data.Category;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryAdapter extends ArrayAdapter<Category> {
	private Activity context;
	private int layoutID;
	private ArrayList<Category> myArr;
	
	public CategoryAdapter(Activity context, int layoutID, ArrayList<Category> myArr) {
		super(context, layoutID, myArr);
		this.context = context;
		this.layoutID = layoutID;
		this.myArr = myArr;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder;
		if(row == null){
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(layoutID, null);
			holder = new ViewHolder(row);
			row.setTag(holder);
		}else {
			holder = (ViewHolder) row.getTag();
		}
		holder.poulateFrom(myArr.get(position));
		if(position % 2 == 0){
			row.setBackgroundColor(context.getResources().getColor(R.color.light_green));
		}
		return row;
	}
	
	
	class ViewHolder{
		TextView txtCategoryStatus, txtCategoryName, txtCategoryCount, txtCategoryCreatedAt, txtCategoryUpdatedAt;
		public ViewHolder(View row){
			txtCategoryStatus = (TextView) row.findViewById(R.id.txtCategoryStatus);
			txtCategoryName = (TextView) row.findViewById(R.id.txtCategoryName);
			txtCategoryCount = (TextView) row.findViewById(R.id.txtCategoryCount);
			txtCategoryCreatedAt = (TextView) row.findViewById(R.id.txtCategoryCreatedAt);
			txtCategoryUpdatedAt = (TextView) row.findViewById(R.id.txtCategoryUpdatedAt);
		}
		
		public void poulateFrom(Category category){
			txtCategoryStatus.setText(category.getStringStatus());
			txtCategoryName.setText(category.getName());
			txtCategoryCount.setText(Integer.toString(category.getCount()));
			txtCategoryCreatedAt.setText(category.getCreated_at());
			txtCategoryUpdatedAt.setText(category.getUpdate_at());
		}
	}


}
