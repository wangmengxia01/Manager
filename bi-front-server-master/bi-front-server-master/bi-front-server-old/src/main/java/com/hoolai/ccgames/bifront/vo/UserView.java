package com.hoolai.ccgames.bifront.vo;

import java.util.*;

/**
 * Created by hoolai on 2016/7/14.
 */
public class UserView {


    public static class LiucunFenxi {
        public String begin = "";
        public String end = "";
        public long install = 0;
        public long win1 = 0;
        public long lose1 = 0;
        public long win2 = 0;
        public long lose2 = 0;
        public long task1 = 0;
        public long task2 = 0;
        public long task3 = 0;
        public long gold1000 = 0;
        public long gold3000 = 0;
        public long gold100 = 0;
        public long LuckyGold = 0;
    }

    public static class phoneNum {
        public String begin = "";
        public String end = "";
        public long userId = 0;
        public String username = "";
        public long phoneNum = 0;
    }

    public static class GameWin {
        public String begin = "";
        public String end = "";
        public long userId = 0;
        public long ZFBWin = 0;
        public long FishWin = 0;
        public long MTLWin = 0;
        public String username = "";
        public long chongzhiGold = 0;
        public long chongzhi = 0;
    }


    public static class Dau {
        public String begin = "";
        public String end = "";
        public String team = "";
        public String qudao = "";
        public String game = "";
        public long userId = 0;
        public String fromWhere = "";
        public String AppName = "";
        public long MAU = 0;
        public long DAU = 0;
        public double huoyuebi = 0;
        public long online = 0;
        public double times = 0;
    }

    public static class Online {
        public String begin = "";
        public String end = "";
        public long install = 0;  // 新注册用户
        public List< Long > onlinePeople = new LinkedList<>(); // 时长玩家
    }
    public static class UserWinLose {
        public String begin = "";
        public String end = "";
        public long userId = 0;
        public long laodezhou = 0;
        public long xindezhou = 0;
        public long xueliu = 0;
        public long xuezhan = 0;
        public long erma = 0;
        public long doudizhu = 0;
        public long zfb = 0;
        public long mtl = 0;
        public long dog = 0;
        public long ppt = 0;
        public long fruit = 0;
        public long all = 0;
    }

    public static class PayDetail {
        public String beg = "";
        public String userName = "";
        public long userId = 0;
        public long itemId = 0;
        public long itemCount = 0;
        public long money = 0;
        public long leijichongzhi = 0;
        public long zhuceshijian = 0;
        public Map<Long , Long> buy   = new HashMap<>() ;
    }

    public static class user_dantou {
        public String beg = "";
        public long userId = 0;
        public long diaoluo_jin = 0;
        public long diaoluo_yin = 0;
        public long diaoluo_tong = 0;
        public long shiyong_jin = 0;
        public long shiyong_yin = 0;
        public long shiyong_tong = 0;
        public long song_jin = 0;
        public long song_yin = 0;
        public long song_tong = 0;
        public long jieshou_jin = 0;
        public long jieshou_yin = 0;
        public long jieshou_tong = 0;
        public long bs_song_jin = 0;
        public long bs_song_yin = 0;
        public long bs_song_tong = 0;
        public long bs_jieshou_jin = 0;
        public long bs_jieshou_yin = 0;
        public long bs_jieshou_tong = 0;

        public long ZFBWin = 0;
        public long FishWin = 0;
        public long MTLWin = 0;
        public long  chongzhiGold = 0;
        public long money = 0;
        public long gold = 0;
        public long chizi = 0;
    }

    public static class InstallPayDays {
        public String begin = "";
        public String end = "";
        public String team = "";
        public String game = "";
        public String fw = "";
        public long install = 0;
        public long ciliu = 0;
        public long money = 0;
        public long PayPeople = 0;
        public List< Long > pays = new LinkedList<>();

        public InstallPayDays merge( InstallPayDays o ) {
            this.install += o.install;
            this.ciliu += o.ciliu;
            this.money += o.money;
            this.PayPeople += o.PayPeople;
            for( int i = 0; i < pays.size(); ++i ) {
                pays.set( i, pays.get( i ) + o.pays.get( i )  );
            }
            return this;
        }
    }

    public static class UserPay {
        public String    begin      = "";
        public String    end        = "";
        public long userId = 0;
        public String userName = "";
        public String install = "";
        public String LastLogin = "";
        public String LastPay = "";
        public long PayMoney = 0;
        public long NoLogin = 0;
        public long NoPay = 0;
        public long TodayWin = 0;

        //中发白
        public long zfbnetWin = 0;
        //摩天轮
        public long mtlnetWin = 0;
        //总输赢
        public long Win = 0;

        public long chongzhi = 0;
        public long t_begin = 0;
        public long t_end = 0;
        public long zengsong = 0;
        public long jieshou = 0;
        public long netWin = 0;


    }

    public static class InstallRetention {
        public String fw = "";
        public String begin = "";
        public String end = "";
        public String team = "";
        public String game = "";
        public long install = 0;
        public long pay = 0;
        public List< Long > retentions = new ArrayList<>();

        public InstallRetention merge( InstallRetention o ) {
            this.install += o.install;
            this.pay += o.pay;
            for( int i = 0; i < retentions.size(); ++i ) {
                retentions.set( i, retentions.get( i ) + o.retentions.get( i ) );
            }
            return this;
        }
    }
}
