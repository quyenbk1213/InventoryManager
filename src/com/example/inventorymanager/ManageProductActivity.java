package com.example.inventorymanager;

import com.example.inventorymanager.adapter.CategoryAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ManageProductActivity extends Activity {
	
	ListView lvProduct;
	Button btnAddProduct; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_product);
		initWidget();
	}
	
	private void initWidget(){
		lvProduct = (ListView) findViewById(R.id.lvProduct);
		btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
		
		
		btnAddProduct.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openAddProductActivity();				
			}
		});

	}
	
	protected void openAddProductActivity(){
		Intent intent = new Intent(this, AddProductActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_product, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
