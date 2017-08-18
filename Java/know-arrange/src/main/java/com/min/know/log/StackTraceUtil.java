package com.min.know.log;

/**
 * Created by minyangcheng on 2017/8/17.
 */
public class StackTraceUtil {

    public static void printStackTrace() {
        StackTraceElement[] elements = (new Throwable()).getStackTrace();
        StringBuffer buf = new StringBuffer();
        buf.append("Stack for :");
        for(int i=0; i<elements.length; i++) {
            buf.append("\n    "
                    + elements[i].getClassName()
                    + "."
                    + elements[i].getMethodName()
                    + "("
                    + elements[i].getFileName()
                    + ":"
                    + elements[i].getLineNumber()
                    + ")");
        }
        System.out.println(buf.toString());
        printCallInfo();
    }

    public static void printCallInfo(){
        StackTraceElement[] elements = (new Throwable()).getStackTrace();
        StackTraceElement e=elements[2];
        System.out.println("call place-->"+e.getClassName());
    }

}
