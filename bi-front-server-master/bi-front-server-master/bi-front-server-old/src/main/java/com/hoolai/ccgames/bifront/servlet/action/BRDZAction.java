package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.FishView;
import com.hoolai.centersdk.sdk.UserSdk;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service( "brdzAction" )
public class BRDZAction extends BaseAction {

    private static List< String > BRDZTableHead = Arrays.asList( "时间","人数","总投注", "总产出", "牌型投注", "牌型产出","彩金投注","彩金产出","总支出","总抽水","当前池子");
    private static List< String > BEDZrankTableHead = Arrays.asList( "用户id", "用户昵称","总下注","总获取","牌型下注","牌型获取","彩金下注","彩金获取","用户净收入");

    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_BRDZ).forward( req, resp );
    }

    public void getBasicPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_BRDZ_BASIC).forward( req, resp );
    }
    public void getRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_BRDZ_RANK ).forward( req, resp );
    }

    private List< List< FishView.Rank > > getBRDZRankData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        int limit = Integer.parseInt( req.getParameter( "limit" ) );
        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > betMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( bet + caijin_bet ) " +
                    " from t_hundred_dezhou " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > caijinBetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( caijin_bet ) " +
                    " from t_hundred_dezhou " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > paixingBetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( bet ) " +
                    " from t_hundred_dezhou " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );

            Map< Long, String> caijinWinMap = ServiceManager.getSqlService().queryMapLongString( "select user_id, win " +
                    " from t_hundred_dezhou " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, String> paixingWinMap = ServiceManager.getSqlService().queryMapLongString( "select user_id,  win " +
                    " from t_hundred_dezhou " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );

            Map< Long, FishView.Rank > sum = new HashMap<>();

            betMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                {sum.get( k ).totalBet = v;}
                List<String> zongchanchu  = ServiceManager.getSqlService().queryListString( "select win " +
                        " from t_hundred_dezhou " +  " where " + beg + " <= timestamp and timestamp < " + end + " and user_id = " + k +" ;", c );
                Map< Long, Long > chanchuMap = new HashMap<>();
                zongchanchu.forEach( info -> mergeMap( splitMap( info, ";", ":" ), chanchuMap ) );
                sum.get( k ).paixingWin = getLong(chanchuMap , 1);
                sum.get( k ).caijinWin = getLong(chanchuMap , 2);
            } );

            caijinBetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).caijinBet = v;
            } );
            paixingBetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).paixingBet = v;
            } );

            List< FishView.Rank > winList = new ArrayList<>();
            List< FishView.Rank > loseList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.userId = k;
                v.user_name = UserSdk.getUserName(k);
                v.totalWin = v.caijinWin + v.paixingWin;
                v.netWin = v.totalWin - v.totalBet  ;
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

    public void queryBRDZRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_FISH_MB, getBRDZRankData( req, resp ),
                BEDZrankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" + info.user_name + "</td>" );
                    pw.write( "<td>" + info.totalBet / 10000 + "万</td>" );
                    pw.write( "<td>" + info.totalWin / 10000+ "万</td>" );
                    pw.write( "<td>" + info.paixingBet / 10000+ "万</td>" );
                    pw.write( "<td>" + info.paixingWin / 10000+ "万</td>" );
                    pw.write( "<td>" + info.caijinBet / 10000+ "万</td>" );
                    pw.write( "<td>" + info.caijinWin / 10000+ "万</td>" );
                    pw.write( "<td>" + info.netWin / 10000+ "万</td>" );
                }, null );
    }

    private List< FishView.BRDZ > getBRDZData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.BRDZ val = new FishView.BRDZ();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.people = ServiceManager.getSqlService().queryLong( "select count(distinct user_id) from t_hundred_dezhou " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            val.chizi = ServiceManager.getSqlService().queryLong( "select pool from t_hundred_dezhou " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " order by id desc limit 1 ;", c );
            val.choushuilv = ServiceManager.getSqlService().queryLong( "select other_info from t_hundred_dezhou " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " order by id desc limit 1 ;", c );
            val.zongtouzhu = ServiceManager.getSqlService().queryLong( "select sum(bet + caijin_bet) from t_hundred_dezhou " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            val.caijintouzhu = ServiceManager.getSqlService().queryLong( "select sum(caijin_bet) from t_hundred_dezhou " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            val.paixingtouzhu = ServiceManager.getSqlService().queryLong( "select sum(bet) from t_hundred_dezhou " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            List<String> zongchanchu  = ServiceManager.getSqlService().queryListString( "select win " +
                    " from t_hundred_dezhou " +  " where " + beg + " <= timestamp and timestamp < " + end + "  ;", c );
            Map< Long, Long > chanchuMap = new HashMap<>();
            zongchanchu.forEach( info -> mergeMap( splitMap( info, ";", ":" ), chanchuMap ) );
            val.paixingchanchu = getLong(chanchuMap , 1);
            val.caijinchanchu = getLong(chanchuMap , 2);
            val.zongchanchu = val.paixingchanchu + val.caijinchanchu;
            val.choushui = val.zongtouzhu - val.zongchanchu;

            return val;

        } );
    }

    public void queryBRDZ( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getBRDZData( req, resp ),
                BRDZTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.zongtouzhu / 10000 + "万</td>" );
                    pw.write( "<td>" + info.zongchanchu / 10000+ "万</td>" );
                    pw.write( "<td>" + info.paixingtouzhu / 10000+ "万</td>" );
                    pw.write( "<td>" + info.paixingchanchu / 10000+ "万</td>" );
                    pw.write( "<td>" + info.caijintouzhu/ 10000 + "万</td>" );
                    pw.write( "<td>" + info.caijinchanchu / 10000+ "万</td>" );
                    pw.write( "<td>" + info.choushui /10000 + "万</td>" );
                    pw.write( "<td>" + ( info.choushui + info.zongchanchu * 0.05  ) * 1.0d / 10000 + "万</td>" );
                    pw.write( "<td>" + info.chizi /10000 + "万</td>" );
                },null );
    }
}