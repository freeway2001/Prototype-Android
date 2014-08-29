package com.beyondsoft.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.beyondsoft.prototype.model.News;
import com.beyondsoft.util.Logs;

public class NewsService extends DataService{
	
	public NewsService(Context context) 
	{
		super(context);
	}

	public List<News> loadData()
	{
		db = dbHelper.getReadableDatabase();
		List<News> newsList = new ArrayList<News>();
		String sql = "SELECT * FROM NEWS";
		Cursor cursor = null;
		try
		{
			cursor = db.rawQuery(sql,null);
			while (cursor.moveToNext())
			{
				News news = new News();
				news.setId(cursor.getString(0));
				news.setTitle(cursor.getString(1));
				news.setBrief(cursor.getString(2));
				news.setImageUrl(cursor.getString(3));
				news.setContent(cursor.getString(4));
				newsList.add(news);
			}
		}catch(Exception e)
		{
			Logs.e(e.getMessage());
		}finally {
			if (cursor != null)
				cursor.close();
			if (db != null)
				db.close();
		}
		
		return newsList;
	}


	public void saveData(List newsList)
	{
		db = dbHelper.getWritableDatabase();
		
		String tableName = "NEWS";
		String nullColumnHack = null;
		ContentValues values;
		try
		{
			for (Object obj:newsList)
			{
				News news = (News) obj;	
				values = new ContentValues();
				values.put("id", news.getId());
				values.put("title", news.getTitle());
				values.put("brief", news.getBrief());
				values.put("imageurl", news.getImageUrl());
				values.put("content", news.getContent());
				db.insert(tableName, nullColumnHack, values);
			}
		}finally {
			if (db != null)
				db.close();
		}
	}
	
	public void cleanData()
	{
		db = dbHelper.getWritableDatabase();
		List<News> newsList = new ArrayList<News>();
		String sql = "DELETE FROM NEWS";
		try
		{
			db.rawQuery(sql,null);
	
		}catch(Exception e)
		{
			Logs.e(e.getMessage());
		}finally {
			if (db != null)
				db.close();
		}

	}
	
}