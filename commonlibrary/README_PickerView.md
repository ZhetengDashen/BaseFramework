# PickerView使用说明与注意事项
> #### github地址：
>https://github.com/Bigkoo/Android-PickerView



#### 配置与注意事项：
##### gradle 配置：
  ```
    compile 'com.contrarywind:Android-PickerView:4.1.8'
  ```

## 使用说明  
  ``` 
 //时间选择器
TimePickerView pvTime = new TimePickerBuilder(MainActivity.this, new OnTimeSelectListener() {
                           @Override
                           public void onTimeSelect(Date date, View v) {
                               Toast.makeText(MainActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                           }
                       }).build();
                       
//条件选择器
 OptionsPickerView pvOptions = new OptionsPickerBuilder(MainActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                tvOptions.setText(tx);
            }
        }).build();
 pvOptions.setPicker(options1Items, options2Items, options3Items);
 pvOptions.show(); 

  ``` 
更多参考 https://github.com/Bigkoo/Android-PickerView
  
