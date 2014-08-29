package com.beyondsoft.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "myDB.db";
	private SQLiteDatabase db;

	private static final int VERSION = 1; // 当前数据库版本号。如果当前发布版本较上次有数据库方面的更新，此变量须依次递增

	// 定义升级数据库所需的SQL
	private static final String strSQL1 = "";
	private static final String strSQL2 = "";

	// 除初始版本外，历次升级数据库的SQL语句集合
	// 每升级数据库，增加一个SQL语句集合
	private List<String> v2SQL = new ArrayList<String>(); // 版本2的数据库升级SQL
	private List<String> v3SQL = new ArrayList<String>(); // 版本3的数据库升级SQL
	private List<String> v4SQL = new ArrayList<String>(); // 版本4的数据库升级SQL

	// 所有的数据库升级SQL语句，以版本号为key值，放入此Map中
	private Map vSQL = new HashMap();

	/*
	 * { //SQL for version 2; v2SQL.add(strSQL1); v2SQL.add(strSQL2);
	 * 
	 * //SQL for version 3; v3SQL.add(strCreateTable3);
	 * 
	 * //SQL for version 4; v4SQL.add(strCreateTable4);
	 * 
	 * //vSQL.put(2, v2SQL); vSQL.put(3, v3SQL);
	 * // vSQL.put(4, v4SQL); }
	 */

	public SqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		System.out.print("---create database now...");
		// 以下是数据库初始化SQL
		db.execSQL("create table if not exists news(id varchar(10), title varchar(80), brief varchar(255), imageurl varchar(100), content varchar(4000))");
				

		// 执行数据库升级SQL
		/*
		 * db.execSQL(strSQL1); db.execSQL(strSQL2);
		 */

	}

	/**
	 * App安装时，如果检测到新安装的数据库版本比已安装的高，则调用此方法，升级数据库。
	 * 
	 * @param db
	 *            数据库
	 * @param oldVersion
	 *            设备上的App数据库版本
	 * @param newVersion
	 *            当前App的数据库版本
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {		
		// 依版本号从低到高升级数据库
		for (int ver = oldVersion + 1; ver <= newVersion; ver++) {
			doUpgrade(db, ver);
		}
	}

	public void doUpgrade(SQLiteDatabase db, int ver) {
		ArrayList verSQL = (ArrayList) vSQL.get(ver);
		if (verSQL != null) {
			// 循环运行SQL语句升级数据库
			for (int i = 0; i < verSQL.size(); i++) {
				String sql = (String) verSQL.get(i);
				db.execSQL(sql);
			}
		}
		
	}
	
	public void initData()
	{
		
	}
}
