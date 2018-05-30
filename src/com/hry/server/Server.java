package com.hry.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket ss;
    private int port = 7890;

    File file = new File("");
    // String ip = "169.254.160.25";

    public Server() {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upload() {
        // 套接字 IP + 端口号 能确定网络上某个计算机上的某个程序
        while (true) {
            try {
                System.out.println("loding......");
                // 接受，当程序监听的端口上有程序发来信息是，返回该程序的套接字
                Socket socket = ss.accept();
                // 把socket传递线程并启动任务
                new uploadThread(socket).start();
                // 返回客户的id
                System.out.println("IP:" + socket.getInetAddress());
                System.out.println("PORT:" + socket.getPort());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (ss != null) {
                    try {
                        ss.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 创建一个私有线程
     *
     */
    private class uploadThread extends Thread {
        Socket socket;

        public uploadThread(Socket socket) {
            this.socket = socket;
        }

        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        public void run() {
            try{	// 需要一个输入流
                in = new BufferedInputStream(new FileInputStream(file));
                // 输出流
                out = new BufferedOutputStream(socket.getOutputStream());
                // 传送数据
                int i;
                while ((i = in.read()) != -1) {
                    out.write(i);
                }
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
