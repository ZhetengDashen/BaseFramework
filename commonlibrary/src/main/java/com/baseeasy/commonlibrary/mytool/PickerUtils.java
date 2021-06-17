package com.baseeasy.commonlibrary.mytool;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

//import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageUtils;
//import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
//import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
//import com.bigkoo.pickerview.view.OptionsPickerView;

import com.github.gzuliyujiang.wheelpicker.OptionPicker;
import com.github.gzuliyujiang.wheelpicker.contract.OnOptionPickedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * 作者：WangZhiQiang
 * 时间：2019/11/11
 * 邮箱：sos181@163.com
 * 描述：
 */
public class PickerUtils {

    public PickerUtils() {
    }

    public void   show(Context context, List<?> optionsItems, int selectOption, OnSelectOptionsListener onSelectOptionsListener){

//        OptionsPickerView optionsPickerView=new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                onSelectOptionsListener.onSelectOptionsListener(options1);
//            }
//        }).build();
//        optionsPickerView.setPicker(optionsItems);
//        optionsPickerView.setSelectOptions(selectOption);
//        optionsPickerView.show();



        OptionPicker picker = new OptionPicker((Activity) context);
        picker.setData(optionsItems);
        picker.setOnOptionPickedListener(new OnOptionPickedListener() {
            @Override
            public void onOptionPicked(int position, Object item) {
                if(null!=onSelectOptionsListener){
                    onSelectOptionsListener.onSelectOptionsListener(position);
                }
            }
        });
        picker.setDefaultPosition(selectOption);
        picker.getWheelView().setCurvedEnabled(true);
        picker.getWheelView().setCurvedMaxAngle(40);
        picker.getTitleView().setText("");
        picker.show();



    }
    public interface OnSelectOptionsListener {
         void onSelectOptionsListener(int options1);

        }

}
