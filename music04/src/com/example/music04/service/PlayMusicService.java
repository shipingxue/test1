package com.example.music04.service;

import com.example.music04.util.GlobalConsts;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings.Global;
import android.util.Log;

public class PlayMusicService extends Service {

	MediaPlayer player = new MediaPlayer();

	@Override
	public void onCreate() {
		player.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				Log.i("ping",
						"player:" + player.hashCode() + "\nmp:" + mp.hashCode());
				mp.start();
				Intent intent = new Intent(GlobalConsts.ACTION_MUSIC_STARTED);
				sendBroadcast(intent);
			}
		});
	}

	@Override
	public IBinder onBind(Intent intent) {
		MusicBinder binder = new MusicBinder();
		return binder;
	}

	public class MusicBinder extends Binder {

		public void palyMusic(String url) {

			try {
				player.reset();
				player.setDataSource(url);
				player.prepareAsync();
				// player.seekTo(msec);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void startOrPause() {
			if (player.isPlaying()) {
				player.pause();
			} else {
				player.start();

			}
		}
	}

	@Override
	public void onDestroy() {
		player.release();
		super.onDestroy();
	}

}
