package com.example.luffy.ui_demo.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by 阿銓 on 2017/3/28.
 * 呼叫裝置上的相機App幫本App拍照
 *
 */

public class PhotoUtil {

    /*
    *
    * @param mActivity
    * 取得拍照權限的流程
    *
    * @param requestPermissionCode
    * 自訂的權限請求ID
    * 於 OnActivityPermissionResult() 會傳回來
    *
    * @param requestCameraCode
    * 自訂的相機Activity ID
    * 於 OnActivityResult() 會傳回來
    *
    * @param pi
    * PermissionInterface
    * 調用者必須實做其行為
    *
    * */
    public static void checkTakePhotoPermission(Activity mActivity,
                                                int requestPermissionCode,
                                                int requestCameraCode ,
                                                PhotoUtilListener listener){

        if(!deviceHaveCamera(mActivity)){
            return;
        }

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            callCameraIntent(mActivity, requestCameraCode, listener);
            return;
        }

        if (!haveTakePhotoPermission(mActivity)) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.CAMERA)){

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                listener.showRequestPermissionRationale();

            } else {
                // No explanation needed, we can request the permission.
                queryTakePicPermission(mActivity, requestPermissionCode);
            }
        }else {
            callCameraIntent(mActivity, requestCameraCode, listener);
        }
    }

    public interface PhotoUtilListener {
        //需要透過UI對使用者說明為什麼要索取權限
        //要有禮貌。
        void showRequestPermissionRationale();

        //當檔案剛生成的時候會呼叫此。外部要藉此保存檔案與路徑。
        void OnFileCreate(File file, String path);
    }



    /*
    *
    * 是否擁有相機與寫入sdcard資料權限
    *
    * */
    public static boolean haveTakePhotoPermission(Context context){
        boolean havePermissionCamera = havePermission(context, Manifest.permission.CAMERA);
        boolean havePermissionStorage = havePermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return havePermissionCamera && havePermissionStorage;
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
    public static void queryTakePicPermission(Activity activity, int requestCode){
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                requestCode);
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

    /*
    *
    * 檢查裝置是否有相機硬體
    *
    * @param context
    * 就Context
    *
    * @return
    * 裝置是否有相機
    * */
    public static boolean deviceHaveCamera(Context context){
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /*
    *
    * 開啟裝置上的預設相機App，拜託他回傳一張照片給此App
    * 此函式會把照相成功的檔案放在裝置的圖片資料夾
    * 詳情參閱：
    * https://developer.android.com/training/camera/photobasics.html
    *
    * @param activity
    * 調用此函式的Activity
    *
    * @param request_code
    * 自訂的請求ID
    *
    * */
    public static void callCameraIntent(Activity activity, int request_code, PhotoUtilListener listener) {

        if(!haveTakePhotoPermission(activity)){
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //檢查有沒有 相機硬體 與 預設相機App 這兩樣東東。
        if (deviceHaveCamera(activity)
                && takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile(activity);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }

            if (photoFile != null) {
                listener.OnFileCreate(photoFile, photoFile.getAbsolutePath());

                //詳情參閱
                //https://developer.android.com/training/camera/photobasics.html
                Uri photoURI = FileProvider.getUriForFile(activity,
                        "fifit.today.appworkout.techsurvey.fileprovider",
                        photoFile);


                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    activity.grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

                if(takePictureIntent.resolveActivity(activity.getPackageManager()) == null){
                    return;
                }


                activity.startActivityForResult(takePictureIntent, request_code);
            }
        }

    }

    /*
    * 生成一個圖片File
    *
    * */
    private static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);//App 自身的資料夾
        File storageDir = context.getCacheDir();//App 自身的暫存資料夾
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//裝置共用的圖片資料夾
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /*
    *
    * 協助處理呼叫相機app之後
    * 回傳至onActivityResult的拍照結果縮圖
    *
    * */
    public static Uri thumbnailInResult(int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
//            Bundle extras = data.getExtras();
//            return (Bitmap) extras.get("data");
            return data.getData();
        }
        return null;
    }

    /*
    *
    * 把指定圖檔變成指定大小的Bitmap
    *
    * @param path
    * 指定的圖檔在sdcard的路徑
    *
    * @param targetW
    * 指定的高，建議等同ImageView的高。
    * 單位是pixel
    *
    * @param targetH
    * 指定的高，建議等同ImageView的高。
    * 單位是pixel
    *
    * */
    public static Bitmap picFileToBitmap(String path, int targetW, int targetH) {

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(path, bmOptions);
    }

    /*
    *
    * 把圖檔放到ImageView裡
    * 請確認傳入的ImageView已經有確定的長寬
    * 不然會在此發生錯誤
    *
    * @param picPath
    * 圖檔路徑
    *
    * @param imageView
    * 要放置的ImageView
    *
    * */
    public static void setPic(String picPath, ImageView imageView){
        if(TextUtils.isEmpty(picPath)){
            return;
        }
        int targetW = imageView.getMeasuredWidth();
        int targetH = imageView.getMeasuredHeight();
        Bitmap bitmap = picFileToBitmap(picPath, targetW, targetH);
        imageView.setImageBitmap(bitmap);
    }





    private static final String KEY_SAVE_PATH = "photo_utility_save_path";

    /*
    *
    * 把暫存於Bundle savedInstanceState的檔案路徑給取出
    *
    * @param takePicSuccess
    * 拍照是否成功
    *
    * */
    public static String getPicturePathFromBundle(Context context, Bundle savedInstanceState, boolean takePicSuccess){

        if(savedInstanceState != null){
            String path = savedInstanceState.getString(KEY_SAVE_PATH, "");

            if(!TextUtils.isEmpty(path) && !takePicSuccess){//拍過照但是拍照失敗
                //刪除這份新建立檔案
                deleteFile(context, path);
                return null;
            }
            return path;
        }

        return null;
    }

    //將檔案路徑暫存於Bundle之中
    public static void saveFilePathToBundle(Bundle outState, String path){
        if(!TextUtils.isEmpty(path)){
            outState.putString(KEY_SAVE_PATH, path);
        }
    }

    //刪除一個sd card上的圖檔
    public static void deleteFile(Context context, String path){
        if(TextUtils.isEmpty(path) || !havePermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            return;
        }

        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
    }
}
