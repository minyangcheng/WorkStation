package com.min.vip.presenter;
import com.min.framework.util.RxTransformsHelper;
import com.min.vip.app.HttpFactory;
import com.min.vip.bean.VipListRespBean;
import com.min.vip.contract.VipListContract;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.CompositeSubscription;

public class VipListPresenter implements VipListContract.Presenter{

    private CompositeSubscription mCompositeSubscription;

    private VipListContract.View view;

    public VipListPresenter(VipListContract.View view){
        this.view=view;
        mCompositeSubscription=new CompositeSubscription();
    }

    @Override
    public void onDestory() {
        if(mCompositeSubscription!=null&&!mCompositeSubscription.isUnsubscribed()){
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void getVipData(final boolean isRefresh) {
        Subscription subscription=HttpFactory.provideApiService()
                .getVipListData()
                .compose(RxTransformsHelper.<VipListRespBean>handleServerResult())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.handleDoOnSubscribe(isRefresh);
                    }
                })
                .subscribe(new Subscriber<VipListRespBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.handleOnError(isRefresh,e);
                    }

                    @Override
                    public void onNext(VipListRespBean vipListRespBean) {
                        view.handleOnNext(vipListRespBean);
                    }
                });

        mCompositeSubscription.add(subscription);
    }

}