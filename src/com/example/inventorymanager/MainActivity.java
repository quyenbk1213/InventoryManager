package com.example.inventorymanager;

import com.example.inventorymanager.data.Grid;
import com.example.inventorymanager.data.GridViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	private GridView grid;
	private Grid[] gridData;
	private GridViewAdapter gridAdapter;

	public static final int SALES = 0;
	public static final int MANAGE_PRODUCT = 1;
	public static final int MANAGE_CATEGORY = 2;
	public static final int MANAGE_ORDERS = 3;
	public static final int REPORT = 4;
	public static final int SETTINGS = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gridData = new Grid[] { new Grid(R.drawable.sale, "Bán hàng"),
				new Grid(R.drawable.product, "Quản lý sản phẩm"),
				new Grid(R.drawable.category, "Quản lý loại hàng"),
				new Grid(R.drawable.order, "Quản lý đơn hàng"),
				new Grid(R.drawable.report, "Báo cáo"),
				new Grid(R.drawable.exit, "Cấu hình") };

		initWidget();
	}

	private void initWidget() {
		grid = (GridView) findViewById(R.id.gridview);
		gridAdapter = new GridViewAdapter(this, R.layout.grid_main, gridData);
		grid.setAdapter(gridAdapter);

		grid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case SALES:
					openActivity(SalesActivity.class);
					break;
				case MANAGE_PRODUCT:
					openActivity(ManageProductActivity.class);
					break;
				case MANAGE_CATEGORY:
					openActivity(ManageCategoryActivity.class);
					break;
				case MANAGE_ORDERS:
					openActivity(ManageOrderActivity.class);
					break;
				case REPORT:
					openActivity(ReportActivity.class);
					break;
				case SETTINGS:
					finish();
					break;
				default:
					break;
				}
			}
		});
	}

	protected void openActivity(Class<?> cls){
		Log.d("asdf", cls.toString());
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
