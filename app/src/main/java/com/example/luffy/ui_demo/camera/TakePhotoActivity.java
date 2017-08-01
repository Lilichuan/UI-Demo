package com.example.luffy.ui_demo.camera;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.example.luffy.ui_demo.R;

import java.io.File;

public class TakePhotoActivity extends FragmentActivity {

    //保管照片路徑
    private String file_path;

    //已選擇的圖片Uri
    private Uri uri;

    //Flag，標記是否成功OnActivityResult的結果。
    private boolean activityResult = false;

    private final int REQUEST_TAKE_PHOTO_PERMISSION_CODE = 1;//請求拍照權限的自訂id
    private final int REQUEST_CHOOSE_PHOTO_PERMISSION_CODE = 2;//請求選擇圖片權限的自訂id

    private final int INTENT_TAKE_PIC = 3;//onActivityResult的requestCode  拍照
    private final int INTENT_CHOOSE_PIC = 4;//onActivityResult的requestCode 選擇圖片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        file_path = PhotoUtil.getPicturePathFromBundle(this, savedInstanceState, activityResult);
        uri = PickPhotoFileUtil.getUriFromBundle(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initFragment();
    }

    private void initFragment(){
        TakePhotoFragment fragment;
        if(uri != null){
            fragment = TakePhotoFragment.newInstance(uri);
        }else {
            fragment = TakePhotoFragment.newInstance(file_path);
        }

        fragment.setTakePictureListener(new TakePhotoFragment.TakePictureListener() {
            @Override
            public void onTakePicture() {
                startTakePicture();
            }

            @Override
            public void onPickPicture() {
                startPickPhoto();
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.take_photo_fragment_container, fragment)
                .commit();
    }

    //使用者啟動拍照功能
    private void startTakePicture(){

        PhotoUtil.checkTakePhotoPermission(this,
                REQUEST_TAKE_PHOTO_PERMISSION_CODE,
                INTENT_TAKE_PIC ,
                photoUtilListener);

    }

    private PhotoUtil.PhotoUtilListener photoUtilListener = new PhotoUtil.PhotoUtilListener() {
        @Override
        public void showRequestPermissionRationale() {
            showCameraExplainDialog();
        }

        @Override
        public void OnFileCreate(File file, String path) {
            file_path = path;
        }
    };

    //使用者啟動選擇照片功能
    private void startPickPhoto(){
        PickPhotoFileUtil.checkPermissionAndStart(this,
                REQUEST_CHOOSE_PHOTO_PERMISSION_CODE,
                INTENT_CHOOSE_PIC,
                new PickPhotoFileUtil.OnPermissionRationaleListener() {
                    @Override
                    public void onShowRequestPermissionRationale() {
                        showPickPhotoExplainDialog();
                    }
                });
    }

    // 呈現Dialog
    // 對使用者說明挑選照片為什麼需要權限的Dialog
    private void showPickPhotoExplainDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.choose_pic_permission_explain)
                .setNeutralButton(R.string.got_it, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PickPhotoFileUtil.queryPickPicPermission(TakePhotoActivity.this, REQUEST_CHOOSE_PHOTO_PERMISSION_CODE);
                    }
                }).show();
    }

    // 呈現Dialog
    // 對使用者說明拍照為什麼需要權限
    private void showCameraExplainDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.take_pic_permission_explain)
                .setNeutralButton(R.string.got_it, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PhotoUtil.queryTakePicPermission(TakePhotoActivity.this, REQUEST_TAKE_PHOTO_PERMISSION_CODE);
                    }
        }).show();
    }

    //Activity銷毀之前把既有資料存下
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        PhotoUtil.saveFilePathToBundle(outState, file_path);
        PickPhotoFileUtil.saveUriToBundle(outState, uri);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_TAKE_PHOTO_PERMISSION_CODE:
                PhotoUtil.callCameraIntent(this, INTENT_TAKE_PIC, photoUtilListener);
                break;
            case REQUEST_CHOOSE_PHOTO_PERMISSION_CODE:
                PickPhotoFileUtil.pickImageFileIntent(this, INTENT_CHOOSE_PIC);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){

            //拍照Activity回傳結果
            case INTENT_TAKE_PIC:
                activityResult = (resultCode == RESULT_OK);
                if(activityResult){
                    uri = null;//清空圖片選擇結果
                }else {
                    PhotoUtil.deleteFile(this, file_path);
                    file_path = null;
                }
                break;
            //選擇圖片Activity回傳結果
            case INTENT_CHOOSE_PIC:
                uri = PickPhotoFileUtil.getUriFromBundleInActivityResult(resultCode, data);
                if(uri != null){
                    file_path = null;//清空拍照結果
                }
                break;
            default:

        }

    }

}
