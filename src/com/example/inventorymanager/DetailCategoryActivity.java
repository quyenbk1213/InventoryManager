package com.example.inventorymanager;

import com.example.inventorymanager.data.Category;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DetailCategoryActivity extends Activity {
	private TextView txtDetailCategoryStatus, textDetailCategoryName, txtDetailCategoryDescription, txtDetailCategoryCount, txtDetailCategoryCreatedAt, txtDetailCategoryUpdatedAt;
	private Button btnBackCategory;
	private Category category;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_category);
		
		category = (Category) getIntent().getExtras().getSerializable("Category");
		initWiget();
	}
	
	private void initWiget(){
		txtDetailCategoryStatus = (TextView) findViewById(R.id.txtDetailCategoryStatus);
		textDetailCategoryName = (TextView) findViewById(R.id.textDetailCategoryName);
		txtDetailCategoryDescription = (TextView) findViewById(R.id.txtDetailCategoryDescription);
		txtDetailCategoryCount = (TextView) findViewById(R.id.txtDetailCategoryCount);
		txtDetailCategoryCreatedAt = (TextView) findViewById(R.id.txtDetailCategoryCreatedAt);
		txtDetailCategoryUpdatedAt = (TextView) findViewById(R.id.txtDetailCategoryUpdatedAt);
		
		btnBackCategory = (Button) findViewById(R.id.btnBackCategory);

		
		txtDetailCategoryStatus.setText(category.getStringStatus());
		textDetailCategoryName.setText(category.getName());
		txtDetailCategoryDescription.setText(category.getDescription());
		txtDetailCategoryCount.setText(Integer.toString(category.getCount()));
		txtDetailCategoryCreatedAt.setText(category.getCreated_at());
		txtDetailCategoryUpdatedAt.setText(category.getUpdate_at());
		btnBackCategory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
		

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_category, menu);
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
