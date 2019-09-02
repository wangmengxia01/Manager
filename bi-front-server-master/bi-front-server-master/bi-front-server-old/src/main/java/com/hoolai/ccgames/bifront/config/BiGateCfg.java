package com.hoolai.ccgames.bifront.config;

import com.hoolai.ccgames.skeleton.config.ClientConfig;
import com.hoolai.ccgames.skeleton.utils.PropUtil;

import java.util.*;

public class BiGateCfg extends ClientConfig {

    public int MESSAGE_VERSION;

    @Override
    public boolean init( String filePath ) {

        Properties props = PropUtil.getProps( filePath );

        SERVER_ADDR = props.getProperty( "server_addr" );
        SERVER_PORT = Integer.parseInt( props.getProperty( "server_port" ) );
        MESSAGE_VERSION = Integer.parseInt( props.getProperty( "message_version" ) );

        return true;
    }

    public static void main( String[] args ) {
        List< Integer > a = Arrays.asList( 1, 2, 3 );
        System.out.println( a.toString() );
        Collections.reverse( a );
        System.out.println( a.toString() );
    }

}
