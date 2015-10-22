package com.like.gcm_client_test.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

/**
 * http operate util, include get, post, delete and put, use HttpClient
 * 
 * @version 1.0
 * @author Jolina Zhou
 */
public class HttpUtil {
	public static String token = "";

	/**
	 * get message from server through HttpCLient
	 * 
	 * @param uri
	 * @return String, return response message else return "-1"
	 */
	public static String get(String uri) {
		uri=uri.replace(" ", "%20");
		String result = "";
		HttpClient httpclient = null;
		HttpGet request = null;
		HttpResponse response = null;
		try {
			Log.i("uri", uri);
			httpclient = new DefaultHttpClient();
			request = new HttpGet(uri);
			response = httpclient.execute(request);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			result = "-1";
		} finally {
			httpclient.getConnectionManager().shutdown();
			request = null;
			response = null;
			httpclient = null;
		}
		return result;
	}
	
	public static String getMsg(String uri) {
		  String result = "";
		  HttpClient httpclient = null;
		  HttpGet request = null;
		  HttpResponse response = null;
		  try {
		   httpclient = new DefaultHttpClient();
		   
		   request = new HttpGet(uri);
		   HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 1*1000);
		   HttpConnectionParams.setSoTimeout(httpclient.getParams(), 1*1000);
		   response = httpclient.execute(request);
		   
		   result = EntityUtils.toString(response.getEntity(), "utf-8");
		  } catch (Exception e) {
		   result = "-1";
		  } finally {
			  httpclient.getConnectionManager().shutdown();
			  request = null;
			  response = null;
			  httpclient = null;
		  }
		  return result;
		 }

	/**
	 * post message to server
	 * 
	 * @param uri
	 * @return String, return response message else return "-1"
	 */
	public static String post(String uri) {
		uri=uri.replace(" ", "%20");
		String result = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(uri);
			HttpResponse response = client.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			return "-1";
		}
		return result;
	}

	/**
	 * post data to server
	 * 
	 * @param data
	 *            , to be send to the server
	 * @param uri
	 * @return String, return response message, if no response or error happen
	 *         then return "";
	 */
	public static String post(JSONObject data, String uri) {
		uri=uri.replace(" ", "%20");
		String result = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(uri);
			StringEntity entity = new StringEntity(data.toString(), HTTP.UTF_8);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpResponse response = client.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			return "-1";
		}
		return result;
	}

	/**
	 * put message to server
	 * 
	 * @param uri
	 * @return String, return response message or "" if no response
	 */
	public static String put(String uri) {
		uri=uri.replace(" ", "%20");
		String result = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPut httpPut = new HttpPut(uri);
			httpPut.addHeader("Accept", "text/plain");
			HttpResponse response = client.execute(httpPut);
			result = EntityUtils.toString(response.getEntity(), "utf-8");

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * put data to server
	 * 
	 * @param data
	 *            , to be send to the server
	 * @param uri
	 * @return String, return response message, if no response or error happen
	 *         then return "";
	 */
	public static String put(JSONObject data, String uri) {
		uri=uri.replace(" ", "%20");
		String result = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPut httpPut = new HttpPut(uri);
			StringEntity entity = new StringEntity(data.toString(), HTTP.UTF_8);
			entity.setContentType("application/json");
			httpPut.setEntity(entity);
			HttpResponse response = client.execute(httpPut);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			return null;
		}
		return result;
	}

	/**
	 * delete from server
	 * 
	 * @param uri
	 * @return String, return response message, if no response or error happen
	 *         then return "";
	 */
	public static String delete(String uri) {
		uri=uri.replace(" ", "%20");
		String result = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpDelete httpDelete = new HttpDelete(uri);
			HttpResponse response = client.execute(httpDelete);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			return null;
		}
		return result;
	}

	/**
	 * get base uri of rest
	 * 
	 * @return String, uri
	 */
	public static String getBaseUri() {
		return "http://192.168.1.80:8081/GCMPushServerTest/rest/";
//		return "http://58.216.152.190:18080/BrainsenseV1.1/rest/";
//		return "http://192.168.1.80:8081/Brainsense/rest/";
//		return "http://192.168.1.71:8080/BrainsenseV1.1/rest/";

	}

	/**
	 * get resource uri
	 * 
	 * @return String uri
	 */
	public static String ProjectUri() {
		return "http://58.216.152.190:18080/BrainsenseV1.1/rest/";
	}
	
	public static String ImageUri() {
		return "http://58.216.152.190:18080";
	}
}
