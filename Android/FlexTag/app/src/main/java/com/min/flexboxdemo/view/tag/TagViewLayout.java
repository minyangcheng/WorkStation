package com.min.flexboxdemo.view.tag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.flexbox.FlexboxLayout;

import java.util.HashSet;

/**
 * Created by minyangcheng on 2016/6/1.
 */
public class TagViewLayout extends FlexboxLayout {

    private TagAdapter mAdapter;

    private HashSet<Integer> mSelectSet=new HashSet<>();

    public TagViewLayout(Context context) {
        this(context, null);
    }

    public TagViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TagViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFlexDirection(FLEX_DIRECTION_ROW);
        setFlexWrap(FLEX_WRAP_WRAP);
        setAlignItems(ALIGN_ITEMS_FLEX_START);
        setAlignContent(ALIGN_CONTENT_FLEX_START);
        setJustifyContent(JUSTIFY_CONTENT_FLEX_START);
    }

    public void setAdapter(TagAdapter adapter){
        if(adapter==null) return;
        mAdapter=adapter;
        mAdapter.setTagViewLayout(this);
        notifyDataChange();
    }

    public void notifyDataChange(){
        mSelectSet.clear();
        removeAllViews();
        View view;
        for(int i=0;i<mAdapter.getCount();i++){
            view=mAdapter.getView(this,i);
            if(mAdapter.getPreSelectSet()!=null&&mAdapter.getPreSelectSet().contains(i)){
                mAdapter.setSelectViewBg(TagViewLayout.this, i, view);
            }else{
                mAdapter.setUnSelectViewBg(TagViewLayout.this, i, view);
            }
            if(mAdapter.getPreSelectSet()!=null){
                mSelectSet.addAll(mAdapter.getPreSelectSet());
            }
            addView(view);
            setItemViewClickListener(i,view);
        }
    }

    private void setItemViewClickListener(final int i, View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer=i;
                if(mSelectSet.contains(integer)){
                    mSelectSet.remove(integer);
                    mAdapter.setUnSelectViewBg(TagViewLayout.this, i, v);
                }else{
                    mSelectSet.add(i);
                    mAdapter.setSelectViewBg(TagViewLayout.this, i, v);
                }
            }
        });
    }

    HashSet<Integer> getSelectSet(){
        return mSelectSet;
    }

}
