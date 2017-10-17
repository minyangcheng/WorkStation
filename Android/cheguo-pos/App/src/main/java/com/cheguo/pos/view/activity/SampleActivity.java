package com.cheguo.pos.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cheguo.pos.R;
import com.cheguo.pos.app.AppEvent;
import com.cheguo.pos.data.model.SearchBean;
import com.cheguo.pos.data.local.db.delegate.SearchDaoDelegate;
import com.cheguo.pos.util.LocationInfoUtil;
import com.cheguo.pos.util.TerminalInfoUtil;
import com.min.core.base.BaseActivity;
import com.min.core.util.GsonUtil;
import com.min.core.util.RxEventBus;
import com.min.ui.widget.CenterTitleToolbar;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class SampleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mToolbar.setTitle();
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
        return R.layout.activity_sample;
    }

    @OnClick(R.id.btn_list_sample)
    void clickListSample() {
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

}
