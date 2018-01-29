package com.min.hybrid.library.view.webview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.min.hybrid.library.util.L;
import com.min.hybrid.library.util.Util;

/**
 * Created by minyangcheng on 2018/1/11.
 */

public class HybridWebView extends WebView {

    private static final String TAG = HybridWebView.class.getSimpleName();
    private String mFirstUrl;

    public HybridWebView(Context context) {
        super(context);
        init();
    }

    public HybridWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HybridWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        initConfig();
    }

    protected void initConfig() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
        webSettings.setNeedInitialFocus(true);// 设置是否可以访问文件
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 不使用缓存
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(getContext().getCacheDir().getPath());
        webSettings.setDomStorageEnabled(true);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setTextZoom(100);

        //屏蔽掉长按事件 因为webview长按时将会调用系统的复制控件
        this.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        addJavascriptInterface(new AndroidJSInterface(), "Android");
        requestFocus();
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
        if (TextUtils.isEmpty(url)) {
            mFirstUrl = url;
        }
    }

    public class AndroidJSInterface {

        @JavascriptInterface
        public void refresh() {
            Util.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    L.d(TAG, "refresh originalUrl=%s , url=%s", HybridWebView.this.getOriginalUrl(), HybridWebView.this.getUrl());
                    HybridWebView.this.reload();
                }
            });
        }

    }

}
