package com.beyondsoft.prototype.activity;

import java.util.Arrays;
import java.util.LinkedList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.beyondsoft.prototype.R;
import com.beyondsoft.prototype.fragment.ListViewFragment;
import com.beyondsoft.prototype.fragment.NewsListViewFragment;
import com.beyondsoft.pulltorefresh.listfragment.PullToRefreshListFragment;
import com.beyondsoft.widget.viewpager.TabPageIndicator;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity { //SherlockFragmentActivity {
    private static final String[] CONTENT = new String[] { "All", "Finance", "Technology", "Military", "Realty" };
    private static final String[] DATAURL = new String[] {
    	"http://116.6.96.157:8888/tcms/services/info/foods?appId=ebcad75de0d42a844d98a755644e30&updateTime=",
    	"http://116.6.96.157:8888/tcms/services/info/foods?appId=ebcad75de0d42a844d98a755644e30&updateTime=",
    	"http://116.6.96.157:8888/tcms/services/info/foods?appId=ebcad75de0d42a844d98a755644e30&updateTime=",
    	"http://116.6.96.157:8888/tcms/services/info/foods?appId=ebcad75de0d42a844d98a755644e30&updateTime=",
    	"http://116.6.96.157:8888/tcms/services/info/foods?appId=ebcad75de0d42a844d98a755644e30&updateTime="
    };
    
    private SlidingMenu menu;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pagers);
        setBehindContentView(R.layout.view_pagers);

        //menu = new SlidingMenu(this);
        menu = getSlidingMenu();
        //menu.setMode(SlidingMenu.LEFT);
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        //menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sliding_menu);
 
        menu.setSecondaryShadowDrawable(R.drawable.shadow);
        menu.setSecondaryMenu(R.layout.secondary_sliding_menu);

        FragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        pager.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View e)
        	{
        		Toast.makeText(e.getContext(), "page onclick", Toast.LENGTH_LONG).show();
        	}
        });
        
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
				Toast.makeText(menu.getContext(), "page onPageScrollStateChanged", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { 
				Toast.makeText(menu.getContext(), "page onPageScrolled", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					break;
				default:
					menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
					break;
				}
			}

		});
		
        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
        case android.R.id.home:
        	this.toggle();
            break;
        case R.id.menu_setting:    
           	menu.showSecondaryMenu(true);
            break;  
        default:
        	break;
        }
        return true;
    }
    
    @Override    
    public boolean onCreateOptionsMenu(Menu menu) {    
        getSupportMenuInflater().inflate(R.menu.main_menu, menu);    
        return super.onCreateOptionsMenu(menu);    
    }  
    
    @Override    
    public boolean onOptionsItemSelected(MenuItem item) {      
        return super.onOptionsItemSelected(item);    
    } 
    
    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	//ListViewFragment fragment = ListViewFragment.newInstance(CONTENT[position % CONTENT.length]);
        	ListViewFragment fragment;
        	switch(position)
        	{
        		case 0:
        			fragment = getListViewFragment();
        			break;
        		case 1:
        			fragment = new NewsListViewFragment();
        			break;
        		default:
        			fragment = getListViewFragment();
        			break;
        	}
        	
//        	fragment.setDataCategory(CONTENT[position]);
//        	fragment.setDataUrl(DATAURL[position]);
//        	fragment.setContent(CONTENT[position % CONTENT.length]);
        	return fragment;
        }

        private ListViewFragment getListViewFragment()
        {
        	ListViewFragment fragment = new NewsListViewFragment();
        	return fragment;
        }
        
        private ListViewFragment getPullToRefreshListFragment()
        {
        	String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
        			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
        			"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
        			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
        			"Allgauer Emmentaler" };
        	
        	LinkedList<String> mListItems;
        	ArrayAdapter<String> mAdapter;

        	PullToRefreshListFragment mPullRefreshListFragment;
        	PullToRefreshListView mPullRefreshListView;

			mPullRefreshListFragment = new PullToRefreshListFragment();

			// Get PullToRefreshListView from Fragment
			mPullRefreshListView = mPullRefreshListFragment.getPullToRefreshListView();

			// Set a listener to be invoked when the list should be refreshed.
		//	mPullRefreshListView.setOnRefreshListener(this);

			// You can also just use mPullRefreshListFragment.getListView()
			ListView actualListView = mPullRefreshListView.getRefreshableView();

			mListItems = new LinkedList<String>();
			mListItems.addAll(Arrays.asList(mStrings));
			mAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, mListItems);

			// You can also just use setListAdapter(mAdapter) or
			// mPullRefreshListView.setAdapter(mAdapter)
			actualListView.setAdapter(mAdapter);

			mPullRefreshListFragment.setListShown(true);
			
			return null;
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
          return CONTENT.length;
        }
    }
}
