package com.min.hybrid.library.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by minyangcheng on 2018/1/10.
 */

public class ParseUtil {

    private static Gson gson = new Gson();

    public static String toJsonString(Object object) {
        return gson.toJson(object);
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        try {
            return gson.fromJson(jsonStr, clazz);
        } catch (Exception e) {
            return null;
        }
    }

}
