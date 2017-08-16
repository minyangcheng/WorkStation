package com.min.seed.net;

import com.min.framework.net.HttpProviderFactory;

/**
 * Created by minyangcheng on 2016/10/13.
 */
public class HttpFactory extends HttpProviderFactory {

    public static ApiService provideApiService() {
        return provideService(ApiService.class);
    }

}
