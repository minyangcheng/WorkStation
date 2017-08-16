package com.min.framework.widget.refresh;

import com.min.framework.bean.BaseBean;
import com.min.framework.util.RxTransformsHelper;
import com.min.framework.widget.recyclerview.BaseRecyclerViewAdapter;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by minyangcheng on 2016/7/29.
 */
public class RxRefreshLoader<DATA> extends RefreshLoaderDelegate<DATA> {

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private Func1<Integer, Observable<BaseBean<List<DATA>>>> mFun1;

    public RxRefreshLoader(RefreshLoaderView refreshLoaderView
            , BaseRecyclerViewAdapter adapter
            , boolean pageEnable
            , Func1 func1) {
        super(refreshLoaderView, adapter, pageEnable);
        this.mFun1 = func1;
    }

    @Override
    protected void onRefreshData() {
        loadData(true, 0);
    }

    @Override
    protected void onLoadMoreData() {
        loadData(false, mNextPage);
    }

    protected void loadData(final boolean isRefresh, int page) {
        Subscription subscription = Observable.just(page)
                .flatMap(mFun1)
                .compose(RxTransformsHelper.<List<DATA>>handleServerResult())
                .subscribe(new Action1<List<DATA>>() {
                    @Override
                    public void call(List<DATA> datas) {
                        if (mIsDestory) {
                            return;
                        }
                        if (isRefresh) {
                            setRefreshDataSuccess(datas);
                        } else {
                            setLoadMoreDataSuccess(datas);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mIsDestory) {
                            return;
                        }
                        if (isRefresh) {
                            setRefreshDataFail(throwable);
                        } else {
                            setLoadLoadMoreFail(throwable);
                        }
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void destory() {
        super.destory();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
