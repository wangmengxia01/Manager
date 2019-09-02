package com.hoolai.ccgames.bifront.service;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bi.protocol.Common;
import com.hoolai.ccgames.bi.protocol.RetCode;
import com.hoolai.ccgames.bi.protocol.User;
import com.hoolai.ccgames.bifront.net.BiClient;
import com.hoolai.ccgames.skeleton.dispatch.MessageHandler;
import com.hoolai.ccgames.skeleton.dispatch.NetMessage;

import java.util.List;

public class AdminService extends BaseService {

    @MessageHandler( kindId = CommandList.LOGIN_FROM_GM, remoteClass = User.MessageUserId.class )
    public long login( String name, String pass, BiClient c ) {
        Common.MessageLogin req = Common.MessageLogin.newBuilder()
                .setLoginName( name )
                .setLoginPass( pass )
                .setMsgVer( c.getMsgVer() ).build();
        Object resp = c.send( new NetMessage( CommandList.LOGIN_FROM_GM, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't login {}", name );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            logger.error( "Can't login for {}, errCode: {} {}",
                    name, resp0.getStatus(), resp0.getDescription() );
        }
        else if( resp instanceof User.MessageUserId ) {
            User.MessageUserId resp0 = (User.MessageUserId) resp;
            return resp0.getUserId();
        }
        return -1L;
    }

    @MessageHandler( kindId = CommandList.MOD_GM_USER_PASS, remoteClass = Common.MessageStatus.class )
    public boolean modifyPass( String name, String pass, BiClient c ) {
        Common.MessageLogin req = Common.MessageLogin.newBuilder()
                .setLoginName( name )
                .setLoginPass( pass ).build();
        Object resp = c.send( new NetMessage( CommandList.MOD_GM_USER_PASS, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't modifyPass {}", name );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            if( resp0.getStatus() == RetCode.OK ) return true;
            logger.error( "Can't modifyPass for {}, errCode: {} {}",
                    name, resp0.getStatus(), resp0.getDescription() );
        }
        return false;
    }

    @MessageHandler( kindId = CommandList.GET_GM_USER_LIST )
    public User.MessageUserList listGmUsers( BiClient c ) {
        Common.MessageEmpty req = Common.MessageEmpty.newBuilder().build();
        Object resp = c.send( new NetMessage( CommandList.GET_GM_USER_LIST, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't listUsers" );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            logger.error( "Can't listGmUsers, errCode: {} {}",
                    resp0.getStatus(), resp0.getDescription() );
        }
        else if( resp instanceof User.MessageUserList ) {
            return (User.MessageUserList) resp;
        }
        return null;
    }

    @MessageHandler( kindId = CommandList.NEW_GM_USER, remoteClass = Common.MessageStatus.class )
    public boolean addGmUser( String name, String pass, BiClient c ) {
        Common.MessageLogin req = Common.MessageLogin.newBuilder()
                .setLoginName( name )
                .setLoginPass( pass ).build();
        Object resp = c.send( new NetMessage( CommandList.NEW_GM_USER, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't addUser" );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            if( resp0.getStatus() == RetCode.OK ) return true;
            logger.error( "Can't addGmUser, errCode: {} {}",
                    resp0.getStatus(), resp0.getDescription() );
        }
        return false;
    }

    @MessageHandler( kindId = CommandList.DEL_GM_USER, remoteClass = Common.MessageStatus.class )
    public boolean delGmUser( long userId, BiClient c ) {
        User.MessageUserId req = User.MessageUserId.newBuilder()
                .setUserId( userId ).build();
        Object resp = c.send( new NetMessage( CommandList.DEL_GM_USER, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't delUser" );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            if( resp0.getStatus() == RetCode.OK ) return true;
            logger.error( "Can't delGmUser, errCode: {} {}",
                    resp0.getStatus(), resp0.getDescription() );
        }
        return false;
    }

    @MessageHandler( kindId = CommandList.GET_GM_USER_AUTH, remoteClass = User.MessageUserAuth.class )
    public List< String > getAuth( long userId, BiClient c ) {
        User.MessageUserId req = User.MessageUserId.newBuilder()
                .setUserId( userId ).build();
        Object resp = c.send( new NetMessage( CommandList.GET_GM_USER_AUTH, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't getAuth" );
        }
        else if( resp instanceof User.MessageUserAuth ) {
            User.MessageUserAuth resp0 = (User.MessageUserAuth) resp;
            return resp0.getAuthList();
        }
        return null;
    }

    @MessageHandler( kindId = CommandList.SET_GM_USER_AUTH, remoteClass = Common.MessageStatus.class )
    public boolean setAuth( long userId, List< String > auth, BiClient c ) {
        User.MessageUserAuth req = User.MessageUserAuth.newBuilder()
                .setUserId( userId )
                .addAllAuth( auth ).build();
        Object resp = c.send( new NetMessage( CommandList.SET_GM_USER_AUTH, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't setAuth {}", userId );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            if( resp0.getStatus() == RetCode.OK ) return true;
            logger.error( "Can't setAuth for {}, errCode: {} {}",
                    userId, resp0.getStatus(), resp0.getDescription() );
        }
        return false;
    }

    @MessageHandler( kindId = CommandList.GET_BI_SERVER_USER_LIST )
    public User.MessageServerUserList listBiServerUsers( BiClient c ) {
        Common.MessageEmpty req = Common.MessageEmpty.newBuilder().build();
        Object resp = c.send( new NetMessage( CommandList.GET_BI_SERVER_USER_LIST, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't listBiServerUsers" );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            logger.error( "Can't listBiServerUsers, errCode: {} {}",
                    resp0.getStatus(), resp0.getDescription() );
        }
        else if( resp instanceof User.MessageServerUserList ) {
            return (User.MessageServerUserList) resp;
        }
        return null;
    }

    @MessageHandler( kindId = CommandList.NEW_BI_SERVER_USER, remoteClass = Common.MessageStatus.class )
    public boolean addBiServerUser( String name, String pass, BiClient c ) {
        Common.MessageLogin req = Common.MessageLogin.newBuilder()
                .setLoginName( name )
                .setLoginPass( pass ).build();
        Object resp = c.send( new NetMessage( CommandList.NEW_BI_SERVER_USER, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't addBiServerUser" );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            if( resp0.getStatus() == RetCode.OK ) return true;
            logger.error( "Can't addBiServerUser for {}, errCode: {} {}",
                    name,
                    resp0.getStatus(), resp0.getDescription() );
        }
        return false;
    }

    @MessageHandler( kindId = CommandList.DEL_BI_SERVER_USER, remoteClass = Common.MessageStatus.class )
    public boolean delBiServerUser( long userId, BiClient c ) {
        User.MessageUserId req = User.MessageUserId.newBuilder()
                .setUserId( userId ).build();
        Object resp = c.send( new NetMessage( CommandList.DEL_BI_SERVER_USER, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't delBiServerUser" );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            if( resp0.getStatus() == RetCode.OK ) return true;
            logger.error( "Can't delBiServerUser for {}, errCode: {} {}",
                    userId,
                    resp0.getStatus(), resp0.getDescription() );
        }
        return false;
    }

    @MessageHandler( kindId = CommandList.GET_GAME_SERVER_USER_LIST )
    public User.MessageServerUserList listGameServerUsers( BiClient c ) {
        Common.MessageEmpty req = Common.MessageEmpty.newBuilder().build();
        Object resp = c.send( new NetMessage( CommandList.GET_GAME_SERVER_USER_LIST, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't listGameServerUsers" );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            logger.error( "Can't listGameServerUsers, errCode: {} {}",
                    resp0.getStatus(), resp0.getDescription() );
        }
        else if( resp instanceof User.MessageServerUserList ) {
            return (User.MessageServerUserList) resp;
        }
        return null;
    }

    @MessageHandler( kindId = CommandList.NEW_GAME_SERVER_USER, remoteClass = Common.MessageStatus.class )
    public boolean addGameServerUser( String name, String pass, BiClient c ) {
        Common.MessageLogin req = Common.MessageLogin.newBuilder()
                .setLoginName( name )
                .setLoginPass( pass ).build();
        Object resp = c.send( new NetMessage( CommandList.NEW_GAME_SERVER_USER, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't addGameServerUser" );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            if( resp0.getStatus() == RetCode.OK ) return true;
            logger.error( "Can't addGameServerUser for {}, errCode: {} {}",
                    name,
                    resp0.getStatus(), resp0.getDescription() );
        }
        return false;
    }

    @MessageHandler( kindId = CommandList.DEL_GAME_SERVER_USER, remoteClass = Common.MessageStatus.class )
    public boolean delGameServerUser( long userId, BiClient c ) {
        User.MessageUserId req = User.MessageUserId.newBuilder()
                .setUserId( userId ).build();
        Object resp = c.send( new NetMessage( CommandList.DEL_GAME_SERVER_USER, 0, req ) );
        if( resp == null ) {
            logger.error( "Timeout Can't delGameServerUser" );
        }
        else if( resp instanceof Common.MessageStatus ) {
            Common.MessageStatus resp0 = (Common.MessageStatus) resp;
            if( resp0.getStatus() == RetCode.OK ) return true;
            logger.error( "Can't delGameServerUser for {}, errCode: {} {}",
                    userId,
                    resp0.getStatus(), resp0.getDescription() );
        }
        return false;
    }
}
