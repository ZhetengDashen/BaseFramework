package com.baseeasy.baseframework.demoactivity;

import com.baseeasy.commonlibrary.weight.dialog.multipledialog.SelectMultipleListDialog;

/**
 * 作者：WangZhiQiang
 * 时间：2020/2/24
 * 邮箱：sos181@163.com
 * 描述：
 */
public class MuTestBean implements SelectMultipleListDialog.MuCheck {
   String  name="";
   boolean isCheck;

    public MuTestBean(String name, boolean isCheck) {
        this.name = name;
        this.isCheck = isCheck;
    }

    @Override
    public boolean getMuCheck() {
        return isCheck;
    }

    @Override
    public void setMuCheck(boolean check) {
         this.isCheck=check;
    }

    @Override
    public String getMuCheckName() {
        return name;
    }
}
