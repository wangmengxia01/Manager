package com.hoolai.ccgames.bifront.vo;

import java.util.*;

public class MjdrView {

    public static class HuaFei{
        public String   begin   = "";
        public String   end     = "";
        public long     Gold    = 0;    //元宝支出
        public long     baotu   = 0;    //宝图支出
        public long     card1   = 0;    //一元话费支出
        public long     card2   = 0;    //一元话费支出
        public long     card5   = 0;    //一元话费支出
        public long     card10   = 0;    //一元话费支出
        public long     card20   = 0;    //一元话费支出
        public long     card50   = 0;    //一元话费支出
        public long     card100   = 0;    //一元话费支出
        public long     card200   = 0;    //一元话费支出
    }

    public static class LianXiWin {
        public String   begin   = "";
        public String   end     = "";
        public long     Gold    = 0;    //元宝支出
        public long     baotu   = 0;    //宝图支出
        public long     card1   = 0;    //一元话费支出
        public long     hp      = 0;    //体力支出
        public long     win1    = 0;    //一连胜
        public long     win2    = 0;
        public long     win3    = 0;
        public long     win4    = 0;
        public long     win5    = 0;
        public long     win6    = 0;
        public long     win7    = 0;
        public long     win8    = 0;
        public long     win9    = 0;
    }


    public static class LianXi {
        public String begin = "";
        public String end = "";
        public long xueliuPeople = 0;  // 血流人数
        public long xueliuInning = 0;    // 血流手
        public long xueliuHP = 0;    // 升级支出
        public long xuezhanPeople = 0;     // 输光补助
        public long xuezhanInning = 0;    // 任务支出
        public long xuezhanHP = 0;  // 血流收入
    }

    public static class Finance {
        public String begin = "";
        public String end = "";
        public long grantInstall = 0;  // 新手支出
        public long grantLogin = 0;    // 登录支出
        public long upgradePay = 0;    // 升级支出
        public long grantHelp = 0;     // 输光补助
        public long missionPay = 0;    // 任务支出
        public long xueliuIncome = 0;  // 血流收入
        public long xuezhanIncome = 0; // 血战收入
        public long errenIncome = 0;   // 二人麻将收入
        public long aiWinLose = 0;     // AI输赢
        public long lotteryPay = 0;    // 抽奖元宝支出
        public long total = 0;
    }

    public static class Funnel {
        public String begin = "";
        public String end = "";
        public long install = 0;  // 新注册用户
        public long caozuoPeople = 0;  // 操作用户
        public long grantLoginPeople = 0;    // 登录奖励人数
        public long goldChangePeople = 0;    // 有金币变动人数
        public List< Long > inningPeople = new LinkedList<>(); // 手数玩家
        public long huan3People = 0;   // 主动点击换3张人数
        public long dingquePeople = 0; // 主动点击定缺人数
        public long fanhuiPeople = 0;  // 点击返回人数
        public long jixuPeople = 0;    // 点击继续游戏人数
        public long fanhuikaishiPeople = 0; // 点击返回后又点击开始游戏人数

        public long newuser = 0;            // 新注册人数
        public long lijilingqu = 0;         // 立即领取人数
        public long lijishiyong = 0;        // 立即使用人数
        public long shurushouji = 0;        // 输入手机号人数
        public long lingquchenggong = 0;    // 领取免费成功人数
        public long lingqushibai = 0;       // 领取免费失败人数
        public long guanbi = 0;             // 关闭充值面板人数
    }

    public static class Online {
        public String begin = "";
        public String end = "";
        public long install = 0;  // 新注册用户
        public List< Long > onlinePeople = new LinkedList<>(); // 时长玩家
    }

    public static class GrantLogin {
        public String begin = "";
        public String end = "";
        public long oldPeople = 0;  // 老用户
        public long oldGold = 0;    // 老用户领取金币
        public long newPeople = 0;    // 新用户
        public long newGold = 0;    // 新用户领取金币
        public long totalGold = 0;      // 总领取金币
    }

    public static class Upgrade {
        public String begin = "";
        public String end = "";
        public long people = 0;  // 升级人数
        public long count = 0;   // 升级次数
        public long gold = 0;    // 升级支出
    }

    public static class Huizong {
        public String begin = "";
        public String end = "";
        public long people = 0;  // 人数
        public long AI = 0;
        public long rake = 0;
        public long shouru = 0;

    }

    public static class Mission {
        public String begin = "";
        public String end = "";
        public long exp = 0;     // 经验支出
        public long gold = 0;    // 元宝支出
        public long hp = 0;      // 体力支出
        public long treasure = 0;// 藏宝图支出
        public long count = 0;   // 领奖次数
        public long people = 0;  // 领奖人数
    }

    public static class BasicItem {
        public long level = 0;
        public long people = 0;
        public long inning = 0;
        public long rake = 0;
    }

    public static class Basic {
        public String begin = "";
        public String end = "";
        public Map< Long, BasicItem > data = new TreeMap<>();
    }

    public static class Rank {
        public String begin = "";
        public String end = "";
        public long userId = 0;
        public String userName = "";
        public long gold = 0;
    }

    public static class Possession {
        public String begin = "";
        public String end = "";
        public long dau = 0;
        public long gold = 0;
    }

    public static class KaiFangHuizong {
        public String begin = "";
        public String end = "";
        public long xuezhan = 0;
        public long xueliu = 0;
        public long zong = 0;
    }
    public static class KaiFangJichu {
        public String begin = "";
        public String end = "";
        public long xueliupeople = 0;
        public long xuezhanpeople = 0;
        public long xueliufangka = 0;
        public long xuezhanfangka = 0;
        public long xueliunum = 0;
        public long xuezhannum = 0;
    }
    public static class KaiFangXingwei {
        public String begin = "";
        public String end = "";
        public long people = 0;
        public long count  = 0;
        public long inning = 0;
        public long fan2 = 0;
        public long fan3 = 0;
        public long huan3 = 0;
        public long buhuan3 = 0;
        public long ju4 = 0;
        public long ju8 = 0;

    }


    public static class PayItem {
        public String begin = "";
        public String end = "";
        public long people = 0;
        public Map< Long, Long > items = new TreeMap<>();
        public long total = 0;
    }

    public static class Stat {
        public String begin = "";
        public String end = "";
        public long people = 0;
        public long inning = 0;
        public long zimo = 0;
        public long dianpao = 0;
        public long minggang = 0;
        public long angang = 0;
        public List< Long > fanCount = new LinkedList<>();
    }

    public static class LotteryStat {
        public String begin = "";
        public String end = "";
        public List< Long > people = new ArrayList<>();
        public List< Long > count = new ArrayList<>();
        public long totalPeople = 0;
        public long totalCount = 0;
    }

    public static class LotteryItem {
        public String begin = "";
        public String end = "";
        public long gold = 0;
        public long quan = 0;
        public long card5 = 0;
    }

    public static class LotterySum {
        public String begin      = "";
        public String end        = "";
        public long totalPeople  = 0;
        public long totalCount   = 0;
        public long quan         = 0;
        public long gold         = 0;
        public long baotu        = 0;
        public long card5        = 0;
        public long card10       = 0;
        public long card20       = 0;
        public long card50       = 0;
        public long card100      = 0;
        public long card200      = 0;


    }

    public static class AiWinLose {
        public String begin = "";
        public String end = "";
        public String gameName = "";
        public long xinShou = 0;
        public long jingYing = 0;
        public long daShen = 0;
        public long tuHao = 0;
        public long total = 0;
    }

    public static class PhoneCardRetention {
        public String begin = "";
        public String end = "";
        public long getCardPeople = 0;        // 获取登录话费卡人数
        public List< Long > retentions = new LinkedList<>();  // 留存
    }
}
