package com.example.music04.fragment;

import java.util.List;

import com.example.music04.entity.Music;

public interface MusicListCallBack {
	/**
	 * �ص��������������б������Ϻ����
	 * �ѵõ��������б��ݸ�������
	 * @param musics
	 */
	void onMusicListLoaded(List<Music> musics);
}
