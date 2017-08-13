package com.min.ws.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.min.ws.R;
import com.min.ws.fragment.ContentFragment;
import com.min.ws.util.L;
import com.min.ws.view.tab.TabIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabActivity extends AppCompatActivity {

    @Bind(R.id.ti_indicator)
    TabIndicator mTabIndicator;

    private List<Fragment> mFragmentList;

    private int mCurrentIndex=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);

        mFragmentList=new ArrayList<>();
        mFragmentList.add(ContentFragment.newInstance("0"));
        mFragmentList.add(ContentFragment.newInstance("1"));
        mFragmentList.add(ContentFragment.newInstance("2"));
        mFragmentList.add(ContentFragment.newInstance("3"));

        mTabIndicator.setOnTabClickListener(new TabIndicator.OnTabClickListener() {
            @Override
            public void onTabClick(int index) {
                showFragment(index);
            }
        });

        if(savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt("currentIndex",-1);
        }
        if(mCurrentIndex<=-1){
            mCurrentIndex=0;
        }
        Fragment fragment=getSupportFragmentManager().findFragmentByTag("min");
        if(fragment!=null){
            L.d("TEST_MY","a--->fragment exit=%s ,isAdd=%s , isHidden=%s, currentIndex=%s"
                    ,fragment!=null,fragment.isAdded(),fragment.isHidden()
                    ,mCurrentIndex);
            L.d("TEST_MY","stack size="+getSupportFragmentManager().getFragments().size());
        }
        showFragment(mCurrentIndex);
        mTabIndicator.selectTab(mCurrentIndex);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentIndex", mCurrentIndex);
    }

    private void showFragment(int index) {
        mCurrentIndex=index;
        showFragment(mFragmentList.get(index));
    }

    private void showFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content,fragment,"min")
                .commit();
    }

}
