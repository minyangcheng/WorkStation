package com.min.sample.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.min.core.base.BaseDialogFragment;
import com.min.sample.R;

import butterknife.OnClick;

/**
 * Created by minyangcheng on 2017/9/19.
 */

public class OrderDialogFragment extends BaseDialogFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_order;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.view_fl)
    void clickView() {
        dismiss();
    }

}