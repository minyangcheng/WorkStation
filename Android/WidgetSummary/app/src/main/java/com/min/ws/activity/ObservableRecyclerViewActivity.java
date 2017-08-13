package com.min.ws.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.min.ws.R;
import com.min.ws.adapter.AnimalAdapter;
import com.min.ws.util.L;
import com.min.ws.util.UIUtils;
import com.min.ws.view.observable.ObservableRecyclerView;
import com.min.ws.view.observable.ObservableScrollViewCallbacks;
import com.min.ws.view.observable.ScrollState;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ObservableRecyclerViewActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    public static final String TAG="ObservableRecyclerViewActivity_TEST";

    @Bind(R.id.rv)
    ObservableRecyclerView mRv;
    @Bind(R.id.view_title_bar)
    View mTitlebarView;

    private View mHeaderView;
    private float mHeaderHeight;

    private AnimalAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable_recycler_view);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new AnimalAdapter(this);
        mRv.setLayoutManager(mLayoutManager);
        mRv.setAdapter(mAdapter);

        mHeaderView= LayoutInflater.from(this).inflate(R.layout.item_header,mRv,false);
        mAdapter.setHeaderView(mHeaderView);
        initData();

        UIUtils.addOnGlobalLayoutListener(mHeaderView, new Runnable() {
            @Override
            public void run() {
                mHeaderHeight = mHeaderView.getHeight();
                L.d(TAG, "mHeaderHeight=%s", mHeaderHeight);
            }
        });
        mRv.addScrollViewCallbacks(this);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        L.d(TAG,"onScrollChanged scrollY=%s , firstScroll=%s dragging=%s",scrollY,firstScroll,dragging);

        float fraction=0;
        if(scrollY>=0&&scrollY<=mHeaderHeight){
            fraction=scrollY/mHeaderHeight;
        }
        if(scrollY>mHeaderHeight){
            fraction=1;
        }
        mTitlebarView.setAlpha(fraction);
    }

    @Override
    public void onDownMotionEvent() {
        L.d(TAG,"onDownMotionEvent");
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        L.d(TAG, "onUpOrCancelMotionEvent scrollState=%s", scrollState);
    }

    private void initData() {
        mAdapter.setData(getFakeData());
    }

    private List<String> getFakeData(){
        List<String> dataList=new ArrayList<>();
        for(int i=0;i<40;i++){
            dataList.add("----"+i+"---");
        }
        return dataList;
    }

}
