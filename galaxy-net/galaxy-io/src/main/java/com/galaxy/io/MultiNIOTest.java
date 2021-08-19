package com.galaxy.io;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 多线程版NIO
 * @author syh
 * @since 2021/8/18 16:56
 */
public class MultiNIOTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector = Selector.open();
        Selector selector1 = Selector.open();
        Selector selector2 = Selector.open();
        Selector selector3 = Selector.open();


    }
}
