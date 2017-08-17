package com.min.framework.base;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.min.framework.util.StatusBarUtils;

import butterknife.ButterKnife;

public abstract class BaseDialog extends Dialog {

    protected String tag;

    protected Context mContext;
    private BaseAnimatorSet mShowAnim;
    private BaseAnimatorSet mdismissAnim;
    private boolean mIsShowingAnim;
    private boolean mIsDissmissAnim;

    private float mWidthScale;
    private float mHeightScale;
    private int mMaxHeight;
    private int mMaxWidth;

    private boolean mIsAutoDismiss;
    private int mAutoDismissDelay = 3000;

    private boolean mIsOutsideDismiss = true;

    private LinearLayout mTopView;
    private View mContentView;

    private int mContentGravity = Gravity.CENTER;

    private Handler mHandler = new Handler(Looper.myLooper());

    public BaseDialog(Context context) {
        super(context);
        mContext = context;
        tag = this.getClass().getSimpleName();
        setDialogTheme();
        initMaxHeightAndWidth(context);
    }

    private void initMaxHeightAndWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mMaxHeight = dm.heightPixels - StatusBarUtils.getHeight(context);
        mMaxWidth = dm.widthPixels;
    }

    private void setDialogTheme() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    protected void setParam() {
    }

    protected View onCreateView() {
        if (getLayoutId() > 0) {
            View view = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
            ButterKnife.bind(this, view);
            return view;
        }
        return null;
    }

    protected void onViewCreate(View contentView) {
    }

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setParam();
        mContentView = onCreateView();
        onViewCreate(mContentView);

        mTopView = new LinearLayout(mContext);
        mTopView.setGravity(mContentGravity);
        mTopView.addView(mContentView);
        mTopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsOutsideDismiss) {
                    dismiss();
                }
            }
        });
        setContentView(mTopView);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setTopViewSize();
        setContentViewSize();

        mContentView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mContentView.getViewTreeObserver().removeOnPreDrawListener(this);
                startEnterAnim();
                return true;
            }
        });
    }

    private void startEnterAnim() {
        if (mShowAnim != null && mContentView != null && !mIsShowingAnim) {
            mShowAnim.listener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    mIsShowingAnim = true;
                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mIsShowingAnim = false;
                    autoDismiss();
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    mIsShowingAnim = false;
                }
            }).playOn(mContentView);
        } else {
            autoDismiss();
        }
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        mIsOutsideDismiss = cancel;
    }

    @Override
    public void dismiss() {
        if (mdismissAnim != null && mContentView != null) {
            if (mIsDissmissAnim) return;

            mdismissAnim.listener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    mIsDissmissAnim = true;
                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mIsDissmissAnim = false;
                    superDismiss();
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    mIsDissmissAnim = false;
                }
            }).playOn(mContentView);
        } else {
            superDismiss();
        }
    }

    public BaseDialog dimEnabled(boolean isDimEnabled) {
        if (isDimEnabled) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        return this;
    }

    public BaseDialog setDimAmount(float value) {
        getWindow().setDimAmount(value);
        return this;
    }

    private void autoDismiss() {
        if (mIsAutoDismiss && mAutoDismissDelay > 0) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, mAutoDismissDelay);
        }
    }

    public BaseDialog showAnim(BaseAnimatorSet showAnim) {
        mShowAnim = showAnim;
        return this;
    }

    public BaseDialog dismissAnim(BaseAnimatorSet dismissAnim) {
        mdismissAnim = dismissAnim;
        return this;
    }

    public BaseDialog setWidthScale(float scale) {
        mWidthScale = scale;
        return this;
    }

    public BaseDialog setHeightScale(float scale) {
        mHeightScale = scale;
        return this;
    }

    public BaseDialog autoDismiss(boolean autoDismiss) {
        mIsAutoDismiss = autoDismiss;
        return this;
    }

    public void superShow() {
        super.show();
    }

    public BaseDialog gravity(int gravity) {
        if (gravity != Gravity.CENTER || gravity != Gravity.CENTER_HORIZONTAL) {
            gravity = gravity | Gravity.CENTER_HORIZONTAL;
        }
        mContentGravity = gravity;
        return this;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mIsShowingAnim || mIsDissmissAnim || mIsAutoDismiss) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        if (mIsShowingAnim || mIsDissmissAnim || mIsAutoDismiss) {
            return;
        }
        super.onBackPressed();
    }

    public void superDismiss() {
        super.dismiss();
    }

    private void setTopViewSize() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
    }

    private void setContentViewSize() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(getScaleValue(mMaxWidth, mWidthScale)
                , getScaleValue(mMaxHeight, mHeightScale));
        mContentView.setLayoutParams(lp);
    }

    private int getScaleValue(int max, float scale) {
        if (scale < 0 || scale > 1f) {
            throw new IllegalArgumentException("mWidthScale or mHeightScale must be 0 to 1");
        }
        int value = 0;
        if (scale == 0) {
            value = LinearLayout.LayoutParams.WRAP_CONTENT;
        } else if (scale == 1) {
            value = LinearLayout.LayoutParams.MATCH_PARENT;
        } else {
            value = (int) (max * scale);
        }
        return value;
    }

}
