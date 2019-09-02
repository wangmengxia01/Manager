package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bi.protocol.Currency;
import com.hoolai.ccgames.bifront.net.BiClient;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.starter.Global;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.MjdrView;
import com.hoolai.ccgames.bifront.vo.baseView;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service( "mjdrAction" )
public class MjdrAction extends BaseAction {

    private static final String APPID_STR = "1104754063";
    private static final String GAME_XUELIU = "血流成河";
    private static final String GAME_XUEZHAN = "血战到底";
    private static final String GAME_ERMA = "二麻";

    private static List< String > financeTableHead = Arrays.asList( "日期",
            "新手支出", "登录奖励支出", "升级奖励支出", "输光补助支出", "任务支出",
            "血流成河收入", "血战到底收入", "二人麻将收入", "AI输赢", "抽奖元宝支出", "总抽水" );

    private static List< String > lianxiTableHead = Arrays.asList( "日期",
             "血流人数","血流手数","血流体力耗费","血战人数","血战手数","血战体力耗费" );

    private static List< String > lianxiwinTableHead = Arrays.asList( "日期",
            "元宝支出","藏宝图支出","一元话费支出" ,"体力支出");
    private static List< String > lianxiwinTableHead1 = Arrays.asList( "日期",
            "连胜1","连胜2","连胜3","连胜4","连胜5","连胜6","连胜7","连胜8","连胜9" );


    private static List< Integer > innings = Arrays.asList( 0, 1, 2, 3, 4, 7, 11, 16, 21, 26, 31, Integer.MAX_VALUE );
    private static List< String > funnelTableHead = new ArrayList<>();
    private static List< String > funnelTableHead2 = Arrays.asList( "日期",
            "新注册用户", "首轮牌局正常开始", "换了三张", "未换三张",
            "已定缺", "未定缺", "结算返回", "继续游戏", "返回后开始游戏" );
    private static List< String > funnelTableHead3 = Arrays.asList( "日期",
            "新注册用户", "立即领取人数", "立即使用人数", "输入手机号人数",
            "免费领奖成功人数", "免费领奖失败人数", "关闭充值面板人数" );

    private static List< String > HuaFeiGiftSumTableHead = Arrays.asList( "日期",
            "一元话费卡支出","二元话费卡支出","五元话费卡支出","十元话费卡支出","二十元话费卡支出",
            "五十元话费卡支出","一百元话费卡支出","二百元话费卡支出" );

    private static List< Integer > minutesOnline = Arrays.asList( 0, 1, 2, 3, 4, 7, 11, 16, 21, 26, 31, Integer.MAX_VALUE );
    private static List< Integer > daysRetention = Arrays.asList( 1, 3, 5, 7, 14, 21, 30 );
    private static List< String > phoneCardRetentionTableHead = new LinkedList<>();
    private static List< Long > millisOnline = new ArrayList<>();
    private static List< String > onlineTableHead = new ArrayList<>();
    private static List< String > upgradeTableHead = Arrays.asList( "日期",
            "升级人数", "升级次数", "升级总支出" );
    private static List< String > basicTableHead = new LinkedList<>();
    private static List< Integer > xuezhanTypes = Arrays.asList( 1, 3 );
    private static List< Integer > xueliuTypes = Arrays.asList( 2, 4 );
    private static List< Integer > errenTypes = Arrays.asList( 5 );
    private static Map< Long, String > levelName = new TreeMap<>();
    private static List< String > rankTableHead = Arrays.asList( "日期",
            "ID", "用户名", "输赢额度" );
    private static List< String > KaiFangHuizongTableHead = Arrays.asList( "日期",
            "血流房卡消耗", "血战房卡消耗", "总消耗" );
    private static List< String > KaiFangJichuTableHead = Arrays.asList( "日期",
            "血流人数", "血流手数", "血流房卡消耗" ,"血战人数", "血战手数", "血战房卡消耗" );
    private static List< String > KaiFangXingweiTableHead = Arrays.asList( "日期",
            "总人数", "开房次数", "对战开始次数" , "选择2番", "选择3番" , "换三张", "不换三张" , "选择4局", "选择8局");

    private static List< String > HuizongTableHead = Arrays.asList( "日期",
            "总人数", "AI输赢", "总抽水" , "总收入" );
    private static List< Long > importantItemIds = new ArrayList<>();
    private static List< Integer > interestFans = Arrays.asList( 2, 3, 5, 6 );
    private static List< String > statTableHead = new ArrayList<>();
    private static List< String > lotteryStatTableHead = Arrays.asList( "日期",
            "初级场人数(抽奖次数)", "中级场人数(抽奖次数)", "高级场人数(抽奖次数)",
            "白银场人数(抽奖次数)", "黄金场人数(抽奖次数)", "钻石场人数(抽奖次数)",
            "总次数", "总人数" );
    private static List< String > lotteryItemTableHead = Arrays.asList( "日期",
            "元宝", "兑换券", "5元话费卡" );
    private static List< String > lotterySumTableHead = Arrays.asList( "日期",
            "抽奖人数", "抽奖次数", "抽奖元宝支出", "藏宝图支出","五元话费卡支出","十元话费卡支出","二十元话费卡支出",
            "五十元话费卡支出","一百元话费卡支出","贰佰元话费卡支出" );
    private static List< String > aiWinLoseTableHead = Arrays.asList( "游戏", "日期",
            "新手", "大神", "精英", "土豪", "AI总抽水" );
    private static List< String > possessionTableHead = Arrays.asList( "日期",
            "登录人数", "元宝持有量" );
    private static List< String > missionTableHead = Arrays.asList( "日期",
            "经验支出", "元宝支出", "体力支出", "藏宝图支出", "领奖次数", "领奖人数" );

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
        levelName.put( 1L, "新手" );
        levelName.put( 2L, "精英" );
        levelName.put( 3L, "大神" );
        levelName.put( 4L, "土豪" );

        basicTableHead.add( "日期" );
        levelName.forEach( ( k, v ) -> {
            basicTableHead.add( v + "人数" );
            basicTableHead.add( v + "手数" );
            basicTableHead.add( v + "抽水" );
        } );

    }

    static {
        statTableHead.add( "日期" );
        statTableHead.add( "总人数" );
        statTableHead.add( "总手数" );
        statTableHead.add( "自摸次数" );
        statTableHead.add( "点炮次数" );
        statTableHead.add( "明杠次数" );
        statTableHead.add( "暗杠次数" );
        for( Integer fan : interestFans ) {
            statTableHead.add( fan + "番次数" );
        }
    }

    static {
        phoneCardRetentionTableHead.add( "日期" );
        phoneCardRetentionTableHead.add( "登陆奖励获得话费人数" );
        daysRetention.forEach( day -> phoneCardRetentionTableHead.add( day + "日留存" ) );
    }


    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR ).forward( req, resp );
    }
    public void getXueliuPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_XUELIU ).forward( req, resp );
    }
    public void getXuezhanPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_XUEZHAN ).forward( req, resp );
    }
    public void getErmaPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_ERMA ).forward( req, resp );
    }

    public void getPageMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_MB ).forward( req, resp );
    }

    public void getFinancePage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_FINANCE ).forward( req, resp );
    }

    public void getFinancePageMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_FINANCE_MB ).forward( req, resp );
    }

    public void getFunnelPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_FUNNEL ).forward( req, resp );
    }

    public void getFunnelPageMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_FUNNEL_MB ).forward( req, resp );
    }

    public void getOnlinePage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_ONLINE ).forward( req, resp );
    }

    public void getOnlinePageMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_ONLINE_MB ).forward( req, resp );
    }

    public void getUpgradePage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_UPGRADE ).forward( req, resp );
    }

    public void getMissionPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_MISSION ).forward( req, resp );
    }

    public void getBasicXueliuPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_BASIC_XUELIU ).forward( req, resp );
    }

    public void getBasicXueliuPageMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_BASIC_XUELIU_MB ).forward( req, resp );
    }

    public void getBasicErRenPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_BASIC_ERREN ).forward( req, resp );
    }

    public void getBasicXuezhanPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_BASIC_XUEZHAN ).forward( req, resp );
    }

    public void getBasicXuezhanPageMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_BASIC_XUEZHAN_MB ).forward( req, resp );
    }

    public void getRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_RANK ).forward( req, resp );
    }
    public void getRankXueliuPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_XUELIURANK ).forward( req, resp );
    }
    public void getRankXuezhanPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_XUEZHANRANK ).forward( req, resp );
    }
    public void getRankermaPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_ERMARANK ).forward( req, resp );
    }

    public void getHuizongXueliuPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_XUELIUHUIZONG ).forward( req, resp );
    }
    public void getHuizongXuezhanPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_XUEZHANHUIZONG ).forward( req, resp );
    }
    public void getHuizongermaPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_ERMAHUIZONG ).forward( req, resp );
    }

    public void getHuaFeiGiftPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_HUAFEI_GIFT ).forward( req, resp );
    }

    public void getPossessionPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_POSSESSION ).forward( req, resp );
    }

    public void getPayItemPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_PAY_ITEM ).forward( req, resp );
    }

    public void getLianXiPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_LIANXI ).forward( req, resp );
    }

    public void getStatXueliuPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_STAT_XUELIU ).forward( req, resp );
    }

    public void getStatXuezhanPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_STAT_XUEZHAN ).forward( req, resp );
    }

    public void getLotteryStatPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_LOTTERY_STAT ).forward( req, resp );
    }

    public void getLotteryItemPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_LOTTERY_ITEM ).forward( req, resp );
    }

    public void getLianXiWinPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_LIANXIWIN ).forward( req, resp );
    }

    public void getLotterySumPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_LOTTERY_SUM ).forward( req, resp );
    }
    public void getKaifangPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_KAIFANG ).forward( req, resp );
    }
    public void getKaifangHuizongPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_KAIFANG_HUIZONG ).forward( req, resp );
    }

    public void getKaifangJichuPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_KAIFANG_BASIC ).forward( req, resp );
    }

    public void getKaifangXueliuPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_KAIFANG_XUELIU ).forward( req, resp );
    }

    public void getKaifangXuezhanPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_KAIFANG_XUEZHAN ).forward( req, resp );
    }


    public void getAiWinLosePage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_AI_WINLOSE ).forward( req, resp );
    }

    public void getPhoneCardRetentionPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_PHONE_CARD_RETENTION ).forward( req, resp );
    }



    public void getXuezhanLiushuiPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_XUEZHANLIUSHUI ).forward( req, resp );
    }
    public void getXueliuLiushuiPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_XUELIULIUSHUI ).forward( req, resp );
    }
    public void getErmaLiushuiPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_MJDR_ERMALIUSHUI ).forward( req, resp );
    }
    private static List< String > LiushuiTableHead = Arrays.asList( "日期",
            "新手场流水", "精英场流水", "大神场流水", "土豪场流水", "总流水" );
    private List< baseView.Liushui > getLiushuiData(HttpServletRequest req, HttpServletResponse resp, long type )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.Liushui val = new baseView.Liushui();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            Map< Long, Long > dataMap = ServiceManager.getSqlService().queryMapLongLong( "select level, sum( gold ) " +
                    " from t_mjdr_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = "+ type +" and is_robot ='N' and gold > 0" +
                    " group by level;", c );

            val.changci1 = getLong( dataMap, 1 );
            val.changci2 = getLong( dataMap, 2 );
            val.changci3 = getLong( dataMap, 3 );
            val.changci4 = getLong( dataMap, 4 );
            return val;
        } );
    }

    public void queryXuezhanLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp ,3),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.changci1 + "</td>" );
                    pw.write( "<td>" + info.changci2 + "</td>" );
                    pw.write( "<td>" + info.changci3 + "</td>" );
                    pw.write( "<td>" + info.changci4 + "</td>" );
                    pw.write( "<td>" + (info.changci1 + info.changci2 + info.changci3 + info.changci4 ) + "</td>" );
                } );
    }

    public void downloadXuezhanLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp ,3),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.changci1 + "," );
                    pw.write( info.changci2 + "," );
                    pw.write( info.changci3 + "," );
                    pw.write( info.changci4 + "," );
                    pw.write( (info.changci1 + info.changci2 + info.changci3 + info.changci4 ) + "," );
                } );
    }
    public void queryXueliuLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp ,4),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.changci1 + "</td>" );
                    pw.write( "<td>" + info.changci2 + "</td>" );
                    pw.write( "<td>" + info.changci3 + "</td>" );
                    pw.write( "<td>" + info.changci4 + "</td>" );
                    pw.write( "<td>" + (info.changci1 + info.changci2 + info.changci3 + info.changci4 ) + "</td>" );
                } );
    }

    public void downloadXueliuLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp ,4),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.changci1 + "," );
                    pw.write( info.changci2 + "," );
                    pw.write( info.changci3 + "," );
                    pw.write( info.changci4 + "," );
                    pw.write( (info.changci1 + info.changci2 + info.changci3 + info.changci4 ) + "," );
                } );
    }
    public void queryErmaLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp ,5),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.changci1 + "</td>" );
                    pw.write( "<td>" + info.changci2 + "</td>" );
                    pw.write( "<td>" + info.changci3 + "</td>" );
                    pw.write( "<td>" + info.changci4 + "</td>" );
                    pw.write( "<td>" + (info.changci1 + info.changci2 + info.changci3 + info.changci4 ) + "</td>" );
                } );
    }

    public void downloadErmaLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp ,5),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.changci1 + "," );
                    pw.write( info.changci2 + "," );
                    pw.write( info.changci3 + "," );
                    pw.write( info.changci4 + "," );
                    pw.write( (info.changci1 + info.changci2 + info.changci3 + info.changci4 ) + "," );
                } );
    }


    private List< MjdrView.KaiFangXingwei > getKaifangXingweiData( HttpServletRequest req, HttpServletResponse resp ,String type)
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.KaiFangXingwei val = new MjdrView.KaiFangXingwei();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.people = ServiceManager.getSqlService().queryLong( " select count( distinct A.user_id1 ) from ( " +
                         " ( select distinct user_id1 from t_mjdr_kaifang where timestamp >= "+ beg +" and timestamp <" + end +" and game_type in ("+ type + ")) " +
                    "union ( select distinct user_id2 from t_mjdr_kaifang where timestamp >= "+ beg +" and timestamp <" + end +" and game_type in ("+ type + " )) " +
                    "union ( select distinct user_id3 from t_mjdr_kaifang where timestamp >= "+ beg +" and timestamp <" + end +" and game_type in ("+ type + " )) " +
                    "union ( select distinct user_id4 from t_mjdr_kaifang where timestamp >= "+ beg +" and timestamp <" + end +" and game_type in ("+ type + " ))  ) as A;" ,c);

            val.count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_kaifang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y'  and game_type in ("+ type +");", c );
            val.inning = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_kaifang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' and game_type in ("+ type +");", c );
            val.fan2 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_kaifang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' and game_type in ("+ type +") and fan = 2;", c );
            val.fan3 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_kaifang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' and game_type in ("+ type +") and fan = 3;", c );
            val.huan3 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_kaifang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' and game_type in ("+ type +") and is_huansan = 'Y';", c );
            val.buhuan3 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_kaifang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' and game_type in ("+ type +") and is_huansan = 'N';", c );
            val.ju4 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_kaifang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' and game_type in ("+ type +") and jushu = 4;", c );
            val.ju8 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_kaifang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' and game_type in ("+ type +") and jushu = 8;", c );

            return val;
        } );
    }

    public void queryXuezhanKaifangXingwei( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getKaifangXingweiData( req, resp ,"1,3"),
                KaiFangXingweiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.inning + "</td>" );
                    pw.write( "<td>" + info.fan2 + "</td>" );
                    pw.write( "<td>" + info.fan3 + "</td>" );
                    pw.write( "<td>" + info.huan3 + "</td>" );
                    pw.write( "<td>" + info.buhuan3 + "</td>" );
                    pw.write( "<td>" + info.ju4 + "</td>" );
                    pw.write( "<td>" + info.ju8 + "</td>" );
                } );
    }

    public void downloadXuezhanKaifangXingwei( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getKaifangXingweiData( req, resp ,"1,3" ),
                KaiFangXingweiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.count + "," );
                    pw.write( info.inning + "," );
                    pw.write( info.fan2 + "," );
                    pw.write( info.fan3 + "," );
                    pw.write( info.huan3 + "," );
                    pw.write( info.buhuan3 + "," );
                    pw.write( info.ju4 + "," );
                    pw.write( info.ju8 + "," );
                } );
    }
    public void queryXueliuKaifangXingwei( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getKaifangXingweiData( req, resp ,"2,4"),
                KaiFangXingweiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.inning + "</td>" );
                    pw.write( "<td>" + info.fan2 + "</td>" );
                    pw.write( "<td>" + info.fan3 + "</td>" );
                    pw.write( "<td>" + info.huan3 + "</td>" );
                    pw.write( "<td>" + info.buhuan3 + "</td>" );
                    pw.write( "<td>" + info.ju4 + "</td>" );
                    pw.write( "<td>" + info.ju8 + "</td>" );
                } );
    }

    public void downloadXueliuKaifangXingwei( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getKaifangXingweiData( req, resp ,"2,4" ),
                KaiFangXingweiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.count + "," );
                    pw.write( info.inning + "," );
                    pw.write( info.fan2 + "," );
                    pw.write( info.fan3 + "," );
                    pw.write( info.huan3 + "," );
                    pw.write( info.buhuan3 + "," );
                    pw.write( info.ju4 + "," );
                    pw.write( info.ju8 + "," );
                } );
    }


    private List< MjdrView.KaiFangJichu > getKaifangJichuData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.KaiFangJichu val = new MjdrView.KaiFangJichu();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.xueliupeople =   ServiceManager.getSqlService().queryLong( " select count( A.user_id) from ( " +
                    " ( select distinct user_id1 as user_id from t_mjdr_kaifang where timestamp >= "+ beg +" and timestamp <" + end +" and game_type = 4 ) " +
                    "union ( select distinct user_id2 as user_id6 from t_mjdr_kaifang where timestamp >= "+ beg +" and timestamp <" + end +" and game_type = 4 ) " +
                    "union ( select distinct user_id3 as user_id5 from t_mjdr_kaifang where timestamp >= "+ beg +" and timestamp <" + end +" and game_type = 4 ) " +
                    "union ( select distinct user_id4 as user_id7 from t_mjdr_kaifang where timestamp >= "+ beg +" and timestamp <" + end +" and game_type = 4 )  ) as A;" ,c);
            val.xuezhanpeople = ServiceManager.getSqlService().queryLong( " select count( A.user_id) from ( " +
                    " ( select distinct user_id1 as user_id from t_mjdr_kaifang where timestamp >= "+ beg +" and timestamp <" + end +" and game_type = 3 ) " +
                    "union ( select distinct user_id2 as user_id5 from t_mjdr_kaifang where timestamp >= "+ beg +" and timestamp <" + end +" and game_type = 3 )" +
                    "union ( select distinct user_id3 as user_id6 from t_mjdr_kaifang where timestamp >= "+ beg +" and timestamp <" + end +" and game_type = 3 ) " +
                    "union ( select distinct user_id4 as user_id7 from t_mjdr_kaifang where timestamp >= "+ beg +" and timestamp <" + end +" and game_type = 3 )   ) as A;" ,c);
            val.xueliunum =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_mobile = 'Y' and type =  6  and user_info in ( 1, 3 ) ;", c );
            val.xuezhannum =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_mobile = 'Y' and type = 6  and user_info in ( 2, 4 );", c );
            val.xueliufangka =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_kaifang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_mobile = 'Y' and game_type in ( 1, 3 ) ;", c );
            val.xuezhanfangka =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_kaifang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_mobile = 'Y' and game_type in ( 2, 4 ) ;", c );
            return val;
        } );
    }

    public void queryKaifangJichu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getKaifangJichuData( req, resp ),
                KaiFangJichuTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.xueliupeople + "</td>" );
                    pw.write( "<td>" + info.xueliunum + "</td>" );
                    pw.write( "<td>" + info.xueliufangka + "</td>" );
                    pw.write( "<td>" + info.xuezhanpeople + "</td>" );
                    pw.write( "<td>" + info.xuezhannum + "</td>" );
                    pw.write( "<td>" + info.xuezhanfangka + "</td>" );
                } );
    }

    public void downloadKaifangJichu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getKaifangJichuData( req, resp ),
                KaiFangJichuTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.xueliupeople + "," );
                    pw.write( info.xueliunum + "," );
                    pw.write( info.xueliufangka + "," );
                    pw.write( info.xuezhanpeople + "," );
                    pw.write( info.xuezhannum + "," );
                    pw.write( info.xuezhanfangka + "," );
                } );
    }


    private List< MjdrView.KaiFangHuizong > getKaifangHuizongData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.KaiFangHuizong val = new MjdrView.KaiFangHuizong();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.xueliu = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_kaifang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and game_type in (2,4);", c );
            val.xuezhan = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_kaifang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and game_type in (1,3) ;", c );
            val.zong =  val.xueliu + val.xuezhan;
            return val;
        } );
    }

    public void queryKaifangHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getKaifangHuizongData( req, resp ),
                KaiFangHuizongTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.xueliu + "</td>" );
                    pw.write( "<td>" + info.xuezhan + "</td>" );
                    pw.write( "<td>" + info.zong + "</td>" );
                } );
    }

    public void downloadKaifangHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getKaifangHuizongData( req, resp ),
                KaiFangHuizongTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.xueliu + "," );
                    pw.write( info.xuezhan + "," );
                    pw.write( info.zong + "," );
                } );
    }



    private List< MjdrView.Huizong > getHuizongData( HttpServletRequest req, HttpServletResponse resp, String type )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.Huizong val = new MjdrView.Huizong();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mjdr_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type in ( "+ type +" )  and is_ccgames = 'Y' ;", c );
            val.rake = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type in ( "+ type +" )  and is_ccgames = 'Y' ;", c );
            val.AI =  ServiceManager.getSqlService().queryLong( "select coalesce( -sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type in ( "+ type +" )  and is_ccgames = 'Y' ;", c );
            val.shouru = val.rake + val.AI;
            return val;
        } );
    }

    public void queryXueliuHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getHuizongData( req, resp, "2,4" ),
                HuizongTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.AI + "</td>" );
                    pw.write( "<td>" + info.rake + "</td>" );
                    pw.write( "<td>" + info.shouru + "</td>" );
                } );
    }

    public void downloadXueliuHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getHuizongData( req, resp,  "2,4"  ),
                HuizongTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.AI + "," );
                    pw.write( info.rake + "," );
                    pw.write( info.shouru + "," );
                } );
    }
    public void queryXuezhanHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getHuizongData( req, resp,  "1,3"  ),
                HuizongTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.AI + "</td>" );
                    pw.write( "<td>" + info.rake + "</td>" );
                    pw.write( "<td>" + info.shouru + "</td>" );
                } );
    }

    public void downloadXuezhanHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getHuizongData( req, resp, "1,3" ),
                HuizongTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.AI + "," );
                    pw.write( info.rake + "," );
                    pw.write( info.shouru + "," );
                } );
    }
    public void queryermaHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getHuizongData( req, resp, "5" ),
                HuizongTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.AI + "</td>" );
                    pw.write( "<td>" + info.rake + "</td>" );
                    pw.write( "<td>" + info.shouru + "</td>" );
                } );
    }

    public void downloadermaHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getHuizongData( req, resp, "5" ),
                HuizongTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.AI + "," );
                    pw.write( info.rake + "," );
                    pw.write( info.shouru + "," );
                } );
    }

    private List< MjdrView.HuaFei > getHuaFeiGiftData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.HuaFei val = new MjdrView.HuaFei();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.card1 = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 7 and item_id = 10100;", c );
            val.card2 = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 7 and item_id = 10101;", c );
            val.card5 = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 7 and item_id = 10102;", c );
            val.card10 = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 7 and item_id = 10103;", c );
            val.card20 = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 7 and item_id = 10104;", c );
            val.card50 = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 7 and item_id = 10105;", c );
            val.card100 = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 7 and item_id = 10106;", c );
            val.card200 = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 7 and item_id = 10107;", c );
            return val;
        } );
    }


    public void queryHuaFeiGift( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getHuaFeiGiftData( req, resp ),
                HuaFeiGiftSumTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.card1 + "</td>" );
                    pw.write( "<td>" + info.card2 + "</td>" );
                    pw.write( "<td>" + info.card5 + "</td>" );
                    pw.write( "<td>" + info.card10 + "</td>" );
                    pw.write( "<td>" + info.card20 + "</td>" );
                    pw.write( "<td>" + info.card50 + "</td>" );
                    pw.write( "<td>" + info.card100 + "</td>" );
                    pw.write( "<td>" + info.card200 + "</td>" );
                });
    }


    public void downloadHuaFeiGift( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getHuaFeiGiftData( req, resp ),
                HuaFeiGiftSumTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.card1 + "," );
                    pw.write( info.card2 + "," );
                    pw.write( info.card5 + "," );
                    pw.write( info.card10 + "," );
                    pw.write( info.card20 + "," );
                    pw.write( info.card50 + "," );
                    pw.write( info.card100 + "," );
                    pw.write( info.card200 + "," );
                });
    }

    private List< MjdrView.LianXiWin > getLianXiWinData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.LianXiWin val = new MjdrView.LianXiWin();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.win1 =  ServiceManager.getSqlService().queryLong( "select count(num) " +
                    " from t_mjdr_win_streak " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and level = 5 and num = 1;", c );
            val.win2 =  ServiceManager.getSqlService().queryLong( "select count(num) " +
                    " from t_mjdr_win_streak " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and level = 5 and num = 2;", c );
            val.win3 =  ServiceManager.getSqlService().queryLong( "select count(num) " +
                    " from t_mjdr_win_streak " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and level = 5 and num = 3;", c );
            val.win4 =  ServiceManager.getSqlService().queryLong( "select count(num) " +
                    " from t_mjdr_win_streak " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and level = 5 and num = 4;", c );
            val.win5 =  ServiceManager.getSqlService().queryLong( "select count(num) " +
                    " from t_mjdr_win_streak " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and level = 5 and num = 5;", c );
            val.win6 =  ServiceManager.getSqlService().queryLong( "select count(num) " +
                    " from t_mjdr_win_streak " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and level = 5 and num = 6;", c );
            val.win7 =  ServiceManager.getSqlService().queryLong( "select count(num) " +
                    " from t_mjdr_win_streak " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and level = 5 and num = 7;", c );
            val.win8 =  ServiceManager.getSqlService().queryLong( "select count(num) " +
                    " from t_mjdr_win_streak " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and level = 5 and num = 8;", c );
            val.win9 =  ServiceManager.getSqlService().queryLong( "select count(num) " +
                    " from t_mjdr_win_streak " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and level = 5 and num = 9;", c );

            List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_mjdr_win_streak " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );

            Map< Long, Long > getMap = new HashMap<>();
            /**
             * 道具id -1为元宝
             *        -2为体力
             * */
            getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
            val.Gold             = getLong( getMap, -1 );
            val.baotu            = getLong( getMap, 10301 );
            val.card1            = getLong( getMap, 10100 );
            val.hp            = getLong( getMap, -2 );

            return val;
        } );

    }

    public void querylianxiWin( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        List<MjdrView.LianXiWin> data  = getLianXiWinData( req, resp, false );
        queryMultiList( req, resp,
                CommandList.GET_MJDR, data,
                lianxiwinTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.Gold  + "</td>" );
                    pw.write( "<td>" + info.baotu + "</td>" );
                    pw.write( "<td>" + info.card1 + "</td>" );
                    pw.write( "<td>" + info.hp + "</td>" );
                },data,
                lianxiwinTableHead1, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.win1  + "</td>" );
                    pw.write( "<td>" + info.win2 + "</td>" );
                    pw.write( "<td>" + info.win3 + "</td>" );
                    pw.write( "<td>" + info.win4 + "</td>" );
                    pw.write( "<td>" + info.win5 + "</td>" );
                    pw.write( "<td>" + info.win6 + "</td>" );
                    pw.write( "<td>" + info.win7 + "</td>" );
                    pw.write( "<td>" + info.win8 + "</td>" );
                    pw.write( "<td>" + info.win9 + "</td>" );
                },null );
    }

    public void downloadlianxiWin( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List<MjdrView.LianXiWin> data  = getLianXiWinData( req, resp, false );

        downloadMultiList( req, resp,
                CommandList.GET_MJDR, data,
                lianxiwinTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.Gold  + "," );
                    pw.write( info.baotu + "," );
                    pw.write( info.card1 + "," );
                    pw.write( info.hp + "," );
                }, data, lianxiwinTableHead1, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.win1  + "," );
                    pw.write( info.win2  + "," );
                    pw.write( info.win3  + "," );
                    pw.write( info.win4  + "," );
                    pw.write( info.win5  + "," );
                    pw.write( info.win6  + "," );
                    pw.write( info.win7  + "," );
                    pw.write( info.win8  + "," );
                    pw.write( info.win9  + "," );
                } );

    }


    private List< MjdrView.LianXi > getLianXiData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.LianXi val = new MjdrView.LianXi();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.xueliuPeople= ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mjdr_play " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 4 and level = 5"  +
                    "   and is_mobile  = '" + boolString( isMobile ) +"';", c );
            val.xueliuInning = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_inning " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 4 and level =5;", c );
            val.xueliuHP = ServiceManager.getSqlService().queryLong( "select  sum( count ) " +
                    " from t_mjdr_hp " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 4 " +
                    "   and is_robot = 'N' and is_mobile = '" + boolString( isMobile ) + "';", c );
            val.xuezhanPeople= ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mjdr_play " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 3 and level = 5"  +
                    "   and is_mobile  = '" + boolString( isMobile ) +"';", c );
            val.xuezhanInning = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_inning " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 3 and level =5;", c );
            val.xuezhanHP = ServiceManager.getSqlService().queryLong( "select  sum( count ) " +
                    " from t_mjdr_hp " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 3 " +
                    "   and is_robot = 'N' and is_mobile = '" + boolString( isMobile ) + "';", c );

            return val;
        } );

    }

    public void queryLianXi( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getLianXiData( req, resp, false ),
                lianxiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.xueliuPeople + "</td>" );
                    pw.write( "<td>" + info.xueliuInning + "</td>" );
                    pw.write( "<td>" + info.xueliuHP + "</td>" );
                    pw.write( "<td>" + info.xuezhanPeople + "</td>" );
                    pw.write( "<td>" + info.xuezhanInning + "</td>" );
                    pw.write( "<td>" + info.xuezhanHP + "</td>" );
                } );
    }

    public void downloadLianXi( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getLianXiData( req, resp, false ),
                lianxiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.xueliuPeople + "," );
                    pw.write( info.xueliuInning + "," );
                    pw.write( info.xueliuHP + "," );
                    pw.write( info.xuezhanPeople + "," );
                    pw.write( info.xuezhanInning + "," );
                    pw.write( info.xuezhanHP + "," );
                } );
    }


    private List< MjdrView.Finance > getFinanceData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.Finance val = new MjdrView.Finance();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.grantInstall = ServiceManager.getCommonService().getGrantInstallGold( APPID_STR, beg, end, isMobile, c );
            val.grantLogin = ServiceManager.getCommonService().getGrantLoginGold( APPID_STR, beg, end, isMobile, c );
            val.grantHelp = ServiceManager.getCommonService().getGrantHelpGold( APPID_STR, beg, end, isMobile, c );
            val.upgradePay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_upgrade " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_ccgames = 'Y' and is_robot = 'N' " +
                    "   and is_mobile = '" + boolString( isMobile ) + "';", c );
            val.missionPay = ServiceManager.getMissionService().getGold( APPID_STR, beg, end, isMobile, c );
            val.xuezhanIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type in ( 1, 3 ) and is_ccgames = 'Y' " +
                    "   and is_robot = 'N' and is_mobile = '" + boolString( isMobile ) + "';", c );
            val.xueliuIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type in ( 2, 4 ) and is_ccgames = 'Y' " +
                    "   and is_robot = 'N' and is_mobile = '" + boolString( isMobile ) + "';", c );
            val.errenIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 5 and is_ccgames = 'Y' " +
                    "   and is_robot = 'N' and is_mobile = '" + boolString( isMobile ) + "';", c );
            val.aiWinLose = ServiceManager.getSqlService().queryLong( "select coalesce( -sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N' and is_mobile = '" + boolString( isMobile ) + "';", c );
            val.lotteryPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( bonus_count ), 0 ) " +
                    " from t_mjdr_lottery " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and bonus_id = -1 and is_mobile = '" + boolString( isMobile ) + "';", c );
            val.total = val.xuezhanIncome + val.xueliuIncome + val.errenIncome -
                    val.grantInstall - val.grantLogin - val.grantHelp -
                    val.upgradePay - val.missionPay +
                    val.aiWinLose - val.lotteryPay;
            return val;
        } );

    }

    public void queryFinance( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getFinanceData( req, resp, false ),
                financeTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.grantInstall + "</td>" );
                    pw.write( "<td>" + info.grantLogin + "</td>" );
                    pw.write( "<td>" + info.upgradePay + "</td>" );
                    pw.write( "<td>" + info.grantHelp + "</td>" );
                    pw.write( "<td>" + info.missionPay + "</td>" );
                    pw.write( "<td>" + info.xueliuIncome + "</td>" );
                    pw.write( "<td>" + info.xuezhanIncome + "</td>" );
                    pw.write( "<td>" + info.errenIncome + "</td>" );
                    pw.write( "<td>" + info.aiWinLose + "</td>" );
                    pw.write( "<td>" + info.lotteryPay + "</td>" );
                    pw.write( "<td>" + info.total + "</td>" );
                } );
    }

    public void downloadFinance( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getFinanceData( req, resp, false ),
                financeTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.grantInstall + "," );
                    pw.write( info.grantLogin + "," );
                    pw.write( info.upgradePay + "," );
                    pw.write( info.grantHelp + "," );
                    pw.write( info.missionPay + "," );
                    pw.write( info.xueliuIncome + "," );
                    pw.write( info.xuezhanIncome + "," );
                    pw.write( info.errenIncome + "," );
                    pw.write( info.aiWinLose + "," );
                    pw.write( info.lotteryPay + "," );
                    pw.write( info.total + "," );
                } );
    }

    public void queryFinanceMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getFinanceData( req, resp, true ),
                financeTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.grantInstall + "</td>" );
                    pw.write( "<td>" + info.grantLogin + "</td>" );
                    pw.write( "<td>" + info.upgradePay + "</td>" );
                    pw.write( "<td>" + info.grantHelp + "</td>" );
                    pw.write( "<td>" + info.missionPay + "</td>" );
                    pw.write( "<td>" + info.xueliuIncome + "</td>" );
                    pw.write( "<td>" + info.xuezhanIncome + "</td>" );
                    pw.write( "<td>" + info.errenIncome + "</td>" );
                    pw.write( "<td>" + info.aiWinLose + "</td>" );
                    pw.write( "<td>" + info.lotteryPay + "</td>" );
                    pw.write( "<td>" + info.total + "</td>" );
                } );
    }

    public void downloadFinanceMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getFinanceData( req, resp, false ),
                financeTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.grantInstall + "," );
                    pw.write( info.grantLogin + "," );
                    pw.write( info.upgradePay + "," );
                    pw.write( info.grantHelp + "," );
                    pw.write( info.missionPay + "," );
                    pw.write( info.xueliuIncome + "," );
                    pw.write( info.xuezhanIncome + "," );
                    pw.write( info.errenIncome + "," );
                    pw.write( info.aiWinLose + "," );
                    pw.write( info.lotteryPay + "," );
                    pw.write( info.total + "," );
                } );
    }

    private List< MjdrView.Funnel > getFunnelData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.Funnel val = new MjdrView.Funnel();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.install = ServiceManager.getCommonService().getInstall( APPID_STR, beg, end, isMobile, c );
            val.grantLoginPeople = ServiceManager.getCommonService().getGrantLoginPeople( APPID_STR, beg, end, isMobile, c );
            val.goldChangePeople = ServiceManager.getSqlService().queryLong( "select count( A.user_id ) " +
                    " from ( select distinct user_id " +
                    "        from t_mjdr_play " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "          and is_ccgames = 'Y' and is_robot = 'N' ) as A " +
                    " inner join " +
                    "      ( select user_id " +
                    "        from t_install " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "          and appid = '" + APPID_STR + "' and is_mobile = '" + boolString( isMobile ) + "' ) as B " +
                    " on A.user_id = B.user_id;", c );
            Map< Long, Long > inningMap = ServiceManager.getSqlService().queryMapLongLong( "select A.user_id, A.cnt " +
                    " from ( select user_id, count( id ) as cnt " +
                    "        from t_mjdr_play " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "          and is_ccgames = 'Y' and is_robot = 'N'" +
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
            val.huan3People          = ServiceManager.getMjdrService().getHandlePeople( 1, beg, end, isMobile, c );
            val.dingquePeople        = ServiceManager.getMjdrService().getHandlePeople( 2, beg, end, isMobile, c );
            val.fanhuiPeople         = ServiceManager.getMjdrService().getHandlePeople( 3, beg, end, isMobile, c );
            val.jixuPeople           = ServiceManager.getMjdrService().getHandlePeople( 4, beg, end, isMobile, c );
            val.fanhuikaishiPeople   = ServiceManager.getMjdrService().getHandlePeople( 5, beg, end, isMobile, c );
            val.newuser              = ServiceManager.getCommonService().getInstall( APPID_STR, beg, end, isMobile, c );
            val.lijilingqu           = ServiceManager.getMjdrService().getHandlePeople( 6, beg, end, isMobile, c );
            val.lijishiyong          = ServiceManager.getMjdrService().getHandlePeople( 7, beg, end, isMobile, c );
            val.shurushouji          = ServiceManager.getMjdrService().getHandlePeople( 8, beg, end, isMobile, c );
            val.lingquchenggong      = ServiceManager.getMjdrService().getHandlePeople( 9, beg, end, isMobile, c );
            val.lingqushibai         = ServiceManager.getMjdrService().getHandlePeople( 10, beg, end, isMobile, c );
            val.guanbi               = ServiceManager.getMjdrService().getHandlePeople( 11, beg, end, isMobile, c );
            return val;
        } );
    }

    public void queryFunnel( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< MjdrView.Funnel > data = getFunnelData( req, resp, false );

        queryMultiList( req, resp,
                CommandList.GET_MJDR,
                data, funnelTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    pw.write( "<td>" + info.grantLoginPeople + "</td>" );
                    pw.write( "<td>" + info.goldChangePeople + "</td>" );
                    info.inningPeople.forEach( i -> pw.write( "<td>" + i + "</td>" ) );
                }, data, funnelTableHead2, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    pw.write( "<td>" + info.goldChangePeople + "</td>" );
                    pw.write( "<td>" + info.huan3People + "</td>" );
                    pw.write( "<td>" + ( info.goldChangePeople - info.huan3People ) + "</td>" );
                    pw.write( "<td>" + info.dingquePeople + "</td>" );
                    pw.write( "<td>" + ( info.goldChangePeople - info.dingquePeople ) + "</td>" );
                    pw.write( "<td>" + info.fanhuiPeople + "</td>" );
                    pw.write( "<td>" + info.jixuPeople + "</td>" );
                    pw.write( "<td>" + info.fanhuikaishiPeople + "</td>" );
                },data, funnelTableHead3, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.newuser + "</td>" );
                    pw.write( "<td>" + info.lijilingqu + "</td>" );
                    pw.write( "<td>" + info.lijishiyong + "</td>" );
                    pw.write( "<td>" + info.shurushouji + "</td>" );
                    pw.write( "<td>" + ( info.lingquchenggong ) + "</td>" );
                    pw.write( "<td>" + info.lingqushibai + "</td>" );
                    pw.write( "<td>" + info.guanbi + "</td>" );
                },null );
    }

    public void downloadFunnel( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< MjdrView.Funnel > data = getFunnelData( req, resp, false );

        downloadMultiList( req, resp,
                CommandList.GET_MJDR,
                data, funnelTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install + "," );
                    pw.write( info.grantLoginPeople + "," );
                    pw.write( info.goldChangePeople + "," );
                    info.inningPeople.forEach( i -> pw.write( i + "," ) );
                },data, funnelTableHead2, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install + "," );
                    pw.write( info.goldChangePeople + "," );
                    pw.write( info.huan3People + "," );
                    pw.write( ( info.goldChangePeople - info.huan3People ) + "," );
                    pw.write( info.dingquePeople + "," );
                    pw.write( ( info.goldChangePeople - info.dingquePeople ) + "," );
                    pw.write( info.fanhuiPeople + "," );
                    pw.write( info.jixuPeople + "," );
                    pw.write( info.fanhuikaishiPeople + "," );
                } ,
                data, funnelTableHead3, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install + "," );
                    pw.write( info.lijilingqu + "," );
                    pw.write( info.lijishiyong + "," );
                    pw.write( info.shurushouji + "," );
                    pw.write( info.lingquchenggong + "," );
                    pw.write( info.lingqushibai + "," );
                    pw.write( info.guanbi + "," );
                });
    }

    public void queryFunnelMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< MjdrView.Funnel > data = getFunnelData( req, resp, true );

        queryMultiList( req, resp,
                CommandList.GET_MJDR,
                data, funnelTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    pw.write( "<td>" + info.grantLoginPeople + "</td>" );
                    pw.write( "<td>" + info.goldChangePeople + "</td>" );
                    info.inningPeople.forEach( i -> pw.write( "<td>" + i + "</td>" ) );
                }, data, funnelTableHead2, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    pw.write( "<td>" + info.goldChangePeople + "</td>" );
                    pw.write( "<td>" + info.huan3People + "</td>" );
                    pw.write( "<td>" + ( info.goldChangePeople - info.huan3People ) + "</td>" );
                    pw.write( "<td>" + info.dingquePeople + "</td>" );
                    pw.write( "<td>" + ( info.goldChangePeople - info.dingquePeople ) + "</td>" );
                    pw.write( "<td>" + info.fanhuiPeople + "</td>" );
                    pw.write( "<td>" + info.jixuPeople + "</td>" );
                    pw.write( "<td>" + info.fanhuikaishiPeople + "</td>" );
                }, null );
    }

    public void downloadFunnelMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< MjdrView.Funnel > data = getFunnelData( req, resp, true );

        downloadMultiList( req, resp,
                CommandList.GET_MJDR,
                data, funnelTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install + "," );
                    pw.write( info.grantLoginPeople + "," );
                    pw.write( info.goldChangePeople + "," );
                    info.inningPeople.forEach( i -> pw.write( i + "," ) );
                }, data, funnelTableHead2, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install + "," );
                    pw.write( info.goldChangePeople + "," );
                    pw.write( info.huan3People + "," );
                    pw.write( ( info.goldChangePeople - info.huan3People ) + "," );
                    pw.write( info.dingquePeople + "," );
                    pw.write( ( info.goldChangePeople - info.dingquePeople ) + "," );
                    pw.write( info.fanhuiPeople + "," );
                    pw.write( info.jixuPeople + "," );
                    pw.write( info.fanhuikaishiPeople + "," );
                } );
    }

    private List< MjdrView.Online > getOnlineData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.Online val = new MjdrView.Online();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.install = ServiceManager.getCommonService().getInstall( APPID_STR, beg, end, isMobile, c );
            Map< Long, Long > onlineMap = ServiceManager.getSqlService().queryMapLongLong( "select A.user_id, A.online " +
                    " from ( select user_id, sum( millis ) as online " +
                    "        from t_mjdr_online " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "          and is_ccgames = 'Y' and is_robot = 'N' " +
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
                CommandList.GET_MJDR, getOnlineData( req, resp, false ),
                onlineTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    info.onlinePeople.forEach( p -> pw.write( "<td>" + p + "</td>" ) );
                } );
    }

    public void downloadOnline( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getOnlineData( req, resp, false ),
                onlineTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install + "," );
                    info.onlinePeople.forEach( p -> pw.write( p + "," ) );
                } );
    }

    public void queryOnlineMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getOnlineData( req, resp, true ),
                onlineTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    info.onlinePeople.forEach( p -> pw.write( "<td>" + p + "</td>" ) );
                } );
    }

    public void downloadOnlineMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getOnlineData( req, resp, true ),
                onlineTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install + "," );
                    info.onlinePeople.forEach( p -> pw.write( p + "," ) );
                } );
    }

    private List< MjdrView.Upgrade > getUpgradeData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.Upgrade val = new MjdrView.Upgrade();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mjdr_upgrade " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_ccgames = 'Y' and is_robot = 'N' " +
                    "   and is_mobile = '" + boolString( isMobile ) + "';", c );
            val.count = ServiceManager.getSqlService().queryLong( "select coalesce( sum( levels ), 0 ) " +
                    " from t_mjdr_upgrade " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_ccgames = 'Y' and is_robot = 'N' " +
                    "   and is_mobile = '" + boolString( isMobile ) + "';", c );
            val.gold = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_upgrade " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_ccgames = 'Y' and is_robot = 'N' " +
                    "   and is_mobile = '" + boolString( isMobile ) + "';", c );
            return val;
        } );
    }

    public void queryUpgrade( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getUpgradeData( req, resp, false ),
                upgradeTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                } );
    }

    public void downloadUpgrade( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getUpgradeData( req, resp, false ),
                upgradeTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.count + "," );
                    pw.write( info.gold + "," );
                } );
    }

    private List< MjdrView.Mission > getMissionData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.Mission val = new MjdrView.Mission();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.exp = ServiceManager.getMissionService().getExp( APPID_STR, beg, end, false, c );
            val.gold = ServiceManager.getMissionService().getGold( APPID_STR, beg, end, false, c );
            val.hp = ServiceManager.getMissionService().getHp( APPID_STR, beg, end, false, c );
            val.treasure = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_mission_finish2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and appid = '" + APPID_STR + "' and item_id = 10301;", c );
            val.count = ServiceManager.getMissionService().getCount( APPID_STR, beg, end, false, c );
            val.people = ServiceManager.getMissionService().getPeople( APPID_STR, beg, end, false, c );
            return val;
        } );
    }

    public void queryMission( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getMissionData( req, resp, false ),
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
                CommandList.GET_MJDR, getMissionData( req, resp, false ),
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

    private List< MjdrView.Basic > getBasicData( HttpServletRequest req, HttpServletResponse resp, List< Integer > types, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.Basic val = new MjdrView.Basic();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            levelName.forEach( ( k, v ) -> {
                MjdrView.BasicItem item = new MjdrView.BasicItem();
                item.level = k;
                types.forEach( t -> {
                    item.people += ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                            " from t_mjdr_play " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            "   and type = " + t + " and level = " + k +
                            "   and is_mobile = '" + boolString( isMobile ) + "';", c );
                    // 手数按照这个方法无法区分手机和PC
                    item.inning += ServiceManager.getSqlService().queryLong( "select count( distinct inning_id ) " +
                            " from t_mjdr_inning " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            "   and type = " + t + " and level = " + k + ";", c );
                    item.rake += ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                            " from t_mjdr_rake " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            "   and type = " + t + " and level = " + k +
                            "   and is_mobile = '" + boolString( isMobile ) + "';", c );
                } );
                val.data.put( k, item );
            } );
            return val;
        } );
    }

    public void queryBasicXueliu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getBasicData( req, resp, xueliuTypes, false ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    levelName.forEach( ( k, v ) -> {
                        pw.write( "<td>" + info.data.get( k ).people + "</td>" );
                        pw.write( "<td>" + info.data.get( k ).inning + "</td>" );
                        pw.write( "<td>" + info.data.get( k ).rake + "</td>" );
                    } );
                } );
    }

    public void downloadBasicXueliu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getBasicData( req, resp, xueliuTypes, false ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    levelName.forEach( ( k, v ) -> {
                        pw.write( info.data.get( k ).people + "," );
                        pw.write( info.data.get( k ).inning + "," );
                        pw.write( info.data.get( k ).rake + "," );
                    } );
                } );
    }

    public void queryBasicXueliuMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getBasicData( req, resp, xueliuTypes, true ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    levelName.forEach( ( k, v ) -> {
                        pw.write( "<td>" + info.data.get( k ).people + "</td>" );
                        pw.write( "<td>" + info.data.get( k ).inning + "</td>" );
                        pw.write( "<td>" + info.data.get( k ).rake + "</td>" );
                    } );
                } );
    }

    public void downloadBasicXueliuMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getBasicData( req, resp, xueliuTypes, true ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    levelName.forEach( ( k, v ) -> {
                        pw.write( info.data.get( k ).people + "," );
                        pw.write( info.data.get( k ).inning + "," );
                        pw.write( info.data.get( k ).rake + "," );
                    } );
                } );
    }

    public void queryBasicXuezhan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getBasicData( req, resp, xuezhanTypes, false ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    levelName.forEach( ( k, v ) -> {
                        pw.write( "<td>" + info.data.get( k ).people + "</td>" );
                        pw.write( "<td>" + info.data.get( k ).inning + "</td>" );
                        pw.write( "<td>" + info.data.get( k ).rake + "</td>" );
                    } );
                } );
    }

    public void downloadBasicXuezhan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getBasicData( req, resp, xuezhanTypes, false ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    levelName.forEach( ( k, v ) -> {
                        pw.write( info.data.get( k ).people + "," );
                        pw.write( info.data.get( k ).inning + "," );
                        pw.write( info.data.get( k ).rake + "," );
                    } );
                } );
    }

    public void queryBasicXuezhanMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getBasicData( req, resp, xuezhanTypes, true ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    levelName.forEach( ( k, v ) -> {
                        pw.write( "<td>" + info.data.get( k ).people + "</td>" );
                        pw.write( "<td>" + info.data.get( k ).inning + "</td>" );
                        pw.write( "<td>" + info.data.get( k ).rake + "</td>" );
                    } );
                } );
    }

    public void downloadBasicXuezhanMB( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getBasicData( req, resp, xuezhanTypes, true ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    levelName.forEach( ( k, v ) -> {
                        pw.write( info.data.get( k ).people + "," );
                        pw.write( info.data.get( k ).inning + "," );
                        pw.write( info.data.get( k ).rake + "," );
                    } );
                } );
    }

    public void queryBasicErRen( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getBasicData( req, resp, errenTypes, false ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    levelName.forEach( ( k, v ) -> {
                        pw.write( "<td>" + info.data.get( k ).people + "</td>" );
                        pw.write( "<td>" + info.data.get( k ).inning + "</td>" );
                        pw.write( "<td>" + info.data.get( k ).rake + "</td>" );
                    } );
                } );
    }

    public void downloadBasicErRen( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getBasicData( req, resp, errenTypes, false ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    levelName.forEach( ( k, v ) -> {
                        pw.write( info.data.get( k ).people + "," );
                        pw.write( info.data.get( k ).inning + "," );
                        pw.write( info.data.get( k ).rake + "," );
                    } );
                } );
    }

//    private List< MjdrView.Rank > getRankData( HttpServletRequest req, HttpServletResponse resp , long type)
//            throws Exception {
//
//        int limit = Integer.parseInt( req.getParameter( "limit" ) );
//        return getDataList2( req, resp, ( beg, end, c ) -> {
//            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
//                    " from t_mjdr_win " +
//                    " where " + beg + " <= timestamp and timestamp < " + end +
//                    "   and is_ccgames = 'Y' and is_robot = 'N' and type=" + type +
//                    " group by user_id;", c );
//
//            List< MjdrView.Rank > winList = new ArrayList<>();
//            winMap.forEach( ( k, v ) -> {
//                MjdrView.Rank val = new MjdrView.Rank();
//                val.begin = TimeUtil.ymdFormat().format( beg );
//                val.end = TimeUtil.ymdFormat().format( end );
//                val.userId = k;
//                val.gold = v;
//                winList.add( val );
//            } );
//
//            Collections.sort( winList, ( o1, o2 ) -> {
//                if( o1.gold > o2.gold ) return -1;
//                if( o1.gold < o2.gold ) return 1;
//                return 0;
//            } );
//
//            return winList;
//        } );
//    }

    public List< List< MjdrView.Rank > > getRankData(HttpServletRequest req, HttpServletResponse resp ,long type)
            throws Exception {

        int limit = Integer.parseInt( req.getParameter( "limit" ) );
        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_ccgames = 'Y' and is_robot = 'N' and type=" + type +
                    " group by user_id;", c );

            Map< Long, MjdrView.Rank > sum = new HashMap<>();


            winMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new MjdrView.Rank() );
                sum.get( k ).gold = v;
            } );


            List< MjdrView.Rank > winList = new ArrayList<>();
            List< MjdrView.Rank > loseList = new ArrayList<>();
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
    public void queryXueliuRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_MJDR, getRankData( req, resp ,4),
                rankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadXueliuRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_MJDR, getRankData( req, resp ,4),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( info.gold + "," );
                } );
    }
    public void queryXuezhanRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_MJDR, getRankData( req, resp ,3),
                rankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadXuezhanRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_MJDR, getRankData( req, resp ,3),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( info.gold + "," );
                } );
    }
    public void queryermaRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_MJDR, getRankData( req, resp ,5),
                rankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadermaRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_MJDR, getRankData( req, resp ,5),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( info.gold + "," );
                } );
    }

    private List< MjdrView.Possession > getPossessionData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.Possession val = new MjdrView.Possession();
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
                CommandList.GET_MJDR, getPossessionData( req, resp ),
                possessionTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.dau + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                } );
    }

    public void downloadPossession( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getPossessionData( req, resp ),
                possessionTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.dau + "," );
                    pw.write( info.gold + "," );
                } );
    }

    private List< MjdrView.PayItem > getPayItemData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.PayItem val = new MjdrView.PayItem();
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

        List< MjdrView.PayItem > data = getPayItemData( req, resp );
        Set< Long > itemIds = new TreeSet<>();
        data.forEach( info -> itemIds.addAll( info.items.keySet() ) );
        List< String > payItemTableHead = new LinkedList<>();
        payItemTableHead.add( "日期" );
        payItemTableHead.add( "购买人数" );
        itemIds.forEach( id -> payItemTableHead.add( Global.itemRegistry.getOrId( id ) ) );
        payItemTableHead.add( "总价值" );
        queryList( req, resp,
                CommandList.GET_MJDR, data,
                payItemTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    itemIds.forEach( id -> pw.write( "<td>" + getLong( info.items, id ) + "</td>" ) );
                    pw.write( "<td>" + Currency.format( info.total ) + "</td>" );
                } );
    }

    public void downloadPayItem( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< MjdrView.PayItem > data = getPayItemData( req, resp );
        Set< Long > itemIds = new TreeSet<>();
        data.forEach( info -> itemIds.addAll( info.items.keySet() ) );
        List< String > payItemTableHead = new LinkedList<>();
        payItemTableHead.add( "日期" );
        payItemTableHead.add( "购买人数" );
        itemIds.forEach( id -> payItemTableHead.add( Global.itemRegistry.getOrId( id ) ) );
        payItemTableHead.add( "总价值" );
        downloadList( req, resp,
                CommandList.GET_MJDR, data,
                payItemTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    itemIds.forEach( id -> pw.write( getLong( info.items, id ) + "," ) );
                    pw.write( Currency.format( info.total ) + "," );
                } );
    }

    private List< MjdrView.Stat > getStatData( HttpServletRequest req, HttpServletResponse resp, List< Integer > types )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.Stat val = new MjdrView.Stat();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            types.forEach( t -> val.zimo += ServiceManager.getSqlService().queryLong( "select coalesce( sum( zimo ), 0 ) " +
                    " from t_mjdr_stat " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = " + t + " and is_ccgames = 'Y' and is_robot = 'N';", c ) );
            types.forEach( t -> val.dianpao += ServiceManager.getSqlService().queryLong( "select coalesce( sum( dianpao ), 0 ) " +
                    " from t_mjdr_stat " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = " + t + " and is_ccgames = 'Y' and is_robot = 'N';", c ) );
            types.forEach( t -> val.minggang += ServiceManager.getSqlService().queryLong( "select coalesce( sum( minggang ), 0 ) " +
                    " from t_mjdr_stat " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = " + t + " and is_ccgames = 'Y' and is_robot = 'N';", c ) );
            types.forEach( t -> val.angang += ServiceManager.getSqlService().queryLong( "select coalesce( sum( angang ), 0 ) " +
                    " from t_mjdr_stat " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = " + t + " and is_ccgames = 'Y' and is_robot = 'N';", c ) );
            types.forEach( t -> val.people += ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mjdr_play " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = " + t + " and is_ccgames = 'Y' and is_robot = 'N';", c ) );
            types.forEach( t -> val.inning += ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_mjdr_inning " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = " + t + ";", c ) );
            Map< Long, Long > fanMap = new TreeMap<>();
            types.forEach( t -> ServiceManager.getSqlService().queryMapLongString( "select timestamp, fan " +
                    " from t_mjdr_stat " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = " + t + " and is_ccgames = 'Y' and is_robot = 'N';", c )
                    .forEach( ( k, v ) -> mergeMap( splitMap( v ), fanMap ) ) );
            interestFans.forEach( fan -> val.fanCount.add( getLong( fanMap, fan ) ) );
            return val;
        } );
    }

    public void queryStatXueliu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getStatData( req, resp, xueliuTypes ),
                statTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.inning + "</td>" );
                    pw.write( "<td>" + info.zimo + "</td>" );
                    pw.write( "<td>" + info.dianpao + "</td>" );
                    pw.write( "<td>" + info.minggang + "</td>" );
                    pw.write( "<td>" + info.angang + "</td>" );
                    info.fanCount.forEach( cnt -> pw.write( "<td>" + cnt + "</td>" ) );
                } );
    }

    public void downloadStatXueliu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getStatData( req, resp, xueliuTypes ),
                statTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.inning + "," );
                    pw.write( info.zimo + "," );
                    pw.write( info.dianpao + "," );
                    pw.write( info.minggang + "," );
                    pw.write( info.angang + "," );
                    info.fanCount.forEach( cnt -> pw.write( cnt + "," ) );
                } );
    }

    public void queryStatXuezhan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getStatData( req, resp, xuezhanTypes ),
                statTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.inning + "</td>" );
                    pw.write( "<td>" + info.zimo + "</td>" );
                    pw.write( "<td>" + info.dianpao + "</td>" );
                    pw.write( "<td>" + info.minggang + "</td>" );
                    pw.write( "<td>" + info.angang + "</td>" );
                    info.fanCount.forEach( cnt -> pw.write( "<td>" + cnt + "</td>" ) );
                } );
    }

    public void downloadStatXuezhan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getStatData( req, resp, xuezhanTypes ),
                statTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.inning + "," );
                    pw.write( info.zimo + "," );
                    pw.write( info.dianpao + "," );
                    pw.write( info.minggang + "," );
                    pw.write( info.angang + "," );
                    info.fanCount.forEach( cnt -> pw.write( cnt + "," ) );
                } );
    }

    private List< MjdrView.LotteryStat > getLotteryStatData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > peopleMap = ServiceManager.getSqlService().queryMapLongLong(
                    "select lottery_level, count( distinct user_id ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            " group by lottery_level;",
                    c );
            Map< Long, Long > countMap = ServiceManager.getSqlService().queryMapLongLong(
                    "select lottery_level, count( id ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            " group by lottery_level;",
                    c );

            MjdrView.LotteryStat val = new MjdrView.LotteryStat();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            for( int level = 1; level <= 6; ++level ) {
                long people = getLong( peopleMap, level );
                long count = getLong( countMap, level );
                val.people.add( people );
                val.count.add( count );
                val.totalPeople += people;
                val.totalCount += count;
            }
            return val;
        } );
    }

    public void queryLotteryStat( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_MJDR, getLotteryStatData( req, resp ),
                lotteryStatTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    for( int i = 0; i < info.people.size(); ++i ) {
                        pw.write( "<td>" + info.people.get( i ) + "(" + info.count.get( i ) + ")</td>" );
                    }
                    pw.write( "<td>" + info.totalCount + "</td>" );
                    pw.write( "<td>" + info.totalPeople + "</td>" );
                } );
    }

    public void downloadLotteryStat( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_MJDR, getLotteryStatData( req, resp ),
                lotteryStatTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    for( int i = 0; i < info.people.size(); ++i ) {
                        pw.write( info.people.get( i ) + "(" + info.count.get( i ) + ")," );
                    }
                    pw.write( info.totalCount + "," );
                    pw.write( info.totalPeople + "," );
                } );
    }

    private List< MjdrView.LotteryItem > getLotteryItemData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > payMap = ServiceManager.getSqlService().queryMapLongLong(
                    "select bonus_id, sum( bonus_count ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            " group by bonus_id;",
                    c );
            MjdrView.LotteryItem val = new MjdrView.LotteryItem();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.gold = getLong( payMap, -1 );
            val.quan = getLong( payMap, 17 );
            val.card5 = getLong( payMap, 18 );
            return val;
        } );
    }

    public void queryLotteryItem( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_MJDR, getLotteryItemData( req, resp ),
                lotteryItemTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + info.quan + "</td>" );
                    pw.write( "<td>" + info.card5 + "</td>" );
                } );
    }

    public void downloadLotteryItem( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getLotteryItemData( req, resp ),
                lotteryItemTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.gold + "," );
                    pw.write( info.quan + "," );
                    pw.write( info.card5 + "," );
                } );
    }

    public static long getTotalRake( long beg, long end, boolean isMobile, BiClient c ) {
        long grantInstall = ServiceManager.getCommonService().getGrantInstallGold( APPID_STR, beg, end, isMobile, c );
        long grantLogin = ServiceManager.getCommonService().getGrantLoginGold( APPID_STR, beg, end, isMobile, c );
        long grantHelp = ServiceManager.getCommonService().getGrantHelpGold( APPID_STR, beg, end, isMobile, c );
        long upgradePay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                " from t_mjdr_upgrade " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and is_ccgames = 'Y' and is_robot = 'N' " +
                "   and is_mobile = '" + boolString( isMobile ) + "';", c );
        long missionPay = ServiceManager.getMissionService().getGold( APPID_STR, beg, end, isMobile, c );
        long xuezhanIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                " from t_mjdr_rake " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and type in ( 1, 3 ) and is_ccgames = 'Y' " +
                "   and is_robot = 'N' and is_mobile = '" + boolString( isMobile ) + "';", c );
        long xueliuIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                " from t_mjdr_rake " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and type in ( 2, 4 ) and is_ccgames = 'Y' " +
                "   and is_robot = 'N' and is_mobile = '" + boolString( isMobile ) + "';", c );
        long errenIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                " from t_mjdr_rake " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and type = 5 and is_ccgames = 'Y' " +
                "   and is_robot = 'N' and is_mobile = '" + boolString( isMobile ) + "';", c );
        long aiWinLose = ServiceManager.getSqlService().queryLong( "select coalesce( -sum( gold ), 0 ) " +
                " from t_mjdr_win " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                "   and is_robot = 'N' and is_mobile = '" + boolString( isMobile ) + "';", c );
        long lotteryPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( bonus_count ), 0 ) " +
                " from t_mjdr_lottery " +
                " where " + beg + " <= timestamp and timestamp < " + end +
                " and bonus_id = -1 and is_mobile = '" + boolString( isMobile ) + "';", c );
        return xuezhanIncome + xueliuIncome + errenIncome -
                grantInstall - grantLogin - grantHelp -
                upgradePay - missionPay +
                aiWinLose - lotteryPay;
    }

    private List< MjdrView.LotterySum > getLotterySumData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.LotterySum val = new MjdrView.LotterySum();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.totalCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end + ";",
                    c );
            val.totalPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end + ";",
                    c );
            val.gold = ServiceManager.getSqlService().queryLong( "select coalesce( sum( bonus_count ), 0 ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and bonus_id = -1;",
                    c );
            val.quan = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and bonus_id = 17;",
                    c );
            val.baotu = ServiceManager.getSqlService().queryLong( "select sum( bonus_count ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and bonus_id = 10301;",
                    c );
            val.card5 = ServiceManager.getSqlService().queryLong( "select sum( bonus_count ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and bonus_id = 10102;",
                    c );
            val.card10 = ServiceManager.getSqlService().queryLong( "select sum( bonus_count ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and bonus_id = 10103;",
                    c );
            val.card20 = ServiceManager.getSqlService().queryLong( "select sum( bonus_count ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and bonus_id = 10104;",
                    c );
            val.card50 = ServiceManager.getSqlService().queryLong( "select sum( bonus_count ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and bonus_id = 10105;",
                    c );
            val.card100 = ServiceManager.getSqlService().queryLong( "select sum( bonus_count ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and bonus_id = 10106;",
                    c );
            val.card200 = ServiceManager.getSqlService().queryLong( "select sum( bonus_count ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and bonus_id = 10107;",
                    c );
            return val;
        } );
    }

    public void queryLotterySum( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_MJDR, getLotterySumData( req, resp ),
                lotterySumTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.totalPeople + "</td>" );
                    pw.write( "<td>" + info.totalCount + "</td>" );
                    pw.write( "<td>" + info.gold    + "</td>" );
                    pw.write( "<td>" + info.baotu   + "</td>" );
                    pw.write( "<td>" + info.card5   + "</td>" );
                    pw.write( "<td>" + info.card10  + "</td>" );
                    pw.write( "<td>" + info.card20  + "</td>" );
                    pw.write( "<td>" + info.card50  + "</td>" );
                    pw.write( "<td>" + info.card100 + "</td>" );
                    pw.write( "<td>" + info.card200 + "</td>" );
                } );
    }

    public void downloadLotterySum( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_MJDR, getLotterySumData( req, resp ),
                lotterySumTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.totalPeople + "," );
                    pw.write( info.totalCount + "," );
                    pw.write( info.gold    + "," );
                    pw.write( info.baotu   + "," );
                    pw.write( info.card5   + "," );
                    pw.write( info.card10  + "," );
                    pw.write( info.card20  + "," );
                    pw.write( info.card50  + "," );
                    pw.write( info.card100 + "," );
                    pw.write( info.card200 + "," );
                } );
    }

    private List< MjdrView.AiWinLose > getAiWinLoseData( HttpServletRequest req, HttpServletResponse resp, long type)
            throws Exception {
        return getDataList( req, resp, ( beg, end, c ) -> {
            MjdrView.AiWinLose val = new MjdrView.AiWinLose();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            if(type == 3)
            {
                val.gameName = "血战到底";
            }else if (type == 4)
            {
                val.gameName = "血流成河";
            }else if (type == 5)
            {
                val.gameName = "二人麻将";
            }

            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select level, coalesce( -sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N' and type = " + type +
                    " group by level;", c );

            val.xinShou = getLong( winMap, 1L );
            val.jingYing = getLong( winMap, 2L );
            val.daShen = getLong( winMap, 3L );
            val.tuHao = getLong( winMap, 4L );
            val.total = val.xinShou + val.jingYing + val.daShen + val.tuHao;
            return val;
        } );
    }

    public void queryXueliuAiWinLose( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        List< MjdrView.AiWinLose > data = getAiWinLoseData( req, resp, 4 );
        queryList( req, resp,
                CommandList.GET_MJDR, data,
                aiWinLoseTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.gameName + "</td>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.xinShou + "</td>" );
                    pw.write( "<td>" + info.jingYing + "</td>" );
                    pw.write( "<td>" + info.daShen + "</td>" );
                    pw.write( "<td>" + info.tuHao + "</td>" );
                    pw.write( "<td>" + info.total + "</td>" );
                } );
    }

    public void downloadXueliuAiWinLose( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< MjdrView.AiWinLose > data = getAiWinLoseData( req, resp, 4 );

        downloadList( req, resp,
                CommandList.GET_MJDR, data,
                aiWinLoseTableHead, ( info, pw ) -> {
                    pw.write( info.gameName + "," );
                    pw.write( info.begin + "," );
                    pw.write( info.xinShou + "," );
                    pw.write( info.jingYing + "," );
                    pw.write( info.daShen + "," );
                    pw.write( info.tuHao + "," );
                    pw.write( info.total + "," );
                } );
    }
    public void queryXuezhanAiWinLose( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        List< MjdrView.AiWinLose > data = getAiWinLoseData( req, resp, 3 );
        queryList( req, resp,
                CommandList.GET_MJDR, data,
                aiWinLoseTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.gameName + "</td>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.xinShou + "</td>" );
                    pw.write( "<td>" + info.jingYing + "</td>" );
                    pw.write( "<td>" + info.daShen + "</td>" );
                    pw.write( "<td>" + info.tuHao + "</td>" );
                    pw.write( "<td>" + info.total + "</td>" );
                } );
    }

    public void downloadXuezhanAiWinLose( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< MjdrView.AiWinLose > data = getAiWinLoseData( req, resp, 3 );

        downloadList( req, resp,
                CommandList.GET_MJDR, data,
                aiWinLoseTableHead, ( info, pw ) -> {
                    pw.write( info.gameName + "," );
                    pw.write( info.begin + "," );
                    pw.write( info.xinShou + "," );
                    pw.write( info.jingYing + "," );
                    pw.write( info.daShen + "," );
                    pw.write( info.tuHao + "," );
                    pw.write( info.total + "," );
                } );
    }
    public void queryermaAiWinLose( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        List< MjdrView.AiWinLose > data = getAiWinLoseData( req, resp, 5 );
        queryList( req, resp,
                CommandList.GET_MJDR, data,
                aiWinLoseTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.gameName + "</td>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.xinShou + "</td>" );
                    pw.write( "<td>" + info.jingYing + "</td>" );
                    pw.write( "<td>" + info.daShen + "</td>" );
                    pw.write( "<td>" + info.tuHao + "</td>" );
                    pw.write( "<td>" + info.total + "</td>" );
                } );
    }

    public void downloadermaAiWinLose( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< MjdrView.AiWinLose > data = getAiWinLoseData( req, resp,5 );

        downloadList( req, resp,
                CommandList.GET_MJDR, data,
                aiWinLoseTableHead, ( info, pw ) -> {
                    pw.write( info.gameName + "," );
                    pw.write( info.begin + "," );
                    pw.write( info.xinShou + "," );
                    pw.write( info.jingYing + "," );
                    pw.write( info.daShen + "," );
                    pw.write( info.tuHao + "," );
                    pw.write( info.total + "," );
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
                CommandList.GET_MJDR, getPhoneCardRetentionData( req, resp ),
                phoneCardRetentionTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.getCardPeople + "</td>" );
                    info.retentions.forEach( retention -> pw.write( "<td>" + formatRatio( retention / (double) info.getCardPeople ) + "</td>" ) );
                } );
    }

    public void downloadPhoneCardRetention( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_MJDR, getPhoneCardRetentionData( req, resp ),
                phoneCardRetentionTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.getCardPeople + "," );
                    info.retentions.forEach( retention -> pw.write( formatRatio( retention / (double) info.getCardPeople ) + "," ) );
                } );
    }

}