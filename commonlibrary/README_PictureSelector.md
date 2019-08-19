# LogUtils使用说明与注意事项
> #### github地址：
>https://github.com/LuckSiege/PictureSelector



#### 配置与注意事项：
##### gradle 配置：
  ```
  implementation 'com.github.LuckSiege.PictureSelector:picture_library:v2.2.3'
  ```

## 使用说明  
 ## 功能配置
```
// 进入相册 以下是例子：用不到的api可以不写
 PictureSelector.create(MainActivity.this)
 	.openGallery()//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
 	.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
 	.maxSelectNum()// 最大图片选择数量 int
 	.minSelectNum()// 最小选择数量 int
	.imageSpanCount(4)// 每行显示个数 int
 	.selectionMode()// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
 	.previewImage()// 是否可预览图片 true or false
 	.previewVideo()// 是否可预览视频 true or false
	.enablePreviewAudio() // 是否可播放音频 true or false
 	.isCamera()// 是否显示拍照按钮 true or false
	.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
	.isZoomAnim(true)// 图片列表点击 缩放效果 默认true
	.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
	.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
 	.enableCrop()// 是否裁剪 true or false
 	.compress()// 是否压缩 true or false
 	.glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
 	.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
 	.hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
 	.isGif()// 是否显示gif图片 true or false
	.compressSavePath(getPath())//压缩图片保存地址
 	.freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
 	.circleDimmedLayer()// 是否圆形裁剪 true or false
 	.showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
 	.showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
 	.openClickSound()// 是否开启点击声音 true or false
 	.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
 	.previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
 	.cropCompressQuality()// 裁剪压缩质量 默认90 int
 	.minimumCompressSize(100)// 小于100kb的图片不压缩 
 	.synOrAsy(true)//同步true或异步false 压缩 默认同步
 	.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int 
 	.rotateEnabled() // 裁剪是否可旋转图片 true or false
 	.scaleEnabled()// 裁剪是否可放大缩小图片 true or false
 	.videoQuality()// 视频录制质量 0 or 1 int
	.videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int 
        .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int 
	.recordVideoSecond()//视频秒数录制 默认60s int
	.isDragFrame(false)// 是否可拖动裁剪框(固定)
 	.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code     
```

## 缓存清除
```
 //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限 
 PictureFileUtils.deleteCacheDirFile(MainActivity.this);
 
```
## 主题配置

```
<!--默认样式 注意* 样式只可修改，不能删除任何一项 否则报错-->
    <style name="picture.default.style" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <!--标题栏背景色-->
        <item name="colorPrimary">@color/bar_grey</item>
        <!--状态栏背景色-->
        <item name="colorPrimaryDark">@color/bar_grey</item>
        <!--是否改变图片列表界面状态栏字体颜色为黑色-->
        <item name="picture.statusFontColor">false</item>
        <!--返回键图标-->
        <item name="picture.leftBack.icon">@drawable/picture_back</item>
        <!--标题下拉箭头-->
        <item name="picture.arrow_down.icon">@drawable/arrow_down</item>
        <!--标题上拉箭头-->
        <item name="picture.arrow_up.icon">@drawable/arrow_up</item>
        <!--标题文字颜色-->
        <item name="picture.title.textColor">@color/white</item>
        <!--标题栏右边文字-->
        <item name="picture.right.textColor">@color/white</item>
        <!--图片列表勾选样式-->
        <item name="picture.checked.style">@drawable/checkbox_selector</item>
        <!--开启图片列表勾选数字模式-->
        <item name="picture.style.checkNumMode">false</item>
        <!--选择图片样式0/9-->
        <item name="picture.style.numComplete">false</item>
        <!--图片列表底部背景色-->
        <item name="picture.bottom.bg">@color/color_fa</item>
        <!--图片列表预览文字颜色-->
        <item name="picture.preview.textColor">@color/tab_color_true</item>
        <!--图片列表已完成文字颜色-->
        <item name="picture.complete.textColor">@color/tab_color_true</item>
        <!--图片已选数量圆点背景色-->
        <item name="picture.num.style">@drawable/num_oval</item>
        <!--预览界面标题文字颜色-->
        <item name="picture.ac_preview.title.textColor">@color/white</item>
        <!--预览界面已完成文字颜色-->
        <item name="picture.ac_preview.complete.textColor">@color/tab_color_true</item>
        <!--预览界面标题栏背景色-->
        <item name="picture.ac_preview.title.bg">@color/bar_grey</item>
        <!--预览界面底部背景色-->
        <item name="picture.ac_preview.bottom.bg">@color/bar_grey_90</item>
        <!--预览界面返回箭头-->
        <item name="picture.preview.leftBack.icon">@drawable/picture_back</item>
        <!--是否改变预览界面状态栏字体颜色为黑色-->
        <item name="picture.preview.statusFontColor">false</item>
        <!--裁剪页面标题背景色-->
        <item name="picture.crop.toolbar.bg">@color/bar_grey</item>
        <!--裁剪页面状态栏颜色-->
        <item name="picture.crop.status.color">@color/bar_grey</item>
        <!--裁剪页面标题文字颜色-->
        <item name="picture.crop.title.color">@color/white</item>
        <!--相册文件夹列表选中图标-->
        <item name="picture.folder_checked_dot">@drawable/orange_oval</item>
    </style>

```

## 常用功能

******启动相册并拍照******       
```
 PictureSelector.create(MainActivity.this)
       .openGallery(PictureMimeType.ofImage())
       .forResult(PictureConfig.CHOOSE_REQUEST);
       
```
******单独启动拍照或视频 根据PictureMimeType自动识别******       
```
  PictureSelector.create(MainActivity.this)
       .openCamera(PictureMimeType.ofImage())
       .forResult(PictureConfig.CHOOSE_REQUEST);
```
******预览图片******       
```
// 预览图片 可自定长按保存路径
*注意 .themeStyle(themeId)；不可少，否则闪退...

PictureSelector.create(MainActivity.this).themeStyle(themeId).openExternalPreview(position, "/custom_file", selectList);
PictureSelector.create(MainActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);

```
******预览视频****** 
```
PictureSelector.create(MainActivity.this).externalPictureVideo(video_path);
 
```
## 结果回调
```
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
    
```


   
