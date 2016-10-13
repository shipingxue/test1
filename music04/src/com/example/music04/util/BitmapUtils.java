package com.example.music04.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class BitmapUtils {

	public static Bitmap loadBitmap(File file) {
		if (!file.exists()) {
			return null;
		}

		Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());

		return bm;
	}
	
	public static void loadBitmap(final String path, final BitmapCallback callback) {
		AsyncTask<String, String, Bitmap> task = new AsyncTask<String, String, Bitmap>(){

			@Override
			protected Bitmap doInBackground(String... params) {
				try {
					InputStream is = HttpUtils.get(path);
					Bitmap bm = BitmapFactory.decodeStream(is);
					return bm;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Bitmap result) {
				callback.onBitmapLoaded(result);
			}
			
		};
		task.execute();
	}
	

	public static Bitmap loadBitmap(InputStream is, int width, int height)
			throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 10];
		int length = 0;
		while ((length = is.read(buffer)) != -1) {
			bos.write(buffer, 0, length);
			bos.flush();
		}

		byte[] btyes = bos.toByteArray();
		bos.close();

		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		// opts.inPreferredConfig = Config.RGB_565;
		BitmapFactory.decodeByteArray(btyes, 0, btyes.length, opts);
		int w = opts.outWidth / width;
		int h = opts.outHeight / height;
		int scale = w > h ? w : h;

		opts.inJustDecodeBounds = false;
		opts.inSampleSize = scale;
		Bitmap bitmap = BitmapFactory.decodeByteArray(btyes, 0, btyes.length,
				opts);
		return bitmap;
	}

	/**
	 * 根据图片地址获取bitmap,但地址需要本地地址，网地址不行
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static Bitmap loadBitmap1(String path, int width, int height)
			throws IOException {
		path = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES).getPath()
				+ "/a009.jpg";
		Log.i("ping", "picture path:" + path);
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		int w = opts.outWidth / width;
		int h = opts.outHeight / height;
		int scale = w > h ? w : h;

		opts.inJustDecodeBounds = false;
		opts.inSampleSize = scale;
		Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
		return bitmap;
	}

	public static void save(Bitmap bitmap, File file)
			throws FileNotFoundException {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		FileOutputStream fos = new FileOutputStream(file);
		bitmap.compress(CompressFormat.JPEG, 100, fos);
	}

}
