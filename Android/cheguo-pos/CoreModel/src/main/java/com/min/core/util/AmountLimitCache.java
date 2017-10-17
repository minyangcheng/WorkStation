package com.min.core.util;

import android.text.TextUtils;

import com.min.common.util.FileIOUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by minyangcheng on 2016/9/13.
 * <p>
 * 可限制一个文件夹下文件数量的cache
 */
public class AmountLimitCache {

    private File mCacheDir;
    private int mMaxAmount = 100;  //默认支持100个文件

    public AmountLimitCache(String cacheDir) {
        if (TextUtils.isEmpty(cacheDir)) {
            throw new IllegalArgumentException("cacheDir can not be null");
        }
        mCacheDir = new File(cacheDir);
        if (!mCacheDir.exists()) {
            mCacheDir.mkdir();
        }
    }

    public boolean save(InputStream is, String fileName) {
        deleteFilesWhenArriveMaxCount();

        File file = getFile(fileName);
        return FileIOUtils.writeFileFromIS(file, is);
    }

    public void clear() {
        File[] files = mCacheDir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    public void setCacheSize(int maxAmount) {
        this.mMaxAmount = maxAmount;
    }

    public File getFile(String fileName) {
        return new File(mCacheDir, fileName);
    }

    public void deleteFilesWhenArriveMaxCount() {
        File[] files = mCacheDir.listFiles();
        if (files != null && files.length >= mMaxAmount) {
            Arrays.sort(files, new FileComprator());
            int tempCnt = files.length - (mMaxAmount - 1);
            for (int i = 0; i < tempCnt; i++) {
                files[i].delete();
            }
        }
    }

    public static class FileComprator implements Comparator<File> {
        @Override
        public int compare(File lhs, File rhs) {
            return (int) (lhs.lastModified() - rhs.lastModified());
        }
    }

}
