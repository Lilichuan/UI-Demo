package com.example.luffy.ui_demo

import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log

import com.android.volley.Request
import com.example.luffy.ui_demo.network.VolleySingleton
import com.google.gson.Gson

/**
 * Created by tim on 2017/5/8.
 */

class ItfFragment : Fragment() {


    private val idTag: String
        get() = javaClass.simpleName

    protected val gson: Gson
        get() = VolleySingleton.instance!!.gson

    protected fun <T> startRequest(request: Request<T>) {
        request.tag = idTag
        VolleySingleton.instance!!.addToRequestQueue(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancelConnect()
    }

    override fun onPause() {
        super.onPause()
        cancelConnect()
    }

    private fun cancelConnect() {
        VolleySingleton.instance!!.cancel(idTag)
    }


    protected fun showLogD(log: String) {
        if (TextUtils.isEmpty(log) || !BuildConfig.DEBUG) {
            return
        }

        Log.d(idTag, log)
    }

    protected fun showLogE(log: String) {
        if (TextUtils.isEmpty(log) || !BuildConfig.DEBUG) {
            return
        }
        Log.e(idTag, log)
    }

    protected fun showSnackBar(@StringRes str: Int, len: Int) {
        Snackbar.make(view!!, str, len).show()
    }
}
