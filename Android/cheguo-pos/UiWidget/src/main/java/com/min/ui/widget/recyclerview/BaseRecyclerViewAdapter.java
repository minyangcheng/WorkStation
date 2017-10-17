package com.min.ui.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> mList;
    public Context mContext;
    protected OnItemClickLitener mOnItemClickLitener;
    protected OnItemLongClickLitener mOnItemLongClickListener;

    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        else
            return 0;
    }

    public void setData(List<T> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mList;
    }

    public void setData(T[] list) {
        ArrayList<T> arrayList = new ArrayList<>(list.length);
        for (T t : list) {
            arrayList.add(t);
        }
        setData(arrayList);
    }


    public void addData(int position, T item) {
        if (mList != null && position < mList.size()) {
            mList.add(position, item);
            notifyMyItemInserted(position);
        }
    }

    public void removeData(int position) {
        if (mList != null && position < mList.size()) {
            mList.remove(position);
            notifyMyItemRemoved(position);
        }
    }

    public void notifyMyItemInserted(int position){
        notifyItemInserted(position);
    }

    public void notifyMyItemRemoved(int position){
        notifyItemRemoved(position);
    }

    public void notifyMyItemChanged(int position){
        notifyItemChanged(position);
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemLongClickLitener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemLongClickLitener(OnItemLongClickLitener onItemLongClickLitener) {
        this.mOnItemLongClickListener = onItemLongClickLitener;
    }

    public void notifyDataChanged(List<T> data, boolean isRefresh) {
        if (isRefresh) {
            getData().clear();
        }
        getData().addAll(data);
        notifyDataSetChanged();
    }

}
