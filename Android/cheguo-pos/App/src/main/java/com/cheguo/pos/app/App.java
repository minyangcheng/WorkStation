package com.cheguo.pos.app;

import com.cheguo.pos.BuildConfig;
import com.cheguo.pos.util.BuglyUtil;
import com.cheguo.pos.util.pos.PosUtil;
import com.min.core.base.BaseApp;
import com.min.core.util.WebHandler;

import java.util.HashMap;
import java.util.Map;

import me.drakeet.floo.Floo;
import me.drakeet.floo.Target;
import me.drakeet.floo.extensions.LogInterceptor;
import me.drakeet.floo.extensions.OpenDirectlyHandler;

/**
 * Created by minyangcheng on 2016/10/13.
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        BuglyUtil.init(this);
        PosUtil.init(getContext());
        initRouter();
    }

    @Override
    public void onTerminate() {
        PosUtil.destory(getContext());
    }

    public void initRouter() {
        Map<String, Target> mappings = new HashMap<>();
        mappings.put("MainOrderActivity", new Target("cg://cheguo.com/order"));

        Floo.configuration()
                .setDebugEnabled(BuildConfig.DEBUG)
                .addRequestInterceptor(new LogInterceptor("Request"))
                .addTargetInterceptor(new LogInterceptor("Target"))
                .addTargetNotFoundHandler(new WebHandler())
                .addTargetNotFoundHandler(new OpenDirectlyHandler());

        Floo.apply(mappings);
    }

}
