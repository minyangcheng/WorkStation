package com.min.vip.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.min.framework.base.ConfigConstants;
import com.min.framework.base.BaseActivity;
import com.min.framework.bean.BaseBean;
import com.min.framework.util.ToastUtils;
import com.min.framework.widget.CenterTitleToolbar;
import com.min.framework.widget.recyclerview.OnRecyclerItemClickListener;
import com.min.framework.widget.refresh.RefreshLoaderView;
import com.min.framework.widget.refresh.RxRefreshLoader;
import com.min.vip.R;
import com.min.vip.adapter.VipListAdapter;
import com.min.vip.app.HttpFactory;
import com.min.vip.bean.OrderRespBean;

import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.functions.Func1;

public class RefreshLoaderActivity extends BaseActivity {

    private static final String TAG="MainActivity_TEST";

    @Bind(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @Bind(R.id.rlv)
    RefreshLoaderView mRlv;

    private RxRefreshLoader<OrderRespBean> mRxRefreshLoader;

    String loantype="2";
    String companyid="2649";
    String dealeruserid="12093";
    int pageSize= ConfigConstants.PAGE_SIZE;
    String assetstatus="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(mToolbar);

        VipListAdapter orderAdapter=new VipListAdapter(this);
        mRxRefreshLoader=new RxRefreshLoader(mRlv, orderAdapter, true, new Func1<Integer, Observable<BaseBean<List<OrderRespBean>>>>() {
            @Override
            public Observable<BaseBean<List<OrderRespBean>>> call(Integer integer) {
                return HttpFactory.provideApiService().getOrderList(loantype, companyid, dealeruserid, pageSize, assetstatus, integer);
            }
        });
        mRxRefreshLoader.startLoad();

        mRxRefreshLoader.mListRv.addOnItemTouchListener(new OnRecyclerItemClickListener(mRxRefreshLoader.mListRv){

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                ToastUtils.showShortToast(getContext(), "pos=%s", position);
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh, int position) {
                ToastUtils.showShortToast(getContext(),"pos=%s",position);
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
        toolbar.setTitle("ListActivity");
        toolbar.inflateMenu(R.menu.menu_normal);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id=item.getItemId();
                switch (id){
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
        if(mRxRefreshLoader!=null){
            mRxRefreshLoader.destory();
        }
    }

}
