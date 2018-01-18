package com.min.hybrid.library;

/**
 * Created by minyangcheng on 2018/1/10.
 */

public class Constants {

    public static final String HYBRID_LOG = "hybrid_log";

    public static final String BRIDGE_SCHEME = "bridge";

    public static final String ACTION_HYBRID_ACTIVITY = "open.hybrid.activity";

    public static class BridgeApi {
        public static final String TOAST = "toast";

        public static final String OPEN_ACTIVITY = "openActivity";

        public static final String POST_EVENT = "postEvent";

        public static final String NATIVE_LOG = "nativeLog";
    }

    public static class Resource {
        public static final String BASE_DIR = "WebApp";
        public static final String BUNDLE_NAME = "bundle.zip";
        public static final String TEMP_BUNDLE_NAME = "temp_bundle.zip";
        public static final String JS_BUNDLE = "/.bundle";
        public static final String JS_BUNDLE_ZIP = "/.bundle_zip";
    }

    public static class Version {
        public static final int UPDATING = 0;
        public static final int SLEEP = 1;
    }

    public static class SP {
        public static final String NATIVE_NAME = "HYBIRD_NATIVE_SP";
        public static final String VERSION = "VERSION";
        public static final String DOWNLOAD_VERSION = "DOWNLOAD_VERSION";
        public static final String INTERCEPTOR_ACTIVE = "INTERCEPTOR_ACTIVE";
    }

}
