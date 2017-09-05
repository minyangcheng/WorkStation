package com.min.seed.ui;

import android.content.Intent;
import android.os.Bundle;

import com.min.framework.base.BaseActivity;
import com.min.framework.rx.RxEventBus;
import com.min.framework.util.GsonUtil;
import com.min.framework.widget.CenterTitleToolbar;
import com.min.seed.R;
import com.min.seed.app.AppEvent;
import com.min.seed.db.bean.SearchBean;
import com.min.seed.db.delegate.SearchDaoDelegate;
import com.min.seed.util.LocationInfoUtil;
import com.min.seed.util.TerminalInfoUtil;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("main");
        RxEventBus.getInstance().filteredObservable(AppEvent.class)
                .filter(o -> o.filter(AppEvent.ChangeEventType.LOCATION))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .doOnUnsubscribe(() -> Timber.d("activity auto unsubscribe"))
                .subscribe(o -> {
                    Timber.d(o.getData().toString());
                }, e -> {

                }, () -> {

                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_list_sample)
    void clickListSample() {
        startActivity(new Intent(this, RefreshLoaderActivity.class));
    }

    @OnClick(R.id.btn_terminal)
    void clickTerminal() {
        Timber.d("teiminal info=%s", GsonUtil.toPrettyJson(TerminalInfoUtil.getInstance().getTerminalInfo()));
    }

    @OnClick(R.id.btn_location)
    void clickLocation() {
        LocationInfoUtil.getInstance(this).startLocation();
    }

    @OnClick(R.id.btn_event)
    void clickEvent() {
        RxEventBus.getInstance().post(AppEvent.newInstance(AppEvent.ChangeEventType.LOCATION, "this is location"));
        RxEventBus.getInstance().post(AppEvent.newInstance(AppEvent.ChangeEventType.REFRESH, "this is refresh"));
    }

    @OnClick(R.id.btn_db)
    void clickDb() {
        SearchDaoDelegate searchDaoDelegate = new SearchDaoDelegate();
        for (int i = 0; i < 5; i++) {
            searchDaoDelegate.save("" + i);
        }
        List<SearchBean> searchBeanList = searchDaoDelegate.query();
        Timber.d(GsonUtil.toPrettyJson(searchBeanList));
    }

    @OnClick(R.id.btn_test_dialog)
    void clickTestDialog() {
        TestDialog.show(getSupportFragmentManager());
    }

}
