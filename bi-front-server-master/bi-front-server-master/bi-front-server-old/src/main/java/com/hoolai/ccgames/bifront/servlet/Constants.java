package com.hoolai.ccgames.bifront.servlet;

import com.hoolai.ccgames.bifront.util.HttpRequest;

import java.util.Arrays;
import java.util.List;

public class Constants {

	public static final String ACT_SYSTEM_LOGIN = "system_login";     // 登录
	public static final String ACT_SYSTEM_CAPTCHA = "system_captcha"; // 验证码
	public static final String ACT_SYSTEM_LOGOUT = "system_logout";   // 退出

	public static final String KEY_USER_INFO = "kUserInfo";
	public static final String KEY_LOGIN_CAPTCHA = "kLoginCaptcha";
	public static final String KEY_BI_CLIENT = "kBiClient";
	public static final String KEY_AUTH = "kUserAuth";

	public static final String URL_TEST = "/WEB-INF/bi/test.jsp";
	public static final String URL_EMPTY = "/WEB-INF/bi/empty.jsp";
	public static final String URL_NO_AUTH = "/WEB-INF/bi/no_auth.jsp";
	public static final String URL_LOGIN = "/WEB-INF/bi/login.jsp";
	public static final String URL_MAIN = "/WEB-INF/bi/main.jsp";
	public static final String URL_TPG_MAIN = "/WEB-INF/bi/tpg_main.jsp";
	public static final String URL_DWBG_MAIN = "/WEB-INF/bi/dwbg_main.jsp";
	public static final String URL_MODIFY_PASS = "/WEB-INF/bi/admin/modifyPass.jsp";
	public static final String URL_LIST_GM_USERS = "/WEB-INF/bi/admin/listGmUsers.jsp";
	public static final String URL_USER_AUTH = "/WEB-INF/bi/admin/userAuth.jsp";
	public static final String URL_LIST_BI_SERVER_USERS = "/WEB-INF/bi/admin/listBiServerUsers.jsp";
	public static final String URL_LIST_GAME_SERVER_USERS = "/WEB-INF/bi/admin/listGameServerUsers.jsp";

	public static final String URL_QUDAO  = "/WEB-INF/bi/summary/qudao_install.jsp";

	public static final String URL_SUMMARY_FINANCE = "/WEB-INF/bi/summary/finance.jsp";
	public static final String URL_SUMMARY_FISH_FINANCE = "/WEB-INF/bi/summary/fish_finance.jsp";
	public static final String URL_SUMMARY_INSTALL_PAY_HUIZONG = "/WEB-INF/bi/summary/install_pay_huizong.jsp";
	public static final String URL_SUMMARY_INSTALL_PAY = "/WEB-INF/bi/summary/install_pay.jsp";
	public static final String URL_SUMMARY_ZIYUAN= "/WEB-INF/bi/summary/ziyuan.jsp";
	public static final String URL_SUMMARY_HUAFEI= "/WEB-INF/bi/summary/summary_huafei.jsp";
	public static final String URL_SUMMARY_BAOTU= "/WEB-INF/bi/summary/summary_baotu.jsp";
	public static final String URL_SUMMARY_HUIZONG= "/WEB-INF/bi/summary/huizong.jsp";
	public static final String URL_SUMMARY_FISH_HUIZONG= "/WEB-INF/bi/summary/FishHuizong.jsp";
	public static final String URL_SUMMARY_LIUSHUI= "/WEB-INF/bi/summary/liushui.jsp";
	public static final String URL_SUMMARY_CHOUSHUILV= "/WEB-INF/bi/summary/choushuilv.jsp";
	public static final String URL_SUMMARY_PEOPLE= "/WEB-INF/bi/summary/people.jsp";
	public static final String URL_SUMMARY_DANTOU= "/WEB-INF/bi/summary/dantouhuizong.jsp";
	public static final String URL_SUMMARY_ZHOUBAO= "/WEB-INF/bi/summary/zhoubao.jsp";
	public static final String URL_SUMMARY_ZICHONG= "/WEB-INF/bi/summary/ZiChong.jsp";
	public static final String URL_SUMMARY_ZICHONG_XIANGXI= "/WEB-INF/bi/summary/ZiChongXiangXi.jsp";


	public static final String URL_USER_INSTALL_PAY_DAYS = "/WEB-INF/bi/user/install_pay_days.jsp";
	public static final String URL_USER_INSTALL_RETENTION = "/WEB-INF/bi/user/install_retention.jsp";
	public static final String URL_USER_PAY_RETENTION = "/WEB-INF/bi/user/install_pay_retention.jsp";
	public static final String URL_USER_WINLOSE = "/WEB-INF/bi/user/winlose.jsp";
	public static final String URL_USER_PHONENUM = "/WEB-INF/bi/user/phonenum.jsp";
	public static final String URL_USER_LTV = "/WEB-INF/bi/user/install_LTV.jsp";
	public static final String URL_USER_SHICHANGBU= "/WEB-INF/bi/user/install_pay_shichang.jsp";
	public static final String URL_USER_DAU = "/WEB-INF/bi/user/dau.jsp";
	public static final String URL_USER_ONLINE = "/WEB-INF/bi/user/all_online.jsp";
	public static final String URL_USER_FUFEILV = "/WEB-INF/bi/user/install_pay_fufeilv.jsp";
	public static final String URL_USER_PAY_DETAIL = "/WEB-INF/bi/user/all_pay_detail.jsp";
	public static final String URL_USER_DANTOU = "/WEB-INF/bi/user/user_dantou.jsp";
	public static final String URL_USER_HUIZONG = "/WEB-INF/bi/user/user_userhuizong.jsp";
	public static final String URL_USER_GAME_WIN = "/WEB-INF/bi/user/user_game_win.jsp";
	public static final String URL_USER_RMB = "/WEB-INF/bi/user/fish_user_rmb.jsp";

	public static final String URL_SINGLE_PAY_RANK = "/WEB-INF/bi/user/single_user_pay_rank.jsp";

	public static final String URL_SINGLE_USER = "/WEB-INF/bi/user/single_user.jsp";
	public static final String URL_SINGLE_CHIPMOVE = "/WEB-INF/bi/user/single_user_chipmove.jsp";
	public static final String URL_SINGLE_FISH_CHIPMOVE = "/WEB-INF/bi/user/single_user_fish_chipmove.jsp";
	public static final String URL_SINGLE_ENEMY = "/WEB-INF/bi/user/single_user_enemy.jsp";
	public static final String URL_SINGLE_IP = "/WEB-INF/bi/user/single_user_ip.jsp";
	public static final String URL_SINGLE_PAY = "/WEB-INF/bi/user/single_user_pay.jsp";

	public static final String URL_TEXASPOKER = "/WEB-INF/bi/texaspoker/texaspoker.jsp";
	public static final String URL_TEXASPOKER_RANK = "/WEB-INF/bi/texaspoker/texaspoker_rank.jsp";
	public static final String URL_TEXASPOKER_YOULUN = "/WEB-INF/bi/texaspoker/texaspoker_youlun.jsp";
	public static final String URL_TEXASPOKER_PAY_DETAIL = "/WEB-INF/bi/texaspoker/texaspoker_pay_detail.jsp";
	public static final String URL_TEXASPOKER_HUIZONG = "/WEB-INF/bi/texaspoker/texaspoker_huizong.jsp";
	public static final String URL_TEXASPOKER_JICHU = "/WEB-INF/bi/texaspoker/texaspoker_jichu.jsp";
	public static final String URL_TEXASPOKER_TIMER = "/WEB-INF/bi/texaspoker/texaspoker_timer.jsp";
	public static final String URL_TEXASPOKER_YAOJIANGJI = "/WEB-INF/bi/texaspoker/texaspoker_yaojiangji.jsp";
	public static final String URL_TEXASPOKER_ZHUANPAN = "/WEB-INF/bi/texaspoker/texaspoker_zhuanpan.jsp";
	public static final String URL_TEXASPOKER_LIUSHUI = "/WEB-INF/bi/texaspoker/texaspoker_liushui.jsp";

	public static final String URL_NEWPOKER = "/WEB-INF/bi/newpoker/newpoker.jsp";
	public static final String URL_NEWPOKER_JICHU = "/WEB-INF/bi/newpoker/newpoker_jichu.jsp";
	public static final String URL_NEWPOKER_HUIZONG = "/WEB-INF/bi/newpoker/newpoker_huizong.jsp";
	public static final String URL_NEWPOKER_INNING = "/WEB-INF/bi/newpoker/newpoker_inning.jsp";
	public static final String URL_NEWPOKER_LIUSHUI = "/WEB-INF/bi/newpoker/newpoker_liushui.jsp";


	public static final String URL_ZFB = "/WEB-INF/bi/zfb/zfb.jsp";
	public static final String URL_ZFB_BASIC = "/WEB-INF/bi/zfb/zfb_basic.jsp";
	public static final String URL_ZFB_ROBOT = "/WEB-INF/bi/zfb/zfb_robot.jsp";
	public static final String URL_ZFB_RANK = "/WEB-INF/bi/zfb/zfb_rank.jsp";
	public static final String URL_ZFB_FISH_RANK = "/WEB-INF/bi/zfb/zfb_FishRank.jsp";
	public static final String URL_ZFB_SHANGZHUANG = "/WEB-INF/bi/zfb/zfb_shangzhuang.jsp";
	public static final String URL_ZFB_CAICHI = "/WEB-INF/bi/zfb/zfb_caichi.jsp";
	public static final String URL_ZFB_RED_ENVELOPE = "/WEB-INF/bi/zfb/zfb_red_envelope.jsp";

	public static final String URL_MTL = "/WEB-INF/bi/mtl/mtl.jsp";
	public static final String URL_MTL_BASIC = "/WEB-INF/bi/mtl/mtl_basic.jsp";
	public static final String URL_MTL_RANK = "/WEB-INF/bi/mtl/mtl_rank.jsp";

	public static final String URL_MTL2 = "/WEB-INF/bi/mtl2/mtl2.jsp";
	public static final String URL_MTL2_FINANCE = "/WEB-INF/bi/mtl2/mtl2_finance.jsp";
	public static final String URL_MTL2_RANK = "/WEB-INF/bi/mtl2/mtl2_rank.jsp";
	public static final String URL_MTL2_FISH_RANK = "/WEB-INF/bi/mtl2/mtl2_FishRank.jsp";
	public static final String URL_MTL2_CAICHI = "/WEB-INF/bi/mtl2/mtl2_caichi.jsp";
	public static final String URL_MTL2_NIUBI = "/WEB-INF/bi/mtl2/mtl2_niubi.jsp";


	public static final String URL_FRUIT = "/WEB-INF/bi/fruit/fruit.jsp";
	public static final String URL_FRUIT_BASIC = "/WEB-INF/bi/fruit/fruit_basic.jsp";
	public static final String URL_FRUIT_RANK = "/WEB-INF/bi/fruit/fruit_rank.jsp";
	public static final String URL_FRUIT_CAIBEI_BASIC = "/WEB-INF/bi/fruit/caibei_basic.jsp";
	public static final String URL_FRUIT_CAIBEI_ITEM = "/WEB-INF/bi/fruit/caibei_item.jsp";
	public static final String URL_FRUIT_CAICHI = "/WEB-INF/bi/fruit/caibei_caichi.jsp";

	public static final String URL_FISH = "/WEB-INF/bi/fish/fish.jsp";
	public static final String URL_FISH_BASIC = "/WEB-INF/bi/fish/fish_basic.jsp";
	public static final String URL_FISH_RANK = "/WEB-INF/bi/fish/fish_rank.jsp";
	public static final String URL_FISH_JACKPOT = "/WEB-INF/bi/fish/fish_rank.jsp";
	public static final String URL_FISH_HUIZONG = "/WEB-INF/bi/fish/fish_huizong.jsp";
	public static final String URL_FISH_ITEM = "/WEB-INF/bi/fish/fish_item.jsp";
	public static final String URL_FISH_ONLINE = "/WEB-INF/bi/fish/fish_online.jsp";
	public static final String URL_FISH_LUCKYJACK = "/WEB-INF/bi/fish/fish_lucky_jack.jsp";

	public static final String URL_FISH_MB = "/WEB-INF/bi/fishMB/fish.jsp";
	public static final String URL_FISH_BASIC_MB = "/WEB-INF/bi/fishMB/fish_basic.jsp";
	public static final String URL_FISH_RANK_MB = "/WEB-INF/bi/fishMB/fish_rank.jsp";
	public static final String URL_FISH_JACKPOT_MB = "/WEB-INF/bi/fishMB/fish_rank.jsp";
	public static final String URL_FISH_JICHU_MB = "/WEB-INF/bi/fishMB/fish_rank.jsp";
	public static final String URL_FISH_HUIZONG_MB = "/WEB-INF/bi/fishMB/fish_huizong.jsp";
	public static final String URL_FISH_ITEM_MB = "/WEB-INF/bi/fishMB/fish_item.jsp";
	public static final String URL_FISH_DASHI_MB = "/WEB-INF/bi/fishMB/fish_DASHI.jsp";
	public static final String URL_FISH_ONLINE_MB = "/WEB-INF/bi/fishMB/fish_online.jsp";
	public static final String URL_FISH_HAVE_GOLD_MB = "/WEB-INF/bi/fishMB/fish_haveGold.jsp";
	public static final String URL_FISH_CHIBANG_MB = "/WEB-INF/bi/fishMB/fish_ChiBang.jsp";
	public static final String URL_FISH_PAOHUOYUE_MB = "/WEB-INF/bi/fishMB/fish_PaoHuoYue.jsp";
	public static final String URL_FISH_SEND_MB = "/WEB-INF/bi/fishMB/fish_Send.jsp";
	public static final String URL_FISH_NEW_USER_MB = "/WEB-INF/bi/fishMB/fish_new_user.jsp";
	public static final String URL_FISH_CHONGZHI_MB = "/WEB-INF/bi/fishMB/fish_chongzhi.jsp";
	public static final String URL_FISH_BROKE_MB = "/WEB-INF/bi/fishMB/fish_broke.jsp";
	public static final String URL_FISH_LOGIN_MB = "/WEB-INF/bi/fishMB/fish_login.jsp";
	public static final String URL_FISH_RED_MB = "/WEB-INF/bi/fishMB/fish_red.jsp";
	public static final String URL_FISH_UP_LEVEL_MB = "/WEB-INF/bi/fishMB/fish_up_level.jsp";
	public static final String URL_FISH_VIP_MB = "/WEB-INF/bi/fishMB/fish_VIP.jsp";
	public static final String URL_FISH_TASK_MB = "/WEB-INF/bi/fishMB/fish_task.jsp";
	public static final String URL_FISH_GAMEGOLD_MB = "/WEB-INF/bi/fishMB/fish_gameGold.jsp";
	public static final String URL_FISH_DANTOU_MB = "/WEB-INF/bi/fishMB/fish_DanTou.jsp";
	public static final String URL_FISH_LOUDOU_MB = "/WEB-INF/bi/fishMB/fish_loudou.jsp";
	public static final String URL_FISH_SHOP_MB = "/WEB-INF/bi/fishMB/fish_Shop.jsp";
	public static final String URL_FISH_USER_ITEM_MB = "/WEB-INF/bi/fishMB/fish_user_item_cha.jsp";
	public static final String URL_FISH_CAPTAIN_MB = "/WEB-INF/bi/fishMB/fish_captain.jsp";
	public static final String URL_FISH_JINGJI_MB = "/WEB-INF/bi/fishMB/fish_jingji.jsp";
	public static final String URL_FISH_BOSS_MB = "/WEB-INF/bi/fishMB/fish_boss.jsp";
	public static final String URL_FISH_FANBEI_MB = "/WEB-INF/bi/fishMB/fish_fanbei.jsp";
	public static final String URL_FISH_JIANGQUAN_MB = "/WEB-INF/bi/fishMB/fish_jiangquan.jsp";
	public static final String URL_FISH_DUIHUAN_MB = "/WEB-INF/bi/fishMB/fish_duihuan.jsp";
	public static final String URL_FISH_ZHUANPAN_MB = "/WEB-INF/bi/fishMB/fish_zhuanpan.jsp";
	public static final String URL_FISH_HANDLE_MB = "/WEB-INF/bi/fishMB/fish_handle.jsp";
	public static final String URL_FISH_USER_DANTOU = "/WEB-INF/bi/fishMB/fish_user_DanTou.jsp";
	public static final String URL_FISH_DANTOUFISH = "/WEB-INF/bi/fishMB/fish_dantoufish.jsp";
	public static final String URL_FISH_POKER= "/WEB-INF/bi/fishMB/fish_poker.jsp";
	public static final String URL_FISH_POOL_MOVE= "/WEB-INF/bi/fishMB/fish_pool_move.jsp";
	public static final String URL_FISH_BIG_R= "/WEB-INF/bi/fishMB/fish_bigR.jsp";
	public static final String URL_FISH_XIAZAI= "/WEB-INF/bi/fishMB/fish_xiazai.jsp";
	public static final String URL_FISH_CAICAICAI = "/WEB-INF/bi/fishMB/fish_caicaicai.jsp";
	public static final String URL_FISH_CAICAICAI_RANK = "/WEB-INF/bi/fishMB/fish_ccc_rank.jsp";

	public static final String URL_BRDZ = "/WEB-INF/bi/BRDZ/brdz.jsp";
	public static final String URL_BRDZ_BASIC = "/WEB-INF/bi/BRDZ/brdz_basic.jsp";
	public static final String URL_BRDZ_RANK = "/WEB-INF/bi/BRDZ/brdz_rank.jsp";


	public static final String URL_PPT = "/WEB-INF/bi/ppt/ppt.jsp";
	public static final String URL_PPT_BASIC = "/WEB-INF/bi/ppt/ppt_basic.jsp";
	public static final String URL_PPT_RANK = "/WEB-INF/bi/ppt/ppt_rank.jsp";
	public static final String URL_PPT_CAICHI = "/WEB-INF/bi/ppt/ppt_caichi.jsp";
	public static final String URL_PPT_SSC_BASIC = "/WEB-INF/bi/ppt/ssc_basic.jsp";

	public static final String URL_DOG = "/WEB-INF/bi/dog/dog.jsp";
	public static final String URL_DOG_BASIC = "/WEB-INF/bi/dog/dog_basic.jsp";
	public static final String URL_DOG_RANK = "/WEB-INF/bi/dog/dog_rank.jsp";
	public static final String URL_DOG_CAICHI = "/WEB-INF/bi/dog/dog_caichi.jsp";

	public static final String URL_FARM = "/WEB-INF/bi/farm/farm.jsp";
	public static final String URL_FARM_BASIC = "/WEB-INF/bi/farm/farm_basic.jsp";

	public static final String URL_SCMJ = "/WEB-INF/bi/scmj/scmj.jsp";
	public static final String URL_SCMJ_BASIC = "/WEB-INF/bi/scmj/scmj_basic.jsp";
	public static final String URL_SCMJ_RANK = "/WEB-INF/bi/scmj/scmj_rank.jsp";

	public static final String URL_RONGLIAN = "/WEB-INF/bi/ronglian/ronglian.jsp";
	public static final String URL_RONGLIAN_SUMMARY = "/WEB-INF/bi/ronglian/ronglian_summary.jsp";

	public static final String URL_MP = "/WEB-INF/bi/masterpoint/mp.jsp";
	public static final String URL_MP_COST = "/WEB-INF/bi/masterpoint/mp_cost.jsp";

	public static final String URL_MJDR = "/WEB-INF/bi/mjdr/mjdr.jsp";
	public static final String URL_XUELIU = "/WEB-INF/bi/xueliu/xueliu.jsp";
	public static final String URL_XUEZHAN = "/WEB-INF/bi/xuezhan/mjdr.jsp";
	public static final String URL_ERMA = "/WEB-INF/bi/erma/mjdr.jsp";
	public static final String URL_MJDR_FINANCE = "/WEB-INF/bi/mjdr/mjdr_finance.jsp";
	public static final String URL_MJDR_FUNNEL = "/WEB-INF/bi/mjdr/mjdr_funnel.jsp";
	public static final String URL_MJDR_ONLINE = "/WEB-INF/bi/mjdr/mjdr_online.jsp";
	public static final String URL_MJDR_UPGRADE = "/WEB-INF/bi/mjdr/mjdr_upgrade.jsp";
	public static final String URL_MJDR_MISSION = "/WEB-INF/bi/mjdr/mjdr_mission.jsp";
	public static final String URL_MJDR_BASIC_ERREN = "/WEB-INF/bi/mjdr/mjdr_basic_erren.jsp";
	public static final String URL_MJDR_BASIC_XUELIU = "/WEB-INF/bi/mjdr/mjdr_basic_xueliu.jsp";
	public static final String URL_MJDR_BASIC_XUEZHAN = "/WEB-INF/bi/mjdr/mjdr_basic_xuezhan.jsp";
	public static final String URL_MJDR_RANK = "/WEB-INF/bi/mjdr/mjdr_rank.jsp";
	public static final String URL_MJDR_XUEZHANRANK = "/WEB-INF/bi/xuezhan/mjdr_rank.jsp";
	public static final String URL_MJDR_XUELIURANK = "/WEB-INF/bi/xueliu/mjdr_rank.jsp";
	public static final String URL_MJDR_ERMARANK = "/WEB-INF/bi/erma/mjdr_rank.jsp";
	public static final String URL_MJDR_POSSESSION = "/WEB-INF/bi/mjdr/mjdr_possession.jsp";
	public static final String URL_MJDR_PAY_ITEM = "/WEB-INF/bi/mjdr/mjdr_pay_item.jsp";
	public static final String URL_MJDR_STAT_XUELIU = "/WEB-INF/bi/mjdr/mjdr_stat_xueliu.jsp";
	public static final String URL_MJDR_STAT_XUEZHAN = "/WEB-INF/bi/mjdr/mjdr_stat_xuezhan.jsp";
	public static final String URL_MJDR_LOTTERY_STAT = "/WEB-INF/bi/mjdr/mjdr_lottery_stat.jsp";
	public static final String URL_MJDR_LOTTERY_ITEM = "/WEB-INF/bi/mjdr/mjdr_lottery_item.jsp";
	public static final String URL_MJDR_LOTTERY_SUM = "/WEB-INF/bi/mjdr/mjdr_lottery_sum.jsp";
	public static final String URL_MJDR_AI_WINLOSE = "/WEB-INF/bi/mjdr/mjdr_ai_winlose.jsp";
	public static final String URL_MJDR_HUAFEI_GIFT = "/WEB-INF/bi/mjdr/mjdr_huafei_gift.jsp";
	public static final String URL_MJDR_XUELIUHUIZONG = "/WEB-INF/bi/xueliu/mjdr_huizong.jsp";
	public static final String URL_MJDR_XUEZHANHUIZONG = "/WEB-INF/bi/xuezhan/mjdr_huizong.jsp";
	public static final String URL_MJDR_ERMAHUIZONG  = "/WEB-INF/bi/erma/mjdr_huizong.jsp";
	public static final String URL_MJDR_PHONE_CARD_RETENTION = "/WEB-INF/bi/mjdr/mjdr_phone_card_retention.jsp";
	public static final String URL_MJDR_XUEZHANLIUSHUI  = "/WEB-INF/bi/xuezhan/mjdr_liushui.jsp";
	public static final String URL_MJDR_XUELIULIUSHUI = "/WEB-INF/bi/xueliu/mjdr_liushui.jsp";
	public static final String URL_MJDR_ERMALIUSHUI = "/WEB-INF/bi/erma/mjdr_liushui.jsp";

	public static final String URL_MJDR_KAIFANG  = "/WEB-INF/bi/kaifang/kaifang.jsp";
	public static final String URL_MJDR_KAIFANG_HUIZONG  = "/WEB-INF/bi/kaifang/kaifang_huizong.jsp";
	public static final String URL_MJDR_KAIFANG_BASIC  = "/WEB-INF/bi/kaifang/kaifang_basic.jsp";
	public static final String URL_MJDR_KAIFANG_XUELIU  = "/WEB-INF/bi/kaifang/kaifang_xueliu.jsp";
	public static final String URL_MJDR_KAIFANG_XUEZHAN  = "/WEB-INF/bi/kaifang/kaifang_xuezhan.jsp";



	public static final String URL_MJDR_MB = "/WEB-INF/bi/mjdr/mjdr_mb.jsp";
	public static final String URL_MJDR_LIANXI = "/WEB-INF/bi/mjdr/mjdr_lianxi.jsp";
	public static final String URL_MJDR_LIANXIWIN = "/WEB-INF/bi/mjdr/mjdr_lianxiWin.jsp";
	public static final String URL_MJDR_FINANCE_MB = "/WEB-INF/bi/mjdr/mjdr_finance_mb.jsp";
	public static final String URL_MJDR_FUNNEL_MB = "/WEB-INF/bi/mjdr/mjdr_funnel_mb.jsp";
	public static final String URL_MJDR_ONLINE_MB = "/WEB-INF/bi/mjdr/mjdr_online_mb.jsp";
	public static final String URL_MJDR_BASIC_XUELIU_MB = "/WEB-INF/bi/mjdr/mjdr_basic_xueliu_mb.jsp";
	public static final String URL_MJDR_BASIC_XUEZHAN_MB = "/WEB-INF/bi/mjdr/mjdr_basic_xuezhan_mb.jsp";

	public static final String URL_DDZ = "/WEB-INF/bi/ddz/ddz.jsp";
	public static final String URL_DDZ_JICHU = "/WEB-INF/bi/ddz/ddz_jichu.jsp";
	public static final String URL_DDZ_TASK = "/WEB-INF/bi/ddz/ddz_task.jsp";
	public static final String URL_DDZ_MULT = "/WEB-INF/bi/ddz/ddz_mult.jsp";
	public static final String URL_DDZ_CHOUJIANG= "/WEB-INF/bi/ddz/ddz_choujiang.jsp";
	public static final String URL_DDZ_RANK= "/WEB-INF/bi/ddz/ddz_rank.jsp";
	public static final String URL_DDZ_LUCKYWHEEL= "/WEB-INF/bi/ddz/ddz_luckywheel.jsp";
	public static final String URL_DDZ_FINANCE = "/WEB-INF/bi/ddz/ddz_finance.jsp";
	public static final String URL_DDZ_MISSION = "/WEB-INF/bi/ddz/ddz_mission.jsp";
	public static final String URL_DDZ_FUNNEL = "/WEB-INF/bi/ddz/ddz_funnel.jsp";
	public static final String URL_DDZ_ONLINE = "/WEB-INF/bi/ddz/ddz_online.jsp";
	public static final String URL_DDZ_POSSESSION = "/WEB-INF/bi/ddz/ddz_possession.jsp";
	public static final String URL_DDZ_PAY_ITEM = "/WEB-INF/bi/ddz/ddz_pay_item.jsp";
	public static final String URL_DDZ_LIUSHUI = "/WEB-INF/bi/ddz/ddz_liushui.jsp";
	public static final String URL_DDZ_PHONE_CARD_RETENTION = "/WEB-INF/bi/ddz/ddz_phone_card_retention.jsp";


	public static final String URL_PRODUCT = "/WEB-INF/bi/product/public.jsp";
	public static final String URL_PRODUCT_LOGIN_GIFT = "/WEB-INF/bi/product/login_gift.jsp";
	public static final String URL_PRODUCT_MONTH_GIFT = "/WEB-INF/bi/product/Month.jsp";
	public static final String URL_PRODUCT_LOGIN_PHONE_CARD = "/WEB-INF/bi/product/login_phone_card.jsp";
	public static final String URL_PRODUCT_LUCKY_FUND = "/WEB-INF/bi/product/lucky_fund.jsp";
	public static final String URL_PRODUCT_EXCHANGE = "/WEB-INF/bi/product/exchange.jsp";
	public static final String URL_PRODUCT_EXCHANGE_BASIC = "/WEB-INF/bi/product/exchange_basic.jsp";
	public static final String URL_PRODUCT_HUIZONG = "/WEB-INF/bi/product/product_huizong.jsp";
	public static final String URL_PRODUCT_DUIHUAN = "/WEB-INF/bi/product/duihuan.jsp";
	public static final String URL_PRODUCT_TASK = "/WEB-INF/bi/product/product_task.jsp";

	public static final String URL_DENGLONG = "/WEB-INF/bi/activity/act_denglong.jsp";
	public static final String URL_DENGLONG_DZ = "/WEB-INF/bi/activity/act_denglongDZ.jsp";
	public static final String URL_HONGBAO = "/WEB-INF/bi/activity/act_hongbao.jsp";
	public static final String URL_HONGBAO_DZ = "/WEB-INF/bi/activity/act_hongbaoDZ.jsp";
	public static final String URL_GOLD_EGG = "/WEB-INF/bi/activity/act_egg.jsp";
	public static final String URL_GOLD_EGG_DZ = "/WEB-INF/bi/activity/act_egg_dz.jsp";
	public static final String URL_PINTU = "/WEB-INF/bi/activity/act_pintu.jsp";
	public static final String URL_TIEREN = "/WEB-INF/bi/activity/act_tieren.jsp";
	public static final String URL_TIEREN_DZ = "/WEB-INF/bi/activity/act_tierenDZ.jsp";
	public static final String URL_PINTU_DZ = "/WEB-INF/bi/activity/act_pintu_dz.jsp";
	public static final String URL_DUOBAO = "/WEB-INF/bi/activity/duobao.jsp";
	public static final String URL_DUOBAO_DANQI = "/WEB-INF/bi/activity/act_danqi.jsp";
	public static final String URL_DUOBAO_DANQIDANREN = "/WEB-INF/bi/activity/act_danqidanren.jsp";
	public static final String URL_DUOBAO_DUOBAOJICHU = "/WEB-INF/bi/activity/act_jichu.jsp";
	public static final String URL_WAKUANGCHAN_ZONG = "/WEB-INF/bi/activity/act_kuangchan.jsp";
	public static final String URL_WAKUANGCHAN = "/WEB-INF/bi/activity/act_wakuangchan.jsp";
	public static final String URL_WAKUANGCHANMJ = "/WEB-INF/bi/activity/act_wakuangchanMJ.jsp";
	public static final String URL_JUBAOPEN = "/WEB-INF/bi/activity/act_jubao.jsp";
	public static final String URL_JUBAOPEN_DEZHOU = "/WEB-INF/bi/activity/act_jubaopen.jsp";
	public static final String URL_JUBAOPEN_MAJIANG = "/WEB-INF/bi/activity/act_jubaopenMJ.jsp";
	public static final String URL_ZHUANPAN = "/WEB-INF/bi/activity/act_dazhuanpan.jsp";
	public static final String URL_FANPAI_ZONG= "/WEB-INF/bi/activity/act_fanpai.jsp";
	public static final String URL_FANPAI_DEZHOU = "/WEB-INF/bi/activity/act_fanpaiDZ.jsp";
	public static final String URL_TAONIU= "/WEB-INF/bi/activity/act_taoniu.jsp";
	public static final String URL_TAONIU_DZ = "/WEB-INF/bi/activity/act_taoniuDZ.jsp";
	public static final String URL_NIANSHOU = "/WEB-INF/bi/activity/act_nianshou.jsp";
	public static final String URL_NIANSHOU_DEZHOU = "/WEB-INF/bi/activity/act_nianshouDZ.jsp";
	public static final String URL_NIANSHOU_MAJIANG = "/WEB-INF/bi/activity/act_nianshouMJ.jsp";
	public static final String URL_SHENGDAN = "/WEB-INF/bi/activity/act_shengdan.jsp";
	public static final String URL_SHENGDAN_DEZHOU = "/WEB-INF/bi/activity/act_shengdanDZ.jsp";
	public static final String URL_SHENGDAN_MAJIANG = "/WEB-INF/bi/activity/act_shengdanMJ.jsp";
	public static final String URL_YUANDAN = "/WEB-INF/bi/activity/act_yuandan.jsp";
	public static final String URL_YUANDAN_DEZHOU = "/WEB-INF/bi/activity/act_yuandanDZ.jsp";
	public static final String URL_YUANDAN_MAJIANG = "/WEB-INF/bi/activity/act_yuandanMJ.jsp";
	public static final String URL_NEWZHUANPAN = "/WEB-INF/bi/activity/act_newzhuanpan.jsp";
	public static final String URL_NEWZHUANPAN_DEZHOU = "/WEB-INF/bi/activity/act_newzhuanpanDZ.jsp";
	public static final String URL_NEWZHUANPAN_CHA_DEZHOU = "/WEB-INF/bi/activity/act_newzhuanpan_cha.jsp";
	public static final String URL_SHEJI = "/WEB-INF/bi/activity/act_sheji.jsp";
	public static final String URL_SHEJI_DEZHOU = "/WEB-INF/bi/activity/act_shejiDZ.jsp";
	public static final String URL_SHEJI_CHA_DEZHOU = "/WEB-INF/bi/activity/act_sheji_cha.jsp";
	public static final String URL_GANEN = "/WEB-INF/bi/activity/act_ganen.jsp";
	public static final String URL_GANEN_DEZHOU = "/WEB-INF/bi/activity/act_ganenDZ.jsp";
	public static final String URL_GANEN_CHA_DEZHOU = "/WEB-INF/bi/activity/act_ganen_cha.jsp";
	public static final String URL_QIFU = "/WEB-INF/bi/activity/act_qifu.jsp";
	public static final String URL_QIFU_DEZHOU = "/WEB-INF/bi/activity/act_qifuDZ.jsp";
	public static final String URL_QIFU_CHA_DEZHOU = "/WEB-INF/bi/activity/act_qifu_cha.jsp";
	public static final String URL_CHOUQIAN = "/WEB-INF/bi/activity/act_chouqian.jsp";
	public static final String URL_CHOUQIAN_FISH = "/WEB-INF/bi/activity/act_fish_chouqian.jsp";
	public static final String URL_CHOUQIAN_CHA_FISH = "/WEB-INF/bi/activity/act_fish_chouqianCha.jsp";
	public static final String URL_DOWNLOAD = "/WEB-INF/bi/activity/download.jsp";
	public static final String URL_MOPING = "/WEB-INF/bi/activity/act_moping.jsp";
	public static final String URL_MOPING_DEZHOU = "/WEB-INF/bi/activity/act_mopingDZ.jsp";
	public static final String URL_MOPING_CHA_DEZHOU = "/WEB-INF/bi/activity/act_moping_cha.jsp";
	public static final String URL_CHUO = "/WEB-INF/bi/activity/act_Chuo.jsp";
	public static final String URL_CHUO_DEZHOU = "/WEB-INF/bi/activity/act_chuoDZ.jsp";
	public static final String URL_CHUO_CHA_DEZHOU = "/WEB-INF/bi/activity/act_chuo_cha.jsp";
	public static final String URL_BAOZI = "/WEB-INF/bi/activity/act_baozi.jsp";
	public static final String URL_BAOZI_DEZHOU = "/WEB-INF/bi/activity/act_baoziDZ.jsp";
	public static final String URL_BAOZI_CHA_DEZHOU = "/WEB-INF/bi/activity/act_baozi_cha.jsp";
	public static final String URL_GUANZI = "/WEB-INF/bi/activity/act_guanzi.jsp";
	public static final String URL_GUANZI_DEZHOU = "/WEB-INF/bi/activity/act_guanziDZ.jsp";
	public static final String URL_GUANZI_CHA_DEZHOU = "/WEB-INF/bi/activity/act_guanzi_cha.jsp";
	public static final String URL_PAISHEN = "/WEB-INF/bi/activity/act_paishen.jsp";
	public static final String URL_PAISHEN_DEZHOU = "/WEB-INF/bi/activity/act_paishenDZ.jsp";
	public static final String URL_PAISHEN_CHA_DEZHOU = "/WEB-INF/bi/activity/act_paishen_cha.jsp";
	public static final String URL_WAZI = "/WEB-INF/bi/activity/act_wazi.jsp";
	public static final String URL_WAZI_DEZHOU = "/WEB-INF/bi/activity/act_waziDZ.jsp";
	public static final String URL_WAZI_CHA_DEZHOU = "/WEB-INF/bi/activity/act_wazi_cha.jsp";
	public static final String URL_ZAJINDAN = "/WEB-INF/bi/activity/act_zajindan.jsp";
	public static final String URL_ZAJINDAN_DEZHOU = "/WEB-INF/bi/activity/act_zajindanDZ.jsp";
	public static final String URL_ZAJINDAN_CHA_DEZHOU = "/WEB-INF/bi/activity/act_zajindan_cha.jsp";
	public static final String URL_YYL = "/WEB-INF/bi/activity/act_yyl.jsp";
	public static final String URL_YYL_DEZHOU = "/WEB-INF/bi/activity/act_yylDZ.jsp";
	public static final String URL_YYL_CHA_DEZHOU = "/WEB-INF/bi/activity/act_yyl_cha.jsp";
	public static final String URL_GUESSPOKER = "/WEB-INF/bi/activity/act_guesspoker.jsp";
	public static final String URL_GUESSPOKER_CHA = "/WEB-INF/bi/activity/act_guesspoker_cha.jsp";
	public static final String URL_GUESSPOKER_FEN = "/WEB-INF/bi/activity/act_guesspoker_fen.jsp";
	public static final String URL_GUESSPOKER_RANK = "/WEB-INF/bi/activity/act_guesspoker_rank.jsp";
	public static final String URL_KAIXINZHUANPAN = "/WEB-INF/bi/activity/act_kaixinzhuanpan.jsp";
	public static final String URL_KAIXINZHUANPAN_CHA = "/WEB-INF/bi/activity/act_kaixinzhuanpan_cha.jsp";
	public static final String URL_KAIXINZHUANPAN_FISH = "/WEB-INF/bi/activity/act_kaixinzhuanpan_fish.jsp";


	public static final String URL_FISH_NANGUA = "/WEB-INF/bi/activity/act_nangua.jsp";
	public static final String URL_NANGUA_FISH = "/WEB-INF/bi/activity/act_nangua_fish.jsp";
	public static final String URL_NANGUA_CHA_FISH = "/WEB-INF/bi/activity/act_nangua_cha.jsp";
	public static final String URL_FISH_DAFUWENG = "/WEB-INF/bi/activity/act_dafuweng.jsp";
	public static final String URL_DAFUWENG_FISH = "/WEB-INF/bi/activity/act_dafuweng_fish.jsp";
	public static final String URL_DAFUWENG_CHA_FISH = "/WEB-INF/bi/activity/act_dafuweng_cha.jsp";
	public static final String URL_FISH_ZADAN = "/WEB-INF/bi/activity/act_zadan.jsp";
	public static final String URL_ZADAN_FISH = "/WEB-INF/bi/activity/act_zadan_fish.jsp";
	public static final String URL_ZADAN_CHA_FISH = "/WEB-INF/bi/activity/act_zadan_cha.jsp";
	public static final String URL_FISH_QIQIU = "/WEB-INF/bi/activity/act_qiqiu.jsp";
	public static final String URL_QIQIU_FISH = "/WEB-INF/bi/activity/act_qiqiu_fish.jsp";
	public static final String URL_QIQIU_CHA_FISH = "/WEB-INF/bi/activity/act_qiqiu_cha.jsp";
	public static final String URL_FISH_SHENGDAN = "/WEB-INF/bi/activity/act_shengdan_fish.jsp";
	public static final String URL_SHENGDAN_FISH = "/WEB-INF/bi/activity/act_shengdan_fish_xiangqing.jsp";
	public static final String URL_SHENGDAN_CHA_FISH = "/WEB-INF/bi/activity/act_shengdan_cha.jsp";
	public static final String URL_FISH_HAIDIDUOBAO = "/WEB-INF/bi/activity/act_haididuobao.jsp";
	public static final String URL_HAIDIDUOBAO_FISH = "/WEB-INF/bi/activity/act_haididuobao_fish.jsp";
	public static final String URL_HAIDIDUOBAO_CHA_FISH = "/WEB-INF/bi/activity/act_haididuobao_cha.jsp";
	public static final String URL_FISH_QINGCAISHEN = "/WEB-INF/bi/activity/act_qingcaishen.jsp";
	public static final String URL_QINGCAISHEN_FISH = "/WEB-INF/bi/activity/act_qingcaishen_fish.jsp";
	public static final String URL_QINGCAISHEN_CHA_FISH = "/WEB-INF/bi/activity/act_qingcaishen_cha.jsp";

	public static final String URL_WANGPAI = "/WEB-INF/bi/activity/act_wangpai.jsp";
	public static final String URL_WANGPAI_DEZHOU = "/WEB-INF/bi/activity/act_wangpaiDZ.jsp";
	public static final String URL_WANGPAI_CHA_DEZHOU = "/WEB-INF/bi/activity/act_wangpai_cha.jsp";
	public static final String URL_WANMI = "/WEB-INF/bi/activity/act_wanmi.jsp";
	public static final String URL_WANMI_DEZHOU = "/WEB-INF/bi/activity/act_wanmiDZ.jsp";
	public static final String URL_WANMI_CHA_DEZHOU = "/WEB-INF/bi/activity/act_wanmi_cha.jsp";
	public static final String URL_YUEBING = "/WEB-INF/bi/activity/act_yuebing.jsp";
	public static final String URL_YUEBING_DEZHOU = "/WEB-INF/bi/activity/act_yuebingDZ.jsp";
	public static final String URL_YUEBING_CHA_DEZHOU = "/WEB-INF/bi/activity/act_yuebing_cha.jsp";
	public static final String URL_WANSHENG = "/WEB-INF/bi/activity/act_wansheng.jsp";
	public static final String URL_WANSHENG_DEZHOU = "/WEB-INF/bi/activity/act_wanshengDZ.jsp";
	public static final String URL_WANSHENG_CHA_DEZHOU = "/WEB-INF/bi/activity/act_wansheng_cha.jsp";

	public static final String URL_LUCKYCHOU = "/WEB-INF/bi/activity/act_LuckyChou.jsp";
	public static final String URL_LUCKYCHOU_CHA = "/WEB-INF/bi/activity/act_Lucky_chou_chaxun.jsp";
	public static final String URL_LUCKYCHOU_DEZHOU = "/WEB-INF/bi/activity/act_LuckyChou_dz.jsp";
	public static final String URL_LUCKYCHOU_RANK_DEZHOU = "/WEB-INF/bi/activity/act_LuckyChou_rank_dz.jsp";

	public static final String URL_YELLOW= "/WEB-INF/bi/yellow/yellow_gift.jsp";

	public static final String URL_PROPS= "/WEB-INF/bi/props/props.jsp";
	public static final String URL_PROPS_ZONGLIANG= "/WEB-INF/bi/props/props_zongliang.jsp";
	public static final String URL_PROPS_SEND= "/WEB-INF/bi/props/props_send.jsp";
	public static final String URL_PROPS_HUAFEI= "/WEB-INF/bi/props/props_huafei.jsp";
	public static final String URL_PROPS_1HUAFEI= "/WEB-INF/bi/props/props_1huafei.jsp";
	public static final String URL_PROPS_HUAFEIOUT= "/WEB-INF/bi/props/props_huafeiout.jsp";
	public static final String URL_PROPS_BUYRANK= "/WEB-INF/bi/props/props_user_buy.jsp";
	public static final String URL_PROPS_BAISHEN= "/WEB-INF/bi/props/props_baishen.jsp";
	public static final String URL_PROPS_BISHANG= "/WEB-INF/bi/props/props_bishang.jsp";
	public static final String URL_PROPS_MAIFEN= "/WEB-INF/bi/props/props_MaiFenBoss.jsp";



	public static void main( String [] args ) {
		String s = HttpRequest.sendGet( "http://app100632434.qzone.qzoneapp.com/managemobile", "input=item_findUserName&userId=" + "10005" );
		System.out.println( s );
	}
}
