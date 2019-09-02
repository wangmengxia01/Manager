package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.starter.Global;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.MpView;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service( "mpAction" )
public class MasterPointAction extends BaseAction {

    private static List< String > costTableHead = Arrays.asList( "时间",
            "玩家ID", "玩家昵称", "道具名称", "道具数量", "消耗大师积分", "地址" );

    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_MP ).forward( req, resp );
    }

    public void getCostPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_MP_COST ).forward( req, resp );
    }

    private List< MpView.Cost > getCostData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            List< MpView.Cost > list = new LinkedList<>();
            Map< Long, String > payMap = ServiceManager.getSqlService().queryMapLongString( "select timestamp, user_id, item_id, item_count, master_point, addr " +
                    " from t_mp_cost " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            payMap.forEach( ( k, v ) -> {
                MpView.Cost val = new MpView.Cost();
                val.begin = TimeUtil.ymdhmsFormat().format( k );
                String[] params = v.split( "\n", 5 );
                val.userId = Long.parseLong( params[0] );
                val.itemId = Long.parseLong( params[1] );
                val.itemCount = Long.parseLong( params[2] );
                val.costMp = Long.parseLong( params[3] );
                val.addr = params[4];
                list.add( val );
            } );
            return list;
        } );
    }

    public void queryCost( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MP_COST, getCostData( req, resp ),
                costTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + Global.itemRegistry.getOrId( info.itemId ) + "</td>" );
                    pw.write( "<td>" + info.itemCount + "</td>" );
                    pw.write( "<td>" + info.costMp + "</td>" );
                    pw.write( "<td>" + info.addr + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadCost( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MP_COST, getCostData( req, resp ),
                costTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( Global.itemRegistry.getOrId( info.itemId ) + "," );
                    pw.write( info.itemCount + "," );
                    pw.write( info.costMp + "," );
                    pw.write( info.addr + "," );
                } );
    }



}