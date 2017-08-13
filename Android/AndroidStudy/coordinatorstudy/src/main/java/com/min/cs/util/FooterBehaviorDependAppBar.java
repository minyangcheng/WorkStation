package com.min.cs.util;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.min.framework.util.L;

/**
 * Created by minyangcheng on 2016/10/17.
 */
public class FooterBehaviorDependAppBar extends CoordinatorLayout.Behavior<View> {

    private static final String TAG="FooterBehaviorDependAppBar_TEST";

    public FooterBehaviorDependAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        L.d(TAG,"child=%s , dependency=%s ,translationY=%s",child.getClass().getSimpleName()
                                ,dependency.getClass().getSimpleName()
                                ,dependency.getTop());
        float translationY = Math.abs(dependency.getTop());
        child.setTranslationY(translationY);
        return true;
    }
}
