package com.min.ws.view.tab;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.min.ws.util.L;

public class TabViewPagerIndicator extends TabIndicator implements ViewPager.OnPageChangeListener, TabIndicator.OnTabClickListener {

    private static final String TAG=TabViewPagerIndicator.class.getSimpleName();

    private ViewPager mVp;

    public TabViewPagerIndicator(Context context) {
        this(context,null);
    }

    public TabViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTabClickListener(this);
    }

    public void setViewPager(ViewPager vp){
        if(vp==null){
            throw new IllegalArgumentException("viewpager should not be null");
        }
        mVp=vp;
        mVp.addOnPageChangeListener(this);
    }

    public void setViewPager(ViewPager vp , int index){
        if(vp==null){
            throw new IllegalArgumentException("viewpager should not be null");
        }
        mVp=vp;
        mVp.addOnPageChangeListener(this);
        mVp.setCurrentItem(index);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        L.d(TAG,"onPageScrolled position=%s , positionOffset=%s",position,positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        L.d(TAG,"onPageSelected position=%s",position);
        selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        L.d(TAG,"onPageScrollStateChanged state=%s",state);
    }

    @Override
    public void onTabClick(int index) {
        mVp.setCurrentItem(index);
    }

}
