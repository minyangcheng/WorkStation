package com.min.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StateLayout extends FrameLayout {

    private static final int defStyleAttr = R.attr.stateLayoutDefStyle;
    private static final int NOT_SET = -1;

    private static final String LOADING_TAG = "loading_tag";
    private static final String EMPTY_TAG = "empty_tag";
    private static final String ERROR_TAG = "error_tag";

    private LayoutInflater layoutInflater;

    private View loadingContainer;
    private View emptyContainer;
    private View errorContainer;

    private int loadingLayoutId;
    private int emptyLayoutId;
    private int errorLayoutId;

    private List<View> contentViews = new ArrayList<>();

    private LAYOUT_TYPE currentState = LAYOUT_TYPE.CONTENT;

    private Drawable errorImg;
    private String errorText;

    private Drawable emptyImg;
    private String emptyText;

    private OnRetryListener retryListener;

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, defStyleAttr);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs, defStyleAttr);
        }
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.StateLayout, defStyleAttr,0);
        if (typedArray == null) {
            return;
        }

        try {
            this.loadingLayoutId = typedArray.getResourceId(R.styleable.StateLayout_loading_layout, NOT_SET);
            this.emptyLayoutId = typedArray.getResourceId(R.styleable.StateLayout_empty_layout, NOT_SET);
            this.errorLayoutId = typedArray.getResourceId(R.styleable.StateLayout_error_layout, NOT_SET);

            errorImg=typedArray.getDrawable(R.styleable.StateLayout_error_img);
            errorText=typedArray.getString(R.styleable.StateLayout_error_text);
            emptyImg=typedArray.getDrawable(R.styleable.StateLayout_empty_img);
            emptyText=typedArray.getString(R.styleable.StateLayout_empty_text);
        } finally {
            typedArray.recycle();
        }

        initViews();
    }

    private void initViews() {
        if (loadingContainer == null) {
            if (loadingLayoutId == NOT_SET) {
                throw new IllegalStateException("loadingLayoutId must be set");
            }
            loadingContainer =layoutInflater.inflate(loadingLayoutId, this, false);
            loadingContainer.setTag(LOADING_TAG);
            addView(loadingContainer);
        }

        if (emptyContainer == null) {
            if (emptyLayoutId == NOT_SET) {
                throw new IllegalStateException("emptyLayoutId must be set");
            }
            emptyContainer = layoutInflater.inflate(emptyLayoutId, this, false);
            emptyContainer.setTag(EMPTY_TAG);

            setDataToEmptyLayout();

            addView(emptyContainer);
        }

        if (errorContainer == null) {
            if (errorLayoutId == NOT_SET) {
                throw new IllegalStateException("errorLayoutId must be set");
            }
            errorContainer =layoutInflater.inflate(errorLayoutId, this, false);
            errorContainer.setTag(ERROR_TAG);

            setDataToErrorLayout();

            addView(errorContainer);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showContentStatus();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        if (child.getTag() == null ||
                (!child.getTag().equals(LOADING_TAG) && !child.getTag().equals(EMPTY_TAG) &&
                        !child.getTag().equals(ERROR_TAG))) {

            this.contentViews.add(child);

            if (!this.isInEditMode()) {
                this.setContentVisibility(false);
            }
        }
    }

    public void showLoadingStatus() {
        if(this.currentState == LAYOUT_TYPE.LOADING){
            return;
        }

        showLoadingView();
        hideEmptyView();
        hideErrorView();
        setContentVisibility(false);
        this.currentState = LAYOUT_TYPE.LOADING;
    }

    public void showEmptyStatus() {
        if(this.currentState == LAYOUT_TYPE.EMPTY){
            return;
        }

        showEmptyView();
        hideLoadingView();
        hideErrorView();
        setContentVisibility(false);
        this.currentState = LAYOUT_TYPE.EMPTY;
    }

    public void showErrorStatus() {
        if(this.currentState == LAYOUT_TYPE.ERROR){
            return;
        }

        showNetErrorView();
        hideLoadingView();
        hideEmptyView();
        setContentVisibility(false);
        this.currentState = LAYOUT_TYPE.ERROR;
    }

    public void showContentStatus() {
        if(this.currentState == LAYOUT_TYPE.CONTENT){
            return;
        }

        hideLoadingView();
        hideEmptyView();
        hideErrorView();
        setContentVisibility(true);
        this.currentState = LAYOUT_TYPE.CONTENT;
    }

    public LAYOUT_TYPE getCurrentState() {
        return currentState;
    }

    public View getErrorView(){
        return errorContainer;
    }

    public View getEmptyView(){
        return emptyContainer;
    }

    private void showLoadingView() {
        loadingContainer.setVisibility(VISIBLE);
    }

    private void showEmptyView() {
        emptyContainer.setVisibility(VISIBLE);
    }

    private void setDataToEmptyLayout() {
        if(emptyImg!=null){
            ImageView emptyImgIv= (ImageView) emptyContainer.findViewById(R.id.iv_empty_img);
            if(emptyImgIv!=null){
                emptyImgIv.setImageDrawable(emptyImg);
            }
        }
        if(emptyText!=null){
            TextView emptyDescTv= (TextView) emptyContainer.findViewById(R.id.tv_empty_desc);
            if(emptyDescTv!=null){
                emptyDescTv.setText(emptyText);
            }
        }
    }

    private void showNetErrorView() {
        this.errorContainer.setVisibility(VISIBLE);
    }

    private void setDataToErrorLayout(){
        if(errorImg!=null){
            TextView errorInfoTv= (TextView) errorContainer.findViewById(R.id.tv_error_info);
            if(errorInfoTv!=null){
                errorImg.setBounds(0, 0, errorImg.getMinimumWidth(), errorImg.getMinimumHeight());
                errorInfoTv.setCompoundDrawables(null, errorImg, null, null);
            }
        }
        TextView errorRefreshTv= (TextView) errorContainer.findViewById(R.id.tv_error_retry);
        if(errorRefreshTv!=null){
            if(errorText!=null) {
                errorRefreshTv.setText(errorText);
            }
            errorRefreshTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (retryListener != null) {
                        retryListener.retry();
                    }
                }
            });
        }
    }

    public void setEmptyImg(int drawableId){
        if(drawableId<=0){
            return;
        }
        emptyImg=getContext().getResources().getDrawable(drawableId);
        setDataToEmptyLayout();
    }

    public void setEmptyText(int textId){
        if(textId<=0){
            return;
        }
        emptyText=getContext().getString(textId);
        setDataToEmptyLayout();
    }

    private void hideLoadingView() {
        if (loadingContainer != null && loadingContainer.getVisibility() != GONE) {
            this.loadingContainer.setVisibility(GONE);
        }
    }

    private void hideEmptyView() {
        if (emptyContainer != null && emptyContainer.getVisibility() != GONE) {
            this.emptyContainer.setVisibility(GONE);
        }
    }

    private void hideErrorView() {
        if (errorContainer != null && errorContainer.getVisibility() != GONE) {
            this.errorContainer.setVisibility(GONE);
        }
    }

    public boolean isLoading() {
        return this.currentState == LAYOUT_TYPE.LOADING;
    }

    public boolean isContent() {
        return this.currentState == LAYOUT_TYPE.CONTENT;
    }

    public boolean isEmpty() {
        return this.currentState == LAYOUT_TYPE.EMPTY;
    }

    public boolean isError() {
        return this.currentState == LAYOUT_TYPE.ERROR;
    }

    private void setContentVisibility(boolean visible) {
        for (View contentView : contentViews) {
            contentView.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public void setOnRetryListener(OnRetryListener retryListener){
        this.retryListener=retryListener;
    }

    public interface OnRetryListener {
        public void retry();
    }

    public enum LAYOUT_TYPE {

        /** 正在加载 */
        LOADING,
        /** 无内容 */
        EMPTY,
        /** 内容显示 */
        CONTENT,
        /** 网络错误 */
        ERROR,
    }

}