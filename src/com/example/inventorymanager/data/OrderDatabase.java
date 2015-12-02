package com.example.inventorymanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class OrderDatabase {
	public static final String TABLE_NAME = "orders";
	public static final String COLUMN_ID = "order_id";
	public static final String COLUMN_TOTAL_PRICE = "total_price";
	public static final String COLUMN_TOTAL_QTY = "total_qty";
	public static final String COLUMN_DATE = "order_date";
	public static final String COLUMN_STATE = "state";

	DatabaseHelper dbHelper;

	public OrderDatabase(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public static void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE "
				+ TABLE_NAME
				+ "("
				+ COLUMN_ID
				+ " integer primary key autoincrement,"
				+ COLUMN_TOTAL_PRICE
				+ " integer NOT NULL DEFAULT '0',"
				+ COLUMN_TOTAL_QTY
				+ " integer NOT NULL DEFAULT '0',"
				+ COLUMN_DATE
				+ " timestamp DEFAULT CURRENT_TIMESTAMP,"
				+ COLUMN_STATE
				+ " TINYINT)";
		db.execSQL(sql);

	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		// TODO Auto-generated method stub

	}
}
