package com.min.parallaxpager.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.min.parallaxpager.R;

import java.util.List;

import butterknife.Bind;

/**
 * Created by minyangcheng on 2016/6/2.
 */
public class GuideFirstFragment extends PagerFragment {

    private int duration=500;
    private int offsetDelay=200;

    @Bind({R.id.guide_item_1,R.id.guide_item_2,R.id.guide_item_3
            ,R.id.guide_item_4,R.id.guide_item_5,R.id.guide_item_6
            ,R.id.guide_item_7,R.id.guide_item_8,R.id.guide_item_9
            ,R.id.guide_item_10,R.id.guide_item_11,R.id.guide_item_12})
    List<View> viewList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guide_first;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animateViews_2();
    }

    private void animateViews_2(){
        for(int i=0;i<viewList.size();i++){
            AnimatorSet animatorSet=new AnimatorSet();
            viewList.get(i).setScaleX(0);
            viewList.get(i).setScaleY(0);
            ObjectAnimator objectAnimator_1=ObjectAnimator.ofFloat(viewList.get(i), "alpha", 0f, 1f);
            ObjectAnimator objectAnimator_2=ObjectAnimator.ofFloat(viewList.get(i), "scaleX", 0f, 1f);
            ObjectAnimator objectAnimator_3=ObjectAnimator.ofFloat(viewList.get(i), "scaleY", 0f, 1f);
            animatorSet.setStartDelay(offsetDelay * i);
            animatorSet.setDuration(duration);
            animatorSet.playTogether(objectAnimator_1, objectAnimator_2, objectAnimator_3);
            animatorSet.start();
        }
    }

    private void animateViews_1(){
        for(int i=0;i<viewList.size();i++){
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.splash_guide_items);
            animation.setDuration(duration);
            animation.setStartOffset(offsetDelay * i);
            viewList.get(i).startAnimation(animation);
        }
    }

    @Override
    public List<View> getParallaxViews() {
        return null;
    }


}
