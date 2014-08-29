package com.beyondsoft.util;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ParametersUtil {
	
	
	private String request_base_url = "";
	/** 请求url **/
	private String reqURL;
	
	/** 参数TreeMap **/
	private TreeMap<String, String> params = new TreeMap<String, String>(
			new Comparator<Object>() {
				public int compare(Object o1, Object o2) {
					if (o1 == null || o2 == null)
						return 0;
					else
						return String.valueOf(o1).compareTo(String.valueOf(o2));
				}
			});
	
	/**
	 * 添加参数方法
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	private void addParam(String key, String value) {
		if (null == key || "".equals(key) || null == value) {
			// 不作处理
		} else {
			params.put(key.trim(), value.trim());
		}
	}

	/**
	 * 添加string类型的参数
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public void addStringParam(String key, String value) {
		addParam(key, value);
	}

	/**
	 * 添加Int类型的参数
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public void addStringParam(String key, Number value) {
		addParam(key, value.toString());
	}

	/**
	 * 得到请求url
	 * 
	 * @param params
	 * @return
	 */
	public String getReqURL(String url) {

		String requestUrl=request_base_url+url;
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(requestUrl);

		Iterator iter = params.entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			urlBuilder.append("&" + key + "=" + val);
		}

		if (!urlBuilder.toString().equals("")) {
			reqURL = urlBuilder.toString().replaceFirst("&", "?");

		}
		if(Logs.IS_DEBUG){
			Logs.e("debug","请求的URL地址:"+reqURL);
		}
		return reqURL;
	}
	
	/**
	 * 获得postUrl
	 * @param url
	 * @return
	 */
	public String getPostURL(String url){
		return request_base_url+url;
	}
	
	/**
	 * 获取post参数列表
	 * @return
	 */
	public TreeMap<String,String> getPostParamsList(){
		return params;
	}
}