package com.cheguo.pos.data.remote;

import com.cheguo.pos.BuildConfig;
import com.cheguo.pos.data.model.TransInfo;
import com.cheguo.pos.data.model.TransRecord;
import com.cheguo.pos.data.model.UpdateRespBean;
import com.min.core.bean.BaseBean;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface MobileService {

    String BASE_URL = BuildConfig.API_SERVER_URL;

    //自更新  1:车果app 2:车商app 3：拖车app 4：车贷app
    @FormUrlEncoded
    @POST(BuildConfig.API_UPDATE_URL + "/version/update")
    Observable<BaseBean<UpdateRespBean>> selfUpdate(@Field("apptype") int apptype, @Field("clienttype") String clienttype
            , @Field("version") String version, @Field("channel") String channel);

    @FormUrlEncoded
    @POST("/mobile/addTransInfo.json")
    Observable<BaseBean<Object>> addTransInfo(@Field("json") TransRecord record);

    @FormUrlEncoded
    @POST("/mobile/getTransInfo.json")
    Observable<BaseBean<List<TransInfo>>> getTransInfoList(@Field("merchantNo") String merchantNo);

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
