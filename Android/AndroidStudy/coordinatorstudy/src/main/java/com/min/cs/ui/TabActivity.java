package com.min.cs.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.min.cs.R;
import com.min.cs.ui.fragment.TabItemFragment;
import com.min.cs.util.ThemeUtils;
import com.min.framework.base.BaseActivity;
import com.min.framework.base.BaseFragment;
import com.min.framework.base.BaseFragmentPagerAdapter;
import com.min.framework.widget.CenterTitleToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class TabActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.vp)
    ViewPager mVp;

    private List<BaseFragment> mFragmentList;
    private List<String> mTitleList;
    private List<TabLayout.Tab> mTabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.setActivityTheme(this);
        super.onCreate(savedInstanceState);
        initToolbar(mToolbar);

        mFragmentList=new ArrayList<>();
        mTitleList=new ArrayList<>();
        for(int i=0;i<6;i++){
            mFragmentList.add(new TabItemFragment());
            mTitleList.add("title"+i);

        }
        BaseFragmentPagerAdapter adapter=new BaseFragmentPagerAdapter(getSupportFragmentManager()
                                    ,mFragmentList, mTitleList);
        mVp.setAdapter(adapter);
//        mTabLayout.setupWithViewPager(mVp);  //此方法会自动设置tab
        customTabToTabLayout();
    }

    private void customTabToTabLayout(){
        mTabList=new ArrayList<>();
        TabLayout.Tab tab=null;
        for(int i=0;i<mTitleList.size();i++){
            tab=mTabLayout.newTab()
                    .setText(mTitleList.get(i))
                    .setIcon(R.drawable.selector_tab_1)
                    .setCustomView(R.layout.item_tab);
            mTabLayout.addTab(tab);
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index=tab.getPosition();
                mVp.setCurrentItem(index);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTabLayout.setScrollPosition(position,positionOffset,true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab;
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        super.initToolbar(toolbar);
        mToolbar.setTitle("TabActivity");
    }
}
