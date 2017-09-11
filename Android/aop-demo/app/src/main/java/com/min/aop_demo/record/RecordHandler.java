package com.min.aop_demo.record;

import timber.log.Timber;

/**
 * Created by minyangcheng on 2017/9/11.
 */

public class RecordHandler {

    private static class HOLDER {
        public static final RecordHandler INSTANCE = new RecordHandler();
    }

    private RecordHandler() {
    }

    public static RecordHandler getInstance() {
        return HOLDER.INSTANCE;
    }

    /**
     * 处理埋点数据回调
     *
     * @param point        埋点位置信息:类名方法
     * @param name         埋点名称
     * @param externalData 埋点附加数据，该数据由被埋点方法的返回，可能为空
     */
    public void handle(String point, String name, Object externalData) {
        Timber.d("point=%s , name=%s , result=%s", point, name, externalData);
    }

}
