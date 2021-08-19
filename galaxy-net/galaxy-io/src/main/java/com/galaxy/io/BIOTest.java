package com.galaxy.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步阻塞IO模型
 * @author syh
 * @since 2021/8/18 13:38
 */
public class BIOTest {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务启动,端口：" + port);
        while (true){
            Socket accept = serverSocket.accept();
            System.out.println("客户端连接,地址：" + accept.getInetAddress());
            executorService.execute(()->{
                try {
                    handler(accept);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    private static void handler(Socket socket) throws IOException {
        byte[] bytes = new byte[1024];
        final InputStream inputStream = socket.getInputStream();
        while (true){
            final int read = inputStream.read(bytes);
            if (read != -1){
                System.out.println("msg from client: " + new String(bytes, 0, read));
            }else {
                break;
            }
        }
    }
}
