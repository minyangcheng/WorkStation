package com.min.ws.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.min.ws.R;
import com.min.ws.util.L;
import com.min.ws.util.UIUtils;
import com.min.ws.view.observable.ObservableScrollView;
import com.min.ws.view.observable.ObservableScrollViewCallbacks;
import com.min.ws.view.observable.ScrollState;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ObservableScrollActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private static final String TAG="ObservableScrollActivity_Test";

    @Bind(R.id.osv)
    ObservableScrollView mOsv;
    @Bind(R.id.view_title_bar)
    View mTitlebarView;
    @Bind(R.id.view_header)
    View mHeaderView;

    private float mHeaderHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable_scroll);
        ButterKnife.bind(this);
        mOsv.addScrollViewCallbacks(this);

        UIUtils.addOnGlobalLayoutListener(mHeaderView, new Runnable() {
            @Override
            public void run() {
                mHeaderHeight=mHeaderView.getHeight();
                L.d(TAG,"mHeaderHeight=%s",mHeaderHeight);
            }
        });
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
        L.d(TAG,"onUpOrCancelMotionEvent scrollState=%s",scrollState);
    }

}
