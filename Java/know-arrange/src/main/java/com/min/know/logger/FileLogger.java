package com.min.know.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class FileLogger {

    private static final String TAG = "HookLogger";
    private static final String LOG_DIR = "hookLogs";
    private static final long MAX_SIZE = 30 * 1024 * 1024;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String args[]) throws Exception {
//        for (int i = 0; i < 1000000; i++) {
//            log("{\"responseData\":\"{\\\"result\\\":{},\\\"resultCode\\\":\\\"1\\\"}\\r\\n\",\"packageName\":\"com.maihaoche.bentley\",\"activityName\":\"com.oppo.launcher.Launcher\"}");
//        }
        File[] files=scanCompleteFile();
        for(File file :files){
            System.out.println(file.getName());
        }
    }

    public static void log(String s) {
        if (s == null) {
            return;
        }
        s = s + "\n\n";
        try {
            File logFile = getOutputFile(s);
            FileOutputStream fileOutputStream = new FileOutputStream(logFile, true);
            fileOutputStream.write(s.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File getOutputFile(String content) throws Exception {
        File hookLogDir = getLogDir();
        String today = simpleDateFormat.format(new Date());
        File file = new File(hookLogDir, today + ".log");
        long fileSize = file.length();
        if (file.exists() && fileSize > MAX_SIZE) {
            String indicator = getIndicatorStr();
            File renameFile = new File(hookLogDir, today + "-" + indicator + ".log");
            file.renameTo(renameFile);
        }
        return file;
    }

    public static File[] scanCompleteFile() throws Exception {
        File dir = getLogDir();
        String today = simpleDateFormat.format(new Date());
        File[] fileArr = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !(today + ".log").equals(name);
            }
        });
        if (fileArr != null && fileArr.length > 0) {
            Arrays.sort(fileArr, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
        return fileArr;
    }

    public static File getLogDir() {
//        File hookLogDir = new File(Environment.getExternalStorageDirectory(), LOG_DIR);
        File file = new File("/home/minych/桌面/test", LOG_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static String getIndicatorStr() {
        return System.currentTimeMillis() + "";
    }

}
