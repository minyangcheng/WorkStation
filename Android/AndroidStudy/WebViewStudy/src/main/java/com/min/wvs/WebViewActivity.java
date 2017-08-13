package com.min.wvs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.min.framework.base.BaseActivity;
import com.min.framework.util.L;
import com.min.wvs.webview.MyJsInterface;
import com.min.wvs.webview.MyWebChromeClient;

import butterknife.Bind;

/**
 * Created by minyangcheng on 2016/9/28.
 */
public class WebViewActivity extends BaseActivity {

    private static final String TAG="WebViewActivity_TEST";

    private static final String ARG_TITLE="titleArg";
    private static final String ARG_URL="urlArg";

    private static final String JS_JAVA="cgwapp";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.webview)
    WebView mWebView;
    @Bind(R.id.view_error)
    View mErrorView;
    @Bind(R.id.btn_retry)
    View mRetryBtn;

    protected boolean isError;
    protected String title;
    protected String url;

    public static void startActivity(Context context,String title,String url){
        Intent intent=new Intent(context,WebViewActivity.class);
        intent.putExtra(ARG_TITLE, title);
        intent.putExtra(ARG_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        initView();
        showContentStatus();
        mWebView.loadUrl(url);
    }

    private void getDataFromIntent() {
        Intent intent=getIntent();
        title=intent.getStringExtra(ARG_TITLE);
        if(title==null){
            title="";
        }
        url=intent.getStringExtra(ARG_URL);
    }

    private void initView() {
        initToolbar();
        initWebview();
        mRetryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    protected void initToolbar() {
        if(!TextUtils.isEmpty(title)){
            mToolbar.setTitle(title);
        }
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        mToolbar.inflateMenu(R.menu.car_type);
//        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                UIUtils.toast(ToolbarActivity.this,item.getTitle().toString());
//                return false;
//            }
//        });
//    }
    }

    protected void initWebview(){
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
        webSettings.setNeedInitialFocus(false);// 设置是否可以访问文件
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 不使用缓存
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(getFilesDir().getPath());
        webSettings.setDomStorageEnabled(true);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.requestFocus();

        //屏蔽掉长按事件 因为webview长按时将会调用系统的复制控件
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        mWebView.addJavascriptInterface(new MyJsInterface(mWebView,mSwipeRefreshLayout), JS_JAVA);
        mWebView.setWebChromeClient(new MyWebChromeClient(this));
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    private void getShareFromHtml(){
        mWebView.loadUrl("javascript:(function() { \n" +
                "\tvar videos = document.getElementsByTagName('audio'); \n" +
                "\tfor(var i=0;i<videos.length;i++){\n" +
                "\t\t\tvideos[i].play();\n" +
                "\t};\n" +
                "\tvar sharestr = document.getElementById(\"shareData\").innerText;window.cgwapp.shareClick(sharestr);\n" +
                "})();");
    }

    private void refresh(){
        isError = false;
        L.d(TAG, "refresh url=%s", mWebView.getUrl());
        if (!TextUtils.isEmpty(mWebView.getUrl())) {
            mWebView.reload();
        } else {
            mWebView.loadUrl(url);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(!isError){
            if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showContentStatus(){
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }

    private void showErrorStatus(){
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        mWebView.onResume();
        mWebView.resumeTimers();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mWebView.onPause();
        mWebView.pauseTimers();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.stopLoading();
        try {
            if (mWebView != null) {
                ViewGroup parent = (ViewGroup) mWebView.getParent();
                if (parent != null) {
                    parent.removeView(mWebView);
                }
                mWebView.removeAllViews();
                mWebView.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            L.d(TAG, "shouldOverrideUrlLoading url=%s",url);
            if(url.startsWith("tel:")){

            }else{
                view.loadUrl(url);
                showContentStatus();
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            L.d(TAG, "onPageFinished title=%s url=%s", view.getTitle(), url);
            if(isFinishing()) return;
            mSwipeRefreshLayout.setRefreshing(false);
            if (TextUtils.isEmpty(title)) {
                String htmlTitle=view.getTitle();
                if(!TextUtils.isEmpty(htmlTitle)){
                    mToolbar.setTitle(htmlTitle);
                }
            }
            if (!isError) {
                getShareFromHtml();
                showContentStatus();
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            L.d(TAG, "onPageStarted title=%s url=%s", view.getTitle(), url);
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String
                failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if(isFinishing()) return;
            L.d(TAG, "onReceivedError url=%s", view.getUrl());
            mSwipeRefreshLayout.setRefreshing(false);
            isError = true;
            String data = " ";
            mWebView.loadUrl("javascript:document.body.innerText=\"" + data + "\"");
            showErrorStatus();
        }

    }

}
