package com.example.inventorymanager;

import java.util.ArrayList;

import com.example.inventorymanager.data.Category;
import com.example.inventorymanager.data.SpinerLabel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
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
	
	private Spinner spnProductStatus, edtProductCategory;
	private EditText edtProductBarcode, edtProductName, edtProductDescription,
					 edtProductPrice, edtProductQty;
	private Button btnAddProductQty, btnMinusProductQty;
	private Button btnCaptureProductImage, btnBrowseProductImage;
	private Button btnSaveProduct, btnCancelProduct;
	private ImageButton imbBarcode;
	private ImageView imgProduct;

	private ArrayAdapter<SpinerLabel> spinStatusAdapter;
	private ArrayList<SpinerLabel> arrStatus = new ArrayList<SpinerLabel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_product);
		arrStatus.add(new SpinerLabel(Category.ENABLE, Category.ENABLE_STRING));
		arrStatus.add(new SpinerLabel(Category.DISABLE,
				Category.DISABLE__STRING));

		initWidget();

	}

	private void initWidget() {
		edtProductBarcode = (EditText) findViewById(R.id.edtProductBarcode);
		spnProductStatus = (Spinner) findViewById(R.id.spnProductStatus);
		imbBarcode = (ImageButton) findViewById(R.id.imbBarcode);
		imgProduct = (ImageView) findViewById(R.id.imgProduct);
		spinStatusAdapter = new ArrayAdapter<SpinerLabel>(this,
				android.R.layout.simple_spinner_item, arrStatus);
		spnProductStatus.setAdapter(spinStatusAdapter);

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
	}

	protected void scanBarcode() {
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();
	}

	protected void captureProductImage() {
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(intent, REQUESTCODE_CAPTURE);
		}
	}

	protected void browseProductImage() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUESTCODE_BROWSE);
	}

	protected void saveProduct() {
		// Chua lam
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			edtProductBarcode.setText(scanContent);
		} else if ((requestCode == REQUESTCODE_BROWSE) && (resultCode == RESULT_OK)) {
			Uri uri = intent.getData();
			imgProduct.setImageURI(uri);
		} else if ((requestCode == REQUESTCODE_CAPTURE) && (resultCode == RESULT_OK)) {
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
}
