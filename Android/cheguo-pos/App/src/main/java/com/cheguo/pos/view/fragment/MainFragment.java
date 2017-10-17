package com.cheguo.pos.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cheguo.pos.R;
import com.cheguo.pos.contract.MainContract;
import com.cheguo.pos.data.DataManager;
import com.cheguo.pos.presenter.MainPresenter;
import com.cheguo.pos.util.FormatUtil;
import com.cheguo.pos.util.LocationInfoUtil;
import com.cheguo.pos.util.Util;
import com.cheguo.pos.view.activity.TransDetailListActivity;
import com.min.common.util.EmptyUtils;
import com.min.common.util.FragmentUtils;
import com.min.common.util.SpanUtils;
import com.min.core.base.BaseFragment;
import com.min.ui.widget.CenterTitleToolbar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by minyangcheng on 2017/8/21.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener, MainContract.View {

    @BindView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @BindView(R.id.et_amount)
    TextView mAmountTv;
    @BindView(R.id.tv_t1)
    TextView mT1Tv;
    @BindView(R.id.tv_d0)
    TextView mD0Tv;
    @BindView(R.id.gl_num)
    GridLayout mNumGl;

    private int mClickCount;

    private MainContract.Presenter mPresenter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new MainPresenter();
        mPresenter.attachView(this);

        LocationInfoUtil.getInstance(getContext()).startLocation();
        initViews();
    }

    private void initViews() {
        initToolbar();
        View view;
        for (int i = 0; i < mNumGl.getChildCount(); i++) {
            view = mNumGl.getChildAt(i);
            view.setOnClickListener(this);
        }
        setSettleUI(false);
        SpanUtils spanUtils = new SpanUtils();
        spanUtils.append("￥").setFontSize(23, true).setBold()
                .append("0.00").setFontSize(44, true).setBold();
        mAmountTv.setHint(spanUtils.create());
    }

    private void setSettleUI(boolean t1) {
        if (t1) {
            mT1Tv.setSelected(true);
            mD0Tv.setSelected(false);
        } else {
            mT1Tv.setSelected(false);
            mD0Tv.setSelected(true);
        }
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.crash_desk);
        mToolbar.inflateMenu(R.menu.main);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_bill) {
                    TransDetailListActivity.startActivity(getContext(), DataManager.getPreferencesHelper().getMerchantNo());
                }
                return false;
            }
        });
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickCount++;
                if (mClickCount % 8 == 0) {
                    FragmentUtils.addFragment(getFragmentManager(), InitFragment.newInstance(InitFragment.FROM_MAIN_FRAGMENT), android.R.id.content, false, true);
                }
            }
        });
    }

    @OnClick(R.id.tv_wifi)
    void clickWifiTv() {
        Util.openWifiUi(getContext());
    }

    @Override
    public void onClick(View v) {
        TextView tv = (TextView) v;
        String s = tv.getText().toString();
        switch (s) {
            case "清空":
                clear();
                break;
            case "退格":
                backspace();
                break;
            case "确认":
                sure();
                break;
            default:
                append(s);
                break;
        }
    }

    private void append(String s) {
        String preAmount = mAmountTv.getText().toString();
        preAmount = preAmount.replaceAll("￥", "");
        preAmount = preAmount.replaceAll(",", "");

        String result = "";
        String[] numArr = new String[2];
        String[] preArr = preAmount.split("\\.");
        if (preArr.length > 0) {
            numArr[0] = EmptyUtils.isEmpty(preArr[0]) ? "0" : preArr[0];
        } else {
            numArr[0] = "0";
        }
        if (preArr.length > 1) {
            numArr[1] = preArr[1];
        } else {
            numArr[1] = "";
        }
        if (s.equals(".")) {
            if (!preAmount.contains(".")) {
                result = FormatUtil.formatRmb(numArr[0], 2) + ".";
            } else {
                return;
            }
        } else {
            if (!preAmount.contains(".")) {
                s = numArr[0] + s;
                if (s.length() > 8) {
                    s = s.substring(0, 8);
                }
                result = FormatUtil.formatRmb(s, 2);
            } else {
                numArr[1] = numArr[1] + s;
                if (numArr[1].length() > 1) {
                    numArr[1] = numArr[1].substring(0, 2);
                }
                result = FormatUtil.formatRmb(numArr[0], 2) + "." + numArr[1];
            }
        }

        SpanUtils spanUtils = new SpanUtils();
        spanUtils.append("￥").setFontSize(23, true).setBold()
                .append(result).setFontSize(44, true).setBold();
        mAmountTv.setText(spanUtils.create());
    }

    private double getAmount() {
        String s = mAmountTv.getText().toString();
        if (EmptyUtils.isEmpty(s)) {
            return 0;
        }
        s = s.replaceAll("￥", "");
        s = s.replaceAll(",", "");
        if (!EmptyUtils.isEmpty(s) && s.endsWith(".")) {
            s = s.substring(0, s.length() - 1);
        }
        return Double.parseDouble(s);
    }

    private void clear() {
        mAmountTv.setText("");
    }

    private void backspace() {
        String nowStr = mAmountTv.getText().toString();
        nowStr = nowStr.replaceAll("￥", "");
        nowStr = nowStr.replaceAll(",", "");
        if (nowStr.length() == 0) {
            mAmountTv.setText("");
        } else {
            nowStr = nowStr.substring(0, nowStr.length() - 1);
            if (nowStr.length() == 0) {
                mAmountTv.setText("");
            } else {
                String result = "";
                String[] numArr = new String[2];
                String[] preArr = nowStr.split("\\.");
                numArr[0] = preArr[0];
                if (preArr.length > 1) {
                    numArr[1] = preArr[1];
                } else {
                    numArr[1] = "";
                }
                if (numArr[1].length() == 0) {
                    result = FormatUtil.formatRmb(numArr[0], 2);
                } else {
                    result = FormatUtil.formatRmb(numArr[0], 2) + "." + numArr[1];
                }
                SpanUtils spanUtils = new SpanUtils();
                spanUtils.append("￥").setFontSize(23, true).setBold()
                        .append(result).setFontSize(44, true).setBold();
                mAmountTv.setText(spanUtils.create());
            }
        }
    }

    private void sure() {
        double amount = getAmount();
        mPresenter.pay(this, amount, mT1Tv.isSelected());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.handleActivityResult(getContext(), data);
    }

    @OnClick(R.id.tv_t1)
    void clickT1Tv() {
        setSettleUI(true);
    }

    @OnClick(R.id.tv_d0)
    void clickd0Tv() {
        setSettleUI(false);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }
}
