package com.min.aop_demo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

    public static Gson gson = new Gson();

    public static Gson prettyGson = (new GsonBuilder().setPrettyPrinting()).create();

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static String toPrettyJson(Object src) {
        return prettyGson.toJson(src);
    }

}
