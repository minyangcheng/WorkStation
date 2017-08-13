package com.min.ws.view.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.min.ws.R;

import java.util.ArrayList;
import java.util.List;

public class TabIndicator extends LinearLayout {

    private Context mContext;

    private int[] mTabSelectedImageResIds;
    private int[] mTabUnselectedImageResIds;

    private String[] mTabTexts;
    private int mSelectColor;
    private int mUnSelectColor;
    private float mTextSize;

    protected List<TabView> mTabViewList;

    protected OnTabClickListener mOnTabClickListener;

    private int mCurrentIndex=-1;

    public TabIndicator(Context context) {
        this(context,null);
    }

    public TabIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {

        int defaultSelectColor= Color.RED;
        int defaultUnSelectColor= Color.BLACK;
        int defaultTextSize=10;

        TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TabIndicator, defStyleAttr, 0);

        int textArrResId=a.getResourceId(R.styleable.TabIndicator_tabTextArr,0);
        mTabTexts =mContext.getResources().getStringArray(textArrResId);

        mSelectColor=a.getColor(R.styleable.TabIndicator_tabSelectColor, defaultSelectColor);
        mUnSelectColor=a.getColor(R.styleable.TabIndicator_tabUnSelectColor, defaultUnSelectColor);

        mTextSize= a.getDimension(R.styleable.TabIndicator_tabTextSize, defaultTextSize);
        mTextSize= mTextSize/mContext.getResources().getDisplayMetrics().scaledDensity;

        int selectedImageResId = a.getResourceId(R.styleable.TabIndicator_tabSelectImageArr, 0);
        TypedArray ta = mContext.getResources().obtainTypedArray(selectedImageResId);
        int len = ta.length();
        mTabSelectedImageResIds = new int[len];
        for(int i = 0; i < len; i++) {
            mTabSelectedImageResIds[i] = ta.getResourceId(i, 0);
        }
        ta.recycle();

        int unSelectedImageResId = a.getResourceId(R.styleable.TabIndicator_tabUnSelectImageArr, 0);
        ta = mContext.getResources().obtainTypedArray(unSelectedImageResId);
        len = ta.length();
        mTabUnselectedImageResIds = new int[len];
        for(int i = 0; i < len; i++) {
            mTabUnselectedImageResIds[i] = ta.getResourceId(i, 0);
        }
        ta.recycle();

        a.recycle();
        initViews();
    }

    private void initViews() {
        mTabViewList=new ArrayList<>();

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,1);
        TabView tabView=null;
        for(int i=0;i< mTabTexts.length;i++){
            tabView=new TabView(mContext);
            tabView.setTabSelectImage(mTabSelectedImageResIds[i]);
            tabView.setUnSelectImage(mTabUnselectedImageResIds[i]);
            tabView.setTabText(mTabTexts[i]);
            tabView.setSelectColor(mSelectColor);
            tabView.setUnSelectColor(mUnSelectColor);
            tabView.setSelect(false);
            tabView.setTabTextSize(mTextSize);
            tabView.setLayoutParams(lp);
            tabView.setTag(i);
            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (int) v.getTag();
                    selectTab(index);

                    if (mOnTabClickListener != null) {
                        mOnTabClickListener.onTabClick(index);
                    }
                }
            });
            addView(tabView);
            mTabViewList.add(tabView);
        }
        selectTab(0);  //默认选中
    }

    public void selectTab(int index){
        if(mCurrentIndex==index) return;

        mCurrentIndex=index;
        TabView tabView=null;
        for(int i=0;i<mTabTexts.length;i++){
            tabView=mTabViewList.get(i);
            tabView.setSelect(index==i);
        }
    }

    public TabView getTabView(int index){
        return  mTabViewList.get(index);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle=new Bundle();
        bundle.putParcelable("parcelable",super.onSaveInstanceState());
        bundle.putInt("currentIndex", mCurrentIndex);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle= (Bundle) state;
        super.onRestoreInstanceState(bundle.getParcelable("parcelable"));
        int index=bundle.getInt("currentIndex");
        selectTab(index);
    }

    public void setOnTabClickListener(OnTabClickListener listener){
        this.mOnTabClickListener =listener;
    }

    public interface OnTabClickListener {
        public void onTabClick(int index);
    }

}
