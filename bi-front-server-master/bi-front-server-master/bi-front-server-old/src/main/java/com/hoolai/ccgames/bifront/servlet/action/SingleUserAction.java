package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.net.BiClient;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.SingleUserView;
import com.hoolai.centersdk.item.ItemManager;
import org.springframework.stereotype.Service;
import com.hoolai.ccgames.bi.protocol.Currency;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service( "singleUserAction" )
public class SingleUserAction extends BaseAction {

    private static List< String > chipMoveTableHead = Arrays.asList( "日期", "游戏ID",
            "现金桌下注", "现金桌获胜", "现金桌彩池", "现金桌抽水", "现金桌局数",
            "跑马下注", "跑马获胜", "跑马彩池",
            "时时彩下注", "时时彩获胜",
            "跑狗下注", "跑狗获胜", "跑狗彩池",
            "摩天轮下注", "摩天轮获胜", "摩天轮彩池",
            "水果机下注", "水果机获胜", "水果机彩池",
            "比倍下注", "比倍获胜", "比倍彩池",
            "时来运转下注", "时来运转获胜",
            "摇奖机下注", "摇奖机获胜" );
    private static List< String > chipMoveTableHead2 = Arrays.asList( "日期", "游戏ID",
            "斗地主获胜", "斗地主局数", "德州获胜", "德州局数", "血流获胜", "血流局数", "血战获胜", "血战局数", "二人麻将获胜", "二人麻将局数"
            ,"中发白闲家下注", "中发白闲家获胜", "中发白坐庄获胜",
            "中发白发红包", "中发白抢红包", "中发白彩池","捕鱼获胜","捕鱼彩池",
            "战队贡献兑换", "领队获得" );

    private static List< String > FishchipMoveTableHead1 = Arrays.asList( "日期", "游戏ID",
            "捕鱼消耗", "捕鱼获得", "捕鱼获胜", "捕鱼掉落道具", "活动消耗", "活动获得", "跑马下注", "跑马获取","跑马收入", "摩天轮下注", "摩天轮获得", "摩天轮彩池"
            ,"中发白闲家下注", "中发白闲家获胜", "中发白坐庄获胜",
            "中发白彩池", "总输赢" );
    private static List< String > FishchipMoveTableHead2 = Arrays.asList( "时间","大师场积分输赢","竞技场输赢", "竞技场次数", "竞技场最高分",
            "竞技场获得", "积分详情" );
    private static List< String > winLoseTableHead = Arrays.asList( "玩家ID", "筹码" );

    private static List< String > possibleIpTableHead = Arrays.asList( "IP", "登录过用户数", "最早用户ID" );

    private static List< String > userIpTableHead = Arrays.asList( "用户ID", "登录过的IP", "登陆过这些IP的用户ID" );

    private static List< String > payTableHead = Arrays.asList( "日期",
            "道具名称", "购买道具个数", "实际发放个数","实际金额" );

    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_SINGLE_USER ).forward( req, resp );
    }

    public void getChipMovePage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_SINGLE_CHIPMOVE ).forward( req, resp );
    }
    public void getFishChipMovePage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_SINGLE_FISH_CHIPMOVE ).forward( req, resp );
    }

    public void getEnemyPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_SINGLE_ENEMY ).forward( req, resp );
    }

    public void getIpPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_SINGLE_IP ).forward( req, resp );
    }

    public void getPayPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_SINGLE_PAY ).forward( req, resp );
    }

    private List< SingleUserView.ChipMove > getFishChipMoveData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        long userId = Long.parseLong( req.getParameter( "userId" ).trim() );
        return getDataList( req, resp, ( beg, end, c ) -> {
            SingleUserView.ChipMove val = new SingleUserView.ChipMove();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.userId = userId;
            val.fishBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.fishWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "  and user_id = " + userId + ";", c );
            val.dashijifen = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "  and user_id = " + userId + " and level = 601;", c )
                    -
                    ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                            " from t_fish_paiment " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            "   and user_id = " + userId + " and level = 601;", c );


            val.fishJackpot = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + " and gold > 0;", c );

            Map<Long, Long> fishGetMap = ServiceManager.getSqlService().queryMapLongLong("select item_id, sum(item_count) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'Y' and user_id ="+ userId +"   and item_count > 0 group by item_id;", c);
            Long yong_jindan = ServiceManager.getSqlService().queryLong("select sum(item_count) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'Y' and user_id ="+ userId +"  and item_id = 20017 and item_count < 0 ;", c);
            Long jieshou = ServiceManager.getSqlService().queryLong("select  sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and get_user_id ="+ userId +" and item_id = 20017 ;", c);
            Long zengsong = ServiceManager.getSqlService().queryLong("select sum(item_count) " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and send_user_id ="+ userId +" and item_id = 20017 ;", c);
            val.daoju = fishGetMap;
            val.daoju.put( -10000L ,yong_jindan );
            val.daoju.put( -10001L ,jieshou );
            val.daoju.put( -10002L ,zengsong );

            Map< Long , Long > xiaohaoMap  = ServiceManager.getSqlService().queryMapLongLong( "select cost_id,sum(cost_count) " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and cost_id in ( -4,10537 ) and cost_count != 0 and user_id in ("+ userId + ") group by cost_id;", c );
            val.huodongxiaohao = xiaohaoMap;

            List<String> xuyuanGetMap  = ServiceManager.getSqlService().queryListString( "select get_info " +
                    " from t_newactivity " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and user_id in ("+ userId + ");", c );

            Map< Long, Long > getMap = new HashMap<>();
            xuyuanGetMap.forEach( info -> mergeMap( splitMap( info, ";", ":" ), getMap ) );
            val.otherDaoju = getMap;

            val.mtlBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( bet_gold ), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.mtlWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( win_gold ), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.mtlJackpot = ServiceManager.getSqlService().queryLong( "select coalesce( sum( jackpot ), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.zfbXianBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.zfbXianWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.zfbZhuangWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips - escape_pump ), 0 ) " +
                    " from t_zfb_xiazhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c ) -
                    ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                            " from t_zfb_shangzhuang " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            "   and user_id = " + userId + " and is_use_card = 'N';", c ) -
                    ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                            " from t_zfb_send_expression " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            "   and user_id = " + userId + ";", c ) ;
            val.zfbJackpot = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );

//            val.BRDZBet = ServiceManager.getSqlService().queryLong( "select sum(bet + caijin_bet) from t_hundred_dezhou " +
//                    "where " + beg + " <= timestamp and timestamp < " + end + " and user_id = " + userId + ";", c );
//            List<String> zongchanchu  = ServiceManager.getSqlService().queryListString( "select win " +
//                    " from t_hundred_dezhou " +  " where " + beg + " <= timestamp and timestamp < " + end + "  and user_id = " + userId + ";", c );
//            Map< Long, Long > chanchuMap = new HashMap<>();
//            zongchanchu.forEach( info -> mergeMap( splitMap( info, ";", ":" ), chanchuMap ) );
//            val.BRDZWin = getLong(chanchuMap , 1) + getLong(chanchuMap , 2);

            val.pptBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_ppt_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and  user_id = " + userId + ";", c );
            val.pptWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_ppt_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and  user_id = " + userId + ";", c ) + ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_ppt_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and  user_id = " + userId + ";", c );

            val.jingjiWin = ServiceManager.getSqlService().queryLong("select sum(gold) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and level = 201 and  user_id = " + userId + ";", c )
                    -
                    ServiceManager.getSqlService().queryLong("select sum(gold) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and level = 201  and user_id = " + userId + ";", c );

            val.jingjiCount = ServiceManager.getSqlService().queryLong("select count(id)" +
                    " from t_fish_jingji " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and  user_id = " + userId + ";", c );

            val.max = ServiceManager.getSqlService().queryLong("select max(score)" +
                    " from t_fish_jingji " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and  user_id = " + userId + ";", c );

            Map<Long, Long> jifen = ServiceManager.getSqlService().queryMapLongLong("select id, score " +
                    " from t_fish_jingji " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and user_id ="+ userId +" group by id;", c);
            val.jingjiInfo = jifen ;

            List<String> jingjiget  = ServiceManager.getSqlService().queryListString( "select bouns " +
                    " from t_fish_mb_grants " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and type in (" + FishMBAction.jingji_jifen_grants + "," +  FishMBAction.jingji_day_rank_grants + "," + FishMBAction.jingji_week_rank_grants + ") and user_id in ("+ userId + ");", c );

            Map< Long, Long > jingjiGetMap = new HashMap<>();

            jingjiget.forEach( info -> mergeMap( splitMap( info, ";", ":" ), jingjiGetMap ) );

            val.jingjiGet = jingjiGetMap;

            return val;
        } );
    }
    long a = 0;
    public void queryFishChipMove( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< SingleUserView.ChipMove > data = getFishChipMoveData( req, resp );

        queryMultiList( req, resp,
                CommandList.GET_SINGLE,
                data, FishchipMoveTableHead1, (info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" + info.fishBet + "</td>" );
                    pw.write( "<td>" + info.fishWin + "</td>" );
                    pw.write( "<td>" + (info.fishWin - info.fishBet  )+ "</td>" );
                    pw.write( "<td>" );
                    info.daoju.forEach( ( itemId, itemCount ) -> {
                        if (itemId == -1 )
                        { pw.write("金币:" + itemCount + "<br>" );
                            a = a + itemCount;}
                        if (itemId == -10000 )
                        { pw.write("使用金蛋:" + itemCount + "<br>" );
                        return;}
                        if (itemId == -10001 )
                        { pw.write("接收金蛋:" + itemCount + "<br>" );
                        return;}
                        if (itemId == -10002 )
                        { pw.write("赠送金蛋:" + itemCount + "<br>" );
                        return;}
                        else
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" );
                    info.huodongxiaohao.forEach( ( itemId, itemCount ) -> {
                        if (itemId == -4 )
                        {
                            pw.write("钻石:" + itemCount + "<br>" );
                        }
                        else
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" );
                    info.otherDaoju.forEach( ( itemId, itemCount ) -> {
                        if (itemId == -1 )
                        {
                            pw.write("金币:" + itemCount + "<br>" );
                        }
                        else
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" + info.BRDZBet + "</td>" );
                    pw.write( "<td>" + info.BRDZWin + "</td>" );
                    pw.write( "<td>" + (info.BRDZWin - info.BRDZBet) + "</td>" );
                    pw.write( "<td>" + info.mtlBet + "</td>" );
                    pw.write( "<td>" + info.mtlWin + "</td>" );
                    pw.write( "<td>" + info.mtlJackpot + "</td>" );
                    pw.write( "<td>" + info.zfbXianBet + "</td>" );
                    pw.write( "<td>" + info.zfbXianWin + "</td>" );
                    pw.write( "<td>" + info.zfbZhuangWin + "</td>" );
                    pw.write( "<td>" + info.zfbJackpot + "</td>" );
                    pw.write( "<td>" + ( info.mtlWin - info.mtlBet + info.mtlJackpot + info.zfbXianWin - info.zfbXianBet + info.zfbJackpot + info.zfbZhuangWin + (info.fishWin - info.fishBet ) + (info.BRDZWin - info.BRDZBet)) + "</td>" );
                    a = 0 ;
                },data, FishchipMoveTableHead2, (info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.dashijifen + "</td>" );
                    pw.write( "<td>" + info.jingjiWin + "</td>" );
                    pw.write( "<td>" + info.jingjiCount + "</td>" );
                    pw.write( "<td>" + info.max + "</td>" );
                    pw.write( "<td>" );
                    info.jingjiGet.forEach( ( itemId, itemCount ) -> {
                        if (itemId == -1 )
                        { pw.write("金币:" + itemCount + "<br>" );
                            a = a + itemCount;}
                        else
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" );
                    info.jingjiInfo.forEach( ( itemId, itemCount ) -> {
                            pw.write( itemCount + "<br>" );
                    } );
                    pw.write( "</td>" );
                }, null );
    }

    public void downloadFishChipMove( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< SingleUserView.ChipMove > data = getFishChipMoveData( req, resp );

        downloadMultiList( req, resp,
                CommandList.GET_SINGLE,
                data, FishchipMoveTableHead1, (info, pw ) -> {
                    pw.write(  info.begin + "," );
                    pw.write(  info.userId + "," );
                    pw.write(  info.fishBet + "," );
                    pw.write(  info.fishWin + "," );
                    pw.write(  (info.fishBet - info.fishWin  )+ "," );
                    pw.write( "<td>" );
                    info.daoju.forEach( ( itemId, itemCount ) -> {
                        if (itemId == -1 )
                        { pw.write("金币:" + itemCount + "<br>" );a = a + itemCount;}
                        else
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                    } );
                    pw.write(  info.xuyuanzuanshi + "," );
                    info.otherDaoju.forEach( ( itemId, itemCount ) -> {
                        if (itemId == -1 )
                        {
                            a = a + itemCount;
                            pw.write("金币:" + itemCount + "<br>" );
                        }
                        else
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                    } );
                    pw.write(  info.BRDZBet + "," );
                    pw.write(  info.BRDZWin + "," );
                    pw.write( (info.BRDZWin - info.BRDZBet) + "," );
                    pw.write(  info.mtlBet + "," );
                    pw.write(  info.mtlWin + "," );
                    pw.write(  info.mtlJackpot + "," );
                    pw.write(  info.zfbXianBet + "," );
                    pw.write(  info.zfbXianWin + "," );
                    pw.write(  info.zfbZhuangWin + "," );
                    pw.write(  info.zfbJackpot + "," );
                    pw.write(  ( info.mtlWin - info.mtlBet + info.mtlJackpot + info.zfbXianWin - info.zfbXianBet + info.zfbJackpot + info.zfbZhuangWin ) + "," );
                    a = 0;
                } ,data, FishchipMoveTableHead2, (info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.jingjiWin + "," );
                    pw.write( info.jingjiCount + "," );
                    pw.write(  info.max + "," );
                    info.jingjiGet.forEach( ( itemId, itemCount ) -> {
                        pw.write( itemCount + "," );
                    } );
                    info.jingjiInfo.forEach( ( itemId, itemCount ) -> {
                        pw.write( itemCount + "," );
                    } );
                });
    }

    private List< SingleUserView.ChipMove > getChipMoveData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        long userId = Long.parseLong( req.getParameter( "userId" ).trim() );
        return getDataList( req, resp, ( beg, end, c ) -> {
            SingleUserView.ChipMove val = new SingleUserView.ChipMove();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.userId = userId;
            val.fishBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.fishJackpot = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + " and gold > 0;", c );
            val.fishWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "  and user_id = " + userId + ";", c ) - val.fishBet;
            val.goldDeskBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_gdesk_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.goldDeskWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_gdesk_win_detail " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and winner_id = " + userId + ";", c ) -
                    ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                            " from t_gdesk_win_detail " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            "   and loser_id = " + userId + ";", c );
            val.goldDeskPump = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_gdesk_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.goldDeskCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_gdesk_play " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.goldDeskJackpot = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_gdesk_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.pptBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_ppt_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.pptWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_ppt_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.pptJackpot = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_ppt_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.sscBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_pptssc_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.sscWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_pptssc_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.dogBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_dogsport_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.dogWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_dogsport_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.dogJackpot = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_dogsport_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.mtlBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( bet_gold ), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.mtlWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( win_gold ), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.mtlJackpot = ServiceManager.getSqlService().queryLong( "select coalesce( sum( jackpot ), 0 ) " +
                    " from t_mtl2_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.fruitBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_fruit_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.fruitWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_fruit_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.fruitJackpot = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_fruit_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.caibeiBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_caibei_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.caibeiWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_caibei_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.caibeiItem = ServiceManager.getSqlService().queryLong( "select coalesce( sum( total_worth ), 0 ) " +
                    " from t_caibei_reward " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.timeluckBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_timeluck_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.timeluckWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_timeluck_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.yylBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_yyl_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.yylWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_yyl_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.zfbXianBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_bet " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.zfbXianWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.zfbRedSend = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_free_reward " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and banker_id = " + userId + " and is_free = 'N';", c );
            val.zfbZhuangWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips - escape_pump ), 0 ) " +
                    " from t_zfb_xiazhuang " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c ) -
                    ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                            " from t_zfb_shangzhuang " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            "   and user_id = " + userId + " and is_use_card = 'N';", c ) -
                    ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                            " from t_zfb_send_expression " +
                            " where " + beg + " <= timestamp and timestamp < " + end +
                            "   and user_id = " + userId + ";", c ) - val.zfbRedSend;
            val.zfbRedRecv = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_get_red " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.zfbJackpot = ServiceManager.getSqlService().queryLong( "select coalesce( sum( chips ), 0 ) " +
                    " from t_zfb_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.ddzWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_ddz_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.ddzCount = ServiceManager.getSqlService().queryLong( "select count( distinct inning_id ) " +
                    " from t_ddz_rake " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            val.dezhouWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + " and type=6;", c );
            val.dezhouCount = ServiceManager.getSqlService().queryLong( "select count( distinct inning_id ) " +
                    " from t_mjdr_play " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + " and type=6;", c );
            val.xueliuWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + " and type in ( 2,4 );", c );
            val.xueliuCount = ServiceManager.getSqlService().queryLong( "select count( distinct inning_id ) " +
                    " from t_mjdr_play " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + " and type in ( 2,4 );", c );
            val.xuezhanWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + " and type in ( 1,3 );", c );
            val.xuezhanCount = ServiceManager.getSqlService().queryLong( "select count( distinct inning_id ) " +
                    " from t_mjdr_play " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + " and type in ( 1,3 );", c );
            val.erWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_mjdr_win " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + " and type=5;", c );
            val.erCount = ServiceManager.getSqlService().queryLong( "select count( distinct inning_id ) " +
                    " from t_mjdr_play " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + " and type=5;", c );

            return val;
        } );
    }

    public void queryChipMove( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< SingleUserView.ChipMove > data = getChipMoveData( req, resp );

        queryMultiList( req, resp,
                CommandList.GET_SINGLE,
                data, chipMoveTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" + info.goldDeskBet + "</td>" );
                    pw.write( "<td>" + info.goldDeskWin + "</td>" );
                    pw.write( "<td>" + info.goldDeskJackpot + "</td>" );
                    pw.write( "<td>" + info.goldDeskPump + "</td>" );
                    pw.write( "<td>" + info.goldDeskCount + "</td>" );
                    pw.write( "<td>" + info.pptBet + "</td>" );
                    pw.write( "<td>" + info.pptWin + "</td>" );
                    pw.write( "<td>" + info.pptJackpot + "</td>" );
                    pw.write( "<td>" + info.sscBet + "</td>" );
                    pw.write( "<td>" + info.sscWin + "</td>" );
                    pw.write( "<td>" + info.dogBet + "</td>" );
                    pw.write( "<td>" + info.dogWin + "</td>" );
                    pw.write( "<td>" + info.dogJackpot + "</td>" );
                    pw.write( "<td>" + info.mtlBet + "</td>" );
                    pw.write( "<td>" + info.mtlWin + "</td>" );
                    pw.write( "<td>" + info.mtlJackpot + "</td>" );
                    pw.write( "<td>" + info.fruitBet + "</td>" );
                    pw.write( "<td>" + info.fruitWin + "</td>" );
                    pw.write( "<td>" + info.fruitJackpot + "</td>" );
                    pw.write( "<td>" + info.caibeiBet + "</td>" );
                    pw.write( "<td>" + info.caibeiWin + "</td>" );
                    pw.write( "<td>" + info.caibeiItem + "</td>" );
                    pw.write( "<td>" + info.timeluckBet + "</td>" );
                    pw.write( "<td>" + info.timeluckWin + "</td>" );
                    pw.write( "<td>" + info.yylBet + "</td>" );
                    pw.write( "<td>" + info.yylWin + "</td>" );
                }, data, chipMoveTableHead2, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" + info.ddzWin + "</td>" );
                    pw.write( "<td>" + info.ddzCount + "</td>" );
                    pw.write( "<td>" + info.dezhouWin + "</td>" );
                    pw.write( "<td>" + info.dezhouCount + "</td>" );
                    pw.write( "<td>" + info.xueliuWin + "</td>" );
                    pw.write( "<td>" + info.xueliuCount + "</td>" );
                    pw.write( "<td>" + info.xuezhanWin + "</td>" );
                    pw.write( "<td>" + info.xuezhanCount + "</td>" );
                    pw.write( "<td>" + info.erWin + "</td>" );
                    pw.write( "<td>" + info.erCount + "</td>" );
                    pw.write( "<td>" + info.zfbXianBet + "</td>" );
                    pw.write( "<td>" + info.zfbXianWin + "</td>" );
                    pw.write( "<td>" + info.zfbZhuangWin + "</td>" );
                    pw.write( "<td>" + info.zfbRedSend + "</td>" );
                    pw.write( "<td>" + info.zfbRedRecv + "</td>" );
                    pw.write( "<td>" + info.zfbJackpot + "</td>" );
                    pw.write( "<td>" + info.fishWin + "</td>" );
                    pw.write( "<td>" + info.fishJackpot + "</td>" );
                    pw.write( "<td>" + info.teamContributeExchange + "</td>" );
                    pw.write( "<td>" + info.teamLeaderGot + "</td>" );
                }, null );
    }

    public void downloadChipMove( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< SingleUserView.ChipMove > data = getChipMoveData( req, resp );

        downloadMultiList( req, resp,
                CommandList.GET_SINGLE,
                data, chipMoveTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.userId + "," );
                    pw.write( info.goldDeskBet + "," );
                    pw.write( info.goldDeskWin + "," );
                    pw.write( info.goldDeskJackpot + "," );
                    pw.write( info.goldDeskPump + "," );
                    pw.write( info.goldDeskCount + "," );
                    pw.write( info.pptBet + "," );
                    pw.write( info.pptWin + "," );
                    pw.write( info.pptJackpot + "," );
                    pw.write( info.sscBet + "," );
                    pw.write( info.sscWin + "," );
                    pw.write( info.dogBet + "," );
                    pw.write( info.dogWin + "," );
                    pw.write( info.dogJackpot + "," );
                    pw.write( info.mtlBet + "," );
                    pw.write( info.mtlWin + "," );
                    pw.write( info.mtlJackpot + "," );
                    pw.write( info.fruitBet + "," );
                    pw.write( info.fruitWin + "," );
                    pw.write( info.fruitJackpot + "," );
                    pw.write( info.caibeiBet + "," );
                    pw.write( info.caibeiWin + "," );
                    pw.write( info.caibeiItem + "," );
                    pw.write( info.timeluckBet + "," );
                    pw.write( info.timeluckWin + "," );
                    pw.write( info.yylBet + "," );
                    pw.write( info.yylWin + "," );
                }, data, chipMoveTableHead2, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.userId + "," );
                    pw.write( info.ddzWin + "," );
                    pw.write( info.ddzCount + "," );
                    pw.write( info.dezhouWin + "," );
                    pw.write( info.dezhouCount + "," );
                    pw.write( info.xueliuWin + "," );
                    pw.write( info.xueliuCount + "," );
                    pw.write( info.xuezhanWin + "," );
                    pw.write( info.xuezhanCount + "," );
                    pw.write( info.erWin + "," );
                    pw.write( info.erCount + "," );
                    pw.write( info.zfbXianBet + "," );
                    pw.write( info.zfbXianWin + "," );
                    pw.write( info.zfbZhuangWin + "," );
                    pw.write( info.zfbRedSend + "," );
                    pw.write( info.zfbRedRecv + "," );
                    pw.write( info.zfbJackpot + "," );
                    pw.write( info.teamContributeExchange + "," );
                    pw.write( info.teamLeaderGot + "," );
                } );
    }


    private List< List< SingleUserView.UserIdGold > > getEnemyData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        long userId = Long.parseLong( req.getParameter( "userId" ).trim() );

        return getData( req, resp, ( beg, end, c ) -> {
            List< SingleUserView.UserIdGold > list = new LinkedList<>();
            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select loser_id, sum( chips ) " +
                    " from t_gdesk_win_detail " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and winner_id = " + userId +
                    " group by loser_id;", c );
            Map< Long, Long > loseMap = ServiceManager.getSqlService().queryMapLongLong( "select winner_id, sum( chips ) " +
                    " from t_gdesk_win_detail " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and loser_id = " + userId +
                    " group by winner_id;", c );

            List< SingleUserView.UserIdGold > winList = new ArrayList<>();
            List< SingleUserView.UserIdGold > loseList = new ArrayList<>();
            winMap.forEach( ( k, v ) -> winList.add( new SingleUserView.UserIdGold( k, v ) ) );
            loseMap.forEach( ( k, v ) -> loseList.add( new SingleUserView.UserIdGold( k, v ) ) );

            Collections.sort( winList, ( o1, o2 ) -> ( o1.gold > o2.gold ? -1 : 1 ) );
            Collections.sort( loseList, ( o1, o2 ) -> ( o1.gold < o2.gold ? -1 : 1 ) );

            return Arrays.asList( winList, loseList );
        } );
    }

    public void queryEnemy( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_SINGLE, getEnemyData( req, resp ),
                winLoseTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                }, null );
    }

    public void downloadEnemy( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_SINGLE, getEnemyData( req, resp ),
                winLoseTableHead, ( info, pw ) -> {
                    pw.write( info.userId + "," );
                    pw.write( info.gold + "," );
                } );
    }

    private SingleUserView.UserIps getUserIpData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        long userId = Long.parseLong( req.getParameter( "userId" ).trim() );
        SingleUserView.UserIps val = new SingleUserView.UserIps();
        val.userId = userId;
        BiClient c = getBiClient( req.getSession() );
        List< String > ipList = ServiceManager.getSqlService().queryListString( "select distinct ip " +
                " from t_dau " +
                " where user_id = " + userId + ";", c );
        StringBuilder b = new StringBuilder( "(" );
        ipList.forEach( ip -> {
            val.ips.add( ip );
            b.append( "'" ).append( ip ).append( "'," );
        } );
        String ipStr = b.toString().replaceFirst( ",$", ")" );
        val.ids = ServiceManager.getSqlService().queryListLong( "select distinct user_id " +
                " from t_dau " +
                " where ip in " + ipStr + ";", c );
        return val;
    }

    public void queryUserIp( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryData( req, resp,
                CommandList.GET_SINGLE, getUserIpData( req, resp ),
                userIpTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td>" );
                    info.ips.forEach( ip -> pw.write( ip + "<br>" ) );
                    pw.write( "</td>" );
                    pw.write( "<td>" );
                    info.ids.forEach( id -> pw.write( id + "<br>" ) );
                    pw.write( "</td>" );
                }, null );
    }

    public void downloadUserIp( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadData( req, resp,
                CommandList.GET_SINGLE, getUserIpData( req, resp ),
                userIpTableHead, ( info, pw ) -> {
                    int rows = Math.max( 1, Math.max( info.ips.size(), info.ids.size() ) );
                    for( int i = 0; i < rows; ++i ) {
                        pw.write( i == 0 ? info.userId + "," : "," );
                        pw.write( i < info.ips.size() ? info.ips.get( i ) + "," : "," );
                        pw.write( i < info.ids.size() ? info.ids.get( i ) + "," : ",");
                        pw.write( "\n" );
                    }
                } );
    }

    private List< SingleUserView.IpUserCount > getPossibleIpData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        long limit = Long.parseLong( req.getParameter( "limit" ).trim() );
        return getData( req, resp, ( beg, end, c ) -> {
            List< SingleUserView.IpUserCount > list = new LinkedList<>();
            Map< Long, String > ipMap = ServiceManager.getSqlService().queryMapLongString( "select u, c, ip " +
                    " from ( select ip, count( distinct user_id ) as c, min( user_id ) as u " +
                    "        from t_dau " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "       group by ip ) as dau_tmp " +
                    " where dau_tmp.c > " + limit +
                    " order by c desc;", c );
            ipMap.forEach( ( k, v ) -> {
                SingleUserView.IpUserCount val = new SingleUserView.IpUserCount();
                val.userId = k;
                String[] arr = v.split( "\n" );
                val.userCount = Long.parseLong( arr[0] );
                val.ip = arr[1];
                list.add( val );
            } );
            return list;
        } );
    }

    public void queryPossibleIp( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_SINGLE, getPossibleIpData( req, resp ),
                possibleIpTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.ip + "</td>" );
                    pw.write( "<td>" + info.userCount + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                } );
    }

    public void downloadPossibleIp( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_SINGLE, getPossibleIpData( req, resp ),
                possibleIpTableHead, ( info, pw ) -> {
                    pw.write( info.ip + "," );
                    pw.write( info.userCount + "," );
                    pw.write( info.userId + "," );
                } );
    }

    private List< SingleUserView.Pay > getPayData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        long userId = Long.parseLong( req.getParameter( "userId" ).trim() );
        return getData( req, resp, ( beg, end, c ) -> {
            List< SingleUserView.Pay > list = new LinkedList<>();
            Map< Long, String > payMap = ServiceManager.getSqlService().queryMapLongString( "select timestamp, item_id, item_buy_count, item_count, money2, money3" +
                    " from t_pay " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_id = " + userId + ";", c );
            payMap.forEach( ( k, v ) -> {
                SingleUserView.Pay val = new SingleUserView.Pay();
                val.time = TimeUtil.ymdhmsFormat().format( k );
                String[] arr = v.split( "\n" );
                val.itemId = Long.parseLong( arr[0] );
                val.itemName = ItemManager.getInstance().getItemName(Integer.parseInt(arr[0]));
                val.itemBuyCount = Long.parseLong( arr[1] );
                val.itemCount = Long.parseLong( arr[2] );
                val.itemPay = Currency.format(Long.parseLong( arr[3] ) + Long.parseLong( arr[4] ));


                list.add( val );
            } );
            return list;
        } );
    }

    public void queryPay( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_SINGLE, getPayData( req, resp ),
                payTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.time + "</td>" );
                    pw.write( "<td>" + info.itemName + "</td>" );
                    pw.write( "<td>" + info.itemBuyCount + "</td>" );
                    pw.write( "<td>" + info.itemCount + "</td>" );
                    pw.write( "<td>" + info.itemPay + "</td>" );
                } );
    }

    public void downloadPay( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_SINGLE, getPayData( req, resp ),
                payTableHead, ( info, pw ) -> {
                    pw.write( info.time + "," );
                    pw.write( info.itemName + "," );
                    pw.write( info.itemBuyCount + "," );
                    pw.write( info.itemCount + "," );
                    pw.write( info.itemPay + "," );
                } );
    }

}