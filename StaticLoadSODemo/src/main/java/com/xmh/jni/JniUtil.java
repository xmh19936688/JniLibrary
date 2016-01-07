package com.xmh.jni;

/**
 * Created by mengh on 2016/1/6 006.
 */
public class JniUtil {

    static {
        System.loadLibrary("JniLibDemo");
    }

    public native String getResult(String value);

}
