package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.User;
import com.hoolai.ccgames.bifront.net.BiClient;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.AuthCollection;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.servlet.GameCollection;
import com.hoolai.ccgames.bifront.util.CaptchaUtil;
import com.hoolai.ccgames.bifront.util.CommonUtil;
import com.hoolai.ccgames.bifront.util.RandUtil;
import com.hoolai.ccgames.bifront.vo.UserInfo;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service( "systemAction" )
public class SystemAction extends BaseAction {

    public void captcha( HttpServletRequest request,
                         HttpServletResponse response ) throws IOException {

        String registerCode = RandUtil
                .getString( "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ", 5 );
        request.getSession()
                .setAttribute( Constants.KEY_LOGIN_CAPTCHA, registerCode );
        ServletOutputStream out = response.getOutputStream();
        CaptchaUtil.getRandGifCode( out, registerCode );
        out.flush();
        out.close();
    }

    public void login( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {

        String userName = req.getParameter( "userName" );
        req.getSession().setAttribute( "userName",userName );
        String passWord = req.getParameter( "passWord" );
        String inputCaptcha = req.getParameter( "captcha" );
        String loginCaptcha = (String) req.getSession().getAttribute( Constants.KEY_LOGIN_CAPTCHA );

        if( CommonUtil.isAnyEmpty( userName, passWord, inputCaptcha, loginCaptcha ) ) {
            logger.error( "{} login param invalid", req.getRemoteAddr() );
            alert( "非法参数", req, resp );
            req.getRequestDispatcher( Constants.URL_LOGIN ).forward( req, resp );
            return;
        }
        if( !inputCaptcha.equalsIgnoreCase( loginCaptcha ) ) {
            logger.debug( "diff {} {}", inputCaptcha, loginCaptcha );
            alert( "验证码错误", req, resp );
            req.getRequestDispatcher( Constants.URL_LOGIN ).forward( req, resp );
            return;
        }

        BiClient c = getBiClient( req.getSession() );
        logger.debug( "=============== {} {}", userName, passWord );
        long uid = ServiceManager.getAdminService().login( userName, passWord, c );
        if( uid == -1L ) {
            popup( "用户名或密码错误", req, resp );
            req.getRequestDispatcher( Constants.URL_LOGIN ).forward( req, resp );
            return;
        }

        UserInfo userInfo = new UserInfo( uid, userName, passWord );
        req.getSession().removeAttribute( Constants.KEY_LOGIN_CAPTCHA );
        req.getSession().setAttribute( Constants.KEY_USER_INFO, userInfo );
        List< String > auth = ServiceManager.getAdminService().getAuth( uid, c );
        req.getSession().setAttribute( Constants.KEY_AUTH, auth );
        if( SummaryAction.userList.contains( userName ))
        {
            req.setAttribute( "teamGroup", "TPG" );
            req.setAttribute( "games", GameCollection.getGames( "TPG" ) );
            req.getRequestDispatcher( Constants.URL_QUDAO ).forward( req, resp );
        }
        else
            req.getRequestDispatcher( Constants.URL_TPG_MAIN ).forward( req, resp );
    }

    public void logout( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getSession().removeAttribute( Constants.KEY_USER_INFO );
        req.getSession().removeAttribute( Constants.KEY_BI_CLIENT );
        resp.sendRedirect( "bi" );
    }

    public void modifyPass( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {

        UserInfo userInfo = (UserInfo) req.getSession().getAttribute( Constants.KEY_USER_INFO );
        String oldPassWord = req.getParameter( "oldPassWord" );
        String newPassWord = req.getParameter( "newPassWord" );

        logger.debug( "modify pass {} {} {}", oldPassWord, newPassWord, userInfo.pass );

        if( CommonUtil
                .isAnyEmpty( userInfo, oldPassWord, newPassWord ) ) {
            logger.error( "{} modifyPass param invalid", req.getRemoteAddr() );
            req.getRequestDispatcher( Constants.URL_MODIFY_PASS ).forward( req, resp );
            return;
        }

        if( !oldPassWord.equals( userInfo.pass ) ) {
            alert( "原密码错误", req, resp );
            req.getRequestDispatcher( Constants.URL_MODIFY_PASS ).forward( req, resp );
            return;
        }

        BiClient c = getBiClient( req.getSession() );
        boolean rv = ServiceManager.getAdminService().modifyPass( userInfo.name, newPassWord, c );
        if( rv ) userInfo.pass = newPassWord;
        alert( rv ? "修改密码成功" : "修改密码失败", req, resp );
        req.getRequestDispatcher( Constants.URL_MODIFY_PASS ).include( req, resp );
    }

    public void listGmUsers( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        BiClient c = getBiClient( req.getSession() );
        User.MessageUserList list = ServiceManager.getAdminService().listGmUsers( c );
        if( list == null ) alert( "没有权限", req, resp );
        else req.setAttribute( "userList", list.getUsersList() );
        req.getRequestDispatcher( Constants.URL_LIST_GM_USERS ).forward( req, resp );
    }

    public void delGmUser( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String userId = req.getParameter( "userId" );
        if( userId == null ) {
            alert( "参数非法", req, resp );
        }
        else {
            BiClient c = getBiClient( req.getSession() );
            boolean rv = ServiceManager.getAdminService().delGmUser( Long.parseLong( userId ), c );
            logger.debug( "rv === {}", rv );
            if( !rv ) alert( "删除用户失败", req, resp );
        }
        listGmUsers( req, resp );
    }

    public void addGmUser( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String name = req.getParameter( "userName" );
        String pass = req.getParameter( "passWord" );
        if( CommonUtil.isAnyEmpty( name, pass ) ) {
            alert( "参数非法", req, resp );
        }
        else {
            BiClient c = getBiClient( req.getSession() );
            boolean rv = ServiceManager.getAdminService().addGmUser( name, pass, c );
            logger.debug( "rv === {}", rv );
            if( rv ) alert( "添加成功", req, resp );
            else alert( "添加用户失败，检查用户是否已经存在", req, resp );
        }
        listGmUsers( req, resp );
    }

    public void getAuth( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String userId = req.getParameter( "userId" );
        if( userId == null ) {
            alert( "参数非法", req, resp );
        }
        else {
            BiClient c = getBiClient( req.getSession() );
            List< String > auths = ServiceManager.getAdminService().getAuth( Long.parseLong( userId ), c );
            if( auths == null ) alert( "未找到", req, resp );
            else {
                req.setAttribute( "userId", userId );
                req.setAttribute( "userAuths", auths );
                req.setAttribute( "authGroups", AuthCollection.getAuthList() );
            }
        }
        req.getRequestDispatcher( Constants.URL_USER_AUTH ).forward( req, resp );
    }

    public void setAuth( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String userId = req.getParameter( "userId" );
        String[] chose = req.getParameterValues( "authCheck" );
        List< String > auths = new ArrayList();
        if( chose == null ) {
            logger.error( "authCheck has no value" );
        }
        else {
            for( String v : chose ) {
                auths.add( v );
            }
        }
        BiClient c = getBiClient( req.getSession() );
        boolean rv = ServiceManager.getAdminService().setAuth( Long.parseLong( userId ), auths, c );
        alert( rv ? "修改成功" : "修改失败", req, resp );
        listGmUsers( req, resp );
    }

    public void listBiServerUsers( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        BiClient c = getBiClient( req.getSession() );
        User.MessageServerUserList biList = ServiceManager.getAdminService().listBiServerUsers( c );
        logger.debug( "============= biList {}", biList );
        if( biList != null ) {
            req.setAttribute( "biList", biList.getUsersList() );
        }
        req.getRequestDispatcher( Constants.URL_LIST_BI_SERVER_USERS ).forward( req, resp );
    }

    public void listGameServerUsers( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        BiClient c = getBiClient( req.getSession() );
        User.MessageServerUserList gameList = ServiceManager.getAdminService().listGameServerUsers( c );
        logger.debug( "============= gameList {}", gameList );
        if( gameList != null ) {
            req.setAttribute( "gameList", gameList.getUsersList() );
        }
        req.getRequestDispatcher( Constants.URL_LIST_GAME_SERVER_USERS ).forward( req, resp );
    }

    public void addBiServerUser( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String name = req.getParameter( "biServerUserName" );
        String pass = req.getParameter( "biServerPassWord" );

        if( CommonUtil.isAnyEmpty( name, pass ) ) {
            alert( "参数非法", req, resp );
        }
        else {
            BiClient c = getBiClient( req.getSession() );
            boolean rv = ServiceManager.getAdminService().addBiServerUser( name, pass, c );
            logger.debug( "rv === {}", rv );
            if( rv ) alert( "添加成功", req, resp );
            else alert( "添加用户失败，检查用户是否已经存在", req, resp );
        }
        listBiServerUsers( req, resp );
    }

    public void delBiServerUser( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String userId = req.getParameter( "userId" );
        if( userId == null ) {
            alert( "参数非法", req, resp );
        }
        else {
            BiClient c = getBiClient( req.getSession() );
            boolean rv = ServiceManager.getAdminService().delBiServerUser( Long.parseLong( userId ), c );
            logger.debug( "rv === {}", rv );
            if( !rv ) alert( "删除用户失败", req, resp );
        }
        listBiServerUsers( req, resp );
    }

    public void addGameServerUser( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String name = req.getParameter( "gameServerUserName" );
        String pass = req.getParameter( "gameServerPassWord" );

        if( CommonUtil.isAnyEmpty( name, pass ) ) {
            alert( "参数非法", req, resp );
        }
        else {
            BiClient c = getBiClient( req.getSession() );
            boolean rv = ServiceManager.getAdminService().addGameServerUser( name, pass, c );
            logger.debug( "rv === {}", rv );
            if( rv ) alert( "添加成功", req, resp );
            else alert( "添加用户失败，检查用户是否已经存在", req, resp );
        }
        listGameServerUsers( req, resp );
    }

    public void delGameServerUser( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String userId = req.getParameter( "userId" );
        if( userId == null ) {
            alert( "参数非法", req, resp );
        }
        else {
            BiClient c = getBiClient( req.getSession() );
            boolean rv = ServiceManager.getAdminService().delGameServerUser( Long.parseLong( userId ), c );
            logger.debug( "rv === {}", rv );
            if( !rv ) alert( "删除用户失败", req, resp );
        }
        listGameServerUsers( req, resp );
    }

}
