package com.example.inventorymanager;

import java.util.ArrayList;

import com.example.inventorymanager.data.Category;
import com.example.inventorymanager.data.CategoryDatabase;
import com.example.inventorymanager.data.SpinerLabel;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddCategoryActivity extends Activity {

	private Spinner spnCategoryStatus;
	private EditText edtCategoryName, edtCategoryDescription;
	private Button btnSaveCategory, btnCancelCategory;

	private ArrayAdapter<SpinerLabel> spinAdapter;

	private ArrayList<SpinerLabel> arrStatus = new ArrayList<SpinerLabel>();

	private int categoryID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_category);
		
		arrStatus.add(new SpinerLabel(Category.ENABLE, Category.ENABLE_STRING));
		arrStatus.add(new SpinerLabel(Category.DISABLE,
				Category.DISABLE__STRING));
		
		
		initWidget();
		try {
			Category category = (Category) getIntent().getExtras()
					.getSerializable("Category");
			setWidget(category);
			categoryID = category.getId();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initWidget() {
		spnCategoryStatus = (Spinner) findViewById(R.id.spnCategoryStatus);
		edtCategoryName = (EditText) findViewById(R.id.edtCategoryName);
		edtCategoryDescription = (EditText) findViewById(R.id.edtCategoryDescription);
		btnSaveCategory = (Button) findViewById(R.id.btnSaveCategory);
		btnCancelCategory = (Button) findViewById(R.id.btnCancelCategory);

		spinAdapter = new ArrayAdapter<SpinerLabel>(this,
				android.R.layout.simple_spinner_item, arrStatus);
		spnCategoryStatus.setAdapter(spinAdapter);

		btnSaveCategory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addCategory();
			}
		});

		btnCancelCategory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	private void setWidget(Category category) {
		
		int selection = arrStatus.indexOf(new SpinerLabel(category.getStatus(), category.getStringStatus()));
		spnCategoryStatus.setSelection(selection);
		edtCategoryName.setText(category.getName());
		edtCategoryDescription.setText(category.getDescription());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_category, menu);
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

	protected void addCategory() {
		Category category = createCategory();
		if (category != null) {
			int result;
			if (categoryID != 0) {
				Log.d("asdf", "a");
				result = new CategoryDatabase(this).updateCategory(category);
				if (result == -2) {
					sendMessage("Loại sản phẩm " + category.getName()
							+ " đã tồn tại");
				} else if (result == -1) {
					sendMessage("Không thể thêm vào cơ sở dữ liệu");
				} else {
					sendMessage("Sửa thành công");
				}
			} else {
				result = (int) new CategoryDatabase(this).addCategory(category);
				if (result == -1) {
					sendMessage("Không thể thêm vào cơ sở dữ liệu");
				} else {
					sendMessage("Thêm thành công");
				}
			}
			finish();
		}		
	}

	private Category createCategory() {
		Category category = null;

		int positionSelected = spnCategoryStatus.getSelectedItemPosition();
		int status = arrStatus.get(positionSelected).getValue();
		String name = edtCategoryName.getText().toString().trim();
		String description = edtCategoryDescription.getText().toString().trim();
		if (checkInfo(name)) {
			category = new Category(name, description, status);
			if(categoryID != 0) category.setId(categoryID);
		}
		return category;
	}

	private boolean checkInfo(String name) {
		if (name.equals("")) {
			sendMessage("Hãy nhập tên loại sản phẩm");
			return false;
		}

		return true;
	}

	private void sendMessage(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
