package com.min.framework.net;

import java.util.HashMap;
import java.util.Map;

public class HttpProviderFactory {

    protected static Map<Class, Object> m_service = new HashMap();

    public static <T> T provideService(Class cls) {
        Object serv = m_service.get(cls);
        if (serv == null) {
            synchronized (cls) {
                serv = m_service.get(cls);
                if (serv == null) {
                    serv = RetrofitManager.getInstance().create(cls);
                    m_service.put(cls, serv);
                }
            }
        }
        return (T) serv;
    }
}
