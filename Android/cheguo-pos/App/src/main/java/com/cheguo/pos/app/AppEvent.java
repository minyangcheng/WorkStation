package com.cheguo.pos.app;

/**
 * Created by minyangcheng on 2017/6/26.
 */

public class AppEvent {

    private ChangeEventType type;

    private Object data;

    private AppEvent(ChangeEventType type) {
        this.type = type;
    }

    private AppEvent(ChangeEventType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public static AppEvent newInstance(ChangeEventType type) {
        AppEvent event = new AppEvent(type);
        return event;
    }

    public static AppEvent newInstance(ChangeEventType type, Object data) {
        AppEvent event = new AppEvent(type, data);
        return event;
    }

    public boolean filter(ChangeEventType type) {
        return this.type == type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public enum ChangeEventType {

        LOCATION,//地理位置更新
        REFRESH,//刷新
        CONSUME_UPDO,//撤销成功

    }

}
