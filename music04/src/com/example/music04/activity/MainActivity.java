package com.example.music04.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.music04.R;
import com.example.music04.fragment.HotMusicListFragment;
import com.example.music04.fragment.NewMusicListFragment;

public class MainActivity extends FragmentActivity implements
		OnPageChangeListener, OnCheckedChangeListener {

	RadioGroup rgTab;
	RadioButton rbNew;
	RadioButton rbHot;
	ViewPager vpMusic;
	PagerAdapter pagerAdapter;
	private List<Fragment> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setViews();

		fragments = new ArrayList<Fragment>();
		fragments.add(new NewMusicListFragment());
		fragments.add(new HotMusicListFragment());

		setAdapter();
		setListeners();

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
