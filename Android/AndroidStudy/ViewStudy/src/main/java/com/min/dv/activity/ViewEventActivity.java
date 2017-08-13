package com.min.dv.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.min.dv.R;
import com.min.dv.util.L;

/**
 * 点圆形即可查看事件分发打印出的日志
 */
public class ViewEventActivity extends AppCompatActivity {

    private static final String TAG="MyViewGroup_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        L.d(TAG,this.getClass().getSimpleName()+" dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag=super.onTouchEvent(event);
        L.d(TAG,this.getClass().getSimpleName()+" onTouchEvent flag=%s",flag);
        return flag;
    }

}
