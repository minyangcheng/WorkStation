package com.example.http;

import com.example.util.GsonUtil;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by minyangcheng on 2016/8/6.
 */
public class HttpClient {

    public static String BASE_URL="http://10.10.13.12:8080/";

    public static final MediaType FORM =MediaType.parse("application/x-www-form-urlencoded");
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType IMAGE = MediaType.parse("image/*");

    private static HttpClient mHttpClient;

    private static OkHttpClient mOkHttpClient;

    public HttpClient(){
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(BuildConfig.DEBUG?HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE);

//        Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
//            @Override public Response intercept(Chain chain) throws IOException {
//                Response originalResponse = chain.proceed(chain.request());
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", "max-age=600")
//                        .removeHeader("Pragma")
//                        .build();
//            }
//        };

//        Cache cache = new Cache(new File(App.getContext().getCacheDir(), "HttpCache"),
//                1024 * 1024 * 30);

        mOkHttpClient = new OkHttpClient.Builder()
//                .cache(cache)
//                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//                .addInterceptor(new ParamInterceptor())
//                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        return null;
                    }
                })
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
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

    public void request(BaseApi api,NetCallBack<? extends BaseResponse> netCallBack){
        String jsonStr=GsonUtil.toJson(api);
        RequestBody requestBody=RequestBody.create(JSON, jsonStr);
        Request request=new Request.Builder()
                .url(api.getPath())
                .post(requestBody)
                .build();
        Call call=mOkHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                int b=2;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

    public static abstract class NetCallBack<T> {
        private Type type;

        protected NetCallBack() {
            Type superClass = getClass().getGenericSuperclass();
            type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        }

        public Type getType() {
            return type;
        }

        public abstract void onResponse(T response);

        public abstract void onFailure(Throwable t);
    }

}
