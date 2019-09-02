package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.*;
import com.hoolai.ccgames.bi.protocol.Currency;
import com.hoolai.ccgames.bifront.net.BiClient;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.FishView;
import com.hoolai.ccgames.bifront.vo.baseView;
import com.hoolai.centersdk.item.ItemManager;
import com.hoolai.centersdk.sdk.UserSdk;
import org.springframework.stereotype.Service;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service( "fishMBAction" )
public class FishMBAction extends BaseAction {


    public static int user_install_grants       = 1; //用户注册 发放初始金币
    public static int login_grants              = 2; //用户登录奖励（签到奖励）
    public static int login_leiji_grants        = 3; //用户累计登录奖励
    public static int broke_grants              = 4; //用户破产救助 好运基金
    public static int user_up_grants            = 5; //用户人物升级奖励
    public static int gun_up_grants             = 6; //用户炮升级奖励
    public static int vip_up_grants             = 7; //用户VIP升级奖励
    public static int red_grants                = 8; //在线红包奖励
    public static int day_task_grants           = 9; //日任务奖励
    public static int week_task_grants          = 10; //周任务奖励
    public static int vip_login_give_grants     = 11; //VIP登录筹码不够时 自动补齐
    public static int pay_diamond_grants        = 12; //用户购买钻石时给玩家个人奖池加钱
    public static int pay_gold_grants           = 13; //玩家购买金币是 可能给玩家奖励
    public static int user_jackpot_grants       = 14; //用户个人奖池变动
    public static int captain_grants            = 15; //用户船长奖励（月卡礼包）
    public static int day_act_grants            = 16; //日活跃度
    public static int week_act_grants           = 17; //周活跃度
    public static int firstRecharge_pay_grants  = 18; //首充
    public static int buy_item_add_diamond      = 19; //购买道具加送钻石
    public static int qipao_fish_get            = 20; //气泡捕鱼转盘获得
    public static int caiyu_fish_get            = 21; //捕鱼猜鱼获得
    public static int week_grants               = 22; //周卡
    public static int phone_grants              = 23; //绑定手机号
    public static int pay_huikui_grants         = 24; //充值
    public static int leijichongzhi_grants      = 25; //累计充值
    public static int world_boss_rank_grants    = 26; //世界boos排行奖励
    public static int world_boss_die_grants     = 27; //世界boos最后一击
    public static int jingji_day_rank_grants    = 28; //竞技场日排行
    public static int jingji_week_rank_grants   = 29; //竞技场周排行
    public static int jingji_jifen_grants       = 30; //竞技场积分奖励
    public static int jingji_task_grants        = 31; //竞技场任务奖励
    public static int update_grants             = 32; //更新奖励
    public static int cdkey_grants              = 33; //使用cdkey
    public static int beta_lottery_grants       = 34; //公测抽奖
    public static int poker_free_grants         = 35; //扑克场免费奖励
    public static int poker_dantou_grants       = 36; //扑克场弹头掉落
    public static int tuiguang_bangding_grants  = 37; //推广员绑定
    public static int tuiguang_jifen_grants     = 38; //推广员积分

    //t_fish_useraction
    public static int fish_in   = 1;
    public static int fish_out  = 2;
    public static int zfb_in    = 3;
    public static int zfb_out   = 4;
    public static int mtl_in    = 5;
    public static int mtl_out   = 6;
    public static int brdz_in   = 7;
    public static int brdz_out  = 8;

    public static int appid     = 264 ;

    private static List< String > payDetailTableHead    = Arrays.asList( "时间",
            "玩家ID", "玩家昵称", "总付费" );
    private static List< String > userDanTouTableHead   = Arrays.asList( "时间",
            "玩家ID", "玩家昵称", "线上充值" , "掉落金", "接收金", "赠送金", "使用金" );
    private static List< String > basicTableHead        = Arrays.asList( "日期","总人数", "总投注","总返奖","幸运彩池","GM修改幸运彩池","个人奖池","大R奖励","GM修改个人彩池","新手保护","其他途径","总抽水");
    private static List< String > goldOutTableHead1     = Arrays.asList( "日期","新进", "登陆","任务","红包","升级","破产","绑定手机","更新","推广",
                "cd-key","竞技","boss","扑克场","累计充值","首充","大师宝箱","周卡月卡","VIP每日补助","vip升级","总支出","抽水(捕鱼总投注-总返奖)","净抽水");
    private static List< String > goldOutTableHead2     = Arrays.asList( "日期","兑换","夺宝","万圣节","大富翁","砸蛋","充值奖励","大R补助","商城购买金币产出","每日特惠产出");
    private static List< String > NewUserTableHead      = Arrays.asList( "日期","新注册人数", "新手初始游戏币支出","新手保护人数", "新手保护次数","新手保护支出","新手支出（总额）");
    private static List< String > LoginTableHead        = Arrays.asList( "日期","登陆人数","领取人数","vip登陆奖励","一般玩家登陆奖励","累计登陆领取人数","累计登陆产出", "总支出");
    private static List< String > BrokeTableHead        = Arrays.asList( "日期","领取人数","vip领取人数","vip领取次数","vip领取总额","一般玩家领取人数","一般玩家领取次数","一般玩家领取总额","登录奖励总支出");
    private static List< String > UpTableHead           = Arrays.asList( "日期","人物升级人数","人物升级次数","人物升级游戏币支出","道具支出","炮升级人数","炮升级次数","炮升级金币支出","道具支出","升级支出（总）");
    private static List< String > RedTableHead          = Arrays.asList( "日期","免费人数","免费次数","免费总金额","vip人数","vip次数","vip总金额","总金额");
    private static List< String > TaskTableHead         = Arrays.asList("日期","日任务人数","日任务次数","日任务支出","日活跃人数","日活跃支出","周任务人数","周任务次数","周任务支出","周活跃人数","周活跃支出","任务活跃支出（总）");
    private static List< String > VipOutTableHead       = Arrays.asList("日期","VIP登陆奖励","VIP红包奖励","VIP首次登陆补足支出","升级道具支出","VIP特权支出总额");
    private static List< String > PayOutTableHead       = Arrays.asList("日期","人数","充值总额","购买金币获得","购买钻石获得","个人奖池加成","充值支出");
    private static List< String > ShopTableHead         = Arrays.asList("日期","道具名","卖出数量","出售总金额","今日收入","占总收入比例");
    private static List< String > UserItemsTableHead    = Arrays.asList("日期","用户id","用户名","道具名","道具数量");
    private static List< String > BossTableHead         = Arrays.asList("场次","日期","参与人数","抽水（不计奖励）","打的伤害","奖励","总抽水");
    private static List< String > captainOutTableHead   = Arrays.asList("日期","月卡开通人数","月卡领取人数","月卡金币支出","月卡道具支出","周卡开通人数","周卡领取人数","周卡金币支出","周卡道具支出");

    private static List< String > LouDouTableHead       = Arrays.asList("日期","注册","签到","中发白","摩天轮","初级场","中级场","高级场","L2","L3","L4","L5","L6","L7","L8","L9","L10","L11","L12","L13","L14+");

    private static List< String > GunTableHead          = Arrays.asList("2","5","10","15","20","30","50","75","100","150","200","300","400","500",
            "600","700","800","900","1000","1500","2000","2500","3000","3500","4000","4500","5000");
    private static List< String > DanTouTableHead       = Arrays.asList("日期","青弹使用人数","青弹使用次数","青弹掉落","银弹使用人数","银弹使用次数","银弹掉落","金弹使用人数","金弹使用次数","金弹掉落");
    private static List< String > DanTouXiangXiTableHead = Arrays.asList("日期","中级青弹","中级白银弹","中级黄金弹","高级青弹","高级白银弹","高级黄金弹","土豪青弹","土豪白银","土豪黄金");

    private static List< String > bigRTableHead1 = Arrays.asList( "时间", "补助人数","补助次数","补助总金额","比例");
    private static List< String > bigRTableHead2 = Arrays.asList( "时间", "用户ID","用户昵称","补助次数","补助金额","今日充值");
    private static List< String > rankTableHead = Arrays.asList( "用户id", "用户昵称","用户打炮","用户获取","幸运彩池获取","新手补助","用户净收入");

    private static List< String > CCCrankTableHead = Arrays.asList( "用户id", "用户昵称","总猜次数","总金额","积分产出","当天兑换消耗","兑换宝箱个数","抽水","开弹头数");
    private static List< String > JichuTableHead = Arrays.asList( "日期", "初级人数", "初级投注","初级返奖", "初级抽水", "中级人数", "中级投注",
            "中级返奖","中级抽水", "高级人数", "高级投注","高级返奖", "高级抽水", "土豪人数", "土豪投注","土豪返奖", "土豪抽水", "竞技人数", "竞技投注","竞技返奖", "竞技抽水","总抽水");
    private static List< String > FanBeiTableHead = Arrays.asList( "日期", "初级人数", "初级投注","初级返奖", "初级抽水", "中级人数", "中级投注",
            "中级返奖","中级抽水", "高级人数", "高级投注","高级返奖", "高级抽水", "土豪投注","土豪返奖", "土豪抽水","总抽水");

    private static List< String > DanTouFishTableHead = Arrays.asList( "鱼倍数","弹头鱼出现次数", "击杀次数" , "金弹头掉落", "银弹头掉落");


    private static List< String > ItemTableHead = Arrays.asList(  "日期", "金币产出","金币消耗","贝壳产出","贝壳使用","话费点产出","上庄卡","牛币","兑换券产出","钻石产出",
            "钻石使用","冰冻产出","瞄准产出","加速产出","分裂产出","冰冻使用","瞄准使用","加速使用","分裂使用");
    private static List< String > OnlineTableHead = Arrays.asList(  "日期", "场次","1分钟","2分钟","3分钟","5分钟","7分钟","10分钟","15分钟","20分钟","30分钟","60分钟","120分钟+");
    private static List< String > JingJiTableHead = Arrays.asList(  "日期", "参与次数","参与人数","门票","1500-2000人","2000-2500人","2500-3000人","3000以上","参加1次","2次","3-5次","5次以上","抽水","奖励","钻石","总抽水");
    private static List< String > HaveGoldTableHead = Arrays.asList(  "日期", "<1万","1万-5万","5万-10万","10万-20万","20万-50万","50万-100万","100万+");
    private static List< String > SendTableHead = Arrays.asList(  "日期", "青铜赠送人数","青铜赠送数量","白银赠送人数","白银赠送数量","黄金赠送人数","黄金赠送数量");
    private static List< String > ChiBangTableHead = Arrays.asList(  "日期", "天使之翼","恶魔之翼","妖王之翼","赤炎之翼","王者之翼");
    private static List< String > PaoHuoYueTableHead = Arrays.asList(  "日期", "活跃人数","100","150","200","300","400","500","600","700","800","900","1000","1500","2000","2500","3000","3500","4000","4500","5000","6000","7000","8000","9000","10000");
    private static List< String > DaShiTableHead = Arrays.asList(  "日期", "参与人数","参与次数","使用","获得","抽水","大师门票产出","积分宝箱兑换次数","积分宝箱使用次数","积分宝箱使用产出");
    private static List< String > caicaicaiTableHead = Arrays.asList(  "日期", "人数","次数","金额","积分产出","抽水");
    private static List< String > zhuanpanTableHead = Arrays.asList(  "日期", "人数","次数","道具产出");
    private static List< String > jiangquanTableHead = Arrays.asList(  "日期", "初级人数","初级次数","初级产出个数", "中级人数","中级次数","中级产出个数", "高级人数","高级次数","高级产出个数"
            , "土豪人数","土豪次数","土豪产出个数");
    private static List< String > fanbeiTableHead = Arrays.asList(  "日期", "初级人数","初级下注","初级支出","初级抽水", "中级人数","中级下注","中级支出","中级抽水",
            "高级人数","高级下注","高级支出","高级抽水","总抽水");
    private static List< String > duihuanTableHead = Arrays.asList(  "日期", "30话费","50话费","100话费","青弹","银弹","金弹","钻石","金币","炮");
    private static List< String > handleTableHead = Arrays.asList(  "日期", "个人资料","商城","背包","消息","任务","兑换","排行榜","设置","炮台","签到","月卡","VIP特权","快速开始","一元抢购","聊天","娱乐城","客服","翅膀","许愿","福利","翅膀图鉴");

    private static List< String > PokerTableHead = Arrays.asList(  "日期", "免费人数","免费次数","初级人数","初级次数","中级人数","中级次数","高级人数","高级次数","免费场金币产出","高级场boss击杀次数");

    private static List< String > poolMoveTableHead = Arrays.asList(  "日期","场次", "个银向个奖人数","个银向个奖次数","个银向个奖金额","幸运向个奖人数","幸运向个奖次数","幸运向个奖金额","各个水线触发次数");

    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_MB ).forward( req, resp );
    }

    public void getDanTouFishPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_DANTOUFISH ).forward( req, resp );
    }

    public void getBasicPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_BASIC_MB ).forward( req, resp );
    }
    public void getduihuanPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_DUIHUAN_MB ).forward( req, resp );
    }
    public void getUserDanTouPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_USER_DANTOU ).forward( req, resp );
    }

    public void gethandlePage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_HANDLE_MB ).forward( req, resp );
    }

    public void getzhuanpanPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_ZHUANPAN_MB ).forward( req, resp );
    }
    public void getjiangquanPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_JIANGQUAN_MB ).forward( req, resp );
    }
    public void getfanbeiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_FANBEI_MB).forward( req, resp );
    }
    public void getItemPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_ITEM_MB ).forward( req, resp );
    }
    public void getDaShiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_DASHI_MB ).forward( req, resp );
    }
    public void getNewUserPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_NEW_USER_MB ).forward( req, resp );
    }
    public void getchongzhiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_CHONGZHI_MB ).forward( req, resp );
    }
    public void getPokerPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_POKER ).forward( req, resp );
    }
    public void getxiazaiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_XIAZAI ).forward( req, resp );
    }

    public void getLoginPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_LOGIN_MB ).forward( req, resp );
    }
    public void getCCCRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_CAICAICAI_RANK ).forward( req, resp );
    }
    public void getbigRPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher(Constants.URL_FISH_BIG_R).forward(req, resp);
    }
    public void getTaskPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher(Constants.URL_FISH_TASK_MB).forward(req, resp);
    }
    public void getBrokePage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_BROKE_MB ).forward( req, resp );
    }

    public void getUPPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_UP_LEVEL_MB ).forward( req, resp );
    }
    public void getRedPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_RED_MB ).forward( req, resp );
    }
    public void getVIPPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_VIP_MB ).forward( req, resp );
    }
    public void getCaptainPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_CAPTAIN_MB ).forward( req, resp );
    }
    public void getBossPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_BOSS_MB ).forward( req, resp );
    }
    public void getJingJiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_JINGJI_MB ).forward( req, resp );
    }
    public void getDanTouPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_DANTOU_MB ).forward( req, resp );
    }
    public void getLouDouPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_LOUDOU_MB ).forward( req, resp );
    }
    public void getShopPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_SHOP_MB ).forward( req, resp );
    }

    public void getUserItemPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_USER_ITEM_MB ).forward( req, resp );
    }

    public void getPoolMovePage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_POOL_MOVE ).forward( req, resp );
    }

    public void getGameGoldPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_GAMEGOLD_MB ).forward( req, resp );
    }
    public void getFishOnlinePage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_ONLINE_MB ).forward( req, resp );
    }
    public void getHaveGoldPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_HAVE_GOLD_MB ).forward( req, resp );
    }
    public void getSendPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_SEND_MB ).forward( req, resp );
    }
    public void getChiBangPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_CHIBANG_MB ).forward( req, resp );
    }
    public void getPaoHuoYuePage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_PAOHUOYUE_MB ).forward( req, resp );
    }
    public void getRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_RANK_MB ).forward( req, resp );
    }
    public void getHuizongPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_HUIZONG_MB ).forward( req, resp );
    }
    public void getcaicaicaiPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_CAICAICAI ).forward( req, resp );
    }

    public long People(long beg, long end, long type, BiClient c )
    {
        long people = ServiceManager.getSqlService().queryLong( "select count(distinct user_id) " +
                " from t_fish_mb_grants " +
                " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + type + " and is_mobile = 'Y' and vip_level = 0;", c );
        return people;
    }
    public long Count(long beg, long end, long type, BiClient c)
    {
        long count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                " from t_fish_mb_grants " +
                " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + type + " and is_mobile = 'Y' and vip_level = 0 ;", c );
        return count;
    }

    public long PeopleVIP(long beg, long end, long type, BiClient c )
    {
        long people = ServiceManager.getSqlService().queryLong( "select count(distinct user_id) " +
                " from t_fish_mb_grants " +
                " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + type + " and is_mobile = 'Y' and vip_level >= 1;", c );
        return people;
    }
    public long CountVIP(long beg, long end, long type, BiClient c)
    {
        long count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                " from t_fish_mb_grants " +
                " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + type + " and is_mobile = 'Y' and vip_level >= 1;", c );
        return count;
    }

    public long SumItem(long beg, long end, long type, long itemId, BiClient c)
    {
        List<String> getinfo = ServiceManager.getSqlService().queryListString( "select bouns " +
                " from t_fish_mb_grants " +
                " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + type +" and is_mobile = 'Y' and vip_level = 0;", c );

        Map< Long, Long > getMap = new HashMap<>();
        getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );

        return getLong(getMap , itemId);
    }

    public long SumItemVip(long beg, long end, long type, long itemId, BiClient c)
    {
        List<String> getinfo = ServiceManager.getSqlService().queryListString( "select bouns " +
                " from t_fish_mb_grants " +
                " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + type +" and is_mobile = 'Y' and vip_level >= 1;", c );

        Map< Long, Long > getMap = new HashMap<>();
        getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );

        return getLong(getMap , itemId);
    }

    public long GetItem(long beg, long end,  long itemId, long type, BiClient c)
    {
        List<String> getinfo = ServiceManager.getSqlService().queryListString( "select bouns " +
                " from t_fish_mb_grants " +
                " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + type +" ;", c );

        Map< Long, Long > getMap = new HashMap<>();
        getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
         long a =    getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000 + getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000 + getLong(getMap , 10507 ) * 10000
                 + getLong(getMap , 20014 ) * 0 +  getLong(getMap , 10512 ) * 1000000 + getLong(getMap , -4 ) * 4500 ;
        return a ;
    }


    private List< FishView.caicaicai > getCaiCaiCaiData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.caicaicai val = new FishView.caicaicai();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.count = ServiceManager.getSqlService().queryLong( "select count(id) from t_newactivity " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 9 ;", c );
            val.people = ServiceManager.getSqlService().queryLong( "select count(distinct user_id) from t_newactivity " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 9 ;", c );
            val.cost = ServiceManager.getSqlService().queryLong( "select sum(cost_count) from t_newactivity " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and  activity_id = 9  and cost_id = -1;", c );

            List<String> chanchu  = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_newactivity " +  " where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 9  ;", c );

            Map< Long, Long > chanchuMap = new HashMap<>();
            chanchu.forEach( info -> mergeMap( splitMap( info, ";", ":" ), chanchuMap ) );
            val.chanchu = getLong(chanchuMap , -12);
            val.choushui = val.cost -   val.chanchu * 500;

            return val;

        } );
    }

    public void queryCaiCaiCai( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getCaiCaiCaiData( req, resp ),
                caicaicaiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.cost / 10000 + "万</td>" );
                    pw.write( "<td>" + info.chanchu + "</td>" );
                    pw.write( "<td>" + info.choushui /10000 + "万</td>" );
                },null );
    }


    private List< FishView.JingJi > getDaShiData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.JingJi val = new FishView.JingJi();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.count = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_useraction " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and type = 1 and level_id = 601;", c );
            val.people = ServiceManager.getSqlService().queryLong( "select count(distinct user_id) from t_fish_paiment " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and level = 601;", c );
            val.cost = ServiceManager.getSqlService().queryLong( "select sum(item_count) from t_fish_item " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and  item_id = 20032", c );
            //宝箱兑换次数
            val.duihuan1 = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_get_ietms " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and  cost_item_id = -12 and cost_item_count = 4000", c );
            val.duihuan2 = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_get_ietms " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and  cost_item_id = -12 and cost_item_count = 10000", c );
            val.duihuan3 = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_get_ietms " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and  cost_item_id = -12 and cost_item_count = 40000", c );
            //宝箱使用次数
            val.cishu1 = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_get_ietms " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and  cost_item_id = 20033;", c );
            val.cishu2 = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_get_ietms " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and  cost_item_id = 20035;", c );
            val.cishu3 = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_get_ietms " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and  cost_item_id = 20037;", c );

            val.choushui = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and level = 601 ;", c );
             val.p1 =  ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and level = 601  ;", c );
            val.p2 = val.choushui - val.p1;

            List<String> baoxiang  = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_fish_get_ietms " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and cost_item_id in (20033,20035,20037)  ;", c );
            Map< Long, Long > baoxiangMap = new HashMap<>();
            baoxiang.forEach( info -> mergeMap( splitMap( info, ";", ":" ), baoxiangMap ) );

            val.daoju = baoxiangMap;

            return val;

        } );
    }

    public void queryDaShi( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getDaShiData( req, resp ),
                DaShiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.choushui + "</td>" );
                    pw.write( "<td>" + info.p1 + "</td>" );
                    pw.write( "<td>" + info.p2  + "</td>" );
                    pw.write( "<td>" + info.cost  + "</td>" );
                    pw.write( "<td>初级：" + info.duihuan1 + "<br>" );
                    pw.write( "中级：" + info.duihuan2 + "<br>" );
                    pw.write( "高级：" + info.duihuan3 + "</td>" );
                    pw.write( "<td>初级：" + info.cishu1 + "<br>" );
                    pw.write( "中级：" + info.cishu2 + "<br>" );
                    pw.write( "高级：" + info.cishu3 + "</td>" );
                    pw.write( "<td>" );
                    info.daoju.forEach( ( itemId, itemCount ) -> {
                        if (itemId == -1 )
                            pw.write("金币:" + itemCount + "<br>" );
                        else
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                    } );
                    pw.write( "</td>" );
                },null );
    }



    private List< FishView.PoolMove > getPoolMoveData(HttpServletRequest req, HttpServletResponse resp , long level)
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.PoolMove val = new FishView.PoolMove();
            val.begin = TimeUtil.ymdFormat().format( beg );

            Map< Long, Long > typePInfo = ServiceManager.getSqlService().queryMapLongLong( "select type,count(distinct user_id) from t_fish_pool_move where " + beg + " <= timestamp and timestamp < " + end  +
                    " and level = " + level + " group by type;", c );
            Map< Long, Long > typeCInfo = ServiceManager.getSqlService().queryMapLongLong( "select type,count(id) from t_fish_pool_move where " + beg + " <= timestamp and timestamp < " + end  +
                    " and level = " + level + " group by type;", c );

            val.yinTojiangP = getLong(typePInfo,1);
            val.xingTojiangP = getLong(typePInfo,2);
            val.yinTojiangC = getLong(typeCInfo,1);
            val.xingTojiangC = getLong(typeCInfo,2);

            Map< Long, Long > shuixianInfo = ServiceManager.getSqlService().queryMapLongLong( "select gold,count(id) from t_fish_pool_move where " + beg + " <= timestamp and timestamp < " + end  +
                    " and level = " + level + " and type = 8 group by gold;", c );
            val.shuixian = shuixianInfo ;

            val.yinTojiangM = ServiceManager.getSqlService().queryLong( "select sum(gold) from t_fish_pool_move where " + beg + " <= timestamp and timestamp < " + end  +
                    " and type = 1 and level = " + level + ";", c );
            val.xingTojiangM = ServiceManager.getSqlService().queryLong( "select sum(gold) from t_fish_pool_move where " + beg + " <= timestamp and timestamp < " + end  +
                    " and type = 2 and level = " + level + ";", c );

            return val;

        } );
    }

    public void queryPoolMove( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_FISH_MB, getPoolMoveData( req, resp, 101 ),
                poolMoveTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>初级场</td>" );
                    pw.write( "<td>" + info.yinTojiangP + "</td>" );
                    pw.write( "<td>" + info.yinTojiangC + "</td>" );
                    pw.write( "<td>" + info.yinTojiangM + "</td>" );
                    pw.write( "<td>" + info.xingTojiangP + "</td>" );
                    pw.write( "<td>" + info.xingTojiangC + "</td>" );
                    pw.write( "<td>" + info.xingTojiangM + "</td>" );
                    pw.write( "<td>" );
                    info.shuixian.forEach( ( k, v ) -> {
                        pw.write("水线:" + k + "    触发次数："+ v +" <br>" );
                    } );
                    pw.write( "</td>" );
                },getPoolMoveData( req, resp, 102 ),
                poolMoveTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>中级场</td>" );
                    pw.write( "<td>" + info.yinTojiangP + "</td>" );
                    pw.write( "<td>" + info.yinTojiangC + "</td>" );
                    pw.write( "<td>" + info.yinTojiangM + "</td>" );
                    pw.write( "<td>" + info.xingTojiangP + "</td>" );
                    pw.write( "<td>" + info.xingTojiangC + "</td>" );
                    pw.write( "<td>" + info.xingTojiangM + "</td>" );
                    pw.write( "<td>" );
                    info.shuixian.forEach( ( k, v ) -> {
                        pw.write("水线:" + k + "    触发次数："+ v +" <br>" );
                    } );
                    pw.write( "</td>" );
                },getPoolMoveData( req, resp, 103 ),
                poolMoveTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>高级场</td>" );
                    pw.write( "<td>" + info.yinTojiangP + "</td>" );
                    pw.write( "<td>" + info.yinTojiangC + "</td>" );
                    pw.write( "<td>" + info.yinTojiangM + "</td>" );
                    pw.write( "<td>" + info.xingTojiangP + "</td>" );
                    pw.write( "<td>" + info.xingTojiangC + "</td>" );
                    pw.write( "<td>" + info.xingTojiangM + "</td>" );
                    pw.write( "<td>" );
                    info.shuixian.forEach( ( k, v ) -> {
                        pw.write("水线:" + k + "    触发次数："+ v +" <br>" );
                    } );
                    pw.write( "</td>" );
                },getPoolMoveData( req, resp, 104 ),
                poolMoveTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>土豪场</td>" );
                    pw.write( "<td>" + info.yinTojiangP + "</td>" );
                    pw.write( "<td>" + info.yinTojiangC + "</td>" );
                    pw.write( "<td>" + info.yinTojiangM + "</td>" );
                    pw.write( "<td>" + info.xingTojiangP + "</td>" );
                    pw.write( "<td>" + info.xingTojiangC + "</td>" );
                    pw.write( "<td>" + info.xingTojiangM + "</td>" );
                    pw.write( "<td>" );
                    info.shuixian.forEach( ( k, v ) -> {
                        pw.write("水线:" + k + "    触发次数："+ v +" <br>" );
                    } );
                    pw.write( "</td>" );
                },null );
    }

    public void downloadPoolMove( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_FISH_MB, getPoolMoveData( req, resp, 101 ),
                poolMoveTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( "初级场</td>" );
                    pw.write( info.yinTojiangP + "," );
                    pw.write( info.yinTojiangC + "," );
                    pw.write( info.yinTojiangM + "," );
                    pw.write( info.xingTojiangP + "," );
                    pw.write( info.xingTojiangC + "," );
                    pw.write( info.xingTojiangM + "," );
                    info.shuixian.forEach( ( k, v ) -> {
                        pw.write("水线:" + k + "    触发次数："+ v +" ," );
                    } );
                    pw.write( "," );
                },getPoolMoveData( req, resp, 102 ),
                poolMoveTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( "中级场," );
                    pw.write( info.yinTojiangP + "," );
                    pw.write( info.yinTojiangC + "," );
                    pw.write( info.yinTojiangM + "," );
                    pw.write( info.xingTojiangP + "," );
                    pw.write( info.xingTojiangC + "," );
                    pw.write( info.xingTojiangM + "," );
                    pw.write( "<td>" );
                    info.shuixian.forEach( ( k, v ) -> {
                        pw.write("水线:" + k + "    触发次数："+ v +" ," );
                    } );
                    pw.write( "," );
                },getPoolMoveData( req, resp, 103 ),
                poolMoveTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( "高级场," );
                    pw.write( info.yinTojiangP + "," );
                    pw.write( info.yinTojiangC + "," );
                    pw.write( info.yinTojiangM + "," );
                    pw.write( info.xingTojiangP + "," );
                    pw.write( info.xingTojiangC + "," );
                    pw.write( info.xingTojiangM + "," );
                    pw.write( "<td>" );
                    info.shuixian.forEach( ( k, v ) -> {
                        pw.write("水线:" + k + "    触发次数："+ v +" ," );
                    } );
                    pw.write( "," );
                },getPoolMoveData( req, resp, 104 ),
                poolMoveTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( "土豪场," );
                    pw.write( info.yinTojiangP + "," );
                    pw.write( info.yinTojiangC + "," );
                    pw.write( info.yinTojiangM + "," );
                    pw.write( info.xingTojiangP + "," );
                    pw.write( info.xingTojiangC + "," );
                    pw.write( info.xingTojiangM + "," );
                    pw.write( "<td>" );
                    info.shuixian.forEach( ( k, v ) -> {
                        pw.write("水线:" + k + "    触发次数："+ v +" ," );
                    } );
                    pw.write( "," );
                } );
    }


    private List< FishView.Poker > getPokerData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.Poker val = new FishView.Poker();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            Map< Long, Long > peopleInfo = ServiceManager.getSqlService().queryMapLongLong( "select level,count(distinct user_id) from t_fish_poker where " + beg + " <= timestamp and timestamp < " + end  +
                    " group by level;", c );
            Map< Long, Long > countInfo = ServiceManager.getSqlService().queryMapLongLong( "select level,count(id) from t_fish_poker where " + beg + " <= timestamp and timestamp < " + end  +
                    " group by level;", c );
            val.mianP = getLong(peopleInfo,401);
            val.chuP = getLong(peopleInfo,402);
            val.zhongP = getLong(peopleInfo,403);
            val.gaoP = getLong(peopleInfo,404);

            val.mianC = getLong(countInfo,401);
            val.chuC = getLong(countInfo,402);
            val.zhongC = getLong(countInfo,403);
            val.gaoC = getLong(countInfo,404);

            val.boss = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_mb_grants where " + beg + " <= timestamp and timestamp < " + end  +
                    " and type = 36;", c );
            List<String> free  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in (" + poker_free_grants + " ) and is_mobile = 'Y' ;", c );
            Map< Long, Long > jingjiMap = new HashMap<>();
            free.forEach( info -> mergeMap( splitMap( info, ";", ":" ), jingjiMap ) );
            val.gold = getLong(jingjiMap , -1) ;

            return val;

        } );
    }

    public void queryPoker( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getPokerData( req, resp ),
                PokerTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.mianP + "</td>" );
                    pw.write( "<td>" + info.mianC + "</td>" );
                    pw.write( "<td>" + info.chuP + "</td>" );
                    pw.write( "<td>" + info.chuC + "</td>" );
                    pw.write( "<td>" + info.zhongP + "</td>" );
                    pw.write( "<td>" + info.zhongC + "</td>" );
                    pw.write( "<td>" + info.gaoP + "</td>" );
                    pw.write( "<td>" + info.gaoC + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + info.boss + "</td>" );

                },null );
    }

    public void downloadPoker( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getPokerData( req, resp ),
                PokerTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.mianP + "</td>" );
                    pw.write( "<td>" + info.mianC + "</td>" );
                    pw.write( "<td>" + info.chuP + "</td>" );
                    pw.write( "<td>" + info.chuC + "</td>" );
                    pw.write( "<td>" + info.zhongP + "</td>" );
                    pw.write( "<td>" + info.zhongC + "</td>" );
                    pw.write( "<td>" + info.gaoP + "</td>" );
                    pw.write( "<td>" + info.gaoC + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + info.boss + "</td>" );
                } );
    }





    private  List< FishView.Boss > getBossData( HttpServletRequest req, HttpServletResponse resp ,long level)
            throws Exception {
        return getData( req, resp, ( beg, end, c ) -> {

            Map< Long, String > itemsMap = ServiceManager.getSqlService().queryMapLongString( "select timestamp,user_get_gold from t_fish_boss where " + beg + " <= timestamp and timestamp < " + end  +
                    " and user_get_gold != 0 and level = "+ level +
                    " group by timestamp;", c );

            Map< Long, FishView.Boss > user = new HashMap<>();

            itemsMap.forEach( ( k, v ) -> {
                FishView.Boss val = new FishView.Boss();
                val.begin = TimeUtil.ymdhmFormat().format( k );
                val.shanghai =   Long.parseLong(v);
                val.people = ServiceManager.getSqlService().queryLong( "select other_info from t_fish_boss " +
                        "where " + beg + " <= timestamp and timestamp < " + end + " and timestamp = "+ k +" ;", c );
                val.choushui = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                        " from t_fish_paiment " +
                        " where " + (k - 3600000 ) + " <= timestamp and timestamp < " + (k + 3600000 * 2) + " and is_mobile = 'Y' and other_info = 10 and level = "+ level +" ;", c )
                        - ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                        " from t_fish_get " +
                        " where " + (k - 3600000 ) + " <= timestamp and timestamp < " + (k + 3600000 * 2) + " and is_mobile = 'Y' and other_info = 10 and level = "+ level +" ;", c );
//                todo : 下次更新线上改一下 区分boss中高级场奖励
                List<String> boss  = ServiceManager.getSqlService().queryListString( "select bouns " +
                        " from t_fish_mb_grants " +
                        " where " + (k - 3600000) + " <= timestamp and timestamp < " + (k + 3600000 * 2) + " and type in (" + world_boss_die_grants +"," + world_boss_rank_grants +" ) and is_mobile = 'Y' and other_info = "+ level +";", c );
                Map< Long, Long > bossMap = new HashMap<>();
                boss.forEach( info -> mergeMap( splitMap( info, ";", ":" ), bossMap ) );
                val.jiangli = getLong(bossMap , -1) +  getLong(bossMap , 20015 ) * 20000 + getLong(bossMap , 20016 ) * 200000 + getLong(bossMap , 20017 ) * 1000000 ;

                user.put( k,val );
            } );

            List< FishView.Boss > winList = new ArrayList<>( user.values() );
            Collections.sort( winList, ( o1, o2 ) -> {
                if( o1.time < o2.time ) return -1;
                if( o1.time > o2.time ) return 1;
                return 0;
            } );

            return winList ;
        } );
    }

    public void queryBoss( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_FISH_MB, getBossData( req, resp ,102),
                BossTableHead, ( info, pw ) -> {
                    pw.write( "<td>中级场</td>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.choushui + "</td>" );
                    pw.write( "<td>" + info.shanghai + "</td>" );
                    pw.write( "<td>" + info.jiangli + "</td>" );
                    pw.write( "<td>" + (info.choushui-info.jiangli) + "</td>" );
                }, getBossData( req, resp,103 ),
                BossTableHead, ( info, pw ) -> {
                    pw.write( "<td>高级场</td>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.choushui + "</td>" );
                    pw.write( "<td>" + info.shanghai + "</td>" );
                    pw.write( "<td>" + info.jiangli + "</td>" );
                    pw.write( "<td>" + (info.choushui-info.jiangli) + "</td>" );
                },getBossData( req, resp,104 ),
                BossTableHead, ( info, pw ) -> {
                    pw.write( "<td>土豪场</td>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.choushui + "</td>" );
                    pw.write( "<td>" + info.shanghai + "</td>" );
                    pw.write( "<td>" + info.jiangli + "</td>" );
                    pw.write( "<td>" + (info.choushui-info.jiangli) + "</td>" );
                }, null  );
    }

    public void downloadBoss( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_FISH_MB, getBossData( req, resp ,102),
                BossTableHead, ( info, pw ) -> {
                    pw.write( "中级场" );
                    pw.write(   info.begin + "，" );
                    pw.write(   info.people + "，" );
                    pw.write(   info.choushui + "，" );
                    pw.write(  info.shanghai + "，" );
                    pw.write(  info.jiangli + "，" );
                    pw.write(  (info.choushui-info.jiangli) + "，" );
                }, getBossData( req, resp,103 ),
                BossTableHead, ( info, pw ) -> {
                    pw.write( "高级场，" );
                    pw.write(  info.begin + "，" );
                    pw.write( info.people + "，" );
                    pw.write(  info.choushui + "，" );
                    pw.write(  info.shanghai + "，" );
                    pw.write( info.jiangli + "，" );
                    pw.write(  (info.choushui-info.jiangli) + "，" );
                } );
    }


    private List< FishView.JingJi > getJingJiData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.JingJi val = new FishView.JingJi();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.count = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_jingji " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            val.people = ServiceManager.getSqlService().queryLong( "select count(distinct user_id) from t_fish_jingji " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            val.cost = ServiceManager.getSqlService().queryLong( "select sum(item_count) from t_fish_item " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and level = 201 and other_info = 'ArenaEnter'", c );

            val.p1  = ServiceManager.getSqlService().queryLong( "select count(id) from  t_fish_jingji where " + beg + " <= timestamp and timestamp < " + end + " and score >= 1500 and score < 2000 ;", c );
            val.p2  = ServiceManager.getSqlService().queryLong( "select count(id) from  t_fish_jingji where " + beg + " <= timestamp and timestamp < " + end + " and score >= 2000 and score < 2500 ;", c );
            val.p3  = ServiceManager.getSqlService().queryLong( "select count(id) from  t_fish_jingji where " + beg + " <= timestamp and timestamp < " + end + " and score >= 2500 and score < 3000 ;", c );
            val.p4  = ServiceManager.getSqlService().queryLong( "select count(id) from  t_fish_jingji where " + beg + " <= timestamp and timestamp < " + end + " and score >= 3000  ;", c );

            val.cishu1 = ServiceManager.getSqlService().queryLong( "select count(distinct a.user_id) from (select count(user_id) as count,user_id from t_fish_jingji where " + beg + " <= timestamp and timestamp < " + end + " group by user_id) as a where a.count = 1 ;", c );
            val.cishu2 = ServiceManager.getSqlService().queryLong( "select count(distinct a.user_id) from (select count(user_id) as count,user_id from t_fish_jingji where " + beg + " <= timestamp and timestamp < " + end + " group by user_id) as a where a.count = 2 ;", c );
            val.cishu3 = ServiceManager.getSqlService().queryLong( "select count(distinct a.user_id) from (select count(user_id) as count,user_id from t_fish_jingji where " + beg + " <= timestamp and timestamp < " + end + " group by user_id) as a where a.count >=3 and a.count <= 5 ;", c );
            val.cishu4 = ServiceManager.getSqlService().queryLong( "select count(distinct a.user_id) from (select count(user_id) as count,user_id from t_fish_jingji where " + beg + " <= timestamp and timestamp < " + end + " group by user_id) as a where a.count > 5  ;", c );


            val.choushui = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' and level = 201 ;", c )
                    - ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' and level = 201  ;", c );

            List<String> jingji  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in (" + jingji_day_rank_grants +"," + jingji_jifen_grants +"," + jingji_task_grants + "," + jingji_week_rank_grants +" ) and is_mobile = 'Y' ;", c );
            Map< Long, Long > jingjiMap = new HashMap<>();
            jingji.forEach( info -> mergeMap( splitMap( info, ";", ":" ), jingjiMap ) );
            val.jiangli = getLong(jingjiMap , -1) + getLong(jingjiMap , 20015 ) * 20000 + getLong(jingjiMap , 20016 ) * 200000 + getLong(jingjiMap , 20017 ) * 1000000 ;
            val.zuanshi = getLong(jingjiMap , -4);
            val.zong = val.choushui - val.jiangli ;
            return val;

        } );
    }

    public void queryJingJi( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getJingJiData( req, resp ),
                JingJiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + (info.cost * -1) + "</td>" );
                    pw.write( "<td>" + info.p1 + "</td>" );
                    pw.write( "<td>" + info.p2 + "</td>" );
                    pw.write( "<td>" + info.p3 + "</td>" );
                    pw.write( "<td>" + info.p4 + "</td>" );
                    pw.write( "<td>" + info.cishu1 + "</td>" );
                    pw.write( "<td>" + info.cishu2 + "</td>" );
                    pw.write( "<td>" + info.cishu3 + "</td>" );
                    pw.write( "<td>" + info.cishu4 + "</td>" );
                    pw.write( "<td>" + info.choushui /10000 + "万</td>" );
                    pw.write( "<td>" + info.jiangli /10000 + "万</td>" );
                    pw.write( "<td>" + info.zuanshi + "</td>" );
                    pw.write( "<td>" + info.zong /10000 + "万</td>" );

                },null );
    }

    public void downloadJingJi( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getJingJiData( req, resp ),
                JingJiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.count + "," );
                    pw.write( info.people + "," );
                    pw.write( (info.cost * -1) + "," );
                    pw.write( info.p1 + "," );
                    pw.write( info.p2 + "," );
                    pw.write( info.p3 + "," );
                    pw.write( info.p4 + "," );
                    pw.write( info.cishu1 + "," );
                    pw.write( info.cishu2 + "," );
                    pw.write( info.cishu3 + "," );
                    pw.write( info.cishu4 + "," );
                    pw.write( info.choushui /10000 + "万</td>" );
                    pw.write( info.jiangli /10000 + "万</td>" );
                    pw.write( info.zong/10000 + "万</td>" );
                } );
    }




    private  List< FishView.UserItem  > getUserItemsData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        long item_id = Long.parseLong( req.getParameter( "actId" ).trim() );
        return getData( req, resp, ( beg, end, c ) -> {

            Map< Long, String > itemsMap = ServiceManager.getSqlService().queryMapLongString( "select t_exit.user_id, t_exit.items from t_exit " +
                    "join " +
                    "( select user_id, max( timestamp ) as mt from t_exit where timestamp >= " + beg + " and timestamp < " + end + " group by user_id ) as A on t_exit.timestamp = A.mt and t_exit.user_id = A.user_id;", c );


            Map< Long, FishView.UserItem > user = new HashMap<>();

            itemsMap.forEach( ( k, v ) -> {
                FishView.UserItem val = new FishView.UserItem();
                val.begin = TimeUtil.ymdFormat().format( beg );
                val.user_id = k ;
                Map< Long, Long > getMap = splitMap( v, ";", "," );
                val.itemId = (int) item_id;
                val.itemCount = getLong( getMap, item_id );
                if (val.user_id != 0 && val.itemCount != 0) user.put( k,val );
            } );

            List< FishView.UserItem > winList = new ArrayList<>( user.values() );
            Collections.sort( winList, ( o1, o2 ) -> {
                if( o1.itemCount > o2.itemCount ) return -1;
                if( o1.itemCount < o2.itemCount ) return 1;
                return 0;
            } );

            return winList ;
        } );
    }

    public void queryUserItems( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getUserItemsData( req, resp ),
                UserItemsTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.user_id + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.user_id + "'></td>" );
                    pw.write( "<td>" + ItemManager.getInstance().getItemName(info.itemId) + "</td>" );
                    pw.write( "<td>" + info.itemCount + "</td>" );
                }, getFishUserNameScript( "td", "tdUserName_")  );
    }

    public void downloadUserItems( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getUserItemsData( req, resp ),
                UserItemsTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.user_id + "," );
                    pw.write( info.user_id + "," );
                    pw.write( ItemManager.getInstance().getItemName(info.itemId) + "," );
                    pw.write( info.itemCount + "," );
                } );
    }

    private  List< FishView.Shop  > getShopData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList2( req, resp, ( beg, end, c ) -> {

            Map< Long, Long > countMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by item_id;", c );


            Map< Long, Long > moneyMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( money2 + money3 ) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by item_id;", c );

            Map< Long, FishView.Shop > user = new HashMap<>();

            long money = ServiceManager.getSqlService().queryLong("select sum(money2 + money3) from t_pay where " + beg + " <= timestamp and timestamp < " + end +
                    ";", c );


            countMap.forEach( ( k, v ) -> {
                if( user.get( k ) == null ) user.put( k, new FishView.Shop() );
                FishView.Shop val = new FishView.Shop();
                val.begin = TimeUtil.ymdFormat().format( beg );
                val.itemId = k.intValue();
                val.itemCount = v;
                user.put( k,val );

            } );

            moneyMap.forEach( ( k, v ) -> {
                if( user.get( k ) == null ) user.put( k, new FishView.Shop() );
                user.get( k ).itemMoney = v;
                user.get( k ).money = money;
            } );


            List< FishView.Shop > winList = new ArrayList<>( user.values() );
            Collections.sort( winList, ( o1, o2 ) -> {
                if( o1.itemMoney > o2.itemMoney) return -1;
                if( o1.itemMoney < o2.itemMoney ) return 1;
                if( o1.itemCount < o2.itemCount ) return -1;
                if( o1.itemCount > o2.itemCount ) return 1;
                return 0;
            } );

            return winList ;
        } );
    }

    public void queryShop( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getShopData( req, resp ),
                ShopTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + ItemManager.getInstance().getItemName(info.itemId) + "</td>" );
                    pw.write( "<td>" + info.itemCount + "</td>" );
                    pw.write( "<td>" +com.hoolai.ccgames.bi.protocol.Currency.format( info.itemMoney ) + "</td>" );
                    pw.write( "<td>" + com.hoolai.ccgames.bi.protocol.Currency.format(info.money) + "</td>" );
                    pw.write( "<td>" + formatRatio( safeDiv( info.itemMoney, info.money ) ) + "</td>" );
                }, null );
    }

    public void downloadShop( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getShopData( req, resp ),
                ShopTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( ItemManager.getInstance().getItemName(info.itemId) + "," );
                    pw.write( info.count + "," );
                    pw.write( info.itemMoney + "," );
                    pw.write( info.money + "," );
                    pw.write( com.hoolai.ccgames.bi.protocol.Currency.format( (long)safeDiv( info.itemMoney, info.money ) ) + "," );
                } );
    }

    private List< FishView.PaoBeiShu > getHuoYuePaoData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.PaoBeiShu val = new FishView.PaoBeiShu();
            val.beg = TimeUtil.ymdFormat().format( beg );
            List<String> Getinfo = ServiceManager.getSqlService().queryListString( "select a.user_info from " +
                    "(select max(timestamp),user_info from t_exit where timestamp > " + beg + " and timestamp <= "+ end +" and LENGTH(user_info) >= 20 " +
                    " and user_id not in ( select user_id from t_install where timestamp > " + beg + " and timestamp <= "+ end +"  ) group by user_id) as a ;", c );
            Map< Long, Long > getMap = new HashMap<>();
            Getinfo.forEach( info -> {
                String[] cols = info.split( ";|:" );
                long cannonMax = Long.parseLong( cols[3] );
                getMap.compute( cannonMax, ( k, v ) -> {
                    if( v == null ) v = 0L;
                    return v + 1;
                } );
            } );

            val.zong = ServiceManager.getSqlService().queryLong("select count(distinct user_id) from t_exit where timestamp > " + beg + " and timestamp <= "+ end +" and LENGTH(user_info) >= 20" +
                    " and user_id not in ( select user_id from t_install where timestamp > " + beg + " and timestamp <= "+ end +"  ) ;",c);
            val.p100 =  getLong( getMap, 100 );val.p150 =  getLong( getMap, 150 );val.p200 =  getLong( getMap, 200 );val.p300 =  getLong( getMap, 300 );
            val.p400 =  getLong( getMap, 400 );val.p500 =  getLong( getMap, 500 );val.p600 =  getLong( getMap, 600 );val.p700 =  getLong( getMap, 700 );
            val.p800 =  getLong( getMap, 800 );val.p900 =  getLong( getMap, 900 );val.p1000 =  getLong( getMap, 1000 );val.p1500 =  getLong( getMap, 1500 );
            val.p2000 =  getLong( getMap, 2000 );val.p2500 =  getLong( getMap, 2500 );val.p3000 =  getLong( getMap, 3000 );val.p3500 =  getLong( getMap, 3500 );
            val.p4000 =  getLong( getMap, 4000 );val.p4500 =  getLong( getMap, 4500 );val.p5000 =  getLong( getMap, 5000 );val.p6000 =  getLong( getMap, 6000 );
            val.p7000 =  getLong( getMap, 7000 );val.p8000 =  getLong( getMap, 8000 );val.p9000 =  getLong( getMap, 9000 );val.p10000 =  getLong( getMap, 10000 );

            return val;
        } );
    }
    public void queryPaoHuoYue( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_FISH_MB, getHuoYuePaoData( req, resp ),
                PaoHuoYueTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.beg  + "</td>" );
                    pw.write( "<td>" + info.zong + "</td>" );
                    pw.write( "<td>" + info.p100 + "</td>" );
                    pw.write( "<td>" + info.p150 + "</td>" );
                    pw.write( "<td>" + info.p200 + "</td>" );
                    pw.write( "<td>" + info.p300 + "</td>" );
                    pw.write( "<td>" + info.p400 + "</td>" );
                    pw.write( "<td>" + info.p500 + "</td>" );
                    pw.write( "<td>" + info.p600 + "</td>" );
                    pw.write( "<td>" + info.p700 + "</td>" );
                    pw.write( "<td>" + info.p800 + "</td>" );
                    pw.write( "<td>" + info.p900 + "</td>" );
                    pw.write( "<td>" + info.p1000 + "</td>" );
                    pw.write( "<td>" + info.p1500 + "</td>" );
                    pw.write( "<td>" + info.p2000 + "</td>" );
                    pw.write( "<td>" + info.p2500 + "</td>" );
                    pw.write( "<td>" + info.p3000 + "</td>" );
                    pw.write( "<td>" + info.p3500 + "</td>" );
                    pw.write( "<td>" + info.p4000 + "</td>" );
                    pw.write( "<td>" + info.p4500 + "</td>" );
                    pw.write( "<td>" + info.p5000 + "</td>" );
                    pw.write( "<td>" + info.p6000 + "</td>" );
                    pw.write( "<td>" + info.p7000 + "</td>" );
                    pw.write( "<td>" + info.p8000 + "</td>" );
                    pw.write( "<td>" + info.p9000 + "</td>" );
                    pw.write( "<td>" + info.p10000 + "</td>" );
                },null );
    }
    public void downloadPaoHuoYue( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_FISH_MB, getHuoYuePaoData( req, resp ),
                PaoHuoYueTableHead, ( info, pw ) -> {
                    pw.write( info.beg + "," );
                    pw.write( info.zong + "," );
                    pw.write( info.p100 + "," );
                    pw.write( info.p150 + "," );
                    pw.write( info.p200 + "," );
                    pw.write( info.p300 + "," );
                    pw.write( info.p400 + "," );
                    pw.write( info.p500 + "," );
                    pw.write( info.p600 + "," );
                    pw.write( info.p700 + "," );
                    pw.write( info.p800 + "," );
                    pw.write( info.p900 + "," );
                    pw.write( info.p1000 + "," );
                    pw.write( info.p1500 + "," );
                    pw.write( info.p2000 + "," );
                    pw.write( info.p2500 + "," );
                    pw.write( info.p3000 + "," );
                    pw.write( info.p3500 + "," );
                    pw.write( info.p4000 + "," );
                    pw.write( info.p4500 + "," );
                    pw.write( info.p5000 + "," );
                    pw.write( info.p6000 + "," );
                    pw.write( info.p7000 + "," );
                    pw.write( info.p8000 + "," );
                    pw.write( info.p9000 + "," );
                    pw.write( info.p10000 + "," );
                } );
    }

    private List< FishView.ChiBang > getChiBangData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.ChiBang val = new FishView.ChiBang();
            val.beg = TimeUtil.ymdFormat().format( beg );
            Map<Long, Long> numMap = ServiceManager.getSqlService().queryMapLongLong("select item_id, coalesce( sum( item_count ), 0 ) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "   and is_mobile = 'Y' and item_count >= 1 group by item_id;", c);

            List<String> Getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and  activity_id =1 ;", c );
            Map< Long, Long > getMap = new HashMap<>();
            Getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );

            List<String> grantsMap  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'Y' ;", c );
            Map< Long, Long > grants = new HashMap<>();

            grantsMap.forEach( info -> mergeMap( splitMap( info, ";", ":" ), grants ) );

            val.tianshi = getLong( numMap , 21010 ) +  getLong( getMap, 21010 ) +  getLong( grants, 21010 );
            val.emo     = getLong( numMap , 21020 ) +  getLong( getMap, 21020 ) +  getLong( grants, 21020 );
            val.yaowang = getLong( numMap , 21030 ) +  getLong( getMap, 21030 ) +  getLong( grants, 21030 );
            val.chiyan  = getLong( numMap , 21040 ) +  getLong( getMap, 21040 ) +  getLong( grants, 21040 );
            val.wangzhe = getLong( numMap , 21050 ) +  getLong( getMap, 21050 ) +  getLong( grants, 21050 );

            return val;
        } );
    }
    public void queryChiBang( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_FISH_MB, getChiBangData( req, resp ),
                ChiBangTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.beg + "</td>" );
                    pw.write( "<td>" + info.tianshi + "</td>" );
                    pw.write( "<td>" + info.emo + "</td>" );
                    pw.write( "<td>" + info.yaowang + "</td>" );
                    pw.write( "<td>" + info.chiyan + "</td>" );
                    pw.write( "<td>" + info.wangzhe + "</td>" );
                },null );
    }
    public void downloadChiBang( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_FISH_MB, getChiBangData( req, resp ),
                ChiBangTableHead, ( info, pw ) -> {
                    pw.write( info.beg + "," );
                    pw.write( info.tianshi + "," );
                    pw.write( info.emo + "," );
                    pw.write( info.yaowang + "," );
                    pw.write( info.chiyan + "," );
                    pw.write( info.wangzhe + "," );
                } );
    }


    private List< baseView.Online > getSendData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.Online val = new baseView.Online();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            Map<Long, Long> numMap = ServiceManager.getSqlService().queryMapLongLong("select item_id, coalesce( sum( item_count ), 0 ) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  group by item_id;", c);

            val.chu1 = getLong( numMap , 20015 )+getLong( numMap , 11017 );
            val.chu2 = getLong( numMap , 20016 )+getLong( numMap , 11018 );
            val.chu3 = getLong( numMap , 20017 )+getLong( numMap , 11019 );

            Map<Long, Long> peopleMap = ServiceManager.getSqlService().queryMapLongLong("select item_id, count(distinct send_user_id) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  group by item_id;", c);

            val.zhong1 = getLong( peopleMap , 20015 )+getLong( peopleMap , 11017 );
            val.zhong2 = getLong( peopleMap , 20016 )+getLong( peopleMap , 11018 );
            val.zhong3 = getLong( peopleMap , 20017 )+getLong( peopleMap , 11019 );

            return val;
        } );
    }
    public void querySend( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_FISH_MB, getSendData( req, resp ),
                SendTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zhong1 + "</td>" );
                    pw.write( "<td>" + info.chu1 + "</td>" );
                    pw.write( "<td>" + info.zhong2 + "</td>" );
                    pw.write( "<td>" + info.chu2 + "</td>" );
                    pw.write( "<td>" + info.zhong3 + "</td>" );
                    pw.write( "<td>" + info.chu3 + "</td>" );
                },null );
    }
    public void downloadSend( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_FISH_MB, getSendData( req, resp ),
                SendTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zhong1 + "," );
                    pw.write( info.chu1 + "," );
                    pw.write( info.zhong2 + "," );
                    pw.write( info.chu2 + "," );
                    pw.write( info.zhong3 + "," );
                    pw.write( info.chu3 + "," );
                } );
    }

    private List< baseView.Online > gethaveGoldData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.Online val = new baseView.Online();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.chu1  = ServiceManager.getSqlService().queryLong( "select count(user_id) from t_possession where " + beg + " <= timestamp and timestamp < " + end + " and gold >= 0 and   gold <= 10000 and is_mobile = 'Y' ;", c );
            val.chu3  = ServiceManager.getSqlService().queryLong( "select count(user_id) from t_possession where " + beg + " <= timestamp and timestamp < " + end + " and gold > 10000   and gold <= 50000 and is_mobile = 'Y' ;", c );
            val.chu5  = ServiceManager.getSqlService().queryLong( "select count(user_id) from t_possession where " + beg + " <= timestamp and timestamp < " + end + " and gold > 50000   and gold <= 100000 and is_mobile = 'Y' ;", c );
            val.chu7  = ServiceManager.getSqlService().queryLong( "select count(user_id) from t_possession where " + beg + " <= timestamp and timestamp < " + end + " and gold > 100000  and gold <= 200000 and is_mobile = 'Y' ;", c );
            val.chu10  = ServiceManager.getSqlService().queryLong( "select count(user_id) from t_possession where " + beg + " <= timestamp and timestamp < " + end + " and gold > 200000  and gold <= 500000 and is_mobile = 'Y' ;", c );
            val.chu15  = ServiceManager.getSqlService().queryLong( "select count(user_id) from t_possession where " + beg + " <= timestamp and timestamp < " + end + " and gold > 500000  and gold <= 1000000 and is_mobile = 'Y' ;", c );
            val.zhong1  = ServiceManager.getSqlService().queryLong( "select count(user_id) from t_possession where " + beg + " <= timestamp and timestamp < " + end + " and gold > 1000000 and is_mobile = 'Y' ;", c );

            return val;
        } );
    }
    public void queryHaveGold( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_FISH_MB, gethaveGoldData( req, resp ),
                HaveGoldTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.chu1 + "</td>" );
                    pw.write( "<td>" + info.chu3 + "</td>" );
                    pw.write( "<td>" + info.chu5 + "</td>" );
                    pw.write( "<td>" + info.chu7 + "</td>" );
                    pw.write( "<td>" + info.chu10 + "</td>" );
                    pw.write( "<td>" + info.chu15 + "</td>" );
                    pw.write( "<td>" + info.zhong1 + "</td>" );
                },null );
    }
    public void downloadHaveGold( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_FISH_MB, gethaveGoldData( req, resp ),
                HaveGoldTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.chu1 + "," );
                    pw.write( info.chu3 + "," );
                    pw.write( info.chu5 + "," );
                    pw.write( info.chu7 + "," );
                    pw.write( info.chu10 + "," );
                    pw.write( info.chu15 + "," );
                    pw.write( info.zhong1 + "," );
                } );
    }


    private List< FishView.handle> getHandleData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.handle val = new FishView.handle();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );


            Map<Long, Long> handleMap = ServiceManager.getSqlService().queryMapLongLong("select handle, count(id) " +
                    " from t_handle " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'Y'  group by handle;", c);
            val.gerenxinxi      = getLong( handleMap , 1L );
            val.shangcheng      = getLong( handleMap , 2L );
            val.beibao          = getLong( handleMap , 3L );
            val.xiaoxi          = getLong( handleMap , 4L );
            val.renwu           = getLong( handleMap , 5L );
            val.duihuan         = getLong( handleMap , 6L );
            val.paihangbang     = getLong( handleMap , 7L );
            val.shezhi          = getLong( handleMap , 8L );
            val.paotai          = getLong( handleMap , 9L );
            val.qiandao         = getLong( handleMap , 10L );
            val.chuanzhanglibao = getLong( handleMap , 11L );
            val.VIPtequan       = getLong( handleMap , 12L );
            val.kuaisukaishi    = getLong( handleMap , 13L );
            val.yiyuanqianggou  = getLong( handleMap , 14L );
            val.liaotian        = getLong( handleMap , 15L );
            val.yulecheng       = getLong( handleMap , 16L );
            val.kefu            = getLong( handleMap , 17L );
            val.chibang         = getLong( handleMap , 19L );
            val.xuyuan          = getLong( handleMap , 20L );
            val.huodong         = getLong( handleMap , 21L );
            val.tujian          = getLong( handleMap , 22L );
            return val;
        } );
    }

    public void queryHandle( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getHandleData( req, resp ),
                handleTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.gerenxinxi + "</td>" );
                    pw.write( "<td>" + info.shangcheng + "</td>" );
                    pw.write( "<td>" + info.beibao + "</td>" );
                    pw.write( "<td>" + info.xiaoxi + "</td>" );
                    pw.write( "<td>" + info.renwu + "</td>" );
                    pw.write( "<td>" + info.duihuan + "</td>" );
                    pw.write( "<td>" + info.paihangbang + "</td>" );
                    pw.write( "<td>" + info.shezhi + "</td>" );
                    pw.write( "<td>" + info.paotai + "</td>" );
                    pw.write( "<td>" + info.qiandao + "</td>" );
                    pw.write( "<td>" + info.chuanzhanglibao + "</td>" );
                    pw.write( "<td>" + info.VIPtequan + "</td>" );
                    pw.write( "<td>" + info.kuaisukaishi + "</td>" );
                    pw.write( "<td>" + info.yiyuanqianggou + "</td>" );
                    pw.write( "<td>" + info.liaotian + "</td>" );
                    pw.write( "<td>" + info.yulecheng + "</td>" );
                    pw.write( "<td>" + info.kefu + "</td>" );
                    pw.write( "<td>" + info.chibang + "</td>" );
                    pw.write( "<td>" + info.xuyuan + "</td>" );
                    pw.write( "<td>" + info.huodong + "</td>" );
                    pw.write( "<td>" + info.tujian + "</td>" );
                } );
    }

    public void downloadHandle( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getHandleData( req, resp ),
                handleTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.gerenxinxi + "</td>" );
                    pw.write( "<td>" + info.shangcheng + "</td>" );
                    pw.write( "<td>" + info.beibao + "</td>" );
                    pw.write( "<td>" + info.xiaoxi + "</td>" );
                    pw.write( "<td>" + info.renwu + "</td>" );
                    pw.write( "<td>" + info.duihuan + "</td>" );
                    pw.write( "<td>" + info.paihangbang + "</td>" );
                    pw.write( "<td>" + info.shezhi + "</td>" );
                    pw.write( "<td>" + info.paotai + "</td>" );
                    pw.write( "<td>" + info.qiandao + "</td>" );
                    pw.write( "<td>" + info.chuanzhanglibao + "</td>" );
                    pw.write( "<td>" + info.VIPtequan + "</td>" );
                    pw.write( "<td>" + info.kuaisukaishi + "</td>" );
                    pw.write( "<td>" + info.yiyuanqianggou + "</td>" );
                    pw.write( "<td>" + info.liaotian + "</td>" );
                    pw.write( "<td>" + info.yulecheng + "</td>" );
                    pw.write( "<td>" + info.kefu + "</td>" );
                    pw.write( "<td>" + info.chibang + "</td>" );
                    pw.write( "<td>" + info.xuyuan + "</td>" );
                    pw.write( "<td>" + info.huodong + "</td>" );
                    pw.write( "<td>" + info.tujian + "</td>" );
                } );
    }

    //兑换券产出
    private List< FishView.jiangquan> getJiangQuanData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.jiangquan val = new FishView.jiangquan();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            Map<Long, Long> PeopleMap = ServiceManager.getSqlService().queryMapLongLong("select level, count(distinct user_id) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'Y' and item_id = 10544 group by level;", c);
            val.chupeople = getLong( PeopleMap , 101 );
            val.zhongpeople = getLong( PeopleMap , 102 );
            val.gaopeople = getLong( PeopleMap , 103 );
            val.tuhaopeople = getLong( PeopleMap , 104 );

            Map<Long, Long> CountMap = ServiceManager.getSqlService().queryMapLongLong("select level, count(id) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'Y' and item_id = 10544 group by level;", c);
            val.chucount = getLong( CountMap , 101 );
            val.zhongcount = getLong( CountMap , 102 );
            val.gaocount = getLong( CountMap , 103 );
            val.tuhaocount = getLong( CountMap , 104 );


            Map<Long, Long> ChanchuMap = ServiceManager.getSqlService().queryMapLongLong("select level, coalesce( sum( item_count ), 0 ) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'Y' and item_id = 10544 group by level;", c);
            val.chujiangquan = getLong( ChanchuMap , 101 );
            val.zhongjiangquan = getLong( ChanchuMap , 102 );
            val.gaojiangquan = getLong( ChanchuMap , 103 );
            val.tuhaojiangquan = getLong( ChanchuMap , 104 );

            return val;
        } );
    }

    public void queryJiangQuan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getJiangQuanData( req, resp ),
                jiangquanTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.chupeople + "</td>" );
                    pw.write( "<td>" + info.chucount + "</td>" );
                    pw.write( "<td>" + info.chujiangquan + "</td>" );
                    pw.write( "<td>" + info.zhongpeople + "</td>" );
                    pw.write( "<td>" + info.zhongcount + "</td>" );
                    pw.write( "<td>" + info.zhongjiangquan + "</td>" );
                    pw.write( "<td>" + info.gaopeople + "</td>" );
                    pw.write( "<td>" + info.gaocount + "</td>" );
                    pw.write( "<td>" + info.gaojiangquan + "</td>" );
                    pw.write( "<td>" + info.tuhaopeople + "</td>" );
                    pw.write( "<td>" + info.tuhaocount + "</td>" );
                    pw.write( "<td>" + info.tuhaojiangquan + "</td>" );
                } );
    }

    public void downloadJiangQuan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getJiangQuanData( req, resp ),
                jiangquanTableHead, ( info, pw ) -> {
                    pw.write(  info.begin + "." );
                    pw.write(  info.chupeople + "." );
                    pw.write(  info.chucount + "." );
                    pw.write(  info.chujiangquan + "." );
                    pw.write(  info.zhongpeople + "." );
                    pw.write(  info.zhongcount + "." );
                    pw.write(  info.zhongjiangquan + "." );
                    pw.write(  info.gaopeople + "." );
                    pw.write(  info.gaocount + "." );
                    pw.write(  info.gaojiangquan + "." );
                    pw.write(  info.danpeople + "." );
                    pw.write(  info.dancount + "." );
                    pw.write(  info.danjiangquan + "." );
                } );
    }

//猜倍支出
    private List< FishView.Basic > getFanBeiData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.Basic val = new FishView.Basic();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            Map<Long, Long> BetMap = ServiceManager.getSqlService().queryMapLongLong("select level, coalesce( sum( gold ), 0 ) " +
                    " from t_fish_special_fish " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = 1  group by level;", c);
            val.chujiBet = getLong( BetMap , 101 );
            val.zhongjiBet = getLong( BetMap , 102 );
            val.gaojiBet = getLong( BetMap , 103 );
            val.tuhaoBet = getLong( BetMap , 104 );

            Map<Long, Long> WinMap = ServiceManager.getSqlService().queryMapLongLong("select level, coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'Y' and other_info = 8 group by level;", c);
            val.chujiWin = getLong( WinMap , 101 );
            val.zhongjiWin = getLong( WinMap , 102 );
            val.gaojiWin = getLong( WinMap , 103 );
            val.tuhaoWin = getLong( WinMap , 104 );

            Map<Long, Long> PeopleMap = ServiceManager.getSqlService().queryMapLongLong("select level, count(distinct user_id) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and gold > 0  and is_mobile = 'Y' and other_info = 8 group by level;", c);
            val.chuPeople = getLong( PeopleMap , 101 );
            val.zhongPeople = getLong( PeopleMap , 102 );
            val.gaoPeople = getLong( PeopleMap , 103 );
            val.tuhaoPeople = getLong( PeopleMap , 104 );

            val.zong = ( val.chujiBet + val.zhongjiBet + val.gaojiBet + val.tuhaoBet ) - (val.chujiWin + val.zhongjiWin + val.gaojiWin + val.tuhaoWin) ;

            return val;
        } );
    }

    public void queryFanBei( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getFanBeiData( req, resp ),
                FanBeiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.chuPeople + "</td>" );
                    pw.write( "<td>" + info.chujiBet /10000+ "万</td>" );
                    pw.write( "<td>" + info.chujiWin /10000+ "万</td>" );
                    pw.write( "<td>" + (info.chujiBet - info.chujiWin - info.chujiJack )/10000 + "万</td>" );
                    pw.write( "<td>" + info.zhongPeople + "</td>" );
                    pw.write( "<td>" + info.zhongjiBet/10000 + "万</td>" );
                    pw.write( "<td>" + info.zhongjiWin /10000+ "万</td>" );
                    pw.write( "<td>" + (info.zhongjiBet - info.zhongjiWin - info.zhongjiJack)/10000 + "万</td>" );
                    pw.write( "<td>" + info.gaoPeople + "</td>" );
                    pw.write( "<td>" + info.gaojiBet/10000 + "万</td>" );
                    pw.write( "<td>" + info.gaojiWin /10000+ "万</td>" );
                    pw.write( "<td>" + (info.gaojiBet - info.gaojiWin - info.gaojiJack  )/10000 + "万</td>" );
                    pw.write( "<td>" + info.tuhaoBet/10000 + "万</td>" );
                    pw.write( "<td>" + info.tuhaoWin /10000+ "万</td>" );
                    pw.write( "<td>" + (info.tuhaoBet - info.tuhaoWin )/10000 + "万</td>" );
                    pw.write( "<td>" + info.zong /10000+ "万</td>" );

                } );
    }

    public void downloadFanBei( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getFanBeiData( req, resp ),
                FanBeiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.chuPeople + "," );
                    pw.write( info.chujiBet /10000+ "万," );
                    pw.write( info.chujiWin/10000 + "万," );
                    pw.write( (info.chujiBet - info.chujiWin -info.chujiJack)/10000 + "万," );
                    pw.write( info.zhongPeople + "," );
                    pw.write( info.zhongjiBet /10000+ "万," );
                    pw.write( info.zhongjiWin/10000 + "万," );
                    pw.write( (info.zhongjiBet - info.chujiWin - info.zhongjiJack)/10000 + "万," );
                    pw.write( info.gaoPeople + "," );
                    pw.write( info.gaojiBet /10000+ "万," );
                    pw.write( info.gaojiWin /10000+ "万," );
                    pw.write( (info.gaojiBet - info.gaojiWin - info.gaojiJack)/10000 + "万," );
                    pw.write( info.tuhaoPeople + "," );
                    pw.write( info.tuhaoBet /10000+ "万," );
                    pw.write( info.tuhaoWin /10000+ "万," );
                    pw.write( (info.tuhaoBet - info.tuhaoWin )/10000  + "万," );
                    pw.write( info.zong /10000+ "万," );
                } );
    }


    //气泡产出
    private List< FishView.qipao> getZhuanPanData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.qipao val = new FishView.qipao();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

             List<String> qipaoFish  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + qipao_fish_get +" and is_mobile = 'Y' ;", c );
            Map< Long, Long > qipaoMap = new HashMap<>();

            qipaoFish.forEach( info -> mergeMap( splitMap( info, ";", ":" ), qipaoMap ) );

            val.people = ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp< " + end + " and type = " + qipao_fish_get +" and is_mobile = 'Y' ;", c );

            val.count = ServiceManager.getSqlService().queryLong("select count( id ) " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp< " + end + " and type = " + qipao_fish_get +" and is_mobile = 'Y' ;", c );

            long a  = ServiceManager.getSqlService().queryLong("select sum( gold ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp< " + end + " and  other_info = 7 ;", c );
            qipaoMap.put( -1L , a );
            val.Daoju     =  qipaoMap;

            return val;
        } );
    }

    public void queryZhuanPan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getZhuanPanData( req, resp ),
                zhuanpanTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" );
                    info.Daoju.forEach( ( itemId, itemCount ) -> {
                        if (itemId == -1 )
                        {pw.write("金币:" + itemCount / 10000 + "万<br>" );
                            return;}
                        if (itemId == -9 )
                        {    pw.write("话费点:" + itemCount / 10000 + "万<br>" );
                        return;}
                        else
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

                    } );
                    pw.write( "</td>" );

                } );
    }

    public void downloadZhuanPan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getZhuanPanData( req, resp ),
                zhuanpanTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.people + "," );
                    pw.write( info.count + "," );
                    pw.write( info.gold + "," );
                    pw.write( info.zuanshi + "," );
                    pw.write( info.jiangquan + "," );
                    pw.write( info.huafei + "," );
                } );
    }


    //兑换产出
    private List< FishView.duihuan> getDuiHuanData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.duihuan val = new FishView.duihuan();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            List<String> userinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_fish_get_ietms " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and cost_item_id in (10544,-6) and is_mobile = 'Y' ;", c );

            Map< Long, Long > userGetMap = new HashMap<>();

            userinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), userGetMap ) );
            val.qingdan     = getLong( userGetMap,20015 );
            val.yindan      = getLong( userGetMap,20016 );
            val.huangdan    = getLong( userGetMap,20017 );
            val.gold        = getLong( userGetMap,-1 );
            val.zuanshi     = getLong( userGetMap,-4 );
            val.huafei30    = getLong( userGetMap,10543 );
            val.huafei50    = getLong( userGetMap,10545 );
            val.huafei100   = getLong( userGetMap,10546 );

            val.pao = ServiceManager.getSqlService().queryLong("select count( distinct id ) " +
                    " from t_fish_get_ietms " +
                    " where " + beg + " <= timestamp and timestamp< " + end + " and get_info like '-7:%';", c);


            return val;
        } );
    }

    public void queryDuiHuan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getDuiHuanData( req, resp ),
                duihuanTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.huafei30 + "</td>" );
                    pw.write( "<td>" + info.huafei50 + "</td>" );
                    pw.write( "<td>" + info.huafei100 + "</td>" );
                    pw.write( "<td>" + info.qingdan + "</td>" );
                    pw.write( "<td>" + info.yindan + "</td>" );
                    pw.write( "<td>" + info.huangdan + "</td>" );
                    pw.write( "<td>" + info.zuanshi + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + info.pao + "</td>" );

                } );
    }

    public void downloadDuiHuan( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getDuiHuanData( req, resp ),
                duihuanTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.huafei30 + "," );
                    pw.write( info.huafei50 + "," );
                    pw.write( info.huafei100 + "," );
                    pw.write( info.qingdan + "," );
                    pw.write( info.yindan + "," );
                    pw.write( info.huangdan + "," );
                    pw.write( info.zuanshi + "," );
                    pw.write( info.gold + "," );
                    pw.write( info.pao + "," );
                } );
    }

    private List< FishView.loudou > getLouDouData(HttpServletRequest req, HttpServletResponse resp ,String type)
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.loudou val = new FishView.loudou();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            if(type.equals("user")) {
                val.install = ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                        " from t_install " +
                        " where " + beg + " <= timestamp and timestamp< " + end + "  ;", c);

                val.qiandao = ServiceManager.getSqlService().queryLong("select count( distinct user_id ) " +
                        " from t_fish_mb_grants " +
                        " where " + beg + " <= timestamp and timestamp< " + end + " and type = " + login_grants + " and user_id in (select distinct user_id  from t_install " +
                        "  where " + beg + " <= timestamp and timestamp< " + end + "  );", c);

                Map<Long, Long> changMap = ServiceManager.getSqlService().queryMapLongLong("select level, count( distinct user_id ) " +
                        " from t_fish_paiment  " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_mobile ='Y'  and user_id in (select distinct user_id from t_install " +
                        "  where " + beg + " <= timestamp and timestamp< " + end + "   ) group by level;", c);

                val.chu = getLong(changMap, 101);
                val.zhong = getLong(changMap, 102);
                val.gao = getLong(changMap, 103);

                val.mtl = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_mtl2_bet  " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id in (select distinct user_id from t_install where " + beg + " <= timestamp and timestamp < " + end +");", c);

                val.zfb = ServiceManager.getSqlService().queryLong("select count(distinct user_id) " +
                        " from t_zfb_bet  " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and user_id in (select distinct user_id from t_install where " + beg + " <= timestamp and timestamp < " + end +");", c);

                Map<String, Long> levelMap = ServiceManager.getSqlService().queryMapStringLong("select other_info, count( id ) " +
                        " from t_fish_mb_grants  " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_mobile ='Y' and type = " + user_up_grants +
                        " and user_id in (select distinct user_id from t_install " +
                        "  where " + beg + " <= timestamp and timestamp< " + end + "   ) group by other_info;", c);
                Map<Long, Long> levelMap2 = new HashMap<>();
                levelMap.forEach((k,v) -> {
                   k = k.replace("[","").replace("]","").replace("\"","").replace("\"","");
                   levelMap2.put(Long.parseLong(k),v);
                });

                val.l2 = getLong(levelMap2, 2);
                val.l3 = getLong(levelMap2, 3);
                val.l4 = getLong(levelMap2, 4);
                val.l5 = getLong(levelMap2, 5);
                val.l6 = getLong(levelMap2, 6);
                val.l7 = getLong(levelMap2, 7);
                val.l8 = getLong(levelMap2, 8);
                val.l9 = getLong(levelMap2, 9);
                val.l10 = getLong(levelMap2, 10);
                val.l11 = getLong(levelMap2, 11);
                val.l12 = getLong(levelMap2, 12);
                val.l13 = getLong(levelMap2, 13);
                val.l14 = ServiceManager.getSqlService().queryLong("select count( id ) " +
                        " from t_fish_mb_grants " +
                        " where " + beg + " <= timestamp and timestamp< " + end + " and type = " + user_up_grants +
                        " and  substring(other_info,'3','2') >= 14 and user_id in (select distinct user_id from t_install " +
                        "  where " + beg + " <= timestamp and timestamp< " + end + "   );", c);
            }else if (type.equals("gun"))
            {
                Map<String, Long> levelMap = ServiceManager.getSqlService().queryMapStringLong("select other_info, count( id ) " +
                        " from t_fish_mb_grants  " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and is_mobile ='Y' and type = " + gun_up_grants +
                        " and user_id in (select distinct user_id from t_install " +
                        "  where " + beg + " <= timestamp and timestamp< " + end + "   ) group by other_info;", c);
//   2 5 10 15 20 30 50 75 100 150 200 300 400 500 600 700 800 900 1000 1500 2000 2500 3000 3500 4000 4500 5000
                Map<Long, Long> levelMap2 = new HashMap<>();
                levelMap.forEach((k,v) -> {
                    k = k.replace("[","").replace("]","").replace("\"","").replace("\"","");
                    levelMap2.put(Long.parseLong(k),v);
                });
                val.l2 = getLong(levelMap2, 2);
                val.l3 = getLong(levelMap2, 5);
                val.l4 = getLong(levelMap2, 10);
                val.l5 = getLong(levelMap2, 15);
                val.l6 = getLong(levelMap2, 20);
                val.l7 = getLong(levelMap2, 30);
                val.l8 = getLong(levelMap2, 50);
                val.l9 = getLong(levelMap2, 75);
                val.l10 = getLong(levelMap2, 100);
                val.l11 = getLong(levelMap2, 150);
                val.l12 = getLong(levelMap2, 200);
                val.l13 = getLong(levelMap2, 300);
                val.l14 = getLong(levelMap2, 400);
                val.l15 = getLong(levelMap2, 500);
                val.l16 = getLong(levelMap2, 600);
                val.l17 = getLong(levelMap2, 700);
                val.l18 = getLong(levelMap2, 800);
                val.l19 = getLong(levelMap2, 900);
                val.l20 = getLong(levelMap2, 1000);
                val.l21 = getLong(levelMap2, 1500);
                val.l22 = getLong(levelMap2, 2000);
                val.l23 = getLong(levelMap2, 2500);
                val.l24 = getLong(levelMap2, 3000);
                val.l25 = getLong(levelMap2, 3500);
                val.l26 = getLong(levelMap2, 4000);
                val.l27 = getLong(levelMap2, 4500);
                val.l28 = getLong(levelMap2, 5000);
            }


            return val;
        } );
    }

    public void queryLouDou( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_FISH_MB, getLouDouData( req, resp ,"user"),
                LouDouTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    pw.write( "<td>" + info.qiandao + "</td>" );
                    pw.write( "<td>" + info.zfb + "</td>" );
                    pw.write( "<td>" + info.mtl + "</td>" );
                    pw.write( "<td>" + info.chu + "</td>" );
                    pw.write( "<td>" + info.zhong + "</td>" );
                    pw.write( "<td>" + info.gao + "</td>" );
                    pw.write( "<td>" + info.l2 + "</td>" );
                    pw.write( "<td>" + info.l3 + "</td>" );
                    pw.write( "<td>" + info.l4 + "</td>" );
                    pw.write( "<td>" + info.l5 + "</td>" );
                    pw.write( "<td>" + info.l6 + "</td>" );
                    pw.write( "<td>" + info.l7 + "</td>" );
                    pw.write( "<td>" + info.l8 + "</td>" );
                    pw.write( "<td>" + info.l9 + "</td>" );
                    pw.write( "<td>" + info.l10 + "</td>" );
                    pw.write( "<td>" + info.l11 + "</td>" );
                    pw.write( "<td>" + info.l12 + "</td>" );
                    pw.write( "<td>" + info.l13 + "</td>" );
                    pw.write( "<td>" + info.l14 + "</td>" );
                },getLouDouData( req, resp ,"gun"),GunTableHead,
                ( info, pw ) -> {
                    pw.write( "<td>" + info.l2  + "</td>" );
                    pw.write( "<td>" + info.l3  + "</td>" );
                    pw.write( "<td>" + info.l4  + "</td>" );
                    pw.write( "<td>" + info.l5  + "</td>" );
                    pw.write( "<td>" + info.l6  + "</td>" );
                    pw.write( "<td>" + info.l7  + "</td>" );
                    pw.write( "<td>" + info.l8  + "</td>" );
                    pw.write( "<td>" + info.l9  + "</td>" );
                    pw.write( "<td>" + info.l10 + "</td>" );
                    pw.write( "<td>" + info.l11 + "</td>" );
                    pw.write( "<td>" + info.l12 + "</td>" );
                    pw.write( "<td>" + info.l13 + "</td>" );
                    pw.write( "<td>" + info.l14 + "</td>" );
                    pw.write( "<td>" + info.l15 + "</td>" );
                    pw.write( "<td>" + info.l16 + "</td>" );
                    pw.write( "<td>" + info.l17 + "</td>" );
                    pw.write( "<td>" + info.l18 + "</td>" );
                    pw.write( "<td>" + info.l19 + "</td>" );
                    pw.write( "<td>" + info.l20 + "</td>" );
                    pw.write( "<td>" + info.l21 + "</td>" );
                    pw.write( "<td>" + info.l22 + "</td>" );
                    pw.write( "<td>" + info.l23 + "</td>" );
                    pw.write( "<td>" + info.l24 + "</td>" );
                    pw.write( "<td>" + info.l25 + "</td>" );
                    pw.write( "<td>" + info.l26 + "</td>" );
                    pw.write( "<td>" + info.l27 + "</td>" );
                    pw.write( "<td>" + info.l28 + "</td>" );
                },null );
    }

    public void downloadLouDou( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_FISH_MB, getLouDouData( req, resp ,"user"),
                LouDouTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install+ "," );
                    pw.write( info.qiandao + "," );
                    pw.write( info.chu + "," );
                    pw.write( info.zhong + "," );
                    pw.write( info.gao + "," );
                    pw.write( info.l1 + "," );
                    pw.write( info.l2 + "," );
                    pw.write( info.l3 + "," );
                    pw.write( info.l4 + "," );
                    pw.write( info.l5 + "," );
                    pw.write( info.l6 + "," );
                    pw.write( info.l7 + "," );
                    pw.write( info.l8 + "," );
                    pw.write( info.l9 + "," );
                    pw.write( info.l10 + "," );
                    pw.write( info.l11 + "," );
                    pw.write( info.l12 + "," );
                    pw.write( info.l13 + "," );
                    pw.write( info.l14 + "," );
                }, getLouDouData( req, resp ,"gun"),GunTableHead , ( info, pw ) -> {
                    pw.write( info.l2 + "," );
                    pw.write( info.l3 + "," );
                    pw.write( info.l4 + "," );
                    pw.write( info.l5 + "," );
                    pw.write( info.l6 + "," );
                    pw.write( info.l7 + "," );
                    pw.write( info.l8 + "," );
                    pw.write( info.l9 + "," );
                    pw.write( info.l10 + "," );
                    pw.write( info.l11 + "," );
                    pw.write( info.l12 + "," );
                    pw.write( info.l13 + "," );
                    pw.write( info.l14 + "," );
                    pw.write( info.l15 + "," );
                    pw.write( info.l16 + "," );
                    pw.write( info.l17 + "," );
                    pw.write( info.l18 + "," );
                    pw.write( info.l19 + "," );
                    pw.write( info.l20 + "," );
                    pw.write( info.l21 + "," );
                    pw.write( info.l22 + "," );
                    pw.write( info.l23 + "," );
                    pw.write( info.l24 + "," );
                    pw.write( info.l25 + "," );
                    pw.write( info.l26 + "," );
                    pw.write( info.l27+ "," );
                    pw.write( info.l28+ "," );
                });
    }

    private List< FishView.dantou > getDanTouData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.dantou val = new FishView.dantou();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            Map< Long, Long > countMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                    " from t_fish_item  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_count < 0 and is_mobile ='Y' " +
                    " group by item_id;", c );

            val.qtUse = -getLong( countMap, 20015 ) -getLong( countMap, 11017 );
            val.byUse = -getLong( countMap, 20016 ) -getLong( countMap, 11018 );
            val.hjUse = -getLong( countMap, 20017 ) -getLong( countMap, 11019 );

            Map< Long, Long > diaoluoMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                    " from t_fish_item  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_count > 0 and is_mobile ='Y' " +
                    " group by item_id;", c );


            val.qtCount = getLong( diaoluoMap, 20015 ) + getLong( diaoluoMap, 11017 );
            val.byCount = getLong( diaoluoMap, 20016 )+ getLong( diaoluoMap, 11018 );
            val.hjCount = getLong( diaoluoMap, 20017 )+ getLong( diaoluoMap, 11019 );

            Map< Long, Long > peopleMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, count( distinct user_id ) " +
                    " from t_fish_item  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and item_count < 0 and is_mobile ='Y' " +
                    " group by item_id;", c );

            val.qtPeople = getLong( peopleMap, 20015 )+getLong( peopleMap, 11017 );
            val.byPeople = getLong( peopleMap, 20016 )+getLong( peopleMap, 11018 );
            val.hjPeople = getLong( peopleMap, 20017 )+getLong( peopleMap, 11019 );

            Map< Long, Long > tuhaoDiaoluoMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                    " from t_fish_item  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "  and level = 104 and item_count > 0 and is_mobile ='Y' " +
                    " group by item_id;", c );
            Map< Long, Long > zhongjiDiaoluoMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                    " from t_fish_item  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "  and level = 102 and item_count > 0 and is_mobile ='Y' " +
                    " group by item_id;", c );
            Map< Long, Long > gaojiDiaoluoMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                    " from t_fish_item  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "  and level = 103 and item_count > 0 and is_mobile ='Y' " +
                    " group by item_id;", c );


            val.tuhaoQing = getLong( tuhaoDiaoluoMap, 20015 )+getLong( tuhaoDiaoluoMap, 11017 );
            val.tuhaoBai = getLong( tuhaoDiaoluoMap, 20016 )+getLong( tuhaoDiaoluoMap, 11018 );
            val.tuhaoHuang = getLong( tuhaoDiaoluoMap, 20017 )+ getLong( tuhaoDiaoluoMap, 11019 );
            val.zhongQing = getLong( zhongjiDiaoluoMap, 20015 )+getLong( zhongjiDiaoluoMap, 11017 );
            val.zhongBai = getLong( zhongjiDiaoluoMap, 20016 )+getLong( zhongjiDiaoluoMap, 11018 );
            val.zhongHuang = getLong( zhongjiDiaoluoMap, 20017 )+getLong( zhongjiDiaoluoMap, 11019 );
            val.gaoQing = getLong( gaojiDiaoluoMap, 20015 )+getLong( gaojiDiaoluoMap, 11017 );
            val.gaoBai = getLong( gaojiDiaoluoMap, 20016 )+getLong( gaojiDiaoluoMap, 11018 );
            val.gaoHuang = getLong( gaojiDiaoluoMap, 20017 )+getLong( gaojiDiaoluoMap, 11019 );


            return val;
        } );
    }

    public void queryDanTou( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_FISH_MB, getDanTouData( req, resp ),
                DanTouTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.qtPeople + "</td>" );
                    pw.write( "<td>" + info.qtUse + "</td>" );
                    pw.write( "<td>" + info.qtCount + "</td>" );
                    pw.write( "<td>" + info.byPeople + "</td>" );
                    pw.write( "<td>" + info.byUse + "</td>" );
                    pw.write( "<td>" + info.byCount + "</td>" );
                    pw.write( "<td>" + info.hjPeople + "</td>" );
                    pw.write( "<td>" + info.hjUse + "</td>" );
                    pw.write( "<td>" + info.hjCount + "</td>" );
                },  getDanTouData( req, resp ),
                DanTouXiangXiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zhongQing + "</td>" );
                    pw.write( "<td>" + info.zhongBai + "</td>" );
                    pw.write( "<td>" + info.zhongHuang + "</td>" );
                    pw.write( "<td>" + info.gaoQing + "</td>" );
                    pw.write( "<td>" + info.gaoBai + "</td>" );
                    pw.write( "<td>" + info.gaoHuang + "</td>" );
                    pw.write( "<td>" + info.tuhaoQing + "</td>" );
                    pw.write( "<td>" + info.tuhaoBai + "</td>" );
                    pw.write( "<td>" + info.tuhaoHuang + "</td>" );
                },null );
    }

    public void downloadDanTou( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_FISH_MB, getDanTouData( req, resp ),
                DanTouTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.qtPeople+ "," );
                    pw.write( info.qtUse+ "," );
                    pw.write( info.qtCount + "," );
                    pw.write( info.byPeople + "," );
                    pw.write( info.byUse + "," );
                    pw.write( info.byCount + "," );
                    pw.write( info.hjPeople + "," );
                    pw.write( info.hjUse + "," );
                    pw.write( info.hjCount + "," );
                }, getDanTouData( req, resp ),
                DanTouXiangXiTableHead, ( info, pw ) -> {
                    pw.write(  info.begin + "," );
                    pw.write(  info.chuQing + "," );
                    pw.write(  info.chuBai + "," );
                    pw.write(  info.chuHuang + "," );
                    pw.write(  info.zhongQing + "," );
                    pw.write(  info.zhongBai + "," );
                    pw.write(  info.zhongHuang + "," );
                    pw.write(  info.gaoQing + "," );
                    pw.write(  info.gaoBai + "," );
                    pw.write(  info.gaoHuang + "," );
                } );
    }
    //任务活跃度
    private List< FishView.Task > getTaskData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.Task val = new FishView.Task();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.DPeople = People(beg,end,day_task_grants,c) +  PeopleVIP(beg,end,day_task_grants,c);
            val.D_HY_People = People(beg,end,day_act_grants,c) + PeopleVIP(beg,end,day_act_grants,c);
            val.DCount = Count(beg,end,day_task_grants,c) + CountVIP(beg,end,day_task_grants,c) ;
            val.DGold = SumItem(beg,end,day_task_grants,-1,c) + SumItemVip(beg,end,day_task_grants,-1,c);
            val.DHuoYue_Gold = SumItem(beg,end,day_act_grants,-1,c)  + SumItemVip(beg,end,day_act_grants,-1,c);

            val.WPeople = People(beg,end,week_task_grants,c) + PeopleVIP(beg,end,week_task_grants,c);
            val.W_HY_People = People(beg,end,week_act_grants,c) + PeopleVIP(beg,end,week_act_grants,c);
            val.WCount = Count(beg,end,week_task_grants,c) + CountVIP(beg,end,week_task_grants,c);
            val.WGold = SumItem(beg,end,week_task_grants,-1,c) + SumItemVip(beg,end,week_task_grants,-1,c);
            val.WHuoYue_Gold = SumItem(beg,end,week_act_grants,-1,c)  + SumItemVip(beg,end,week_act_grants,-1,c);

            List<String> userinfo = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + week_task_grants +" and is_mobile = 'Y' ;", c );

            Map< Long, Long > userGetMap = new HashMap<>();

            userinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), userGetMap ) );
            val.WDaoju = userGetMap;

            List<String> Duserinfo = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + day_task_grants +" and is_mobile = 'Y' ;", c );

            Map< Long, Long > DuserGetMap = new HashMap<>();

            Duserinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), DuserGetMap ) );
            val.DDaoju = DuserGetMap;

            val.zongGold = val.DHuoYue_Gold + val.WHuoYue_Gold + val.DGold + val.WGold ;
            return val;
        } );
    }

    public void queryTask( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getTaskData( req, resp ),
                TaskTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.DPeople + "</td>" );
                    pw.write( "<td>" + info.DCount + "</td>" );
                    pw.write( "<td>" );
                    info.DDaoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                        if(itemId == -1)
                        {
                            pw.write("金币:" + itemCount + "<br>" );
                        }
                        if(itemId == -4)
                        {
                            pw.write("钻石:" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" + info.D_HY_People + "</td>" );
                    pw.write( "<td>" + info.DHuoYue_Gold + "</td>" );
                    pw.write( "<td>" + info.WPeople + "</td>" );
                    pw.write( "<td>" + info.WCount + "</td>" );
                    pw.write( "<td>" );
                    info.WDaoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                        if(itemId == -1)
                        {
                            pw.write("金币:" + itemCount + "<br>" );
                        }
                        if(itemId == -4)
                        {
                            pw.write("钻石:" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" + info.W_HY_People + "</td>" );
                    pw.write( "<td>" + info.WHuoYue_Gold + "</td>" );
                    pw.write( "<td>" + info.zongGold + "</td>" );

                } );
    }

    public void downloadTask( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getTaskData( req, resp ),
                TaskTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.DPeople+ "," );
                    pw.write( info.DCount + "," );
                    pw.write( info.DGold + "," );
                    pw.write( info.D_HY_People+ "," );
                    pw.write( info.DHuoYue_Gold + "," );
                    pw.write( info.WPeople + "," );
                    pw.write( info.WCount + "," );
                    pw.write( info.WGold + "," );
                    pw.write( info.W_HY_People + "," );
                    pw.write( info.WHuoYue_Gold + "," );
                    pw.write( info.zongGold + "," );

                } );
    }

    //船长
    private List< FishView.captain> getCaptainData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.captain val = new FishView.captain();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.BuyPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10008 and appid = "+ appid +";", c );
            val.BuyZhouPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10009 and appid = "+ appid +";", c );

            val.LingquPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp< " + end + " and type = " + captain_grants +" and is_mobile = 'Y' ;", c );
            val.LingquZhouPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp< " + end + " and type = " + week_grants +" and is_mobile = 'Y' ;", c );

            List<String> userinfo = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + captain_grants +" and is_mobile = 'Y' ;", c );

            Map< Long, Long > userGetMap = new HashMap<>();

            userinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), userGetMap ) );
            val.Daoju = userGetMap;
            val.gold = getLong( userGetMap ,-1 );

            List<String> zhouInfo = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + week_grants +" and is_mobile = 'Y' ;", c );

            Map< Long, Long > zhouGetMap = new HashMap<>();

            zhouInfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), zhouGetMap ) );
            val.zhouDaoju = zhouGetMap;
            val.ZhouGold  = getLong( zhouGetMap ,-1 );


            return val;
        } );
    }

    public void queryCaptain( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getCaptainData( req, resp ),
                captainOutTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.BuyPeople + "</td>" );
                    pw.write( "<td>" + info.LingquPeople + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" );
                    info.Daoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" + info.BuyZhouPeople + "</td>" );
                    pw.write( "<td>" + info.LingquZhouPeople + "</td>" );
                    pw.write( "<td>" + info.ZhouGold + "</td>" );
                    pw.write( "<td>" );
                    info.zhouDaoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( "</td>" );

                } );
    }

    public void downloadCaptain( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getCaptainData( req, resp ),
                captainOutTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.BuyPeople+ "," );
                    pw.write( info.LingquPeople + "," );
                    pw.write( info.gold + "," );
                    info.Daoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( info.BuyZhouPeople+ "," );
                    pw.write( info.LingquZhouPeople + "," );
                    pw.write( info.ZhouGold + "," );
                    info.zhouDaoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                    } );
                } );
    }

    //VIP产出
    private List< FishView.VipOut> getVipOutData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.VipOut val = new FishView.VipOut();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.VipLogin = SumItemVip(beg,end,login_grants,-1,c);
            val.VipRed  = SumItemVip(beg,end,red_grants,-1,c);
            val.VipBuquan  = SumItemVip(beg,end,vip_login_give_grants,-1,c);

            List<String> userinfo = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + vip_up_grants +" and is_mobile = 'Y' ;", c );

            Map< Long, Long > userGetMap = new HashMap<>();

            userinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), userGetMap ) );
            val.Daoju = userGetMap;

            val.upGold = getLong(userGetMap,-1);

            return val;
        } );
    }

    public void queryVipOut( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getVipOutData( req, resp ),
                VipOutTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.VipLogin + "</td>" );
                    pw.write( "<td>" + info.VipRed + "</td>" );
                    pw.write( "<td>" + info.VipBuquan + "</td>" );
                    pw.write( "<td>" );
                    info.Daoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                        if(itemId == -1)
                        {
                            pw.write("金币:" + itemCount + "<br>" );
                        }
                        if(itemId == -4)
                        {
                            pw.write("钻石:" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" + ( info.VipLogin + info.VipRed + info.VipBuquan + info.upGold ) + "</td>" );

                } );
    }

    public void downloadVipOut( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getVipOutData( req, resp ),
                VipOutTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.VipLogin+ "," );
                    pw.write( info.VipRed + "," );
                    pw.write( info.VipBuquan + "," );
                    info.Daoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                        if(itemId == -1)
                        {
                            pw.write("金币:" + itemCount + "<br>" );
                        }
                        if(itemId == -4)
                        {
                            pw.write("钻石:" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( ( info.VipLogin + info.VipRed + info.VipBuquan + info.upGold) + "," );
                } );
    }

    //在线红包奖励
    private List< FishView.redOnlid > getRedOnlineData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.redOnlid val = new FishView.redOnlid();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.freePeople = People(beg,end,red_grants,c);
            val.VIPPeople  = PeopleVIP(beg,end,red_grants,c);
            val.freeCount  = Count(beg,end,red_grants,c);
            val.VIPCount   = CountVIP(beg,end,red_grants,c);
            val.freeGold   = SumItem(beg,end,red_grants,-1,c);
            val.VIPGold    = SumItemVip(beg,end,red_grants,-1,c);

            return val;
        } );
    }

    public void queryRedOnline( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getRedOnlineData( req, resp ),
                RedTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.freePeople + "</td>" );
                    pw.write( "<td>" + info.freeCount + "</td>" );
                    pw.write( "<td>" + info.freeGold + "</td>" );
                    pw.write( "<td>" + info.VIPPeople + "</td>" );
                    pw.write( "<td>" + info.VIPCount + "</td>" );
                    pw.write( "<td>" + info.VIPGold + "</td>" );
                    pw.write( "<td>" + ( info.VIPGold + info.freeGold ) + "</td>" );

                } );
    }

    public void downloadRedOnlone( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getRedOnlineData( req, resp ),
                RedTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.freePeople+ "," );
                    pw.write( info.freeCount + "," );
                    pw.write( info.freeGold + "," );
                    pw.write( info.VIPPeople+ "," );
                    pw.write( info.VIPCount + "," );
                    pw.write( info.VIPGold + "," );
                    pw.write(  ( info.VIPGold + info.freeGold ) + "," );

                } );
    }

    //人物 炮 升级
    private List< FishView.upLevle > getUPData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.upLevle val = new FishView.upLevle();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.userUpPeople = People(beg,end,user_up_grants,c) + PeopleVIP(beg,end,user_up_grants,c);
            val.gunUpPeople  = People(beg,end,gun_up_grants,c) + PeopleVIP(beg,end,gun_up_grants,c) ;
            val.userUpCount  = Count(beg,end,user_up_grants,c) + CountVIP(beg,end,user_up_grants,c);
            val.gunUpCount   = Count(beg,end,gun_up_grants,c) + CountVIP(beg,end,gun_up_grants,c);


            List<String> guninfo = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + gun_up_grants +" and is_mobile = 'Y' ;", c );
            List<String> userinfo = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in  (" + user_up_grants +") and is_mobile = 'Y' ;", c );

            Map< Long, Long > userGetMap = new HashMap<>();
            Map< Long, Long > gunGetMap = new HashMap<>();

            userinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), userGetMap ) );
            guninfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), gunGetMap ) );

            val.userDaoju = userGetMap;
            val.gunDaoju = gunGetMap;
            val.userUpGold = getLong(userGetMap , -1);
            val.gunUpGold = getLong(gunGetMap , -1);

            return val;
        } );
    }

    public void queryUP( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getUPData( req, resp ),
                UpTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userUpPeople + "</td>" );
                    pw.write( "<td>" + info.userUpCount + "</td>" );
                    pw.write( "<td>" + info.userUpGold + "</td>" );
                    pw.write( "<td>" );
                    info.userDaoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" + info.gunUpPeople + "</td>" );
                    pw.write( "<td>" + info.gunUpCount + "</td>" );
                    pw.write( "<td>" + info.gunUpGold + "</td>" );
                    pw.write( "<td>" );
                    info.gunDaoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" + ( info.gunUpGold + info.userUpGold ) + "</td>" );
                } );
    }

    public void downloadUP( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getUPData( req, resp ),
                UpTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "，" );
                    pw.write( info.userUpPeople + "，" );
                    pw.write( info.userUpCount + "，" );
                    pw.write( info.userUpGold + "，" );
                    info.userDaoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( info.gunUpPeople + "，" );
                    pw.write( info.gunUpCount + "，" );
                    pw.write( info.gunUpGold + "，" );
                    info.gunDaoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( ( info.gunUpGold + info.userUpGold ) + "，" );
                } );
    }

    //破产补助
    private List< FishView.broke > getBrokeData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.broke val = new FishView.broke();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.people = People(beg,end,broke_grants,c);
            val.count = Count(beg,end,broke_grants,c);
            val.Gold = SumItem(beg,end,broke_grants,-1,c);

            val.VIP_people = PeopleVIP(beg,end,broke_grants,c);
            val.VIP_count = CountVIP(beg,end,broke_grants,c);
            val.VIP_Gold = SumItemVip(beg,end,broke_grants,-1,c);

            return val;
        } );
    }

    public void queryBroke( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getBrokeData( req, resp ),
                BrokeTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + (info.VIP_people + info.people) + "</td>" );
                    pw.write( "<td>" + info.VIP_people + "</td>" );
                    pw.write( "<td>" + info.VIP_count + "</td>" );
                    pw.write( "<td>" + info.VIP_Gold  + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.Gold  + "</td>" );
                    pw.write( "<td>" + (info.Gold + info.VIP_Gold)  + "</td>" );
                } );
    }

    public void downloadBroke( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getBrokeData( req, resp ),
                BrokeTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( (info.VIP_people + info.people) + "," );
                    pw.write( info.VIP_people+ "," );
                    pw.write( info.VIP_count + "," );
                    pw.write( info.VIP_Gold + "," );
                    pw.write( info.people+ "," );
                    pw.write( info.count + "," );
                    pw.write( info.Gold + "," );
                    pw.write( (info.Gold + info.VIP_Gold) + "," );

                } );
    }

    //玩家领取登录奖励
    private List< FishView.login > getLoginData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.login val = new FishView.login();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.login_people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_dau " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " ;", c );
            val.lingqu_people = People(beg,end,login_grants,c);

            val.Gold = SumItem(beg,end,login_grants,-1,c);
            val.Vip_Gold = SumItemVip(beg,end,login_grants,-1,c);

            val.leijiPeople = People(beg,end,login_leiji_grants ,c) + PeopleVIP(beg,end,login_leiji_grants ,c);

            List<String> userinfo = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + login_leiji_grants +" and is_mobile = 'Y' ;", c );

            Map< Long, Long > userGetMap = new HashMap<>();

            userinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), userGetMap ) );
            val.Daoju = userGetMap;
            val.leijiGold = getLong(userGetMap , -1) + getLong( userGetMap , -1) + getLong( userGetMap , 20015 ) * 20000 + getLong( userGetMap , 20016 ) * 200000 + getLong( userGetMap , 20017 ) * 1000000 + getLong( userGetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
                    +  getLong( userGetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth) +   getLong( userGetMap , -4 ) * 3000 + getLong( userGetMap , 20033 ) * (ItemManager.getInstance().get( 20033 ).worth) ;
            return val;
        } );
    }

    public void queryLogin( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getLoginData( req, resp ),
                LoginTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.login_people + "</td>" );
                    pw.write( "<td>" + info.lingqu_people + "</td>" );
                    pw.write( "<td>" + info.Vip_Gold + "</td>" );
                    pw.write( "<td>" + info.Gold  + "</td>" );
                    pw.write( "<td>" + info.leijiPeople  + "</td>" );
                    pw.write( "<td>" );
                    info.Daoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                        if(itemId == -1)
                        {
                            pw.write("金币:" + itemCount + "<br>" );
                        }
                        if(itemId == -4)
                        {
                            pw.write("钻石:" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" +( info.Gold + info.Vip_Gold + info.leijiGold ) + "</td>" );
                } );
    }

    public void downloadLogin( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getLoginData( req, resp ),
                LoginTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.login_people+ "," );
                    pw.write( info.lingqu_people + "," );
                    pw.write( info.Vip_Gold + "," );
                    pw.write( info.Gold + "," );
                    pw.write( info.leijiPeople + "," );
                    info.Daoju.forEach( ( itemId, itemCount ) -> {
                        if(itemId > 0  )
                        {
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                        if(itemId == -1)
                        {
                            pw.write("金币:" + itemCount + "<br>" );
                        }
                        if(itemId == -4)
                        {
                            pw.write("钻石:" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( (info.Gold + info.Vip_Gold + info.leijiGold ) + "," );
                } );
    }

    //新手注册金币支出
    private List< FishView.install > getInstallData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.install val = new FishView.install();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.install = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_install " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and appid in (1167896519,1,252) and is_mobile = 'Y' ;", c );
            val.installGold = SumItem(beg,end,user_install_grants,-1,c);

            val.baohuUser = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                   " from t_fish_get " +
                   " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 4 and is_mobile = 'Y' ;", c )
                   -
                   ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                           " from t_fish_jackpot " +
                           " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 4 and gold < 0 and is_mobile = 'Y' ;", c )
                   -       ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                   " from t_fish_paiment " +
                   " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 4 and is_mobile = 'Y' ;", c ) ;
            val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 100 and is_mobile = 'Y' ;", c );

            val.count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 100 and is_mobile = 'Y' ;", c );
            val.zongOut = val.installGold + val.baohuUser;
            return val;
        } );
    }

    public void queryInstall( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getInstallData( req, resp ),
                NewUserTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.install + "</td>" );
                    pw.write( "<td>" + info.installGold + "</td>" );
                    pw.write( "<td>" + info.people + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.baohuUser + "</td>" );
                    pw.write( "<td>" + info.zongOut + "</td>" );
                } );
    }

    public void downloadInstall( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getInstallData( req, resp ),
                NewUserTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.install+ "," );
                    pw.write( info.installGold + "," );
                    pw.write( info.people + "," );
                    pw.write( info.count + "," );
                    pw.write( info.baohuUser + "," );
                    pw.write( info.zongOut + "," );
                } );
    }




    private List< baseView.Online > getFishOnlineData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.Online val = new baseView.Online();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.chu1    = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 101 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 0 and  a.sum<= 60000;", c );
            val.chu2    = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 101 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000  and  a.sum<= 60000 * 2;", c );
            val.chu3    = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 101 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 2 and  a.sum<= 60000 * 5;", c );
            val.chu5    = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 101 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 5 and  a.sum<= 60000 * 7;", c );
            val.chu7    = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 101 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 7 and  a.sum<= 60000 * 10;", c );
            val.chu10   = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 101 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 10 and  a.sum<= 60000 * 15;", c );
            val.chu15   = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 101 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 15 and  a.sum<= 60000 * 20;", c );
            val.chu20   = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 101 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 20 and  a.sum<= 60000 * 30;", c );
            val.chu30   = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 101 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 30 and  a.sum<= 60000 * 60;", c );
            val.chu60   = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 101 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 60 and  a.sum<= 60000 * 120;", c );
            val.chu120   = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 101 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 120 ;", c );

            val.zhong1  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 102 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 0 and  a.sum<= 60000;", c );
            val.zhong2  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 102 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000  and  a.sum<= 60000 * 2;", c );
            val.zhong3  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 102 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 2 and a.sum <= 60000 * 5;", c );
            val.zhong5  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 102 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 5 and a.sum <= 60000 * 7 ;", c );
            val.zhong7  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 102 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 7 and a.sum <= 60000 * 10;", c );
            val.zhong10 = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 102 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 10 and a.sum <= 60000 * 15;", c );
            val.zhong15 = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 102 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 15 and a.sum <= 60000 * 20;", c );
            val.zhong20 = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 102 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 20 and a.sum <= 60000 * 30;", c );
            val.zhong30 = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 102 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 30 and a.sum <= 60000 * 60;", c );
            val.zhong60 = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 102 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 60 and a.sum <= 60000 * 120;", c );
            val.zhong120 = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 102 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 120 ;", c );

            val.gao1    = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 103 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 0 and  a.sum<= 60000;", c );
            val.gao2    = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 103 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000  and  a.sum<= 60000 * 2;", c );
            val.gao3    = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 103 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 2 and  a.sum<= 60000 * 5;", c );
            val.gao5    = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 103 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 5 and  a.sum<= 60000 * 7;", c );
            val.gao7    = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 103 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 7 and a.sum <= 60000 * 10;", c );
            val.gao10   = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 103 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 10 and  a.sum<= 60000 * 15;", c );
            val.gao15   = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 103 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 15 and  a.sum<= 60000 * 20;", c );
            val.gao20   = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 103 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 20 and  a.sum<= 60000 * 30;", c );
            val.gao30   = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 103 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 30 and  a.sum<= 60000 * 60;", c );
            val.gao60   = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 103 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 60 and  a.sum<= 60000 * 120;", c );
            val.gao120   = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 103 and kind = 1 and is_mobile = 'Y' group by user_id) as a  where a.sum > 60000 * 120 ;", c );

             return val;

        } );
    }

    public void queryFishOnline( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getFishOnlineData( req, resp ),
                OnlineTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + "初级" + "</td>" );
                    pw.write( "<td>" + info.chu1 + "</td>" );
                    pw.write( "<td>" + info.chu2 + "</td>" );
                    pw.write( "<td>" + info.chu3 + "</td>" );
                    pw.write( "<td>" + info.chu5 + "</td>" );
                    pw.write( "<td>" + info.chu7 + "</td>" );
                    pw.write( "<td>" + info.chu10 + "</td>" );
                    pw.write( "<td>" + info.chu15 + "</td>" );
                    pw.write( "<td>" + info.chu20 + "</td>" );
                    pw.write( "<td>" + info.chu30 + "</td>" );
                    pw.write( "<td>" + info.chu60 + "</td>" );
                    pw.write( "<td>" + info.chu120 + "</td>" );
                    pw.write( "</tr>" );
                    pw.write( "<tr>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + "中级" + "</td>" );
                    pw.write( "<td>" + info.zhong1 + "</td>" );
                    pw.write( "<td>" + info.zhong2 + "</td>" );
                    pw.write( "<td>" + info.zhong3 + "</td>" );
                    pw.write( "<td>" + info.zhong5 + "</td>" );
                    pw.write( "<td>" + info.zhong7 + "</td>" );
                    pw.write( "<td>" + info.zhong10 + "</td>" );
                    pw.write( "<td>" + info.zhong15 + "</td>" );
                    pw.write( "<td>" + info.zhong20 + "</td>" );
                    pw.write( "<td>" + info.zhong30 + "</td>" );
                    pw.write( "<td>" + info.zhong60 + "</td>" );
                    pw.write( "<td>" + info.zhong120 + "</td>" );
                    pw.write( "</tr>" );
                    pw.write( "<tr>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + "高级" + "</td>" );
                    pw.write( "<td>" + info.gao1 + "</td>" );
                    pw.write( "<td>" + info.gao2 + "</td>" );
                    pw.write( "<td>" + info.gao3 + "</td>" );
                    pw.write( "<td>" + info.gao5 + "</td>" );
                    pw.write( "<td>" + info.gao7 + "</td>" );
                    pw.write( "<td>" + info.gao10 + "</td>" );
                    pw.write( "<td>" + info.gao15 + "</td>" );
                    pw.write( "<td>" + info.gao20 + "</td>" );
                    pw.write( "<td>" + info.gao30 + "</td>" );
                    pw.write( "<td>" + info.gao60 + "</td>" );
                    pw.write( "<td>" + info.gao120 + "</td>" );
                },null );
    }

    public void downloadFishOnline( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getFishOnlineData( req, resp ),
                OnlineTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( "初级" + "," );
                    pw.write( info.chu1 + "," );
                    pw.write( info.chu2 + "," );
                    pw.write( info.chu3 + "," );
                    pw.write( info.chu5 + "," );
                    pw.write( info.chu7 + "," );
                    pw.write( info.chu10 + "," );
                    pw.write( info.chu15 + "," );
                    pw.write( info.chu20 + "," );
                    pw.write( info.chu30 + "," );
                    pw.write( info.chu60 + "," );
                    pw.write( info.chu120 + "," );
                    pw.write( "</tr>" );
                    pw.write( "<tr>" );
                    pw.write( "中级" + "," );
                    pw.write( info.zhong1 + "," );
                    pw.write( info.zhong2 + "," );
                    pw.write( info.zhong3 + "," );
                    pw.write( info.zhong5 + "," );
                    pw.write( info.zhong7 + "," );
                    pw.write( info.zhong10 + "," );
                    pw.write( info.zhong15 + "," );
                    pw.write( info.zhong20 + "," );
                    pw.write( info.zhong30 + "," );
                    pw.write( info.zhong60 + "," );
                    pw.write( info.zhong120 + "," );
                    pw.write( "</tr>" );
                    pw.write( "<tr>" );
                    pw.write( "高级" + "," );
                    pw.write( info.gao1 + "," );
                    pw.write( info.gao2 + "," );
                    pw.write( info.gao3 + "," );
                    pw.write( info.gao5 + "," );
                    pw.write( info.gao7 + "," );
                    pw.write( info.gao10 + "," );
                    pw.write( info.gao15 + "," );
                    pw.write( info.gao20 + "," );
                    pw.write( info.gao30 + "," );
                    pw.write( info.gao60 + "," );
                    pw.write( info.gao120 + "," );
                } );
    }

    private List< baseView.ItemMB > getItemData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.ItemMB val = new baseView.ItemMB();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            Map< Long, Long > getInfo = ServiceManager.getSqlService().queryMapLongLong( "select item_id,sum( abs(item_count) ) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' and item_count > 0 group by item_id  ;", c );

            List<String> duihuanInfo  = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_fish_get_ietms " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' ;", c );

            Map< Long, Long > duihuan = new HashMap<>();
            duihuanInfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), duihuan ) );

            long DHGold = getLong(duihuan , -1);

//            val.diamondOut = getLong(getInfo , -4) ;
//            val.beikeOut = getLong(getInfo , 20014);
//            val.miaozhun = getLong(getInfo , 20010);
//            val.bingdong = getLong(getInfo , 20011);
//            val.fenlie = getLong(getInfo , 20012);
//            val.jiasu = getLong(getInfo , 20013);
//            val.qingdan = getLong(getInfo , 20015);
//            val.yindan = getLong(getInfo , 20016);
//            val.jindan = getLong(getInfo , 20017);

            val.duijiangquan = getLong(getInfo , 10544);

           Map< Long, Long > useInfo = ServiceManager.getSqlService().queryMapLongLong( "select item_id,sum( abs(item_count) ) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' and item_count < 0 group by item_id  ;", c );

            val.diamond = getLong(useInfo , -4) + ServiceManager.getSqlService().queryLong( "select coalesce( sum( cost_item_count ), 0 ) " +
                    " from t_fish_get_ietms " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and cost_item_id = -4 and is_mobile = 'Y' ;", c ) +
                    ServiceManager.getSqlService().queryLong( "select sum(cost_count) " +
                            " from t_newactivity " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and cost_id = -4 and is_mobile = 'Y' ;", c ) -
                    ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
                            " from t_fish_item " +
                            " where " + beg + " <= timestamp and timestamp < " + end + " and other_info in  ('ArenaEnter','BombEnter') and is_mobile = 'Y' ;", c );

            val.beike       = getLong(useInfo , 20014);
            val.useMiaozhun       = getLong(useInfo , 20010);
            val.useBingdong       = getLong(useInfo , 20011);
            val.useFenlie       = getLong(useInfo , 20012);
            val.useJiasu       = getLong(useInfo , 20013);
//            val.useQingdan  = getLong(useInfo , 20015);
//            val.useYindan   = getLong(useInfo , 20016);
//            val.useJindan   = getLong(useInfo , 20017);

            List<String> gold  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type not in ( " + user_jackpot_grants +","+ pay_huikui_grants +") and is_mobile = 'Y' ;", c );

            List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  ;", c );

            Map< Long, Long > actGetMap = new HashMap<>();
            getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), actGetMap ) );

            Map< Long, Long > goldMap = new HashMap<>();
            gold.forEach( info -> mergeMap( splitMap( info, ";", ":" ), goldMap ) );

            val.diamondOut = getLong(getInfo , -4) + getLong(goldMap , -4) + getLong(actGetMap ,-4);
            val.miaozhun = getLong(getInfo , 20010) + getLong(goldMap , 20010) + getLong(actGetMap , 20010) ;
            val.bingdong = getLong(getInfo , 20011) + getLong(goldMap , 20011) + getLong(actGetMap , 20011) ;
            val.fenlie = getLong(getInfo , 20012) + getLong(goldMap , 20012) + getLong(actGetMap , 20012) ;
            val.jiasu = getLong(getInfo , 20013) + getLong(goldMap , 20013) + getLong(actGetMap , 20013) ;
            val.goldOut = getLong(goldMap , -1) + DHGold + getLong(actGetMap , -1);



            val.beikeOut = getLong(goldMap , 20014) + getLong(getInfo , 20014)+getLong(actGetMap , 20014) ;
            val.shangzhuangkaOut = getLong(goldMap , 10512) + getLong(getInfo , 10512) + getLong(actGetMap , 10512) ;
            val.niubiOut = getLong(goldMap , 10507) + getLong(getInfo , 10507) + getLong(actGetMap , 10507) ;
            val.huafeidian = getLong(goldMap , -9) / 10000;

            val.gold = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' ;", c )
                        -  ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y'  ;", c );

            return val;

        } );
    }

    public void queryItem( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getItemData( req, resp ),
                ItemTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.goldOut + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + info.beikeOut + "</td>" );
                    pw.write( "<td>" + info.beike + "</td>" );
                    pw.write( "<td>" + info.huafeidian + "</td>" );
                    pw.write( "<td>" + info.shangzhuangkaOut + "</td>" );
                    pw.write( "<td>" + info.niubiOut + "</td>" );
                    pw.write( "<td>" + info.duijiangquan + "</td>" );
                    pw.write( "<td>" + info.diamondOut + "</td>" );
                    pw.write( "<td>" + info.diamond + "</td>" );
                    pw.write( "<td>" + info.bingdong + "</td>" );
                    pw.write( "<td>" + info.miaozhun + "</td>" );
                    pw.write( "<td>" + info.jiasu + "</td>" );
                    pw.write( "<td>" + info.fenlie + "</td>" );
                    pw.write( "<td>" + info.useBingdong + "</td>" );
                    pw.write( "<td>" + info.useMiaozhun + "</td>" );
                    pw.write( "<td>" + info.useJiasu + "</td>" );
                    pw.write( "<td>" + info.useFenlie + "</td>" );
                },null );
    }

    public void downloadItem( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getItemData( req, resp ),
                ItemTableHead, ( info, pw ) -> {
                    pw.write(  info.begin + "," );
                    pw.write(  info.goldOut + "," );
                    pw.write(  info.gold + "," );
                    pw.write(  info.beikeOut + "," );
                    pw.write(  info.beike + "," );
                    pw.write(  info.huafeidian + "," );
                    pw.write(  info.shangzhuangkaOut + "," );
                    pw.write(  info.niubiOut + "," );
                    pw.write(  info.duijiangquan + "," );
                    pw.write(  info.diamondOut + "," );
                    pw.write(  info.diamond + "," );
                    pw.write(  info.bingdong + "," );
                    pw.write(  info.miaozhun + "," );
                    pw.write(  info.jiasu + "," );
                    pw.write(  info.useBingdong + "," );
                    pw.write(  info.useMiaozhun + "," );
                    pw.write(  info.useJiasu + "," );
                } );
    }

    private List< FishView.Basic > getBasicData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.Basic val = new FishView.Basic();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            Map<Long, Long> BetMap = ServiceManager.getSqlService().queryMapLongLong("select level, coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'Y' group by level;", c);
            val.chujiBet = getLong( BetMap , 101 );
            val.zhongjiBet = getLong( BetMap , 102 );
            val.gaojiBet = getLong( BetMap , 103 );
            val.danBet = getLong( BetMap , 104 );
            val.jingjiBet = getLong( BetMap , 201 );

            Map<Long, Long> WinMap = ServiceManager.getSqlService().queryMapLongLong("select level, coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'Y'  group by level;", c);
            val.chujiWin = getLong( WinMap , 101 );
            val.zhongjiWin = getLong( WinMap , 102 );
            val.gaojiWin = getLong( WinMap , 103 );
            val.danWin = getLong( WinMap , 104 );
            val.jingjiWin = getLong( WinMap , 201 );

            Map<Long, Long> PeopleMap = ServiceManager.getSqlService().queryMapLongLong("select level, count(distinct user_id) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and gold > 0  and is_mobile = 'Y' group by level;", c);
            val.chuPeople = getLong( PeopleMap , 101 );
            val.zhongPeople = getLong( PeopleMap , 102 );
            val.gaoPeople = getLong( PeopleMap , 103 );
            val.danPeople = getLong( PeopleMap , 104 );
            val.jingjiPeople = getLong( PeopleMap , 201 );

            val.zong = ( val.chujiBet + val.zhongjiBet + val.gaojiBet + val.danBet + val.jingjiBet) - ( val.chujiWin + val.zhongjiWin + val.gaojiWin  + val.danWin + val.jingjiWin ) ;

            return val;
        } );
    }

    public void queryBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getBasicData( req, resp ),
                JichuTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.chuPeople + "</td>" );
                    pw.write( "<td>" + info.chujiBet /10000+ "万</td>" );
                    pw.write( "<td>" + info.chujiWin /10000+ "万</td>" );
                    pw.write( "<td>" + (info.chujiBet - info.chujiWin)/10000 + "万</td>" );
                    pw.write( "<td>" + info.zhongPeople + "</td>" );
                    pw.write( "<td>" + info.zhongjiBet/10000 + "万</td>" );
                    pw.write( "<td>" + info.zhongjiWin /10000+ "万</td>" );
                    pw.write( "<td>" + (info.zhongjiBet - info.zhongjiWin )/10000 + "万</td>" );
                    pw.write( "<td>" + info.gaoPeople + "</td>" );
                    pw.write( "<td>" + info.gaojiBet / 10000 + "万</td>" );
                    pw.write( "<td>" + info.gaojiWin / 10000+ "万</td>" );
                    pw.write( "<td>" + (info.gaojiBet - info.gaojiWin   )/10000 + "万</td>" );
                    pw.write( "<td>" + info.danPeople + "</td>" );
                    pw.write( "<td>" + info.danBet / 10000 + "万</td>" );
                    pw.write( "<td>" + info.danWin / 10000+ "万</td>" );
                    pw.write( "<td>" + (info.danBet - info.danWin  )/10000 + "万</td>" );
                    pw.write( "<td>" + info.jingjiPeople + "</td>" );
                    pw.write( "<td>" + info.jingjiBet / 10000 + "万</td>" );
                    pw.write( "<td>" + info.jingjiWin / 10000+ "万</td>" );
                    pw.write( "<td>" + (info.jingjiBet - info.jingjiWin  )/10000 + "万</td>" );
                    pw.write( "<td>" + info.zong /10000+ "万</td>" );

                } );
    }

    public void downloadBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getBasicData( req, resp ),
                JichuTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.chuPeople + "," );
                    pw.write( info.chujiBet /10000+ "万," );
                    pw.write( info.chujiWin/10000 + "万," );
                    pw.write( (info.chujiBet - info.chujiWin )/10000 + "万," );
                    pw.write( info.zhongPeople + "," );
                    pw.write( info.zhongjiBet /10000+ "万," );
                    pw.write( info.zhongjiWin/10000 + "万," );
                    pw.write( (info.zhongjiBet - info.chujiWin )/10000 + "万," );
                    pw.write( info.gaoPeople + "," );
                    pw.write( info.gaojiBet /10000+ "万," );
                    pw.write( info.gaojiWin /10000+ "万," );
                    pw.write( (info.gaojiBet - info.gaojiWin )/10000 + "万," );
                    pw.write( info.danPeople + "," );
                    pw.write( info.danBet /10000+ "万," );
                    pw.write( info.danWin /10000+ "万," );
                    pw.write( (info.danBet - info.danWin  ) +  "万," );
                    pw.write( info.zong /10000+ "万," );
                } );
    }

    private List< FishView.Huizong > getHuizongData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.Huizong val = new FishView.Huizong();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.totalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' ;", c );

            val.totalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y'  ;", c );

            val.LuckyPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and other_info =2 and is_mobile = 'Y' ;", c )
            -       ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get  " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 2 and is_mobile = 'Y' ;", c );

            val.NewUser = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 4 and is_mobile = 'Y' ;", c )
                    -     ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 4 and is_mobile = 'Y' ;", c ) ;

            val.bigR = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_fish_pool_move " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = 9 ;", c );

            val.zongPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' ;", c );

            long install         = GetItem( beg, end, -1, user_install_grants, c) ;
            long login           = GetItem( beg, end, -1, login_grants, c)+ GetItem( beg, end, -1, login_leiji_grants, c);
            long task            = GetItem( beg, end, -1, day_task_grants, c) + GetItem( beg, end, -1, week_task_grants, c) + GetItem( beg, end, -1, day_act_grants, c) + GetItem( beg, end, -1, week_act_grants, c);
            long onlineRed       = GetItem( beg, end, -1, red_grants, c);
            long upLevel         = GetItem( beg, end, -1, user_up_grants, c) + GetItem( beg, end, -1, gun_up_grants, c);
            long broke           = GetItem( beg, end, -1, broke_grants, c);
            long phone           = GetItem( beg, end, -1, phone_grants, c);
            long gengxin         = GetItem( beg, end, -1, update_grants, c);
            long cdkey           = GetItem( beg, end, -1, cdkey_grants, c);
            long VIPLogin        = GetItem( beg, end,  -1,vip_login_give_grants, c)  ;
            long vip_up          = GetItem( beg, end,  -1,vip_up_grants, c) ;
            long Pay_jiangchi    = GetItem( beg, end,  -1,pay_diamond_grants, c) ;
            long captain         = GetItem( beg, end,  -1,captain_grants, c) +GetItem( beg, end, -1, week_grants, c) ;
            long first_pay       = GetItem( beg, end,  -1, firstRecharge_pay_grants,c) ;
            long caibeiFish      = GetItem( beg, end,  -1,caiyu_fish_get, c) ;


            //            val.gongcechoujiang = SumItem( beg, end, beta_lottery_grants, -1, c)   + SumItemVip( beg, end, beta_lottery_grants, -1, c);
            //            val.huoyue          = SumItem( beg, end, day_act_grants, -1, c)             + SumItem( beg, end, week_act_grants, -1, c)            + SumItemVip( beg, end, day_act_grants, -1, c) + SumItemVip( beg, end, week_act_grants, -1, c);


            List<String> qipaoFish  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + qipao_fish_get +" and is_mobile = 'Y' ;", c );
            Map< Long, Long > qipaoMap = new HashMap<>();
            qipaoFish.forEach( info -> mergeMap( splitMap( info, ";", ":" ), qipaoMap ) );
            val.zhuanpandaoju = qipaoMap;
            long zhuanpan = getLong(qipaoMap , -1) ;

            List<String> chongzhi  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + pay_huikui_grants +" and is_mobile = 'Y' ;", c );
            Map< Long, Long > chongzhiMap = new HashMap<>();
            chongzhi.forEach( info -> mergeMap( splitMap( info, ";", ":" ), chongzhiMap ) );
            val.chongzhiDaoju = chongzhiMap;
            long chongzhi1 = getLong(chongzhiMap , -1) + getLong(chongzhiMap , -4) * 4500 +  getLong(chongzhiMap , 20015 ) * 20000 + getLong(chongzhiMap , 20016 ) * 200000 + getLong(chongzhiMap , 20017 ) * 1000000  ;

            List<String> leiji1  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + leijichongzhi_grants +" and is_mobile = 'Y' ;", c );
            Map< Long, Long > leijiMap = new HashMap<>();
            leiji1.forEach( info -> mergeMap( splitMap( info, ";", ":" ), leijiMap ) );
            val.leijiDaoju = leijiMap;
            long leijichongzhi = getLong(leijiMap , -1) + getLong(leijiMap , -4) * 4500 + getLong(leijiMap , 20015 ) * 20000 + getLong(leijiMap , 20016 ) * 200000 + getLong(leijiMap , 20017 ) * 1000000;

            List<String> Getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and  activity_id =1 ;", c );
            Map< Long, Long > getMap = new HashMap<>();
            Getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
            long qifu   = getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000 + getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000 + getLong(getMap , 10507 ) * 10000
                    + getLong(getMap , 20014 ) * 0 +  getLong(getMap , 10512 ) * 1000000 + getLong(getMap , -4 ) * 4500 ;

            List<String> boss  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in (" + world_boss_die_grants +"," + world_boss_rank_grants +" ) and is_mobile = 'Y' ;", c );
            Map< Long, Long > bossMap = new HashMap<>();
            boss.forEach( info -> mergeMap( splitMap( info, ";", ":" ), bossMap ) );
            val.bossdaoju = bossMap;
            long boss1 = getLong(bossMap , -1) + getLong(bossMap , 20015 ) * 20000 + getLong(bossMap , 20016 ) * 200000 + getLong(bossMap , 20017 ) * 1000000 ;

            List<String> jingji  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in (" + jingji_day_rank_grants +"," + jingji_jifen_grants +"," + jingji_task_grants + "," + jingji_week_rank_grants +" ) and is_mobile = 'Y' ;", c );
            Map< Long, Long > jingjiMap = new HashMap<>();
            jingji.forEach( info -> mergeMap( splitMap( info, ";", ":" ), jingjiMap ) );
            val.jingjidaoju = jingjiMap;
            long jingji1 = getLong(jingjiMap , -1)+getLong(jingjiMap , -4) * 4500  + getLong(jingjiMap , 20015 ) * 20000 + getLong(jingjiMap , 20016 ) * 200000 + getLong(jingjiMap , 20017 ) * 1000000 ;

            List<String> free  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in (" + poker_free_grants + " ) and is_mobile = 'Y' ;", c );
            Map< Long, Long > freemap = new HashMap<>();
            free.forEach( info -> mergeMap( splitMap( info, ";", ":" ), freemap ) );

            long min  = ServiceManager.getSqlService().queryLong( "select min(inning_id) " +
                    " from t_fish_poker " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and level = 404 ;", c );
            long max  = ServiceManager.getSqlService().queryLong( "select max(inning_id) " +
                    " from t_fish_poker " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and level = 404 ;", c );
            long poker = getLong(freemap , -1)
                    - ( max - min + 1 ) * 1000000
                    + ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_mb_grants where " + beg + " <= timestamp and timestamp < " + end  +
                    " and type = "+ FishMBAction.poker_dantou_grants +";", c ) * 1000000 ;
            long tuiguang      = GetItem( beg, end, -1,tuiguang_bangding_grants,  c)+ GetItem( beg, end, -1,tuiguang_jifen_grants,  c) ;

            List<String> duihuanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_fish_get_ietms " +
                    " where "    + beg + " <= timestamp and timestamp < " + end + "  ;", c );
            Map< Long, Long > duihuangetMap = new HashMap<>();
            duihuanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), duihuangetMap ) );
//            long duihuan   = getLong(duihuangetMap , -1) + getLong(duihuangetMap , 20015 ) * 20000 + getLong(duihuangetMap , 20016 ) * 200000 + getLong(duihuangetMap , 20017 ) * 1000000 + getLong(duihuangetMap , 10507 ) * 10000
//                    +  getLong(duihuangetMap , 10512 ) * 1000000 +   getLong(duihuangetMap , -4 ) * 3000;
            long baoxiang =  getLong(duihuangetMap , 20033 ) * (ItemManager.getInstance().get( 20033 ).worth) + getLong(duihuangetMap , 20035 ) * (ItemManager.getInstance().get( 20035 ).worth)
                    + getLong(duihuangetMap , 20037 ) * (ItemManager.getInstance().get( 20037 ).worth);

            val.GoldOut     = install + task + login + onlineRed + upLevel + broke + phone
                    + gengxin +  cdkey  + jingji1 + boss1  + first_pay + leijichongzhi
                    + captain + VIPLogin + vip_up + poker + tuiguang + baoxiang - ServiceManager.getSqlService().queryLong("select sum(cost_count) from t_newactivity where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 9 ;",c);

            val.GM_Lucky    = ServiceManager.getSqlService().queryLong("select sum(gold) from t_fish_gm where " + beg + " <= timestamp and timestamp < " + end + " and other_info != '';",c);
            val.GM_single   = ServiceManager.getSqlService().queryLong("select sum(gold) from t_fish_gm where " + beg + " <= timestamp and timestamp < " + end + " and other_info = '' and user_id != 0 ;",c);
            val.singleUser  = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' and other_info = 3 ;", c )
                    - ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' and other_info = 3 ;", c );
            val.winPump = val.totalBet - val.totalWin - val.GoldOut;

            return val;
        } );
    }

    public void queryHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH_MB, getHuizongData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zongPeople  + "</td>" );
                    pw.write( "<td>" + info.totalBet /10000+ "万</td>" );
                    pw.write( "<td>" + info.totalWin /10000+ "万</td>" );
                    pw.write( "<td>" + info.LuckyPay /10000+ "万</td>" );
                    pw.write( "<td>" + info.GM_Lucky /10000+ "万</td>" );
                    pw.write( "<td>" + info.singleUser /10000+ "万</td>" );
                    pw.write( "<td>" + info.bigR /10000+ "万</td>" );
                    pw.write( "<td>" + info.GM_single /10000+ "万</td>" );
                    pw.write( "<td>" + info.NewUser /10000+ "万</td>" );
                    pw.write( "<td>" + info.GoldOut /10000+ "万</td>" );
                    pw.write( "<td>" + info.winPump  /10000+ "万</td>" );
                });
    }

    public void downloadHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH_MB, getHuizongData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zongPeople + "," );
                    pw.write( info.totalBet /10000 + "万," );
                    pw.write( info.totalWin /10000+ "万," );
                    pw.write( info.LuckyPay/10000 + "万," );
                    pw.write( info.GM_Lucky/10000 + "万," );
                    pw.write( info.singleUser /10000+ "万," );
                    pw.write( info.bigR /10000+ "万," );
                    pw.write( info.GM_single /10000+ "万," );
                    pw.write( info.NewUser /10000+ "万," );
                    pw.write( info.GoldOut/10000 + "万," );
                    pw.write( info.winPump /10000+ "万," );
                } );
    }

    //游戏币支出汇总
    public List< FishView.GameGold > getGameGoldOutData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.GameGold val = new FishView.GameGold();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.install         = GetItem( beg, end, -1, user_install_grants, c);
            val.login           = GetItem( beg, end, -1, login_grants, c) + GetItem( beg, end, -1, login_leiji_grants, c);
            val.task            = GetItem( beg, end, -1, day_task_grants, c) + GetItem( beg, end, -1, week_task_grants, c) + GetItem( beg, end, -1, day_act_grants, c) + GetItem( beg, end, -1, week_act_grants, c);
            val.onlineRed       = GetItem( beg, end, -1, red_grants, c);
            val.upLevel         = GetItem( beg, end, -1, user_up_grants, c) + GetItem( beg, end, -1, gun_up_grants, c);
            val.broke           = GetItem( beg, end, -1, broke_grants, c);
            val.phone           = GetItem( beg, end, -1, phone_grants, c);
            val.gengxin         = GetItem( beg, end, -1, update_grants, c);
            val.cdkey           = GetItem( beg, end, -1, cdkey_grants, c);
            val.VIPLogin        = GetItem( beg, end,  -1,vip_login_give_grants, c)  ;
            val.vip_up          = GetItem( beg, end, -1, vip_up_grants, c) ;
            val.Pay_jiangchi    = GetItem( beg, end, -1,  pay_diamond_grants,c) ;
            val.captain         = GetItem( beg, end,  -1,captain_grants, c) +GetItem( beg, end, -1,  week_grants, c) ;
            val.first_pay       = GetItem( beg, end, -1, firstRecharge_pay_grants, c) ;
            val.tuiguang      = GetItem( beg, end, -1,tuiguang_bangding_grants,  c)+ GetItem( beg, end, -1,tuiguang_jifen_grants,  c) ;

            long gold33 = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_get_ietms where " + beg + " <= timestamp and timestamp < " + end  +
                    " and cost_item_id = 20033;", c ) * 2000000 ;
            long gold35 = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_get_ietms where " + beg + " <= timestamp and timestamp < " + end  +
                    " and cost_item_id = 20035;", c ) * 5000000 ;
            long gold37 = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_get_ietms where " + beg + " <= timestamp and timestamp < " + end  +
                    " and cost_item_id = 20037;", c ) * 20000000 ;
            val.baoxiang =  ServiceManager.getSqlService().queryLong( "select sum( coalesce( getValue2(get_info,\"20017\"), 0 ) )  from t_fish_get_ietms where " + beg + " <= timestamp and timestamp < " + end  +
                    " and cost_item_id in (20035,20033,20037);", c ) * 1000000 - gold33 - gold35 - gold37;

            List<String> duihuanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_fish_get_ietms " +
                    " where "    + beg + " <= timestamp and timestamp < " + end + " ;", c );
            Map< Long, Long > duihuangetMap = new HashMap<>();
            duihuanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), duihuangetMap ) );
            val.duihuan   = getLong(duihuangetMap , -1) + getLong(duihuangetMap , 20015 ) * 20000 + getLong(duihuangetMap , 20016 ) * 200000 + getLong(duihuangetMap , 20017 ) * 1000000 + getLong(duihuangetMap , 10507 ) * 10000
                    +  getLong(duihuangetMap , 10512 ) * 1000000 +   getLong(duihuangetMap , -4 ) * 3000;

            List<String> chongzhi  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + pay_huikui_grants +" and is_mobile = 'Y' ;", c );
            Map< Long, Long > chongzhiMap = new HashMap<>();
            chongzhi.forEach( info -> mergeMap( splitMap( info, ";", ":" ), chongzhiMap ) );
            val.chongzhiDaoju = chongzhiMap;
            val.chongzhi = getLong(chongzhiMap , -1) + getLong(chongzhiMap , -4) * 4500 + getLong(chongzhiMap , 20015 ) * 20000 + getLong(chongzhiMap , 20016 ) * 200000 + getLong(chongzhiMap , 20017 ) * 1000000 ;

            List<String> leiji  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type = " + leijichongzhi_grants +" and is_mobile = 'Y' ;", c );
            Map< Long, Long > leijiMap = new HashMap<>();
            leiji.forEach( info -> mergeMap( splitMap( info, ";", ":" ), leijiMap ) );
            val.leijiDaoju = leijiMap;
            val.leijichongzhi = getLong(leijiMap , -1) + getLong(leijiMap , -4) * 4500 +  getLong(leijiMap , 20015 ) * 20000 + getLong(leijiMap , 20016 ) * 200000 + getLong(leijiMap , 20017 ) * 1000000 ;

            List<String> Getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and  activity_id =1 ;", c );
            Map< Long, Long > getMap = new HashMap<>();
            Getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
            val.qifu   = getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000 + getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000 + getLong(getMap , 10507 ) * 10000
                    + getLong(getMap , 20014 ) * 0 +  getLong(getMap , 10512 ) * 1000000 +  getLong(getMap , -4 ) * 4500 ;

            List<String> Getinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and  activity_id = 4 ;", c );
            Map< Long, Long > getMap2 = new HashMap<>();
            Getinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );
            val.wanshengjie   = getLong(getMap2 , -1) + getLong(getMap2 , 20015 ) * 20000 + getLong(getMap2 , 20016 ) * 200000 + getLong(getMap2 , 20017 ) * 1000000 + getLong(getMap2 , 10507 ) * 10000
                    + getLong(getMap2 , 20014 ) * 0 +  getLong(getMap2 , 10512 ) * 1000000 +  getLong(getMap2 , -4 ) * 4500 ;

            List<String> zadan = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and  activity_id = 8 ;", c );
            Map< Long, Long > zadanGetMap2 = new HashMap<>();
            zadan.forEach( info -> mergeMap( splitMap( info, ";", ":" ), zadanGetMap2 ) );
            val.zadan   = getLong(zadanGetMap2 , -1) + getLong(zadanGetMap2 , 20015 ) * 20000 + getLong(zadanGetMap2 , 20016 ) * 200000 + getLong(zadanGetMap2 , 20017 ) * 1000000 + getLong(zadanGetMap2 , 10507 ) * 10000
                    + getLong(zadanGetMap2 , 20014 ) * 0 +  getLong(zadanGetMap2 , 10512 ) * 1000000 +  getLong(zadanGetMap2 , -4 ) * 4500 ;

            List<String> dafuwengGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and  activity_id = 5 ;", c );
            Map< Long, Long > dafuwengGetMap = new HashMap<>();
            dafuwengGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), dafuwengGetMap ) );
            val.dafuweng   = getLong(dafuwengGetMap , -1) + getLong(dafuwengGetMap , 20015 ) * 20000 + getLong(dafuwengGetMap , 20016 ) * 200000 + getLong(dafuwengGetMap , 20017 ) * 1000000 + getLong(dafuwengGetMap , 10507 ) * 10000
                    + getLong(dafuwengGetMap , 20014 ) * 0 +  getLong(dafuwengGetMap , 10512 ) * 1000000 +  getLong(dafuwengGetMap , -4 ) * 4500 ;

            List<String> boss  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in (" + world_boss_die_grants +"," + world_boss_rank_grants +" ) and is_mobile = 'Y' ;", c );
            Map< Long, Long > bossMap = new HashMap<>();
            boss.forEach( info -> mergeMap( splitMap( info, ";", ":" ), bossMap ) );
            val.bossdaoju = bossMap;
            val.boss = getLong(bossMap , -1) + getLong(bossMap , 20015 ) * 20000 + getLong(bossMap , 20016 ) * 200000 + getLong(bossMap , 20017 ) * 1000000 ;

            List<String> jingji  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in (" + jingji_day_rank_grants +"," + jingji_jifen_grants +"," + jingji_task_grants + "," + jingji_week_rank_grants +" ) and is_mobile = 'Y' ;", c );
            Map< Long, Long > jingjiMap = new HashMap<>();
            jingji.forEach( info -> mergeMap( splitMap( info, ";", ":" ), jingjiMap ) );
            val.jingjidaoju = jingjiMap;
            val.jingji = getLong(jingjiMap , -1)  + getLong(jingjiMap , -4 ) * 4500 + getLong(jingjiMap , 20015 ) * 20000 + getLong(jingjiMap , 20016 ) * 200000 + getLong(jingjiMap , 20017 ) * 1000000 ;

            List<String> free  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in (" + poker_free_grants + " ) and is_mobile = 'Y' ;", c );
            Map< Long, Long > freemap = new HashMap<>();
            free.forEach( info -> mergeMap( splitMap( info, ";", ":" ), freemap ) );
            Map< Long, Long > countInfo = ServiceManager.getSqlService().queryMapLongLong( "select level,count(id) from t_fish_poker where " + beg + " <= timestamp and timestamp < " + end  +
                    " group by level;", c );

//            long min  = ServiceManager.getSqlService().queryLong( "select min(inning_id) " +
//                    " from t_fish_poker " +
//                    " where " + beg + " <= timestamp and timestamp < " + end + " and level = 404 ;", c );
//            long max  = ServiceManager.getSqlService().queryLong( "select max(inning_id) " +
//                    " from t_fish_poker " +
//                    " where " + beg + " <= timestamp and timestamp < " + end + " and level = 404 ;", c );

//            val.poker = getLong(freemap , -1)
//                    -   (max - min + 1)*1000000
//                    + ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_mb_grants where " + beg + " <= timestamp and timestamp < " + end  +
//                    " and type = "+ FishMBAction.poker_dantou_grants +";", c ) * 1000000 ;

            val.poker = ServiceManager.getSqlService().queryLong( "select count(id) from t_fish_mb_grants where " + beg + " <= timestamp and timestamp < " + end  +
                    " and type = "+ FishMBAction.poker_dantou_grants +";", c ) * 1000000 ;

            val.zongOut   = val.install + val.task + val.login + val.onlineRed + val.upLevel + val.broke + val.phone
                    + val.gengxin +  val.cdkey  + val.jingji + val.boss + val.first_pay + val.leijichongzhi + val.captain
                    + val.VIPLogin + val.vip_up + val.poker + val.tuiguang + val.baoxiang;
            //            + getLong( leijiMap , -1 ) + getLong( leijiMap , -4  ) * 2000 + getLong(leijiMap , 10544 ) * 625
            //            + getLong( chongzhiMap , -1 ) + getLong( chongzhiMap , -4  ) * 2000 + getLong(chongzhiMap , 10544 ) * 625
            //            + getLong( qipaoMap , -1 ) + getLong( qipaoMap , -4  ) * 2000 + getLong(qipaoMap , 10544 ) * 625
            //            + getLong( duihuanMap , -1 ) + getLong( duihuanMap , -4  ) * 2000 + getLong(duihuanMap , 10544 ) * 625
            //            + getLong( bangdingMap , -1 ) + getLong( bangdingMap , -4  ) * 2000 + getLong(bangdingMap , 10544 ) * 625;

            long totalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' ;", c );

            long totalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y'  ;", c );

//            long jackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
//                    " from t_fish_jackpot " +
//                    " where " + beg + " <= timestamp and timestamp < " + end + " and other_info  = 1 and gold > 0 and is_mobile = 'Y' ;", c );
            val.zongIn     = totalBet - totalWin ;

            val.newUserBuzhu = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' and other_info = 4 ;", c )
                    - ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and is_mobile = 'Y' and other_info = 4 ;", c );

            Map< Long, Long > user_buy_item = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum(item_count) " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by item_id;", c );

            Map< Long, Long > chizi_info = ServiceManager.getSqlService().queryMapLongLong( "select type,sum(gold) from t_fish_pool_move  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by type;", c );
            val.chongzhichoujiang = getLong(chizi_info,11) + getLong(chizi_info,10);
            val.big_r = getLong(chizi_info,9);

            val.shopOut = getLong(user_buy_item ,20110 ) * (ItemManager.getInstance().get( 20110 ).worth)
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

            val.tehui = getLong(user_buy_item ,20022 ) * (ItemManager.getInstance().get( 20022 ).worth + ItemManager.getInstance().get( 20022 ).buyAddGold)
                    + getLong(user_buy_item ,20023 ) * (ItemManager.getInstance().get( 20023 ).worth + ItemManager.getInstance().get( 20023 ).buyAddGold)
                    + getLong(user_buy_item ,20030 ) * (ItemManager.getInstance().get( 20030 ).worth + ItemManager.getInstance().get( 20030 ).buyAddGold)
                    + getLong(user_buy_item ,20031 ) * (ItemManager.getInstance().get( 20031 ).worth + ItemManager.getInstance().get( 20031 ).buyAddGold)
            ;



            return val;
        } );
    }

    public void queryGameGold( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_FISH_MB, getGameGoldOutData( req, resp ),
                goldOutTableHead1, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin     + "</td>" );
                    pw.write( "<td>" + info.install  /10000   + "万</td>" );
                    pw.write( "<td>" + info.login   /10000    + "万</td>" );
                    pw.write( "<td>" + info.task   /10000    + "万</td>" );
                    pw.write( "<td>" + info.onlineRed   /10000   + "万</td>" );
                    pw.write( "<td>" + info.upLevel   /10000    + "万</td>" );
                    pw.write( "<td>" + info.broke  /10000   + "万</td>" );
                    pw.write( "<td>" + info.phone /10000  + "万</td>" );
                    pw.write( "<td>" + info.gengxin  /10000     + "万</td>" );
                    pw.write( "<td>" + info.tuiguang  /10000     + "万</td>" );
                    pw.write( "<td>" + info.cdkey /10000      + "万</td>" );
                    pw.write( "<td>" + info.jingji   /10000      + "万</td>" );
                    pw.write( "<td>" + info.boss  /10000  + "万</td>" );
                    pw.write( "<td>" + info.poker  /10000   + "万</td>" );
                    pw.write( "<td>" + info.leijichongzhi   /10000  + "万</td>" );
                    pw.write( "<td>" + info.first_pay   /10000  + "万</td>" );
                    pw.write( "<td>" + info.baoxiang   /10000  + "万</td>" );
                    pw.write( "<td>" + info.captain  /10000  + "万</td>" );
                    pw.write( "<td>" + info.VIPLogin   /10000  + "万</td>" );
                    pw.write( "<td>" + info.vip_up  /10000  + "万</td>" );
                    pw.write( "<td>" + info.zongOut  /10000   + "万</td>" );
                    pw.write( "<td>" + info.zongIn    /10000+ "万</td>" );
                    pw.write( "<td>" + (info.zongIn-info.zongOut)/10000   + "万</td>" );
                } ,getGameGoldOutData( req, resp ),
                goldOutTableHead2, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin     + "</td>" );
                    pw.write( "<td>" + info.duihuan  /10000    + "万</td>" );
                    pw.write( "<td>" + info.qifu  /10000    + "万</td>" );
                    pw.write( "<td>" + info.wanshengjie  /10000    + "万</td>" );
                    pw.write( "<td>" + info.dafuweng  /10000    + "万</td>" );
                    pw.write( "<td>" + info.zadan  /10000    + "万</td>" );
                    pw.write( "<td>" + info.chongzhichoujiang  /10000    + "万</td>" );
                    pw.write( "<td>" + info.big_r  /10000    + "万</td>" );
                    pw.write( "<td>" + info.shopOut  /10000    + "万</td>" );
                    pw.write( "<td>" + info.tehui /10000   + "万</td>" );
                } ,null );
    }

    public void downloadGameGold( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_FISH_MB, getGameGoldOutData( req, resp ),
                goldOutTableHead1, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin     + "," );
                    pw.write( "<td>" + info.install  /10000   + "万," );
                    pw.write( "<td>" + info.login   /10000    + "万," );
                    pw.write( "<td>" + info.task   /10000    + "万," );
                    pw.write( "<td>" + info.onlineRed   /10000   + "万," );
                    pw.write( "<td>" + info.upLevel   /10000    + "万," );
                    pw.write( "<td>" + info.broke  /10000   + "万," );
                    pw.write( "<td>" + info.phone /10000  + "万," );
                    pw.write( "<td>" + info.gengxin  /10000     + "万," );
                    pw.write( "<td>" + info.cdkey /10000      + "万," );
                    pw.write( "<td>" + info.jingji   /10000      + "万," );
                    pw.write( "<td>" + info.boss  /10000  + "万," );
                    pw.write( "<td>" + info.poker  /10000   + "万," );
                    pw.write( "<td>" + info.leijichongzhi   /10000  + "万," );
                    pw.write( "<td>" + info.first_pay   /10000  + "万," );
                    pw.write( "<td>" + info.qifu  /10000  + "万," );
                    pw.write( "<td>" + info.captain  /10000  + "万," );
                    pw.write( "<td>" + info.VIPLogin   /10000  + "万," );
                    pw.write( "<td>" + info.vip_up  /10000  + "万," );
                    pw.write( "<td>" + info.zongOut  /10000   + "万," );
                    pw.write( "<td>" + info.zongIn    /10000+ "万," );
                    pw.write( "<td>" + (info.zongIn-info.zongOut)/10000   + "万," );
                },getGameGoldOutData( req, resp ),
                goldOutTableHead2, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin     + "," );
                    pw.write( "<td>" + info.duihuan  /10000    + "万," );
                    pw.write( "<td>" + info.qifu  /10000    + "万," );
                    pw.write( "<td>" + info.wanshengjie  /10000    + "万," );
                    pw.write( "<td>" + info.zadan  /10000    + "万," );
                    pw.write( "<td>" + info.chongzhichoujiang  /10000    + "万," );
                    pw.write( "<td>" + info.big_r /10000    + "万," );
                    pw.write( "<td>" + info.shopOut  /10000    + "万," );
                    pw.write( "<td>" + info.tehui /10000   + "万," );
                } );
    }

    private List< List< FishView.Rank > > getRankData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        int limit = Integer.parseInt( req.getParameter( "limit" ) );
        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > betMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' group by user_id;", c );

            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' group by user_id;", c );

            Map< Long, Long > LuckyBetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' and other_info = 2  group by user_id;", c );

            Map< Long, Long > LuckyGetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' and other_info = 2 group by user_id;", c );

            Map< Long, Long > NewUserBetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' and other_info in ( 4 ) group by user_id;", c );

            Map< Long, Long > NewUserGetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_mobile = 'Y' and other_info in ( 4 ) group by user_id;", c );

            Map< Long, FishView.Rank > sum = new HashMap<>();

            betMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).totalBet = v;
            } );

            winMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).totalWin = v;
            } );

            LuckyBetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).LuckyJackpot -= v;
            } );

            LuckyGetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).LuckyJackpot += v;
            } );

            NewUserBetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).newUser -= v;
            } );

            NewUserGetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).newUser += v;
            } );



            List< FishView.Rank > winList = new ArrayList<>();
            List< FishView.Rank > loseList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.userId = k;
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

    public void queryRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_FISH_MB, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.totalWin + "</td>" );
                    pw.write( "<td>" + info.LuckyJackpot + "</td>" );
                    pw.write( "<td>" + info.newUser + "</td>" );
                    pw.write( "<td>" + info.netWin + "</td>" );
                }, getFishUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_FISH_MB, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.totalWin + "," );
                    pw.write( info.LuckyJackpot + "," );
                    pw.write( info.newUser + "," );
                    pw.write( info.netWin + "," );
                } );
    }

    private List< List< FishView.dantouFish > > getDanTouFishData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > chuxianMap = ServiceManager.getSqlService().queryMapLongLong( "select fish_multiple, count(id) " +
                    " from t_fish_dantoufish " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and type = 1 group by fish_multiple;", c );

            Map< Long, Long > dasiMap = ServiceManager.getSqlService().queryMapLongLong( "select fish_multiple, count(id) " +
                    " from t_fish_dantoufish " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and type = 2 group by fish_multiple;", c );


            Map< Long, FishView.dantouFish > sum = new HashMap<>();

            chuxianMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.dantouFish() );
                sum.get( k ).chuxian = v;
                List<String> Getinfo = ServiceManager.getSqlService().queryListString( "select drops " +
                        " from t_fish_dantoufish " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and fish_multiple = " + k + " ;", c );

                Map< Long, Long > getMap2 = new HashMap<>();

                Getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );
                sum.get(k).jin = getLong(getMap2,20017);
                sum.get(k).yin = getLong(getMap2,20016);
            } );

            dasiMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.dantouFish() );
                sum.get( k ).jisha = v;
            } );


            List< FishView.dantouFish > dantouList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.fishId = k;
                dantouList.add( v );
            } );

            Collections.sort( dantouList, ( o1, o2 ) -> {
                if( o1.jin > o2.jin ) return -1;
                if( o1.jin  < o2.jin ) return 1;
                if( o1.chuxian < o2.chuxian ) return -1;
                if( o1.chuxian > o2.chuxian ) return 1;
                return 0;
            } );


            return Arrays.asList( dantouList );
        } );
    }

    public void queryDanTouFish( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_FISH_MB, getDanTouFishData( req, resp ),
                DanTouFishTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.fishId + "</td>" );
                    pw.write( "<td>" + info.chuxian + "</td>" );
                    pw.write( "<td>" + info.jisha + "</td>" );
                    pw.write( "<td>" + info.jin + "</td>" );
                    pw.write( "<td>" + info.yin  + "</td>" );
                },null );
    }

    public void downloadDanTouFish( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_FISH_MB, getDanTouFishData( req, resp ),
                DanTouFishTableHead, ( info, pw ) -> {
                    pw.write(  info.fishId + "," );
                    pw.write(  info.chuxian + "," );
                    pw.write(  info.jisha + "," );
                    pw.write(  info.jin + "," );
                    pw.write(  info.yin  + "," );
                } );
    }

    private List< FishView.bigR > getbigRZongData( HttpServletRequest req, HttpServletResponse resp ,String type)
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.bigR val = new FishView.bigR();
            val.begin   = TimeUtil.ymdFormat().format( beg );
            val.end     = TimeUtil.ymdFormat().format( end );
            val.people =  ServiceManager.getSqlService().queryLong( "select count(distinct user_id) " +
                    " from t_fish_pool_move " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in( "+ type +" ) and user_id not in (select distinct user_id from t_install where from_where = 3311) and gold != 0;", c );
            val.count =  ServiceManager.getSqlService().queryLong( "select count(id) " +
                    " from t_fish_pool_move " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in ( "+ type +" ) and user_id not in (select distinct user_id from t_install where from_where = 3311) and gold != 0;", c );
            val.count2 =  ServiceManager.getSqlService().queryLong( "select count(id) " +
                    " from t_fish_pool_move " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where from_where = 3311) and type in ( "+ type +" ) ;", c );
            val.gold =  ServiceManager.getSqlService().queryLong( "select sum(gold) " +
                    " from t_fish_pool_move " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and user_id not in (select distinct user_id from t_install where from_where = 3311) and type in ("+ type +" );", c );
            val.chongzhi =  ServiceManager.getSqlService().queryLong( "select sum(money2)/10000 " +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and user_id in ( select distinct user_id from t_fish_pool_move  " +
                    "where " + beg + " <= timestamp and timestamp < " + end + " and type in( "+ type +" ) and user_id not in (select distinct user_id from t_install where from_where = 3311) );", c );
            if (val.chongzhi ==0)
                val.chongzhi = 1;

            return val;
        } );

    }



    private  List< FishView.bigR  > getbigRData(HttpServletRequest req, HttpServletResponse resp,String type )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, FishView.bigR > sum = new HashMap<>();
            List<FishView.bigR> a = new LinkedList<>();

            Map<Long, Long> countMap = ServiceManager.getSqlService().queryMapLongLong("select user_id,count(id)" +
                    " from t_fish_pool_move " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and type in ( " + type + " ) and gold != 0 and user_id not in (select distinct user_id from t_install where from_where = 3311) group by user_id;", c);
            Map<Long, Long> goldMap = ServiceManager.getSqlService().queryMapLongLong("select user_id,sum(gold)" +
                    " from t_fish_pool_move " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and type in ( " + type + " ) and user_id not in (select distinct user_id from t_install where from_where = 3311) group by user_id;", c);

            countMap.forEach( ( k, v ) -> {
                FishView.bigR val = new FishView.bigR();
                val.begin   = TimeUtil.ymdFormat().format( beg );
                val.count = v;
                val.user_id = k;
                val.chongzhi  = ServiceManager.getSqlService().queryLong( "select sum(money2 + money3) " +
                        " from t_pay " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id = "+ k +" ;", c );
                val.user_name = UserSdk.getUserName(k);
                val.begin   = TimeUtil.ymdFormat().format( beg );
                val.gold = goldMap.get(k);
                a.add(val);
            } );

            return a;
        } );
    }

    public void querybigR( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List<FishView.bigR> data  = getbigRZongData( req, resp,"9" );

        queryMultiList( req, resp,
                CommandList.GET_FISH_MB, data,
                bigRTableHead1, ( info, pw ) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.people + "</td>");
                    pw.write("<td>" + info.count + "</td>");
                    pw.write("<td>" + info.gold + "</td>");
                },getbigRData( req, resp ,"9" ),
                bigRTableHead2, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write("<td>" + info.user_id + "</td>");
                    pw.write( "<td>" + info.user_name + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + Currency.format( info.chongzhi )+ "</td>" );
                }, null );

    }

    public void querychongzhi( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List<FishView.bigR> data  = getbigRZongData( req, resp,"10,11,12" );

        queryMultiList( req, resp,
                CommandList.GET_FISH_MB, data,
                bigRTableHead1, ( info, pw ) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.people + "</td>");
                    pw.write("<td>" + info.count + "</td>");
                    pw.write("<td>" + info.gold + "</td>");
                    pw.write("<td>" + info.gold/info.chongzhi + "</td>");
                },getbigRData( req, resp ,"10,11,12" ),
                bigRTableHead2, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write("<td>" + info.user_id + "</td>");
                    pw.write( "<td>" + info.user_name + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                    pw.write( "<td>" + Currency.format( info.chongzhi )+ "</td>" );
                }, null );

    }


    private List< List< FishView.CCC > > getCCCRankData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        int limit = Integer.parseInt( req.getParameter( "limit" ) );
        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > cishuMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, count( id ) " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and activity_id = 9 and cost_id = -1 group by user_id;", c );
            Map< Long, Long > jinbiBetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( cost_count ) " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and activity_id = 9 and cost_id = -1 group by user_id;", c );

            Map< Long, FishView.CCC > sum = new HashMap<>();

            cishuMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.CCC() );
                {sum.get( k ).count = v;}
                List<String> zongchanchu  = ServiceManager.getSqlService().queryListString( "select get_info " +
                        " from t_newactivity " +  " where " + beg + " <= timestamp and timestamp < " + end + " and user_id = " + k +" and activity_id = 9 and cost_id = -1;", c );
                Map< Long, Long > chanchuMap = new HashMap<>();
                zongchanchu.forEach( info -> mergeMap( splitMap( info, ";", ":" ), chanchuMap ) );
                sum.get( k ).jifenchanchu = getLong(chanchuMap , -12 );
            } );

            jinbiBetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.CCC() );
                sum.get( k ).totalBet = v;

                List<String> duihuan  = ServiceManager.getSqlService().queryListString( "select get_info " +
                        " from t_fish_get_ietms " +  " where " + beg + " <= timestamp and timestamp < " + end + " and user_id = " + k +" and type = 9 and cost_item_id = -12;", c );
                Map< Long, Long > duihuanMap = new HashMap<>();
                duihuan.forEach( info -> mergeMap( splitMap( info, ";", ":" ), duihuanMap ) );
                sum.get( k ).duihuan = duihuanMap;
                sum.get( k ).duihuanxiaohao =   ServiceManager.getSqlService().queryLong( "select sum( cost_item_count ) " +
                        " from t_fish_get_ietms " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id = "+ k +" and type = 9 and cost_item_id = -12 ;", c );

                sum.get( k ).kaidantou =  ServiceManager.getSqlService().queryLong( "select sum( coalesce( getValue2(get_info,'20017'), 0 ) ) " +
                        " from t_fish_get_ietms " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and user_id = "+ k +" and cost_item_id in( 20033 ,20035 ,20037 ) ;", c );

            } );

            List< FishView.CCC > winList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.userId = k;
                v.user_name = UserSdk.getUserName(k);
                 winList.add( v );
            } );

            Collections.sort( winList, ( o1, o2 ) -> {
                if( o1.jifenchanchu < o2.jifenchanchu ) return 1;
                if( o1.jifenchanchu > o2.jifenchanchu ) return -1;
                if( o1.totalBet < o2.totalBet ) return -1;
                if( o1.totalBet > o2.totalBet ) return 1;
                return 0;
            } );

            return Arrays.asList( winList );
        } );
    }

    public void queryCCCRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_FISH_MB, getCCCRankData( req, resp ),
                CCCrankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" + info.user_name + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.totalBet / 10000 + "万</td>" );
                    pw.write( "<td>" + info.jifenchanchu + "</td>" );
                    pw.write( "<td>" + info.duihuanxiaohao + "</td>" );
                    pw.write( "<td>" );
                    info.duihuan.forEach( ( itemId, itemCount ) -> {
                        if (itemId == -1 )
                            pw.write("金币:" + itemCount + "<br>" );
                        else
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" + formatRatio((double)(info.totalBet -  info.jifenchanchu * 500) / (double)info.totalBet)+ "</td>" );
                    pw.write( "<td>" + info.kaidantou + "</td>" );
                }, null );
    }


}