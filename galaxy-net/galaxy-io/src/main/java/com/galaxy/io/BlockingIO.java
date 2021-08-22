package com.galaxy.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步阻塞IO模型
 * BIO (Blocking I/O): 同步阻塞I/O模式，数据的读取写入必须阻塞在一个线程内等待其完成。在活动
 * 连接数不是特别高（小于单机1000）的情况下，这种模型是比较不错的，可以让每一个连接专注于
 * 自己的 I/O 并且编程模型简单，也不用过多考虑系统的过载、限流等问题。线程池本身就是一个天
 * 然的漏斗，可以缓冲一些系统处理不了的连接或请求。但是，当面对十万甚至百万级连接的时候，
 * 传统的 BIO 模型是无能为力的。因此，我们需要一种更高效的 I/O 处理模型来应对更高的并发量。
 * @author syh
 * @since 2021/8/18 13:38
 */
public class BlockingIO {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务启动,端口：" + port);
        while (true){
            Socket accept = serverSocket.accept();
            System.out.println("新客户端连接,地址：" + accept.getInetAddress());
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
            //把Socket中的数据读取到字节数组中
            int read = inputStream.read(bytes);
            if (read != -1){
                System.out.println("msg from client: " + new String(bytes, 0, read));
            }else {
                break;
            }
        }
    }
}
