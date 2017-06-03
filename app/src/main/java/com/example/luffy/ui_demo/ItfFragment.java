package com.example.luffy.ui_demo;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.example.luffy.ui_demo.network.VolleySingleton;
import com.google.gson.Gson;

/**
 * Created by tim on 2017/5/8.
 */

public class ItfFragment extends Fragment{


    private String getIdTag(){
        return getClass().getSimpleName();
    }

    protected <T> void startRequest(Request<T> request){
        request.setTag(getIdTag());
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    protected Gson getGson(){
        return VolleySingleton.getInstance(getContext()).getGson();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        VolleySingleton.getInstance(getContext()).cancel(getIdTag());

    }

    protected void showLogD(@NonNull String log){
        if(TextUtils.isEmpty(log) || !BuildConfig.DEBUG) {
            return;
        }

        Log.d(getIdTag(), log);
    }

    protected void showLogE(@NonNull String log){
        if(TextUtils.isEmpty(log) || !BuildConfig.DEBUG) {
            return;
        }
        Log.e(getIdTag(), log);
    }
}
