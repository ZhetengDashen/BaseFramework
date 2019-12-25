package com.baseeasy.baseframework.demoactivity.httptest.api;


/**
 * 作者：WangZhiQiang
 * 时间：2019/10/29
 * 邮箱：sos181@163.com
 * 描述：API 调用类
 */
public class ApiUrls {
    private static BaseUrlTemplate urlsManager;
    private static Class<? extends BaseUrlTemplate> urlClass;
    static {
        //切换接口
      urlClass=BLZQ_UrlsManager.class;
//       urlClass=GXUrlsManager.class;
        try {
            urlsManager= (BaseUrlTemplate) Class.forName(urlClass.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String BASE_URL=urlsManager.baseUrl();

    public static String LOGIN=urlsManager.login();

    public  static String LIST=urlsManager.listData();

    public  static String ACTION_NEXT=urlsManager.actionNext();

    public  static  String ACTION_BACK=urlsManager.actionBack();

    public  static  String ACTION_ACCEPT=urlsManager.actionAccept();

    public static String UP_IMAGE=urlsManager.upImage();

    public  static  String LIST_DELIBERATE_NAME=urlsManager.deliberateNames();

    public  static  String COMMIT_DELIBERATE=urlsManager.commitDeliberate();

    public static String  HEDUI=urlsManager.hedui();

    public  static  String ACTION_CS=urlsManager.actionCsb();

    public  static  String DOWN_CS=urlsManager.downCsb();

}
