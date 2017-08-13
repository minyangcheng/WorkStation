package com.min.study;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.blankj.utilcode.utils.BarUtils;
import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.DeviceUtils;
import com.blankj.utilcode.utils.EncryptUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.SDCardUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.min.framework.util.AESUtil;
import com.min.framework.util.L;

import java.io.File;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    public static final String TAG="TestActivity_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        L.d(TAG, "onCreate");
    }

    @OnClick(R.id.btn_test)
    void clickBtn(){
//        try {
//            String s= StringUtils.getPYFirstLetter("闵");
//            boolean b= RegexUtils.isChz("a");
//            boolean c= RegexUtils.isChz("一");
//            L.d(TAG,"b=%s",s);
//            L.d(TAG,"b=%s",b);
//            L.d(TAG,"b=%s",c);
//        } catch (Exception e) {
//            L.e(TAG,e);
//        }
        byte[] bytes={~((byte)3)};
        String str=ConvertUtils.bytes2Bits(bytes);
        L.d(TAG,"str=%s",str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d(TAG,"onDestroy");
    }

    /**
     * 获取256位的加密密钥
     *
     * @param seed
     * @return
     * @throws Exception
     */
    @SuppressLint("TrulyRandom")
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = null;
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else {
            sr = SecureRandom.getInstance("SHA1PRNG");
        }
        sr.setSeed(seed);
        // 256 bits or 128 bits,192bits
        kgen.init(256, sr);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

}
