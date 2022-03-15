package com.example.luffy.ui_demo.util;

import androidx.annotation.StringRes;

public interface PermissionUtilListener {

    @StringRes
    int getPermissionExplainRes(String permission);

    void startFunction();
}
