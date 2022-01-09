package com.yipengup.client.handler;

import com.yipengup.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author yipengup
 * @date 2021/12/18
 */
public class TimeDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // ByteToMessageDecoder 自身就会维护一个缓冲区
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        // list.add(byteBuf.readBytes(4));
        //  这里将读取到的信息转化成 UnitTime 类
        list.add(new UnixTime(byteBuf.readUnsignedInt()));
        // 这里不能释放引用
        // System.out.println("TimeDecoder(byteBuf): " + byteBuf);
        // ReferenceCountUtil.release(byteBuf);
        // System.out.println("TimeDecoder(byteBuf): " + byteBuf);
    }
}
