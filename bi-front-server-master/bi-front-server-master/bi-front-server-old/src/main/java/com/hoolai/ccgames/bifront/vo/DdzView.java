package com.hoolai.ccgames.bifront.vo;

import java.util.*;

/**
 * Created by Administrator on 2016/9/1.
 */
public class DdzView {

    public static class ddzTaskXX{
        public String    begin            = "";
        public String    end              = "";
        public long      zha2            = 0 ;
        public long      zha3            = 0 ;
        public long      zha4            = 0 ;
        public long      zha5            = 0 ;
        public long      zha6            = 0 ;
        public long      zha7            = 0 ;
        public long      zha8            = 0 ;
        public long      zha9            = 0 ;
        public long      zha10            = 0 ;
        public long      zhaJ            = 0 ;
        public long      zhaQ            = 0 ;
        public long      zhaK            = 0 ;
        public long      zhaA            = 0 ;
        public long      sandai            = 0 ;
        public long      huojian         = 0 ;
        public long      danwang      = 0 ;
        public long      feiji   = 0 ;
        public long      san2           = 0 ;
        public long      dan2            = 0 ;
        public long      liandui           = 0 ;
        public long      zhuawang            = 0 ;
        public long      win            = 0 ;
        public long      zhuahuojian            = 0 ;
        public long      sidai            = 0 ;
        public long      danshun            = 0 ;
    }

    public static class ddzChouJiang{
        public String    begin            = "";
        public String    end              = "";
        public long      people            = 0 ;
        public long      count        = 0 ;
        public long      gold     = 0 ;
        public long      card1   = 0 ;
        public long      card2          = 0 ;
        public long      card5            = 0 ;
        public long      card10         = 0 ;
        public long      card20         = 0 ;

    }

    public static class ddzTaskChangci{
        public String    begin            = "";
        public String    end              = "";
        public List< Long > zongsuiji            = new ArrayList<>();
        public List< Long > zongwancheng            = new ArrayList<>();
        public List< Long > Gold            = new ArrayList<>();
        public List< Long > baotu            = new ArrayList<>();
        public List< Long > zongshoushu            = new ArrayList<>();
        public List< Long > finishZong            = new ArrayList<>();

    }
    public static class ddzTask{
        public String    begin            = "";
        public String    end              = "";
        public long      zongsuiji            = 0 ;
        public long      zongwancheng         = 0 ;
        public long      Gold      = 0 ;
        public long      baotu   = 0 ;
        public long      zongshoushu           = 0 ;
        public long      finishZong           = 0 ;

    }

    public static class ddzBujiao{
        public String    begin            = "";
        public String    end              = "";
        public long      chuji            = 0 ;
        public long      zhongji         = 0 ;
        public long      gaoji      = 0 ;
        public long      jingying   = 0 ;
        public long      dashi           = 0 ;

    }

    public static class ddzMult{
        public String    begin            = "";
        public String    end              = "";
        public List< Long > multiples            = new ArrayList<>();

    }

    public static class ddzPeople{
        public String    begin            = "";
        public String    end              = "";
        public long      chuji            = 0 ;
        public long      jingying         = 0 ;
        public long      dashi      = 0 ;
        public long      zhongji   = 0 ;
        public long      gaoji   = 0 ;
        public long      zong             = 0 ;

    }

    public static class ddzNum{
        public String    begin            = "";
        public String    end              = "";
        public long      chuji            = 0 ;
        public long      jingying         = 0 ;
        public long      dashi      = 0 ;
        public long      zhongji   = 0 ;
        public long      gaoji   = 0 ;
        public long      zong             = 0 ;

    }


    public static class ddzChoushui{
        public String    begin            = "";
        public String    end              = "";
        public long      chujiChoushui            = 0 ;
        public long      chujiGold            = 0 ;
        public long      jingyingChoushui         = 0 ;
        public long      jingyingGold         = 0 ;
        public long      dashiChoushui      = 0 ;
        public long      dashiGold      = 0 ;
        public long      gaojiChoushui   = 0 ;
        public long      gaojiGold   = 0 ;
        public long      zhongjiChoushui             = 0 ;
        public long      zhongjiGold             = 0 ;
        public long      zongchoushui             = 0 ;
        public long      zongyuabao             = 0 ;

        public long      AIwin             = 0 ;

    }

    public static class ddzLuckyWheelBonusCount {
        public String begin = "";
        public String end = "";
        public Map< Long, Long > id2count = new TreeMap<>();
    }

    public static class Finance {
        public String begin = "";
        public String end = "";
        public long grantInstall = 0;  // 新手支出
        public long grantLogin = 0;    // 登录支出
        public long grantHelp = 0;     // 输光补助
        public long missionPay = 0;    // 任务支出
        public long ddzShouru = 0;    // 斗地主收入
        public long total = 0;
        public long people = 0;
        public long choujiang = 0;
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
    public static class Funnel {
        public String begin = "";
        public String end = "";
        public long install = 0;  // 新注册用户
        public long grantLoginPeople = 0;    // 登录奖励人数
        public long goldChangePeople = 0;    // 有金币变动人数
        public List<Long> inningPeople = new LinkedList<>(); // 手数玩家
    }
    public static class Online {
        public String begin = "";
        public String end = "";
        public long install = 0;  // 新注册用户
        public List< Long > onlinePeople = new LinkedList<>(); // 时长玩家
    }


    public static class Possession {
        public String begin = "";
        public String end = "";
        public long dau = 0;
        public long gold = 0;
    }
    public static class Rank {
        public String begin = "";
        public String end = "";
        public long userId = 0;
        public String userName = "";
        public long gold = 0;
    }


    public static class PayItem {
        public String begin = "";
        public String end = "";
        public long people = 0;
        public Map< Long, Long > items = new TreeMap<>();
        public long total = 0;
    }
}
