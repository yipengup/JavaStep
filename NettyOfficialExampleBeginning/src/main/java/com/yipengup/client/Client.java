package com.yipengup.client;

import com.yipengup.client.handler.SimpleTimeClientHandler;
import com.yipengup.client.handler.TimeDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author yipengup
 * @date 2021/12/17
 */
public class Client {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8080;
        if (args.length >= 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, false)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // pipeline.addLast(new TimeClientHandler());
                            pipeline.addLast(new TimeDecoder(), new SimpleTimeClientHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            workerGroup.shutdownGracefully();
        }


    }
}
