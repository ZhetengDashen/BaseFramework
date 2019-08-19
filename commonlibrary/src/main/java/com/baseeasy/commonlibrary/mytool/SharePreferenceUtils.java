package com.baseeasy.commonlibrary.mytool;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：WangZhiQiang
 * 时间：2019/8/14
 * 邮箱：sos181@163.com
 * 描述：
 */
public class SharePreferenceUtils {

    private  static SharedPreferences sp=null;
    private  static String sharePreferncesName="SP_SETTING";


    public static void  setValue(Context context, String key, Object valve){
        if(sp==null){
            sp=context.getSharedPreferences(sharePreferncesName,context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor =sp.edit();
        if(valve instanceof String){
         editor.putString(key,valve+"").commit();
        }else if(valve instanceof Boolean){
            editor.putBoolean(key,(Boolean) valve);
        }else if(valve instanceof Float){
           editor.putFloat(key,(Float) valve);
        }else if (valve instanceof Integer){
            editor.putInt(key,(int) valve);
        }else if (valve instanceof Long){
            editor.putLong(key,(Long)valve);
        }
    }
    public static Boolean getBoolean(Context context, String key){
        if(sp==null){
            sp=context.getSharedPreferences(sharePreferncesName, Context.MODE_PRIVATE);
        }
        return  sp.getBoolean(key,false);
  }

    public static String getString(Context context, String key){
        if(sp==null){
            sp=context.getSharedPreferences(sharePreferncesName, Context.MODE_PRIVATE);
        }
        return  sp.getString(key,"");
    }

    public static Float getFloat(Context context, String key){
        if(sp==null){
            sp=context.getSharedPreferences(sharePreferncesName, Context.MODE_PRIVATE);
        }
        return  sp.getFloat(key,0f);
    }
    public static int getInt(Context context, String key){
        if(sp==null){
            sp=context.getSharedPreferences(sharePreferncesName, Context.MODE_PRIVATE);
        }
        return  sp.getInt(key,0);
    }

    public static Long getLong(Context context, String key){
        if(sp==null){
            sp=context.getSharedPreferences(sharePreferncesName, Context.MODE_PRIVATE);
        }
        return  sp.getLong(key,0);
    }

    public static  void  deleteShare(){
         sp.edit().clear().commit();

    }



}
