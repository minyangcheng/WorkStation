package com.example.gson;


import com.example.util.L;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.Arrays;
import java.util.List;

/**
 * Created by minyangcheng on 2016/9/25.
 */
public class GsonUtil {

    public static Gson gson=new Gson();

    public static void main(String args[]){
        BaseBean<DataEntity> baseBean=new BaseBean<>();
        DataEntity entity=new DataEntity("minyangcheng",123);
        baseBean.code=1;
        baseBean.message="nihaoma?";
        baseBean.t=entity;
        L.d(gson.toJson(baseBean));

        DataEntity[] dataArr=new DataEntity[3];
        for (int i = 0; i < dataArr.length; i++) {
            DataEntity data=new DataEntity("minyangcheng",i+100);
            dataArr[i]=data;
        }
        L.d("array=%s",gson.toJson(dataArr));
        List<DataEntity> list= Arrays.asList(dataArr);
        L.d("list=%s",gson.toJson(dataArr));
    }

    public static Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static  <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json, classOfT);
    }

    /**
     * 日志打印美化
     * @param src
     * @return
     */
    public static String toPrettyJson(Object src) {
        if(src==null) return null;
        return gson.toJson(src);
    }

    public static Gson getGson(){
        return gson;
    }

}
