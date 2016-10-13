package com.example.music04.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.music04.entity.SongInfo;
import com.example.music04.entity.SongUrl;

public class JsonParser {

	public static List<SongUrl> parserUrls(JSONArray urlAry)
			throws JSONException {
		List<SongUrl> urls = new ArrayList<SongUrl>();

		for (int i = 0; i < urlAry.length(); i++) {

			JSONObject o = urlAry.getJSONObject(i);
			SongUrl url = new SongUrl(o.getInt("song_file_id"),
					o.getInt("file_size"), o.getInt("file_duration"),
					o.getInt("file_bitrate"), o.getString("show_link"),
					o.getString("file_extension"), o.getString("file_link"));
			urls.add(url);
		}

		return urls;
	}

	public static SongInfo parserInfo(JSONObject jonsInfo) throws JSONException {
		SongInfo songInfo = new SongInfo(
				jonsInfo.getString("pic_huge"), 
				jonsInfo.getString("album_1000_1000"), 
				jonsInfo.getString("pic_singer"), 
				jonsInfo.getString("album_500_500"), 
				jonsInfo.getString("compose"), 
				jonsInfo.getString("artist_500_500"), 
				jonsInfo.getString("file_duration"), 
				jonsInfo.getString("album_title"), 
				jonsInfo.getString("title"), 
				jonsInfo.getString("pic_radio"), 
				jonsInfo.getString("language"), 
				jonsInfo.getString("lrclink"), 
				jonsInfo.getString("pic_big"), 
				jonsInfo.getString("pic_premium"), 
				jonsInfo.getString("artist_480_800"), 
				jonsInfo.getString("artist_id"), 
				jonsInfo.getString("album_id"), 
				jonsInfo.getString("artist_1000_1000"), 
				jonsInfo.getString("all_artist_id"), 
				jonsInfo.getString("artist_640_1136"), 
				jonsInfo.getString("publishtime"), 
				jonsInfo.getString("share_url"), 
				jonsInfo.getString("author"), 
				jonsInfo.getString("pic_small"), 
				jonsInfo.getString("song_id"));
		
		return songInfo;
	}

}
