package com.hoolai.ccgames.bifront.service;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bi.protocol.Common;
import com.hoolai.ccgames.skeleton.dispatch.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseService {

    protected static final Logger logger = LoggerFactory.getLogger( BaseService.class );

    public static String safeString( String s ) {
        return s == null ? "" : s;
    }

    public static String boolString( boolean b ) {
        return b ? "Y" : "N";
    }

    @MessageHandler( kindId = CommandList.ERROR )
    protected Common.MessageStatus declareType1() {
        return null;
    }

    @MessageHandler( kindId = CommandList.ERROR_FROM_BI )
    protected Common.MessageStatus declareType2() {
        return null;
    }
}
