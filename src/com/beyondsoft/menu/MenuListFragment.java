package com.beyondsoft.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.prototype.R;

public class MenuListFragment extends ListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		MenuListAdapter adapter = new MenuListAdapter(getActivity());
		for (int i = 0; i < 5; i++) {
			adapter.add(new MenuItem("Menu " + i, android.R.drawable.ic_menu_search));
		}
		setListAdapter(adapter);
	}

	private class MenuItem implements OnClickListener {
		public String tag;
		public int iconRes;
		public MenuItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
		
		@Override
		public void onClick(View v) {
			 
		}

	}

	public class MenuListAdapter extends ArrayAdapter<MenuItem> {

		public MenuListAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}
}
