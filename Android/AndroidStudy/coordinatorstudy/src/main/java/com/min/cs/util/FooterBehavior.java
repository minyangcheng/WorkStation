package com.min.cs.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import com.min.framework.util.L;

/**
 * Created by minyangcheng on 2016/10/17.
 */
public class FooterBehavior extends CoordinatorLayout.Behavior<View> {

    private static final String TAG="FooterBehavior_TEST";

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private ObjectAnimator mAnimator;

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes==ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed > 0 && dyUnconsumed == 0) {
            L.d(TAG,"上滑中...");
            hide(child);
        }else if (dyConsumed == 0 && dyUnconsumed > 0) {
            L.d(TAG,"到边界了还在上滑...");
        }else if (dyConsumed < 0 && dyUnconsumed == 0) {
            L.d(TAG, "下滑中...");
            show(child);
        }else if (dyConsumed == 0 && dyUnconsumed < 0) {
            L.d(TAG,"到边界了还在下滑...");
        }
    }

    private void hide(final View view) {
        if(mAnimator!=null&&mAnimator.isRunning()){
            return;
        }
        if(view!=null&&view.getVisibility()==View.GONE){
            return;
        }
        mAnimator=ObjectAnimator.ofFloat(view,"translationY",0,view.getHeight());
        mAnimator.setInterpolator(INTERPOLATOR);
        mAnimator.setDuration(200);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mAnimator.start();
    }


    private void show(final View view) {
        if(mAnimator!=null&&mAnimator.isRunning()){
            return;
        }
        if(view!=null&&view.getVisibility()==View.VISIBLE){
            return;
        }
        mAnimator=ObjectAnimator.ofFloat(view,"translationY",0);
        mAnimator.setInterpolator(INTERPOLATOR);
        mAnimator.setDuration(200);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mAnimator.start();
    }
}