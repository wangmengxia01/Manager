package com.hoolai.ccgames.bifront.vo;

public class ZfbView {


    public static class shangzhuangka {
        public String begin = "";
        public String end = "";
        public long shangzhuangPeople = 0;
        public long shangzhuangCount = 0;
        public long hezhuangPeople = 0;
        public long hezhuangCount = 0;
    }

    public static class RedEnvelope {
        public String begin = "";
        public String end = "";
        public long netPump = 0;
        public long systemReward = 0;
        public long systemRewardPeople = 0;
        public long playerReward = 0;
        public long playerRewardPeople = 0;
        public long sendFlowers = 0;
        public long sendFlowersPeople = 0;
    }

    public static class Rank {
        public long userId = 0;
        public String userName = "";
        public long totalBet = 0;
        public long zhuangWin = 0;
        public long xianWin = 0;
        public long jackpotWin = 0;
        public long netWin = 0;
        public double winRatio = 0;
    }

    public static class Basic {
        public String begin = "";
        public String end = "";
        public long people = 0;       // 净人数
        public long netPump = 0;       // 净抽水
        public long totalBet = 0;      // 总下注
        public long betPumpSum = 0;      // 下注抽水
        public long totalWin = 0;      // 总下注
        public long chipZhuangCnt = 0; // 筹码上庄次数
        public long joinZhuangCnt = 0; // 合庄次数
        public long cardZhuangCnt = 0; // 上庄券上庄次数
        public long escapePump = 0;    // 逃庄抽水
        public long xiazhuangPump = 0;    // 下庄抽水
        public long Pump = 0;    // 抽水
        public long cardPay = 0;       // 上庄券支出
        public long jackpotPay = 0;    // 爆彩支出
        public long rewardIncome = 0;  // 红包竟营收
        public long exprIncome = 0;    // 表情营收
        public long AIzhuangBet = 0;    // 胡莱莱当庄下
        public long AIzhuangWin = 0;    // 胡莱莱当庄赢


    }

    public static class RobotSum {
        public String begin = "";
        public String end = "";
        public long bet = 0;
        public long win = 0;
        public long pump = 0;
        public long jackpot = 0;
        public long total = 0;
    }

    public static class RobotSingle {
        public String robotId = "";
        public long bet = 0;
        public long win = 0;
        public long pump = 0;
        public long jackpot = 0;
        public long total = 0;
    }
}
