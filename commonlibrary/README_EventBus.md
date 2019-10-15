# EventBus使用说明与注意事项

> #### EventBus地址：
>  https://github.com/greenrobot/EventBus

#### EventBus配置与注意事项：
##### gradle 配置：
  ```

 dependencies {
    implementation 'org.greenrobot:eventbus:3.1.1'
 } 
  ```
## 功能说明以及使用注意事项  
##### 1.使用注意事项  <br>
 1.eventBus的注册与注销都已经封装到BasePresenter中，所以如果使用EventBus必须继承P层必须继承BasePresenter<br>
 2.BasePresenter当中 isOpenEventBus的抽象方法，如果不需要Event功能直接返回false即可<br>  
##### 2.使用说明 <br>  
   1.发送事件：
 ```
  例：
    构建EventMessage对象
    EventMessage<TestUser> event = new EventMessage.Builder()
                        .setCode(EventConst.EVENT_CODE_OK)
                        .setFlag(ARouterPath.AppMode.MAIN_TEST)
                        .setEvent(new TestUser("王大锤子","23","女"))
                        .create();
                //发送普通事件
                EventBusUtils.post(event);      
 ```
  2.接收事件：
 ```  
  例：
  
    @Override
    public void eventBusMessageOnMainThread(EventMessage event) {
        super.eventBusMessageOnMainThread(event);
        switch (event.getFlag()){
            case  ARouterPath.AppMode.MAIN_TEST:
                TestUser testUser=   (TestUser)event.getEvent();
                Toast.makeText(this,testUser.getName() , Toast.LENGTH_SHORT).show();
            break;
        }

    }
    
  ------------------------------
  
   根据当前情况重写BasePresenter 中的方法  
     判断Flag是有哪个页面传过来的。
    // 在主线程处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMessageOnMainThread(EventMessage event) {     

    }

    // 在后台处理事件
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void eventBusMessageOnBackgroundThread(EventMessage event) {
      
    }

    // 在发送事件的同一线程中处理事件
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void eventBusMessageOnPostThread(EventMessage event) {
      

    }

    // 在主线程处理粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMessageOnMainStickyThread(EventMessage event) {
  
    }
  
 ```
   

 