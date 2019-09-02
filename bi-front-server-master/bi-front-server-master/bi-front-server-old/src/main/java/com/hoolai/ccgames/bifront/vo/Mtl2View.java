package com.hoolai.ccgames.bifront.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mtl2View {
	
	public static class Finance {
		public String begin = "";
		public String end = "";
		public long people = 0;
		public long goldBet = 0;
		public long niubiBet = 0;
		public long xiaoNiubiBet = 0;
		public long totalPay = 0;
		public long gamePay = 0;
		public long jackpotPay = 0;
		public long totalRake = 0;
		public long potPump = 0;

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

	public static class Nb {
		public String begin = "";
		public String end = "";
		public long niubiBetPeople = 0;
		public long xiaoniubiBetPeople = 0;
		public long niubiBet = 0;
		public long xiaoniubiBet = 0;
	}

}
