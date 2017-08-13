package com.min.parallaxpager.fragment;

import android.view.View;

import com.min.parallaxpager.R;

import java.util.List;

import butterknife.Bind;

/**
 * Created by minyangcheng on 2016/6/2.
 */
public class GuideSecondFragment extends PagerFragment {

    @Bind({R.id.guide_item_1,R.id.guide_item_2,R.id.guide_item_3})
    List<View> mViewList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guide_second;
    }

    @Override
    public List<View> getParallaxViews() {
        return mViewList;
    }
}
