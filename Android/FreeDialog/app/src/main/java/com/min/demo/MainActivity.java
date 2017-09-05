package com.min.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.min.demo.dialog.CustomDialog;
import com.min.demo.dialog.CustomDialogFragment;
import com.min.demo.dialog.CustomPopupWindow;
import com.min.dialog.dialog.ActionSheetDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_popup_window)
    Button showPopupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_dialog_fragment)
    void clickBtnDialogFragment(){
        CustomDialogFragment dialogFragment=new CustomDialogFragment();
        dialogFragment.show(getSupportFragmentManager(),dialogFragment.tag);
    }

    @OnClick(R.id.btn_popup_window)
    void clickBtnPopupShow(){
        CustomPopupWindow customPopupWindow=new CustomPopupWindow(this);
        customPopupWindow.showAsDropDown(showPopupBtn);
    }

    @OnClick(R.id.btn_ios_dialog)
    void clickBtnIOSShow(){
        String[] items={"中国银行","工商银行","建设银行","九江银行"};
        ActionSheetDialogFragment dialogFragment=new ActionSheetDialogFragment();
        dialogFragment.setTitle("请选择银行")
                .setItems(items)
                .setCancel("取消")
                .setOnClickListener(new ActionSheetDialogFragment.ActionSheetListener() {
                    @Override
                    public void onClickItem(View view, int index) {

                    }

                    @Override
                    public void onClickCancle() {

                    }
                })
                .show(getSupportFragmentManager(),dialogFragment.tag);
    }

    @OnClick(R.id.btn_anim_show)
    void clickBtnCustomShow(){
        CustomDialog customDialog=new CustomDialog(this);
        customDialog.show();
    }

}
