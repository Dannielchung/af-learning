package com.lzp.netty.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @Description: 事件处理器-服务器端
 * @Author: lzp
 * @Date: 2017/10/29 8:58
 * @Version V1.0
 */
public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            //do something msg
            ByteBuf buf = (ByteBuf) msg;
            byte[] data = new byte[buf.readableBytes()];
            buf.readBytes(data);
            System.out.println(new String(data,"utf-8"));

            // 给客户端反馈
            ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务器端的反馈".getBytes())).addListener(ChannelFutureListener.CLOSE);
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
