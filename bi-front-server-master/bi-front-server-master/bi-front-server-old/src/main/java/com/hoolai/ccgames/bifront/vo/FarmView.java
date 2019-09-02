package com.hoolai.ccgames.bifront.vo;

public class FarmView {

    public static class Basic {
        public String begin = "";
        public String end = "";
        public long netPump = 0;            // 净抽水
        public long openIncome = 0;         // 开启玩法营收
        public long landextIncome = 0;      // 扩地营收
        public long orePay = 0;             // 矿石回收支出
        public long miningPeople = 0;       // 挖矿人数
        public long diamondProduce = 0;     // 钻石产出
        public long diamondTradeNum = 0;    // 钻石交易数量
        public long diamondTradePeople = 0; // 钻石交易人数
        public long diamondTradeTax = 0;    // 钻石交易收税
    }

}
