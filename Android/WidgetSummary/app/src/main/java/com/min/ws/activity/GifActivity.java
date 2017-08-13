package com.min.ws.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;

import com.min.ws.R;
import com.min.ws.util.ImageLoaderWrap;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GifActivity extends AppCompatActivity {

    @Bind(R.id.giv_2)
    GifImageView mGiv_2;
    @Bind(R.id.giv_3)
    GifImageView mGiv_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        ButterKnife.bind(this);

        mGiv_2.setImageResource(R.drawable.deleting_anim);

        String url="drawable://" + R.drawable.deleting_anim;
        ImageLoaderWrap.loadImage(url, new ImageLoaderWrap.ImageLoadListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                try {
                    File gifFile=ImageLoader.getInstance().getDiskCache().get(imageUri);
                    GifDrawable gifFromFile = new GifDrawable(gifFile);
                    mGiv_3.setImageDrawable(gifFromFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
}
