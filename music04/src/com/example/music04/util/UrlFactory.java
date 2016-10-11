package com.example.music04.util;

public class UrlFactory {

	public static String getNewMusicListUrl(int offset, int size) {
		String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.billboard.billList&format=xml&type=2&offset="+offset+"&size="+size;
		return url;
	}

}
