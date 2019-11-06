# MyFingerprintUtils使用说明与注意事项


## 使用说明  
#### 1.打开指纹录入设备
  ``` 
    MyFingerprintUtils.getInstance(this).fingerprintOpen(new MyFingerprintUtils.OnFingerprintCallBack() {
                    @Override
                    public void callBack(int code, String msg, String data, String signatureTag) {

                        if (code == 1) {
                            //打开成功
                            content.setText(msg);
                        }

                    }
                });
  ``` 
#### 2.获取指纹图片
  ``` 
 MyFingerprintUtils.getInstance(FingerprintActivity.this).fingerprintImage(new MyFingerprintUtils.OnFingerprintCallBack() {
                    @Override
                    public void callBack(int code, String msg, String data, String signatureTag) {
                        switch (code) {
                            case -1:
                                //录入失败
                                content.setText(msg);
                                break;
                            case 0:
                                //等待录入
                                content.setText(msg + ":" + data);
                                break;
                            case 1:
                                //录入成功
                                content.setText(data);
                                Glide.with(FingerprintActivity.this).load(data).into(image);
                                break;

                        }
                    }
                });
 ``` 
### 注意事项
>获取指纹图片前必须调用 fingerprintOpen 方法打开设备
>图片路径：/storage/emulated/0/(APP名称)/fingerprint 


