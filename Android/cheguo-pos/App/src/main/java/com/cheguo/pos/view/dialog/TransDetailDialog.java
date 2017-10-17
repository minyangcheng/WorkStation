package com.cheguo.pos.view.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.cheguo.pos.R;
import com.cheguo.pos.app.AppEvent;
import com.cheguo.pos.contract.TransDetailContract;
import com.cheguo.pos.data.DataManager;
import com.cheguo.pos.data.model.TransInfo;
import com.cheguo.pos.data.model.status.OrderStatus;
import com.cheguo.pos.data.model.status.SettleTypeStatus;
import com.cheguo.pos.presenter.TransDetailPresenter;
import com.cheguo.pos.util.FormatUtil;
import com.min.common.util.EmptyUtils;
import com.min.core.base.BaseDialogFragment;
import com.min.core.util.RxEventBus;
import com.min.ui.widget.CenterTitleToolbar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by minyangcheng on 2017/8/23.
 */

public class TransDetailDialog extends BaseDialogFragment implements TransDetailContract.View {

    @BindView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @BindView(R.id.tv_money)
    TextView mMoneyTv;
    @BindView(R.id.tv_status)
    TextView mStatusTv;
    @BindView(R.id.tv_name)
    TextView mNameTv;
    @BindView(R.id.tv_pay_type)
    TextView mPayTypeTv;
    @BindView(R.id.tv_card)
    TextView mCardTv;
    @BindView(R.id.tv_order)
    TextView mOrderTv;
    @BindView(R.id.tv_time)
    TextView mTimeTv;
    @BindView(R.id.tv_updo)
    TextView mUpdoTv;
    @BindView(R.id.tv_print)
    TextView mPrintTv;

    @BindView(R.id.view_card)
    View mCardView;
    @BindView(R.id.view_order)
    View mOrderView;

    private TransInfo mTransInfo;
    private TransDetailContract.Presenter mPresenter;

    public static void show(FragmentManager fragmentManager, TransInfo transInfo) {
        TransDetailDialog dialog = new TransDetailDialog();
        dialog.setTransInfo(transInfo);
        dialog.show(fragmentManager, TransDetailDialog.class.getSimpleName());
    }

    public TransDetailDialog() {
        setStyle(STYLE_NORMAL, R.style.dialogMatch);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_trans_detail;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new TransDetailPresenter();
        mPresenter.attachView(this);

        initViews();
    }

    private void initViews() {
        initToolbar(mToolbar);
        mToolbar.setTitle(R.string.trans_detail);
        mMoneyTv.setText((1.0 * mTransInfo.amount / 100) + "元");
        mStatusTv.setText(mTransInfo.orderStatus.getStr());
        mNameTv.setText(DataManager.getPreferencesHelper().getMerchantName());
        mPayTypeTv.setText(mTransInfo.payType.getStr());
        mTimeTv.setText(FormatUtil.formatTimeStr(mTransInfo.timestamp, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm"));

        if (EmptyUtils.isEmpty(mTransInfo.pan)) {
            mCardView.setVisibility(View.GONE);
        } else {
            mCardView.setVisibility(View.VISIBLE);
            mCardTv.setText(mTransInfo.pan);
        }
        if (EmptyUtils.isEmpty(mTransInfo.traceNo)) {
            mOrderView.setVisibility(View.GONE);
        } else {
            mOrderView.setVisibility(View.VISIBLE);
            mOrderTv.setText(mTransInfo.traceNo);
        }

        if (mTransInfo.orderStatus == OrderStatus.SUCCESS) {
            mUpdoTv.setVisibility(mTransInfo.settleType == SettleTypeStatus.T1 ? View.VISIBLE : View.GONE);
            mPrintTv.setVisibility(View.VISIBLE);
        } else {
            mUpdoTv.setVisibility(View.GONE);
            mPrintTv.setVisibility(View.GONE);
        }
    }

    public void setTransInfo(TransInfo transInfo) {
        this.mTransInfo = transInfo;
    }

    @OnClick(R.id.tv_updo)
    void clickUpdo() {
        mPresenter.updo(this, mTransInfo.traceNo);
    }

    @OnClick(R.id.tv_print)
    void clickPrint() {
        mPresenter.print(this, mTransInfo.traceNo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.handleActivityResult(getContext(), data, mTransInfo);
    }

    @Override
    public void updoSuccess() {
        mStatusTv.setText("撤销成功");
        mUpdoTv.setVisibility(View.GONE);
        RxEventBus.getInstance().post(AppEvent.newInstance(AppEvent.ChangeEventType.CONSUME_UPDO));
    }
}
