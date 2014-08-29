package com.beyondsoft.prototype.fragment;

import java.util.ArrayList;

import com.beyondsoft.database.NewsService;
import com.beyondsoft.prototype.model.ListViewItem;
import com.beyondsoft.prototype.model.News;

public class NewsListViewFragment extends ListViewFragment {

	private ArrayList<News> newsList = new ArrayList<News>();
	
	public NewsListViewFragment() 
	{
		super();
	}

	protected void cleanData()
	{
		super.cleanData();
		NewsService newsService = new NewsService(context);
		newsService.cleanData();
	}
	
	protected void refreshListView()
	{
		newsList = new ArrayList<News>();
		try
		{
	//		String jsonData = WebUtils.doGet(dataUrl);
			for (int i =0; i<30; i++)
			{
				News news = new News();
				news.setId("News " + i);
				news.setTitle("Today's News " + i);
				news.setBrief("News brief description");
				news.setImageUrl("");
				news.setContent("");
				newsList.add(news);
			}
			
		}catch(Exception e)
		{
			
		}
		
		items = reflectView(newsList);
		super.refreshListView();
	}
	
	protected void initListView()
	{
		NewsService newsService = new NewsService(context);
		newsList = (ArrayList<News>)newsService.loadData();
		items = reflectView(newsList);
		super.initListView();
	}
	
	private ArrayList<ListViewItem> reflectView(ArrayList<News> newsList)
	{
		ArrayList<ListViewItem> list = new ArrayList<ListViewItem>();
		for (int i=0; i<newsList.size();i++)
		{
			News news  = newsList.get(i);
			ListViewItem item = new ListViewItem();
			item.setTitle(news.getTitle());
			item.setBrief(news.getBrief());
			item.setImageurl(news.getImageUrl());
			list.add(item);
		}
		return list;
	}
	
	protected void loadMore()
	{
		
	}
	
	protected void saveData()
	{
		NewsService newsService = new NewsService(context);
		newsService.saveData(newsList);
	} 
}
