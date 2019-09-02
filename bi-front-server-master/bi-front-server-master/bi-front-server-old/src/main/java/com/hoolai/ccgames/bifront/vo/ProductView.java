package com.hoolai.ccgames.bifront.vo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hoolai on 2016/7/22.
 */
public class ProductView {

    public static class HuiZong {
        public String begin = "";
        public String end = "";
        public long loginGift = 0;
        public long goodLuck = 0;
        public long task = 0;
        public long month = 0;
        public long newUser = 0;
        public long all = 0;

    }

    public static class Duihuan {
        public String begin = "";
        public String end = "";
        public String fromWhere = "";
        public String appName = "";
        public long count = 0;
        public long people = 0;
    }

    public static class Task {
        public String begin = "";
        public String end = "";
        public long people = 0;
        public long gold = 0;
        public long exp = 0;
        public long hp = 0;
        public long baotu = 0;
        public long times = 0;
    }

    public static class MonthGift {
        public String begin = "";
        public String end = "";
        public long kaitongpeople = 0;
        public long lingqupeople = 0;
        public long gold = 0;
        public long exp = 0;
        public long hp = 0;

    }

    public static class LoginGift {
        public String begin = "";
        public String end = "";
        public String fromWhere = "";
        public String appName = "";
        public long people = 0;
        public long gold = 0;
        public long exp = 0;
        public long hp = 0;
        public long card1 = 0;
        public long card5 = 0;

    }

    public static class LoginPhoneCard {
        public String begin = "";
        public String end = "";
        public String fromWhere = "";
        public String appName   = "";
        public long people = 0;
        public long count = 0;
        public long card1 = 0;
        public long card2 = 0;
        public long card5 = 0;
        public long card10 = 0;
        public long card20 = 0;
        public long card50 = 0;
        public long card100 = 0;
    }

    public static class LuckyFund {
        public String begin = "";
        public String end = "";
        public String fromWhere = "";
        public String appName = "";
        public long people = 0;
        public long count = 0;
        public long gold = 0;
    }

    public static class ExchangeBasic {
        public String begin = "";
        public String end = "";
        public String fromWhere = "";
        public String appName = "";
        public long userId = 0;         // 玩家ID
        public long costCount = 0;      // 消耗数量
        public long itemId = 0;         // 购买道具ID
        public String itemName = "";    // 购买道具名称
        public long itemCount = 0;      // 购买道具数量
        public String realName = "";    // 真实姓名
        public String mobileNo = "";    // 手机号码
        public String addr = "";        // 收货地址
        public String other = "";        // 其他信息
    }

}
