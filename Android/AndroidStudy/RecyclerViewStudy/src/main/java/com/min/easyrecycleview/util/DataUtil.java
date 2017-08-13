package com.min.easyrecycleview.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minyangcheng on 2016/6/23.
 */
public class DataUtil {

    public static List<String> getDataList(int count){
        List<String> dataList=new ArrayList<>();
        for(int i=0;i<count;i++){
            dataList.add("i am "+i);
        }
        return dataList;
    }

}
