package com.min.plugin;

/**
 * Created by minyangcheng on 2017/7/20.
 */

public class Message implements IMessage{

    @Override
    public String getMessage() {
        Counter.add();
        return "i am from plugin count="+Counter.getCount();
    }

}
