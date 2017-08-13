package com.min.flexboxdemo.view.tag;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by minyangcheng on 2016/6/1.
 */
public abstract class TagAdapter<T> {

    protected Context mContext;
    protected List<T> mDataList;
    private TagViewLayout mTagViewLayout;

    private HashSet<Integer> mPreSelectSet;

    public TagAdapter(Context context){
        mContext=context;
    }

    public void setData(List<T> data){
        mDataList=data;
        mPreSelectSet=null;
        notifyDataSetChange();
    }

    public void notifyDataSetChange(){
        if(mTagViewLayout!=null){
            mTagViewLayout.notifyDataChange();
        }
    }

    public void setPreSelectSet(HashSet<Integer> preSelectSet){
        mPreSelectSet=preSelectSet;
        notifyDataSetChange();
    }

    public void setPreSelectSet(int...index){
        mPreSelectSet=new HashSet<>();
        for(int i=0;i<index.length;i++){
            mPreSelectSet.add(index[i]);
        }
        notifyDataSetChange();
    }

    public HashSet<Integer> getPreSelectSet(){
        return mPreSelectSet;
    }

    public HashSet<Integer> getSelectSet(){
        if(mTagViewLayout!=null){
            return mTagViewLayout.getSelectSet();
        }
        return null;
    }

    void setTagViewLayout(TagViewLayout tagViewLayout){
        mTagViewLayout=tagViewLayout;
    }

    public List<T> getData(){
        return mDataList;
    }

    public int getCount(){
        return mDataList==null?0:mDataList.size();
    }

    public abstract View getView(ViewGroup viewParent,int position);

    public abstract void setSelectViewBg(ViewGroup viewParent, int position, View view);

    public abstract void setUnSelectViewBg(ViewGroup viewParent, int position, View view);

}
