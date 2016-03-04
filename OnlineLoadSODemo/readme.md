#在线导入so文件
同样是在运行时导入so文件，但此时so文件是在网上存储，而非本地

##准备so文件
假设so文件在`LIB_FILE_NET_PATH`

##配置grandle
与运行时导入一样，不需要配置gradle

##加载so文件
将so文件从`LIB_FILE_NET_PATH`下载到应用私有lib目录（具体见代码）

##写java代码
- 包名类名方法名与C函数对应：com.pack.name.ClassName.methodName(String arg)=>Java_com_pack_name_ClassName_methodName(JNIEnv *env,jobject obj,jstring arg)
- 类中静态代码块中`System.loadLibrary("JniLibDemo");`参数一般为so文件名去掉前缀`lib`和后缀`.so`之后的部分。
- 调用该方法`new JniUtil().getResult("arg")`