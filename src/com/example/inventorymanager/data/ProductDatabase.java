package com.example.inventorymanager.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ProductDatabase {
	public static final String TABLE_NAME = "product";
	public static final String COLUMN_ID = "product_id";
	public static final String COLUMN_BARCODE = "barcode";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_QTY = "qty";
	public static final String COLUMN_IMAGE = "image";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_CATEGORY_ID = "category_id";
	public static final String COLUMN_CREATED = "created_at";
	public static final String COLUMN_UPDATED = "updated_at";
	DatabaseHelper dbHelper;

	public ProductDatabase(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public static void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE "
				+ TABLE_NAME
				+ "("
				+ COLUMN_ID 
				+ " integer primary key autoincrement,"
				+ COLUMN_BARCODE
				+ " text NOT NULL,"
				+ COLUMN_NAME
				+ " text NOT NULL,"
				+ COLUMN_DESCRIPTION
				+ " text,"
				+ COLUMN_PRICE
				+ " integer NOT NULL DEFAULT '0',"
				+ COLUMN_QTY
				+ " integer,"
				+ COLUMN_IMAGE
				+ " blob,"
				+ COLUMN_STATUS
				+ " TINYINT,"
				+ COLUMN_CATEGORY_ID
				+ " integer,"
				+ COLUMN_CREATED
				+ " timestamp DEFAULT CURRENT_TIMESTAMP,"
				+ COLUMN_UPDATED
				+ " timestamp DEFAULT CURRENT_TIMESTAMP,"
				+ "CONSTRAINT `fk_product_category_id` FOREIGN KEY (`"
				+ COLUMN_CATEGORY_ID + "`) REFERENCES `"
				+ CategoryDatabase.TABLE_NAME + "` (`"
				+ CategoryDatabase.COLUMN_ID + "`))";
		db.execSQL(sql);

	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * Thêm category CSDL
	 * 
	 * @param s
	 *            : category
	 * @return -2: nếu category đã tồn tại trong bảng
	 * @return -1: nếu thêm không thành công
	 * @return row_id: nếu thêm thành công
	 */
	public long addProduct(Product p) {
		if (!isExistedBarcode(p.getBarcode())) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues productValues = new ContentValues();
			productValues.put(COLUMN_BARCODE, p.getBarcode());
			productValues.put(COLUMN_NAME, p.getName());
			productValues.put(COLUMN_DESCRIPTION, p.getDescription());
			productValues.put(COLUMN_PRICE, p.getPrice());
			productValues.put(COLUMN_QTY, p.getQty());
			productValues.put(COLUMN_IMAGE, p.getImage());
			productValues.put(COLUMN_STATUS, p.getStatus());
			productValues.put(COLUMN_CATEGORY_ID, p.getCategory_id());
			long result = db.insert(TABLE_NAME, null, productValues);
			if(result != -1){
				
				return result;
			}else{
				return result;
			}			
		} else {
			return -2;
		}
	}

	/**
	 * Kiểm tra barcode co trong bang chua
	 * 
	 * @param name
	 *            : ten cua category
	 * @return true: nếu đã tồn tại
	 * @return false: nếu chưa tồn tại
	 */
	public boolean isExistedBarcode(String barcode) {
		try {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			String Query = "Select * from " + TABLE_NAME + " where "
					+ COLUMN_BARCODE + " = ? limit 1";

			Cursor cursor = db.rawQuery(Query, new String[] { barcode });
			if (cursor.getCount() == 1) {
				cursor.close();
				return true;
			}
			cursor.close();
			return false;
		} catch (Exception e) {
			Log.d("my_app", e.toString());
			return true;
		}

	}

	/**
	 * Lấy tất cả product trong CSDL
	 * 
	 * @return: list product
	 */
	public ArrayList<Product> getAllProduct() {
		ArrayList<Product> list = new ArrayList<Product>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "Select * from " + TABLE_NAME;
		Cursor cs = db.rawQuery(sql, null);
		if (cs.getCount() > 0) {
			cs.moveToFirst();
			while (cs.isAfterLast() == false) {
				Product product = new Product(cs.getInt(cs
						.getColumnIndex(COLUMN_ID)), cs.getString(cs
						.getColumnIndex(COLUMN_BARCODE)), cs.getString(cs
						.getColumnIndex(COLUMN_NAME)), cs.getString(cs
						.getColumnIndex(COLUMN_DESCRIPTION)), cs.getInt(cs
						.getColumnIndex(COLUMN_PRICE)), cs.getInt(cs
						.getColumnIndex(COLUMN_QTY)), cs.getBlob(cs
						.getColumnIndex(COLUMN_IMAGE)), cs.getInt(cs
						.getColumnIndex(COLUMN_STATUS)), cs.getInt(cs
						.getColumnIndex(COLUMN_CATEGORY_ID)), cs.getString(cs
						.getColumnIndex(COLUMN_CREATED)), cs.getString(cs
						.getColumnIndex(COLUMN_UPDATED)));
				list.add(product);
				cs.moveToNext();
			}
		}
		cs.close();
		return list;
	}

	/**
	 * Cập nhật thông tin một product
	 * 
	 * @param p
	 *            : product
	 * @return 0: nếu không thể cập nhật
	 * @return > 0: nếu cập nhật thành công
	 */
	public int updateProduct(Product p) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_BARCODE, p.getBarcode());
		values.put(COLUMN_NAME, p.getName());
		values.put(COLUMN_DESCRIPTION, p.getDescription());
		values.put(COLUMN_PRICE, p.getPrice());
		values.put(COLUMN_QTY, p.getQty());
		values.put(COLUMN_IMAGE, p.getImage());
		values.put(COLUMN_STATUS, p.getStatus());
		values.put(COLUMN_CATEGORY_ID, p.getCategory_id());

		return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
				new String[] { Integer.toString(p.getId()) });
	}
	
	/**
	 * Xóa một product ra khỏi bảng
	 * 
	 * @param id
	 *            : id của product cần xóa
	 * @return 0: nếu không xoa thanh cong
	 * @return > 0: nếu xoa thành công
	 */
	public Integer removeProduct(Integer id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		return db.delete(TABLE_NAME, COLUMN_ID + " = ?",
				new String[] { Integer.toString(id) });
	}
	
	public Product getProductByID(Integer id) {
		Product product = new Product();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String query = "Select * from " + TABLE_NAME + " where " + COLUMN_ID
				+ " = ? limit 1";

		Cursor cursor = db.rawQuery(query,
				new String[] { Integer.toString(id) });
		if (cursor.getCount() == 1) {
			product.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
			product.setBarcode(cursor.getString(cursor.getColumnIndex(COLUMN_BARCODE)));
			product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
			product.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
			product.setPrice(cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)));
			product.setQty(cursor.getInt(cursor.getColumnIndex(COLUMN_QTY)));
			product.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE)));
			product.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
			product.setCategory_id(cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID)));
			product.setCreated_at(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED)));
			product.setUpdate_at(cursor.getString(cursor.getColumnIndex(COLUMN_UPDATED)));
		}
		cursor.close();
		return product;
	}
	
	public Product getProductByBarcode(String barcode) {
		Product product = new Product();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String query = "Select * from " + TABLE_NAME + " where " + COLUMN_BARCODE + " = ? limit 1";

		Cursor cursor = db.rawQuery(query, new String[] { barcode });
		if (cursor.getCount() == 1) {
			product.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
			product.setBarcode(cursor.getString(cursor.getColumnIndex(COLUMN_BARCODE)));
			product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
			product.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
			product.setPrice(cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)));
			product.setQty(cursor.getInt(cursor.getColumnIndex(COLUMN_QTY)));
			product.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE)));
			product.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
			product.setCategory_id(cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID)));
			product.setCreated_at(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED)));
			product.setUpdate_at(cursor.getString(cursor.getColumnIndex(COLUMN_UPDATED)));
		}
		cursor.close();
		return product;
	}
}
