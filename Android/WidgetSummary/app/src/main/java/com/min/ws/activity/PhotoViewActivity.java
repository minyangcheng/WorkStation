package com.min.ws.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.min.ws.R;
import com.min.ws.fragment.conponent.PhotoViewFragment;

public class PhotoViewActivity extends AppCompatActivity {

    private static final String TAG="PhotoViewActivity_TEST";

//    @Bind(R.id.pb)
//    ProgressBar mProgressView;
//    @Bind(R.id.pv)
//    PhotoView mPhotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_photo_view);
//        ButterKnife.bind(this);
        String[] urlArr={"http://img5.duitang.com/uploads/item/201306/26/20130626164033_HdN5j.thumb.700_0.jpeg"
                ,String.valueOf(R.drawable.lufei_1)
                ,"http://image.91wan.com/hzw/resource/yuanhuabizhi/h001/h77/img201209291102280.jpg"};
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, PhotoViewFragment.newInstance(urlArr,0))
                .commit();
    }
}
