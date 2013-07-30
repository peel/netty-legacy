package com.nordea.nemis.core.globus.server;

import com.nordea.nemis.core.globus.handler.GlobusServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class GlobusServer {
    private final int port;

    public GlobusServer(int port){
        this.port = port;
    }

    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new GlobusServerInitializer());

            b.bind(port).sync().channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = (args.length>0) ? Integer.parseInt(args[0]) : 9999;
        new GlobusServer(port).run();
    }
}
