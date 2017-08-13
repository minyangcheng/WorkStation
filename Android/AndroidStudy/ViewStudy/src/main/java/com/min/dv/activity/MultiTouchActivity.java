package com.min.dv.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.min.dv.R;
import com.min.dv.util.L;

public class MultiTouchActivity extends AppCompatActivity {

    private static final String TAG="MultiTouchActivity_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_touch);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()&MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                d("ACTION_DOWN",event);
                break;
            case MotionEvent.ACTION_UP:
                d("ACTION_UP",event);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount=event.getPointerCount();
                L.d(TAG,"pointerCount=%s",pointerCount);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                d("ACTION_POINTER_DOWN",event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                d("ACTION_POINTER_UP",event);
                break;
        }
        return true;
    }

    /**
     * ACTION_DOWN --> ACTION_POINTER_DOWN --> ACTION_POINTER_UP --> ACTION_UP
     * @param str
     * @param event
     */
    private void d(String str,MotionEvent event){
        L.d(TAG,str+",x=%s , y=%s",event.getX(),event.getY());
    }

}
