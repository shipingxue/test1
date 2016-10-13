package com.example.music04.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.music04.R;
import com.example.music04.app.MusicApplication;
import com.example.music04.entity.Music;
import com.example.music04.fragment.HotMusicListFragment;
import com.example.music04.fragment.NewMusicListFragment;
import com.example.music04.service.PlayMusicService;
import com.example.music04.service.PlayMusicService.MusicBinder;
import com.example.music04.util.BitmapCallback;
import com.example.music04.util.BitmapUtils;
import com.example.music04.util.GlobalConsts;

public class MainActivity extends FragmentActivity implements
		OnPageChangeListener, OnCheckedChangeListener {

	RadioGroup rgTab;
	RadioButton rbNew;
	RadioButton rbHot;
	ViewPager vpMusic;
	PagerAdapter pagerAdapter;
	private List<Fragment> fragments;
	private InnerServiceConnection conn;
	private MusicBroadcastReceiver receiver;

	private ImageView ivAlbum;
	private TextView tvTitle;
	private TextView tvAuthor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setViews();

		// 绑定 Service
		bindMusicService();

		fragments = new ArrayList<Fragment>();
		fragments.add(new NewMusicListFragment());
		fragments.add(new HotMusicListFragment());

		setAdapter();
		setListeners();

		receiver = new MusicBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(GlobalConsts.ACTION_MUSIC_STARTED);
		registerReceiver(receiver, filter);

	}

	private void bindMusicService() {
		Intent intent = new Intent(this, PlayMusicService.class);
		conn = new InnerServiceConnection();
		bindService(intent, conn, Service.BIND_AUTO_CREATE);
	}

	private class InnerServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MusicBinder binder = (MusicBinder) service;
			NewMusicListFragment f1 = (NewMusicListFragment) fragments.get(0);
			f1.setBinder(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

	}

	private class MusicBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(GlobalConsts.ACTION_MUSIC_STARTED)) {
				MusicApplication app = MusicApplication.getApp();
				Music music = app.getCurrentMusic();
				tvTitle.setText(music.getTitle());
				tvAuthor.setText(music.getAuthor());
				
				//加载图片
				String path = music.getPic_small();
				BitmapUtils.loadBitmap(path, new BitmapCallback() {
					
					@Override
					public void onBitmapLoaded(Bitmap bitmap) {
						if (bitmap != null) {
							ivAlbum.setImageBitmap(bitmap);
							RotateAnimation anim = new RotateAnimation(0, 360, ivAlbum.getWidth()/2, ivAlbum.getHeight()/2);
							anim.setDuration(10000);
							anim.setInterpolator(new LinearInterpolator()); //匀速运动
							anim.setRepeatCount(RotateAnimation.INFINITE);
							ivAlbum.startAnimation(anim);
						} else {
							ivAlbum.setImageResource(R.drawable.ic_launcher);
						}
						
					}
				});
			}
		}

	}

	@Override
	protected void onDestroy() {
		this.unbindService(conn);
		this.unregisterReceiver(receiver);
		super.onDestroy();
	}

	private void setAdapter() {
		pagerAdapter = new InnerFragmentPagerAdapter(
				getSupportFragmentManager());
		vpMusic.setAdapter(pagerAdapter);
	}

	private void setListeners() {
		rgTab.setOnCheckedChangeListener(this);
		vpMusic.setOnPageChangeListener(this);
	}

	private void setViews() {
		rgTab = (RadioGroup) findViewById(R.id.rg_tab);
		rbNew = (RadioButton) findViewById(R.id.rb_new);
		rbHot = (RadioButton) findViewById(R.id.rb_hot);
		vpMusic = (ViewPager) findViewById(R.id.vp_music);

		ivAlbum = (ImageView) findViewById(R.id.iv_album);
		tvTitle = (TextView) findViewById(R.id.tv_musicTitle);
		tvAuthor = (TextView) findViewById(R.id.tv_musicAuthor);

	}

	private class InnerFragmentPagerAdapter extends FragmentPagerAdapter {

		public InnerFragmentPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		if (position == 0) {
			rbNew.setChecked(true);
		} else {
			rbHot.setChecked(true);
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.rb_new) {
			vpMusic.setCurrentItem(0);
		} else {
			vpMusic.setCurrentItem(1);
		}
	}

}
