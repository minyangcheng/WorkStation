package com.min.ws.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.min.ws.R;
import com.min.ws.helper.InputTxtFilter;
import com.min.ws.util.KeyboardUtils;
import com.min.ws.util.L;
import com.min.ws.view.ClearableEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1、当textCursorDrawable设置为null时，cursor跟字体的颜色保持一致
 * android:textColor="#ffffff"
 * android:textCursorDrawable="@null"
 *
 * 2、mEt.setSelection(1) 可以将cursor定位到指定位置
 *
 * 3、showPass(boolean isVisiable)  动态设置密码是否显示
 *
 * 4、限制输入内容
 * android:digits="0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ."
 * 或 mEt.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
 * 或 mEt.addTextChangedListener(new TextWatcher())做字符串过滤操作
 * 或 InputTxtFilter.inputFilter(this, mEt_1, InputTxtFilter.INPUT_TYPE_CH, 5);
 *
 * 5、最外层布局加上ScrollView就能解决由于输入框弹出的问题
 */
public class EditTextActivity extends AppCompatActivity {

    private static final String TAG="EditTextActivity_TEST";

    @Bind(R.id.et_1)
    ClearableEditText mEt_1;

    private boolean mPwShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        ButterKnife.bind(this);

        mEt_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                L.i(TAG, "tempStr=%s , textStr=%s ,start=%s ,before=%s ,count=%s"
//                        , s, mEt_1.getText().toString(), start, before, count);
//                String tempStr = s.toString();
//                if (tempStr.length() > 6) {
//                    tempStr = tempStr.substring(0, 6);
//                    mEt_1.setText(tempStr);
//                    mEt_1.setSelection(tempStr.length());
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //IME_ACTION_SEARCH 搜索 、IME_ACTION_SEND 发送 、IME_ACTION_NEXT 下一步 、IME_ACTION_DONE 完成
//        mEt_1.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEt_1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event!=null&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    L.d(TAG, "onEditorAction");
                }
                return false;
            }
        });

        //限制只能输入中文
        InputTxtFilter.inputFilter(this, mEt_1, InputTxtFilter.INPUT_TYPE_CH, 5);

        //屏蔽粘贴复制功能
        mEt_1.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        mEt_1.setLongClickable(false);
    }

    @OnClick(R.id.tv_show)
    void onClickTvShow(){
        mPwShow=!mPwShow;
        showPass(mPwShow);
        int selection=mEt_1.getText().toString().length();
        mEt_1.setSelection(selection);
    }

    private void showPass(boolean mPwShow){
        if(mPwShow){
            mEt_1.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
        }else{
            mEt_1.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());
        }
    }

    @OnClick(R.id.btn_open)
    void onClickOpen(){
        KeyboardUtils.open(this, mEt_1);
    }

    @OnClick(R.id.btn_close)
    void onClickClose(){
        KeyboardUtils.close(this,mEt_1);
    }

}
