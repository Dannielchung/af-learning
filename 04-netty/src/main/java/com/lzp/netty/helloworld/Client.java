package com.lzp.netty.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description: 客户端启动程序
 * @Author: lzp
 * @Date: 2017/10/29 8:58
 * @Version V1.0
 */
public class Client {

    public static void main(String[] args) throws Exception{
        EventLoopGroup workgroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workgroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });

        ChannelFuture cf1 = b.connect("127.0.0.1", 9876).sync();

        //buf
        cf1.channel().writeAndFlush(Unpooled.copiedBuffer("777".getBytes()));

        cf1.channel().closeFuture().sync();
        workgroup.shutdownGracefully();
    }
}
