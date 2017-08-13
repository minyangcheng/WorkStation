package com.example;

import com.example.bean.RecommendUser;
import com.example.bean.UserBean;
import com.example.bean.api.RegisterApi;
import com.example.bean.response.RegisterResponse;
import com.example.http.BaseApi;
import com.example.http.HttpClient;
import com.example.test.Animal;
import com.example.test.Dog;
import com.example.util.GsonUtil;
import com.example.util.L;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MyClass {

    public static Gson gson=new Gson();

    public static void main(String[] args){
//        RegisterApi api=new RegisterApi();
//        api.setUserName("min");
//        api.setPassword("123");
//        api.setPhone("15257178956");
//        api.setRecommendUser(new RecommendUser("zhang", "15179889894"));
//
//        JsonElement jsonElement=gson.toJsonTree(api);
//        JsonObject jsonObject=jsonElement.getAsJsonObject();
//        Set<Map.Entry<String,JsonElement>> set= jsonObject.entrySet();
//        for(Map.Entry<String,JsonElement> element: set){
//            if(element.getValue().isJsonPrimitive()){
//                L.d(element.getValue().getAsString());
//            }else if(element.getValue().isJsonObject()){
//                L.d(element.getValue().getAsJsonObject().toString());
//            }else if(element.getValue().isJsonArray()){
//                L.d(element.getValue().getAsJsonArray().toString());
//            }
//        }

//        HttpClient.getInstance()
//                .request(api, new HttpClient.NetCallBack<RegisterResponse>() {
//                    @Override
//                    public void onResponse(RegisterResponse response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//
//                    }
//                });

//        RegisterResponse registerResponse=new RegisterResponse();
//        registerResponse.setPath("/register");
//        registerResponse.setUserBean(new UserBean("1201", "min", "123", 999));
//        L.d(gson.toJson(registerResponse));

//        HttpClient.NetCallBack netCallBack=new HttpClient.NetCallBack<UserBean>() {
//            @Override
//            public void onResponse(UserBean response) {
//                L.d(response.getUserId());
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        };
//        HttpClient.getInstance().request(null,netCallBack);
//        Object object=new UserBean("min","","",12);
//        netCallBack.onResponse(object);
        try {
            Dog dog=new Dog();
            dog.setAnimalType("gou");
            dog.setTypeCode(12);
//            dog.setName("zhang");
//            dog.setAge(14);
            dog.setIntroFile(new File("E:\\GitHubProject\\EasyHttp\\README.md"));
            dog.setTraName("traZhang");
            dog.setTraAge(999);

            Map<String,String> paramsMap=new HashMap<>();
            Map<String,File> filesMap=new HashMap<>();
            Class clazz= Dog.class;
            getField(paramsMap,filesMap,clazz,dog);
            clazz=clazz.getSuperclass();
            getField(paramsMap,filesMap,clazz,dog);

            for(Map.Entry<String,String> entry : paramsMap.entrySet()){
                L.d(entry.getKey()+"  "+entry.getValue());
            }

            for (Map.Entry<String,File> entry : filesMap.entrySet()){
                L.d(entry.getKey()+"  "+entry.getValue().getAbsolutePath());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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

}
