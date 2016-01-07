package com.xmh.application;

import android.app.Application;
import android.util.Log;

import com.xmh.jni.JniUtil;

/**
 * Created by mengh on 2016/1/7 007.
 */
public class JniApplication extends Application{

    /**在应用创建时开始加载so文件*/
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("xmh-jni", "app-create");
        JniUtil.getInstance().loadLibrary(this);
    }
}
