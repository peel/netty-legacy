import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.junit.Test;

public class GlobusServerTest {
    @Test
    public void getShouldReturnWhatWasPut() throws Exception{
       new GlobusServer().run();
    }

    class GlobusServer {
        public void run() throws Exception {
            ServerBootstrap b = new ServerBootstrap();
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try{
                b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new GlobusChannelInitializer());
                b.bind(8080).sync().channel().closeFuture().sync();
            }finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }
    }

    private class GlobusChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast("formatter", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
            pipeline.addLast("decoder", new StringDecoder());
            pipeline.addLast("encoder", new StringEncoder());
            pipeline.addLast("handshaker", new GlobusHandshakeHandler());
        }
    }

    private class GlobusHandshakeHandler extends SimpleChannelInboundHandler<String>{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush("START OFS Y/N=");
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
            if(s.equals("Y")){
                if (ctx.pipeline().get(GlobusHandshakeHandler.class)!=null){
                   ctx.pipeline().remove(GlobusHandshakeHandler.class);
                   ctx.pipeline().addLast("handler", new GlobusServerHandler());
                }
            }else if(s.equals("N")){
                ctx.writeAndFlush("START GLOBUS Y/N=");
                if (ctx.pipeline().get(GlobusHandshakeHandler.class)!=null){
                    ctx.pipeline().remove(GlobusHandshakeHandler.class);
                }
            }
        }
    }

    private class GlobusServerHandler extends SimpleChannelInboundHandler<String> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
            ctx.writeAndFlush("? ");
        }
    }
}
