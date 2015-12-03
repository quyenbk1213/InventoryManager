package com.example.inventorymanager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.example.inventorymanager.data.Category;
import com.example.inventorymanager.data.CategoryDatabase;
import com.example.inventorymanager.data.Product;
import com.example.inventorymanager.data.ProductDatabase;
import com.example.inventorymanager.data.SpinerLabel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProductActivity extends Activity {

	private final static Integer REQUESTCODE_BROWSE = 10;
	private final static Integer REQUESTCODE_CAPTURE = 11;

	private Spinner spnProductStatus, spnProductCategory;
	private EditText edtProductBarcode, edtProductName, edtProductDescription,
			edtProductPrice, edtProductQty;
	private Button btnAddProductQty, btnMinusProductQty;
	private Button btnCaptureProductImage, btnBrowseProductImage;
	private Button btnSaveProduct, btnCancelProduct;
	private ImageButton imbBarcode;
	private ImageView imgProduct;

	private ArrayAdapter<SpinerLabel> spinStatusAdapter;
	private ArrayAdapter<SpinerLabel> spinCategoryAdapter;
	private ArrayList<SpinerLabel> arrStatus = new ArrayList<SpinerLabel>();
	private ArrayList<SpinerLabel> arrCategory = new ArrayList<SpinerLabel>();
	
	private int productID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_product);
		arrStatus.add(new SpinerLabel(Category.ENABLE, Category.ENABLE_STRING));
		arrStatus.add(new SpinerLabel(Category.DISABLE,
				Category.DISABLE__STRING));
		ArrayList<Category> listCategory = new CategoryDatabase(this)
				.getAllEnableCategory();
		for (int i = 0; i < listCategory.size(); i++) {
			Category category = listCategory.get(i);
			arrCategory.add(new SpinerLabel(category.getId(), category
					.getName()));
		}

		initWidget();
		//Edit
		try {
			Product product = (Product) getIntent().getExtras()
					.getSerializable("Product");
			setWidget(product);
			productID = product.getId();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initWidget() {
		edtProductBarcode = (EditText) findViewById(R.id.edtProductBarcode);
		edtProductName = (EditText) findViewById(R.id.edtProductName);
		edtProductDescription = (EditText) findViewById(R.id.edtProductDescription);
		edtProductPrice = (EditText) findViewById(R.id.edtProductPrice);
		edtProductQty = (EditText) findViewById(R.id.edtProductQty);

		spnProductStatus = (Spinner) findViewById(R.id.spnProductStatus);
		spnProductCategory = (Spinner) findViewById(R.id.spnProductCategory);

		imbBarcode = (ImageButton) findViewById(R.id.imbBarcode);
		imgProduct = (ImageView) findViewById(R.id.imgProduct);

		btnAddProductQty = (Button) findViewById(R.id.btnAddProductQty);
		btnMinusProductQty = (Button) findViewById(R.id.btnMinusProductQty);

		// QuyenNH spinner status
		spinStatusAdapter = new ArrayAdapter<SpinerLabel>(this,
				android.R.layout.simple_spinner_item, arrStatus);
		spnProductStatus.setAdapter(spinStatusAdapter);

		// QuyenNH spinner Category
		spinCategoryAdapter = new ArrayAdapter<SpinerLabel>(this,
				android.R.layout.simple_spinner_item, arrCategory);
		spnProductCategory.setAdapter(spinCategoryAdapter);

		// QuyenNH scan barcode
		imbBarcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				scanBarcode();
			}
		});

		btnCaptureProductImage = (Button) findViewById(R.id.btnCaptureProductImage);
		btnCaptureProductImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				captureProductImage();
			}
		});

		btnBrowseProductImage = (Button) findViewById(R.id.btnBrowseProductImage);
		btnBrowseProductImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				browseProductImage();
			}
		});

		btnSaveProduct = (Button) findViewById(R.id.btnSaveProduct);
		btnSaveProduct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveProduct();
			}
		});

		btnCancelProduct = (Button) findViewById(R.id.btnCancelProduct);
		btnCancelProduct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btnAddProductQty.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addProductQty();
			}
		});

		btnMinusProductQty.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				minusProductQty();
			}
		});
	}
	
	private void setWidget(Product product) {
		

	}

	protected void addProductQty() {
		try {
			String strQty = edtProductQty.getText().toString().trim();
			int qty = Integer.parseInt(strQty);
			edtProductQty.setText(Integer.toString(qty + 1));
		} catch (Exception e) {
			sendMessage("Hãy nhập số lượng sản phẩm");
		}
	}

	protected void minusProductQty() {
		try {
			String strQty = edtProductQty.getText().toString().trim();
			int qty = Integer.parseInt(strQty);
			edtProductQty.setText(Integer.toString(qty - 1));
		} catch (Exception e) {
			sendMessage("Hãy nhập số lượng sản phẩm");
		}
	}

	protected void scanBarcode() {
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();
	}

	protected void captureProductImage() {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(intent, REQUESTCODE_CAPTURE);
		}
	}

	protected void browseProductImage() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"),
				REQUESTCODE_BROWSE);
	}

	protected void saveProduct() {
		Product product = createProduct();
		if(product !=null){
			int result;
			if (productID != 0) {
				
			} else {
				result = (int) new ProductDatabase(this).addProduct(product);
				if (result == -1) {
					sendMessage("Không thể thêm vào cơ sở dữ liệu");
				} else {
					sendMessage("Thêm thành công");
				}
			}
			finish();
		}
	}

	private Product createProduct() {
		Product product = null;

		String barcode = edtProductBarcode.getText().toString().trim();
		String name = edtProductName.getText().toString().trim();
		String description = edtProductDescription.getText().toString().trim();
		String StrQty = edtProductPrice.getText().toString().trim();
		String StrPrice = edtProductQty.getText().toString().trim();

		if (checkInfo(barcode, name, description, StrQty, StrPrice)) {
			int id = 0;
			int pStatusSelected = spnProductStatus.getSelectedItemPosition();
			int status = arrStatus.get(pStatusSelected).getValue();

			int pCategorySelected = spnProductCategory
					.getSelectedItemPosition();
			int categoryId = arrCategory.get(pCategorySelected).getValue();

			int qty = Integer.parseInt(StrQty);
			int price = Integer.parseInt(StrPrice);

			// QuyenNH convert bitmap to byte
			BitmapDrawable imgDrawable = (BitmapDrawable) imgProduct
					.getDrawable();
			Bitmap imgBitmap = imgDrawable.getBitmap();
			ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
			imgBitmap.compress(CompressFormat.PNG, 100, outPutStream);
			byte[] image = outPutStream.toByteArray();

			product = new Product(id, barcode, name, description, price, qty, image, status, categoryId);
		}
		return product;
	}

	private boolean checkInfo(String barcode, String name, String description,
			String price, String qty) {
		try {
			Integer.parseInt(price);
		} catch (Exception e) {
			sendMessage("Hãy nhập lại giá sản phẩm");
			return false;
		}

		try {
			Integer.parseInt(qty);
		} catch (Exception e) {
			sendMessage("Hãy nhập lại số lượng");
			return false;
		}

		if (barcode.equals("")) {
			sendMessage("Hãy nhập tên loại sản phẩm");
			return false;
		}
		if (name.equals("")) {
			sendMessage("Hãy nhập tên loại sản phẩm");
			return false;
		}
		if (description.equals("")) {
			sendMessage("Hãy nhập tên loại sản phẩm");
			return false;
		}

		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			edtProductBarcode.setText(scanContent);
		} else if ((requestCode == REQUESTCODE_BROWSE)
				&& (resultCode == RESULT_OK)) {
			Uri uri = intent.getData();
			imgProduct.setImageURI(uri);
		} else if ((requestCode == REQUESTCODE_CAPTURE)
				&& (resultCode == RESULT_OK)) {
			Bundle extras = intent.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			imgProduct.setImageBitmap(imageBitmap);
		} else {
			Toast toast = Toast.makeText(getApplicationContext(),
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_product, menu);
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

	private void sendMessage(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
