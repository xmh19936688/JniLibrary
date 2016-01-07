package com.xmh.jni;

import android.content.Context;
import android.util.Log;

import com.xmh.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by mengh on 2016/1/6 006.
 */
public class JniUtil {

    public String getResult(Context context,String value){
        loadLibrary(context);
        return getResult(value);
    }

    private void loadLibrary(Context context) {
        try {

            InputStream inStream = context.getAssets().open("armeabi/libJniLibDemo.so");
            File libsDir = context.getDir("libs", Context.MODE_PRIVATE);
            String soFilePath=libsDir.getAbsolutePath() + "/libJniLibDemo.so";
            FileOutputStream outStream = new FileOutputStream(soFilePath);
            FileUtil.copyFile(inStream,outStream);


            Log.e("xmh", new File(soFilePath).exists() + "");
            System.load(soFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public native String getResult(String value);

}
