package com.min.dv.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by minyangcheng on 2016/8/29.
 */
public class SaveRestoreView extends View {

    private static final String TAG="MyViewGroup_TEST";
    private Paint mPaint;

    public SaveRestoreView(Context context) {
        this(context, null);
    }

    public SaveRestoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaveRestoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.parseColor("#436EEE"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
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
        float w=getWidth();
        float h=getHeight();
        canvas.save();
        canvas.translate(w / 2, h / 2);
        canvas.scale(0.5f,0.5f);
        canvas.drawCircle(0, 0, 50, mPaint);
        canvas.restore();
        canvas.drawCircle(w / 2, h / 2, 50,mPaint);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("parcelable",super.onSaveInstanceState());
        //save your data
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle= (Bundle) state;
        Parcelable parcelable=bundle.getParcelable("parcelable");
        super.onRestoreInstanceState(parcelable);
        //restore your data
    }

}
