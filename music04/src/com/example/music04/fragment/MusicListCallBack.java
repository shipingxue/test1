package com.example.music04.fragment;

import java.util.List;

import com.example.music04.entity.Music;

public interface MusicListCallBack {
	/**
	 * 回调方法，当音乐列表加载完毕后调用
	 * 把得到的音乐列表传递给调用者
	 * @param musics
	 */
	void onMusicListLoaded(List<Music> musics);
}
