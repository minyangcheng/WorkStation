package com.min.weex;

/**
 * Created by minych on 18-3-16.
 */

public class ImageAdapter implements IWXImgLoaderAdapter {


    @Override
    public void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
        //实现你自己的图片下载，否则图片无法显示。
    }
}
