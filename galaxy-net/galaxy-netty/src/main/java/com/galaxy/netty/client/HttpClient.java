package com.galaxy.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;

/**
 * @author galaxy
 * @since 2021/8/22 9:03
 */
public class HttpClient extends AbstractClient{
    private Bootstrap bootstrap;

    private volatile Channel channel;

    private static final NioEventLoopGroup NIO_GROUP = new NioEventLoopGroup();

    public HttpClient(String host, int port, int connectionTimeout) {
        super(host, port, connectionTimeout);
    }

    private class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            channel = ctx.channel();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            try {
                respMsg = msg.toString(StandardCharsets.UTF_8);
            } finally {
                countDownLatch.countDown();
                ctx.close();
            }
        }
    }

    @Override
    protected void doOpen() throws Exception {
        bootstrap = new Bootstrap();
        bootstrap
            .group(NIO_GROUP)
            .remoteAddress(getRemoteAddress())
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getConnectionTimeout())
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ClientHandler());
                }
            });
    }

    @Override
    public void doConnect() {
        ChannelFuture f = bootstrap.connect().syncUninterruptibly();
        if (!f.isSuccess() && null != f.cause()) {
            System.out.println("The client failed to connect the server:" + getRemoteAddress() + ",error message is:" + f.cause().getMessage());
        }
    }

    @Override
    protected Channel getChannel() {
        return channel;
    }
}
