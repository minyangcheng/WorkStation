package com.example.util;

import com.google.gson.Gson;

/**
 * Created by minyangcheng on 2016/8/6.
 */
public class GsonUtil {

    private static Gson gson=new Gson();

    public static Gson getGson(){
        return gson;
    }

    public static String toJson(Object src){
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> classOfT){
        return gson.fromJson(json,classOfT);
    }

}
