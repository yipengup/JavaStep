package com.yipengup.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yipengup
 * @date 2021/12/17
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private ByteBuf byteBuf;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 初始化byteBuf
        this.byteBuf = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 释放byteBuf引用
        ReferenceCountUtil.release(byteBuf);
        this.byteBuf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            System.out.println(new Date() + ": 接收到服务端请求。");
            ByteBuf byteBuf = (ByteBuf) msg;
            this.byteBuf.writeBytes(byteBuf);
            System.out.println(new Date() + "：this.byteBuf.readableBytes()：" + this.byteBuf.readableBytes());

            if (this.byteBuf.readableBytes() >= 4) {
                long timestamp = this.byteBuf.readUnsignedInt();
                // 格式化时间戳
                Date date = new Date(timestamp * 1000L);
                System.out.println(FORMAT.format(date));
                ctx.close();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
