package com.min.savestate.demo;

import android.os.Bundle;

import com.min.savestate.demo.base.BaseActivity;
import com.min.savestate.demo.base.BaseFragment;
import com.min.savestate.demo.fragment.FourFragment;
import com.min.savestate.demo.fragment.OneFragment;
import com.min.savestate.demo.fragment.ThreeFragment;
import com.min.savestate.demo.fragment.TwoFragment;
import com.min.savestate.demo.util.MyLog;
import com.min.savestate.demo.util.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String KEY_CURRENT_INDEX="currentIndexKey";

    private List<Class> mFragmentClassList= Arrays.asList(
            new Class[]{OneFragment.class,TwoFragment.class,ThreeFragment.class,FourFragment.class});

    private int mCurrentIndex=-1;
    private List<BaseFragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        initFragmentList(savedInstanceState);
        if(mCurrentIndex!=-1){
            changeFragment(mCurrentIndex);
        }else {
            changeFragment(0);
        }
    }

    private void initViews() {
    }

    private void initFragmentList(Bundle savedInstanceState) {
        mFragmentList=new ArrayList<>();
        BaseFragment fragment=null;
        if(savedInstanceState!=null){
            for (Class clazz : mFragmentClassList){
                fragment= (BaseFragment) getSupportFragmentManager().findFragmentByTag(clazz.getSimpleName());
                if(fragment==null){
                    try {
                        fragment= (BaseFragment) clazz.newInstance();
                    } catch (Exception e) {
                        MyLog.e(tag,e);
                    }
                }
                mFragmentList.add(fragment);
            }
            mCurrentIndex=savedInstanceState.getInt(KEY_CURRENT_INDEX,-1);
        }else {
            for(Class clazz : mFragmentClassList){
                try {
                    fragment= (BaseFragment) clazz.newInstance();
                    mFragmentList.add(fragment);
                } catch (Exception e) {
                    MyLog.e(tag, e);
                }
            }
        }
    }

    private void changeFragment(int index){
        mCurrentIndex=index;
        UIUtils.changeFragment(getSupportFragmentManager()
            ,mFragmentList,index);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX,mCurrentIndex);
    }

    @OnClick(R.id.view_one)
    void clickViewOne(){
        changeFragment(0);
    }

    @OnClick(R.id.view_two)
    void clickViewTwo(){
        changeFragment(1);
    }

    @OnClick(R.id.view_three)
    void clickViewThree(){
        changeFragment(2);
    }

    @OnClick(R.id.view_four)
    void clickViewFour(){
        changeFragment(3);
    }

}
