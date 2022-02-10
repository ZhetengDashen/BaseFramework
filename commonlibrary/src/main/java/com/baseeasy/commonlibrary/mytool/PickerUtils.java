package com.baseeasy.commonlibrary.mytool;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

//import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageUtils;
//import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
//import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
//import com.bigkoo.pickerview.view.OptionsPickerView;

import androidx.annotation.NonNull;

import com.github.gzuliyujiang.wheelpicker.LinkagePicker;
import com.github.gzuliyujiang.wheelpicker.OptionPicker;
import com.github.gzuliyujiang.wheelpicker.contract.LinkageProvider;
import com.github.gzuliyujiang.wheelpicker.contract.OnLinkagePickedListener;
import com.github.gzuliyujiang.wheelpicker.contract.OnOptionPickedListener;

import org.jetbrains.annotations.NotNull;

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
        picker.getWheelView().setItemSpace(100);
        picker.setBodyHeight(200);
        picker.getWheelView().setCurvedMaxAngle(30);
        picker.getTitleView().setText("");
        picker.show();



    }
    public interface OnSelectOptionsListener {
         void onSelectOptionsListener(int options1);

        }
    public interface OnSelectThirdOptionsListener {
        void onSelectThirdOptionsListener(Object first,int options1, Object second,int options2, Object third,int options3);

    }
    /**
     * @author：Mr.Zan
     * @date： 2022/2/10 11:28
     * email：644962006@qq.com
     * detail：三级联动选择
     * 用法:
     *              PickerUtils pickerUtils =new PickerUtils();
     *                 List<SexBean.WORK> first = Arrays.asList(SexBean.WORK.values());
     *
     *                 ArrayList<Object> second = new ArrayList<>();
     *                 for (int i = 0; i < 3; i++) {
     *                     second.add("二级"+i);
     *                 }
     *                 ArrayList<Object> third = new ArrayList<>();
     *                 for (int i = 0; i < 3; i++) {
     *                     third.add("三级"+i);
     *                 }
     *                 pickerUtils.showThirdPicker(this, first, null, null, new PickerUtils.OnSelectThirdOptionsListener() {
     *                     @Override
     *                     public void onSelectThirdOptionsListener(Object first, int options1, Object second, int options2, Object third, int options3) {
     *                     }
     *                 });
     * @param context
     * @param firstDatas 一级数据
     * @param secondDatas 二级数据
     * @param thirdDatas 三级数据
     */
    public void   showThirdPicker(Context context, List<?> firstDatas, List<?> secondDatas, List<?> thirdDatas,OnSelectThirdOptionsListener onSelectThirdOptionsListener ){
        showThirdPicker(context,firstDatas,secondDatas,thirdDatas,0,0,0,onSelectThirdOptionsListener);
    }
    public void   showThirdPicker(Context context, List<?> firstDatas, List<?> secondDatas, List<?> thirdDatas,int firstDefaultPos,int secondDefaultPos,int thirdDefaultPos,OnSelectThirdOptionsListener onSelectThirdOptionsListener ){

        LinkagePicker picker = new LinkagePicker((Activity) context);
        LinkageProvider provider =new LinkageProvider() {
            @Override
            public boolean firstLevelVisible() {
                return null!=firstDatas;
            }

            @Override
            public boolean thirdLevelVisible() {
                return null!=thirdDatas;
            }

            @NonNull
            @NotNull
            @Override
            public List<?> provideFirstData() {
                return firstDatas;
            }

            @NonNull
            @NotNull
            @Override
            public List<?> linkageSecondData(int firstIndex) {
                return secondDatas;
            }

            @NonNull
            @NotNull
            @Override
            public List<?> linkageThirdData(int firstIndex, int secondIndex) {
                return thirdDatas;
            }

            @Override
            public int findFirstIndex(Object firstValue) {
                return firstDefaultPos;
            }

            @Override
            public int findSecondIndex(int firstIndex, Object secondValue) {
                return secondDefaultPos;
            }

            @Override
            public int findThirdIndex(int firstIndex, int secondIndex, Object thirdValue) {
                return thirdDefaultPos;
            }
        };
        picker.setData(provider);
        picker.setOnLinkagePickedListener(new OnLinkagePickedListener() {
            @Override
            public void onLinkagePicked(Object first, Object second, Object third) {
                if (null!=onSelectThirdOptionsListener){
                    int pos1=-1;
                    int pos2=-1;
                    int pos3=-1;
                    if (null != firstDatas){
                        for (int i = 0; i < firstDatas.size(); i++) {
                            if (firstDatas.get(i).equals(first)){
                                pos1=i;
                            }
                        }
                    }
                    if (null != secondDatas){
                        for (int i = 0; i < secondDatas.size(); i++) {
                            if (secondDatas.get(i).equals(second)){
                                pos2=i;
                            }
                        }
                    }
                    if (null != thirdDatas){
                        for (int i = 0; i < thirdDatas.size(); i++) {
                            if (thirdDatas.get(i).equals(third)){
                                pos3=i;
                            }
                        }
                    }
                    onSelectThirdOptionsListener.onSelectThirdOptionsListener(first,pos1,second,pos2,third,pos3);
                }
            }
        });

        picker.setBodyHeight(200);

        picker.getTitleView().setText("");
        picker.show();



    }
}
