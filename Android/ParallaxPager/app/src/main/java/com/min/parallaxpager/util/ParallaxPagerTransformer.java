package com.min.parallaxpager.util;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.min.parallaxpager.fragment.PagerFragment;

/**
 * Created by minyangcheng on 2016/6/2.
 */
public class ParallaxPagerTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        Object object=page.getTag();
        if(object!=null&&object instanceof PagerFragment){
            PagerFragment pagerFragment= (PagerFragment) object;
            pagerFragment.parallax(page,position);
        }
    }
}
