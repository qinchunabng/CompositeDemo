package com.qin.netty;

import com.qin.invoke.InvokeHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by DELL on 2017/11/20.
 */
public class NettyServerInHandler extends ChannelInboundHandlerAdapter {

    /**
     * nettty客户端有消息过来的时候会调用这个方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        ByteBuf result= (ByteBuf) msg;
        byte[] bytes=new byte[result.readableBytes()];
        result.readBytes(bytes);
        String resultStr=new String(bytes);
        System.out.println(resultStr);
        //释放
        result.release();
        //服务端返回消息给客户端
        String response=new InvokeHandler().invokeService(resultStr);
        ByteBuf encoded=ctx.alloc().buffer(4*response.length());
        encoded.writeBytes(response.getBytes());
        ctx.writeAndFlush(encoded);
        ctx.close();
    }



}
