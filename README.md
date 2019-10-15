
# 目录
* [编码记录](commonlibrary/README_CodingHistory.md)<br>

* [详细功能使用说明](commonlibrary/README_FunctionDetails.md)<br>

#  配置
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
# 注
> `项目修改请补充文件说明。`<br/>
> `第三方类库请注明出处地址或参考文档地址。`


    
       