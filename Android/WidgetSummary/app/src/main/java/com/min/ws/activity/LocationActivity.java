package com.min.ws.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.media.Image;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.min.ws.R;
import com.min.ws.util.L;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationActivity extends AppCompatActivity {

    public static final String TAG="LocationActivity_TEST";

    @Bind(R.id.iv_start)
    ImageView mStartIv;
    @Bind(R.id.iv_end)
    ImageView mEndIv;
    @Bind(R.id.fl_content)
    FrameLayout mParentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_fly)
    void clickBtnFly(){
        int[] parentLoaction=new int[2];
        int[] startLoaction=new int[2];
        int[] endLoaction=new int[2];
        mParentView.getLocationInWindow(parentLoaction);
        mStartIv.getLocationInWindow(startLoaction);
        mEndIv.getLocationInWindow(endLoaction);

        final ImageView createIv=new ImageView(this);
        createIv.setImageDrawable(mStartIv.getDrawable());
        createIv.setScaleType(ImageView.ScaleType.FIT_XY);
        createIv.setAlpha(mStartIv.getAlpha());
        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(mStartIv.getWidth(),mStartIv.getHeight());
        lp.leftMargin=startLoaction[0]-parentLoaction[0]-mParentView.getPaddingLeft();
        lp.topMargin=startLoaction[1]-parentLoaction[1]-mParentView.getPaddingTop();
        mParentView.addView(createIv,lp);

        int dx=endLoaction[0]+mEndIv.getWidth()/2-(startLoaction[0]+mStartIv.getWidth()/2);
        int dy=endLoaction[1]+mEndIv.getHeight()/2-(startLoaction[1]+mStartIv.getHeight()/2);
        final float ratio=((float)dy)/(dx*dx);
        ValueAnimator animator=ValueAnimator.ofInt(0,dx);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int x = (int) animation.getAnimatedValue();
                int y = (int) (x*x * ratio);

                createIv.setTranslationX(x);
                createIv.setTranslationY(y);
            }
        });
        animator.start();
    }

}
