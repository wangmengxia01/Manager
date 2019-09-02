package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.YellowView;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */

@Service( "yellowAction" )
public class YellowAction extends BaseAction {


    private static List< String > YelloeNewTableHead = Arrays.asList( "日期", "领取人数", "元宝支出", "体力支出" );
    private static List< String > YellowDayTableHead = Arrays.asList( "日期", "领取人数", "元宝支出", "体力支出" );
    private static List< String > YellowLevelTableHead = Arrays.asList( "日期", "领取人数", "体力支出", "青铜戒指支出" );



    public void getYellowPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_YELLOW ).forward( req, resp );
    }

    private List< YellowView.YellowNew > getYellowNewData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            YellowView.YellowNew val = new YellowView.YellowNew();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 4;", c );
            val.Gold = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 4;", c );
            val.HP = ServiceManager.getSqlService().queryLong( "select coalesce( sum( hp ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 4;", c );
            return val;
        } );
    }

    private List< YellowView.YellowDay > getYellowDayData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            YellowView.YellowDay val = new YellowView.YellowDay();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 5;", c );
            val.Gold = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 5;", c );
            val.HP = ServiceManager.getSqlService().queryLong( "select coalesce(  sum( hp ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 5;", c );
            return val;
        } );
    }
    private List< YellowView.YellowLevel > getYellowLevelData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            YellowView.YellowLevel val = new YellowView.YellowLevel();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 6;", c );
            val.qingtong = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 6 and item_id = 10501;", c );
            val.HP = ServiceManager.getSqlService().queryLong( "select coalesce( sum( hp ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 6;", c );
            return val;
        } );
    }

    public void queryYellow( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_YELLOW_GIFT, getYellowNewData( req, resp ),
                YelloeNewTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.Gold + "</td>" );
                    pw.write( "<td>" + info.HP + "</td>" );
                } ,getYellowDayData( req, resp ),
                YellowDayTableHead, ( info, pw ) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.people + "</td>");
                    pw.write("<td>" + info.Gold + "</td>");
                    pw.write("<td>" + info.HP + "</td>");
                },getYellowLevelData( req, resp ),
                YellowLevelTableHead, ( info, pw ) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.people + "</td>");
                    pw.write("<td>" + info.HP + "</td>");
                    pw.write("<td>" + info.qingtong + "</td>");
                },null);
    }

    public void downloadYellow( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_YELLOW_GIFT, getYellowNewData( req, resp ),
                YelloeNewTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.Gold + "," );
                    pw.write( info.HP + "," );
                },getYellowDayData( req, resp ),
                YellowDayTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.Gold + "," );
                    pw.write( info.HP + "," );
                },getYellowLevelData( req, resp ),
                YellowDayTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.HP + "," );
                    pw.write( info.qingtong + "," );
                } );
    }

}
