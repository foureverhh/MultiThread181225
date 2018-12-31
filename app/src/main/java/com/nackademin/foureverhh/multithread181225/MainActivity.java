package com.nackademin.foureverhh.multithread181225;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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
    private MyAsyncTask myAsyncTask;
    LooperThread looperThread;
    CustomHandlerThread customHandlerThread;
    //Handler handler;
    //Thread newThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"Thread of Main thread: "+ Thread.currentThread().getId());
        btn_start = findViewById(R.id.buttonThreadStarter);
        btn_stop = findViewById(R.id.buttonStopThread);
        textView = findViewById(R.id.text_counter);

        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        looperThread = new LooperThread();
        looperThread.start();

        customHandlerThread = new CustomHandlerThread("CustomHandlerThread");
        customHandlerThread.start();
        //Make an instance of handler on the main looper
        //handler = new Handler(getApplicationContext().getMainLooper());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(looperThread != null && looperThread.isAlive())
            looperThread.handler.getLooper().quit();
        if(customHandlerThread != null)
            customHandlerThread.getLooper().quit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonThreadStarter:
                Toast.makeText(getApplicationContext(),"Starter",Toast.LENGTH_SHORT).show();
                mStopLoop=true;
                executeOnCustomLooper();
                //executeOnCustomLooperWithCustomHandler();


                //Thread newThread = new Thread(new Runnable() {
            /*    newThread = new Thread(new Runnable() {
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
                */
            /*
            //To instantiate MyAsyncTask and make it execute
                myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute(count);
            */
                break;
            case R.id.buttonStopThread:
                mStopLoop=false;
                //myAsyncTask.cancel(true);
                //newThread.interrupt();
                //count = 0;
                //textView.setText("Counter stop :"+count);
                break;
        }
    }

    public void executeOnCustomLooperWithCustomHandler(){
        //looperThread.handler.post(new Runnable() {
        customHandlerThread.mHandler.post(new Runnable() {
            @Override
            public void run() {
                while(mStopLoop){
                    try {
                        Thread.sleep(1000);
                        count++;
                        Log.i(TAG,"Thread id if Runnable posted: " +
                                Thread.currentThread().getId());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               Log.i(TAG,"Thread id of runOnUIThread: "+
                                       Thread.currentThread().getId()+", Count: "+count);
                               textView.setText(""+count);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void executeOnCustomLooper(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mStopLoop){
                    try {
                        Log.i(TAG,"Thread id of thread that sends message: "+
                                Thread.currentThread().getId());
                        Thread.sleep(1000);
                        count++;
                        Message message = new Message();
                        message.obj = ""+count;
                        //looperThread.handler.sendMessage(message);
                        customHandlerThread.mHandler.sendMessage(message);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("Counter start:" + count);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }


    private class MyAsyncTask extends AsyncTask<Integer,Integer,Integer>{

        private int customCounter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customCounter = 0;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            customCounter = integers[0];
            while(mStopLoop){
                try {
                    Thread.sleep(500);
                    customCounter ++;
                    //Pass customCounter to onProgressUpdate
                    publishProgress(customCounter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isCancelled()){
                    //To make count goes on counting after pressing start button
                    count=customCounter;
                    break;
                }

            }
            //Pass customCounter back to onPreExecute()
            return customCounter;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textView.setText(""+values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            textView.setText("Counter is "+integer);
            //Make count to be the last integer in Async task
            //count = integer;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
