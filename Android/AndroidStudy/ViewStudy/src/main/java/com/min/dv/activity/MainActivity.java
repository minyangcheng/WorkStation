package com.min.dv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.min.dv.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_event_study)
    void clickBtnEventStudy(){
        go(ViewEventActivity.class);
    }

    @OnClick(R.id.btn_canvas_study)
    void clickBtnCanvasStudy(){
        go(CanvasActivity.class);
    }

    @OnClick(R.id.btn_ruler)
    void clickBtnRuler(){
        go(RulerActivity.class);
    }

    @OnClick(R.id.btn_multi_touch)
    void clickBtnMultiTouch(){
        go(MultiTouchActivity.class);
    }

    @OnClick(R.id.btn_image_scale)
    void clickBtnImageScale(){
        go(ImageViewScaleActivity.class);
    }

    @OnClick(R.id.btn_view_anim)
    void clickBtnViewAnim(){
        go(ViewAnimActivity.class);
    }

    private void go(Class clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

}
