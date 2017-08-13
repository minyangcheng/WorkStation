package com.min.framework.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by minyangcheng on 2016/10/17.
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragmentList;
    private List<String> mTitleList;

    public BaseFragmentPagerAdapter(FragmentManager fm,List<BaseFragment> fragmentList,List<String> titleList) {
        super(fm);
        this.mFragmentList=fragmentList;
        this.mTitleList=titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList==null?null:mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList==null?0:mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList==null?null:mTitleList.get(position);
    }
}
