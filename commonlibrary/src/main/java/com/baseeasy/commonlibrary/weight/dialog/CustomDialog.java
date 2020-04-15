package com.baseeasy.commonlibrary.weight.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseeasy.commonlibrary.R;


/**
 * 作者：MagicLon
 * 时间：2018/4/16 016
 * 邮箱：1348149485@qq.com
 * 描述：
 */
public class CustomDialog {
    private Context context;
    private String title;
    private String content;
    private String left;
    private String right;
    private String midstr;
    private String single;
    private boolean isSingle = false;
    private boolean isDouble = true;
    private OnCustomLeftClickLister oncustomleftclicklister;
    private OnCustomRightClickLister oncustomrightclicklister;
    private OnCustomMidClickLister oncustommidclicklister;
    private OnCustomSingleClickLister oncustomsingleclicklister;
    private Dialog dialog;
    private TextView tv_title, tv_content, tv_left, tv_right, tv_middle, tv_sure;
    private LinearLayout ll_dialog_two;


    public CustomDialog(DialogBuilder builder,Boolean isShow) {
        initBuilder(builder);
        showDialog(isShow);
    }
    public CustomDialog(DialogBuilder builder) {
        initBuilder(builder);
        showDialog(true);
    }
    private void initBuilder(DialogBuilder builder){
        this.context = builder.context;
        this.title = builder.title;
        this.content = builder.content;
        this.left = builder.left;
        this.right = builder.right;
        this.midstr = builder.midstr;
        this.single = builder.single;
        this.isSingle = builder.isSingle;
        this.isDouble = builder.isDouble;
        this.oncustomleftclicklister = builder.onCustomLeftClickLister;
        this.oncustomrightclicklister = builder.onCustomRightClickLister;
        this.oncustommidclicklister = builder.onCustomMidClickLister;
        this.oncustomsingleclicklister = builder.onCustomSingleClickLister;
    }

    private void initDialog(){
        View dialogv = LayoutInflater.from(context).inflate(R.layout.dialog_customdialog, null);
        dialog = new Dialog(context, R.style.customdialog);
        dialog.setContentView(dialogv);
        tv_title = dialogv.findViewById(R.id.customdialog_title);
        tv_content = dialogv.findViewById(R.id.customdialog_content);
        if (title != null && !"".equals(title)) {
            tv_title.setText(title);
        }
        if (content != null && !"".equals(content)) {
            tv_content.setText(content);
        }
        if (isSingle) {
            tv_sure = dialogv.findViewById(R.id.customdialog_sure);
            tv_sure.setVisibility(View.VISIBLE);
            if (midstr != null && !"".equals(midstr)) {
                tv_sure.setText(midstr);
            }
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (oncustomsingleclicklister != null) {
                        oncustomsingleclicklister.onSingleClicked();
                    }
                }
            });
        } else {
            tv_left = dialogv.findViewById(R.id.customdialog_left);
            tv_right = dialogv.findViewById(R.id.customdialog_right);
            ll_dialog_two = dialogv.findViewById(R.id.ll_dialog_two);
            ll_dialog_two.setVisibility(View.VISIBLE);
            if (left != null && !"".equals(left)) {
                tv_left.setText(left);
            }
            if (right != null && !"".equals(right)) {
                tv_right.setText(right);
            }
            if (!isDouble) {
                tv_middle = dialogv.findViewById(R.id.customdialog_middle);
                tv_middle.setVisibility(View.VISIBLE);
                if (midstr != null && !"".equals(midstr)) {
                    tv_middle.setText(midstr);
                }
                tv_middle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        if (oncustommidclicklister != null) {
                            oncustommidclicklister.onMiddleClicked();
                        }
                    }
                });
            }
            tv_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (oncustomleftclicklister != null) {
                        oncustomleftclicklister.onLeftClicked();
                    }
                }
            });
            tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (oncustomrightclicklister != null) {
                        oncustomrightclicklister.onRightClicked();
                    }
                }
            });
        }

    }
    private void showDialog(Boolean isShow) {
        initDialog();
        if(isShow){
            dialog.show();
        }


    }

    /**
     * 显示dialog
     */
    public void show() {
        if (dialog != null&&!dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 隐藏dialog
     */
    public void dimiss() {
        if (dialog != null&&dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 判断是否显示
     * @return
     */
    public boolean isShow(){
        if (dialog != null&&dialog.isShowing()) {
            return true;
        }
        return false;
    }

    public static class DialogBuilder {
        protected String title;
        protected String content;
        protected String left;
        protected String right;
        protected String midstr;
        protected String single;
        protected Context context;
        protected boolean isSingle = false;
        protected boolean isDouble = true;
        protected OnCustomLeftClickLister onCustomLeftClickLister;
        protected OnCustomRightClickLister onCustomRightClickLister;
        protected OnCustomMidClickLister onCustomMidClickLister;
        protected OnCustomSingleClickLister onCustomSingleClickLister;

        public DialogBuilder(Context context) {
            this.context = context;
        }

        /**
         * 创建builde对象
         *
         * @param context
         * @return
         */
        public static DialogBuilder with(Context context) {
            return new DialogBuilder(context);
        }

        /**
         * 提示的标题
         *
         * @param title
         * @return
         */
        public DialogBuilder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * 提示的内容
         *
         * @param content
         * @return
         */
        public DialogBuilder content(String content) {
            this.content = content;
            return this;
        }

        /**
         * 左按钮显示的字
         *
         * @param left
         * @return
         */
        public DialogBuilder left(String left) {
            this.left = left;
            return this;
        }

        /**
         * 右按钮显示的字
         *
         * @param right
         * @return
         */
        public DialogBuilder right(String right) {
            this.right = right;
            return this;
        }

        /**
         * 单按钮显示的字
         *
         * @param middle
         * @return
         */
        public DialogBuilder middle(String middle) {
            this.midstr = middle;
            return this;
        }

        public DialogBuilder single(String single) {
            this.single = single;
            return this;
        }

        /**
         * 是否为单按钮模式显示 true 则为只显示一个按钮，false则为显示左右两个按钮模式,或三个按钮模式
         *
         * @param isSingle
         * @return
         */
        public DialogBuilder singleMode(boolean isSingle) {
            this.isSingle = isSingle;
            return this;
        }

        /**
         * 是否为双按钮模式显示 true 则为只显示两个按钮，false则为显示三个按钮模 式
         *
         * @param isDouble
         * @return
         */
        public DialogBuilder doubleMode(boolean isDouble) {
            this.isDouble = isDouble;
            return this;
        }

        /**
         * 左按钮的监听
         *
         * @param onCustomLeftClickLister
         * @return
         */
        public DialogBuilder leftLister(OnCustomLeftClickLister onCustomLeftClickLister) {
            this.onCustomLeftClickLister = onCustomLeftClickLister;
            return this;
        }

        /**
         * 右按钮的监听
         *
         * @param onCustomRightClickLister
         * @return
         */
        public DialogBuilder rightLister(OnCustomRightClickLister onCustomRightClickLister) {
            this.onCustomRightClickLister = onCustomRightClickLister;
            return this;
        }

        /**
         * 中间按钮的监听
         *
         * @param onCustomMidClickLister
         * @return
         */
        public DialogBuilder middleLister(OnCustomMidClickLister onCustomMidClickLister) {
            this.onCustomMidClickLister = onCustomMidClickLister;
            return this;
        }

        /**
         * 单按钮的监听
         *
         * @param onCustomSingleClickLister
         * @return
         */
        public DialogBuilder singleLister(OnCustomSingleClickLister onCustomSingleClickLister) {
            this.onCustomSingleClickLister = onCustomSingleClickLister;
            return this;
        }

        /**
         * 创建dialog
         *
         * @return
         */
        public CustomDialog build() {
            return new CustomDialog(this);
        }
    }
}
