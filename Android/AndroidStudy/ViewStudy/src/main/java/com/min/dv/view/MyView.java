package com.min.dv.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.min.dv.util.L;

/**
 * Created by minyangcheng on 2016/8/29.
 */
public class MyView extends View {

    private static final String TAG="MyViewGroup_TEST";
    private Paint mPaint;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.parseColor("#436EEE"));
        mPaint.setStyle(Paint.Style.FILL);

//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                L.d(TAG,"setOnTouchListener...");
//                return true;
//            }
//        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec){
        int width=0;
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else{
            width=getPaddingLeft()+getPaddingRight()+100;
            if(widthMode==MeasureSpec.AT_MOST){
                width=Math.min(width,widthSize);
            }
        }
        return width;
    }

    private int measureHeight(int heightMeasureSpec){
        int height=0;
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        if(heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else{
            height=getPaddingTop()+getPaddingBottom()+100;
            if(heightMode==MeasureSpec.AT_MOST){
                height=Math.min(height,heightSize);
            }
        }
        return height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredHeight() / 2, mPaint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        L.d(TAG, this.getClass().getSimpleName() + " dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.d(TAG,this.getClass().getSimpleName()+" onTouchEvent");
        return super.onTouchEvent(event);
    }

}
