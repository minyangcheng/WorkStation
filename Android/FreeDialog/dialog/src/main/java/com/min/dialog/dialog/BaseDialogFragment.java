package com.min.dialog.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.min.dialog.util.StatusBarUtils;

import butterknife.ButterKnife;

public abstract class BaseDialogFragment extends DialogFragment {

    public String tag;

    private float mWidthScale;
    private float mHeightScale;

    private int mMaxHeight;
    private int mMaxWidth;

    protected Context mContext;

    public BaseDialogFragment(){
        super();
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        tag=this.getClass().getSimpleName();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        initMaxHeightAndWidth(getContext());
    }

    private void initMaxHeightAndWidth(Context context){
        DisplayMetrics dm=context.getResources().getDisplayMetrics();
        mMaxHeight=dm.heightPixels - StatusBarUtils.getHeight(context);
        mMaxWidth=dm.widthPixels;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getLayoutId()>0){
            View view=inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, view);
            return view;
        }else {
            return super.onCreateView(inflater,container,savedInstanceState);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected abstract int getLayoutId();

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog=getDialog();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        setParams(dialog);
        lp.width=getScaleValue(mMaxWidth,mWidthScale);
        lp.height=getScaleValue(mMaxHeight,mHeightScale);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    protected void setParams(Dialog dialog){
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
            value= WindowManager.LayoutParams.WRAP_CONTENT;
        }else if(scale==1){
            value=WindowManager.LayoutParams.MATCH_PARENT;
        }else{
            value= (int) (max*scale);
        }
        return value;
    }

}
