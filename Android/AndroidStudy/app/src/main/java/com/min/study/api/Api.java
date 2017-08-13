package com.min.study.api;

import com.min.framework.bean.BaseBean;
import com.min.study.bean.LoginRespBean;
import com.min.study.bean.OrderBean;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by minyangcheng on 2016/9/14.
 */
public interface Api {

    @FormUrlEncoded
    @POST("http://10.10.13.12:8080/dealer/login.json")
    Observable<BaseBean<LoginRespBean>> login(@Field("username") String userName, @Field("userpwd") String userPass);

    @FormUrlEncoded
    @POST("http://10.10.13.12:8080/dealer/login.json")
    Call<BaseBean<LoginRespBean>> loginCall(@Field("username") String userName, @Field("userpwd") String userPass);

    @GET("http://www.baidu.com/")
    Observable<BaseBean> get();

    @POST("useRequestBody")
    Observable<BaseBean> useRequestBody(@Body RequestBody photoBody);

    @Multipart
    @POST("useMultipart")
    Observable<BaseBean> useMultipart(@Part MultipartBody.Part namePart, @Part MultipartBody.Part photoPart);

    @GET("http://xiazai.xiazaiba.com/Phone/M/meejian_3.3_XiaZaiBa.apk")
    Observable<ResponseBody> downloadFile();

    @FormUrlEncoded
    @POST("http://10.10.13.12:8080/dealer/newgetrecord.json")
    Observable<BaseBean<List<OrderBean>>> getOrderList(@Field("loantype") String loantype
            , @Field("companyid") String companyid
            , @Field("dealeruserid") String dealeruserid
            , @Field("pageSize") int pageSize
            , @Field("assetstatus") String assetstatus
            , @Field("currentPage") int currentPage);


}
