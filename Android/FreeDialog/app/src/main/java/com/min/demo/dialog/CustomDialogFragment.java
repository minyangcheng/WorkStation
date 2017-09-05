package com.min.demo.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.min.demo.R;
import com.min.dialog.dialog.BaseDialogFragment;

import butterknife.OnClick;

public class CustomDialogFragment extends BaseDialogFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_custom;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void setParams(Dialog dialog) {
        setWidthScale(0.85f); //直接将布局中的最外层view的width和height变为此处设置的
        setHeightScale(0.3f);
        //设置dialog动画
//        dialog.getWindow().setWindowAnimations(R.style.PopupAnimation);
        dialog.setCanceledOnTouchOutside(false);
    }

    @OnClick({R.id.tv_cancel,R.id.tv_sure})
    void clickTvCancel(){
        this.dismiss();
    }

}
