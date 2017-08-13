package com.min.sas;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.min.framework.util.L;
import com.min.framework.util.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG="MainActivity_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DisplayMetrics dm=this.getResources().getDisplayMetrics();
        L.d(TAG, "屏幕信息:%s", dm.toString());
        //每一种密度的dpi范围都有一个最大值，这个最大值之间的比例就是图片会被系统自动放大的比例。
        logDrawableInfo(R.drawable.lufei_hdpi, "hdpi");
        logDrawableInfo(R.drawable.lufei_xhdpi, "xhdpi");
        logDrawableInfo(R.drawable.lufei_xxhdpi, "xxhdpi");
    }

    private void logDrawableInfo(int resId , String preTag){
        Drawable drawable=getResources().getDrawable(resId);
        int width=drawable.getIntrinsicWidth();
        int height=drawable.getIntrinsicHeight();
        L.d(TAG, "%s:width=%s , height=%s",preTag,width,height);
    }

}
