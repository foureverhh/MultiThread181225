package com.nackademin.foureverhh.multithread181225;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btn_start, btn_stop;
    private TextView textView;
    private boolean mStopLoop;
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.buttonThreadStarter);
        btn_stop = findViewById(R.id.buttonStopThread);
        textView = findViewById(R.id.text_counter);

        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonThreadStarter:
                mStopLoop=true;
                //Thread newThread = new Thread(new Runnable() {
                new Thread(new Runnable() {
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
                            textView.setText("Counter :"+count);
                        }
                    }
                }).start();
                //newThread.start();
                break;
            case R.id.buttonStopThread:
                mStopLoop=false;
                break;
        }
    }
}
