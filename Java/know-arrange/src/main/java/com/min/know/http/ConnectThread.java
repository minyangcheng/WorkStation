package com.min.know.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by minyangcheng on 2016/9/14.
 */
public class ConnectThread extends Thread{

    private File mFilesDir;
    private Socket mSocket;
    private File mLogFile;

    public ConnectThread(Socket socket){
        this.mSocket=socket;
        mFilesDir=new File("C:\\Users\\Administrator\\Desktop\\test");
        long timeStamp=System.currentTimeMillis();
        mLogFile=new File(mFilesDir,timeStamp+".txt") ;
        if(mLogFile.exists()){
            mLogFile.delete();
        }
    }

    @Override
    public void run() {
        System.out.println("接受到请求了:"+mSocket.getInetAddress().toString());
        BufferedReader br=null;
        PrintWriter pw=null;
        FileWriter fw=null;
        try {
            br=new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            pw=new PrintWriter(mSocket.getOutputStream());
            String str = null;
            fw=new FileWriter(mLogFile,true);
            while ((str = br.readLine()) != null) {
                System.out.println(str);
                fw.write(str+"\n\r");
            }

            pw.write("{code:1000,message:\"request is success\"}");
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fw.close();
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
