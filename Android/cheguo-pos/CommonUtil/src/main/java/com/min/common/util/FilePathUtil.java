package com.min.common.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

public class FilePathUtil {

    /**
     * getExternalStorageDirectory=/storage/sdcard0
     getExternalFilesDir=/storage/sdcard0/Android/data/com.slh.download/files
     getExternalFilesDirMusic=/storage/sdcard0/Android/data/com.slh.download/files/Music
     getExternalCacheDir=/storage/sdcard0/Android/data/com.slh.download/cache
     getFileDir=/data/data/com.slh.download/files
     getCacheDir=/data/data/com.slh.download/cache
     * @param context
     * @return
     */
    public static File getFilesRootDir(Context context){
        File dir=null;
        if(isSDCardEnable()){
            dir=context.getExternalFilesDir(null);
        }
        if(dir==null){
            dir=context.getFilesDir();
        }
        if(!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }

    public static File getCacheRootDir(Context context){
        File dir=null;
        if(isSDCardEnable()){
            dir=context.getExternalCacheDir();
        }
        if(dir==null){
            dir=context.getCacheDir();
        }
        if(!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }

    public static File getSharedRootDir(String name){
        if(TextUtils.isEmpty(name)){
            return null;
        }
        File fileDir=null;
        if(isSDCardEnable()){
            fileDir=new File(Environment.getExternalStorageDirectory(),name);
        }
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        return fileDir;
    }

    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String getSDCardPath() {
        if (!isSDCardEnable()) return null;
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

}
