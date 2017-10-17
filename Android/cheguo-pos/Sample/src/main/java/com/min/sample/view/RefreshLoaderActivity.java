package com.min.sample.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.min.core.base.BaseActivity;
import com.min.core.bean.BaseBean;
import com.min.core.util.RxRefreshLoader;
import com.min.sample.R;
import com.min.sample.adapter.SampleListAdapter;
import com.min.sample.data.DataManager;
import com.min.sample.data.model.OrderBean;
import com.min.ui.widget.CenterTitleToolbar;
import com.min.ui.widget.Constants;
import com.min.ui.widget.recyclerview.OnRecyclerItemClickListener;
import com.min.ui.widget.refresh.RefreshLoaderView;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Func1;

public class RefreshLoaderActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @BindView(R.id.rlv)
    RefreshLoaderView mRlv;

    private RxRefreshLoader<OrderBean> mRxRefreshLoader;

    String loantype = "2";
    String companyid = "234";
    String dealeruserid = "14033";
    int pageSize = Constants.PAGE_SIZE;
    String assetstatus = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(mToolbar);

        SampleListAdapter orderAdapter = new SampleListAdapter(this);
        mRxRefreshLoader = new RxRefreshLoader(mRlv, orderAdapter, true, new Func1<Integer, Observable<BaseBean<List<OrderBean>>>>() {
            @Override
            public Observable<BaseBean<List<OrderBean>>> call(Integer integer) {
                return DataManager.getMobileService().getOrderList(loantype, companyid, dealeruserid, pageSize, assetstatus, integer);
            }
        });
        mRxRefreshLoader.startLoad();

        mRxRefreshLoader.mListRv.addOnItemTouchListener(new OnRecyclerItemClickListener(mRxRefreshLoader.mListRv) {

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh, int position) {
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_loader;
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        super.initToolbar(toolbar);
        toolbar.setTitle("SampleListActivity");
        toolbar.inflateMenu(R.menu.menu_normal);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.manual_refresh:
                        mRxRefreshLoader.manualRefresh();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRxRefreshLoader != null) {
            mRxRefreshLoader.destory();
        }
    }

}
