package com.min.wvs;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.min.framework.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.et)
    EditText mEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_open_webview)
    void clickOpenWebView(){
        if(!TextUtils.isEmpty(mEt.getText().toString())){
            WebViewActivity.startActivity(this,"this is title",mEt.getText().toString());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
