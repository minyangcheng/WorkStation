package com.min.study;

import android.os.Bundle;

import com.min.framework.base.BaseActivity;
import com.min.framework.bean.BaseBean;
import com.min.framework.net.CountingRequestBody;
import com.min.framework.net.download.DownloadManager;
import com.min.framework.net.download.FileCallBack;
import com.min.framework.net.download.OkHttpUtil;
import com.min.framework.util.FilePathUtil;
import com.min.framework.util.GsonUtil;
import com.min.framework.util.L;
import com.min.framework.util.RxTransformsHelper;
import com.min.study.app.HttpFactory;
import com.min.study.bean.LoginRespBean;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HttpActivity extends BaseActivity {

    public static final String TAG="HttpActivity_TEST";

    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOkHttpClient= OkHttpUtil.getOkHttpClient();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net;
    }

    @OnClick(R.id.btn_login)
    void clickBtnLogin(){
        HttpFactory.provideApiService()
                .login("15500000001", "123456a")
                .delay(5, TimeUnit.SECONDS)
                .compose(RxTransformsHelper.<LoginRespBean>handleServerResult())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgressDialog();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        dismissProgressDailog();
                    }
                })
                .subscribe(new Action1<LoginRespBean>() {
                    @Override
                    public void call(LoginRespBean loginRespBean) {
                        L.d(TAG, "loginRespBean=%s", GsonUtil.toJson(loginRespBean));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        L.d(TAG, "throwable=%s", throwable);
                    }
                });
    }

    @OnClick(R.id.btn_get)
    void clickBtnGet(){
        HttpFactory.provideApiService()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        L.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.d(TAG, "onError=%s", e.getMessage());
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        L.d(TAG, "onNext=%s", GsonUtil.getGson().toJson(baseBean));
                    }
                });
    }

    @OnClick(R.id.btn_requestbody)
    void clickBtnRequestBody(){
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        String name="nickName";
        final String content="minych";
        builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + name + "\""),
                RequestBody.create(null, content));

        File file=new File("/storage/emulated/0/Android/data/com.min.study/cache/myPhotos/min_1473823782563.jpg");
        MediaType IMAGE = MediaType.parse("image/*");
        RequestBody fileBody = RequestBody.create(IMAGE, file);
        builder.addFormDataPart("this_is_file", file.getName(), fileBody);

        CountingRequestBody requestBody=new CountingRequestBody(builder.build(), new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {
                float progress=bytesWritten/(float)contentLength;
                L.d(TAG,"progress=%s",progress);
            }
        });

        HttpFactory.provideApiService()
                .useRequestBody(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        L.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.d(TAG, "onError=%s", e.getMessage());
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        L.d(TAG, "onNext=%s", GsonUtil.getGson().toJson(baseBean));
                    }
                });
    }

    @OnClick(R.id.btn_multipart)
    void clickBtnMultipart(){
        String name="account";
        String content="332485508";
        MultipartBody.Part namePart= MultipartBody.Part.create(Headers.of("Content-Disposition", "form-data; name=\"" + name + "\""),
                RequestBody.create(null, content));

        File file=new File("/storage/emulated/0/Android/data/com.min.study/cache/myPhotos/min_1473823782563.jpg");
        MediaType IMAGE = MediaType.parse("image/*");
        RequestBody fileBody = RequestBody.create(IMAGE, file);
        MultipartBody.Part photoPart= MultipartBody.Part.createFormData("this_is_file", file.getName(), fileBody);

        HttpFactory.provideApiService()
                .useMultipart(namePart, photoPart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        L.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.d(TAG, "onError=%s", e.getMessage());
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        L.d(TAG, "onNext=%s", GsonUtil.getGson().toJson(baseBean));
                    }
                });
    }

    @OnClick(R.id.btn_download)
    void clickBtnDownload(){
        String url="http://shengan-image.cheguo.com/cgw360/cls/loan_patch/6421d2be-aff4-44a8-a78b-b3fe8920991d.mp4";
        File destFile=new File(FilePathUtil.getFilesRootDir(this),"abc.mp4");
        DownloadManager.getInstance()
                .downloadFile(url, new FileCallBack(destFile) {
                    @Override
                    public void onStart(String url) {
                        L.d(TAG, "onStart url=%s", url);
                    }

                    @Override
                    public void onProgress(String url, long progress, long total) {
                        L.d(TAG, "onProgress url=%s ,p=%s", url, progress * 1f / total);
                    }

                    @Override
                    public void onSuccess(String url, File file) {
                        L.d(TAG, "onSuccess url=%s ,file=%s", url, file.getAbsolutePath());
                    }

                    @Override
                    public void onFail(String url, Throwable t) {
                        L.d(TAG, "onFail url=%s ,t=%s", url, t.getClass().getName());
                    }
                });
    }

    @OnClick(R.id.btn_https)
    void clickBtnHttps(){
        Request request=new Request.Builder()
//                .url("https://www.zhihu.com/")
                .url("https://www.12306.cn/mormhweb/")
                .get()
                .build();
        okhttp3.Call call=mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                L.d(TAG,"onFailure=============================");
                L.e(TAG,e);
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                L.d(TAG,"onResponse=============================");
                L.d(TAG,"onResponse=%s",response.body().string().substring(0,100));
            }
        });
    }

}
