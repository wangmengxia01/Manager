package com.hoolai.ccgames.bi.protocol;

public class CommandList {

	public static final int ERROR = -1;
	public static final int IGNORE = -2;

	public static final int LOGIN_FROM_GAME_SERVER = -11;
	public static final int LOGIN_FROM_BI_SERVER = -12;
	public static final int LOGIN_FROM_GM = -13;
	public static final int LOGOUT_FROM_BI_SERVER = -14;

	public static final int GET_GM_USER_LIST = -20;
	public static final int NEW_GM_USER = -21;
	public static final int DEL_GM_USER = -22;
	public static final int GET_GM_USER_AUTH = -23;
	public static final int SET_GM_USER_AUTH = -24;
	public static final int MOD_GM_USER_PASS = -25;

	public static final int NEW_BI_SERVER_USER = -31;
	public static final int DEL_BI_SERVER_USER = -32;
	public static final int NEW_GAME_SERVER_USER = -33;
	public static final int DEL_GAME_SERVER_USER = -34;
	public static final int GET_BI_SERVER_USER_LIST = -35;
	public static final int GET_GAME_SERVER_USER_LIST = -36;

	public static final int REPORT = 1;
	public static final int ERROR_FROM_BI = 2;

	public static final int GET_LONG = 90;
	public static final int GET_STRING = 91;
	public static final int GET_LIST_LONG = 92;
	public static final int GET_LIST_STRING = 93;
	public static final int GET_MAP_LONG_LONG = 94;
	public static final int GET_MAP_LONG_STRING = 95;
	public static final int GET_MAP_STRING_LONG = 96;


	public static final int GET_SINGLE = 102;

	public static final int GET_USER_INSTALL_RETENTION = 99;
	public static final int GET_SUMMARY_INSTALL_PAY = 100;
	public static final int GET_USER_INSTALL_PAY_DAYS = 101;
	public static final int GET_USER_LTV = 108;
	public static final int GET_USER_PHONE_NUM = 109;
	public static final int GET_USER_DAU = 113;
	public static final int GET_USER_USER_WIN_LOSE = 114;
	public static final int GET_USER_FUFEILV = 115;
	public static final int GET_SUMMARY_FINANCE = 110;
	public static final int GET_SUMMARY_HUAFEI  = 111;
	public static final int GET_SUMMARY_BAOTU   = 112;
	public static final int GET_SUMMARY_HUIZONG = 115;
	public static final int GET_SUMMARY_INSTALL_PAY_HUIZONG = 116;
	public static final int GET_SUMMARY_FISH_HUIZONG = 117;
	public static final int GET_PAY_DETAIL = 118;
	public static final int GET_USER_SHICHANGBU = 119;
	public static final int GET_SUMMARY_FISH_FINANCE = 120;
	public static final int GET_USER_DANTOU = 121;
	public static final int GET_USER_JINRONG = 122;
	public static final int GET_USER_GAME_WIN = 123;
	public static final int GET_QUDAO_INSATLL = 124;
	public static final int GET_SUMMARY_DANTOU   = 125;
	public static final int GET_SUMMARY_ZICHONG   = 126;
	public static final int GET_SUMMARY_ZHOUBAO   = 127;

	public static final int GET_ZFB = 1000;


	public static final int GET_MTL_BASIC = 2001;
	public static final int GET_MTL_RANK = 2002;

	public static final int GET_MTL2 = 2010;

	public static final int GET_FRUIT = 3001;

	public static final int GET_FISH = 13001;

	public static final int GET_FISH_MB = 13002;

	public static final int GET_BRDZ  = 13003;

	public static final int GET_PPT = 4001;

	public static final int GET_DOGSPORT = 5001;

	public static final int GET_FARM_BASIC = 6001;

	public static final int GET_TEXASPOKER = 7001;

	public static final int GET_NEWPOKER = 7004;


	public static final int GET_SCMJ = 8001;

	public static final int GET_RONGLIAN_SUMMARY = 9001;

	public static final int GET_MP_COST = 10001;

	public static final int GET_MJDR = 11001;

	public static final int GET_PRODUCT = 12001;

	public static final int GET_ACTIVITY_GOLDEGG = 20001;
	public static final int GET_ACTIVITY_YIYUANDUOBAO = 20002;
	public static final int GET_ACTIVITY_YIYUANDUOBAO_DANQI = 20003;
	public static final int GET_ACTIVITY_YIYUANDUOBAO_DANQIDANREN = 20004;
	public static final int GET_ACTIVITY_WAKUANGCHAN = 20000;
	public static final int GET_ACTIVITY_JUBAOPEN = 20005;
	public static final int GET_ACTIVITY_DAZHUANPAN = 20006;
	public static final int GET_ACTIVITY_FANPAI = 20007;
	public static final int GET_ACTIVITY_NIANSHOU = 20008;
	public static final int GET_ACTIVITY_SHENGDAN = 20009;
	public static final int GET_DOWNLOAD = 20010;
	public static final int GET_ACTIVITY_YUANDAN = 20011;
	public static final int GET_ACTIVITY_PINTU = 20012;
	public static final int GET_ACTIVITY_DENGLONG = 20013;
	public static final int GET_ACTIVITY_HONGBAO = 20014;
	public static final int GET_ACTIVITY_TIEREN = 20015;
	public static final int GET_ACTIVITY_LUCKY_CHOUJIANG = 20016;
	public static final int GET_ACTIVITY_TAONIU = 20017;
	public static final int GET_ACTIVITY_NEWZHUANPAN = 20018;
	public static final int GET_ACTIVITY_SHEJI = 20019;
	public static final int GET_ACTIVITY_QIFU = 20020;
	public static final int GET_ACTIVITY_FISH_CHOUQIAN = 20021;
	public static final int GET_ACTIVITY_MOPING = 20022;
	public static final int GET_ACTIVITY_CHUO = 20023;
	public static final int GET_ACTIVITY_BAOZI = 20024;
	public static final int GET_ACTIVITY_GUANZI = 20025;
	public static final int GET_ACTIVITY_WANGPAI = 20026;
	public static final int GET_ACTIVITY_WANMI = 20027;
	public static final int GET_ACTIVITY_PAISHEN = 20028;
	public static final int GET_ACTIVITY_YUEBING = 20029;
	public static final int GET_ACTIVITY_FISH_NANGUA = 20030;
	public static final int GET_ACTIVITY_WANSHENG = 20031;
	public static final int GET_ACTIVITY_DAFUWENG = 20032;
	public static final int GET_ACTIVITY_ZADAN = 20033;
	public static final int GET_ACTIVITY_GANEN = 20034;
	public static final int GET_ACTIVITY_QIQIU = 20035;
	public static final int GET_ACTIVITY_SHUANGDAN = 20036;
	public static final int GET_ACTIVITY_SHENGDAN_FISH = 20037;
	public static final int GET_ACTIVITY_WAZI = 20038;
	public static final int GET_ACTIVITY_HAIDIDUOBAO = 20039;
	public static final int GET_ACTIVITY_QINGCAISHEN = 20040;
	public static final int GET_ACTIVITY_ZAJINDAN = 20041;
	public static final int GET_ACTIVITY_YYL  = 20042;
	public static final int GET_ACTIVITY_BEICAI  = 20043;
	public static final int GET_ACTIVITY_KAIXINZHUANPAN = 20044;


	public static final int GET_YELLOW_GIFT = 30001;

	public static final int GET_PROPS = 40001;
	public static final int GET_USER_RMB = 40002;

	public static final int GET_DDZ = 50003;

	public static final int GET_LIUSHUI = 6666;
	public static final int GET_PEOPLE = 6667;

}
