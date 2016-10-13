package com.example.music04.util;

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
import android.widget.AbsListView;
import android.widget.ImageView;

import com.example.music04.R;

public class ImageLoader {
	private Context context;
	private AbsListView listView;
	private boolean isLoop = true;
	private Thread thread;
	private Map<String, SoftReference<Bitmap>> cache = new HashMap<String, SoftReference<Bitmap>>();

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HAND_IMAGE_LOADED:
				ImageLoadTask task = (ImageLoadTask) msg.obj;
				ImageView ivalbum = (ImageView) listView
						.findViewWithTag(task.path);
				if (ivalbum != null) {
					Bitmap bm = task.bitmap;
					if (bm != null) {
						ivalbum.setImageBitmap(bm);
						Log.i("ping", "从网上加载的图片..");
					} else {
						ivalbum.setImageResource(R.drawable.ic_launcher);
					}
				}
				break;

			}
		}
	};

	private static final int HAND_IMAGE_LOADED = 100;

	// 声明用于轮询的任务集合
	List<ImageLoadTask> tasks = new ArrayList<ImageLoadTask>();

	public ImageLoader(Context context, AbsListView listView) {
		this.context = context;
		this.listView = listView;

		thread = new Thread() {
			@Override
			public void run() {
				while (isLoop) {
					if (!tasks.isEmpty()) {
						ImageLoadTask task = tasks.remove(0);
						String path = task.path;
						Bitmap bitmap = loadBitmap(path);
						task.bitmap = bitmap;

						// handler
						Message msg = new Message();
						msg.what = HAND_IMAGE_LOADED;
						msg.obj = task;
						handler.sendMessage(msg);
					} else {
						synchronized (thread) {
							try {
								thread.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}

				}

			}
		};
		thread.start();
	}

	public void display(ImageView ivAlbum, String path) {

		ivAlbum.setTag(path);

		// 去内存中寻找图片，是否已经存过
		SoftReference<Bitmap> ref = cache.get(path);
		if (ref != null) {
			Bitmap bm = ref.get();
			if (bm != null) {
				ivAlbum.setImageBitmap(bm);
				Log.i("ping", "从内存缓存中找到的图片..");
				return;
			}
		}

		// 去文件缓存中寻找是否有图片
		String fileName = path.substring(path.lastIndexOf("/"));
		File file = new File(context.getCacheDir(), "images" + fileName);
		Bitmap bm = BitmapUtils.loadBitmap(file);
		if (bm != null) {
			ivAlbum.setImageBitmap(bm);
			Log.i("ping", "从文件缓存中找到的图片..");
			cache.put(path, new SoftReference<Bitmap>(bm));
			return;
		}

		ImageLoadTask task = new ImageLoadTask();
		task.path = path;
		tasks.add(task);
		synchronized (thread) {
			thread.notify();
		}

	}

	class ImageLoadTask {
		String path;
		Bitmap bitmap; // 根据 path 下载到的图片
	}

	protected Bitmap loadBitmap(String path) {
		try {
			InputStream is = HttpUtils.get(path);
			Bitmap bitmap = BitmapUtils.loadBitmap(is, 50, 50);
			// Bitmap bitmap = BitmapUtils.loadBitmap1(path, 50, 50);

			// 把bitmap存入内存缓存中
			cache.put(path, new SoftReference<Bitmap>(bitmap));

			// 向文件缓存中存图片
			String fileName = path.substring(path.lastIndexOf("/"));
			File file = new File(context.getCacheDir().getPath(), "images"
					+ fileName);
			BitmapUtils.save(bitmap, file);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void stopThread() {
		isLoop = false;
		synchronized (thread) {
			thread.notify();
		}

	}

}
