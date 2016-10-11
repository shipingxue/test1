package com.example.music04.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;

import com.example.music04.entity.Music;
import com.example.music04.fragment.NewMusicListFragment.MusicListCallBack;
import com.example.music04.util.HttpUtils;
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
			protected void onPostExecute(List<Music> result) {
				super.onPostExecute(result);
			}

		};
	}

}
