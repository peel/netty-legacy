package com.nordea.nemis.core.globus.handler;

import io.netty.channel.*;

import java.util.logging.Logger;

public class GlobusServerHandshaker {
    private static final Logger logger = Logger.getLogger(GlobusServerHandshaker.class.getName());

    public ChannelFuture handshake(Channel channel, String msg){
        return handshake(channel, msg, channel.newPromise());
    }

    public final ChannelFuture handshake(Channel channel, String msg, final ChannelPromise promise){
        logger.info(String.format("%s Globus server handshake", channel));
        String response = "?";
        channel.writeAndFlush(response).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    promise.setSuccess();
                } else {
                    promise.setFailure(future.cause());
                }
            }
        });
        return promise;
    }

}