package com.min.ui.widget.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.min.ui.widget.Constants;
import com.min.ui.widget.R;
import com.min.ui.widget.StateLayout;
import com.min.ui.widget.recyclerview.BaseRecyclerViewAdapter;
import com.min.ui.widget.recyclerview.PageLoader;

import java.util.List;

/**
 * Created by minyangcheng on 2016/7/29.
 */
public abstract class RefreshLoaderDelegate<DATA> {

    protected static final int STATE_CONTENT = 1;
    protected static final int STATE_EMPTY = 2;
    protected static final int STATE_EROOR = 3;
    protected static final int STATE_LOADING = 4;

    public RefreshLoaderView mRefreshLoaderView;
    public StateLayout mStateView;
    public SwipeRefreshLayout mRefreshView;
    public RecyclerView mListRv;

    protected BaseRecyclerViewAdapter<DATA> mAdapter;
    protected PageLoader mPageLoader;

    protected int mNextPage = 0;
    protected int mState;

    protected Context mContext;
    protected boolean mPageEnable;

    protected boolean mIsDestory;

    public RefreshLoaderDelegate(RefreshLoaderView view, BaseRecyclerViewAdapter adapter, boolean pageEnable) {
        if (view == null) {
            throw new IllegalArgumentException("refreshLoaderView 参数不能为空");
        }
        if (adapter == null) {
            throw new IllegalArgumentException("adapter 参数不能为空");
        }
        mContext = view.getContext();
        mRefreshLoaderView = view;
        mAdapter = adapter;
        mPageEnable = pageEnable;
        getViewsFromRefreshLoaderView();
    }

    protected void getViewsFromRefreshLoaderView() {
        mStateView = mRefreshLoaderView.getStateLayout();
        mRefreshView = mRefreshLoaderView.getSwipeRefreshLayout();
        mListRv = mRefreshLoaderView.getRecyclerView();
    }

    public void startLoad() {
        initViews();
        initData();
    }

    public void manualRefresh() {
        mRefreshView.setRefreshing(true);
        onRefreshData();
    }

    private void initData() {
        changeState(STATE_LOADING);
        onRefreshData();
    }

    protected void initViews() {
        mRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshData();
            }
        });
        mStateView.setOnRetryListener(new StateLayout.OnRetryListener() {
            @Override
            public void retry() {
                initData();
            }
        });

        mListRv.setAdapter(mAdapter);
        initRecycleView();

        if (mPageEnable && mPageLoader == null) {
            mPageLoader = new PageLoader(mListRv);
            mPageLoader.setShouldShowFinishFooter(true);
            //如果分页
            mPageLoader.setLoadListener(new PageLoader.OnLoadListener() {
                @Override
                public void onLoad() {
                    onLoadMoreData();
                }
            });
        }
    }

    protected void initRecycleView() {
        mRefreshLoaderView.initWithLinearLayout();
    }

    protected abstract void onRefreshData();

    protected abstract void onLoadMoreData();

    protected void setRefreshDataSuccess(List<DATA> receiveList) {
        if (receiveList == null || receiveList.size() == 0) {
            changeState(STATE_EMPTY);
        } else {
            changeState(STATE_CONTENT);
            mAdapter.setData(receiveList);
        }
        judgeLoadFinally(receiveList);
        if (mRefreshView.isRefreshing()) {
            mRefreshView.setRefreshing(false);
        }

        if (mPageEnable) {
            mNextPage = 1;
        }
    }

    protected void setRefreshDataFail(Throwable throwable) {
        if (mAdapter.getData() == null || mAdapter.getData().size() == 0) {
            changeState(STATE_EROOR);
        } else {
            Toast.makeText(mContext, R.string.refresh_fail, Toast.LENGTH_SHORT).show();
        }
        if (mRefreshView.isRefreshing()) {
            mRefreshView.setRefreshing(false);
        }
    }

    protected void setLoadMoreDataSuccess(List<DATA> receiveList) {
        if (receiveList != null && receiveList.size() > 0) {
            mAdapter.getData().addAll(receiveList);
            mAdapter.notifyDataSetChanged();

            mNextPage++;
        }
        if (mPageLoader != null) {
            mPageLoader.setLoadSuccess();
        }
        judgeLoadFinally(receiveList);
    }

    protected void setLoadLoadMoreFail(Throwable throwable) {
        if (mPageLoader != null) mPageLoader.setLoadFail();
    }

    protected void judgeLoadFinally(List<DATA> receiveList) {
        if (mPageLoader == null) return;
        if (receiveList == null || receiveList.size() < Constants.PAGE_SIZE) {
            mPageLoader.setLoadFianlly(true);
        } else {
            mPageLoader.setLoadFianlly(false);
        }
    }

    protected void changeState(int state) {
        if (mState == state) return;
        switch (state) {
            case STATE_CONTENT:
                mStateView.showContentStatus();
                break;
            case STATE_LOADING:
                mStateView.showLoadingStatus();
                break;
            case STATE_EMPTY:
                mStateView.showEmptyStatus();
                break;
            case STATE_EROOR:
                mStateView.showErrorStatus();
                break;
        }
    }

    public void destory() {
        mIsDestory = true;
        if (mPageLoader != null) {
            mListRv.removeOnScrollListener(mPageLoader);
        }
    }

}
