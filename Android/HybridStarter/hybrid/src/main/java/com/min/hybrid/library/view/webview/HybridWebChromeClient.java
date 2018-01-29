package com.min.hybrid.library.view.webview;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.min.hybrid.library.R;
import com.min.hybrid.library.bridge.Bridge;

public class HybridWebChromeClient extends DelegateChromeClient {

    private WebChromeClient delegate;
    private Context context;

    public HybridWebChromeClient(Bridge bridge) {
        super();
        this.context = bridge.getContext();
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        AlertDialog.Builder b2 = new AlertDialog.Builder(context)
                .setTitle(R.string.app_name).setMessage(message)
                .setPositiveButton(
                        "确认",
                        new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        });

        b2.setCancelable(false);
        b2.create();
        b2.show();
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        AlertDialog.Builder b2 = new AlertDialog.Builder(context)
                .setTitle(R.string.app_name).setMessage(message)
                .setPositiveButton(
                        "确认",
                        new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
        b2.setCancelable(false);
        b2.create();
        b2.show();
        return true;
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("是否允许使用你的位置?");
        DialogInterface.OnClickListener dialogButtonOnClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int clickedButton) {
                if (DialogInterface.BUTTON_POSITIVE == clickedButton) {
                    callback.invoke(origin, true, true);
                } else if (DialogInterface.BUTTON_NEGATIVE == clickedButton) {
                    callback.invoke(origin, false, false);
                }
            }
        };
        builder.setPositiveButton("允许", dialogButtonOnClickListener);
        builder.setNegativeButton("拒绝", dialogButtonOnClickListener);
        builder.show();
    }

    @Override
    protected WebChromeClient getDelegate() {
        return delegate;
    }

    public void setDelegate(WebChromeClient chromeClient) {
        delegate = chromeClient;
    }

}
