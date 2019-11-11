package com.baseeasy.commonlibrary.mytool;

import android.content.Context;
import android.view.View;

import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.Arrays;
import java.util.List;

/**
 * 作者：WangZhiQiang
 * 时间：2019/11/11
 * 邮箱：sos181@163.com
 * 描述：
 */
public class PickerUtils<T> {
    public volatile static PickerUtils pickerUtils;

    public static PickerUtils getInstance() {
        if (pickerUtils == null) {
            synchronized (PickerUtils.class) {
                if (pickerUtils == null) {
                    pickerUtils = new PickerUtils();

                }
            }
        }
        return pickerUtils;
    }
    public void   show(Context context, List<T> optionsItems,int selectOption,OnSelectOptionsListener onSelectOptionsListener){

        OptionsPickerView optionsPickerView=new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                onSelectOptionsListener.onSelectOptionsListener(options1);
            }
        }).build();
        optionsPickerView.setPicker(optionsItems);
        optionsPickerView.setSelectOptions(selectOption);
        optionsPickerView.show();

    }
    public interface OnSelectOptionsListener {
         void onSelectOptionsListener(int options1);

        }

}
