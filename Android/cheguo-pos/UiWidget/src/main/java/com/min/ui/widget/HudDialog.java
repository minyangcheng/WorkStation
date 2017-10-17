package com.min.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

public class HudDialog extends Dialog {

    private TextView mMessTv;

    public static HudDialog createProgressHud(Context context) {
        return createProgressHud(context, null, true, null);
    }

    public static HudDialog createProgressHud(Context context, String mess
            , boolean canCancel, DialogInterface.OnCancelListener onCancelListener) {
        HudDialog dialog = new HudDialog(context);
        dialog.setMessage(mess);
        dialog.setCancelable(canCancel);
        dialog.setCanceledOnTouchOutside(false);
        if (onCancelListener != null) {
            dialog.setOnCancelListener(onCancelListener);
        }
        return dialog;
    }

    public HudDialog(Context context) {
        super(context, R.style.HudStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress_hud);
        initView();

        getWindow().getAttributes().gravity= Gravity.CENTER;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        getWindow().setAttributes(lp);
    }

    private void initView() {
        mMessTv = (TextView) findViewById(R.id.mess_tv);
    }

    private void setMessage(String message) {
        if (TextUtils.isEmpty(message)) return;
        mMessTv.setText(message);
    }

}
