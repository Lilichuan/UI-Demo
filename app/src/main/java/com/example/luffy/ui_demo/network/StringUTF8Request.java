package com.example.luffy.ui_demo.network;


import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 阿銓 on 2017/3/13.
 *
 * 一般的Volley的Request物件處理UTF8會亂碼，
 * 使用此Request物件可避免此Bug。
 *
 */

public class StringUTF8Request extends StringRequest {

    public StringUTF8Request(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public StringUTF8Request(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String utf8String = null;
        try {
            utf8String = new String(response.data, "UTF-8");
            return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if(myHeader == null || myHeader.size() <= 0){
            return super.getHeaders();
        }
        return myHeader;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if(myParams == null || myParams.size() <= 0){
            return super.getParams();
        }
        return myParams;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if(!TextUtils.isEmpty(myBody)){
            return myBody.getBytes();
        }
        return super.getBody();
    }

    private Map<String, String> myHeader;

    public void addHeader(String key, String value){
        if(myHeader == null){
            myHeader = new HashMap<String, String>();
        }

        if(TextUtils.isEmpty(key) || TextUtils.isEmpty(value)){
            return;
        }

        myHeader.put(key, value);
    }



    private Map<String, String> myParams;

    public void addParam(String key, String value){
        if(myParams == null){
            myParams = new HashMap<String, String>();
        }

        if(TextUtils.isEmpty(key) || TextUtils.isEmpty(value)){
            return;
        }

        myParams.put(key, value);
    }

    private String myBody;

    public void setBody(String body) {
        this.myBody = body;
    }
}
