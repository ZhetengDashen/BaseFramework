# ImageLoader使用说明与注意事项
> #### picasso地址：
> https://github.com/square/picasso
> #### glide地址：
> https://github.com/bumptech/glide


#### 配置与注意事项：
##### gradle 配置：
  ```
    api 'com.github.bumptech.glide:glide:4.9.0' 
    annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'
    api 'com.squareup.picasso:picasso:2.71828'
  ```

## 功能说明  
  > [1.ImageLoader初始化](#M1)<br>
  > [2.ImageLoader使用](#ImageLoader的使用)<br>
  `必须继承BaseApplication` 


#####  1.ImageLoader初始化  <br>
 > ImageLoader的初始化已经在BaseApplication中初始化，默认使用 Picasso。如选择其他类型加载框架请在Application中重写父类initImageLoader() 方法，返回对应的图形框架即可。
 ```
    例：
   @Override
    public ImageLoader initImageLoader() {
        return new PicassoImageLoader(getApplicationContext());
        // return new GlideImageLoader(getApplicationContext()); 或者
    }
 ```
##### ImageLoader的使用  <br>
  ``` 
   //简单使用
       ImageLoaderFactory.
       getInstance().
       displayImage(imageView,url);
       
   //设置DisplayOption
      DisplayOption displayOption=new DisplayOption.Builder()
                .setLoadErrorResId(R.drawable.ic_launcher_background)//设置加载错误图片
                .setPlaceHolderResId(R.drawable.ic_launcher_foreground)//设置占位图
                .setHeight(500)//设置宽高
                .setWidth(500)
                .create();
                
       ImageLoaderFactory.
       getInstance().
       displayImage(imageView,url,displayOption);
  ``` 