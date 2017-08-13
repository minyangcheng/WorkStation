package com.min.vip.contract;

import com.min.vip.app.mvp.BaseModle;
import com.min.vip.app.mvp.BasePresenter;
import com.min.vip.app.mvp.BaseView;
import com.min.vip.bean.VipListRespBean;

/**
 * Created by minyangcheng on 2016/10/13.
 */
public interface VipListContract {

    interface View extends BaseView {
        void handleDoOnSubscribe(boolean isRefresh);
        void handleOnError(boolean isRefresh,Throwable e);
        void handleOnNext(VipListRespBean vipListRespBean);
    }

    interface Presenter extends BasePresenter {
        void getVipData(boolean isRefresh);
    }

    interface Modle extends BaseModle{
    }

}