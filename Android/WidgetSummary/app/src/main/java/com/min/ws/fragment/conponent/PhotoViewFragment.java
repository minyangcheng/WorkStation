package com.min.ws.fragment.conponent;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.min.ws.R;
import com.min.ws.util.L;
import com.min.ws.util.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by minyangcheng on 2016/9/22.
 */
public class PhotoViewFragment extends Fragment {

    private static final String TAG="PhotoViewFragment_TEST";

    private static final String ARG_SOURCE_Arr="sourceArrArg";
    private static final String ARG_POS="posArg";

    private Context mContext;
    private ViewPager mVp;
    private TextView mNumTv;

    private String[] sourceArr;
    private int pos;

    private List<ImageItem> mImageItemList;

    /**
     * sourceArr中的字符串对象将会自动被识别加载源
     * 1、从网络url加载，直接放入url string值
     * 2、从res资源加载，直接放入int 的string值
     * 3、从file中加载，放入file 路径string值
     * @param sourceArr
     * @param pos
     * @return
     */
    public static PhotoViewFragment newInstance(String[] sourceArr,int pos){
        PhotoViewFragment photoViewFragment=new PhotoViewFragment();
        Bundle bundle=new Bundle();
        bundle.putStringArray(ARG_SOURCE_Arr,sourceArr);
        bundle.putInt(ARG_POS,pos);
        photoViewFragment.setArguments(bundle);
        return photoViewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        sourceArr =bundle.getStringArray(ARG_SOURCE_Arr);
        pos=bundle.getInt(ARG_POS);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.fragment_photo_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVp= (ViewPager) view.findViewById(R.id.vp);
        mNumTv= (TextView) view.findViewById(R.id.tv);
        mImageItemList=createImageItemListByUrlArr();

//        MyPageTransformer transformer=new MyPageTransformer();
//        mVp.setPageTransformer(false,transformer);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                L.d(TAG, "onPageSelected=%s", position);
                displayImage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ImagePageAdapter pageAdapter=new ImagePageAdapter(mImageItemList);
        mVp.setAdapter(pageAdapter);
        if(pos==0){
            displayImage(pos);
        }
        mVp.setCurrentItem(pos);
    }

    private void displayImage(int pos){
        mImageItemList.get(pos).loadImage();
        mNumTv.setText((pos+1)+"/"+mImageItemList.size());
    }

    private List<ImageItem> createImageItemListByUrlArr() {
        if(sourceArr ==null){
            return null;
        }
        List<ImageItem> imageItemList=new ArrayList<>();
        for(int i=0;i< sourceArr.length;i++){
            imageItemList.add(getImageItem(sourceArr[i]));
        }
        return imageItemList;
    }

    private ImageItem getImageItem(String imageUrl){
        ImageItem imageItem=new ImageItem();

        RelativeLayout imageLayout=new RelativeLayout(mContext);
        PhotoView imageView = new PhotoView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(lp);
        imageLayout.addView(imageView);

        ProgressBar wheel= new ProgressBar(mContext);
        wheel.setIndeterminateDrawable(mContext.getResources().getDrawable(R.drawable.img_progress));
        int width= ScreenUtils.dpToPxInt(mContext, 60);
        lp=new RelativeLayout.LayoutParams(width, width);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        wheel.setLayoutParams(lp);
        imageLayout.addView(wheel);

        imageItem.sourceStr =imageUrl;
        imageItem.imageLayout=imageLayout;
        imageItem.imageView=imageView;
        imageItem.wheel=wheel;
        return imageItem;
    }

    public class ImagePageAdapter extends PagerAdapter {

        private List<ImageItem> imageItems;

        public ImagePageAdapter(List<ImageItem> imageItems){
            this.imageItems=imageItems;
        }

        @Override
        public int getCount() {
            return imageItems.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageItems.get(position).imageLayout);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=imageItems.get(position).imageLayout;
            container.addView(view);
            return view;
        }
    }

    public class ImageItem {

        public String sourceStr;
        public RelativeLayout imageLayout;
        public ProgressBar wheel;
        public PhotoView imageView;

        private void loadImage() {
            SourceType sourceType=judgeSourceType();
            if(sourceType==SourceType.NET){
                loadUrlImage(sourceStr);
            }else if(sourceType==SourceType.RES){
                try {
                    int resId=Integer.parseInt(sourceStr);
                    loadResIdImage(resId);
                } catch (Exception e) {
                }
            }else{
                File file=new File(sourceStr);
                loadFileImage(file);
            }
        }

        private void loadUrlImage(String url){
            Glide.with(PhotoViewFragment.this)
                    .load(url)
                    .error(R.drawable.img_holder_error)
                    .crossFade()
                    .into(getImageViewTarget());
        }

        private void loadResIdImage(int resId){
            Glide.with(PhotoViewFragment.this)
                    .load(resId)
                    .error(R.drawable.img_holder_error)
                    .crossFade()
                    .into(getImageViewTarget());
        }

        private void loadFileImage(File file){
            Glide.with(PhotoViewFragment.this)
                    .load(file)
                    .error(R.drawable.img_holder_error)
                    .crossFade()
                    .into(getImageViewTarget());
        }

        private GlideDrawableImageViewTarget getImageViewTarget(){
            return new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        wheel.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        wheel.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setZoomable(false);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        wheel.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setZoomable(true);
                    }
                };
        }

        private SourceType judgeSourceType(){
            SourceType sourceType=null;
            if(sourceStr.startsWith("http://")|| sourceStr.startsWith("https://")){
                sourceType=SourceType.NET;
            }else if(sourceStr.indexOf("/")==-1){
                sourceType=SourceType.RES;
            }else {
                sourceType=SourceType.FILE;
            }
            return sourceType;
        }

    }

    private enum SourceType{
        NET,RES,FILE
    }

    private class MyPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when
                // moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);
                // Counteract the default slide transition
                view.setTranslationX(pageWidth / 2 * -position);
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }

    }

}
