package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.starter.Global;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.FruitView;
import com.hoolai.ccgames.bifront.vo.baseView;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service( "fruitAction" )
public class FruitAction extends BaseAction {

    private static List< String > basicTableHead = Arrays.asList( "日期",
            "总人数", "总下注","下注返奖", "彩池返奖","比倍收入", "水果大满贯收入" );
    private static List< String > rankTableHead = Arrays.asList( "玩家ID",
            "玩家昵称", "总下注", "水果机下注", "水果机获胜", "爆彩总额", "比倍下注", "比倍获胜", "比倍道具获得", "总净胜", "盈利率" );
    private static List< String > caibeiBasicTableHead = Arrays.asList( "日期","总人数","比倍人数",
             "比倍下注", "比倍返奖", "比倍收入" );
    private static List< String > caibeiItemTableHead = Arrays.asList( "日期",
            "道具ID", "道具名称", "道具数量" );

    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FRUIT ).forward( req, resp );
    }

    public void getBasicPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FRUIT_BASIC ).forward( req, resp );
    }

    public void getRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FRUIT_RANK ).forward( req, resp );
    }

    public void getCaibeiBasicPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FRUIT_CAIBEI_BASIC ).forward( req, resp );
    }

    public void getCaibeiItemPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FRUIT_CAIBEI_ITEM ).forward( req, resp );
    }
    public void getCaichiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FRUIT_CAICHI ).forward( req, resp );
    }

    private List< FruitView.Basic > getBasicData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FruitView.Basic val = new FruitView.Basic();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.potPump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_fruit_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.totalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_fruit_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.totalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_fruit_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.jackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_fruit_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.zongPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_fruit_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            long bibeiWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_caibei_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            long bibeiBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_caibei_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.bibei = bibeiBet - bibeiWin;
            val.winPump = val.bibei + val.totalBet - val.totalWin - val.jackpotPay;
            return val;
        } );
    }

    public void queryBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FRUIT, getBasicData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zongPeople + "</td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.totalWin + "</td>" );
                    pw.write( "<td>" + info.jackpotPay + "</td>" );
                    pw.write( "<td>" + info.bibei+ "</td>" );
                    pw.write( "<td>" + info.winPump + "</td>" );
                } );
    }

    public void downloadBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_FRUIT, getBasicData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zongPeople + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.totalWin + "," );
                    pw.write( info.jackpotPay + "," );
                    pw.write( info.bibei + "," );
                    pw.write( info.winPump + "," );
                } );
    }


    private List< List< FruitView.Rank > > getRankData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        int limit = Integer.parseInt( req.getParameter( "limit" ) );
        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > betMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_fruit_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_fruit_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > jackpotMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_fruit_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > caibeiBetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_caibei_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > caibeiWinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_caibei_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > caibeiItemMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( total_worth ) " +
                    " from t_caibei_reward " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );

            Map< Long, FruitView.Rank > sum = new HashMap<>();

            betMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FruitView.Rank() );
                sum.get( k ).fruitBet = v;
            } );

            winMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FruitView.Rank() );
                sum.get( k ).fruitWin = v;
            } );

            jackpotMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FruitView.Rank() );
                sum.get( k ).jackpot = v;
            } );

            caibeiBetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FruitView.Rank() );
                sum.get( k ).caibeiBet = v;
            } );

            caibeiWinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FruitView.Rank() );
                sum.get( k ).caibeiWin = v;
            } );

            caibeiItemMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FruitView.Rank() );
                sum.get( k ).caibeiItem = v;
            } );

            List< FruitView.Rank > winList = new ArrayList<>();
            List< FruitView.Rank > loseList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.userId = k;
                v.totalBet = v.fruitBet + v.caibeiBet;
                v.netWin = v.fruitWin + v.jackpot + v.caibeiWin + v.caibeiItem - v.totalBet;
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
                CommandList.GET_FRUIT, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.fruitBet + "</td>" );
                    pw.write( "<td>" + info.fruitWin + "</td>" );
                    pw.write( "<td>" + info.jackpot + "</td>" );
                    pw.write( "<td>" + info.caibeiBet + "</td>" );
                    pw.write( "<td>" + info.caibeiWin + "</td>" );
                    pw.write( "<td>" + info.caibeiItem + "</td>" );
                    pw.write( "<td>" + info.netWin + "</td>" );
                    pw.write( "<td>" + formatRatio( info.winRatio ) + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_FRUIT, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.fruitBet + "," );
                    pw.write( info.fruitWin + "," );
                    pw.write( info.jackpot + "," );
                    pw.write( info.caibeiBet + "," );
                    pw.write( info.caibeiWin + "," );
                    pw.write( info.caibeiItem + "," );
                    pw.write( info.netWin + "," );
                    pw.write( formatRatio( info.winRatio ) + "," );
                } );
    }

    private List< FruitView.CaibeiBasic > getCaibeiBasicData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FruitView.CaibeiBasic val = new FruitView.CaibeiBasic();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.potPump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_caibei_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.itemPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( total_worth ), 0 ) " +
                    " from t_caibei_reward " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );

            val.bibeiPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_caibei_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.zongPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_fruit_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.totalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_caibei_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.totalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_caibei_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );

            val.winPump = val.totalBet - val.totalWin - val.itemPay;
            return val;
        } );
    }

    public void queryCaibeiBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FRUIT, getCaibeiBasicData( req, resp ),
                caibeiBasicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zongPeople + "</td>" );
                    pw.write( "<td>" + info.bibeiPeople + "</td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.totalWin + "</td>" );
                    pw.write( "<td>" + info.winPump + "</td>" );
                } );
    }

    public void downloadCaibeiBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FRUIT, getCaibeiBasicData( req, resp ),
                caibeiBasicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zongPeople + "," );
                    pw.write( info.bibeiPeople + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.totalWin + "," );
                    pw.write( info.winPump + "," );
                } );
    }

    private List< FruitView.CaibeiItem > getCaibeiItemData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList2( req, resp, ( beg, end, c ) -> {
            List< FruitView.CaibeiItem > chunk = new LinkedList<>();
            Map< Long, Long > itemMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                    " from t_caibei_reward " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by item_id;", c );

            itemMap.forEach( ( k, v ) -> {
                FruitView.CaibeiItem val = new FruitView.CaibeiItem();
                val.begin = TimeUtil.ymdFormat().format( beg );
                val.end = TimeUtil.ymdFormat().format( end );
                val.itemId = k;
                val.itemCount = v;
                val.itemName = Global.itemRegistry.getOrId( k );
                chunk.add( val );
            } );

            return chunk;
        } );
    }

    public void queryCaibeiItem( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FRUIT, getCaibeiItemData( req, resp ),
                caibeiItemTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.itemId + "</td>" );
                    pw.write( "<td>" + info.itemName + "</td>" );
                    pw.write( "<td>" + info.itemCount + "</td>" );
                } );
    }

    public void downloadCaibeiItem( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FRUIT, getCaibeiItemData( req, resp ),
                caibeiItemTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.itemId + "," );
                    pw.write( info.itemName + "," );
                    pw.write( info.itemCount + "," );
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
                    " from t_fruit_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.jackportCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_fruit_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.jackport = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_fruit_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            return val;
        } );
    }

    public void queryJackport( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FRUIT, getJackportData( req, resp ),
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
                CommandList.GET_FRUIT, getJackportData( req, resp ),
                jackportTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.jackportPeople+ "," );
                    pw.write( info.jackportCount + "," );
                    pw.write( info.jackport + "," );
                } );
    }

}