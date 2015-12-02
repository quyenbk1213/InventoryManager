package com.example.inventorymanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class OrderItemDatabase {
	public static final String TABLE_NAME = "order_item";
	public static final String COLUMN_ID = "order_item_id";
	public static final String COLUMN_ORDER_ID = "order_id";
	public static final String COLUMN_PRODUCT_ID = "product_id";
	public static final String COLUMN_BARCODE = "barcode";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_QTY = "qty";
	public static final String COLUMN_ROW_TOTAL = "row_total";
	public static final String COLUMN_CATEGORY_ID = "category_id";

	DatabaseHelper dbHelper;

	public OrderItemDatabase(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public static void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE "
				+ TABLE_NAME
				+ "("
				+ COLUMN_ID
				+ " integer primary key autoincrement,"
				+ COLUMN_ORDER_ID
				+ " integer,"
				+ COLUMN_PRODUCT_ID
				+ " integer,"
				+ COLUMN_BARCODE
				+ " text NOT NULL,"
				+ COLUMN_NAME
				+ " text NOT NULL,"
				+ COLUMN_PRICE
				+ " integer NOT NULL DEFAULT '0',"
				+ COLUMN_QTY
				+ " integer,"
				+ COLUMN_ROW_TOTAL
				+ " integer NOT NULL DEFAULT '0',"
				+ "CONSTRAINT `fk_order_item_order_id` FOREIGN KEY (`"
				+ COLUMN_ORDER_ID + "`) REFERENCES `"
				+ OrderDatabase.TABLE_NAME + "` (`"
				+ OrderDatabase.COLUMN_ID + "`)"
				+ "CONSTRAINT `fk_order_item_product_id` FOREIGN KEY (`"
				+ COLUMN_PRODUCT_ID + "`) REFERENCES `"
				+ ProductDatabase.TABLE_NAME + "` (`"
				+ ProductDatabase.COLUMN_ID + "`))";
		db.execSQL(sql);

	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		// TODO Auto-generated method stub

	}
}
