package com.min.study;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.min.framework.base.BaseActivity;
import com.min.framework.util.ImageLoaderWrap;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.OnClick;

public class ImageLoaderActivity extends BaseActivity {

    @Bind(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_loader;
    }

    @OnClick(R.id.btn_get_image)
    void clickBtnGetImage(){
        String imageUrl="https://pic2.zhimg.com/v2-88e45e0a0879c2f29fcda2ee9097a4a9_r.jpg";
        ImageLoaderWrap.displayHttpImage(imageUrl,iv);
    }

}
