package com.hoolai.ccgames.bifront.vo;

public class MtlView {

	public static class Basic {
		public String begin = "";
		public String end = "";
		public long potPump = 0;       // 池子抽水
		public long totalBet = 0;      // 总下注
		public long winPump = 0;       // 玩家输赢抽水
		public long jackpotPay = 0;    // 爆彩支出
	}
	
	public static class Rank {
		public long userId = 0;
		public String userName = "";
		public long totalBet = 0;
		public long totalWin = 0;
		public long jackpot = 0;
		public long netWin = 0;
		public double winRatio = 0;
	}
}
