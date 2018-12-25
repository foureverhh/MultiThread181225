package com.nackademin.foureverhh.multithread181225;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btn_start, btn_stop;
    private TextView textView;
    private boolean mStopLoop;
    int count = 0;
    Handler handler;
    Thread newThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.buttonThreadStarter);
        btn_stop = findViewById(R.id.buttonStopThread);
        textView = findViewById(R.id.text_counter);

        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        //Make an instance of handler on the main looper
        handler = new Handler(getApplicationContext().getMainLooper());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonThreadStarter:
                Toast.makeText(getApplicationContext(),"Starter",Toast.LENGTH_SHORT).show();
                mStopLoop=true;
                //Thread newThread = new Thread(new Runnable() {
                newThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(mStopLoop){
                            try {
                                Thread.sleep(1000);
                                count++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.i(TAG,"Thread id: " + Thread.currentThread().getId());
                            Log.e("Counter"," is "+count);
                            //Use the handler to post a task on the messageQueue
                            //handler.post(new Runnable() {
                            //    @Override
                            //    public void run() {
                            //        textView.setText("Counter start:"+count);
                            //    }
                            //});
                            textView.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("Counter start:"+count);
                                }
                            });
                        }
                    }
                });
                newThread.start();
                break;
            case R.id.buttonStopThread:
                mStopLoop=false;
                newThread.interrupt();
                count = 0;
                textView.setText("Counter stop :"+count);
                break;
        }
    }
}
