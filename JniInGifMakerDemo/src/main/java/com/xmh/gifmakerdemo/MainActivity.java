package com.xmh.gifmakerdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.dewmobile.gifmaker.GifUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifDrawableBuilder;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG="MainActivity";

	private Button decodeBtn;
	private Button makeBtn;
	private ImageView imageImg;
	private GifImageView oldGif;
	private GifImageView recodeGif;
	private GifImageView newGif;

	private List<Bitmap> mList;
	private UIHandler mUIHandler=new UIHandler();

	private class UIHandler extends Handler{
		public static final int UPDATE_NEW_GIF=1001;
		public static final int UPDATE_FRAME_IMAGE=1002;
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case UPDATE_NEW_GIF:
					updateUI((Uri) msg.obj);
					break;

				case UPDATE_FRAME_IMAGE:
					imageImg.setImageBitmap(mList.get(0));
					imageImg.setVisibility(View.GONE);
					decodeBtn.setText(decodeBtn.getText()+""+mList.size());
					break;
			}
			super.handleMessage(msg);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
	}

	private void init() {
		decodeBtn=(Button) findViewById(R.id.btn_decode);
		makeBtn=(Button) findViewById(R.id.btn_make);
		newGif=(GifImageView) findViewById(R.id.gif_new);
		oldGif=(GifImageView) findViewById(R.id.gif_old);
		recodeGif=(GifImageView) findViewById(R.id.gif_old_recode);
		imageImg=(ImageView) findViewById(R.id.img_image);

		oldGif.setImageResource(R.drawable.gif_girl);
//		recodeGif.setImageResource(R.drawable.gif_recode);
		decodeBtn.setOnClickListener(this);
		makeBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_decode:
//			new Thread(new Runnable() {
//				public void run() {
				if(decode()){
					mUIHandler.sendEmptyMessage(UIHandler.UPDATE_FRAME_IMAGE);
				}
//				}
//			}).start();


				break;

			case R.id.btn_make:
				if(mList.size()<1){
					return;
				}
				new Thread(new Runnable() {
					public void run() {
						Uri uri = getGif();
						Message message = mUIHandler.obtainMessage();
						message.what=UIHandler.UPDATE_NEW_GIF;
						if(uri!=null){
							message.obj=uri;
							mUIHandler.sendMessage(message);
						}
					}
				}).start();

				break;
		}
	}

	private boolean decode() {
		try {
			GifDrawableBuilder gifDrawableBuilder = new GifDrawableBuilder();
			gifDrawableBuilder.from(getResources(),R.drawable.gif_girl);
			GifDrawable gifDrawable = gifDrawableBuilder.build();
			int numberOfFrames = gifDrawable.getNumberOfFrames();

			mList=new ArrayList<Bitmap>();
			for(int i=0;i<numberOfFrames;i++){
				Bitmap bitmap = gifDrawable.seekToFrameAndGet(i);
				mList.add(bitmap);
				saveBitmap(bitmap, i);
//				bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);

			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void saveBitmap(Bitmap bm,int i) {
		Log.e(TAG, "保存图片");
		File f = new File(Environment.getExternalStorageDirectory()+"/zzz", i+".png");
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			Log.i(TAG, "已经保存");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Uri getGif() {
		String filepath=Environment.getExternalStorageDirectory() +"/test.gif";
		int delay =1;
		Log.e(TAG, "start");
		new GifUtil().Encode(filepath, mList, delay);
		Log.e(TAG, "finish");
		File f=new File(filepath);
		if(!f.exists()){
			Log.e(TAG, "file is not exists");
			return null;
		}
		Uri uri=Uri.fromFile(f);
		return uri;
	}

	private void updateUI(Uri uri){
		newGif.setImageURI(uri);
	}
}
