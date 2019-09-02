package com.hoolai.ccgames.bifront.net;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bi.protocol.Common;
import com.hoolai.ccgames.skeleton.arch.ExceptionHandler;
import com.hoolai.ccgames.skeleton.codec.json.JsonUtil;
import com.hoolai.ccgames.skeleton.dispatch.NetMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BiFrontMessageHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger( BiFrontMessageHandler.class );

    public static Common.MessageEmpty heartbeat = Common.MessageEmpty.newBuilder().build();

    private BlockingQueue< Object > queue = new LinkedBlockingQueue< Object >();

    public Object getResponse() {
        try {
            return queue.take();
        } catch( InterruptedException e ) {
            logger.error( e.getMessage(), e );
        } finally {
            queue.clear();
        }
        return null;
    }

    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) {
        try {
            logger.debug( JsonUtil.toString( msg ) );
            if( msg instanceof NetMessage ) {
                NetMessage msg0 = (NetMessage) msg;
                queue.add( msg0.getMessage() );
            }
        } finally {
            ReferenceCountUtil.release( msg );
        }
    }

    @Override
    public void channelActive( ChannelHandlerContext ctx ) {

    }

    @Override
    public void channelInactive( ChannelHandlerContext ctx ) {
        logger.info( "Disconnect from {}", ctx.channel().remoteAddress() );
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) {
        ExceptionHandler.handle( ctx, cause );
    }

    @Override
    public void userEventTriggered( ChannelHandlerContext ctx, Object evt )
            throws Exception {
        if( evt instanceof IdleStateEvent ) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if( event.state() == IdleState.WRITER_IDLE ) {
                ctx.writeAndFlush( new NetMessage( CommandList.IGNORE, 0, heartbeat ) );
            }
        }
    }
}
