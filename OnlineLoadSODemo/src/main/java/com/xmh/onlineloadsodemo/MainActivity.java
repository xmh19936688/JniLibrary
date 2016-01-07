package com.xmh.onlineloadsodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.thin.downloadmanager.DownloadStatusListener;
import com.xmh.jni.JniUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv_result)TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final JniUtil jniUtil = JniUtil.getInstance();
        //判断是否已加载so文件，如已加载则调用封装后的本地方法，否则设置加载回调后开始加载so文件
        if(jniUtil.getLibraryStatus()==JniUtil.LIBRARY_LOAD_STATUS_LOADED) {
            tvResult.setText(jniUtil.getResult(this, "xmh"));
        }else {
            jniUtil.setOnDownloadListener(new DownloadStatusListener() {
                @Override
                public void onDownloadComplete(int id) {
                    //加载完成后执行操作
                    tvResult.setText(jniUtil.getResult(MainActivity.this, "xmh"));
                }
                @Override
                public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                }
                @Override
                public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {
                }
            });
            jniUtil.loadLibrary(this);
        }
    }
}
