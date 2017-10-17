package com.cheguo.pos.contract;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.min.core.base.AbstractBasePresenter;
import com.min.core.base.BaseView;

/**
 * Created by minyangcheng on 2017/9/20.
 */

public class MainContract {

    public interface View extends BaseView {

    }

    public static abstract class Presenter extends AbstractBasePresenter<MainContract.View> {

        public abstract void pay(Fragment fragment, double amount, boolean t1Flag);

        public abstract void handleActivityResult(Context context, Intent data);
    }

}
