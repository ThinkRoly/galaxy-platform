package com.galaxy.netty.handler;

import com.galaxy.netty.client.HttpClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author galaxy
 * @since 2021/8/22 8:42
 */
public class RequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        if (request != null) {

            //获取uri, 过滤指定的资源
            URI uri = new URI(request.uri());

            ByteBuf content = request.content();
            HttpHeaders headers = request.headers();


            FullHttpResponse response;
            ByteBuf body ;
            if (request.method() == HttpMethod.GET){
                body = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);

                response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE, body);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, body.readableBytes()+"");
            }else if (request.method() == HttpMethod.POST){

                body = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);
                response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE, body);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, body.readableBytes()+"");
            }else {

                response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.MULTI_STATUS);
            }


            //将构建好 response返回
            ctx.writeAndFlush(response);
        }
    }
}
