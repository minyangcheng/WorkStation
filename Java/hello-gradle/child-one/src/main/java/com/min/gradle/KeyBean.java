package com.min.gradle;

import java.security.Key;

/**
 * Created by minyangcheng on 2017/7/15.
 */
public class KeyBean {

    public String keyName;
    public int keyNum;

    public KeyBean(String keyName, int keyNum) {
        this.keyName = keyName;
        this.keyNum = keyNum;
    }

    @Override
    public int hashCode() {
        return keyNum;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof KeyBean){
            KeyBean other= (KeyBean) obj;
//            return keyName.equals(other.keyName);
            return keyNum==other.keyNum;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "{"+keyName+","+keyNum+"}";
    }
}
