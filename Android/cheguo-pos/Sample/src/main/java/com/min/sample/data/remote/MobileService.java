package com.min.sample.data.remote;

import com.min.core.bean.BaseBean;
import com.min.sample.data.model.OrderBean;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface MobileService {

    String BASE_URL = "http://10.10.13.12:8080";

    @FormUrlEncoded
    @POST("http://10.10.13.12:8080/dealer/login.json")
    Observable<BaseBean> login(@Field("username") String userName, @Field("userpwd") String userPass);

    @FormUrlEncoded
    @POST("/dealer/newgetrecord.json")
    Observable<BaseBean<List<OrderBean>>> getOrderList(@Field("loantype") String loantype
            , @Field("companyid") String companyid
            , @Field("dealeruserid") String dealeruserid
            , @Field("pageSize") int pageSize
            , @Field("assetstatus") String assetstatus
            , @Field("currentPage") int currentPage);

    class Creator {
        public static MobileService newMobileService() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OkHttpClientManager.getInstance().getOkHttpClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(MobileService.class);
        }
    }
}
