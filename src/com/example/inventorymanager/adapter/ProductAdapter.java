package com.example.inventorymanager.adapter;

import java.util.ArrayList;

import com.example.inventorymanager.R;
import com.example.inventorymanager.data.Product;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends ArrayAdapter<Product> {
	private Activity context;
	private int layoutID;
	private ArrayList<Product> myArr;
	
	public ProductAdapter(Activity context, int layoutID, ArrayList<Product> myArr) {
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
		TextView txtProductName, txtProductQty, txtProductPrice, 
				 txtProductBarcode, txtProductStatus;
		ImageView imgProduct;
		public ViewHolder(View row){
			imgProduct = (ImageView)row.findViewById(R.id.ivProduct);
			txtProductName = (TextView) row.findViewById(R.id.tvProductName);
			txtProductStatus = (TextView)row.findViewById(R.id.tvProductStatus);
			txtProductBarcode = (TextView)row.findViewById(R.id.tvProductBarcode);
			txtProductQty = (TextView) row.findViewById(R.id.tvProductQty);
			txtProductPrice = (TextView) row.findViewById(R.id.tvProductPrice);
		}
		
		public void poulateFrom(Product product){
			imgProduct.setBackgroundResource(R.drawable.add_button);
			txtProductName.setText(product.getName());
			txtProductStatus.setText("Trạng thái: "+product.getStringStatus());
			txtProductBarcode.setText("Barcode: "+product.getBarcode());
			txtProductQty.setText("Số lượng: "+product.getQty());
			txtProductPrice.setText("Giá: "+product.getPrice());
		}
	}


}
