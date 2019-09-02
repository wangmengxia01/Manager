package com.hoolai.ccgames.bifront.vo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SingleUserView {

	public static class ChipMove {
		public String begin = "";
		public String end = "";
		public long userId = 0;
		public long goldDeskBet = 0;
		public long goldDeskWin = 0;
		public long goldDeskJackpot = 0;
		public long goldDeskPump = 0;
		public long goldDeskCount = 0;
		public long pptBet = 0;
		public long pptWin = 0;
		public long pptJackpot = 0;
		public long sscBet = 0;
		public long sscWin = 0;
		public long dogBet = 0;
		public long dogWin = 0;
		public long dogJackpot = 0;
		public long mtlBet = 0;
		public long mtlWin = 0;
		public long mtlJackpot = 0;
		public long fruitBet = 0;
		public long fruitWin = 0;
		public long fruitJackpot = 0;
		public long caibeiBet = 0;
		public long caibeiWin = 0;
		public long caibeiItem = 0;
		public long timeluckBet = 0;
		public long timeluckWin = 0;
		public long yylBet = 0;
		public long yylWin = 0;
		public long ddzWin = 0;
		public long ddzCount = 0;
		public long dezhouWin = 0;
		public long dezhouCount = 0;
		public long xueliuWin = 0;
		public long xueliuCount = 0;
		public long xuezhanWin = 0;
		public long xuezhanCount = 0;
		public long erWin = 0;
		public long erCount = 0;
		public long zfbXianBet = 0;
		public long zfbXianWin = 0;
		public long zfbZhuangWin = 0;
		public long zfbRedSend = 0;
		public long zfbRedRecv = 0;
		public long zfbJackpot = 0;
		public long teamContributeExchange = 0;
		public long teamLeaderGot = 0;
		public long fishBet = 0;
		public long fishWin = 0;
		public long BRDZBet = 0;
		public long BRDZWin = 0;
		public long fishJackpot = 0;
		public long xuyuanzuanshi = 0;

		public long dashijifen = 0;

		public long jingjiWin = 0;
		public long jingjiCount = 0;
		public long max = 0;
		public Map<Long , Long> 	 huodongxiaohao   	  = new HashMap<>() ;
		public Map<Long , Long> 	 daoju   	  = new HashMap<>() ;
		public Map<Long , Long>      otherDaoju   = new HashMap<>() ;
		public Map<Long , Long> 	 jingjiGet   	  = new HashMap<>() ;
		public Map<Long , Long> 	 jingjiInfo   	  = new HashMap<>() ;

	}
	
	public static class UserIdGold {
		public long userId = 0;
		public long gold = 0;

		public UserIdGold() {}
		public UserIdGold( long u, long g ) { this.userId = u; this.gold = g; }
	}

	public static class UserIps {
		public long userId = 0;
		public List< String > ips = new LinkedList<>();
		public List< Long > ids = new LinkedList<>();
	}
	
	public static class IpUserCount {
		public String ip = "";
		public long userCount = 0;    // 用该ip登录过的用户数
		public long userId = 0;       // 该ip下最早的用户ID
	}
	
	public static class Pay {
		public String time = "";
		public long itemId = 0;
		public String itemName = "";
		public String itemPay = "";  // 购买花费
		public long itemBuyCount = 0;  // 购买个数
		public long itemCount = 0;     // 实际发放个数
	}
	
}
