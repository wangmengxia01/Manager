package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.*;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.PptView;
import com.hoolai.ccgames.bifront.vo.baseView;
import com.hoolai.centersdk.sdk.UserSdk;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service( "pptAction" )
public class PptAction extends BaseAction {

    private static List< String > basicTableHead = Arrays.asList( "日期","总人数", "总下注","下注返奖","彩池返奖", "时时彩收入", "跑跑堂收入" );
    private static List< String > rankTableHead = Arrays.asList( "玩家ID", "玩家昵称", "总下注", "跑跑堂下注", "跑跑堂获胜", "爆彩总额", "时时彩下注", "时时彩获胜", "总净胜", "盈利率" );
    private static List< String > sscBasicTableHead = Arrays.asList( "日期",
            "总人数", "时时彩人数", "总下注", "时时彩总返奖", "时时彩收入" );
    private static List< String > xiazaiTableHead = Arrays.asList( "时间","用户id","名字","输赢", "充值","接收","赠送","使用","掉落", "发炮", "获得","幸运获得","放入幸运","放入抽水");

    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PPT ).forward( req, resp );
    }

    public void getBasicPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PPT_BASIC ).forward( req, resp );
    }

    public void getRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PPT_RANK ).forward( req, resp );
    }

    public void getSscBasicPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PPT_SSC_BASIC ).forward( req, resp );
    }
    public void getCaichiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PPT_SSC_BASIC ).forward( req, resp );
    }

    private List< PptView.Basic > getBasicData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            PptView.Basic val = new PptView.Basic();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.totalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_ppt_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.totalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_ppt_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.jackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_ppt_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            long sscBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_pptssc_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            long sscWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_pptssc_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.zongPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                                 " from t_ppt_bet " +
                                 " where " + beg + " <= timestamp and timestamp < " + end + ";", c )
                            +
                             ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                                 " from t_pptssc_bet " +
                                 " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.shishicai = sscBet - sscWin ;
            val.winPump = val.totalBet - val.totalWin ;
            return val;
        } );
    }

    public void queryBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PPT, getBasicData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zongPeople + "</td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.totalWin + "</td>" );
                    pw.write( "<td>" + info.jackpotPay + "</td>" );
                    pw.write( "<td>" + info.shishicai + "</td>" );
                    pw.write( "<td>" + info.winPump + "</td>" );
                } );
    }

    public void downloadBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PPT, getBasicData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zongPeople + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.totalWin + "," );
                    pw.write( info.jackpotPay + "," );
                    pw.write( info.shishicai+ "," );
                    pw.write( info.winPump + "," );
                } );
    }


    private List< List< PptView.Rank > > getRankData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        int limit = Integer.parseInt( req.getParameter( "limit" ) );
        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > betMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_ppt_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_ppt_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > jackpotMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_ppt_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > sscBetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_pptssc_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > sscWinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_pptssc_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );

            Map< Long, PptView.Rank > sum = new HashMap<>();

            betMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new PptView.Rank() );
                sum.get( k ).pptBet = v;
            } );

            winMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new PptView.Rank() );
                sum.get( k ).pptWin = v;
            } );

            jackpotMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new PptView.Rank() );
                sum.get( k ).jackpot = v;
            } );

            sscBetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new PptView.Rank() );
                sum.get( k ).sscBet = v;
            } );

            sscWinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new PptView.Rank() );
                sum.get( k ).sscWin = v;
            } );

            List< PptView.Rank > winList = new ArrayList<>();
            List< PptView.Rank > loseList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.userId = k;
                v.userName = UserSdk.getUserName(k);
                v.totalBet = v.pptBet + v.sscBet;
                v.netWin = v.pptWin + v.jackpot + v.sscWin - v.totalBet;
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
                CommandList.GET_PPT, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" + info.userName + "</td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.pptBet + "</td>" );
                    pw.write( "<td>" + info.pptWin + "</td>" );
                    pw.write( "<td>" + info.jackpot + "</td>" );
                    pw.write( "<td>" + info.sscBet + "</td>" );
                    pw.write( "<td>" + info.sscWin + "</td>" );
                    pw.write( "<td>" + info.netWin + "</td>" );
                    pw.write( "<td>" + formatRatio( info.winRatio ) + "</td>" );
                }, null );
    }

    public void downloadRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_PPT, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.userId + "," );
                    pw.write(UserSdk.getUserName( info.userId ) + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.pptBet + "," );
                    pw.write( info.pptWin + "," );
                    pw.write( info.jackpot + "," );
                    pw.write( info.sscBet + "," );
                    pw.write( info.sscWin + "," );
                    pw.write( info.netWin + "," );
                    pw.write( formatRatio( info.winRatio ) + "," );
                } );
    }


    private List < PptView.xiazai >  getxiazaiData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {

            List<PptView.xiazai> a = new LinkedList<>();

            Map< Long, Long > betMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and level >= 103 group by user_id;", c );
            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and level >= 103  group by user_id;", c );

            Map< Long, PptView.Rank > sum = new HashMap<>();

            betMap.forEach( ( k , v ) -> {
                PptView.xiazai val = new PptView.xiazai();
                val.begin = TimeUtil.ymdFormat().format( beg );
                val.paiment = v;
                val.user_id = k;
                if (winMap.get(k) == null)
                    return;
                val.get = winMap.get(k);
                       long win = val.get - val.paiment;
                       if ( Math.abs( win ) >= 1000000 )
                       {
                           val.shuying = win;
                           val.chongzhi = ServiceManager.getSqlService().queryLong( "select coalesce( sum( money2+money3 ), 0 ) " +
                                   " from t_pay " +
                                   " where " + beg + " <= timestamp and timestamp < " + end + " and user_id = " + k + ";", c );
                           val.choushui = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                                   " from t_fish_paiment " +
                                   " where " + beg + " <= timestamp and timestamp < " + end + " and user_id = " + k + " and other_info = 5;", c );
                           val.fangruxingyun = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                                   " from t_fish_paiment " +
                                   " where " + beg + " <= timestamp and timestamp < " + end + " and user_id = " + k + " and other_info = 2;", c );
                           val.xingyun =ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                                   " from t_fish_get " +
                                   " where " + beg + " <= timestamp and timestamp < " + end + " and user_id = " + k + " and other_info = 2;", c )
                                   - val.fangruxingyun;
                           val.diaoluo  = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                                   " from t_fish_item " +
                                   " where " + beg + " <= timestamp and timestamp < " + end + " and user_id = " + k + " and item_id = 20017 and item_count > 0 ;", c );
                           val.shiyong  = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                                   " from t_fish_item " +
                                   " where " + beg + " <= timestamp and timestamp < " + end + " and user_id = " + k + " and item_id = 20017 and item_count < 0 ;", c );

                           val.jieshou  = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                                   " from t_important_props " +
                                   " where " + beg + " <= timestamp and timestamp < " + end + " and get_user_id = " + k + " and item_id = 20017  ;", c );
                           val.zengsong  = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                                   " from t_important_props " +
                                   " where " + beg + " <= timestamp and timestamp < " + end + " and send_user_id = " + k + " and item_id = 20017  ;", c );
                           a.add(val);
                       }
            });
//private static List< String > xiazaiTableHead = Arrays.asList( "用户id","输赢", "充值","接收","赠送","使用","掉落", "paiment", "get","幸运","抽水");

            return a;
        } );
    }


    public void queryxiazai( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
//"用户id","输赢", "充值","接收","赠送","使用","掉落", "paiment", "get","幸运","抽水");
        queryList( req, resp,
                CommandList.GET_PPT, getxiazaiData( req, resp ),
                xiazaiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.user_id + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.user_id + "'></td>" );
                    pw.write( "<td>" + info.shuying + "</td>" );
                    pw.write( "<td>" + com.hoolai.ccgames.bi.protocol.Currency.format(info.chongzhi) + "</td>" );
                    pw.write( "<td>" + info.jieshou + "</td>" );
                    pw.write( "<td>" + info.zengsong + "</td>" );
                    pw.write( "<td>" + info.shiyong + "</td>" );
                    pw.write( "<td>" + info.diaoluo + "</td>" );
                    pw.write( "<td>" + info.paiment + "</td>" );
                    pw.write( "<td>" + info.get + "</td>" );
                    pw.write( "<td>" + info.xingyun + "</td>" );
                    pw.write( "<td>" + info.fangruxingyun + "</td>" );
                    pw.write( "<td>" + info.choushui + "</td>" );
                },getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadxiazai( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
    CommandList.GET_PPT, getxiazaiData( req, resp ),
    xiazaiTableHead, ( info, pw ) -> {
        pw.write( info.begin + "," );
        pw.write( info.user_id + "," );
        pw.write(  getFishUserName(info.user_id )+ "," );
        pw.write( info.shuying + "," );
        pw.write( com.hoolai.ccgames.bi.protocol.Currency.format(info.chongzhi) + "," );
        pw.write( info.jieshou + "," );
        pw.write( info.zengsong + "," );
        pw.write( info.shiyong + "," );
        pw.write( info.diaoluo + "," );
        pw.write( info.paiment + "," );
        pw.write( info.get + "," );
        pw.write( info.xingyun + "," );
        pw.write( info.choushui + "," );
    } );
}





    private List< PptView.SscBasic > getSscBasicData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            PptView.SscBasic val = new PptView.SscBasic();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_pptssc_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.sumInnings = ServiceManager.getSqlService().queryLong( "select coalesce( sum( inning_count ), 0 ) " +
                    " from ( select count( distinct inning_id ) as inning_count " +
                    "        from ( select user_id, inning_id " +
                    "               from t_pptssc_bet " +
                    "               where " + beg + " <= timestamp and timestamp < " + end + " ) as A " +
                    "        group by user_id ) as B;", c );
            val.totalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_pptssc_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.totalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_pptssc_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.zongPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_ppt_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c ) + val.people;
            val.winPump = val.totalBet - val.totalWin;
            return val;
        } );
    }

    public void querySscBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PPT, getSscBasicData( req, resp ),
                sscBasicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zongPeople + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.totalWin + "</td>" );
                    pw.write( "<td>" + info.winPump + "</td>" );
                } );
    }

    public void downloadSscBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PPT, getSscBasicData( req, resp ),
                sscBasicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.zongPeople + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.totalWin + "," );
                    pw.write( info.winPump + "," );
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
                    " from t_ppt_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.jackportCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_ppt_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.jackport = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_ppt_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            return val;
        } );
    }

    public void queryJackport( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PPT, getJackportData( req, resp ),
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
                CommandList.GET_PPT, getJackportData( req, resp ),
                jackportTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.jackportPeople+ "," );
                    pw.write( info.jackportCount + "," );
                    pw.write( info.jackport + "," );
                } );
    }


}