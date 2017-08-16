package com.min.seed.net;

import com.min.framework.bean.BaseBean;
import com.min.seed.bean.OrderBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by minyangcheng on 2016/9/14.
 */
public interface ApiService {

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
}
