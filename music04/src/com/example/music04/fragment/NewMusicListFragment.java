package com.example.music04.fragment;

import java.util.List;

import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.music04.R;
import com.example.music04.adapter.FragmentMusicAdapter;
import com.example.music04.app.MusicApplication;
import com.example.music04.entity.Music;
import com.example.music04.entity.SongInfo;
import com.example.music04.entity.SongUrl;
import com.example.music04.model.MusicModel;
import com.example.music04.service.PlayMusicService.MusicBinder;

public class NewMusicListFragment extends Fragment {
	ListView lvNew;
	TextView tvEmpty;
	List<Music> musics;
	FragmentMusicAdapter adapter;
	MusicModel model;

	private MusicBinder binder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_new, null);

		model = new MusicModel();
		lvNew = (ListView) view.findViewById(R.id.lv_new);
		tvEmpty = (TextView) view.findViewById(R.id.tv_new);
		model.getNewMusicList(0, 20, new MusicListCallBack() {

			@Override
			public void onMusicListLoaded(List<Music> musics) {
				NewMusicListFragment.this.musics = musics;
				adapter = new FragmentMusicAdapter(musics, getActivity(), lvNew);
				lvNew.setAdapter(adapter);
				lvNew.setEmptyView(tvEmpty);
			}

		});

		setListeners();

		return view;
	}

	private void setListeners() {
		lvNew.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String songId = musics.get(position).getSong_id();
				MusicApplication app = MusicApplication.getApp();
				app.setMusics(musics);
				app.setPosition(position);

				model.loadSongInfoBySongId(songId, new SongInfoCallback() {

					@Override
					public void onSongInfoLoaded(List<SongUrl> urls,
							SongInfo info) {
						Log.i("ping", "urls:\n" + urls + "info:\n" + info);
						binder.palyMusic(urls.get(0).getFile_link());
					}
				});
			}

		});

	}

	@Override
	public void onDestroy() {
		adapter.stopThread();
	}

	public void setBinder(IBinder service) {
		this.binder = (MusicBinder) service;
	}

}
