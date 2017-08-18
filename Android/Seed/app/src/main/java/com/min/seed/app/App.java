package com.min.seed.app;

import com.min.framework.FrameWorkApp;
import com.min.seed.util.BuglyUtil;

/**
 * Created by minyangcheng on 2016/10/13.
 */
public class App extends FrameWorkApp {

    @Override
    public void onCreate() {
        super.onCreate();
        BuglyUtil.init(this);
    }

}
