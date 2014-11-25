package org.zarroboogs.weibo.login.javabean;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

public class DoorImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

	public static interface OnDoorOpenListener {
		public void onDoorOpen(Bitmap result);
	}

	public OnDoorOpenListener mOnDoorOpenListener;
	
    public void setOnDoorOpenListener(OnDoorOpenListener mOnDoorOpenListener) {
		this.mOnDoorOpenListener = mOnDoorOpenListener;
	}
	
	//http://login.sina.com.cn/cgi/pin.php?r=36335909&s=0&p=gz-9cd2855b50ab922e36b69594515415d307ec
	@Override
	protected Bitmap doInBackground(String... params) {
		// params[0]  picd
		String r = SystemClock.uptimeMillis() + "";
		Log.d("DoorImageAsyncTask ", "" + r);
		String url = "http://login.sina.com.cn/cgi/pin.php?r=" + r + "&s=0&p=" + params[0];
		return returnBitMap(url);
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (mOnDoorOpenListener != null) {
			this.mOnDoorOpenListener.onDoorOpen(result);
		}
	}

	public Bitmap returnBitMap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
