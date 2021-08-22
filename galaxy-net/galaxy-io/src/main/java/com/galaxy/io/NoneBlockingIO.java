package com.galaxy.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

/**
 * NIO非阻塞
 * 多线程版的多路复用器
 * @author syh
 * @since 2021/8/18 16:41
 */
public class NoneBlockingIO {
    //最简单的NIO，一个线程处理多个连接
    public static void main(String[] args) throws IOException {
        int port = 8080;

        List<SocketChannel> clients = new LinkedList<>();
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(port));
        ss.configureBlocking(false);
        System.out.println("服务启动,端口：" + port);

        while (true){
            SocketChannel client = ss.accept();
            if (client == null){
                //没有客户端连接
                //System.out.println();
            }else {
                client.configureBlocking(false);
                System.out.println("新客户端连接,地址：" + client.getRemoteAddress());
                clients.add(client);
            }

            ByteBuffer buffer = ByteBuffer.allocate(8096);
            try{
                for (SocketChannel c: clients){
                    int read = c.read(buffer);
                    if (read > 0){
                        buffer.flip();
                        byte[] bytes = new byte[buffer.limit()];
                        buffer.get(bytes);
                        System.out.println("msg from client : " + c.getRemoteAddress() + ":" + new String(bytes));
                        buffer.clear();
                    }else if (read == 0){
                        break;
                    }else {
                        c.close();
                        clients.remove(c);
                    }

                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
