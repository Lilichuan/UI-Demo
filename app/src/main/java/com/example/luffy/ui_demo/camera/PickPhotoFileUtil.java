package com.example.luffy.ui_demo.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 阿銓 on 2017/3/31.
 * 利用intent呼叫其他App來選擇圖片
 */

public class PickPhotoFileUtil {

    /*
    *
    * @param mActivity
    * 取得挑選照片權限的流程
    *
    * @param requestPermissionCode
    * 自訂的權限請求ID
    * 於 OnActivityPermissionResult() 會傳回來
    *
    * @param requestActivityCode
    * 自訂的Activity ID
    * 於 OnActivityResult() 會傳回來
    *
    * @param pi
    * PermissionInterface
    * 調用者必須實做其行為
    *
    * */
    public static void checkPermissionAndStart(Activity mActivity,
                                               int requestPermissionCode,
                                               int requestActivityCode ,
                                               OnPermissionRationaleListener listener){

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            pickImageFileIntent(mActivity, requestActivityCode);
            return;
        }

        if (!havePickPhotoPermission(mActivity)) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                listener.onShowRequestPermissionRationale();

            } else {
                // No explanation needed, we can request the permission.
                queryPickPicPermission(mActivity, requestPermissionCode);
            }
        }else {
            pickImageFileIntent(mActivity, requestActivityCode);
        }
    }

    public interface OnPermissionRationaleListener{
        void onShowRequestPermissionRationale();
    }

    /*
    *
    * 把圖片Uri放進ImageView中
    *
    * @param context
    *
    * @param uri
    *
    * @param imageView
    *
    * */
    public static void setPic(Context context, Uri uri, ImageView imageView){
        int targetW = imageView.getMeasuredWidth();
        int targetH = imageView.getMeasuredHeight();

        try {
            Bitmap bitmap = getBitmapFormUri(context, uri, targetW, targetH);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    *
    * 將圖片Uri轉換成指定寬高的Bitmap
    *
    * */
    private static Bitmap getBitmapFormUri(Context ac, Uri uri, int targetW, int targetH) throws IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, bmOptions);
        input.close();
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        //比例压缩
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bmOptions);
        input.close();

        return bitmap;//再进行质量压缩
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

    public static boolean havePickPhotoPermission(Context context){
        return havePermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    /*
    * 開始調用android api向使用者索取拍照權限
    *
    * @param activity
    * 當前Activity
    *
    * @param requestCode
    * 自訂的請求ID
    *
    * */
    public static void queryPickPicPermission(Activity activity, int requestCode){
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                requestCode);
    }

    /*
    *
    * 用Intent呼叫挑選圖片的功能
    *
    * */
    public static void pickImageFileIntent(Activity activity, int requestCode){

        if(!havePickPhotoPermission(activity)){
            return;
        }

        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        activity.startActivityForResult(intent, requestCode);
    }

    /*
    *
    * 在onActivityResult()中
    * 把uri取出的邏輯
    *
    * */
    public static Uri getUriFromBundleInActivityResult(int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK
                && data != null){
            return data.getData();
        }

        return null;
    }


    //存放Uri用的key值
    private static final String KEY_PIC_URI = "picture_uri";

    /*
    *
    * 把uri暫存進Activity 的 Bundle裡面
    *
    * */
    public static void saveUriToBundle(Bundle bundle, Uri uri){
        if(uri == null){
            return;
        }

        bundle.putString(KEY_PIC_URI, uri.toString());
    }

    /*
    *
    * 把暫存進Activity的uri給取出
    * 如果沒有，會安全回傳null。
    *
    * */
    public static Uri getUriFromBundle(Bundle bundle){
        if(bundle == null){
            return null;
        }

        String uri_str = bundle.getString(KEY_PIC_URI, "");
        if(TextUtils.isEmpty(uri_str)){
            return null;
        }

        return Uri.parse(uri_str);
    }

}
