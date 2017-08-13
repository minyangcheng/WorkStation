package com.eh.library.http;

import com.eh.library.base.BaseApi;
import com.eh.library.callback.CallBack;
import com.eh.library.callback.FileCallBack;
import com.eh.library.exception.NetError;
import com.eh.library.util.GsonUtil;
import com.eh.library.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by minyangcheng on 2016/8/6.
 */
public class HttpClient {

    public static final String CONFIG_IS_NULL="HttpClientConfig should not be null";
    public static final String RESPONSE_IS_NOT_SUCCESS="request failed ,response code is %s";
    public static final String CANCAL_ERROR="request is cancel ";

    public static final MediaType FORM =MediaType.parse("application/x-www-form-urlencoded");
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String CACHE_DIR="HttpCache";

    private static HttpClient mHttpClient;

    private HttpClientConfig mConfig;

    private OkHttpClient mOkClient;
    private DeliveryExecutor mDelivery;
    private UserInterceptor mInterceptor;

    private HttpClient(){
    }

    public void init(HttpClientConfig config){
        if(config==null){
            throw new IllegalArgumentException(CONFIG_IS_NULL);
        }
        this.mConfig=config;
        mOkClient=config.getOkHttpClient();
        mDelivery=config.getDelivery();
        mInterceptor=config.getInterceptor();
    }

    public static HttpClient getInstance(){
        if(mHttpClient==null){
            synchronized (HttpClient.class){
                if(mHttpClient==null){
                    mHttpClient=new HttpClient();
                }
            }
        }
        return mHttpClient;
    }

    public void request(BaseApi api, CallBack callBack,Object tag){
        Request request=buildRequest(api, tag);
        Call call= mOkClient.newCall(request);
        enqueueCall(call,callBack);
    }

    public void requestSync(BaseApi api , CallBack callBack, Object tag){
        if(callBack==null){
            callBack=new CallBack.DefaultCallBack();
        }
        Request request=buildRequest(api, tag);
        Call call= mOkClient.newCall(request);
        try {
            Response response=call.execute();
            if(!response.isSuccessful()){
                callBack.onFailure(new NetError(String.format(RESPONSE_IS_NOT_SUCCESS,response.code())));
            }
            Object object=callBack.parseNetworkResponse(response);
            if(mInterceptor!=null&&object!=null){
                if(!mInterceptor.filter(object)){
                    callBack.onResponse(object);
                }
            }else{
                callBack.onResponse(object);
            }
        } catch (Exception e) {
            callBack.onFailure(new NetError(e));
        }
    }

    public void uploadFile(BaseApi api, final CallBack callBack, Object tag){
        RequestBody requestBody=getMultiRequestBody(api);
        CountingRequestBody wrapRequestBody=new CountingRequestBody(requestBody, new CountingRequestBody.Listener(){
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength){
                mDelivery.deliveryProgress(bytesWritten * 1.0f / contentLength,contentLength,callBack);
            }
        });
        Request request=getRequestBuilder(api.getPath(),wrapRequestBody,tag).build();
        Call call = mOkClient.newCall(request);
        enqueueCall(call, callBack);
    }

    public void downloadFile(String fileUrl,FileCallBack callback, Object tag){
        Request request=getRequestBuilder(fileUrl, null, tag).build();
        Call call=mOkClient.newCall(request);
        enqueueCall(call,callback);
    }

    public void get(String url, CallBack callBack, Object tag){
        Request request=getRequestBuilder(url, null, tag).build();
        Call call=mOkClient.newCall(request);
        enqueueCall(call, callBack);
    }

    public void cancelTag(Object tag){
        if(tag==null) return;
        for (Call call : mOkClient.dispatcher().queuedCalls()){
            if (tag.equals(call.request().tag())){
                call.cancel();
            }
        }
        for (Call call : mOkClient.dispatcher().runningCalls()){
            if (tag.equals(call.request().tag())){
                call.cancel();
            }
        }
    }

    private void enqueueCall(Call call, CallBack callBack ){
        if(callBack==null){
            callBack=new CallBack.DefaultCallBack();
        }
        final CallBack finalCallBack=callBack;
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mDelivery.deliveryError(new NetError(e)
                            ,finalCallBack);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(call.isCanceled()){
                    mDelivery.deliveryError(new NetError(CANCAL_ERROR)
                            ,finalCallBack);
                }
                if (response.isSuccessful()) {
                    try {
                        Object object=finalCallBack.parseNetworkResponse(response);
                        if(mInterceptor!=null&&object!=null){
                            if(!mInterceptor.filter(object)){
                                mDelivery.deliveryResponse(object, finalCallBack);
                            }
                        }else{
                            mDelivery.deliveryResponse(object, finalCallBack);
                        }
                    } catch (Exception e) {
                        mDelivery.deliveryError(new NetError(e)
                                    ,finalCallBack);
                    }
                } else {
                    mDelivery.deliveryError(new NetError(String.format(RESPONSE_IS_NOT_SUCCESS,response.code()))
                                    ,finalCallBack);
                }
            }
        });
    }

    protected Request buildRequest(BaseApi api, Object tag){
        Request request=null;
        if(api.getHttpType()== BaseApi.HTTP_TYPE.POST){
            request=getRequestBuilder(api.getPath(),getFormRequestBoby(api),tag)
                    .build();
        }else{
            Map<String,String> params=Util.getParamsFromApi(api);
            String url= Util.appendParams(api.getPath(), params);
            request=getRequestBuilder(url, null, tag).build();
        }
        return request;
    }

    protected Request.Builder getRequestBuilder(String url, RequestBody requestBody, Object tag){
        Request.Builder builder=new Request.Builder()
                .url(url);
        if(requestBody!=null){
            builder.post(requestBody);
        }
        if(tag!=null){
            builder.tag(tag);
        }
        return builder;
    }

    /**
     * 接口api请求为form表单请求,BaseApi中的transient和static修饰的变量不会被添加到表单中
     * @param api
     * @return
     */
    private RequestBody getFormRequestBoby(BaseApi api){
        FormBody.Builder builder=new FormBody.Builder();
        Map<String,String> params=Util.getParamsFromApi(api);
        for(Map.Entry<String,String> entry : params.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody requestBody=builder.build();
        return requestBody;
    }

    /**
     * 接口api请求直接为json请求
     * @param api
     * @return
     */
    private RequestBody getJsonRequestBody(BaseApi api){
        String requestStr= GsonUtil.toJson(api);
        RequestBody requestBody=RequestBody.create(JSON, requestStr);
        return requestBody;
    }

    private RequestBody getMultiRequestBody(BaseApi api){
        RequestBody requestBody=null;
        Map<String,String> paramsMap=new HashMap<>();
        Map<String,File> filesMap=new HashMap<>();
        try {
            Util.getField(paramsMap, filesMap, api.getClass(), api);
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            Util.addMultiParamsPart(builder,paramsMap);
            Util.addMultiFilePart(builder, filesMap);
            requestBody=builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestBody;
    }

    public DeliveryExecutor getDelivery(){
        return mDelivery;
    }

}
