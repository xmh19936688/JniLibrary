#最简单的Jni，包含java代码和c代码
主要是C代码与Java代码的编写与gradle配置

##配置grandle
```
android {
    defaultConfig {
        ndk{
            moduleName "JniLibDemo"
        }
    }
}
```

##写C代码
- module上右键->new->Folder->JNI Folder
- 在文件夹中新建包含代码的C文件，文件名随意（如jnidemo.c）
- 在文件夹中新建空C文件，文件名随意（如empty.c），文件内容可不写东西，但必须有这个空文件，是AndroidStudio的bug
- 在jnidemo.c中写C代码（详见文件,注意函数名与include）
- 新建java文件对应C函数，注意java类的包名文件名与c方法对应
- C方法名与java类名对应：com.pack.name.ClassName.methodName(String arg)=>Java_com_pack_name_ClassName_methodName(JNIEnv *env,jobject obj,jstring arg)

##写java代码
- 包名类名方法名如上述规则
- 类中静态代码块中`System.loadLibrary("JniLibDemo");`参数同gradle中配置的moduleName
- 调用该方法`new JniUtil().getResult("arg")`