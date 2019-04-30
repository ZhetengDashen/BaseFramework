# AVLoadingIndicatorView使用说明与注意事项
> #### github地址：
> https://github.com/81813780/AVLoadingIndicatorView



#### 配置与注意事项：
##### gradle 配置：
  ```
   compile 'com.wang.avi:library:2.1.3'
  ```

## 使用说明  
  ``` 
     //启动LoadingView
     MyLoader.showLoading(LoadingViewActivity.this);
    
     //停止LoadingView
     MyLoader.stopLoading();
  ``` 
#### 切换动画   
 ```  
     MyLoader.showLoading(LoadingViewActivity.this,LoaderStyle.BallPulseSyncIndicator);
 ``` 
>查看动画示例详情, 请运行之后查看LoadingViewActivity， 根据对应的动画名称进行切换。