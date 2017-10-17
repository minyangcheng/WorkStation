package com.min.sample.data.remote.interceptor;

import android.text.TextUtils;

import com.min.common.util.EncryptUtils;
import com.min.core.Constants;
import com.min.core.util.GsonUtil;
import com.min.sample.BuildConfig;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import timber.log.Timber;

public class ParamInterceptor implements Interceptor {

    public static final String FORM = "application/x-www-form-urlencoded";

    public static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = addMobileInfoToReqHeader(request);

        RequestBody requestBody = request.body();
        if (request.method().equalsIgnoreCase("GET") || !isFormUrlEncoded(requestBody)) {
            Timber.tag(Constants.HTTP_LOG).d("request=  %s", request.url().toString());
            return chain.proceed(request);
        }

        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        String paramStr = buffer.readUtf8();
        paramStr = URLDecoder.decode(paramStr, "utf-8");

        Map<String, String> map = splitStrToMap(paramStr);
        handleParams(map);
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBuilder.add(entry.getKey(), entry.getValue());
        }
        request = request.newBuilder()
                .post(formBuilder.build())
                .build();

        //http log
        map.put("api", request.url().toString());
        Timber.tag(Constants.HTTP_LOG).d("request=  %s", GsonUtil.toPrettyJson(map));

        Response response = chain.proceed(request);
        return response;
    }

    private Request addMobileInfoToReqHeader(Request request) {
        String jsonString = "";
        try {
            Head head = new Head();
            head.apiVersion = Constants.VERSION_NAME;
            head.mobileVersion = Constants.SDK_INT;
            head.channel = Constants.FLAVOR;
            head.time = mDateFormat.format(new Date());

            jsonString = GsonUtil.toJson(head);
            Timber.d(jsonString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        request = request.newBuilder()
                .addHeader("MobileInfo", jsonString)
                .build();
        return request;
    }

    private boolean isFormUrlEncoded(RequestBody requestBody) {
        if (requestBody != null) {
            if (requestBody.contentType() != null) {
                String mediaTypeStr = requestBody.contentType().toString();
                if (!TextUtils.isEmpty(mediaTypeStr) && mediaTypeStr.equals(FORM)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Map<String, String> splitStrToMap(String originalStr) {
        Map<String, String> map = new TreeMap<>();
        if (TextUtils.isEmpty(originalStr)) return map;
        String[] keyValueArr = originalStr.split("&");
        for (String keyValue : keyValueArr) {
            String[] entry = keyValue.split("=");
            if (entry.length <= 1) continue;
            map.put(entry[0], entry[1]);
        }
        return map;
    }

    private String mapToString(Map<String, String> map) {
        String formData = "";
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
        params.put("source", BuildConfig.SOURCE);
        String mapUrl = mapToString(params);
        params.put("signature", EncryptUtils.encryptMD5ToString(mapUrl + EncryptUtils.encryptMD5ToString(BuildConfig.APP_SECRET)));
    }

    class Head {
        public String apiVersion;
        public String mobileVersion;
        public String channel;
        public String time;
        public String token;
    }
}
