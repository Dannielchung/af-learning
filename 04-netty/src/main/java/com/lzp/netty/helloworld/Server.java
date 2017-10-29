package com.lzp.netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description: 服务器端启动程序
 * @Author: lzp
 * @Date: 2017/10/29 8:58
 * @Version V1.0
 */
public class Server {

    public static void main(String[] args) throws Exception {
        //1、创建两个线程组
        //bossGroup用于接收Client的连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //workGroup用于实际的业务处理操作
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //2、启动NIO服务的辅助启动类，对server进行配置
            ServerBootstrap b = new ServerBootstrap();
            //3、对server进行配置
            //把两个线程组加入进来
            b.group(bossGroup, workGroup)
                    // 指定NioServerSocketChannel类型的通道
                    .channel(NioServerSocketChannel.class)
                    // 使用childHandler绑定具体的 事件处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    })
                    // 设置tcp缓冲区
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 保持连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //4、绑定端口，启动server
            ChannelFuture f = b.bind(9876).sync();

            //5、阻塞server
            f.channel().closeFuture().sync();

        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
