package com.galaxy.netty.server;

import com.galaxy.netty.handler.RequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.stereotype.Service;

/**
 * 初始化Netty服务
 * @author galaxy
 * @since 2021/8/21 22:33
 */
@Service
public class NettyServer {
    private final NioEventLoopGroup boss = new NioEventLoopGroup(1);
    private final NioEventLoopGroup worker = new NioEventLoopGroup();

    public void start(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try{
            int port = 8080;
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HttpServerCodec());
                            socketChannel.pipeline().addLast(new HttpObjectAggregator(512 * 1024));
                            socketChannel.pipeline().addLast(new RequestHandler());
                        }
                    });


            //同步阻塞 直到绑定完成
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            //监听通道关闭方法 等待服务端通道关闭完成
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
