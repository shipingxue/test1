package com.example.music04.model;

import java.io.InputStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Log;

import com.example.music04.entity.Music;
import com.example.music04.entity.SongInfo;
import com.example.music04.entity.SongUrl;
import com.example.music04.fragment.MusicListCallBack;
import com.example.music04.fragment.SongInfoCallback;
import com.example.music04.util.HttpUtils;
import com.example.music04.util.JsonParser;
import com.example.music04.util.UrlFactory;
import com.example.music04.util.XmlParser;

/**
 * 封装音乐相关业务
 * 
 * @author root
 * 
 */
public class MusicModel {

	public void getNewMusicList(final int offset, final int size,
			final MusicListCallBack callback) {
		AsyncTask<String, String, List<Music>> task = new AsyncTask<String, String, List<Music>>() {

			@Override
			protected List<Music> doInBackground(String... params) {

				try {
					String url = UrlFactory.getNewMusicListUrl(offset, size);
					InputStream is = HttpUtils.get(url);
					List<Music> musics;
					musics = XmlParser.parseMusicList(is);

					return musics;

				} catch (XmlPullParserException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(List<Music> musics) {
				Log.i("ping", musics.toString());
				callback.onMusicListLoaded(musics);
			}

		};
		task.execute();
	}

	public void getHotMusicList(final int offset, final int size,
			final MusicListCallBack callback) {
		AsyncTask<String, String, List<Music>> task = new AsyncTask<String, String, List<Music>>() {

			@Override
			protected List<Music> doInBackground(String... params) {

				try {
					String url = UrlFactory.getHotMusicListUrl(offset, size);
					InputStream is = HttpUtils.get(url);
					List<Music> musics;
					musics = XmlParser.parseMusicList(is);

					return musics;

				} catch (XmlPullParserException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(List<Music> musics) {
				Log.i("ping", musics.toString());
				callback.onMusicListLoaded(musics);
			}

		};
		task.execute();
	}

	public void loadSongInfoBySongId(final String songId,
			final SongInfoCallback callback) {
		AsyncTask<String, String, Music> task = new AsyncTask<String, String, Music>() {

			@Override
			protected Music doInBackground(String... params) {
				Music music = new Music();
				try {
					String path = UrlFactory.getSongInfoUrl(songId);
					InputStream is = HttpUtils.get(path);
					String json = HttpUtils.isToString(is);
					JSONObject obj = new JSONObject(json);
					
					JSONArray urlAry = obj.getJSONObject("songurl").getJSONArray("url");
					List<SongUrl> urls = JsonParser.parserUrls(urlAry);
					
					JSONObject jonsInfo = obj.getJSONObject("songinfo");
					SongInfo songInfo = JsonParser.parserInfo(jonsInfo);
					music.setSongInfo(songInfo);
					music.setUrls(urls);

				} catch (Exception e) {
					e.printStackTrace();
				}

				return music;
			}

			@Override
			protected void onPostExecute(Music music) {
				callback.onSongInfoLoaded(music.getUrls(), music.getSongInfo());
			}

		};
		task.execute();
	}

}
