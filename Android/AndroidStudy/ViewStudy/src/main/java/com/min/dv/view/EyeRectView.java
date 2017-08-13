package com.min.dv.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.min.dv.util.L;
import com.min.dv.util.ScreenUtils;

/**
 * Created by minyangcheng on 2016/8/30.
 */
public class EyeRectView extends View{

    public static final String TAG="EyeRectView_TEST";

    private int mStorkeWidth;
    private int mBgColor;
    private int mColor;

    private int mDivider;

    private Paint mPaint;

    private Rect mRect;
    private float mScale=1;
    private int mTotal=100;

    public EyeRectView(Context context) {
        this(context, null);
    }

    public EyeRectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EyeRectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStorkeWidth=ScreenUtils.dpToPxInt(getContext(),1);
        mBgColor=Color.WHITE;
        mColor= Color.BLACK;
        mDivider=ScreenUtils.dpToPxInt(getContext(),10);

        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStorkeWidth);
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBgColor);
        int w=getMeasuredWidth();
        int h=getMeasuredHeight();
        canvas.translate(w / 2, h / 2);

        for (int i = 0; i < mTotal; i+=3) {
            float fraction=i/(float)mTotal;
            canvas.save();
            canvas.scale(fraction,fraction);
            L.d(TAG,"fraction=%s",fraction);
            canvas.drawRect(mRect, mPaint);
            canvas.restore();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=measureWidth(widthMeasureSpec);
        int height=measureHeight(heightMeasureSpec);
        int dimen=Math.min(width,height);
        setMeasuredDimension(dimen,dimen);

        int l=-(getMeasuredWidth()/2-mDivider);
        int t=-(getMeasuredHeight()/2-mDivider);
        mRect=new Rect(l,t,l*-1,t*-1);
    }

    private int measureWidth(int widthMeasureSpec){
        int width=0;
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else{
            width=getPaddingLeft()+getPaddingRight()+ ScreenUtils.dpToPxInt(getContext(),100);
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
            height=getPaddingTop()+getPaddingBottom()+ScreenUtils.dpToPxInt(getContext(),100);
            if(heightMode==MeasureSpec.AT_MOST){
                height=Math.min(height,heightSize);
            }
        }
        return height;
    }
}
