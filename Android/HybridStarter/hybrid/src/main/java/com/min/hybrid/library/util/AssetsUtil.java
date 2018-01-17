package com.min.hybrid.library.util;

import android.content.Context;

import com.min.hybrid.library.bean.VersionInfoBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Assets 文件工具类
 */
public class AssetsUtil {

    /**
     * 读取assets 文件
     *
     * @param fileName 文件名称
     */
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources()
                    .getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static VersionInfoBean getAssetsVersionInfo(Context context) {
        String fromAssets = getFromAssets(context, "config.json");
        return ParseUtil.parseObject(fromAssets, VersionInfoBean.class);
    }

    /**
     * 复制assets 文件到指定目录
     *
     * @param fileName        文件名
     * @param destinationPath 指定目录的地址
     */
    public static boolean copyAssetsFile(Context context, String fileName, File destinationPath) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = context.getResources().getAssets().open(fileName);
            outputStream = new FileOutputStream(destinationPath);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}
