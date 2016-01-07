//
// Created by mengh on 2016/1/6 006.
//

#include "jni.h"
#include <stdio.h>

JNIEXPORT jstring JNICALL
Java_com_xmh_jni_JniUtil_getResult(JNIEnv *env,jobject obj,jstring value){
    char * str;
    str=(*env)->GetStringUTFChars(env,value,NULL);
    sprintf(str,"%s-_-%s",str,str);
    return (*env)->NewStringUTF(env, str);
}