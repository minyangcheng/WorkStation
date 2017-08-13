package com.min.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.min.framework.util.L;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    public String tag;

    protected boolean mIsVisible;
    protected boolean mIsPrepared;

    protected Context mContext;

    protected View mContentView;

    public BaseFragment(){
        tag=this.getClass().getSimpleName();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(tag, this.getClass().getSimpleName() + "  setUserVisibleHint-->" + getUserVisibleHint());
        if (getUserVisibleHint()) {
            mIsVisible = true;
            judgeLoad();
        } else {
            mIsVisible = false;
        }
    }

    protected void judgeLoad(){
        if(mIsPrepared&&mIsVisible){
            lazyLoad();
        }
    }

    protected void lazyLoad(){
        Log.d(tag, this.getClass().getSimpleName() + "  lazyLoad");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d(tag, this.getClass().getSimpleName() + "  onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getLayoutId()>0){
            mContentView=LayoutInflater.from(mContext).inflate(getLayoutId(),container,false);
            ButterKnife.bind(this, mContentView);
            return mContentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract int getLayoutId();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.d(tag, this.getClass().getSimpleName() + "  onViewCreated");
        mIsPrepared =true;
        judgeLoad();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(tag, this.getClass().getSimpleName() + "  onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        L.d(tag, this.getClass().getSimpleName() + "  onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsPrepared=false;
        mIsVisible=false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d(tag, this.getClass().getSimpleName() + "  onDestroy");
    }

}
