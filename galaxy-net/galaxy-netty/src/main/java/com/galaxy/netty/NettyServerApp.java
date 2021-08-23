package com.galaxy.netty;

import com.galaxy.netty.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author galaxy
 * @since 2021/8/21 22:22
 */
@SpringBootApplication
public class NettyServerApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(NettyServerApp.class, args);
        NettyServer nettyServer = applicationContext.getBean(NettyServer.class);
        nettyServer.start();
    }
}
