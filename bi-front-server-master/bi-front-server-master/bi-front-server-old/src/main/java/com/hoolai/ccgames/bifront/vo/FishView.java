package com.hoolai.ccgames.bifront.vo;

import java.util.HashMap;
import java.util.Map;

public class FishView {

	public static class Poker {
		public String    begin            = "";
		public String    end              = "";
		public long      mianC        = 0 ;
		public long      mianP        = 0 ;
		public long      chuC        = 0 ;
		public long      chuP        = 0 ;
		public long      zhongC        = 0 ;
		public long      zhongP        = 0 ;
		public long      gaoC        = 0 ;
		public long      gaoP        = 0 ;
		public long      boss     = 0 ;
		public long      gold     = 0 ;
	}
	public static class bigR {
		public String    begin            = "";
		public String    end              = "";
		public long      people        = 0 ;
		public long      count        = 0 ;
		public long      count2        = 0 ;
		public long      gold        = 0 ;
		public long      user_id        = 0 ;
		public long      chongzhi      = 0 ;
		public String    user_name        = "" ;
	}
	public static class caicaicai {
		public String    begin            = "";
		public String    end              = "";
		public long      people        = 0 ;
		public long      count        = 0 ;
		public long      cost        = 0 ;
		public long      gold        = 0 ;
		public long      user_id        = 0 ;
		public long      chanchu      = 0 ;
		public long      choushui      = 0 ;
		public String    user_name        = "" ;
	}

	public static class BRDZ {
		public String    begin            = "";
		public String    end              = "";
		public long      people        = 0 ;
		public long      zongtouzhu        = 0 ;
		public double      choushuilv        = 0 ;
		public long      zongchanchu    = 0 ;
		public long      paixingtouzhu     = 0 ;
		public long      paixingchanchu     = 0 ;
		public long      caijintouzhu   = 0 ;
		public long      caijinchanchu    = 0 ;
		public long      choushui		   = 0 ;
		public long      chizi		   = 0 ;
		public String    user_name        = "" ;
	}

	public static class JingJi {
		public String    begin            = "";
		public String    end              = "";
		public long      count        = 0 ;
		public long      people        = 0 ;
		public long      cost        = 0 ;
		public long      p1        = 0 ;
		public long      p2         = 0 ;
		public long      p3         = 0 ;
		public long      p4        = 0 ;
		public long      choushui       = 0 ;
		public long      jiangli      = 0 ;
		public long      zuanshi       = 0 ;
		public long      zong       = 0 ;
		public long      cishu1       = 0 ;
		public long      cishu2     = 0 ;
		public long      cishu3     = 0 ;
		public long      cishu4     = 0 ;
		public long      duihuan1     = 0 ;
		public long      duihuan2    = 0 ;
		public long      duihuan3    = 0 ;
		public Map<Long , Long> daoju = new HashMap<>() ;
		public Map<Long , Long> renshu = new HashMap<>() ;
		public Map<Long , Long> cishu = new HashMap<>() ;


	}

	public static class Huizong {
		public String begin = "";
		public String end = "";
		public long totalBet = 0;      // 总下注
		public long totalWin = 0;      // 总返奖
		public long winPump = 0;       // 玩家输赢抽水
		public long jackpotPay = 0;    // 爆彩支出
		public long LuckyPay = 0;    // 幸运爆彩支出
		public long NewUser = 0;    // 新手保护支出
		public long caibeiFish = 0;
		public long bigR = 0;
		public long zhuanpan = 0;
		public long hedanBet = 0;
		public long hedanWin = 0;
		public long singleUser = 0;
		public long useDuiHuanQuan = 0;
		public Map<Long , Long> zhuanpandaoju = new HashMap<>() ;
		public Map<Long , Long> leijiDaoju = new HashMap<>() ;
		public Map<Long , Long> chongzhiDaoju = new HashMap<>() ;
		public Map<Long , Long> bangdingDaoju = new HashMap<>() ;
		public Map<Long , Long> duihuandaoju = new HashMap<>() ;
		public Map<Long , Long> bossdaoju = new HashMap<>() ;
		public Map<Long , Long> jingjidaoju = new HashMap<>() ;
		public long zongPeople = 0;
		public long GoldOut = 0;
		public long GM_single = 0;
		public long GM_Lucky = 0;
	}
	public static class LuckyJack {
		public String begin 		= "";
		public String end 			= "";
		public long chuPeople1 		= 0;
		public long chuCount1 		= 0;
		public long chuGold1 		= 0;
		public long zhongPeople1 	= 0;
		public long zhongCount1 	= 0;
		public long zhongGold1 		= 0;
		public long gaoPeople1 		= 0;
		public long gaoCount1 		= 0;
		public long gaoGold1 		= 0;
		public long chuPeople2 		= 0;
		public long chuCount2 		= 0;
		public long chuGold2	 	= 0;
		public long zhongPeople2 	= 0;
		public long zhongCount2 	= 0;
		public long zhongGold2 		= 0;
		public long gaoPeople2 		= 0;
		public long gaoCount2 		= 0;
		public long gaoGold2 		= 0;
	}
	
	public static class Basic {
		public String begin = "";
		public String end = "";
		public long chuPeople = 0;
		public long chujiBet = 0;
		public long chujiWin = 0;
		public long chujiJack = 0;
		public long zhongPeople = 0;
		public long zhongjiBet = 0;
		public long zhongjiWin = 0;
		public long zhongjiJack = 0;
		public long gaoPeople = 0;
		public long gaojiBet = 0;
		public long gaojiWin = 0;
		public long gaojiJack = 0;
		public long danPeople = 0;
		public long danBet = 0;
		public long danWin = 0;
		public long tuhaoPeople = 0;
		public long tuhaoBet = 0;
		public long tuhaoWin = 0;
		public long jingjiPeople = 0;
		public long jingjiBet = 0;
		public long jingjiWin = 0;
		public long zong = 0;

	}

	public static class Shop {
		public String begin = "";
		public int itemId = 0;
		public long itemCount = 0;
		public long itemMoney = 0;
		public long money = 0;
		public long count = 0;
	}

	public static class PoolMove {
		public String begin = "";
		public long yinTojiangP = 0;
		public long xingTojiangP = 0;
		public long yinTojiangC = 0;
		public long xingTojiangC = 0;
		public long yinTojiangM = 0;
		public long xingTojiangM = 0;
		public Map<Long , Long> shuixian = new HashMap<>() ;
	}

	public static class UserItem {
		public String begin = "";
		public int itemId = 0;
		public long user_id = 0;
		public long itemCount = 0;
	}
	public static class Boss {
		public String begin = "";
		public int time = 0;
		public long people = 0;
		public long cost = 0;
		public long choushui = 0;
		public long shanghai = 0;
		public long jiangli = 0;
		public long zong = 0;
	}

	public static class Rank {
		public String begin = "";
		public String user_name = "";
		public long userId = 0;
		public long totalBet = 0;
		public long totalWin = 0;
		public long jackpot = 0;
		public long newUser = 0;
		public long LuckyJackpot = 0;
		public long netWin = 0;
		public long caijinBet = 0;
		public long paixingBet = 0;
		public long caijinWin = 0;
		public long paixingWin = 0;
	}
	public static class CCC {
		public String begin = "";
		public String user_name = "";
		public long userId = 0;
		public long totalBet = 0;
		public long count = 0;
		public long kaidantou = 0;
		public long jifenchanchu = 0;
		public long duihuanxiaohao = 0;
		public Map<Long , Long> duihuan = new HashMap<>() ;
	}

	public static class dantouFish {
		public String begin = "";
		public long fishId = 0;
		public long chuxian = 0;
		public long jisha = 0;
		public long jin= 0;
		public long yin= 0;
	}

	public static class GameGold {
		public String begin = "";
		public String end = "";
		public long install = 0;
		public long login = 0;
		public long leiji = 0;
		public long broke = 0;
		public long huoyue = 0;
		public long upLevel = 0;
		public long onlineRed = 0;
		public long task = 0;
		public long vip_up = 0;
		public long VIPLogin = 0;
		public long Pay = 0;
		public long Pay_jiangchi = 0;
		public long captain = 0;
		public long first_pay = 0;
		public long qifu = 0;
		public long wanshengjie = 0;
		public long zadan = 0;
		public long dafuweng = 0;
		public long zongOut = 0;
		public long zongIn = 0;
		public long newUserBuzhu = 0;
		public long shopOut = 0;
		public long tehui = 0;
		public long singleUserPay = 0;
		public long useDuiHuanQuan = 0;
		public long caibeiFish = 0;
		public long zhuanpan = 0;
		public long leijichongzhi = 0;
		public long chongzhi = 0;
		public long chongzhichoujiang = 0;
		public long big_r = 0;
		public long bangding = 0;
		public long duihuan = 0;
		public long boss = 0;
		public long jingji = 0;
		public long phone = 0;
		public long cdkey	 = 0;
		public long tuiguang	 = 0;
		public long poker	 = 0;
		public long gengxin = 0;
		public long baoxiang = 0;
		public long gongcechoujiang = 0;
		public Map<Long , Long> zhuanpandaoju = new HashMap<>() ;
		public Map<Long , Long> leijiDaoju = new HashMap<>() ;
		public Map<Long , Long> chongzhiDaoju = new HashMap<>() ;
		public Map<Long , Long> bangdingDaoju = new HashMap<>() ;
		public Map<Long , Long> duihuandaoju = new HashMap<>() ;
		public Map<Long , Long> bossdaoju = new HashMap<>() ;
		public Map<Long , Long> jingjidaoju = new HashMap<>() ;

	}
	public static class install {
		public String begin = "";
		public String end = "";
		public long install = 0;
		public long installGold = 0;
		public long baohuUser = 0;
		public long zongOut = 0;
		public long people = 0;
		public long count = 0;
		public long gold = 0;

	}
	public static class NewUser {
		public String begin = "";
		public String end = "";
		public long install = 0;
		public long people = 0;
		public long count = 0;
		public long gold = 0;

	}

	public static class login {
		public String begin = "";
		public String end = "";
		public long login_people = 0;
		public long lingqu_people = 0;
		public long Vip_Gold = 0;
		public long Gold = 0;
		public long leijiGold = 0;
		public long leijiPeople = 0;
		public Map<Long , Long> 	Daoju   = new HashMap<>() ;
	}

	public static class loudou {
		public String begin = "";
		public String end = "";
		public long install = 0;
		public long qiandao = 0;
		public long chu = 0;
		public long zhong = 0;
		public long gao = 0;
		public long l1 = 0;
		public long l2 = 0;
		public long l3 = 0;
		public long l4 = 0;
		public long l5 = 0;
		public long l6 = 0;
		public long l7 = 0;
		public long l8 = 0;
		public long l9 = 0;
		public long l10 = 0;
		public long l11 = 0;
		public long l12 = 0;
		public long l13 = 0;
		public long l14 = 0;
		public long l15 = 0;
		public long l16 = 0;
		public long l17 = 0;
		public long l18 = 0;
		public long l19 = 0;
		public long l20 = 0;
		public long l21 = 0;
		public long l22 = 0;
		public long l23 = 0;
		public long l24 = 0;
		public long l25 = 0;
		public long l26 = 0;
		public long l27 = 0;
		public long l28 = 0;
		public long zfb = 0;
		public long mtl = 0;
	}

	public static class dantou {
		public String begin = "";
		public String end = "";
		public long qtPeople = 0;
		public long qtUse = 0;
		public long qtCount = 0;
		public long byPeople = 0;
		public long byUse = 0;
		public long byCount = 0;
		public long hjPeople = 0;
		public long hjUse = 0;
		public long hjCount = 0;
		public long chuQing = 0;
		public long chuBai = 0;
		public long chuHuang = 0;
		public long zhongQing = 0;
		public long zhongBai = 0;
		public long zhongHuang = 0;
		public long gaoQing = 0;
		public long gaoBai = 0;
		public long gaoHuang = 0;
		public long tuhaoQing = 0;
		public long tuhaoBai = 0;
		public long tuhaoHuang = 0;
	}

	public static class broke {
		public String begin = "";
		public String end = "";
		public long people = 0;
		public long count = 0;
		public long Gold = 0;
		public long VIP_people = 0;
		public long VIP_count = 0;
		public long VIP_Gold = 0;
	}

	public static class upLevle {
		public String begin = "";
		public String end = "";
		public long userUpPeople = 0;
		public long gunUpPeople = 0;
		public long userUpCount = 0;
		public long gunUpCount = 0;
		public long userUpGold = 0;
		public long gunUpGold = 0;
		public long zongGold = 0;
		public Map<Long , Long> 	userDaoju   = new HashMap<>() ;
		public Map<Long , Long>     gunDaoju   = new HashMap<>() ;
	}

	public static class redOnlid {
		public String begin = "";
		public String end = "";
		public long freePeople = 0;
		public long freeCount = 0;
		public long freeGold = 0;
		public long VIPPeople = 0;
		public long VIPCount = 0;
		public long VIPGold = 0;
		public long zongGold = 0;

	}

	public static class Task {
		public String begin = "";
		public String end = "";
		public long DPeople = 0;
		public long DCount = 0;
		public long DGold = 0;
		public long DHuoYue_Gold = 0;
		public long WPeople = 0;
		public long WCount = 0;
		public long WGold = 0;
		public long WHuoYue_Gold = 0;
		public long D_HY_People = 0;
		public long W_HY_People = 0;
		public long zongGold = 0;

		public Map<Long , Long> DDaoju   = new HashMap<>() ;
		public Map<Long , Long> WDaoju   = new HashMap<>() ;

	}

	public static class VipOut {
		public String begin = "";
		public String end = "";
		public long VipLogin = 0;
		public long VipRed = 0;
		public long VipBuquan = 0;
		public long zongGold = 0;
		public long upGold = 0;

		public Map<Long , Long> Daoju   = new HashMap<>() ;
	}

	public static class duihuan {
		public String begin = "";
		public String end = "";
		public long qingdan = 0;
		public long yindan = 0;
		public long huangdan = 0;
		public long zuanshi = 0;
		public long gold = 0;
		public long huafei30 = 0;
		public long huafei50 = 0;
		public long huafei100 = 0;
		public long pao = 0;
	}

	public static class jiangquan {
		public String begin = "";
		public String end = "";
		public long chupeople = 0;
		public long chucount = 0;
		public long chujiangquan = 0;
		public long zhongpeople = 0;
		public long zhongcount = 0;
		public long zhongjiangquan = 0;
		public long gaopeople = 0;
		public long gaocount = 0;
		public long gaojiangquan = 0;
		public long danpeople = 0;
		public long dancount = 0;
		public long danjiangquan = 0;
		public long tuhaopeople = 0;
		public long tuhaocount = 0;
		public long tuhaojiangquan = 0;
	}
	public static class handle {
		public String begin = "";
		public String end = "";
		public  long gerenxinxi =0;
		public  long shangcheng =0;
		public  long beibao =0;
		public  long xiaoxi =0;
		public  long renwu =0;
		public  long duihuan =0;
		public  long paihangbang =0;
		public  long shezhi=0;
		public  long paotai =0;
		public  long qiandao =0;
		public  long chuanzhanglibao=0;
		public  long VIPtequan =0;
		public  long kuaisukaishi=0;
		public  long yiyuanqianggou =0;
		public  long liaotian =0;
		public  long yulecheng =0;
		public  long kefu =0;
		public  long chibang =0;
		public  long xuyuan =0;
		public  long huodong =0;
		public  long tujian =0;

	}


	public static class qipao {
		public String begin = "";
		public String end = "";
		public long people = 0;
		public long count = 0;
		public long jiangquan = 0;
		public long zuanshi = 0;
		public long gold = 0;
		public long huafei = 0;

		public Map<Long , Long> Daoju   = new HashMap<>() ;
	}

	public static class captain {
		public String begin = "";
		public String end = "";
		public long BuyPeople = 0;
		public long LingquPeople = 0;
		public long gold = 0;
		public long BuyZhouPeople = 0;
		public long LingquZhouPeople = 0;
		public long ZhouGold = 0;
		public long zongGold = 0;

		public Map<Long , Long> Daoju   = new HashMap<>() ;
		public Map<Long , Long> zhouDaoju   = new HashMap<>() ;
	}

	public static class PayDetail {
		public String beg = "";
		public long userId = 0;
		public long itemId = 0;
		public long itemCount = 0;
		public long money = 0;
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

		public long money = 0;
	}


	public static class ChiBang {
		public String beg = "";
		public long tianshi = 0;
		public long emo = 0;
		public long yaowang = 0;
		public long chiyan = 0;
		public long wangzhe = 0;
	}

	public static class PaoBeiShu {
		public String beg = "";
		public long zong = 0;
		public long p100 = 0;
		public long p150 = 0;
		public long p200 = 0;
		public long p300 = 0;
		public long p400 = 0;
		public long p500 = 0;
		public long p600 = 0;
		public long p700 = 0;
		public long p800 = 0;
		public long p900 = 0;
		public long p1000 = 0;
		public long p1500 = 0;
		public long p2000 = 0;
		public long p2500 = 0;
		public long p3000 = 0;
		public long p3500 = 0;
		public long p4000 = 0;
		public long p4500 = 0;
		public long p5000 = 0;
		public long p6000 = 0;
		public long p7000 = 0;
		public long p8000 = 0;
		public long p9000 = 0;
		public long p10000 = 0;
	}
}
