package com.min.cs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.min.cs.R;
import com.min.cs.bean.Person;
import com.min.framework.widget.recyclerview.HFRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by minyangcheng on 2016/10/17.
 */
public class PersonAdapter extends HFRecyclerViewAdapter<Person,PersonAdapter.ItemViewHolder> {

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
        Person bean=getData().get(position);
        holder.nameTv.setText(bean.name);
        holder.nameTv.setText(bean.address);
        holder.nameTv.setText(String.valueOf(bean.age));
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_name)
        TextView nameTv;
        @Bind(R.id.tv_address)
        TextView addressTv;
        @Bind(R.id.tv_age)
        TextView ageTv;

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
