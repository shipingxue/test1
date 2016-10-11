package com.example.music04.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class HttpUtils {
	
	/**
	 * 向url地址发送http请求
	 * @param path
	 * @return
	 * @throws MalformedURLException 
	 */
	public static InputStream get(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		InputStream is = conn.getInputStream();
		return is;
	}
	
	public static  String isToString(InputStream url) {
		return null;
	}

}
