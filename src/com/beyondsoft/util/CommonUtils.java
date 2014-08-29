package com.beyondsoft.util;

import java.util.UUID;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CommonUtils
{
	/**
	 * 判断网络是否畅通
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} 
		else 
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) 
			{
				for (int i = 0; i < info.length; i++) 
				{
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断是否有SD卡
	 * @return
	 */
	public static boolean avaiableSDCard(){  
		
        String status = Environment.getExternalStorageState();  
          
        if(status.equals(Environment.MEDIA_MOUNTED)){  
            return true;  
        }  
        else {  
            return false;  
        }  
    }
	
	/**
	 * 获取设备的ID号
	 * @return
	 */
	public static String getDeviceId(Context context)
	{
		TelephonyManager telephonyManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
	/**
	 * 获取UUID码
	 * @return
	 */
	public static String getRandomUUID()
	{
	    String uuidRaw = UUID.randomUUID().toString();
	    return uuidRaw.replaceAll("-", "");
	}
	
//	/**
//	 * 添加公共信息
//	 * @param key
//	 * @param value
//	 */
//	public static void addConfigInfo(Context context,String key,String value)
//	{ 
//		if(!StringUtils.isNull(key) && !StringUtils.isNull(value))
//		{
//			mShareConfig = context.getSharedPreferences(Constants.YHKJ, Context.MODE_PRIVATE);
//			Editor conEdit = mShareConfig.edit();
//			conEdit.putString(key, value);
//			conEdit.commit();
//		}
//	}
//	
//	/**
//	 * 添加公共信息
//	 * @param key
//	 * @param value
//	 */
//	public static void addConfigInfo(Context context,String key,boolean value)
//	{ 
//		if(!StringUtils.isNull(key))
//		{
//			mShareConfig = context.getSharedPreferences(Constants.YHKJ, Context.MODE_PRIVATE);
//			Editor conEdit = mShareConfig.edit();
//			conEdit.putBoolean(key, value);
//			conEdit.commit();
//		}
//	}
//	
//	/**
//	 * 删除公共信息
//	 * @param key
//	 * @param value
//	 */
//	public static void deleteConfigInfo(Context context,String key)
//	{ 
//		if(!StringUtils.isNull(key))
//		{
//			mShareConfig = context.getSharedPreferences(Constants.YHKJ, Context.MODE_PRIVATE);
//			Editor conEdit = mShareConfig.edit();
//			conEdit.remove(key);
//			conEdit.commit();
//		}
//	}
//	
//	/**
//	 * 根据key得到信息
//	 * @param key
//	 * @param value
//	 */
//	public static String getStringByKey(Context context,String key)
//	{ 
//		String value = null;
//		if(!StringUtils.isNull(key))
//		{
//			mShareConfig = context.getSharedPreferences(Constants.YHKJ, Context.MODE_PRIVATE);
//			if(null != mShareConfig){
//				value = mShareConfig.getString(key, "");
//			}
//		}
//		return value;
//	}
//	
//	/**
//	 * 根据key得到信息
//	 * @param key
//	 * @param value
//	 */
//	public static boolean getBooleanByKey(Context context,String key)
//	{ 
//		boolean value = false;
//		if(!StringUtils.isNull(key))
//		{
//			mShareConfig = context.getSharedPreferences(Constants.YHKJ, Context.MODE_PRIVATE);
//			if(null != mShareConfig){
//				value = ((Boolean)mShareConfig.getBoolean(key, false)).booleanValue();
//			}
//		}
//		return value;
//	}
//	
//	/**
//	 * 根据key得到信息
//	 * @param key
//	 * @param value
//	 */
//	public static boolean getBooleanByKeyRem(Context context,String key)
//	{ 
//		boolean value = false;
//		if(!StringUtils.isNull(key))
//		{
//			mShareConfig = context.getSharedPreferences(Constants.YHKJ, Context.MODE_PRIVATE);
//			if(null != mShareConfig){
//				value = ((Boolean)mShareConfig.getBoolean(key, true)).booleanValue();
//			}
//		}
//		return value;
//	}
	
	/**
	 * 弹出消息提示框
	 * @param context
	 * @param msg
	 * @param gravity
	 */
	public static void ToastMsg(Context context, String msg, int gravity) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(gravity, 0, 0);
		toast.show();
	}
	
	/**
	 * 弹出消息提示框，位置：CENTER_VERTICAL
	 * @param context
	 * @param msg
	 */
	public static void ToastMsg(Context context,String msg) {
		if (null != msg && !"".equals(msg)) {
			Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	/**
	 * 弹出消息提示框，时间稍长
	 * @param context
	 * @param msg
	 */
	public static void ToastMsgForLong(Context context,String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		toast.show();
	}
	
	/**
	 * 弹出消息提示框
	 * @param context
	 * @param resID
	 */
	public static void ToastMsg(Context context,int resID) {
		Toast toast = Toast.makeText(context, resID, Toast.LENGTH_SHORT);
		toast.show();
	}

	/**
	 * 版本比较
	 * @param curVersion The current package version on client device
	 * @param latestVersion The latest version available
	 * @return
	 */
	private boolean compareVersion(String curVersion, String latestVersion) {
		if (curVersion == null || curVersion.trim().length() == 0) {
			return true;
		}
		if (latestVersion == null || latestVersion.trim().length() == 0) {
			return false;
		}
		String[] arrCurVersion = curVersion.split("\\.");
		String[] arrLatestVersion = latestVersion.split("\\.");
		int curLength = arrCurVersion.length;
		int latestLength = arrLatestVersion.length;
		for (int index = 0; index < curLength && index < latestLength; index++) {
			int curVerNum = 0;
			int latestVerNum = 0;
			try {
				curVerNum = Integer.valueOf(arrCurVersion[index]);
				latestVerNum = Integer.valueOf(arrLatestVersion[index]);
			} catch (Exception e) {
			}
			if (curVerNum > latestVerNum) {
				return false;
			}
			if (curVerNum < latestVerNum) {
				return true;
			}
			// 如果相等，继续比较下一位
		}
		// 比较到最后一位，仍然相等，则比较长度，较长者为大
		if (curLength >= latestLength) {
			return false;
		} else {
			return true;
		}
	}
	
}