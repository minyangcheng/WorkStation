package com.example.proxy.retrofit;

import com.example.proxy.retrofit.annotation.Field;
import com.example.proxy.retrofit.annotation.POST;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by minyangcheng on 2017/8/11.
 */

public class ServiceMethod {

    private Method method;
    private Object[] args;

    public ServiceMethod(Method method, Object[] args) {
        this.method = method;
        this.args = args;
        getReturnType();
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }

    public String getRequestUrl() {
        POST postAnnotation = method.getAnnotation(POST.class);
        if (postAnnotation != null) {
            return postAnnotation.value();
        } else {
            throw new RetrofitException(RetrofitException.URL_ERROR);
        }
    }

    public Map<String, String> getRequestFileds() {
        Map<String, String> map = new HashMap<>();
        List<String> fieldNames = getRequestFieldName();
        if (args.length != 0 && args.length == fieldNames.size()) {
            for (int i = 0; i < args.length; i++) {
                map.put(fieldNames.get(i), args[i].toString());
            }
            return map;
        } else {
            throw new RetrofitException(RetrofitException.METHOD_FIELD_ERROR);
        }
    }

    private List<String> getRequestFieldName() {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations != null && parameterAnnotations.length != 0) {
            List<String> fieldNames = new ArrayList<>();
            Field field;
            for (Annotation[] annotations : parameterAnnotations) {
                field = (Field) annotations[0];
                fieldNames.add(field.value());
            }
            return fieldNames;
        } else {
            throw new RetrofitException(RetrofitException.METHOD_FIELD_ERROR);
        }
    }

}
