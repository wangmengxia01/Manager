/**
 * Author: guanxin
 * Date: 2015-07-24
 */

package com.hoolai.ccgames.bifront.net;

import com.hoolai.ccgames.bifront.starter.Global;
import com.hoolai.ccgames.skeleton.codec.protobuf.ProtobufDecoder;
import com.hoolai.ccgames.skeleton.codec.protobuf.ProtobufEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

public class BiFrontChannelInitializer extends
        ChannelInitializer< SocketChannel > {

    @Override
    protected void initChannel( SocketChannel ch ) throws Exception {

        ch.pipeline()
                .addLast( "FrameDecoder", new LengthFieldBasedFrameDecoder( 32 * 1024 * 1024, 0, 4, 0, 4 ) )
                .addLast( "MessageDecoder", new ProtobufDecoder( Global.commandRegistry ) )
                .addLast( "IdleStateHandler", new IdleStateHandler( 0, 60, 0 ) )
                .addLast( "FrameEncoder", new LengthFieldPrepender( 4 ) )
                .addLast( "MessageEncoder", new ProtobufEncoder() )
                .addLast( "MessageHandler", new BiFrontMessageHandler() );

    }

}
