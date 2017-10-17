package com.cheguo.pos.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cheguo.pos.R;
import com.cheguo.pos.contract.InitContract;
import com.cheguo.pos.presenter.InitPresenter;
import com.cheguo.pos.util.Util;
import com.jakewharton.rxbinding.view.RxView;
import com.min.common.util.FragmentUtils;
import com.min.common.util.SpanUtils;
import com.min.core.base.BaseFragment;
import com.min.core.util.UIUtil;
import com.min.ui.widget.CenterTitleToolbar;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.floo.Floo;

/**
 * Created by minyangcheng on 2017/8/21.
 */

public class InitFragment extends BaseFragment implements InitContract.View {

    public static final int FROM_MAIN_ACTIVITY = 1;
    public static final int FROM_MAIN_FRAGMENT = 2;

    @BindView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @BindView(R.id.tv_explain)
    TextView mExplainTv;
    @BindView(R.id.tv_init)
    TextView mInitTv;

    private int mFromType;
    private int mFinishCount;

    private InitContract.Presenter mPresenter;

    public static InitFragment newInstance(int fromType) {
        InitFragment fragment = new InitFragment();
        fragment.setFromType(fromType);
        return fragment;
    }

    private void setFromType(int fromType) {
        this.mFromType = fromType;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_init;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new InitPresenter();
        mPresenter.attachView(this);

        initToolbar();
        SpanUtils spanUtils = new SpanUtils();
        spanUtils.append("一.系统参数设置").setFontSize(15, true).setForegroundColor(Color.parseColor("#333333"))
                .appendLine()
                .append("1.点击“初始化设置”，选择“商户参数设置”，输入终端设备号，点击“更新商户号和商户名称”按钮。").setFontSize(14, true).setForegroundColor(Color.parseColor("#666666"))
                .appendLine()
                .append("2.选择“终端密钥管理”，插入密钥卡，设备会自动下载密钥数据，下载完成后，点击“签到”按钮。").setFontSize(14, true).setForegroundColor(Color.parseColor("#666666"))
                .appendLine()
                .append("3.勾选初始化设置右侧的勾选按钮。").setFontSize(14, true).setForegroundColor(Color.parseColor("#666666"))
                .appendLine()
                .append("4.点击“初始化完成”按钮，完成初始化。").setFontSize(14, true).setForegroundColor(Color.parseColor("#666666"))
                .appendLine()
                .append("二.重新设置").setFontSize(15, true).setForegroundColor(Color.parseColor("#333333"))
                .appendLine()
                .append("在“收银台”界面，若需重新进行系统初始化设置，则连续点击“收银台”标题8次，系统将重新跳转至系统初始化界面。").setFontSize(14, true).setForegroundColor(Color.parseColor("#666666"));
        mExplainTv.setText(spanUtils.create());

        RxView.clicks(mInitTv)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe((Void) -> openInitSettingUI());
    }

    private void initToolbar() {
        if (mFromType == FROM_MAIN_FRAGMENT) {
            initToolbar(mToolbar);
        }
        mToolbar.setTitle(R.string.system_init);
        mToolbar.inflateMenu(R.menu.init);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_setting) {
                    Util.openSettingUi(getContext());
                }
                return false;
            }
        });
    }

    @OnClick(R.id.tv_info)
    public void clickInfoFinish() {
        mFinishCount++;
        if (mFinishCount % 5 == 0) {
            try {
                getActivity().finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.tv_web)
    void clickOpenWeb() {
        Floo.navigation(getContext(), "http://www.baidu.com")
                .start();
    }

    @OnClick(R.id.tv_order)
    void clickOpenOrder() {
        Floo.navigation(getContext(), "MainOrderActivity")
                .appendQueryParameter("company_name", "cheguo")
                .appendQueryParameter("user_id", "minych")
                .putExtra("isLogin", true)
                .start();

        UIUtil.getMainHandler().postDelayed(() -> {
            Floo.navigation(getContext(), "cg://cheguo.com/order")
                    .appendQueryParameter("company_name", "cheguo")
                    .appendQueryParameter("user_id", "minych")
                    .putExtra("isLogin", true)
                    .start();
        }, 300);
    }

    @OnClick(R.id.tv_done)
    void clickDoneTv() {
        if (mPresenter.check()) {
            if (mFromType == FROM_MAIN_ACTIVITY) {
                FragmentUtils.replaceFragment(getFragmentManager(), MainFragment.newInstance(), android.R.id.content, false);
            } else if (mFromType == FROM_MAIN_FRAGMENT) {
                getFragmentManager().popBackStackImmediate();
            }
        }
    }

    private void openInitSettingUI() {
        showHudDialog(false);
        mPresenter.openInitSetting(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        hideHudDailog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.handleActivityResult(data);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

}
