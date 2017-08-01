package com.example.luffy.ui_demo.camera;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.luffy.ui_demo.R;


public class TakePhotoFragment extends Fragment {

    private static final String KEY_PICTURE_PATH = "picture_path";
    private static final String KEY_PICTURE_URI = "picture_uri";

    private String path;
    private Uri uri;

    private ImageView pic_image_view;
    private View takePhoto,pickPhoto;


    public TakePhotoFragment() {
        // Required empty public constructor
    }


    @Nullable
    public static TakePhotoFragment newInstance(String path) {
        TakePhotoFragment fragment = new TakePhotoFragment();
        Bundle args = new Bundle();
        if(!TextUtils.isEmpty(path)){
            args.putString(KEY_PICTURE_PATH, path);
        }
        fragment.setArguments(args);
        return fragment;
    }

    public static TakePhotoFragment newInstance(Uri uri) {
        TakePhotoFragment fragment = new TakePhotoFragment();
        Bundle args = new Bundle();
        if(uri != null){
            args.putString(KEY_PICTURE_URI, uri.toString());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();

            path = args.getString(KEY_PICTURE_PATH, "");

            String uri_str = args.getString(KEY_PICTURE_URI, "");
            if(!TextUtils.isEmpty(uri_str)){
                uri = Uri.parse(uri_str);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_take_photo, container, false);

        pic_image_view = (ImageView) root.findViewById(R.id.picture_image_view);
        takePhoto = root.findViewById(R.id.btn_take_picture);
        pickPhoto = root.findViewById(R.id.btn_pick_picture);

        if(PhotoUtil.deviceHaveCamera(getContext())){
            takePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTakePictureListener != null){
                        mTakePictureListener.onTakePicture();
                    }
                }
            });
        }else {
            takePhoto.setEnabled(false);
        }

        pickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTakePictureListener != null){
                    mTakePictureListener.onPickPicture();
                }
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        *
        * 注意：太早執行的話，寬高尚未完成將會發生錯誤
        *
        * 注意：不要放在OnStart，實驗證明拍完照的 Activity.OnActivityResult() 也會觸發Fragment.OnStart()，
        * 這是靈異現象。
        *
        * */
        pic_image_view.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(uri != null){
                    PickPhotoFileUtil.setPic(getContext(), uri, pic_image_view);
                }else {
                    PhotoUtil.setPic(path, pic_image_view);
                }
            }
        }, 500);
    }

    private TakePictureListener mTakePictureListener;

    public void setTakePictureListener(TakePictureListener takePictureListener) {
        mTakePictureListener = takePictureListener;
    }

    public interface TakePictureListener{

        void onTakePicture();

        void onPickPicture();

    }
}
