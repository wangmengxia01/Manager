package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.ZfbView;
import com.hoolai.ccgames.bifront.vo.baseView;
import com.hoolai.centersdk.sdk.UserSdk;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service( "zfbAction" )
public class ZfbAction extends BaseAction {

    private static List< String > basicTableHead = Arrays.asList( "日期", "总下注","闲家总赢","AI庄下注","AI庄闲赢", "筹码上庄次数", "合庄次数", "上庄券上庄次数", "逃庄抽水", "上庄券支出", "爆彩支出", "红包净营收" );
    private static List< String > rankTableHead = Arrays.asList( "玩家ID", "玩家昵称", "坐庄净胜", "闲家净胜", "爆彩总额", "总净胜筹码", "盈利率" );
    private static List< String > HuiZongTableHead = Arrays.asList( "日期","总下注", "人数", "胡莱莱输赢", "注入彩池", "抽水", "收入");
    private static List< String > robotSumTableHead = Arrays.asList( "日期", "机器人下注总额", "机器人获胜总额", "机器人爆彩总额", "机器人总营收" );
    private static List< String > robotSingleTableHead = Arrays.asList( "机器人ID", "机器人下注额", "机器人获胜额", "机器人爆彩额", "机器人营收" );
    private static List< String > redEnvelopeTableHead = Arrays.asList( "日期", "献花人数", "献花消费额度", "发红包人数", "发红包额度", "系统红包人数", "系统红包额度", "红包收入" );
    private static List< String > shangzhuangTableHead = Arrays.asList(  "日期", "上庄卡使用人数", "上庄卡使用次数", "合庄人数", "合庄次数");

    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_ZFB ).forward( req, resp );
    }

    public void getShangzhuangPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_ZFB_SHANGZHUANG ).forward( req, resp );
    }
    public void getCaichiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_ZFB_CAICHI ).forward( req, resp );
    }
    public void getBasicPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_ZFB_BASIC ).forward( req, resp );
    }

    public void getRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_ZFB_RANK ).forward( req, resp );
    }
    public void getFishRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_ZFB_FISH_RANK ).forward( req, resp );
    }

    public void getRobotPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_ZFB_ROBOT ).forward( req, resp );
    }

    public void getRedEnvelopePage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_ZFB_RED_ENVELOPE ).forward( req, resp );
    }


    private List< ZfbView.shangzhuangka > getshangzhuangData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            ZfbView.shangzhuangka val = new ZfbView.shangzhuangka();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.shangzhuangPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id  ) " +
                    " from t_zfb_shangzhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_use_card ='Y';", c );
            val.shangzhuangCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_zfb_shangzhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_use_card ='Y';", c );
            val.hezhuangPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id  ) " +
                    " from t_zfb_shangzhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_join ='Y';", c );
            val.hezhuangCount = ServiceManager.getSqlService().queryLong( "select count(  distinct inning_id ) " +
                    " from t_zfb_shangzhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_join ='Y';", c );
            return val;
        } );
    }

    public void queryShangzhuang( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_ZFB, getshangzhuangData( req, resp ),
                shangzhuangTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.shangzhuangPeople + "</td>" );
                    pw.write( "<td>" + info.shangzhuangCount + "</td>" );
                    pw.write( "<td>" + info.hezhuangPeople + "</td>" );
                    pw.write( "<td>" + info.hezhuangCount + "</td>" );
                } );
    }

    public void downloadShangzhuang( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_ZFB, getshangzhuangData( req, resp ),
                shangzhuangTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.shangzhuangPeople + "," );
                    pw.write( info.shangzhuangCount + "," );
                    pw.write( info.hezhuangPeople + "," );
                    pw.write( info.hezhuangCount + "," );
                } );
    }

    private List< ZfbView.RobotSum > getRobotSumData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        String robotIds = req.getParameter( "robotIDs" ).trim().replaceAll( "[ \t\n\r,]+", "," );

        return getDataList( req, resp, ( beg, end, c ) -> {
            ZfbView.RobotSum val = new ZfbView.RobotSum();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.bet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id in ( " + robotIds + " );", c );
            val.win = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id in ( " + robotIds + " );", c );
            val.pump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id in ( " + robotIds + " );", c );
            val.jackpot = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id in ( " + robotIds + " );", c );
            val.total = val.win + val.jackpot - val.bet;
            return val;
        } );
    }

    private List< ZfbView.RobotSingle > getRobotSingleData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        String robotIds = req.getParameter( "robotIDs" ).trim().replaceAll( "[ \t\n\r,]+", "," );

        List< ZfbView.RobotSingle > list = new LinkedList();

        for( String robotId : robotIds.split( "," ) ) {
            list.add( getData( req, resp, ( beg, end, c ) -> {
                ZfbView.RobotSingle val = new ZfbView.RobotSingle();
                val.robotId = robotId;
                val.bet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and user_id = " + robotId + ";", c );
                val.win = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and user_id = " + robotId + ";", c );
                val.pump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_pump " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and user_id = " + robotId + ";", c );
                val.jackpot = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and user_id = " + robotId + ";", c );
                val.total = val.win + val.jackpot - val.bet;
                return val;
            } ) );
        }
        return list;
    }

    public void queryRobot( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_ZFB,
                getRobotSumData( req, resp ), robotSumTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.bet + "</td>" );
                    pw.write( "<td>" + info.win + "</td>" );
                    pw.write( "<td>" + info.jackpot + "</td>" );
                    pw.write( "<td>" + info.total + "</td>" );
                }, getRobotSingleData( req, resp ), robotSingleTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.robotId + "</td>" );
                    pw.write( "<td>" + info.bet + "</td>" );
                    pw.write( "<td>" + info.win + "</td>" );
                    pw.write( "<td>" + info.jackpot + "</td>" );
                    pw.write( "<td>" + info.total + "</td>" );
                }, null );
    }

    public void downloadRobot( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_ZFB,
                getRobotSumData( req, resp ), robotSumTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.bet + "," );
                    pw.write( info.win + "," );
                    pw.write( info.jackpot + "," );
                    pw.write( info.total + "," );
                }, getRobotSingleData( req, resp ), robotSingleTableHead, ( info, pw ) -> {
                    pw.write( info.robotId + "," );
                    pw.write( info.bet + "," );
                    pw.write( info.win + "," );
                    pw.write( info.jackpot + "," );
                    pw.write( info.total + "," );
                } );
    }

    private List< ZfbView.Basic > getBasicData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            ZfbView.Basic val = new ZfbView.Basic();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_zfb_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot = 'N';", c );
            val.totalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' ;", c );
            val.totalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' ;", c );
            val.betPumpSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips * ratio ), 0 ) " +
                    " from t_zfb_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and banker_id != 0 and is_robot = 'N';", c );
            val.chipZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_zfb_shangzhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_use_card = 'N';", c );
            val.joinZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_zfb_shangzhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_join = 'Y';", c );
            val.cardZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_zfb_shangzhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_use_card = 'Y';", c );
            val.escapePump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( escape_pump ), 0 ) " +
                    " from t_zfb_xiazhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            val.Pump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            val.xiazhuangPump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips * ratio ), 0 ) " +
                    " from t_zfb_xiazhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_escape = 'N';", c );
            val.cardPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( use_card_change ), 0 ) " +
                    " from t_zfb_xiazhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            long cardPayPumpSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( use_card_change * ratio ), 0 ) " +
                    " from t_zfb_xiazhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and chips > 0;", c );
            val.jackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N';", c );
            long sendFlowerSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_flowers " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            long systemRedSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_free_reward " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_free = 'Y';", c );
            val.exprIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_send_expression " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );

            val.AIzhuangWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' and banker_id = 0;", c );
            val.AIzhuangBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' and banker_id = 0;", c );

            val.rewardIncome = sendFlowerSum - systemRedSum;
            val.netPump = cardPayPumpSum + val.betPumpSum - val.jackpotPay + val.escapePump + val.rewardIncome + val.exprIncome
                        + val.AIzhuangBet - val.AIzhuangWin - val.cardPay;
            return val;
        } );
    }

    public void queryBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_ZFB, getBasicData( req, resp ),
                HuiZongTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + (info.AIzhuangBet - info.AIzhuangWin) + "</td>" );
                    pw.write( "<td>" + (long)(info.Pump * 0.2) + "</td>" );
                    pw.write( "<td>" + (info.Pump ) + "</td>" );
                    pw.write( "<td>" + (info.netPump - (long)(info.Pump * 0.2)) + "</td>" );
                },getBasicData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.totalWin + "</td>" );
                    pw.write( "<td>" + info.AIzhuangBet + "</td>" );
                    pw.write( "<td>" + info.AIzhuangWin + "</td>" );
                    pw.write( "<td>" + info.chipZhuangCnt + "</td>" );
                    pw.write( "<td>" + info.joinZhuangCnt + "</td>" );
                    pw.write( "<td>" + info.cardZhuangCnt + "</td>" );
                    pw.write( "<td>" + info.escapePump + "</td>" );
                    pw.write( "<td>" + info.cardPay + "</td>" );
                    pw.write( "<td>" + info.jackpotPay + "</td>" );
                    pw.write( "<td>" + info.rewardIncome + "</td>" );} ,null );
    }

    public void downloadBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_ZFB, getBasicData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.totalWin + "," );
                    pw.write( info.AIzhuangBet + "," );
                    pw.write( info.AIzhuangWin + "," );
                    pw.write( info.chipZhuangCnt + "," );
                    pw.write( info.joinZhuangCnt + "," );
                    pw.write( info.cardZhuangCnt + "," );
                    pw.write( info.escapePump + "," );
                    pw.write( info.cardPay + "," );
                    pw.write( info.jackpotPay + "," );
                    pw.write( info.rewardIncome + "," );
                    pw.write( info.netPump + "," );
                } );
    }


    private List< List< ZfbView.Rank > > getRankData( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {

        int limit = Integer.parseInt( req.getParameter( "limit" ) );
        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > zhuangWin = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips - escape_pump ) " +
                    " from t_zfb_xiazhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > zhuangBet = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_zfb_shangzhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_use_card = 'N' " +
                    " group by user_id;", c );
            Map< Long, Long > zhuangExpr = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_zfb_send_expression " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > zhuangRed = ServiceManager.getSqlService().queryMapLongLong( "select banker_id, sum( chips ) " +
                    " from t_zfb_free_reward " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_free = 'N' " +
                    " group by banker_id;", c );
            Map< Long, Long > xianWin = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_zfb_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > xianBet = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_zfb_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > jackpotWin = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_zfb_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > sendFlower = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_zfb_flowers " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > recvRed = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_zfb_get_red " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, ZfbView.Rank > sum = new HashMap<>();

            zhuangWin.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new ZfbView.Rank() );
                sum.get( k ).zhuangWin += v;
            } );

            zhuangBet.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new ZfbView.Rank() );
                sum.get( k ).zhuangWin -= v;
                sum.get( k ).totalBet += v;
            } );

            zhuangExpr.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new ZfbView.Rank() );
                sum.get( k ).zhuangWin -= v;
            } );

            zhuangRed.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new ZfbView.Rank() );
                sum.get( k ).zhuangWin -= v;
            } );

            xianWin.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new ZfbView.Rank() );
                sum.get( k ).xianWin += v;
            } );

            xianBet.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new ZfbView.Rank() );
                sum.get( k ).xianWin -= v;
                sum.get( k ).totalBet += v;
            } );

            jackpotWin.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new ZfbView.Rank() );
                sum.get( k ).jackpotWin += v;
            } );

            sendFlower.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new ZfbView.Rank() );
                sum.get( k ).netWin -= v;
            } );

            recvRed.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new ZfbView.Rank() );
                sum.get( k ).netWin += v;
            } );

            List< ZfbView.Rank > winList = new ArrayList<>();
            List< ZfbView.Rank > loseList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.userId = k;
                v.netWin += v.zhuangWin + v.xianWin + v.jackpotWin;
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
                CommandList.GET_ZFB, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.zhuangWin + "</td>" );
                    pw.write( "<td>" + info.xianWin + "</td>" );
                    pw.write( "<td>" + info.jackpotWin + "</td>" );
                    pw.write( "<td>" + info.netWin + "</td>" );
                    pw.write( "<td>" + formatRatio( info.winRatio ) + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_ZFB, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( info.zhuangWin + "," );
                    pw.write( info.xianWin + "," );
                    pw.write( info.jackpotWin + "," );
                    pw.write( info.netWin + "," );
                    pw.write( formatRatio( info.winRatio ) + "," );
                } );
    }

    public void queryFishRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_ZFB, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>_" + UserSdk.getUserName(info.userId )+ "</td>" );
                    pw.write( "<td>" + info.zhuangWin + "</td>" );
                    pw.write( "<td>" + info.xianWin + "</td>" );
                    pw.write( "<td>" + info.jackpotWin + "</td>" );
                    pw.write( "<td>" + info.netWin + "</td>" );
                    pw.write( "<td>" + formatRatio( info.winRatio ) + "</td>" );
                }, null );
    }

    public void downloadFishRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_ZFB, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.userId + "," );
                    pw.write( getFishUserName( info.userId ) + "," );
                    pw.write( info.zhuangWin + "," );
                    pw.write( info.xianWin + "," );
                    pw.write( info.jackpotWin + "," );
                    pw.write( info.netWin + "," );
                    pw.write( formatRatio( info.winRatio ) + "," );
                } );
    }


    private List< ZfbView.RedEnvelope > getRedEnvelopeData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            ZfbView.RedEnvelope val = new ZfbView.RedEnvelope();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.sendFlowers = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_flowers " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.sendFlowersPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_zfb_flowers " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.systemReward = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_free_reward " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_free = 'Y';", c );
            val.systemRewardPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_zfb_free_reward " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_free = 'Y';", c );
            val.playerReward = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_free_reward " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_free = 'N';", c );
            val.playerRewardPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_zfb_free_reward " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_free = 'N';", c );
            val.netPump = val.sendFlowers - val.systemReward;
            return val;
        } );
    }

    public void queryRedEnvelope( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_ZFB, getRedEnvelopeData( req, resp ),
                redEnvelopeTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.sendFlowersPeople + "</td>" );
                    pw.write( "<td>" + info.sendFlowers + "</td>" );
                    pw.write( "<td>" + info.playerRewardPeople + "</td>" );
                    pw.write( "<td>" + info.playerReward + "</td>" );
                    pw.write( "<td>" + info.systemRewardPeople + "</td>" );
                    pw.write( "<td>" + info.systemReward + "</td>" );
                    pw.write( "<td>" + info.netPump + "</td>" );
                } );
    }

    public void downloadRedEnvelope( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_ZFB, getRedEnvelopeData( req, resp ),
                redEnvelopeTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.sendFlowersPeople + "," );
                    pw.write( info.sendFlowers + "," );
                    pw.write( info.playerRewardPeople + "," );
                    pw.write( info.playerReward + "," );
                    pw.write( info.systemRewardPeople + "," );
                    pw.write( info.systemReward + "," );
                    pw.write( info.netPump + "," );
                } );
    }

    private static List< String > jackportTableHead = Arrays.asList(  "日期", "彩池中奖人数", "彩池中奖次数", "爆彩总额");
    private List< baseView.Jackport > getJackportData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.Jackport val = new baseView.Jackport();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.jackportPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_zfb_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.jackportCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_zfb_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.jackport = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_zfb_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            return val;
        } );
    }

    public void queryJackport( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_ZFB, getJackportData( req, resp ),
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
                CommandList.GET_ZFB, getJackportData( req, resp ),
                jackportTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.jackportPeople+ "," );
                    pw.write( info.jackportCount + "," );
                    pw.write( info.jackport + "," );
                } );
    }
}

