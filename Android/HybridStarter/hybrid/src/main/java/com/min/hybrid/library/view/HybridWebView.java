package com.min.hybrid.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by minyangcheng on 2018/1/11.
 */

public class HybridWebView extends WebView {

    public HybridWebView(Context context) {
        this(context, null);
    }

    public HybridWebView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HybridWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
