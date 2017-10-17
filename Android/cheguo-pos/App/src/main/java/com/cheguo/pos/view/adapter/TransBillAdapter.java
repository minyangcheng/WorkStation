package com.cheguo.pos.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheguo.pos.R;
import com.cheguo.pos.data.model.TransInfo;
import com.cheguo.pos.util.FormatUtil;
import com.min.ui.widget.recyclerview.HFRecyclerViewAdapter;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by minyangcheng on 2016/10/13.
 */
public class TransBillAdapter extends HFRecyclerViewAdapter<TransInfo, TransBillAdapter.ItemViewHolder> {

    public TransBillAdapter(Context context) {
        super(context);
    }

    @Override
    public ItemViewHolder onCreateDataItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_trans_bill, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindDataItemViewHolder(ItemViewHolder holder, int position) {
        TransInfo data = getData().get(position);
        setMonthTv(holder.monthTv, data, position);
        holder.statusTv.setText(data.orderStatus.getStr());
        double amount = 1.0 * data.amount / 100;
        BigDecimal bg = new BigDecimal(String.valueOf(amount));
        holder.moneyTv.setText(bg.toPlainString() + "元");
        holder.timeTv.setText(FormatUtil.formatTimeStr(data.timestamp, "yyyy-MM-dd HH:mm:ss", "MM-dd HH:mm"));
        holder.payTypeTv.setText(data.payType.getStr());

        holder.itemView.setOnClickListener((v) -> {
            if (mOnItemClickLitener != null) {
                mOnItemClickLitener.onItemClick(v, position);
            }
        });
    }

    private void setMonthTv(TextView monthTv, TransInfo data, int position) {
        String nowMonth = FormatUtil.getBeautifulYearMonth(data.timestamp);
        monthTv.setText(nowMonth);
        int index = -1;
        for (int i = 0; i < getData().size(); i++) {
            if (FormatUtil.getBeautifulYearMonth(getData().get(i).timestamp).equals(nowMonth)) {
                index = i;
                break;
            }
        }
        monthTv.setVisibility(position == index ? View.VISIBLE : View.GONE);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_month)
        TextView monthTv;
        @BindView(R.id.tv_status)
        TextView statusTv;
        @BindView(R.id.tv_money)
        TextView moneyTv;
        @BindView(R.id.tv_time)
        TextView timeTv;
        @BindView(R.id.tv_pay_type)
        TextView payTypeTv;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
