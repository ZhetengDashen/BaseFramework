# LogUtils使用说明与注意事项
> #### github地址：
> https://github.com/pengwei1024/LogUtils



#### 配置与注意事项：
##### gradle 配置：
  ```
   implementation 'com.apkfuns.logutils:library:1.7.5'
   implementation 'com.apkfuns.log2file:log2file:1.3.0'
  ```

## 使用说明  
  ``` 
 // 输出字符串
LogUtils.d("12345");

// 输出参数
LogUtils.d("12%s3%d45", "a", 0);

// 输出异常
LogUtils.d(new NullPointerException("12345"));

// 输出对象
Person person = new Person();
person.setAge(11);
person.setName("pengwei");
person.setScore(37.5f);
LogUtils.d(person);

// 对象为空
LogUtils.d(null);

// 输出json（json默认debug打印）
String json = "{'a':'b','c':{'aa':234,'dd':{'az':12}}}";
LogUtils.json(json);

// 打印数据集合
List<Person> list1 = new ArrayList<>();
for(int i = 0; i < 4; i++){
    list1.add(person);
}
LogUtils.d(list1);

// 打印数组
double[][] doubles = {{1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33}};
LogUtils.d(doubles);

// 自定义tag
LogUtils.tag("我是自定义tag").d("我是打印内容");

// 其他用法
LogUtils.v("12345");
LogUtils.i("12345");
LogUtils.w("12345");
LogUtils.e("12345");
LogUtils.wtf("12345");
  ``` 
### 注意事项
>LogUtils已经在BaseApplication中初始化。APP请继承BaseApplication
>日志路径：/storage/emulated/0/(APP名称)/log


