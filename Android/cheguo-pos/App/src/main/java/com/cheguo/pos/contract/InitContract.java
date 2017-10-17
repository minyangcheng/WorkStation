package com.cheguo.pos.contract;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.min.core.base.AbstractBasePresenter;
import com.min.core.base.BaseView;

/**
 * Created by minyangcheng on 2017/9/20.
 */

public class InitContract {

    public interface View extends BaseView {
    }

    public static abstract class Presenter extends AbstractBasePresenter<View> {
        public abstract boolean check();

        public abstract void openInitSetting(Fragment fragment);

        public abstract void handleActivityResult(Intent data);
    }

}
