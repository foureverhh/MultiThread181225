package com.nackademin.foureverhh.multithread181225;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class LooperThread extends Thread {

    private static final String TAG = LooperThread.class.getSimpleName();
    Handler handler;

    @Override
    public void run() {
        Looper.prepare();
        handler = new Handler(){
            //Message API is used to send any arbitrary data to a handler
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //msg.obj can send message to the handler
                Log.i(TAG,"Thread id: "+Thread.currentThread().getId()+", Count :"+msg.obj);
            }
        };
        Looper.loop();
    }
}
