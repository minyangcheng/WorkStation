package com.cheguo.pos.util;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import com.cheguo.pos.BuildConfig;
import com.cheguo.pos.data.DataManager;
import com.cheguo.pos.data.model.UpdateRespBean;
import com.cheguo.pos.service.UpdateService;
import com.min.common.util.ToastUtils;
import com.min.core.base.BaseActivity;
import com.min.core.util.RxUtil;
import com.min.core.util.UIUtil;
import com.min.ui.widget.HudDialog;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.lang.reflect.Field;

import rx.functions.Action0;
import rx.functions.Action1;

public class UpdateManager {

    private static final String TAG = UpdateManager.class.getSimpleName();

    private BaseActivity mActivity;
    private HudDialog mProgressHUD;
    private AlertDialog mDialog;
    private boolean mSilently;
    private UpdateRespBean mUpdateResponse;

    public UpdateManager(BaseActivity activity) {
        this.mActivity = activity;
    }

    public void checkUpdate(boolean silently) {
        if (Util.isServiceRunning(mActivity, UpdateService.class.getName())) {
            if (!silently) {
                ToastUtils.showShort("更新服务正在运行");
            }
            return;
        }
        this.mSilently = silently;
        requestApi();
    }

    private boolean validateResponse(UpdateRespBean bean) {
        int temp = bean.appversion.compareToIgnoreCase(BuildConfig.VERSION_NAME);
        if (temp <= 0) {
            return false;
        }
        return true;
    }

    private void showForceUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle("发现新版本")
                .setMessage(mUpdateResponse.funcdesc)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hackAlertDialog(dialog, false); //使之不能关闭(此为机关所在，其它语句相同)
                        startUpdateService(true, mUpdateResponse.packageurl
                                , mUpdateResponse.hashvalue);
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {

                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        //如果在强制更新的时候，点击回退键则直接退出应用
                        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                            hackAlertDialog(dialog, true);
                            dialog.dismiss();
                            Util.getHandler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mActivity.finish();
                                }
                            }, 100);
                            return true;
                        }
                        return false;
                    }
                });
        mDialog = builder.create();
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    private void hackAlertDialog(DialogInterface dialog, boolean isShowing) {
        try {
            Field field = dialog.getClass().getSuperclass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, isShowing);// false - 使之不能关闭(此为机关所在，其它语句相同)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlertUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle("发现新版本")
                .setMessage(mUpdateResponse.funcdesc)
                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startUpdateService(false, mUpdateResponse.packageurl
                                , mUpdateResponse.hashvalue);
                    }
                })
                .setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        mDialog = builder.create();
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }

    private void startUpdateService(boolean isForce, String apkUrl, String md5) {
        if (Util.isServiceRunning(mActivity, UpdateService.class.getName())) {
            ToastUtils.showShort("更新服务正在运行");
        } else {
//            apkUrl="http://p.gdown.baidu.com/d1c78c83a8651717a44109420add1bd9f125b7cc8198b35e9d43410a58f412a7a9dd125301819f0362498fea1f7476291d2222a5a22ee0c902932da7ae4bedf8eee9cb01d164d522f8d55efa076b1efd6da462db3fb95953acba17f45756f06d03a0aa2e112972ed41fe4d320eb297fee07c3972529464dea0dfbd50018133b2e9f1073cc0b697743a66ed2652b5c7b7de186e39ee82682043a25d8c5e221c4d65c598c33f89b11259059fd03e8553294dd4e14dc8d79310d6ddfce3d5751c1989044be52b7b47388a4b256da3653342bada2fafbda1b0069d49847d0e6dcae45345edae51eca48bfbdd91fe544c46ef151b725bd6fb7f83";
            UpdateService.startService(mActivity, isForce, apkUrl, md5);
        }
    }

    private void requestApi() {
        int apptype = 10;
        String clienttype = "Android";
        String version = BuildConfig.VERSION_NAME;
        String channel = BuildConfig.FLAVOR;
        DataManager.getMobileService()
                .selfUpdate(apptype, clienttype, version, channel)
                .compose(RxUtil.handleServerResult())
                .compose(mActivity.bindUntilEvent(ActivityEvent.DESTROY))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!mSilently) {
                            showHUD();
                        }
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (!mSilently) {
                            dismissHUD();
                        }
                    }
                })
                .subscribe(new Action1<UpdateRespBean>() {
                    @Override
                    public void call(UpdateRespBean bean) {
                        mUpdateResponse = bean;

                        if (validateResponse(bean)) {
                            //更新
                            boolean isForceUpdate = bean.forceupdate == 2;
                            if (isForceUpdate) {
                                //强制更新
                                showForceUpdateDialog();
                            } else {
                                //提示更新
                                showAlertUpdateDialog();
                            }
                        } else {
                            //已经是最新版本
                            if (!mSilently) {
                                ToastUtils.showShort("当前已是最新版本");
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (!mSilently) {
                            UIUtil.handlerError(mActivity, throwable);
                        }
                    }
                });
    }

    private void showHUD() {
        if (mProgressHUD == null) {
            mProgressHUD = HudDialog.createProgressHud(mActivity, "正在检测更新...", true, null);
            mProgressHUD = new HudDialog(mActivity);
        }
        if (!mProgressHUD.isShowing()) {
            mProgressHUD.show();
        }
    }

    private void dismissHUD() {
        if (mProgressHUD != null && mProgressHUD.isShowing()) {
            mProgressHUD.dismiss();
        }
    }


}
