package com.example.music04.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {

	/**
	 * 向url地址发送http请求
	 * 
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

	public static String isToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuffer buf = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buf.append(line);
		}

		return buf.toString();
	}

}
