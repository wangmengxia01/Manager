package com.hoolai.ccgames.skeleton.codec.protobuf;

import com.google.protobuf.MessageLite;
import com.hoolai.ccgames.skeleton.dispatch.CommandProperties;
import com.hoolai.ccgames.skeleton.dispatch.CommandRegistry;
import com.hoolai.ccgames.skeleton.dispatch.NetMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LengthProtobufDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger( LengthProtobufDecoder.class );

    private CommandRegistry registry;

    public LengthProtobufDecoder( CommandRegistry registry ) {
        this.registry = registry;
    }

    @Override
    public void decode( ChannelHandlerContext ctx, ByteBuf msg,
                        List< Object > out ) throws Exception {

        int length = msg.readInt();
        int kindId = msg.readInt();
        int msgId = msg.readInt();
        CommandProperties properties = registry.get( kindId );
        if( properties == null ) {
            msg.skipBytes( length - 8 );
            logger.error( "[LengthProtobufDecoder::decode] Can't find cmd {}", kindId );
            return;
        }
        Object message = ProtobufUtil.decode( msg, (MessageLite) properties.remoteObjectInst );
        out.add( new NetMessage( kindId, msgId, message ) );
    }

    @Override
    public void decodeLast( ChannelHandlerContext ctx, ByteBuf msg,
                            List< Object > out ) throws Exception {
        // do nothing
    }

}
