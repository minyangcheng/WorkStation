package com.min.dv.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.min.dv.util.L;
import com.min.dv.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class AnimView extends View {

    public static final String TAG="AnimView_TEST";

    private int mBgColor;
    private int mColor;

    private Paint mPaint;

    private int mRaudis;
    private int mCount=3;

    private boolean mHasAnimation;
    private float[] mScales={1f,1f,1f};
    private AnimatorSet mAnimationSet;

    public AnimView(Context context) {
        this(context, null);
    }

    public AnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBgColor= Color.parseColor("#E5E5E5");
        mColor= Color.parseColor("#CD5C5C");

        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);

        mRaudis=ScreenUtils.dpToPxInt(getContext(),8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBgColor);
        int w=getMeasuredWidth();
        int h=getMeasuredHeight();

        int x=0;
        int y=h/2;
        for(int i=0;i<mCount;i++){
            x=2*mRaudis+(3*mRaudis)*i;
            canvas.drawCircle(x,y,mRaudis*mScales[i],mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=measureWidth(widthMeasureSpec);
        int height=measureHeight(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    private int measureWidth(int widthMeasureSpec){
        int width=0;
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else{
            width=getPaddingLeft()+getPaddingRight()+ mCount*2*mRaudis+(mCount+1)*mRaudis;
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
            height=getPaddingTop()+getPaddingBottom()+4*mRaudis;
            if(heightMode==MeasureSpec.AT_MOST){
                height=Math.min(height,heightSize);
            }
        }
        return height;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int w=getMeasuredWidth();
        int h=getMeasuredHeight();
        L.d(TAG, "onAttachedToWindow w=%s , h=%s", w, h);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        int w=getMeasuredWidth();
        int h=getMeasuredHeight();
        L.d(TAG, "onDetachedFromWindow w=%s , h=%s", w, h);

        closeAnim();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        L.d(TAG, "onSizeChanged w=%s , h=%s ,oldw=%s , oldh=%s", w, h,oldw,oldh);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        L.d(TAG, "layout ...");

        if (!mHasAnimation){
            mHasAnimation=true;
            startAnim();
        }
    }

    private void startAnim(){
        mAnimationSet=new AnimatorSet();
        List<Animator> animatorList=new ArrayList<>();
        for(int i=0;i<mCount;i++){
            animatorList.add(getAnimByIndex(i));
        }
        mAnimationSet.playTogether(animatorList);
        mAnimationSet.start();
    }

    private void closeAnim(){
        if(mAnimationSet!=null&&mAnimationSet.isRunning()){
            mAnimationSet.cancel();
        }
    }

    private Animator getAnimByIndex(final int index){
        ValueAnimator animator=ValueAnimator.ofFloat(1,0.3f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mScales[index] = value;
                postInvalidate();
            }
        });
        animator.setDuration(750);
        animator.setStartDelay((index+1) * 120);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        return animator;
    }

}
