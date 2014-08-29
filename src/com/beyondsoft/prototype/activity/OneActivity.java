package com.beyondsoft.prototype.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.beyondsoft.prototype.R;
import com.beyondsoft.prototype.fragment.ListViewFragment;
import com.beyondsoft.widget.viewpager.TabPageIndicator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class OneActivity extends SherlockFragmentActivity {
    private static final String[] CONTENT = new String[] { "All", "Finance", "Technology", "Cars", "House", "Woman" };
    private SlidingMenu menu;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pagers);
 
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sliding_menu);

        FragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) { }

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }

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
            if(!menu.isShown())
            {
            	menu.showMenu(true);
            }
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
        switch(item.getItemId()){    
        case R.id.menu_setting:    
            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();    
            break;    
        }    
        return super.onOptionsItemSelected(item);    
    } 
    
    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	ListViewFragment fragment = ListViewFragment.newInstance(CONTENT[position % CONTENT.length]);
        	fragment.setDataCategory(CONTENT[position]);
        	return fragment;
            //return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
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
