package com.min.core.base;

public interface BasePresenter<V extends BaseView> {

    void attachView(V mvpView);

    void detachView();
}
