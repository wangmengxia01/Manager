package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.MtlView;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service( "mtlAction" )
public class MtlAction extends BaseAction {

    private static List< String > basicTableHead = Arrays.asList( "日期", "池子抽水", "总下注", "玩家输赢抽水", "爆彩支出" );
    private static List< String > rankTableHead = Arrays.asList( "玩家ID", "玩家昵称", "总下注额度", "下注获胜总额", "爆彩总额", "总净胜筹码", "盈利率" );

    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_MTL ).forward( req, resp );
    }

    public void getBasicPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_MTL_BASIC ).forward( req, resp );
    }

    public void getRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_MTL_RANK ).forward( req, resp );
    }

    private List< MtlView.Basic > getBasicData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MtlView.Basic val = new MtlView.Basic();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.totalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_mtl_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            long totalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_mtl_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            val.potPump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_mtl_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            val.jackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_mtl_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            val.winPump = val.totalBet - totalWin - val.jackpotPay;
            return val;
        } );
    }

    public void queryBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MTL_BASIC, getBasicData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.potPump + "</td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.winPump + "</td>" );
                    pw.write( "<td>" + info.jackpotPay + "</td>" );
                } );
    }

    public void downloadBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MTL_BASIC, getBasicData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.potPump + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.winPump + "," );
                    pw.write( info.jackpotPay + "," );
                } );
    }


    private List< List< MtlView.Rank > > getRankData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        int limit = Integer.parseInt( req.getParameter( "limit" ) );
        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > betMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_mtl_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_mtl_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > jackpotMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_mtl_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, MtlView.Rank > sum = new HashMap<>();

            betMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new MtlView.Rank() );
                sum.get( k ).totalBet = v;
            } );

            winMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new MtlView.Rank() );
                sum.get( k ).totalWin = v;
            } );

            jackpotMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new MtlView.Rank() );
                sum.get( k ).jackpot = v;
            } );

            List< MtlView.Rank > winList = new ArrayList<>();
            List< MtlView.Rank > loseList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.userId = k;
                v.netWin = v.totalWin + v.jackpot - v.totalBet;
                v.winRatio = v.netWin / (double) ( v.totalBet );
                if( v.netWin >= limit ) winList.add( v );
                else if( v.netWin <= -limit ) loseList.add( v );
            } );

            Collections.sort( winList, ( o1, o2 ) -> {
                if( o1.netWin > o2.netWin ) return -1;
                if( o1.netWin < o2.netWin ) return 1;
                if( o1.totalBet < o2.totalBet ) return -1;
                if( o1.totalBet > o2.totalBet ) return 1;
                return 0;
            } );

            Collections.sort( loseList, ( o1, o2 ) -> {
                if( o1.netWin < o2.netWin ) return -1;
                if( o1.netWin > o2.netWin ) return 1;
                if( o1.totalBet < o2.totalBet ) return -1;
                if( o1.totalBet > o2.totalBet ) return 1;
                return 0;
            } );

            return Arrays.asList( winList, loseList );
        } );
    }

    public void queryRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_MTL_RANK, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.totalWin + "</td>" );
                    pw.write( "<td>" + info.jackpot + "</td>" );
                    pw.write( "<td>" + info.netWin + "</td>" );
                    pw.write( "<td>" + formatRatio( info.winRatio ) + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_MTL_RANK, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.totalWin + "," );
                    pw.write( info.jackpot + "," );
                    pw.write( info.netWin + "," );
                    pw.write( formatRatio( info.winRatio ) + "," );
                } );
    }
}