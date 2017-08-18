package com.min.know.type;

import com.min.know.util.L;

/**
 * Created by minyangcheng on 2017/8/18.
 */
public class TestDemo {

    public static void main(String[] args){
        EventType type1=EventType.LOCATION;
        EventType type2=EventType.LOCATION;
        type1.setValue(1);
        type2.setValue(2);
        L.d("type1==type2-->%s",type1==type2);
        L.d("type1=%s,type2=%s",type1.toString(),type2.toString());
        L.d("type1.value=%s,type2.value=%s",type1.getValue(),type2.getValue());
    }

}
