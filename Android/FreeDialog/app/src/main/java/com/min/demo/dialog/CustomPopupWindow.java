package com.min.demo.dialog;

import android.content.Context;
import android.view.View;

import com.min.demo.R;
import com.min.dialog.dialog.BasePopupWindow;

public class CustomPopupWindow extends BasePopupWindow {

    public CustomPopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popwin_custom;
    }

    @Override
    protected void onViewCreate(View rootView) {
        super.onViewCreate(rootView);
    }

    @Override
    protected void setParams() {
        setWidthScale(1f);  //直接将布局中的最外层view的width和height变为此处设置的
        setHeightScale(1f);
        setAnimationStyle(R.style.PopupAnimation);
    }
}
