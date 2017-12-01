package com.qin.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.Future;

/**
 * Netty通讯的工具类
 * Created by DELL on 2017/11/20.
 */
public class NettyUtil {

    public static void startServer(String port) throws InterruptedException {
        //调度线程
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        //工作线程
        EventLoopGroup workGroup=new NioEventLoopGroup();

        ServerBootstrap bootstrap=new ServerBootstrap();
        try {
            bootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyServerInHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG,128);
            ChannelFuture future = bootstrap.bind(Integer.parseInt(port)).sync();
            future.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    public static String sendMsg(String host, String port, final String msg) throws InterruptedException {
        EventLoopGroup workGroup=new NioEventLoopGroup();
        final StringBuilder resultMsg=new StringBuilder();
        Bootstrap bootstrap=new Bootstrap();
        try {
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientInHandler(resultMsg,msg));
                        }
                    });
            ChannelFuture future =bootstrap.connect(host,Integer.valueOf(port)).sync();
            future.channel().closeFuture().await();
        } finally {
            workGroup.shutdownGracefully();
        }
        return resultMsg.toString();
    }
}
