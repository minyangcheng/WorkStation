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
public class CenterTextView extends View {

    public static final String TAG="CenterTextView_TEST";

    private int mBgColor;

    private Paint mPaint;

    private String mText;

    public CenterTextView(Context context) {
        this(context, null);
    }

    public CenterTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBgColor= Color.parseColor("#7EC0EE");
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
//        mPaint.setStrokeWidth(ScreenUtils.dpToPxInt(getContext(), 3));
        mPaint.setTextSize(ScreenUtils.sp2px(getContext(), 16));
        mPaint.setStyle(Paint.Style.STROKE);
        mText="minyangcheng123";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBgColor);

        Rect targetRect=new Rect(0,0,getMeasuredWidth(),getMeasuredHeight());
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mText, targetRect.centerX(), baseline, mPaint);
    }
}
