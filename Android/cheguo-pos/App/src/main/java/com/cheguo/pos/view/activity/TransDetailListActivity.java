package com.cheguo.pos.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cheguo.pos.R;
import com.cheguo.pos.app.AppEvent;
import com.cheguo.pos.data.DataManager;
import com.cheguo.pos.data.model.TransInfo;
import com.cheguo.pos.data.model.status.OrderStatus;
import com.cheguo.pos.util.FormatUtil;
import com.cheguo.pos.view.adapter.TransBillAdapter;
import com.cheguo.pos.view.dialog.TransDetailDialog;
import com.min.common.util.EmptyUtils;
import com.min.core.base.BaseActivity;
import com.min.core.bean.BaseBean;
import com.min.core.util.RxEventBus;
import com.min.core.util.RxRefreshLoader;
import com.min.ui.widget.CenterTitleToolbar;
import com.min.ui.widget.refresh.RefreshLoaderView;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Func1;

public class TransDetailListActivity extends BaseActivity {

    private static final String KEY_MECHANT_NO = "mechantNoKey";

    @BindView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @BindView(R.id.rlv)
    RefreshLoaderView mRlv;

    private String mMerchantNo;
    private RxRefreshLoader<TransInfo> mRxRefreshLoader;
    private TransBillAdapter mAdapter;

    private View mHeaderView;

    public static void startActivity(Context context, String merchantNo) {
        Intent intent = new Intent(context, TransDetailListActivity.class);
        intent.putExtra(KEY_MECHANT_NO, merchantNo);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trans_detail_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        RxEventBus.getInstance().filteredObservable(AppEvent.class)
                .filter(o -> o.filter(AppEvent.ChangeEventType.CONSUME_UPDO))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(o -> {
                    mRxRefreshLoader.manualRefresh();
                }, e -> {
                }, () -> {
                });
        initViews();
    }

    private void initViews() {
        initToolbar(mToolbar);
        mToolbar.setTitle(R.string.trans_bill);

        mAdapter = new TransBillAdapter(this);
        mRxRefreshLoader = new RxRefreshLoader(mRlv, mAdapter, false, new Func1<Integer, Observable<BaseBean<List<TransInfo>>>>() {
            @Override
            public Observable<BaseBean<List<TransInfo>>> call(Integer integer) {
                return DataManager.getMobileService().getTransInfoList(mMerchantNo);
            }
        }) {
            @Override
            protected void setRefreshDataSuccess(List receiveList) {
                super.setRefreshDataSuccess(receiveList);
                addMonthAccountHeader();
            }
        };

        mAdapter.setOnItemClickLitener((v, p) -> TransDetailDialog.show(getSupportFragmentManager(), mAdapter.getData().get(p)));

        mRxRefreshLoader.startLoad();
    }

    private void addMonthAccountHeader() {
        List<TransInfo> dataList = mAdapter.getData();
        if (EmptyUtils.isEmpty(dataList)) return;
        String nowTime = FormatUtil.getNowTime("yyyy-MM");
        long all = 0L;
        for (TransInfo info : dataList) {
            if (FormatUtil.getYearMonth(info.timestamp).equals(nowTime) && info.orderStatus == OrderStatus.SUCCESS) {
                all += info.amount;
            }
        }
        double amount = 1.0 * all / 100;
        BigDecimal bg = new BigDecimal(String.valueOf(amount));
        String amountStr = bg.toPlainString();
        if (mAdapter.hasHeader()) {
            TextView monthTv = (TextView) mHeaderView.findViewById(R.id.tv_month_money);
            monthTv.setText("￥" + amountStr + "元");
        } else {
            mHeaderView = LayoutInflater.from(this).inflate(R.layout.item_trans_bill_header, mRlv.getRecyclerView(), false);
            TextView monthTv = (TextView) mHeaderView.findViewById(R.id.tv_month_money);
            monthTv.setText("￥" + amountStr + "元");
            mAdapter.setHeaderView(mHeaderView);
        }
    }

    private void getDataFromIntent() {
        mMerchantNo = getIntent().getStringExtra(KEY_MECHANT_NO);
    }

    @Override
    protected void onDestroy() {
        if (mRxRefreshLoader != null) {
            mRxRefreshLoader.destory();
        }
        super.onDestroy();
    }

}
