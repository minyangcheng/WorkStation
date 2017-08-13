package com.min.vip.app;


import com.min.framework.net.HttpProviderFactory;
import com.min.vip.api.Api;

/**
 * Created by minyangcheng on 2016/10/13.
 */
public class HttpFactory extends HttpProviderFactory {

    public static Api provideApiService() {
        return provideService(Api.class);
    }

}
