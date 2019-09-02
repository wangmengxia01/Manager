package com.hoolai.ccgames.bifront.vo;

public class Auth {

	public static final String ALL = "ALL";  // 表示所有选项
	
	// 项目组编号
	public static final String TP_GROUP = "TPG";    // 德州扑克项目组
	public static final String DWB_GROUP = "DWBG";   // 电玩吧项目组
	
	// 平台
	public static final String PF_TENCENT = "TENCENT";  // 腾讯平台
	
	// 游戏
	public static final String GAME_TEXASPOKER = "100632434";  // 德州扑克
	public static final String GAME_HLMAHJONG = "1104754063";  // 胡莱麻将
	public static final String GAME_HLCHESS = "1104737759";  // 胡莱棋牌
	public static final String GAME_HLDOUNIU = "1104881083";  // 血拼斗牛
	public static final String GAME_HLLANDLORDS = "1104830871";  // 斗地主合集
	public static final String GAME_HLTHIRTEEN = "1104947175";  // 精品十三张
	
	public static String generate( int cmd, String group, String platform, String app ) {
		return cmd + "-" + group + "-" + platform + "-" + app;
	}
}
