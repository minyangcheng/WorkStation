package com.min.hybrid.library.view.webview;

import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.min.hybrid.library.bridge.Bridge;
import com.min.hybrid.library.bridge.Envelope;
import com.min.hybrid.library.Constants;
import com.min.hybrid.library.util.L;
import com.min.hybrid.library.util.ParseUtil;
import com.min.hybrid.library.view.webview.DelegateWebViewClient;

import java.net.URI;
import java.net.URLDecoder;

public class HybridWebViewClient extends DelegateWebViewClient {

    private Bridge mBridge;
    private WebViewClient mDelegate;

    public HybridWebViewClient(Bridge bridge) {
        mBridge = bridge;
    }

    public void setDelegate(WebViewClient client) {
        mDelegate = client;
    }

    @Override
    public WebViewClient getDelegate() {
        return mDelegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (getDelegate() != null && getDelegate().shouldOverrideUrlLoading(view, url)) {
            return true;
        }
        try {
            L.d("Test","url=%s", URLDecoder.decode(url));
            URI uri = new URI(url);
            if (validateScheme(uri)) {
                processUri(view, uri);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateScheme(URI uri) {
        return uri.getScheme().equals(Constants.BRIDGE_SCHEME) && !uri.getQuery().equals("");
    }

    public void processUri(WebView view, URI uri) {
        try {
            String[] parts = uri.getPath().replaceAll("^\\/", "").split("/");
            String host = uri.getHost();
            Envelope envelope = ParseUtil.parseObject(uri.getQuery(), Envelope.class);
            if (parts.length > 0) {
                if (host.equals("event")) {
                    mBridge.triggerEventFromWebView(view, envelope);
                } else if (host.equals("callback")) {
                    mBridge.triggerCallbackFromWebView(
                            Integer.parseInt(parts[0]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//        String tempUrl = url.replace("/webapp", "");
//        Uri uri = Uri.parse(tempUrl);
//        if (uri.getHost().equals(mBridge.getInterceptHost())) {
////            File file = new File(FileUtil.getRootDir(view.getContext()).getAbsolutePath() + "/" + HybridConfig.FILE_HYBRID_DATA_PATH + "/" + uri.getPath());
//            File file = null;
//            if (file.exists()) {
//                try {
//                    InputStream localCopy = new FileInputStream(file);
//                    String mimeType = getMimeType(tempUrl);
//                    return new WebResourceResponse(mimeType, "UTF-8", localCopy);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return super.shouldInterceptRequest(view, url);
    }

    private String getMimeType(String url) {
        if (url.contains(".")) {
            int index = url.lastIndexOf(".");
            if (index > -1) {
                int paramIndex = url.indexOf("?");
                String type = url.substring(index + 1, paramIndex == -1 ? url.length() : paramIndex);
                switch (type) {
                    case "js":
                        return "text/javascript";
                    case "css":
                        return "text/css";
                    case "html":
                        return "text/html";
                    case "png":
                        return "image/png";
                    case "jpg":
                        return "image/jpg";
                    case "gif":
                        return "image/gif";
                }
            }
        }
        return "text/plain";
    }

}