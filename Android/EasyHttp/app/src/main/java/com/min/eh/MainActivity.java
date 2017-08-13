package com.min.eh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eh.library.base.BaseResponse;
import com.eh.library.callback.FileCallBack;
import com.eh.library.callback.JsonCallBack;
import com.eh.library.callback.StringCallBack;
import com.eh.library.exception.NetError;
import com.eh.library.http.HttpClient;
import com.eh.library.util.GsonUtil;
import com.min.eh.bean.RecommendUser;
import com.min.eh.bean.api.RegisterApi;
import com.min.eh.bean.api.UploadApi;
import com.min.eh.bean.api.UserInfoGetApi;
import com.min.eh.bean.response.RegisterResponse;
import com.min.eh.util.FilePathUtil;
import com.min.eh.util.L;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_get_https)
    void clickBtnGetHttps(){
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
    }

    @OnClick(R.id.btn_register)
    void clickBtnRegister(){
        RegisterApi api=new RegisterApi();
        api.setUserName("小城");
        api.setPassword("abc123");
        api.setPhone("15257178956");
        api.setRecommendUser(new RecommendUser("zhang", "15179889894"));
        HttpClient.getInstance()
                .request(api, new JsonCallBack<RegisterResponse>() {
                    @Override
                    public void onResponse(RegisterResponse response) {
                        L.d(TAG, "onResponse=%s", GsonUtil.toJson(response));
                    }

                    @Override
                    public void onFailure(NetError error) {
                        L.e(TAG, error);
                    }
                }, TAG);
    }

    @OnClick(R.id.btn_register_syn)
    void clickBtnRegisterSyn(){
        final RegisterApi api=new RegisterApi();
        api.setUserName("min");
        api.setPassword("123");
        api.setPhone("15257178956");
        api.setRecommendUser(new RecommendUser("zhang", "15179889894"));
        new Thread(){
            @Override
            public void run() {
                    HttpClient.getInstance()
                            .requestSync(api, new JsonCallBack<RegisterResponse>() {
                                @Override
                                public void onResponse(RegisterResponse response) {
                                    L.d(TAG,"onResponse=%s",GsonUtil.toJson(response));
                                }
                                @Override
                                public void onFailure(NetError error) {
                                    L.e(TAG,error);
                                }
                            },TAG);
            }
        }.start();
    }

    @OnClick(R.id.btn_upload_file)
    void clickBtnUploadFile(){
        File file=new File(FilePathUtil.getFilesRootDir(MainActivity.this),"bdsjzh.apk");
        UploadApi api=new UploadApi();
        api.setUserName("min");
        api.setPassword("123");
        api.setFile(file);
        HttpClient.getInstance()
                .uploadFile(api, new JsonCallBack<BaseResponse>() {
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
    }

    @OnClick(R.id.btn_download_file)
    void clickBtnDownloadFile(){
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpClient.getInstance().cancelTag(TAG);
    }

}
