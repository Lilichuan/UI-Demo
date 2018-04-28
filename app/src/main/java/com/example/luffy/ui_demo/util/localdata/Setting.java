package com.example.luffy.ui_demo.util.localdata;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用SharedPreferences存放設定值。
 * 存放之後，有利於重複操作與測試
 */

public class Setting {
    private SharedPreferences sp;
    private final String SP_NAME = "my_setting";

    public Setting(Context context){
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

}
