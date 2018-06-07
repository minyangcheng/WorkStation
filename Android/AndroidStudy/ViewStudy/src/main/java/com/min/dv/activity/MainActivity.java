package com.min.dv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.min.dv.R;
import com.min.dv.util.L;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Thread mThread;
    private Looper mLooper;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_handler)
    void clickBtnHandler() {
        L.d("mytest","ui-thread name=%s",Thread.currentThread().getName());
        if(mThread==null){
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("qdx", "step 0 ");
                    Looper.prepare();
                    mLooper=Looper.myLooper();
                    Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                        @Override
                        public boolean queueIdle() {
                            L.d("mytest","queueIdle");
                            return true;
                        }
                    });

                    Looper.loop();
                }
            });
            mThread.start();
        }
    }

    @OnClick(R.id.btn_event_study)
    void clickBtnEventStudy() {
//        go(ViewEventActivity.class);
//        new Handler(mLooper).post(new Runnable() {
//            @Override
//            public void run() {
//                L.d("mytest","async-thread name=%s",Thread.currentThread().getName());
//            }
//        });
        if(mHandler==null){
            mHandler=new Handler(mLooper, new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    L.d("mytest","%s,Handler=%s",Thread.currentThread().getName(),msg.obj);
                    return false;
                }
            }){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    L.d("mytest","%s,handleMessage=%s",Thread.currentThread().getName(),msg.obj);
                }
            };
        }
    }

    @OnClick(R.id.btn_canvas_study)
    void clickBtnCanvasStudy() {
//        go(CanvasActivity.class);
        Message message=new Message();
        message.obj="nihaoma__"+System.currentTimeMillis();
        mHandler.sendMessage( message);
    }

    @OnClick(R.id.btn_ruler)
    void clickBtnRuler() {
        go(RulerActivity.class);
    }

    @OnClick(R.id.btn_multi_touch)
    void clickBtnMultiTouch() {
        go(MultiTouchActivity.class);
    }

    @OnClick(R.id.btn_image_scale)
    void clickBtnImageScale() {
        go(ImageViewScaleActivity.class);
    }

    @OnClick(R.id.btn_view_anim)
    void clickBtnViewAnim() {
        go(ViewAnimActivity.class);
    }

    private void go(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
