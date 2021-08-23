package com.galaxy.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 * @author galaxy
 * @since 2021/8/22 8:55
 */
public abstract class AbstractClient implements EndPoint{
    private String host;
    private int port;

    private int connectionTimeout;

    protected final CountDownLatch countDownLatch = new CountDownLatch(1);
    protected String respMsg;


    public void send(Object message) throws Exception{
        doOpen();
        doConnect();
        write(message);
    }

    private void write(Object message) {
        Channel channel = getChannel();
        if (null != channel) {
            ChannelFuture f = channel.writeAndFlush(byteBufferFrom(message)).syncUninterruptibly();
            if (!f.isSuccess()) {
                System.out.printf("Failed to send message to %s %s%n", getRemoteAddress(), f.cause().getMessage());
            }else {

            }
        }
    }
    private ByteBuf byteBufferFrom(Object message) {
        return message instanceof String ? Unpooled.copiedBuffer((String) message, StandardCharsets.UTF_8) : Unpooled.copiedBuffer((byte[]) message);
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return new InetSocketAddress(host, port);
    }
    @Override
    public InetSocketAddress getLocalAddress() throws Exception {
        throw new Exception();
    }


    protected abstract void doOpen() throws Exception;
    protected abstract void doConnect() throws Exception;
    protected abstract Channel getChannel();


    public AbstractClient(String host, int port, int connectionTimeout) {
        this.host = host;
        this.port = port;
        this.connectionTimeout = connectionTimeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
