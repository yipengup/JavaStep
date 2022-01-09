package com.yipengup.server.handler;

import com.yipengup.pojo.UnixTime;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author yipengup
 * @date 2021/12/17
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 建立连接后立即向客户端发送服务器的时间戳， 然后关闭连接
        ChannelFuture channelFuture = ctx.writeAndFlush(new UnixTime());
        // 操作完成之后关闭连接
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 抛出异常后关闭连接
        ctx.close();
    }
}
