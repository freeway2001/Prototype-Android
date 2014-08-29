package com.beyondsoft.database;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DataService {
	
	protected SqliteHelper dbHelper;
	protected SQLiteDatabase db;

	public DataService(Context context) 
	{
		dbHelper = new SqliteHelper(context);
	}

	public abstract void cleanData();
	
	public abstract List loadData();
	
	public abstract void saveData(List data);
}