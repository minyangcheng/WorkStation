package com.min.ws.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.min.ws.R;
import com.min.ws.adapter.banner.BannerAdapter;
import com.min.ws.adapter.banner.NetworkImageHolderView;
import com.min.ws.util.DataUtils;
import com.min.ws.util.UIUtils;
import com.min.ws.view.banner.ConvenientBanner;
import com.min.ws.view.banner.holder.CBViewHolderCreator;
import com.min.ws.view.indicator.InfiniteCirclePageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecycleViewBannerActivity extends AppCompatActivity {

    @Bind(R.id.rv)
    RecyclerView mRv;
    @Bind(R.id.srf)
    SwipeRefreshLayout mSRf;

    private View mHeaderView;
    private ConvenientBanner mCbBanner;

    private BannerAdapter mBannerAdapter;

    private InfiniteCirclePageIndicator mCircleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_banner);
        ButterKnife.bind(this);
        initViews();
        initData();
    }

    private void initData() {
        mBannerAdapter.setData(DataUtils.getRecycleViewData());

        mCbBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new NetworkImageHolderView();
            }
        }, DataUtils.getAdImageList());
//        mCbBanner.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});

        setBannerParams();

        mBannerAdapter.setHeaderView(mHeaderView);
    }

    private void initViews() {
        initRecycleView();
        mHeaderView= LayoutInflater.from(this).inflate(R.layout.item_ad,null);
        mCbBanner= (ConvenientBanner) mHeaderView.findViewById(R.id.cb);
        mCircleIndicator= (InfiniteCirclePageIndicator) mHeaderView.findViewById(R.id.cpi);
        mSRf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });
    }

    private void initRecycleView() {
        LinearLayoutManager lm=new LinearLayoutManager(this);
        mRv.setLayoutManager(lm);
        mBannerAdapter=new BannerAdapter(this);
        mRv.setAdapter(mBannerAdapter);
    }

    private void updateData() {
        mRv.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBannerAdapter.setData(DataUtils.getUpdateRecycleViewData());

                mCbBanner.setPages(new CBViewHolderCreator() {
                    @Override
                    public Object createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, DataUtils.getUpdateAdImageList());

                setBannerParams();

                mSRf.setRefreshing(false);
            }
        }, 2000);
    }

    private void setBannerParams() {
        if (mCbBanner == null) return;
        int realCount = mCbBanner.getViewPager().getAdapter().getRealCount();
        mCircleIndicator.setPageCount(realCount);
        mCircleIndicator.setViewPager(mCbBanner.getViewPager());
        if(realCount<=1){
            mCbBanner.setCanLoop(false);
            mCbBanner.setManualPageable(false);
        }else{
            mCbBanner.setCanLoop(true);
            mCbBanner.setManualPageable(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCbBanner.startTurning(5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCbBanner.stopTurning();
    }
}
