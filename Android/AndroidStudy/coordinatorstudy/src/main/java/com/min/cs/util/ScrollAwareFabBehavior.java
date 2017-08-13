package com.min.cs.util;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

public class ScrollAwareFabBehavior extends CoordinatorLayout.Behavior<View> {

    private ListenerAnimatorEndBuild listenerAnimatorEndBuild;

    public ScrollAwareFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        listenerAnimatorEndBuild = new ListenerAnimatorEndBuild();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        if (dyConsumed > 0 && dyUnconsumed == 0) {
//            LogUtil.d("上滑中。。。");
//        }
//        if (dyConsumed == 0 && dyUnconsumed > 0) {
//            LogUtil.d("到边界了还在上滑。。。");
//        }
//        if (dyConsumed < 0 && dyUnconsumed == 0) {
//            LogUtil.d("下滑中。。。");
//        }
//        if (dyConsumed == 0 && dyUnconsumed < 0) {
//            LogUtil.d("到边界了，还在下滑。。。");
//        }

        //缩放动画
        if ((dyConsumed > 0 || dyUnconsumed > 0) && listenerAnimatorEndBuild.isFinish() && child.getVisibility() == View.VISIBLE) {//往下滑
            scaleHide(child, listenerAnimatorEndBuild.build());
        } else if ((dyConsumed < 0 || dyUnconsumed < 0) && child.getVisibility() != View.VISIBLE) {
            scaleShow(child, null);
        }
    }

    public static final FastOutSlowInInterpolator FASTOUTSLOWININTERPOLATOR = new FastOutSlowInInterpolator();


    public static void scaleShow(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        view.setVisibility(View.VISIBLE);
        ViewCompat.animate(view)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .alpha(1.0f)
                .setDuration(500)
                .setInterpolator(FASTOUTSLOWININTERPOLATOR)
                .setListener(viewPropertyAnimatorListener)
                .start();
    }


    public static void scaleHide(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        ViewCompat.animate(view)
                .scaleX(0.0f)
                .scaleY(0.0f)
                .alpha(0.0f)
                .setDuration(500)
                .setInterpolator(FASTOUTSLOWININTERPOLATOR)
                .setListener(viewPropertyAnimatorListener)
                .start();
    }

    class ListenerAnimatorEndBuild {
        // 记录View移出动画是否执行完。
        private boolean isOutExecute = false;

        private ViewPropertyAnimatorListener outAnimatorListener;

        public ListenerAnimatorEndBuild() {
            outAnimatorListener = new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {
                    isOutExecute = true;
                }

                @Override
                public void onAnimationEnd(View view) {
                    view.setVisibility(View.GONE);
                    isOutExecute = false;
                }

                @Override
                public void onAnimationCancel(View view) {
                    isOutExecute = false;
                }
            };
        }

        // View移出动画是否执行完。
        public boolean isFinish() {
            return !isOutExecute;
        }

        // 返回ViewPropertyAnimatorListener。
        public ViewPropertyAnimatorListener build() {
            return outAnimatorListener;
        }
    }
}