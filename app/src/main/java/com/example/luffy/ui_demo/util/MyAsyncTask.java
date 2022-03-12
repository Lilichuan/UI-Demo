package com.example.luffy.ui_demo.util;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

public abstract class MyAsyncTask {

    private static final int MSG_ON_POST_EXECUTE = 1;
    private static final int MSG_UPDATE_PROGRESS = 2;

    public MyAsyncTask(){

    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if(msg.what == MSG_ON_POST_EXECUTE){
                onPostExecute(msg.arg1);
                return false;
            } else if(msg.what == MSG_UPDATE_PROGRESS ){
                onProgressUpdate(msg.arg1, msg.arg2, msg.obj);
                return true;
            }

            return true;
        }
    });

    protected abstract int doInBackground();

    private Thread myThread = null;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int result = doInBackground();
            Message message = handler.obtainMessage(MSG_ON_POST_EXECUTE, result, 0);
            message.sendToTarget();
        }
    };

    public void execute(){
        if(myThread  == null){
            myThread = new Thread(runnable);
            myThread.start();
        }

    }

    protected abstract void onProgressUpdate(int arg1, int arg2, Object obj);

    protected abstract void onPostExecute(int result);

    protected void publishProgress(int arg1, int arg2, Object data){
        Message message = handler.obtainMessage(MSG_UPDATE_PROGRESS,arg1,arg2, data);
        message.sendToTarget();
    }

    protected void publishProgress(int arg1){
        publishProgress(arg1, 0,null);
    }

    protected void publishProgress(int arg1, int arg2){
        publishProgress(arg1, arg2,null);
    }

    public void cancel(){
        if(myThread != null && myThread.isAlive()){
            myThread.interrupt();
            myThread = null;
        }
    }

    public boolean isCancel(){
        if(myThread != null){
            return !myThread.isAlive();
        }else {
            return true;
        }
    }

}
