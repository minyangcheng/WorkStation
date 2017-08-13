package com.min.savestate.demo.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.min.savestate.demo.util.MyLog;

/**
 * Created by minyangcheng on 2016/5/21.
 */
public class BaseFragment extends Fragment {

    public String tag;

    public BaseFragment(){
        tag=this.getClass().getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.i(tag,"onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        MyLog.i(tag, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        MyLog.i(tag, "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyLog.i(tag, "onDestroyView");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        MyLog.i(tag,"onHiddenChanged...hidden=%s",hidden);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyLog.i(tag, "onSaveInstanceState");
    }
}
