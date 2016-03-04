#运行时导入so文件
主要是如何在运行时导入so文件

##准备so文件
假设so文件在assets目录下

##配置grandle
与静态导入不同，此时不再需要配置gradle

##加载so文件
将so文件从assets文件导入到应用私有lib目录（具体见代码）

##写java代码
- 包名类名方法名与C函数对应：com.pack.name.ClassName.methodName(String arg)=>Java_com_pack_name_ClassName_methodName(JNIEnv *env,jobject obj,jstring arg)
- 类中静态代码块中`System.loadLibrary("JniLibDemo");`参数一般为so文件名去掉前缀`lib`和后缀`.so`之后的部分。
- 调用该方法`new JniUtil().getResult("arg")`