package com.hoolai.ccgames.bifront.net;

import com.hoolai.ccgames.bifront.config.BiGateCfg;
import com.hoolai.ccgames.bifront.starter.BiFrontFinalizer;
import com.hoolai.ccgames.bifront.starter.BiFrontInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class BiClientFactory {

    private static final Logger logger = LoggerFactory.getLogger( BiClientFactory.class );

    private static AtomicInteger index = new AtomicInteger( 0 );

    private static String configFilePath = null;

    public static void setConfig( String filePath ) {
        configFilePath = filePath;
    }

    public static BiClient create() {
        BiClient c = new BiClient( "BiClient" + index.incrementAndGet() );
        BiGateCfg config = new BiGateCfg();
        config.init( configFilePath );
        c.setMsgVer( config.MESSAGE_VERSION );
        c.setInitializer( new BiFrontInitializer() )
                .setFinalizer( new BiFrontFinalizer() )
                .setChannelInitializer( new BiFrontChannelInitializer() )
                .initClient( config );
        return c;
    }

    public static BiClient createAndStart() {
        BiClient c = create();
        c.start();
        int tryCount = 30;
        while( !c.getChannel().isActive() ) {
            try {
                Thread.sleep( 100L );
            } catch( InterruptedException e ) {
                logger.error( e.getMessage(), e );
            }
            if( tryCount-- < 0 ) return null;
        }
        return c;
    }
}
