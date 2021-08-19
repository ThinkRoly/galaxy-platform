package com.galaxy.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO同步非阻塞
 * @author syh
 * @since 2021/8/18 16:41
 */
public class NIOTest {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.socket().bind(new InetSocketAddress(port));
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动,端口：" + port);
        while (true){
            if (selector.select(100) == 0){
                //没有事件
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();

                //OP_ACCEPT事件
                if (selectionKey.isAcceptable()){
                    SocketChannel accept = socketChannel.accept();
                    // 将 socketChannel 也注册到 selector，关注 OP_READ 事件，并给 socketChannel 关联 Buffer
                    accept.configureBlocking(false);
                    accept.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                // 发生 OP_READ 事件，读客户端数据
                if (selectionKey.isReadable()){
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("msg form client: " + new String(buffer.array()));
                }
                iterator.remove();
            }
        }
    }
}
