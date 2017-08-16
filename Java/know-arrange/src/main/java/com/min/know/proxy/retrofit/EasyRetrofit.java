package com.min.know.proxy.retrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by minyangcheng on 2017/8/11.
 */

public class EasyRetrofit {

    private final Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();

    public <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object... args)
                            throws Throwable {
                        ServiceMethod serviceMethod = loadServiceMethod(method, args);
                        HttpCall httpCall = new HttpCall(serviceMethod);
                        return httpCall.execute();
                    }
                });
    }

    private ServiceMethod loadServiceMethod(Method method, Object[] args) {
        ServiceMethod result;
        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod(method, args);
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }


}
