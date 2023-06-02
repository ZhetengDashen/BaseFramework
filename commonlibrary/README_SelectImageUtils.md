# SelectImageUtils使用说明与注意事项
## 使用说明  
#### 1.
>  功能说明：选择图片+图片列表编辑  
>  参数说明： activity、   SelectImageCallBack：回调接口  
>  回调方式：接口  
>  使用示例：
> ```
>  SelectImageUtils.getInstance().startSelectImage(MainActivity.this, new SelectImageCallBack() {
>                    @Override
>                    public void onImageSelected(List<SelectImageBean> imageBeans) {
>                        
>                    }
>                });
>```
#### 2.
>  功能说明：选择图片+图片列表编辑  
>  参数说明： activity、   SelectImageCallBack：回调接口  、selectImageBeans：预设图片列表数据  
>  回调方式：接口  
>  使用示例：
> ```
>  SelectImageUtils.getInstance().startSelectImage(MainActivity.this, new SelectImageCallBack() {
>                    @Override
>                    public void onImageSelected(List<SelectImageBean> imageBeans) {
>                        
>                    }
>                },selectImageBeans);
>```
#### 3.
>  功能说明：选择图片+图片列表编辑  
>  参数说明： activity、   "imageCallback"：EventBusFlagName  
>  回调方式：EventBus  
>  使用示例：
> ```
>  SelectImageUtils.getInstance().startSelectImage(MainActivity.this,"imageCallback");
>```
#### 4.
>  功能说明：选择图片+图片列表编辑  
>  参数说明： activity、   "imageCallback"：EventBusFlagName 、selectImageBeans：预设图片列表数据  
>  回调方式：EventBus  
>  使用示例：
> ```
>  SelectImageUtils.getInstance().startSelectImage(this,"imageCallback",selectImageBeans);
>```

#### 5.
>  功能说明：只有拍照+图片列表编辑  
>  参数说明： activity、    takingPhotoCallBack :回调接口
>  回调方式：接口  
>  使用示例：
> ```
 >    SelectImageUtils.getInstance().startTakingPhoto(this, new TakingPhotoCallBack() {
 >                   @Override
 >                    public void onTakingPhoto(List<SelectImageBean> imageBeans) {
 >                       
 >                   }
 >              });
>```

#### 6.
>  功能说明：只有拍照+图片列表编辑  
>  参数说明： activity、    takingPhotoCallBack :回调接口  、selectImageBeans：预设图片列表数据
>  回调方式：接口  
>  使用示例：
> ```
>     SelectImageUtils.getInstance().startTakingPhoto(this, new TakingPhotoCallBack() {
>                     @Override
>                    public void onTakingPhoto(List<SelectImageBean> imageBeans) {
>                       
>                  }
  >               },selectImageBeans);
>```

#### 7.
>  功能说明：只有拍照+图片列表编辑  
>  参数说明： activity、   "imageCallback"：EventBusFlagName  
>  回调方式：EventBus  
>  使用示例：
> ```
>     SelectImageUtils.getInstance().startTakingPhoto(this, "imageCallback");
>```

#### 8.
>  功能说明：只有拍照+图片列表编辑  
>  参数说明： activity、   "imageCallback"：EventBusFlagName 、selectImageBeans：预设图片列表数据  
>  回调方式：EventBus  
>  使用示例：
> ```
>     SelectImageUtils.getInstance().startTakingPhoto(this, "imageCallback",selectImageBeans);
>```
#### 9.
>  功能说明：单张拍照  
>  参数说明： activity、   takingPhotoSeparateCallBack  回调接口  
>  回调方式：接口  
>  使用示例：
> ```
>    SelectImageUtils.getInstance().startTakingPhotoSeparate(this, new TakingPhotoSeparateCallBack() {
>                     @Override
>                     public void onTakingPhoto(SelectImageBean imageBean) {     
>                     }
 >                });
>```

#### 10.
>  功能说明：单张拍照  
>  参数说明： activity、  "imageCallback"：EventBusFlagName    
>  回调方式： EventBus  
>  使用示例：
> ```
>    SelectImageUtils.getInstance().startTakingPhotoSeparate(this, "takingPhotoCallBack");
>```

#### 11.
>  功能说明：单张拍照或图片选择 
>  参数说明： activity、   takingPhotoSeparateCallBack  回调接口  
>  回调方式：接口  
>  使用示例：
> ```
>      SelectImageUtils.getInstance().startTakingPhotoAndImageSeparate(this,new TakingPhotoSeparateCallBack() {
>                     @Override
>                     public void onTakingPhoto(SelectImageBean imageBean) {     
>                     }
 >                }););
>```

#### 12.
>  功能说明：单张拍照或图片选择  
>  参数说明： activity、"imageCallback"：EventBusFlagName    
>  回调方式： EventBus  
>  使用示例：
> ```
>   SelectImageUtils.getInstance().startTakingPhotoAndImageSeparate(this, "imageCallback");
>```

#### 13.
>  功能说明：拍摄身份证头像面+裁剪 
>  参数说明： activity、TakingPhotoIDCardCallBack 接口    
>  回调方式： 接口  
>  使用示例：
> ```
>    SelectImageUtils.getInstance().startTakingPhotoIdCardImageHead(this, new TakingPhotoIDCardCallBack() {
                    @Override
                    public void onTakingPhotoHead(String imagePaths) {
                      
                        ImageLoaderFactory.getInstance().displayImage(image,imagePaths);
                    }

                    @Override
                    public void onTakingPhotoEmblem(String imagePaths) {
                       
                    }
                });
>```
#### 14.
>  功能说明：拍摄身份证头像面+裁剪
>  参数说明： activity、TakingPhotoIDCardCallBack 接口    
>  回调方式： 接口  
>  使用示例：
> ```
>    SelectImageUtils.getInstance().startTakingPhotoIdCardImageEmblem(this, new TakingPhotoIDCardCallBack() {
                    @Override
                    public void onTakingPhotoHead(String imagePaths) {

                    }

                    @Override
                    public void onTakingPhotoEmblem(String imagePaths) {
                       
                        ImageLoaderFactory.getInstance().displayImage(image,imagePaths);

                    }
                });
>```
#### 15.
>  功能说明：拍摄银行卡
>  参数说明： activity、TakingPhotoBankCallBack 接口    
>  回调方式： 接口  
>  使用示例：
> ```
>     SelectImageUtils.getInstance().startTakingPhotoBankImage(this, new TakingPhotoBankCallBack() {
                    @Override
                    public void onTakingPhoto(String imagePaths) {
                        ImageLoaderFactory.getInstance().displayImage(image,imagePaths);
                    }
                });
>```
### 附加说明：
 > 1.通过EventBus回调接收数据说明：  
 ```  
 @Override
   public void eventBusMessageOnMainThread(EventMessage event) {
         super.eventBusMessageOnMainThread(event);  
           case "imageCallback"://EventBusFlagName
               selectImageBeans= (List<SelectImageBean>) event.getEvent();              
              break;
       }
 }
  ```
  >2.实体类：SelectImageBean  
 ``` 
     String path="";//原图路径  
     String compressPath="";//压缩图路径
 ```
 
 
 其他功能请参考： PictureSelector <br>（有时间封装其他功能）
>介绍：图片选择、拍照、视频、压缩、头像截图  <br>
>使用说明：[PictureSelector 使用说明与注意事项](README_PictureSelector.md)
