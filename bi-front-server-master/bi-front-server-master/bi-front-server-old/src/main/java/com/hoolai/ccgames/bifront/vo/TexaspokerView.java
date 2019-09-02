package com.hoolai.ccgames.bifront.vo;

public class TexaspokerView {

	public static class Rank {
		
		public long userId = 0;
		public String userName = "";
		public long totalBet = 0;
		public long totalWin = 0;
		public long totalRebate = 0;
		public long totalPump = 0;
		public long netWin = 0;
		public double winRatio = 0;
	}

	public static class huizong {
		public String    begin              = "";
		public String    end                = "";
		public long      dezhouRake        = 0 ;
		public long      yaojiangjiRake           = 0 ;
		public long      dingshiOut          = 0 ;
		public long      zhuanpanOut      = 0 ;
		public long      youlunRake         = 0 ;
		public long      zhongRake        = 0 ;
	}
	public static class yaojiangji{
		public String    begin              = "";
		public String    end                = "";
		public long      People        = 0 ;
		public long      zongtouzhu        = 0 ;
		public long      fanjiang 		= 0 ;
		public long      caichi 		= 0 ;
		public long      zong        = 0 ;
	}
	public static class dingshi{
		public String    begin              = "";
		public String    end                = "";
		public long      People        = 0 ;
		public long      Gold         = 0 ;
		public long      zhongjisuipian        = 0 ;
		public long      qingtie        = 0 ;
	}

	public static class zhuanpan {
		public String    begin              = "";
		public String    end                = "";
		public long      zhuanpanPeople        = 0 ;
		public long      zhuanpanCount           = 0 ;
		public long      hulaibi          = 0 ;
		public long      shenmu      = 0 ;
		public long      Gold         = 0 ;
		public long      zhongjicansai        = 0 ;
		public long      zhongjisuipian        = 0 ;
		public long      chujicansai        = 0 ;
	}

	public static class jichu {
		public String    begin              = "";
		public String    end                = "";
		public long      xinshouPeople        = 0 ;
		public long      xinshouNum           = 0 ;
		public long      xinshouRake          = 0 ;
		public long      zhongjiPeople      = 0 ;
		public long      zhongjiNum         = 0 ;
		public long      zhongjiRake        = 0 ;
		public long      dashiPeople        = 0 ;
		public long      dashiNum           = 0 ;
		public long      dashiRake          = 0 ;
		public long      zhuanjiaPeople       = 0 ;
		public long      zhuanjiaNum          = 0 ;
		public long      zhuanjiaRake         = 0 ;
		public long      fangkaBuy         = 0 ;
		public long      fangkaUse         = 0 ;

		public long      zongRake          = 0 ;

	}

	public static class Youlun {
		
		public String begin = "";
		public String end = "";
		public long totalPump = 0;
		public long playerCount = 0;
		public long masterPointPay = 0;
		public long bonusPay = 0;
		public long otherPay = 0;
		public long netPump = 0;
		
	}
	
	public static class PayDetail {
		public String beg = "";
		public long userId = 0;
		public long itemId = 0;
		public long itemCount = 0;
		public long money = 0;
	}
}
