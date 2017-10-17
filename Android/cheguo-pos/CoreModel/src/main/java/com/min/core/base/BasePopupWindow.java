package com.min.core.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BasePopupWindow extends PopupWindow {

    protected String tag;

    protected Context mContext;

    private View mRootView;

    private OnDismissListener mListener;

    private Unbinder mUnbinder;

    public BasePopupWindow(Context context) {
        this(context, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public BasePopupWindow(Context context, int width, int height) {
        super(context);
        tag = this.getClass().getSimpleName();
        mContext = context;
        setWidth(width);
        setHeight(height);
        init();
    }

    private void init() {
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mRootView = onCreateView();
        if (mRootView != null) {
            onViewCreate(mRootView);
            setContentView(mRootView);
        }
    }

    protected View onCreateView() {
        View view = null;
        if (getLayoutId() > 0) {
            view = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
            mUnbinder = ButterKnife.bind(this, view);
            super.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (mUnbinder != null) {
                        mUnbinder.unbind();
                    }
                    if (mListener != null) {
                        mListener.onDismiss();
                    }
                }
            });
        }
        return view;
    }

    protected abstract int getLayoutId();

    protected void onViewCreate(View view) {
    }

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mListener = onDismissListener;
    }
}
