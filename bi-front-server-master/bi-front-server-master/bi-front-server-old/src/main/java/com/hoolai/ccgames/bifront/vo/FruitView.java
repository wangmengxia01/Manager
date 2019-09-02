package com.hoolai.ccgames.bifront.vo;

public class FruitView {

	public static class Basic {
		public String begin = "";
		public String end = "";
		public long potPump = 0;       // 池子抽水
		public long totalBet = 0;      // 总下注
		public long totalWin = 0;      // 总返奖
		public long winPump = 0;       // 玩家输赢抽水
		public long jackpotPay = 0;    // 爆彩支出
		public long zongPeople = 0;       //
		public long bibei = 0;       //
	}
	
	public static class Rank {
		public long userId = 0;
		public String userName = "";
		public long totalBet = 0;
		public long fruitBet = 0;
		public long fruitWin = 0;
		public long jackpot = 0;
		public long caibeiBet = 0;
		public long caibeiWin = 0;
		public long caibeiItem = 0;
		public long netWin = 0;
		public double winRatio = 0;
	}
	
	public static class CaibeiBasic {
		public String begin = "";
		public String end = "";
		public long potPump = 0;       // 池子抽水
		public long totalBet = 0;      // 总下注
		public long totalWin = 0;      // 总产出
		public long winPump = 0;       // 玩家输赢抽水
		public long itemPay = 0;       // 道具支出
		public long bibeiPeople = 0;       //
		public long zongPeople = 0;       //
	}
	
	public static class CaibeiItem {
		public String begin = "";
		public String end = "";
		public long itemId = 0;
		public String itemName = "";
		public long itemCount = 0;
	}
}
