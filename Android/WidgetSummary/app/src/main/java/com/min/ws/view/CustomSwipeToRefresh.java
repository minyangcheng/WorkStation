package com.min.ws.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class CustomSwipeToRefresh extends SwipeRefreshLayout {

    private int mTouchSlop;
    private float mPrevX;
    private float mPreY;

    public CustomSwipeToRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);

//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mTouchSlop= (int) (context.getResources().getDisplayMetrics().density*5);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX=event.getX();
                mPreY=event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);
                float eventY=event.getY();
                float yDiff=Math.abs(eventY-mPreY);

                if (xDiff > yDiff && xDiff>mTouchSlop) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }
}