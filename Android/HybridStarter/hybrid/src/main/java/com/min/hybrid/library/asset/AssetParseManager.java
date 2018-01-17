package com.min.hybrid.library.asset;

import android.content.Context;
import android.text.TextUtils;

import com.min.hybrid.library.Constants;
import com.min.hybrid.library.bean.VersionInfoBean;
import com.min.hybrid.library.util.AssetsUtil;
import com.min.hybrid.library.util.FileUtil;
import com.min.hybrid.library.util.ParseUtil;
import com.min.hybrid.library.util.SharePreferenceUtil;
import com.min.hybrid.library.util.Util;

import java.io.File;
import java.util.Date;

public class AssetParseManager {

    public long prepareJsBundle(Context context) {
        long startTime = new Date().getTime();
        if (Constants.SP.INTERCEPTOR_ACTIVE.equals(SharePreferenceUtil.getInterceptorActive(context))) {
            if (TextUtils.isEmpty(SharePreferenceUtil.getVersion(context))) {
                setVersion(context, AssetsUtil.getAssetsVersionInfo(context));
            }
            if (isCover(context)) {
                transferInsideBundle(context);
            } else {
                String downloadVersion = SharePreferenceUtil.getDownLoadVersion(context);
                if (TextUtils.isEmpty(downloadVersion)) {
                    checkBundleDir(context);
                } else {
                    File zip = FileUtil.getFileInDir(FileUtil.getTempBundleDir(context), 0);
                    FileUtil.unZip(zip, FileUtil.getBundleDir(context));
                    SharePreferenceUtil.setVersion(context, SharePreferenceUtil.getDownLoadVersion(context));
                    SharePreferenceUtil.setDownLoadVersion(context, null);
                }
            }
        }

        return new Date().getTime() - startTime;
    }

    private void checkBundleDir(Context context) {
        if (FileUtil.isEmptyDir(FileUtil.getBundleDir(context))) {
            transferInsideBundle(context);
        }
    }

    private void transferInsideBundle(Context context) {
        AssetsUtil.copyAssetsFile(context, Constants.Asset.BUNDLE_NAME, new File(FileUtil.getTempBundleDir(context), Constants.Asset.BUNDLE_NAME));
        FileUtil.unZip(new File(FileUtil.getTempBundleDir(context), Constants.Asset.BUNDLE_NAME), FileUtil.getBundleDir(context));
        setVersion(context, AssetsUtil.getAssetsVersionInfo(context));
    }

    private void setVersion(Context context, Object assetsVersionInfo) {
        if (assetsVersionInfo != null) {
            SharePreferenceUtil.setVersion(context, ParseUtil.toJsonString(assetsVersionInfo));
        }
    }

    private boolean isCover(Context context) {
        VersionInfoBean assetsVersionInfo = AssetsUtil.getAssetsVersionInfo(context);
        String downloadVersion = SharePreferenceUtil.getDownLoadVersion(context);
        VersionInfoBean compareVersion = null;
        if (TextUtils.isEmpty(downloadVersion)) {
            String currentVersion = SharePreferenceUtil.getVersion(context);
            compareVersion = ParseUtil.parseObject(currentVersion, VersionInfoBean.class);
        } else {
            compareVersion = ParseUtil.parseObject(downloadVersion, VersionInfoBean.class);
        }
        return isCoverByAssetsZip(context, assetsVersionInfo, compareVersion);
    }

    public boolean isCoverByAssetsZip(Context context, VersionInfoBean assets, VersionInfoBean current) {
        if (Util.compareVersion(Util.getVersionName(context), current.androidVersion) < 0) {
            return true;
        }
        if (assets.jsVersion.equals(current.jsVersion)) {
            return true;
        }
        return false;
    }

}
