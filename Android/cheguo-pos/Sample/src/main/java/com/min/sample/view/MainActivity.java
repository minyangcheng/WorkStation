package com.min.sample.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import com.min.common.util.ToastUtils;
import com.min.core.base.BaseActivity;
import com.min.core.base.BaseDialog;
import com.min.core.base.BasePopupWindow;
import com.min.core.base.BaseView;
import com.min.core.util.PermissionUtil;
import com.min.core.util.UIUtil;
import com.min.sample.R;
import com.min.sample.presenter.TestPresenter;

import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BaseView {

    private TestPresenter mTestPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.requestAppPermission(this);
        mTestPresenter = new TestPresenter();
        mTestPresenter.attachView(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_hud)
    void clickHud() {
        showHudDialog();
    }

    @OnClick(R.id.btn_popup)
    void clickPopup(View view) {
        BasePopupWindow popupWindow = new BasePopupWindow(this) {

            @Override
            protected void onViewCreate(View view) {
                super.onViewCreate(view);
                view.findViewById(R.id.view_fl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }

            @Override
            protected int getLayoutId() {
                return R.layout.popup_order;
            }
        };
        popupWindow.showAsDropDown(view);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ToastUtils.showShort("popup window dismiss");
            }
        });
    }

    @OnClick(R.id.btn_dialog)
    void clickDialog() {
        final BaseDialog dialog = new BaseDialog(this) {
            @Override
            protected int getLayoutId() {
                return R.layout.dialog_order;
            }

            @Override
            protected void onViewCreate(View view) {
                super.onViewCreate(view);
                view.findViewById(R.id.view_fl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }
        };
        dialog.show();
    }

    @OnClick(R.id.btn_fragment_dialog)
    void clickFragmentDialog() {
        OrderDialogFragment dialogFragment = new OrderDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
    }

    @OnClick(R.id.btn_list)
    void clickHttp() {
        startActivity(new Intent(this, RefreshLoaderActivity.class));
    }

    @OnClick(R.id.btn_presenter)
    void clickPresenter() {
        mTestPresenter.startInt();
        UIUtil.getMainHandler().postDelayed(() -> {
            mTestPresenter.startStr();
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTestPresenter.detachView();
    }
}
