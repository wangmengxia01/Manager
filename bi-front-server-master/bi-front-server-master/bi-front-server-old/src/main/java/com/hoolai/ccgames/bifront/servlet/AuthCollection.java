package com.hoolai.ccgames.bifront.servlet;

import java.util.LinkedList;
import java.util.List;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.vo.AuthGroup;

public class AuthCollection {

	private static List< AuthGroup > authList = new LinkedList< AuthGroup >();

	public static List< AuthGroup > getAuthList() {
		return authList;
	}

	static {
		authList.add( new AuthGroup( "汇总项" )
				.addItem( String.valueOf( CommandList.GET_SUMMARY_INSTALL_PAY ), "收入详情" )
				.addItem( String.valueOf( CommandList.GET_SUMMARY_FINANCE), "德州金融基础" )
				.addItem( String.valueOf( CommandList.GET_SUMMARY_FISH_FINANCE ), "捕鱼金融基础" )
				.addItem( String.valueOf( CommandList.GET_SUMMARY_HUIZONG), "德州金融汇总" )
				.addItem( String.valueOf( CommandList.GET_SUMMARY_FISH_HUIZONG), "捕鱼金融汇总" )
				.addItem( String.valueOf( CommandList.GET_SUMMARY_INSTALL_PAY_HUIZONG), "收入汇总" )
				.addItem( String.valueOf( CommandList.GET_PEOPLE), "玩游戏的人" )
				.addItem( String.valueOf( CommandList.GET_LIUSHUI), "流水" )
				.addItem( String.valueOf( CommandList.GET_SUMMARY_DANTOU), "弹头" )
				.addItem( String.valueOf( CommandList.GET_QUDAO_INSATLL ), "渠道注册" )
				.addItem( String.valueOf( CommandList.GET_SUMMARY_ZICHONG), "自充" )
				.addItem( String.valueOf( CommandList.GET_SUMMARY_ZHOUBAO), "周报" )
		);

		authList.add( new AuthGroup( "产品项" )
				.addItem( String.valueOf( CommandList.GET_PRODUCT), "公共玩法" )
				.addItem( String.valueOf( CommandList.GET_YELLOW_GIFT ), "黄钻特权统计" )
				.addItem( String.valueOf( CommandList.GET_MJDR), "开房玩法" )
		);

		authList.add( new AuthGroup( "用户项" )
						.addItem( String.valueOf( CommandList.GET_USER_INSTALL_PAY_DAYS ), "新增后续付费" )
						.addItem( String.valueOf( CommandList.GET_USER_SHICHANGBU ), "市场部" )
						.addItem( String.valueOf( CommandList.GET_USER_INSTALL_RETENTION ), "新增用户留存" )
						.addItem( String.valueOf( CommandList.GET_SINGLE ), "单用户" )
						.addItem( String.valueOf( CommandList.GET_USER_LTV ), "LTV查询" )
						.addItem( String.valueOf( CommandList.GET_USER_DAU ), "用户活跃度" )
						.addItem( String.valueOf( CommandList.GET_USER_USER_WIN_LOSE ), "用户输赢" )
						.addItem( String.valueOf( CommandList.GET_PAY_DETAIL), "付费排行" )
						.addItem( String.valueOf( CommandList.GET_USER_DANTOU), "用户弹头" )
						.addItem( String.valueOf( CommandList.GET_USER_JINRONG), "用户金融汇总" )
						.addItem( String.valueOf( CommandList.GET_USER_GAME_WIN), "用户游戏输赢" )
						.addItem( String.valueOf( CommandList.GET_USER_RMB ), "人民币" )
//			.addItem( String.valueOf( CommandList.GET_SINGLE ), "用户手机号" )
		);

		authList.add( new AuthGroup( "活动项" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_KAIXINZHUANPAN ), "开心转盘" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_BEICAI ), "渔场猜倍" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_YYL ), "德州YYL" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_GOLDEGG), "幸运金蛋" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_YIYUANDUOBAO ), "一元夺宝基础" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_YIYUANDUOBAO_DANQI ), "一元夺宝单期" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_YIYUANDUOBAO_DANQIDANREN ), "一元夺宝单期单人" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_WAKUANGCHAN ), "挖矿铲" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_JUBAOPEN ), "聚宝盆" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_DAZHUANPAN ), "大转盘" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_FANPAI), "翻牌" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_SHENGDAN), "圣诞活动" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_YUANDAN), "元旦活动" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_PINTU), "拼图活动" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_DENGLONG), "灯笼活动" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_HONGBAO), "红包活动" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_LUCKY_CHOUJIANG), "幸运抽奖" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_TAONIU), "套牛" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_NEWZHUANPAN), "新转盘" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_SHEJI), "射击" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_QIFU), "祈福" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_FISH_CHOUQIAN), "抽签" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_MOPING ), "魔瓶" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_CHUO ), "戳戳" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_BAOZI ), "包子" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_GUANZI ), "罐子" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_WANGPAI ), "王牌" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_WANMI ), "拉力赛" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_PAISHEN ), "牌神" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_YUEBING ), "月饼" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_FISH_NANGUA ), "捕鱼万圣节" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_DAFUWENG ), "捕鱼大富翁" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_WANSHENG ), "德州万圣节" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_GANEN ), "德州感恩节" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_ZADAN ), "捕鱼砸蛋" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_QIQIU ), "捕鱼气球" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_SHENGDAN_FISH ), "捕鱼圣诞节" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_WAZI ), "德州圣诞袜" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_HAIDIDUOBAO ), "海底夺宝" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_QINGCAISHEN), "捕鱼拼图" )
				.addItem( String.valueOf( CommandList.GET_ACTIVITY_ZAJINDAN), "德州砸金蛋" )
				.addItem( String.valueOf( CommandList.GET_DOWNLOAD), "下载页" )
		);

		authList.add( new AuthGroup( "胡来德州扑克" )
				.addItem( String.valueOf( CommandList.GET_TEXASPOKER), "德州扑克" )
				.addItem( String.valueOf( CommandList.GET_NEWPOKER), "新德州扑克" )
		);

		authList.add( new AuthGroup( "重要道具" )
				.addItem( String.valueOf( CommandList.GET_PROPS), "重要道具" )
		);

		authList.add( new AuthGroup( "中发白" )
				.addItem( String.valueOf( CommandList.GET_ZFB), "中发白" )
		);

		authList.add( new AuthGroup( "森林摩天轮" )
				.addItem( String.valueOf( CommandList.GET_MTL_BASIC ), "森林摩天轮基础报表" )
				.addItem( String.valueOf( CommandList.GET_MTL_RANK ), "森林摩天轮玩家输赢排行" )
		);

		authList.add( new AuthGroup( "无双捕鱼" )
				.addItem( String.valueOf( CommandList.GET_FISH), "捕鱼" )
		);

		authList.add( new AuthGroup( "新摩天轮" )
				.addItem( String.valueOf( CommandList.GET_MTL2), "新摩天轮" )
		);

		authList.add( new AuthGroup( "水果大满贯" )
				.addItem( String.valueOf( CommandList.GET_FRUIT), "水果大满贯" )
		);

		authList.add( new AuthGroup( "跑跑堂" )
				.addItem( String.valueOf( CommandList.GET_PPT), "跑跑堂" )
		);

		authList.add( new AuthGroup( "汪汪运动会" )
				.addItem( String.valueOf( CommandList.GET_DOGSPORT), "汪汪运动会" )
		);

		authList.add( new AuthGroup( "黄金矿工" )
				.addItem( String.valueOf( CommandList.GET_FARM_BASIC ), "黄金矿工基础报表" )
		);

		authList.add( new AuthGroup( "熔炼系统" )
				.addItem( String.valueOf( CommandList.GET_RONGLIAN_SUMMARY ), "熔炼汇总报表" )
		);

		authList.add( new AuthGroup( "大师积分" )
				.addItem( String.valueOf( CommandList.GET_MP_COST ), "大师积分实物兑换" )
		);

		authList.add( new AuthGroup( "麻将达人" )
				.addItem( String.valueOf( CommandList.GET_MJDR ), "麻将" )
		);

		authList.add( new AuthGroup( "斗地主" )
				.addItem( String.valueOf( CommandList.GET_DDZ ), "斗地主" )
		);
		authList.add( new AuthGroup( "手机捕鱼" )
				.addItem( String.valueOf( CommandList.GET_FISH_MB ), "手机捕鱼" )
		);
		authList.add( new AuthGroup( "百人德州" )
				.addItem( String.valueOf( CommandList.GET_BRDZ ), "百人德州" )
		);
	}
}
