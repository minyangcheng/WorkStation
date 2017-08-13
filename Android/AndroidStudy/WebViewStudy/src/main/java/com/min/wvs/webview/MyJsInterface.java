package com.min.wvs.webview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.min.framework.util.L;
import com.min.framework.util.UIHelper;

/**
 * Created by minyangcheng on 2016/9/27.
 */
public class MyJsInterface {

    private Context mContext;
    private WebView mWebView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public MyJsInterface(WebView webView, SwipeRefreshLayout swipeRefreshLayout){
        this.mContext=webView.getContext();
        this.mSwipeRefreshLayout=swipeRefreshLayout;
    }

    @JavascriptInterface
    public void makeVerify() {
        //此类方法运行在子线程中
    }

    @JavascriptInterface
    public void shareClick(final String sharestr){
    }

    @JavascriptInterface
    public void showPictures(String jsonArrStr,int index) {
    }

    @JavascriptInterface
    public void showAlertMsg(String title , String content){
    }

    @JavascriptInterface
    public void setCanRefresh(final boolean canRefresh){
        L.d("WebViewActivity",""+canRefresh+"  "+mSwipeRefreshLayout.isEnabled());
        if(canRefresh&&!mSwipeRefreshLayout.isEnabled()){
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    L.d("WebViewActivity","setCanRefrehs=%s",canRefresh);
                    mSwipeRefreshLayout.setEnabled(true);
                }
            });
        }else if(!canRefresh&&mSwipeRefreshLayout.isEnabled()){
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    L.d("WebViewActivity","setCanRefrehs=%s",canRefresh);
                    mSwipeRefreshLayout.setEnabled(false);
                }
            });
        }
    }

}
