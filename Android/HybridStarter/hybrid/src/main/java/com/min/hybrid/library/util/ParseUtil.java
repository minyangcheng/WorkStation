package com.min.hybrid.library.util;

import com.google.gson.Gson;

/**
 * Created by minyangcheng on 2018/1/10.
 */

public class ParseUtil {

    private static Gson gson = new Gson();

    public static String toJsonString(Object object) {
        return gson.toJson(object);
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        return gson.fromJson(jsonStr, clazz);
    }

}
