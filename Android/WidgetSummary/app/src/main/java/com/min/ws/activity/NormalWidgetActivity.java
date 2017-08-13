package com.min.ws.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.widget.CheckBox;
import android.widget.TextView;

import com.min.ws.R;
import com.min.ws.util.L;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class NormalWidgetActivity extends AppCompatActivity {

    private static final String TAG="NormalWidgetActivity_TEST";

    @Bind(R.id.tv_color)
    TextView mColorTv;
    @Bind(R.id.tv_size)
    TextView mSizeTv;
    @Bind(R.id.tv_style)
    TextView mStyleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_widget);
        ButterKnife.bind(this);

        //<font color="#FF0000">设置的颜色值会覆盖掉布局文件里的设置的颜色值
        String text=getString(R.string.color_test,"#B23AEE");
        mColorTv.setText(Html.fromHtml(text));

        //setSpan设置的字体大小颜色属性会把布局文件里面的设置的属性覆盖掉
        SpannableString styledText = new SpannableString("亲爱的小宝，你好");
        styledText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new AbsoluteSizeSpan(32), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSizeTv.setText(styledText);

        styledText = new SpannableString("亲爱的小宝，你好");
        styledText.setSpan(new TextAppearanceSpan(this, R.style.textStyle1), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.textStyle2), 3, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mStyleTv.setText(styledText);
    }

    @OnCheckedChanged(R.id.cb)
    void clickCheckBox(CheckBox checkBox){
        L.d(TAG,"checkbox=%s",checkBox.isChecked());
    }

}
