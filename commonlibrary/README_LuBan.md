# LuBan使用说明与注意事项
> #### github地址：
>https://github.com/Curzibn/Luban



#### 配置与注意事项：
##### gradle 配置：
  ```
    implementation 'top.zibin:Luban:1.1.8'
  ```

## 使用说明  
  ``` 
   //单个文件
   LuBanUtils.compressionImage(filepath, context, new OnCompressListener() {
            @Override
            public void onStart() {
                
            }

            @Override
            public void onSuccess(File file) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
        
       //多个文件 
    LuBanUtils.compressionImage(filepathList, context, new LuBanUtils.OnCompressionImageListListener() {
            @Override
            public void onStart() {
                
            }

            @Override
            public void onSuccess(File file, int index) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
  ``` 

  
