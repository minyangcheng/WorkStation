package com.min.dialog.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.min.dialog.util.StatusBarUtils;

import butterknife.ButterKnife;

public abstract class BasePopupWindow extends PopupWindow {

    protected String tag;

    protected Context mContext;

    private View mRootView;

    private float mWidthScale;
    private float mHeightScale;
    private int mMaxHeight;
    private int mMaxWidth;

    public BasePopupWindow(Context context){
        super(context);
        tag=this.getClass().getSimpleName();
        mContext=context;
        initMaxHeightAndWidth(context);
        init();
    }

    private void init() {
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setParams();
        setWidth(getScaleValue(mMaxWidth, mWidthScale));
        setHeight(getScaleValue(mMaxHeight, mHeightScale));
        mRootView=onCreateView();
        onViewCreate(mRootView);
        setContentView(mRootView);
    }

    private void initMaxHeightAndWidth(Context context){
        DisplayMetrics dm=context.getResources().getDisplayMetrics();
        mMaxHeight=dm.heightPixels - StatusBarUtils.getHeight(context);
        mMaxWidth=dm.widthPixels;
    }

    protected void setParams(){
        setWidthScale(mWidthScale);
        setHeightScale(mHeightScale);
    }

    protected void setWidthScale(float scale){
        mWidthScale=scale;
    }

    protected void setHeightScale(float scale){
        mHeightScale=scale;
    }

    private int getScaleValue(int max,float scale){
        if(scale<0||scale>1f){
            throw new IllegalArgumentException("mWidthScale or mHeightScale must be 0 to 1");
        }
        int value;
        if(scale==0){
            value= ViewGroup.LayoutParams.WRAP_CONTENT;
        }else if(scale==1){
            value= ViewGroup.LayoutParams.MATCH_PARENT;
        }else{
            value= (int) (max*scale);
        }
        return value;
    }

    protected void onViewCreate(View rootView) {}

    protected View onCreateView(){
        View view= LayoutInflater.from(mContext).inflate(getLayoutId(),null);
        ButterKnife.bind(this,view);
        return view;
    }

    protected abstract int getLayoutId();

}
