package com.min.framework.net.interceptor;

import android.os.Build;
import android.text.TextUtils;

import com.min.framework.base.BaseApp;
import com.min.framework.base.ConfigConstants;
import com.min.framework.util.AESUtil;
import com.min.framework.util.DeviceId;
import com.min.framework.util.GsonUtil;
import com.min.framework.util.L;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class ParamInterceptor implements Interceptor {

    private static String TAG="ParamInterceptor";

    public static final String FORM ="application/x-www-form-urlencoded";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        RequestBody requestBody=request.body();
        if(request.method().equalsIgnoreCase("GET")||!isFormUrlEncoded(requestBody)){
            L.d(ConfigConstants.HTTP_LOG,"request=  %s",request.url().toString());
            return chain.proceed(request);
        }

        Buffer buffer=new Buffer();
        requestBody.writeTo(buffer);
        String paramStr=buffer.readUtf8();
        paramStr= URLDecoder.decode(paramStr,"utf-8");
        L.d(TAG,"ParamInterceptor=%s",paramStr);
        if(!TextUtils.isEmpty(paramStr)){
            Map<String,String> map=splitStrToMap(paramStr);
            handleParams(map);
            FormBody.Builder formBuilder= new FormBody.Builder();
            for(Map.Entry<String,String> entry : map.entrySet()){
                formBuilder.add(entry.getKey(),entry.getValue());
            }

            //http log
            map.put("api",request.url().toString());
            L.d(ConfigConstants.HTTP_LOG,"request=  %s",GsonUtil.toPrettyJson(map));

            request=request.newBuilder()
                    .post(formBuilder.build())
                    .build();
        }
        Response response=chain.proceed(request);
        return response;
    }

    private boolean isFormUrlEncoded(RequestBody requestBody){
        if(requestBody!=null){
            String mediaTypeStr=requestBody.contentType().toString();
            if(!TextUtils.isEmpty(mediaTypeStr)&&mediaTypeStr.equals(FORM)){
                return true;
            }
        }
        return false;
    }

    private Map<String,String> splitStrToMap(String originalStr){
        Map<String,String> map=new TreeMap<>();
        String[] keyValueArr=originalStr.split("&");
        for(String keyValue : keyValueArr){
            String[] entry=keyValue.split("=");
            map.put(entry[0],entry[1]);
        }
        return map;
    }

    private String mapToString(Map<String,String> map){
        String formData="";
        StringBuilder encodedParams = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            encodedParams.append(entry.getKey());
            encodedParams.append('=');
            encodedParams.append(entry.getValue());
            encodedParams.append('&');
        }
        formData = encodedParams.toString();
        if (formData.endsWith("&")) {
            formData = formData.substring(0, formData.lastIndexOf("&"));
        }
        return formData;
    }

    public void handleParams(Map<String, String> params) {
        params.put("source", "123456");

        String mapUrl = mapToString(params);
        params.put("signature", AESUtil.md5(mapUrl + AESUtil.md5("1234567890")));

        Head head = new Head();
        head.clientKey = DeviceId.getDeviceId(BaseApp.getContext());
        head.apiVersion = "v1.0.1";
        head.sourceType = Build.MANUFACTURER + " " + Build.MODEL;
        head.mobileVersion = String.valueOf(Build.VERSION.SDK_INT);
        head.channel = "offical";
        head.screenWidth = 720;
        head.userId = "";

        String headStr= GsonUtil.getGson().toJson(head);
        params.put("head", headStr);
        L.d(TAG, GsonUtil.getGson().toJson(params));
    }

    public static class Head {
        public String apiVersion = "1.0";
        /* 设备唯一标识md5 */
        public String clientKey;
        public String userId;
        /* 屏幕宽度 */
        public int screenWidth;
        /* 2 : Android */
        public int mobileType = 2;
        /* 品牌类型 */
        public String sourceType;
        /* 系统版本 */
        public String mobileVersion;
        /* 渠道 */
        public String channel;
        /* 请求时间 */
        public String time;
    }

}
