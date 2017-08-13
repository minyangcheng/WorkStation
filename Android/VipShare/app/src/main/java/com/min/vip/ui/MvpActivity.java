package com.min.vip.ui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.min.framework.base.BaseActivity;
import com.min.framework.util.ClipboardUtils;
import com.min.framework.util.DialogUtils;
import com.min.framework.util.GsonUtil;
import com.min.framework.util.ImageUtils;
import com.min.framework.util.L;
import com.min.framework.util.StringUtils;
import com.min.framework.util.UIHelper;
import com.min.framework.widget.CenterTitleToolbar;
import com.min.framework.widget.StateLayout;
import com.min.framework.widget.divider.DividerItemDecoration;
import com.min.vip.R;
import com.min.vip.adapter.RefactorVipListAdapter;
import com.min.vip.bean.VipAccountBean;
import com.min.vip.bean.VipListRespBean;
import com.min.vip.contract.VipListContract;
import com.min.vip.presenter.VipListPresenter;
import com.min.vip.widget.adapter.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MvpActivity extends BaseActivity implements VipListContract.View{

    private static final String TAG="VipListActivity_TEST";

    @Bind(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @Bind(R.id.view_state)
    StateLayout mStateView;
    @Bind(R.id.view_refresh)
    SwipeRefreshLayout mRefhreshView;
    @Bind(R.id.rv)
    RecyclerView mRecyclerView;

    private RefactorVipListAdapter adapter;

    private VipListContract.Presenter mVipListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

        mVipListPresenter=new VipListPresenter(this);
        mVipListPresenter.getVipData(false);
    }

    private void initViews(){
        initToolbar(mToolbar);

        mStateView.setOnRetryListener(new StateLayout.OnRetryListener() {
            @Override
            public void retry() {
                mVipListPresenter.getVipData(false);
            }
        });

        mRefhreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mVipListPresenter.getVipData(true);
            }
        });

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        Drawable dividerDrawable= ImageUtils.getShapeDrawable(Color.TRANSPARENT,1);
        DividerItemDecoration decoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,dividerDrawable);
        mRecyclerView.addItemDecoration(decoration);

        adapter = new RefactorVipListAdapter(getContext());
        adapter.setMode(ExpandableRecyclerAdapter.MODE_NORMAL);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RefactorVipListAdapter.OnItemClickListener() {
            @Override
            public void onNormalClickListener(VipAccountBean vipAccountBean, int pos) {
                L.d(TAG,"vipAccountBean=%s , pos=%s", GsonUtil.toPrettyJson(vipAccountBean),pos);
                showCopyDialog(vipAccountBean);
            }
        });
    }

    private void showCopyDialog(final VipAccountBean bean){
        DialogUtils.showItemsDialog(this,R.string.copy_title, R.array.copyItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    ClipboardUtils.copyText(getContext(),bean.userAccount);
                } else if (which == 1) {
                    ClipboardUtils.copyText(getContext(),bean.userPass);
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        mToolbar.setTitle(R.string.app_name);
    }

    public void setDataToRvView(VipListRespBean bean) {
        List<ExpandableRecyclerAdapter.ListItem> items = new ArrayList<>();
        if(!StringUtils.isEmpty(bean.youKuList)){
            items.add(new RefactorVipListAdapter.HeaderItem("优酷"));
            items.addAll(bean.youKuList);
        }
        if(!StringUtils.isEmpty(bean.aiQiYiList)){
            items.add(new RefactorVipListAdapter.HeaderItem("爱奇艺"));
            items.addAll(bean.aiQiYiList);
        }
        if(!StringUtils.isEmpty(bean.xuLeiList)){
            items.add(new RefactorVipListAdapter.HeaderItem("迅雷"));
            items.addAll(bean.xuLeiList);
        }
        if(!StringUtils.isEmpty(bean.leShiList)){
            items.add(new RefactorVipListAdapter.HeaderItem("乐视"));
            items.addAll(bean.leShiList);
        }
        if(!StringUtils.isEmpty(bean.tuDouList)){
            items.add(new RefactorVipListAdapter.HeaderItem("土豆"));
            items.addAll(bean.tuDouList);
        }
        adapter.setItems(items);
        adapter.toggleExpandedItems(0, false);
    }

    @Override
    protected void onDestroy() {
        mVipListPresenter.onDestory();
        super.onDestroy();
    }

    @Override
    public void handleDoOnSubscribe(boolean isRefresh) {
        if (!isRefresh) {
            mStateView.showLoadingStatus();
        }
    }

    @Override
    public void handleOnError(boolean isRefresh,Throwable e) {
        if (!isRefresh) {
            mStateView.showErrorStatus();
        }else{
            UIHelper.handlerError(getContext(),e);
        }
        mRefhreshView.setRefreshing(false);
    }

    @Override
    public void handleOnNext(VipListRespBean vipListRespBean) {
        mStateView.showContentStatus();
        mRefhreshView.setRefreshing(false);
        setDataToRvView(vipListRespBean);
    }

}
