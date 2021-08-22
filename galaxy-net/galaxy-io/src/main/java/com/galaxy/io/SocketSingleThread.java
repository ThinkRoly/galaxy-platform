package com.galaxy.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 单线程版的多路复用器
 * @author galaxy
 * @since 2021/8/21 17:12
 */
public class SocketSingleThread {

    private static Selector selector = null;

    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            while (selector.select(100) > 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isAcceptable()){
                        acceptHandler(selectionKey);
                    }
                    if (selectionKey.isReadable()){
                        readHandler(selectionKey);
                    }
                }
            }
        }
    }

    public static void readHandler(SelectionKey key) {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel client = (SocketChannel) key.channel();
        int read = 0;
        buffer.clear();
        try {
            while (true){
                read = client.read(buffer);
                if (read > 0){
                    buffer.flip();
                    while (buffer.hasRemaining()){
                        client.write(buffer);
                    }
                    buffer.clear();
                }else if (read == 0){
                    break;
                }else {//-1
                    client.close();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private static void acceptHandler(SelectionKey key) {
        try {

            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel client = serverSocketChannel.accept();
            client.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("新客户端连接,地址：" + client.getRemoteAddress());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
