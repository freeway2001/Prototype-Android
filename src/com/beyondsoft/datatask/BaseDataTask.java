package com.beyondsoft.datatask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class BaseDataTask {
	
	ProgressDialog progressDialog;
	
	/**是否需要显示Dialog**/
	private boolean mIsShowDialog=false;
	
	/**Dialog显示的内容**/
	private String mShowText;

	/** 状态，连接失败 **/
	private final int STATE_ERROR = 1;
	
	/** 状态，连接正常 **/
	private final int STATE_NORMAL = 2;
	
	/** 是否在连接 **/
	private boolean mIsConnection = false;
	
	/** 连接类型，用于区分开多个数据请求 **/
	private int mConnectionType;
	
	/** 远程数据连接Task **/
	private MyDataTask mTask;
	
	/** 远程数据连接监听器 **/
	private IDataTaskListener mListener;
	
	private Context mContext;
	
	public BaseDataTask(Context c)
	{
		mContext=c;
	}
	
	
	/**
	 * 开启一个连接
	 * @param connectionType  连接类型
	 * @param isShowDialog    是否需要显示连接中Dialog
	 * @param resText 连接时显示的内容  resText=null时,使用默认的提示信息
	 * @param params
	 */
	public void start(int connectionType,boolean isShowDialog,String resText,Object... params){
		if(null==mTask&&!isStarted())
		{
			if(resText==null)
				mShowText="获取数据中...";
			else
				mShowText=resText;
			mIsConnection=true;
			mIsShowDialog=isShowDialog;
			mConnectionType=connectionType;
			mTask=new MyDataTask();
			mTask.execute(params);
		}
	}
	
	/**
	 * 是否正在连接
	 *
	 * @return
	 */
	public boolean isStarted(){
		if(!mIsConnection && mTask==null){
			return false;
		}
		return true;
	}
	
	/**
	 * 断开连接
	 *
	 * @return 
	 */
	public void cancel(){
		if(mTask!=null){
			mTask.cancel(true);
			mTask = null;
		}
		if (mIsShowDialog) {
			progressDialog.dismiss();
			progressDialog=null;
		}
		mIsConnection = false;
		mIsShowDialog=false;
		
	}
	
	/**
	 * 设置connection监听器
	 *
	 * @param listener
	 * @return 
	 */
	public void setListener(IDataTaskListener listener) {
		this.mListener = listener;
	}
	
	/**
	 * 数据连接线程
	 *
	 */
	class MyDataTask extends AsyncTask
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(mIsShowDialog)
			{
				progressDialog=new ProgressDialog(mContext);
				progressDialog.setMessage(mShowText);
				progressDialog.setCancelable(false);
				progressDialog.show();
			}
		}

		@Override
		protected Object doInBackground(Object... params){
			
			try
			{
				if(!isCancelled()){
					Object data = mListener.doDataTask(mConnectionType, params);
					if(params != null)
					{
						if(params.length<1)
							publishProgress(STATE_NORMAL, data);
						if(params.length==1)
							publishProgress(STATE_NORMAL, data,params[0].toString());
						if(params.length==2)
							publishProgress(STATE_NORMAL, data,params[0].toString(),params[1].toString());
					}
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
				String msg=e.getMessage();
			    Throwable throwable=e.fillInStackTrace();
			    if(throwable instanceof java.net.ConnectException){
			    	msg="网络无法连接，请检查网络配置";
			    }
			    else if(throwable instanceof java.net.SocketException){
			    	msg="无网络或服务器正忙";
			    }
			    else if (throwable instanceof java.net.SocketTimeoutException) {
			    	msg ="连接超时";
				}else if (throwable instanceof android.util.AndroidRuntimeException) {
					msg ="系统异常";
				}
				publishProgress(STATE_ERROR, msg);
			}

			return null;
		}
		
		@Override
		protected void onProgressUpdate(Object... values) {
			try {
				
				if(mListener!=null){
					if(!isCancelled()){
						int state = Integer.parseInt(values[0].toString());
						switch (state) {
							case STATE_NORMAL:
								if(mListener!=null)
									mListener.doProcessData(mConnectionType, values);
								break;
							case STATE_ERROR:
								//数据获取失败
								Object msgObject = values[1];
								String errorMsg = null;
								if(msgObject!=null){
									errorMsg = msgObject.toString();
								}
								if(mListener!=null)
									mListener.doProcessError(mConnectionType, errorMsg);
								break;
							default:
								break;
						}
					}
				}
				mIsConnection = false;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				if(mListener!=null)
					mListener.doProcessError(mConnectionType,e.getMessage());
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			if(mTask!=null){
				mTask.cancel(true);
				try {
					mTask.finalize();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
			
			mIsConnection = false;
			mTask = null;
			if(mIsShowDialog)
			{
				try {
					progressDialog.dismiss();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				progressDialog=null;
				mIsShowDialog=false;
			}
		}
	}
}
