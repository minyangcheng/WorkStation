package com.min.ws.view.tab;

import android.content.Context;
import android.graphics.Color;
import android.net.MailTo;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.min.ws.R;

public class TabView extends FrameLayout {

    private Context mContext;

    private ImageView mTabImageIv;
    private TextView mTabTextTv;

    private int mSelectColor;
    private int mUnSelectColor;

    private int mSelectImageResId;
    private int mUnSelectImageResId;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext=context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(mContext, R.layout.view_tab, this);
        initViews();
    }

    private void initViews() {
        mTabImageIv= (ImageView) findViewById(R.id.iv_tab_image);
        mTabTextTv= (TextView) findViewById(R.id.tv_tab_text);
    }

    public void setTabSelectImage(int resId){
        if(resId<=0) return;
        mSelectImageResId=resId;
    }

    public void setUnSelectImage(int resId){
        if(resId<=0) return;
        mUnSelectImageResId=resId;
    }

    public void setTabText(String str){
        if(TextUtils.isEmpty(str)) return;
        mTabTextTv.setText(str);
    }

    public void setTabTextSize(float textSize){
        mTabTextTv.setTextSize(textSize);
    }

    public void setSelectColor(int color){
        mSelectColor=color;
    }

    public void setUnSelectColor(int color){
        mUnSelectColor=color;
    }

    public void setSelect(boolean select){
        if(select){
            mTabImageIv.setImageResource(mSelectImageResId);
            mTabTextTv.setTextColor(mSelectColor);
        }else{
            mTabImageIv.setImageResource(mUnSelectImageResId);
            mTabTextTv.setTextColor(mUnSelectColor);
        }
    }

}
