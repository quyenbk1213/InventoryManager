package com.example.inventorymanager.data;


import com.example.inventorymanager.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;




public class GridViewAdapter extends ArrayAdapter<Grid> {

	private Activity context = null;
	private Grid[] myArray = null;
	private int layoutId;

	public GridViewAdapter(Activity context, int layoutId, Grid[] arr) {
		super(context, layoutId, arr);
		this.context = context;
		this.layoutId = layoutId;
		this.myArray = arr;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(layoutId, null);
			holder = new ViewHolder(row);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		holder.poulateFrom(myArray[position]);
		return row;
	}

	static class ViewHolder {

		TextView txtTitle;
		ImageView imgIcon;

		public ViewHolder(View row) {
			txtTitle = (TextView) row.findViewById(R.id.txtTitle);
			imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
		}

		public void poulateFrom(Grid grid) {
			txtTitle.setText(grid.getTitle());
			imgIcon.setImageResource(grid.getIcon());			
		}
	}

}
