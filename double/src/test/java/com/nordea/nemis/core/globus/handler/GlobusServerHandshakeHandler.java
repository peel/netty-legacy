package com.nordea.nemis.core.globus.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Logger;

public class GlobusServerHandshakeHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = Logger.getLogger(GlobusServerHandshakeHandler.class.getName());
    private GlobusServerHandshaker handshaker;

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("START OFS Y/N=");
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        if(msg instanceof TcpRequest){
            handleTcpRequest((TcpRequest)msg);
        }
        else if(msg instanceof OfsRequest){
            handleOfsRequest((OfsRequest) msg);
        }
    }

    private void handleTcpRequest(TcpRequest msg) {
    }

    private void handleOfsRequest(OfsRequest msg) {

    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
