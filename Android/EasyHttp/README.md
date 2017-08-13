## EasyHttp

1. 大多数项目都需要http请求服务器数据，那么就需要定义网络请求层。设想一下如果输入和输出都为变为对象，我们的请求代码书写会变的多么的简洁明了。
2. 继承BaseApi和BaseResponse即可轻松实现输入和输出。在解析BaseApi的时候，static和transient修饰的变量将不会被解析到；在定义BaseResponse的时候你可以重写isSuccess()方法，即可定义接口的逻辑错误。
3. 上传下载都有进度回调。

#### 初始化HttpClient

	HttpClientConfig config=new HttpClientConfig.Builder(this)
                .setDelivery(new MainDeliveryExecutor())
                .setOkHttpClient(OkHttpUtil.getOkHttpClient(BuildConfig.DEBUG,this.getCacheDir()))
                .build();
    HttpClient.getInstance().init(config);

#### 配置业务层拦截器

	config.setInterceptor(new UserInterceptor() {
            @Override
            public boolean interceptor(BaseResponse baseResponse) {
                if(baseResponse.getCode()==9999){
                    return true;
                }
                return false;
            }
        })

#### GET请求

	public class UserInfoGetApi extends BaseApi{

	    private String userName;
	
	    private String password;
	
	    public UserInfoGetApi(String userName, String password) {
	        this.userName = userName;
	        this.password = password;
	    }
	
	    public String getUserName() {
	        return userName;
	    }
	
	    public void setUserName(String userName) {
	        this.userName = userName;
	    }
	
	    public String getPassword() {
	        return password;
	    }
	
	    public void setPassword(String password) {
	        this.password = password;
	    }
	
	
	    @Override
	    public String getPath() {
	        return "https://kyfw.12306.cn/otn/";
	    }
	
	    @Override
	    public HTTP_TYPE getHttpType() {
			//指定为get请求方式
	        return HTTP_TYPE.GET;
	    }
	}

	UserInfoGetApi api=new UserInfoGetApi("小城","abc123");
    HttpClient.getInstance()
            .request(api, new StringCallBack() {
                @Override
                public void onResponse(String response) {
                    L.d(TAG,"onResponse=%s",response);
                }

                @Override
                public void onFailure(NetError error) {
                    L.e(TAG,error);
                }
            },this);

#### post请求

	public class BaseResponse {

	    protected int code;  //假设code==1000为正确码，其余都为错误
	
	    public int getCode() {
	        return code;
	    }
	
	    public void setCode(int code) {
	        this.code = code;
	    }
	
	    public boolean isSuccess(){
			//此处定义接口返回的错误码
	        return code==1000;
	    }
	}

	RegisterApi api=new RegisterApi();
    api.setUserName("小城");
    api.setPassword("abc123");
    api.setPhone("15257178956");
    api.setRecommendUser(new RecommendUser("zhang", "15179889894"));
    HttpClient.getInstance()
            .request(api, new NetCallBack<RegisterResponse>() {
                @Override
                public void onResponse(RegisterResponse response) {
                    L.d(TAG, "onResponse=%s", GsonUtil.toJson(response));
                }

                @Override
                public void onFailure(NetError error) {
                    L.e(TAG, error);
                }
            }, TAG);

#### 上传(自定义UploadApi里面的File类型字段将会以文件形式上传)

	File file=new File(FilePathUtil.getFilesRootDir(MainActivity.this),"bdsjzh.apk");
    UploadApi api=new UploadApi();
    api.setUserName("min");
    api.setPassword("123");
    api.setFile(file);
    HttpClient.getInstance()
            .uploadFile(api, new NetCallBack<BaseResponse>() {
                @Override
                public void onResponse(BaseResponse response) {
                    L.d(TAG, "onResponse response=%s",GsonUtil.toJson(response));
                }

                @Override
                public void onFailure(NetError error) {
                    L.e(TAG, error);
                }

                @Override
                public void onProgress(float progress, long total) {
                    L.d(TAG,"onPregress progress=%s , total =%s",progress,total);
                }
            }, TAG);

#### 下载

	String fileUrl="http://xiazai.xiazaiba.com/Phone/M/meejian_3.3_XiaZaiBa.apk";
    String destDir=FilePathUtil.getFilesRootDir(this).getAbsolutePath();
    String filename="bdsjzh.apk";
    File file=new File(destDir,filename);
    if(file.exists()){
        file.delete();
        L.d(TAG, "删除文件....");
    }
    HttpClient.getInstance()
            .downloadFile(fileUrl, new FileCallBack(destDir,filename) {
                @Override
                public void onResponse(File response) {
                    L.d(TAG,"onResponse response filePath=%s",response.getAbsolutePath());
                }

                @Override
                public void onFailure(NetError error) {
                    L.e(TAG,error);
                }

                @Override
                public void onProgress(float progress, long total) {
                    L.d(TAG,"onPregress progress=%s , total =%s",progress,total);
                }
            }, TAG);

#### 取消请求

	HttpClient.getInstance().cancelTag(TAG);

#### Gson相关

1. toJson(Object src)默认会将src对象和其继承的所有非null且非transient、非static修饰的成员变量进行序列化。

2. Collection类型转化
	
	```
	List<ChinessPerson> reverseList=gson.fromJson(jsonStr
            ,new TypeToken<List<ChinessPerson>>() {}.getType());
	```

3. map类型转化
	
	```
	Map<String,String> strMap=gson.fromJson(str
            , new TypeToken<Map<String, String>>(){}.getType());
	```

4. 序列化和反序列带泛型类型的对象，由于java存在泛型察除，需要指定对象的Type。
		
	```
	Foo<Bar> foo = new Foo<Bar>();
	Type fooType = new TypeToken<Foo<Bar>>() {}.getType();
	gson.toJson(foo, fooType);
	gson.fromJson(json, fooType);
	```

5. gson输出格式自动美化:
		
	```
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String jsonOutput = gson.toJson(someObject);
	```
	
6. 配置支持null,默认情况下是不支持输出null的
	```
	//{"s":null,"i":5}
	Gson gson = new GsonBuilder().serializeNulls().create();
	```

7. 将Gson转化后的json字符串按照键值对取出:

	```
	JsonElement jsonElement=gson.toJsonTree(person);
	JsonObject jsonObject=jsonElement.getAsJsonObject();
	Set<Map.Entry<String,JsonElement>> set= jsonObject.entrySet();
	for(Map.Entry<String,JsonElement> element: set){
		if(element.getValue().isJsonPrimitive()){
			L.d(element.getValue().getAsString());
		}else if(element.getValue().isJsonObject()){
			L.d(element.getValue().getAsJsonObject().toString());
		}else if(element.getValue().isJsonArray()){
			L.d(element.getValue().getAsJsonArray().toString());
		}
	}
	```

#### j2se相关

1. 获取类上的泛型类型
	
	```
	public class Foo<T> {
    	public T value;
	}

	public class FooChild extends Foo<ChinessPerson>{
	}

	FooChild foo=new FooChild();
    Type superClass =foo.getClass().getGenericSuperclass();
    Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    L.d(type.toString());

	```