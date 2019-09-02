package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.*;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.ActivityView;
import com.hoolai.centersdk.item.ItemIdUtils;
import com.hoolai.centersdk.item.ItemManager;
import com.hoolai.centersdk.sdk.UserSdk;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Service( "actAction" )
public class ActivityAction extends BaseAction {

	public static String DEZHOU_APPID ="100632434";
	public static String MAJIANG_APPID ="1104754063";
	public static String DOUNIU_APPID ="1104881083";
	public static String SHISANZHANG_APPID ="1104947175";
	public static String QIPAI_APPID ="1104737759";


	private static List< String > oneDuoBaoJichuTableHead = Arrays.asList( "时间","参与总人数",
			"总消耗藏宝图数量", "人均消耗量", "已开奖商品消耗藏宝图总量" );
	private static List< String > HongBaoTableHead = Arrays.asList( "时间","场次id",
			"红包总数量", "红包总额度", "付费领取人数", "付费领取额度", "免费领取人数", "免费领取额度", "领取详情" );

	private static List< String > LuckyChouTableHead = Arrays.asList( "时间","活动总人数","红包触发次数", "抢红包人数","红包产出金额", "购买人数","胡莱币购买","胡莱币消耗数", "产出道具");
	private static List< String > LuckyChouChaTableHead = Arrays.asList( "时间","参加次数","红包触发次数", "抢红包次数","抢红包金额", "胡莱币购买","胡莱币消耗数", "产出道具");
	private static List< String > rankTableHead = Arrays.asList( "时间","用户id","红包用户名", "领取金额");
	private static List< String > GuessPokerRankTableHead = Arrays.asList( "时间","用户id","用户名","大王牌", "下注金币", "下注返奖", "金蛋下注", "金蛋返奖", "银弹下注", "银弹返奖", "金币抽水"
			, "金蛋抽水", "银弹抽水");

	private static List< String > FanpaiTableHead = Arrays.asList( "时间","翻牌人数",
			"消费翻牌次数", "消费总数量" ,"总获得点数" , "兑换元宝1次数" , "兑换元宝2次数" , "兑换元宝3次数"
			, "兑换元宝4次数" , "兑换小牛币次数", "兑换牛币次数", "道具详情" );
	private static List< String > DianshuJichuTableHead = Arrays.asList( "用户ID",
			"获得点数" );
	private static List< String > OpenXiangxiTableHead = Arrays.asList( "本期已开奖商品期号",
			"对应名称","中奖人id","中奖人昵称");

	private static List< String > SheShouTableHead = Arrays.asList( "时间",
			"用户ID","用户名","奖励");


	private static List< String > NewZhuanPanTableHead = Arrays.asList( "时间","转盘人数 ",
			"转盘次数", "付费率","胡莱币消耗", "胡莱币购买","转盘产出","奖池产出", "排行产出", "比例");
	private static List< String > KaiXinZhuanPanTableHead = Arrays.asList( "时间","展示人数","展示次数","展示率",
			"参与人数","参与次数","付费率", "胡莱币消耗", "转盘产出", "比例","赢--不赌--输","奖池");

	private static List< String > NanGuaTableHead = Arrays.asList( "时间","展示人数","展示次数","展示率",
			"参与人数","参与次数","付费率", "胡莱币消耗","正常产出", "排行产出","奖池/狂欢产出","比例");
	private static List< String > QingCaiShenTableHead = Arrays.asList( "时间","展示人数","展示次数","展示率",
			"参与人数","参与次数","参与率", "掉落情况","兑换次数","积分消耗","拼图兑换产出","积分兑换产出","产出总价值");
	private static List< String > HaiDiBaoZangTableHead = Arrays.asList( "时间","展示人数","展示次数","展示率",
			"参与人数","参与次数","付费率", "胡莱币消耗", "任务胡莱币产出","刷新任务消耗","正常产出","翻牌产出","比例");
	private static List< String > QiQiuTableHead= Arrays.asList( "时间","展示人数","展示次数","展示率",
			"参与人数","参与次数","付费率", "胡莱币消耗","礼包购买","气球产出","字符兑换产出","礼包产出","比例");
	private static List< String > ZaDanTableHead = Arrays.asList( "时间","展示人数","展示次数","展示率",
			"参与人数","参与次数","付费率", "胡莱币消耗", "抽签产出","比例");
	private static List< String > ChouQianTableHead = Arrays.asList( "时间","展示人数","展示次数","展示率",
			"参与人数","参与次数","付费率", "钻石消耗", "正常产出","比例");
	private static List< String > ChuoTableHead = Arrays.asList( "时间","参与人数",
			"参与次数","付费率","胡莱币消耗", "胡莱币购买","活动产出","全服/彩池/领取", "排行产出", "比例");
	private static List< String > YYLTableHead = Arrays.asList( "时间","参与人数",
			"参与次数","次数类型","付费率","胡莱币消耗", "胡莱币购买","活动产出","彩池", "排行产出", "比例");
	private static List< String > WanShengTableHead = Arrays.asList( "时间","参与人数",
			"参与次数","付费率","胡莱币消耗", "胡莱币购买","活动产出","抽奖/转盘/任务", "排行产出");
	private static List< String > YueBingTableHead = Arrays.asList( "时间","参与人数",
			"参与次数","付费率","胡莱币消耗","月饼祈福消耗","月饼使用", "胡莱币购买","活动产出","全服/彩池/领取", "排行产出","月饼祈福/月饼产出");
	private static List< String > GanEnTableHead = Arrays.asList( "时间","参与人数",
			"参与次数","付费率","胡莱币消耗", "胡莱币购买","普通产出","特殊产出", "奖池/兑换产出","排行产出");
	private static List< String > ZaJinDanTableHead = Arrays.asList( "时间","参与人数",
			"参与次数","付费率","胡莱币消耗","特殊蛋次数", "胡莱币购买","普通产出","特殊蛋", "奖池产出","排行产出","比例");
	private static List< String > GuanZiTableHead = Arrays.asList( "时间","参与人数",
			"参与次数","付费率","胡莱币消耗", "胡莱币购买","活动产出","彩金", "排行产出");
	private static List< String > BeiCaiTableHead = Arrays.asList( "时间","参与人数",
			"参与次数","投注金额","返奖金额","金币抽水", "投注金弹","返奖金弹","金弹抽水","投注银弹","返奖银弹","银弹抽水","财神数量", "每轮人数");

	private static List< String > SheJiTableHead = Arrays.asList( "时间","参与人数",
			"参与次数","付费率","胡莱币消耗", "胡莱币购买","活动产出","全服领奖", "排行产出");

	private static List< String > QiFuTableHead = Arrays.asList( "时间","祈福人数 ",
			"祈福次数", "消耗道具", "祈福产出");
	private static List< String > ShuangDanTableHead = Arrays.asList( "时间","人数 ",
			"次数", "道具掉落", "兑换产出");
	private static List< String > TaoNiuTableHead = Arrays.asList( "时间","套牛人数 ",
			"套牛次数", "胡莱币消耗", "胡莱币购买","牛票产出数量","任务产出", "兑换产出");

	private static List< String > KuangChanTableHead = Arrays.asList( "时间","展示人数 ",
			"展示次数 ", "参与人数 ", "矿铲消耗 ", "元宝产出 ",
			"道具产出 ", "吸引力 ", "参与率 ");
	private static List< String > JuBaoTableHead = Arrays.asList( "时间","展示人数 ",
			"展示次数 ", "参与人数 ", "聚宝点数消耗 ", "元宝产出 ",
			"道具产出 ", "吸引力 ", "参与率 ");
	private static List< String > ZhuanPanTableHead = Arrays.asList( "时间","展示人数 ",
			"展示次数 ", "参与人数 ","购买人数 ","胡莱币购买 ", "胡莱币消耗 ", "元宝产出 ",
			"道具产出 ", "吸引力 ", "参与率 ");
	private static List< String > DengLongTableHead = Arrays.asList( "时间","展示人数 ",
			"展示次数 ", "参与人数 ","获得灯笼人数 ","获得灯笼数量", "灯笼消耗", "元宝产出 ",
			"道具产出 ", "吸引力 ", "参与率 ");
	private static List< String > GoldEggTableHead = Arrays.asList( "时间","展示人数 ",
			"展示次数 ", "参与人数 ","购买人数 ","胡莱币购买 ", "胡莱币消耗 ", "元宝产出 ",
			"道具产出 ", "小玩法道具 ", "吸引力 ", "参与率 ");
	private static List< String > YandanTableHead = Arrays.asList( "时间","展示人数 ",
			"展示次数 ", "参与人数 ","购买人数 ","胡莱币购买 ", "胡莱币消耗 ", "元宝产出 ", "积分产出 ",
			"兑换人数","道具产出 ", "吸引力 ", "参与率 ");
	private static List< String > NianshouTableHead = Arrays.asList( "时间","展示人数 ",
			"展示次数 ", "参与人数 ","胡莱币购买 ", "胡莱币消耗 ", "元宝产出 ",
			"道具产出 ", "吸引力 ", "参与率 ","抢红包人数","抢红包次数");
	private static List< String > DanQiTableHead1= Arrays.asList( "时间","已购买号码总数",
			"所需号码总数" ,"开奖结果");
	private static List< String > DownloadTableHead = Arrays.asList( "时间","安卓总人数",
			"安卓微信查看" ,"安卓下载","IOS总人数",
			"IOS微信查看" ,"IOS下载");
	private static List< String > DanQiTableHead2 = Arrays.asList( "每个参与玩家ID","玩家昵称",
			"参与玩家购买号码个数");

	private static List< String > DanQiDanRenTableHead = Arrays.asList( "该玩家本期购买号码",
			"开奖结果");

	public void getHongBaoPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_HONGBAO).forward( req, resp );
	}

	public void getHongBaoDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_HONGBAO_DZ).forward( req, resp );
	}

	public void getPintuDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_PINTU_DZ).forward( req, resp );
	}
	public void getTieRenDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_TIEREN_DZ).forward( req, resp );
	}
	public void getTieRenPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_TIEREN).forward( req, resp );
	}

	public void getPintuPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_PINTU).forward( req, resp );
	}
	public void getDengLongDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_DENGLONG_DZ).forward( req, resp );
	}

	public void getDengLongPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_DENGLONG).forward( req, resp );
	}
	public void getGoldEggDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_GOLD_EGG_DZ).forward( req, resp );
	}

	public void getGoldEggPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_GOLD_EGG).forward( req, resp );
	}
	public void getFanpaiPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_FANPAI_ZONG).forward( req, resp );
	}
	public void getFanpaiDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_FANPAI_DEZHOU).forward( req, resp );
	}
	public void getShengdanPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_SHENGDAN).forward( req, resp );
	}

	public void getGuessPokerPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_GUESSPOKER).forward( req, resp );
	}
	public void getGuessPokerfenPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_GUESSPOKER_FEN).forward( req, resp );
	}
	public void getGuessPokerChaPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_GUESSPOKER_CHA).forward( req, resp );
	}
	public void getGuessPokerRankPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_GUESSPOKER_RANK).forward( req, resp );
	}
	public void getKaiXinZhuanPanPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_KAIXINZHUANPAN).forward( req, resp );
	}
	public void getKaiXinZhuanPanChaPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_KAIXINZHUANPAN_CHA).forward( req, resp );
	}
	public void getKaiXinZhuanPanFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_KAIXINZHUANPAN_FISH).forward( req, resp );
	}

	public void getShengdanDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_SHENGDAN_DEZHOU).forward( req, resp );
	}
	public void getShengdanMJPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_SHENGDAN_MAJIANG).forward( req, resp );
	}
	public void getYuandanPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_YUANDAN).forward( req, resp );
	}
	public void getYuandanDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_YUANDAN_DEZHOU).forward( req, resp );
	}
	public void getYuandanMJPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_YUANDAN_MAJIANG).forward( req, resp );
	}

	public void getLuckyChouDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_LUCKYCHOU_DEZHOU).forward( req, resp );
	}
	public void getLuckyChouRankPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_LUCKYCHOU_RANK_DEZHOU).forward( req, resp );
	}
	public void getLuckyChouPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_LUCKYCHOU).forward( req, resp );
	}
	public void getLuckyChouChaPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_LUCKYCHOU_CHA).forward( req, resp );
	}


	public void getDuoBaoPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_DUOBAO).forward( req, resp );
	}
	public void getDownloadPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_DOWNLOAD).forward( req, resp );
	}
	public void getDuoBaoJichuPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_DUOBAO_DUOBAOJICHU).forward( req, resp );
	}
	public void getDanQiPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_DUOBAO_DANQI).forward( req, resp );
	}

	public void getKuangChanPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WAKUANGCHAN).forward( req, resp );
	}
	public void getKuangChanZongPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WAKUANGCHAN_ZONG).forward( req, resp );
	}

	public void getJuBaoPageMJ( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_JUBAOPEN_MAJIANG).forward( req, resp );
	}
	public void getJuBaoPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_JUBAOPEN_DEZHOU ).forward( req, resp );
	}
	public void getJuBaoPageZong( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_JUBAOPEN).forward( req, resp );
	}

	public void getZhuanpanPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_ZHUANPAN).forward( req, resp );
	}
	public void getTaoNiuPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_TAONIU).forward( req, resp );
	}
	public void getTaoNiuDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_TAONIU_DZ ).forward( req, resp );
	}

	public void getNewZhuanPanPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_NEWZHUANPAN ).forward( req, resp );
	}
	public void getNewZhuanPanDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_NEWZHUANPAN_DEZHOU).forward( req, resp );
	}
	public void getNewZhuanPanChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_NEWZHUANPAN_CHA_DEZHOU).forward( req, resp );
	}

	public void getSheJiPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_SHEJI ).forward( req, resp );
	}
	public void getSheJiDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_SHEJI_DEZHOU ).forward( req, resp );
	}
	public void getSheJiChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_SHEJI_CHA_DEZHOU).forward( req, resp );
	}
	public void getQiQiuPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_FISH_QIQIU ).forward( req, resp );
	}
	public void getQiQiuFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_QIQIU_FISH).forward( req, resp );
	}
	public void getQiQiuChaPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_QIQIU_CHA_FISH ).forward( req, resp );
	}
	public void getQingCaiShenPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_FISH_QINGCAISHEN ).forward( req, resp );
	}
	public void getQingCaiShenFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_QINGCAISHEN_FISH ).forward( req, resp );
	}
	public void getQingCaiShenChaPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_QINGCAISHEN_CHA_FISH ).forward( req, resp );
	}
	public void getHaiDiDuoBaoPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_FISH_HAIDIDUOBAO ).forward( req, resp );
	}
	public void getHaiDiDuoBaoFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_HAIDIDUOBAO_FISH ).forward( req, resp );
	}
	public void getHaiDiDuoBaoChaPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_HAIDIDUOBAO_CHA_FISH ).forward( req, resp );
	}
	public void getZaJinDanPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_ZAJINDAN ).forward( req, resp );
	}
	public void getZaJinDanDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_ZAJINDAN_DEZHOU ).forward( req, resp );
	}
	public void getZaJinDanChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_ZAJINDAN_CHA_DEZHOU).forward( req, resp );
	}

	public void getGanEnPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_GANEN ).forward( req, resp );
	}
	public void getGanEnDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_GANEN_DEZHOU ).forward( req, resp );
	}
	public void getGanEnChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_GANEN_CHA_DEZHOU).forward( req, resp );
	}

	public void getPaiShenPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_PAISHEN ).forward( req, resp );
	}
	public void getPaiShenDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_PAISHEN_DEZHOU ).forward( req, resp );
	}
	public void getPaiShenChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_PAISHEN_CHA_DEZHOU).forward( req, resp );
	}

	public void getYYLPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_YYL ).forward( req, resp );
	}
	public void getYYLDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_YYL_DEZHOU ).forward( req, resp );
	}
	public void getYYLChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_YYL_CHA_DEZHOU).forward( req, resp );
	}
	public void getMoPingPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_MOPING ).forward( req, resp );
	}
	public void getMoPingDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_MOPING_DEZHOU ).forward( req, resp );
	}
	public void getMoPingChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_MOPING_CHA_DEZHOU).forward( req, resp );
	}
	public void getNanGuaPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_FISH_NANGUA ).forward( req, resp );
	}
	public void getNanGuaFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_NANGUA_FISH ).forward( req, resp );
	}
	public void getNanGuaChaFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_NANGUA_CHA_FISH).forward( req, resp );
	}
	public void getZaDanPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_FISH_ZADAN  ).forward( req, resp );
	}
	public void getZaDanFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_ZADAN_FISH ).forward( req, resp );
	}
	public void getZaDanChaFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_ZADAN_CHA_FISH).forward( req, resp );
	}
	public void getDaFuWengPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_FISH_DAFUWENG ).forward( req, resp );
	}
	public void getDaFuWengFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_DAFUWENG_FISH ).forward( req, resp );
	}
	public void getDaFuWengChaFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_DAFUWENG_CHA_FISH).forward( req, resp );
	}
	public void getShengDanFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_FISH_SHENGDAN ).forward( req, resp );
	}
	public void getShengDanXiangXiFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_SHENGDAN_FISH ).forward( req, resp );
	}
	public void getShengDanFishChaPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_SHENGDAN_CHA_FISH).forward( req, resp );
	}
	public void getChuoPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_CHUO ).forward( req, resp );
	}
	public void getChuoDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_CHUO_DEZHOU ).forward( req, resp );
	}
	public void getChuoChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_CHUO_CHA_DEZHOU).forward( req, resp );
	}
	public void getBaoZiPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_BAOZI ).forward( req, resp );
	}
	public void getBaoZiDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_BAOZI_DEZHOU ).forward( req, resp );
	}
	public void getBaoZiChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_BAOZI_CHA_DEZHOU).forward( req, resp );
	}
	public void getWaZiPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WAZI ).forward( req, resp );
	}
	public void getWaZiDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WAZI_DEZHOU ).forward( req, resp );
	}
	public void getWaZiChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WAZI_CHA_DEZHOU).forward( req, resp );
	}
	public void getGuanZiPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_GUANZI ).forward( req, resp );
	}
	public void getGuanZiDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_GUANZI_DEZHOU ).forward( req, resp );
	}
	public void getGuanZiChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_GUANZI_CHA_DEZHOU).forward( req, resp );
	}
	public void getChouQianPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_CHOUQIAN).forward( req, resp );
	}
	public void getChouQianFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_CHOUQIAN_FISH ).forward( req, resp );
	}
	public void getChouQianChaFishPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_CHOUQIAN_CHA_FISH ).forward( req, resp );
	}
	public void getQiFuPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_QIFU ).forward( req, resp );
	}
	public void getQiFuDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_QIFU_DEZHOU ).forward( req, resp );
	}
	public void getQiFuChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_QIFU_CHA_DEZHOU).forward( req, resp );
	}

	public void getWangPaiPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WANGPAI ).forward( req, resp );
	}
	public void getWangPaiDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WANGPAI_DEZHOU ).forward( req, resp );
	}
	public void getWangPaiChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WANGPAI_CHA_DEZHOU ).forward( req, resp );
	}

	public void getWanMiPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WANMI ).forward( req, resp );
	}
	public void getWanMiDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WANMI_DEZHOU ).forward( req, resp );
	}
	public void getWanMiChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WANMI_CHA_DEZHOU ).forward( req, resp );
	}

	public void getWanShengPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WANSHENG ).forward( req, resp );
	}
	public void getWanShengDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WANSHENG_DEZHOU ).forward( req, resp );
	}
	public void getWanShengChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WANSHENG_CHA_DEZHOU ).forward( req, resp );
	}

	public void getKuangChanPageMJ( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_WAKUANGCHANMJ).forward( req, resp );
	}
	public void getNianshouPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_NIANSHOU ).forward( req, resp );
	}
	public void getNianshouDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_NIANSHOU_DEZHOU ).forward( req, resp );
	}
	public void getDanQiDanRenPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_DUOBAO_DANQIDANREN).forward( req, resp );
	}

	public void getYueBingPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_YUEBING ).forward( req, resp );
	}
	public void getYueBingDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_YUEBING_DEZHOU ).forward( req, resp );
	}
	public void getYueBingChaDZPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_YUEBING_CHA_DEZHOU).forward( req, resp );
	}


	public List< ActivityView.huodong > getWanShengChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//点灯次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//转盘次数
			val.Count2 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -2 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//正常产出
			List<String> zhengchangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//巨奖/产出/renwu
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1,-2,-3  ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );


			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			zhengchangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryWanShengChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_WANSHENG, getWanShengChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 56 ),
				WanShengTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
						if (itemId == 10537 )
							pw.write("转次数:"+ info.Count2 + "  奖励胡莱币:" +  itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}


	public List< ActivityView.huodong > getWanShengDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") ;", c );

			//点灯次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") ;", c );
			//转盘次数
			val.Count2 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -2 and activity_id ="+ actId +"  and appid in ("+ appid + ") ;", c );

			//正常产出
			List<String> zhengchangGetInfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			//巨奖/月饼祈福中奖/产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1,-2,-3 ) and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			zhengchangGetInfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;


			return val;
		} );
	}

	public void queryWanShengDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_WANSHENG, getWanShengDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 56 ),
				WanShengTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
						if (itemId == 10537 )
							pw.write("转次数:"+ info.Count2 + "  奖励胡莱币:" +  itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );

				} );
	}


	public List< ActivityView.huodong > getNanGuaChaData(HttpServletRequest req, HttpServletResponse resp ,long actId )
			throws Exception {
		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );
		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			long dau = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + " ;", c );

			val.Count2 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = -2  and user_id = "+ user_id +";", c );

			//展示率
			val.canyulv = val.seepeople * 1.0 / dau ;
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			if (actId == 4)
			{
				val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and cost_count = 5  and user_id = "+ user_id +";", c )
						+
						ServiceManager.getSqlService().queryLong( "select count( id ) " +
								" from t_newactivity " +
								" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and cost_count = 28  and user_id = "+ user_id +";", c ) * 6;
			}
			if (actId == 5 || actId == 12)
			{
				val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and cost_count = 2  and user_id = "+ user_id +";", c );

				val.Count3	= ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and cost_count = 5  and user_id = "+ user_id +";", c );

				val.Count4 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and cost_count = 10 and user_id = "+ user_id +";", c );
			}
			if (actId == 8 )
			{
				val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and user_id = "+ user_id +";", c );
				val.Count2 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and get_info = '-1:50000000;' and user_id = "+ user_id +";", c );
			}

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and user_id = "+ user_id +";", c );

			//正产产出
			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 10537  and user_id = "+ user_id +";", c );
			Map< Long, Long > getMap = new HashMap<>();
			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			val.daoju = getMap;
			if (actId == 8)
			{
				val.daoju.put(2l,val.Count2);
			}

			//排行产出
			List<String> paihanggetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 0  and user_id = "+ user_id +";", c );
			Map< Long, Long > paihanggetMap = new HashMap<>();
			paihanggetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), paihanggetMap ) );
			val.otherDaoju = paihanggetMap;

			//狂欢 奖池产出
			List<String> kuanghuangetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id in ( -2,-1 )  and user_id = "+ user_id +";", c );
			Map< Long, Long > kuanghuangetMap = new HashMap<>();
			kuanghuangetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), kuanghuangetMap ) );
			val.otherDaoju2 = kuanghuangetMap;
			if (val.xiaohao == 0 )
			{val.xiaohao = 1;}

			if (actId != 10){
				val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000
						+ getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000
						+ getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
						+ getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
						+ getLong(getMap , -4 ) * 4000
						+ getLong(getMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
						+ getLong(getMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
						+ getLong(getMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
//					+ getLong(paihanggetMap , -1) + getLong(paihanggetMap , 20015 ) * 20000 + getLong(paihanggetMap , 20016 ) * 200000 + getLong(paihanggetMap , 20017 ) * 1000000 + getLong(paihanggetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
//					+  getLong(paihanggetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth) +   getLong(paihanggetMap , -4 ) * 3000 + getLong(paihanggetMap , 20033 ) * (ItemManager.getInstance().get( 20033 ).worth) +
						+ getLong(kuanghuangetMap , -1) + getLong(kuanghuangetMap , 20015 ) * 20000
						+ getLong(kuanghuangetMap , 20016 ) * 200000
						+ getLong(kuanghuangetMap , 20017 ) * 1000000
						+ getLong(kuanghuangetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
						+ getLong(kuanghuangetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
						+ getLong(kuanghuangetMap , -4 ) * 4000
						+ getLong(kuanghuangetMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
						+ getLong(kuanghuangetMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
						+ getLong(kuanghuangetMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
				) / ( val.xiaohao );}
			if (actId == 10 )
			{
				val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and user_id = "+ user_id +";", c );
				val.Count2 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -1 and activity_id ="+ actId +" and user_id = "+ user_id +";", c );
				val.Count3 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count = 1  and activity_id ="+ actId +" and user_id = "+ user_id +";", c );
				val.Count4 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count = 20  and activity_id ="+ actId +" and user_id = "+ user_id +";", c );
				val.Count5 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count = 50  and activity_id ="+ actId +" and user_id = "+ user_id +";", c );
				val.Count6 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count = 100  and activity_id ="+ actId +" and user_id = "+ user_id +";", c );

				List<String> libaogetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 10537 and cost_count in (1,20,50,100) and user_id = "+ user_id +";", c );
				Map< Long, Long > libaogetMap = new HashMap<>();
				libaogetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), libaogetMap  ) );
				List<String> getinfo1 = ServiceManager.getSqlService().queryListString( "select get_info " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 10537 and cost_count = 5 and user_id = "+ user_id +";", c );
				Map< Long, Long > getMap1 = new HashMap<>();
				getinfo1.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
				val.daoju = getMap1;
				val.otherDaoju3 = libaogetMap ;
				val.bili = (getLong(getMap1 , -1) + getLong(getMap1 , 20015 ) * 20000
						+ getLong(getMap1 , 20016 ) * 200000 + getLong(getMap1 , 20017 ) * 1000000
						+ getLong(getMap1 , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
						+ getLong(getMap1 , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
						+ getLong(getMap1 , -4 ) * 4000
						+ getLong(getMap1 , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
						+ getLong(getMap1 , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
						+ getLong(getMap1 , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
//					+ getLong(paihanggetMap , -1) + getLong(paihanggetMap , 20015 ) * 20000 + getLong(paihanggetMap , 20016 ) * 200000 + getLong(paihanggetMap , 20017 ) * 1000000 + getLong(paihanggetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
//					+  getLong(paihanggetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth) +   getLong(paihanggetMap , -4 ) * 3000 + getLong(paihanggetMap , 20033 ) * (ItemManager.getInstance().get( 20033 ).worth) +
						+ getLong(kuanghuangetMap , -1) + getLong(kuanghuangetMap , 20015 ) * 20000
						+ getLong(kuanghuangetMap , 20016 ) * 200000
						+ getLong(kuanghuangetMap , 20017 ) * 1000000
						+ getLong(kuanghuangetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
						+ getLong(kuanghuangetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
						+ getLong(kuanghuangetMap , -4 ) * 4000
						+ getLong(kuanghuangetMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
						+ getLong(kuanghuangetMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
						+ getLong(kuanghuangetMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
						+ getLong(libaogetMap , -1) + getLong(libaogetMap , 20015 ) * 20000
						+ getLong(libaogetMap , 20016 ) * 200000
						+ getLong(libaogetMap , 20017 ) * 1000000
						+ getLong(libaogetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
						+ getLong(libaogetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
						+ getLong(libaogetMap , -4 ) * 4000
						+ getLong(libaogetMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
						+ getLong(libaogetMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
						+ getLong(libaogetMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
				) / ( val.xiaohao );
			}
			return val;
		} );
	}

	public void queryNanGuaCha( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_FISH_NANGUA, getNanGuaChaData( req, resp  , 4 ),
				NanGuaTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:次数:" + info.Count2 + "数量:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}
	public void queryDaFuWengCha( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_DAFUWENG, getNanGuaChaData( req, resp  , 5 ),
				NanGuaTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>初级：" + info.Count1 + "<br>" );
					pw.write( "中级：" + info.Count3 + "<br>" );
					pw.write( "高级：" + info.Count4 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:次数:" + info.Count2 + "数量:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}

	public void queryDaFuWeng( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_DAFUWENG, getNanGuaData( req, resp  , 5  ),
				NanGuaTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>初级：" + info.Count1 + "<br>" );
					pw.write( "中级：" + info.Count3 + "<br>" );
					pw.write( "高级：" + info.Count4 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:次数:" + info.Count2 + "数量:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}

	public void queryShengDanCha( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryMultiList( req, resp,
				CommandList.GET_ACTIVITY_SHENGDAN_FISH, getNanGuaChaData( req, resp  , 12 ),
				NanGuaTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>初级：" + info.Count1 + "<br>" );
					pw.write( "中级：" + info.Count3 + "<br>" );
					pw.write( "高级：" + info.Count4 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:次数:" + info.Count2 + "数量:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				},getShuangDanChaData( req, resp ),
				ShuangDanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.people + "</td>" );
					pw.write( "<td>" + info.count + "</td>" );
					pw.write( "<td>" );
					info.diaoluo.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.chanchu.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				},null );
	}

	public void queryShengDan( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryMultiList( req, resp,
				CommandList.GET_ACTIVITY_SHENGDAN_FISH, getNanGuaData( req, resp  , 12  ),
				NanGuaTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>初级：" + info.Count1 + "<br>" );
					pw.write( "中级：" + info.Count3 + "<br>" );
					pw.write( "高级：" + info.Count4 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("爆彩:次数:" + info.Count2 + " 金币数量:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 0 && itemId <= 120  )
							pw.write("炮台:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				},getShuangDanData( req, resp ),
				ShuangDanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.people + "</td>" );
					pw.write( "<td>" + info.count + "</td>" );
					pw.write( "<td>" );
					info.diaoluo.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.chanchu.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				},null );
	}


	public void queryZaDan( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_ZADAN, getNanGuaData( req, resp  , 8 ),
				ZaDanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == 2 )
						{
							pw.write("大奖触发次数:" + itemCount + "次<br>");
						}
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
						if ( itemId !=2 )
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}
	public void queryZaDanCha( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_ZADAN, getNanGuaChaData( req, resp  , 8 ),
				ZaDanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == 2 ) {
							pw.write("大奖触发次数:" + itemCount + "次<br>");
						}
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
						if ( itemId !=2 )
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}


	public List< ActivityView.huodong > getNanGuaData(HttpServletRequest req, HttpServletResponse resp ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.Count2 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = -2  ;", c );

			long dau = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + " ;", c );

			//展示率
			val.canyulv = val.seepeople * 1.0 / dau ;
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			if (actId == 4)
			{
				val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and cost_count = 5 ;", c )
						+
						ServiceManager.getSqlService().queryLong( "select count( id ) " +
								" from t_newactivity " +
								" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id =  10537 and activity_id ="+ actId +" and cost_count = 28 ;", c ) * 6;
			} if ( actId ==5 || actId == 12)
			{
				val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and cost_count = 2 ;", c );

				val.Count3	= ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and cost_count = 5  ;", c );

				val.Count4 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and cost_count = 10 ;", c );
			}
			if (actId == 8 )
			{
				val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" ;", c );
				val.Count2 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and get_info = '-1:50000000;';", c );
			}

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" ;", c );

			//正产产出
			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 10537 ;", c );
			Map< Long, Long > getMap = new HashMap<>();
			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			val.daoju = getMap;
			if (actId == 8  )
			{
				val.daoju.put(  2L , val.Count2 );
			}

			//排行产出
			List<String> paihanggetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 0 ;", c );
			Map< Long, Long > paihanggetMap = new HashMap<>();
			paihanggetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), paihanggetMap ) );
			val.otherDaoju = paihanggetMap;

			//狂欢 奖池产出
			List<String> kuanghuangetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id in ( -2,-1 );", c );
			Map< Long, Long > kuanghuangetMap = new HashMap<>();
			kuanghuangetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), kuanghuangetMap ) );
			val.otherDaoju2 = kuanghuangetMap;
			if (val.xiaohao == 0 )
			{val.xiaohao = 1;}
			if (actId != 10){
				val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000
						+ getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000
						+ getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
						+ getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
						+ getLong(getMap , -4 ) * 4000
						+ getLong(getMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
						+ getLong(getMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
						+ getLong(getMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
//					+ getLong(paihanggetMap , -1) + getLong(paihanggetMap , 20015 ) * 20000 + getLong(paihanggetMap , 20016 ) * 200000 + getLong(paihanggetMap , 20017 ) * 1000000 + getLong(paihanggetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
//					+ getLong(paihanggetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth) +   getLong(paihanggetMap , -4 ) * 3000 + getLong(paihanggetMap , 20033 ) * (ItemManager.getInstance().get( 20033 ).worth) +
						+ getLong(kuanghuangetMap , -1) + getLong(kuanghuangetMap , 20015 ) * 20000
						+ getLong(kuanghuangetMap , 20016 ) * 200000
						+ getLong(kuanghuangetMap , 20017 ) * 1000000
						+ getLong(kuanghuangetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
						+ getLong(kuanghuangetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
						+ getLong(kuanghuangetMap , -4 ) * 4000
						+ getLong(kuanghuangetMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
						+ getLong(kuanghuangetMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
						+ getLong(kuanghuangetMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
				) / ( val.xiaohao );}
			if (actId == 10 )
			{
				val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" ;", c );
				val.Count2 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -1 and activity_id ="+ actId +" ;", c );
				val.Count3 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count = 1  and activity_id ="+ actId +";", c );
				val.Count4 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count = 20  and activity_id ="+ actId +";", c );
				val.Count5 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count = 50  and activity_id ="+ actId +";", c );
				val.Count6 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count = 100  and activity_id ="+ actId +";", c );

				List<String> libaogetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 10537 and cost_count in (1,20,50,100);", c );
				Map< Long, Long > libaogetMap = new HashMap<>();
				libaogetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), libaogetMap  ) );
				List<String> getinfo1 = ServiceManager.getSqlService().queryListString( "select get_info " +
						" from t_newactivity " +
						" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 10537 and cost_count = 5 ;", c );
				Map< Long, Long > getMap1 = new HashMap<>();
				getinfo1.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
				val.daoju = getMap1;
				val.otherDaoju3 = libaogetMap ;
				val.bili = (getLong(getMap1 , -1) + getLong(getMap1 , 20015 ) * 20000
						+ getLong(getMap1 , 20016 ) * 200000 + getLong(getMap1 , 20017 ) * 1000000
						+ getLong(getMap1 , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
						+ getLong(getMap1 , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
						+ getLong(getMap1 , -4 ) * 4000
						+ getLong(getMap1 , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
						+ getLong(getMap1 , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
						+ getLong(getMap1 , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
//					+ getLong(paihanggetMap , -1) + getLong(paihanggetMap , 20015 ) * 20000 + getLong(paihanggetMap , 20016 ) * 200000 + getLong(paihanggetMap , 20017 ) * 1000000 + getLong(paihanggetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
//					+  getLong(paihanggetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth) +   getLong(paihanggetMap , -4 ) * 3000 + getLong(paihanggetMap , 20033 ) * (ItemManager.getInstance().get( 20033 ).worth) +
						+ getLong(kuanghuangetMap , -1) + getLong(kuanghuangetMap , 20015 ) * 20000
						+ getLong(kuanghuangetMap , 20016 ) * 200000
						+ getLong(kuanghuangetMap , 20017 ) * 1000000
						+ getLong(kuanghuangetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
						+ getLong(kuanghuangetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
						+ getLong(kuanghuangetMap , -4 ) * 4000
						+ getLong(kuanghuangetMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
						+ getLong(kuanghuangetMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
						+ getLong(kuanghuangetMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
						+ getLong(libaogetMap , -1) + getLong(libaogetMap , 20015 ) * 20000
						+ getLong(libaogetMap , 20016 ) * 200000
						+ getLong(libaogetMap , 20017 ) * 1000000
						+ getLong(libaogetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
						+ getLong(libaogetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
						+ getLong(libaogetMap , -4 ) * 4000
						+ getLong(libaogetMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
						+ getLong(libaogetMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
						+ getLong(libaogetMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
				) / ( val.xiaohao );
			}

			return val;
		} );
	}

	public void queryQiQiuCha( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_QIQIU, getNanGuaChaData( req, resp  , 10 ),
				QiQiuTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>限量1:" + info.Count3 + "<br>" );
					pw.write( "限量20:" + info.Count4 + "<br>" );
					pw.write( "限量50:" + info.Count5 + "<br>" );
					pw.write( "限量100:" + info.Count6 + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId <= 20 && itemId >= 0  )
							pw.write("炮台:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:次数:" + info.Count2 + "数量:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId <= 20 && itemId >= 0  )
							pw.write("炮台:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju3.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:次数:" + info.Count2 + " 数量:" + itemCount / 10000 + "万<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}

	public void queryQiQiu( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_QIQIU, getNanGuaData( req, resp  , 10  ),
				QiQiuTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>限量1:" + info.Count3 + "<br>" );
					pw.write( "限量20:" + info.Count4 + "<br>" );
					pw.write( "限量50:" + info.Count5 + "<br>" );
					pw.write( "限量100:" + info.Count6 + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 130  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:次数:" + info.Count2 + "数量:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId <= 20 && itemId >= 0  )
							pw.write("炮台:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju3.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}

	private List< ActivityView.ShuangDan > getShuangDanData(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.ShuangDan val = new ActivityView.ShuangDan();
			val.begin = TimeUtil.ymdFormat().format( beg );

			val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 13 ;", c );

			val.count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 13 ;", c );

			val.diaoluo = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
					" from t_fish_item  " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					"   and item_count > 0 and item_id in (20047,20048,20049,20050) " +
					" group by item_id;", c );

			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 13 ;", c );
			Map< Long, Long > getMap = new HashMap<>();
			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			val.chanchu = getMap;

			return val;
		} );
	}
	private List< ActivityView.ShuangDan > getShuangDanChaData(HttpServletRequest req, HttpServletResponse resp  )
			throws Exception {
		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );
		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.ShuangDan val = new ActivityView.ShuangDan();
			val.begin = TimeUtil.ymdFormat().format( beg );

			val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 13 and user_id = " + user_id + ";", c );

			val.count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 13 and user_id = " + user_id + ";", c );

			val.diaoluo = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
					" from t_fish_item  " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					"   and item_count > 0 and item_id in (20047,20048,20049,20050) and user_id = " + user_id  +
					" group by item_id;", c );

			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 13 and user_id = " + user_id + ";", c );
			Map< Long, Long > getMap = new HashMap<>();
			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			val.chanchu = getMap;

			return val;
		} );
	}

	public void queryNanGua( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_FISH_NANGUA, getNanGuaData( req, resp  , 4  ),
				NanGuaTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:次数:" + info.Count2 + "数量:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}


	public List< ActivityView.huodong > getChouQianChaData(HttpServletRequest req, HttpServletResponse resp ,long actId )
			throws Exception {
		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );
		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );


			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.Count2 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = -2  ;", c );

			long dau = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + " ;", c );

			//展示率
			val.canyulv = val.seepeople * 1.0 / dau ;
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and cost_count = 5  and user_id = "+ user_id +" ;", c )
					+
					ServiceManager.getSqlService().queryLong( "select count( id ) " +
							" from t_newactivity " +
							" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and cost_count = 22  and user_id = "+ user_id +" ;", c ) * 5;

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and user_id = "+ user_id +" ;", c );


			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +"  and user_id = "+ user_id +" ;", c );

			Map< Long, Long > getMap = new HashMap<>();

			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );

			val.daoju = getMap;

			val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000 + getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000 + getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+  getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth) +   getLong(getMap , -4 ) * 3000 + getLong(getMap , 20033 ) * (ItemManager.getInstance().get( 20033 ).worth)) / (val.xiaohao );

			return val;
		} );
	}


	public void queryChouQianCha( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_FISH_CHOUQIAN, getChouQianChaData( req, resp  , 1 ),
				ChouQianTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}

	public List< ActivityView.huodong > getChouQianData(HttpServletRequest req, HttpServletResponse resp ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );


			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.Count2 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = -2  ;", c );

			long dau = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + " ;", c );

			//展示率
			val.canyulv = val.seepeople * 1.0 / dau ;
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and cost_count = 5 ;", c )
					+
					ServiceManager.getSqlService().queryLong( "select count( id ) " +
							" from t_newactivity " +
							" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and cost_count = 22 ;", c ) * 5;

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" ;", c );


			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			Map< Long, Long > getMap = new HashMap<>();

			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			if (val.xiaohao == 0 )
			{val.xiaohao = 1;}
			val.daoju = getMap;

			val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000 + getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000 + getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+  getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth) +   getLong(getMap , -4 ) * 4500 + getLong(getMap , 20033 ) * (ItemManager.getInstance().get( 20033 ).worth)) / (val.xiaohao);

			return val;
		} );
	}

	public void queryChouQian( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_FISH_CHOUQIAN, getChouQianData( req, resp  , 1 ),
				ChouQianTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );

						else
						if (itemId >= 20 && itemId <= 120  )
							pw.write("四级铭文:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}

	public void downloadChouQian( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_FISH_CHOUQIAN,getQiFuChaData( req, resp , "'264'" , 1 ),
				ChouQianTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.xiaohao + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}


	public List< ActivityView.huodong > getYueBingChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.xiaohao2 = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 7051 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.xiaohao1 = ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
					" from t_use_props " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 7051 and use_info = 4 and item_count > 0 and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//点灯次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//正常产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//巨奖/产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			//月饼祈福中奖
			List<String> YueBingGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 7051,-2 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );


			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();
			Map< Long, Long > getMap3 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );
			YueBingGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap3 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;
			val.otherDaoju3   =  getMap3;

			return val;
		} );
	}

	public void queryYueBingChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_YUEBING, getYueBingChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 55 ),
				YueBingTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.xiaohao2 + "</td>" );
					pw.write( "<td>" + info.xiaohao1 + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju3.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}


	public List< ActivityView.huodong > getYueBingDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			val.xiaohao2 = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 7051 and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			val.xiaohao1 = ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
					" from t_use_props " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 7051 and use_info = 4 and item_count > 0 and appid in ("+ appid + ") ;", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") ;", c );

			//点灯次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") ;", c );

			//正常产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			//巨奖/月饼祈福中奖/产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1 ) and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			//巨奖/月饼祈福中奖/产出
			List<String> YueBingGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 7051,-2 ) and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();
			Map< Long, Long > getMap3 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );
			YueBingGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap3 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;
			val.otherDaoju3   =  getMap3;


			return val;
		} );
	}

	public void queryYueBingDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_YUEBING, getYueBingDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 55 ),
				YueBingTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.xiaohao2 + "</td>" );
					pw.write( "<td>" + info.xiaohao1 + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju3.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadYueBingDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_YUEBING,getYueBingDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 55 ),
				YueBingTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.xiaohao2 + "，" );
					pw.write(info.xiaohao1 + "，" );
					pw.write(info.item_count + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}

	public List< ActivityView.huodong > getPaiShenChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );
			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			val.Count2 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and cost_count = 2 and user_id = "+ user_id +";", c );
			val.Count3 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and cost_count = 5 and user_id = "+ user_id +";", c );
			val.Count4 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and cost_count = 10 and user_id = "+ user_id +";", c );


			//正常产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//免费产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1,1 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;
			val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000 + getLong(getMap , 6 ) * 100 + getLong(getMap , 302 ) * 52000+getLong(getMap , 311 ) * 2000 +getLong(getMap , 312 ) * 8000 +getLong(getMap , 544 ) * 8000+getLong(getMap , 10508 ) * 1000+getLong(getMap , 10513 ) * 1000+getLong(getMap ,10514) * 10000+ getLong(getMap ,3013) * 5000+ getLong(getMap ,4791) * 25000
					+ getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000
					+ getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap , -4 ) * 4000
					+ getLong(getMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
					+ getLong(getMap1 , -1) + getLong(getMap1 , 20015 ) * 20000 + getLong(getMap1 , 6 ) * 100 + getLong(getMap1 , 302 ) * 52000+getLong(getMap1 , 311 ) * 2000 +getLong(getMap1 , 312 ) * 8000 +getLong(getMap1 , 544 ) * 8000+getLong(getMap1 , 10508 ) * 1000+getLong(getMap1 , 10513 ) * 1000+getLong(getMap1 ,10514) * 10000+ getLong(getMap1 ,3013) * 5000+ getLong(getMap1 ,4791) * 25000
					+ getLong(getMap1 , 20016 ) * 200000 + getLong(getMap1 , 20017 ) * 1000000
					+ getLong(getMap1 , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap1 , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap1 , -4 ) * 4000
					+ getLong(getMap1 , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap1 , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap1 , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
					+ getLong(getMap2 , -1) + getLong(getMap2 , 20015 ) * 20000 + getLong(getMap2 , 6 ) * 100 + getLong(getMap2 , 302 ) * 52000+getLong(getMap2 , 311 ) * 2000 +getLong(getMap2 , 312 ) * 8000 +getLong(getMap2 , 544 ) * 8000+getLong(getMap2 , 10508 ) * 1000+getLong(getMap2 , 10513 ) * 1000+getLong(getMap2 ,10514) * 10000+ getLong(getMap2 ,3013) * 5000+ getLong(getMap2 ,4791) * 25000
					+ getLong(getMap2 , 20016 ) * 200000 + getLong(getMap2 , 20017 ) * 1000000
					+ getLong(getMap2 , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap2 , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap2 , -4 ) * 4000
					+ getLong(getMap2 , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap2 , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap2 , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)) ;
			return val;
		} );
	}

	public void queryPaiShenChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_PAISHEN, getPaiShenChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 54 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili / (info.xiaohao * 0.8) + "</td>" );
				} );
	}



	public List< ActivityView.huodong > getPaiShenDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );

			val.Count2 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and cost_count = 2 ;", c );
			val.Count3 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and cost_count = 5;", c );
			val.Count4 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and cost_count = 10;", c );

			//普通产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//免费产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1,1 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000 + getLong(getMap , 6 ) * 100 + getLong(getMap , 302 ) * 52000 + getLong(getMap , 311 ) * 2000 +getLong(getMap , 312 ) * 8000 + getLong(getMap , 544 ) * 8000 + getLong(getMap , 10508 ) * 1000 + getLong(getMap , 10513 ) * 1000 + getLong(getMap ,10514) * 10000+ getLong(getMap ,3013) * 5000+ getLong(getMap ,4791) * 25000
					+ getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000
					+ getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap , -4 ) * 4000
					+ getLong(getMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
					+ getLong(getMap1 , -1) + getLong(getMap1 , 20015 ) * 20000 + getLong(getMap1 , 6 ) * 100 + getLong(getMap1 , 302 ) * 52000+getLong(getMap1 , 311 ) * 2000 +getLong(getMap1 , 312 ) * 8000 +getLong(getMap1 , 544 ) * 8000+getLong(getMap1 , 10508 ) * 1000+getLong(getMap1 , 10513 ) * 1000+getLong(getMap1 ,10514) * 10000+ getLong(getMap1 ,3013) * 5000+ getLong(getMap1 ,4791) * 25000
					+ getLong(getMap1 , 20016 ) * 200000 + getLong(getMap1 , 20017 ) * 1000000
					+ getLong(getMap1 , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap1 , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap1 , -4 ) * 4000
					+ getLong(getMap1 , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap1 , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap1 , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
					+ getLong(getMap2 , -1) + getLong(getMap2 , 20015 ) * 20000 + getLong(getMap2 , 6 ) * 100 + getLong(getMap2 , 302 ) * 52000+getLong(getMap2 , 311 ) * 2000 +getLong(getMap2 , 312 ) * 8000 +getLong(getMap2 , 544 ) * 8000+getLong(getMap2 , 10508 ) * 1000+getLong(getMap2 , 10513 ) * 1000+getLong(getMap2 ,10514) * 10000+ getLong(getMap2 ,3013) * 5000+ getLong(getMap2 ,4791) * 25000
					+ getLong(getMap2 , 20016 ) * 200000 + getLong(getMap2 , 20017 ) * 1000000
					+ getLong(getMap2 , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap2 , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap2 , -4 ) * 4000
					+ getLong(getMap2 , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap2 , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap2 , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)) ;
			return val;
		} );
	}

	public void queryPaiShenDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_PAISHEN, getPaiShenDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 54 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + (double)info.bili / (info.xiaohao * 0.8) + "</td>" );
				} );
	}



	public List< ActivityView.huodong > getWanMiChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//正常产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//免费产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 1 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryWanMiChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_WANMI, getWanMiChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 36 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadWanMiChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_WANMI,getWanMiChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 36 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}



	public List< ActivityView.huodong > getWanMiDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );

			//普通产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//免费产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 1 ) and cost_count = 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryWanMiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_WANMI, getWanMiDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 36 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadWanMiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_WANMI,getWanMiDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 36 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}


	public List< ActivityView.huodong > getWangPaiChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//正常产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//彩金产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1,-2 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryWangPaiChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_WANGPAI, getWangPaiChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 53 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadWangPaiChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_WANGPAI,getWangPaiChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 53 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}



	public List< ActivityView.huodong > getWangPaiDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );

			//射击产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//兑换产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1 ) and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryWangPaiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_WANGPAI, getWangPaiDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 53 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadWangPaiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_WANGPAI,getGuanZiDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 53 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}


	public List< ActivityView.huodong > getGuanZiDZChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//正常产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//彩金产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryGuanZiChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_GUANZI, getGuanZiDZChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 52 ),
				GuanZiTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadGuanZiChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_GUANZI,getGuanZiDZChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 52 ),
				GuanZiTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}



	public List< ActivityView.huodong > getGuanZiDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );

			//射击产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//兑换产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1 ) and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryGuanZiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_GUANZI, getGuanZiDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 52 ),
				GuanZiTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadGuanZiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_GUANZI,getGuanZiDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 52 ),
				GuanZiTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}

	public List< ActivityView.huodong > getBaoZiDZChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//正常产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//兑换产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 7047,7048,7049 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryBaoZiChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_BAOZI, getBaoZiDZChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 51 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadBaoZiChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_BAOZI,getBaoZiDZChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 51 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}



	public List< ActivityView.huodong > getBaoZiDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );

			//射击产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//兑换产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 7047,7048,7049 ) and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryBaoZiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_BAOZI, getBaoZiDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 51 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadBaoZiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_BAOZI,getBaoZiDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 51 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}


	public List< ActivityView.huodong > getChuoDZChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//正常产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//兑换/合成/领取产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 7046,-1 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryChuoChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_CHUO, getChuoDZChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 50 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadChuoChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_CHUO,getChuoDZChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 50 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					pw.write(info.Jifen + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}



	public List< ActivityView.huodong > getChuoDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );

			//射击产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//兑换产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 7046,-1 ) and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryChuoDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_CHUO, getChuoDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 50 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadChuoDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_CHUO,getChuoDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 50 ),
				ChuoTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					pw.write(info.Jifen + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}


	public List< ActivityView.huodong > getMoPingDZChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//射击产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//兑换产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryMoPingChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_MOPING, getMoPingDZChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 48 ),
				SheJiTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadMoPingChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_MOPING,getMoPingDZChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 48 ),
				SheJiTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					pw.write(info.Jifen + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}



	public List< ActivityView.huodong > getMoPingDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );

			//射击产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//兑换产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id = 0 and cost_count = 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1 ) and cost_count = 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void queryMoPingDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_MOPING, getMoPingDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 48 ),
				SheJiTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadMoPingDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_MOPING,getMoPingDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 48 ),
				SheJiTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.fufeilv + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					pw.write(info.Jifen + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}


	public List< ActivityView.huodong > getQiFuChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id != 10537 and user_id = "+ user_id +";", c );

			//祈福次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//qifu产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id != 10537 and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

//消耗道具
			Map<Long, Long> costMap = ServiceManager.getSqlService().queryMapLongLong("select cost_id, sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'N' and activity_id ="+ actId +"   and cost_id != 0 and user_id = "+ user_id + " group by cost_id;", c);


			Map< Long, Long > getMap = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );

			val.daoju = getMap;
			if(costMap != null)
				val.otherDaoju   =  costMap;

			return val;
		} );
	}

	public void queryQiFuChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_QIFU, getQiFuChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 47 ),
				QiFuTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadQiFuChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_QIFU,getQiFuChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 47 ),
				QiFuTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.Jifen + "，" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}



	public List< ActivityView.huodong > getQiFuDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id != 0;", c );

			//抽奖次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );

			//抽奖产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id != 0 and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			//消耗道具
			Map<Long, Long> costMap = ServiceManager.getSqlService().queryMapLongLong("select cost_id, sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'N' and activity_id ="+ actId +"   and cost_id != 0 group by cost_id;", c);


			Map< Long, Long > getMap = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );

			val.daoju = getMap;
			if(costMap != null)
				val.otherDaoju   =  costMap;

			return val;
		} );
	}

	public void queryQiFuDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_QIFU, getQiFuDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 47 ),
				QiFuTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadQiFuDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_QIFU,getQiFuDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 47 ),
				QiFuTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}

	public List< ActivityView.huodong > getSheJiDZChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//射击产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537,0 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//兑换产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 1 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void querySheJiChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_SHEJI, getSheJiDZChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 46 ),
				SheJiTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadSheJiChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_SHEJI,getSheJiDZChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 46 ),
				SheJiTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					pw.write(info.Jifen + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}



	public List< ActivityView.huodong > getSheJiDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );
			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );

			//射击次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );

			//射击产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537,0 ) and cost_count != 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//兑换产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id = 0 and cost_count = 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 1 ) and cost_count = 0  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;

			return val;
		} );
	}

	public void querySheJiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_SHEJI, getSheJiDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 46 ),
				SheJiTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadSheJiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_SHEJI,getSheJiDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 46 ),
				SheJiTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write( formatRatio(info.fufeilv )+ "," );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					pw.write(info.Jifen + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}



	public List< ActivityView.huodong > NewZhuanPanChaDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//转盘次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//转盘产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 )  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//奖池产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -2 )  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and get_info not like '-2%' and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;
			val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000 + getLong(getMap , 6 ) * 100 + getLong(getMap , 302 ) * 50000+getLong(getMap , 311 ) * 2000 +getLong(getMap , 312 ) * 8000 +getLong(getMap , 544 ) * 8000+getLong(getMap , 10508 ) * 1000+getLong(getMap , 10513 ) * 1000+getLong(getMap ,10514) * 10000
					+ getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000
					+ getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap , -4 ) * 4000
					+ getLong(getMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
					+ getLong(getMap1 , -1) + getLong(getMap1 , 20015 ) * 20000 + getLong(getMap1 , 6 ) * 100 + getLong(getMap1 , 302 ) * 50000+getLong(getMap1 , 311 ) * 2000 +getLong(getMap1 , 312 ) * 8000 +getLong(getMap1 , 544 ) * 8000+getLong(getMap1 , 10508 ) * 1000+getLong(getMap1 , 10513 ) * 1000+getLong(getMap1 ,10514) * 10000
					+ getLong(getMap1 , 20016 ) * 200000 + getLong(getMap1 , 20017 ) * 1000000
					+ getLong(getMap1 , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap1 , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap1 , -4 ) * 4000
					+ getLong(getMap1 , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap1 , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap1 , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
					+ getLong(getMap2 , -1) + getLong(getMap2 , 20015 ) * 20000 + getLong(getMap2 , 6 ) * 100 + getLong(getMap2 , 302 ) * 50000+getLong(getMap2 , 311 ) * 2000 +getLong(getMap2 , 312 ) * 8000 +getLong(getMap2 , 544 ) * 8000+getLong(getMap2 , 10508 ) * 1000+getLong(getMap2 , 10513 ) * 1000+getLong(getMap2 ,10514) * 10000
					+ getLong(getMap2 , 20016 ) * 200000 + getLong(getMap2 , 20017 ) * 1000000
					+ getLong(getMap2 , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap2 , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap2 , -4 ) * 4000
					+ getLong(getMap2 , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap2 , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap2 , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)) ;
			return val;
		} );
	}

	public void queryNewZhuanPanChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_TAONIU, NewZhuanPanChaDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 45 ),
				NewZhuanPanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili / (info.xiaohao * 0.8) + "</td>" );
				} );
	}

	public void downloadNewZhuanPanChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_TAONIU,NewZhuanPanChaDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 45 ),
				TaoNiuTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					pw.write(info.Jifen + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}



	public List< ActivityView.huodong > NewZhuanPanDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );
			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );

			//转盘次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );

			//转盘产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 )  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//奖池产出
			List<String> JiangChiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -2 )  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and get_info not like '-2%' and activity_id ="+ actId +" and appid in ("+ appid + ");", c );



			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();

			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			JiangChiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;
			val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000 + getLong(getMap , 6 ) * 100 + getLong(getMap , 302 ) * 50000+getLong(getMap , 311 ) * 2000 +getLong(getMap , 312 ) * 8000 +getLong(getMap , 544 ) * 8000+getLong(getMap , 10508 ) * 1000+getLong(getMap , 10513 ) * 1000 + getLong(getMap ,10514) * 10000 + getLong(getMap ,3013 ) * 5000 + getLong(getMap ,4791 ) * 25000
					+ getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000
					+ getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap , -4 ) * 4000
					+ getLong(getMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
					+ getLong(getMap1 , -1) + getLong(getMap1 , 20015 ) * 20000 + getLong(getMap1 , 6 ) * 100 + getLong(getMap1 , 302 ) * 50000+getLong(getMap1 , 311 ) * 2000 +getLong(getMap1 , 312 ) * 8000 +getLong(getMap1 , 544 ) * 8000+getLong(getMap1 , 10508 ) * 1000+getLong(getMap1 , 10513 ) * 1000+getLong(getMap1 ,10514) * 10000 + getLong(getMap ,3013 ) * 5000 + getLong(getMap ,4791 ) * 25000
					+ getLong(getMap1 , 20016 ) * 200000 + getLong(getMap1 , 20017 ) * 1000000
					+ getLong(getMap1 , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap1 , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap1 , -4 ) * 4000
					+ getLong(getMap1 , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap1 , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap1 , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
					+ getLong(getMap2 , -1) + getLong(getMap2 , 20015 ) * 20000 + getLong(getMap2 , 6 ) * 100 + getLong(getMap2 , 302 ) * 50000+getLong(getMap2 , 311 ) * 2000 +getLong(getMap2 , 312 ) * 8000 +getLong(getMap2 , 544 ) * 8000+getLong(getMap2 , 10508 ) * 1000+getLong(getMap2 , 10513 ) * 1000+getLong(getMap2 ,10514) * 10000 + getLong(getMap ,3013 ) * 5000 + getLong(getMap ,4791 ) * 25000
					+ getLong(getMap2 , 20016 ) * 200000 + getLong(getMap2 , 20017 ) * 1000000
					+ getLong(getMap2 , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap2 , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap2 , -4 ) * 4000
					+ getLong(getMap2 , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap2 , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap2 , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)) ;

			return val;
		} );
	}

	public void queryNewZhuanPanDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_TAONIU, NewZhuanPanDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 45 ),
				NewZhuanPanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili / (info.xiaohao * 0.8) + "</td>" );
				} );
	}

	public void downloadNewZhuanPanDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_TAONIU,getTaoNiuDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 45 ),
				TaoNiuTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					pw.write(info.Jifen + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
				} );
	}

	public List< ActivityView.huodong > getTaoNiuDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );

			//套牛次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );

			//任务和套牛
			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in (10537,0 )  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			//兑换
			List<String> duihuanGet = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id not in  (10537,0) and cost_count = 0 and appid in ("+ appid + ");", c );

			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();

			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			duihuanGet.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			//牛票产出
			val.Jifen = getLong(getMap ,635);

			return val;
		} );
	}

	public void queryTaoNiuDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
//			"时间","套牛人数 ",	"套牛次数", "胡莱币消耗", "胡莱币购买","牛票产出数量", "兑换产出",	"任务产出");
		queryList( req, resp,
				CommandList.GET_ACTIVITY_TAONIU, getTaoNiuDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 42 ),
				TaoNiuTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.Jifen + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0 || itemId !=  635 )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0 || itemId !=  635 )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadTaoNiuDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_TAONIU,getTaoNiuDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 42 ),
				TaoNiuTableHead, ( info, pw ) -> {
					pw.write(info.begin + "，" );
					pw.write(info.People + "，" );
					pw.write(info.Count1 + "，" );
					pw.write(info.xiaohao + "，" );
					pw.write(info.item_count + "，" );
					pw.write(info.Jifen + "，" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0 && itemId !=  635 )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0 && itemId !=  635 )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
				} );
	}



	private List< List< ActivityView.Rank > > getRankData(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		int limit = Integer.parseInt( req.getParameter( "limit" ) );

		return getData( req, resp, ( beg, end, c ) -> {

			Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( get_info ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and activity_id = 44 and cost_id = 0 and cost_count = 0 and get_info not like '%;' group by user_id;", c );

			Map< Long, ActivityView.Rank > sum = new HashMap<>();


			winMap.forEach( ( k, v ) -> {
				if( sum.get( k ) == null ) sum.put( k, new ActivityView.Rank() );
				sum.get( k ).gold = v;

			} );

			List< ActivityView.Rank > winList = new ArrayList<>();

			sum.forEach( ( k, v ) -> {
				v.userId = k;
				v.begin = TimeUtil.ymdFormat().format( beg );
				winList.add( v );

			} );

			return  Arrays.asList(winList);
		} );
	}


	public void queryRank( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryMultiList( req, resp,
				CommandList.GET_ACTIVITY_LUCKY_CHOUJIANG, getRankData( req, resp ),
				rankTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.userId + "</td>" );
					pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
					pw.write( "<td>" + info.gold + "</td>" );
				}, getUserNameScript( "td", "tdUserName_" ) );
	}

	public void downloadRank( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadMultiList( req, resp,
				CommandList.GET_ACTIVITY_LUCKY_CHOUJIANG, getRankData( req, resp),
				rankTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.userId + "," );
					pw.write( UserSdk.getUserName( info.userId ) + "," );
					pw.write( info.gold + "," );
				} );
	}
	private List< ActivityView.LuckyChou > getLuckyChouChaData(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.LuckyChou val = new ActivityView.LuckyChou();

			val.begin = TimeUtil.ymdFormat().format( beg );

			val.count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 44  and user_id = " + user_id +";", c );
			val.costCount = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 44 and cost_id = 10537 and user_id = " + user_id +";", c );

			val.hongbaoCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id =44 and cost_id = -1 and user_id = "+ user_id +";", c );
			val.qiangHongbaoCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id =44 and cost_id = 0 and cost_count = 0 and get_info not like '%;' and user_id = "+ user_id +";", c );
			val.BuyItem = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and user_id = " + user_id + ";", c );

			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in (10537,0 ) and cost_count != 0 and activity_id =44 and user_id = " +user_id+ ";", c );

//			List<String> hongbaoGold = ServiceManager.getSqlService().queryListString( "select get_info " +
//					" from t_newactivity " +
//					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id =44 and cost_id = 0 and cost_count = 0 and user_id = "+user_id+";", c );

			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();

			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
//			hongbaoGold.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );

			val.daoju = getMap;
			val.hongbaoGold   = ServiceManager.getSqlService().queryLong( "select sum( get_info ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 44 and cost_id = 0 and cost_count = 0 and user_id = " + user_id +";", c );

			return val;
		} );

	}

	public void queryLuckyChouCha( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,CommandList.GET_ACTIVITY_LUCKY_CHOUJIANG, getLuckyChouChaData( req, resp ),
				LuckyChouChaTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.count + "</td>" );
					pw.write( "<td>" + info.hongbaoCount + "</td>" );
					pw.write( "<td>" + info.qiangHongbaoCount + "</td>" );
					pw.write( "<td>" + info.hongbaoGold + "</td>" );
					pw.write( "<td>" + info.BuyItem + "</td>" );
					pw.write( "<td>" + info.costCount + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
				});
	}

	public void downloadLuckyChouCha( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList(req, resp,
				CommandList.GET_ACTIVITY_LUCKY_CHOUJIANG, getLuckyChouChaData( req, resp ),
				LuckyChouChaTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.count + "</td>" );
					pw.write( "<td>" + info.hongbaoCount + "</td>" );
					pw.write( "<td>" + info.qiangHongbaoCount + "</td>" );
					pw.write( "<td>" + info.hongbaoGold + "</td>" );
					pw.write( "<td>" + info.BuyItem + "</td>" );
					pw.write( "<td>" + info.costCount + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
				});
	}

	public List< ActivityView.huodong > getLuckyChouDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );
			val.Paypeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );
			val.hongbaoPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 0 and cost_count = 0 and get_info not like '%;' and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );
			val.hongbaoCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 0 and cost_count = 0 and get_info not like '%;' and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );
			//红包触发次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -1 and cost_count = 0 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );


			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in (10537,0 ) and get_info like '%;' and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

//			List<String> hongbaoGold = ServiceManager.getSqlService().queryListString( "select get_info " +
//					" from t_newactivity " +
//					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 0 and cost_count = 0 and appid in ("+ appid + ");", c );

			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();

			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
//			hongbaoGold.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );

			val.daoju = getMap;
			val.Gold   =  ServiceManager.getSqlService().queryLong( "select sum( get_info ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 0 and cost_count = 0 and  get_info not like '%;' and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			return val;
		} );
	}

	public void queryLuckyChouDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_LUCKY_CHOUJIANG, getLuckyChouDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 44 ),
				LuckyChouTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.hongbaoPeople + "</td>" );
					pw.write( "<td>" + info.hongbaoCount + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" + info.Paypeople + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
				} );
	}

	public void downloadLuckyChouDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_LUCKY_CHOUJIANG,getLuckyChouDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 44 ),
				LuckyChouTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.People + "," );
					pw.write( info.hongbaoPeople + "," );
					pw.write( info.hongbaoCount + "," );
					pw.write( info.Gold + "," );
					pw.write( info.Paypeople + "," );
					pw.write( info.item_count + "," );
					pw.write( info.xiaohao + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
				} );
	}

	private List< ActivityView.Hongbao > getHongBaoData(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.Hongbao val = new ActivityView.Hongbao();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			long inning_id = ServiceManager.getSqlService().queryLong( "select min(inning_id) from t_grab_red " +
					" where " + beg + " <= timestamp and timestamp < " + end + " ;",c );

			long inning_id1 = inning_id;
			long inning_id2 = inning_id + 1 ;
			long inning_id3 = inning_id + 2;
			long inning_id4 = inning_id + 3;

			Map<Long, Long> CountMap = ServiceManager.getSqlService().queryMapLongLong("select inning_id, count(inning_id) " +
					" from t_grab_red " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  group by inning_id;", c);
			val.count1 = getLong(CountMap, inning_id1 );
			val.count2 = getLong(CountMap, inning_id2 );
			val.count3 = getLong(CountMap, inning_id3 );
			val.count4 = getLong(CountMap, inning_id4 );

			Map<Long, Long> GoldMap = ServiceManager.getSqlService().queryMapLongLong("select inning_id, sum(gold) " +
					" from t_grab_red " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  group by inning_id;", c);
			val.gold1 = getLong(GoldMap, inning_id1);
			val.gold2 = getLong(GoldMap, inning_id2);
			val.gold3 = getLong(GoldMap, inning_id3);
			val.gold4 = getLong(GoldMap, inning_id4);

			Map<Long, Long> PayPeopleMap = ServiceManager.getSqlService().queryMapLongLong("select inning_id, count(distinct user_id) " +
					" from t_grab_red " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and user_info = 'Y' group by inning_id;", c);
			val.payPeople1 = getLong(PayPeopleMap, inning_id1);
			val.payPeople2 = getLong(PayPeopleMap, inning_id2);
			val.payPeople3 = getLong(PayPeopleMap, inning_id3);
			val.payPeople4 = getLong(PayPeopleMap, inning_id4);

			Map<Long, Long> PayGoldMap = ServiceManager.getSqlService().queryMapLongLong("select inning_id, sum(gold) " +
					" from t_grab_red " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and user_info = 'Y' group by inning_id;", c);
			val.payGold1 = getLong(PayGoldMap, inning_id1);
			val.payGold2 = getLong(PayGoldMap, inning_id2);
			val.payGold3 = getLong(PayGoldMap, inning_id3);
			val.payGold4 = getLong(PayGoldMap, inning_id4);

			Map<Long, Long> freePeopleMap = ServiceManager.getSqlService().queryMapLongLong("select inning_id, count(distinct user_id) " +
					" from t_grab_red " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and user_info = 'N' group by inning_id;", c);
			val.freePeople1 = getLong(freePeopleMap, inning_id1);
			val.freePeople2 = getLong(freePeopleMap, inning_id2);
			val.freePeople3 = getLong(freePeopleMap, inning_id3);
			val.freePeople4 = getLong(freePeopleMap, inning_id4);

			Map<Long, Long> freeGoldMap = ServiceManager.getSqlService().queryMapLongLong("select inning_id, sum(gold) " +
					" from t_grab_red " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and user_info = 'N' group by inning_id;", c);
			val.freeGold1 = getLong(freeGoldMap, inning_id1);
			val.freeGold2 = getLong(freeGoldMap, inning_id2);
			val.freeGold3 = getLong(freeGoldMap, inning_id3);
			val.freeGold4 = getLong(freeGoldMap, inning_id4);

			val.xiangxi1 = ServiceManager.getSqlService().queryMapStringLong("select user_id, sum(gold) " +
					" from t_grab_red " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and inning_id = " + inning_id1 + " group by user_id;", c);
			val.xiangxi2 = ServiceManager.getSqlService().queryMapStringLong("select user_id, sum(gold) " +
					" from t_grab_red " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and inning_id = " + inning_id2 + " group by user_id;", c);
			val.xiangxi3 = ServiceManager.getSqlService().queryMapStringLong("select user_id, sum(gold) " +
					" from t_grab_red " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and inning_id = " + inning_id3 + " group by user_id;", c);
			val.xiangxi4 = ServiceManager.getSqlService().queryMapStringLong("select user_id, sum(gold) " +
					" from t_grab_red " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and inning_id = " + inning_id4 + " group by user_id;", c);



			return val;
		} );
	}

	public void queryHongBao( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_HONGBAO, getHongBaoData( req, resp ),
				HongBaoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + "1" + "</td>" );
					pw.write( "<td>" + info.count1 + "</td>" );
					pw.write( "<td>" + info.gold1 + "</td>" );
					pw.write( "<td>" + info.payPeople1 + "</td>" );
					pw.write( "<td>" + info.payGold1 + "</td>" );
					pw.write( "<td>" + info.freePeople1 + "</td>" );
					pw.write( "<td>" + info.freeGold1 + "</td>" );
					pw.write( "<td>" );
					info.xiangxi1.forEach( ( k, V ) -> {
						pw.write( "ID:" + k + "-gold:" + V +"<br>");
					} );
					pw.write( "</td>" );
					pw.write( "</tr>" );
					pw.write( "<tr>" );
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + "2" + "</td>" );
					pw.write( "<td>" + info.count2 + "</td>" );
					pw.write( "<td>" + info.gold2 + "</td>" );
					pw.write( "<td>" + info.payPeople2 + "</td>" );
					pw.write( "<td>" + info.payGold2 + "</td>" );
					pw.write( "<td>" + info.freePeople2 + "</td>" );
					pw.write( "<td>" + info.freeGold2 + "</td>" );
					pw.write( "<td>" );
					info.xiangxi2.forEach( ( k, V ) -> {
						pw.write( "ID:" + k + "-gold:" + V +"<br>");
					} );
					pw.write( "</td>" );
					pw.write( "</tr>" );
					pw.write( "<tr>" );
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + "3" + "</td>" );
					pw.write( "<td>" + info.count3 + "</td>" );
					pw.write( "<td>" + info.gold3 + "</td>" );
					pw.write( "<td>" + info.payPeople3 + "</td>" );
					pw.write( "<td>" + info.payGold3 + "</td>" );
					pw.write( "<td>" + info.freePeople3 + "</td>" );
					pw.write( "<td>" + info.freeGold3 + "</td>" );
					pw.write( "<td>" );
					info.xiangxi3.forEach( ( k, V ) -> {
						pw.write( "ID:" + k + "-gold:" + V +"<br>");
					} );
					pw.write( "</td>" );
					pw.write( "</tr>" );
					pw.write( "<tr>" );
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + "4" + "</td>" );
					pw.write( "<td>" + info.count4 + "</td>" );
					pw.write( "<td>" + info.gold4 + "</td>" );
					pw.write( "<td>" + info.payPeople4 + "</td>" );
					pw.write( "<td>" + info.payGold4 + "</td>" );
					pw.write( "<td>" + info.freePeople4 + "</td>" );
					pw.write( "<td>" + info.freeGold4 + "</td>" );
					pw.write( "<td>" );
					info.xiangxi4.forEach( ( k, V ) -> {
						pw.write( "ID:" + k + "-gold:" + V +"<br>");
					} );
					pw.write( "</td>" );
				},null );
	}

	public void downloadHongBao( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_HONGBAO, getHongBaoData( req, resp ),
				HongBaoTableHead, ( info, pw ) -> {
					pw.write(info.begin + "," );
					pw.write("1" + "," );
					pw.write(info.count1 + "," );
					pw.write(info.gold1 + "," );
					pw.write(info.payPeople1 + "," );
					pw.write(info.payGold1 + "," );
					pw.write(info.freePeople1 + "," );
					pw.write(info.freeGold1 + "," );
					pw.write( "<td>" );
					info.xiangxi1.forEach( ( k, V ) -> {
						pw.write( "ID:" + k + "-gold:" + V +"<br>");
					} );
					pw.write( "</td>" );
					pw.write( "</tr>" );
					pw.write( "<tr>" );
					pw.write(info.begin + "," );
					pw.write("2" + "," );
					pw.write(info.count2 + "," );
					pw.write(info.gold2 + "," );
					pw.write(info.payPeople2 + "," );
					pw.write(info.payGold2 + "," );
					pw.write(info.freePeople2 + "," );
					pw.write(info.freeGold2 + "," );
					pw.write( "<td>" );
					info.xiangxi2.forEach( ( k, V ) -> {
						pw.write( "ID:" + k + "-gold:" + V +"<br>");
					} );
					pw.write( "</td>" );
					pw.write( "</tr>" );
					pw.write( "<tr>" );
					pw.write(info.begin + "," );
					pw.write("3" + "," );
					pw.write(info.count3 + "," );
					pw.write(info.gold3 + "," );
					pw.write(info.payPeople3 + "," );
					pw.write(info.payGold3 + "," );
					pw.write(info.freePeople3 + "," );
					pw.write(info.freeGold3 + "," );
					pw.write( "<td>" );
					info.xiangxi3.forEach( ( k, V ) -> {
						pw.write( "ID:" + k + "-gold:" + V +"<br>");
					} );
					pw.write( "</td>" );
					pw.write( "</tr>" );
					pw.write( "<tr>" );
					pw.write(info.begin + "," );
					pw.write("4" + "," );
					pw.write(info.count4 + "," );
					pw.write(info.gold4 + "," );
					pw.write(info.payPeople4 + "," );
					pw.write(info.payGold4 + "," );
					pw.write(info.freePeople4 + "," );
					pw.write(info.freeGold4 + "," );
					pw.write( "<td>" );
					info.xiangxi4.forEach( ( k, V ) -> {
						pw.write( "ID:" + k + "-gold:" + V +"<br>");
					} );
					pw.write( "</td>" );
				} );
	}


	public void queryPintuDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_PINTU, getGoldEggDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 7 ),
				GoldEggTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Paypeople + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadPintuDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_PINTU,getGoldEggDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 7 ),
				GoldEggTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.Paypeople + "," );
					pw.write( info.item_count + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( "" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}

	public List< ActivityView.huodong > getGoldEggDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );
			val.Paypeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );
			val.duihuanPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 0 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );


			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id = 10537  and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			List<String> othergetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id != 10537 and appid in ("+ appid + ");", c );

			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();

			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			othergetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );

			val.otherDaoju = getMap1;
			val.daoju = getMap;
			val.Jifen = getLong(getMap , -5);
			val.Gold   =  ServiceManager.getSqlService().queryLong( "select  sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = -1;", c );
			val.xiyinli = val.seepeople * 1.0 / val.seetimes;
			val.canyulv = val.People * 1.0 / val.seepeople;

			return val;
		} );
	}

	public void queryGoleEggDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_GOLDEGG, getGoldEggDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 37 ),
				GoldEggTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Paypeople + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadGoleEggDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_GOLDEGG,getGoldEggDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 37 ),
				GoldEggTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.Paypeople + "," );
					pw.write( info.item_count + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( "" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}


	private List<ActivityView.Download> getDownloadData(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.Download val = new ActivityView.Download();
			val.begin = TimeUtil.ymdFormat().format( beg );


//			val.people = ServiceManager.getSqlService().queryMapLongLong( "select other_info, count( id ) " +
//					" from t_download " +
//					" where " + beg + " <= timestamp and timestamp < " + end +
//					" and is_android = 'android' and  action = 'open'"+
//					" group by other_info;", c );

			val.download = ServiceManager.getSqlService().queryMapStringLong( "select other_info , count( id ) " +
					" from t_download " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and is_android = 'android' and  action = 'download'"+
					" group by other_info;", c );

			val.wechat = ServiceManager.getSqlService().queryMapStringLong( "select other_info, count( id ) " +
					" from t_download " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and is_android = 'android' and  action = 'open'  and is_wechat = 'Y'"+
					" group by other_info;", c );


			val.ios =ServiceManager.getSqlService().queryLong( "select count(id) " +
					" from t_download " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and is_android =  'ios';", c );

			val.ioswechat = ServiceManager.getSqlService().queryLong( "select count(id) " +
					" from t_download " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and is_wechat = 'N' and action = 'open';", c );

			val.iosdownload = ServiceManager.getSqlService().queryLong( "select count(id) " +
					" from t_download " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and is_wechat = 'N' and is_android ='ios' and action = 'download';", c );



			return val;
		} );
	}
	public void queryDownload( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		List<ActivityView.Download> data = getDownloadData( req, resp );
		Set< String > allQuDao = new TreeSet<>();
		data.forEach( d -> {
			allQuDao.addAll( d.download.keySet() );
			allQuDao.addAll( d.wechat.keySet() );
		});

		List< String > DownloadTableHead = new ArrayList<>();
		DownloadTableHead.add( "日期");
		DownloadTableHead.add( "ios人数");
		DownloadTableHead.add( "ios微信查看");
		DownloadTableHead.add( "ios下载");
		allQuDao.forEach( qudao -> {
			DownloadTableHead.add( qudao + "人数" );
			DownloadTableHead.add( qudao + "微信查看人数" );
			DownloadTableHead.add( qudao + "下载量" );
		} );

		queryList( req, resp,
				CommandList.GET_DOWNLOAD, data,
				DownloadTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.ios + "</td>" );
					pw.write( "<td>" + info.ioswechat + "</td>" );
					pw.write( "<td>" + info.iosdownload + "</td>" );
					allQuDao.forEach( qudao -> {
						pw.write( "<td>" + (getLong( info.wechat,  qudao  ) + getLong( info.download, qudao ) )  + "</td>" );
						pw.write( "<td>" + getLong( info.wechat, qudao  ) + "</td>" );
						pw.write( "<td>" + getLong( info.download,  qudao  ) + "</td>" );
					} );
				} );
	}

	public void downloadDownload( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		List<ActivityView.Download> data = getDownloadData(  req, resp );
		Set< String > allQuDao = new TreeSet<>();
		data.forEach( d -> {
			allQuDao.addAll( d.download.keySet() );
			allQuDao.addAll( d.wechat.keySet() );
		});

		List< String > DownloadTableHead = new ArrayList<>();
		DownloadTableHead.add( "日期");
		DownloadTableHead.add( "ios人数");
		DownloadTableHead.add( "ios微信查看");
		DownloadTableHead.add( "ios下载");
		allQuDao.forEach( qudao -> {
			DownloadTableHead.add( qudao + "人数" );
			DownloadTableHead.add( qudao + "微信查看人数" );
			DownloadTableHead.add( qudao + "下载量" );
		} );

		downloadList( req, resp,
				CommandList.GET_DOWNLOAD, data,
				DownloadTableHead, ( info, pw ) -> {
					pw.write(   info.begin + "," );
					pw.write(  info.ios + "," );
					pw.write(  info.ioswechat + "," );
					pw.write(  info.iosdownload + "," );
					allQuDao.forEach( qudao -> {
						pw.write( (getLong( info.wechat, qudao  ) + getLong( info.download, qudao ))  + "," );
						pw.write(  getLong( info.wechat,  qudao )  + "," );
						pw.write(  getLong( info.download, qudao ) + "," );
					} );
				} );
	}

	private List< ActivityView.huodong > getNianshouData(HttpServletRequest req, HttpServletResponse resp ,String appid )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id =39 and appid in ("+ appid + ");", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id =39 and appid in ("+ appid + ");", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id =39 and cost_id != 0;", c );

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id =39 and cost_id != 0;", c );

			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537;", c );

			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 39 and cost_id != 0;", c);

			val.People1 = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id = 39 and cost_id = 0 ;", c );

			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id = 39 and cost_id = 0 ;", c );

			Map< Long, Long > getMap = new HashMap<>();

			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			val.daoju = getMap;

			val.xiyinli = val.seepeople * 1.0 / val.seetimes;
			val.canyulv = val.People * 1.0 / val.seepeople;



			return val;
		} );
	}
	public void queryNianshouDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_NIANSHOU, getNianshouData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'"),
				NianshouTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People1 + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
				} );
	}

	public void downloadNianshouDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_NIANSHOU,getNianshouData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'"),
				NianshouTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.item_count + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
					pw.write( info.People1 + "," );
					pw.write( info.Count1 + "," );
				} );
	}

	private List< ActivityView.FanPaiDianshu > getFanPaiData(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		return getData( req, resp, ( beg, end, c ) -> {
			List<ActivityView.FanPaiDianshu> a = new LinkedList<>();

			Map<Long, Long> sendMap = ServiceManager.getSqlService().queryMapLongLong("select user_id,sum( get_info ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and activity_id = 5 and cost_id != 0 and get_info != 'null' group by user_id ;", c);

			sendMap.forEach((k, v) -> {
				ActivityView.FanPaiDianshu val = new ActivityView.FanPaiDianshu();
				val.userId = k;
				val.count = v ;
				a.add(val);
			});

			return a;
		} );
	}
	public List< ActivityView.FanPai > getFanPaiXiangxiData(HttpServletRequest req, HttpServletResponse resp ,String appid )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.FanPai val = new ActivityView.FanPai();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id != 0 and activity_id =5 and appid in ("+ appid + ");", c );

			val.count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id != 0 and activity_id =5 and appid in ("+ appid + ");", c );

			val.fufeicount = val.count - val.people;

			val.xiaofeizong = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id =5 and cost_id != 0 and appid in ("+ appid + ");", c )
					- val.people;

			val.huodezong = ServiceManager.getSqlService().queryLong( "select sum( get_info ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id =5 and cost_id != 0 and appid in ("+ appid + ");", c );


			val.gold1count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id =5 and get_info like 'gold1%' and appid in ("+ appid + ");", c );

			val.gold2count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id =5 and get_info like 'gold2%' and appid in ("+ appid + ");", c );

			val.gold3count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id =5 and get_info like 'gold4%' and appid in ("+ appid + ");", c );

			val.gold4count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id =5 and get_info like 'gold5%' and appid in ("+ appid + ");", c );

			val.xiaoniubi = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id =5 and get_info like '10508%' and appid in ("+ appid + ");", c );

			val.niubi = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id =5 and get_info like '10507%' and appid in ("+ appid + ");", c );


			return val;
		} );
	}

	public void queryFanpaiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		List<ActivityView.FanPai> data  = getFanPaiXiangxiData( req, resp, "'100632434','1104737759','1104881083','1104947175','1104830871'" );

		queryMultiList( req, resp,
				CommandList.GET_ACTIVITY_FANPAI, data,
				FanpaiTableHead, ( info, pw ) -> {
					pw.write("<td>" + info.begin + "</td>");
					pw.write("<td>" + info.people + "</td>");
//					pw.write("<td>" + (info.count - info.people) + "</td>");
					pw.write("<td>" + (info.fufeicount) + "</td>");
					pw.write("<td>" + info.xiaofeizong + "</td>");
					pw.write("<td>" + info.huodezong + "</td>");
					pw.write("<td>" + info.gold1count + "</td>");
					pw.write("<td>" + info.gold2count + "</td>");
					pw.write("<td>" + info.gold3count + "</td>");
					pw.write("<td>" + info.gold4count + "</td>");
					pw.write("<td>" + info.xiaoniubi + "</td>");
					pw.write("<td>" + info.niubi + "</td>");
					pw.write( "<td>" );
					pw.write( "gold:" + (info.gold1count * 6666 + info.gold2count * 17000 + info.gold3count * 38888 + info.gold4count * 80000)  + "<br>");
					pw.write( "小牛币:" +  info.xiaoniubi * 30 +"<br>");
					pw.write( "牛币:" +  info.niubi * 17 );
					pw.write( "</td>" );
				},getFanPaiData( req, resp ),
				DianshuJichuTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.userId+ "</td>" );
					pw.write( "<td>" + info.count + "</td>" );} ,null );
	}

	public void downloadFanpaiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		List<ActivityView.FanPai> data  = getFanPaiXiangxiData( req, resp, "'100632434','1104737759','1104881083','1104947175','1104830871'" );

		downloadMultiList( req, resp,
				CommandList.GET_ACTIVITY_FANPAI, data,
				FanpaiTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.people + "," );
					pw.write( (info.people  - info.fufeicount) + "," );
					pw.write( (info.count - info.people) + "," );
					pw.write( info.xiaofeizong + "," );
					pw.write( info.huodezong + "," );
					pw.write( info.gold1count + "," );
					pw.write( info.gold2count + "," );
					pw.write( info.gold3count + "," );
					pw.write( info.gold4count + "," );
					pw.write( info.xiaoniubi + "," );
					pw.write( info.niubi + "," );
					pw.write( " " );
					pw.write( "gold:" + (info.gold1count * 6666 + info.gold2count * 17000 + info.gold3count * 38888 + info.gold4count * 80000)  + "<br>");
					pw.write( "小牛币:" +  info.xiaoniubi * 30 +"<br>");
					pw.write( "牛币:" +  info.niubi * 17 );
					pw.write( "," );
				},getFanPaiData( req, resp ),
				DianshuJichuTableHead, ( info, pw ) -> {
					pw.write( info.userId + "," );
					pw.write( info.count + "," );
				});
	}


	public List< ActivityView.huodong > gethuodongData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id != 0 and activity_id ="+ actId +" and appid in ("+ appid + ");", c );
			if(actId != 9)
			{
				val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
						" from t_pay " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );
				val.Paypeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
						" from t_pay " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ");", c );
			}
			if(actId == 9)
			{
				val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
						" from t_use_props " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10542 and use_info = 6 and item_source = 148 and appid in ("+ appid + ");", c );
				val.Paypeople =ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
						" from t_use_props " +
						" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10542 and use_info = 6 and item_source = 148 and appid in ("+ appid + ");", c );
			}
			val.duihuanPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 0 and activity_id ="+ actId +"  and appid in ("+ appid + ");", c );


			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			Map< Long, Long > getMap = new HashMap<>();

			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );

			val.Jifen = getLong(getMap , -5);
			val.daoju = getMap;
			val.Gold   = getLong( getMap, -1) ;
			val.xiyinli = val.seepeople * 1.0 / val.seetimes;
			val.canyulv = val.People * 1.0 / val.seepeople;

			return val;
		} );
	}

	private List< ActivityView.huodong > getZhuanpanData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ");", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_activity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id ="+ actId +" ;", c );

			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 546;", c );

			val.daoju = ServiceManager.getSqlService().queryMapLongLong( "select got_id, coalesce( sum( got_count ), 0 ) " +
					" from t_activity " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and activity_id = 40 and is_ccgames = 'Y' " +
					" group by got_id;", c );

			val.xiyinli = val.seepeople * 1.0 / val.seetimes;
			val.canyulv = val.People * 1.0 / val.seepeople;



			return val;
		} );
	}
	public void queryZhuanpan( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_DAZHUANPAN, getZhuanpanData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 40 ),
				ZhuanPanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Paypeople + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadZhuanpan( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_DAZHUANPAN,getZhuanpanData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 40 ),
				ZhuanPanTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.Paypeople + "," );
					pw.write( info.item_count + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}
	public void queryTieRenDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_TIEREN, gethuodongData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 43 ),
				ZhuanPanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Paypeople + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadTieRenDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_DENGLONG,gethuodongData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 43 ),
				DengLongTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.Paypeople + "," );
					pw.write( info.item_count + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}

	public void queryDengLongDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_DENGLONG, gethuodongData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 9 ),
				DengLongTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Paypeople + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadDengLongDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_DENGLONG,gethuodongData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 7 ),
				DengLongTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.Paypeople + "," );
					pw.write( info.item_count + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}

	public void queryShengdanDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_SHENGDAN, gethuodongData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 7 ),
				ZhuanPanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Paypeople + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadShengdanDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_SHENGDAN,gethuodongData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 7 ),
				ZhuanPanTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.Paypeople + "," );
					pw.write( info.item_count + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}
	public void queryShengdanMJ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_SHENGDAN, gethuodongData( req, resp ,  "'1104754063'" , 7 ),
				ZhuanPanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Paypeople + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadShengdanMJ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_SHENGDAN,gethuodongData( req, resp , "'1104754063'" , 7 ),
				ZhuanPanTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.Paypeople + "," );
					pw.write( info.item_count + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}

	public void queryYuanDanDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_YUANDAN, gethuodongData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 8 ),
				YandanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Paypeople + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" + info.Jifen + "</td>" );
					pw.write( "<td>" + info.duihuanPeople + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadYuanDanDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_YUANDAN,gethuodongData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 8 ),
				YandanTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.Paypeople + "," );
					pw.write( info.item_count + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( info.Jifen + "," );
					pw.write( info.duihuanPeople + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}
	public void queryYuanDanMJ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_YUANDAN, gethuodongData( req, resp ,  "'1104754063'" , 8 ),
				YandanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Paypeople + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" + info.Jifen + "</td>" );
					pw.write( "<td>" + info.duihuanPeople + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadYuanDanMJ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_YUANDAN,gethuodongData( req, resp , "'1104754063'" , 8 ),
				YandanTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.Paypeople + "," );
					pw.write( info.item_count + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( info.Jifen + "," );
					pw.write( info.duihuanPeople + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}

	public void queryJuBao( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_JUBAOPEN, gethuodongData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'", 4 ),
				JuBaoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadJuBao( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_JUBAOPEN, gethuodongData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 4 ),
				JuBaoTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}
	public void queryJuBaoMJ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_JUBAOPEN, gethuodongData( req, resp , "'1104754063'", 4 ),
				JuBaoTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadJubaoMJ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_JUBAOPEN, gethuodongData( req, resp , MAJIANG_APPID , 4 ),
				JuBaoTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}

	public void queryKuangChanMJ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_WAKUANGCHAN, gethuodongData( req, resp , MAJIANG_APPID , 3 ),
				KuangChanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId > 0  )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
						}
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadKuangChanMJ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_WAKUANGCHAN, gethuodongData( req, resp , MAJIANG_APPID , 3 ),
				KuangChanTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if(itemId  != -1 ||  itemId != 0 || itemId.equals("-1")   ||  itemId.equals("0") )
						{
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "," );
						}
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}

	public void queryKuangChan( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_WAKUANGCHAN, gethuodongData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 9 ),
				KuangChanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write( ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio( info.xiyinli ) + "</td>" );
					pw.write( "<td>" + formatRatio( info.canyulv ) + "</td>" );
				} );
	}

	public void downloadKuangChan( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_WAKUANGCHAN, gethuodongData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 9 ),
				KuangChanTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.People + "," );
					pw.write( info.xiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						pw.write( ItemManager.getInstance().getItemName( Integer.getInteger( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "," );
					pw.write( formatRatio( info.xiyinli ) + "," );
					pw.write( formatRatio( info.canyulv ) + "," );
				} );
	}

	private List< ActivityView.DanqiDanRen > getDanQiDanRenData(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		long userId= Long.parseLong( req.getParameter( "userId" ).trim() );
		long actId = Long.parseLong( req.getParameter( "actId" ).trim() );
		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.DanqiDanRen val = new ActivityView.DanqiDanRen();
			List< String > nums = ServiceManager.getSqlService().queryListString( "select buy_num " +
					" from t_duobao_buy " +
					" where  user_id = "+ userId +" and  act_id = " + actId + ";", c );
			nums.forEach( num -> val.num = val.num + num + "," );
			val.kaijiangjieguo = ServiceManager.getSqlService().queryLong( "select win_num " +
					" from t_duobao_open " +
					" where  win_user_id = "+ userId +" and  act_id = " + actId + ";", c );
			return val;
		} );
	}

	public void queryDanQiDanRen( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		List< ActivityView.DanqiDanRen > data1  = getDanQiDanRenData( req, resp );
		queryList( req, resp,CommandList.GET_ACTIVITY_YIYUANDUOBAO_DANQIDANREN, data1,
				DanQiDanRenTableHead, ( info, pw ) -> {
					pw.write( "<td>" );
					for (String s : info.num.split(",")) {
						pw.write( s + "<br>" );
					}
					pw.write( "</td>" );
					pw.write( "<td>" + info.kaijiangjieguo + "</td>" );
				}, null );
	}

	public void downloadDanQiDanRen( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {


		List< ActivityView.DanqiDanRen > data1  = getDanQiDanRenData( req, resp );

		downloadList(req, resp,
				CommandList.GET_ACTIVITY_YIYUANDUOBAO_DANQIDANREN, data1,
				DanQiDanRenTableHead, (info, pw) -> {
					String[] numArr = info.num.split(",");
					int rows = Math.max( numArr.length, 1 );
					for( int i = 0; i < rows; ++i ) {
						pw.write( numArr[i] + "," );
						pw.write( i == 0 ? info.kaijiangjieguo + "," : "," );
						pw.write( "\n" );
					}
				});
	}


	private List< ActivityView.Danqi1 > getDanqi1Data(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		long Actid = Long.parseLong( req.getParameter( "actId" ).trim() );
		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.Danqi1 val = new ActivityView.Danqi1();
			val.begin = TimeUtil.ymdhmsFormat().format( ServiceManager.getSqlService().queryLong( "select timestamp " +
					" from t_duobao_open " +
					" where act_id = " + Actid + ";", c ));

			val.yigoumai = ServiceManager.getSqlService().queryLong( "select coalesce( sum( other_info + 0 ), 0 ) " +
					" from t_duobao_buy  where act_id = " + Actid + ";", c );

			val.suoxu = ServiceManager.getSqlService().queryLong( "select distinct need_count " +
					" from t_duobao_buy where act_id = " + Actid + ";", c );

			long a = ServiceManager.getSqlService().queryLong( "select win_num " +
					" from t_duobao_open where act_id = " + Actid + ";", c );

			if (a == 0)
			{
				val.kaijiangjieguo = "未开奖";
			}else {
				val.kaijiangjieguo = Long.toString(a);
			}

			return val;
		} );
	}

	private List< ActivityView.Danqi2 > getDanQi2Data(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		long Actid = Long.parseLong( req.getParameter( "actId" ).trim() );
		return getData( req, resp, ( beg, end, c ) -> {
			List< ActivityView.Danqi2 > list = new LinkedList<>();
			Map< Long, Long > payMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( other_info + 0 )" +
					" from t_duobao_buy " +
					" where act_id = " + Actid + " group by user_id;", c );
			payMap.forEach( ( k, v ) -> {
				ActivityView.Danqi2 val = new ActivityView.Danqi2();
				val.user_id = k;
				val.num = v;
				list.add( val );
			} );
//			Collections.reverse(list);
			return list;
		} );
	}

	public void queryDanQi( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		List< ActivityView.Danqi1 > data1  = getDanqi1Data( req, resp );
		List< ActivityView.Danqi2 > data2 = getDanQi2Data( req, resp );
		queryMultiList( req, resp,CommandList.GET_ACTIVITY_YIYUANDUOBAO_DANQI, data1,
				DanQiTableHead1, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.yigoumai + "</td>" );
					pw.write( "<td>" + info.suoxu + "</td>" );
					pw.write( "<td>" + info.kaijiangjieguo + "</td>" );
				}, data2 ,
				DanQiTableHead2, ( info, pw ) -> {
					pw.write( "<td>" + info.user_id + "</td>" );
					pw.write( "<td name='tdUserName_" + info.user_id + "'></td>" );
					pw.write( "<td>" + info.num + "</td>" );
				}, getUserNameScript( "td", "tdUserName_" ) );
	}

	public void downloadDanQi( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {


		List< ActivityView.Danqi1 > data1  = getDanqi1Data( req, resp );
		List< ActivityView.Danqi2 > data2 = getDanQi2Data( req, resp );

		downloadMultiList(req, resp,
				CommandList.GET_ACTIVITY_YIYUANDUOBAO_DANQI, data1,
				DanQiTableHead1, (info, pw) -> {
					pw.write(info.begin + ",");
					pw.write(info.yigoumai + ",");
					pw.write(info.suoxu + ",");
					pw.write(info.kaijiangjieguo + ",");
				}, data2, DanQiTableHead2, (info, pw) -> {
					pw.write(info.user_id + ",");
					pw.write( UserSdk.getUserName( info.user_id ) + "," );
					pw.write(info.num + ",");

				});
	}

	/**
	 *  pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
	 * */
	private List< ActivityView.DuoBaoJichu1 > getDuoBaoJichuData(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

//		req.setCharacterEncoding("UTF-8");

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.DuoBaoJichu1 val = new ActivityView.DuoBaoJichu1();
			val.begin = TimeUtil.ymdFormat().format( beg );

			val.canjiapeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_duobao_buy " +
					" where " + beg + " <= timestamp and timestamp < " + end + ";", c );

			val.baotuout = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_duobao_buy " +
					" where " + beg + " <= timestamp and timestamp < " + end + ";", c );
			if(val.baotuout != 0)
			{
				val.renjin =val.baotuout / val.canjiapeople;
			}else{
				val.renjin = 0;
			}


			val.openzongbaotuOut = ServiceManager.getSqlService().queryLong( "select sum( need_count ) " +
					" from t_duobao_open " +
					" where " + beg + " <= timestamp and timestamp < " + end + ";" , c ) * 10;

			return val;
		} );
	}
	private List< ActivityView.DuoBaoJichu2 > getjichuData(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		return getData( req, resp, ( beg, end, c ) -> {
			List< ActivityView.DuoBaoJichu2 > list = new LinkedList<>();
			Map< Long, String > openMap = ServiceManager.getSqlService().queryMapLongString(
					"select act_id, max( name_info ) " +
							" from t_duobao_open " +
							" where " + beg + " <= timestamp and timestamp < " + end +
							" group by act_id;", c );



			openMap.forEach( ( k, v ) -> {
				ActivityView.DuoBaoJichu2 val = new ActivityView.DuoBaoJichu2();
				val.userid = ServiceManager.getSqlService().queryLong( "select win_user_id " +
						" from t_duobao_open " +
						" where " + beg + " <= timestamp and timestamp < " + end + " and act_id = "+ k +";", c );
				Long tmp = ServiceManager.getSqlService().queryLong( "select timestamp " +
						" from t_duobao_open " +
						" where act_id= "+ k +";" , c );
				val.begin = TimeUtil.ymdhmsFormat().format(  tmp  );
				val.openid = k;
				val.openname = v;
				list.add( val );
			} );
			return list;
		} );
	}

	public void queryDuoBaoJichu( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		List< ActivityView.DuoBaoJichu1 > data1  = getDuoBaoJichuData( req, resp );
		List< ActivityView.DuoBaoJichu2 > data2 = getjichuData( req, resp );
		queryMultiList( req, resp,CommandList.GET_ACTIVITY_YIYUANDUOBAO, data1,
				oneDuoBaoJichuTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.canjiapeople + "</td>" );
					pw.write( "<td>" + info.baotuout + "</td>" );
					pw.write( "<td>" + info.renjin + "</td>" );
					pw.write( "<td>" + info.openzongbaotuOut + "</td>" );
				}, data2 ,
				OpenXiangxiTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.openid + "</td>" );
					pw.write( "<td>" + info.openname + "</td>" );
					pw.write( "<td>" + info.userid + "</td>" );
					pw.write( "<td name='tdUserName_" + info.userid + "'></td>" );
				}, getUserNameScript( "td", "tdUserName_" ) );
	}

	public void downloadDuoBaoJichu( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {


		List<ActivityView.DuoBaoJichu1> data1 = getDuoBaoJichuData(req, resp);
		List<ActivityView.DuoBaoJichu2> data2 = getjichuData(req, resp);

		downloadMultiList(req, resp,
				CommandList.GET_ACTIVITY_YIYUANDUOBAO, data1,
				oneDuoBaoJichuTableHead, (info, pw) -> {
					pw.write(info.begin + ",");
					pw.write(info.canjiapeople + ",");
					pw.write(info.baotuout + ",");
					pw.write(info.renjin + ",");
					pw.write(info.openzongbaotuOut + ",");
				}, data2, OpenXiangxiTableHead, (info, pw) -> {
					pw.write(info.openid + ",");
					pw.write(info.openname + ",");
					pw.write(info.userid + ",");
					pw.write( getUserName( info.userid ) + "," );
				});
	}

	private List< ActivityView.GoldEgg > getGoldEggData(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.GoldEgg val = new ActivityView.GoldEgg();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id =1;", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id =1;", c );

			val.zadanpeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id = 1;", c );

			val.jinchuixiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id =1;" , c );

			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id =1;", c );

			Map< Long, Long > getMap = new HashMap<>();

			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			val.Gold             = getLong( getMap, -1 );
			val.qingtongjiezhi   = getLong( getMap, ItemIdUtils.ITEM_ID_10501);
			val.yiyuanhuafei     = getLong( getMap, ItemIdUtils.ITEM_ID_10100 );
			val.eryuanhuafei     = getLong( getMap, ItemIdUtils.ITEM_ID_10101 );
			val.wuyuanhuafei     = getLong( getMap, ItemIdUtils.ITEM_ID_10102  );
			val.cangbaotu        = getLong( getMap, ItemIdUtils.ITEM_ID_10301 );

			return val;
		} );
	}

	public void queryGoldEgg( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryList( req, resp,
				CommandList.GET_ACTIVITY_GOLDEGG, getGoldEggData( req, resp ),
				GoldEggTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + info.zadanpeople + "</td>" );
					pw.write( "<td>" + info.jinchuixiaohao + "</td>" );
					pw.write( "<td>" + info.Gold + "</td>" );
					pw.write( "<td>" );
					pw.write( "一元话费：" + info.yiyuanhuafei + "<br>" );
					pw.write( "二元话费：" + info.eryuanhuafei + "<br>" );
					pw.write( "五元话费：" + info.wuyuanhuafei + "<br>" );
					pw.write( "青铜戒指：" + info.qingtongjiezhi + "<br>" );
					pw.write( "藏宝图：" + info.cangbaotu + "<br>" );
					pw.write( "</td>" );
					pw.write( "<td>" + formatRatio(info.seepeople * 1.0 / info.seetimes) + "</td>" );
					pw.write( "<td>" + formatRatio(info.zadanpeople * 1.0 / info.seepeople) + "</td>" );
				} );
	}

	public void downloadGoldEgg( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		downloadList( req, resp,
				CommandList.GET_ACTIVITY_GOLDEGG, getGoldEggData( req, resp ),
				GoldEggTableHead, ( info, pw ) -> {
					pw.write( info.begin + "," );
					pw.write( info.seepeople + "," );
					pw.write( info.seetimes + "," );
					pw.write( info.zadanpeople + "," );
					pw.write( info.jinchuixiaohao + "," );
					pw.write( info.Gold + "," );
					pw.write( "一元话费：" + info.yiyuanhuafei + "<br>" );
					pw.write( "二元话费：" + info.eryuanhuafei + "<br>" );
					pw.write( "五元话费：" + info.wuyuanhuafei + "<br>" );
					pw.write( "青铜戒指：" + info.qingtongjiezhi + "<br>" );
					pw.write( "藏宝图：" + info.cangbaotu + "<br>" );
					pw.write( formatRatio(info.seepeople * 1.0 / info.seetimes) + "," );
					pw.write( formatRatio(info.zadanpeople * 1.0 / info.seepeople) + "," );
				} );
	}

	public List< ActivityView.huodong > getGanEnChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//点灯次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count != 0  and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//正常产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and get_info like '-1%' and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			//正常产出
			List<String> ZhuanPanGetinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -2 )  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			//幸运产出
			List<String> xingyunGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and get_info like '-2%' and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			List<String> xingyunGetinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -3 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//彩池中奖
			List<String> caichiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );


			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();
			Map< Long, Long > getMap3 = new HashMap<>();


			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			xingyunGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			caichiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap3 ) );

			Map< Long, Long > zhengchgangdaoju = new HashMap<>();
			Map< Long, Long > xingyundaoju = new HashMap<>();
			ZhuanPanGetinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), zhengchgangdaoju ) );
			xingyunGetinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), xingyundaoju ) );
			getMap.putAll(zhengchgangdaoju);
			getMap2.putAll(xingyundaoju);

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;
			val.otherDaoju3   =  getMap3;

			return val;
		} );
	}

	public void queryGanEnChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_GANEN, getGanEnChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 57 ),
				GanEnTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -2 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju3.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}


	public List< ActivityView.huodong > getGanEnDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") ;", c );

			//点灯次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count != 0 and activity_id ="+ actId +"  and appid in ("+ appid + ") ;", c );

			//普通产出
			List<String> PuTongGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537,-2 ) and get_info like '-1%' and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			List<String> PuTongGetinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -2 ) and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			//幸运产出
			List<String> xingyunGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537,-3 ) and get_info like '-2%' and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			List<String> xingyunGetinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -3 ) and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			//奖池产出
			List<String> jiangchiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1 ) and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();
			Map< Long, Long > getMap3 = new HashMap<>();

			PuTongGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			xingyunGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			jiangchiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap3 ) );

			Map< Long, Long > zhengchgangdaoju = new HashMap<>();
			Map< Long, Long > xingyundaoju = new HashMap<>();
			PuTongGetinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), zhengchgangdaoju ) );
			xingyunGetinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), xingyundaoju ) );
			getMap.putAll(zhengchgangdaoju);
			getMap1.putAll(xingyundaoju);

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;
			val.otherDaoju3   =  getMap3;

			return val;
		} );
	}

	public void queryGanEnDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_GANEN, getGanEnDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 57 ),
				GanEnTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -2 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju3.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public List< ActivityView.huodong > getWaZiChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//点灯次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count != 0  and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//正常产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ,-2 ) and get_info like '-1%' and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			//正常产出
			List<String> ZhuanPanGetinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -2 ,-4 )  and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			//幸运产出
			List<String> xingyunGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and get_info like '-2%' and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			List<String> xingyunGetinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -3 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//彩池中奖
			List<String> caichiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 7057 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );


			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();
			Map< Long, Long > getMap3 = new HashMap<>();


			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			xingyunGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			caichiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap3 ) );

			Map< Long, Long > zhengchgangdaoju = new HashMap<>();
			Map< Long, Long > xingyundaoju = new HashMap<>();
			ZhuanPanGetinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), zhengchgangdaoju ) );
			xingyunGetinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), xingyundaoju ) );
			getMap.putAll(zhengchgangdaoju);
			getMap2.putAll(xingyundaoju);

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;
			val.otherDaoju3   =  getMap3;

			return val;
		} );
	}

	public void queryWaZiChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_WAZI, getWaZiChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 58 ),
				GanEnTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -2 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju3.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}


	public List< ActivityView.huodong > getWaZiDZData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") ;", c );

			//点灯次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count != 0 and activity_id ="+ actId +"  and appid in ("+ appid + ") ;", c );

			//普通产出
			List<String> PuTongGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537,-2 ) and get_info like '-1%' and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			List<String> PuTongGetinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -2 ,-4 ) and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			//幸运产出
			List<String> xingyunGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537,-3 ) and get_info like '-2%' and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			List<String> xingyunGetinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -3 ) and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			//袜子产出
			List<String> jiangchiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 7057 ) and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();
			Map< Long, Long > getMap3 = new HashMap<>();

			PuTongGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			xingyunGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			jiangchiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap3 ) );

			Map< Long, Long > zhengchgangdaoju = new HashMap<>();
			Map< Long, Long > xingyundaoju = new HashMap<>();
			PuTongGetinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), zhengchgangdaoju ) );
			xingyunGetinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), xingyundaoju ) );
			getMap.putAll(zhengchgangdaoju);
			getMap1.putAll(xingyundaoju);

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;
			val.otherDaoju3   =  getMap3;

			return val;
		} );
	}

	public void queryWaZiDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_WAZI, getWaZiDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 58 ),
				GanEnTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -2 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju3.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public List< ActivityView.huodong > getHaiDiBaoZangData(HttpServletRequest req, HttpServletResponse resp ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );


			long dau = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + " ;", c );

			//展示率
			val.canyulv = val.seepeople * 1.0 / dau ;
			val.fufeilv = val.People * 1.0 / val.seepeople ;

			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" ;", c );
			val.Count2 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -2 and activity_id ="+ actId +" ;", c );

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" ;", c );
			//正产产出
			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 10537 ;", c );
			Map< Long, Long > getMap = new HashMap<>();
			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			val.daoju = getMap;
			//产出胡莱币个数
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum(coalesce( getValue2(bouns,\"10537\"), 0 )) " +
					" from t_fish_mb_grants " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and type = 9 ;", c );
			//刷新任务消耗金币
			val.Money1 = ServiceManager.getSqlService().queryLong( "select sum(cost_count) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -1 and activity_id ="+ actId +";", c ) / 10000;
			//翻牌产出
			List<String> fanpaigetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id in ( -2 );", c );
			Map< Long, Long > kuanghuangetMap = new HashMap<>();
			fanpaigetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), kuanghuangetMap ) );
			val.otherDaoju2 = kuanghuangetMap;
			if (val.xiaohao == 0 )
			{val.xiaohao = 1;}
			val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000
					+ getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000
					+ getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap , -4 ) * 4000
					+ getLong(getMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
					+ getLong(kuanghuangetMap , -1) + getLong(kuanghuangetMap , 20015 ) * 20000
					+ getLong(kuanghuangetMap , 20016 ) * 200000
					+ getLong(kuanghuangetMap , 20017 ) * 1000000
					+ getLong(kuanghuangetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(kuanghuangetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(kuanghuangetMap , -4 ) * 4000
					+ getLong(kuanghuangetMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(kuanghuangetMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(kuanghuangetMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
			) / ( val.xiaohao );

			return val;
		} );
	}

	public void queryHaiDiDuoBao( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_HAIDIDUOBAO, getHaiDiBaoZangData( req, resp  , 16 ),
				HaiDiBaoZangTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.Money1  + "万</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId <= 20 && itemId >= 0  )
							pw.write("炮台:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:次数:" + info.Count2 + "数量:" + itemCount / 10000 + "万<br>" );
						else
						if (itemId <= 20 && itemId >= 0  )
							pw.write("炮台:" + itemCount  + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}

	public List< ActivityView.huodong > getHaiDiBaoZangChaData(HttpServletRequest req, HttpServletResponse resp ,long actId )
			throws Exception {
		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );
		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );


			long dau = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + " ;", c );

			//展示率
			val.canyulv = val.seepeople * 1.0 / dau ;
			val.fufeilv = val.People * 1.0 / val.seepeople ;

			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and user_id = "+ user_id +";", c );
			val.Count2 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -2 and activity_id ="+ actId +" and user_id = "+ user_id +";", c );

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and user_id = "+ user_id +";", c );

			//正产产出
			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 10537 and user_id = "+ user_id +";", c );
			Map< Long, Long > getMap = new HashMap<>();
			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			val.daoju = getMap;
			//产出胡莱币个数
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum(coalesce( getValue2(bouns,\"10537\"), 0 )) " +
					" from t_fish_mb_grants " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and type = 9 and user_id = "+ user_id +";", c );
			//刷新任务消耗金币
			val.Money1 = ServiceManager.getSqlService().queryLong( "select sum(cost_count) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -1 and activity_id ="+ actId +" and user_id = "+ user_id +";", c );


			//翻牌产出
			List<String> fanpaigetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id in ( -2 ) and user_id = "+ user_id +";", c );
			Map< Long, Long > kuanghuangetMap = new HashMap<>();
			fanpaigetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), kuanghuangetMap ) );
			val.otherDaoju2 = kuanghuangetMap;
			if (val.xiaohao == 0 )
			{val.xiaohao = 1;}
			val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000
					+ getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000
					+ getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap , -4 ) * 4000
					+ getLong(getMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
					+ getLong(kuanghuangetMap , -1) + getLong(kuanghuangetMap , 20015 ) * 20000
					+ getLong(kuanghuangetMap , 20016 ) * 200000
					+ getLong(kuanghuangetMap , 20017 ) * 1000000
					+ getLong(kuanghuangetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(kuanghuangetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(kuanghuangetMap , -4 ) * 4000
					+ getLong(kuanghuangetMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(kuanghuangetMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(kuanghuangetMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
			) / ( val.xiaohao );

			return val;
		} );
	}

	public void queryHaiDiDuoBaoCha( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_HAIDIDUOBAO, getHaiDiBaoZangChaData( req, resp  , 16 ),
				HaiDiBaoZangTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" + info.Money1  + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:次数:" + info.Count2 + "数量:" + itemCount / 10000 + "万<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}


	public List< ActivityView.huodong > getQingCaiShenData(HttpServletRequest req, HttpServletResponse resp ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );


			long dau = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + " ;", c );

			//展示率
			val.canyulv = val.seepeople * 1.0 / dau ;
			val.fufeilv = val.People * 1.0 / val.seepeople ;

			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id ="+ actId +"  and cost_id = -1 and cost_count = 4;", c );
			val.Count2 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id ="+ actId +"  and cost_id = -1 and cost_count = 9;", c );
			val.Count3 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id ="+ actId +"  and cost_id = -1 and cost_count = 16;", c );

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -13 and activity_id ="+ actId +" ;", c );
			//积分产出
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum(coalesce( getValue2(get_info,'-13'), 0 )) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -1 and activity_id ="+ actId +" ;", c );

			//掉落碎片 20053-20068
			val.otherDaoju = ServiceManager.getSqlService().queryMapLongLong("select item_id,sum(item_count) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id >= 20053 and item_id <= 20068 and item_count > 0 group by item_id;", c);

			//正产兑换产出
			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = -1 ;", c );
			Map< Long, Long > getMap = new HashMap<>();
			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			val.daoju = getMap;

			//积分兑换产出
			List<String> duihuangetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id in ( -13 );", c );
			Map< Long, Long > duihuangetMap = new HashMap<>();
			duihuangetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), duihuangetMap ) );
			val.otherDaoju2 = duihuangetMap;

			val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000
					+ getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000
					+ getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap , -4 ) * 4000
					+ getLong(getMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
					+ getLong(duihuangetMap , -1) + getLong(duihuangetMap , 20015 ) * 20000
					+ getLong(duihuangetMap , 20016 ) * 200000
					+ getLong(duihuangetMap , 20017 ) * 1000000
					+ getLong(duihuangetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(duihuangetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(duihuangetMap , -4 ) * 4000
					+ getLong(duihuangetMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(duihuangetMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(duihuangetMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
			);

			return val;
		} );
	}

	public void queryQingCaiShen( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_QINGCAISHEN, getQingCaiShenData( req, resp  , 18 ),
				QingCaiShenTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {	pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );	} );
					pw.write( "</td>" );
					pw.write( "<td>4个碎片：" + info.Count1 + "<br>" );
					pw.write( "9个碎片：" + info.Count2 + "<br>" );
					pw.write( "16个碎片：" + info.Count3 + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:次数:" + info.Count2 + "数量:" + itemCount / 10000 + "万<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
				} );
	}

	public List< ActivityView.huodong > getQingCaiShenChaData(HttpServletRequest req, HttpServletResponse resp ,long actId )
			throws Exception {
		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );
		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );


			long dau = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + " ;", c );

			//展示率
			val.canyulv = val.seepeople * 1.0 / dau ;
			val.fufeilv = val.People * 1.0 / val.seepeople ;

			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id ="+ actId +"  and cost_id = -1 and cost_count = 4 and user_id = "+ user_id +";", c );
			val.Count2 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id ="+ actId +"  and cost_id = -1 and cost_count = 9 and user_id = "+ user_id +";", c );
			val.Count3 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and activity_id ="+ actId +"  and cost_id = -1 and cost_count = 16 and user_id = "+ user_id +";", c );

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -13 and activity_id ="+ actId +"  and user_id = "+ user_id +";", c );
			//积分产出
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum(coalesce( getValue2(get_info,\"-13\"), 0 )) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = -1 and activity_id ="+ actId +" and user_id = "+ user_id +";", c );

			//掉落碎片 20053-20068
			val.otherDaoju = ServiceManager.getSqlService().queryMapLongLong("select item_id,sum(item_count) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id >= 20053 and item_id <= 20068 and item_count > 0 and user_id = "+ user_id +" group by item_id;", c);

			//正产兑换产出
			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = -1 and user_id = "+ user_id +";", c );
			Map< Long, Long > getMap = new HashMap<>();
			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			val.daoju = getMap;

			//积分兑换产出
			List<String> duihuangetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id in ( -13 ) and user_id = "+ user_id +";", c );
			Map< Long, Long > duihuangetMap = new HashMap<>();
			duihuangetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), duihuangetMap ) );
			val.otherDaoju2 = duihuangetMap;

			val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000
					+ getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000
					+ getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap , -4 ) * 4000
					+ getLong(getMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
					+ getLong(duihuangetMap , -1) + getLong(duihuangetMap , 20015 ) * 20000
					+ getLong(duihuangetMap , 20016 ) * 200000
					+ getLong(duihuangetMap , 20017 ) * 1000000
					+ getLong(duihuangetMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(duihuangetMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(duihuangetMap , -4 ) * 4000
					+ getLong(duihuangetMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(duihuangetMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(duihuangetMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)
			);

			return val;
		} );
	}

	public void queryQingCaiShenCha( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_QINGCAISHEN, getQingCaiShenChaData( req, resp  , 18 ),
				QingCaiShenTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {	pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );	} );
					pw.write( "</td>" );
					pw.write( "<td>4个碎片：" + info.Count1 + "<br>" );
					pw.write( "9个碎片：" + info.Count2 + "<br>" );
					pw.write( "16个碎片：" + info.Count3 + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:次数:" + info.Count2 + "数量:" + itemCount / 10000 + "万<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public List< ActivityView.huodong > getDZJinDanChaData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537 and user_id = "+ user_id +";", c );
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//点灯次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count != 0  and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			//点灯次数
			val.Count2 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count != 0 and get_info like '%-2%' and activity_id ="+ actId +"  and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//正常产出
			List<String> ZhuanPanGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537  ) and get_info like '-1%' and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			List<String> PuTongGetinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -2 ,-4 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			//幸运产出
			List<String> xingyunGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and get_info like '-2%' and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );
			List<String> xingyunGetinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -3 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );

			//彩池中奖
			List<String> caichiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1 ) and activity_id ="+ actId +" and appid in ("+ appid + ") and user_id = "+ user_id +";", c );


			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();
			Map< Long, Long > getMap3 = new HashMap<>();


			ZhuanPanGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			xingyunGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			caichiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap3 ) );

			Map< Long, Long > zhengchgangdaoju = new HashMap<>();
			Map< Long, Long > xingyundaoju = new HashMap<>();
			xingyunGetinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), xingyundaoju ) );
			PuTongGetinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), zhengchgangdaoju ) );
			getMap.putAll(zhengchgangdaoju);
			getMap2.putAll(xingyundaoju);

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;
			val.otherDaoju3   =  getMap3;
			val.bili = getLong(getMap ,-1) + getLong(getMap ,-2) + getLong(getMap ,10514) * 10000 + getLong(getMap ,3013 ) * 5000 + getLong(getMap ,4791 ) * 25000  + getLong(getMap ,302) * 52000 + getLong(getMap,303 ) * 106000 + getLong(getMap,315) * 220000 +
					   getLong(getMap1,-1) + getLong(getMap1,-2) + getLong(getMap1,10514) * 10000 + getLong(getMap ,3013 ) * 5000 + getLong(getMap ,4791 ) * 25000  + getLong(getMap1,302) * 52000 + getLong(getMap1,303 ) * 106000 + getLong(getMap1,315) * 220000 +
					   getLong(getMap2,-1) + getLong(getMap2,-2) + getLong(getMap2,10514) * 10000 + getLong(getMap ,3013 ) * 5000 + getLong(getMap ,4791 ) * 25000  + getLong(getMap2,302) * 52000 + getLong(getMap2,303 ) * 106000 + getLong(getMap2,315) * 220000 +
					   getLong(getMap3,-1) + getLong(getMap3,-2) + getLong(getMap3,10514) * 10000 + getLong(getMap ,3013 ) * 5000 + getLong(getMap ,4791 ) * 25000  + getLong(getMap3,302) * 52000 + getLong(getMap3,303 ) * 106000 + getLong(getMap3,315) * 220000 ;
			return val;
		} );
	}

	public void queryDZJinDanChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_ZAJINDAN, getDZJinDanChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 59 ),
				ZaJinDanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Count2 + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -2 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju3.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili /((double)info.xiaohao * 0.8) + "</td>" );
				} );
	}


	public List< ActivityView.huodong > getDZJinDanData(HttpServletRequest req, HttpServletResponse resp ,String appid ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and appid in ("+ appid + ") and cost_id = 10537;", c );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and appid in ("+ appid + ") ;", c );
			val.fufeilv = val.People * 1.0 / val.seepeople ;
			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			val.item_count = ServiceManager.getSqlService().queryLong( "select sum( item_buy_count ) " +
					" from t_pay " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and item_id = 10537 and appid in ("+ appid + ") ;", c );

			//点灯次数
			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count != 0 and activity_id ="+ actId +"  and appid in ("+ appid + ") ;", c );
			val.Count2 = ServiceManager.getSqlService().queryLong( "select count( user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and cost_count != 0 and get_info like '%-2%' and activity_id ="+ actId +"  and appid in ("+ appid + ") ;", c );

			//普通产出
			List<String> PuTongGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and get_info like '-1%' and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			List<String> PuTongGetinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -2 ,-4 ) and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			//幸运产出
			List<String> xingyunGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 10537 ) and get_info like '-2%' and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );
			List<String> xingyunGetinfo2 = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -3 ) and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			//排行产出
			List<String> PaiHangGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( 0 ) and cost_count = 0 and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			//袜子产出
			List<String> jiangchiGetinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -1 ) and activity_id ="+ actId +" and appid in ("+ appid + ") ;", c );

			Map< Long, Long > getMap = new HashMap<>();
			Map< Long, Long > getMap1 = new HashMap<>();
			Map< Long, Long > getMap2 = new HashMap<>();
			Map< Long, Long > getMap3 = new HashMap<>();

			PuTongGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			xingyunGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap1 ) );
			jiangchiGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap2 ) );
			PaiHangGetinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap3 ) );

			Map< Long, Long > zhengchgangdaoju = new HashMap<>();
			Map< Long, Long > xingyundaoju = new HashMap<>();
			PuTongGetinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), zhengchgangdaoju ) );
			xingyunGetinfo2.forEach( info -> mergeMap( splitMap( info, ";", ":" ), xingyundaoju ) );
			getMap.putAll(zhengchgangdaoju);
			getMap1.putAll(xingyundaoju);

			val.daoju = getMap;
			val.otherDaoju   =  getMap1;
			val.otherDaoju2   =  getMap2;
			val.otherDaoju3   =  getMap3;

			val.bili = getLong(getMap,-1) + getLong(getMap,-2) + getLong(getMap,10514) * 10000 + getLong(getMap ,3013 ) * 5000  + getLong(getMap,4791) * 25000 + getLong(getMap,302) * 52000 + getLong(getMap,303 ) * 106000 + getLong(getMap,315) * 220000 +
					getLong(getMap1,-1) + getLong(getMap1,-2) + getLong(getMap1,10514) * 10000 + getLong(getMap ,3013 ) * 5000  + getLong(getMap1,4791) * 25000 + getLong(getMap1,302) * 52000 + getLong(getMap1,303 ) * 106000 + getLong(getMap1,315) * 220000 +
					getLong(getMap2,-1) + getLong(getMap2,-2) + getLong(getMap2,10514) * 10000 + getLong(getMap ,3013 ) * 5000 + getLong(getMap2,4791) * 25000 + getLong(getMap2,302) * 52000 + getLong(getMap2,303 ) * 106000 + getLong(getMap2,315) * 220000 +
					getLong(getMap3,-1) + getLong(getMap3,-2) + getLong(getMap3,10514) * 10000 + getLong(getMap ,3013 ) * 5000  + getLong(getMap3,4791) * 25000 + getLong(getMap3,302) * 52000 + getLong(getMap3,303 ) * 106000 + getLong(getMap3,315) * 220000 ;
			return val;
		} );
	}

	public void queryDZJinDan( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_ZAJINDAN, getDZJinDanData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 59 ),
				ZaJinDanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.Count2 + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -2 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju3.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili /((double)info.xiaohao * 0.8) + "</td>" );
				} );
	}

	public void queryYYLDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_YYL  , getPaiShenDZData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 60 ),
				YYLTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>2:  " + info.Count2 + "<br>" );
					pw.write(  "5:  " +info.Count3 + "<br>" );
					pw.write(  "10:  " +info.Count4 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + (double)info.bili / (info.xiaohao * 0.8) + "</td>" );
				} );
	}
	public void queryYYLChaDZ( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_YYL  , getPaiShenChaData( req, resp , "'100632434','1104737759','1104881083','1104947175','1104830871'" , 60 ),
				YYLTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>2:  " + info.Count2 + "<br>" );
					pw.write(  "5:  " +info.Count3 + "<br>" );
					pw.write(  "10:  " +info.Count4 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv )+ "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" + info.item_count + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );

					} );
					pw.write( "</td>" );
					pw.write( "<td>" );
					info.otherDaoju2.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount + "<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + (double)info.bili / (info.xiaohao * 0.8) + "</td>" );
				} );
	}


	public List< ActivityView.GuessPoker > getGuessPokerData(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.GuessPoker val = new ActivityView.GuessPoker();
			val.begin = TimeUtil.ymdFormat().format( beg );

			long buyaBetGold = ServiceManager.getSqlService().queryLong( "select sum( last_count ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and guess_duicuo = 2 and guess_index = 1 and guess_index = 1 and item_id = -1 ;", c );

			long buyaBetGoldDan = ServiceManager.getSqlService().queryLong( "select sum( last_count ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and guess_duicuo = 2 and guess_index = 1   and guess_index = 1 and item_id = 20017 ;", c );
			long buyaBetYinDan = ServiceManager.getSqlService().queryLong( "select sum( last_count ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and guess_duicuo = 2 and guess_index = 1  and guess_index = 1 and item_id = 20016 ;", c );
			long buyaCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and guess_duicuo = 2 and guess_index = 1 ;", c );
			val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and guess_type != -1 ;", c );
			val.count = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and guess_duicuo in ( 0 , 2 )  ;", c ) - buyaCount;
			val.betGold = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
					" from t_fish_paiment " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and other_info = 14 ;", c ) - buyaBetGold ;
			val.betGoldDan = ServiceManager.getSqlService().queryLong( "select -sum( item_count ) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and item_id = 20017 and item_count < 0 and other_info = 'guess'  ;", c ) - buyaBetGoldDan;
			val.betYinDan = ServiceManager.getSqlService().queryLong( "select -sum( item_count ) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and item_id = 20016 and item_count < 0 and other_info = 'guess'  ;", c ) - buyaBetYinDan ;
			val.winGold = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
					" from t_fish_get " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and other_info = 14 ;", c ) - buyaBetGold ;
			val.winGoldDan = ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and item_id = 20017 and item_count > 0  and other_info = 'guess' ;", c ) - buyaBetGoldDan ;
			val.winYinDan = ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and item_id = 20016 and item_count > 0  and other_info = 'guess' ;", c ) - buyaBetYinDan ;
			val.wangpai = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and item_id = 20017 and card = 66 and guess_duicuo != 2 ;", c );
			val.meilunrenshu = ServiceManager.getSqlService().queryMapLongLong("select guess_index,count( id ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and guess_duicuo in ( 0 , 2 ) and item_count != last_count  group by guess_index;", c );

			return val;
		} );
	}

	public List< ActivityView.GuessPoker > getGuessPokerChaData(HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );
		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.GuessPoker val = new ActivityView.GuessPoker();
			val.begin = TimeUtil.ymdFormat().format( beg );

			long buyaBetGold = ServiceManager.getSqlService().queryLong( "select sum( last_count ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and guess_duicuo = 2 and guess_index = 1 and item_id = -1  and user_id = "+ user_id +";", c );

			long buyaBetGoldDan = ServiceManager.getSqlService().queryLong( "select sum( last_count ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and guess_duicuo = 2 and guess_index = 1 and item_id = 20017 and user_id = "+ user_id +";", c );
			long buyaBetYinDan = ServiceManager.getSqlService().queryLong( "select sum( last_count ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and guess_duicuo = 2 and guess_index = 1 and item_id = 20016  and user_id = "+ user_id +";", c );

			long buyaCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and guess_duicuo = 2 and guess_index = 1  and user_id = "+ user_id +";", c );


			val.people = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and user_id = "+ user_id +" and guess_type != -1;", c );

			val.count = ServiceManager.getSqlService().queryLong( "select count( distinct id ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and guess_duicuo in ( 0 , 2 )  and guess_type != -1 and user_id = "+ user_id +" ;", c ) - buyaCount ;

			val.betGold = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
					" from t_fish_paiment " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and other_info = 14  and user_id = "+ user_id +";", c ) - buyaBetGold;
			val.betGoldDan = ServiceManager.getSqlService().queryLong( "select -sum( item_count ) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and item_id = 20017 and item_count < 0 and other_info = 'guess' and user_id = "+ user_id +";", c ) - buyaBetGoldDan;
			val.betYinDan = ServiceManager.getSqlService().queryLong( "select -sum( item_count ) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and item_id = 20016 and item_count < 0 and other_info = 'guess' and user_id = "+ user_id +";", c ) - buyaBetYinDan;
			val.winGold = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
					" from t_fish_get " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and other_info = 14  and user_id = "+ user_id +";", c ) - buyaBetGold;
			val.winGoldDan = ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and item_id = 20017 and item_count > 0  and other_info = 'guess' and user_id = "+ user_id +";", c ) - buyaBetGoldDan ;
			val.winYinDan = ServiceManager.getSqlService().queryLong( "select sum( item_count ) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and item_id = 20016 and item_count > 0  and other_info = 'guess' and user_id = "+ user_id +";", c ) - buyaBetYinDan ;
			val.wangpai = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + "  and item_id = 20017 and card = 66 and guess_duicuo != 2 and user_id = "+ user_id +";", c );
			val.meilunrenshu = ServiceManager.getSqlService().queryMapLongLong("select guess_index,count( id ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and user_id = "+ user_id +" and guess_duicuo in ( 0 , 2 ) and item_count != last_count  group by guess_index ;", c );

			return val;
		} );
	}

	public void queryGuessPoker( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_BEICAI  , getGuessPokerData( req, resp ),
				BeiCaiTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.people + "</td>" );
					pw.write( "<td>" + info.count + "</td>" );
					pw.write( "<td>" + info.betGold + "</td>" );
					pw.write( "<td>" + info.winGold + "</td>" );
					pw.write( "<td>" + (info.betGold - info.winGold) + "</td>" );
					pw.write( "<td>" + info.betGoldDan + "</td>" );
					pw.write( "<td>" + info.winGoldDan + "</td>" );
					pw.write( "<td>" + (info.betGoldDan - info.winGoldDan ) + "</td>" );
					pw.write( "<td>" + info.betYinDan + "</td>" );
					pw.write( "<td>" + info.winYinDan + "</td>" );
					pw.write( "<td>" + (info.betYinDan - info.winYinDan) + "</td>" );
					pw.write( "<td>" + info.wangpai + "</td>" );
					pw.write( "<td>" );
					info.meilunrenshu.forEach( ( itemId, itemCount ) -> {
							pw.write( "轮数：  " + itemId  + "   次数： " + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}
	public void queryGuessPokerCha( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_BEICAI , getGuessPokerChaData( req, resp ),
				BeiCaiTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.people + "</td>" );
					pw.write( "<td>" + info.count + "</td>" );
					pw.write( "<td>" + info.betGold + "</td>" );
					pw.write( "<td>" + info.winGold + "</td>" );
					pw.write( "<td>" + (info.betGold - info.winGold) + "</td>" );
					pw.write( "<td>" + info.betGoldDan + "</td>" );
					pw.write( "<td>" + info.winGoldDan + "</td>" );
					pw.write( "<td>" + (info.betGoldDan - info.winGoldDan) + "</td>" );
					pw.write( "<td>" + info.betYinDan + "</td>" );
					pw.write( "<td>" + info.winYinDan + "</td>" );
					pw.write( "<td>" + (info.betYinDan - info.winYinDan) + "</td>" );
					pw.write( "<td>" + info.wangpai + "</td>" );
					pw.write( "<td>" );
					info.meilunrenshu.forEach( ( itemId, itemCount ) -> {
						pw.write( "轮数：  " + itemId  + "   次数： " + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
				} );
	}

	public List< ActivityView.huodong > getKaiXinZhuanPanData (HttpServletRequest req, HttpServletResponse resp ,long actId )
			throws Exception {

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" ;", c );


			long dau = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + " ;", c );

			//展示率
			val.canyulv = val.seepeople * 1.0 / dau ;
			val.fufeilv = val.People * 1.0 / val.seepeople ;

			val.chizi = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
					" from t_fish_pool_move " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and type = 71 ;", c );

			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" ;", c );
			//输
			val.Count2 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and is_mobile = -1 and activity_id ="+ actId +" ;", c );
			//赢
			val.Count3 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and is_mobile = 1 and activity_id ="+ actId +" ;", c );
			//不赌
			val.Count4 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and is_mobile = 0 and activity_id ="+ actId +" ;", c );

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +" ;", c );
			//正产产出
			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 10537 ;", c );
			Map< Long, Long > getMap = new HashMap<>();
			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			val.daoju = getMap;

			if (val.xiaohao == 0 )
			{val.xiaohao = 1;}
			val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000
					+ getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000
					+ getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap , -4 ) * 4000
					+ getLong(getMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)

			) / ( val.xiaohao );

			return val;
		} );
	}
	public List< ActivityView.huodong > getKaiXinZhuanPanCha(HttpServletRequest req, HttpServletResponse resp ,long actId )
			throws Exception {

		long user_id = Long.parseLong( req.getParameter( "actId" ).trim() );

		return getDataList( req, resp, ( beg, end, c ) -> {
			ActivityView.huodong val = new ActivityView.huodong();
			val.begin = TimeUtil.ymdFormat().format( beg );
			val.end = TimeUtil.ymdFormat().format( end );

			val.seepeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and user_id = " + user_id + " ;", c );

			val.seetimes = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_activity_people " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +"  and user_id = " + user_id + " ;", c );

			val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +"  and user_id = " + user_id + " ;", c );


			long dau = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
					" from t_possession " +
					" where " + beg + " <= timestamp and timestamp < " + end + " ;", c );

			//展示率
			val.canyulv = val.seepeople * 1.0 / dau ;
			val.fufeilv = val.People * 1.0 / val.seepeople ;

			val.chizi = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
					" from t_fish_pool_move " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and type = 71 and user_id = " + user_id + " ;", c );

			val.Count1 = ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and user_id = " + user_id + " ;", c );
			//输
			val.Count2 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and is_mobile = -1 and activity_id ="+ actId +"  and user_id = " + user_id + " ;", c );
			//赢
			val.Count3 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and is_mobile = 1 and activity_id ="+ actId +"  and user_id = " + user_id + " ;", c );
			//不赌
			val.Count4 =  ServiceManager.getSqlService().queryLong( "select count( id ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and is_mobile = 0 and activity_id ="+ actId +"  and user_id = " + user_id + " ;", c );

			val.xiaohao = ServiceManager.getSqlService().queryLong( "select sum( cost_count ) " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp< " + end + " and cost_id = 10537 and activity_id ="+ actId +"  and user_id = " + user_id + " ;", c );
			//正产产出
			List<String> getinfo = ServiceManager.getSqlService().queryListString( "select get_info " +
					" from t_newactivity " +
					" where " + beg + " <= timestamp and timestamp < " + end + " and activity_id ="+ actId +" and cost_id = 10537  and user_id = " + user_id + " ;", c );
			Map< Long, Long > getMap = new HashMap<>();
			getinfo.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
			val.daoju = getMap;

			if (val.xiaohao == 0 )
			{val.xiaohao = 1;}
			val.bili = (getLong(getMap , -1) + getLong(getMap , 20015 ) * 20000
					+ getLong(getMap , 20016 ) * 200000 + getLong(getMap , 20017 ) * 1000000
					+ getLong(getMap , 10507 ) * (ItemManager.getInstance().get( 10507 ).worth)
					+ getLong(getMap , 10512 )  * (ItemManager.getInstance().get( 10512 ).worth)
					+ getLong(getMap , -4 ) * 4000
					+ getLong(getMap , 20032 ) * (ItemManager.getInstance().get( 20032 ).worth)
					+ getLong(getMap , 20034 ) * (ItemManager.getInstance().get( 20034 ).worth)
					+ getLong(getMap , 20036 ) * (ItemManager.getInstance().get( 20036 ).worth)

			) / ( val.xiaohao );

			return val;
		} );
	}

	public void queryKaiXinZhuanPanCha( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		//private static List< String > KaiXinZhuanPanTableHead = Arrays.asList( "时间","展示人数","展示次数","展示率",
//		"参与人数","参与次数","付费率", "胡莱币消耗", "转盘产出", "比例","赢--不赌--输","赢比例");
		queryList( req, resp,
				CommandList.GET_ACTIVITY_HAIDIDUOBAO, getKaiXinZhuanPanCha( req, resp  , 19 ),
				KaiXinZhuanPanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
					pw.write( "<td>" + info.Count3 +" -- " + info.Count4 + "--" + info.Count2 + "</td>" );
					pw.write( "<td>" + info.chizi + "</td>" );
				} );
	}

	public void queryKaiXinZhuanPan( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {
		queryList( req, resp,
				CommandList.GET_ACTIVITY_KAIXINZHUANPAN, getKaiXinZhuanPanData( req, resp  , 19 ),
				KaiXinZhuanPanTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.seepeople + "</td>" );
					pw.write( "<td>" + info.seetimes + "</td>" );
					pw.write( "<td>" + formatRatio(info.canyulv ) + "</td>" );
					pw.write( "<td>" + info.People + "</td>" );
					pw.write( "<td>" + info.Count1 + "</td>" );
					pw.write( "<td>" + formatRatio(info.fufeilv ) + "</td>" );
					pw.write( "<td>" + info.xiaohao + "</td>" );
					pw.write( "<td>" );
					info.daoju.forEach( ( itemId, itemCount ) -> {
						if (itemId == -1 )
							pw.write("金币:" + itemCount / 10000 + "万<br>" );
						else
							pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
					} );
					pw.write( "</td>" );
					pw.write( "<td>" + info.bili + "</td>" );
					pw.write( "<td>" + info.Count3 +" -- " + info.Count4 + " -- " + info.Count2 + "</td>" );
					pw.write( "<td>" + info.chizi + "</td>" );
				} );
	}

	private List< List< ActivityView.GuessPokerRank > > getGuessPokerRankData( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {

		int limit = Integer.parseInt( req.getParameter( "limit" ) );
		return getData( req, resp, ( beg, end, c ) -> {
			Map< Long, Long > goldBet = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold) " +
					" from t_fish_paiment " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and other_info = 14 group by user_id;", c );
			Map< Long, Long > goldWin = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
					" from t_fish_get " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and other_info = 14 group by user_id;", c );
			Map< Long, Long > bucaiGoldWin = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( last_count ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and guess_index = 1 and guess_duicuo = 2 and item_id = -1 group by user_id;", c );
			Map< Long, Long > bucaiGoldDanWin = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( last_count ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and guess_index = 1 and guess_duicuo = 2 and item_id = 20017 group by user_id;", c );
			Map< Long, Long > bucaiYinDanWin = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( last_count ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and guess_index = 1 and guess_duicuo = 2 and item_id = 20016 group by user_id;", c );
			Map< Long, Long > GoldDanBet = ServiceManager.getSqlService().queryMapLongLong( "select user_id, -sum( item_count ) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and item_id = 20017 and item_count < 0 and other_info = 'guess' group by user_id;", c );
			Map< Long, Long > GoldDanWin = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( item_count ) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and item_id = 20017 and item_count > 0 and other_info = 'guess' group by user_id;", c );
			Map< Long, Long > YinDanBet = ServiceManager.getSqlService().queryMapLongLong( "select user_id, -sum( item_count ) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and item_id = 20016 and item_count < 0 and other_info = 'guess' group by user_id;", c );
			Map< Long, Long > YinDanWin = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( item_count ) " +
					" from t_fish_item " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and item_id = 20016 and item_count > 0 and other_info = 'guess' group by user_id;", c );
			Map< Long, Long > CaiShenPai = ServiceManager.getSqlService().queryMapLongLong( "select user_id, count( id ) " +
					" from t_fish_guess_poker " +
					" where " + beg + " <= timestamp and timestamp < " + end +
					" and card = 66 group by user_id;", c );

			Map< Long, ActivityView.GuessPokerRank > sum = new HashMap<>();

			goldBet.forEach( ( k, v ) -> {
				if( sum.get( k ) == null ) sum.put( k, new ActivityView.GuessPokerRank() );
				sum.get( k ).betGold = v;
			} );

			goldWin.forEach( ( k, v ) -> {
				if( sum.get( k ) == null ) sum.put( k, new ActivityView.GuessPokerRank() );
				sum.get( k ).winGold = v;
			} );

			GoldDanBet.forEach( ( k, v ) -> {
				if( sum.get( k ) == null ) sum.put( k, new ActivityView.GuessPokerRank() );
				sum.get( k ).betGoldDan = v;
			} );

			GoldDanWin.forEach( ( k, v ) -> {
				if( sum.get( k ) == null ) sum.put( k, new ActivityView.GuessPokerRank() );
				sum.get( k ).winGoldDan = v;
			} );
			YinDanBet.forEach( ( k, v ) -> {
				if( sum.get( k ) == null ) sum.put( k, new ActivityView.GuessPokerRank() );
				sum.get( k ).betYinDan = v;
			} );

			YinDanWin.forEach( ( k, v ) -> {
				if( sum.get( k ) == null ) sum.put( k, new ActivityView.GuessPokerRank() );
				sum.get( k ).winYinDan = v;
			} );

			bucaiGoldWin.forEach( ( k, v ) -> {
				if( sum.get( k ) == null ) sum.put( k, new ActivityView.GuessPokerRank() );
				sum.get( k ).winGold -= v;
				sum.get( k ).betGold -= v;
			} );
			bucaiGoldDanWin.forEach( ( k, v ) -> {
				if( sum.get( k ) == null ) sum.put( k, new ActivityView.GuessPokerRank() );
				sum.get( k ).winGoldDan -= v;
				sum.get( k ).betGoldDan -= v;
			} );
			bucaiYinDanWin.forEach( ( k, v ) -> {
				if( sum.get( k ) == null ) sum.put( k, new ActivityView.GuessPokerRank() );
				sum.get( k ).winYinDan -= v;
				sum.get( k ).betYinDan -= v;
			} );
			CaiShenPai.forEach( ( k, v ) -> {
				if( sum.get( k ) == null ) sum.put( k, new ActivityView.GuessPokerRank() );
				sum.get( k ).wangpai = v;
			} );

			List< ActivityView.GuessPokerRank > winList = new ArrayList<>();
			List< ActivityView.GuessPokerRank > loseList = new ArrayList<>();
			sum.forEach( ( k, v ) -> {
				v.user_id = k;
				v.begin = TimeUtil.ymdFormat().format( beg );
				v.netWinGold = v.winGold  - v.betGold ;
				v.netWinGoldDan = v.winGoldDan - v.betGoldDan ;
				v.netWinYinDan = v.winYinDan  - v.betYinDan ;
				if (v.betGoldDan == 0 || v.betGold ==0 ) return;
				if( v.netWinGold + (v.netWinGoldDan * 1000000) + (v.netWinYinDan * 200000) >  0 ) winList.add( v );
				else if( v.netWinGold + (v.netWinGoldDan * 1000000) + (v.netWinYinDan * 200000) <  0  ) loseList.add( v );
			} );

			Collections.sort( winList, ( o1, o2 ) -> {
				if( o1.netWinGoldDan > o2.netWinGoldDan ) return -1;
				if( o1.netWinGoldDan < o2.netWinGoldDan ) return 1;
				if( o1.netWinGold < o2.netWinGold ) return -1;
				if( o1.netWinGold > o2.netWinGold ) return 1;
				return 0;
			} );

			Collections.sort( loseList, ( o1, o2 ) -> {
				if( o1.netWinGoldDan < o2.netWinGoldDan ) return -1;
				if( o1.netWinGoldDan > o2.netWinGoldDan ) return 1;
				if( o1.netWinGold < o2.netWinGold ) return -1;
				if( o1.netWinGold > o2.netWinGold ) return 1;
				return 0;
			} );

			return Arrays.asList( winList, loseList );
		} );
	}

	public void queryGuessPokerRank( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		queryMultiList( req, resp,
				CommandList.GET_ZFB, getGuessPokerRankData( req, resp ),
				GuessPokerRankTableHead, ( info, pw ) -> {
					pw.write( "<td>" + info.begin + "</td>" );
					pw.write( "<td>" + info.user_id + "</td>" );
					pw.write( "<td>" + UserSdk.getUserName(info.user_id) + "</td>" );
					pw.write( "<td>" + info.wangpai + "</td>" );
					pw.write( "<td>" + info.betGold + "</td>" );
					pw.write( "<td>" + info.winGold + "</td>" );
					pw.write( "<td>" + info.betGoldDan + "</td>" );
					pw.write( "<td>" + info.winGoldDan + "</td>" );
					pw.write( "<td>" + info.betYinDan + "</td>" );
					pw.write( "<td>" + info.winYinDan + "</td>" );
					pw.write( "<td>" + ( info.betGold - info.winGold ) + "</td>" );
					pw.write( "<td>" + ( info.betGoldDan - info.winGoldDan ) + "</td>" );
					pw.write( "<td>" + ( info.betYinDan - info.winYinDan ) + "</td>" );
				}, null );
	}


}