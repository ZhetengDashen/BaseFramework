package com.baseeasy.commonlibrary.arouter;

/**
 * 作者：WangZhiQiang
 * 时间：2019/3/13
 * 邮箱：sos181@163.com
 * 描述：各个Activity路由跳转路径，每个静态内部类为mode名称，如果新建了mode请新建一个静态内部类。
 * 注： 路径名称规范:"/mode名称/activity名称"
 * 不同module的一级路径必须不同，否则会导致一个moudle中的一级路径失效！！！
 */
public class ARouterPath {


    public static class  AppMode{
        public static  final String MAIN_TEST="/app/MainTestActivity";
        public static  final String MAIN_ACTIVITY="/app/MainActivity";
    }

    public static  class  TestMode{
        public static final String TEST_ACTIVITY="/mytestmode/testActivity";

    }

    public static class  TestMode2{
        public static  final String MAIN_TEST="/mytestmode2/TestMode2Activity";

    }
}
