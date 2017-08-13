package com.min.ws.adapter.banner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.min.ws.R;
import com.min.ws.base.HFRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by minyangcheng on 2016/5/19.
 */
public class BannerAdapter extends HFRecyclerViewAdapter<String,BannerAdapter.ItemViewHolder> {

    public BannerAdapter(Context context) {
        super(context);
    }

    @Override
    public ItemViewHolder onCreateDataItemViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_txt,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindDataItemViewHolder(ItemViewHolder holder, int position) {
        String str=getData().get(position);
        holder.tv.setText(str);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv)
        TextView tv;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickLitener!=null){
                        mOnItemClickLitener.onItemClick(itemView,itemPositionInData(getLayoutPosition()));
                    }
                }
            });
        }

    }

}
