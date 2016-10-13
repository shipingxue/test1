package com.example.music04.adapter;

import java.io.File;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.music04.R;
import com.example.music04.entity.Music;
import com.example.music04.util.BitmapUtils;
import com.example.music04.util.HttpUtils;
import com.example.music04.util.ImageLoader;

public class FragmentMusicAdapter extends BaseAdapter {
	List<Music> musics;
	Context context;
	LayoutInflater layoutInflater;

	ImageLoader imageLoader;
	private ListView listView;

	public FragmentMusicAdapter(List<Music> musics, Context context,
			ListView listView) {
		super();
		this.musics = musics;
		this.context = context;
		this.listView = listView;
		layoutInflater = LayoutInflater.from(context);
		this.imageLoader = new ImageLoader(context, listView);

	}

	public void stopThread() {
		imageLoader.stopThread();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Music music = musics.get(position);

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.fragment_list_music,
					null);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tvAuthor = (TextView) convertView
					.findViewById(R.id.tv_author);
			holder.ivAlbum = (ImageView) convertView.findViewById(R.id.iv_pic);

			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();

		holder.tvTitle.setText(music.getTitle());
		holder.tvAuthor.setText(music.getAuthor());

		// Õº∆¨¥¶¿Ì
		String path = music.getPic_small();
		imageLoader.display(holder.ivAlbum, path);

		return convertView;
	}

	private class ViewHolder {
		ImageView ivAlbum;
		TextView tvTitle;
		TextView tvAuthor;
	}

	@Override
	public int getCount() {
		return musics.size();
	}

	@Override
	public Music getItem(int position) {
		return musics.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
