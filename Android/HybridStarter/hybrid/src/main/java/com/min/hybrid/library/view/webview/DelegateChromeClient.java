package com.min.hybrid.library.view.webview;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

public abstract class DelegateChromeClient extends WebChromeClient {

    protected abstract WebChromeClient getDelegate();

    protected boolean hasDelegate() {
        return getDelegate() != null;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (hasDelegate()) {
            getDelegate().onProgressChanged(view, newProgress);
        } else {
            super.onProgressChanged(view, newProgress);
        }
    }
}
