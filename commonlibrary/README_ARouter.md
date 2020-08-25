# ARouter使用说明与注意事项

> #### ARouter地址：
>  https://github.com/alibaba/ARouter

#### ARouter配置与注意事项：
##### gradle 配置：
  ```
 android {
     defaultConfig {
         ...
         javaCompileOptions {
             annotationProcessorOptions {
                 arguments = [AROUTER_MODULE_NAME: project.getName()]
             }
         }
     }
 }
 
 dependencies {
     // 替换成最新版本, 需要注意的是api
     // 要与compiler匹配使用，均使用最新版可以保证兼容
     compile 'com.alibaba:arouter-api:x.x.x'
     annotationProcessor 'com.alibaba:arouter-compiler:x.x.x'
     ...
 } 
  ```
##### 新引用module需要在build.gradle 进行如下配置
  ```   
    android {
        defaultConfig {
            ...
            javaCompileOptions {
                annotationProcessorOptions {
                    arguments = [AROUTER_MODULE_NAME: project.getName()]
                }
            }
        }
    }    
    dependencies {      
        // 要与commonlibrary中的版本号一致  
        annotationProcessor 'com.alibaba:arouter-compiler:x.x.x'
        ...
    } 
  ```
## 功能说明
  
  > [1.页面跳转](#1页面跳转) <br>
  > [2.页面跳转传值](#2页面跳转传值)<br>

  
##### 1页面跳转  <br>
 >###### ARouterTools为页面统一跳转管理类
 
 >``` 
 >例：
 >  ARouterTools.startActivity(ARouterPath.AppMode.MAIN_TEST);
 > ```
 >`*必须继承BaseActivity`
 > ##### 添加注解: 要跳转到的Activity 需要配置 @Route  path 注解 <br>
 >  ARouterPath为Activity统一跳转路径管理类
 >  ```   
 > 例：
 > @Route(path = ARouterPath.AppMode.MAIN_ACTIVITY)
 > public class MainActivity extends BaseActivity{
 >    .....
 >  }
 > ```

#### 2页面跳转传值 <br>
> ###### 跳转并传值 
>`*必须继承BaseActivity`
 >  ```   
 > 例：
 > 传String值
 >ARouterTools.startActivity(ARouterPath.AppMode.MAIN_TEST,"wo","卡机是");
 > 例2： 
 >传Object
 > ARouterTools.startActivity(ARouterPath.AppMode.MAIN_TEST,"user",new TestUser("王大锤" ,"0","女"));
 > ```
  
> ###### 取值  
> `*必须继承BaseActivity`
 >  ```   
 > 例：
 > @Autowired
 > public String wo; //变量名称要与传值的key一致
 > @Autowired
> public TestUser user;
>  //也可以这么写（重命名）
> @Autowired(name="user")
 > public TestUser testUser;
 > @Override
 > protected void onCreate(Bundle savedInstanceState) {
 >  ......
 >  //testUser已经自动赋值 可以直接使用
 >  Log.e("KK",testUser.toString());
 >  }
 > ``` 

   
##### 各类说明：<br>
 [ARouterPath.java]( src\/main\/java\/com\/baseeasy\/commonlibrary\/arouter\/ARouterPath.java) :<br> 各个Activity路由配置地址管理类
  
 `注： ARouter的初始化已经放在了BaseApplication当中, 引用commonlibrary的app需要在AndroidManifest中配置BaseApplication。` <br>
 `如果需要请自定义Application需要继承BaseApplication 避免ARouter 出错 ` <br>
 ` * 描述：各个Activity路由跳转路径，每个静态内部类为mode名称，如果新建了mode请新建一个静态内部类。` <br>
 ` * 注： 路径名称规范:"/mode名称/activity名称"` <br>
 ` * 不同module的一级路径必须不同，否则会导致一个moudle中的一级路径失效！！！` <br>
 ` * 多个mode必须都在app中添加依赖 否则无法找到路由路径`<br>
 ` * 多个mode必须都在app中添加依赖 否则无法找到路由路径`<br>
 ` *  arouter传值、对象中不可私有变量`<br>
 [ARouterTools.java](src\/main\/java\/com\/baseeasy\/commonlibrary\/arouter\/ARouterTools.java): <br> Activity 路由跳转工具类
 
 [JsonServiceImpl.java](src\/main\/java\/com\/baseeasy\/commonlibrary\/arouter\/JsonServiceImpl.java): <br> Activity 路由跳转传递对象时候序列化用到的。

 
 