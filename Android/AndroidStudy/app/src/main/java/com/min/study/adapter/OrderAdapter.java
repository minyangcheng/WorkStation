package com.min.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.min.framework.widget.recyclerview.HFRecyclerViewAdapter;
import com.min.study.R;
import com.min.study.bean.OrderBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by minyangcheng on 2016/10/10.
 */
public class OrderAdapter extends HFRecyclerViewAdapter<OrderBean,OrderAdapter.ItemViewHolder> {

    public OrderAdapter(Context context) {
        super(context);
    }

    @Override
    public ItemViewHolder onCreateDataItemViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_order,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindDataItemViewHolder(ItemViewHolder holder, int position) {
        OrderBean bean=getData().get(position);
        holder.nameTv.setText(bean.username);
        holder.carTv.setText(bean.carbrand);
        holder.timeTv.setText(bean.ctime);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_name)
        TextView nameTv;
        @Bind(R.id.tv_car)
        TextView carTv;
        @Bind(R.id.tv_time)
        TextView timeTv;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
