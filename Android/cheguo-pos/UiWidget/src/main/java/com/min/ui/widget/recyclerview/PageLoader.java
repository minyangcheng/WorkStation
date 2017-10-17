package com.min.ui.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.min.ui.widget.R;

public class PageLoader extends RecyclerView.OnScrollListener {

    private static final String TAG = PageLoader.class.getSimpleName();

    private static final int TYPE_LOADING = 1;
    private static final int TYPE_LOAD_FIAL = 2;
    private static final int TYPE_END = 3;

    private Context mContext;
    private LinearLayoutManager mLayoutManager;
    private HFRecyclerViewAdapter mAdapter;

    private OnLoadListener mLoadListener;

    private View mFooterView;
    private FooterHolder mHolder;

    private boolean mIsLoading;
    private boolean mEnable = true;

    private boolean mShouldShowFinishFooter;

    public PageLoader(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new RuntimeException("recycleView must not be null in pageloader");
        }
        mContext = recyclerView.getContext();
        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (mLayoutManager == null) {
            throw new RuntimeException("must set layoutManager before init pageloader");
        }

        mAdapter = (HFRecyclerViewAdapter) recyclerView.getAdapter();
        if (mAdapter == null) {
            throw new RuntimeException("must set adapter before init pageloader");
        }
        recyclerView.addOnScrollListener(this);
        mFooterView = LayoutInflater.from(mContext).inflate(R.layout.item_footer_base_rl, recyclerView, false);
        mHolder = new FooterHolder(mFooterView);
        mHolder.loadFailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoadListener != null) {
                    setLoadStart();
                }
            }
        });
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        int totalItemCount = mLayoutManager.getItemCount();
        if (mLoadListener != null && mEnable && !mIsLoading && lastVisibleItem >= totalItemCount - 1 && dy > 0) {
            setLoadStart();
        }
    }

    public void setLoadStart() {
        showType(TYPE_LOADING);
        mAdapter.setFooterView(mFooterView);
        mIsLoading = true;
        mLoadListener.onLoad();
    }

    public void setLoadFail() {
        showType(TYPE_LOAD_FIAL);
    }

    public void setLoadSuccess() {
        mAdapter.removeFooter();
        mIsLoading = false;
    }

    public void setLoadFianlly(boolean isFinish) {
        mEnable = !isFinish;
        if (isFinish && mShouldShowFinishFooter) {
            showType(TYPE_END);
            mAdapter.setFooterView(mFooterView);
        }
    }

    public void setShouldShowFinishFooter(boolean showFinishFooter) {
        mShouldShowFinishFooter = showFinishFooter;
    }

    public void setLoadListener(OnLoadListener loadListener) {
        mLoadListener = loadListener;
    }

    public interface OnLoadListener {
        public void onLoad();
    }

    private void showType(int type) {
        switch (type) {
            case TYPE_LOADING: //正在加载
                mHolder.progressBar.setVisibility(View.VISIBLE);
                mHolder.loadingView.setVisibility(View.VISIBLE);
                mHolder.loadFailView.setVisibility(View.GONE);
                mHolder.endView.setVisibility(View.GONE);
                break;
            case TYPE_LOAD_FIAL:  //加载失败
                mHolder.progressBar.setVisibility(View.GONE);
                mHolder.loadingView.setVisibility(View.GONE);
                mHolder.loadFailView.setVisibility(View.VISIBLE);
                mHolder.endView.setVisibility(View.GONE);
                break;
            case TYPE_END:  //到底了
                mHolder.progressBar.setVisibility(View.GONE);
                mHolder.loadingView.setVisibility(View.GONE);
                mHolder.loadFailView.setVisibility(View.GONE);
                mHolder.endView.setVisibility(View.VISIBLE);
                break;
            default:
                mHolder.progressBar.setVisibility(View.GONE);
                mHolder.loadingView.setVisibility(View.GONE);
                mHolder.loadFailView.setVisibility(View.GONE);
                mHolder.endView.setVisibility(View.GONE);
                break;
        }
    }

    public class FooterHolder {

        View progressBar;
        View loadingView;
        View loadFailView;
        View endView;

        public FooterHolder(View view) {
            progressBar = view.findViewById(R.id.pb_bar);
            loadingView = view.findViewById(R.id.tv_loading);
            loadFailView = view.findViewById(R.id.tv_fail);
            endView = view.findViewById(R.id.tv_end);
        }

    }

}