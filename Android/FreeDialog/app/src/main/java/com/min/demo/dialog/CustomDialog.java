package com.min.demo.dialog;

import android.content.Context;
import android.view.View;

import com.min.demo.R;
import com.min.demo.anim.EnterAnimator;
import com.min.demo.anim.ExitAnimator;
import com.min.dialog.dialog.BaseDialog;

public class CustomDialog extends BaseDialog {

    public CustomDialog(Context context) {
        super(context);
    }

    @Override
    public void setParam() {
        super.setParam();
        setWidthScale(1f);
        setHeightScale(1f);
        showAnim(new EnterAnimator());
        dismissAnim(new ExitAnimator());
        setCanceledOnTouchOutside(false);
        getWindow().setWindowAnimations(R.style.dialogAlphaAnimStyle);
        dimEnabled(false);  //背景透明，此方法可以设置dialog背景是否透明
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_anim;
    }

    @Override
    public void onViewCreate(View contentView) {
        super.onViewCreate(contentView);
    }

}
