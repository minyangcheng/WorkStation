package com.min.ws.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.min.ws.R;
import com.min.ws.fragment.ContentFragment;
import com.min.ws.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SlidingTabActivity extends AppCompatActivity {

    @Bind(R.id.vp)
    ViewPager mVp;
    @Bind(R.id.psts)
    PagerSlidingTabStrip mPsts;

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tab);
        ButterKnife.bind(this);

        mFragmentList=new ArrayList<>();
        mTitleList=new ArrayList<>();
        for(int i=0;i<3;i++){
            mFragmentList.add(ContentFragment.newInstance("_"+i));
            mTitleList.add("_"+i);
        }

        MyFragmentPageAdapter adapter=new MyFragmentPageAdapter(getSupportFragmentManager(),mFragmentList,mTitleList);
        mVp.setAdapter(adapter);
        mVp.setOffscreenPageLimit(mFragmentList.size());
        mPsts.setViewPager(mVp);
    }

    private class MyFragmentPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList;
        private List<String> mTitleList;

        public MyFragmentPageAdapter(FragmentManager fm,List<Fragment> fragmentList,List<String> titleList) {
            super(fm);
            this.mFragmentList=fragmentList;
            this.mTitleList=titleList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

    }
}
