package com.cheguo.pos.contract;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.cheguo.pos.data.model.TransInfo;
import com.min.core.base.AbstractBasePresenter;
import com.min.core.base.BaseView;

/**
 * Created by minyangcheng on 2017/9/20.
 */

public class TransDetailContract {

    public interface View extends BaseView {
        void updoSuccess();
    }

    public static abstract class Presenter extends AbstractBasePresenter<TransDetailContract.View> {

        public abstract void print(Fragment fragment, String transNo);

        public abstract void updo(Fragment fragment, String transNo);

        public abstract void handleActivityResult(Context context, Intent data, TransInfo transInfo);
    }

}
