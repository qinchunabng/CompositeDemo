package com.qin.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by DELL on 2017/11/20.
 */
public class NettyClientInHandler extends ChannelInboundHandlerAdapter {

    private StringBuilder message;
    private String sendMsg;

    public NettyClientInHandler(StringBuilder message,String sendMsg){
        this.message=message;
        this.sendMsg=sendMsg;
    }

    //当Netty连接成功后会调用这个方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        System.out.println("--------------------Channel Active---------------------");
        ByteBuf encoded=ctx.alloc().buffer(4*sendMsg.length());
        encoded.writeBytes(sendMsg.getBytes());
        ctx.writeAndFlush(encoded);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("----------------Channel Read-------------------");
        ByteBuf result= (ByteBuf) msg;
        byte[] bytes=new byte[result.readableBytes()];
        result.readBytes(bytes);
        String resultStr=new String(bytes);
        System.out.println("Server response msg:"+resultStr);
        message.append(resultStr);
        result.release();
    }
}
