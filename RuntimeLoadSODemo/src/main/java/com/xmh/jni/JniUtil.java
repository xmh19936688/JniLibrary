package com.xmh.jni;

import android.content.Context;

import com.xmh.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by mengh on 2016/1/6 006.
 */
public class JniUtil {

    /*封装native方法，先加载so文件后调用native方法*/
    public String getResult(Context context,String value){
        loadLibrary(context);
        return getResult(value);
    }

    /*加载so文件*/
    private void loadLibrary(Context context) {
        try {
            //获取文件输入流
            InputStream inStream = context.getAssets().open("armeabi/libJniLibDemo.so");
            //获取文件输出流：目录是/data/data/app包名/app_lib/（该目录为app私有目录，具有执行权限）
            File libsDir = context.getDir("libs", Context.MODE_PRIVATE);
            String soFilePath=libsDir.getAbsolutePath() + "/libJniLibDemo.so";
            FileOutputStream outStream = new FileOutputStream(soFilePath);
            //使用两个流复制文件
            FileUtil.copyFile(inStream,outStream);
            //加载so文件
            System.load(soFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public native String getResult(String value);

}
