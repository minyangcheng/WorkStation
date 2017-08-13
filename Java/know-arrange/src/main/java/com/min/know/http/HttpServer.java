package com.min.know.http;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by minyangcheng on 2016/9/14.
 */
public class HttpServer {

    public static void main(String args[]){
        try {
            ServerSocket server = new ServerSocket(49999);
            Socket socket=null;
            while ((socket=server.accept())!=null){
                new ConnectThread(socket).start();
            }
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
