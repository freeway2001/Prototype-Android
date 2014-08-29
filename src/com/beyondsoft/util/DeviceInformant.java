package com.beyondsoft.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * 设备信息，主要用于服务器端设备信息的统计
 * 
 * @author Steven.Luo
 * 
 */
public class DeviceInformant {
	private static final String TAG = "DeviceInfo";

    private Map<String, String> paramMap = new HashMap<String, String>();

	private String clientName;
	private String deviceMode;
	private String osSystem;
	private String osSystemVer;
	private String romVer;
	private int appVerCode;
	private String countryCode;
	private String deviceID;
	private int screenHeight;
	private int screenWidth;
	private int densityDpi;
	private String version;

	public DeviceInformant(Context context) {
	    try {
	        clientName = "Alibaba.Android";
	        osSystem = "Android";
            osSystemVer = Build.VERSION.RELEASE;
            deviceMode = Build.MODEL;
            romVer = Build.FINGERPRINT;
            
    		version = AndroidUtil.getVerName(context);
    		deviceID = AndroidUtil.getDeviceId(context);
    		appVerCode = AndroidUtil.getVerCode(context);
    		screenWidth = context.getResources().getDisplayMetrics().widthPixels;
    		screenHeight = context.getResources().getDisplayMetrics().heightPixels;
    		densityDpi = context.getResources().getDisplayMetrics().densityDpi;
    
    		TelephonyManager tm = (TelephonyManager) context
    				.getSystemService(Context.TELEPHONY_SERVICE);
    		countryCode = tm == null ? "" : (tm.getSimCountryIso() == null ? ""
    				: tm.getSimCountryIso().toUpperCase());
	    } catch (Exception e) {
	        e.printStackTrace();
	        Logs.e(TAG, "Device Info Error");
	    }
	}

	/**
	 * 获取Post请求的键值对
	 * 
	 * @return
	 */
	public Map<String, String> getParamMap() {

		paramMap.put("clientName", clientName);
		paramMap.put("version", version);
		paramMap.put("countryCode", countryCode);
		paramMap.put("deviceID", deviceID);
		paramMap.put("appVerCode", String.valueOf(appVerCode));
		paramMap.put("osSystem", osSystem);
		paramMap.put("osSystemVer", osSystemVer);
		paramMap.put("deviceMode", deviceMode);
		paramMap.put("screenWidth", String.valueOf(screenWidth));
		paramMap.put("screenHeight", String.valueOf(screenHeight));
		paramMap.put("romVer", romVer);
		paramMap.put("densityDpi", String.valueOf(densityDpi));
		//paramMap.put("loginId", getLoginId());

		return paramMap;
	}
}
