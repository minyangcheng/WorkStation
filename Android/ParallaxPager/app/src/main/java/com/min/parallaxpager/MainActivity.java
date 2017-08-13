package com.min.parallaxpager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.min.parallaxpager.fragment.GuideFirstFragment;
import com.min.parallaxpager.fragment.GuideFiveFragment;
import com.min.parallaxpager.fragment.GuideFourFragment;
import com.min.parallaxpager.fragment.GuideSecondFragment;
import com.min.parallaxpager.fragment.GuideThirdFragment;
import com.min.parallaxpager.util.MyLog;
import com.min.parallaxpager.util.ParallaxPagerTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG=MainActivity.class.getSimpleName();

    @Bind(R.id.vp)
    ViewPager mVp;

    private List<Fragment> mFragmentList;
    private ArgbEvaluator mArgbEvaluator;
    private float mTotalPageWidth;
    private int mStartColor;
    private int mEndColor;
    private int mPageWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragmentList();
        mVp.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mFragmentList));

        mVp.setPageTransformer(true, new ParallaxPagerTransformer());

        mArgbEvaluator=new ArgbEvaluator();
        mPageWidth=getWindowManager().getDefaultDisplay().getWidth();
        mTotalPageWidth=mPageWidth*mFragmentList.size();
        mStartColor= getResources().getColor(R.color.guide_start_background);
        mEndColor=getResources().getColor(R.color.guide_end_background);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                MyLog.i(TAG, "onPageScrolled position=%s , positionOffset=%s", position, positionOffset);
                float radio=mPageWidth*(position+positionOffset)/mTotalPageWidth;
                int color= (int) mArgbEvaluator.evaluate(radio,mStartColor,mEndColor);
                mVp.setBackgroundColor(color);
            }

            @Override
            public void onPageSelected(int position) {
                MyLog.i(TAG, "onPageSelected position=%s ", position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initFragmentList() {
        mFragmentList=new ArrayList<>();
        mFragmentList.add(new GuideFirstFragment());
        mFragmentList.add(new GuideSecondFragment());
        mFragmentList.add(new GuideThirdFragment());
        mFragmentList.add(new GuideFourFragment());
        mFragmentList.add(new GuideFiveFragment());
    }

    public class MyPagerAdapter extends FragmentPagerAdapter{

        private List<Fragment> mFragmentList;

        public MyPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
            super(fm);
            this.mFragmentList=fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

}
