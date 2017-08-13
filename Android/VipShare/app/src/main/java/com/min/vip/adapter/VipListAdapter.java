package com.min.vip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.min.framework.widget.recyclerview.HFRecyclerViewAdapter;
import com.min.vip.R;
import com.min.vip.bean.OrderRespBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by minyangcheng on 2016/10/13.
 */
public class VipListAdapter extends HFRecyclerViewAdapter<OrderRespBean,VipListAdapter.ItemViewHolder> {

    public VipListAdapter(Context context) {
        super(context);
    }

    @Override
    public ItemViewHolder onCreateDataItemViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_order,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindDataItemViewHolder(ItemViewHolder holder, int position) {
        OrderRespBean bean=getData().get(position);
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
