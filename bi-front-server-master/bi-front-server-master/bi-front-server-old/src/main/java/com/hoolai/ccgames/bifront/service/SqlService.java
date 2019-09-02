package com.hoolai.ccgames.bifront.service;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bi.protocol.Common;
import com.hoolai.ccgames.bi.protocol.Sql;
import com.hoolai.ccgames.bifront.net.BiClient;
import com.hoolai.ccgames.skeleton.dispatch.MessageHandler;
import com.hoolai.ccgames.skeleton.dispatch.NetMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SqlService extends BaseService {

    @MessageHandler( kindId = CommandList.GET_LONG, remoteClass = Sql.LongReply.class )
    public long queryLong( String sql, BiClient c ) {
        Sql.SqlQuery req = Sql.SqlQuery.newBuilder()
                .setStatement( sql ).build();
        Object resp = c.send( new NetMessage( CommandList.GET_LONG, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't SqlService queryLong {}", sql );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            logger.error( "Can't SqlService queryLong, errCode: {} {}",
                    resp0.getStatus(), resp0.getDescription() );
        }
        else if( resp instanceof Sql.LongReply ) {
            return ( (Sql.LongReply) resp ).getData();
        }
        return -1L;
    }

    @MessageHandler( kindId = CommandList.GET_STRING, remoteClass = Sql.StringReply.class )
    public String queryString( String sql, BiClient c ) {
        Sql.SqlQuery req = Sql.SqlQuery.newBuilder()
                .setStatement( sql ).build();
        Object resp = c.send( new NetMessage( CommandList.GET_STRING, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't SqlService queryString {}", sql );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            logger.error( "Can't SqlService queryString, errCode: {} {}",
                    resp0.getStatus(), resp0.getDescription() );
        }
        else if( resp instanceof Sql.StringReply ) {
            return ( (Sql.StringReply) resp ).getData();
        }
        return null;
    }

    @MessageHandler( kindId = CommandList.GET_LIST_LONG, remoteClass = Sql.ListLongReply.class )
    public List< Long > queryListLong( String sql, BiClient c ) {
        Sql.SqlQuery req = Sql.SqlQuery.newBuilder()
                .setStatement( sql ).build();
        Object resp = c.send( new NetMessage( CommandList.GET_LIST_LONG, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't SqlService queryListLong {}", sql );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            logger.error( "Can't SqlService queryListLong, errCode: {} {}",
                    resp0.getStatus(), resp0.getDescription() );
        }
        else if( resp instanceof Sql.ListLongReply ) {
            Sql.ListLongReply resp0 = (Sql.ListLongReply) resp;
            List< Long > rv = new ArrayList<>();
            rv.addAll( resp0.getDataList() );
            return rv;
        }
        return null;
    }

    @MessageHandler( kindId = CommandList.GET_LIST_STRING, remoteClass = Sql.ListStringReply.class )
    public List< String > queryListString( String sql, BiClient c ) {
        Sql.SqlQuery req = Sql.SqlQuery.newBuilder()
                .setStatement( sql ).build();
        Object resp = c.send( new NetMessage( CommandList.GET_LIST_STRING, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't SqlService queryListString {}", sql );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            logger.error( "Can't SqlService queryListString, errCode: {} {}",
                    resp0.getStatus(), resp0.getDescription() );
        }
        else if( resp instanceof Sql.ListStringReply ) {
            Sql.ListStringReply resp0 = (Sql.ListStringReply) resp;
            List< String > rv = new ArrayList<>();
            rv.addAll( resp0.getDataList() );
            return rv;
        }
        return null;
    }

    @MessageHandler( kindId = CommandList.GET_MAP_LONG_LONG, remoteClass = Sql.MapLongLongReply.class )
    public Map< Long, Long > queryMapLongLong( String sql, BiClient c ) {
        Sql.SqlQuery req = Sql.SqlQuery.newBuilder()
                .setStatement( sql ).build();
        Object resp = c.send( new NetMessage( CommandList.GET_MAP_LONG_LONG, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't SqlService queryMapLongLong {}", sql );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            logger.error( "Can't SqlService queryMapLongLong, errCode: {} {}",
                    resp0.getStatus(), resp0.getDescription() );
        }
        else if( resp instanceof Sql.MapLongLongReply ) {
            Sql.MapLongLongReply resp0 = (Sql.MapLongLongReply) resp;
            Map< Long, Long > rv = new TreeMap<>();
            for( int i = 0; i < resp0.getKeysCount(); ++i ) {
                rv.put( resp0.getKeys( i ), resp0.getVals( i ) );
            }
            return rv;
        }
        return null;
    }

    @MessageHandler( kindId = CommandList.GET_MAP_LONG_STRING, remoteClass = Sql.MapLongStringReply.class )
    public Map< Long, String > queryMapLongString( String sql, BiClient c ) {
        Sql.SqlQuery req = Sql.SqlQuery.newBuilder()
                .setStatement( sql ).build();
        Object resp = c.send( new NetMessage( CommandList.GET_MAP_LONG_STRING, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't SqlService queryMapLongString {}", sql );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            logger.error( "Can't SqlService queryMapLongString, errCode: {} {}",
                    resp0.getStatus(), resp0.getDescription() );
        }
        else if( resp instanceof Sql.MapLongStringReply ) {
            Sql.MapLongStringReply resp0 = (Sql.MapLongStringReply) resp;
            Map< Long, String > rv = new TreeMap<>();
            for( int i = 0; i < resp0.getKeysCount(); ++i ) {
                rv.put( resp0.getKeys( i ), resp0.getVals( i ) );
            }
            return rv;
        }
        return null;
    }


    @MessageHandler( kindId = CommandList.GET_MAP_STRING_LONG, remoteClass = Sql.MapStringLongReply.class )
    public Map< String, Long > queryMapStringLong( String sql, BiClient c ) {
        Sql.SqlQuery req = Sql.SqlQuery.newBuilder()
                .setStatement( sql ).build();
        Object resp = c.send( new NetMessage( CommandList.GET_MAP_STRING_LONG, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't SqlService queryMapStringLong {}", sql );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            logger.error( "Can't SqlService queryMapStringLong, errCode: {} {}",
                    resp0.getStatus(), resp0.getDescription() );
        }
        else if( resp instanceof Sql.MapStringLongReply ) {
            Sql.MapStringLongReply resp0 = (Sql.MapStringLongReply) resp;
            Map< String, Long > rv = new TreeMap<>();
            for( int i = 0; i < resp0.getKeysCount(); ++i ) {
                rv.put( resp0.getKeys( i ), resp0.getVals( i ) );
            }
            return rv;
        }
        return null;
    }


}
