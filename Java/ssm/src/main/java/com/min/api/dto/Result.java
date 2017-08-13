package com.min.api.dto;

public class Result<T> {

    private int code;
    private String message;
    private T data;

    public Result() {
    }

    // 成功时的构造器
    public Result(T data, String message) {
        this.code = 10000;
        this.data = data;
        this.message = message;
    }

    // 错误时的构造器
    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
