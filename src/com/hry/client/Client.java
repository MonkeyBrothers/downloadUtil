package com.hry.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    String host = "169.254.160.25";
    int port = 7890;
    File file = new File("D:/fight.zip");
    Socket socket;
    public Client() {
        try {

            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载功能
     */
    public void download() {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        int i;
        try {
            // 需要一个流从服务端socket处，读取数据
            in = new BufferedInputStream(socket.getInputStream());
            // 需要一个输出流向file中写入数据
            out = new BufferedOutputStream(new FileOutputStream(file));

            while ((i = in.read()) != -1) {
                out.write(i);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
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

    /**
     * 测试主方法
     * @param args
     */
    public static void main(String[] args) {
        new Client().download();
    }

}
