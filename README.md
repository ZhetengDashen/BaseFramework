# 公共类库 
> `注：` <br/>`项目修改请补充此文件说明，并注明作者与日期。`<br/>
> `第三方类库请注明出处地址或参考文档地址。`

# 目录
* [编码记录](#编码记录)

* [各类库使用说明](#各类库使用说明)<br>

#  使用
######  Step 1. Add the JitPack repository to your build file
  ```  
  allprojects {
	repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
###### Step 2. Add the dependency
  ``` 
   apply plugin: 'com.android.application'
   apply plugin: 'org.greenrobot.greendao' // apply plugin
   
    ..........
    android {
           greendao {
                schemaVersion 1
                daoPackage 'XXXXX'//greendao //路径
                targetGenDir 'src/main/java'//保存到java代码路径
            }
    }
   dependencies {
       implementation 'com.github.ZhetengDashen:BaseFramework:1.9.5'
	}  
 ``` 
###### 添加greendao插件
   ``` 
   // 在 Project的build.gradle 文件中添加:
        buildscript {
            repositories {
                jcenter()
                mavenCentral() // add repository
            }
            dependencies {
                classpath 'com.android.tools.build:gradle:3.1.2'
                classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2' // add plugin
            }
        }
   ``` 
` *注  每创建一个Bean类就需要创建一个BeanManager，用于管理数据库中的表。`  <br/>
       `BeanManager 需要继承 BaseDbBeanManager .`   
   ```   
//例子
public class UnitBeanManager extends BaseDbBeanManager<UnitBean,String> {
  public UnitBeanManager(AbstractDao dao) {
        super(dao);
    }
}
 
   ``` 
## 编码记录


  >  修改人： 王志强 <br>
  >  修改时间：2019年8月23日16:09:22 <br>
  >  修改内容：公共参数修改，BaseObserver构造方法修改 <br> 

  >  修改人： 王志强 <br>
  >  修改时间：2019年8月23日09:56:57 <br>
  >  修改内容：更新构建版本、更新BaseRecyclerViewAdapter版本 <br>  

  >  修改人： 王志强 <br>
  >  修改时间：2019年8月19日15:36:26 <br>
  >  修改内容：增加MVP基础框架、增加Retrofit+RxJava <br>  

  >  修改人： 王志强 <br>
  >  修改时间：2019年7月22日11:37 <br>
  >  修改内容：迁移AndroidX <br>

  >  修改人： 王志强 <br>
  >  修改时间：2019年7月19日17:15 <br>
  >  修改内容：增加 PickerView、PictureSelector、LuBan 第三方库及使用说明及 <br>

  >  修改人： 王志强 <br>
  >  修改时间：2019年5月23日14:14 <br>
  >  修改内容：增加 RxPermissions 使用说明及Android各权限说明 <br>

  >  修改人： 王志强 <br>
  >  修改时间：2019年5月17日13:56 <br>
  >  修改内容：增加 LogUtils 示例与说明 <br>

  >  修改人： 王志强 <br>
  >  修改时间：2019年5月5日10:05 <br>
  >  修改内容：增加 BaseRecyclerViewAdapter 示例与说明 <br>

  >  修改人： 王志强 <br>
  >  修改时间：2019年4月29日14:29 <br>
  >  修改内容：增加 统一封装ImageLoader（Glide、Picasso） <br>
  
  >  修改人： 王志强 <br>
  >  修改时间：2019年4月24日14:12 <br>
  >  修改内容：增加第三方类库 FTP <br>

  >  修改人： 王志强 <br>
  >  修改时间：2019年3月12日13:20 <br>
  >  修改内容：增加第三方类库 EventBus <br>

  >  修改人： 王志强 <br>
  >  修改时间：2019年3月18日16:36:19 <br>
  >  修改内容：增加第三方类库 OkHttp、AVLoadingIndicatorView <br>

  >  修改人： 王志强 <br>
  >  修改时间：2019年3月13日17:23 <br>
  >  修改内容：增加第三方类库 ARouter <br>


## 各类库使用说明
> 1.名称：ARouter <br>
> 出处：https://github.com/alibaba/ARouter <br>
> 介绍：android 路由跳转页面 <br>
> 使用说明：[ARouter使用说明与注意事项](commonlibrary/README_ARouter.md)<br>
   
     
> 2.名称：fastjson <br>
>出处：https://github.com/alibaba/fastjson <br>
>介绍：JSON 解析 <br>
     
     
>3.名称：OkHttp <br>
>出处：https://github.com/square/okhttp <br>
>介绍：网络请求 <br>    
     
     
>4.名称：AVLoadingIndicatorView <br>
>出处：https://github.com/81813780/AVLoadingIndicatorView <br>
>介绍：加载动画 <br>
>使用说明：[AVLoadingIndicatorView使用说明](commonlibrary/README_LoadingView.md)<br>   
     
     
>5.名称：EventBus <br>
>出处：https://github.com/greenrobot/EventBus <br>
>介绍：事件发布/订阅 <br>
>使用说明：[EventBus使用说明与注意事项](commonlibrary/README_EventBus.md)

>6.名称：ImageLoader <br>
>介绍：图形加载框架同一封装 <br>
>使用说明：[ImageLoader使用说明与注意事项](../commonlibrary/README_ImageLoader.md)
   
>7.名称：BaseRecyclerViewAdapterHelper <br>
>出处：https://github.com/CymChad/BaseRecyclerViewAdapterHelper <br>
>介绍：RecyclerViewAdapter封装 <br>
>使用说明：[RecyclerViewAdapter使用说明与注意事项](commonlibrary/README_BaseRecyclerViewAdapter.md)

>8.名称：LogUtils <br>
>出处：https://github.com/pengwei1024/LogUtils <br>
>介绍：日志打印+本地记录  <br>
>使用说明：[LogUtils使用说明与注意事项](commonlibrary/README_LogUtils.md)

>9.名称：RxPermissions <br>
>出处：https://github.com/tbruyelle/RxPermissions <br>
>介绍：权限控制  <br>
>使用说明：[RxPermissions使用说明与注意事项](commonlibrary/README_RxPermissions.md)
  
>10.名称：PickerView <br>
>出处：https://github.com/Bigkoo/Android-PickerView    <br>
>介绍：时间选择、条件选择、城市选择  <br>
>使用说明：[PickerView 使用说明与注意事项](commonlibrary/README_PickerView.md)

>10.名称：PictureSelector <br>
>出处：https://github.com/LuckSiege/PictureSelector   <br>
>介绍：图片选择、拍照、视频、压缩、头像截图  <br>
>使用说明：[PictureSelector 使用说明与注意事项](commonlibrary/README_PickerView.md)

>10.名称：LuBan <br>
>出处：https://github.com/Curzibn/Luban   <br>
>介绍：图片压缩..  <br>
>使用说明：[Luban 使用说明与注意事项](commonlibrary/README_LuBan.md)



    
       