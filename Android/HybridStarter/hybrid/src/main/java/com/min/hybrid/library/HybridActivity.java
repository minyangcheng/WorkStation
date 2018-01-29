package com.min.hybrid.library;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.min.hybrid.library.bean.HybridEvent;
import com.min.hybrid.library.bridge.Bridge;
import com.min.hybrid.library.util.EventUtil;
import com.min.hybrid.library.util.FileUtil;
import com.min.hybrid.library.util.L;
import com.min.hybrid.library.util.ParseUtil;
import com.min.hybrid.library.util.SharePreferenceUtil;
import com.min.hybrid.library.util.Util;
import com.min.hybrid.library.view.HybridWebView;
import com.min.hybrid.library.view.WebViewProgressBar;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import de.greenrobot.event.Subscribe;

public class HybridActivity extends AppCompatActivity {

    public static final String TAG = HybridActivity.class.getSimpleName();
    public static final String KEY_URL = "url";
    public static final String KEY_DATA = "data";

    protected HybridWebView mWebView;
    private WebViewProgressBar mProgressBar;
    private View mErrorContentView;

    private boolean mContinueFlag;
    protected Bridge mBridge;
    protected HybridConfiguration mHybridConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventUtil.register(this);
        setContentView(R.layout.activity_hybrid);
        findView();
        initBridge();
        render();
    }

    private void findView() {
        mWebView = findViewById(R.id.wv);
        mProgressBar = (WebViewProgressBar) findViewById(R.id.pb);
        mErrorContentView = (TextView) findViewById(R.id.view_error);
        mErrorContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mErrorContentView.setVisibility(View.INVISIBLE);
                mWebView.reload();
            }
        });
    }

    private void initBridge() {
        mHybridConfiguration = HybridManager.getInstance().getConfiguration();
        mBridge = new Bridge.Builder()
                .setWebView(mWebView)
                .setWebViewClient(new WebViewClient() {
                    @Override
                    public void onReceivedError(WebView view, int errorCode, final String description, final String failingUrl) {
                        super.onReceivedError(view, errorCode, description, failingUrl);
                        Util.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                handleError(description, failingUrl);
                            }
                        });
                    }

                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        handler.proceed();
                    }
                })
                .setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, final int newProgress) {
                        super.onProgressChanged(view, newProgress);
                        L.d(TAG, "onProgressChanged=%s", newProgress);
                        Util.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                handleProgressChanged(newProgress);
                            }
                        });
                    }
                })
                .addDefaultBridgeApi()
                .addCustomBridgeApi(mHybridConfiguration.getCustomBridgeApiList())
                .build();
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
        EventUtil.unregister(this);
        super.onDestroy();
    }

    protected void render() {
        String url = getIntent().getStringExtra(KEY_URL);
        if (!TextUtils.isEmpty(url)) {
            url = handlePath(url);
        }
        loadUrl(url);
    }

    private String handlePath(String url) {
        if (url.startsWith("/")) {
            url = mHybridConfiguration.getPageHostUrl() + "/#" + url;
        }
        Serializable serializable = getIntent().getSerializableExtra(KEY_DATA);
        if (serializable != null) {
            Map<String, Object> map = (Map<String, Object>) serializable;
            url = Util.joinMapToUrl(url, map);
        }
        if (intercept(url)) {
            url = getLocalUrl(url);
        }
        return url;
    }

    protected void loadUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            L.d(Constants.HYBRID_LOG, "load url=%s", url);
            mWebView.loadUrl(url);
        } else {
            L.d(Constants.HYBRID_LOG, "load empty url");
        }
    }

    protected boolean intercept(String url) {
        return SharePreferenceUtil.getInterceptorActive(this) && url.contains(mHybridConfiguration.getPageHostUrl());
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    protected String getLocalUrl(String url) {
        File webAppDir = new File(FileUtil.getBundleDir(this), "bundle");
        File indexFile = new File(webAppDir, "index.html");
        if (!indexFile.exists()) {
            return url;
        }
        String urlTemplate = "file:///%s/index.html#/%s";
        String[] arr = url.split("#");
        String s = "";
        if (arr.length > 1) {
            s = arr[1].substring(1);
        }
        url = String.format(urlTemplate, webAppDir.getAbsolutePath(), s);
        return url;
    }

    @Subscribe
    public void onEvent(HybridEvent event) {
        L.d("Test", "event=%s", ParseUtil.toJsonString(event));
        mBridge.send(event.type, event.data);
    }

    protected void handleProgressChanged(int newProgress) {
        if (!Util.isNetworkAvailable(this)) {
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        if (newProgress >= 80) {
            if (mContinueFlag) {
                return;
            }
            mWebView.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(100, 3000, new WebViewProgressBar.OnEndListener() {
                @Override
                public void onEnd() {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mErrorContentView.setVisibility(View.INVISIBLE);
                    mContinueFlag = false;
                }
            });
            mContinueFlag = true;
        } else {
            mProgressBar.setProgress(newProgress);
        }
    }

    protected void handleError(String description, String failingUrl) {
        mWebView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(100, 3500, new WebViewProgressBar.OnEndListener() {
            @Override
            public void onEnd() {
                mProgressBar.setVisibility(View.INVISIBLE);
                mErrorContentView.setVisibility(View.VISIBLE);
            }
        });
    }

}
