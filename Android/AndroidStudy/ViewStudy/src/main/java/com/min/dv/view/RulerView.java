package com.min.dv.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.min.dv.util.L;
import com.min.dv.util.ScreenUtils;

/**
 * Created by minyangcheng on 2016/8/30.
 */
public class RulerView extends View{

    public static final String TAG="RulerView_TEST";

    private int mBgColor;
    private int mStrokeColor;

    private int mMinHeight;
    private int mMidHeight;
    private int mMaxHeight;
    private int mDivider;

    private int mTextDivider;
    private int mTextSize;

    private Paint mPaint;

    private int mTouchSlop;
    private float mDownY;
    private float mLastY;

    private int mCanvasTranslateX;

    private int mCount=1001;  //总刻度数

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBgColor= Color.WHITE;
        mStrokeColor=Color.BLACK;

        mMinHeight=ScreenUtils.dpToPxInt(getContext(),8);
        mMidHeight=ScreenUtils.dpToPxInt(getContext(),16);
        mMaxHeight=ScreenUtils.dpToPxInt(getContext(),20);
        mDivider=ScreenUtils.dpToPxInt(getContext(),15);
        mTextSize=ScreenUtils.dpToPxInt(getContext(),12);
        mTextDivider=ScreenUtils.dpToPxInt(getContext(),35);

        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(ScreenUtils.dpToPxInt(getContext(), 1));
        mPaint.setTextSize(ScreenUtils.sp2px(getContext(), 16));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mStrokeColor);

        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBgColor);
        translateCanvas(canvas);
        canvas.translate(mCanvasTranslateX,0);
        drawCalibrations(canvas);
    }

    private void drawCalibrations(Canvas canvas) {
        int xD=getHeight();
        int yD=getWidth();
        for (int i = 0; i < mCount; i++) {
            drawSingle(canvas,i);
            canvas.translate(mDivider,0);
        }
    }

    private void drawSingle(Canvas canvas,int index){
        int singleHeight=0;
        if(index%10==0){
            singleHeight=mMaxHeight;
            drawNumber(canvas, index);
        }else if(index%5==0){
            singleHeight=mMidHeight;
        }else{
            singleHeight=mMinHeight;
        }
        canvas.drawLine(0,0,0,singleHeight,mPaint);
    }

    private void drawNumber(Canvas canvas, int index) {
        String s=String.valueOf(index/10);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(s,0,mTextDivider,mPaint);
    }

    //将原点从左上角移动到做下角
    private void translateCanvas(Canvas canvas) {
        int h=getHeight();
        canvas.rotate(-90);
        canvas.translate(-h,0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float distance=event.getY()-mLastY;
                if(Math.abs(distance)>5){
                    if(mCanvasTranslateX + (-(int) distance)<=0){
                        mCanvasTranslateX+= -(int) distance;
//                        mCount+=(Math.abs(mCanvasTranslateX/10));
                        postInvalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        mLastY=event.getY();
        return true;
    }
}
