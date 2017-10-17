package com.min.core.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.min.core.R;
import com.min.ui.widget.HudDialog;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends RxFragment {

    public String tag;

    protected boolean mIsVisible;
    protected boolean mIsPrepared;

    protected Context mContext;

    private HudDialog mHudDialog;

    private Unbinder mUnbinder;

    public BaseFragment() {
        tag = this.getClass().getSimpleName();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(tag, this.getClass().getSimpleName() + "  onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() > 0) {
            View view = LayoutInflater.from(getContext()).inflate(getLayoutId(), container, false);
            mUnbinder = ButterKnife.bind(this, view);
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract int getLayoutId();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(tag, this.getClass().getSimpleName() + "  onViewCreated");
        mIsPrepared = true;
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
        Log.d(tag, this.getClass().getSimpleName() + "  onPause");
    }

    @Override
    public void onDestroyView() {
        mIsPrepared = false;
        mIsVisible = false;
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(tag, this.getClass().getSimpleName() + "  onDestroy");
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

    protected void initToolbar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getFragmentManager().popBackStackImmediate()) {
                    getActivity().finish();
                }
            }
        });
    }

    protected void judgeLoad() {
        if (mIsPrepared && mIsVisible) {
            lazyLoad();
        }
    }

    protected void lazyLoad() {
        Log.d(tag, this.getClass().getSimpleName() + "  lazyLoad");
    }

    protected void showHudDialog() {
        showHudDialog(true);
    }

    protected void showHudDialog(boolean canCancel) {
        if (mHudDialog == null) {
            mHudDialog = HudDialog.createProgressHud(getContext(), null, canCancel, null);
        }
        if (!mHudDialog.isShowing()) {
            mHudDialog.show();
        }
    }

    protected void hideHudDailog() {
        if (mHudDialog != null && mHudDialog.isShowing()) {
            mHudDialog.dismiss();
        }
    }

}
