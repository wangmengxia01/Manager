package com.hoolai.ccgames.bifront.vo;

public class PptView {

	public static class Basic {
		public String begin = "";
		public String end = "";
		public long totalBet = 0;      // 总下注
		public long totalWin = 0;      // 总返奖
		public long winPump = 0;       // 玩家输赢抽水
		public long jackpotPay = 0;    // 爆彩支出
		public long zongPeople = 0;
		public long shishicai = 0;
	}

	public static class SscBasic {
		public String begin = "";
		public String end = "";
		public long totalBet = 0;      // 总下注
		public long totalWin = 0;
		public long winPump = 0;       // 玩家输赢抽水
		public long people = 0;        // 参与人数
		public long zongPeople = 0;
		public long sumInnings = 0;    // 所有人参与局数之和
	}

	public static class xiazai {
		public String begin = "";
		public String end = "";
		public long user_id = 0;
		public long shuying = 0;
		public long chongzhi = 0;
		public long xingyun = 0;
		public long jieshou = 0;
		public long shiyong = 0;
		public long diaoluo = 0;
		public long zengsong = 0;
		public long paiment = 0;
		public long get = 0;
		public long choushui = 0;
		public long fangruxingyun = 0;
	}
	
	public static class Rank {
		public long userId = 0;
		public String userName = "";
		public long totalBet = 0;
		public long pptBet = 0;
		public long pptWin = 0;
		public long jackpot = 0;
		public long sscBet = 0;
		public long sscWin = 0;
		public long netWin = 0;
		public double winRatio = 0;
	}
}
