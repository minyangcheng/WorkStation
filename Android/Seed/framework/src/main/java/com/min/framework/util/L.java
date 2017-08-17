package com.min.framework.util;

import android.util.Log;

public class L {

    private static final int MAX_LEN = 4000;
    private static final String LOG_FORMAT = "%1$s\n%2$s";
    private static volatile boolean writeLogs = true;

    private L() {
    }

    public static void writeLogs(boolean writeLogs) {
        L.writeLogs = writeLogs;
    }
    
    public static void d(String message,Object... args){
        String tag=getCallClassNameByStackTrace();
        d(tag,message,args);
    }

    public static void d(String tag, String message, Object... args) {
        log(Log.DEBUG, tag, null, message, args);
    }

    public static void i(String tag, String message, Object... args) {
        log(Log.INFO, tag, null, message, args);
    }

    public static void w(String tag, String message, Object... args) {
        log(Log.WARN, tag, null, message, args);
    }

    public static void e(String tag, Throwable ex) {
        log(Log.ERROR, tag, ex, null);
    }

    public static void e(String tag, String message, Object... args) {
        log(Log.ERROR, tag, null, message, args);
    }

    public static void e(String tag, Throwable ex, String message, Object... args) {
        log(Log.ERROR, tag, ex, message, args);
    }

    private static void log(int priority, String tag, Throwable ex, String message, Object... args) {
        if (!writeLogs) return;
        if (args.length > 0) {
            message = String.format(message, args);
        }

        String log;
        if (ex == null) {
            log = message;
        } else {
            String logMessage = message == null ? ex.getMessage() : message;
            String logBody = Log.getStackTraceString(ex);
            log = String.format(LOG_FORMAT, logMessage, logBody);
        }
        print2Console(priority, tag, log);
    }

    private static void print2Console(int priority, final String tag, String msg) {
        int len = msg.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            print(priority, tag, msg.substring(0, MAX_LEN));
            String sub;
            int index = MAX_LEN;
            for (int i = 1; i < countOfSub; i++) {
                sub = msg.substring(index, index + MAX_LEN);
                print(priority, tag, sub);
                index += MAX_LEN;
            }
            sub = msg.substring(index, len);
            print(priority, tag, sub);
        } else {
            print(priority, tag, msg);
        }
    }

    private static void print(int priority, String tag, String msg) {
        Log.println(priority, tag, msg);
    }

    private static String getCallClassNameByStackTrace(){
        StackTraceElement targetElement = new Throwable().getStackTrace()[2];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1];
        }
        if (className.contains("$")) {
            className = className.split("\\$")[0];
        }
        return className;
    }

}
