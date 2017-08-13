package com.min.framework.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.min.framework.R;
import com.min.framework.base.BaseDialog;

public class HudDialog extends BaseDialog {

    private ProgressBar mProgressPb;
    TextView mMessTv;

    private String mMessStr;

    public static HudDialog createProgressHud(Context context){
        return createProgressHud(context, null, true, null);
    }

    public static HudDialog createProgressHud(Context context,String mess
                ,boolean canCancel,OnCancelListener onCancelListener){
        HudDialog progressDialog=new HudDialog(context);
        if(!TextUtils.isEmpty(mess)){
            progressDialog.setMessage(mess);
        }
        progressDialog.setCancelable(canCancel);
        progressDialog.setCanceledOnTouchOutside(false);
        if(onCancelListener!=null){
            progressDialog.setOnCancelListener(onCancelListener);
        }
        return progressDialog;
    }

    public HudDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_progress_hud;
    }

    @Override
    protected void onViewCreate(View contentView) {
        super.onViewCreate(contentView);
        mProgressPb= (ProgressBar) contentView.findViewById(R.id.pb_progress);
        mMessTv= (TextView) contentView.findViewById(R.id.mess_tv);
        if(TextUtils.isEmpty(mMessStr)){
            mMessTv.setVisibility(View.GONE);
        }else{
            mMessTv.setVisibility(View.VISIBLE);
            mMessTv.setText(mMessStr);
        }
    }

    @Override
    protected void setParam() {
        setWidthScale(0f);
        setHeightScale(0f);
        dimEnabled(false);
    }

    public void setMessage(String mess){
        this.mMessStr=mess;
    }

}
