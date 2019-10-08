# 公共类库 
> `注：` <br/>`项目修改请补充此文件说明，并注明作者与日期。`<br/>
> `第三方类库请注明出处地址或参考文档地址。`

# 目录
* [编码记录](#编码记录)

* [各类库使用说明](#各类库使用说明)<br>

#  使用
######  Step 1. JitPack库添加到你的构建文件

  ```  
  allprojects {
	repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
###### Step 2. 添加相关依赖
  ``` 
   apply plugin: 'com.android.application'
   apply plugin: 'org.greenrobot.greendao' // apply plugin
   
    ..........
    android {    
            defaultConfig {
                 ..........
                ndk {
                    abiFilters "armeabi"    //添加ndk支持
                }        
                 ..........
            }    
           greendao {
                schemaVersion 1
                daoPackage 'XXXXX'//greendao //路径
                targetGenDir 'src/main/java'//保存到java代码路径
            }
             }
           dependencies {
               implementation 'com.github.ZhetengDashen:BaseFramework:1.9.9'
            }  
 ``` 
######  
 
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
  >  修改时间：2019年9月3日12:04:36 <br>
  >  修改内容：修改BaseActivity、BaseFragment、BasePresenter 初始化View顺序逻辑 <br> 


  >  修改人： 王志强 <br>
  >  修改时间：2019年8月29日18:07:42 <br>
  >  修改内容：修复本地日志权限BUG <br> 

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
>  


    
       