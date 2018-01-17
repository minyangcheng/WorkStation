package com.min.hybrid.library.asset;

import android.content.Context;
import android.text.TextUtils;

import com.min.hybrid.library.Constants;
import com.min.hybrid.library.bean.VersionInfoBean;
import com.min.hybrid.library.net.DownloadManager;
import com.min.hybrid.library.net.FileCallBack;
import com.min.hybrid.library.net.HttpManager;
import com.min.hybrid.library.util.FileUtil;
import com.min.hybrid.library.util.Md5Util;
import com.min.hybrid.library.util.ParseUtil;
import com.min.hybrid.library.util.SharePreferenceUtil;
import com.min.hybrid.library.util.Util;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class AssetCheckManager {

    private static final String TAG = "VersionChecker";

    private String mUpdateUrl;
    private Context mContext;
    private int mCurrentStatus = Constants.Version.SLEEP;

    public AssetCheckManager(Context context) {
        this.mContext = context;
    }

    public void checkVersion() {
        if (mCurrentStatus == Constants.Version.UPDATING) return;
        readyUpdate();
    }

    private void readyUpdate() {
        mUpdateUrl = "http://10.10.13.117:8080/static/config.json";
        if (TextUtils.isEmpty(mUpdateUrl) || TextUtils.isEmpty(SharePreferenceUtil.getVersion(mContext))) {
            return;
        }
        if (Constants.SP.INTERCEPTOR_ACTIVE.equals(SharePreferenceUtil.getInterceptorActive(mContext))) {
            mCurrentStatus = Constants.Version.UPDATING;
            checkBundleUpdate(mContext, mUpdateUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mCurrentStatus = Constants.Version.SLEEP;
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        VersionInfoBean remoteVersion = ParseUtil.parseObject(response.body().string(), VersionInfoBean.class);
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
            });
        }
    }

    public void checkBundleUpdate(Context context, String url, Callback callback) {
//        String versionInfo = SharePreferenceUtil.getVersion(context);
//        if (TextUtils.isEmpty(versionInfo)) {
//            versionInfo = "";
//        } else {
//            VersionInfoBean jsVersionInfoBean = ParseUtil.parseObject(versionInfo, VersionInfoBean.class);
//            if (jsVersionInfoBean == null) {
//                versionInfo = "";
//            } else {
//                versionInfo = jsVersionInfoBean.jsVersion;
//            }
//        }
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okhttp3.Call call = HttpManager.getHttpClient().newCall(request);
        call.enqueue(callback);
    }


    public void download(final VersionInfoBean version) {
        try {
            File destination = new File(FileUtil.getTempBundleDir(mContext), Constants.Asset.TEMP_BUNDLE_NAME);
            DownloadManager.getInstance()
                    .downloadFile(version.dist, new FileCallBack(destination) {
                        @Override
                        public void onStart(String url) {

                        }

                        @Override
                        public void onProgress(String url, long progress, long total) {
                        }

                        @Override
                        public void onSuccess(String url, File file) {
                            if (checkZipValidate(file, version.md5) && !hasDownloadVersion(version.jsVersion)) {
                                RenameDeleteFile();
                                SharePreferenceUtil.setDownLoadVersion(mContext, ParseUtil.toJsonString(version));
                            } else {
                                FileUtil.deleteFile(new File(FileUtil.getTempBundleDir(mContext), Constants.Asset.TEMP_BUNDLE_NAME));
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
        String jsonStr = SharePreferenceUtil.getVersion(mContext);
        if (!TextUtils.isEmpty(jsonStr)) {
            VersionInfoBean downloadVersion = ParseUtil.parseObject(jsonStr, VersionInfoBean.class);
            if (downloadVersion.jsVersion.equals(version)) {
                return true;
            }
        }
        return false;
    }

    private void RenameDeleteFile() {
        FileUtil.deleteFile(new File(FileUtil.getTempBundleDir(mContext), Constants.Asset.BUNDLE_NAME));
        FileUtil.renameFile(FileUtil.getTempBundleDir(mContext), Constants.Asset.TEMP_BUNDLE_NAME, Constants.Asset.BUNDLE_NAME);
    }

}
