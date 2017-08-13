package com.min.eh.bean.api;

import com.eh.library.base.BaseApi;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by minyangcheng on 2016/8/7.
 */
public class UploadApi extends BaseApi {

    private String userName;

    private String password;

    private File file;

    @Override
    public String getPath() {
        return "http://10.10.13.117:49999/";
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
