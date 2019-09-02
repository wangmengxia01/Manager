package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.net.BiClient;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.Mtl2View;
import com.hoolai.ccgames.bifront.vo.baseView;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service( "mtl2Action" )
public class Mtl2Action extends BaseAction {

    private static List< String > financeTableHead = Arrays.asList( "日期",
            "人数", "金币投注总额","牛币投注总额","小牛币投注总额", "总返奖额", "玩法返奖", "彩池返奖", "池子抽水" , "摩天轮收入" );

    private static List< String > rankTableHead = Arrays.asList( "玩家ID", "玩家昵称",
            "总下注额度", "下注获胜总额", "爆彩总额", "总净胜筹码", "盈利率" );
    private static List< String > jackportTableHead = Arrays.asList(  "日期", "彩池中奖人数", "彩池中奖次数", "爆彩总额");
    private static List< String > niubiTableHead = Arrays.asList(  "日期", "牛币下注人数", "牛币下注量", "小牛币下注人数",
            "小牛币下注量");


    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_MTL2 ).forward( req, resp );
    }

    public void getFinancePage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_MTL2_FINANCE ).forward( req, resp );
    }

    public void getRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_MTL2_RANK ).forward( req, resp );
    }
    public void getFishRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_MTL2_FISH_RANK ).forward( req, resp );
    }
    public void getCaichiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_MTL2_CAICHI ).forward( req, resp );
    }
    public void getNiubiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_MTL2_NIUBI ).forward( req, resp );
    }


    private List< Mtl2View.Nb > getNbData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            Mtl2View.Nb val = new Mtl2View.Nb();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.niubiBetPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and bet_nb_gold > 0;", c );
            val.niubiBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum(  bet_nb_gold ), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N';", c ) / 10000;

            val.xiaoniubiBetPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and bet_xnb_gold > 0;", c );


            val.xiaoniubiBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum(  bet_xnb_gold ), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N';", c ) / 1000;
            return val;
        } );
    }

    public void queryNb( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MTL2, getNbData( req, resp ),
                niubiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.niubiBetPeople + "</td>" );
                    pw.write( "<td>" + info.niubiBet + "</td>" );
                    pw.write( "<td>" + info.xiaoniubiBetPeople + "</td>" );
                    pw.write( "<td>" + info.xiaoniubiBet + "</td>" );
                } );
    }

    public void downloadNb( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MTL2, getNbData( req, resp ),
                niubiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.niubiBetPeople + "," );
                    pw.write( info.niubiBet + "," );
                    pw.write( info.xiaoniubiBetPeople  + "," );
                    pw.write( info.xiaoniubiBet + "," );
                } );
    }

    private List< List< Mtl2View.Rank > > getRankData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        int limit = Integer.parseInt( req.getParameter( "limit" ) );
        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > betMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( bet_gold + bet_nb_gold + bet_xnb_gold ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( win_gold ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > jackpotMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( jackpot ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Mtl2View.Rank > sum = new HashMap<>();

            betMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new Mtl2View.Rank() );
                sum.get( k ).totalBet = v;
            } );

            winMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new Mtl2View.Rank() );
                sum.get( k ).totalWin = v;
            } );

            jackpotMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new Mtl2View.Rank() );
                sum.get( k ).jackpot = v;
            } );

            List< Mtl2View.Rank > winList = new ArrayList<>();
            List< Mtl2View.Rank > loseList = new ArrayList<>();
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
                CommandList.GET_MTL2, getRankData( req, resp ),
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
                CommandList.GET_MTL2, getRankData( req, resp ),
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
    public void queryFishRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_MTL2, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.totalWin + "</td>" );
                    pw.write( "<td>" + info.jackpot + "</td>" );
                    pw.write( "<td>" + info.netWin + "</td>" );
                    pw.write( "<td>" + formatRatio( info.winRatio ) + "</td>" );
                }, getFishUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadFishRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_MTL2, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.userId + "," );
                    pw.write( getFishUserName( info.userId ) + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.totalWin + "," );
                    pw.write( info.jackpot + "," );
                    pw.write( info.netWin + "," );
                    pw.write( formatRatio( info.winRatio ) + "," );
                } );
    }



    public static long getTotalRake( long beg, long end, BiClient c ) {
        long totalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( bet_gold + bet_nb_gold + bet_xnb_gold ), 0 ) " +
                " from t_mtl2_bet " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and is_robot = 'N';", c );
        long gamePay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( win_gold ), 0 ) " +
                " from t_mtl2_bet " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and is_robot = 'N';", c );
        long jackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( jackpot ), 0 ) " +
                " from t_mtl2_bet " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and is_robot = 'N';", c );
        return totalBet - gamePay - jackpotPay;
    }



    private List< Mtl2View.Finance > getFinanceData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            Mtl2View.Finance val = new Mtl2View.Finance();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N';", c );
            val.goldBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( bet_gold), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N';", c );
            val.niubiBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum(  bet_nb_gold ), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N';", c );
            val.xiaoNiubiBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( bet_xnb_gold ), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N';", c );
            val.gamePay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( win_gold ), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N';", c );
            val.jackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( jackpot ), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N';", c );
            val.potPump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mtl2_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " ;", c );
            val.totalPay = val.gamePay + val.jackpotPay;
            val.totalRake = val.goldBet + val.niubiBet + val.xiaoNiubiBet - val.totalPay;
            return val;
        } );
    }

    public void queryFinance( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_MTL2, getFinanceData( req, resp ),
                financeTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.goldBet + "</td>" );
                    pw.write( "<td>" + info.niubiBet + "</td>" );
                    pw.write( "<td>" + info.xiaoNiubiBet + "</td>" );
                    pw.write( "<td>" + info.totalPay + "</td>" );
                    pw.write( "<td>" + info.gamePay + "</td>" );
                    pw.write( "<td>" + info.jackpotPay + "</td>" );
                    pw.write( "<td>" + info.potPump + "</td>" );
                    pw.write( "<td>" + info.totalRake + "</td>" );
                } );
    }

    public void downloadFinance( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_MTL2, getFinanceData( req, resp ),
                financeTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.goldBet + "," );
                    pw.write( info.niubiBet + "," );
                    pw.write( info.xiaoNiubiBet + "," );
                    pw.write( info.totalPay + "," );
                    pw.write( info.gamePay + "," );
                    pw.write( info.jackpotPay + "," );
                    pw.write( info.potPump + "," );
                    pw.write( info.totalRake + "," );
                } );
    }


    private List< baseView.Jackport > getJackportData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.Jackport val = new baseView.Jackport();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.jackportPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and jackpot > 0;", c );
            val.jackportCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and jackpot > 0;", c );
            val.jackport = ServiceManager.getSqlService().queryLong( "select sum( jackpot ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            return val;
        } );
    }

    public void queryJackport( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MTL2, getJackportData( req, resp ),
                jackportTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.jackportPeople + "</td>" );
                    pw.write( "<td>" + info.jackportCount + "</td>" );
                    pw.write( "<td>" + info.jackport+ "</td>" );
                } );
    }

    public void downloadJackport( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MTL2, getJackportData( req, resp ),
                jackportTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.jackportPeople+ "," );
                    pw.write( info.jackportCount + "," );
                    pw.write( info.jackport + "," );
                } );
    }

}