package com.min.parallaxpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by minyangcheng on 2016/6/2.
 */
public abstract class PagerFragment extends Fragment {

    protected float mParallaxCoefficient = 1.2f;
    protected float mDistanceCoefficient = 0.5f;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(getLayoutId(),container,false);
        view.setTag(this);
        ButterKnife.bind(this,view);
        return view;
    }

    public abstract int getLayoutId();

    public void parallax(View page,float position){
        if(getParallaxViews()==null||getParallaxViews().size()==0)
            return;
        float scrollXOffset=page.getWidth()*mParallaxCoefficient;
        for (View view : getParallaxViews()){
            view.setTranslationX(scrollXOffset*position);
            scrollXOffset*=mDistanceCoefficient;
        }
    }

    public abstract List<View> getParallaxViews();

}
