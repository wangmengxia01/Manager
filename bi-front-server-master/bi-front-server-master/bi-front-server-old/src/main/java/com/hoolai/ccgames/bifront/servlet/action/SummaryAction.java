package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bi.protocol.Currency;
import com.hoolai.ccgames.bifront.net.BiClient;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.servlet.GameCollection;
import com.hoolai.ccgames.bifront.util.CommonUtil;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.SummaryView;
import com.hoolai.ccgames.bifront.vo.ZhuboView;
import com.hoolai.centersdk.item.ItemManager;
import com.hoolai.centersdk.sdk.UserSdk;
import flex.messaging.config.ChannelSettings;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service( "summaryAction" )
public class SummaryAction extends BaseAction {

    public static List< String > lianyun = Arrays.asList( "2516", "2513","2512","2511","2506","2498","2496","2492","2465","2545","585901","1106207849","9889988","2887","2973","2988","3285");
    public static List< String > IOS = Arrays.asList( "2452", "2298","2173","wx1be902a48209d13c","1251807724","IOS","1239313319");
    public static List< String > CESHI = Arrays.asList("2968");
    public static List< String > mengxiao = Arrays.asList("3287","3115","2969","3093","3292","2955","2954","2953","2952","2158","2950","2958","2957","2956","2959","2961","2962","2960","3323");
    public static List< String > YIYEHEZUO = Arrays.asList("2969");
    public static List< String > qudao = Arrays.asList( "2516", "2513","2512","2511","2506","2498","2496","2492","2465","2545","585901","1106207849","9889988","2887","2973");
//    private static List< String > zimailiang = Arrays.asList( "2152", "2260","2154","2155","2156","2160","2157","2158","2159"
//            ,"2161","2175","2183","2184","2202","2203","2204","2205","2271 ","2276","2206","2207","2208");

    public long GetItem(long beg, long end,  long itemId, long type, BiClient c)
    {
        List<String> getinfo = ServiceManager.getSqlService().queryListString( "select bouns " +
                " from t_fish_mb_grants " +
                " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + type +" ;", c );

        Map< Long, Long > getMap = new HashMap<>();
        getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
        long a =    getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000 + getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000;
        return a ;
    }


    public static final Map<String , String> QuDao = new HashMap<String, String>();
    static {
        QuDao.put("2152", "今日头条");              QuDao.put("2260", "UC");
        QuDao.put("2154", "广点通外链");            QuDao.put("2155", "UC");
        QuDao.put("2156", "博点DSP");               QuDao.put("2160", "斗鱼");
        QuDao.put("2157", "斗鱼");                  QuDao.put("2158", "猎豹");
        QuDao.put("2159", "猎豹");                  QuDao.put("2161", "斗鱼");
        QuDao.put("2175", "内涵段子GD");            QuDao.put("2183", "wifi万能钥匙");
        QuDao.put("2184", "wifi万能钥匙");          QuDao.put("2202", "搜狗助手");
        QuDao.put("2203", "一点资讯");              QuDao.put("2204", "信淼今日头条");
        QuDao.put("2205", "晋拓今日头条");          QuDao.put("2271", "百度SEM");
        QuDao.put("2276", "百度SEM");               QuDao.put("2516", "UC");
        QuDao.put("2513", "联想");                  QuDao.put("2512", "OPPO");
        QuDao.put("2511", "小米");                  QuDao.put("2506", "VIVO");
        QuDao.put("2498", "华为");                  QuDao.put("2496", "酷派");
        QuDao.put("2492", "360");                   QuDao.put("2465", "魅族");
        QuDao.put("2173", "IOS-正式");              QuDao.put("2452", "IOS");
        QuDao.put("2298", "IOS");                   QuDao.put("2206", "官网包");
        QuDao.put("2207", "官方QQ下载");            QuDao.put("2208", "兔子直播");
        QuDao.put("wx1be902a48209d13c", "IOS微信"); QuDao.put("1251807724", "IOS");
        QuDao.put("2153", "UC");                    QuDao.put("2509", "懂球帝");
        QuDao.put("2545", "金立");                  QuDao.put("2540", "mobsink");
        QuDao.put("IOS", "IOS");                    QuDao.put("2541", "渠聚wap");
        QuDao.put("2542", "搜狗奇点");              QuDao.put("2543", "搜狗奇点");
        QuDao.put("2590", "搜狗奇点");              QuDao.put("2591", "搜狗奇点");
        QuDao.put("2610", "搜狗奇点");              QuDao.put("2611", "搜狗奇点");
        QuDao.put("2592", "搜狗奇点");              QuDao.put("MIXI", "德州");
        QuDao.put("ALL", "汇总");                   QuDao.put("2785", "互乐");
        QuDao.put("2600", "粉丝通");                QuDao.put("2601", "mobsink");
        QuDao.put("2602", "金立官网");              QuDao.put("2790", "酷派");
        QuDao.put("2624", "官网包");                QuDao.put("1239313319", "IOS");
        QuDao.put("585901", "新游(电视)");          QuDao.put("9889988", "百度");
        QuDao.put("1106207849", "应用宝");          QuDao.put("2788", "万圣");
        QuDao.put("2977", "百度信息流");            QuDao.put("2887", "应用宝");
        QuDao.put("2950", "主播");                  QuDao.put("2973", "联运VX公众号");
        QuDao.put("2968", "捕鱼u3d版");             QuDao.put("2969", "大玩家");
        QuDao.put("2971", "VX公众号");              QuDao.put("2988", "VIVO");
        QuDao.put("2950", "麦哥");                  QuDao.put("2952", "宇洋");
        QuDao.put("2953", "快乐");                  QuDao.put("3059", "斗鱼信息流");
        QuDao.put("2954", "花妖");                  QuDao.put("2955", "静静");
        QuDao.put("2956", "葫芦");                  QuDao.put("3121", "百度贴吧");
        QuDao.put("2957", "白小黑");                QuDao.put("2958", "闹闹");
        QuDao.put("3093", "电梯");                  QuDao.put("3114", "微信1");
        QuDao.put("3115", "微信2");                 QuDao.put("3285", "今日头条");
        QuDao.put("2959", "毒药");                  QuDao.put("3292", "绅士阿兴");
        QuDao.put("3287", "KKJ");                   QuDao.put("2960", "俊俊");
        QuDao.put("3323", "霸王鹤勇");              QuDao.put("b477860b9724470cba748d4bae67beee", "火柴");

    }

    private static List< String > financeTableHead = Arrays.asList( "日期",
            "新德州收入", "老德州收入", "中发白收入", "摩天轮收入", "血流成河收入", "血战到底收入",
            "斗地主收入", "二麻收入", "泡泡堂收入","水果机收入", "汪汪运动会收入","捕鱼收入", "公共玩法支出","麻将抽奖支出","总抽水" );
    private static List< String > FishFinanceTableHead = Arrays.asList( "日期",
            "中发白抽水", "摩天轮抽水", "百人德州抽水","捕鱼抽水","跑狗抽水", "总游戏币产出","总抽水" );
    private static List< String > huizongTableHead = Arrays.asList( "日期",
            "游戏币收入","人民币收入");
    private static List< String > FishihuizongTableHead = Arrays.asList( "日期",
            "总投放","总抽水","总充值","实际比例(（总投放-总抽水）/总充值 ))");
    private static List< String > LiushuiTableHead = Arrays.asList( "日期",
            "老德州流水","新德州流水","中发白总投注","摩天轮总投注","跑跑堂总投注","跑狗总投注","水果机总投注","斗地主流水",
            "血流流水","血战流水","二麻流水","捕鱼流水","总流水");
    private static List< String > ChoushuiLvTableHead = Arrays.asList( "日期",
            "老德州rate","新德州rate","中发白总rate","摩天轮总rate","跑跑堂总rate","跑狗总rate","水果机总rate","斗地主rate",
            "血流rate","血战rate","二麻rate","捕鱼rate","总抽水率");

    private static List< String > zhoubaoTableHead1 = Arrays.asList("日期",
            "周收入","周活跃账号数","周付费账号数","周活跃账号付费率","周活跃老用户(登陆2次或更多)","老用户当周付费数","老用户当周付费率","老用户历史付费账号数",
            "老用户历史付费率","ARPU","ARPPU","周新增","日均总活跃账号","日均持续活跃");
    private static List< String > zhoubaoTableHead2 = Arrays.asList("日期",
            "IOS收入","IOS收入占比","安卓收入","安卓收入占比","联运收入","联运收入占比","官网收入","官网收入占比");
    private static List< String > zhoubaoTableHead3 = Arrays.asList("日期",
            "总收入","IOS新增","IOS收入","安卓新增","安卓收入","联运新增","联运收入","官网新增","官网收入");

    private static List< String > PeopleTableHead = Arrays.asList( "日期",
            "老德州人数","新德州人数","中发白总人数","摩天轮总人数","跑跑堂总人数","跑狗总人数","水果机总人数","斗地主人数",
            "血流人数","血战人数","二麻人数","捕鱼人数","总人数");

    //    private static List< String > installPayTableHead = Arrays.asList( "时间",
//            "渠道", "游戏", "新进", "old付费人数", "old付费次数", "old付费额","new付费人数", "new付费次数", "new付费额", "DAU", "去新增DAU" );
    private static List< String > installPayTableHead = Arrays.asList( "时间",
            "渠道号","渠道名","渠道类型", "游戏", "新进","新增设备", "DAU", "去新增DAU" , "付费人数", "付费次数", "付费率","ARPU", "ARPPU","去新增付费","新增付费人数","新增付费","新增付费率", "总收入");

    private static List< String > installTableHead = Arrays.asList( "时间","安卓进量","ios进量(总进量)","新增设备数量","新增ip", "有效注册用户");
    private static List< String > installTableHead2 = Arrays.asList( "时间","宇洋AND","宇洋IOS","小麦AND","小麦IOS","花妖AND","花妖IOS","快乐AND","快乐IOS","白小黑AND","白小黑IOS",
            "静静AND","静静IOS","闹闹AND","闹闹IOS","毒药AND","毒药IOS","君君AND","君君IOS","葫芦AND","葫芦IOS");

    private static List< String > DanTouHuiZongTableHead = Arrays.asList( "时间","渔场掉落","活动产出","大师宝箱","总使用量", "差值", "线上充值","交易量");

    private static List< String > installPayHuizongTableHead = Arrays.asList( "时间",
            "新进", "DAU", "去新增DAU" , "付费人数", "付费次数", "付费率","ARPU", "ARPPU","去新增付费","新增付费", "总收入");


    private static List< String > baotuHead = Arrays.asList( "时间",
            "登录用户总量", "出售数量", "任务产出", "总产出", "一元夺宝回收", "兑换功能回收" );

    private static List< String > ZiChongTableHead = Arrays.asList( "时间",
            "渠道号", "充值", "捕鱼", "中发白", "摩天轮", "跑跑堂", "赠送道具" );
    private static List< String > ZiChongXiangXiTableHead = Arrays.asList( "时间",
            "充值信息");

    private static List< String > huafeiHead = Arrays.asList( "时间",
            "1元产出", "1元使用", "1元出售", "1元赠送","2元产出", "2元使用", "2元出售", "2元赠送",
            "5元产出", "5元使用", "5元出售", "5元赠送","10元产出", "10元使用", "10元出售", "10元赠送",
            "20元产出", "20元使用", "20元出售", "20元赠送","50元产出", "50元使用", "50元出售", "50元赠送");

    // 返回 汇总项>收入汇总 初始页面
    public void getInstallPayPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String team = req.getParameter( "team" );
        req.setAttribute( "teamGroup", team );
        req.setAttribute( "games", GameCollection.getGames( team ) );
        req.getRequestDispatcher( Constants.URL_SUMMARY_INSTALL_PAY ).forward( req, resp );
    }

    public void getInstallPayHuizongPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_INSTALL_PAY_HUIZONG ).forward( req, resp );
    }
    public void getFinancePage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_FINANCE ).forward( req, resp );
    }
    public void getFishFinancePage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_FISH_FINANCE ).forward( req, resp );
    }
    public void getZiChongPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_ZICHONG ).forward( req, resp );
    }
    public void getZiChongXiangXiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_ZICHONG_XIANGXI ).forward( req, resp );
    }
    public void getHuizongPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_HUIZONG ).forward( req, resp );
    }
    public void getFishHuizongPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_FISH_HUIZONG ).forward( req, resp );
    }
    public void getLiushuiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_LIUSHUI ).forward( req, resp );
    }
    public void getPeoplePage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_PEOPLE ).forward( req, resp );
    }
    public void getZhouBaoPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_ZHOUBAO ).forward( req, resp );
    }
    public void getChoushuiLvPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_CHOUSHUILV ).forward( req, resp );
    }
    public void getziyuanPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_ZIYUAN ).forward( req, resp );
    }
    public void gethuafeiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_HUAFEI ).forward( req, resp );
    }
    public void getDanTouHuiZongPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_DANTOU ).forward( req, resp );
    }
    public void getbaotuPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_SUMMARY_BAOTU ).forward( req, resp );
    }

    private List< SummaryView.InstallPay > getInstallPayHuizongData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.InstallPay val = new SummaryView.InstallPay();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.install = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_install  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee';", c );
            val.oldDau = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_dau  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee';", c );
            val.dau = val.oldDau - val.install;

            val.oldPayPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_pay  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee';", c );
            val.oldPayCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_pay  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee';", c );
            val.oldPayMoney = ServiceManager.getSqlService().queryLong( "select sum(money2 + money3) " +
                    " from t_pay  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee';", c );
            val.newPayMoney = ServiceManager.getCommonService().getPayMoney(beg,end,false,false,c);

            return val;
        } );
    }

    public void queryInstallPayHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_SUMMARY_INSTALL_PAY_HUIZONG, getInstallPayHuizongData( req, resp ),
                installPayHuizongTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + ( info.install ) + "</td>" );
                    pw.write( "<td>" + ( info.oldDau ) + "</td>" );
                    pw.write( "<td>" + ( info.dau      ) + "</td>" );
                    pw.write( "<td>" + ( info.oldPayPeople     ) + "</td>" );
                    pw.write( "<td>" + ( info.oldPayCount      ) + "</td>" );
                    pw.write( "<td>" + formatRatio( safeDiv( info.oldPayPeople , info.oldDau ) )  + "</td>" );
                    pw.write( "<td>" + Currency.format( (long)safeDiv( info.oldPayMoney, info.oldDau ) ) + "</td>" );
                    pw.write( "<td>" + Currency.format( (long)safeDiv( info.oldPayMoney, info.oldPayPeople ) ) + "</td>" );
                    pw.write( "<td>" + Currency.format( info.oldPayMoney - info.newPayMoney) + "</td>" );
                    pw.write( "<td>" + Currency.format( info.newPayMoney) + "</td>" );
                    pw.write( "<td>" + Currency.format( info.oldPayMoney ) + "</td>" );
                } );
    }

    public void downloadInstallPayHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_SUMMARY_INSTALL_PAY_HUIZONG, getInstallPayHuizongData( req, resp ),
                installPayHuizongTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install + "," );
                    pw.write( info.oldDau + "," );
                    pw.write( info.dau + "," );
                    pw.write( info.oldPayPeople + "," );
                    pw.write( info.oldPayCount + "," );
                    pw.write( formatRatio( safeDiv( info.oldPayPeople , info.oldDau ) )  + "," );
                    pw.write( Currency.format( (long)safeDiv( info.oldPayMoney, info.oldDau ) ) + "," );
                    pw.write( Currency.format( (long)safeDiv( info.oldPayMoney, info.oldPayPeople ) ) + "," );
                    pw.write( Currency.format( info.oldPayMoney - info.newPayMoney) + "," );
                    pw.write( Currency.format( info.newPayMoney) + "," );
                    pw.write( Currency.format( info.oldPayMoney ) + "," );
                } );
    }

    private List< SummaryView.Finance > getPeopleData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.Finance val = new SummaryView.Finance();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.oldPokerRake = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_gdesk_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and appid in ( 100632434 , 1104737759 ,1104830871 );", c );
            val.newPokerRake = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_gdesk_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0 and appid not in ( 100632434 , 1104737759 ,1104830871 );", c );
            val.zfbRake = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_zfb_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N';", c );
            val.mtl2Rake = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mtl2_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' ;", c );
            val.pptRake = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_ppt_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N';", c )
                    +
                    ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                            " from t_pptssc_bet  " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            " and is_robot ='N';", c ) ;
            val.dogRake = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_dogsport_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' ;", c );
            val.fruitRake = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_fruit_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' ;", c );
            val.ddzRake = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_ddz_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' ;", c );
            val.xueliuRake = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mjdr_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and type in ( 2,4 );", c );
            val.xuezhanRake = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mjdr_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and type in ( 1,3 );", c );
            val.twoMaRake = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_mjdr_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and type = 5 ;", c );
            val.fishRake = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_fish_paiment  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N';", c );
            val.allRake  = val.oldPokerRake + val.zfbRake + val.mtl2Rake + val.dogRake + val.pptRake + val.ddzRake + val.twoMaRake
                    + val.xuezhanRake + val.xueliuRake + val.fruitRake + val.fishRake;
            return val;
        } );
    }

    public void queryPeople( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PEOPLE, getPeopleData( req, resp ),
                PeopleTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + ( info.oldPokerRake ) + "</td>" );
                    pw.write( "<td>" + ( info.newPokerRake ) + "</td>" );
                    pw.write( "<td>" + ( info.zfbRake      ) + "</td>" );
                    pw.write( "<td>" + ( info.mtl2Rake     ) + "</td>" );
                    pw.write( "<td>" + ( info.pptRake      ) + "</td>" );
                    pw.write( "<td>" + ( info.dogRake      ) + "</td>" );
                    pw.write( "<td>" + ( info.fruitRake    ) + "</td>" );
                    pw.write( "<td>" + ( info.ddzRake      ) + "</td>" );
                    pw.write( "<td>" + ( info.xueliuRake   ) + "</td>" );
                    pw.write( "<td>" + ( info.xuezhanRake  ) + "</td>" );
                    pw.write( "<td>" + ( info.twoMaRake    ) + "</td>" );
                    pw.write( "<td>" + ( info.fishRake     ) + "</td>" );
                    pw.write( "<td>" + ( info.allRake      ) + "</td>" );
                } );
    }

    public void downloadPeople( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PEOPLE, getPeopleData( req, resp ),
                PeopleTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.oldPokerRake + "," );
                    pw.write( info.newPokerRake + "," );
                    pw.write( info.zfbRake + "," );
                    pw.write( info.mtl2Rake + "," );
                    pw.write( info.pptRake + "," );
                    pw.write( info.dogRake + "," );
                    pw.write( info.fruitRake + "," );
                    pw.write( info.ddzRake + "," );
                    pw.write( info.xueliuRake + "," );
                    pw.write( info.xuezhanRake + "," );
                    pw.write( info.twoMaRake + "," );
                    pw.write( info.fishRake + "," );
                    pw.write( info.allRake + "," );
                } );
    }

    private List< SummaryView.Finance > getChoushuiLvData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.Finance val = new SummaryView.Finance();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.oldPokerRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0 and appid in ( 100632434 , 1104737759 ,1104830871 );", c );
            val.newPokerRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0 and appid not in ( 100632434 , 1104737759 ,1104830871 );", c );
            val.zfbRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_zfb_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0;", c );
            val.mtl2Rake = ServiceManager.getSqlService().queryLong( "select sum( bet_gold + bet_nb_gold + bet_xnb_gold ) " +
                    " from t_mtl2_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and bet_gold > 0;", c );
            val.pptRake =   ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_ppt_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0;", c )
                    +
                    ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                            " from t_pptssc_bet  " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            " and is_robot ='N' and chips > 0;", c ) ;
            val.dogRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_dogsport_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0;", c );
            val.fruitRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_fruit_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0;", c );
            val.ddzRake = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_ddz_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and gold > 0;", c );
            val.xueliuRake = ServiceManager.getSqlService().queryLong( "select sum( abs(gold) ) " +
                    " from t_mjdr_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and type in ( 2,4 );", c );
            val.xuezhanRake = ServiceManager.getSqlService().queryLong( "select sum( abs(gold) ) " +
                    " from t_mjdr_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and type in ( 1,3 );", c );
            val.twoMaRake = ServiceManager.getSqlService().queryLong( "select sum( abs(gold) ) " +
                    " from t_mjdr_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and type = 5 ;", c );
            val.fishRake = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_fish_paiment  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' ;", c );
            val.fishRake1 = val.fishRake - ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_fish_get  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' ;", c );

            //中发白收入
            {
                long zfbtotalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' ;", c );
                long zfbbetPumpSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips * ratio ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and banker_id != 0 and is_robot = 'N';", c );
                long zfbchipZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                        " from t_zfb_shangzhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_use_card = 'N';", c );
                long zfbjoinZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                        " from t_zfb_shangzhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_join = 'Y';", c );
                long zfbcardZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                        " from t_zfb_shangzhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_use_card = 'Y';", c );
                long zfbescapePump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( escape_pump ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
                long zfbxiazhuangPump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips * ratio ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_escape = 'N';", c );
                long zfbcardPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( use_card_change ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long zfbcardPayPumpSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( use_card_change * ratio ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and chips > 0;", c );
                long zfbjackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N';", c );
                long zfbsendFlowerSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_flowers " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long zfbsystemRedSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_free_reward " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_free = 'Y';", c );
                long zfbexprIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_send_expression " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long zfbtotalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' ;", c );
                long zfbAIzhuangWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' and banker_id = 0;", c );
                long zfbAIzhuangBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' and banker_id = 0;", c );

                long zfbrewardIncome = zfbsendFlowerSum - zfbsystemRedSum;
                val.zfbRake1 = zfbcardPayPumpSum + zfbbetPumpSum
                        - zfbjackpotPay + zfbescapePump + zfbrewardIncome + zfbexprIncome
                        + zfbAIzhuangBet - zfbAIzhuangWin - zfbcardPay;
            }
            //摩天轮收入

            val.mtl2Rake1 = Mtl2Action.getTotalRake( beg, end, c );
            //血流收入
            {
                long xueliurake = ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                        " from t_mjdr_rake " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and type in (2,4)  and is_ccgames = 'Y' and is_robot = 'N';", c);
                long xueliuAI = ServiceManager.getSqlService().queryLong("select coalesce( -sum( gold ), 0 ) " +
                        " from t_mjdr_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and type in (2,4)  and is_ccgames = 'Y' and is_robot = 'N' ;", c);
                val.xueliuRake1 = xueliurake + xueliuAI;
            }
            //血战收入
            {
                long xuezhanrake = ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                        " from t_mjdr_rake " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and type in (1,3)  and is_ccgames = 'Y' and is_robot = 'N';", c);
                long xuezhanAI = ServiceManager.getSqlService().queryLong("select coalesce( -sum( gold ), 0 ) " +
                        " from t_mjdr_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and type in (1,3)  and is_ccgames = 'Y' and is_robot = 'N' ;", c);
                val.xuezhanRake1 = xuezhanAI + xuezhanrake;
            }
            //二麻收入
            long errenrake = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 5  and is_ccgames = 'Y' and is_robot = 'N';", c );
            long errenAI =  ServiceManager.getSqlService().queryLong( "select coalesce( -sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 5  and is_ccgames = 'Y' and is_robot = 'N';", c );

            val.twoMaRake1 = errenAI + errenrake ;
            //老德州收入
            {
                long dezhouRake = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_gdesk_pump " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and desk_type not in ('chuji','zhongji','gaoji','dashi','zhizun');", c );
                long yaojiangjiRake = ServiceManager.getSqlService().queryLong("select sum( chips ) " +
                        " from t_yyl_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_robot ='N';", c) - ServiceManager.getSqlService().queryLong("select sum( chips ) " +
                        " from t_yyl_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_robot ='N';", c);
                long dingshiOut = ServiceManager.getSqlService().queryLong("select sum( chips ) " +
                        " from t_gdesk_expense " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_robot ='N' and kind ='timer';", c);

                long zhuanpanOut = ServiceManager.getSqlService().queryLong("select sum( chips ) " +
                        " from t_gdesk_expense " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_robot ='N' and kind = 'gdeskLuckyWheelBonus';", c);

                long totalPump = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_gdesk_pump " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_robot = 'N' and desk_type = 'vip';", c);
                long playerCount = ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                        " from t_gdesk_play " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_robot = 'N' and desk_type = 'vip';", c);
                long masterPointPay = ServiceManager.getSqlService().queryLong("select coalesce( sum( master_point ), 0 ) " +
                        " from t_mp_get " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_robot = 'N' and source = 0;", c);
                long bonusChips = (playerCount > 0L ? 55000000L : 0L);
                long OldDzjackpotPay = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_gdesk_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_robot = 'N' and desk_type = 'vip';", c);
                long rebatePay = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_gdesk_expense " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_robot = 'N' and desk_type = 'vip';", c);
                long otherPay = OldDzjackpotPay + rebatePay;
                long youlunRake = totalPump - bonusChips - otherPay;

                val.oldPokerRake1 = dezhouRake + yaojiangjiRake + youlunRake - dingshiOut - zhuanpanOut;
            }
            //新德州收入
            val.newPokerRake1 = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_gdesk_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and desk_type in ('chuji','zhongji','gaoji','dashi','zhizun')" +
                    " and is_robot = 'N';", c );
            //斗地主收入
            {
                List<String> DDZgetinfo = ServiceManager.getSqlService().queryListString( "select bonus " +
                        " from t_task_finish " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and type =100 ;", c );

                Map< Long, Long > DDZgetMap = new HashMap<>();
                /**
                 * 道具id -1为元宝
                 *        -2为体力
                 * */
                DDZgetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), DDZgetMap ) );
                long missionPay             = getLong( DDZgetMap, -1 );

                long choujiang = ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
                        " from t_ddz_choujiang " +
                        " where "  + beg + " <= timestamp and timestamp <  " + end + " and item_id = -1;", c );

                long ddzShouru = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                        " from t_ddz_rake  " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   ;", c );

                val.ddzRake1 =ddzShouru  -choujiang -missionPay ;
//                val.ddzRake = ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
//                        " from t_ddz_rake " +
//                        " where " + beg + " <= timestamp and timestamp < " + end + " ;", c);
            }
            //跑跑堂
            {
                long totalBet = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_ppt_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long totalWin = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_ppt_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long pptJackpot = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_ppt_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long sscBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_pptssc_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long sscWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_pptssc_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                val.pptRake1 = totalBet + sscBet - sscWin - totalWin - pptJackpot;
            }
            //水果机收入
            {
                long totalWinFruit = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_fruit_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long totalBetFruit = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_fruit_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long jackpotPayFruit = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_fruit_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long bibeiWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_caibei_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long bibeiBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_caibei_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                val.fruitRake1 = totalBetFruit + bibeiBet - bibeiWin - totalWinFruit - jackpotPayFruit;
            }
            //跑狗收入
            {
                long dogTotalBet = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_dogsport_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long dogWinSum = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_dogsport_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long dogJackpotPay = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_dogsport_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                val.dogRake1 = dogTotalBet - dogWinSum - dogJackpotPay;
            }
            val.allRake  = val.oldPokerRake + val.zfbRake + val.mtl2Rake + val.dogRake + val.pptRake + val.ddzRake + val.twoMaRake
                    + val.xuezhanRake + val.xueliuRake + val.fruitRake ;

            val.allRake1 = val.zfbRake1 + val.mtl2Rake1     + val.xueliuRake1   + val.xuezhanRake1   + val.newPokerRake1
                    + val.oldPokerRake1 + val.ddzRake1      + val.twoMaRake1     + val.pptRake1       + val.pptRake1
                    + val.dogRake1      + val.fruitRake1;
            return val;
        } );
    }

    public void queryChoushuiLv( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_LIUSHUI, getChoushuiLvData( req, resp ),
                ChoushuiLvTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + formatRatio((double) info.oldPokerRake1 * 1.0 / (double) info.oldPokerRake) + "</td>" );
                    pw.write( "<td>" + formatRatio((double) info.newPokerRake1 * 1.0 / (double)info.newPokerRake) + "</td>" );
                    pw.write( "<td>" + formatRatio((double) info.zfbRake1    * 1.0   / (double)info.zfbRake) + "</td>" );
                    pw.write( "<td>" + formatRatio((double) info.mtl2Rake1   * 1.0   / (double)info.mtl2Rake) + "</td>" );
                    pw.write( "<td>" + formatRatio((double) info.pptRake1   * 1.0    / (double)info.pptRake) + "</td>" );
                    pw.write( "<td>" + formatRatio((double) info.dogRake1    * 1.0   / (double)info.dogRake) + "</td>" );
                    pw.write( "<td>" + formatRatio((double) info.fruitRake1  * 1.0   / (double)info.fruitRake) + "</td>" );
                    pw.write( "<td>" + formatRatio((double) info.ddzRake1   * 1.0    / (double)info.ddzRake) + "</td>" );
                    pw.write( "<td>" + formatRatio((double) info.xueliuRake1  * 1.0  / (double)info.xueliuRake) + "</td>" );
                    pw.write( "<td>" + formatRatio((double) info.xuezhanRake1 * 1.0  / (double)info.xuezhanRake) + "</td>" );
                    pw.write( "<td>" + formatRatio((double) info.twoMaRake1  * 1.0   / (double)info.twoMaRake) + "</td>" );
                    pw.write( "<td>" + formatRatio((double) info.fishRake1   * 1.0   / (double)info.fishRake) + "</td>" );
                    pw.write( "<td>" +formatRatio( info.allRake1    * 1.0   / (double)info.allRake) + "</td>" );
                } );
    }

    public void downloadChoushuiLv( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp ),
                ChoushuiLvTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( formatRatio((double)info.oldPokerRake1 / (double)info.oldPokerRake)+ "," );
                    pw.write( formatRatio((double)info.newPokerRake1 / (double)info.newPokerRake)+ "," );
                    pw.write( formatRatio((double)info.zfbRake1 / (double)info.zfbRake)+ "," );
                    pw.write( formatRatio((double)info.mtl2Rake1 / (double)info.mtl2Rake)+ "," );
                    pw.write( formatRatio((double)info.pptRake1 / (double)info.pptRake)+ "," );
                    pw.write( formatRatio((double)info.dogRake1 / (double)info.dogRake)+ "," );
                    pw.write( formatRatio((double)info.fruitRake1 / (double)info.fruitRake)+ "," );
                    pw.write( formatRatio((double)info.ddzRake1 / (double)info.ddzRake)+ "," );
                    pw.write( formatRatio((double)info.xueliuRake1 / (double)info.xueliuRake)+ "," );
                    pw.write( formatRatio((double)info.xuezhanRake1/ (double)info.xuezhanRake )+ "," );
                    pw.write( formatRatio((double)info.twoMaRake1 / (double)info.twoMaRake)+ "," );
                    pw.write( formatRatio((double)info.fishRake1 / (double)info.fishRake)+ "," );
                    pw.write( formatRatio((double)info.allRake1 / (double)info.allRake)+ "," );
                } );
    }

    private List< SummaryView.Finance > getLiushuiData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.Finance val = new SummaryView.Finance();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.oldPokerRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0 and appid in ( 100632434 , 1104737759 ,1104830871 );", c );
            val.newPokerRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0 and appid not in ( 100632434 , 1104737759 ,1104830871 );", c );
            val.zfbRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_zfb_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0;", c );
            val.mtl2Rake = ServiceManager.getSqlService().queryLong( "select sum( bet_gold + bet_nb_gold + bet_xnb_gold ) " +
                    " from t_mtl2_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and bet_gold > 0;", c );
            val.pptRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_ppt_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0;", c )
                    +
                    ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                            " from t_pptssc_bet  " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            " and is_robot ='N' and chips > 0;", c ) ;
            val.dogRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_dogsport_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0;", c );
            val.fruitRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_fruit_bet  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and chips > 0;", c );
            val.ddzRake = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_ddz_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and gold > 0;", c );
            val.xueliuRake = ServiceManager.getSqlService().queryLong( "select sum( abs(gold) ) " +
                    " from t_mjdr_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and gold > 0 and type in ( 2,4 );", c );
            val.xuezhanRake = ServiceManager.getSqlService().queryLong( "select sum( abs(gold) ) " +
                    " from t_mjdr_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and gold > 0 and type in ( 1,3 );", c );
            val.twoMaRake = ServiceManager.getSqlService().queryLong( "select sum( abs(gold) ) " +
                    " from t_mjdr_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and gold > 0 and type = 5 ;", c );
            val.fishRake = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_fish_paiment  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N';", c );
            val.allRake  = val.oldPokerRake + val.zfbRake + val.mtl2Rake + val.dogRake + val.pptRake + val.ddzRake + val.twoMaRake
                    + val.xuezhanRake + val.xueliuRake + val.fruitRake + val.fishRake;
            return val;
        } );
    }

    public void queryLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp ),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + ( info.oldPokerRake  / 10000) + "万</td>" );
                    pw.write( "<td>" + ( info.newPokerRake  / 10000) + "万</td>" );
                    pw.write( "<td>" + ( info.zfbRake       / 10000) + "万</td>" );
                    pw.write( "<td>" + ( info.mtl2Rake      / 10000) + "万</td>" );
                    pw.write( "<td>" + ( info.pptRake       / 10000) + "万</td>" );
                    pw.write( "<td>" + ( info.dogRake       / 10000) + "万</td>" );
                    pw.write( "<td>" + ( info.fruitRake     / 10000) + "万</td>" );
                    pw.write( "<td>" + ( info.ddzRake       / 10000) + "万</td>" );
                    pw.write( "<td>" + ( info.xueliuRake    / 10000) + "万</td>" );
                    pw.write( "<td>" + ( info.xuezhanRake   / 10000) + "万</td>" );
                    pw.write( "<td>" + ( info.twoMaRake     / 10000) + "万</td>" );
                    pw.write( "<td>" + ( info.fishRake      / 10000) + "万</td>" );
                    pw.write( "<td>" + ( info.allRake       / 10000) + "万</td>" );
                } );
    }

    public void downloadLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp ),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.oldPokerRake + "," );
                    pw.write( info.newPokerRake + "," );
                    pw.write( info.zfbRake + "," );
                    pw.write( info.mtl2Rake + "," );
                    pw.write( info.pptRake + "," );
                    pw.write( info.dogRake + "," );
                    pw.write( info.fruitRake + "," );
                    pw.write( info.ddzRake + "," );
                    pw.write( info.xueliuRake + "," );
                    pw.write( info.xuezhanRake + "," );
                    pw.write( info.twoMaRake + "," );
                    pw.write( info.fishRake + "," );
                    pw.write( info.allRake + "," );
                } );
    }

    private List< SummaryView.huafei > getHuafeiData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.huafei val = new SummaryView.huafei();
            val.begin = TimeUtil.ymdFormat().format( beg );

            val.out_1   = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10100 ,c );
            val.out_2   = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10101 ,c );
            val.out_5   = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10102 ,c );
            val.out_10  = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10103 ,c );
            val.out_20  = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10104 ,c );
            val.out_50  = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10105 ,c );

            Map<Long, Long> val2count = ServiceManager.getSqlService().queryMapLongLong( "select card_val, count(id) " +
                    " from t_use_huafei " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_info != 'message'" +
                    " group by card_val;", c );

            val.use_1   = getLong( val2count, 1 );
            val.use_2   = getLong( val2count, 2 );
            val.use_5   = getLong( val2count, 5 );
            val.use_10  = getLong( val2count, 10 );
            val.use_20  = getLong( val2count, 20 );
            val.use_50  = getLong( val2count, 50 );

            val.sell_1   = ServiceManager.getCommonService().getHuaFeiSellCount( beg , end , 10100 ,c );
            val.sell_2   = ServiceManager.getCommonService().getHuaFeiSellCount( beg , end , 10101 ,c );
            val.sell_5   = ServiceManager.getCommonService().getHuaFeiSellCount( beg , end , 10102 ,c );
            val.sell_10  = ServiceManager.getCommonService().getHuaFeiSellCount( beg , end , 10103 ,c );
            val.sell_20  = ServiceManager.getCommonService().getHuaFeiSellCount( beg , end , 10104 ,c );
            val.sell_50  = ServiceManager.getCommonService().getHuaFeiSellCount( beg , end , 10105 ,c );

            val.sent_1   = ServiceManager.getCommonService().getHuaFeiSentCount( beg , end , 10100 ,c );
            val.sent_2   = ServiceManager.getCommonService().getHuaFeiSentCount( beg , end , 10101 ,c );
            val.sent_5   = ServiceManager.getCommonService().getHuaFeiSentCount( beg , end , 10102 ,c );
            val.sent_10  = ServiceManager.getCommonService().getHuaFeiSentCount( beg , end , 10103 ,c );
            val.sent_20  = ServiceManager.getCommonService().getHuaFeiSentCount( beg , end , 10104 ,c );
            val.sent_50  = ServiceManager.getCommonService().getHuaFeiSentCount( beg , end , 10105 ,c );

            return val;
        } );
    }

    public void queryHuafei( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_SUMMARY_HUAFEI, getHuafeiData( req, resp ),
                huafeiHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.out_1 + "</td>" );
                    pw.write( "<td>" + info.use_1 + "</td>" );
                    pw.write( "<td>" + info.sell_1 + "</td>" );
                    pw.write( "<td>" + info.sent_1 + "</td>" );
                    pw.write( "<td>" + info.out_2 + "</td>" );
                    pw.write( "<td>" + info.use_2 + "</td>" );
                    pw.write( "<td>" + info.sell_2 + "</td>" );
                    pw.write( "<td>" + info.sent_2 + "</td>" );
                    pw.write( "<td>" + info.out_5 + "</td>" );
                    pw.write( "<td>" + info.use_5 + "</td>" );
                    pw.write( "<td>" + info.sell_5 + "</td>" );
                    pw.write( "<td>" + info.sent_5 + "</td>" );
                    pw.write( "<td>" + info.out_10 + "</td>" );
                    pw.write( "<td>" + info.use_10 + "</td>" );
                    pw.write( "<td>" + info.sell_10 + "</td>" );
                    pw.write( "<td>" + info.sent_10 + "</td>" );
                    pw.write( "<td>" + info.out_20 + "</td>" );
                    pw.write( "<td>" + info.use_20 + "</td>" );
                    pw.write( "<td>" + info.sell_20 + "</td>" );
                    pw.write( "<td>" + info.sent_20 + "</td>" );
                    pw.write( "<td>" + info.out_50 + "</td>" );
                    pw.write( "<td>" + info.use_50 + "</td>" );
                    pw.write( "<td>" + info.sell_50 + "</td>" );
                    pw.write( "<td>" + info.sent_50 + "</td>" );
                } );
    }

    public void downloadHuafei( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_SUMMARY_HUAFEI, getHuafeiData( req, resp ),
                huafeiHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.out_1 + "," );
                    pw.write( info.use_1 + "," );
                    pw.write( info.sell_1 + "," );
                    pw.write( info.sent_1 + "," );
                    pw.write( info.out_2 + "," );
                    pw.write( info.use_2 + "," );
                    pw.write( info.sell_2 + "," );
                    pw.write( info.sent_2 + "," );
                    pw.write( info.out_5 + "," );
                    pw.write( info.use_5 + "," );
                    pw.write( info.sell_5 + "," );
                    pw.write( info.sent_5 + "," );
                    pw.write( info.out_10 + "," );
                    pw.write( info.use_10 + "," );
                    pw.write( info.sell_10 + "," );
                    pw.write( info.sent_10 + "," );
                    pw.write( info.out_20 + "," );
                    pw.write( info.use_20 + "," );
                    pw.write( info.sell_20 + "," );
                    pw.write( info.sent_20 + "," );
                    pw.write( info.out_50 + "," );
                    pw.write( info.use_50 + "," );
                    pw.write( info.sell_50 + "," );
                    pw.write( info.sent_50 + "," );
                } );
    }
    private List< SummaryView.baotu > getBaotuData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.baotu val = new SummaryView.baotu();
            val.begin = TimeUtil.ymdFormat().format( beg );

            List<String> a = ServiceManager.getSqlService().queryListString( "select items " +
                    " from t_possession " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );

            Map< Long, Long > getMap = new HashMap<>();
            a.forEach( info -> mergeMap( splitMap( info, ";", "," ), getMap ) );
            val.userHave             = getLong( getMap, 10301);

            val.sellCount = ServiceManager.getSqlService().queryLong("select count( id ) " +
                    " from t_use_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and item_id=10301 and use_info = 2;", c);
            List<String> getinfo = ServiceManager.getSqlService().queryListString( "select bonus " +
                    " from t_task_finish " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            Map< Long, Long > getMap1 = new HashMap<>();
            /**
             * 道具id -1为元宝
             *
             * */
            getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
            long a1              = getLong( getMap1, 10301 );
            val.taskOut = ServiceManager.getSqlService().queryLong("select sum(item_count) " +
                    " from t_mission_finish2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and item_id=10301 ;", c) + a1;

            val.allOut = ServiceManager.getSqlService().queryLong("select sum(item_count) " +
                    " from t_use_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and item_id=10301 and use_info=6 and item_source != 61;", c);

            val.duobaoUse =  ServiceManager.getSqlService().queryLong("select sum(cost_count) " +
                    " from t_duobao_buy " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and item_id=10301 ;", c);
            val.duihuan  = ServiceManager.getSqlService().queryLong("select sum(cost_count) " +
                    " from t_exchange " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " ;", c);
            return val;
        } );
    }

    public void queryBaotu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_SUMMARY_BAOTU, getBaotuData( req, resp ),
                baotuHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userHave + "</td>" );
                    pw.write( "<td>" + info.sellCount + "</td>" );
                    pw.write( "<td>" + info.taskOut + "</td>" );
                    pw.write( "<td>" + info.allOut + "</td>" );
                    pw.write( "<td>" + info.duobaoUse + "</td>" );
                    pw.write( "<td>" + info.duihuan + "</td>" );
                } );
    }



    public void downloadBaotu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_SUMMARY_BAOTU, getBaotuData( req, resp ),
                baotuHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.userHave + "," );
                    pw.write( info.sellCount + "," );
                    pw.write( info.taskOut + "," );
                    pw.write( info.allOut + "," );
                    pw.write( info.duobaoUse + "," );
                    pw.write( info.duihuan + "," );
                } );
    }



    private List< SummaryView.InstallPay > getInstallPayData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        TimeRange tr = TimeRange.parseFrom( req );
        String team = req.getParameter( "team" );
        String game = req.getParameter( "game" );
        String qudao = req.getParameter( "qudao" );
        String qudaohao = req.getParameter( "qudaohao" );
        String user_name =  (String) req.getSession().getAttribute( "userName" );
        logger.debug( "========== {} {} {} {}", team, game, tr.begin, tr.end );
        List< SummaryView.InstallPay > rv = new LinkedList();

        if( CommonUtil.isNoneEmpty( tr, team, game ) ) {
            List< String > games = new ArrayList<>();
            if( "ALL".equals( game ) ) {
                GameCollection.getAll().forEach( gg -> {
                    if( gg.id.equals( team ) ) games.addAll( gg.games.keySet() );
                    if (user_name.equals("w") || user_name.equals("admin") || user_name.equals("qiecong") || user_name.equals("l") || user_name.equals("yangzheng") )
                        games.add("TPG-TENCENT-b477860b9724470cba748d4bae67beee");
                } );
            }
            else {
                games.add( game );
            }
            SummaryView.InstallPay sum = new SummaryView.InstallPay();
            sum.begin = TimeUtil.ymdhmFormat().format( tr.begin );
            sum.end = TimeUtil.ymdhmFormat().format( tr.end );
            sum.team = "所有项目";
            sum.game = "所有游戏";
            sum.qudao = "所有渠道";
            rv.add( sum );

            games.forEach( g -> {
                String[] x = g.split( "-" );  // team-platform-appid
                List< String > PERM_LIST = Arrays.asList( "264" );
//                if( req.getSession().getAttribute( "userName" ).equals("shichangbu" ) && !PERM_LIST.contains( x[2] ) ) return;
//                if(x[2].equals("1251807724") ) return;
                try {
                    rv.addAll( getDataList2( req, resp, ( beg, end, c ) -> {
                        List<SummaryView.InstallPay> dataList = new ArrayList<>();

                        Map<String, Long> installMap = ServiceManager.getCommonService().getInstallMap(x[2], beg, end, c);
                        Map<String, Long> oldPayPeopleMap = ServiceManager.getCommonService().getPayPeopleMap(x[2], beg, end, true, c);
                        Map<String, Long> newPayPeopleMap = ServiceManager.getCommonService().getPayPeopleMap(x[2], beg, end, false, c);
                        Map<String, Long> oldPayCountMap = ServiceManager.getCommonService().getPayCountMap(x[2], beg, end, true, c);
                        Map<String, Long> newPayCountMap = ServiceManager.getCommonService().getPayCountMap(x[2], beg, end, false, c);
                        Map<String, Long> oldPayMoneyMap = ServiceManager.getCommonService().getPayMoneyMap(x[2], beg, end, true, c);
                        Map<String, Long> newPayMoneyMap = ServiceManager.getCommonService().getPayMoneyMap(x[2], beg, end, false, c);
                        Map<String, Long> oldDauMap = ServiceManager.getCommonService().getDauMap(x[2], beg, end, true, c);
                        Map<String, Long> DauMap = ServiceManager.getCommonService().getDauMap(x[2], beg, end, c);
                        Map<String, Long> YouxiaoMap = ServiceManager.getCommonService().getYouXiaoInstallMap(x[2], beg, end, c);

                        Set<String> allQuDao = new HashSet<>();
                        allQuDao.addAll(installMap.keySet());
                        allQuDao.addAll(oldPayPeopleMap.keySet());
                        allQuDao.addAll(newPayPeopleMap.keySet());
                        allQuDao.addAll(oldPayCountMap.keySet());
                        allQuDao.addAll(newPayCountMap.keySet());
                        allQuDao.addAll(oldPayMoneyMap.keySet());
                        allQuDao.addAll(newPayMoneyMap.keySet());
                        allQuDao.addAll(oldDauMap.keySet());
                        allQuDao.addAll(DauMap.keySet());
                        allQuDao.addAll(YouxiaoMap.keySet());

                        if (!allQuDao.isEmpty())
                        {
                            if (!qudao.equals("ALL")) {
                                if (qudao.equals("LIANYUN")) {
                                    allQuDao.clear();
                                    lianyun.forEach(a -> {
                                        allQuDao.add(a);
                                    });
                                }
                                if (qudao.equals("IOS")) {
                                    allQuDao.clear();
                                    IOS.forEach(a -> {
                                        allQuDao.add(a);
                                    });
                                }
                                if (qudao.equals("CESHI")) {
                                    allQuDao.clear();
                                    CESHI.forEach(a -> {
                                        allQuDao.add(a);
                                    });
                                }
                                if (qudao.equals("ZHUBO")) {
                                    allQuDao.clear();
                                    qudaoInstall.forEach(a -> {
                                        allQuDao.add(a);
                                    });
                                }
                                if (qudao.equals("YIYEHEZUO")) {
                                    allQuDao.clear();
                                    YIYEHEZUO.forEach(a -> {
                                        allQuDao.add(a);
                                    });
                                }
                                if (qudao.equals("ZIMAILIANG")) {
                                    YIYEHEZUO.forEach(a -> allQuDao.remove(a));
                                    IOS.forEach(a -> allQuDao.remove(a) );
                                    lianyun.forEach(a -> allQuDao.remove(a));
                                    CESHI.forEach(a -> allQuDao.remove(a));
                                    qudaoInstall .forEach(a -> allQuDao.remove(a));
                                }
                            }
                        }

                        if ( !qudaohao.equals("") )
                        {
                            allQuDao.clear();
                            allQuDao.add(qudaohao);
                        }
                        if (user_name.equals("zbdz"))
                        {
                            allQuDao.clear();
                            qudaoInstall.forEach((v) -> allQuDao.add( v ) );
                        }
                        if (user_name.equals("mengxiao"))
                        {
                            allQuDao.clear();
                            mengxiao.forEach((v) -> allQuDao.add( v ) );
                        }
                        allQuDao.remove("3311");
                        allQuDao.remove("b477860b9724470cba748d4bae67beee");
                        if (user_name.equals("yangzheng"))
                        {
                            allQuDao.clear();
                            allQuDao.add("b477860b9724470cba748d4bae67beee");
                        }
                        if (user_name.equals("l") || user_name.equals("w")  || user_name.equals("zhangliguang")  || user_name.equals("qiecong")  || user_name.equals("admin") )
                        {
                            allQuDao.add("3311");
                            allQuDao.add("b477860b9724470cba748d4bae67beee");
                        }

                        allQuDao.forEach( quDao -> {
                            SummaryView.InstallPay all = new SummaryView.InstallPay();
                            all.begin = TimeUtil.ymdFormat().format( beg );
                            all.end = TimeUtil.ymdFormat().format( end );
                            all.qudao = quDao;
                            if (all.qudao.equals("b477860b9724470cba748d4bae67beee"))
                            {all.qudao = "火柴";}
                            all.game = GameCollection.getName( g );
                            all.install = getLong( installMap, quDao );
                            all.oldPayPeople = getLong( oldPayPeopleMap, quDao );
                            all.newPayPeople = getLong( newPayPeopleMap, quDao );
                            all.oldPayCount = getLong( oldPayCountMap, quDao );
                            all.newPayCount = getLong( newPayCountMap, quDao );
                            all.oldPayMoney = getLong( oldPayMoneyMap, quDao );
                            all.newPayMoney = getLong( newPayMoneyMap, quDao );
                            all.oldDau = getLong( oldDauMap, quDao );
                            all.dau = getLong( DauMap, quDao );
                            all.youxiao = getLong( YouxiaoMap, quDao );

                            if (lianyun.contains(quDao))
                            {all.qudaoleixing = "联运";}
                            if (YIYEHEZUO.contains(quDao))
                            {all.qudaoleixing = "异业合作";}
                            if (IOS.contains(quDao))
                            {all.qudaoleixing = "IOS";}
                            if (CESHI.contains(quDao))
                            {all.qudaoleixing = "测试渠道";}
                            if (qudaoInstall.contains(quDao))
                            {all.qudaoleixing = "主播";}
                            if (!lianyun.contains(quDao) && !IOS.contains(quDao) && !YIYEHEZUO.contains(quDao) && !CESHI.contains(quDao) && !qudaoInstall.contains(quDao))
                            {all.qudaoleixing = "买量";}

                            if (all.dau != 0)
                                dataList.add( all );
                        } );
                        return dataList;
                    } ) );
                } catch( Exception e ) {
                    throw new RuntimeException( "getInstallPayData error" );
                }
            } );

            rv.forEach( val -> {
                if (val.qudao.equals("2969")) return;
                if (val.qudao.equals("3311")) return;
                if (val.qudao.equals("b477860b9724470cba748d4bae67beee")) return;
                if (val.qudao.equals("火柴")) return;
                sum.install += val.install;
                sum.oldPayPeople += val.oldPayPeople;
                sum.newPayPeople += val.newPayPeople;
                sum.oldPayCount += val.oldPayCount;
                sum.newPayCount += val.newPayCount;
                sum.newPayMoney += val.newPayMoney;
                sum.oldPayMoney += val.oldPayMoney;
                sum.dau += val.dau;
                sum.youxiao += val.youxiao;
                sum.oldDau += val.oldDau;
                sum.qudao = "ALL";
            } );
        }
        return rv;
    }
    //    private static List< String > installPayTableHead = Arrays.asList( "
// 时间","渠道", "游戏", "新进", "DAU", "去新增DAU" , "付费人数", "付费次数", "付费率","ARPU", "ARPPU","去新增付费","新增付费", "总收入");
    public void queryInstallPay( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
//"渠道", "游戏", "新进", "DAU", "去新增DAU" , "付费人数", "付费次数", "付费率","ARPU", "ARPPU","去新增付费","新增付费人数","新增付费","新增付费率", "总收入");
        queryList( req, resp,
                CommandList.GET_SUMMARY_INSTALL_PAY, getInstallPayData( req, resp ),
                installPayTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.qudao +  "</td>" );
                    pw.write( "<td>" + QuDao.get(info.qudao+"")+ "</td>" );
                    pw.write( "<td>" + info.qudaoleixing +  "</td>" );
                    pw.write( "<td>" + info.game +  "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    pw.write( "<td>" + info.youxiao + "</td>" );
                    pw.write( "<td>" + info.dau + "</td>" );
                    pw.write( "<td>" + info.oldDau + "</td>" );
                    pw.write( "<td>" + (info.oldPayPeople + info.newPayPeople)  + "</td>" );
                    pw.write( "<td>" + (info.oldPayCount + info.newPayCount) + "</td>" );
                    pw.write( "<td>" + formatRatio( safeDiv( info.oldPayPeople + info.newPayPeople, info.dau ) )  + "</td>" );
                    pw.write( "<td>" + Currency.format( (long)safeDiv( info.newPayMoney + info.oldPayMoney, info.dau ) ) + "</td>" );
                    pw.write( "<td>" + Currency.format( (long)safeDiv( info.newPayMoney + info.oldPayMoney, info.oldPayPeople + info.newPayPeople ) ) + "</td>" );
                    pw.write( "<td>" + Currency.format(info.oldPayMoney) + "</td>" );
                    pw.write( "<td>" + info.newPayPeople + "</td>" );
                    pw.write( "<td>" + Currency.format(info.newPayMoney) + "</td>" );
                    pw.write( "<td>" + formatRatio( safeDiv( info.newPayPeople, info.install ) )  + "</td>" );
                    pw.write( "<td>" + Currency.format( info.newPayMoney + info.oldPayMoney ) + "</td>" );
                } );
    }

    public void downloadInstallPay( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_SUMMARY_INSTALL_PAY, getInstallPayData( req, resp ),
                installPayTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.qudao +  "," );
                    pw.write( QuDao.get(info.qudao+"")+ "," );
                    pw.write( info.qudaoleixing +  "," );
                    pw.write( info.game +  "," );
                    pw.write( info.install + "," );
                    pw.write( info.youxiao + "," );
                    pw.write( info.dau + "," );
                    pw.write( info.oldDau + "," );
                    pw.write( (info.oldPayPeople + info.newPayPeople)  + "," );
                    pw.write( (info.oldPayCount + info.newPayCount) + "," );
                    pw.write( formatRatio( safeDiv( info.oldPayPeople + info.newPayPeople, info.dau ) )  + "," );
                    pw.write( Currency.format( (long)safeDiv( info.newPayMoney + info.oldPayMoney, info.dau ) ) + "," );
                    pw.write( Currency.format( (long)safeDiv( info.newPayMoney + info.oldPayMoney, info.oldPayPeople + info.newPayPeople ) ) + "," );
                    pw.write( Currency.format(info.oldPayMoney) + "," );
                    pw.write( info.newPayPeople + "," );
                    pw.write( Currency.format(info.newPayMoney) + "," );
                    pw.write( formatRatio( safeDiv( info.newPayPeople, info.install ) )  + "," );
                    pw.write( Currency.format( info.newPayMoney + info.oldPayMoney ) + "," );
                } );
    }
    /**
     * 老版收入基础
     * */
//    public void queryInstallPay( HttpServletRequest req, HttpServletResponse resp )
//            throws Exception {
//
//        queryList( req, resp,
//                CommandList.GET_SUMMARY_INSTALL_PAY, getInstallPayData( req, resp ),
//                installPayTableHead, ( info, pw ) -> {
//                    pw.write( "<td>" + info.begin + "</td>" );
//                    pw.write( "<td>" + info.qudao +  "</td>" );
//                    pw.write( "<td>" + info.game +  "</td>" );
//                    pw.write( "<td>" + info.install + "</td>" );
//                    pw.write( "<td>" + info.oldPayPeople + "</td>" );
//                    pw.write( "<td>" + info.oldPayCount + "</td>" );
//                    pw.write( "<td>" + Currency.format( info.oldPayMoney ) + "</td>" );
//                    pw.write( "<td>" + info.newPayPeople + "</td>" );
//                    pw.write( "<td>" + info.newPayCount + "</td>" );
//                    pw.write( "<td>" + Currency.format( info.newPayMoney ) + "</td>" );
//                    pw.write( "<td>" + info.dau + "</td>" );
//                    pw.write( "<td>" + info.oldDau+ "</td>" );
//                } );
//    }
//
//    public void downloadInstallPay( HttpServletRequest req, HttpServletResponse resp )
//            throws Exception {
//        downloadList( req, resp,
//                CommandList.GET_SUMMARY_INSTALL_PAY, getInstallPayData( req, resp ),
//                installPayTableHead, ( info, pw ) -> {
//                    pw.write( info.begin + "," );
//                    pw.write( info.qudao + "," );
//                    pw.write( info.game + "," );
//                    pw.write( info.install + "," );
//                    pw.write( info.oldPayPeople + "," );
//                    pw.write( info.oldPayCount + "," );
//                    pw.write( Currency.format( info.oldPayMoney ) + "," );
//                    pw.write( info.newPayPeople + "," );
//                    pw.write( info.newPayCount + "," );
//                    pw.write( Currency.format( info.newPayMoney ) + "," );
//                    pw.write( info.dau + "," );
//                    pw.write( info.oldDau + "," );
//                } );
//    }


    private List< SummaryView.Finance > getFinanceData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.Finance val = new SummaryView.Finance();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.rmb = ServiceManager.getSqlService().queryLong("select coalesce( sum( money2 + money3 ), 0 ) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " ;", c);

            List<String> getinfo = ServiceManager.getSqlService().queryListString( "select bonus " +
                    " from t_task_finish " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            Map< Long, Long > getMap = new HashMap<>();
            /**
             * 道具id -1为元宝
             *
             * */
            getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
            long a              = getLong( getMap, -1 );
            //公共玩法支出
            val.publicOut = ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                    " from t_grants2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_ccgames = 'Y' and is_robot = 'N';", c)
                    + ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                    " from t_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and type = 1 " +
                    " and is_ccgames = 'Y' and is_robot = 'N';", c)
                    +  a  + ServiceManager.getSqlService().queryLong("select sum(gold) " +
                    " from t_mission_finish2 " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " ;", c);
            //中发白收入
            {
                long zfbtotalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' ;", c );
                long zfbbetPumpSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips * ratio ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and banker_id != 0 and is_robot = 'N';", c );
                long zfbchipZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                        " from t_zfb_shangzhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_use_card = 'N';", c );
                long zfbjoinZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                        " from t_zfb_shangzhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_join = 'Y';", c );
                long zfbcardZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                        " from t_zfb_shangzhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_use_card = 'Y';", c );
                long zfbescapePump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( escape_pump ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
                long zfbxiazhuangPump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips * ratio ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_escape = 'N';", c );
                long zfbcardPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( use_card_change ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long zfbcardPayPumpSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( use_card_change * ratio ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and chips > 0;", c );
                long zfbjackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N';", c );
                long zfbsendFlowerSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_flowers " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long zfbsystemRedSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_free_reward " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_free = 'Y';", c );
                long zfbexprIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_send_expression " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long zfbtotalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' ;", c );
                long zfbAIzhuangWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' and banker_id = 0;", c );
                long zfbAIzhuangBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' and banker_id = 0;", c );

                long zfbrewardIncome = zfbsendFlowerSum - zfbsystemRedSum;
                val.zfbRake = zfbcardPayPumpSum + zfbbetPumpSum
                        - zfbjackpotPay + zfbescapePump + zfbrewardIncome + zfbexprIncome
                        + zfbAIzhuangBet - zfbAIzhuangWin - zfbcardPay;
            }
            //摩天轮收入

            val.mtl2Rake = Mtl2Action.getTotalRake( beg, end, c );
            //血流收入
            {
                long xueliurake = ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                        " from t_mjdr_rake " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and type = 4  and is_ccgames = 'Y' ;", c);
                long xueliuAI = ServiceManager.getSqlService().queryLong("select coalesce( -sum( gold ), 0 ) " +
                        " from t_mjdr_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and type = 4  and is_ccgames = 'Y' ;", c);
                val.xueliuRake = xueliurake + xueliuAI;
            }
            //血战收入
            {
                long xuezhanrake = ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                        " from t_mjdr_rake " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and type = 3  and is_ccgames = 'Y' ;", c);
                long xuezhanAI = ServiceManager.getSqlService().queryLong("select coalesce( -sum( gold ), 0 ) " +
                        " from t_mjdr_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and type = 3  and is_ccgames = 'Y' ;", c);
                val.xuezhanRake = xuezhanAI + xuezhanrake;
            }
            //二麻收入
            long errenrake = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 5  and is_ccgames = 'Y' ;", c );
            long errenAI =  ServiceManager.getSqlService().queryLong( "select coalesce( -sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and type = 5  and is_ccgames = 'Y' ;", c );

            val.twoMaRake = errenAI + errenrake ;
            //老德州收入
            {
                long dezhouRake = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_gdesk_pump " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and desk_type not in ('chuji','zhongji','gaoji','dashi','zhizun');", c );
                long yaojiangjiRake = ServiceManager.getSqlService().queryLong("select sum( chips ) " +
                        " from t_yyl_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_robot ='N';", c) - ServiceManager.getSqlService().queryLong("select sum( chips ) " +
                        " from t_yyl_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_robot ='N';", c);
                long dingshiOut = ServiceManager.getSqlService().queryLong("select sum( chips ) " +
                        " from t_gdesk_expense " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_robot ='N' and kind ='timer';", c);

                long zhuanpanOut = ServiceManager.getSqlService().queryLong("select sum( chips ) " +
                        " from t_gdesk_expense " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_robot ='N' and kind = 'gdeskLuckyWheelBonus';", c);

                long totalPump = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_gdesk_pump " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_robot = 'N' and desk_type = 'vip';", c);
                long playerCount = ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                        " from t_gdesk_play " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_robot = 'N' and desk_type = 'vip';", c);
                long masterPointPay = ServiceManager.getSqlService().queryLong("select coalesce( sum( master_point ), 0 ) " +
                        " from t_mp_get " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_robot = 'N' and source = 0;", c);
                long bonusChips = (playerCount > 0L ? 55000000L : 0L);
                long OldDzjackpotPay = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_gdesk_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_robot = 'N' and desk_type = 'vip';", c);
                long rebatePay = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_gdesk_expense " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_robot = 'N' and desk_type = 'vip';", c);
                long otherPay = OldDzjackpotPay + rebatePay;
                long youlunRake = totalPump - bonusChips - otherPay;

                val.oldPokerRake = dezhouRake + yaojiangjiRake + youlunRake - dingshiOut - zhuanpanOut;
            }
            //新德州收入
            val.newPokerRake = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_gdesk_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and desk_type in ('chuji','zhongji','gaoji','dashi','zhizun')" +
                    " and is_robot = 'N';", c );
            //斗地主收入
            {
                List<String> DDZgetinfo = ServiceManager.getSqlService().queryListString( "select bonus " +
                        " from t_task_finish " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and type =100 ;", c );

                Map< Long, Long > DDZgetMap = new HashMap<>();
                /**
                 * 道具id -1为元宝
                 *        -2为体力
                 * */
                DDZgetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), DDZgetMap ) );
                long missionPay             = getLong( getMap, -1 );
//            val.baotu            = getLong( getMap, 10301 );

                long choujiang = ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
                        " from t_ddz_choujiang " +
                        " where "  + beg + " <= timestamp and timestamp <  " + end + " and item_id = -1;", c );

                long ddzShouru = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                        " from t_ddz_rake  " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   ;", c );

                val.ddzRake =ddzShouru  -choujiang -missionPay ;
                val.ddzRake = ServiceManager.getSqlService().queryLong("select coalesce( sum( gold ), 0 ) " +
                        " from t_ddz_rake " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " ;", c);
            }
            //跑跑堂
            {
                long totalBet = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_ppt_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long totalWin = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_ppt_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long pptJackpot = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_ppt_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long sscBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_pptssc_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long sscWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_pptssc_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                val.pptRake = totalBet + sscBet - sscWin - totalWin - pptJackpot;
            }
            //水果机收入
            {
                long totalWinFruit = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_fruit_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long totalBetFruit = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_fruit_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long jackpotPayFruit = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_fruit_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long bibeiWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_caibei_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long bibeiBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_caibei_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                val.fruitRake = totalBetFruit + bibeiBet - bibeiWin - totalWinFruit - jackpotPayFruit;
            }
            //跑狗收入
            {
                long dogTotalBet = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_dogsport_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long dogWinSum = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_dogsport_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                long dogJackpotPay = ServiceManager.getSqlService().queryLong("select coalesce( sum( chips ), 0 ) " +
                        " from t_dogsport_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c);
                val.dogRake = dogTotalBet - dogWinSum - dogJackpotPay;
            }
            val.mjdrChoujiang  = ServiceManager.getSqlService().queryLong( "select coalesce( sum( bonus_count ), 0 ) " +
                            " from t_mjdr_lottery " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and bonus_id = -1;",
                    c );

            val.fishRake = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_fish_paiment  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N';", c )
                    -
                    ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                            " from t_fish_get  " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            " and is_robot ='N';", c );

            val.allRake = val.zfbRake + val.mtl2Rake     + val.xueliuRake   + val.xuezhanRake   + val.newPokerRake
                    + val.oldPokerRake + val.ddzRake      + val.twoMaRake     + val.pptRake       + val.pptRake
                    + val.dogRake      + val.fruitRake    - val.mjdrChoujiang - val.publicOut + val.fishRake;
            return val;
        } );
    }

    public void queryFinance( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_SUMMARY_FINANCE, getFinanceData( req, resp ),
                financeTableHead, (info, pw) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.newPokerRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.oldPokerRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.zfbRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.mtl2Rake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.xueliuRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.xuezhanRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.ddzRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.twoMaRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.pptRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.fruitRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.dogRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.fishRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.publicOut/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.mjdrChoujiang/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.allRake/10000 + "万 " + "</td>");
                });
    }

    public void downloadFinance( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_SUMMARY_FINANCE, getFinanceData( req, resp ),
                financeTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.newPokerRake/10000 + "万 " + ",");
                    pw.write( info.oldPokerRake/10000 + "万 " + ",");
                    pw.write( info.zfbRake/10000 + "万 " + ",");
                    pw.write( info.mtl2Rake/10000 + "万 " + ",");
                    pw.write( info.xueliuRake/10000 + "万 " + ",");
                    pw.write( info.xuezhanRake/10000 + "万 " + ",");
                    pw.write( info.ddzRake/10000 + "万 " + ",");
                    pw.write( info.twoMaRake/10000 + "万 " + ",");
                    pw.write( info.pptRake/10000 + "万 " + ",");
                    pw.write( info.fruitRake/10000 + "万 " + ",");
                    pw.write( info.dogRake/10000 + "万 " + ",");
                    pw.write( info.fishRake/10000 + "万 " + ",");
                    pw.write( info.publicOut/10000 + "万 " + ",");
                    pw.write( info.mjdrChoujiang/10000 + "万 " + ",");
                    pw.write( info.allRake/10000 + "万 " + ",");
                } );
    }

    private List< SummaryView.Finance > getFishFinanceData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.Finance val = new SummaryView.Finance();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            //中发白收入
            {
                long zfbtotalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' ;", c );
                long zfbbetPumpSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips * ratio ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and banker_id != 0 and is_robot = 'N';", c );
                long zfbchipZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                        " from t_zfb_shangzhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_use_card = 'N';", c );
                long zfbjoinZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                        " from t_zfb_shangzhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_join = 'Y';", c );
                long zfbcardZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                        " from t_zfb_shangzhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_use_card = 'Y';", c );
                long zfbescapePump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( escape_pump ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
                long zfbxiazhuangPump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips * ratio ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_escape = 'N';", c );
                long zfbcardPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( use_card_change ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long zfbcardPayPumpSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( use_card_change * ratio ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and chips > 0;", c );
                long zfbjackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N';", c );
                long zfbsendFlowerSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_flowers " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long zfbsystemRedSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_free_reward " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_free = 'Y';", c );
                long zfbexprIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_send_expression " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long zfbtotalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' ;", c );
                long zfbAIzhuangWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' and banker_id = 0;", c );
                long zfbAIzhuangBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' and banker_id = 0;", c );

                long zfbrewardIncome = zfbsendFlowerSum - zfbsystemRedSum;
                val.zfbRake = zfbcardPayPumpSum + zfbbetPumpSum
                        - zfbjackpotPay + zfbescapePump + zfbrewardIncome + zfbexprIncome
                        + zfbAIzhuangBet - zfbAIzhuangWin - zfbcardPay;
            }
            //摩天轮收入

            val.mtl2Rake = Mtl2Action.getTotalRake( beg, end, c );

            //百人德州收入
            {
                long zongtouzhu = ServiceManager.getSqlService().queryLong("select sum(bet + caijin_bet) from t_hundred_dezhou " +
                        "where " + beg + " <= timestamp and timestamp < " + end + " ;", c);
                List<String> zongchanchu = ServiceManager.getSqlService().queryListString("select win " +
                        " from t_hundred_dezhou " + " where " + beg + " <= timestamp and timestamp < " + end + "  ;", c);
                Map<Long, Long> chanchuMap = new HashMap<>();
                zongchanchu.forEach(info -> mergeMap(splitMap(info, ";", ":"), chanchuMap));
                long zongchanchu1 = getLong(chanchuMap, 1) + getLong(chanchuMap, 2);
                val.BRDZRake = (long) (zongtouzhu - zongchanchu1 + ((double)zongchanchu1 * 0.05));
            }
            val.fishRake = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_fish_paiment  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    ";", c )
                    -
                    ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                            " from t_fish_get  " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            " ;", c );

//            List<String> gold  = ServiceManager.getSqlService().queryListString( "select bouns " +
//                    " from t_fish_mb_grants " +
//                    " where " + beg + " <= timestamp and timestamp < " + end + " and type not in (" + FishMBAction.user_jackpot_grants + " , " + FishMBAction.caiyu_fish_get +","+ FishMBAction.pay_diamond_grants +"," + FishMBAction.qipao_fish_get +")  ;", c );
//
//            Map< Long, Long > goldMap = new HashMap<>();
//            gold.forEach( info -> mergeMap( splitMap( info, ";", ":" ), goldMap ) );
//            List<String> Getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
//                    " from t_newactivity " +
//                    " where "    + beg + " <= timestamp and timestamp < " + end + " and  activity_id =1 ;", c );
//            Map< Long, Long > getMap = new HashMap<>();
//            Getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
//            val.publicOut     = getLong(goldMap , -1) + getLong(goldMap , -4 ) * 2000 + getLong(goldMap , 10544 ) * 625  + getLong(goldMap , 20015) * 20000 + getLong(goldMap , 20016 ) * 200000 + getLong(goldMap , 20017 ) * 1000000 +
//                    getLong( getMap , -1)  + getLong(getMap , 20106 ) * 10000 + getLong(getMap , 20107 ) * 30000 + getLong(getMap , 20108 ) * 50000 + getLong(getMap , 20109 ) * 100000 ;

            long install         = GetItem( beg, end, -1, FishMBAction.user_install_grants, c);
            long login           = GetItem( beg, end, -1, FishMBAction.login_grants, c);
            long leiji           = GetItem( beg, end, -1, FishMBAction.login_leiji_grants, c);
            long task            = GetItem( beg, end, -1, FishMBAction.day_task_grants, c) + GetItem( beg, end, -1, FishMBAction.week_task_grants, c) + GetItem( beg, end, -1, FishMBAction.day_act_grants, c) + GetItem( beg, end, -1, FishMBAction.week_act_grants, c);
            long onlineRed       = GetItem( beg, end, -1, FishMBAction.red_grants, c);
            long upLevel         = GetItem( beg, end, -1, FishMBAction.user_up_grants, c) + GetItem( beg, end, -1, FishMBAction.gun_up_grants, c);
            long broke           = GetItem( beg, end, -1, FishMBAction.broke_grants, c);
            long phone           = GetItem( beg, end, -1, FishMBAction.phone_grants, c);
            long gengxin         = GetItem( beg, end, -1, FishMBAction.update_grants, c);
            long cdkey           = GetItem( beg, end, -1, FishMBAction.cdkey_grants, c);
            long VIPLogin        = GetItem( beg, end,  -1,FishMBAction.vip_login_give_grants, c)  ;
            long vip_up          = GetItem( beg, end,  -1,FishMBAction.vip_up_grants, c) ;
            long Pay_jiangchi    = GetItem( beg, end,  -1,FishMBAction.pay_diamond_grants, c) ;
            long captain         = GetItem( beg, end,  -1,FishMBAction.captain_grants, c) +GetItem( beg, end, -1,FishMBAction. week_grants, c) ;
            long first_pay       = GetItem( beg, end,  -1, FishMBAction.firstRecharge_pay_grants,c) ;
            long caibeiFish      = GetItem( beg, end,  -1,FishMBAction.caiyu_fish_get, c) ;
            long tuiguang      = GetItem( beg, end,  -1,FishMBAction.tuiguang_jifen_grants, c) +  GetItem( beg, end,  -1,FishMBAction.tuiguang_bangding_grants, c) ;

            List<String> leiji1  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + FishMBAction.leijichongzhi_grants +"  ;", c );
            Map< Long, Long > leijiMap = new HashMap<>();
            leiji1.forEach( info -> mergeMap( splitMap( info, ";", ":" ), leijiMap ) );
            long leijichongzhi = getLong(leijiMap , -1) + getLong(leijiMap , 20015 ) * 20000 + getLong(leijiMap , 20016 ) * 200000 + getLong(leijiMap , 20017 ) * 1000000;

//            List<String> qifuGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
//                    " from t_newactivity " +
//                    " where "    + beg + " <= timestamp and timestamp < " + end + " and  activity_id =1 ;", c );
//            Map< Long, Long > qifugetMap = new HashMap<>();
//            qifuGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), qifugetMap ) );
//            long qifu   = getLong(qifugetMap , -1) + getLong(qifugetMap , 20015 ) * 20000 + getLong(qifugetMap , 20016 ) * 200000 + getLong(qifugetMap , 20017 ) * 1000000 + getLong(qifugetMap , 10507 ) * 10000
//                     +  getLong(qifugetMap , 10512 ) * 1000000 +   getLong(qifugetMap , -4 ) * 4500 ;

            long cccGoldBet = ServiceManager.getSqlService().queryLong( "select sum(cost_count) " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 9 and cost_id = -1 ;", c );

            List<String> Getinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            Map< Long, Long > getMap2 = new HashMap<>();
            Getinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );
            long wanshengjie   = getLong(getMap2 , -1) + getLong(getMap2 , 20015 ) * 20000
                    + getLong(getMap2 , 20016 ) * 200000 + getLong(getMap2 , 20017 ) * 1000000
                    + getLong(getMap2 , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
                    + getLong(getMap2 , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
                    + getLong(getMap2 , 20040 )  * (ItemManager.getInstance().get( 20040 ).worth)
                    + getLong(getMap2 , 20041 )  * (ItemManager.getInstance().get( 20041 ).worth)
                    + getLong(getMap2 , 20042 )  * (ItemManager.getInstance().get( 20042 ).worth)
                    + getLong(getMap2 , 20043 )  * (ItemManager.getInstance().get( 20043 ).worth)
                    + getLong(getMap2 , -4 ) * 4000 - cccGoldBet + getLong(getMap2 , -12 ) * 500;

            List<String> boss  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in (" + FishMBAction.world_boss_die_grants +"," + FishMBAction.world_boss_rank_grants +" )  ;", c );
            Map< Long, Long > bossMap = new HashMap<>();
            boss.forEach( info -> mergeMap( splitMap( info, ";", ":" ), bossMap ) );
            long boss1 = getLong(bossMap , -1) + getLong(bossMap , 20015 ) * 20000 + getLong(bossMap , 20016 ) * 200000 + getLong(bossMap , 20017 ) * 1000000 ;

            List<String> jingji  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in (" + FishMBAction.jingji_day_rank_grants +"," + FishMBAction.jingji_jifen_grants +"," + FishMBAction.jingji_task_grants + "," + FishMBAction.jingji_week_rank_grants +" )  ;", c );
            Map< Long, Long > jingjiMap = new HashMap<>();
            jingji.forEach( info -> mergeMap( splitMap( info, ";", ":" ), jingjiMap ) );
            long jingji1 = getLong(jingjiMap , -1)  + getLong(jingjiMap , 20015 ) * 20000 + getLong(jingjiMap , 20016 ) * 200000 + getLong(jingjiMap , 20017 ) * 1000000 ;

            List<String> free  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in (" + FishMBAction.poker_free_grants + " );", c );
            Map< Long, Long > freemap = new HashMap<>();
            free.forEach( info -> mergeMap( splitMap( info, ";", ":" ), freemap ) );
            Map< Long, Long > countInfo = ServiceManager.getSqlService().queryMapLongLong( "select level,count(id) from t_fish_poker where " + beg + " <= timestamp and timestamp < " + end  +
                    " group by level;", c );
            long min  = ServiceManager.getSqlService().queryLong( "select min(inning_id) " +
                    " from t_fish_poker " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and level = 404 ;", c );
            long max  = ServiceManager.getSqlService().queryLong( "select max(inning_id) " +
                    " from t_fish_poker " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and level = 404 ;", c );
//            long poker = getLong(freemap , -1)
//                    -   (max - min + 1)*1000000
//                    + ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_mb_grants where " + beg + " <= timestamp and timestamp < " + end  +
//                    " and type = "+ FishMBAction.poker_dantou_grants +";", c ) * 1000000 ;

            long poker = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_mb_grants where " + beg + " <= timestamp and timestamp < " + end  +
                    " and type = "+ FishMBAction.poker_dantou_grants +";", c ) * 1000000 ;

            Map< Long, Long > user_buy_item = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum(item_count) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by item_id;", c );

            long shopOut = getLong(user_buy_item ,20110 ) * (ItemManager.getInstance().get( 20110 ).worth)
                    + getLong(user_buy_item ,20111 ) * (ItemManager.getInstance().get( 20111 ).worth)
                    + getLong(user_buy_item ,20112 ) * (ItemManager.getInstance().get( 20112 ).worth)
                    + getLong(user_buy_item ,20113 ) * (ItemManager.getInstance().get( 20113 ).worth)
                    + getLong(user_buy_item ,20114 ) * (ItemManager.getInstance().get( 20114 ).worth)
                    + getLong(user_buy_item ,20115 ) * (ItemManager.getInstance().get( 20115 ).worth)
                    + getLong(user_buy_item ,20130 ) * (ItemManager.getInstance().get( 20130 ).worth)
                    + getLong(user_buy_item ,20131 ) * (ItemManager.getInstance().get( 20131 ).worth)
                    + getLong(user_buy_item ,20132 ) * (ItemManager.getInstance().get( 20132 ).worth)
                    + getLong(user_buy_item ,20133 ) * (ItemManager.getInstance().get( 20133 ).worth)
                    + getLong(user_buy_item ,20134 ) * (ItemManager.getInstance().get( 20134 ).worth)
                    + getLong(user_buy_item ,20135 ) * (ItemManager.getInstance().get( 20135 ).worth)
                    +  GetItem( beg, end,  -1,FishMBAction.pay_huikui_grants, c);

            long tehui = getLong(user_buy_item ,20022 ) * (ItemManager.getInstance().get( 20022 ).worth + ItemManager.getInstance().get( 20022 ).buyAddGold)
                    + getLong(user_buy_item ,20023 ) * (ItemManager.getInstance().get( 20023 ).worth + ItemManager.getInstance().get( 20023 ).buyAddGold)
                    + getLong(user_buy_item ,20030 ) * (ItemManager.getInstance().get( 20030 ).worth + ItemManager.getInstance().get( 20030 ).buyAddGold)
                    + getLong(user_buy_item ,20031 ) * (ItemManager.getInstance().get( 20031 ).worth + ItemManager.getInstance().get( 20031 ).buyAddGold)
                    ;
            long gold33 = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_get_ietms where " + beg + " <= timestamp and timestamp < " + end  +
                    " and cost_item_id = 20033;", c ) * 2000000 ;
            long gold35 = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_get_ietms where " + beg + " <= timestamp and timestamp < " + end  +
                    " and cost_item_id = 20035;", c ) * 5000000 ;
            long gold37 = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_get_ietms where " + beg + " <= timestamp and timestamp < " + end  +
                    " and cost_item_id = 20037;", c ) * 20000000 ;
            List<String> duihuanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_fish_get_ietms " +
                    " where "    + beg + " <= timestamp and timestamp < " + end + "  ;", c );
            Map< Long, Long > duihuangetMap = new HashMap<>();
            duihuanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), duihuangetMap ) );
            long duihuan   = getLong(duihuangetMap , -1) + getLong(duihuangetMap , 20015 ) * 20000 + getLong(duihuangetMap , 20016 ) * 200000 + getLong(duihuangetMap , 20017 ) * 1000000 + getLong(duihuangetMap , 10507 ) * 10000
                    +  getLong(duihuangetMap , 10512 ) * 1000000 +   getLong(duihuangetMap , -4 ) * 3000 - gold33 - gold35 - gold37;


            val.publicOut     = install + task + login + onlineRed + upLevel + broke + phone
                    + gengxin +  cdkey  + jingji1 + boss1  + first_pay + leijichongzhi + wanshengjie + captain + VIPLogin + vip_up + poker  + tehui + shopOut + tuiguang + duihuan;

            val.allRake = val.zfbRake + val.mtl2Rake + val.BRDZRake  + val.fishRake + val.pptRake -  val.publicOut;
            return val;
        } );
    }


    public void queryFishFinance( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_SUMMARY_FISH_FINANCE, getFishFinanceData( req, resp ),
                FishFinanceTableHead, (info, pw) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.zfbRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.mtl2Rake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.BRDZRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.fishRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.pptRake/10000 + "万 " + "</td>");
                    pw.write("<td>" + -info.publicOut/10000 + "万 " + "</td>");
                    pw.write("<td>" + info.allRake/10000 + "万 " + "</td>");
                });
    }

    public void downloadFishFinance( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_SUMMARY_FISH_FINANCE, getFishFinanceData( req, resp ),
                FishFinanceTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zfbRake/10000 + "万 " + ",");
                    pw.write( info.mtl2Rake/10000 + "万 " + ",");
                    pw.write( info.BRDZRake/10000 + "万 " + ",");
                    pw.write( info.fishRake/10000 + "万 " + ",");
                    pw.write( -info.publicOut/10000 + "万 " + ",");
                    pw.write( info.allRake/10000 + "万 " + ",");
                } );
    }



    public void queryHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_SUMMARY_HUIZONG, getFinanceData( req, resp ),
                huizongTableHead, (info, pw) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.allRake + "</td>");
                    pw.write("<td>" + info.rmb / 10000 + "</td>");
                });
    }

    public void downloadHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_SUMMARY_HUIZONG, getFinanceData( req, resp ),
                huizongTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.allRake + ",");
                    pw.write( info.rmb / 10000 + ",");
                } );
    }

    private List< SummaryView.Finance > getFishHuiZongData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.Finance val = new SummaryView.Finance();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.rmb = ServiceManager.getSqlService().queryLong("select coalesce( sum( money2 + money3 ), 0 ) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " ;", c);

            //中发白收入
            {
                long zfbtotalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' ;", c );
                long zfbbetPumpSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips * ratio ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and banker_id != 0 and is_robot = 'N';", c );
                long zfbchipZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                        " from t_zfb_shangzhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_use_card = 'N';", c );
                long zfbjoinZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                        " from t_zfb_shangzhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_join = 'Y';", c );
                long zfbcardZhuangCnt = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                        " from t_zfb_shangzhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_use_card = 'Y';", c );
                long zfbescapePump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( escape_pump ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
                long zfbxiazhuangPump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips * ratio ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_escape = 'N';", c );
                long zfbcardPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( use_card_change ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long zfbcardPayPumpSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( use_card_change * ratio ), 0 ) " +
                        " from t_zfb_xiazhuang " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and chips > 0;", c );
                long zfbjackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N';", c );
                long zfbsendFlowerSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_flowers " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long zfbsystemRedSum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_free_reward " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "   and is_free = 'Y';", c );
                long zfbexprIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_send_expression " +
                        " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
                long zfbtotalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' ;", c );
                long zfbAIzhuangWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_win " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' and banker_id = 0;", c );
                long zfbAIzhuangBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                        " from t_zfb_bet " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and is_robot='N' and banker_id = 0;", c );

                long zfbrewardIncome = zfbsendFlowerSum - zfbsystemRedSum;
                val.zfbRake = zfbcardPayPumpSum + zfbbetPumpSum
                        - zfbjackpotPay + zfbescapePump + zfbrewardIncome + zfbexprIncome
                        + zfbAIzhuangBet - zfbAIzhuangWin - zfbcardPay;
            }
            //摩天轮收入

            val.mtl2Rake = Mtl2Action.getTotalRake( beg, end, c );

            val.fishRake = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_fish_paiment  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N';", c )
                    -
                    ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                            " from t_fish_get  " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            " and is_robot ='N';", c );

            List<String> gold  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type not in (" + FishMBAction.user_jackpot_grants + " , " + FishMBAction.caiyu_fish_get +","+ FishMBAction.pay_diamond_grants +"," + FishMBAction.qipao_fish_get +") and is_mobile = 'Y' ;", c );

            Map< Long, Long > goldMap = new HashMap<>();
            gold.forEach( info -> mergeMap( splitMap( info, ";", ":" ), goldMap ) );
            List<String> Getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_newactivity " +
                    " where "    + beg + " <= timestamp and timestamp < " + end + "  ;", c );
            Map< Long, Long > getMap = new HashMap<>();
            Getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
            val.Out     = getLong(goldMap , -1) + getLong(goldMap , -4 ) * 2000 + getLong(goldMap , 10544 ) * 625  + getLong(goldMap , 20015) * 20000 + getLong(goldMap , 20016 ) * 200000 + getLong(goldMap , 20017 ) * 1000000 +
                    getLong( getMap , -1)  + getLong(getMap , 20106 ) * 10000 + getLong(getMap , 20107 ) * 30000 + getLong(getMap , 20108 ) * 50000 + getLong(getMap , 20109 ) * 100000 ;
// 测试 获取道具价值
// long a = ItemManager.getInstance().get(10537).worth;
            val.allRake = val.zfbRake + val.mtl2Rake + val.fishRake;
            return val;
        } );
    }

    public void queryFishHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_SUMMARY_FISH_HUIZONG, getFishHuiZongData( req, resp ),
                FishihuizongTableHead, (info, pw) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.Out /10000+ "万</td>");
                    pw.write("<td>" + info.allRake /10000+ "万</td>");
                    pw.write("<td>" + info.rmb / 10000 + "元</td>");
                    pw.write("<td>" +  (Math.abs(info.allRake - info.Out) / ( info.rmb  / 10000 ))  + "</td>");
                });
    }

    public void downloadFishHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_SUMMARY_FISH_HUIZONG, getFishHuiZongData( req, resp ),
                FishihuizongTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.Out + "," );
                    pw.write( info.allRake + ",");
                    pw.write( info.rmb / 10000 + ",");
                    pw.write((Math.abs(info.allRake - info.Out) /( info.rmb * 1.0 / 10000 ))  + "," );
                } );
    }

    public static final Map<String , String> user = new HashMap<String, String>();
    static {
        user.put("xiaomai", "2950");            user.put("kuaile", "2953");
        user.put("yuyang", "2952");              user.put("huayao", "2954");
        user.put("jingjing", "2955");           user.put("hulu", "2956");
        user.put("baixiaohei", "2957");        user.put("naonao", "2958");
        user.put("duyao", "2959");              user.put("junjun", "2960");
    }
    private static List< String > qudaoInstall = Arrays.asList( "2950", "2953","2952","2954","2955","2956","2957","2958","2959","2960");
    public static final List< String > userList = Arrays.asList( "xiaomai", "kuaile","yuyang","huayao","jingjing","hulu","baixiaohei","naonao","duyao","junjun","zbdz");

    private List< SummaryView.Install > getInstallData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        TimeRange tr = TimeRange.parseFrom( req );
        String  qudao = user.get((String) req.getSession().getAttribute( "userName" ));

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.Install val = new SummaryView.Install();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.install = ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                    " from t_install " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and from_where = " + qudao + ";", c);

            val.ip = ServiceManager.getSqlService().queryLong("select count( distinct ip ) " +
                    " from t_install " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and from_where = " + qudao + ";", c);

            val.shebei = ServiceManager.getSqlService().queryLong("select count( distinct remark ) " +
                    " from t_install " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and from_where = " + qudao + ";", c);

            val.youxiao = ServiceManager.getSqlService().queryLong("select count( distinct CONCAT( ip , remark)) " +
                    " from t_install " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and from_where = " + qudao + ";", c);

            List<String> cdkey = ServiceManager.getSqlService().queryListString( "select other_info " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and appid in (1239313319,1251807724) and type =  33 ;", c );

//            cdkey.replaceAll( k ->  k.replace("[","").replace("]","").replace("\"","").replace("\"",""));
//            cdkey.forEach( k ->  k.replace("["," ").replace("]"," ").replace("\""," ").replace("\""," ") );
            String aa = (String) req.getSession().getAttribute( "userName" );

            if (aa.equals("yuyang"))
            {ZhuboView.yuyangList.retainAll(cdkey);val.ios = ZhuboView.yuyangList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"F8957B139D9C4EB\"]';", c);}
            if (aa.equals("xiaomai"))
            {ZhuboView.xiaomaiList.retainAll(cdkey);val.ios = ZhuboView.xiaomaiList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"ED96DC026CEC9BB\"]';", c);}
            if (aa.equals("huayao"))
            {ZhuboView.huayaoList.retainAll(cdkey);val.ios =  ZhuboView.huayaoList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"865F13C6F786135\"]';", c);}
            if (aa.equals("kuaile"))
            {ZhuboView.yongleList.retainAll(cdkey);val.ios =  ZhuboView.yongleList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"54FF33057497066\"]';", c);}
            if (aa.equals("baixiaohei"))
            {ZhuboView.baixiaoheiList.retainAll(cdkey);val.ios = ZhuboView.baixiaoheiList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"A83D5804A5EAFC7\"]';", c);}
            if (aa.equals("jingjing"))
            {ZhuboView.xiaojingList.retainAll(cdkey);val.ios = ZhuboView.xiaojingList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"F6200E94CD250B9\"]';", c);}
            if (aa.equals("naonao"))
            {ZhuboView.naonaoList.retainAll(cdkey);val.ios = ZhuboView.naonaoList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"41834AC62AA69A5\"]';", c);}
            if (aa.equals("duyao"))
            {ZhuboView.duyaoList.retainAll(cdkey);val.ios = ZhuboView.duyaoList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"B8A11B612E3F1EB\"]';", c);}
            if (aa.equals("junjun"))
            {ZhuboView.junjunList.retainAll(cdkey);val.ios = ZhuboView.junjunList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"F13BB354A6F3F8C\"]';", c);}
            if (aa.equals("hulu"))
            {val.ios = ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"0A954031EA5AC39\"]';", c);}

            if (aa.equals("zbdz"))
            {
                val.and = ServiceManager.getSqlService().queryMapStringLong("select from_where,count( distinct CONCAT( ip , remark)) " +
                        " from t_install " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " group by from_where;", c);
                val.yuyangand   = getLong(val.and,user.get( "yuyang" ));
                val.xiaomaiand  = getLong(val.and,user.get( "xiaomai" ));
                val.huayaoand   = getLong(val.and,user.get( "huayao" ));
                val.kuaileand   = getLong(val.and,user.get( "kuaile" ));
                val.baixiaoheiand = getLong(val.and,user.get( "baixiaohei" ));
                val.jingjingand = getLong(val.and,user.get( "jingjing" ));
                val.naonaoand   = getLong(val.and,user.get( "naonao" ));
                val.duyaoand    = getLong(val.and,user.get( "duyao" ));
                val.junjunand   = getLong(val.and,user.get( "junjun" ));
                val.huluand     = getLong(val.and,user.get( "hulu" ));

                ZhuboView.yuyangList.retainAll( cdkey );val.yuyangios  = ZhuboView.yuyangList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"F8957B139D9C4EB\"]';", c);
                ZhuboView.xiaomaiList.retainAll( cdkey );val.xiaomaiios = ZhuboView.xiaomaiList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"ED96DC026CEC9BB\"]';", c);
                ZhuboView.huayaoList.retainAll( cdkey );val.huayaoios =  ZhuboView.huayaoList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"865F13C6F786135\"]';", c);
                ZhuboView.yongleList.retainAll( cdkey );val.kuaileios =  ZhuboView.yongleList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"54FF33057497066\"]';", c);
                ZhuboView.baixiaoheiList.retainAll( cdkey );val.baixiaoheiios = ZhuboView.baixiaoheiList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"A83D5804A5EAFC7\"]';", c);
                ZhuboView.xiaojingList.retainAll( cdkey );val.jingjingios = ZhuboView.xiaojingList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"F6200E94CD250B9\"]';", c);
                ZhuboView.naonaoList.retainAll( cdkey );val.naonaoios = ZhuboView.naonaoList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"41834AC62AA69A5\"]';", c);
                ZhuboView.duyaoList.retainAll( cdkey );val.duyaoios = ZhuboView.duyaoList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"B8A11B612E3F1EB\"]';", c);
                ZhuboView.junjunList.retainAll( cdkey );val.junjunios = ZhuboView.junjunList.size() + ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"F13BB354A6F3F8C\"]';", c);
                val.huluios = ServiceManager.getSqlService().queryLong("select count( id ) from t_fish_mb_grants  where " + beg + " <= timestamp and timestamp < " + end + " and type = 33 and appid in (1239313319,1251807724) and other_info = '[\"0A954031EA5AC39\"]';", c);

            }
            try {
                ZhuboView.init();
            }catch ( Exception e)
            {
                System.out.print("aaaa");
            }
            return val;
        } );
    }
    public void queryInstall( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        String aa = (String) req.getSession().getAttribute( "userName" );
        if (aa.equals("zbdz"))
        {
            queryList( req, resp,
                    CommandList.GET_QUDAO_INSATLL, getInstallData( req, resp ),
                    installTableHead2, ( info, pw ) -> {
                        pw.write( "<td>" + info.begin + "</td>" );
                        pw.write( "<td>" + info.yuyangand +  "</td>" );
                        pw.write( "<td>" + info.yuyangios +  "</td>" );
                        pw.write( "<td>" + info.xiaomaiand +  "</td>" );
                        pw.write( "<td>" + info.xiaomaiios +  "</td>" );
                        pw.write( "<td>" + info.huayaoand +  "</td>" );
                        pw.write( "<td>" + info.huayaoios +  "</td>" );
                        pw.write( "<td>" + info.kuaileand +  "</td>" );
                        pw.write( "<td>" + info.kuaileios +  "</td>" );
                        pw.write( "<td>" + info.baixiaoheiand +  "</td>" );
                        pw.write( "<td>" + info.baixiaoheiios +  "</td>" );
                        pw.write( "<td>" + info.jingjingand +  "</td>" );
                        pw.write( "<td>" + info.jingjingios +  "</td>" );
                        pw.write( "<td>" + info.naonaoand +  "</td>" );
                        pw.write( "<td>" + info.naonaoios +  "</td>" );
                        pw.write( "<td>" + info.duyaoand +  "</td>" );
                        pw.write( "<td>" + info.duyaoios +  "</td>" );
                        pw.write( "<td>" + info.junjunand +  "</td>" );
                        pw.write( "<td>" + info.junjunios +  "</td>" );
                        pw.write( "<td>" + info.huluand +  "</td>" );
                        pw.write( "<td>" + info.huluios +  "</td>" );
                    } );
        }
        else {
        queryList( req, resp,
                CommandList.GET_QUDAO_INSATLL, getInstallData( req, resp ),
                installTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.install +  "</td>" );
                    pw.write( "<td>" + info.ios +  "</td>" );
                    pw.write( "<td>" + info.shebei +  "</td>" );
                    pw.write( "<td>" + info.ip +  "</td>" );
                    pw.write( "<td>" + (info.youxiao  + info.ios )+ "</td>" );
                } );
        }
    }

    private List< SummaryView.dantou > getUserDanTouData( HttpServletRequest req, HttpServletResponse resp  )
            throws Exception {
        String qudao = req.getParameter( "qudao" );
        return getData( req, resp, ( beg, end, c ) -> {

            Map<String , SummaryView.dantou > val = new TreeMap<>();
            if (qudao.equals("huai"))
            {
                //掉落
                Map< String, Long > diaoluo = ServiceManager.getSqlService().queryMapStringLong( "select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H'), sum(item_count) " +
                        " from t_fish_item " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "  and appid != 'b477860b9724470cba748d4bae67beee' and item_id in (20017,11019) and item_count > 0 group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H');", c );
                Map< String, Long > shiyong = ServiceManager.getSqlService().queryMapStringLong( "select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H'), sum(item_count) " +
                        " from t_fish_item " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and appid != 'b477860b9724470cba748d4bae67beee'  and item_id in (20017,11019) and item_count < 0 group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H');", c );

                Map< String, Long > huodong = ServiceManager.getSqlService().queryMapStringLong( "select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H'),sum(getValue(get_info,'20017')) " +
                        " from t_newactivity " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and appid != 'b477860b9724470cba748d4bae67beee' group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H');", c );

                Map< String, Long > baoxiang = ServiceManager.getSqlService().queryMapStringLong( "select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H'),sum( coalesce( getValue2(get_info,\"20017\"), 0 ) ) " +
                        " from t_fish_get_ietms " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and appid != 'b477860b9724470cba748d4bae67beee' and cost_item_id in (20033,20035,20037) group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H');", c );
                Map< String, Long > chongzhi = ServiceManager.getSqlService().queryMapStringLong( "select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H'),sum(money2) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "  and appid != 'b477860b9724470cba748d4bae67beee' and user_id not in (select distinct user_id from t_install where from_where = 3311 ) group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H');", c );
                Map< String, Long > jiaoyi = ServiceManager.getSqlService().queryMapStringLong( "select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H'),sum(item_count) " +
                        " from t_important_props " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and send_appid != 'b477860b9724470cba748d4bae67beee' and item_id in (20017,11019) group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H');", c );
                diaoluo.forEach(( k, v) -> {
                    if( val.get( k ) == null ) val.put( k, new SummaryView.dantou() );
                    val.get( k ).diaoluo = v;
                });
                shiyong.forEach(( k, v) -> {
                    if( val.get( k ) == null ) val.put( k, new SummaryView.dantou() );
                    val.get( k ).zongshiyong = v;
                });
                baoxiang.forEach(( k, v) -> {
                    if( val.get( k ) == null ) val.put( k, new SummaryView.dantou() );
                    val.get( k ).baoxiang = v;
                });
                chongzhi.forEach(( k, v) -> {
                    if( val.get( k ) == null ) val.put( k, new SummaryView.dantou() );
                    val.get( k ).chongzhi = v;
                    List<String> huodong2 = ServiceManager.getSqlService().queryListString( "select get_info " +
                            " from t_newactivity " +
                            " where " + beg + " <= timestamp and timestamp < " + end + "  and appid != 'b477860b9724470cba748d4bae67beee' and FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H') = '" + k + "';", c );
                    Map< Long, Long > getMap = new HashMap<>();
                    huodong2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );

                    List<String> sheshou = ServiceManager.getSqlService().queryListString( "select get_info " +
                            " from t_fish_get_ietms " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and appid != 'b477860b9724470cba748d4bae67beee' and cost_item_id in (20043,20042,20041,20040) and FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H') = '" + k + "';", c );
                    Map< Long, Long > sheshougetMap = new HashMap<>();
                    sheshou.forEach( info -> mergeMap( splitMap( info, ";", ":" ), sheshougetMap ) );

                    val.get( k ).huodong = getLong( getMap,20017 ) + getLong(sheshougetMap , 20017);
                });
                jiaoyi.forEach(( k, v) -> {
                    if( val.get( k ) == null ) val.put( k, new SummaryView.dantou() );
                    val.get( k ).jiaoyi = v;
                });
            }
           else{
                //掉落
                Map< String, Long > diaoluo = ServiceManager.getSqlService().queryMapStringLong( "select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H'), sum(item_count) " +
                        " from t_fish_item " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "  and appid = 'b477860b9724470cba748d4bae67beee' and item_id in (20017,11019) and item_count > 0 group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H');", c );
                Map< String, Long > shiyong = ServiceManager.getSqlService().queryMapStringLong( "select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H'), sum(item_count) " +
                        " from t_fish_item " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and appid = 'b477860b9724470cba748d4bae67beee'  and item_id in (20017,11019) and item_count < 0 group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H');", c );

                Map< String, Long > huodong = ServiceManager.getSqlService().queryMapStringLong( "select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H'),sum(getValue(get_info,'20017')) " +
                        " from t_newactivity " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and appid = 'b477860b9724470cba748d4bae67beee' group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H');", c );

                Map< String, Long > baoxiang = ServiceManager.getSqlService().queryMapStringLong( "select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H'),sum( coalesce( getValue2(get_info,\"20017\"), 0 ) ) " +
                        " from t_fish_get_ietms " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and appid = 'b477860b9724470cba748d4bae67beee' and cost_item_id in (20033,20035,20037) group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H');", c );
                Map< String, Long > chongzhi = ServiceManager.getSqlService().queryMapStringLong( "select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H'),sum(money2) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        "  and appid = 'b477860b9724470cba748d4bae67beee' and user_id not in (select distinct user_id from t_install where from_where = 3311 ) group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H');", c );
                Map< String, Long > jiaoyi = ServiceManager.getSqlService().queryMapStringLong( "select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H'),sum(item_count) " +
                        " from t_important_props " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and send_appid = 'b477860b9724470cba748d4bae67beee' and item_id in (20017,11019) group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H');", c );
                diaoluo.forEach(( k, v) -> {
                    if( val.get( k ) == null ) val.put( k, new SummaryView.dantou() );
                    val.get( k ).diaoluo = v;
                });
                shiyong.forEach(( k, v) -> {
                    if( val.get( k ) == null ) val.put( k, new SummaryView.dantou() );
                    val.get( k ).zongshiyong = v;
                });
                baoxiang.forEach(( k, v) -> {
                    if( val.get( k ) == null ) val.put( k, new SummaryView.dantou() );
                    val.get( k ).baoxiang = v;
                });
                chongzhi.forEach(( k, v) -> {
                    if( val.get( k ) == null ) val.put( k, new SummaryView.dantou() );
                    val.get( k ).chongzhi = v;
                    List<String> huodong2 = ServiceManager.getSqlService().queryListString( "select get_info " +
                            " from t_newactivity " +
                            " where " + beg + " <= timestamp and timestamp < " + end + "  and appid = 'b477860b9724470cba748d4bae67beee' and FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H') = '" + k + "';", c );
                    Map< Long, Long > getMap = new HashMap<>();
                    huodong2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );

                    List<String> sheshou = ServiceManager.getSqlService().queryListString( "select get_info " +
                            " from t_fish_get_ietms " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and appid = 'b477860b9724470cba748d4bae67beee' and cost_item_id in (20043,20042,20041,20040) and FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d %H') = '" + k + "';", c );
                    Map< Long, Long > sheshougetMap = new HashMap<>();
                    sheshou.forEach( info -> mergeMap( splitMap( info, ";", ":" ), sheshougetMap ) );

                    val.get( k ).huodong = getLong( getMap,20017 ) + getLong(sheshougetMap , 20017);
                });
                jiaoyi.forEach(( k, v) -> {
                    if( val.get( k ) == null ) val.put( k, new SummaryView.dantou() );
                    val.get( k ).jiaoyi = v;
                });
            }

            List< SummaryView.dantou > userList = new ArrayList<>();
            SummaryView.dantou sum = new SummaryView.dantou();
            val.forEach( ( k, v ) -> {
                sum.begin = "汇总";
                sum.diaoluo += val.get(k).diaoluo;
                sum.zongshiyong += val.get(k).zongshiyong;
                sum.huodong += val.get(k).huodong;
                sum.jiaoyi += val.get(k).jiaoyi;
                sum.chongzhi += val.get(k).chongzhi;
                sum.baoxiang += val.get(k).baoxiang;
                sum.chazhi += val.get(k).diaoluo + val.get(k).huodong + val.get(k).baoxiang + val.get(k).zongshiyong;
            } );
            val.forEach( ( k, v ) -> {
                val.get( k ).begin = k + "点";
                val.get( k ).chazhi = val.get( k ).diaoluo + val.get( k ).huodong + val.get( k ).baoxiang + val.get( k ).zongshiyong;
                userList.add(v);
            } );
            userList.add(sum);
            Collections.reverse(userList);
            return userList;
        } );
    }

    public void queryDanTouHuiZong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_SUMMARY_DANTOU, getUserDanTouData( req, resp ),
                DanTouHuiZongTableHead, (info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.diaoluo + "</td>" );
                    pw.write( "<td>" + info.huodong + "</td>" );
                    pw.write( "<td>" + info.baoxiang + "</td>" );
                    pw.write( "<td>" + info.zongshiyong + "</td>" );
                    pw.write( "<td>" + info.chazhi + "</td>" );
                    pw.write( "<td>" + com.hoolai.ccgames.bi.protocol.Currency.format( info.chongzhi ) + "</td>" );
                    pw.write( "<td>" + info.jiaoyi + "</td>" );
                }, null );
    }

    private List< SummaryView.zichong > getZiChongData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.zichong val = new SummaryView.zichong();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.qudaoHao = 3311;
            val.money = ServiceManager.getSqlService().queryLong( "select sum(money2) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and user_id in (select distinct user_id from t_install where from_where = 3311);", c );
            val.ppt = ServiceManager.getSqlService().queryMapLongLong( "select user_id,sum(chips) " +
                    " from t_ppt_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and user_id in (select distinct user_id from t_install where from_where = 3311) group by user_id;", c );
            val.mtl = ServiceManager.getSqlService().queryMapLongLong( "select user_id,sum( bet_gold + bet_nb_gold + bet_xnb_gold ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and user_id in (select distinct user_id from t_install where from_where = 3311) group by user_id;", c );
            val.zfb = ServiceManager.getSqlService().queryMapLongLong( "select user_id,sum(chips) " +
                    " from t_zfb_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and user_id in (select distinct user_id from t_install where from_where = 3311) group by user_id;", c );
            val.buyu = ServiceManager.getSqlService().queryMapLongLong( "select user_id,sum(gold) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and user_id in (select distinct user_id from t_install where from_where = 3311) group by user_id;", c );
            val.zengsong = ServiceManager.getSqlService().queryMapLongLong( "select item_id,sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and send_user_id in (select distinct user_id from t_install where from_where = 3311) group by item_id;", c );
            return val;

        } );
    }

    public void queryZiChong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_SUMMARY_ZICHONG, getZiChongData( req, resp ),
                ZiChongTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.qudaoHao + "</td>" );
                    pw.write( "<td>" + Currency.format(info.money) + "</td>" );
                    pw.write( "<td>" );
                    info.buyu.forEach( ( user_id, Count ) -> {
                        if(Count == 0 ) return;
                        pw.write("用户id:" + user_id + "    下注金额: " +  Count + "<br>" );
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" );
                    info.zfb.forEach( ( user_id, Count ) -> {
                        pw.write("用户id:" + user_id + "    下注金额: " +  Count + "<br>" );
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" );
                    info.mtl.forEach( ( user_id, Count ) -> {
                        pw.write("用户id:" + user_id + "    下注金额: " +  Count + "<br>" );
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" );
                    info.ppt.forEach( ( user_id, Count ) -> {
                        pw.write("用户id:" + user_id + "    下注金额: " +  Count + "<br>" );
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" );
                    info.zengsong.forEach( ( itemId, Count ) -> {
                        pw.write("道具id:" + ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString())) + "    赠送数量: " +  Count + "<br>" );
                    } );
                    pw.write( "</td>" );
                } );
    }

    private List< SummaryView.zichong > getZiChongXiangXiData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.zichong val = new SummaryView.zichong();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.zengsong = ServiceManager.getSqlService().queryMapLongLong( "select user_id,sum(money2) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and user_id in (select distinct user_id from t_install where from_where = 3311) group by user_id;", c );
            return val;
        } );
    }

    public void queryZiChongXiangXi( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_SUMMARY_ZICHONG, getZiChongXiangXiData( req, resp ),
                ZiChongXiangXiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td> 用户昵称:  " + info.name + "</td>" );
                    pw.write( "<td>" );
                    info.zengsong.forEach( ( user_id, Count ) -> {
                        pw.write("用户id:" + user_id  + "       用户昵称： " + UserSdk.getUserName(user_id) + "       充值金额: " +  Currency.format(Count) + "<br>" );
                    } );
                    pw.write( "</td>" );
                } );
    }

    private List< SummaryView.zhoubao > getZhouBaoData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        String qudao = req.getParameter( "qudao" );
        return getDataList( req, resp, ( beg, end, c ) -> {
            SummaryView.zhoubao val = new SummaryView.zhoubao();
            if (qudao.equals("budai")) {
                val.begin = TimeUtil.ymdFormat().format(beg);
                val.end = TimeUtil.ymdFormat().format(end);
                val.shouru = ServiceManager.getSqlService().queryLong("select sum(money2) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee';", c);
                val.huoyuezhanghao = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_possession " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee';", c);
                val.fufeizhanghao = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee';", c);
                val.huoyuelaoyonghu = ServiceManager.getSqlService().queryLong("select count(a.user_id) from ( select user_id,count(id) as ct from t_dau " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where from_where = 3311) and user_id not in (select distinct user_id from t_install where timestamp > " + beg + " ) and appid != 'b477860b9724470cba748d4bae67beee' group by user_id ) as a where a.ct >= 2 ;", c);
                val.huoyuelaoyonghufufei = ServiceManager.getSqlService().queryLong("select count(a.user_id) from (select user_id,count(id) as ct from t_dau " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where from_where = 3311) and user_id not in (select distinct user_id from t_install where timestamp > " + beg + " ) and appid != 'b477860b9724470cba748d4bae67beee' group by user_id ) as a  where a.ct >= 2 " +
                        " and a.user_id in (select distinct user_id from t_pay where " + beg + " <= timestamp and timestamp < " + end + ");", c);
                val.huoyuelaoyonghulishi = ServiceManager.getSqlService().queryLong("select count(a.user_id) from (select user_id,count(id) as ct from t_dau " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where from_where = 3311) and user_id not in (select distinct user_id from t_install where timestamp > " + beg + " ) and appid != 'b477860b9724470cba748d4bae67beee' group by user_id ) as a  where a.ct >= 2 " +
                        " and a.user_id in (select distinct user_id from t_pay );", c);

                val.xinzeng = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_install " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee';", c);
                val.rijunzonghuoyue = ServiceManager.getSqlService().queryLong("select sum(a.ct)/7 from (select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d'),count(distinct user_id) as ct  " +
                        " from t_possession " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and appid != 'b477860b9724470cba748d4bae67beee' and user_id not in (select distinct user_id from t_install where from_where = 3311)  group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d'))as a ;", c);
                val.rijunchixuhuoyue = ServiceManager.getSqlService().queryLong("select count(distinct a.user_id) from (select user_id,count(id) as ct  " +
                        " from t_possession " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and appid != 'b477860b9724470cba748d4bae67beee' and user_id not in (select distinct user_id from t_install where from_where = 3311)  group by user_id )as a where a.ct >= 7;", c);
                val.IOSshouru = ServiceManager.getSqlService().queryLong("select sum(money2) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee' and appid in (1239313319,1251807724);", c);
                val.ANDshouru = ServiceManager.getSqlService().queryLong("select sum(money2) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where from_where = 3311) and appid != 'b477860b9724470cba748d4bae67beee' and appid not in (1239313319,1251807724);", c);
                val.IOSxinzeng = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_install " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id != 3311 and appid != 'b477860b9724470cba748d4bae67beee' and appid in (1239313319,1251807724);", c);
                val.ANDxinzeng = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_install " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id != 3311 and appid != 'b477860b9724470cba748d4bae67beee' and appid not in (1239313319,1251807724);", c);

                val.LIANYUNshouru = ServiceManager.getSqlService().queryLong("select sum(money2) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + "  and appid != 'b477860b9724470cba748d4bae67beee' and user_id in (select distinct user_id from t_install where from_where in (2516,2513,2512,2511,2506,2498,2496,2492,2465,2545,585901,1106207849,9889988,2887,2973,2988,3285));", c);
                val.GUANWANGshouru = ServiceManager.getSqlService().queryLong("select sum(money2) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + "  and appid != 'b477860b9724470cba748d4bae67beee' and appid not in (1239313319,1251807724) and user_id not in (select distinct user_id from t_install where from_where in (2516,2513,2512,2511,2506,2498,2496,2492,2465,2545,585901,1106207849,9889988,2887,2973,2988,3285,3311));", c);
                val.LIANYUNxinzeng = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_install " +
                        " where " + beg + " <= timestamp and timestamp < " + end + "  and appid != 'b477860b9724470cba748d4bae67beee' and from_where in (2516,2513,2512,2511,2506,2498,2496,2492,2465,2545,585901,1106207849,9889988,2887,2973,2988,3285);", c);
                val.GUANWANGxinzeng = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_install " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and appid != 'b477860b9724470cba748d4bae67beee' and appid not in (1239313319,1251807724) and from_where not in (2516,2513,2512,2511,2506,2498,2496,2492,2465,2545,585901,1106207849,9889988,2887,2973,2988,3285,3311);", c);


            }if (qudao.equals("dai")) {
                val.begin = TimeUtil.ymdFormat().format(beg);
                val.end = TimeUtil.ymdFormat().format(end);
                val.shouru = ServiceManager.getSqlService().queryLong("select sum(money2) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and appid != 'b477860b9724470cba748d4bae67beee';", c);
                val.huoyuezhanghao = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_possession " +
                        " where " + beg + " <= timestamp and timestamp < " + end + "  and appid != 'b477860b9724470cba748d4bae67beee';", c);
                val.fufeizhanghao = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and appid != 'b477860b9724470cba748d4bae67beee';", c);
                val.huoyuelaoyonghu = ServiceManager.getSqlService().queryLong("select count(a.user_id) from ( select user_id,count(id) as ct from t_dau " +
                        " where " + beg + " <= timestamp and timestamp < " + end + "  and user_id not in (select distinct user_id from t_install where timestamp > " + beg + " ) and appid != 'b477860b9724470cba748d4bae67beee' group by user_id ) as a where a.ct >= 2 ;", c);
                val.huoyuelaoyonghufufei = ServiceManager.getSqlService().queryLong("select count(a.user_id) from (select user_id,count(id) as ct from t_dau " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where timestamp > " + beg + " ) and appid != 'b477860b9724470cba748d4bae67beee' group by user_id ) as a  where a.ct >= 2 " +
                        " and a.user_id in (select distinct user_id from t_pay where " + beg + " <= timestamp and timestamp < " + end + ");", c);
                val.huoyuelaoyonghulishi = ServiceManager.getSqlService().queryLong("select count(a.user_id) from (select user_id,count(id) as ct from t_dau " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where timestamp > " + beg + " ) and appid != 'b477860b9724470cba748d4bae67beee' group by user_id ) as a  where a.ct >= 2 " +
                        " and a.user_id in (select distinct user_id from t_pay );", c);

                val.xinzeng = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_install " +
                        " where " + beg + " <= timestamp and timestamp < " + end + "  and appid != 'b477860b9724470cba748d4bae67beee';", c);
                val.rijunzonghuoyue = ServiceManager.getSqlService().queryLong("select sum(a.ct)/7 from (select FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d'),count(distinct user_id) as ct  " +
                        " from t_possession " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and appid != 'b477860b9724470cba748d4bae67beee' group by FROM_UNIXTIME(timestamp/ 1000,'%y-%m-%d'))as a ;", c);
                val.rijunchixuhuoyue = ServiceManager.getSqlService().queryLong("select count(distinct a.user_id) from (select user_id,count(id) as ct  " +
                        " from t_possession " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and appid != 'b477860b9724470cba748d4bae67beee'  group by user_id )as a where a.ct >= 7;", c);
                val.IOSshouru = ServiceManager.getSqlService().queryLong("select sum(money2) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + "  and appid != 'b477860b9724470cba748d4bae67beee' and appid in (1239313319,1251807724);", c);
                val.ANDshouru = ServiceManager.getSqlService().queryLong("select sum(money2) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + "  and appid != 'b477860b9724470cba748d4bae67beee' and appid not in (1239313319,1251807724);", c);
                val.IOSxinzeng = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_install " +
                        " where " + beg + " <= timestamp and timestamp < " + end + "  and appid != 'b477860b9724470cba748d4bae67beee' and appid in (1239313319,1251807724);", c);
                val.ANDxinzeng = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_install " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and appid != 'b477860b9724470cba748d4bae67beee' and appid not in (1239313319,1251807724);", c);

                val.LIANYUNshouru = ServiceManager.getSqlService().queryLong("select sum(money2) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + "  and appid != 'b477860b9724470cba748d4bae67beee' and user_id in (select distinct user_id from t_install where from_where in (2516,2513,2512,2511,2506,2498,2496,2492,2465,2545,585901,1106207849,9889988,2887,2973,2988,3285));", c);
                val.GUANWANGshouru = ServiceManager.getSqlService().queryLong("select sum(money2) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + "  and appid != 'b477860b9724470cba748d4bae67beee' and appid not in (1239313319,1251807724) and user_id not in (select distinct user_id from t_install where from_where in (2516,2513,2512,2511,2506,2498,2496,2492,2465,2545,585901,1106207849,9889988,2887,2973,2988,3285));", c);
                val.LIANYUNxinzeng = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_install " +
                        " where " + beg + " <= timestamp and timestamp < " + end + "  and appid != 'b477860b9724470cba748d4bae67beee' and from_where in (2516,2513,2512,2511,2506,2498,2496,2492,2465,2545,585901,1106207849,9889988,2887,2973,2988,3285);", c);
                val.GUANWANGxinzeng = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_install " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and appid != 'b477860b9724470cba748d4bae67beee' and appid not in (1239313319,1251807724) and from_where not in (2516,2513,2512,2511,2506,2498,2496,2492,2465,2545,585901,1106207849,9889988,2887,2973,2988,3285);", c);

            }

            return val;

        } );
    }

    public void queryZhouBao( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        List<SummaryView.zhoubao > data  = getZhouBaoData( req, resp );
        queryMultiList( req, resp,
                CommandList.GET_SUMMARY_ZHOUBAO, data,
                zhoubaoTableHead1, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "---" + info.end  + "</td>" );
                    pw.write( "<td>" + Currency.format(info.shouru)  + "</td>" );
                    pw.write( "<td>" + info.huoyuezhanghao  + "</td>" );
                    pw.write( "<td>" + info.fufeizhanghao  + "</td>" );
                    pw.write( "<td>" + formatRatio( (double)info.fufeizhanghao / info.huoyuezhanghao  )+ "</td>" );
                    pw.write( "<td>" + info.huoyuelaoyonghu  + "</td>" );
                    pw.write( "<td>" + info.huoyuelaoyonghufufei  + "</td>" );
                    pw.write( "<td>" + formatRatio( (double)info.huoyuelaoyonghufufei / info.huoyuelaoyonghu  )+ "</td>" );
                    pw.write( "<td>" + info.huoyuelaoyonghulishi  + "</td>" );
                    pw.write( "<td>" + formatRatio( (double)info.huoyuelaoyonghulishi /info.huoyuelaoyonghu)  + "</td>" );
                    pw.write( "<td>" + Currency.format(info.shouru / info.huoyuezhanghao)  + "</td>" );
                    pw.write( "<td>" + Currency.format(info.shouru / info.fufeizhanghao) + "</td>" );
                    pw.write( "<td>" + info.xinzeng + "</td>" );
                    pw.write( "<td>" + info.rijunzonghuoyue + "</td>" );
                    pw.write( "<td>" + info.rijunchixuhuoyue + "</td>" );
                },data,
                zhoubaoTableHead2, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "---" + info.end  + "</td>" );
                    pw.write( "<td>" + Currency.format(info.IOSshouru)  + "</td>" );
                    pw.write( "<td>" + formatRatio( (double)info.IOSshouru/info.shouru) + "</td>" );
                    pw.write( "<td>" + Currency.format(info.ANDshouru) + "</td>" );
                    pw.write( "<td>" + formatRatio((double)info.ANDshouru/info.shouru) + "</td>" );
                    pw.write( "<td>" + Currency.format(info.LIANYUNshouru) + "</td>" );
                    pw.write( "<td>" + formatRatio((double)info.LIANYUNshouru/info.shouru) + "</td>" );
                    pw.write( "<td>" + Currency.format(info.GUANWANGshouru) + "</td>" );
                    pw.write( "<td>" + formatRatio((double)info.GUANWANGshouru/info.shouru) + "</td>" );
                },data,
                zhoubaoTableHead3, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "---" + info.end  + "</td>" );
                    pw.write( "<td>" + Currency.format(info.shouru)  + "</td>" );
                    pw.write( "<td>" + info.IOSxinzeng + "</td>" );
                    pw.write( "<td>" + Currency.format(info.IOSshouru) + "</td>" );
                    pw.write( "<td>" + info.ANDxinzeng + "</td>" );
                    pw.write( "<td>" + Currency.format(info.ANDshouru) + "</td>" );
                    pw.write( "<td>" + info.LIANYUNxinzeng + "</td>" );
                    pw.write( "<td>" + Currency.format(info.LIANYUNshouru) + "</td>" );
                    pw.write( "<td>" + info.GUANWANGxinzeng + "</td>" );
                    pw.write( "<td>" + Currency.format(info.GUANWANGshouru) + "</td>" );
                },null );
    }


}
