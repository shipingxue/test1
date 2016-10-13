package com.example.music04.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.music04.R;
import com.example.music04.adapter.FragmentMusicAdapter;
import com.example.music04.entity.Music;
import com.example.music04.model.MusicModel;

public class HotMusicListFragment extends Fragment {
	ListView lvHot;
	TextView tvEmpty;
	List<Music> musics;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_hot, null);
		MusicModel model = new MusicModel();
		lvHot = (ListView) view.findViewById(R.id.lv_hot);
		tvEmpty = (TextView) view.findViewById(R.id.tv_hot);
		model.getHotMusicList(0, 20, new MusicListCallBack(){

			@Override
			public void onMusicListLoaded(List<Music> musics) {
				HotMusicListFragment.this.musics = musics;
				FragmentMusicAdapter adapter = new FragmentMusicAdapter(musics, getActivity(), lvHot);
				lvHot.setAdapter(adapter);
				lvHot.setEmptyView(tvEmpty);
			}
			
		});
		
		
		return view;
	}


}
