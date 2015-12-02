package com.example.inventorymanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "inventoryDB";
	private static final int DB_VERSION = 1;
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		CategoryDatabase.onCreate(db);
		ProductDatabase.onCreate(db);
		OrderDatabase.onCreate(db);
		OrderItemDatabase.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		CategoryDatabase.onUpgrade(db, oldVersion, newVersion);
		ProductDatabase.onUpgrade(db, oldVersion, newVersion);
		OrderDatabase.onUpgrade(db, oldVersion, newVersion);
		OrderItemDatabase.onUpgrade(db, oldVersion, newVersion);
	}

}
