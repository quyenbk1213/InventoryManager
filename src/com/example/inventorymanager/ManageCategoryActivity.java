package com.example.inventorymanager;


import java.util.ArrayList;

import com.example.inventorymanager.adapter.CategoryAdapter;
import com.example.inventorymanager.data.Category;
import com.example.inventorymanager.data.CategoryDatabase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ManageCategoryActivity extends Activity {
	ListView lvCategory;
	Button btnAddCategory;
	CategoryAdapter adapter;
	ArrayList<Category> data = new ArrayList<Category>();
	int positionLV;
	
	CategoryDatabase categorydb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_category);
		categorydb = new CategoryDatabase(this);
		initWidget();
	}
	
	private void initWidget(){
		lvCategory = (ListView) findViewById(R.id.lvCategory);
		btnAddCategory = (Button) findViewById(R.id.btnAddCategory);
		
		
		btnAddCategory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openAddCategoryActivity();				
			}
		});
		
		adapter = new CategoryAdapter(this, R.layout.row_category, data);
		lvCategory.setAdapter(adapter);
		lvCategory.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openDetailActivity(data.get(position));
			}
		});
		lvCategory.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				positionLV = position;
				return false;
			}
		});
		registerForContextMenu(lvCategory);
	}
	
	protected void openAddCategoryActivity(){
		Intent intent = new Intent(this, AddCategoryActivity.class);
		startActivity(intent);
	}
	
	protected void onResume() {
		super.onResume();
		data.clear();
		data.addAll(categorydb.getAllCategory());
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_category, menu);
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.context, menu);
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
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_delete:
			deleteCategory();
			break;
		case R.id.action_edit:
			openEditActivity();
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	private void deleteCategory(){
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Xác nhận");
		b.setMessage("Bạn thực sự muốn xóa loại sản phẩm: " + data.get(positionLV).getName());
		
		b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				categorydb.removeCategory(data.get(positionLV).getId());
				data.remove(positionLV);
				adapter.notifyDataSetChanged();
				dialog.cancel();
				Toast.makeText(getApplicationContext(), "Xóa thành công!",
						Toast.LENGTH_LONG).show();
				
			}
		});
		b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		b.create().show();
		
		
	}
	
	private void openEditActivity(){ 
		Intent intent = new Intent(this, AddCategoryActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("Category", data.get(positionLV));
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	protected void openDetailActivity(Category category) {
		Intent intent = new Intent(this, DetailCategoryActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("Category", category);
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
