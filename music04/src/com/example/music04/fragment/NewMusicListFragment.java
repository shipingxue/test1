package com.example.music04.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.music04.R;
import com.example.music04.entity.Music;
import com.example.music04.model.MusicModel;

public class NewMusicListFragment extends Fragment {
	ListView lvNew;
	List<Music> musics;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_new, null);
		
		MusicModel model = new MusicModel();
		lvNew = (ListView) view.findViewById(R.id.lv_new);
		model.getNewMusicList(0, 20, new MusicListCallBack(){

			@Override
			public void onMusicListLoaded(List<Music> musics) {
				ListAdapter adapter;
				//lvNew.setAdapter(adapter);
			}
			
		});
		
		
		return view;
		
	}
	
	public interface MusicListCallBack {
		/**
		 * �ص��������������б������Ϻ����
		 * �ѵõ��������б��ݸ�������
		 * @param musics
		 */
		void onMusicListLoaded(List<Music> musics);
	}

}
