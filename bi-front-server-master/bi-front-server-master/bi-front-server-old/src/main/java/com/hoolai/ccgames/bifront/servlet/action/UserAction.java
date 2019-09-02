package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bi.protocol.Currency;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.servlet.GameCollection;
import com.hoolai.ccgames.bifront.servlet.TeamCollection;
import com.hoolai.ccgames.bifront.util.CommonUtil;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.SummaryView;
import com.hoolai.ccgames.bifront.vo.UserView;
import com.hoolai.ccgames.skeleton.utils.CollectionUtil;
import com.hoolai.centersdk.item.ItemInfo;
import com.hoolai.centersdk.item.ItemManager;
import com.hoolai.centersdk.sdk.ItemSdk;
import com.hoolai.centersdk.sdk.UserSdk;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service( "userAction" )
public class UserAction extends BaseAction {

    private static List< Integer > payDays = Arrays.asList( 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,30,60,90,120,180,270,360 );
    private static List< String > installPayDaysTableHead;
    private static List< String > LTVTableHead;
    private static List< Integer > retentionDays = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 14, 15, 30, 60, 90, 120, 180, 270, 360 );
    private static List< String > retentionTableHead;

    private static List< Integer > PayUserAfterPayDays = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 14, 15, 30, 45, 60 , 120);
    private static List< String > PayUserAfterPayTableHead;

    private static List< Integer > FuFeiLvDays = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 14, 15, 30 );
    private static List< String > FuFeiLvTableHead;

    private static List< Integer > minutesOnline = Arrays.asList( 0, 1, 2, 3, 4, 7, 11, 16, 21, 26, 31, Integer.MAX_VALUE );
    private static List< String > onlineTableHead = new ArrayList<>();
    private static List< Long > millisOnline = new ArrayList<>();

    private static List< String > UserWinLoseTableHead = Arrays.asList( "时间","玩家ID", "玩家昵称", "老德州输赢", "新德州输赢"
            , "血流输赢", "血战输赢", "二麻输赢", "斗地主输赢", "中发白输赢", "摩天轮输赢", "汪汪运动会输赢", "跑跑堂输赢", "水果大满贯输赢",
            "用户总盈利" );

    private static List< String > payDetailTableHead = Arrays.asList( "时间",
            "玩家ID", "玩家昵称", "当日付费","注册时间","总充值" );

    private static List< String > UserHuizongTableHead = Arrays.asList( "时间",
            "当日活跃人数", "携带总金币", "金弹头总携带","银弹头总携带" );

    private static List< String > userDanTouTableHead1 = Arrays.asList( "时间",
            "玩家ID", "玩家昵称", "线上充值" ,"中发白" ,"摩天轮" ,"捕鱼" ,"退出金币", "掉落金", "接收金", "赠送金", "使用金", "掉落银", "接收银", "赠送银", "使用银" );

    private static List< String > userGameWin = Arrays.asList( "时间",
            "玩家ID", "玩家昵称", "充值金币" ,"捕鱼输赢","中发白输赢", "摩天轮输赢");

    private static List< String > userDanTouTableHead2 = Arrays.asList( "时间",
            "玩家ID", "玩家昵称", "掉落金", "银", "铜", "接收金", "银", "铜", "赠送金", "银", "铜", "使用金", "银", "铜" , "币商(收)金" , "银" , "铜" , "币商(售)金" , "银" , "铜" , "线上充值" );

    private static List< String > DAUTableHead = Arrays.asList( "时间","渠道", "应用", "MAU", "DAU"
            , "DAU/MAU活跃比", "平均使用时长", "平均启动次数" );
    private static List< String > phoneNumTableHead = Arrays.asList( "用户id","用户名", "手机号");
    private static List< String > UserRMBTableHead = Arrays.asList( "区间","用户id","用户名", "赠送弹头", "接收弹头", "初始弹头量", "最后弹头量", "充值金额", "总盈利");

    private static List< String > UserPayRankTableHead = Arrays.asList( "日期","用户id","用户名","注册时间", "最近登陆","当日输赢","未登录天数","最近充值","未充值天数","充值总金额","中发白总输赢","摩天轮总输赢","生涯总输赢");

    private static List< Integer > shichangDays = Arrays.asList( 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,60,90,120 );
    private static List< String > shichangTableHead;

    static {
        shichangTableHead = new ArrayList< String >();
        shichangTableHead.add( "日期" );
        shichangTableHead.add( "游戏" );
        shichangTableHead.add( "渠道号" );
        shichangTableHead.add( "渠道名" );
        shichangTableHead.add( "当日付费人数" );
        shichangTableHead.add( "截止今日收入" );
        shichangTableHead.add( "当日新增" );
        shichangTableHead.add( "次留" );
        for( Integer x : shichangDays ) {
            shichangTableHead.add( "D" + x );
        }
    }

    static {
        onlineTableHead.add( "日期" );
        onlineTableHead.add( "新注册用户" );
        minutesOnline.stream().reduce( ( beg, end ) -> {
            millisOnline.add( TimeUtil.MINUTE_MILLIS * beg );
            if( end == Integer.MAX_VALUE ) {
                onlineTableHead.add( ( beg - 1 ) + "+分钟" );
                millisOnline.add( Long.MAX_VALUE );
            }
            else if( beg + 1 == end ) onlineTableHead.add( beg + "分钟" );
            else onlineTableHead.add( beg + "-" + ( end - 1 ) + "分钟" );
            return end;
        } );
    }

    static {
        PayUserAfterPayTableHead = new ArrayList< String >();
        PayUserAfterPayTableHead.add( "日期" );
        PayUserAfterPayTableHead.add( "游戏" );
        PayUserAfterPayTableHead.add( "渠道" );
        PayUserAfterPayTableHead.add( "渠道名" );
        PayUserAfterPayTableHead.add( "当日新增" );
        PayUserAfterPayTableHead.add( "当日注册付费人数" );
        for( Integer x : PayUserAfterPayDays) {
            PayUserAfterPayTableHead.add(  "D" + x  );
        }
    }

    static {
        FuFeiLvTableHead = new ArrayList< String >();
        FuFeiLvTableHead.add( "日期" );
        FuFeiLvTableHead.add( "游戏" );
        FuFeiLvTableHead.add( "渠道号" );
        FuFeiLvTableHead.add( "渠道名" );
        FuFeiLvTableHead.add( "当日注册付费人数" );
        for( Integer x : FuFeiLvDays) {
            FuFeiLvTableHead.add( "D" + x );
        }
    }

    static {
        installPayDaysTableHead = new ArrayList< String >();
        installPayDaysTableHead.add( "日期" );
        installPayDaysTableHead.add( "游戏" );
        installPayDaysTableHead.add( "渠道号" );
        installPayDaysTableHead.add( "渠道名" );
        installPayDaysTableHead.add( "当日付费人数" );
        installPayDaysTableHead.add( "当日新增" );
        for( Integer x : payDays ) {
            installPayDaysTableHead.add( "D" + x );
        }
    }

    static {
        LTVTableHead = new ArrayList< String >();
        LTVTableHead.add( "日期" );
        LTVTableHead.add( "游戏" );
        LTVTableHead.add( "渠道号" );
        LTVTableHead.add( "渠道名" );
        LTVTableHead.add( "当日新增" );
        for( Integer x : payDays ) {
            LTVTableHead.add( "D" + x );
        }
    }

    static {
        retentionTableHead = new ArrayList< String >();
        retentionTableHead.add( "日期" );
        retentionTableHead.add( "游戏" );
        retentionTableHead.add( "渠道号" );
        retentionTableHead.add( "渠道名" );
        retentionTableHead.add( "当日新增" );
        for( Integer x : retentionDays ) {
            retentionTableHead.add( x + "日留存" );
        }
    }

    public void getOnlinePage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_USER_ONLINE).forward( req, resp );
    }


    public void getUserHuiZongPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_USER_HUIZONG ).forward( req, resp );
    }
    public void getUserWinLosePage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_USER_WINLOSE ).forward( req, resp );
    }
    public void getbigRPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_SINGLE_PAY_RANK ).forward( req, resp );
    }

    public void getUserGameWinPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_USER_GAME_WIN ).forward( req, resp );
    }

    public void getPAYDETAILPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_USER_PAY_DETAIL ).forward( req, resp );
    }
    public void getUserDantouPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_USER_DANTOU ).forward( req, resp );
    }
    public void getUserRMBPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_USER_RMB ).forward( req, resp );
    }

    public void getLTVPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        String team = req.getParameter( "team" );
        req.setAttribute( "teamGroup", team );
        req.setAttribute( "games", GameCollection.getGames( team ) );
        req.getRequestDispatcher( Constants.URL_USER_LTV).forward( req, resp );
    }
    public void getshichangPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        String team = req.getParameter( "team" );
        req.setAttribute( "teamGroup", team );
        req.setAttribute( "games", GameCollection.getGames( team ) );
        req.getRequestDispatcher( Constants.URL_USER_SHICHANGBU).forward( req, resp );
    }
    public void getDAUPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        String team = req.getParameter( "team" );
        req.setAttribute( "teamGroup", team );
        req.setAttribute( "games", GameCollection.getGames( team ) );
        req.getRequestDispatcher( Constants.URL_USER_DAU).forward( req, resp );
    }
    public void getUserPhoneNumberPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_USER_PHONENUM ).forward( req, resp );
    }

    public void getInstallPayDaysPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String team = req.getParameter( "team" );
        req.setAttribute( "teamGroup", team );
        req.setAttribute( "games", GameCollection.getGames( team ) );
        req.getRequestDispatcher( Constants.URL_USER_INSTALL_PAY_DAYS ).forward( req, resp );
    }

    public void getInstallRetentionPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String team = req.getParameter( "team" );
        req.setAttribute( "teamGroup", team );
        req.setAttribute( "games", GameCollection.getGames( team ) );
        req.getRequestDispatcher( Constants.URL_USER_INSTALL_RETENTION ).forward( req, resp );
    }
    public void getPayRetentionPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String team = req.getParameter( "team" );
        req.setAttribute( "teamGroup", team );
        req.setAttribute( "games", GameCollection.getGames( team ) );
        req.getRequestDispatcher( Constants.URL_USER_PAY_RETENTION ).forward( req, resp );
    }
    public void getFuFeiLvPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String team = req.getParameter( "team" );
        req.setAttribute( "teamGroup", team );
        req.setAttribute( "games", GameCollection.getGames( team ) );
        req.getRequestDispatcher( Constants.URL_USER_FUFEILV ).forward( req, resp );
    }

    private List<UserView.UserPay>  getBuyRankData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        long PayMoney = Long.parseLong( req.getParameter( "userId" ).trim() ) * 10000;

        return getData( req, resp, ( beg, end, c ) -> {

            Map< Long, UserView.UserPay > user = new HashMap<>();



            Map< Long, Long > PayMoneyMap = ServiceManager.getSqlService().queryMapLongLong( "select a.user_id,a.p from " +
                    " ( select user_id , sum(money2 +money3 ) as p from t_pay group by user_id ) as a " +
                    " where a.p >= "+ PayMoney +";", c );
//            select aa.user_id,bb.t from (select a.user_id,a.p from  ( select user_id , sum(money2 +money3 ) as p from t_pay group by user_id ) as a where a.p >= 100000 ) as aa inner join (select a2.user_id, a2.t from ( select user_id,max(timestamp) as t from t_possession where timestamp >= 1493222400000 group by user_id) as a2) as bb on aa.user_id = bb.user_id;

            Map< Long, Long > LastLoginMap = ServiceManager.getSqlService().queryMapLongLong( "select aa.user_id,bb.t from ( select a.user_id,a.p from " +
                    " ( select user_id , sum(money2 +money3 ) as p from t_pay group by user_id ) as a "  +
                    " where a.p >= "+ PayMoney +") as aa left join (select a2.user_id, a2.t " +
                    " from " +
                    " ( select user_id,max(timestamp) as t from t_possession where timestamp >= "+ (beg - 2592000000L * 2) +"   group by user_id) as a2) as bb " +
                    " on aa.user_id = bb.user_id;", c );

            Map< Long, Long > LastPayMap = ServiceManager.getSqlService().queryMapLongLong( "select aa.user_id,bb.t from ( select a.user_id,a.p from " +
                    " ( select user_id , sum(money2 +money3 ) as p from t_pay group by user_id ) as a "  +
                    " where a.p >= "+ PayMoney +") as aa left join (select a2.user_id, a2.t " +
                    " from " +
                    " ( select user_id,max(timestamp) as t from t_pay where timestamp >= "+ (beg - 2592000000L * 2) +"  group by user_id) as a2) as bb " +
                    " on aa.user_id = bb.user_id;", c );


            Map< Long, Long > installMap = ServiceManager.getSqlService().queryMapLongLong( "select aa.user_id, t_install.timestamp from (select a.user_id,a.p from " +
                    " ( select user_id , sum(money2 +money3 ) as p from t_pay group by user_id ) as a "  +
                    " where a.p >= "+ PayMoney +") as aa left join  t_install  " +
                    " on aa.user_id = t_install.user_id;", c );

            LastLoginMap.forEach( ( k,v ) -> {

                long dayBegin = com.hoolai.ccgames.skeleton.utils.TimeUtil.getDayBeginMillis( v );
                long dayEnd = dayBegin + TimeUtil.DAY_MILLIS;
                UserView.UserPay val = new UserView.UserPay();
                val.userId = k;
                val.begin = TimeUtil.ymdFormat().format( beg );
                val.NoLogin = (System.currentTimeMillis() - v) / 86400000L ;
                val.TodayWin = ServiceManager.getSqlService().queryLong( "select ( select sum(gold) from t_fish_get where timestamp >= "+ dayBegin +" and timestamp < "+ dayEnd +" and user_id = "+ k +" ) " +
                        "- " +
                        "( select sum(gold) from t_fish_paiment where timestamp >= "+ dayBegin +" and timestamp < "+ dayEnd +" and user_id = "+ k +" );" ,c);
                if (v < beg - 86400000 * 10)
                {
                    val.LastLogin = "玩家十日内未登录";
                }else{
                    val.LastLogin = TimeUtil.ymdhmFormat().format(v);
                }

                Long  betMap = ServiceManager.getSqlService().queryLong( "select sum( bet_gold + bet_nb_gold + bet_xnb_gold ) " +
                        " from t_mtl2_bet " +
                        " where user_id = " + k +
                        " ;", c );
                Long  winMap = ServiceManager.getSqlService().queryLong( "select  sum( win_gold ) " +
                        " from t_mtl2_bet " +
                        " where user_id = " + k +
                        " ;", c );
                Long jackpotMap = ServiceManager.getSqlService().queryLong( "select  sum( jackpot ) " +
                        " from t_mtl2_bet " +
                        " where user_id = " + k +
                        " ;", c );
                val.mtlnetWin = winMap - betMap + jackpotMap;

                Long zhuangWin = ServiceManager.getSqlService().queryLong( "select  sum( chips - escape_pump ) " +
                        " from t_zfb_xiazhuang " +
                        " where user_id = " + k +
                        " ;", c );
                Long zhuangBet = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                        " from t_zfb_shangzhuang " +
                        " where user_id = " + k +
                        "   and is_use_card = 'N';", c );

                Long xianWin = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                        " from t_zfb_win " +
                        " where user_id = " + k +
                        " ;", c );
                Long xianBet = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                        " from t_zfb_bet " +
                        " where user_id = " + k +
                        " ;", c );
                Long jackpotWin = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                        " from t_zfb_jackpot " +
                        " where user_id = " + k +
                        " ;", c );
                val.zfbnetWin   = xianWin + zhuangWin + jackpotWin - zhuangBet - xianBet;

                user.put( k,val );
            });

            LastLoginMap.forEach( ( k,v ) -> {
                if( user.get( k ) == null ) user.put( k, new UserView.UserPay() );
                user.get(k).NoPay = (System.currentTimeMillis() - v) / 86400000L ;
                user.get(k).LastPay = TimeUtil.ymdhmFormat().format(v);
            });

            PayMoneyMap.forEach( ( k, v ) -> {
                if( user.get( k ) == null ) user.put( k, new UserView.UserPay() );
                user.get( k ).PayMoney = v;
                user.get( k ).userName = getUserName(k);
            } );

            installMap.forEach( ( k, v ) -> {
                if( user.get( k ) == null ) user.put( k, new UserView.UserPay() );
                user.get( k ).install = TimeUtil.ymdhmFormat().format(v);
                user.get( k ).Win = user.get( k ).zfbnetWin + user.get( k ).mtlnetWin + user.get( k ).TodayWin;
            } );

            ArrayList< UserView.UserPay > rv = new ArrayList<>( user.values() );

            rv.sort( ( a, b ) -> {
                if( a.PayMoney > b.PayMoney ) return -1;
                if( a.PayMoney < b.PayMoney ) return 1;
                if( a.Win > b.Win ) return -1;
                if( a.Win < b.Win ) return 1;
                return 0;
            } );
            return rv;
        } );
    }
//( "日期","用户id","用户名","注册时间", "最近登陆","当日输赢","未登录天数","最近充值","未充值天数","充值总金额","中发白总输赢","摩天轮总输赢","生涯总输赢");
    public void queryBuyRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PROPS, getBuyRankData( req, resp ),
                UserPayRankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" + info.userName+ "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    pw.write( "<td>" + info.LastLogin + "</td>" );
                    pw.write( "<td>" + info.TodayWin + "</td>" );
                    pw.write( "<td>" + (info.NoLogin +1) + "</td>" );
                    pw.write( "<td>" + info.LastPay + "</td>" );
                    pw.write( "<td>" + (info.NoPay+1) + "</td>" );
                    pw.write( "<td>" + info.PayMoney /10000+ "元</td>" );
                    pw.write( "<td>" + info.zfbnetWin + "</td>" );
                    pw.write( "<td>" + info.mtlnetWin + "</td>" );
                    pw.write( "<td>" + info.Win + "</td>" );
                }, null );
    }

    public void downloadBuyRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PROPS, getBuyRankData( req, resp ),
                UserPayRankTableHead, ( info, pw ) -> {
                    pw.write(  info.begin + "," );
                    pw.write(  info.userId + "," );
                    pw.write(  info.userId + "'></td>" );
                    pw.write(  info.install + "," );
                    pw.write(  info.LastLogin + "," );
                    pw.write(  (info.TodayWin+1) + "," );
                    pw.write(  info.LastPay + "," );
                    pw.write(  (info.NoPay +1 )+ "," );
                    pw.write(  info.PayMoney /10000+ "元</td>" );
                    pw.write(  info.zfbnetWin + "," );
                    pw.write(  info.mtlnetWin + "," );
                    pw.write(  info.Win + "," );
                } );
    }

    private List< UserView.LiucunFenxi > getYellowNewData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            UserView.LiucunFenxi val = new UserView.LiucunFenxi();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.install = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_install " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "  ;", c );
            val.win1 = ServiceManager.getSqlService().queryLong( "select COUNT(qq.user_id) from " +
                    "(" +
                    "   select manzu.user_id from  (select a.user_id from ((select user_id from t_install where timestamp >= "+ beg +" and timestamp < " + end + " and appid = '1104754063' ) as newUser " +
                    "                inner join (select distinct user_id from t_mjdr_win where timestamp >= " + beg + " and timestamp < " +  end + "  and gold > 0 and appid = '1104754063' order by timestamp asc) as a on newUser.user_id = a.user_id))" +
                    "   as manzu inner join " +
                    "  (" +
                    "    select distinct user_id from t_dau where timestamp > "+ end + " and timestamp < " + end + " + 86400000 and appid = '1104754063'" +
                    "  ) as nextDay  on manzu.user_id = nextDay.user_id " +
                    ") as qq;", c );
            val.lose1 = ServiceManager.getSqlService().queryLong( "select COUNT(qq.user_id) from " +
                    "(" +
                    "   select manzu.user_id from  (select a.user_id from ((select user_id from t_install where timestamp >= "+ beg +" and timestamp < " + end + " and appid = '1104754063' ) as newUser " +
                    "                inner join (select distinct user_id from t_mjdr_win where timestamp >= " + beg + " and timestamp < " +  end + "  and gold < 0 and appid = '1104754063' order by timestamp asc) as a on newUser.user_id = a.user_id))" +
                    "   as manzu inner join " +
                    "  (" +
                    "    select distinct user_id from t_dau where timestamp > "+ end + " and timestamp < " + end + " + 86400000 and appid = '1104754063'" +
                    "  ) as nextDay  on manzu.user_id = nextDay.user_id " +
                    ") as qq;", c );

            val.task1 = ServiceManager.getSqlService().queryLong( "select count(distinct b.uid) from (select user_id as uid,count(id) as ct from t_mission_finish2 as a where timestamp  > " + beg + " and timestamp <= " + end + "and appid = '1104754063' group by user_id) as b where ct = 1 ;",c);
            val.task2 = ServiceManager.getSqlService().queryLong( "select count(distinct b.uid) from (select user_id as uid,count(id) as ct from t_mission_finish2 as a where timestamp  > " + beg + " and timestamp <= " + end + "and appid = '1104754063' group by user_id) as b where ct = 2 ;",c);
            val.task3 = ServiceManager.getSqlService().queryLong( "select count(distinct b.uid) from (select user_id as uid,count(id) as ct from t_mission_finish2 as a where timestamp  > " + beg + " and timestamp <= " + end + "and appid = '1104754063' group by user_id) as b where ct = 3 ;",c);

            return val;
        } );
    }

    private  List<UserView.UserWinLose> getUserWinLoseData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            //中发白
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
            Map< Long, UserView.UserWinLose > sum = new HashMap<>();

            zhuangWin.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).zfb += v;
            } );

            zhuangBet.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).zfb -= v;
            } );

            zhuangExpr.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).zfb -= v;
            } );

            zhuangRed.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).zfb -= v;
            } );

            xianWin.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).zfb += v;
            } );

            xianBet.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).zfb -= v;
            } );

            jackpotWin.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).zfb += v;
            } );

            sendFlower.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).zfb -= v;
            } );

            recvRed.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).zfb += v;
            } );
            // 摩天轮输赢
            Map< Long, Long > MTLbetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( bet_gold + bet_nb_gold + bet_xnb_gold ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > MTLwinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( win_gold ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > MTLjackpotMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( jackpot ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );

            MTLbetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).mtl -= v;
            } );

            MTLwinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).mtl += v;
            } );

            MTLjackpotMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).mtl += v;
            } );
            //汪汪运动会
            Map< Long, Long > DOGbetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_dogsport_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > DOGwinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_dogsport_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > DOGjackpotMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_dogsport_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );

            DOGbetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).dog -= v;
            } );

            DOGwinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).dog += v;
            } );

            DOGjackpotMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).dog += v;
            } );

            //跑跑堂
            Map< Long, Long > PPTbetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_ppt_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > PPTwinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_ppt_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > PPTjackpotMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_ppt_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > PPTsscBetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_pptssc_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > PPTsscWinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_pptssc_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );


            PPTbetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).ppt -= v;
            } );

            PPTwinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).ppt += v;
            } );

            PPTjackpotMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).ppt += v;
            } );

            PPTsscBetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).ppt -= v;
            } );

            PPTsscWinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).ppt += v;
            } );

            //水果大满贯
            Map< Long, Long > FRUITbetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_fruit_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > FRUITwinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_fruit_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > FRUITjackpotMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_fruit_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > FRUITcaibeiBetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_caibei_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > FRUITcaibeiWinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_caibei_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );

            FRUITbetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).fruit -= v;
            } );

            FRUITwinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).fruit += v;
            } );

            FRUITjackpotMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).fruit += v;
            } );

            FRUITcaibeiBetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).fruit -= v;
            } );

            FRUITcaibeiWinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).fruit += v;
            } );

            //老德州
            Map< Long, Long > OLDdzbetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_gdesk_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid in ( 100632434 ,1104737759 ) group by user_id;", c );
            // 包括抽水的盈利
//            Map< Long, Long > OLDdzwinMap = ServiceManager.getSqlService().queryMapLongLong( "select A.winner_id, sum( A.chips ) " +
//                    " from ( select t_gdesk_win_detail.*, t_install.appid from t_gdesk_win_detail left join t_install on t_gdesk_win_detail.winner_id = t_install.user_id ) A " +
//                    " where " + beg + " <= A.timestamp and A.timestamp < " + end +
//                    " and A.appid in ( 100632434 ,1104737759 ) group by A.winner_id;", c );

            Map< Long, Long > OLDdzwinMap = ServiceManager.getSqlService().queryMapLongLong( "select A.winner_id, sum( A.chips ) " +
                    " from ( select t_gdesk_win_detail.winner_id, t_gdesk_win_detail.chips, t_install.appid " +
                    "        from t_gdesk_win_detail " +
                    "        left join t_install " +
                    "        on t_gdesk_win_detail.winner_id = t_install.user_id " +
                    "        where " + beg + " <= t_gdesk_win_detail.timestamp and t_gdesk_win_detail.timestamp < " + end +
                    "          and coalesce( t_install.appid, '100632434' ) in ( '100632434', '1104737759' ) " +
                    "      ) as A " +
                    " group by A.winner_id;", c );

            Map< Long, Long > OLDdzloseMap = ServiceManager.getSqlService().queryMapLongLong( "select A.loser_id, sum( A.chips ) " +
                    " from ( select t_gdesk_win_detail.loser_id, t_gdesk_win_detail.chips, t_install.appid " +
                    "        from t_gdesk_win_detail " +
                    "        left join t_install " +
                    "        on t_gdesk_win_detail.loser_id = t_install.user_id " +
                    "        where " + beg + " <= t_gdesk_win_detail.timestamp and t_gdesk_win_detail.timestamp < " + end +
                    "          and t_install.appid in ( 100632434 ,1104737759 ) " +
                    "      ) as A " +
                    " group by A.loser_id;", c );

            Map< Long, Long > OLDdzjackpotMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_gdesk_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid in ( 100632434 ,1104737759 ) group by user_id;", c );
            // 现金桌返利，包括忽来横财，幸运转盘，神秘道具
            Map< Long, Long > OLDdzrebateMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid in ( 100632434 ,1104737759 ) group by user_id;", c );
            Map< Long, Long > OLDdzpumpMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_gdesk_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid in ( 100632434 ,1104737759 ) group by user_id;", c );

            OLDdzbetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).laodezhou -= v;
                sum.get( k ).userId = k;
            } );

            OLDdzwinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).laodezhou += v;
            } );

            OLDdzloseMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).laodezhou -= v;
            } );

            OLDdzjackpotMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).laodezhou += v;
            } );

            OLDdzrebateMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).laodezhou += v;
            } );

            OLDdzpumpMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).laodezhou -= v;
            } );
            //新德州

            Map< Long, Long > NEWdzbetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_gdesk_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid in ( 100632434 ,1104737759 ) group by user_id;", c );

            Map< Long, Long > NEWdzwinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_gdesk_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid not in ( 100632434 ,1104737759 ) group by user_id;", c );

            NEWdzbetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).xindezhou -= v;
            } );

            NEWdzwinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).xindezhou += v;
            } );
            //麻将收入

            Map< Long, Long > xueliuRakeMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type in ( 2, 4 ) and is_ccgames = 'Y' " +
                    "   and is_robot = 'N' group by user_id;", c );

            Map< Long, Long > xueliuWinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type in ( 2, 4 ) and is_ccgames = 'Y' " +
                    "   and is_robot = 'N' group by user_id;", c );

            Map< Long, Long > xuezhanRakeMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type in ( 1, 3 ) and is_ccgames = 'Y' " +
                    "   and is_robot = 'N' group by user_id;", c );

            Map< Long, Long > xuezhanWinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type in ( 1, 3 ) and is_ccgames = 'Y' " +
                    "   and is_robot = 'N' group by user_id;", c );

            Map< Long, Long > ermaRakeMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type =5 and is_ccgames = 'Y' " +
                    "   and is_robot = 'N' group by user_id;", c );
            Map< Long, Long > ermaWinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type =5 and is_ccgames = 'Y' " +
                    "   and is_robot = 'N' group by user_id;", c );

            xueliuWinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).xueliu += v;
            } );

            xueliuRakeMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).xueliu -= v;
            } );
            xueliuWinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).xuezhan += v;
            } );
            xuezhanRakeMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).xuezhan -= v;
            } );
            ermaWinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).erma += v;
            } );

            ermaRakeMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).erma -= v;
            } );

            // 斗地主
            Map< Long, Long > ddzWinMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 100  " +
                    "   and is_robot = 'N' group by user_id;", c );
            Map< Long, Long > ddzRakeMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, coalesce( sum( gold ), 0 ) " +
                    " from t_ddz_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type =100 " +
                    "   and is_robot = 'N' group by user_id;", c );

            ddzWinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).doudizhu += v;
            } );

            ddzRakeMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.UserWinLose() );
                sum.get( k ).doudizhu -= v;
            } );

            sum.forEach( ( k, v ) -> {
                v.begin = TimeUtil.ymdhmFormat().format( beg );
                v.end = TimeUtil.ymdhmFormat().format( end );
            } );
            long limit = 100000;
            List< UserView.UserWinLose > list = sum.values().stream().filter( v -> {
                return Math.abs( v.laodezhou ) >= limit ||
                        Math.abs( v.xindezhou ) >= limit ||
                        Math.abs( v.xueliu ) >= limit ||
                        Math.abs( v.xuezhan ) >= limit ||
                        Math.abs( v.erma ) >= limit ||
                        Math.abs( v.doudizhu ) >= limit ||
                        Math.abs( v.zfb ) >= limit ||
                        Math.abs( v.mtl ) >= limit ||
                        Math.abs( v.dog ) >= limit ||
                        Math.abs( v.ppt ) >= limit ||
                        Math.abs( v.fruit ) >= limit ||
                        Math.abs( v.all ) >= limit;
            } ).collect(Collectors.toList() );

            return list;
        } );
    };

    public void queryUserWinLose( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_USER_USER_WIN_LOSE, getUserWinLoseData( req, resp, false ),
                UserWinLoseTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.laodezhou + "</td>" );
                    pw.write( "<td>" + info.xindezhou + "</td>" );
                    pw.write( "<td>" + info.xueliu + "</td>" );
                    pw.write( "<td>" + info.xuezhan + "</td>" );
                    pw.write( "<td>" + info.erma + "</td>" );
                    pw.write( "<td>" + info.doudizhu + "</td>" );
                    pw.write( "<td>" + info.zfb + "</td>" );
                    pw.write( "<td>" + info.mtl + "</td>" );
                    pw.write( "<td>" + info.dog + "</td>" );
                    pw.write( "<td>" + info.ppt + "</td>" );
                    pw.write( "<td>" + info.fruit + "</td>" );
                    pw.write( "<td>" + (info.fruit + info.ppt + info.dog + info.mtl + info.zfb
                                     + info.doudizhu + info.erma + info.xuezhan + info.xueliu + info.xindezhou + info.laodezhou) + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadUserWinLose( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_USER_USER_WIN_LOSE, getUserWinLoseData( req, resp, false ),
                UserWinLoseTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId)  + "'>," );
                    pw.write( info.laodezhou + "," );
                    pw.write( info.xindezhou + "," );
                    pw.write( info.xueliu + "," );
                    pw.write( info.xuezhan + "," );
                    pw.write( info.erma + "," );
                    pw.write( info.doudizhu + "," );
                    pw.write( info.zfb + "," );
                    pw.write( info.mtl + "," );
                    pw.write( info.dog + "," );
                    pw.write( info.ppt + "," );
                    pw.write( info.fruit + "," );
                    pw.write( (info.fruit + info.ppt + info.dog + info.mtl + info.zfb
                            + info.doudizhu + info.erma + info.xuezhan + info.xueliu + info.xindezhou + info.laodezhou) + "," );
                } );
    }

    private List< UserView.Dau > getDAUData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        TimeRange tr = TimeRange.parseFrom( req );
        String team = req.getParameter( "team" );
        String game = req.getParameter( "game" );

        logger.debug( "========== {} {} {} {}", team, game, tr.begin, tr.end );
        List< UserView.Dau > rv = new LinkedList();

        if( CommonUtil.isNoneEmpty( tr, team, game ) ) {
            List< String > games = new ArrayList< String >();
            if( "ALL".equals( game ) ) {
                GameCollection.getAll().forEach( gg -> {
                    if( gg.id.equals( team ) ) games.addAll( gg.games.keySet() );
                } );
            }
            else {
                games.add( game );
            }


            UserView.Dau sum = new UserView.Dau();
            sum.begin = TimeUtil.ymdhmFormat().format( tr.begin );
            sum.end = TimeUtil.ymdhmFormat().format( tr.end );
            sum.team = "所有项目";
            sum.game = "所有游戏";
            sum.qudao = "所有渠道";
            rv.add( sum );

            games.forEach( g -> {
                try {
                    rv.addAll( getDataList2( req, resp, ( beg, end, c ) -> {
                        String[] x = g.split( "-" );  // team-platform-appid
                        List< UserView.Dau > dataList = new ArrayList<>();

                        Map< String, Long > DauMap = ServiceManager.getCommonService().getDauMap( x[2], beg, end, c );
                        Map< String, Long > MauMap = ServiceManager.getCommonService().getMauMap( x[2], beg, end, c );

                        Set< String > allQuDao = new HashSet<>();
                        allQuDao.addAll( DauMap.keySet() );
                        allQuDao.addAll( MauMap.keySet() );

                        allQuDao.forEach( quDao -> {
                            UserView.Dau val = new UserView.Dau();
                            val.begin = TimeUtil.ymdFormat().format( beg );
                            val.end = TimeUtil.ymdFormat().format( end );
                            val.qudao = quDao;
                            val.game = GameCollection.getName( g );

                            long onlinePeople = ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                                    " from t_online " +
                                    " where " + beg + " <= timestamp and timestamp < " + end +
                                    "   and appid = '" + x[2] + "';", c);
                            long a1 = 1;
                            if (onlinePeople != 0 )
                                a1 = onlinePeople;
                            val.online = ServiceManager.getSqlService().queryLong("select coalesce( sum( millis ), 0 ) " +
                                    " from t_online " +
                                    " where " + beg + " <= timestamp and timestamp < " + end +
                                    "   and appid = '" + x[2] + "';", c)
                                    / a1 ;

                            long aa = ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                                    " from t_dau " +
                                    " where " + beg + " <= timestamp and timestamp < " + end +
                                    "   and appid = '" + x[2] + "';", c);
                            long bb  = ServiceManager.getSqlService().queryLong("select count( id ) " +
                                    " from t_dau " +
                                    " where " + beg + " <= timestamp and timestamp < " + end +
                                    "   and appid = '" + x[2] + "';", c);

                            val.times = (double)bb / (double)aa;


                            val.DAU = getLong( DauMap, quDao );
                            val.MAU = getLong( MauMap, quDao );
                            if (val.MAU != 0)
                            {
                                val.huoyuebi = (double) val.DAU / (double) val.MAU  ;
                            }

                            dataList.add( val );
                        } );

                        return dataList;
                    } ) );
                } catch( Exception e ) {
                    throw new RuntimeException( "huoyuebi error" );
                }
            } );

            rv.forEach( val -> {
                sum.DAU += val.DAU;
                sum.MAU += val.MAU;
                sum.online += val.online;
                sum.times += val.times;
            } );
            if (sum.MAU != 0 && rv.size() > 1 )
            {
                sum.huoyuebi = (double) sum.DAU / (double) sum.MAU;
                sum.online = sum.online / ( rv.size() - 1 );
                sum.times = sum.times / ( rv.size() - 1 );
            }
        }
        return rv;
    }

    public void queryDAU( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        queryList( req, resp,
                CommandList.GET_USER_DAU, getDAUData( req, resp ),
                DAUTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.qudao +  "</td>" );
                    pw.write( "<td>" + info.game +  "</td>" );
                    pw.write( "<td>" + info.MAU + "</td>" );
                    pw.write( "<td>" + info.DAU + "</td>" );
                    pw.write( "<td>" + df.format(info.huoyuebi)+ "</td>" );
                    pw.write( "<td>" + (info.online / 1000) + "</td>" );
                    pw.write( "<td>" + df.format(info.times)+ "</td>" );
                } );
    }

    public void downloadDAU( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        downloadList( req, resp,
                CommandList.GET_USER_DAU, getDAUData( req, resp ),
                DAUTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.qudao + "," );
                    pw.write( info.game + "," );
                    pw.write( info.MAU + "," );
                    pw.write( info.DAU + "," );
                    pw.write( df.format(info.huoyuebi) + "," );
                    pw.write( (info.online / 1000) + "," );
                    pw.write( df.format(info.times) + "," );
                } );
    }

    private List< UserView.phoneNum > getphoneNumData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            List<UserView.phoneNum> a = new LinkedList<>();

            Map<Long, String> sendMap = ServiceManager.getSqlService().queryMapLongString("select phone_num, user_id " +
                    " from t_use_huafei;", c);

            sendMap.forEach((k, v) -> {
                UserView.phoneNum val = new UserView.phoneNum();
                String[] params = v.split("\n", 1);
                val.userId = Long.parseLong(params[0]);
                val.phoneNum = k ;
                a.add(val);
            });

            return a;
        } );
    }

    public void queryPhoneNum( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_USER_PHONE_NUM, getphoneNumData( req, resp ),
                phoneNumTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.phoneNum + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadPhoneNum( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_USER_PHONE_NUM, getphoneNumData( req, resp ),
                phoneNumTableHead, ( info, pw ) -> {
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( info.phoneNum + "," );
                } );
    }

    public void queryLTV( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_USER_LTV, getLTVData( req, resp ),
                LTVTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.game + "</td>" );
                    pw.write( "<td>" + info.fw + "</td>" );
                    pw.write( "<td>" + SummaryAction.QuDao.get(info.fw) + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    info.pays.forEach( money -> {
                        pw.write( "<td>" + Currency.format( info.install == 0 ? 0 : money / info.install ) + "</td>" );
                    });
                },null );
    }


    public void downloadLTV( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_USER_LTV, getLTVData( req, resp ),
                LTVTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.game + "," );
                    pw.write( info.fw + "," );
                    pw.write( SummaryAction.QuDao.get(info.fw) + "," );
                    pw.write( info.install + "," );
                    info.pays.forEach( money -> {
                        pw.write(   Currency.format( info.install == 0 ? 0 : money / info.install ) + "," );
                    } );
                } );
    }


    private List< UserView.Online > getOnlineData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            UserView.Online val = new UserView.Online();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.install = ServiceManager.getCommonService().getInstall(  beg, end, isMobile, c );
            Map< Long, Long > onlineMap = ServiceManager.getSqlService().queryMapLongLong( "select A.user_id, A.online " +
                    " from ( select user_id, sum( millis ) as online " +
                    "        from t_online  " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "        and kind = 0 "+
                    "        group by user_id ) as A " +
                    "        inner join " +
                    "      ( select user_id " +
                    "        from t_install " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "        and is_mobile = '" + boolString( isMobile ) + "' ) as B " +
                    " on A.user_id = B.user_id;", c );
            millisOnline.stream().reduce( ( b, e ) -> {
                long people = onlineMap.values().stream().filter( v -> b <= v && v < e ).count();
                val.onlinePeople.add( people );
                return e;
            } );
            if( millisOnline.get( 0 ) == 0L ) {
                long notPlayPeople = val.onlinePeople.stream().reduce( val.install, Math::subtractExact );
                val.onlinePeople.set( 0, val.onlinePeople.get( 0 ) + notPlayPeople );
            }
            return val;
        } );
    }

    public void queryOnline( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_SINGLE, getOnlineData( req, resp, false ),
                onlineTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    info.onlinePeople.forEach( p -> pw.write( "<td>" + p + "</td>" ) );
                } );
    }

    public void downloadOnline( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_SINGLE, getOnlineData( req, resp, false ),
                onlineTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install + "," );
                    info.onlinePeople.forEach( p -> pw.write( p + "," ) );
                } );
    }

    private  List< List< UserView.InstallPayDays > > getLTVData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        TimeRange tr = TimeRange.parseFrom( req );
        String team = req.getParameter( "team" );
        String game = req.getParameter( "game" );
        logger.debug( "========== {} {} {} {}", team, game, tr.begin, tr.end );
        List< UserView.InstallPayDays > rv = new LinkedList<>();
        List< UserView.InstallPayDays > sum = new LinkedList<>();
        List< UserView.InstallPayDays > IOS = new LinkedList<>();
        List< UserView.InstallPayDays > AND = new LinkedList<>();
        List< UserView.InstallPayDays > ANDDAI = new LinkedList<>();

        if( CommonUtil.isNoneEmpty( tr, team, game ) ) {
            List< String > games = new ArrayList< String >();
            if( "ALL".equals( game ) ) {
                GameCollection.getAll().forEach( gg -> {
                    if( gg.id.equals( team ) ) games.addAll( gg.games.keySet() );
                } );
            }
            else {
                games.add( game );
            }

            tr.timePoints.stream().reduce( ( beg, end ) -> {
                UserView.InstallPayDays sval = new UserView.InstallPayDays();
                sval.begin = TimeUtil.ymdFormat().format( beg );
                sval.end = TimeUtil.ymdFormat().format( end );
                sval.game = "所有游戏";
                sval.install = 0;
                payDays.forEach( day -> {
                    sval.pays.add( 0L );
                } );
                sum.add( sval );
                return end;
            } );
            tr.timePoints.stream().reduce( ( beg, end ) -> {
                UserView.InstallPayDays sval = new UserView.InstallPayDays();
                sval.begin = TimeUtil.ymdFormat().format( beg );
                sval.end = TimeUtil.ymdFormat().format( end );
                sval.game = "IOS";
                sval.install = 0;
                payDays.forEach( day -> {
                    sval.pays.add( 0L );
                } );
                IOS.add( sval );
                return end;
            } );
            tr.timePoints.stream().reduce( ( beg, end ) -> {
                UserView.InstallPayDays sval = new UserView.InstallPayDays();
                sval.begin = TimeUtil.ymdFormat().format( beg );
                sval.end = TimeUtil.ymdFormat().format( end );
                sval.game = "AND";
                sval.install = 0;
                payDays.forEach( day -> {
                    sval.pays.add( 0L );
                } );
                AND.add( sval );
                return end;
            } );
            tr.timePoints.stream().reduce( ( beg, end ) -> {
                UserView.InstallPayDays sval = new UserView.InstallPayDays();
                sval.begin = TimeUtil.ymdFormat().format( beg );
                sval.end = TimeUtil.ymdFormat().format( end );
                sval.game = "ANDDAI";
                sval.install = 0;
                payDays.forEach( day -> {
                    sval.pays.add( 0L );
                } );
                ANDDAI.add( sval );
                return end;
            } );


            games.forEach( g -> {
                String[] x = g.split( "-" );  // team-platform-appid
                List< String > PERM_LIST = Arrays.asList( "264" ,"252","1251807724","290","300","302" );
//                if( req.getSession().getAttribute( "userName" ).equals("shichangbu" ) && !PERM_LIST.contains( x[2] ) ) return;
//                if( req.getSession().getAttribute( "userName" ).equals("shoujibuyu" ) && !PERM_LIST.contains( x[2] ) ) return;
                try {
                    rv.addAll( getDataList2( req, resp, ( beg, end, c ) -> {

                        Map< String, UserView.InstallPayDays > map = new HashMap<>();
                        Map< String, Long > installMap = ServiceManager.getCommonService().getInstallMap( x[2], beg, end, c );
                        installMap.forEach( ( k, v ) -> {
                            UserView.InstallPayDays val = new UserView.InstallPayDays();
                            val.fw = k;
                            val.begin = TimeUtil.ymdFormat().format( beg );
                            val.end = TimeUtil.ymdFormat().format( end );
                            val.team = TeamCollection.getName( x[0] );
                            val.game = GameCollection.getName( g );
                            val.install = v;
                            map.put( k, val );
                        } );
                        long a = System.currentTimeMillis();
                        payDays.forEach( day -> {
                            long rBeg = beg;
                            long rEnd = beg + ( day + 1 ) * TimeUtil.DAY_MILLIS;
                            Map< String, Long > payMoneyMap = ServiceManager.getCommonService().getPayMoneyMap( x[2], beg, end, rBeg, rEnd, c );
                            map.forEach( ( fw, val ) -> {
                                val.pays.add( getLong( payMoneyMap, fw ) );
                            } );
                        } );

                        sum.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if (fw.equals("2969")) return;
                                if (fw.equals("3311")) return;
                                if( sval.begin.equals( val.begin ) ) {
                                    sval.merge( val );
                                }
                            });
                        } );
                        IOS.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if (!fw.equals("1251807724") && !fw.equals("1239313319")) return;
                                if( sval.begin.equals( val.begin ) ) {
                                    sval.merge( val );
                                }
                            });
                        } );
                        AND.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if (fw.equals("1251807724")) return;
                                if (fw.equals("1239313319")) return;
                                if (fw.equals("3311")) return;
                                if( sval.begin.equals( val.begin ) ) {
                                    sval.merge( val );
                                }
                            });
                        } );
                        ANDDAI.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if (fw.equals("1251807724")) return;
                                if (fw.equals("1239313319")) return;
                                if( sval.begin.equals( val.begin ) ) {
                                    sval.merge( val );
                                }
                            });
                        } );

                        return new ArrayList<>( map.values() );
                    } ) );
                }
                catch( Exception e ) {
                    throw new RuntimeException( "InstallPayDays error" );
                }
            } );
        }

        return Arrays.asList( sum, IOS, AND, ANDDAI, rv );
    }

    private  List< List< UserView.InstallPayDays > > getInstallPayDaysData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        TimeRange tr = TimeRange.parseFrom( req );
        String team = req.getParameter( "team" );
        String game = req.getParameter( "game" );
        logger.debug( "========== {} {} {} {}", team, game, tr.begin, tr.end );
        List< UserView.InstallPayDays > rv = new LinkedList<>();
        List< UserView.InstallPayDays > sum = new LinkedList<>();

        if( CommonUtil.isNoneEmpty( tr, team, game ) ) {
            List< String > games = new ArrayList< String >();
            if( "ALL".equals( game ) ) {
                GameCollection.getAll().forEach( gg -> {
                    if( gg.id.equals( team ) ) games.addAll( gg.games.keySet() );
                } );
            }
            else {
                games.add( game );
            }

            tr.timePoints.stream().reduce( ( beg, end ) -> {
                UserView.InstallPayDays sval = new UserView.InstallPayDays();
                sval.begin = TimeUtil.ymdFormat().format( beg );
                sval.end = TimeUtil.ymdFormat().format( end );
                sval.game = "所有游戏";
                sval.install = 0;
                payDays.forEach( day -> {
                    sval.pays.add( 0L );
                } );
                sum.add( sval );
                return end;
            } );

            games.forEach( g -> {
                String[] x = g.split( "-" );  // team-platform-appid
                List< String > PERM_LIST = Arrays.asList( "264" ,"252","1251807724","290","300","302" );
//                if( req.getSession().getAttribute( "userName" ).equals("shichangbu" ) && !PERM_LIST.contains( x[2] ) ) return;
//                if( req.getSession().getAttribute( "userName" ).equals("shoujibuyu" ) && !PERM_LIST.contains( x[2] ) ) return;
                try {
                    rv.addAll( getDataList2( req, resp, ( beg, end, c ) -> {

                        Map< String, UserView.InstallPayDays > map = new HashMap<>();
                        long endTimet = System.currentTimeMillis();
                        Map< String, Long > timeMap = ServiceManager.getCommonService().getPayPeopleMap( x[2], beg, end, endTimet, c );
                        Map< String, Long > installMap = ServiceManager.getCommonService().getInstallMap( x[2], beg, end, c );
                        installMap.forEach( ( k, v ) -> {
                            UserView.InstallPayDays val = new UserView.InstallPayDays();
                            val.fw = k;
                            val.begin = TimeUtil.ymdFormat().format( beg );
                            val.end = TimeUtil.ymdFormat().format( end );
                            val.team = TeamCollection.getName( x[0] );
                            val.game = GameCollection.getName( g );
                            val.install = v;
                            map.put( k, val );
                        } );
                        timeMap.forEach( ( k, v ) -> {
                            int a = 0;
                            map.get( k ).PayPeople = v;
                        } );
                        payDays.forEach( day -> {
                            long rBeg = beg;
                            long rEnd = beg + ( day + 1 ) * TimeUtil.DAY_MILLIS;
                            Map< String, Long > payMoneyMap = ServiceManager.getCommonService().getPayMoneyMap( x[2], beg, end, rBeg, rEnd, c );
                            map.forEach( ( fw, val ) -> {
                                val.pays.add( getLong( payMoneyMap, fw ) );
                            } );
                        } );

                        sum.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if( sval.begin.equals( val.begin ) ) {
                                    sval.merge( val );
                                }
                            });
                        } );

                        return new ArrayList<>( map.values() );
                    } ) );
                }
                catch( Exception e ) {
                    throw new RuntimeException( "InstallPayDays error" );
                }
            } );
        }

        return Arrays.asList( sum, rv );
    }

    public void queryInstallPayDays( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_USER_INSTALL_PAY_DAYS, getInstallPayDaysData( req, resp ),
                installPayDaysTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.game + "</td>" );
                    pw.write( "<td>" + info.fw + "</td>" );
                    pw.write( "<td>" + SummaryAction.QuDao.get(info.fw) + "</td>" );
                    pw.write( "<td>" + info.PayPeople + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    info.pays.forEach( money -> {
                        pw.write( "<td>" + Currency.format(  money ) + "</td>" );
                    });
                } , null);
    }

    public void downloadInstallPayDays( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_USER_INSTALL_PAY_DAYS, getInstallPayDaysData( req, resp ),
                installPayDaysTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.game + "," );
                    pw.write( info.fw + "," );
                    pw.write( SummaryAction.QuDao.get(info.fw) + "," );
                    pw.write( info.PayPeople + "," );
                    pw.write( info.install + "," );
                    info.pays.forEach( money -> {
                        pw.write( Currency.format( money ) + "," );
                    } );
                } );
    }

    // 汇总表和详细表
    private List< List< UserView.InstallRetention > > getInstallRetentionData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        TimeRange tr = TimeRange.parseFrom( req );
        String team = req.getParameter( "team" );
        String game = req.getParameter( "game" );
        logger.debug( "========== {} {} {} {}", team, game, tr.begin, tr.end );
        List< UserView.InstallRetention > detail = new LinkedList<>();
        List< UserView.InstallRetention > sum = new LinkedList<>();
        List< UserView.InstallRetention > pcSum = new LinkedList<>();
        List< UserView.InstallRetention > mobileSum = new LinkedList<>();
        List< UserView.InstallRetention > lianyunSum = new LinkedList<>();
        List< UserView.InstallRetention > zimailiangSum = new LinkedList<>();
        List< UserView.InstallRetention > IOSSum = new LinkedList<>();
        List< UserView.InstallRetention > ANDSum = new LinkedList<>();
        List< UserView.InstallRetention > ANDDAISum = new LinkedList<>();


        if( CommonUtil.isNoneEmpty( tr, team, game ) ) {

            List< String > games = new ArrayList<>();
            if( "ALL".equals( game ) ) {
                GameCollection.getAll().forEach( gg -> {
                    if( gg.id.equals( team ) ) games.addAll( gg.games.keySet() );
                } );
            }
            else {
                games.add( game );
            }

            tr.timePoints.stream().reduce( ( beg, end ) -> {
                UserView.InstallRetention sval = new UserView.InstallRetention();
                sval.begin = TimeUtil.ymdFormat().format( beg );
                sval.end = TimeUtil.ymdFormat().format( end );
                sval.game = "所有游戏";
                sval.install = 0;
                retentionDays.forEach( day -> {
                    sval.retentions.add( 0L );
                } );
                sum.add( sval );

                UserView.InstallRetention pcVal = new UserView.InstallRetention();
                pcVal.begin = TimeUtil.ymdFormat().format( beg );
                pcVal.end = TimeUtil.ymdFormat().format( end );
                pcVal.game = "PC游戏";
                pcVal.install = 0;
                retentionDays.forEach( day -> {
                    pcVal.retentions.add( 0L );
                } );
                pcSum.add( pcVal );

                UserView.InstallRetention lianyunVal = new UserView.InstallRetention();
                lianyunVal.begin = TimeUtil.ymdFormat().format( beg );
                lianyunVal.end = TimeUtil.ymdFormat().format( end );
                lianyunVal.game = "联运渠道";
                lianyunVal.install = 0;
                retentionDays.forEach( day -> {
                    lianyunVal.retentions.add( 0L );
                } );
                lianyunSum.add( lianyunVal );


                UserView.InstallRetention zimailiangVal = new UserView.InstallRetention();
                zimailiangVal.begin = TimeUtil.ymdFormat().format( beg );
                zimailiangVal.end = TimeUtil.ymdFormat().format( end );
                zimailiangVal.game = "自买量渠道";
                zimailiangVal.install = 0;
                retentionDays.forEach( day -> {
                    zimailiangVal.retentions.add( 0L );
                } );
                zimailiangSum.add( zimailiangVal );

                UserView.InstallRetention IOSVal = new UserView.InstallRetention();
                IOSVal.begin = TimeUtil.ymdFormat().format( beg );
                IOSVal.end = TimeUtil.ymdFormat().format( end );
                IOSVal.game = "IOS渠道";
                IOSVal.install = 0;
                retentionDays.forEach( day -> {
                    IOSVal.retentions.add( 0L );
                } );
                IOSSum.add( IOSVal );

                UserView.InstallRetention ANDVal = new UserView.InstallRetention();
                ANDVal.begin = TimeUtil.ymdFormat().format( beg );
                ANDVal.end = TimeUtil.ymdFormat().format( end );
                ANDVal.game = "AND渠道";
                ANDVal.install = 0;
                retentionDays.forEach( day -> {
                    ANDVal.retentions.add( 0L );
                } );
                ANDSum.add( ANDVal );

                UserView.InstallRetention ANDDAIVal = new UserView.InstallRetention();
                ANDDAIVal.begin = TimeUtil.ymdFormat().format( beg );
                ANDDAIVal.end = TimeUtil.ymdFormat().format( end );
                ANDDAIVal.game = "AND带渠道";
                ANDDAIVal.install = 0;
                retentionDays.forEach( day -> {
                    ANDDAIVal.retentions.add( 0L );
                } );
                ANDDAISum.add( ANDDAIVal );


                UserView.InstallRetention mobileVal = new UserView.InstallRetention();
                mobileVal.begin = TimeUtil.ymdFormat().format( beg );
                mobileVal.end = TimeUtil.ymdFormat().format( end );
                mobileVal.game = "Mobile游戏";
                mobileVal.install = 0;
                retentionDays.forEach( day -> {
                    mobileVal.retentions.add( 0L );
                } );
                mobileSum.add( mobileVal );

                return end;
            } );

            games.forEach( g -> {
                try {
                    String[] x = g.split( "-" );  // team-platform-appid
                    List< String > PERM_LIST = Arrays.asList( "264" ,"252","1251807724","290","300","302" );
                    detail.addAll( getDataList2( req, resp, ( beg, end, c ) -> {
                        Map< String, UserView.InstallRetention > map = new HashMap<>();
                        Map< String, Long > installMap = ServiceManager.getCommonService().getInstallMap( x[2], beg, end, c );

                        installMap.forEach( ( k, v ) -> {
                            UserView.InstallRetention val = new UserView.InstallRetention();
                            val.fw = k;
                            val.begin = TimeUtil.ymdFormat().format( beg );
                            val.end = TimeUtil.ymdFormat().format( end );
                            val.team = TeamCollection.getName( x[0] );
                            val.game = GameCollection.getName( g );
                            val.install = v;
                            map.put( k, val );
                        } );

                        retentionDays.forEach( day -> {
                            long rBeg = beg + TimeUtil.DAY_MILLIS * day;
                            long rEnd = rBeg + TimeUtil.DAY_MILLIS;
                            Map< String, Long > DauMap = ServiceManager.getCommonService().getDauMap( x[2], beg, end, rBeg, rEnd, c );
                            map.forEach( ( fw, val ) -> {
                                val.retentions.add( getLong( DauMap, fw ) );
                            } );
                        } );

                        sum.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if( fw.equals("2969") ) return;
                                if( fw.equals("3311") ) return;
                                if( sval.begin.equals( val.begin ) ) {
                                    sval.merge( val );
                                }
                            });
                        } );


                        if( GameCollection.mobileGames.contains( x[2] ) ) {
                            mobileSum.forEach( sval -> {
                                map.forEach( ( fw, val ) -> {
                                    if( fw.equals("2969") ) return;
                                    if( fw.equals("3311") ) return;
                                    if (sval.begin.equals(val.begin)) {
                                        sval.merge(val);
                                    }
                                });
                            } );
                        }
                        else {
                            pcSum.forEach( sval -> {
                                map.forEach( ( fw, val ) -> {
                                    if( fw.equals("2969") ) return;
                                    if( fw.equals("3311") ) return;
                                    if (sval.begin.equals(val.begin)) {
                                        sval.merge(val);
                                    }
                                } );
                            } );
                        }

                            lianyunSum.forEach( sval -> {
                                map.forEach( ( fw, val ) -> {
                                    if( fw.equals("2969") ) return;
                                    if( fw.equals("3311") ) return;
                                    if (sval.begin.equals(val.begin)) {
                                        if (SummaryAction.lianyun.contains( fw ))
                                        {sval.merge(val);}
                                    }
                                });
                            } );

                            zimailiangSum.forEach( sval -> {
                                map.forEach( ( fw, val ) -> {
                                    if( fw.equals("2969") ) return;
                                    if( fw.equals("3311") ) return;
                                    if (sval.begin.equals(val.begin)) {
                                        if (!SummaryAction.CESHI.contains(fw) && !SummaryAction.IOS.contains(fw) && !SummaryAction.lianyun.contains(fw) && !SummaryAction.YIYEHEZUO.contains(fw))
                                        sval.merge(val);
                                    }
                                } );
                            } );
                        IOSSum.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if( !fw.equals("1239313319") && !fw.equals("1251807724") ) return;
                                if (sval.begin.equals(val.begin)) {
                                        sval.merge(val);
                                }
                            } );
                        } );
                        ANDSum.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if( fw.equals("1239313319") ) return;
                                if( fw.equals("1251807724") ) return;
                                if( fw.equals("3311") ) return;
                                if (sval.begin.equals(val.begin)) {
                                        sval.merge(val);
                                }
                            } );
                        } );
                        ANDDAISum.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if( fw.equals("1239313319") ) return;
                                if( fw.equals("1251807724") ) return;
                                if (sval.begin.equals(val.begin)) {
                                    sval.merge(val);
                                }
                            } );
                        } );

                        return new ArrayList<>( map.values() );
                    } ) );
                }
                catch( Exception e ) {
                    throw new RuntimeException( "getInstallRetentionData error" );
                }
            } );
        }
        sum.addAll( pcSum );
        sum.addAll( mobileSum );
        sum.addAll( zimailiangSum );
        sum.addAll( lianyunSum );
        sum.addAll( IOSSum );
        sum.addAll( ANDSum );
        sum.addAll( ANDDAISum );

        Collections.sort( detail, Comparator.comparing(o -> o.fw));


        return Arrays.asList( sum, detail );
    }

    public void queryInstallRetention( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_USER_INSTALL_RETENTION, getInstallRetentionData( req, resp ),
                retentionTableHead, ( info, pw ) -> {
                    pw.write( "<tr>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.game + "</td>" );
                    pw.write( "<td>" + info.fw + "</td>" );
                    pw.write( "<td>" + SummaryAction.QuDao.get(info.fw) + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    info.retentions.forEach( retention -> {
//                        info.install == 0 ? 0 : retention / info.install
                        pw.write( "<td>" + formatRatio( retention / (double) info.install ) + "</td>" );
                    } );
                }, null );
    }

    public void downloadInstallRetention( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_USER_INSTALL_RETENTION, getInstallRetentionData( req, resp ),
                retentionTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.game + "," );
                    pw.write( info.fw + "," );
                    pw.write( SummaryAction.QuDao.get(info.fw) + "," );
                    pw.write( info.install + "," );
                    info.retentions.forEach( retention -> {
                        pw.write( formatRatio( retention / (double) info.install ) + "," );
                    } );
                } );
    }

    private List< List< UserView.InstallRetention > > getPayRetentionData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        TimeRange tr = TimeRange.parseFrom( req );
        String team = req.getParameter( "team" );
        String game = req.getParameter( "game" );
        logger.debug( "========== {} {} {} {}", team, game, tr.begin, tr.end );
        List< UserView.InstallRetention > detail = new LinkedList<>();
        List< UserView.InstallRetention > sum = new LinkedList<>();
        List< UserView.InstallRetention > IOS = new LinkedList<>();
        List< UserView.InstallRetention > AND = new LinkedList<>();
        List< UserView.InstallRetention > ANDDAI = new LinkedList<>();


        if( CommonUtil.isNoneEmpty( tr, team, game ) ) {

            List< String > games = new ArrayList<>();
            if( "ALL".equals( game ) ) {
                GameCollection.getAll().forEach( gg -> {
                    if( gg.id.equals( team ) ) games.addAll( gg.games.keySet() );
                } );
            }
            else {
                games.add( game );
            }

            tr.timePoints.stream().reduce( ( beg, end ) -> {
                UserView.InstallRetention sval = new UserView.InstallRetention();
                sval.begin = TimeUtil.ymdFormat().format( beg );
                sval.end = TimeUtil.ymdFormat().format( end );
                sval.game = "所有游戏";
                sval.install = 0;
                PayUserAfterPayDays.forEach( day -> {
                    sval.retentions.add( 0L );
                } );
                sum.add( sval );
                return end;
            } );
            tr.timePoints.stream().reduce( ( beg, end ) -> {
                UserView.InstallRetention sval = new UserView.InstallRetention();
                sval.begin = TimeUtil.ymdFormat().format( beg );
                sval.end = TimeUtil.ymdFormat().format( end );
                sval.game = "IOS";
                sval.install = 0;
                PayUserAfterPayDays.forEach( day -> {
                    sval.retentions.add( 0L );
                } );
                IOS.add( sval );
                return end;
            } );
            tr.timePoints.stream().reduce( ( beg, end ) -> {
                UserView.InstallRetention sval = new UserView.InstallRetention();
                sval.begin = TimeUtil.ymdFormat().format( beg );
                sval.end = TimeUtil.ymdFormat().format( end );
                sval.game = "AND";
                sval.install = 0;
                PayUserAfterPayDays.forEach( day -> {
                    sval.retentions.add( 0L );
                } );
                AND.add( sval );
                return end;
            } );
            tr.timePoints.stream().reduce( ( beg, end ) -> {
                UserView.InstallRetention sval = new UserView.InstallRetention();
                sval.begin = TimeUtil.ymdFormat().format( beg );
                sval.end = TimeUtil.ymdFormat().format( end );
                sval.game = "ANDDAI";
                sval.install = 0;
                PayUserAfterPayDays.forEach( day -> {
                    sval.retentions.add( 0L );
                } );
                ANDDAI.add( sval );
                return end;
            } );

            games.forEach( g -> {
                try {
                    String[] x = g.split( "-" );  // team-platform-appid
                    List< String > PERM_LIST = Arrays.asList( "264" ,"252","1251807724","290","300","302" );
                    detail.addAll( getDataList2( req, resp, ( beg, end, c ) -> {
                        Map< String, UserView.InstallRetention > map = new HashMap<>();
                        Map< Long, String > fwMap = ServiceManager.getCommonService().getFwMap( x[2], beg, end, c );
                        Set< Long > payUser = new HashSet<>( ServiceManager.getCommonService().getPayUserList( x[2], beg, end, c ) );

                        long dauBegin = beg + PayUserAfterPayDays.get( 0 ) * TimeUtil.DAY_MILLIS;
                        long dauEnd = beg + PayUserAfterPayDays.get( PayUserAfterPayDays.size() - 1 ) * TimeUtil.DAY_MILLIS + TimeUtil.DAY_MILLIS;
                        List< String > timeUserList = ServiceManager.getCommonService().getPossessionTimeUserList( x[2], dauBegin, dauEnd, c );

                        fwMap.forEach( ( k, v ) -> {
                            map.compute( v, ( k1, v1 ) -> {
                                if( v1 == null ) {
                                    v1 = new UserView.InstallRetention();
                                    v1.fw = v;
                                    v1.begin = TimeUtil.ymdFormat().format( beg );
                                    v1.end = TimeUtil.ymdFormat().format( end );
                                    v1.team = TeamCollection.getName( x[0] );
                                    v1.game = GameCollection.getName( g );
                                    v1.pay = 0;
                                }
                                ++v1.install;
                                return v1;
                            } );
                        } );

                        payUser.forEach( userId -> {
                            String fw = fwMap.get( userId );
                            if( fw == null ) return;
                            map.compute( fw, ( k1, v1 ) -> {
                                ++v1.pay;
                                return v1;
                            } );
                        } );

                        Map< String, Map< Integer, Long > > dauMap = new HashMap<>();

                        timeUserList.forEach( str -> {
                            String[] args = str.split( "\n" );
                            long ts = Long.parseLong( args[0] );
                            long userId = Long.parseLong( args[1] );
                            if( !payUser.contains( userId ) ) return;
                            String fw = fwMap.get( userId );
                            if( fw == null ) return;
                            int days = ( int ) ( ( ts - beg ) / TimeUtil.DAY_MILLIS );
                            dauMap.compute( fw, ( k1, v1 ) -> {
                                if( v1 == null ) v1 = new HashMap<>();
                                v1.compute( days, ( k2, v2 ) -> v2 == null ? 1 : v2 + 1 );
                                return v1;
                            } );
                        } );

                        map.forEach( ( fw, data ) -> {
                            Map< Integer, Long > tmp = dauMap.get( fw );
                            if( tmp != null ) {
                                PayUserAfterPayDays.forEach( day -> {
                                    data.retentions.add( getLong( tmp, day ) );
                                } );
                            }
                            else {
                                PayUserAfterPayDays.forEach( day -> {
                                    data.retentions.add( 0L );
                                } );
                            }
                        } );

                        sum.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if( sval.begin.equals( val.begin ) ) {
                                    sval.merge( val );
                                }
                            });
                        } );
                        IOS.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if (!fw.equals("1251807724") && !fw.equals("1239313319")) return;
                                if( sval.begin.equals( val.begin ) ) {
                                    sval.merge( val );
                                }
                            });
                        } );
                        AND.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if (fw.equals("1251807724")) return;
                                if (fw.equals("1239313319")) return;
                                if (fw.equals("3311")) return;
                                if( sval.begin.equals( val.begin ) ) {
                                    sval.merge( val );
                                }
                            });
                        } );
                        ANDDAI.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if (fw.equals("1251807724")) return;
                                if (fw.equals("1239313319")) return;
                                if( sval.begin.equals( val.begin ) ) {
                                    sval.merge( val );
                                }
                            });
                        } );

                        return new ArrayList<>( map.values() );
                    } ) );
                }
                catch( Exception e ) {
                    throw new RuntimeException( "getInstallRetentionData error" );
                }
            } );
        }
        sum.addAll( IOS );
        sum.addAll( AND );
        sum.addAll( ANDDAI );
        logger.debug( "size {} {}", sum.size(), detail.size() );
        return Arrays.asList( sum, detail );
    }

    public void queryPayRetention( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_USER_INSTALL_RETENTION, getPayRetentionData( req, resp ),
                PayUserAfterPayTableHead, ( info, pw ) -> {
                    pw.write( "<tr>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.game + "</td>" );
                    pw.write( "<td>" + info.fw + "</td>" );
                    pw.write( "<td>" + SummaryAction.QuDao.get(info.fw) + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    pw.write( "<td>" + info.pay + "</td>" );
                    info.retentions.forEach( retention -> {
                        pw.write( "<td>" + formatRatio( retention / (double) info.pay ) + "</td>" );
                    } );
                }, null );
    }

    public void downloadPayRetention( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_USER_INSTALL_RETENTION, getPayRetentionData( req, resp ),
                PayUserAfterPayTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.game + "," );
                    pw.write( info.fw + "," );
                    pw.write( SummaryAction.QuDao.get(info.fw) + "," );
                    pw.write( info.install + "," );
                    pw.write( info.pay + "," );
                    info.retentions.forEach( retention -> {
                        pw.write( formatRatio( retention / (double) info.pay ) + "," );
                    } );
                } );
    }


    private List< List< UserView.InstallRetention > > getFuFeiLvData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        TimeRange tr = TimeRange.parseFrom( req );
        String team = req.getParameter( "team" );
        String game = req.getParameter( "game" );
        logger.debug( "========== {} {} {} {}", team, game, tr.begin, tr.end );
        List< UserView.InstallRetention > detail = new LinkedList<>();
        List< UserView.InstallRetention > sum = new LinkedList<>();


        if( CommonUtil.isNoneEmpty( tr, team, game ) ) {

            List< String > games = new ArrayList<>();
            if( "ALL".equals( game ) ) {
                GameCollection.getAll().forEach( gg -> {
                    if( gg.id.equals( team ) ) games.addAll( gg.games.keySet() );
                } );
            }
            else {
                games.add( game );
            }

            tr.timePoints.stream().reduce( ( beg, end ) -> {
                UserView.InstallRetention sval = new UserView.InstallRetention();
                sval.begin = TimeUtil.ymdFormat().format( beg );
                sval.end = TimeUtil.ymdFormat().format( end );
                sval.game = "所有游戏";
                sval.install = 0;
                sval.retentions = CollectionUtil.repeat( 0L, FuFeiLvDays.size() );
                sum.add( sval );
                return end;
            } );

            games.forEach( g -> {
                try {
                    String[] x = g.split( "-" );  // team-platform-appid
                    List< String > PERM_LIST = Arrays.asList( "264" ,"252","1251807724","290","300","302" );
//                    if( req.getSession().getAttribute( "userName" ).equals("shichangbu" ) && !PERM_LIST.contains( x[2] ) ) return;
//                    if( req.getSession().getAttribute( "userName" ).equals("shoujibuyu" ) && !PERM_LIST.contains( x[2] ) ) return;
                    detail.addAll( getDataList2( req, resp, ( beg, end, c ) -> {
                        Map< String, UserView.InstallRetention > map = new HashMap<>();
                        Map< String, Long > installMap = ServiceManager.getCommonService().getInstallMap( x[2], beg, end, c );

                        installMap.forEach( ( k, v ) -> {
                            UserView.InstallRetention val = new UserView.InstallRetention();
                            val.fw = k;
                            val.begin = TimeUtil.ymdFormat().format( beg );
                            val.end = TimeUtil.ymdFormat().format( end );
                            val.team = TeamCollection.getName( x[0] );
                            val.game = GameCollection.getName( g );
                            val.install = v;
                            val.retentions = CollectionUtil.repeat( 0L, FuFeiLvDays.size() );
                            map.put( k, val );
                        } );

                        long dauBegin = beg + TimeUtil.DAY_MILLIS * Collections.min( FuFeiLvDays );
                        long dauEnd = end + TimeUtil.DAY_MILLIS * Collections.max( FuFeiLvDays );
                        Map< Long, String > payPeopleMap = ServiceManager.getCommonService().getPayPeopleMap( x[2], beg, end, beg, dauEnd, c );

                        Map< Integer, Set< String > > dayUsersMap = new HashMap<>();

                        payPeopleMap.forEach( ( ts, uidFw ) -> {
                            int day = (int) ( ( ts - beg ) / TimeUtil.DAY_MILLIS );
                            dayUsersMap.compute( day, ( k, v ) -> {
                                if( v == null ) v = new HashSet<>();
                                v.add( uidFw );
                                return v;
                            } );
                        });

                        Set< String > day0Pay = dayUsersMap.get( 0 );
                        if( day0Pay != null && day0Pay.size() > 0 ) {
                            for( String uidFw : day0Pay ) {
                                String fw = uidFw.split( "\n" )[1];
                                map.get( fw ).pay++;
                            }

                            for( int i = 0; i < FuFeiLvDays.size(); ++i ) {
                                Set< String > dayPay = dayUsersMap.get( FuFeiLvDays.get( i ) );
                                if( dayPay == null || dayPay.size() <= 0 ) continue;
                                for( String uidFw : dayPay ) {
                                    if( day0Pay.contains( uidFw ) ) {
                                        String fw = uidFw.split( "\n" )[1];
                                        List< Long > tmp =  map.get( fw ).retentions;
                                        tmp.set( i, tmp.get( i ) + 1 );
                                    }
                                }
                            }
                        }

                        sum.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if( sval.begin.equals( val.begin ) ) {
                                    sval.merge( val );
                                }
                            });
                        } );

                        return new ArrayList<>( map.values() );
                    } ) );
                }
                catch( Exception e ) {
                    throw new RuntimeException( "getFuFeiLvData error" );
                }
            } );
        }
        logger.debug( "size {} {}", sum.size(), detail.size() );
        return Arrays.asList( sum, detail );
    }

    public void queryFuFeiLv( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_USER_INSTALL_RETENTION, getFuFeiLvData( req, resp ),
                FuFeiLvTableHead, ( info, pw ) -> {
                    pw.write( "<tr>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.game + "</td>" );
                    pw.write( "<td>" + info.fw + "</td>" );
                    pw.write( "<td>" + SummaryAction.QuDao.get(info.fw) + "</td>" );
                    pw.write( "<td>" + info.pay + "</td>" );
                    info.retentions.forEach( retention -> {
                        pw.write( "<td>" + formatRatio( retention / (double) info.pay ) + "</td>" );
                    } );
                }, null );
    }

    public void downloadFuFeiLv( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_USER_INSTALL_RETENTION, getFuFeiLvData( req, resp ),
                FuFeiLvTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.game + "," );
                    pw.write( info.fw + "," );
                    pw.write( SummaryAction.QuDao.get(info.fw) + "," );
                    pw.write( info.pay + "," );
                    info.retentions.forEach( retention -> {
                        pw.write( formatRatio( retention / (double) info.pay ) + "," );
                    } );
                } );
    }

    private  List< List< UserView.InstallPayDays > > getshichangData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        TimeRange tr = TimeRange.parseFrom( req );
        String team = req.getParameter( "team" );
        String game = req.getParameter( "game" );
        logger.debug( "========== {} {} {} {}", team, game, tr.begin, tr.end );
        List< UserView.InstallPayDays > rv = new LinkedList<>();
        List< UserView.InstallPayDays > sum = new LinkedList<>();

        if( CommonUtil.isNoneEmpty( tr, team, game ) ) {
            List< String > games = new ArrayList< String >();
            if( "ALL".equals( game ) ) {
                GameCollection.getAll().forEach( gg -> {
                    if( gg.id.equals( team ) ) games.addAll( gg.games.keySet() );
                } );
            }
            else {
                games.add( game );
            }

            tr.timePoints.stream().reduce( ( beg, end ) -> {
                UserView.InstallPayDays sval = new UserView.InstallPayDays();
                sval.begin = TimeUtil.ymdFormat().format( beg );
                sval.end = TimeUtil.ymdFormat().format( end );
                sval.game = "所有游戏";
                sval.install = 0;
                shichangDays.forEach( day -> {
                    sval.pays.add( 0L );
                } );
                sum.add( sval );
                return end;
            } );

            games.forEach( g -> {
                String[] x = g.split( "-" );  // team-platform-appid
                List< String > PERM_LIST = Arrays.asList( "264" ,"252","1251807724","290","300","302" );
//                if( req.getSession().getAttribute( "userName" ).equals("shichangbu" ) && !PERM_LIST.contains( x[2] ) ) return;
//                if( req.getSession().getAttribute( "userName" ).equals("shoujibuyu" ) && !PERM_LIST.contains( x[2] ) ) return;
                try {
                    rv.addAll( getDataList2( req, resp, ( beg, end, c ) -> {

                        Map< String, UserView.InstallPayDays > map = new HashMap<>();
                        long endTimet = System.currentTimeMillis();
                        Map< String, Long > timeMap = ServiceManager.getCommonService().getPayPeopleMap( x[2], beg, end, end, c );
                        Map< String, Long > installMap = ServiceManager.getCommonService().getInstallMap( x[2], beg, end, c );
                        Map< String, Long > moneyMap = ServiceManager.getCommonService().getPayMoneyMap( x[2], beg, end, c );
                        installMap.forEach( ( k, v ) -> {
                            UserView.InstallPayDays val = new UserView.InstallPayDays();
                            val.fw = k;
                            if (k.equals("3311")) return;
                            val.begin = TimeUtil.ymdFormat().format( beg );
                            val.ciliu = ServiceManager.getSqlService().queryLong("select count(distinct user_id) from t_dau where timestamp > "+ end +" and timestamp <= "+ (end + 86400000) +" and user_id in (select user_id from t_install " +
                                    "where timestamp > "+ beg +" and timestamp <= "+ end +" and from_where = '"+ k +"' )",c);
                            val.end = TimeUtil.ymdFormat().format( end );
                            val.team = TeamCollection.getName( x[0] );
                            val.game = GameCollection.getName( g );
                            val.install = v;
                            map.put( k, val );
                        } );
                        moneyMap.forEach( ( k , v ) ->{
                            if(map.get(k) != null)
                            map.get(k).money = v;
                        });
                        timeMap.forEach( ( k, v ) -> {
                            int a = 0;
                            if(map.get(k) != null)
                            map.get( k ).PayPeople = v;
                        } );

                        long now = System.currentTimeMillis();
                        shichangDays.forEach( day -> {
                            long rBeg = beg;
                            long rEnd = beg + ( day + 1 ) * TimeUtil.DAY_MILLIS;
                            if( rEnd > now ) {
                                map.forEach( ( fw, val ) -> {
                                    val.pays.add( 0L );
                                } );
                                return;
                            }
                            Map< String, Long > payMoneyMap = ServiceManager.getCommonService().getPayMoneyMap( x[2], beg, end, rBeg, rEnd, c );
                            map.forEach( ( fw, val ) -> {
                                if (fw.equals("3311")) return;
                                val.money =getLong( payMoneyMap, fw ) ;
                                val.pays.add( getLong( payMoneyMap, fw ) );
                            } );
                        } );

                        sum.forEach( sval -> {
                            map.forEach( ( fw, val ) -> {
                                if (fw.equals("3311")) return;
                                if( sval.begin.equals( val.begin ) ) {
                                    sval.merge( val );
                                }
                            });
                        } );

                        return new ArrayList<>( map.values() );
                    } ) );
                }
                catch( Exception e ) {
                    throw new RuntimeException( "InstallPayDays error" );
                }
            } );
        }

        return Arrays.asList( sum, rv );
    }

    public void queryshichangDays( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_USER_SHICHANGBU, getshichangData( req, resp ),
                shichangTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.game + "</td>" );
                    pw.write( "<td>" + info.fw + "</td>" );
                    pw.write( "<td>" + SummaryAction.QuDao.get(info.fw) + "</td>" );
                    pw.write( "<td>" + info.PayPeople + "</td>" );
                    pw.write( "<td>" + info.money /10000+ "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    if (info.install != 0)
                    pw.write( "<td>" + formatRatio(((double) info.ciliu) / ((double)info.install)) + "</td>" );
                    else
                    {pw.write( "<td>" + " 0 " + "</td>" );}
                    info.pays.forEach( money -> {
                        pw.write( "<td>" + Currency.format(  money ) + "</td>" );
                    });
                } , null);
    }

    public void downloadshichangDays( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_USER_SHICHANGBU, getshichangData( req, resp ),
                shichangTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.game + "," );
                    pw.write( info.fw + "," );
                    pw.write( SummaryAction.QuDao.get(info.fw) + "," );
                    pw.write( info.PayPeople + "," );
                    pw.write( info.money /10000 + "," );
                    pw.write( info.install + "," );
                    if (info.install != 0)
                    pw.write( formatRatio(info.ciliu / info.install) + "," );
                    else
                    {pw.write( " 0 " + "," );}
                    info.pays.forEach( money -> {
                        pw.write( Currency.format( money ) + "," );
                    } );
                } );
    }
//
//    private List<List< UserView.PayDetail >> getPayDetailData(HttpServletRequest req, HttpServletResponse resp )
//            throws Exception {
//
//        return getData( req, resp, ( beg, end, c ) -> {
//            List< UserView.PayDetail > list = new LinkedList<>();
//            Map< Long, Long > payMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(money2 + money3) " +
//                    " from t_pay " +
//                    " where " + beg + " <= timestamp and timestamp < " + end +
//                    "  group by user_id;", c );
//
//            Map< Long, UserView.PayDetail > sum = new HashMap<>();
//
//            payMap.forEach(( k, v) -> {
//                if( sum.get( k ) == null ) sum.put( k, new UserView.PayDetail() );
//                sum.get( k ).money = v;
//            });
//            List< UserView.PayDetail > userList = new ArrayList<>();
//            sum.forEach( ( k, v ) -> {
//                v.beg = TimeUtil.ymdFormat().format( beg );
//                v.userId = k;
//                userList.add(v);
//            } );
//            Collections.sort( userList, ( o1, o2 ) -> {
//                if( o1.money > o2.money) return -1;
//                if( o1.money < o2.money ) return 1;
//                return 0;
//            } );
//            return Arrays.asList(userList);
//        } );
//    }
//
//    public void queryPayDetail( HttpServletRequest req, HttpServletResponse resp )
//            throws Exception {
//
//        queryMultiList( req, resp,
//                CommandList.GET_PAY_DETAIL, getPayDetailData( req, resp ),
//                payDetailTableHead, ( info, pw ) -> {
//                    pw.write( "<td>" + info.beg + "</td>" );
//                    pw.write( "<td>" + info.userId + "</td>" );
//                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
//                    pw.write( "<td>" + com.hoolai.ccgames.bi.protocol.Currency.format( info.money ) + "</td>" );
//                }, getFishUserNameScript( "td", "tdUserName_" ) );
//    }
//
//    public void downloadPayDetail( HttpServletRequest req, HttpServletResponse resp )
//            throws Exception {
//
//        downloadMultiList( req, resp,
//                CommandList.GET_PAY_DETAIL, getPayDetailData( req, resp ),
//                payDetailTableHead, ( info, pw ) -> {
//                    pw.write( info.beg + "," );
//                    pw.write( info.userId + "," );
//                    pw.write( getFishUserName( info.userId )  + "," );
//                    pw.write( com.hoolai.ccgames.bi.protocol.Currency.format( info.money ) + "," );
//                } );
//    }


        private List<List< UserView.PayDetail >> getPayDetailData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            List< UserView.PayDetail > list = new LinkedList<>();
            Map< Long, Long > payMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(money2 + money3) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );

            Map< Long, UserView.PayDetail > sum = new HashMap<>();

            payMap.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.PayDetail() );
                sum.get( k ).money = v;
            });

            List< UserView.PayDetail > userList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.beg = TimeUtil.ymdFormat().format( beg );
                v.userId = k;
                v.buy = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum(item_count) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and user_id = "+ k +" group by item_id;", c );
                v.zhuceshijian = ServiceManager.getSqlService().queryLong( "select timestamp " +
                        " from t_install " +
                        " where  user_id = "+ k +";", c );
                v.leijichongzhi = ServiceManager.getSqlService().queryLong( "select sum(money2 + money3) " +
                        " from t_pay " +
                        " where user_id = "+ k +";", c );
                userList.add(v);
            } );

            Collections.sort( userList, ( o1, o2 ) -> {
                if( o1.money > o2.money) return -1;
                if( o1.money < o2.money ) return 1;
                return 0;
            } );
            return Arrays.asList(userList);
        } );
    }

    public void queryPayDetail( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_FISH_MB, getPayDetailData( req, resp ),
                payDetailTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.beg + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" + UserSdk.getUserName(info.userId) + "</td>" );
                    pw.write( "<td>" + com.hoolai.ccgames.bi.protocol.Currency.format( info.money ) + "</td>" );
                    pw.write( "<td>" + TimeUtil.ymdFormat().format( info.zhuceshijian  )+ "</td>" );
                    pw.write( "<td>" + com.hoolai.ccgames.bi.protocol.Currency.format( info.leijichongzhi ) + "</td>" );
                    pw.write( "</tr><tr class ='dianji' >");
                    pw.write( "<td>用户名</td><td>道具名</td><td>道具数量</td><td>花费</td></tr>" );
                    info.buy.forEach( (k , v) -> {
                        pw.write( "<tr class ='dianji'><td>" + UserSdk.getUserName(info.userId) + "</td>" );
                        pw.write( "<td>" + ItemManager.getInstance().get(Integer.parseInt(k.toString())).item_name + "</td>" );
                        pw.write( "<td>" + v + "</td>" );
                        pw.write( "<td>" + (v  * ItemManager.getInstance().get(Integer.parseInt(k.toString())).buy.need_rmb) + "</td></tr>" );
                    });
                }, null);
    }

    public void downloadPayDetail( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadMultiList( req, resp,
                CommandList.GET_FISH_MB, getPayDetailData( req, resp ),
                payDetailTableHead, ( info, pw ) -> {
                    pw.write( info.beg + "," );
                    pw.write( info.userId + "," );
                    pw.write( getFishUserName( info.userId )  + "," );
                    pw.write( com.hoolai.ccgames.bi.protocol.Currency.format( info.money ) + "," );
                } );
    }


    private List<List< UserView.user_dantou >> getUserDanTouData( HttpServletRequest req, HttpServletResponse resp , long a )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            List< UserView.PayDetail > list = new LinkedList<>();
            //用户获得
            Map< Long, Long > user_get_jindan = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(item_count) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20017 and item_count > 0 group by user_id;", c );
            Map< Long, Long > user_get_yindan = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(item_count) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20016 and item_count > 0 group by user_id;", c );
            Map< Long, Long > user_get_qingdan = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(item_count) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20015 and item_count > 0 group by user_id;", c );
            //用户使用
            Map< Long, Long > user_use_jindan = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(item_count) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20017 and item_count < 0 group by user_id;", c );
            Map< Long, Long > user_use_yindan = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(item_count) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20016 and item_count < 0 group by user_id;", c );
            Map< Long, Long > user_use_qingdan = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(item_count) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20015 and item_count < 0 group by user_id;", c );
            //用户赠送
            Map< Long, Long > user_send_jindan = ServiceManager.getSqlService().queryMapLongLong( "select send_user_id, sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20017 and item_count > 0 group by send_user_id;", c );
            Map< Long, Long > user_send_yindan = ServiceManager.getSqlService().queryMapLongLong( "select send_user_id, sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20016 and item_count > 0 group by send_user_id;", c );
            Map< Long, Long > user_send_qingdan = ServiceManager.getSqlService().queryMapLongLong( "select send_user_id, sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20015 and item_count > 0 group by send_user_id;", c );
            //用户收取
            Map< Long, Long > user_shouqu_jindan = ServiceManager.getSqlService().queryMapLongLong( "select get_user_id, sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20017 and item_count > 0 group by get_user_id;", c );
            Map< Long, Long > user_shouqu_yindan = ServiceManager.getSqlService().queryMapLongLong( "select get_user_id, sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20016 and item_count > 0 group by get_user_id;", c );
            Map< Long, Long > user_shouqu_qingdan = ServiceManager.getSqlService().queryMapLongLong( "select get_user_id, sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20015 and item_count > 0 group by get_user_id;", c );
            //币商收
            Map< Long, Long > user_bishang_shou_jindan = ServiceManager.getSqlService().queryMapLongLong( "select send_user_id, sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20017 and item_count > 0 and get_user_id in (4649308,1972888,3729325,1600031999,1600009075,1600019525,1600178793,1600083717,1600022553,3478187) group by send_user_id;", c );
            Map< Long, Long > user_bishang_shou_yindan = ServiceManager.getSqlService().queryMapLongLong( "select send_user_id, sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20016 and item_count > 0 and get_user_id in (4649308,1972888,3729325,1600031999,1600009075,1600019525,1600178793,1600083717,1600022553,3478187 ) group by send_user_id;", c );
            Map< Long, Long > user_bishang_shou_qingdan = ServiceManager.getSqlService().queryMapLongLong( "select send_user_id, sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20015 and item_count > 0 and get_user_id in (4649308,1972888,3729325,1600031999,1600009075,1600019525,1600178793,1600083717,1600022553,3478187 ) group by send_user_id;", c );
            //币商处买
            Map< Long, Long > user_bishang_mai_jindan = ServiceManager.getSqlService().queryMapLongLong( "select get_user_id, sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20017 and item_count > 0 and send_user_id in (4649308,1972888,3729325,1600031999,1600009075,1600019525,1600178793,1600083717,1600022553,3478187 ) group by get_user_id;", c );
            Map< Long, Long > user_bishang_mai_yindan = ServiceManager.getSqlService().queryMapLongLong( "select get_user_id, sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20016 and item_count > 0 and send_user_id in (4649308,1972888,3729325,1600031999,1600009075,1600019525,1600178793,1600083717,1600022553,3478187) group by get_user_id;", c );
            Map< Long, Long > user_bishang_mai_qingdan = ServiceManager.getSqlService().queryMapLongLong( "select get_user_id, sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 20015 and item_count > 0 and send_user_id in (4649308,1972888,3729325,1600031999,1600009075,1600019525,1600178793,1600083717,1600022553,3478187) group by get_user_id;", c );
            //用户充值
            Map< Long, Long > user_pay = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(money2 + money3) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );

            Map< Long, Long > gold = ServiceManager.getSqlService().queryMapLongLong( "select t_exit.user_id, t_exit.gold from t_exit " +
                    "join " +
                    "( select user_id, max( timestamp ) as mt from t_exit where timestamp >= " + beg + " and timestamp < " + end + " group by user_id ) as A on t_exit.timestamp = A.mt and t_exit.user_id = A.user_id;", c );

//            Map< Long, Long > pot = ServiceManager.getSqlService().queryMapLongLong( "select t_exit.user_id,getValue( t_exit.user_info, 'user_fishBombPot') " +
//                    " from t_exit join" +
//                    "( select user_id, max( timestamp ) as mt from t_exit where timestamp >= " + beg + " and timestamp < " + end + " group by user_id ) as A on t_exit.timestamp = A.mt and t_exit.user_id = A.user_id;", c );


            Map< Long, UserView.user_dantou > sum = new HashMap<>();
//
//            pot.forEach(( k, v) -> {
//                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
//                sum.get(k).chizi = v;
//            });

            gold.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).gold = v;

            });
            //玩家游戏掉落
            user_get_jindan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).diaoluo_jin = v;
            });
            user_get_yindan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).diaoluo_yin = v;
            });
            user_get_qingdan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).diaoluo_tong = v;
            });
            //玩家使用
            user_use_jindan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).shiyong_jin = v;
            });
            user_use_yindan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).shiyong_yin = v;
            });
            user_use_qingdan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).shiyong_tong = v;
            });
            //用户赠送
            user_send_jindan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).song_jin = v;
            });
            user_send_yindan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).song_yin = v;
            });
            user_send_qingdan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).song_tong = v;
            });
            //用户收取
            user_shouqu_jindan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).jieshou_jin = v;
            });
            user_shouqu_yindan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).jieshou_yin = v;
            });
            user_shouqu_qingdan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).jieshou_tong = v;
            });
            //用户赠送币商
            user_bishang_shou_jindan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).bs_song_jin = v;
            });
            user_bishang_shou_yindan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).bs_song_yin = v;
            });
            user_bishang_shou_qingdan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).bs_song_tong = v;
            });
            //用户币商收取
            user_bishang_mai_jindan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).bs_jieshou_jin = v;
            });
            user_bishang_mai_yindan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).bs_jieshou_yin = v;
            });
            user_bishang_mai_qingdan.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).bs_jieshou_tong = v;
            });
            user_pay.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).money = v;

                Long  betMap = ServiceManager.getSqlService().queryLong( "select sum( bet_gold + bet_nb_gold + bet_xnb_gold ) " +
                        " from t_mtl2_bet " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                Long  winMap = ServiceManager.getSqlService().queryLong( "select  sum( win_gold ) " +
                        " from t_mtl2_bet " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                Long jackpotMap = ServiceManager.getSqlService().queryLong( "select  sum( jackpot ) " +
                        " from t_mtl2_bet " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                sum.get(k).MTLWin = winMap - betMap + jackpotMap;

                Long zhuangWin = ServiceManager.getSqlService().queryLong( "select  sum( chips - escape_pump ) " +
                        " from t_zfb_xiazhuang " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                Long zhuangBet = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                        " from t_zfb_shangzhuang " +" where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        "   and is_use_card = 'N';", c );

                Long xianWin = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                        " from t_zfb_win " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                Long xianBet = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                        " from t_zfb_bet " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                Long jackpotWin = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                        " from t_zfb_jackpot " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                sum.get(k).ZFBWin   = xianWin + zhuangWin + jackpotWin - zhuangBet - xianBet;

                Map< Long, Long > user_buy_item = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum(item_count) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = "+ k +" group by item_id;", c );


                sum.get( k ).chongzhiGold = getLong(user_buy_item ,20110 ) * (ItemManager.getInstance().get( 20110 ).worth + ItemManager.getInstance().get( 20110 ).buyAddGold)
                        + getLong(user_buy_item ,20111 ) * (ItemManager.getInstance().get( 20111 ).worth + ItemManager.getInstance().get( 20111 ).buyAddGold)
                        + getLong(user_buy_item ,20112 ) * (ItemManager.getInstance().get( 20112 ).worth + ItemManager.getInstance().get( 20112 ).buyAddGold)
                        + getLong(user_buy_item ,20113 ) * (ItemManager.getInstance().get( 20113 ).worth + ItemManager.getInstance().get( 20113 ).buyAddGold)
                        + getLong(user_buy_item ,20114 ) * (ItemManager.getInstance().get( 20114 ).worth + ItemManager.getInstance().get( 20114 ).buyAddGold)
                        + getLong(user_buy_item ,20115 ) * (ItemManager.getInstance().get( 20115 ).worth + ItemManager.getInstance().get( 20115 ).buyAddGold);

            });


            Map< Long, Long > user_fish_paiment = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(gold) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > user_fish_get = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(gold) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );


            user_fish_paiment.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).FishWin -= v;
            });
            user_fish_get.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.user_dantou() );
                sum.get( k ).FishWin += v;
            });



            List< UserView.user_dantou > userList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.beg = TimeUtil.ymdFormat().format( beg );
                v.userId = k;
                if (a == 1){
                    if (v.money != 0 || v.song_jin != 0 || v.jieshou_jin != 0)
                        userList.add(v);
                }
                if (a == 2 )
                {
                    if (v.money != 0 || v.diaoluo_jin != 0 || v.diaoluo_tong != 0 || v.diaoluo_yin != 0 || v.bs_song_jin != 0 || v.bs_song_yin != 0
                            || v.bs_song_tong != 0 || v.bs_jieshou_jin != 0 || v.bs_jieshou_yin != 0 || v.bs_jieshou_tong != 0 || v.song_jin != 0
                            || v.song_yin != 0 || v.song_tong != 0 || v.shiyong_yin != 0 || v.shiyong_jin != 0 || v.shiyong_tong != 0 )
                        userList.add(v);
                }
            } );
            Collections.sort( userList, ( o1, o2 ) -> {
                if( o1.diaoluo_jin > o2.diaoluo_jin) return -1;
                if( o1.diaoluo_jin < o2.diaoluo_jin ) return 1;
                if( o1.money > o2.money) return -1;
                if( o1.money < o2.money ) return 1;
                if( o1.jieshou_jin > o2.jieshou_jin) return -1;
                if( o1.jieshou_jin < o2.jieshou_jin ) return 1;
                if( o1.shiyong_jin > o2.shiyong_jin) return -1;
                if( o1.shiyong_jin < o2.shiyong_jin ) return 1;
                if( o1.song_jin > o2.song_jin) return -1;
                if( o1.song_jin < o2.song_jin ) return 1;
                return 0;
            } );
            return Arrays.asList(userList);
        } );
    }

    public void queryUserDanTou( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_USER_DANTOU, getUserDanTouData( req, resp ,1 ),
                userDanTouTableHead1, (info, pw ) -> {
                    pw.write( "<td>" + info.beg + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
//                    pw.write( "<td>" + getFishUserName( info.userId ).replace(",","").replace("   ","").replace("\"","") + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + com.hoolai.ccgames.bi.protocol.Currency.format( info.money ) + "</td>" );
                    pw.write( "<td>" + info.ZFBWin + "</td>" );
                    pw.write( "<td>" + info.MTLWin + "</td>" );
                    pw.write( "<td>" + info.FishWin + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + info.diaoluo_jin + "</td>" );
                    pw.write( "<td>" + info.jieshou_jin + "</td>" );
                    pw.write( "<td>" + info.song_jin + "</td>" );
                    pw.write( "<td>" + -info.shiyong_jin + "</td>" );
                    pw.write( "<td>" + info.diaoluo_yin + "</td>" );
                    pw.write( "<td>" + info.jieshou_yin + "</td>" );
                    pw.write( "<td>" + info.song_yin + "</td>" );
                    pw.write( "<td>" + -info.shiyong_yin + "</td>" );
                }, getFishUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadUserDanTou( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_USER_DANTOU, getUserDanTouData( req, resp ,2 ),
                userDanTouTableHead2, (info, pw ) -> {
                    pw.write( info.beg + "," );
                    pw.write( info.userId + "," );
                    pw.write( getFishUserName( info.userId ).replace(",","").replace("\"","")  + "," );
                    pw.write(  info.diaoluo_jin + "," );
                    pw.write(  info.diaoluo_yin + "," );
                    pw.write(  info.diaoluo_tong + "," );
                    pw.write(  info.jieshou_jin + "," );
                    pw.write(  info.jieshou_yin + "," );
                    pw.write(  info.jieshou_tong + "," );
                    pw.write(  info.song_jin + "," );
                    pw.write(  info.song_yin + "," );
                    pw.write(  info.song_tong + "," );
                    pw.write(  -info.shiyong_jin + "," );
                    pw.write(  -info.shiyong_yin + "," );
                    pw.write(  -info.shiyong_tong + "," );
                    pw.write(  info.bs_song_jin + "," );
                    pw.write(  info.bs_song_yin + "," );
                    pw.write(  info.bs_song_tong + "," );
                    pw.write(  info.bs_jieshou_jin + "," );
                    pw.write(  info.bs_jieshou_yin + "," );
                    pw.write(  info.bs_jieshou_tong + "," );
                    pw.write( com.hoolai.ccgames.bi.protocol.Currency.format( info.money ) + "," );
                } );
    }

    private List< SummaryView.baotu > getUserJinrongdata(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.baotu val = new SummaryView.baotu();
            val.begin = TimeUtil.ymdFormat().format( beg );

            List<String> a = ServiceManager.getSqlService().queryListString( "select items " +
                    " from t_possession " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );

            Map< Long, Long > getMap = new HashMap<>();
            a.forEach( info -> mergeMap( splitMap( info, ";", "," ), getMap ) );
            val.userHave              = getLong( getMap, 20017);
            val.userHave2             = getLong( getMap, 20016);

            val.people  = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                    " from t_possession " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " ;", c);

            val.gold  = ServiceManager.getSqlService().queryLong("select sum(gold) " +
                    " from t_possession " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " ;", c);

            return val;
        } );
    }

    public void queryUserHuiZong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_USER_JINRONG, getUserJinrongdata( req, resp ),
                UserHuizongTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.gold/10000 + "万</td>" );
                    pw.write( "<td>" + info.userHave + "</td>" );
                    pw.write( "<td>" + info.userHave2 + "</td>" );
                } );
    }



    public void downloadUserHuiZong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_SUMMARY_BAOTU, getUserJinrongdata( req, resp ),
                UserHuizongTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.gold + "," );
                    pw.write( info.userHave + "," );
                    pw.write( info.userHave2 + "," );
                } );
    }

    private List<List< UserView.GameWin >> getUserGameWinData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            List< UserView.PayDetail > list = new LinkedList<>();

            Map< Long, Long > user_pay = ServiceManager.getSqlService().queryMapLongLong( "select user_id, count(id) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            //捕鱼获得
            Map< Long, Long > user_fish_paiment = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(gold) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );
            Map< Long, Long > user_fish_get = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum(gold) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by user_id;", c );



            Map< Long, UserView.GameWin > sum = new HashMap<>();

            user_fish_paiment.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.GameWin() );
                sum.get( k ).FishWin -= v;
            });
            user_fish_get.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.GameWin() );
                sum.get( k ).FishWin += v;
            });
            user_pay.forEach(( k, v) -> {
                if( sum.get( k ) == null ) sum.put( k, new UserView.GameWin() );

                Long  betMap = ServiceManager.getSqlService().queryLong( "select sum( bet_gold + bet_nb_gold + bet_xnb_gold ) " +
                        " from t_mtl2_bet " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                Long  winMap = ServiceManager.getSqlService().queryLong( "select  sum( win_gold ) " +
                        " from t_mtl2_bet " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                Long jackpotMap = ServiceManager.getSqlService().queryLong( "select  sum( jackpot ) " +
                        " from t_mtl2_bet " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                sum.get(k).MTLWin = winMap - betMap + jackpotMap;

                Long zhuangWin = ServiceManager.getSqlService().queryLong( "select  sum( chips - escape_pump ) " +
                        " from t_zfb_xiazhuang " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                Long zhuangBet = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                        " from t_zfb_shangzhuang " +" where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        "   and is_use_card = 'N';", c );

                Long xianWin = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                        " from t_zfb_win " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                Long xianBet = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                        " from t_zfb_bet " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                Long jackpotWin = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                        " from t_zfb_jackpot " + " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        " ;", c );
                sum.get(k).ZFBWin   = xianWin + zhuangWin + jackpotWin - zhuangBet - xianBet;

                Map< Long, Long > user_buy_item = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum(item_count) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = "+ k +" group by item_id;", c );

//
//                sum.get( k ).chongzhiGold = getLong(user_buy_item ,20110 ) * (ItemManager.getInstance().get( 20110 ).worth + ItemManager.getInstance().get( 20110 ).buyAddGold)
//                            + getLong(user_buy_item ,20111 ) * (ItemManager.getInstance().get( 20111 ).worth + ItemManager.getInstance().get( 20111 ).buyAddGold)
//                            + getLong(user_buy_item ,20112 ) * (ItemManager.getInstance().get( 20112 ).worth + ItemManager.getInstance().get( 20112 ).buyAddGold)
//                            + getLong(user_buy_item ,20113 ) * (ItemManager.getInstance().get( 20113 ).worth + ItemManager.getInstance().get( 20113 ).buyAddGold)
//                            + getLong(user_buy_item ,20114 ) * (ItemManager.getInstance().get( 20114 ).worth + ItemManager.getInstance().get( 20114 ).buyAddGold)
//                            + getLong(user_buy_item ,20115 ) * (ItemManager.getInstance().get( 20115 ).worth + ItemManager.getInstance().get( 20115 ).buyAddGold);

            });

            List< UserView.GameWin > userList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.begin = TimeUtil.ymdFormat().format( beg );
                v.userId =  k;
                if (v.chongzhiGold != 0)
                userList.add(v);
            } );
            Collections.sort( userList, ( o1, o2 ) -> {
                if( o1.chongzhiGold > o2.chongzhiGold ) return -1;
                if( o1.chongzhiGold < o2.chongzhiGold ) return 1;
                if( o1.FishWin > o2.FishWin) return -1;
                if( o1.FishWin < o2.FishWin ) return 1;
                if( o1.ZFBWin > o2.ZFBWin) return -1;
                if( o1.ZFBWin < o2.ZFBWin ) return 1;
                if( o1.MTLWin > o2.MTLWin) return -1;
                if( o1.MTLWin < o2.MTLWin ) return 1;
                return 0;
            } );
            return Arrays.asList(userList);
        } );
    }

    public void queryUserGameWin( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_USER_GAME_WIN, getUserGameWinData( req, resp ),
                userGameWin , (info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" + getFishUserName( info.userId ).replace(",","").replace("\"","") + "</td>" );
                    pw.write( "<td>" + info.chongzhiGold + "</td>" );
                    pw.write( "<td>" + info.FishWin + "</td>" );
                    pw.write( "<td>" + info.ZFBWin + "</td>" );
                    pw.write( "<td>" + info.MTLWin + "</td>" );
                }, getFishUserNameScript( "td", "tdUserName_" ));
    }

    public void downloadUserGameWin( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_USER_GAME_WIN, getUserGameWinData( req, resp ),
                userGameWin, (info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.userId + "," );
                    pw.write( info.username + "," );
                    pw.write(  info.chongzhiGold + "," );
                    pw.write(  info.FishWin + "," );
                    pw.write(  info.ZFBWin + "," );
                    pw.write(  info.MTLWin + "," );
                } );
    }

    private List<UserView.UserPay>  getUserRMBData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        long PayMoney = Long.parseLong( req.getParameter( "userId" ).trim() ) * 10000;
        long jiage = Long.parseLong( req.getParameter( "jiage" ).trim() );
        return getDataList2( req, resp, ( beg, end, c ) -> {

            Map< Long, UserView.UserPay > user = new HashMap<>();

            Map< Long, Long > PayMoneyMap = ServiceManager.getSqlService().queryMapLongLong( "select a.user_id,a.p from " +
                    " ( select user_id , sum(money2 +money3 ) as p from t_pay group by user_id ) as a " +
                    " where a.p >= "+ PayMoney +";", c );

            PayMoneyMap.forEach( ( k,v ) -> {

                long dayBegin = beg;
                long dayEnd = end;
                UserView.UserPay val = new UserView.UserPay();
                val.userId = k;
                val.begin = TimeUtil.ymdFormat().format( beg );
                val.end = TimeUtil.ymdFormat().format( end );

                val.zengsong = ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
                        " from t_important_props " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and send_user_id = " + k +
                        " and item_id = 20017;", c );
                val.jieshou = ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
                        " from t_important_props " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and get_user_id = " + k +
                        " and item_id = 20017;", c );
                val.t_begin  = ServiceManager.getSqlService().queryLong( "select coalesce( getValue2(items,\"20017\"), 0 ) + gold/1000000 " +
                        " from t_possession " +
                        " where " + beg + " > timestamp   " +
                        " and user_id = " + k +
                        " and timestamp = (select max(timestamp) from t_possession +" +
                        " where " + beg + " > timestamp and timestamp < " + end + "  and user_id = "+ k +" );", c );

                val.t_end = ServiceManager.getSqlService().queryLong( "select coalesce( getValue2(items,\"20017\"), 0 ) + gold/1000000 " +
                        " from t_exit " +
                        " where " + beg + " < timestamp and timestamp > "+ end +
                        " and user_id = " + k +
                        " and timestamp = (select max(timestamp) from t_exit +" +
                        " where " + end + " > timestamp and timestamp > "+ beg +" and user_id = "+ k +" );", c );

                val.chongzhi = ServiceManager.getSqlService().queryLong( "select sum( money2+money3 ) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id = " + k +
                        ";", c );
                val.userName = UserSdk.getUserName( k );
                val.netWin = (val.zengsong - val.jieshou + val.t_end - val.t_begin) * jiage - val.chongzhi / 10000;
                user.put( k,val );

            });

            ArrayList< UserView.UserPay > rv = new ArrayList<>( user.values() );

            rv.sort( ( a, b ) -> {
                if( a.chongzhi > b.chongzhi ) return -1;
                if( a.chongzhi < b.chongzhi ) return 1;
                if( a.zengsong > b.zengsong ) return -1;
                if( a.zengsong < b.zengsong ) return 1;
                return 0;
            } );
            return rv;
        } );
    }

    public void queryUserRMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_USER_RMB, getUserRMBData( req, resp ),
                UserRMBTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "—" + info.end + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" + info.userName+ "</td>" );
                    pw.write( "<td>" + info.zengsong + "</td>" );
                    pw.write( "<td>" + info.jieshou + "</td>" );
                    if (info.t_begin == -1 ){pw.write( "<td>未登录</td>" );}
                    else{pw.write( "<td>" + info.t_begin + "</td>" );}
                    if (info.t_end == -1 ){pw.write( "<td>未退出</td>" );}
                    else{pw.write( "<td>" + info.t_begin + "</td>" );}
                    pw.write( "<td>" + info.chongzhi/10000 + "</td>" );
                    pw.write( "<td>" + info.netWin + "</td>" );
                }, null );
    }


}