package com.nordea.nemis.core.globus.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Logger;

public class GlobusServerHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = Logger.getLogger(GlobusServerHandler.class.getName());

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }
}
