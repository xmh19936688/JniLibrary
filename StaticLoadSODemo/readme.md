#静态导入so文件
主要是如何使用so文件

##拷贝so文件
将so文件及其父目录（一般是平台名称）拷贝到module的libs目录下

##配置grandle
```
android {
    sourceSets {
        main {
            jniLibs.srcDirs = ['libs']
        }
    }
}
```

##写java代码
- 包名类名方法名与C函数对应：com.pack.name.ClassName.methodName(String arg)=>Java_com_pack_name_ClassName_methodName(JNIEnv *env,jobject obj,jstring arg)
- 类中静态代码块中`System.loadLibrary("JniLibDemo");`参数一般为so文件名去掉前缀`lib`和后缀`.so`之后的部分。
- 调用该方法`new JniUtil().getResult("arg")`