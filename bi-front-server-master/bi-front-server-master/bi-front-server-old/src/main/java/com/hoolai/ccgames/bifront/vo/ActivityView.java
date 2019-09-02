package com.hoolai.ccgames.bifront.vo;

import java.util.*;

/**
 * Created by Administrator on 2016/8/11.
 */
public class ActivityView {

    public static class GoldEgg {
        public String    begin            = "";
        public String    end              = "";
        public long      seepeople        = 0 ;    //查看人数
        public long      seetimes         = 0 ;    //查看次数
        public long      zadanpeople      = 0 ;    //砸蛋人数
        public long      jinchuixiaohao   = 0 ;    //金锤消耗
        public long      Gold             = 0 ;    //产出金币
        public long      qingtongjiezhi   = 0 ;    // 青铜戒指产出
        public long      yiyuanhuafei     = 0 ;    //一元话费产出
        public long      eryuanhuafei     = 0 ;    //一元话费产出
        public long      cangbaotu        = 0 ;    // 藏宝图产出
        public long      wuyuanhuafei     = 0 ;    // 五元话费产出

    }
    public static class huodong {
        public String    begin            = "";
        public String    end              = "";
        public long      seepeople        = 0 ;    //查看人数
        public long      seetimes         = 0 ;    //查看次数
        public long      People = 0 ;    //参加人数
        public long      Paypeople      = 0 ;    //付费人数
        public long      xiaohao = 0 ;    //矿铲消耗
        public long      xiaohao1 = 0 ;    //矿铲消耗
        public long      xiaohao2 = 0 ;    //矿铲消耗
        public long      get = 0 ;    //矿铲消耗
        public long      Gold             = 0 ;    //产出金币
        public long      item_count             = 0 ;    //道具购买
        public double    xiyinli             = 0.0 ;    //吸引力
        public double    canyulv             = 0.0 ;    //参与率
        public long      duihuanPeople   = 0 ;    //兑换人数
        public long      hongbaoPeople   = 0 ;    //兑换人数
        public long      hongbaoCount   = 0 ;    //抢红包次数
        public long      People1   = 0 ;    //道具购买
        public long      Count1   = 0 ;    //购买次数
        public long      Count2   = 0 ;    //购买次数
        public long      Count3   = 0 ;    //购买次数
        public long      Count4   = 0 ;    //购买次数
        public long      Count5   = 0 ;    //购买次数
        public long      Count6   = 0 ;    //购买次数
        public long      Money1   = 0 ;    //购买次数
        public long      Jifen   = 0 ;    //积分产出
        public long      bili   = 0 ;    //比例
        public long      chizi   = 0 ;    //池子
        public double      fufeilv   = 0.0 ;    //付费率
        public Map<Long , Long>      daoju   = new HashMap<>() ;
        public Map<Long , Long>      otherDaoju   = new HashMap<>() ;
        public Map<Long , Long>      otherDaoju2   = new HashMap<>() ;
        public Map<Long , Long>      otherDaoju3   = new HashMap<>() ;

    }

    public static class FanPai{
        public String    time              = "";
        public String    begin              = "";
        public String    end                = "";
        public Map<Long , Long>      daoju   = new HashMap<>() ;
        public long      people            = 0 ;
        public long      fufeicount = 0 ;
        public long      count                = 0 ;
        public long      xiaofeizong                = 0 ;
        public long      huodezong             = 0 ;
        public long      gold1              = 0 ;
        public long      gold1count              = 0 ;
        public long      gold2               = 0 ;
        public long      gold2count              = 0 ;
        public long      gold3              = 0 ;
        public long      gold3count              = 0 ;
        public long      gold4              = 0 ;
        public long      gold4count              = 0 ;
        public long      xiaoniubi              = 0 ;
        public long      niubi             = 0 ;

    }
    public static class FanPaiDianshu {
        public long userId = 0;
        public long count = 0;
    }

    public static class LuckyChou {
        public String    begin              = "";
        public long userId = 0;
        public long count = 0;
        public long hongbaoCount = 0;
        public long hongbaoGold = 0;
        public long qiangHongbaoCount = 0;
        public long hongbaoMoney = 0;
        public long costCount = 0;
        public long BuyItem = 0;
        public Map<Long , Long>      daoju   = new HashMap<>() ;

    }
    public static class ShuangDan {
        public String    begin        = "";
        public long userId = 0;
        public long count = 0;
        public long people = 0;
        public Map<Long , Long>      diaoluo   = new HashMap<>() ;
        public Map<Long , Long>      chanchu   = new HashMap<>() ;

    }
    public static class Rank {
        public String begin = "";
        public String end = "";
        public long userId = 0;
        public String userName = "";
        public long gold = 0;
    }

    public static class Download {
        public String    begin              = "";
        public long adr = 0;
        public long ios = 0;
        public long adrdownload = 0;
        public long iosdownload = 0;
        public long ioswechat = 0;
        public long adrliulanqi = 0;
        public long adrwechat = 0;
        public long iosliulanqi = 0;
        public Map< String, Long > people;
        public Map< String, Long > download;
        public Map< String, Long > wechat;
    }

    public static class DuoBaoJichu1{
        public String    begin              = "";
        public String    end                = "";
        public long      canjiapeople       = 0 ;
        public long      baotuout           = 0 ;
        public long      renjin             = 0 ;
        public long      openzongbaotuOut       = 0 ;

    }

    public static class DuoBaoJichu2{
        public String    begin              = "";
        public String    end                = "";
        public long      openid       = 0 ;
        public long      userid       = 0 ;
        public String      openname           = "" ;

    }

    public static class Hongbao{
        public String    begin              = "";
        public String    end                = "";
        public long      count1       = 0 ;
        public long      count2       = 0 ;
        public long      count3       = 0 ;
        public long      count4       = 0 ;
        public long      gold1           = 0 ;
        public long      gold2           = 0 ;
        public long      gold3           = 0 ;
        public long      gold4           = 0 ;
        public long      payPeople1           = 0 ;
        public long      payPeople2           = 0 ;
        public long      payPeople3           = 0 ;
        public long      payPeople4           = 0 ;
        public long      payGold1           = 0 ;
        public long      payGold2           = 0 ;
        public long      payGold3           = 0 ;
        public long      payGold4           = 0 ;
        public long      freePeople1           = 0 ;
        public long      freePeople2           = 0 ;
        public long      freePeople3           = 0 ;
        public long      freePeople4           = 0 ;
        public long      freeGold1           = 0 ;
        public long      freeGold2           = 0 ;
        public long      freeGold3           = 0 ;
        public long      freeGold4           = 0 ;
        public Map< String, Long > xiangxi1;
        public Map<String , Long>      xiangxi2   = new HashMap<>() ;
        public Map<String , Long>      xiangxi3   = new HashMap<>() ;
        public Map<String , Long>      xiangxi4   = new HashMap<>() ;


    }
    public static class Danqi1{
        public String    begin              = "";
        public String    end                = "";
        public long      yigoumai       = 0 ;
        public long      suoxu           = 0 ;

        public  String   kaijiangjieguo  = "";
    }
    public static class Danqi2{
        public String    time              = "";
        public String    begin              = "";
        public String    end                = "";
        public long      user_id            = 0 ;
        public String   user_name            = "" ;
        public long      num                = 0 ;

    }
    public static class DanqiDanRen{
        public String    time               = "";
        public String    begin              = "";
        public String      num                = "" ;
        public long      kaijiangjieguo     = 0;

    }

    public static class GuessPoker{
        public String    begin              = "";
        public long      people     = 0;
        public long      count     = 0;
        public long      betGold     = 0;
        public long      winGold     = 0;
        public long      betGoldDan     = 0;
        public long      winGoldDan     = 0;
        public long      betYinDan     = 0;
        public long      winYinDan     = 0;
        public long wangpai = 0;
        public Map<Long , Long>      meilunrenshu   = new HashMap<>() ;

    }
    public static class GuessPokerRank{
        public String    begin              = "";
        public long      user_id     = 0;
        public long      betGold     = 0;
        public long      winGold     = 0;
        public long      betGoldDan     = 0;
        public long      winGoldDan     = 0;
        public long      betYinDan     = 0;
        public long      winYinDan     = 0;
        public long      netWinGold     = 0;
        public long      netWinGoldDan     = 0;
        public long      netWinYinDan     = 0;
        public long wangpai = 0;

    }


}
