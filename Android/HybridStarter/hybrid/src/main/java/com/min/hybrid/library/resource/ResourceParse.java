package com.min.hybrid.library.resource;

import android.content.Context;
import android.text.TextUtils;

import com.min.hybrid.library.Constants;
import com.min.hybrid.library.bean.VersionInfoBean;
import com.min.hybrid.library.util.AssetsUtil;
import com.min.hybrid.library.util.FileUtil;
import com.min.hybrid.library.util.L;
import com.min.hybrid.library.util.ParseUtil;
import com.min.hybrid.library.util.SharePreferenceUtil;
import com.min.hybrid.library.util.Util;

import java.io.File;
import java.util.Date;

public class ResourceParse {

    public long prepareJsBundle(Context context) {
        long startTime = new Date().getTime();
        if (SharePreferenceUtil.getInterceptorActive(context)) {
            String downloadVersion = SharePreferenceUtil.getDownLoadVersion(context);
            if (TextUtils.isEmpty(downloadVersion)) {
                if (FileUtil.isEmptyDir(FileUtil.getBundleDir(context))) {
                    transferInsideBundle(context);
                    L.d(Constants.HYBRID_LOG, "prepare js bundle from assert");
                }
            } else {
                if (compareVersion(context)) {
                    transferInsideBundle(context);
                    L.d(Constants.HYBRID_LOG, "prepare js bundle from assert");
                } else {
                    File zip = FileUtil.getFileInDir(FileUtil.getTempBundleDir(context), 0);
                    FileUtil.unZip(zip, FileUtil.getBundleDir(context));
                    SharePreferenceUtil.setVersion(context, downloadVersion);
                    SharePreferenceUtil.setDownLoadVersion(context, null);
                    L.d(Constants.HYBRID_LOG, "prepare js bundle from zip file , info=%s", downloadVersion);
                }
            }
        }
        long time = new Date().getTime() - startTime;
        L.d(Constants.HYBRID_LOG, "prepare js bundle waste time=%s", time);
        return time;
    }

    private void transferInsideBundle(Context context) {
        AssetsUtil.copyAssetsFile(context, Constants.Resource.BUNDLE_NAME, new File(FileUtil.getTempBundleDir(context), Constants.Resource.BUNDLE_NAME));
        FileUtil.unZip(new File(FileUtil.getTempBundleDir(context), Constants.Resource.BUNDLE_NAME), FileUtil.getBundleDir(context));
        setVersion(context, AssetsUtil.getAssetsVersionInfo(context));
    }

    private void setVersion(Context context, Object assetsVersionInfo) {
        if (assetsVersionInfo != null) {
            SharePreferenceUtil.setVersion(context, ParseUtil.toJsonString(assetsVersionInfo));
        }
    }

    private boolean compareVersion(Context context) {
        VersionInfoBean assetsVersion = AssetsUtil.getAssetsVersionInfo(context);
        String downLoadVersionStr = SharePreferenceUtil.getDownLoadVersion(context);
        VersionInfoBean downloadVersion = ParseUtil.parseObject(downLoadVersionStr, VersionInfoBean.class);
        return downloadVersion == null || Util.compareVersion(assetsVersion.jsVersion, downloadVersion.jsVersion) >= 0;
    }

}
