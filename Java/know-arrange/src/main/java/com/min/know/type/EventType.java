package com.min.know.type;

/**
 * Created by minyangcheng on 2017/6/26.
 */

public enum EventType {

    LOCATION,//位置更新
    REFRESH,//刷新
    ;

    private Object value;

    private EventType() {
    }

    public EventType setValue(Object value) {
        this.value = value;
        return this;
    }

    public Object getValue() {
        return value;
    }

}
