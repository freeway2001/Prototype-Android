package com.beyondsoft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

public class WebUtil {

	private static final String DEFAULT_CHARSET = "utf-8";
	private static final String METHOD_POST = "POST";
	private static final String METHOD_GET = "GET";
	private static final int CONNECTION_TIMEOUT = 30 * 1000;
	private static final int READ_TIMEOUT = 30 * 1000;

	/**
	 * doPost请求方法
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params)
			throws IOException {
		String ctype = "application/x-www-form-urlencoded;charset="
				+ DEFAULT_CHARSET;
		String query = buildQuery(params, DEFAULT_CHARSET);
		byte[] content = {};
		if (query != null) {
			content = query.getBytes(DEFAULT_CHARSET);
		}
		return doPost(url, ctype, content);
	}

	/**
	 * doPost请求方法
	 * 
	 * @param url
	 * @param ctype
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, String ctype, byte[] content)
			throws IOException {

		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		if (Logs.IS_DEBUG)
			Logs.v(url);
		try {
			conn = getConnection(new URL(url), METHOD_POST, ctype);
			conn.setConnectTimeout(CONNECTION_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);
			out = conn.getOutputStream();
			out.write(content);
			out.flush();
			rsp = getResponseAsString(conn);
		} catch (IOException e) {
			throw e;
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}
	
	
	/**
	 * doPut方法提交
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPut(String url, Map<String, String> params) {
		String rsp = null;
		try {
			HttpPut request = new HttpPut(url);
			Set<String> keySet = params.keySet();
			ArrayList<NameValuePair> values=new ArrayList<NameValuePair>();
			for (String key : keySet) {
				NameValuePair valuePair=new BasicNameValuePair(key, params.get(key));
				values.add(valuePair);
			}
			UrlEncodedFormEntity entity;
			entity = new UrlEncodedFormEntity(values,"utf-8");
			request.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			client.getParams().setIntParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,
					15000);
			HttpResponse response = client.execute(request);
			int responseCode = response.getStatusLine().getStatusCode();
			if (Logs.IS_DEBUG) {
				Logs.e("返回码:" + responseCode);
			}
			if (responseCode == 200) {
				StringBuffer json_str = new StringBuffer();
				InputStream is = response.getEntity().getContent();
				String response_str;
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				while ((response_str = reader.readLine()) != null) {
					json_str.append(response_str);
				}
				reader.close();
				if (Logs.IS_DEBUG) {
					Logs.e("返回的内容:" + json_str.toString());
					Logs.e("*******************************");
				}

				return json_str.toString();
			} else {
				return null;
			}
		} catch (IOException e) {
		}
		return rsp;
	}
	
	/**
	 * doPost图片和素材
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String doPost_img(String url, Map<String, String> params) {
		try {
			Iterator iterator = params.entrySet().iterator();
			MultipartEntity multipartEntity = new MultipartEntity();
			while (iterator.hasNext()) {
				Map.Entry entry = (Entry) iterator.next();
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				if (Logs.IS_DEBUG) {
					Logs.i(key + ":" + value);
				}
				if ("img".equals(key)||"video".equals(key)||"avatar".equals(key)) {
					multipartEntity.addPart(key,new FileBody(new File(value)));
				} else {
					multipartEntity.addPart(key,new StringBody(value,Charset.forName("UTF-8")));
				}
			}
			HttpPost request = new HttpPost(url);
			request.setEntity(multipartEntity);
			HttpClient client=new DefaultHttpClient();
			client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,15000);
			client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,15000);
			HttpResponse response=client.execute(request);
			int responseCode = response.getStatusLine().getStatusCode();
			if (Logs.IS_DEBUG) {
				Logs.e("返回码:" + responseCode);
			}
			if (responseCode == 200) {
				StringBuffer json_str = new StringBuffer();
				InputStream is = response.getEntity().getContent();
				String response_str;
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				while ((response_str = reader.readLine()) != null) {
					json_str.append(response_str);
				}

				reader.close();
				if (Logs.IS_DEBUG) {
					Logs.e("返回的内容:" + json_str.toString());
					Logs.e("*******************************");
				}

				return json_str.toString();
			} else {
				return null;
			}
		} catch (IOException e) {
		}
		return null;
	}
	
	

	/**
	 * doGet请求方法
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params)
			throws IOException {

		HttpURLConnection conn = null;
		String rsp = null;

		try {
			String ctype = "application/x-www-form-urlencoded;charset="
					+ DEFAULT_CHARSET;
			String query = buildQuery(params, DEFAULT_CHARSET);

			conn = getConnection(buildGetUrl(url, query), METHOD_GET, ctype);
			conn.setConnectTimeout(CONNECTION_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);

			rsp = getResponseAsString(conn);
		} catch (IOException e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	/**
	 * doGet请求方法
	 * 
	 * @param strUrl
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String strUrl) throws Exception {

		HttpURLConnection conn = null;
		StringBuffer json_str = new StringBuffer();
			if (!StringUtil.isEmpty(strUrl)) {
				URL url = new URL(strUrl);
//				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
//						"10.50.0.100", 8080));
//				conn = (HttpURLConnection) url.openConnection(proxy);
				 conn=(HttpURLConnection) url.openConnection();
				conn.setUseCaches(false);
				conn.setRequestMethod(METHOD_GET);
				conn.setConnectTimeout(CONNECTION_TIMEOUT);
				conn.setReadTimeout(READ_TIMEOUT);
				int responseCode = conn.getResponseCode();
				if (Logs.IS_DEBUG)
					Logs.e("返回码是:" + responseCode);
				if (HttpURLConnection.HTTP_OK == responseCode) {
					String response_str;
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));
					while ((response_str = reader.readLine()) != null) {
						json_str.append(response_str);
					}
					reader.close();
				}
			}
		if (Logs.IS_DEBUG)
			Logs.e("返回的json是:" + json_str.toString());
		if (conn != null) {
			conn.disconnect();
		}
		return json_str.toString();
	}

	/**
	 * 得到Http连接
	 * 
	 * @param url
	 * @param method
	 * @param ctype
	 * @return
	 * @throws IOException
	 */
	private static HttpURLConnection getConnection(URL url, String method,
			String ctype) throws IOException {

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", "paipai-sdk-java");
		conn.setRequestProperty("Content-Type", ctype);

		return conn;
	}

	/**
	 * 生成以GET方式请求的URL参数查询形式
	 * @param strUrl
	 * @param query
	 * @return
	 * @throws IOException
	 */
	private static URL buildGetUrl(String strUrl, String query)
			throws IOException {
		URL url = new URL(strUrl);
		if (StringUtil.isEmpty(query)) {
			return url;
		}

		if (StringUtil.isEmpty(url.getQuery())) {
			if (strUrl.endsWith("?")) {
				strUrl = strUrl + query;
			} else {
				strUrl = strUrl + "?" + query;
			}
		} else {
			if (strUrl.endsWith("&")) {
				strUrl = strUrl + query;
			} else {
				strUrl = strUrl + "&" + query;
			}
		}
		if (Logs.IS_DEBUG)
			if (strUrl.length() > 3000) {
				Logs.v("  ------------------> request url1 : "
						+ strUrl.substring(0, 3000));
				Logs.v("  ------------------> request url2: "
						+ strUrl.substring(3000, strUrl.length()));
			} else {
				Logs.v("  ------------------> request url1 : " + strUrl);
			}
		return new URL(strUrl);
	}

	/**
	 * 
	 * 将查询键值对 生成查询字符串
	 * @param params
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String buildQuery(Map<String, String> params, String charset)
			throws IOException {
		if (params == null || params.isEmpty()) {
			return null;
		}

		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			if (StringUtil.areNotEmpty(name, value)) {
				if (hasParam) {
					query.append("&");
				} else {
					hasParam = true;
				}

				query.append(name).append("=")
						.append(URLEncoder.encode(value, charset));
			}
		}
		if (Logs.IS_DEBUG)
			Logs.v("  ------------------> params list : " + query.toString());
		return query.toString();
	}

	/**
	 * 从HttpURLConnection中获取内容并按指定字符集转化成字符串
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	protected static String getResponseAsString(HttpURLConnection conn)
			throws IOException {
		String charset = getResponseCharset(conn.getContentType());
		InputStream es = conn.getErrorStream();
		// 判断错误流是否为空，为空则表示正常，并且从连接中取出输入流并将输入流中内容按指定字符集转成字符串返回
		if (es == null) {
			if (Logs.IS_DEBUG)
				Logs.v("getStreamAsString(conn.getInputStream()");
			try {
				return getStreamAsString(conn.getInputStream(), charset);
			} catch (Exception e) {
				if (Logs.IS_DEBUG) {
					Logs.v("返回数据:"
							+ getStreamAsString(conn.getInputStream(), charset));
				}
				throw new IOException("抱歉，未能连接到网络！");
			}
		} else {
			// 错误流不为空，表示连接出错，试图读出错误流内容，并抛出异常
			String msg = getStreamAsString(es, charset);
			Logs.v("msg = " + msg);
			if (Logs.IS_DEBUG)
				Logs.v("getStreamAsString(es, charset)");
			if (StringUtil.isEmpty(msg)) {
				throw new IOException(conn.getResponseCode() + ":"
						+ conn.getResponseMessage());
			} else {
				throw new IOException(msg);
			}
		}
	}

	/**
	 * 获取输入流中的内容并按指定的字符集转化成字符串
	 * @param stream
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	private static String getStreamAsString(InputStream stream, String charset)
			throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream, charset));
			StringWriter writer = new StringWriter();

			char[] chars = new char[256];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}

			return writer.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * 
	 * 获得请求回应的字符集
	 * @param ctype
	 * @return
	 */
	private static String getResponseCharset(String ctype) {
		String charset = DEFAULT_CHARSET;

		if (!StringUtil.isEmpty(ctype)) {
			String[] params = ctype.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if (pair.length == 2) {
						if (!StringUtil.isEmpty(pair[1])) {
							charset = pair[1].trim();
						}
					}
					break;
				}
			}
		}

		return charset;
	}

	/**
	 * 按照默认的字符集解码
	 * @param value
	 * @return
	 */
	public static String decode(String value) {
		return decode(value, DEFAULT_CHARSET);
	}

	/**
	 * 按照默认的字符集编码
	 * @param value
	 * @return
	 */
	public static String encode(String value) {
		return encode(value, DEFAULT_CHARSET);
	}

	/**
	 * 按照给定的字符集进行解码
	 * @param value
	 * @param charset
	 * @return
	 */
	public static String decode(String value, String charset) {
		String result = null;
		if (!StringUtil.isEmpty(value)) {
			try {
				result = URLDecoder.decode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * 按照给定的字符集进行编码
	 * @param value
	 * @param charset
	 * @return
	 */
	public static String encode(String value, String charset) {
		String result = null;
		if (!StringUtil.isEmpty(value)) {
			try {
				result = URLEncoder.encode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * 从给定的URL地址中获取查询参数对并保存到Map中返回
	 * @param url
	 * @return
	 */
	private static Map<String, String> getParamsFromUrl(String url) {
		Map<String, String> map = null;
		if (url != null && url.indexOf('?') != -1) {
			map = splitUrlQuery(url.substring(url.indexOf('?') + 1));
		}
		if (map == null) {
			map = new HashMap<String, String>();
		}
		return map;
	}

	/**
	 * 拆分获取查询参数对，并保存到Map中返回
	 * @param query
	 * @return
	 */
	public static Map<String, String> splitUrlQuery(String query) {
		Map<String, String> result = new HashMap<String, String>();

		String[] pairs = query.split("&");
		if (pairs != null && pairs.length > 0) {
			for (String pair : pairs) {
				String[] param = pair.split("=", 2);
				if (param != null && param.length == 2) {
					result.put(param[0], param[1]);
				}
			}
		}

		return result;
	}
}