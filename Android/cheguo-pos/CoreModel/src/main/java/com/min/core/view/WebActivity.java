package com.min.core.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.min.core.R;

public class WebActivity extends AppCompatActivity {

    private static final String URL = "url";
    private TextView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("webview");
        WebView webView = (WebView) findViewById(R.id.web_view);
        String url = getIntent().getStringExtra(URL);
        if (url == null && getIntent().getData() != null) {
            url = getIntent().getData().getQueryParameter(URL);
        }
        if (url == null) {
            finish();
        }
        webView.setWebViewClient(new InnerWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        loading = (TextView) findViewById(R.id.loading);
        Animation animation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(500);
        loading.startAnimation(animation);
        setTitle(url);
    }


    public class InnerWebViewClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            loading.clearAnimation();
            loading.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            Animation animation = new AlphaAnimation(0.1f, 1.0f);
            animation.setDuration(800);
            view.setAnimation(animation);
            setTitle(view.getTitle());
        }
    }
}
