package com.example.luffy.ui_demo.localdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by tim on 2017/4/24.
 *
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
