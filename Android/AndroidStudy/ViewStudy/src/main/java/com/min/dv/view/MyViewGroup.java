package com.min.dv.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.min.dv.util.L;

/**
 * 自定义垂直布局
 * 1、根据排列规则计算布局宽高
 * 2、根据排练规则布局子view的left、top、right、bottom
 */

public class MyViewGroup extends ViewGroup {

    private static final String TAG="MyViewGroup_TEST";

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top=0;
        int cl,ct,cr,cb=0;
        int childCount=getChildCount();
        View childView=null;
        MarginLayoutParams mp=null;
        for (int i = 0; i < childCount; i++) {
            childView=getChildAt(i);
            mp= (MarginLayoutParams) childView.getLayoutParams();
            cl=mp.leftMargin;
            ct=mp.topMargin+top;
            cr=cl+childView.getMeasuredWidth();
            cb=ct+childView.getMeasuredHeight();
            childView.layout(cl,ct,cr,cb);
            top=mp.bottomMargin+cb;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int width=0;
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else{
            int childCount=getChildCount();
            View childView=null;
            MarginLayoutParams mp=null;
            for (int i = 0; i < childCount; i++) {
                childView=getChildAt(i);
                mp= (MarginLayoutParams) childView.getLayoutParams();
                if(i==0){
                    width=childView.getMeasuredWidth()+mp.leftMargin+mp.rightMargin;
                }else{
                    width=Math.max(width,childView.getMeasuredWidth()+mp.leftMargin+mp.rightMargin);
                }
            }
            if(widthMode==MeasureSpec.AT_MOST){
                width=Math.min(width,widthSize);
            }
        }
        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int height=0;
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        if(heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else{
            int childCount=getChildCount();
            View childView=null;
            MarginLayoutParams mp=null;
            for (int i = 0; i < childCount; i++) {
                childView=getChildAt(i);
                mp= (MarginLayoutParams) childView.getLayoutParams();
                height+=childView.getMeasuredHeight()+mp.topMargin+mp.bottomMargin;
            }
            if(heightMode==MeasureSpec.AT_MOST){
                height=Math.min(height,heightSize);
            }
        }
        return height;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        L.d(TAG,this.getClass().getSimpleName()+" dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.d(TAG,this.getClass().getSimpleName()+" onTouchEvent");
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        L.d(TAG,this.getClass().getSimpleName()+" onInterceptTouchEvent");
        return false;
    }
}
