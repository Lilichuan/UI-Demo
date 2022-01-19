package com.example.luffy.ui_demo.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {


    public static void checkHavePermission(Activity context ,String[] permission, int requestCode){
        for (String s : permission){
            if(!havePermission(context, s)){
                ActivityCompat.requestPermissions(context,
                        permission,
                        requestCode);
                return ;
            }
        }

        return;
    }

    /*
     *
     * 判斷是否有權限
     *
     * @param permission
     * 以Manifest.permission帶入參數
     *
     * @return boolean
     * 是否有權限
     * */
    public static boolean havePermission(Context context, String permission){
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 是否顯示提醒訊息
     * @return
     */
    public static boolean needToShowRationale(Activity activity, String[] permissions){
        for (String permissionName : permissions){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionName)){
                return true;
            }
        }

        return false;
    }
}
