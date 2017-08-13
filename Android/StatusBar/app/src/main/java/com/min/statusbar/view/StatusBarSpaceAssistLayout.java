package com.min.statusbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.min.statusbar.R;
import com.min.statusbar.util.Utils;

/**
 * Created by minyangcheng on 2016/6/29.
 */
public class StatusBarSpaceAssistLayout extends LinearLayout {

    private int mStatusHeight;
    private View mSpaceView;

    private int mStatusBarColor;

    public StatusBarSpaceAssistLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public StatusBarSpaceAssistLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public StatusBarSpaceAssistLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.statusBarSpaceAssistLayoutStyle, defStyle, 0);
        mStatusBarColor=a.getColor(R.styleable.statusBarSpaceAssistLayoutStyle_statusBarColor
                ,Utils.getColorPrimary(getContext()));
        a.recycle();

        setOrientation(VERTICAL);
        mStatusHeight= Utils.getStatusBarHeight(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mSpaceView=new View(getContext());
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    mStatusHeight);
            mSpaceView.setBackgroundColor(mStatusBarColor);
            mSpaceView.setLayoutParams(lp);
            addView(mSpaceView,0);
        }
    }

    public void setStatusBarSpaceColor(int color){
        if(mSpaceView==null){
            return;
        }
        mSpaceView.setBackgroundColor(color);
    }

}
