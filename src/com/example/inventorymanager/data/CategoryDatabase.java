package com.example.inventorymanager.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CategoryDatabase {

	public static final String TABLE_NAME = "category";
	public static final String COLUMN_ID = "category_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_COUNT = "count";
	public static final String COLUMN_CREATED = "created_at";
	public static final String COLUMN_UPDATED = "updated_at";
	DatabaseHelper dbHelper;

	public CategoryDatabase(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public static void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE "
				+ TABLE_NAME
				+ "("
				+ COLUMN_ID
				+ " integer primary key autoincrement,"
				+ COLUMN_NAME
				+ " text,"
				+ COLUMN_DESCRIPTION
				+ " text,"
				+ COLUMN_STATUS
				+ " TINYINT,"
				+ COLUMN_COUNT
				+ " TINYINT,"
				+ COLUMN_CREATED
				+ " timestamp DEFAULT CURRENT_TIMESTAMP,"
				+ COLUMN_UPDATED
				+ " timestamp DEFAULT CURRENT_TIMESTAMP)";
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
	 * @return 1: nếu thêm thành công
	 */
	public long addCategory(Category c) {
		if (!isExistedID(c.getName())) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(COLUMN_NAME, c.getName());
			values.put(COLUMN_DESCRIPTION, c.getDescription());
			values.put(COLUMN_STATUS, c.getStatus());
			values.put(COLUMN_COUNT, c.getCount());
			return db.insert(TABLE_NAME, null, values);
		} else {
			return -2;
		}
	}

	/**
	 * Kiểm tra ten category co trong bang chua
	 * 
	 * @param name
	 *            : ten cua category
	 * @return true: nếu đã tồn tại
	 * @return false: nếu chưa tồn tại
	 */
	public boolean isExistedID(String name) {
		try {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			String Query = "Select * from " + TABLE_NAME + " where "
					+ COLUMN_NAME + " = ? limit 1";
			Cursor cursor = db.rawQuery(Query, new String[] { name });
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
	 * Lấy tất cả category trong CSDL
	 * 
	 * @return: list category
	 */
	public ArrayList<Category> getAllCategory() {
		ArrayList<Category> list = new ArrayList<Category>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "Select * from " + TABLE_NAME;
		Cursor cs = db.rawQuery(sql, null);
		if (cs.getCount() > 0) {
			cs.moveToFirst();
			while (cs.isAfterLast() == false) {
				Category category = new Category(cs.getInt(cs.getColumnIndex(COLUMN_ID)), 
						cs.getString(cs.getColumnIndex(COLUMN_NAME)),
						cs.getString(cs.getColumnIndex(COLUMN_DESCRIPTION)), 
						cs.getInt(cs.getColumnIndex(COLUMN_STATUS)), 
						cs.getInt(cs.getColumnIndex(COLUMN_COUNT)),
						cs.getString(cs.getColumnIndex(COLUMN_CREATED)),
						cs.getString(cs.getColumnIndex(COLUMN_UPDATED)));
				list.add(category);
				cs.moveToNext();
			}
		}
		cs.close();
		return list;

	}

	/**
	 * Cập nhật thông tin một category
	 * 
	 * @param category
	 *            : Category
	 * @return 0: nếu không thể cập nhật
	 * @return > 0: nếu cập nhật thành công
	 */
	public int updateCategory(Category category) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Log.d("asdf", category.getName());
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, category.getName());
		values.put(COLUMN_DESCRIPTION, category.getDescription());
		values.put(COLUMN_STATUS, category.getStatus());
		values.put(COLUMN_COUNT, category.getCount());
		values.put(COLUMN_UPDATED, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
				new String[] { Integer.toString(category.getId()) });

	}

	/**
	 * Xóa một category ra khỏi bảng
	 * 
	 * @param id
	 *            : id của category cần xóa
	 * @return 0: nếu không xoa thanh cong
	 * @return > 0: nếu xoa thành công
	 */
	public Integer removeCategory(Integer id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		return db.delete(TABLE_NAME, COLUMN_ID + " = ?",
				new String[] { Integer.toString(id) });
	}

	public Category getCategoryByID(Integer id) {
		Category category = new Category();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String query = "Select * from " + TABLE_NAME + " where " + COLUMN_ID
				+ " = ? limit 1";

		Cursor cursor = db.rawQuery(query,
				new String[] { Integer.toString(id) });
		if (cursor.getCount() == 1) {
			category.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
			category.setName(cursor.getString(cursor
					.getColumnIndex(COLUMN_NAME)));
			category.setDescription(cursor.getString(cursor
					.getColumnIndex(COLUMN_DESCRIPTION)));
			category.setStatus(cursor.getInt(cursor
					.getColumnIndex(COLUMN_STATUS)));
			category.setCount(cursor.getInt(cursor
					.getColumnIndex(COLUMN_COUNT)));
			category.setCreated_at(cursor.getString(cursor
					.getColumnIndex(COLUMN_CREATED)));
			category.setUpdate_at(cursor.getString(cursor
					.getColumnIndex(COLUMN_UPDATED)));
		}
		cursor.close();
		return category;
	}

}
