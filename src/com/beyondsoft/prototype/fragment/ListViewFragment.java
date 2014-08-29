package com.beyondsoft.prototype.fragment;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.beyondsoft.datatask.BaseDataTask;
import com.beyondsoft.prototype.R;
import com.beyondsoft.prototype.model.ListViewItem;
import com.beyondsoft.prototype.model.News;
import com.beyondsoft.widget.xlistview.XListView;
import com.beyondsoft.widget.xlistview.XListView.IXListViewListener;

public class ListViewFragment extends BaseFragment implements IXListViewListener {
    private static final String KEY_CONTENT = "ListViewFragment:Content";

	private XListView mListView;
	protected ArrayAdapter<ListViewItem> listViewAdapter;
	protected ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
	private Handler mHandler;
	private static int refreshCnt = 0;
	protected Context context;
	private String dataCategory;
	private String dataUrl;

   public ListViewFragment() 
   {
	   super();
   }
	   
    public static ListViewFragment newInstance(String content)
    {
    	ListViewFragment fragment = new ListViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	context = this.getActivity();
 	    this.setmDataTask(new BaseDataTask(context));

 	    initListView();
 	    
        mListView = new XListView(this.getActivity());
		mListView.setPullLoadEnable(true);
		listViewAdapter = new ArrayAdapter<ListViewItem>(context, R.layout.list_item, items);
		listViewAdapter.setNotifyOnChange(true);
		mListView.setAdapter(listViewAdapter);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
		
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        layout.setGravity(Gravity.CENTER);
        layout.addView(mListView);

        this.dataTask.start(0, true, "loading data...",null);
        
        return layout;
    }

	@Override
	public Object doDataTask(int type, Object... p) throws Exception {

		cleanData();
		refreshListView();
		return null;
	}

	@Override
	public void doProcessData(int type, Object... values) {
	}

	@Override
	public void doProcessError(int type, String errorMsg) {
	}
	
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    
	private void didLoaded() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		String lastUpdated = Calendar.getInstance().getTime().toLocaleString();
		mListView.setRefreshTime(lastUpdated);
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				items.clear();
				reloadData();
				listViewAdapter = new ArrayAdapter<ListViewItem>(ListViewFragment.this.getActivity(), R.layout.list_item, items); 
				mListView.setAdapter(listViewAdapter);
				didLoaded();
			}
		}, 2000);
	}
	
	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadMore();
				didLoaded();
			}
		}, 2000);
	}

	protected void cleanData()
	{
		items = new ArrayList<ListViewItem>();
	}
	
	protected void refreshListView()
	{
		if (items.size() == 0)
		{
			items.add(new ListViewItem());
		}
	}

	
	protected void saveData()
	{
	}
	
	protected void initListView() 
	{
		if (items.size() == 0)
		{
			items.add(new ListViewItem());
		}
	}
	
	protected void reloadData()
	{
		cleanData();
		refreshListView();
		this.getListViewAdapter().notifyDataSetChanged();
		saveData();
	}
	
	protected void loadMore()
	{
		reloadData();
	}

	/**
	 * @return the dataCategory
	 */
	public String getDataCategory() {
		return dataCategory;
	}

	/**
	 * @param dataCategory the dataCategory to set
	 */
	public void setDataCategory(String dataCategory) {
		this.dataCategory = dataCategory;
	}

	/**
	 * @return the dataUrl
	 */
	public String getDataUrl() {
		return dataUrl;
	}

	/**
	 * @param dataUrl the dataUrl to set
	 */
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	/**
	 * @return the listViewAdapter
	 */
	public ArrayAdapter<ListViewItem> getListViewAdapter() {
		return listViewAdapter;
	}

	/**
	 * @param listViewAdapter the listViewAdapter to set
	 */
	public void setListViewAdapter(ArrayAdapter<ListViewItem> listViewAdapter) {
		this.listViewAdapter = listViewAdapter;
	}

	/**
	 * @return the items
	 */
	public ArrayList<ListViewItem> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(ArrayList<ListViewItem> items) {
		this.items = items;
	}
}
