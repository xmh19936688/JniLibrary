package com.xmh.jni;

import android.content.Context;
import android.net.Uri;

import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;

/**
 * Created by mengh on 2016/1/6 006.
 */
public class JniUtil {

    /**so文件的URL*/
    private static String LIB_FILE_NET_PATH="https://raw.githubusercontent.com/xmh19936688/JniLibrary/master/RuntimeLoadSODemo/src/main/assets/armeabi/libJniLibDemo.so";

    //region so文件加载状态
    /**未加载*/
    public static int LIBRARY_LOAD_STATUS_NONE=0;
    /**正在加载*/
    public static int LIBRARY_LOAD_STATUS_LOADING=1;
    /**已加载*/
    public static int LIBRARY_LOAD_STATUS_LOADED=2;
    /**so文件加载状态*/
    private int libraryLoadStatus=LIBRARY_LOAD_STATUS_NONE;
    //endregion

    /**so文件加载回调*/
    private DownloadStatusListener downloadStatusListener=null;

    //region 单例模式
    private static JniUtil jniUtil=null;
    private JniUtil(){}
    public static JniUtil getInstance(){
        if(jniUtil!=null)return jniUtil;
        return jniUtil=new JniUtil();
    }
    //endregion

    /**获取so文件加载状态*/
    public int getLibraryStatus(){
        return libraryLoadStatus;
    }

    /**本地方法私有化，防止未加载就调用*/
    public native String getResult(String value);

    /**封装后的本地方法*/
    public String getResult(Context context,String value){
        //先判断是否加载了so文件
        if(libraryLoadStatus==LIBRARY_LOAD_STATUS_NONE) {
            loadLibrary(context);
            return null;
        }
        return getResult(value);
    }

    /**加载so文件*/
    public void loadLibrary(Context context) {
        //设置加载状态
        libraryLoadStatus=LIBRARY_LOAD_STATUS_LOADING;
        try {
            File libsDir = context.getDir("libs", Context.MODE_PRIVATE);
            final String soFilePath=libsDir.getAbsolutePath() + "/libJniLibDemo.so";

            Uri uri=Uri.parse(LIB_FILE_NET_PATH);
            final DownloadRequest downloadRequest = new DownloadRequest(uri);
            downloadRequest.setRetryPolicy(new DefaultRetryPolicy());//默认重试逻辑
            downloadRequest.setDestinationURI(Uri.parse(soFilePath));
            downloadRequest.setDownloadListener(new DownloadStatusListener() {
                @Override
                public void onDownloadComplete(int id) {
                    System.load(soFilePath);
                    libraryLoadStatus=LIBRARY_LOAD_STATUS_LOADED;//设置加载状态
                    if(downloadStatusListener!=null) downloadStatusListener.onDownloadComplete(id);
                }

                @Override
                public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                    libraryLoadStatus=LIBRARY_LOAD_STATUS_NONE;//设置加载状态
                    if(downloadStatusListener!=null) downloadStatusListener.onDownloadFailed(id, errorCode, errorMessage);
                }

                @Override
                public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {
                    if(downloadStatusListener!=null) downloadStatusListener.onProgress(id,totalBytes,downloadedBytes,progress);
                }
            });
            new ThinDownloadManager().add(downloadRequest);

        } catch (Exception e) {
            //设置加载状态
            libraryLoadStatus=LIBRARY_LOAD_STATUS_NONE;
            e.printStackTrace();
        }
    }

    /**设置so文件加载回调*/
    public void setOnDownloadListener(DownloadStatusListener listener){
        this.downloadStatusListener =listener;
    }

}
