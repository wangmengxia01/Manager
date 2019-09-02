package com.hoolai.ccgames.bifront.net;

import com.hoolai.ccgames.skeleton.arch.GameClient;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class BiClient extends GameClient implements HttpSessionBindingListener {

    private static final Logger logger = LoggerFactory.getLogger( BiClient.class );

    private int msgVer;

    public int getMsgVer() {
        return msgVer;
    }

    public void setMsgVer( int msgVer ) {
        this.msgVer = msgVer;
    }

    public BiClient( String name ) {
        super( name );
    }

    public void start() {
        this.startClient( 1, false, false );
    }

    public synchronized Object send( Object msg ) {

        logger.info( "{} enter", Thread.currentThread().getId() );
        Channel c = this.getChannel();
        c.writeAndFlush( msg );
        BiFrontMessageHandler handler = (BiFrontMessageHandler) c.pipeline().get( "MessageHandler" );
        return handler.getResponse();
    }

    public void stop() {
        this.stopClient();
    }

    @Override
    public void valueBound( HttpSessionBindingEvent event ) {
        // do nothing
        logger.debug( "GameClient-{} start by HttpSessionBindingListener", getName() );
    }

    @Override
    public void valueUnbound( HttpSessionBindingEvent event ) {
        logger.debug( "GameClient-{} stop by HttpSessionBindingListener", getName() );
        stop();
    }
}
