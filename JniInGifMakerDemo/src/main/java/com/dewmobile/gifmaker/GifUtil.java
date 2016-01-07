package com.dewmobile.gifmaker;

import java.util.List;

import android.graphics.Bitmap;
import android.util.Log;

public class GifUtil {
	
	private final String TAG=this.getClass().getName();
	static{
		System.loadLibrary("gifflen");
	}
	
	public native int Init(String gifName,int w,int h,int munColors,int quality,int frameDelay);
	public native void Close();
	public native int AddFrame(int[] pixels);
	
	public void Encode(String fileName,Bitmap[] bitmaps,int delay){
		if(bitmaps==null||bitmaps.length==0){
            throw new NullPointerException("Bitmaps should have content!!!");
        }
        int width=bitmaps[0].getWidth();
        int height=bitmaps[0].getHeight();
 
        if(Init(fileName,width,height,256,100,delay)!=0){
            Log.e(TAG, "GifUtil init failed");
            return;
        }

        for(Bitmap bp:bitmaps){
            int pixels[]=new  int[width*height]; 
            bp.getPixels(pixels, 0, width,  0, 0, width, height);
            AddFrame(pixels);
        }
        Close();
	}
	
	public void Encode(String fileName,List<Bitmap> bitmaps,int delay){
		if(bitmaps==null||bitmaps.size()==0){
			throw new NullPointerException("Bitmaps should have content!!!");
		}
		int width=bitmaps.get(0).getWidth();
		int height=bitmaps.get(0).getHeight();
		Log.e(TAG, "init start");
		int result=Init(fileName,width,height,256,100,delay);
//		int result=Init(fileName,width,height,128,9,delay);
		Log.e(TAG, "init finish");
		if(result!=0){
			Log.e(TAG, "GifUtil init failed with result:"+result+",filename:"+fileName);
			return;
		}
		
		for(Bitmap bp:bitmaps){
			int pixels[]=new  int[width*height]; 
			bp.getPixels(pixels, 0, width,  0, 0, width, height);
			Log.e(TAG, "addframe start");
			AddFrame(pixels);
			Log.e(TAG, "addframe finish");
		}
		Log.e(TAG, "close start");
		Close();
		Log.e(TAG, "close finish");
	}

	
}
