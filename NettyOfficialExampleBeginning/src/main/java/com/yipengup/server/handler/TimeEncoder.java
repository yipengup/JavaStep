package com.yipengup.server.handler;

import com.yipengup.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author yipengup
 * @date 2021/12/30
 */
public class TimeEncoder extends MessageToByteEncoder<UnixTime> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, UnixTime unixTime, ByteBuf byteBuf) throws Exception {
        // 将传过来的UnixTime转化成ByteBuf
        byteBuf.writeInt(((int) unixTime.value()));
    }
}
