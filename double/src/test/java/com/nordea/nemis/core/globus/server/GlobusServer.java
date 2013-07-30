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
        ServerBootstrap b = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new GlobusServerInitializer());

        b.bind(port).sync().channel().closeFuture().sync();
    }

    public static void main(String[] args) throws Exception {
        int port = (args.length>0) ? Integer.parseInt(args[0]) : 8080;
        new GlobusServer(port).run();
    }
}
