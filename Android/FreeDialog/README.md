##FreeDialog

* 扩展DialogFragment、PopupWindow、BaseDialog类功能
* 实现ios7弹出对话框效果

###实现

* 自定义DialogFragment,可以很方便的指定宽高大小和布局

```
public class CustomDialogFragment extends BaseDialogFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_custom;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void setParams(Dialog dialog) {
        setWidthScale(0.85f); //直接将布局中的最外层view的width和height变为此处设置的
        setHeightScale(0.3f);
        //设置dialog动画
//        dialog.getWindow().setWindowAnimations(R.style.PopupAnimation);
        dialog.setCanceledOnTouchOutside(false);
    }

    @OnClick({R.id.tv_cancel,R.id.tv_sure})
    void clickTvCancel(){
        this.dismiss();
    }

}
```

* 自定义PopupWindow,可以很方便的指定宽高大小和布局

```
public class CustomPopupWindow extends BasePopupWindow {

    public CustomPopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popwin_custom;
    }

    @Override
    protected void onViewCreate(View rootView) {
        super.onViewCreate(rootView);
    }

    @Override
    protected void setParams() {
        setWidthScale(1f);  //直接将布局中的最外层view的width和height变为此处设置的
        setHeightScale(1f);
        setAnimationStyle(R.style.PopupAnimation);
    }
}
```

* 实现ios7底部弹出对话框效果

```
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
```

* 自定义Dialog,可以指定布局内部元素在show和dismiss时的动画效果

```
public class CustomDialog extends BaseDialog {

    public CustomDialog(Context context) {
        super(context);
    }

    @Override
    public void setParam() {
        super.setParam();
        scaleWidthScale(1f);
        scaleHeightScale(1f);
        showAnim(new EnterAnimator());
        dismissAnim(new ExitAnimator());
        setCanceledOnTouchOutside(false);
        getWindow().setWindowAnimations(R.style.dialogAlphaAnimStyle);
        dimEnabled(false);  //背景透明，此方法可以设置dialog背景是否透明
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_anim;
    }

    @Override
    public void onViewCreate(View contentView) {
        super.onViewCreate(contentView);
    }

}
```