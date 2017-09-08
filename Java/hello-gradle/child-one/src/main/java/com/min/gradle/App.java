package com.min.gradle;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by minyangcheng on 2017/7/8.
 */
public class App {

    public static void main(String args[]) {
        System.out.println("this is execute in child-one moudle");
        HashSet<KeyBean> hashSet = new HashSet();
        hashSet.add(new KeyBean("a", 1));
        hashSet.add(new KeyBean("b", 1));
        Iterator<KeyBean> iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            KeyBean bean = iterator.next();
            System.out.print(bean.keyName + "  " + bean.keyNum);
        }
    }

}
