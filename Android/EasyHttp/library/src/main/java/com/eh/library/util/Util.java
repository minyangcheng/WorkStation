package com.eh.library.util;

import android.net.Uri;

import com.eh.library.base.BaseApi;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by minyangcheng on 2016/8/8.
 */
public class Util {

    public static String appendParams(String url, Map<String, String> params){
        if (url == null || params == null || params.isEmpty()){
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }

    public static Map<String,String> getParamsFromApi(BaseApi api){
        if(api==null) return null;
        Map<String,String> params=new HashMap<>();
        JsonElement jsonElement=GsonUtil.getGson().toJsonTree(api);
        JsonObject jsonObject=jsonElement.getAsJsonObject();
        Set<Map.Entry<String,JsonElement>> set= jsonObject.entrySet();
        String keyStr=null;
        String valueStr=null;
        JsonElement valueElement=null;
        for(Map.Entry<String,JsonElement> element: set){
            keyStr=element.getKey();
            valueElement=element.getValue();
            if(element.getValue().isJsonPrimitive()){
                valueStr=valueElement.getAsString();
            }else if(element.getValue().isJsonObject()){
                valueStr=valueElement.getAsJsonObject().toString();
            }else if(element.getValue().isJsonArray()){
                valueStr=valueElement.getAsJsonArray().toString();
            }else {
                valueStr="";
            }
            params.put(keyStr,valueStr);
        }
        return params;
    }

    public static void getField(Map<String,String> paramsMap,Map<String,File> filesMap,Class clazz,Object obj)
            throws Exception{
        if(clazz==null) return;
        Field[] fieldArr=clazz.getDeclaredFields();
        Field field=null;
        for(int i=0;i<fieldArr.length;i++){
            field=fieldArr[i];
            field.setAccessible(true);
            //首先排除掉transient和static修饰的变量
            int mod=field.getModifiers();
            if(!Modifier.isTransient(mod)&&!Modifier.isStatic(mod)){
                Object fieldObj=null;
                if(field.getType()==File.class){
                    fieldObj=field.get(obj);
                    if(fieldObj!=null){
                        filesMap.put(field.getName(),(File)fieldObj);
                    }
                }else if(field.getType().isPrimitive()){
                    paramsMap.put(field.getName(), field.get(obj).toString());
                }else if(field.getType()==String.class){
                    fieldObj=field.get(obj);
                    if(fieldObj!=null){
                        paramsMap.put(field.getName(), fieldObj.toString());
                    }
                }else{
                    fieldObj=field.get(obj);
                    if(fieldObj!=null){
                        paramsMap.put(field.getName(), GsonUtil.toJson(fieldObj));
                    }
                }
            }
        }
    }

    public static void addMultiParamsPart(MultipartBody.Builder builder,Map<String,String> params){
        if (params != null && !params.isEmpty()){
            for (String key : params.keySet()){
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params.get(key)));
            }
        }
    }

    public static void addMultiFilePart(MultipartBody.Builder builder,Map<String,File> filesMap){
        if(filesMap==null||filesMap.isEmpty()) return;
        String filename=null;
        String name=null;
        File file=null;
        for(Map.Entry<String,File> entry : filesMap.entrySet()){
            name=entry.getKey();
            file=entry.getValue();
            filename=file.getName();
            RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(filename)),file);
            builder.addFormDataPart(name, filename, fileBody);
        }
    }

    private static String guessMimeType(String fileName){
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try{
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        if (contentTypeFor == null){
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

}
