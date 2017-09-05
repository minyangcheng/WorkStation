package com.min.dialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.min.dialog.R;
import com.min.dialog.util.ScreenUtils;

public class ActionSheetDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private static final String ITEMS_TAG_PREFIX="items_";
    private static final String CANCEL_TAG_PREFIX="cancle";

    private int mTitleTextColor;
    private int mItemTextColor;
    private int mCancelTextColor;
    private int mTitleTextSize;
    private int mItemTextSize;
    private int mCancelTextSize;
    private int mCancelMargin;

    private String mTitle;

    private String[] mItemArr;

    private String mCancel;

    private ActionSheetListener mListener;

    public ActionSheetDialogFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTitleTextColor= Color.parseColor("#999999");
        mItemTextColor= Color.parseColor("#1E82FF");
        mCancelTextColor=mItemTextColor;
        mTitleTextSize= ScreenUtils.sp2px(mContext, 14);
        mItemTextSize= ScreenUtils.sp2px(mContext, 16);
        mCancelTextSize=mTitleTextSize;
        mCancelMargin=ScreenUtils.dpToPxInt(mContext,10);
        mCancel="取消";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout llView=new LinearLayout(mContext);
        llView.setOrientation(LinearLayout.VERTICAL);
        createTitleView(llView);
        createItemViews(llView);
        createCancleView(llView);
        return llView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void setParams(Dialog dialog) {
        super.setParams(dialog);
        setWidthScale(0.95f);
        setHeightScale(0);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity=Gravity.BOTTOM;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setWindowAnimations(R.style.actionSheetAnim);
    }

    private void createItemViews(LinearLayout llView) {
        if(mItemArr==null||mItemArr.length==0){
            throw new IllegalArgumentException("itemArr must be not null");
        }

        TextView tv;
        int length=mItemArr.length;
        for(int i=0;i<length;i++){
            tv=new TextView(mContext);
            tv.setText(mItemArr[i]);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mItemTextSize);
            tv.setTextColor(mItemTextColor);
            tv.setBackgroundResource(getItemBackGround(i));
            llView.addView(tv,getLayoutParams());
            tv.setTag(ITEMS_TAG_PREFIX+i);
            tv.setOnClickListener(this);
        }
    }

    private int getItemBackGround(int i){
        int res=0;
        int length=mItemArr.length;
        if(length==1){
            if(TextUtils.isEmpty(mTitle)){
                res=R.drawable.slt_as_ios7_other_bt_single;
            }else{
                res=R.drawable.slt_as_ios7_other_bt_bottom;
            }
        }else{
            if(i==length-1){
                res=R.drawable.slt_as_ios7_other_bt_bottom;
            }else{
                if(TextUtils.isEmpty(mTitle)){
                    if(i==0){
                        res=R.drawable.slt_as_ios7_other_bt_top;
                    }else{
                        res=R.drawable.slt_as_ios7_other_bt_middle;
                    }
                }else{
                    res=R.drawable.slt_as_ios7_other_bt_middle;
                }
            }
        }
        return res;
    }

    private void createCancleView(LinearLayout llView) {
        TextView bottomTv=new TextView(mContext);
        bottomTv.setText(mCancel);
        bottomTv.setBackgroundResource(R.drawable.slt_as_ios7_other_bt_single);
        bottomTv.setGravity(Gravity.CENTER);
        bottomTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCancelTextSize);
        bottomTv.setTextColor(mCancelTextColor);
        bottomTv.setTag(CANCEL_TAG_PREFIX);
        bottomTv.setOnClickListener(this);
        llView.addView(bottomTv, getLayoutParams(mCancelMargin, mCancelMargin));
    }

    private void createTitleView(LinearLayout llView) {
        if(TextUtils.isEmpty(mTitle)) return;

        TextView titleTv=new TextView(mContext);
        titleTv.setText(mTitle);
        titleTv.setBackgroundResource(R.drawable.actionsheet_top_normal);
        titleTv.setGravity(Gravity.CENTER);
        titleTv.setFocusable(false);
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        titleTv.setTextColor(mTitleTextColor);
        llView.addView(titleTv, getLayoutParams());
    }

    public LinearLayout.LayoutParams getLayoutParams(){
        return getLayoutParams(0,0);
    }

    public LinearLayout.LayoutParams getLayoutParams(int marginTop,int marginBottom){
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                                            ,LinearLayout.LayoutParams.WRAP_CONTENT);
        if(marginTop>0){
            lp.topMargin=marginTop;
        }
        if(marginBottom>0){
            lp.bottomMargin=marginBottom;
        }
        return lp;
    }

    public ActionSheetDialogFragment setTitle(String title){
        mTitle=title;
        return this;
    }

    public ActionSheetDialogFragment setItems(String[] items){
        mItemArr=items;
        return this;
    }

    public ActionSheetDialogFragment setCancel(String cancle){
        mCancel=cancle;
        return this;
    }

    public ActionSheetDialogFragment setOnClickListener(ActionSheetListener listener){
        mListener=listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        if(v.getTag()==null) return;
        String tagStr=v.getTag().toString();
        if(tagStr.equals(CANCEL_TAG_PREFIX)){
            Log.d(tag, "onClick cancel");
            
            dismiss();
            if(mListener!=null) mListener.onClickCancle();
        }else{
            int index=Integer.parseInt(tagStr.split("_")[1]);
            Log.d(tag,"onClick index="+index);

            dismiss();
            if(mListener!=null) mListener.onClickItem(v,index);
        }
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    public interface ActionSheetListener{
        public void onClickItem(View view, int index);
        public void onClickCancle();
    }

}
