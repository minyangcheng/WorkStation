package com.min.ws.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.min.ws.R;
import com.min.ws.fragment.ContentFragment;
import com.min.ws.view.tab.TabViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabViewPagerActivity extends AppCompatActivity {

    @Bind(R.id.vp)
    ViewPager mVp;
    @Bind(R.id.ti_indicator)
    TabViewPagerIndicator mTabIndicator;

    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view_pager);
        ButterKnife.bind(this);

        mFragmentList=new ArrayList<>();
        mFragmentList.add(ContentFragment.newInstance("0"));
        mFragmentList.add(ContentFragment.newInstance("1"));
        mFragmentList.add(ContentFragment.newInstance("2"));
        mFragmentList.add(ContentFragment.newInstance("3"));

        MyFragmentPageAdapter adapter=new MyFragmentPageAdapter(getSupportFragmentManager(),mFragmentList);
        mVp.setAdapter(adapter);
        mTabIndicator.setViewPager(mVp,1);
    }

    private class MyFragmentPageAdapter extends FragmentPagerAdapter{

        private List<Fragment> mFragmentList;

        public MyFragmentPageAdapter(FragmentManager fm,List<Fragment> fragmentList) {
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
