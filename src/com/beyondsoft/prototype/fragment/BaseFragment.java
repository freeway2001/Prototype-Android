package com.beyondsoft.prototype.fragment;

import android.support.v4.app.Fragment;

import com.beyondsoft.datatask.BaseDataTask;
import com.beyondsoft.datatask.IDataTaskListener;

public abstract class BaseFragment extends Fragment implements IDataTaskListener
{
	protected BaseDataTask dataTask;
	
	public BaseFragment(){
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		dataTask.setListener(null);
		dataTask=null;
	}

	@Override
	public Object doDataTask(int type, Object... p) throws Exception {
		return null;
	}

	@Override
	public void doProcessData(int type, Object... values) {
	}

	@Override
	public void doProcessError(int type, String errorMsg) {
	}

	/**
	 * @return the mDataTask
	 */
	public BaseDataTask getmDataTask() {
		return dataTask;
	}

	/**
	 * @param mDataTask the mDataTask to set
	 */
	public void setmDataTask(BaseDataTask mDataTask) {
		this.dataTask = mDataTask;
		dataTask.setListener(this);
	}

}
