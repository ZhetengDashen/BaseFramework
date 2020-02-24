package com.baseeasy.commonlibrary.weight.dialog.multipledialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baseeasy.commonlibrary.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：WangZhiQiang
 * 时间：2019/9/4
 * 邮箱：sos181@163.com
 * 描述：Dialog 多选择列表Dialog
 */
public class SelectMultipleListDialog extends Dialog {




    public SelectMultipleListDialog(Context context) {
        super(context);
    }
    public SelectMultipleListDialog(Context context, int theme) {
        super(context, theme);
    }

    public static <T extends MuCheck> SelectMultipleListDialog createDialog(Context context, String title,List<T> list, SelectCallback<T> screeningCallback) {
        SelectMultipleListAdapter selectActionListAdapter=new SelectMultipleListAdapter(list);
        SelectMultipleListDialog dialog;
        TextView tv_cancel;
        TextView tv_ok;
        TextView tv_title;
        RecyclerView recyclerView;
        dialog = new SelectMultipleListDialog(context, R.style.ScreeningDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // must be called before set content
        View dg_view = (View) LayoutInflater.from(context)
                .inflate(R.layout.dialog_select_multiple_dialog, null);
        dialog.setContentView(dg_view);
        dialog.setCanceledOnTouchOutside(true);
        tv_title=dg_view.findViewById(R.id.customdialog_title);
        tv_title.setText(title);
        tv_cancel=dg_view.findViewById(R.id.customdialog_left);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_ok=dg_view.findViewById(R.id.customdialog_right);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<T> tList=new ArrayList<>();
                for (int i = 0; i <list.size() ; i++) {
                    if(list.get(i).getMuCheck()){
                        tList.add(list.get(i));
                    }
                }
                screeningCallback.callback(tList);
                dialog.dismiss();
            }
        });
        recyclerView=dg_view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setAdapter(selectActionListAdapter);
        selectActionListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    CheckBox checkBox= (CheckBox) view;
                    list.get(position).setMuCheck(checkBox.isChecked());
                    adapter.notifyDataSetChanged();

            }
        });
        // 设置宽度为屏宽、靠近屏幕底部。
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.BOTTOM;
//        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(wlp);

        return dialog;
    }

    public  interface  SelectCallback<T>{
      void callback(List<T> list);

    };
    public  interface  MuCheck{

        boolean getMuCheck();
        void  setMuCheck(boolean check);
        String  getMuCheckName();
    };

}