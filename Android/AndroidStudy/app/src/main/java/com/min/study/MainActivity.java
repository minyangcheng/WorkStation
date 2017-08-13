package com.min.study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.min.framework.util.L;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final LayoutInflater inflater=LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(inflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                L.d(TAG,"name=%s",name);
                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);
                if(view==null){
                    try {
                        view=inflater.createView(name,name,attrs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return view;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.d(TAG,"button-->name=%s",findViewById(R.id.et).getClass().getName());
    }

    @OnClick(R.id.btn_photo)
    void clickPhoto() {
        go(PhotoActivity.class);
    }

    @OnClick(R.id.btn_net)
    void clickNet(){
        go(HttpActivity.class);
    }

    @OnClick(R.id.btn_test)
    void clickTest(){
        go(TestActivity.class);
    }

    @OnClick(R.id.btn_list)
    void clickList(){
        go(ListActivity.class);
    }

    @OnClick(R.id.btn_imageloader)
    void clickImageLoader(){
        go(ImageLoaderActivity.class);
    }

    @OnClick(R.id.btn_download_manager)
    void clickDownloadManager(){
        go(DownloadActivity.class);
    }

    private void go(Class clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

}
