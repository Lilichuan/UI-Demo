package com.example.luffy.ui_demo.network

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

/**
 * Created by tim.
 * 使用Kotlin模式
 * 必須在專案的Application初始化此物件
 */

class VolleySingleton (val mCtx: Context) {


    val imageLoader: ImageLoader
    val gson: Gson
    get

    val context : Context = mCtx.applicationContext

    //需要自訂mRequestQueue的參數的話，寫在這裡
            //例如：
            //Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB
            //Network network = new BasicNetwork(new HurlStack());
            //範例結束
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
    private val mRequestQueue : RequestQueue by lazy {
        Volley.newRequestQueue(context)
    }
        get

    init {

        gson = Gson()

        imageLoader = ImageLoader(mRequestQueue,
                object : ImageLoader.ImageCache {
                    private val cache = LruCache<String, Bitmap>(20)

                    override fun getBitmap(url: String): Bitmap {
                        return cache.get(url)
                    }

                    override fun putBitmap(url: String, bitmap: Bitmap) {
                        cache.put(url, bitmap)
                    }
                })
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        mRequestQueue.add(req)
    }

    fun cancel(tag: String) {
        mRequestQueue.cancelAll(tag)
    }

    companion object {
        @get:Synchronized var instance: VolleySingleton? = null
            private set
    }
}
