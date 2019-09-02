package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.*;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.starter.Global;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.DdzView;
import com.hoolai.ccgames.bifront.vo.MjdrView;
import com.hoolai.ccgames.bifront.vo.baseView;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2016/9/1.
 */

@Service( "ddzAction" )
public class DdzAction extends BaseAction{

    private static final String APPID_STR = "1104787171";

    private static List< String > possessionTableHead = Arrays.asList( "日期",
            "登录人数", "元宝持有量" );
    private static List< String > rankTableHead = Arrays.asList( "日期",
            "ID", "用户名", "输赢额度" );
    private static List< String > multipleTableHead = new ArrayList<>();
    private static List< Integer > multiples = Arrays.asList( 1, 2, 3, 4 ,6,8,12,16,24,32,48,64,96,128,192,156,384,512,768,1024,1536 );

    private static List< String > funnelTableHead = new ArrayList<>();
    private static List< String > onlineTableHead = new ArrayList<>();
    private static List< Long > millisOnline = new ArrayList<>();
    private static List< String > phoneCardRetentionTableHead = new LinkedList<>();

    private static List< Integer > daysRetention = Arrays.asList( 1, 3, 5, 7, 14, 21, 30 );
    static {
        phoneCardRetentionTableHead.add( "日期" );
        phoneCardRetentionTableHead.add( "登陆奖励获得话费人数" );
        daysRetention.forEach( day -> phoneCardRetentionTableHead.add( day + "日留存" ) );
    }

    private static List< Integer > innings = Arrays.asList( 0, 1, 2, 3, 4, 7, 11, 16, 21, 26, 31, Integer.MAX_VALUE );
    private static List< Integer > minutesOnline = Arrays.asList( 0, 1, 2, 3, 4, 7, 11, 16, 21, 26, 31, Integer.MAX_VALUE );
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
        multipleTableHead.add( "日期" ) ;
        multiples.stream().forEach( multiple -> multipleTableHead.add( multiple + "倍" ) );
    }

    static {
        funnelTableHead.add( "日期" );
        funnelTableHead.add( "新注册用户" );
        funnelTableHead.add( "登录奖励人数" );
        funnelTableHead.add( "有金币变动" );
        innings.stream().reduce( ( beg, end ) -> {
            if( end == Integer.MAX_VALUE ) funnelTableHead.add( ( beg - 1 ) + "+手" );
            else if( beg + 1 == end ) funnelTableHead.add( beg + "手" );
            else funnelTableHead.add( beg + "-" + ( end - 1 ) + "手" );
            return end;
        } );
    }

    private static List< String > missionTableHead = Arrays.asList( "日期",
            "经验支出", "元宝支出", "体力支出", "藏宝图支出", "领奖次数", "领奖人数" );

    private static List< String > chouShuiTableHead = Arrays.asList( "日期", "初级抽水", "中级抽水", "高级抽水",
            "精英抽水","大师抽水","初级元宝","中级元宝","高级元宝","精英元宝","大师元宝","AI输赢","总元宝","总抽水");
    private static List< String > ddzPeopleTableHead = Arrays.asList( "日期", "初级人数","中级人数", "高级人数","精英人数","大师人数","总人数");
    private static List< String > ddzInningTableHead = Arrays.asList( "日期", "初级手数","中级手数", "高级手数","精英手数","大师手数","总手数");
    private static List< String > ddzChoujiangTableHead = Arrays.asList( "日期", "抽奖人数","抽奖次数", "游戏币支出","1元话费支出","2元话费支出", "5元话费支出",
            "10元话费支出");
    private static List< String > ddzBujiaoTableHead = Arrays.asList( "日期", "初级场","中级场", "高级场","精英场","大师场");

    private static List< String > ddzTaskTableHead = Arrays.asList( "日期", "总随机任务","总完成任务数量", "元宝产出","藏宝图产出","所进行的总手数","完成任务的手数");

    private static List< String > ddzTaskXiangxiTableHead = Arrays.asList( "日期", "三带", "炸2","炸3","炸4","炸5","炸6","炸7","炸8",
            "炸9","炸10","炸J","炸Q","炸K","炸A","火箭","单王","连对","飞机","四带","单2","单顺","底三个二","底牌王","底牌火箭","获胜");

    private static List< String > financeTableHead = Arrays.asList( "日期",
            "总人数", "斗地主总抽水", "随机任务支出", "抽奖支出",
            "斗地主收入");

    public void getFinancePage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_DDZ_FINANCE ).forward( req, resp );
    }

    public void getOnlinePage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_DDZ_ONLINE ).forward( req, resp );
    }

    public void getPhoneCardRetentionPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_DDZ_PHONE_CARD_RETENTION ).forward( req, resp );
    }

    public void getddzJichuPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_DDZ_JICHU ).forward( req, resp );
    }
    public void getddzMultPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_DDZ_MULT ).forward( req, resp );
    }
    public void getddzChoujiangPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_DDZ_CHOUJIANG ).forward( req, resp );
    }

    public void getddzLuckyWheelPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_DDZ_LUCKYWHEEL ).forward( req, resp );
    }

    public void getddzTaskPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_DDZ_TASK ).forward( req, resp );
    }

    public void getFunnelPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_DDZ_FUNNEL ).forward( req, resp );
    }

    public void getPossessionPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_DDZ_POSSESSION ).forward( req, resp );
    }

    public void getPayItemPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_DDZ_PAY_ITEM ).forward( req, resp );
    }

    public void getPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_DDZ ).forward( req, resp );
    }

    public void getRankPage (HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_DDZ_RANK).forward( req, resp );
    }

    public void getMissionPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_DDZ_MISSION ).forward( req, resp );
    }

    private static List< String > LiushuiTableHead = Arrays.asList( "日期",
            "初级场流水", "中级场流水", "高级场流水", "精英场流水", "大师场流水", "总流水" );
    public void getLiushuiPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_DDZ_LIUSHUI ).forward( req, resp );
    }
    private List< baseView.Liushui > getLiushuiData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.Liushui val = new baseView.Liushui();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            Map< Long, Long > dataMap = ServiceManager.getSqlService().queryMapLongLong( "select level, sum( gold ) " +
                    " from t_ddz_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 100 and is_robot ='N' and gold > 0" +
                    " group by level;", c );

            val.changci1 = getLong( dataMap, 1 );
            val.changci2 = getLong( dataMap, 2 );
            val.changci3 = getLong( dataMap, 3 );
            val.changci4 = getLong( dataMap, 4 );
            val.changci5 = getLong( dataMap, 5 );
            return val;
        } );
    }

    public void queryLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp ),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.changci1 + "</td>" );
                    pw.write( "<td>" + info.changci2 + "</td>" );
                    pw.write( "<td>" + info.changci3 + "</td>" );
                    pw.write( "<td>" + info.changci4 + "</td>" );
                    pw.write( "<td>" + info.changci5 + "</td>" );
                    pw.write( "<td>" + (info.changci1 + info.changci2 + info.changci3 + info.changci4 + info.changci5) + "</td>" );
                } );
    }

    public void downloadLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp ),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.changci1 + "," );
                    pw.write( info.changci2 + "," );
                    pw.write( info.changci3 + "," );
                    pw.write( info.changci4 + "," );
                    pw.write( info.changci5 + "," );
                    pw.write( (info.changci1 + info.changci2 + info.changci3 + info.changci4 + info.changci5) + "," );
                } );
    }

    private List< List< DdzView.Rank > > getRankData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        int limit = Integer.parseInt( req.getParameter( "limit" ) );
        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_ddz_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot = 'N' group by user_id;", c );

            Map< Long, DdzView.Rank > sum = new HashMap<>();


            winMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new DdzView.Rank() );
                sum.get( k ).gold = v;
            } );


            List< DdzView.Rank > winList = new ArrayList<>();
            List< DdzView.Rank > loseList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.userId = k;

                if( v.gold >= limit ) winList.add( v );
                else if( v.gold <= -limit ) loseList.add( v );
            } );

            Collections.sort( winList, ( o1, o2 ) -> {
                if( o1.gold > o2.gold ) return -1;
                if( o1.gold < o2.gold ) return 1;
                return 0;
            } );

            Collections.sort( loseList, ( o1, o2 ) -> {
                if( o1.gold < o2.gold ) return -1;
                if( o1.gold > o2.gold ) return 1;
                return 0;
            } );

            return Arrays.asList( winList, loseList );
        } );
    }


    public void queryRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_MJDR, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                        pw.write( "<td>" + info.begin + "</td>" );
                        pw.write( "<td>" + info.userId + "</td>" );
                        pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                        pw.write( "<td>" + info.gold + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_MJDR, getRankData( req, resp),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( info.gold + "," );
                } );
    }

    private List< DdzView.Possession > getPossessionData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.Possession val = new DdzView.Possession();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.dau = ServiceManager.getCommonService().getDau( APPID_STR, beg, end, false, c );
            val.gold = ServiceManager.getCommonService().getPossessionGold( APPID_STR, beg, end, false, c );
            return val;
        } );
    }

    public void queryPossession( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_DDZ, getPossessionData( req, resp ),
                possessionTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.dau + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                } );
    }

    public void downloadPossession( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_DDZ, getPossessionData( req, resp ),
                possessionTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.dau + "," );
                    pw.write( info.gold + "," );
                } );
    }

    private List< DdzView.PayItem > getPayItemData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.PayItem val = new DdzView.PayItem();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.people = ServiceManager.getCommonService().getPayPeople( APPID_STR, beg, end, c );
            val.items = ServiceManager.getCommonService().getPayItemMap( APPID_STR, beg, end, c );
            val.total = ServiceManager.getCommonService().getPayMoney( APPID_STR, beg, end, c );
            return val;
        } );
    }

    public void queryPayItem( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< DdzView.PayItem > data = getPayItemData( req, resp );
        Set< Long > itemIds = new TreeSet<>();
        data.forEach( info -> itemIds.addAll( info.items.keySet() ) );
        List< String > payItemTableHead = new LinkedList<>();
        payItemTableHead.add( "日期" );
        payItemTableHead.add( "购买人数" );
        itemIds.forEach( id -> payItemTableHead.add( Global.itemRegistry.getOrId( id ) ) );
        payItemTableHead.add( "总价值" );
        queryList( req, resp,
                CommandList.GET_DDZ, data,
                payItemTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    itemIds.forEach( id -> pw.write( "<td>" + getLong( info.items, id ) + "</td>" ) );
                    pw.write( "<td>" + com.hoolai.ccgames.bi.protocol.Currency.format( info.total ) + "</td>" );
                } );
    }

    public void downloadPayItem( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< DdzView.PayItem > data = getPayItemData( req, resp );
        Set< Long > itemIds = new TreeSet<>();
        data.forEach( info -> itemIds.addAll( info.items.keySet() ) );
        List< String > payItemTableHead = new LinkedList<>();
        payItemTableHead.add( "日期" );
        payItemTableHead.add( "购买人数" );
        itemIds.forEach( id -> payItemTableHead.add( Global.itemRegistry.getOrId( id ) ) );
        payItemTableHead.add( "总价值" );
        downloadList( req, resp,
                CommandList.GET_DDZ, data,
                payItemTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    itemIds.forEach( id -> pw.write( getLong( info.items, id ) + "," ) );
                    pw.write( com.hoolai.ccgames.bi.protocol.Currency.format( info.total ) + "," );
                } );
    }

    private List< DdzView.Online > getOnlineData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.Online val = new DdzView.Online();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.install = ServiceManager.getCommonService().getInstall( APPID_STR, beg, end, isMobile, c );
            Map< Long, Long > onlineMap = ServiceManager.getSqlService().queryMapLongLong( "select A.user_id, A.online " +
                    " from ( select user_id, sum( millis ) as online " +
                    "        from t_online " +
                    "        where appid='"+ APPID_STR + " ' and " + beg + " <= timestamp and timestamp < " + end +
                    "        group by user_id ) as A " +
                    " inner join " +
                    "      ( select user_id " +
                    "        from t_install " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "          and appid = '" + APPID_STR + "' and is_mobile = '" + boolString( isMobile ) + "' ) as B " +
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
                CommandList.GET_DDZ, getOnlineData( req, resp, false ),
                onlineTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    info.onlinePeople.forEach( p -> pw.write( "<td>" + p + "</td>" ) );
                } );
    }

    public void downloadOnline( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_DDZ, getOnlineData( req, resp, false ),
                onlineTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install + "," );
                    info.onlinePeople.forEach( p -> pw.write( p + "," ) );
                } );
    }

    private List< DdzView.Funnel > getFunnelData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.Funnel val = new DdzView.Funnel();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.install = ServiceManager.getCommonService().getInstall( APPID_STR, beg, end, isMobile, c );
            val.grantLoginPeople = ServiceManager.getCommonService().getGrantLoginPeople( APPID_STR, beg, end, isMobile, c );
            val.goldChangePeople = ServiceManager.getSqlService().queryLong( "select count( A.user_id ) " +
                    " from ( select distinct user_id " +
                    "        from t_ddz_rake " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "           ) as A " +
                    " inner join " +
                    "      ( select user_id " +
                    "        from t_install " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "          and appid = '" + APPID_STR + "' and is_mobile = '" + boolString( isMobile ) + "' ) as B " +
                    " on A.user_id = B.user_id;", c );
            Map< Long, Long > inningMap = ServiceManager.getSqlService().queryMapLongLong( "select A.user_id, A.cnt " +
                    " from ( select user_id, count( id ) as cnt " +
                    "        from t_ddz_win " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "        group by user_id ) as A " +
                    " inner join " +
                    "      ( select user_id " +
                    "        from t_install " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "          and appid = '" + APPID_STR + "' and is_mobile = '" + boolString( isMobile ) + "' ) as B " +
                    " on A.user_id = B.user_id;", c );
            innings.stream().reduce( ( b, e ) -> {
                long people = inningMap.values().stream().filter( v -> b <= v && v < e ).count();
                val.inningPeople.add( people );
                return e;
            } );

            return val;
        } );
    }

    public void queryFunnel( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< DdzView.Funnel > data = getFunnelData( req, resp, false );

        queryList( req, resp,
                CommandList.GET_DDZ,
                data, funnelTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    pw.write( "<td>" + info.grantLoginPeople + "</td>" );
                    pw.write( "<td>" + info.goldChangePeople + "</td>" );
                    info.inningPeople.forEach( i -> pw.write( "<td>" + i + "</td>" ) );
                },null );
    }

    public void downloadFunnel( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< DdzView.Funnel > data = getFunnelData( req, resp, false );

        downloadList( req, resp,
                CommandList.GET_DDZ,
                data, funnelTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install + "," );
                    pw.write( info.grantLoginPeople + "," );
                    pw.write( info.goldChangePeople + "," );
                    info.inningPeople.forEach( i -> pw.write( i + "," ) );
                });
    }


    private List< DdzView.Mission > getMissionData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.Mission val = new DdzView.Mission();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.exp = ServiceManager.getMissionService().getExp( APPID_STR, beg, end, false, c );

            List<String> getinfo = ServiceManager.getSqlService().queryListString( "select bonus " +
                    " from t_task_finish " +
                    " where appid ='"+ APPID_STR + "' and type = 0  and "  + beg + " <= timestamp and timestamp < " + end + ";", c );

            Map< Long, Long > getMap = new HashMap<>();
            /**
             * 道具id -1为元宝
             *
             * */

            getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
            val.gold             = getLong( getMap, -1 );
            val.hp             = getLong( getMap, -2 );
            val.exp             = getLong( getMap, -3 );
            val.treasure         = getLong( getMap, 10301 );


            val.count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_task_finish " +
                    " where type = 0 and appid = '" + APPID_STR + "' and " + beg + " <= timestamp and timestamp < " + end +
                    "   " +
                    " ;", c );
            val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_task_finish " +
                    " where type = 0 and appid ='" + APPID_STR + "' and " + beg + " <= timestamp and timestamp < " + end +
                    "  ;", c );
            return val;
        } );
    }

    public void queryMission( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_DDZ, getMissionData( req, resp, false ),
                missionTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.exp + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + info.hp + "</td>" );
                    pw.write( "<td>" + info.treasure + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                } );
    }

    public void downloadMission( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_DDZ, getMissionData( req, resp, false ),
                missionTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.exp + "," );
                    pw.write( info.gold + "," );
                    pw.write( info.hp + "," );
                    pw.write( info.treasure + "," );
                    pw.write( info.count + "," );
                    pw.write( info.people + "," );
                } );
    }


    private List< DdzView.Finance > getFinanceData(HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.Finance val = new DdzView.Finance();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
//            val.grantInstall = ServiceManager.getCommonService().getGrantInstallGold( APPID_STR, beg, end, isMobile, c );
//            val.grantLogin = ServiceManager.getCommonService().getGrantLoginGold( APPID_STR, beg, end, isMobile, c );
//            val.grantHelp = ServiceManager.getCommonService().getGrantHelpGold( APPID_STR, beg, end, isMobile, c );

            val.people  = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_ddz_rake " +
                    " where "  + beg + " <= timestamp and timestamp <  " + end + ";", c );

            List<String> getinfo = ServiceManager.getSqlService().queryListString( "select bonus " +
                    " from t_task_finish " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type =100 ;", c );

            Map< Long, Long > getMap = new HashMap<>();
            /**
             * 道具id -1为元宝
             *        -2为体力
             * */
            getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
            val.missionPay             = getLong( getMap, -1 );
//            val.baotu            = getLong( getMap, 10301 );

            val.choujiang = ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
                    " from t_ddz_choujiang " +
                    " where "  + beg + " <= timestamp and timestamp <  " + end + " and item_id = -1;", c );

            val.ddzShouru = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_ddz_rake  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   ;", c );

            val.total = val.ddzShouru  - val.choujiang - val.missionPay ;
            return val;
        } );

    }

    public void queryFinance( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_DDZ, getFinanceData( req, resp, false ),
                financeTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.ddzShouru + "</td>" );
                    pw.write( "<td>" + info.missionPay + "</td>" );
                    pw.write( "<td>" + info.choujiang + "</td>" );
                    pw.write( "<td>" + info.total + "</td>" );
                } );
    }

    public void downloadFinance( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_DDZ, getFinanceData( req, resp, false ),
                financeTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.ddzShouru  + "," );
                    pw.write( info.missionPay + "," );
                    pw.write( info.choujiang + "," );
                    pw.write( info.total + "," );
                } );
    }


    private List< DdzView.ddzChouJiang > getChoujiangData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.ddzChouJiang val = new DdzView.ddzChouJiang();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.people  = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_ddz_choujiang " +
                    " where "  + beg + " <= timestamp and timestamp <  " + end + " ;", c );

            val.count  = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_ddz_choujiang " +
                    " where "  + beg + " <= timestamp and timestamp <  " + end + " ;", c );
            val.gold  = ServiceManager.getSqlService().queryLong( "select sum(item_count) " +
                    " from t_ddz_choujiang " +
                    " where "  + beg + " <= timestamp and timestamp <  " + end + " and item_id = -1;", c );
            val.card1  = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_ddz_choujiang " +
                    " where "  + beg + " <= timestamp and timestamp <  " + end + " and item_id = 10515;", c );
            val.card2  = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_ddz_choujiang " +
                    " where "  + beg + " <= timestamp and timestamp <  " + end + " and item_id = 10516;", c );
            val.card5  = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_ddz_choujiang " +
                    " where "  + beg + " <= timestamp and timestamp <  " + end + " and item_id = 10517;", c );
            val.card10  = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_ddz_choujiang " +
                    " where "  + beg + " <= timestamp and timestamp <  " + end + " and item_id = 10518;", c );


            return val;
        } );
    }

    public void queryChoujiang( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_DDZ, getChoujiangData( req, resp ),
                ddzChoujiangTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + info.card1 + "</td>" );
                    pw.write( "<td>" + info.card2 + "</td>" );
                    pw.write( "<td>" + info.card5 + "</td>" );
                    pw.write( "<td>" + info.card10 + "</td>" );
                } );
    }

    public void downloadChoujiang( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_DDZ, getChoujiangData( req, resp ),
                ddzChoujiangTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.count + "," );
                    pw.write( info.gold + "," );
                    pw.write( info.card1 + "," );
                    pw.write( info.card2 + "," );
                    pw.write( info.card5 + "," );
                    pw.write( info.card10 + "," );
                } );
    }

    private List< DdzView.ddzTask > getTaskData(HttpServletRequest req, HttpServletResponse resp ,int level)
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.ddzTask val = new DdzView.ddzTask();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.zongsuiji = 3 * ServiceManager.getSqlService().queryLong( "select count( distinct inning_id ) " +
                    " from t_ddz_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and type = 100 and level ="+ level +";", c );
            val.zongwancheng = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and game_id = 1005 and level ="+ level + ";", c );
            val.zongshoushu = ServiceManager.getSqlService().queryLong( "select count( distinct inning_id  ) " +
                    " from t_ddz_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and level ="+ level +";", c );

            val.finishZong = ServiceManager.getSqlService().queryLong( "select count( distinct inning_id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and game_id = 1005 and level ="+ level +";", c );


            List<String> getinfo = ServiceManager.getSqlService().queryListString( "select bonus " +
                    " from t_task_finish " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and game_id =1005 and level ="+ level +" ;", c );

            Map< Long, Long > getMap = new HashMap<>();
            /**
             * 道具id -1为元宝
             *        -2为体力
             * */
            getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
            val.Gold             = getLong( getMap, -1 );
            val.baotu            = getLong( getMap, 10301 );

            return val;

        } );
    }

    private List< DdzView.ddzTask > getTaskData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.ddzTask val = new DdzView.ddzTask();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.zongsuiji = 3 * ServiceManager.getSqlService().queryLong( "select count( distinct inning_id ) " +
                    " from t_ddz_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 100;", c );
            val.zongwancheng = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and game_id = 1005 and level != 0 ; ", c );
            val.zongshoushu = ServiceManager.getSqlService().queryLong( "select count( distinct inning_id  ) " +
                    " from t_ddz_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 100;", c );

            val.finishZong = ServiceManager.getSqlService().queryLong( "select count( distinct inning_id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and game_id = 1005 and level != 0;", c );


            List<String> getinfo = ServiceManager.getSqlService().queryListString( "select bonus " +
                    " from t_task_finish " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1005 and level != 0;", c );

            Map< Long, Long > getMap = new HashMap<>();
            /**
             * 道具id -1为元宝
             *        -2为体力
             * */

                getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
                val.Gold             = getLong( getMap, -1 );
                val.baotu            = getLong( getMap, 10301 );

            return val;

        } );
    }

    private List< DdzView.ddzTaskXX > getTaskXXData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.ddzTaskXX val = new DdzView.ddzTaskXX();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.zha2 = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050003  ", c );
            val.zha3 = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050004  ;", c );
            val.zha4 = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050005  ;", c );
            val.zha5 = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050006  ;", c );
            val.zha6 = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050007  ;", c );
            val.zha7 = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050008  ;", c );
            val.zha8 = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050009  ;", c );
            val.zha9 = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050010  ;", c );
            val.zha10 = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050011  ;", c );
            val.zhaJ = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050012  ;", c );
            val.zhaQ = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050013  ;", c );
            val.zhaK = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050014  ;", c );
            val.zhaA = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050002  ;", c );
            val.sandai = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050001  ;", c );
            val.huojian = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050015  ;", c );
            val.danwang = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050016  ;", c );
            val.liandui = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050017  ;", c );
            val.feiji = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050018  ;", c );
            val.sidai = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050019  ;", c );
            val.dan2 = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050020  ;", c );
            val.danshun = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050021  ;", c );
            val.san2 = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050022  ;", c );
            val.zhuawang = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050023  ;", c );
            val.zhuahuojian = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050024  ;", c );
            val.win = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and task_id =10050025  ;", c );



            return val;
        } );
    }
//"日期", "总随机任务","总完成任务数量", "元宝产出","藏宝图产出","所进行的总手数","完成任务的手数");
// "日期", "三带", "炸2","炸3","炸4","炸5","炸6","炸7","炸8",
// "炸9","炸10","炸J","炸Q","炸K","炸A","火箭","单王","连对","飞机","四带","单2","单顺","底三个二","底牌王","底牌火箭","获胜"

    public void queryTask( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_DDZ, getTaskData( req, resp ),
                ddzTaskTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zongsuiji + "</td>" );
                    pw.write( "<td>" + info.zongwancheng + "</td>" );
                    pw.write( "<td>" + info.Gold + "</td>" );
                    pw.write( "<td>" + info.baotu + "</td>" );
                    pw.write( "<td>" + info.zongshoushu + "</td>" );
                    pw.write( "<td>" + info.finishZong + "</td>" );
                }, getTaskData( req, resp ,1),
                ddzTaskTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zongsuiji + "</td>" );
                    pw.write( "<td>" + info.zongwancheng + "</td>" );
                    pw.write( "<td>" + info.Gold + "</td>" );
                    pw.write( "<td>" + info.baotu + "</td>" );
                    pw.write( "<td>" + info.zongshoushu + "</td>" );
                    pw.write( "<td>" + info.finishZong + "</td>" );
                }
                , getTaskData( req, resp ,2),
                ddzTaskTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zongsuiji + "</td>" );
                    pw.write( "<td>" + info.zongwancheng + "</td>" );
                    pw.write( "<td>" + info.Gold + "</td>" );
                    pw.write( "<td>" + info.baotu + "</td>" );
                    pw.write( "<td>" + info.zongshoushu + "</td>" );
                    pw.write( "<td>" + info.finishZong + "</td>" );
                }
                , getTaskData( req, resp ,3),
                ddzTaskTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zongsuiji + "</td>" );
                    pw.write( "<td>" + info.zongwancheng + "</td>" );
                    pw.write( "<td>" + info.Gold + "</td>" );
                    pw.write( "<td>" + info.baotu + "</td>" );
                    pw.write( "<td>" + info.zongshoushu + "</td>" );
                    pw.write( "<td>" + info.finishZong + "</td>" );
                }
                , getTaskData( req, resp ,4),
                ddzTaskTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zongsuiji + "</td>" );
                    pw.write( "<td>" + info.zongwancheng + "</td>" );
                    pw.write( "<td>" + info.Gold + "</td>" );
                    pw.write( "<td>" + info.baotu + "</td>" );
                    pw.write( "<td>" + info.zongshoushu + "</td>" );
                    pw.write( "<td>" + info.finishZong + "</td>" );
                }
                , getTaskData( req, resp ,5),
                ddzTaskTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zongsuiji + "</td>" );
                    pw.write( "<td>" + info.zongwancheng + "</td>" );
                    pw.write( "<td>" + info.Gold + "</td>" );
                    pw.write( "<td>" + info.baotu + "</td>" );
                    pw.write( "<td>" + info.zongshoushu + "</td>" );
                    pw.write( "<td>" + info.finishZong + "</td>" );
                },getTaskXXData( req, resp ),
                ddzTaskXiangxiTableHead, ( info, pw ) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.sandai + "</td>");
                    pw.write("<td>" + info.zha2 + "</td>");
                    pw.write("<td>" + info.zha3 + "</td>");
                    pw.write("<td>" + info.zha4 + "</td>");
                    pw.write("<td>" + info.zha5 + "</td>");
                    pw.write("<td>" + info.zha6 + "</td>");
                    pw.write("<td>" + info.zha7 + "</td>");
                    pw.write("<td>" + info.zha8 + "</td>");
                    pw.write("<td>" + info.zha9 + "</td>");
                    pw.write("<td>" + info.zha10 + "</td>");
                    pw.write("<td>" + info.zhaJ + "</td>");
                    pw.write("<td>" + info.zhaQ + "</td>");
                    pw.write("<td>" + info.zhaK + "</td>");
                    pw.write("<td>" + info.zhaA + "</td>");
                    pw.write("<td>" + info.huojian + "</td>");
                    pw.write("<td>" + info.danwang + "</td>");
                    pw.write("<td>" + info.liandui + "</td>");
                    pw.write("<td>" + info.feiji + "</td>");
                    pw.write("<td>" + info.sidai + "</td>");
                    pw.write("<td>" + info.dan2 + "</td>");
                    pw.write("<td>" + info.danshun + "</td>");
                    pw.write("<td>" + info.san2 + "</td>");
                    pw.write("<td>" + info.zhuawang + "</td>");
                    pw.write("<td>" + info.zhuahuojian + "</td>");
                    pw.write("<td>" + info.win + "</td>");
                },null);
    }

    public void downloadTask( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_DDZ, getTaskData( req, resp ),
                ddzTaskTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zongsuiji + "," );
                    pw.write( info.zongwancheng + "," );
                    pw.write( info.Gold + "," );
                    pw.write( info.baotu + "," );
                    pw.write( info.zongshoushu + "," );
                    pw.write( info.finishZong + "," );
                },getTaskData( req, resp ,1),
                ddzTaskTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zongsuiji + "," );
                    pw.write( info.zongwancheng + "," );
                    pw.write( info.Gold + "," );
                    pw.write( info.baotu + "," );
                    pw.write( info.zongshoushu + "," );
                    pw.write( info.finishZong + "," );
                },getTaskData( req, resp ,2),
                ddzTaskTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zongsuiji + "," );
                    pw.write( info.zongwancheng + "," );
                    pw.write( info.Gold + "," );
                    pw.write( info.baotu + "," );
                    pw.write( info.zongshoushu + "," );
                    pw.write( info.finishZong + "," );
                },
                getTaskData( req, resp ,3),
                ddzTaskTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zongsuiji + "," );
                    pw.write( info.zongwancheng + "," );
                    pw.write( info.Gold + "," );
                    pw.write( info.baotu + "," );
                    pw.write( info.zongshoushu + "," );
                    pw.write( info.finishZong + "," );
                },
                getTaskData( req, resp ,4),
                ddzTaskTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zongsuiji + "," );
                    pw.write( info.zongwancheng + "," );
                    pw.write( info.Gold + "," );
                    pw.write( info.baotu + "," );
                    pw.write( info.zongshoushu + "," );
                    pw.write( info.finishZong + "," );
                },
                getTaskData( req, resp ,5),
                ddzTaskTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zongsuiji + "," );
                    pw.write( info.zongwancheng + "," );
                    pw.write( info.Gold + "," );
                    pw.write( info.baotu + "," );
                    pw.write( info.zongshoushu + "," );
                    pw.write( info.finishZong + "," );
                },getTaskXXData( req, resp ),
                ddzTaskXiangxiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.sandai + "," );
                    pw.write( info.zha2 + "," );
                    pw.write( info.zha3 + "," );
                    pw.write( info.zha4 + "," );
                    pw.write( info.zha5 + "," );
                    pw.write( info.zha6 + "," );
                    pw.write( info.zha7 + "," );
                    pw.write( info.zha8 + "," );
                    pw.write( info.zha9 + "," );
                    pw.write( info.zha10 + "," );
                    pw.write( info.zhaJ + "," );
                    pw.write( info.zhaQ + "," );
                    pw.write( info.zhaK + "," );
                    pw.write( info.zhaA + "," );
                    pw.write( info.huojian + "," );
                    pw.write( info.danwang + "," );
                    pw.write( info.liandui + "," );
                    pw.write( info.feiji + "," );
                    pw.write( info.sidai + "," );
                    pw.write( info.dan2 + "," );
                    pw.write( info.danshun + "," );
                    pw.write( info.san2 + "," );
                    pw.write( info.zhuawang + "," );
                    pw.write( info.zhuahuojian + "," );
                    pw.write( info.win + "," );
                } );
    }


    private List< DdzView.ddzChoushui > getchoushuiData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.ddzChoushui val = new DdzView.ddzChoushui();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            Map< Long, Long > dataMap = ServiceManager.getSqlService().queryMapLongLong( "select level, sum( gold ) " +
                    " from t_ddz_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 100 and is_robot ='N' and gold > 0" +
                    " group by level;", c );

            val.chujiGold = getLong( dataMap, 1 );
            val.zhongjiGold = getLong( dataMap, 2 );
            val.gaojiGold = getLong( dataMap, 3 );
            val.jingyingGold = getLong( dataMap, 4 );
            val.dashiGold = getLong( dataMap, 5 );

            dataMap = ServiceManager.getSqlService().queryMapLongLong( "select level, sum( gold ) " +
                    " from t_ddz_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 100 and is_robot ='N' " +
                    " group by level;", c );

            val.chujiChoushui = getLong( dataMap, 1 );
            val.zhongjiChoushui = getLong( dataMap, 2 );
            val.gaojiChoushui = getLong( dataMap, 3 );
            val.jingyingChoushui = getLong( dataMap, 4 );
            val.dashiChoushui = getLong( dataMap, 5 );

            val.zongyuabao = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_ddz_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type =100 and gold > 0 and is_robot ='N';", c );
            val.zongchoushui = val.chujiChoushui + val.zhongjiChoushui +
                    val.gaojiChoushui + val.jingyingChoushui + val.dashiChoushui;
            val.AIwin = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_ddz_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type =100 and is_robot ='Y';", c );
            return val;
        } );
    }

    private List< DdzView.ddzPeople > getPeopleData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.ddzPeople val = new DdzView.ddzPeople();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            Map< Long, Long > dataMap = ServiceManager.getSqlService().queryMapLongLong( "select level, count( distinct user_id ) " +
                    " from t_ddz_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 100 and is_robot ='N' " +
                    " group by level;", c );

            val.chuji = getLong( dataMap, 1 );
            val.zhongji = getLong( dataMap, 2 );
            val.gaoji = getLong( dataMap, 3 );
            val.jingying = getLong( dataMap, 4 );
            val.dashi = getLong( dataMap, 5 );
            val.zong = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_ddz_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type =100 and is_robot ='N';", c );

            return val;
        } );
    }

    private List< DdzView.ddzNum > getNumData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.ddzNum val = new DdzView.ddzNum();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            Map< Long, Long > dataMap = ServiceManager.getSqlService().queryMapLongLong( "select level, count( distinct inning_id ) " +
                    " from t_ddz_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 100 and is_robot ='N' " +
                    " group by level;", c );

            val.chuji = getLong( dataMap, 1 );
            val.zhongji = getLong( dataMap, 2 );
            val.gaoji = getLong( dataMap, 3 );
            val.jingying = getLong( dataMap, 4 );
            val.dashi = getLong( dataMap, 5 );

            val.zong = val.chuji + val.zhongji +
                    val.gaoji + val.jingying + val.dashi;

            return val;
        } );
    }
    private List< DdzView.ddzBujiao > getBujiaoData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.ddzBujiao val = new DdzView.ddzBujiao();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            Map< Long, Long > dataMap = ServiceManager.getSqlService().queryMapLongLong( "select level, count( distinct inning_id ) " +
                    " from t_ddz_multiple  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 100 and multiple = 0" +
                    " group by level;", c );

            val.chuji = getLong( dataMap, 1 );
            val.zhongji = getLong( dataMap, 2 );
            val.gaoji = getLong( dataMap, 3 );
            val.jingying = getLong( dataMap, 4 );
            val.dashi = getLong( dataMap, 5 );

            return val;
        } );
    }


    public void queryJichu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_DDZ, getPeopleData( req, resp ),
                ddzPeopleTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.chuji + "</td>" );
                    pw.write( "<td>" + info.zhongji + "</td>" );
                    pw.write( "<td>" + info.gaoji + "</td>" );
                    pw.write( "<td>" + info.jingying + "</td>" );
                    pw.write( "<td>" + info.dashi + "</td>" );
                    pw.write( "<td>" + info.zong + "</td>" );
                } ,getNumData( req, resp ),
                ddzInningTableHead, ( info, pw ) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.chuji + "</td>");
                    pw.write("<td>" + info.zhongji + "</td>");
                    pw.write("<td>" + info.gaoji + "</td>");
                    pw.write("<td>" + info.jingying + "</td>");
                    pw.write("<td>" + info.dashi + "</td>");
                    pw.write( "<td>" + info.zong + "</td>" );
                },getchoushuiData( req, resp ),
                chouShuiTableHead, ( info, pw ) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.chujiChoushui + "</td>");
                    pw.write("<td>" + info.zhongjiChoushui + "</td>");
                    pw.write("<td>" + info.gaojiChoushui + "</td>");
                    pw.write("<td>" + info.jingyingChoushui + "</td>");
                    pw.write("<td>" + info.dashiChoushui + "</td>");
                    pw.write("<td>" + info.chujiGold + "</td>");
                    pw.write("<td>" + info.zhongjiGold + "</td>");
                    pw.write("<td>" + info.gaojiGold + "</td>");
                    pw.write("<td>" + info.jingyingGold + "</td>");
                    pw.write("<td>" + info.dashiGold + "</td>");
                    pw.write("<td>" + info.AIwin + "</td>");
                    pw.write("<td>" + info.zongyuabao + "</td>");
                    pw.write("<td>" + info.zongchoushui + "</td>");
                },getBujiaoData( req, resp ),
                ddzBujiaoTableHead, ( info, pw ) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.chuji + "</td>");
                    pw.write("<td>" + info.zhongji + "</td>");
                    pw.write("<td>" + info.gaoji + "</td>");
                    pw.write("<td>" + info.jingying + "</td>");
                    pw.write("<td>" + info.dashi + "</td>");
                },null);
    }

    public void downloadJichu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_DDZ, getPeopleData( req, resp ),
                ddzPeopleTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.chuji + "," );
                    pw.write( info.zhongji + "," );
                    pw.write( info.gaoji + "," );
                    pw.write( info.jingying + "," );
                    pw.write( info.dashi + "," );
                    pw.write( info.zong + "," );
                },getNumData( req, resp ),
                ddzInningTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.chuji + "," );
                    pw.write( info.zhongji + "," );
                    pw.write( info.gaoji + "," );
                    pw.write( info.jingying + "," );
                    pw.write( info.dashi + "," );
                    pw.write( info.zong + "," );
                },getchoushuiData( req, resp ),
                chouShuiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.chujiChoushui + "," );
                    pw.write( info.zhongjiChoushui + "," );
                    pw.write( info.gaojiChoushui + "," );
                    pw.write( info.jingyingChoushui + "," );
                    pw.write( info.dashiChoushui + "," );
                    pw.write( info.chujiGold + "," );
                    pw.write( info.zhongjiGold + "," );
                    pw.write( info.gaojiGold + "," );
                    pw.write( info.jingyingGold + "," );
                    pw.write( info.dashiGold + "," );
                    pw.write( info.AIwin + "," );
                    pw.write( info.zongyuabao + "," );
                    pw.write( info.zongchoushui + "," );
                } );
    }

    private List< DdzView.ddzMult > getMultData(HttpServletRequest req, HttpServletResponse resp, int level )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.ddzMult val = new DdzView.ddzMult();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            Map< Long, Long > mulInnings = ServiceManager.getSqlService().queryMapLongLong( "select multiple, count(distinct inning_id) "
                    + " from t_ddz_multiple "
                    + " where " + beg + " <= timestamp and timestamp < " + end
                    + "   and level = " + level
                    + " group by multiple;", c );
            multiples.stream().forEach( multiple -> val.multiples.add( getLong( mulInnings, multiple ) ) );
            return val;
        } );
    }

    public void queryMult( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_DDZ,
                Arrays.asList( getMultData( req, resp, 1 ),
                        getMultData( req, resp, 2 ),
                        getMultData( req, resp, 3 ),
                        getMultData( req, resp, 4 ),
                        getMultData( req, resp, 5 ) ),
                multipleTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    info.multiples.forEach( multiple -> pw.write( "<td>" + multiple + "</td>" ) );
                }, null);
    }

    public void downloadMult( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_DDZ,
                Arrays.asList( getMultData( req, resp, 1 ),
                        getMultData( req, resp, 2 ),
                        getMultData( req, resp, 3 ),
                        getMultData( req, resp, 4 ),
                        getMultData( req, resp, 5 ) ),
                multipleTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    info.multiples.forEach( multiple -> pw.write( multiple + "," ) );
                } );
    }

    private List< DdzView.ddzLuckyWheelBonusCount > getLuckyWheelData(HttpServletRequest req, HttpServletResponse resp, int level )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            DdzView.ddzLuckyWheelBonusCount val = new DdzView.ddzLuckyWheelBonusCount();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.id2count = ServiceManager.getSqlService().queryMapLongLong("select choujiang_id, count(id) "
                    + " from t_ddz_choujiang "
                    + " where " + beg + " <= timestamp and timestamp < " + end
                    + "   and level = " + level
                    + " group by choujiang_id;", c );

            return val;
        } );
    }

    public static Map< Integer, AbstractMap.SimpleEntry< String, Integer > > luckyWheelId2desc = new HashMap<>();
    static {
        luckyWheelId2desc.put( 10050001, new AbstractMap.SimpleEntry< String, Integer >( "5元宝", 1 ) );
        luckyWheelId2desc.put( 10050002, new AbstractMap.SimpleEntry< String, Integer >( "5元宝", 2 ) );
        luckyWheelId2desc.put( 10050003, new AbstractMap.SimpleEntry< String, Integer >( "5元宝", 3 ) );
        luckyWheelId2desc.put( 10050004, new AbstractMap.SimpleEntry< String, Integer >( "15元宝", 1 ) );
        luckyWheelId2desc.put( 10050005, new AbstractMap.SimpleEntry< String, Integer >( "15元宝", 2 ) );
        luckyWheelId2desc.put( 10050006, new AbstractMap.SimpleEntry< String, Integer >( "15元宝", 3 ) );
        luckyWheelId2desc.put( 10050007, new AbstractMap.SimpleEntry< String, Integer >( "25元宝", 1 ) );
        luckyWheelId2desc.put( 10050008, new AbstractMap.SimpleEntry< String, Integer >( "25元宝", 2 ) );
        luckyWheelId2desc.put( 10050009, new AbstractMap.SimpleEntry< String, Integer >( "25元宝", 3 ) );
        luckyWheelId2desc.put( 10050010, new AbstractMap.SimpleEntry< String, Integer >( "25元宝", 1 ) );
        luckyWheelId2desc.put( 10050011, new AbstractMap.SimpleEntry< String, Integer >( "25元宝", 2 ) );
        luckyWheelId2desc.put( 10050012, new AbstractMap.SimpleEntry< String, Integer >( "25元宝", 3 ) );
        luckyWheelId2desc.put( 10050013, new AbstractMap.SimpleEntry< String, Integer >( "50元宝", 1 ) );
        luckyWheelId2desc.put( 10050014, new AbstractMap.SimpleEntry< String, Integer >( "50元宝", 2 ) );
        luckyWheelId2desc.put( 10050015, new AbstractMap.SimpleEntry< String, Integer >( "50元宝", 3 ) );
        luckyWheelId2desc.put( 10050016, new AbstractMap.SimpleEntry< String, Integer >( "50元宝", 1 ) );
        luckyWheelId2desc.put( 10050017, new AbstractMap.SimpleEntry< String, Integer >( "50元宝", 2 ) );
        luckyWheelId2desc.put( 10050018, new AbstractMap.SimpleEntry< String, Integer >( "50元宝", 3 ) );
        luckyWheelId2desc.put( 10050019, new AbstractMap.SimpleEntry< String, Integer >( "80元宝", 1 ) );
        luckyWheelId2desc.put( 10050020, new AbstractMap.SimpleEntry< String, Integer >( "80元宝", 2 ) );
        luckyWheelId2desc.put( 10050021, new AbstractMap.SimpleEntry< String, Integer >( "80元宝", 3 ) );
        luckyWheelId2desc.put( 10050022, new AbstractMap.SimpleEntry< String, Integer >( "100元宝", 1 ) );
        luckyWheelId2desc.put( 10050023, new AbstractMap.SimpleEntry< String, Integer >( "100元宝", 2 ) );
        luckyWheelId2desc.put( 10050024, new AbstractMap.SimpleEntry< String, Integer >( "100元宝", 3 ) );
        luckyWheelId2desc.put( 10050025, new AbstractMap.SimpleEntry< String, Integer >( "1元话费", 1 ) );
        luckyWheelId2desc.put( 10050026, new AbstractMap.SimpleEntry< String, Integer >( "1元话费", 1 ) );
        luckyWheelId2desc.put( 10050027, new AbstractMap.SimpleEntry< String, Integer >( "20元宝", 1 ) );
        luckyWheelId2desc.put( 10050028, new AbstractMap.SimpleEntry< String, Integer >( "20元宝", 2 ) );
        luckyWheelId2desc.put( 10050029, new AbstractMap.SimpleEntry< String, Integer >( "20元宝", 3 ) );
        luckyWheelId2desc.put( 10050030, new AbstractMap.SimpleEntry< String, Integer >( "60元宝", 1 ) );
        luckyWheelId2desc.put( 10050031, new AbstractMap.SimpleEntry< String, Integer >( "60元宝", 2 ) );
        luckyWheelId2desc.put( 10050032, new AbstractMap.SimpleEntry< String, Integer >( "60元宝", 3 ) );
        luckyWheelId2desc.put( 10050033, new AbstractMap.SimpleEntry< String, Integer >( "100元宝", 1 ) );
        luckyWheelId2desc.put( 10050034, new AbstractMap.SimpleEntry< String, Integer >( "100元宝", 2 ) );
        luckyWheelId2desc.put( 10050035, new AbstractMap.SimpleEntry< String, Integer >( "100元宝", 3 ) );
        luckyWheelId2desc.put( 10050036, new AbstractMap.SimpleEntry< String, Integer >( "100元宝", 1 ) );
        luckyWheelId2desc.put( 10050037, new AbstractMap.SimpleEntry< String, Integer >( "100元宝", 2 ) );
        luckyWheelId2desc.put( 10050038, new AbstractMap.SimpleEntry< String, Integer >( "100元宝", 3 ) );
        luckyWheelId2desc.put( 10050039, new AbstractMap.SimpleEntry< String, Integer >( "200元宝", 1 ) );
        luckyWheelId2desc.put( 10050040, new AbstractMap.SimpleEntry< String, Integer >( "200元宝", 2 ) );
        luckyWheelId2desc.put( 10050041, new AbstractMap.SimpleEntry< String, Integer >( "200元宝", 3 ) );
        luckyWheelId2desc.put( 10050042, new AbstractMap.SimpleEntry< String, Integer >( "200元宝", 1 ) );
        luckyWheelId2desc.put( 10050043, new AbstractMap.SimpleEntry< String, Integer >( "200元宝", 2 ) );
        luckyWheelId2desc.put( 10050044, new AbstractMap.SimpleEntry< String, Integer >( "200元宝", 3 ) );
        luckyWheelId2desc.put( 10050045, new AbstractMap.SimpleEntry< String, Integer >( "320元宝", 1 ) );
        luckyWheelId2desc.put( 10050046, new AbstractMap.SimpleEntry< String, Integer >( "320元宝", 2 ) );
        luckyWheelId2desc.put( 10050047, new AbstractMap.SimpleEntry< String, Integer >( "320元宝", 3 ) );
        luckyWheelId2desc.put( 10050048, new AbstractMap.SimpleEntry< String, Integer >( "400元宝", 1 ) );
        luckyWheelId2desc.put( 10050049, new AbstractMap.SimpleEntry< String, Integer >( "400元宝", 2 ) );
        luckyWheelId2desc.put( 10050050, new AbstractMap.SimpleEntry< String, Integer >( "400元宝", 3 ) );
        luckyWheelId2desc.put( 10050051, new AbstractMap.SimpleEntry< String, Integer >( "1元话费", 1 ) );
        luckyWheelId2desc.put( 10050052, new AbstractMap.SimpleEntry< String, Integer >( "1元话费", 1 ) );
        luckyWheelId2desc.put( 10050053, new AbstractMap.SimpleEntry< String, Integer >( "100元宝", 1 ) );
        luckyWheelId2desc.put( 10050054, new AbstractMap.SimpleEntry< String, Integer >( "100元宝", 3 ) );
        luckyWheelId2desc.put( 10050055, new AbstractMap.SimpleEntry< String, Integer >( "100元宝", 5 ) );
        luckyWheelId2desc.put( 10050056, new AbstractMap.SimpleEntry< String, Integer >( "300元宝", 1 ) );
        luckyWheelId2desc.put( 10050057, new AbstractMap.SimpleEntry< String, Integer >( "300元宝", 3 ) );
        luckyWheelId2desc.put( 10050058, new AbstractMap.SimpleEntry< String, Integer >( "300元宝", 5 ) );
        luckyWheelId2desc.put( 10050059, new AbstractMap.SimpleEntry< String, Integer >( "500元宝", 1 ) );
        luckyWheelId2desc.put( 10050060, new AbstractMap.SimpleEntry< String, Integer >( "500元宝", 3 ) );
        luckyWheelId2desc.put( 10050061, new AbstractMap.SimpleEntry< String, Integer >( "500元宝", 5 ) );
        luckyWheelId2desc.put( 10050062, new AbstractMap.SimpleEntry< String, Integer >( "500元宝", 1 ) );
        luckyWheelId2desc.put( 10050063, new AbstractMap.SimpleEntry< String, Integer >( "500元宝", 3 ) );
        luckyWheelId2desc.put( 10050064, new AbstractMap.SimpleEntry< String, Integer >( "500元宝", 5 ) );
        luckyWheelId2desc.put( 10050065, new AbstractMap.SimpleEntry< String, Integer >( "1000元宝", 1 ) );
        luckyWheelId2desc.put( 10050066, new AbstractMap.SimpleEntry< String, Integer >( "1000元宝", 3 ) );
        luckyWheelId2desc.put( 10050067, new AbstractMap.SimpleEntry< String, Integer >( "1000元宝", 5 ) );
        luckyWheelId2desc.put( 10050068, new AbstractMap.SimpleEntry< String, Integer >( "1000元宝", 1 ) );
        luckyWheelId2desc.put( 10050069, new AbstractMap.SimpleEntry< String, Integer >( "1000元宝", 3 ) );
        luckyWheelId2desc.put( 10050070, new AbstractMap.SimpleEntry< String, Integer >( "1000元宝", 5 ) );
        luckyWheelId2desc.put( 10050071, new AbstractMap.SimpleEntry< String, Integer >( "1600元宝", 1 ) );
        luckyWheelId2desc.put( 10050072, new AbstractMap.SimpleEntry< String, Integer >( "1600元宝", 3 ) );
        luckyWheelId2desc.put( 10050073, new AbstractMap.SimpleEntry< String, Integer >( "1600元宝", 5 ) );
        luckyWheelId2desc.put( 10050074, new AbstractMap.SimpleEntry< String, Integer >( "2000元宝", 1 ) );
        luckyWheelId2desc.put( 10050075, new AbstractMap.SimpleEntry< String, Integer >( "2000元宝", 3 ) );
        luckyWheelId2desc.put( 10050076, new AbstractMap.SimpleEntry< String, Integer >( "2000元宝", 5 ) );
        luckyWheelId2desc.put( 10050077, new AbstractMap.SimpleEntry< String, Integer >( "2元话费", 1 ) );
        luckyWheelId2desc.put( 10050078, new AbstractMap.SimpleEntry< String, Integer >( "2元话费", 1 ) );
        luckyWheelId2desc.put( 10050079, new AbstractMap.SimpleEntry< String, Integer >( "300元宝", 2 ) );
        luckyWheelId2desc.put( 10050080, new AbstractMap.SimpleEntry< String, Integer >( "300元宝", 4 ) );
        luckyWheelId2desc.put( 10050081, new AbstractMap.SimpleEntry< String, Integer >( "300元宝", 6 ) );
        luckyWheelId2desc.put( 10050082, new AbstractMap.SimpleEntry< String, Integer >( "900元宝", 2 ) );
        luckyWheelId2desc.put( 10050083, new AbstractMap.SimpleEntry< String, Integer >( "900元宝", 4 ) );
        luckyWheelId2desc.put( 10050084, new AbstractMap.SimpleEntry< String, Integer >( "900元宝", 6 ) );
        luckyWheelId2desc.put( 10050085, new AbstractMap.SimpleEntry< String, Integer >( "1500元宝", 2 ) );
        luckyWheelId2desc.put( 10050086, new AbstractMap.SimpleEntry< String, Integer >( "1500元宝", 4 ) );
        luckyWheelId2desc.put( 10050087, new AbstractMap.SimpleEntry< String, Integer >( "1500元宝", 6 ) );
        luckyWheelId2desc.put( 10050088, new AbstractMap.SimpleEntry< String, Integer >( "1500元宝", 2 ) );
        luckyWheelId2desc.put( 10050089, new AbstractMap.SimpleEntry< String, Integer >( "1500元宝", 4 ) );
        luckyWheelId2desc.put( 10050090, new AbstractMap.SimpleEntry< String, Integer >( "1500元宝", 6 ) );
        luckyWheelId2desc.put( 10050091, new AbstractMap.SimpleEntry< String, Integer >( "3000元宝", 2 ) );
        luckyWheelId2desc.put( 10050092, new AbstractMap.SimpleEntry< String, Integer >( "3000元宝", 4 ) );
        luckyWheelId2desc.put( 10050093, new AbstractMap.SimpleEntry< String, Integer >( "3000元宝", 6 ) );
        luckyWheelId2desc.put( 10050094, new AbstractMap.SimpleEntry< String, Integer >( "3000元宝", 2 ) );
        luckyWheelId2desc.put( 10050095, new AbstractMap.SimpleEntry< String, Integer >( "3000元宝", 4 ) );
        luckyWheelId2desc.put( 10050096, new AbstractMap.SimpleEntry< String, Integer >( "3000元宝", 6 ) );
        luckyWheelId2desc.put( 10050097, new AbstractMap.SimpleEntry< String, Integer >( "4800元宝", 2 ) );
        luckyWheelId2desc.put( 10050098, new AbstractMap.SimpleEntry< String, Integer >( "4800元宝", 4 ) );
        luckyWheelId2desc.put( 10050099, new AbstractMap.SimpleEntry< String, Integer >( "4800元宝", 6 ) );
        luckyWheelId2desc.put( 10050100, new AbstractMap.SimpleEntry< String, Integer >( "6000元宝", 2 ) );
        luckyWheelId2desc.put( 10050101, new AbstractMap.SimpleEntry< String, Integer >( "6000元宝", 4 ) );
        luckyWheelId2desc.put( 10050102, new AbstractMap.SimpleEntry< String, Integer >( "6000元宝", 6 ) );
        luckyWheelId2desc.put( 10050103, new AbstractMap.SimpleEntry< String, Integer >( "5元话费", 1 ) );
        luckyWheelId2desc.put( 10050104, new AbstractMap.SimpleEntry< String, Integer >( "5元话费", 1 ) );
        luckyWheelId2desc.put( 10050105, new AbstractMap.SimpleEntry< String, Integer >( "1000元宝", 3 ) );
        luckyWheelId2desc.put( 10050106, new AbstractMap.SimpleEntry< String, Integer >( "1000元宝", 5 ) );
        luckyWheelId2desc.put( 10050107, new AbstractMap.SimpleEntry< String, Integer >( "1000元宝", 7 ) );
        luckyWheelId2desc.put( 10050108, new AbstractMap.SimpleEntry< String, Integer >( "3000元宝", 3 ) );
        luckyWheelId2desc.put( 10050109, new AbstractMap.SimpleEntry< String, Integer >( "3000元宝", 5 ) );
        luckyWheelId2desc.put( 10050110, new AbstractMap.SimpleEntry< String, Integer >( "3000元宝", 7 ) );
        luckyWheelId2desc.put( 10050111, new AbstractMap.SimpleEntry< String, Integer >( "5000元宝", 3 ) );
        luckyWheelId2desc.put( 10050112, new AbstractMap.SimpleEntry< String, Integer >( "5000元宝", 5 ) );
        luckyWheelId2desc.put( 10050113, new AbstractMap.SimpleEntry< String, Integer >( "5000元宝", 7 ) );
        luckyWheelId2desc.put( 10050114, new AbstractMap.SimpleEntry< String, Integer >( "5000元宝", 3 ) );
        luckyWheelId2desc.put( 10050115, new AbstractMap.SimpleEntry< String, Integer >( "5000元宝", 5 ) );
        luckyWheelId2desc.put( 10050116, new AbstractMap.SimpleEntry< String, Integer >( "5000元宝", 7 ) );
        luckyWheelId2desc.put( 10050117, new AbstractMap.SimpleEntry< String, Integer >( "10000元宝", 3 ) );
        luckyWheelId2desc.put( 10050118, new AbstractMap.SimpleEntry< String, Integer >( "10000元宝", 5 ) );
        luckyWheelId2desc.put( 10050119, new AbstractMap.SimpleEntry< String, Integer >( "10000元宝", 7 ) );
        luckyWheelId2desc.put( 10050120, new AbstractMap.SimpleEntry< String, Integer >( "10000元宝", 3 ) );
        luckyWheelId2desc.put( 10050121, new AbstractMap.SimpleEntry< String, Integer >( "10000元宝", 5 ) );
        luckyWheelId2desc.put( 10050122, new AbstractMap.SimpleEntry< String, Integer >( "10000元宝", 7 ) );
        luckyWheelId2desc.put( 10050123, new AbstractMap.SimpleEntry< String, Integer >( "16000元宝", 3 ) );
        luckyWheelId2desc.put( 10050124, new AbstractMap.SimpleEntry< String, Integer >( "16000元宝", 5 ) );
        luckyWheelId2desc.put( 10050125, new AbstractMap.SimpleEntry< String, Integer >( "16000元宝", 7 ) );
        luckyWheelId2desc.put( 10050126, new AbstractMap.SimpleEntry< String, Integer >( "20000元宝", 3 ) );
        luckyWheelId2desc.put( 10050127, new AbstractMap.SimpleEntry< String, Integer >( "20000元宝", 5 ) );
        luckyWheelId2desc.put( 10050128, new AbstractMap.SimpleEntry< String, Integer >( "20000元宝", 7 ) );
        luckyWheelId2desc.put( 10050129, new AbstractMap.SimpleEntry< String, Integer >( "10元话费", 1 ) );
        luckyWheelId2desc.put( 10050130, new AbstractMap.SimpleEntry< String, Integer >( "10元话费", 1 ) );
    }

    public void queryLuckyWheel( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        final int ID_START = 10050001;
        final int ID_END = 10050130;
        final int ID_PER_LEVEL = 26;
        List< List< Integer > > tableIds = new ArrayList<>();
        List< List< String > > tableHeads = new ArrayList<>();
        for( int id = ID_START; id <= ID_END; id += ID_PER_LEVEL ) {
            List< String > tableHead = new ArrayList<>();
            tableHead.add( "日期" );
            List< Integer > tableId = new ArrayList<>();
            IntStream.range( id, id + ID_PER_LEVEL ).forEach( x -> {
                tableId.add( x );
                AbstractMap.SimpleEntry< String, Integer > desc = luckyWheelId2desc.get( x );
                tableHead.add( desc.getKey() + "*" + desc.getValue() );
            } );
            tableIds.add( tableId );
            tableHeads.add( tableHead );
        }

        queryMultiList( req, resp,
                CommandList.GET_DDZ,
                getLuckyWheelData( req, resp, 1 ), tableHeads.get( 0 ), ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    tableIds.get( 0 ).forEach( id -> pw.write( "<td>" + getLong( info.id2count, id ) + "</td>" ) );
                },
                getLuckyWheelData( req, resp, 2 ), tableHeads.get( 1 ), ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    tableIds.get( 1 ).forEach( id -> pw.write( "<td>" + getLong( info.id2count, id ) + "</td>" ) );
                },
                getLuckyWheelData( req, resp, 3 ), tableHeads.get( 2 ), ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    tableIds.get( 2 ).forEach( id -> pw.write( "<td>" + getLong( info.id2count, id ) + "</td>" ) );
                },
                getLuckyWheelData( req, resp, 4 ), tableHeads.get( 3 ), ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    tableIds.get( 3 ).forEach( id -> pw.write( "<td>" + getLong( info.id2count, id ) + "</td>" ) );
                },
                getLuckyWheelData( req, resp, 5 ), tableHeads.get( 4 ), ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    tableIds.get( 4 ).forEach( id -> pw.write( "<td>" + getLong( info.id2count, id ) + "</td>" ) );
                },
                null);
    }

    public void downloadLuckyWheel( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        final int ID_START = 10050001;
        final int ID_END = 10050130;
        final int ID_PER_LEVEL = 26;
        List< List< Integer > > tableIds = new ArrayList<>();
        List< List< String > > tableHeads = new ArrayList<>();
        for( int id = ID_START; id <= ID_END; id += ID_PER_LEVEL ) {
            List< String > tableHead = Arrays.asList( "日期" );
            List< Integer > tableId = new ArrayList<>();
            IntStream.range( id, id + ID_PER_LEVEL ).forEach( x -> {
                tableId.add( x );
                AbstractMap.SimpleEntry< String, Integer > desc = luckyWheelId2desc.get( x );
                tableHead.add( desc.getKey() + "*" + desc.getValue() );
            } );
            tableIds.add( tableId );
            tableHeads.add( tableHead );
        }

        downloadMultiList( req, resp,
                CommandList.GET_DDZ,
                getLuckyWheelData( req, resp, 1 ), tableHeads.get( 0 ), ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    tableIds.get( 0 ).forEach( id -> pw.write( getLong( info.id2count, id ) + "," ) );
                },
                getLuckyWheelData( req, resp, 2 ), tableHeads.get( 1 ), ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    tableIds.get( 1 ).forEach( id -> pw.write( getLong( info.id2count, id ) + "," ) );
                },
                getLuckyWheelData( req, resp, 3 ), tableHeads.get( 2 ), ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    tableIds.get( 2 ).forEach( id -> pw.write( getLong( info.id2count, id ) + "," ) );
                },
                getLuckyWheelData( req, resp, 4 ), tableHeads.get( 3 ), ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    tableIds.get( 3 ).forEach( id -> pw.write( getLong( info.id2count, id ) + "," ) );
                },
                getLuckyWheelData( req, resp, 5 ), tableHeads.get( 4 ), ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    tableIds.get( 4 ).forEach( id -> pw.write( getLong( info.id2count, id ) + "," ) );
                } );
    }

    private List< MjdrView.PhoneCardRetention > getPhoneCardRetentionData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.PhoneCardRetention val = new MjdrView.PhoneCardRetention();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.getCardPeople = ServiceManager.getSqlService().queryLong( "select count( A.user_id ) " +
                    " from ( select distinct user_id " +
                    "        from t_grants2 " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "        and appid = '" + APPID_STR + "' and type <= 2 " +
                    "        and 10100 <= item_id and item_id <= 10108 ) as A " +
                    " inner join " +
                    "      ( select user_id " +
                    "        from t_install " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "          and appid = '" + APPID_STR + "' ) as B " +
                    " on A.user_id = B.user_id;", c );

            daysRetention.forEach( day -> {
                        long beg2 = beg + TimeUtil.DAY_MILLIS * day;
                        long end2 = beg2 + TimeUtil.DAY_MILLIS;
                        val.retentions.add( ServiceManager.getSqlService()
                                .queryLong( "select count( C.user_id ) " +
                                        " from ( select A.user_id " +
                                        "        from ( select distinct user_id " +
                                        "               from t_grants2 " +
                                        "               where " + beg + " <= timestamp and timestamp < " + end +
                                        "               and appid = '" + APPID_STR + "' and type <= 2 " +
                                        "               and 10100 <= item_id and item_id <= 10108 ) as A " +
                                        "        inner join " +
                                        "             ( select user_id " +
                                        "               from t_install " +
                                        "               where " + beg + " <= timestamp and timestamp < " + end +
                                        "                 and appid = '" + APPID_STR + "' ) as B " +
                                        "        on A.user_id = B.user_id ) as C " +
                                        " inner join " +
                                        "      ( select distinct user_id " +
                                        "        from t_dau " +
                                        "        where " + beg2 + " <= timestamp and timestamp < " + end2 +
                                        "          and appid = '" + APPID_STR + "' ) as D " +
                                        " on C.user_id = D.user_id;", c ) );
                    }
            );

            return val;
        } );
    }

    public void queryPhoneCardRetention( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_DDZ, getPhoneCardRetentionData( req, resp ),
                phoneCardRetentionTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.getCardPeople + "</td>" );
                    info.retentions.forEach( retention -> pw.write( "<td>" + formatRatio( retention / (double) info.getCardPeople ) + "</td>" ) );
                } );
    }

    public void downloadPhoneCardRetention( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_DDZ, getPhoneCardRetentionData( req, resp ),
                phoneCardRetentionTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.getCardPeople + "," );
                    info.retentions.forEach( retention -> pw.write( formatRatio( retention / (double) info.getCardPeople ) + "," ) );
                } );
    }


}
