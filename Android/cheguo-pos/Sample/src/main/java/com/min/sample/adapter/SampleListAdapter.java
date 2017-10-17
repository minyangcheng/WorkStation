package com.min.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.min.sample.R;
import com.min.sample.data.model.OrderBean;
import com.min.ui.widget.recyclerview.HFRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by minyangcheng on 2016/10/13.
 */
public class SampleListAdapter extends HFRecyclerViewAdapter<OrderBean, SampleListAdapter.ItemViewHolder> {

    public SampleListAdapter(Context context) {
        super(context);
    }

    @Override
    public ItemViewHolder onCreateDataItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindDataItemViewHolder(ItemViewHolder holder, int position) {
        OrderBean bean = getData().get(position);
        holder.nameTv.setText(bean.username);
        holder.carTv.setText(bean.carbrand);
        holder.timeTv.setText(bean.ctime);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView nameTv;
        @BindView(R.id.tv_car)
        TextView carTv;
        @BindView(R.id.tv_time)
        TextView timeTv;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
