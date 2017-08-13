package com.min.easyrecycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.min.easyrecycleview.R;
import com.min.easyrecycleview.base.HFRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonAdapter extends HFRecyclerViewAdapter<String,PersonAdapter.ItemViewHolder>{

    public PersonAdapter(Context context) {
        super(context);
    }

    @Override
    public ItemViewHolder onCreateDataItemViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_person,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindDataItemViewHolder(ItemViewHolder holder, int position) {
        String nameStr=getData().get(position);
        holder.nameTv.setText(nameStr);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_name)
        TextView nameTv;

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
