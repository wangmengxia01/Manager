package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.net.BiClient;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.servlet.GameCollection;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.ProductView;
import com.hoolai.centersdk.item.ItemIdUtils;
import com.hoolai.centersdk.sdk.AppSdk;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service( "productAction" )
public class ProductAction extends BaseAction {

    private static List< String > monthGiftTableHead = Arrays.asList( "日期",
            "开通人数", "领取人数","元宝支出","经验支出","体力支出" );
    private static List< String > duihuanTableHead = Arrays.asList( "日期",
            "渠道", "应用", "兑换人数","兑换次数" );
    private static List< String > TaskTableHead = Arrays.asList( "日期",
            "领取人数","领取次数","元宝支出" ,"经验支出","体力支出","藏宝图支出"   );

    private static List< String > loginGiftTableHead = Arrays.asList( "日期",
            "渠道","应用","领取人数","元宝支出", "体力支出", "1元话费卡支出", "5元话费卡支出" );
    private static List< String > loginPhoneCardTableHead = Arrays.asList( "日期","渠道","应用","领取人数","领取次数",
            "1元话费卡支出", "2元话费卡支出", "5元话费卡支出", "10元话费卡支出", "20元话费卡支出", "50元话费卡支出", "100元话费卡支出" );

    private static List< String > luckyFundTableHead = Arrays.asList( "日期","渠道","应用",
            "领取人数", "领取次数", "元宝支出" );

    private static List< String > exchangeBasicTableHead = Arrays.asList( "日期","兑换人ID",
            "收货人", "收货地址", "手机号码", "购买道具", "藏宝图消耗" ,"其他信息");

    private static List< String > huizongTableHead = Arrays.asList( "日期","新用户登陆支出","登陆奖励元宝支出",
            "好运基金元宝支出", "任务元宝支出", "月卡元宝支出", "公共玩法总支出");


    public static long getLoginPay( long beg, long end, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                " from t_grants2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and type <= 2;", c );
    }

    public static long getLuckyFundPay( long beg, long end, BiClient c ) {
        return ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                " from t_grants2 " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and type = 3;", c );
    }

    public void getPage ( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PRODUCT ).forward( req, resp );
    }
    public void getLoginGiftPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PRODUCT_LOGIN_GIFT ).forward( req, resp );
    }

    public void getMonthGiftPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PRODUCT_MONTH_GIFT ).forward( req, resp );
    }

    public void getLoginPhoneCardPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PRODUCT_LOGIN_PHONE_CARD ).forward( req, resp );
    }
    public void getHuizongPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PRODUCT_HUIZONG ).forward( req, resp );
    }
    public void getDuihuanPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PRODUCT_DUIHUAN ).forward( req, resp );
    }
    public void getTaskPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PRODUCT_TASK ).forward( req, resp );
    }
    public void getLuckyFundPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PRODUCT_LUCKY_FUND ).forward( req, resp );
    }

    public void getExchangePage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PRODUCT_EXCHANGE ).forward( req, resp );
    }

    public void getExchangeBasicPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PRODUCT_EXCHANGE_BASIC ).forward( req, resp );
    }


    private List< ProductView.Task > getTaskData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            ProductView.Task val =  new ProductView.Task();

            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.people = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mission_finish2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    ";", c ) + ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_task_finish  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and type != 0 ;", c ) ;
            val.times = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mission_finish2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " ;", c ) + ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_task_finish " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and type != 0 ;", c ) ;
            List<String> getinfo = ServiceManager.getSqlService().queryListString( "select bonus " +
                    " from t_task_finish " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            Map< Long, Long > getMap1 = new HashMap<>();
            /**
             * 道具id -1为元宝
             *
             * */
            getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
            long gold              = getLong( getMap1, -1 );
            long hp              = getLong( getMap1, -2 );
            long exp              = getLong( getMap1, -3 );
            long baotu              = getLong( getMap1, 10301 );
            val.gold = ServiceManager.getSqlService().queryLong("select sum( gold ) " +
                    " from t_mission_finish2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " ;", c) + gold;
            val.hp = ServiceManager.getSqlService().queryLong("select sum( hp ) " +
                    " from t_mission_finish2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " ;", c) + hp;
            val.exp = ServiceManager.getSqlService().queryLong("select sum( exp ) " +
                    " from t_mission_finish2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " ;", c) + exp;
            val.baotu = ServiceManager.getSqlService().queryLong("select sum( item_count ) " +
                    " from t_mission_finish2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and item_id = 10301 ;", c) + baotu;

            return val;

        } );
    }

    public void queryTask( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PRODUCT, getTaskData( req, resp ),
                TaskTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.times + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + info.exp + "</td>" );
                    pw.write( "<td>" + info.hp + "</td>" );
                    pw.write( "<td>" + info.baotu + "</td>" );

                } );
    }

    public void downloadTask( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PRODUCT, getTaskData( req, resp ),
                TaskTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.times + "," );
                    pw.write( info.gold + "," );
                    pw.write( info.times + "," );
                    pw.write( info.exp + "," );
                    pw.write( info.hp + "," );
                    pw.write( info.baotu + "," );
                } );
    }




    private List< ProductView.Duihuan > getDuihuanData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList2( req, resp, ( beg, end, c ) -> {
            List<ProductView.Duihuan > list =  new LinkedList<>();

            Set< String > allFw = new HashSet<>();


            List< String > dataList = ServiceManager.getSqlService().queryListString( "select A.fw, A.appid, count(A.id) " +
                    "      count( distinct A.user_id ) " +
                    " from ( select t_exchange.id as id, " +
                    "               t_exchange.appid as appid, " +
                    "               coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                    "        from t_exchange " +
                    "        left join t_install " +
                    "        using( user_id ) " +
                    "        where " + beg + " <= t_exchange.timestamp and t_exchange.timestamp < " + end + ") as A " +
                    " group by A.fw, A.appid;", c );



            dataList.forEach( data -> {
                String[] args = data.split( "\n", 4 );
                ProductView.Duihuan val = new ProductView.Duihuan();
                val.begin = TimeUtil.ymdFormat().format( beg );
                val.end = TimeUtil.ymdFormat().format( end );
                val.fromWhere = args[0];
                val.appName = GameCollection.getName( args[1] );
                val.count = Long.parseLong( args[2] );
                val.people = Long.parseLong( args[3] );

                list.add( val );
            } );
            return list;

        } );
    }

    public void queryDuihuan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PRODUCT, getDuihuanData( req, resp ),
                duihuanTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.fromWhere + "</td>" );
                    pw.write( "<td>" + info.appName + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                } );
    }

    public void downloadDuihuan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PRODUCT, getDuihuanData( req, resp ),
                duihuanTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.fromWhere + "," );
                    pw.write( info.appName + "," );
                    pw.write( info.people + "," );
                    pw.write( info.count + "," );
                } );
    }





    private List< ProductView.HuiZong > gethuiZongData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            ProductView.HuiZong val = new ProductView.HuiZong();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.loginGift = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 1;", c );
            val.goodLuck = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 3;", c );
            val.month = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 8;", c );
            List<String> getinfo = ServiceManager.getSqlService().queryListString( "select bonus " +
                    " from t_task_finish " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            Map< Long, Long > getMap1 = new HashMap<>();
            /**
             * 道具id -1为元宝
             *
             * */
            getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
            long a1              = getLong( getMap1, -1 );
            val.task = ServiceManager.getSqlService().queryLong("select sum(gold) " +
                    " from t_mission_finish2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " ;", c) + a1;
            val.newUser = ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                    " from t_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and type = 1 " +
                    " and is_ccgames = 'Y' and is_robot = 'N';", c);

            val.all = val.loginGift + val.goodLuck + val.month + val.task + val.newUser ;
            return val;
        } );
    }

    public void queryHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PRODUCT, gethuiZongData( req, resp ),
                huizongTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.newUser + "</td>" );
                    pw.write( "<td>" + info.loginGift + "</td>" );
                    pw.write( "<td>" + info.goodLuck + "</td>" );
                    pw.write( "<td>" + info.task + "</td>" );
                    pw.write( "<td>" + info.month + "</td>" );
                    pw.write( "<td>" + info.all + "</td>" );
                } );
    }

    public void downloadHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PRODUCT, gethuiZongData( req, resp ),
                huizongTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.newUser + "," );
                    pw.write( info.loginGift + "," );
                    pw.write( info.goodLuck + "," );
                    pw.write( info.task + "," );
                    pw.write( info.month + "," );
                    pw.write( info.all + "," );
                } );
    }


    private List< ProductView.MonthGift > getMonthGiftData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            ProductView.MonthGift val = new ProductView.MonthGift();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.gold = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 8;", c );
            val.hp = ServiceManager.getSqlService().queryLong( "select coalesce( sum( hp ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 8;", c );
            val.exp = ServiceManager.getSqlService().queryLong( "select coalesce( sum( exp ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 8 ;", c );
            val.kaitongpeople = ServiceManager.getSqlService().queryLong( "select coalesce( count( id ), 0 ) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_id = 10008;", c );
            val.lingqupeople = ServiceManager.getSqlService().queryLong( "select coalesce( count( id ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 8;", c );
            return val;
        } );
    }

    public void queryMonthGift( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PRODUCT, getMonthGiftData( req, resp ),
                monthGiftTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.kaitongpeople + "</td>" );
                    pw.write( "<td>" + info.lingqupeople + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + info.exp + "</td>" );
                    pw.write( "<td>" + info.hp + "</td>" );
                } );
    }

    public void downloadMonthGift( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PRODUCT, getMonthGiftData( req, resp ),
                monthGiftTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.kaitongpeople + "," );
                    pw.write( info.lingqupeople + "," );
                    pw.write( info.gold + "," );
                    pw.write( info.exp + "," );
                    pw.write( info.hp + "," );
                } );
    }



    private List< ProductView.LoginGift > getLoginGiftData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList2( req, resp, ( beg, end, c ) -> {
            List<ProductView.LoginGift > list =  new LinkedList<>();

            Set< String > allFw = new HashSet<>();


            List< String > dataList = ServiceManager.getSqlService().queryListString( "select A.fw, A.appid, sum( A.gold ), sum( A.hp ), " +
                    "      count( distinct A.user_id ), " +
                    "      sum( case when A.item_id = " + ItemIdUtils.ITEM_ID_10100  +" then A.item_count else 0 end ), " +
                    "      sum( case when A.item_id = " + ItemIdUtils.ITEM_ID_10102  +" then A.item_count else 0 end ) " +
                    " from ( select t_grants2.id as id, " +
                    "               t_grants2.user_id as user_id, " +
                    "               t_grants2.appid as appid, " +
                    "               t_grants2.gold as gold, " +
                    "               t_grants2.item_id as item_id, " +
                    "               t_grants2.item_count as item_count, " +
                    "               t_grants2.hp as hp, " +
                    "               coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                    "        from t_grants2 " +
                    "        left join t_install " +
                    "        using( user_id ) " +
                    "        where " + beg + " <= t_grants2.timestamp and t_grants2.timestamp < " + end + " and " +
                    "        t_grants2.type = 1 ) as A " +
                    " group by A.fw, A.appid;", c );

                dataList.forEach( data -> {
                    String[] args = data.split( "\n", 7 );
                    ProductView.LoginGift val = new ProductView.LoginGift();
                    val.begin = TimeUtil.ymdFormat().format( beg );
                    val.end = TimeUtil.ymdFormat().format( end );
                    val.fromWhere = args[0];
                    val.appName = AppSdk.getAppName( args[1] );
                    val.gold= Long.parseLong( args[2] ) / 2;
                    val.hp = Long.parseLong( args[3] );
                    val.people = Long.parseLong( args[4] );
                    val.card1 = Long.parseLong( args[5] );
                    val.card5 = Long.parseLong( args[6] );
                    list.add( val );
                } );
            return list;

        } );
    }

    public void queryLoginGift( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PRODUCT, getLoginGiftData( req, resp ),
                loginGiftTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.fromWhere + "</td>" );
                    pw.write( "<td>" + info.appName + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + info.hp + "</td>" );
                    pw.write( "<td>" + info.card1 + "</td>" );
                    pw.write( "<td>" + info.card5 + "</td>" );
                } );
    }

    public void downloadLoginGift( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PRODUCT, getLoginGiftData( req, resp ),
                loginGiftTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.fromWhere + "," );
                    pw.write( info.appName + "," );
                    pw.write( info.people + "," );
                    pw.write( info.gold + "," );
                    pw.write( info.hp + "," );
                    pw.write( info.card1 + "," );
                    pw.write( info.card5 + "," );
                } );
    }

    private List< ProductView.LoginPhoneCard > getLoginPhoneCardData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList2( req, resp, ( beg, end, c ) -> {
            List<ProductView.LoginPhoneCard > list =  new LinkedList<>();

            Set< String > allFw = new HashSet<>();


            List< String > dataList = ServiceManager.getSqlService().queryListString( "select A.fw, A.appid, " +
                    "      count( distinct A.user_id ), count( A.id ), " +
                    "      sum( case when A.item_id = " + ItemIdUtils.ITEM_ID_10100  +" then A.item_count else 0 end ), " +
                    "      sum( case when A.item_id = " + ItemIdUtils.ITEM_ID_10101  +" then A.item_count else 0 end ), " +
                    "      sum( case when A.item_id = " + ItemIdUtils.ITEM_ID_10102  +" then A.item_count else 0 end ), " +
                    "      sum( case when A.item_id = " + ItemIdUtils.ITEM_ID_10103  +" then A.item_count else 0 end ), " +
                    "      sum( case when A.item_id = " + ItemIdUtils.ITEM_ID_10104  +" then A.item_count else 0 end ), " +
                    "      sum( case when A.item_id = " + ItemIdUtils.ITEM_ID_10105  +" then A.item_count else 0 end ), " +
                    "      sum( case when A.item_id = " + ItemIdUtils.ITEM_ID_10106  +" then A.item_count else 0 end ) " +
                    " from ( select t_grants2.id as id, " +
                    "               t_grants2.user_id as user_id, " +
                    "               t_grants2.appid as appid, " +
                    "               t_grants2.gold as gold, " +
                    "               t_grants2.item_id as item_id, " +
                    "               t_grants2.item_count as item_count, " +
                    "               coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                    "        from t_grants2 " +
                    "        left join t_install " +
                    "        using( user_id ) " +
                    "        where " + beg + " <= t_grants2.timestamp and t_grants2.timestamp < " + end + " and " +
                    "        t_grants2.type = 2 ) as A " +
                    " group by A.fw, A.appid;", c );



            dataList.forEach( data -> {
                String[] args = data.split( "\n", 11 );
                ProductView.LoginPhoneCard val = new ProductView.LoginPhoneCard();
                val.begin = TimeUtil.ymdFormat().format( beg );
                val.end = TimeUtil.ymdFormat().format( end );
                val.fromWhere = args[0];
                val.appName = GameCollection.getName( args[1] );
                val.people = Long.parseLong( args[2] );
                val.count = Long.parseLong( args[3] );
                val.card1 = Long.parseLong( args[4] );
                val.card2 = Long.parseLong( args[5] );
                val.card5 = Long.parseLong( args[6] );
                val.card10 = Long.parseLong( args[7] );
                val.card20 = Long.parseLong( args[8] );
                val.card50 = Long.parseLong( args[9] );
                val.card100 = Long.parseLong( args[10] );
                list.add( val );
            } );
            return list;

        } );
    }

    public void queryLoginPhoneCard( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PRODUCT, getLoginPhoneCardData( req, resp ),
                loginPhoneCardTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.fromWhere + "</td>" );
                    pw.write( "<td>" + info.appName + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.card1 + "</td>" );
                    pw.write( "<td>" + info.card2 + "</td>" );
                    pw.write( "<td>" + info.card5 + "</td>" );
                    pw.write( "<td>" + info.card10 + "</td>" );
                    pw.write( "<td>" + info.card20 + "</td>" );
                    pw.write( "<td>" + info.card50 + "</td>" );
                    pw.write( "<td>" + info.card100 + "</td>" );
                } );
    }

    public void downloadLoginPhoneCard( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PRODUCT, getLoginPhoneCardData( req, resp ),
                loginPhoneCardTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.fromWhere + "," );
                    pw.write( info.appName + "," );
                    pw.write( info.people + "," );
                    pw.write( info.count + "," );
                    pw.write( info.card1 + "," );
                    pw.write( info.card2 + "," );
                    pw.write( info.card5 + "," );
                    pw.write( info.card10 + "," );
                    pw.write( info.card20 + "," );
                    pw.write( info.card50 + "," );
                    pw.write( info.card100 + "," );
                } );
    }

    private List< ProductView.LuckyFund > getLuckyFundData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList2( req, resp, ( beg, end, c ) -> {
            List<ProductView.LuckyFund > list =  new LinkedList<>();

            List< String > dataList = ServiceManager.getSqlService().queryListString( "select A.fw, A.appid, count( distinct A.user_id ), count( A.id ), sum( A.gold ) " +
                    " from ( select t_grants2.id as id, " +
                    "               t_grants2.user_id as user_id, " +
                    "               t_grants2.appid as appid, " +
                    "               t_grants2.gold as gold, " +
                    "               coalesce( t_install.from_where, 'TENCENT' ) as fw " +
                    "        from t_grants2 " +
                    "        left join t_install " +
                    "        using( user_id ) " +
                    "        where " + beg + " <= t_grants2.timestamp and t_grants2.timestamp < " + end + " and " +
                    "        t_grants2.type = 3 ) as A " +
                    " group by A.fw, A.appid;", c );

            dataList.forEach( data -> {
                String[] args = data.split( "\n", 5 );
                ProductView.LuckyFund val = new ProductView.LuckyFund();
                val.begin = TimeUtil.ymdFormat().format( beg );
                val.end = TimeUtil.ymdFormat().format( end );
                val.fromWhere = args[0];
                val.appName = AppSdk.getAppName(args[1] );
                val.people = Long.parseLong( args[2] );
                val.count = Long.parseLong( args[3] );
                val.gold = Long.parseLong( args[4] );
                list.add( val );
            } );
            return list;
        } );
    }

    public void queryLuckyFund( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PRODUCT, getLuckyFundData( req, resp ),
                luckyFundTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.fromWhere + "</td>" );
                    pw.write( "<td>" + info.appName + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                } );
    }

    public void downloadLuckyFund( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PRODUCT, getLuckyFundData( req, resp ),
                luckyFundTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.fromWhere + "," );
                    pw.write( info.appName + "," );
                    pw.write( info.people + "," );
                    pw.write( info.count + "," );
                    pw.write( info.gold + "," );
                } );
    }

    private List< ProductView.ExchangeBasic > getExchangeBasicData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            List< ProductView.ExchangeBasic > list = new LinkedList<>();
            Map< Long, String > buyMap = ServiceManager.getSqlService().queryMapLongString(
                    "select timestamp, user_id, cost_count, item_id, item_name, item_count, real_name, mobile_no, addr,other_info " +
                            " from t_exchange " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            "   and cost_id = 10301;", c );

            buyMap.forEach( ( k, v ) -> {
                ProductView.ExchangeBasic val = new ProductView.ExchangeBasic();
                val.begin = TimeUtil.ymdhmsFormat().format( k );
                String[] arr = v.split( "\n", 9 );
                val.userId = Long.parseLong( arr[0] );
                val.costCount = Long.parseLong( arr[1] );
                val.itemId = Long.parseLong( arr[2] );
                val.itemName = arr[3];
                val.itemCount = Long.parseLong( arr[4] );
                val.realName = arr[5];
                val.mobileNo = arr[6];
                val.addr = arr[7];
                val.other = arr[8];
                list.add( val );
            } );
            return list;
        } );
    }

//        return getData( req, resp, ( beg, end, c ) -> {
//            List< ProductView.ExchangeBasic > list = new LinkedList<>();
//
//
//            List< String > dataList = ServiceManager.getSqlService().queryListString( "select A.fw, A.appid, A.user_id, A.appid, A.cost_count， " +
//                    "A.item_id，A.item_name，A.item_count，A.real_name，A.mobile_no，A.addr，A.other_info " +
//                    " from ( select t_exchange.id as id, " +
//                    "               t_exchange.user_id as user_id, " +
//                    "               t_exchange.appid as appid, " +
//                    "               t_exchange.cost_count as cost_count, " +
//                    "               t_exchange.item_id as item_id, " +
//                    "               t_exchange.item_name as item_name, " +
//                    "               t_exchange.item_count as item_count, " +
//                    "               t_exchange.real_name as real_name, " +
//                    "               t_exchange.mobile_no as mobile_no, " +
//                    "               t_exchange.addr as addr, " +
//                    "               t_exchange.other_info as other_info, " +
//                    "               coalesce( t_install.from_where, 'TENCENT' ) as fw " +
//                    "        from t_exchange " +
//                    "        left join t_install " +
//                    "        using( user_id ) " +
//                    "        where " + beg + " <= t_exchange.timestamp and t_exchange.timestamp < " + end + " " +
//                    "        and t_exchange.cost_id = 10301) as A;", c );
//
//
//            dataList.forEach( data -> {
//                ProductView.ExchangeBasic val = new ProductView.ExchangeBasic();
//                val.begin = TimeUtil.ymdFormat().format( beg );
//                String[] arr = data.split( "\n", 12 );
//                val.fromWhere = arr[0];
//  
//                val.userId = Long.parseLong( arr[0] );
//                val.costCount = Long.parseLong( arr[1] );
//                val.itemId = Long.parseLong( arr[2] );
//                val.itemName = arr[3];
//                val.itemCount = Long.parseLong( arr[4] );
//                val.realName = arr[5];
//                val.mobileNo = arr[6];
//                val.addr = arr[7];
//                val.other = arr[8];
//                list.add( val );
//            } );
//            return list;
//        } );
//    }

    public void queryExchangeBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PRODUCT, getExchangeBasicData( req, resp ),
                exchangeBasicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" + info.realName + "</td>" );
                    pw.write( "<td>" + info.addr + "</td>" );
                    pw.write( "<td>" + info.mobileNo + "</td>" );
                    pw.write( "<td>" + info.itemName + "</td>" );
                    pw.write( "<td>" + info.costCount + "</td>" );
                    pw.write( "<td>" + info.other + "</td>" );
                } );
    }

    public void downloadExchangeBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PRODUCT, getExchangeBasicData( req, resp ),
                exchangeBasicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.userId + "," );
                    pw.write( info.realName + "," );
                    pw.write( info.addr + "," );
                    pw.write( info.mobileNo + "," );
                    pw.write( info.itemName + "," );
                    pw.write( info.costCount + "," );
                    pw.write( info.other + "," );
                } );
    }

}