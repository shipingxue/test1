package com.example.music04.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.example.music04.entity.Music;

public class XmlParser {

	public static List<Music> parseMusicList(InputStream is)
			throws XmlPullParserException, IOException {
		List<Music> musics = new ArrayList<Music>();

		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int event = parser.getEventType();

		Music music = null;
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_TAG:
				String tagName = parser.getName();
				if ("song".equals(parser.getName())) {
					music = new Music();

				} else if ("artist_id".equals(tagName)) {
					event = parser.next();
					music.setArtist_id(parser.getText());
				} else if (tagName.equals("language")) {
					music.setLanguage(parser.nextText());
				} else if (tagName.equals("pic_big")) {
					music.setPic_big(parser.nextText());
				} else if (tagName.equals("pic_small")) {
					music.setPic_small(parser.nextText());
				} else if (tagName.equals("lrclink")) {
					music.setLrclink(parser.nextText());
				} else if (tagName.equals("all_artist_id")) {
					music.setAll_artist_id(parser.nextText());
				} else if (tagName.equals("file_duration")) {
					music.setFile_duration(parser.nextText());
				} else if (tagName.equals("song_id")) {
					music.setSong_id(parser.nextText());
				} else if (tagName.equals("title")) {
					music.setTitle(parser.nextText());
				} else if (tagName.equals("author")) {
					music.setAuthor(parser.nextText());
				} else if (tagName.equals("album_id")) {
					music.setAlbum_id(parser.nextText());
				} else if (tagName.equals("album_title")) {
					music.setAlbum_title(parser.nextText());
				} else if (tagName.equals("artist_name")) {
					music.setArtist_name(parser.nextText());
				}
				break;

			case XmlPullParser.END_TAG:
				if ("song".equals(parser.getName())) {
					musics.add(music);
				}
				break;

			}
			event = parser.next();

		}

		return musics;
	}

}
