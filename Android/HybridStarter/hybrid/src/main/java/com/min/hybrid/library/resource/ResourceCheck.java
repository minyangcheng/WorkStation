package com.min.hybrid.library.resource;

import android.content.Context;
import android.text.TextUtils;

import com.min.hybrid.library.Constants;
import com.min.hybrid.library.bean.VersionInfoBean;
import com.min.hybrid.library.net.DownloadManager;
import com.min.hybrid.library.net.FileCallBack;
import com.min.hybrid.library.util.FileUtil;
import com.min.hybrid.library.util.L;
import com.min.hybrid.library.util.Md5Util;
import com.min.hybrid.library.util.ParseUtil;
import com.min.hybrid.library.util.SharePreferenceUtil;
import com.min.hybrid.library.util.Util;

import java.io.File;

public class ResourceCheck {

    private Context mContext;
    private int mCurrentStatus = Constants.Version.SLEEP;
    private CheckApiHandler mCheckApiHandler;

    public ResourceCheck(Context context, CheckApiHandler checkApiHandler) {
        this.mContext = context;
        this.mCheckApiHandler = checkApiHandler;
    }

    public void checkVersion() {
        if (mCurrentStatus == Constants.Version.UPDATING || TextUtils.isEmpty(SharePreferenceUtil.getVersion(mContext))) {
            return;
        }
        if (SharePreferenceUtil.getInterceptorActive(mContext)) {
            if (mCheckApiHandler != null) {
                startRequestCheckApi();
                mCheckApiHandler.checkRequest(this);
            }
        }
    }

    private void startRequestCheckApi() {
        mCurrentStatus = Constants.Version.UPDATING;
    }

    public void setCheckApiFailResp(Exception e) {
        L.e(Constants.HYBRID_LOG, e);
        mCurrentStatus = Constants.Version.SLEEP;
    }

    public void setCheckApiSuccessResp(String jsVersion, String md5, String dist) {
        try {
            VersionInfoBean remoteVersion = new VersionInfoBean();
            remoteVersion.jsVersion = jsVersion;
            remoteVersion.md5 = md5;
            remoteVersion.dist = dist;
            L.d(Constants.HYBRID_LOG, "check version resp=%s", ParseUtil.toJsonString(remoteVersion));
            String localVersionStr = SharePreferenceUtil.getVersion(mContext);
            VersionInfoBean localVersion = ParseUtil.parseObject(localVersionStr, VersionInfoBean.class);
            if (Util.compareVersion(remoteVersion.jsVersion, localVersion.jsVersion) > 0) {
                download(remoteVersion);
            } else {
                mCurrentStatus = Constants.Version.SLEEP;
            }
        } catch (Exception e) {
            mCurrentStatus = Constants.Version.SLEEP;
            e.printStackTrace();
        }
    }

    private void download(final VersionInfoBean version) {
        try {
            if (hasDownloadVersion(version.jsVersion)) {
                L.d(Constants.HYBRID_LOG, "this jsVersion=%s has been download", version.jsVersion);
                mCurrentStatus = Constants.Version.SLEEP;
                return;
            }
            File destination = new File(FileUtil.getTempBundleDir(mContext), Constants.Resource.TEMP_BUNDLE_NAME);
            DownloadManager.getInstance()
                    .downloadFile(version.dist, new FileCallBack(destination) {
                        @Override
                        public void onStart(String url) {
                            L.d(Constants.HYBRID_LOG, "startDownload url=%s", url);
                        }

                        @Override
                        public void onProgress(String url, long progress, long total) {
                        }

                        @Override
                        public void onSuccess(String url, File file) {
                            L.d(Constants.HYBRID_LOG, "complete download url=%s", url);
                            if (checkZipValidate(file, version.md5)) {
                                RenameDeleteFile();
                                SharePreferenceUtil.setDownLoadVersion(mContext, ParseUtil.toJsonString(version));
                            } else {
                                FileUtil.deleteFile(new File(FileUtil.getTempBundleDir(mContext), Constants.Resource.TEMP_BUNDLE_NAME));
                            }
                            mCurrentStatus = Constants.Version.SLEEP;
                        }

                        @Override
                        public void onFail(String url, Throwable t) {
                            mCurrentStatus = Constants.Version.SLEEP;
                        }
                    });
        } catch (Exception e) {
            mCurrentStatus = Constants.Version.SLEEP;
            e.printStackTrace();
        }
    }

    private boolean checkZipValidate(File file, String md5) {
        try {
            if (file.exists() && Md5Util.getFileMD5(file).equals(md5)) {
                return true;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean hasDownloadVersion(String version) {
        String jsonStr = SharePreferenceUtil.getDownLoadVersion(mContext);
        if (!TextUtils.isEmpty(jsonStr)) {
            VersionInfoBean downloadVersion = ParseUtil.parseObject(jsonStr, VersionInfoBean.class);
            if (downloadVersion.jsVersion.equals(version)) {
                return true;
            }
        }
        return false;
    }

    private void RenameDeleteFile() {
        FileUtil.deleteFile(new File(FileUtil.getTempBundleDir(mContext), Constants.Resource.BUNDLE_NAME));
        FileUtil.renameFile(FileUtil.getTempBundleDir(mContext), Constants.Resource.TEMP_BUNDLE_NAME, Constants.Resource.BUNDLE_NAME);
    }

}
