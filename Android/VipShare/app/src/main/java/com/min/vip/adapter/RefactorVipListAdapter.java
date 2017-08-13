package com.min.vip.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.min.vip.R;
import com.min.vip.bean.VipAccountBean;
import com.min.vip.widget.adapter.ExpandableRecyclerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RefactorVipListAdapter extends ExpandableRecyclerAdapter<ExpandableRecyclerAdapter.ListItem> {

    public static final int TYPE_ITEM = 1001;

    private OnItemClickListener mOnItemClickListener;

    public RefactorVipListAdapter(Context context) {
        super(context);
    }

    public static class HeaderItem extends ExpandableRecyclerAdapter.ListItem {

        public String groupText;

        public HeaderItem(String groupText) {
            super(TYPE_HEADER);
            this.groupText = groupText;
        }
    }

    public class HeaderViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {

        @Bind(R.id.item_header_name)
        TextView itemHeaderName;

        public HeaderViewHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.item_arrow));
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            super.bind(position);
            HeaderItem headerItem= (HeaderItem) visibleItems.get(position);
            itemHeaderName.setText(headerItem.groupText);
        }
    }

    public class OrderViewHolder extends ExpandableRecyclerAdapter.ViewHolder {

        @Bind(R.id.tv_account)
        TextView accountTv;
        @Bind(R.id.tv_password)
        TextView passwordTv;

        public OrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final int position) {
            final VipAccountBean item= (VipAccountBean) visibleItems.get(position);
            accountTv.setText(mContext.getString(R.string.username_format,item.userAccount));
            passwordTv.setText(mContext.getString(R.string.password_format,item.userPass));


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener!=null) {
                        mOnItemClickListener.onNormalClickListener(item,position);
                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEADER:
                viewHolder = new HeaderViewHolder(inflate(R.layout.item_group_header, parent));
                break;
            case TYPE_ITEM:
                viewHolder = new OrderViewHolder(inflate(R.layout.item_vip, parent));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            case TYPE_ITEM:
                ((OrderViewHolder) holder).bind(position);
                break;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }

    public interface OnItemClickListener{
        void onNormalClickListener(VipAccountBean vipAccountBean,int pos);
    }

}