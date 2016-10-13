package com.example.music04.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

import com.example.music04.entity.Music;

public class MusicApplication extends Application {
	private List<Music> musics;
	private int position;
	private static MusicApplication app;
	
	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
	}

	public static MusicApplication getApp() {
		return app;
	}

	public void setMusics(List<Music> musics) {
		this.musics = musics;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Music getCurrentMusic() {
		return this.musics.get(position);
	}

}
