package com.baseeasy.commonlibrary.weight.dialog.actiondialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baseeasy.commonlibrary.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;


/**
 * 作者：WangZhiQiang
 * 时间：2019/9/4
 * 邮箱：sos181@163.com
 * 描述：Dialog 选择列表
 */
public class SelectActionListDialog extends Dialog {




    public SelectActionListDialog(Context context) {
        super(context);
    }
    public SelectActionListDialog(Context context, int theme) {
        super(context, theme);
    }

    public static <T> SelectActionListDialog createDialog(Context context,  List<T> list,ActionCallback<T> screeningCallback) {
        SelectActionListAdapter selectActionListAdapter=new SelectActionListAdapter(list);
        SelectActionListDialog dialog;
        TextView tv_cancel;
        RecyclerView recyclerView;
        dialog = new SelectActionListDialog(context, R.style.customdialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // must be called before set content
        View dg_view = (View) LayoutInflater.from(context)
                .inflate(R.layout.dialog_list_action, null);
        dialog.setContentView(dg_view);
        dialog.setCanceledOnTouchOutside(true);
        tv_cancel=dg_view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        recyclerView=dg_view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(selectActionListAdapter);
        selectActionListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.tv_content){
                    screeningCallback.callback(list.get(position));
                    dialog.dismiss();
                }
            }
        });
        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        return dialog;
    }

    public  interface  ActionCallback<T>{
      void callback(T action);

    };

}