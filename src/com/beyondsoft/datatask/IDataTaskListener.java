package com.beyondsoft.datatask;

import android.content.Intent;

public interface IDataTaskListener {

	/**
	 * 进行数据连接，此方法中调用service获取数据
	 * 
	 * @param type
	 *            连接类型 用于区分不同的连接类型
	 * @param p
	 *            连接的数据
	 * @return Object
	 * @throws Exception
	 */
	public Object doDataTask(int type, Object... p) throws Exception;

	/**
	 * 处理 doDataConnection 得到的数据
	 * 
	 * @param type
	 *            连接类型
	 * @param values
	 *            数据参数,values[1]为连接状态，失败或者成功，values[2]为doDataConnection返回的数据
	 * @return
	 */
	public void doProcessData(int type, Object... values);

	/**
	 * 处理连接异常
	 * 
	 * @param type
	 *            连接类型
	 * @return
	 */
	public void doProcessError(int type, String errorMsg);
	
}