# Databinding使用说明与注意事项

#### 配置与注意事项：
##### gradle 配置：

  ```
  android {
        ......
       dataBinding {
            enabled = true
        }
    }
  ```

## 使用说明  

     1.在写好的布局中，光标移动到根布局，按住Alt+Enter 选择Convert to data binding layout 自动进行代码生成
     样式：
     <layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
        <data>
        </data>
        <自己的布局/>
       </layout>
    2.补充完善xml绑定的信息 
       <data class="自己起的名字">
        <import type="bean的全路径" />
        <variable
            name="mybean"//绑定的bean别名
            type="bean的名字" />
        <variable
            name="databindevent"//点击事件别名
            type="点击事件全路径（可以为activity的内部类）" />
       </data>
    3.在Activity中
        绑定布局
        dataBinding = DataBindingUtil.setContentView(this, R.layout.***);
        绑定bean
        dataBinding.setMybean(mBean);
        绑定点击事件
        dataBinding.setDatabindevent(new DataBinDingEvent());
    4.在布局中设置详细数据
            <TextView
                        android:layout_width="0dp"
                        android:layout_height="37dp"
                        android:layout_weight="1"
                        android:hint="请选择"
                        android:onClick="@{databindevent::点击事件名字}"
                        android:text="@={mybean.bean中对应字段}"
                        android:textSize="16sp" />
    5.注意事项
      将要绑定的bean要继承 BaseObservable ，在set数据完成后要调用 notifyChange();方法通知更新数据。