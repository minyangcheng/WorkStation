package com.min.ws.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.min.ws.R;
import com.min.ws.base.HFRecyclerViewAdapter;
import com.min.ws.util.L;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by minyangcheng on 2016/8/26.
 */
public class AnimalAdapter extends HFRecyclerViewAdapter<String,AnimalAdapter.ItemViewHolder> {

    public AnimalAdapter(Context context) {
        super(context);
    }

    @Override
    public ItemViewHolder onCreateDataItemViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_animal,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindDataItemViewHolder(ItemViewHolder holder, int position) {
        L.d("MYTEST","position=%s",position);
        holder.tv.setText(getData().get(position));
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
