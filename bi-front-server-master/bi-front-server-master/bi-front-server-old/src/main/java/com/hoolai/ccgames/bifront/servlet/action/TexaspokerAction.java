package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bi.protocol.Currency;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.starter.Global;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.TexaspokerView;
import com.hoolai.ccgames.bifront.vo.baseView;
import com.hoolai.centersdk.item.ItemIdUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service( "texaspokerAction" )
public class TexaspokerAction extends BaseAction {

    private static final String APPID_STR = "100632434";

    private static List< String > youlunTableHead = Arrays.asList( "日期", "总抽水", "参与人数", "大师积分支出", "排名奖励支出", "其它支出", "净抽水" );
    private static List< String > rankTableHead = Arrays.asList( "玩家ID", "玩家昵称", "总下注额度", "获胜总额", "获得返水", "总净胜筹码", "盈利率" );
    private static List< String > payDetailTableHead = Arrays.asList( "时间",
            "玩家ID", "玩家昵称", "道具名称", "道具数量", "付费" );
    private static List< String > huiZongTableHead = Arrays.asList( "时间",
            "德州总抽水", "摇奖机总抽水", "定时比赛抽水", "转盘支出", "邮轮赛抽水", "老德州总收入");
    private static List< String > PokerJichuTableHead = Arrays.asList( "日期", "新手人数", "新手手数", "新手抽水"
            , "中级人数", "中级手数", "中级抽水", "专家人数", "专家手数", "专家抽水", "大师人数", "大师手数", "大师抽水"
            ,"总抽水","购买桌卡数量","创建牌桌次数");
    private static List< String > zhuanpanTableHead = Arrays.asList( "日期", "转盘人数", "转盘次数", "胡莱币支出"
            , "神木钥支出", "游戏币支出", "中级参赛券", "中级参赛券碎片", "初级参赛券支出");
    private static List< String > dingshiTableHead = Arrays.asList( "日期", "总人数", "游戏币支出", "中级参赛券碎片", "牌神请帖");
    private static List< String > yaojiangTableHead = Arrays.asList( "日期", "总人数", "总投注", "投注返奖", "彩池返奖", "摇奖机总抽水");


    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_TEXASPOKER ).forward( req, resp );
    }

    public void getRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_TEXASPOKER_RANK ).forward( req, resp );
    }

    public void getYoulunPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_TEXASPOKER_YOULUN ).forward( req, resp );
    }
    public void getHuizongPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_TEXASPOKER_HUIZONG ).forward( req, resp );
    }
    public void getJichuPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_TEXASPOKER_JICHU ).forward( req, resp );
    }
    public void getTimerPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_TEXASPOKER_TIMER ).forward( req, resp );
    }
    public void getYaojiangjiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_TEXASPOKER_YAOJIANGJI ).forward( req, resp );
    }
    public void getZhuanpanPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_TEXASPOKER_ZHUANPAN ).forward( req, resp );
    }


    public void getPayDetailPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_TEXASPOKER_PAY_DETAIL ).forward( req, resp );
    }
    public void getLiushuiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_TEXASPOKER_LIUSHUI ).forward( req, resp );
    }

    private static List< String > LiushuiTableHead = Arrays.asList( "日期",
            "新手场流水", "中级场流水","专家场流水", "大师场流水", "邮轮赛流水" ,"总流水" );
    private List< baseView.Liushui > getLiushuiData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.Liushui val = new baseView.Liushui();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            Map< String, Long > dataMap = ServiceManager.getSqlService().queryMapStringLong( "select desk_type, sum( chips ) " +
                    " from t_gdesk_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and appid in ( 100632434 ,1104737759 ) and is_robot ='N' and chips > 0" +
                    " group by desk_type;", c );

            val.changci1 = getLong( dataMap, "beginner" );
            val.changci2 = getLong( dataMap, "medium" );
            val.changci3 = getLong( dataMap, "expert" );
            val.changci4 = getLong( dataMap, "master" );
            val.changci5 = getLong( dataMap, "vip" );

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
                    pw.write( "<td>" + (info.changci1 + info.changci2 + info.changci3 + info.changci4+ info.changci5 ) + "</td>" );
                } );
    }

    public void downloadLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.changci1 + "," );
                    pw.write( info.changci2 + "," );
                    pw.write( info.changci3 + "," );
                    pw.write( info.changci4 + "," );
                    pw.write( info.changci5 + "," );
                    pw.write( (info.changci1 + info.changci2 + info.changci3 + info.changci4+ info.changci5 ) + "," );
                } );
    }
    private List< TexaspokerView.yaojiangji > getYaojiangData(HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            TexaspokerView.yaojiangji val = new TexaspokerView.yaojiangji();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.People     = ServiceManager.getSqlService().queryLong( "select count( distinct user_id  ) " +
                    " from t_yyl_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N';", c );

            val.zongtouzhu      = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_yyl_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N';", c );

            val.fanjiang = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_yyl_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' ;", c );
            val.zong = val.zongtouzhu - val.fanjiang;

            return val;
        } );
    }

    public void queryYaojiang( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< TexaspokerView.yaojiangji > data = getYaojiangData( req, resp, false );

        queryList( req, resp,
                CommandList.GET_TEXASPOKER,
                data, yaojiangTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin           + "</td>" );
                    pw.write( "<td>" + info.People     + "</td>" );
                    pw.write( "<td>" + info.zongtouzhu  + "</td>" );
                    pw.write( "<td>" + info.fanjiang     + "</td>" );
                    pw.write( "<td>" + info.caichi   + "</td>" );
                    pw.write( "<td>" + info.zong   + "</td>" );

                });
    }

    public void downloadYaojiang( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        List<TexaspokerView.yaojiangji> data = getYaojiangData(req, resp, false);
        downloadList(req, resp,
                CommandList.GET_TEXASPOKER,
                data, yaojiangTableHead, (info, pw) -> {
                    pw.write(info.begin + ",");
                    pw.write(info.People + ",");
                    pw.write(info.zongtouzhu + ",");
                    pw.write(info.fanjiang + ",");
                    pw.write(info.caichi + ",");
                    pw.write(info.zong + ",");

                });
    }

    private List< TexaspokerView.dingshi > getdingShiData(HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            TexaspokerView.dingshi val = new TexaspokerView.dingshi();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.People     = ServiceManager.getSqlService().queryLong( "select count( distinct user_id  ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'timer';", c );

            val.Gold      = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'timer';", c );

            val.zhongjisuipian = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'timer' and item_id = "+ ItemIdUtils.ITEM_ID_109 +";", c );
            val.qingtie = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'gdeskLuckyWheelBonus' and item_id = "+ ItemIdUtils.ITEM_ID_502 +";", c );

            return val;
        } );
    }

    public void queryDingShi( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< TexaspokerView.dingshi > data = getdingShiData( req, resp, false );

        queryList( req, resp,
                CommandList.GET_TEXASPOKER,
                data, dingshiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin           + "</td>" );
                    pw.write( "<td>" + info.People     + "</td>" );
                    pw.write( "<td>" + info.Gold  + "</td>" );
                    pw.write( "<td>" + info.zhongjisuipian     + "</td>" );
                    pw.write( "<td>" + info.qingtie   + "</td>" );

                });
    }

    public void downloadDingShi( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        List<TexaspokerView.dingshi> data = getdingShiData(req, resp, false);
        downloadList(req, resp,
                CommandList.GET_TEXASPOKER,
                data, dingshiTableHead, (info, pw) -> {
                    pw.write(info.begin + ",");
                    pw.write(info.People + ",");
                    pw.write(info.Gold + ",");
                    pw.write(info.zhongjisuipian + ",");
                    pw.write(info.qingtie + ",");

                });
    }
    private List< TexaspokerView.zhuanpan > getzhuanPanData(HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            TexaspokerView.zhuanpan val = new TexaspokerView.zhuanpan();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.zhuanpanPeople     = ServiceManager.getSqlService().queryLong( "select count( distinct user_id  ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'gdeskLuckyWheelBonus';", c );
            val.zhuanpanCount      = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'gdeskLuckyWheelBonus';", c );

            val.shenmu      = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'gdeskLuckyWheelBonus' and item_id = "+ ItemIdUtils.ITEM_ID_111 +";", c );
            val.hulaibi      = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'gdeskLuckyWheelBonus' and item_id = "+ ItemIdUtils.ITEM_ID_546 +";", c );
            val.Gold      = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'gdeskLuckyWheelBonus';", c );
            val.zhongjicansai      = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'gdeskLuckyWheelBonus' and item_id = "+ ItemIdUtils.ITEM_ID_102 +";", c );
            val.zhongjisuipian = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'gdeskLuckyWheelBonus' and item_id = "+ ItemIdUtils.ITEM_ID_109 +";", c );
            val.chujicansai = ServiceManager.getSqlService().queryLong( "select count( id  ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'gdeskLuckyWheelBonus' and item_id = "+ ItemIdUtils.ITEM_ID_101 +";", c );

            return val;
        } );
    }

    public void queryZhuanPan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< TexaspokerView.zhuanpan > data = getzhuanPanData( req, resp, false );

        queryList( req, resp,
                CommandList.GET_TEXASPOKER,
                data, zhuanpanTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin           + "</td>" );
                    pw.write( "<td>" + info.zhuanpanPeople     + "</td>" );
                    pw.write( "<td>" + info.zhuanpanCount        + "</td>" );
                    pw.write( "<td>" + info.hulaibi     + "</td>" );
                    pw.write( "<td>" + info.shenmu   + "</td>" );
                    pw.write( "<td>" + info.Gold     + "</td>" );
                    pw.write( "<td>" + info.zhongjicansai    + "</td>" );
                    pw.write( "<td>" + info.zhongjisuipian    + "</td>" );
                    pw.write( "<td>" + info.chujicansai   + "</td>" );

                });
    }

    public void downloadZhuanPan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        List< TexaspokerView.zhuanpan > data = getzhuanPanData( req, resp, false );
        downloadList( req, resp,
                CommandList.GET_TEXASPOKER,
                data, zhuanpanTableHead, ( info, pw ) -> {
                    pw.write( info.begin            + "," );
                    pw.write( info.zhuanpanPeople      + "," );
                    pw.write( info.zhuanpanCount         + "," );
                    pw.write( info.hulaibi        + "," );
                    pw.write( info.shenmu    + "," );
                    pw.write( info.Gold       + "," );
                    pw.write( info.zhongjicansai      + "," );
                    pw.write( info.zhongjisuipian      + "," );
                    pw.write( info.chujicansai      + "," );

                });
    }
    private List< TexaspokerView.huizong > gethuiZongData(HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            TexaspokerView.huizong val = new TexaspokerView.huizong();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.dezhouRake     = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' ;", c );
            val.yaojiangjiRake     = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_yyl_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N';", c ) - ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_yyl_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N';", c );
            val.dingshiOut = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind ='timer';", c );

            val.zhuanpanOut  = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and kind = 'gdeskLuckyWheelBonus';", c );

            long totalPump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_gdesk_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N' and desk_type = 'vip';", c );
            long playerCount = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_gdesk_play " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N' and desk_type = 'vip';", c );
            long masterPointPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( master_point ), 0 ) " +
                    " from t_mp_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N' and source = 0;", c );
            long bonusChips = ( playerCount > 0L ? 55000000L : 0L );
            long jackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_gdesk_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N' and desk_type = 'vip';", c );
            long rebatePay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N' and desk_type = 'vip';", c );
            long OlddezhouRake     = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and desk_type not in ('chuji','zhongji','gaoji','chaoji','dashi','zhizun');", c );
            long otherPay = jackpotPay + rebatePay;
            val.youlunRake =   totalPump - bonusChips - otherPay;

            val.zhongRake = OlddezhouRake  + val.yaojiangjiRake + val.youlunRake - val.dingshiOut - val.zhuanpanOut;

            return val;
        } );
    }

    public void queryHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< TexaspokerView.huizong > data = gethuiZongData( req, resp, false );

        queryList( req, resp,
                CommandList.GET_TEXASPOKER,
                data, huiZongTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin           + "</td>" );
                    pw.write( "<td>" + info.dezhouRake     + "</td>" );
                    pw.write( "<td>" + info.yaojiangjiRake        + "</td>" );
                    pw.write( "<td>" + info.dingshiOut       + "</td>" );
                    pw.write( "<td>" + info.zhuanpanOut   + "</td>" );
                    pw.write( "<td>" + info.youlunRake     + "</td>" );
                    pw.write( "<td>" + info.zhongRake     + "</td>" );

                });
    }

    public void downloadHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        List< TexaspokerView.huizong > data = gethuiZongData( req, resp, false );
        downloadList( req, resp,
                CommandList.GET_TEXASPOKER,
                data, huiZongTableHead, ( info, pw ) -> {
                    pw.write( info.begin            + "," );
                    pw.write( info.dezhouRake      + "," );
                    pw.write( info.yaojiangjiRake         + "," );
                    pw.write( info.dingshiOut        + "," );
                    pw.write( info.zhuanpanOut    + "," );
                    pw.write( info.youlunRake       + "," );
                    pw.write( info.zhongRake      + "," );

                });
    }

    private List< TexaspokerView.jichu > getjichuData(HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            TexaspokerView.jichu val = new TexaspokerView.jichu();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.xinshouPeople     = ServiceManager.getNewPokerService().getchangciPeople("beginner",APPID_STR,beg,end,false , c);
            val.zhongjiPeople   = ServiceManager.getNewPokerService().getchangciPeople("medium",APPID_STR,beg,end,false , c);
            val.zhuanjiaPeople     = ServiceManager.getNewPokerService().getchangciPeople("expert",APPID_STR,beg,end,false , c);
            val.dashiPeople     = ServiceManager.getNewPokerService().getchangciPeople("master",APPID_STR,beg,end,false , c);


            val.xinshouNum    = ServiceManager.getNewPokerService().getchangciNum("beginner",APPID_STR,beg,end,false , c);
            val.zhongjiNum  = ServiceManager.getNewPokerService().getchangciNum("medium",APPID_STR,beg,end,false , c);
            val.zhuanjiaNum    = ServiceManager.getNewPokerService().getchangciNum("expert",APPID_STR,beg,end,false , c);
            val.dashiNum    = ServiceManager.getNewPokerService().getchangciNum("master",APPID_STR,beg,end,false , c);

            val.xinshouRake   = ServiceManager.getNewPokerService().getchangciRake("beginner",APPID_STR,beg,end,false , c);
            val.zhongjiRake = ServiceManager.getNewPokerService().getchangciRake("medium",APPID_STR,beg,end,false , c);
            val.zhuanjiaRake   = ServiceManager.getNewPokerService().getchangciRake("expert",APPID_STR,beg,end,false , c);
            val.dashiRake   = ServiceManager.getNewPokerService().getchangciRake("master",APPID_STR,beg,end,false , c);

            val.zongRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid ='"+ APPID_STR +"' and is_robot ='N';", c );

            val.fangkaBuy = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid ='"+ APPID_STR +"' and item_id in ( 10536 , 10538 );", c );
            val.fangkaUse = ServiceManager.getSqlService().queryLong( "select count( distinct desk_id ) " +
                    " from t_gdesk_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid ='"+ APPID_STR +"' and desk_type = 'special' ;", c ) * (-1);


            return val;
        } );
    }

    public void queryJichu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< TexaspokerView.jichu > data = getjichuData( req, resp, false );

        queryList( req, resp,
                CommandList.GET_TEXASPOKER,
                data, PokerJichuTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin           + "</td>" );
                    pw.write( "<td>" + info.xinshouPeople     + "</td>" );
                    pw.write( "<td>" + info.xinshouNum        + "</td>" );
                    pw.write( "<td>" + info.xinshouRake       + "</td>" );
                    pw.write( "<td>" + info.zhongjiPeople   + "</td>" );
                    pw.write( "<td>" + info.zhongjiNum      + "</td>" );
                    pw.write( "<td>" + info.zhongjiRake     + "</td>" );
                    pw.write( "<td>" + info.zhuanjiaPeople     + "</td>" );
                    pw.write( "<td>" + info.zhuanjiaNum        + "</td>" );
                    pw.write( "<td>" + info.zhuanjiaRake       + "</td>" );
                    pw.write( "<td>" + info.dashiPeople     + "</td>" );
                    pw.write( "<td>" + info.dashiNum        + "</td>" );
                    pw.write( "<td>" + info.dashiRake       + "</td>" );
                    pw.write( "<td>" + info.zongRake        + "</td>" );
                    pw.write( "<td>" + info.fangkaBuy        + "</td>" );
                    pw.write( "<td>" + info.fangkaUse       + "</td>" );

                });
    }

    public void downloadJichu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        List< TexaspokerView.jichu > data = getjichuData( req, resp, false );
        downloadList( req, resp,
                CommandList.GET_TEXASPOKER,
                data, PokerJichuTableHead, ( info, pw ) -> {
                    pw.write( info.begin                + "," );
                    pw.write( info.xinshouPeople        + "," );
                    pw.write( info.xinshouNum           + "," );
                    pw.write( info.xinshouRake          + "," );
                    pw.write( info.zhongjiPeople        + "," );
                    pw.write( info.zhongjiNum           + "," );
                    pw.write( info.zhongjiRake          + "," );
                    pw.write( info.zhuanjiaPeople       + "," );
                    pw.write( info.zhuanjiaNum          + "," );
                    pw.write( info.zhuanjiaRake         + "," );
                    pw.write( info.dashiPeople          + "," );
                    pw.write( info.dashiNum             + "," );
                    pw.write( info.dashiRake            + "," );
                    pw.write( info.zongRake             + "," );
                    pw.write( info.fangkaBuy             + "," );
                    pw.write( info.fangkaUse             + "," );

                });
    }


    private List< List< TexaspokerView.Rank > > getRankData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        int limit = Integer.parseInt( req.getParameter( "limit" ) );
        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > betMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_gdesk_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid in ( 100632434 ,1104737759 ) group by user_id;", c );
            // 包括抽水的盈利
//            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select A.winner_id, sum( A.chips ) " +
//                    " from ( select t_gdesk_win_detail.*, t_install.appid from t_gdesk_win_detail left join t_install on t_gdesk_win_detail.winner_id = t_install.user_id ) A " +
//                    " where " + beg + " <= A.timestamp and A.timestamp < " + end +
//                    " and A.appid in ( 100632434 ,1104737759 ) group by A.winner_id;", c );
//            Map< Long, Long > loseMap = ServiceManager.getSqlService().queryMapLongLong( "select A.loser_id, sum( A.chips ) " +
//                    " from ( select t_gdesk_win_detail.*, t_install.appid from t_gdesk_win_detail left join t_install on t_gdesk_win_detail.loser_id = t_install.user_id ) A " +
//                    " where " + beg + " <= A.timestamp and A.timestamp < " + end +
//                    "  and A.appid in ( 100632434 ,1104737759 ) group by A.loser_id;", c );

            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select A.winner_id, sum( A.chips ) " +
                    " from ( select t_gdesk_win_detail.winner_id, t_gdesk_win_detail.chips, t_install.appid " +
                    "        from t_gdesk_win_detail " +
                    "        left join t_install " +
                    "        on t_gdesk_win_detail.winner_id = t_install.user_id " +
                    "        where " + beg + " <= t_gdesk_win_detail.timestamp and t_gdesk_win_detail.timestamp < " + end +
                    "          and coalesce( t_install.appid, '100632434' ) in ( '100632434', '1104737759' ) " +
                    "      ) as A " +
                    " group by A.winner_id;", c );

            Map< Long, Long > loseMap = ServiceManager.getSqlService().queryMapLongLong( "select A.loser_id, sum( A.chips ) " +
                    " from ( select t_gdesk_win_detail.loser_id, t_gdesk_win_detail.chips, t_install.appid " +
                    "        from t_gdesk_win_detail " +
                    "        left join t_install " +
                    "        on t_gdesk_win_detail.loser_id = t_install.user_id " +
                    "        where " + beg + " <= t_gdesk_win_detail.timestamp and t_gdesk_win_detail.timestamp < " + end +
                    "          and coalesce( t_install.appid, '100632434' ) in ( '100632434', '1104737759' ) " +
                    "      ) as A " +
                    " group by A.loser_id;", c );

            Map< Long, Long > jackpotMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_gdesk_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid in ( 100632434 ,1104737759 ) group by user_id;", c );
            // 现金桌返利，包括忽来横财，幸运转盘，神秘道具
            Map< Long, Long > rebateMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid in ( 100632434 ,1104737759 ) group by user_id;", c );
            Map< Long, Long > pumpMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( chips ) " +
                    " from t_gdesk_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and appid in ( 100632434 ,1104737759 ) group by user_id;", c );
            Map< Long, TexaspokerView.Rank > sum = new HashMap<>();

            betMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new TexaspokerView.Rank() );
                sum.get( k ).totalBet += v;
            } );

            winMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new TexaspokerView.Rank() );
                sum.get( k ).totalWin += v;
            } );

            loseMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new TexaspokerView.Rank() );
                sum.get( k ).totalWin -= v;
            } );

            jackpotMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new TexaspokerView.Rank() );
                sum.get( k ).totalRebate += v;
            } );

            rebateMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new TexaspokerView.Rank() );
                sum.get( k ).totalRebate += v;
            } );

            pumpMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new TexaspokerView.Rank() );
                sum.get( k ).totalPump += v;
            } );

            List< TexaspokerView.Rank > winList = new ArrayList<>();
            List< TexaspokerView.Rank > loseList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.userId = k;
                v.netWin = v.totalWin + v.totalRebate - v.totalPump;
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
                CommandList.GET_TEXASPOKER, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.totalWin + "</td>" );
                    pw.write( "<td>" + info.totalRebate + "</td>" );
                    pw.write( "<td>" + info.netWin + "</td>" );
                    pw.write( "<td>" + formatRatio( info.winRatio ) + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_TEXASPOKER, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.totalWin + "," );
                    pw.write( info.totalRebate + "," );
                    pw.write( info.netWin + "," );
                    pw.write( formatRatio( info.winRatio ) + "," );
                } );
    }

    private List< TexaspokerView.Youlun > getYoulunData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            TexaspokerView.Youlun val = new TexaspokerView.Youlun();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.totalPump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_gdesk_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N' and appid in ( 100632434 ,1104737759 ) and desk_type = 'vip';", c );
            val.playerCount = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_gdesk_play " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N' and appid in ( 100632434 ,1104737759 ) and desk_type = 'vip';", c );
            val.masterPointPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( master_point ), 0 ) " +
                    " from t_mp_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N' and appid in ( 100632434 ,1104737759 ) and source = 0;", c );
            long bonusChips = ( val.playerCount > 0L ? 55000000L : 0L );
            long jackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_gdesk_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N' and appid in ( 100632434 ,1104737759 ) and desk_type = 'vip';", c );
            long rebatePay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_gdesk_expense " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_robot = 'N' and desk_type = 'vip';", c );
            val.otherPay = jackpotPay + rebatePay;
            val.netPump = val.totalPump - bonusChips - val.otherPay;
            return val;
        } );
    }

    public void queryYoulun( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_TEXASPOKER, getYoulunData( req, resp ),
                youlunTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.totalPump + "</td>" );
                    pw.write( "<td>" + info.playerCount + "</td>" );
                    pw.write( "<td>" + info.masterPointPay + "</td>" );
                    pw.write( "<td>" + info.bonusPay + "</td>" );
                    pw.write( "<td>" + info.otherPay + "</td>" );
                    pw.write( "<td>" + info.netPump + "</td>" );
                } );
    }

    public void downloadYoulun( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_TEXASPOKER, getYoulunData( req, resp ),
                youlunTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.totalPump + "," );
                    pw.write( info.playerCount + "," );
                    pw.write( info.masterPointPay + "," );
                    pw.write( info.bonusPay + "," );
                    pw.write( info.otherPay + "," );
                    pw.write( info.netPump + "," );
                } );
    }

    private List< TexaspokerView.PayDetail > getPayDetailData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            List< TexaspokerView.PayDetail > list = new LinkedList<>();
            Map< Long, String > payMap = ServiceManager.getSqlService().queryMapLongString( "select timestamp, user_id, item_id, item_buy_count, money2 + money3 as money " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and appid in ( 100632434 ,1104737759 ) ;", c );
            payMap.forEach( ( k, v ) -> {
                TexaspokerView.PayDetail val = new TexaspokerView.PayDetail();
                val.beg = TimeUtil.ymdhmsFormat().format( k );
                String[] params = v.split( "\n" );
                val.userId = Long.parseLong( params[0] );
                val.itemId = Long.parseLong( params[1] );
                val.itemCount = Long.parseLong( params[2] );
                val.money = Long.parseLong( params[3] );
                list.add( val );
            } );
            return list;
        } );
    }

    public void queryPayDetail( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_TEXASPOKER, getPayDetailData( req, resp ),
                payDetailTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.beg + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + Global.itemRegistry.getOrId( info.itemId ) + "</td>" );
                    pw.write( "<td>" + info.itemCount + "</td>" );
                    pw.write( "<td>" + Currency.format( info.money ) + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadPayDetail( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_TEXASPOKER, getPayDetailData( req, resp ),
                payDetailTableHead, ( info, pw ) -> {
                    pw.write( info.beg + "," );
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( Global.itemRegistry.getOrId( info.itemId ) + "," );
                    pw.write( info.itemCount + "," );
                    pw.write( Currency.format( info.money ) + "," );
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
                    " from t_gdesk_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and appid in ( 100632434 ,1104737759 ) ;", c );
            val.jackportCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_gdesk_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and appid in ( 100632434 ,1104737759 ) ;", c );
            val.jackport = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and appid in ( 100632434 ,1104737759 ) ;", c );
            return val;
        } );
    }

    public void queryJackport( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_TEXASPOKER, getJackportData( req, resp ),
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
                CommandList.GET_TEXASPOKER, getJackportData( req, resp ),
                jackportTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.jackportPeople+ "," );
                    pw.write( info.jackportCount + "," );
                    pw.write( info.jackport + "," );
                } );
    }

}