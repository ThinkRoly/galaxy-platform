package com.galaxy.netty.client;

import java.net.InetSocketAddress;

/**
 * @author galaxy
 * @since 2021/8/22 8:59
 */
public interface EndPoint {

    InetSocketAddress getLocalAddress() throws Exception;

    InetSocketAddress getRemoteAddress();
}
