package com.yipengup.server;

import com.yipengup.server.handler.TimeEncoder;
import com.yipengup.server.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Setter;

/**
 * @author yipengup
 * @date 2021/12/17
 */
@Setter
public class Server {

    public static final int SO_BACKLOG_MAX_LENGTH = 128;
    /**
     * 指定服务端监听的端口
     */
    private static int port;

    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new TimeEncoder(), new TimeServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, SO_BACKLOG_MAX_LENGTH)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);

            // 绑定并开始接受到来的连接
            ChannelFuture channelFuture = serverBootstrap.bind(bindPort(args)).sync();
            // 阻塞直到服务器Socket被关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            // 当出现异常时，需要优雅的关闭线程池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static int bindPort(String[] args) {
        port = 8080;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }
        return port;
    }

}
