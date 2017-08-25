package com.min.know.gson;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.min.know.util.L;

import java.lang.reflect.Type;

/**
 * Created by minyangcheng on 2016/9/25.
 */
public class GsonUtil {

    public static Gson gson = new Gson();

    public static void main(String args[]) {
        BaseBean<DataEntity> baseBean = new BaseBean<>();
        DataEntity entity = new DataEntity("minyangcheng", 123, OrderStatus.CANCEL);
        baseBean.code = 1;
        baseBean.message = "nihaoma?";
        baseBean.t = entity;

        String jsonStr = gson.toJson(baseBean);
        L.d(jsonStr);
        Type fooType = new TypeToken<BaseBean<DataEntity>>() {
        }.getType();
        baseBean = getGson().fromJson(jsonStr, fooType);
        L.d(gson.toJson(baseBean));
    }

    public static Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json, classOfT);
    }

    /**
     * 日志打印美化
     *
     * @param src
     * @return
     */
    public static String toPrettyJson(Object src) {
        if (src == null) return null;
        return gson.toJson(src);
    }

    public static Gson getGson() {
        return gson;
    }

}
