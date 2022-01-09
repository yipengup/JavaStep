package com.yipengup.client.handler;

import com.yipengup.pojo.UnixTime;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;

/**
 * @author yipengup
 * @date 2021/12/18
 */
public class SimpleTimeClientHandler extends ChannelInboundHandlerAdapter {
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UnixTime unixTime = (UnixTime) msg;
        System.out.println("unixTime = " + unixTime);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
