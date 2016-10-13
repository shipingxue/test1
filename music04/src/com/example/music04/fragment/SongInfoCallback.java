package com.example.music04.fragment;

import java.util.List;

import com.example.music04.entity.SongInfo;
import com.example.music04.entity.SongUrl;

public interface SongInfoCallback {
	void onSongInfoLoaded(List<SongUrl> urls, SongInfo info);
}
