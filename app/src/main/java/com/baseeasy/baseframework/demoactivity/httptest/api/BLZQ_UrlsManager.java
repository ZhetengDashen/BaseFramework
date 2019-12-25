package com.baseeasy.baseframework.demoactivity.httptest.api;




/**
 * 作者：WangZhiQiang
 * 时间：2019/10/29
 * 邮箱：sos181@163.com
 * 描述：巴林左旗API URL
 */
public class BLZQ_UrlsManager extends  BaseUrlTemplate {
//private String BASE_URL = "http://192.168.6.134/appdb_nm/";
//private String BASE_URL="http://116.113.102.51/appdb/";
   private   String BASE_URL = "http://192.168.6.118:9182/appdb/";

    private   String LOGIN=BASE_URL+"paperLessApplication.do?action=login";

    private   String  LIST_DATA=BASE_URL+"paperLessApplication.do?action=list";

    private   String  ACTION_NEXT=BASE_URL+"paperLessApplication.do?action=next";

    private   String  ACTION_BACK=BASE_URL+"paperLessApplication.do?action=back";

    private   String  ACTION_ACCEPT=BASE_URL+"paperLessApplication.do?action=accept";

    private   String  UP_IMAGE=BASE_URL+"paperLessApplication.do?action=upAd";

    private   String  LIST_DELIBERATE_NAME=BASE_URL+"paperLessApplication.do?action=reViewAccout";

    private   String  COMMIT_DELIBERATE=BASE_URL+"paperLessApplication.do?action=initiate";
    private   String  ACTION_CSB=BASE_URL+"paperLessApply.do?action=surveyAd";

    private   String  HEDUI=BASE_URL+"paperLessApplication.do?action=hedui";
    private   String  DOWN_CSB=BASE_URL+"paperLessApplication.do?action=formDown";
    @Override
    public String baseUrl() {
        return BASE_URL;
    }

    @Override
    public String login() {
        return LOGIN;
    }

    @Override
    public String listData() {
        return LIST_DATA;
    }

    @Override
    public String actionNext() {
        return ACTION_NEXT;
    }

    @Override
    public String actionBack() {
        return ACTION_BACK;
    }

    @Override
    public String actionAccept() {
        return ACTION_ACCEPT;
    }

    @Override
    public String deliberateNames() {
        return LIST_DELIBERATE_NAME;
    }

    @Override
    public String commitDeliberate() {
        return COMMIT_DELIBERATE;
    }

    @Override
    public String upImage() {
        return UP_IMAGE;
    }

    @Override
    public String actionCsb() {
        return ACTION_CSB;
    }

    @Override
    public String hedui() {
        return HEDUI;
    }

    @Override
    public String downCsb() {
        return DOWN_CSB;
    }
}
