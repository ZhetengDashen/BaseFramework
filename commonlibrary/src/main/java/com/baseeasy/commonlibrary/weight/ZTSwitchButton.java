package com.baseeasy.commonlibrary.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;
import androidx.databinding.InverseMethod;
import androidx.databinding.adapters.ListenerUtil;
import androidx.databinding.adapters.TextViewBindingAdapter;

import com.baseeasy.commonlibrary.R;
import com.kyleduo.switchbutton.SwitchButton;

import java.lang.reflect.Method;

/**
 * 作者：WangZhiQiang
 * 时间：2020/3/24
 * 邮箱：sos181@163.com
 * 描述：开关按钮 暂时不支持DataBinDing双向绑定
 * 如果是在databinding 中使用必须设置属性ztswisdatabinding=  true  改变SwitchButton的状态只能通过数据源去改变
 *
 */

public class ZTSwitchButton extends FrameLayout {

    private TextView contentTextView;
    private SwitchButton switchButton;
    private String content = "";
    private Boolean checked = false;
    private Boolean isDataBinDing=false;//是否支持DataBinDing
    public ZTSwitchButton(Context context) {
        super(context);
        initView();
    }

    public ZTSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ztswitchbutton);
        initByAttributes(typedArray);
        initView();
        typedArray.recycle();
    }

    public ZTSwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ztswitchbutton);
        initByAttributes(typedArray);
        initView();
        typedArray.recycle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ZTSwitchButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ztswitchbutton);
        initByAttributes(typedArray);
        initView();
        typedArray.recycle();
    }

    protected void initByAttributes(TypedArray attributes) {
        content = attributes.getString(R.styleable.ztswitchbutton_ztswcontent);
        checked = attributes.getBoolean(R.styleable.ztswitchbutton_ztswchecked, false);
        isDataBinDing=attributes.getBoolean(R.styleable.ztswitchbutton_ztswisdatabinding, false);


    }

    public void initView() {
        View view = View.inflate(getContext(), R.layout.ztswitch_button_layout, this);
        contentTextView = view.findViewById(R.id.tv_ztsw_content);
        switchButton = view.findViewById(R.id.swbt);
        contentTextView.setText(content);
        switchButton.setChecked(checked);
        switchButton.setEnabled(!isDataBinDing);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                     checked=isChecked;
                }
            });

    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener){
        switchButton.setOnCheckedChangeListener(onCheckedChangeListener);
    };
    public TextView getContentTextView() {
        return contentTextView;
    }

    public SwitchButton getSwitchButton() {
        return switchButton;
    }


    public String getContent() {
        return contentTextView.getText().toString();
    }

    public void setContent(CharSequence content) {
        contentTextView.setText(content);
    }

    public Boolean getChecked() {
        return  checked;
    }

    public void setChecked(Boolean checked) {
        switchButton.setChecked(checked);
    }


    @BindingAdapter(value = "ztswcontent", requireAll = false)
    public static void setSwitchButtonContent(ZTSwitchButton ztSwitchButton, String content) {
        ztSwitchButton.setContent(content);
    }

    @BindingAdapter(value = "ztswchecked", requireAll = false)
    public static void setSwitchButtonChecked(ZTSwitchButton ztSwitchButton, boolean isChecked) {
        ztSwitchButton.setChecked(isChecked);
    }

    @BindingAdapter(value = "ztswisdatabinding", requireAll = false)
    public static void setSwitchButtDatabinding(ZTSwitchButton ztSwitchButton, boolean isEnabled) {
        ztSwitchButton.getSwitchButton().setEnabled(!isEnabled);
    }


//    @InverseBindingAdapter(attribute = "ztswcontent", event="contentAttrChanged")
//    public static String getSwitchButtonContent(ZTSwitchButton ztSwitchButton) {
//        return ztSwitchButton.getContent();
//    }
//
//     @BindingAdapter(value = {"contentAttrChanged"},requireAll = false)
//    public static void setContentAttrChanged(ZTSwitchButton ztSwitchButton, InverseBindingListener mInverseBindingListener){
//        if(mInverseBindingListener_content==null){
//            Log.e("kk","mInverseBindingListener_content--------null");
//        }else {
//            Log.e("kk","mInverseBindingListener_content--------");
//            mInverseBindingListener_content = mInverseBindingListener;
//
//        }
//
//    }
//
//
//
//        @BindingAdapter({"ztswchecked"})
//        public static void setSwitchButtonChecked(ZTSwitchButton ztSwitchButton, Boolean isChecked) {
//          ztSwitchButton.setChecked(isChecked);
//        }


}
