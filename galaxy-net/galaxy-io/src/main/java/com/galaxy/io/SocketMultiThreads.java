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
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程版NIO
 * @author syh
 * @since 2021/8/18 16:56
 */
public class SocketMultiThreads {
    int port = 8080;
    private Selector selector1 = null;
    private Selector selector2 = null;
    private Selector selector3 = null;

    void initServer() {
        try {
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.configureBlocking(false);
            serverSocket.bind(new InetSocketAddress(port));

            selector1 = Selector.open();
            selector2 = Selector.open();
            selector3 = Selector.open();

            serverSocket.register(selector1, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            System.out.println(" initServer error " + e);
        }
    }

    public static void main(String[] args) {
        SocketMultiThreads socketMultiplexingThreads = new SocketMultiThreads();
        socketMultiplexingThreads.initServer();

        NIOThread boss = new NIOThread(socketMultiplexingThreads.selector1, 2);
        boss.start();
        NIOThread work1 = new NIOThread(socketMultiplexingThreads.selector2);
        work1.start();
        NIOThread work2 = new NIOThread(socketMultiplexingThreads.selector3);
        work2.start();

    }
}
class NIOThread extends Thread {
    Selector selector;
    static int selectors = 0;

    int id = 0;
    volatile static BlockingDeque<SocketChannel>[] queue;

    static AtomicInteger idx = new AtomicInteger();

    NIOThread(Selector selector, int n) {
        this.selector = selector;
        this.selectors = n;
        queue = new LinkedBlockingDeque[selectors];
        for (int i = 0; i < n; i++) {
            queue[i] = new LinkedBlockingDeque<>();
        }
        System.out.println("BOSS 启动 ");
    }
    NIOThread(Selector selector) {
        this.selector = selector;
        id = idx.getAndIncrement() % selectors;
        System.out.println("WORK 启动 ");
    }

    @Override
    public void run() {
        try {
            while (true){
                while (selector.select(100) > 0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    final Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        final SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()){
                            acceptHandler(key);
                        }
                        if (key.isReadable()){
                            SocketSingleThread.readHandler(key);
                        }
                    }
                }
                if (!queue[id].isEmpty()){
                    ByteBuffer buffer = ByteBuffer.allocate(8192);
                    SocketChannel client = queue[id].take();
                    client.register(selector, SelectionKey.OP_READ, buffer);
                    System.out.println("新客户端：" + client.getRemoteAddress() + "分配到" + id);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel client = serverSocketChannel.accept();
            client.configureBlocking(false);
            int num = idx.getAndIncrement() % selectors;
            queue[num].add(client);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
