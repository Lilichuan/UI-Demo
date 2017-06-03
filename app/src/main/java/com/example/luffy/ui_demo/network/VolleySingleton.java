package com.example.luffy.ui_demo.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 * Created by tim on 2017/5/8.
 */

public class VolleySingleton {

    private static VolleySingleton instance;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;
    private Gson gson;

    private VolleySingleton(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();
        gson = new Gson();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized VolleySingleton getInstance(Context context){
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {


            //需要自訂mRequestQueue的參數的話，寫在這裡
            //例如：
            //Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB
            //Network network = new BasicNetwork(new HurlStack());
            //範例結束

            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public Gson getGson(){
        return gson;
    }

    public void cancel(String tag){
        mRequestQueue.cancelAll(tag);
    }

}
