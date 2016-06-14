# JniLibrary
:cn:Library for how to use jni and load so file

##AndroidGifDrawableLibrary1.1.8
gif相关Library，新版见[android-gif-drawable]
[android-gif-drawable]:https://github.com/koral--/android-gif-drawable

##JniInGifMakerDemo
使用gifflen合成gif的demo

##SimpleJniDemo
1. 最简单的Jni实现，native层用C实现。
1. 主要功能是java向C传递一个字符串，C经过处理后返回新的字符串。

##StaticLoadSODemo
1. 静态加载so文件。
1. 按照常规方式将so文件放到libs下，并通过java代码调用。
1. 功能与SimpleJniDemo相同。

##RuntimeLoadSODemo
1. 动态（运行时）加载so文件。
1. so文件在assets文件夹下（反正动态加载，随意放哪）。
1. 功能与SimpleJniDemo相同。

##OnlineLoadSODemo
1. 原理还是动态（运行时）加载so文件。
1. so文件在网上，运行时先下载后加载。
1. 功能与SimpleJniDemo相同。
