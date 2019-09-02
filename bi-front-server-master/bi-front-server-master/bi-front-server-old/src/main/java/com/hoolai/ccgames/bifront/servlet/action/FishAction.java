package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.FishView;
import com.hoolai.ccgames.bifront.vo.baseView;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service( "fishAction" )
public class FishAction extends BaseAction {

    private static List< String > basicTableHead = Arrays.asList( "日期","总人数", "总投注","总返奖","幸运彩池支出","彩池支出","新手保护支出","总抽水");
    private static List< String > rankTableHead = Arrays.asList( "用户id", "用户昵称","用户打炮","用户获取","彩池获取","幸运彩池获取","用户净收入");
    private static List< String > JichuTableHead = Arrays.asList( "日期", "初级人数", "初级投注","初级返奖", "初级抽水", "中级人数", "中级投注",
            "中级返奖","中级抽水", "高级人数", "高级投注","高级返奖", "高级抽水");
    private static List< String > jackportTableHead = Arrays.asList(  "日期", "彩池中奖人数", "彩池中奖次数", "爆彩总额");
    private static List< String > ItemTableHead = Arrays.asList(  "日期", "加速产出","冰冻产出","分裂产出","锁定产出","加速使用",
            "冰冻使用","分裂使用","锁定使用");
    private static List< String > LuckJackTableHead = Arrays.asList(  "日期","场次","小奖人数","小奖次数","小奖支出","大奖人数","大奖次数","大奖支出","总支出");

    private static List< String > OnlineTableHead = Arrays.asList(  "日期", "场次","1分钟","2分钟","3分钟","5分钟","7分钟",
            "10分钟","15分钟+");



    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH ).forward( req, resp );
    }

    public void getBasicPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_BASIC ).forward( req, resp );
    }
    public void getItemPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_ITEM ).forward( req, resp );
    }
    public void getFishOnlinePage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_ONLINE ).forward( req, resp );
    }

    public void getRankPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_RANK ).forward( req, resp );
    }
    public void getLuckyJackPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_LUCKYJACK ).forward( req, resp );
    }
    public void getHuizongPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FISH_HUIZONG ).forward( req, resp );
    }

    private List< FishView.LuckyJack > getLuckyJackData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.LuckyJack val = new FishView.LuckyJack();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            // 小幸运
            {
                Map<Long, Long> bigLuckyCountMap = ServiceManager.getSqlService().queryMapLongLong("select level, count(id) " +
                        " from t_fish_paiment " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 3  and is_mobile = 'N' group by level;", c);
                val.chuCount1 = getLong(bigLuckyCountMap, 1);
                val.zhongCount1 = getLong(bigLuckyCountMap, 2);
                val.gaoCount1 = getLong(bigLuckyCountMap, 3);

                Map<Long, Long> bigLuckyPeopleMap = ServiceManager.getSqlService().queryMapLongLong("select level, count(distinct user_id) " +
                        " from t_fish_paiment " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 3   and is_mobile = 'N' group by level;", c);
                val.chuPeople1 = getLong(bigLuckyPeopleMap, 1);
                val.zhongPeople1 = getLong(bigLuckyPeopleMap, 2);
                val.gaoPeople1 = getLong(bigLuckyPeopleMap, 3);

                Map<Long, Long> bigLuckyGetMoneyMap = ServiceManager.getSqlService().queryMapLongLong("select level, sum( gold ) " +
                        " from t_fish_get " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 3   and is_mobile = 'N' group by level;", c);
                Map<Long, Long> bigLuckyPayMoneyMap = ServiceManager.getSqlService().queryMapLongLong("select level, sum( gold ) " +
                        " from t_fish_paiment " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 3   and is_mobile = 'N' group by level;", c);
                Map<Long, Long> bigLuckyJackpotMoneyMap = ServiceManager.getSqlService().queryMapLongLong("select level, sum( gold ) " +
                        " from t_fish_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 3   and is_mobile = 'N' and gold < 0 group by level;", c);
                val.chuGold1 = getLong(bigLuckyGetMoneyMap, 1) - getLong(bigLuckyJackpotMoneyMap, 1) - getLong(bigLuckyPayMoneyMap, 1);
                val.zhongGold1 = getLong(bigLuckyGetMoneyMap, 2) - getLong(bigLuckyJackpotMoneyMap, 2) - getLong(bigLuckyPayMoneyMap, 2);
                val.gaoGold1 = getLong(bigLuckyGetMoneyMap, 3) - getLong(bigLuckyJackpotMoneyMap, 3) - getLong(bigLuckyPayMoneyMap, 3);

            }

            // 大幸运
            {
                Map<Long, Long> bigLuckyCountMap = ServiceManager.getSqlService().queryMapLongLong("select level, count(id) " +
                        " from t_fish_paiment " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 2   and is_mobile = 'N' group by level;", c);
                val.chuCount2 = getLong(bigLuckyCountMap, 1);
                val.zhongCount2 = getLong(bigLuckyCountMap, 2);
                val.gaoCount2 = getLong(bigLuckyCountMap, 3);

                Map<Long, Long> bigLuckyPeopleMap = ServiceManager.getSqlService().queryMapLongLong("select level, count(distinct user_id) " +
                        " from t_fish_paiment " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 2   and is_mobile = 'N' group by level;", c);
                val.chuPeople2 = getLong(bigLuckyPeopleMap, 1);
                val.zhongPeople2 = getLong(bigLuckyPeopleMap, 2);
                val.gaoPeople2 = getLong(bigLuckyPeopleMap, 3);

                Map<Long, Long> bigLuckyGetMoneyMap = ServiceManager.getSqlService().queryMapLongLong("select level, sum( gold ) " +
                        " from t_fish_get " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 2   and is_mobile = 'N' group by level;", c);
                Map<Long, Long> bigLuckyPayMoneyMap = ServiceManager.getSqlService().queryMapLongLong("select level, sum( gold ) " +
                        " from t_fish_paiment " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 2   and is_mobile = 'N' group by level;", c);
                Map<Long, Long> bigLuckyJackpotMoneyMap = ServiceManager.getSqlService().queryMapLongLong("select level, sum( gold ) " +
                        " from t_fish_jackpot " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 2 and gold < 0   and is_mobile = 'N' group by level;", c);
                val.chuGold2 = getLong(bigLuckyGetMoneyMap, 1) - getLong(bigLuckyJackpotMoneyMap, 1) - getLong(bigLuckyPayMoneyMap, 1);
                val.zhongGold2 = getLong(bigLuckyGetMoneyMap, 2) - getLong(bigLuckyJackpotMoneyMap, 2) - getLong(bigLuckyPayMoneyMap, 2);
                val.gaoGold2 = getLong(bigLuckyGetMoneyMap, 3) - getLong(bigLuckyJackpotMoneyMap, 3) - getLong(bigLuckyPayMoneyMap, 3);

            }

            return val;
        } );
    }

    public void queryLuckyJack( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH, getLuckyJackData( req, resp ),
                LuckJackTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + "初级" + "</td>" );
                    pw.write( "<td>" + info.chuPeople1 + "</td>" );
                    pw.write( "<td>" + info.chuCount1 + "</td>" );
                    pw.write( "<td>" + info.chuGold1 + "</td>" );
                    pw.write( "<td>" + info.chuPeople2 + "</td>" );
                    pw.write( "<td>" + info.chuCount2 + "</td>" );
                    pw.write( "<td>" + info.chuGold2 + "</td>" );
                    pw.write( "<td>" + (info.chuGold1 + info.chuGold2)+ "</td>" );
                    pw.write( "</tr>" );
                    pw.write( "<tr>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + "中级" + "</td>" );
                    pw.write( "<td>" + info.zhongPeople1 + "</td>" );
                    pw.write( "<td>" + info.zhongCount1 + "</td>" );
                    pw.write( "<td>" + info.zhongGold1 + "</td>" );
                    pw.write( "<td>" + info.zhongPeople2 + "</td>" );
                    pw.write( "<td>" + info.zhongCount2 + "</td>" );
                    pw.write( "<td>" + info.zhongGold2 + "</td>" );
                    pw.write( "<td>" + (info.zhongGold1 + info.zhongGold2)+ "</td>" );
                    pw.write( "</tr>" );
                    pw.write( "<tr>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + "高级" + "</td>" );
                    pw.write( "<td>" + info.gaoPeople1 + "</td>" );
                    pw.write( "<td>" + info.gaoCount1 + "</td>" );
                    pw.write( "<td>" + info.gaoGold1 + "</td>" );
                    pw.write( "<td>" + info.gaoPeople2 + "</td>" );
                    pw.write( "<td>" + info.gaoCount2 + "</td>" );
                    pw.write( "<td>" + info.gaoGold2 + "</td>" );
                    pw.write( "<td>" + (info.gaoGold1 + info.gaoGold2)+ "</td>" );
                },null );
    }

    public void downloadLuckyJack( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH, getLuckyJackData( req, resp ),
                LuckJackTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( "初级" + "," );
                    pw.write( info.chuPeople1 + "," );
                    pw.write( info.chuCount1 + "," );
                    pw.write( info.chuGold1 + "," );
                    pw.write( info.chuPeople2 + "," );
                    pw.write( info.chuCount2 + "," );
                    pw.write( info.chuGold2 + "," );
                    pw.write( (info.chuGold1 + info.chuGold2) + "," );
                    pw.write( "</tr>" );
                    pw.write( "<tr>" );
                    pw.write( "中级" + "," );
                    pw.write( info.zhongPeople1 + "," );
                    pw.write( info.zhongCount1 + "," );
                    pw.write( info.zhongGold1 + "," );
                    pw.write( info.zhongPeople2 + "," );
                    pw.write( info.zhongCount2 + "," );
                    pw.write( info.zhongGold2 + "," );
                    pw.write( (info.zhongGold1 + info.zhongGold2) + "," );
                    pw.write( "</tr>" );
                    pw.write( "<tr>" );
                    pw.write( "高级" + "," );
                    pw.write( info.gaoPeople1 + "," );
                    pw.write( info.gaoCount1 + "," );
                    pw.write( info.gaoGold1 + "," );
                    pw.write( info.gaoPeople2 + "," );
                    pw.write( info.gaoCount2 + "," );
                    pw.write( info.gaoGold2 + "," );
                    pw.write( (info.gaoGold1 + info.gaoGold2) + "," );
                } );
    }



    private List< baseView.Online > getFishOnlineData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.Online val = new baseView.Online();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.chu1  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 1 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 0 and  a.sum<= 60000;", c );
            val.chu2  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 1 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000  and  a.sum<= 60000 * 2;", c );
            val.chu3  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 1 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 2 and  a.sum<= 60000 * 5;", c );
            val.chu5  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 1 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 5 and  a.sum<= 60000 * 7;", c );
            val.chu7  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 1 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 7 and  a.sum<= 60000 * 10;", c );
            val.chu10 = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 1 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 10 and  a.sum<= 60000 * 15;", c );
            val.chu15 = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 1 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 15 ;", c );

            val.zhong1  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 2 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 0 and  a.sum<= 60000;", c );
            val.zhong2  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 2 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000  and  a.sum<= 60000 * 2;", c );
            val.zhong3  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 2 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 2 and a.sum <= 60000 * 5;", c );
            val.zhong5  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 2 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 5 and a.sum <= 60000 * 7 ;", c );
            val.zhong7  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 2 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 7 and a.sum <= 60000 * 10;", c );
            val.zhong10 = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 2 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 10 and a.sum <= 60000 * 15;", c );
            val.zhong15 = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 2 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 15;", c );

            val.gao1  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 3 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 0 and  a.sum<= 60000;", c );
            val.gao2  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 3 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000  and  a.sum<= 60000 * 2;", c );
            val.gao3  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 3 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 2 and  a.sum<= 60000 * 5;", c );
            val.gao5  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 3 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 5 and  a.sum<= 60000 * 7;", c );
            val.gao7  = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 3 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 7 and a.sum <= 60000 * 10;", c );
            val.gao10 = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 3 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 10 and  a.sum<= 60000 * 15;", c );
            val.gao15 = ServiceManager.getSqlService().queryLong( "select count(a.user_id) from (select user_id , sum(millis) as sum from t_online where " + beg + " <= timestamp and timestamp < " + end + " and game_id = 1002 and level = 3 and kind = 1   and is_mobile = 'N' group by user_id) as a  where a.sum > 60000 * 15;", c );

            return val;

        } );
    }

    public void queryFishOnline( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH, getFishOnlineData( req, resp ),
                OnlineTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + "初级" + "</td>" );
                    pw.write( "<td>" + info.chu1 + "</td>" );
                    pw.write( "<td>" + info.chu2 + "</td>" );
                    pw.write( "<td>" + info.chu3 + "</td>" );
                    pw.write( "<td>" + info.chu5 + "</td>" );
                    pw.write( "<td>" + info.chu7 + "</td>" );
                    pw.write( "<td>" + info.chu10 + "</td>" );
                    pw.write( "<td>" + info.chu15 + "</td>" );
                    pw.write( "</tr>" );
                    pw.write( "<tr>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + "中级" + "</td>" );
                    pw.write( "<td>" + info.zhong1 + "</td>" );
                    pw.write( "<td>" + info.zhong2 + "</td>" );
                    pw.write( "<td>" + info.zhong3 + "</td>" );
                    pw.write( "<td>" + info.zhong5 + "</td>" );
                    pw.write( "<td>" + info.zhong7 + "</td>" );
                    pw.write( "<td>" + info.zhong10 + "</td>" );
                    pw.write( "<td>" + info.zhong15 + "</td>" );
                    pw.write( "</tr>" );
                    pw.write( "<tr>" );
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + "高级" + "</td>" );
                    pw.write( "<td>" + info.gao1 + "</td>" );
                    pw.write( "<td>" + info.gao2 + "</td>" );
                    pw.write( "<td>" + info.gao3 + "</td>" );
                    pw.write( "<td>" + info.gao5 + "</td>" );
                    pw.write( "<td>" + info.gao7 + "</td>" );
                    pw.write( "<td>" + info.gao10 + "</td>" );
                    pw.write( "<td>" + info.gao15 + "</td>" );
                },null );
    }

    public void downloadFishOnline( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH, getFishOnlineData( req, resp ),
                OnlineTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( "初级" + "," );
                    pw.write( info.chu1 + "," );
                    pw.write( info.chu2 + "," );
                    pw.write( info.chu3 + "," );
                    pw.write( info.chu5 + "," );
                    pw.write( info.chu7 + "," );
                    pw.write( info.chu10 + "," );
                    pw.write( info.chu15 + "," );
                    pw.write( "</tr>" );
                    pw.write( "<tr>" );
                    pw.write( "中级" + "," );
                    pw.write( info.zhong1 + "," );
                    pw.write( info.zhong2 + "," );
                    pw.write( info.zhong3 + "," );
                    pw.write( info.zhong5 + "," );
                    pw.write( info.zhong7 + "," );
                    pw.write( info.zhong10 + "," );
                    pw.write( info.zhong15 + "," );
                    pw.write( "</tr>" );
                    pw.write( "<tr>" );
                    pw.write( "高级" + "," );
                    pw.write( info.gao1 + "," );
                    pw.write( info.gao2 + "," );
                    pw.write( info.gao3 + "," );
                    pw.write( info.gao5 + "," );
                    pw.write( info.gao7 + "," );
                    pw.write( info.gao10 + "," );
                    pw.write( info.gao15 + "," );
                } );
    }

    private List< baseView.Item > getItemData(HttpServletRequest req, HttpServletResponse resp , long level )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.Item val = new baseView.Item();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.fenlie = ServiceManager.getSqlService().queryLong( "select coalesce( sum( abs(item_count) ), 0 ) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and item_count < 0 and item_id = 20012 and level = "+ level +"  and is_mobile = 'N' ;", c );
            val.fenlieOut = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and item_count > 0 and item_id = 20012 and level = "+ level +"  and is_mobile = 'N' ;", c );
            val.bingdong = ServiceManager.getSqlService().queryLong( "select coalesce( sum( abs(item_count) ), 0 )" +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and item_count < 0 and item_id = 20011 and level = "+ level +";  and is_mobile = 'N' ", c );
            val.bingdongOut = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and item_count > 0 and item_id = 20011 and level = "+ level +"  and is_mobile = 'N' ;", c );
            val.suoding = ServiceManager.getSqlService().queryLong( "select coalesce( sum( abs(item_count) ), 0 ) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and item_count < 0 and item_id = 20010 and level = "+ level +"  and is_mobile = 'N' ;", c );
            val.suodingOut = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and item_count > 0 and item_id = 20010 and level = "+ level +"  and is_mobile = 'N' ;", c );
            val.jisu = ServiceManager.getSqlService().queryLong( "select coalesce( sum( abs(item_count) ), 0 ) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and item_count < 0 and item_id = 20013 and level = "+ level +"  and is_mobile = 'N' ;", c );
            val.jisuOut = ServiceManager.getSqlService().queryLong( "select coalesce( sum( item_count ), 0 ) " +
                    " from t_fish_item " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and item_count > 0 and item_id = 20013 and level = "+ level +"  and is_mobile = 'N' ;", c );


            return val;

        } );
    }

    public void queryItem( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_FISH, getItemData( req, resp ,1),
                ItemTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.jisuOut + "</td>" );
                    pw.write( "<td>" + info.bingdongOut + "</td>" );
                    pw.write( "<td>" + info.fenlieOut + "</td>" );
                    pw.write( "<td>" + info.suodingOut + "</td>" );
                    pw.write( "<td>" + info.jisu + "</td>" );
                    pw.write( "<td>" + info.bingdong + "</td>" );
                    pw.write( "<td>" + info.fenlie + "</td>" );
                    pw.write( "<td>" + info.suoding + "</td>" );
                },getItemData( req, resp ,2),
                ItemTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.jisuOut + "</td>" );
                    pw.write( "<td>" + info.bingdongOut + "</td>" );
                    pw.write( "<td>" + info.fenlieOut + "</td>" );
                    pw.write( "<td>" + info.suodingOut + "</td>" );
                    pw.write( "<td>" + info.jisu + "</td>" );
                    pw.write( "<td>" + info.bingdong + "</td>" );
                    pw.write( "<td>" + info.fenlie + "</td>" );
                    pw.write( "<td>" + info.suoding + "</td>" );
                },getItemData( req, resp ,3),
                ItemTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.jisuOut + "</td>" );
                    pw.write( "<td>" + info.bingdongOut + "</td>" );
                    pw.write( "<td>" + info.fenlieOut + "</td>" );
                    pw.write( "<td>" + info.suodingOut + "</td>" );
                    pw.write( "<td>" + info.jisu + "</td>" );
                    pw.write( "<td>" + info.bingdong + "</td>" );
                    pw.write( "<td>" + info.fenlie + "</td>" );
                    pw.write( "<td>" + info.suoding + "</td>" );
                },null );
    }

    public void downloadItem( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_FISH, getItemData( req, resp ,1),
                ItemTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.jisuOut+ "," );
                    pw.write( info.bingdongOut+ "," );
                    pw.write( info.fenlieOut+ "," );
                    pw.write( info.suodingOut+ "," );
                    pw.write( info.jisu + "," );
                    pw.write( info.bingdong + "," );
                    pw.write( info.fenlie + "," );
                    pw.write( info.suoding + "," );
                }, getItemData( req, resp ,2),
                ItemTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.jisuOut+ "," );
                    pw.write( info.bingdongOut+ "," );
                    pw.write( info.fenlieOut+ "," );
                    pw.write( info.suodingOut+ "," );
                    pw.write( info.jisu + "," );
                    pw.write( info.bingdong + "," );
                    pw.write( info.fenlie + "," );
                    pw.write( info.suoding + "," );
                }, getItemData( req, resp ,3),
                ItemTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.jisuOut+ "," );
                    pw.write( info.bingdongOut+ "," );
                    pw.write( info.fenlieOut+ "," );
                    pw.write( info.suodingOut+ "," );
                    pw.write( info.jisu + "," );
                    pw.write( info.bingdong + "," );
                    pw.write( info.fenlie + "," );
                    pw.write( info.suoding + "," );
                } );
    }

    private List< FishView.Basic > getBasicData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.Basic val = new FishView.Basic();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            Map<Long, Long> BetMap = ServiceManager.getSqlService().queryMapLongLong("select level, coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "   and is_mobile = 'N' group by level;", c);
            val.chujiBet = getLong( BetMap , 1 );
            val.zhongjiBet = getLong( BetMap , 2 );
            val.gaojiBet = getLong( BetMap , 3 );

            Map<Long, Long> WinMap = ServiceManager.getSqlService().queryMapLongLong("select level, coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "   and is_mobile = 'N' group by level;", c);
            val.chujiWin = getLong( WinMap , 1 );
            val.zhongjiWin = getLong( WinMap , 2 );
            val.gaojiWin = getLong( WinMap , 3 );

            Map<Long, Long> JackpotMap = ServiceManager.getSqlService().queryMapLongLong("select level, coalesce( sum( gold ), 0 ) " +
                    " from t_fish_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and gold > 0   and is_mobile = 'N' group by level;", c);
            val.chujiJack = getLong( JackpotMap , 1 );
            val.zhongjiJack = getLong( JackpotMap , 2 );
            val.gaojiJack = getLong( JackpotMap , 3 );

            Map<Long, Long> PeopleMap = ServiceManager.getSqlService().queryMapLongLong("select level, count(distinct user_id) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and gold > 0   and is_mobile = 'N' group by level;", c);
            val.chuPeople = getLong( PeopleMap , 1 );
            val.zhongPeople = getLong( PeopleMap , 2 );
            val.gaoPeople = getLong( PeopleMap , 3 );

            return val;
        } );
    }

    public void queryBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH, getBasicData( req, resp ),
                JichuTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.chuPeople + "</td>" );
                    pw.write( "<td>" + info.chujiBet + "</td>" );
                    pw.write( "<td>" + info.chujiWin + "</td>" );
                    pw.write( "<td>" + (info.chujiBet - info.chujiWin - info.chujiJack ) + "</td>" );
                    pw.write( "<td>" + info.zhongPeople + "</td>" );
                    pw.write( "<td>" + info.zhongjiBet + "</td>" );
                    pw.write( "<td>" + info.zhongjiWin + "</td>" );
                    pw.write( "<td>" + (info.zhongjiBet - info.zhongjiWin - info.zhongjiJack) + "</td>" );
                    pw.write( "<td>" + info.gaoPeople + "</td>" );
                    pw.write( "<td>" + info.gaojiBet + "</td>" );
                    pw.write( "<td>" + info.gaojiWin + "</td>" );
                    pw.write( "<td>" + (info.gaojiBet - info.gaojiWin - info.gaojiJack  ) + "</td>" );
                } );
    }

    public void downloadBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH, getBasicData( req, resp ),
                JichuTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.chuPeople + "," );
                    pw.write( info.chujiBet + "," );
                    pw.write( info.chujiWin + "," );
                    pw.write( info.chujiBet - info.chujiWin -info.chujiJack + "," );
                    pw.write( info.zhongPeople + "," );
                    pw.write( info.zhongjiBet + "," );
                    pw.write( info.zhongjiWin + "," );
                    pw.write( info.zhongjiBet - info.chujiWin - info.zhongjiJack + "," );
                    pw.write( info.gaoPeople + "," );
                    pw.write( info.gaojiBet + "," );
                    pw.write( info.gaojiWin + "," );
                    pw.write( info.gaojiBet - info.gaojiWin - info.gaojiJack + "," );
                } );
    }

    private List< FishView.Huizong > getHuizongData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FishView.Huizong val = new FishView.Huizong();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.totalBet = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'N' ;", c );

            val.totalWin = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'N'   ;", c );

            val.jackpotPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and other_info  = 1 and gold > 0  and is_mobile = 'N' ;", c );

            val.LuckyPay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and other_info in (2,3)  and is_mobile = 'N' ;", c )
            -       ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and other_info in (2,3)  and is_mobile = 'N' ;", c ) ;

            val.NewUser = ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 4  and is_mobile = 'N' ;", c )
                    -       ServiceManager.getSqlService().queryLong( "select coalesce( sum( gold ), 0 ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + " and other_info = 4  and is_mobile = 'N' ;", c ) ;

            val.zongPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'N' ;", c );

            val.winPump = val.totalBet - val.totalWin - val.jackpotPay ;
            return val;
        } );
    }

    public void queryHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH, getHuizongData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zongPeople + "</td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.totalWin + "</td>" );
                    pw.write( "<td>" + info.LuckyPay + "</td>" );
                    pw.write( "<td>" + info.jackpotPay + "</td>" );
                    pw.write( "<td>" + info.NewUser + "</td>" );
                    pw.write( "<td>" + info.winPump + "</td>" );
                });
    }

    public void downloadHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH, getHuizongData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.zongPeople + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.totalWin + "," );
                    pw.write( info.LuckyPay + "," );
                    pw.write( info.jackpotPay + "," );
                    pw.write( info.NewUser + "," );
                    pw.write( info.winPump + "," );
                } );
    }

//10520500
    private List< List< FishView.Rank > > getRankData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        int limit = Integer.parseInt( req.getParameter( "limit" ) );
        return getData( req, resp, ( beg, end, c ) -> {
            Map< Long, Long > betMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_mobile = 'N' group by user_id;", c );

            Map< Long, Long > winMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_mobile = 'N'   group by user_id;", c );

            Map< Long, Long > jackpotOutMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_mobile = 'N' and other_info = 1 and gold > 0 group by user_id;", c );

            Map< Long, Long > jackpotInMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_mobile = 'N' and other_info in ( 2,3,4) and gold < 0 group by user_id;", c );

            Map< Long, Long > LuckyJackpotBetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_paiment " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_mobile = 'N' and other_info in ( 2,3,4) group by user_id;", c );

            Map< Long, Long > LuckyJackpotGetMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( gold ) " +
                    " from t_fish_get " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and is_mobile = 'N' and other_info in ( 2,3,4) group by user_id;", c );




            Map< Long, FishView.Rank > sum = new HashMap<>();

            betMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).totalBet = v;
            } );

            winMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).totalWin = v;
            } );

            jackpotOutMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).jackpot = v;
            } );

            LuckyJackpotBetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).LuckyJackpot -= v;
            } );

            LuckyJackpotGetMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).LuckyJackpot += v;
            } );

            jackpotInMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new FishView.Rank() );
                sum.get( k ).LuckyJackpot -= v;
            } );


            List< FishView.Rank > winList = new ArrayList<>();
            List< FishView.Rank > loseList = new ArrayList<>();
            sum.forEach( ( k, v ) -> {
                v.userId = k;
                v.netWin = v.totalWin - v.totalBet + v.jackpot ;
                if( v.netWin >= limit ) winList.add( v );
                else if( v.netWin <= -limit ) loseList.add( v );
            } );

            Collections.sort( winList, ( o1, o2 ) -> {
                if( o1.netWin > o2.netWin ) return -1;
                if( o1.netWin < o2.netWin ) return 1;
                if( o1.totalBet < o2.totalBet ) return -1;
                if( o1.totalBet > o2.totalBet ) return 1;
                return 0;
            } );

            Collections.sort( loseList, ( o1, o2 ) -> {
                if( o1.netWin < o2.netWin ) return -1;
                if( o1.netWin > o2.netWin ) return 1;
                if( o1.totalBet < o2.totalBet ) return -1;
                if( o1.totalBet > o2.totalBet ) return 1;
                return 0;
            } );

            return Arrays.asList( winList, loseList );
        } );
    }

    public void queryRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryMultiList( req, resp,
                CommandList.GET_FISH, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.totalBet + "</td>" );
                    pw.write( "<td>" + info.totalWin + "</td>" );
                    pw.write( "<td>" + info.jackpot + "</td>" );
                    pw.write( "<td>" + info.LuckyJackpot + "</td>" );
                    pw.write( "<td>" + info.netWin + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadMultiList( req, resp,
                CommandList.GET_FISH, getRankData( req, resp ),
                rankTableHead, ( info, pw ) -> {
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( info.totalBet + "," );
                    pw.write( info.totalWin + "," );
                    pw.write( info.jackpot + "," );
                    pw.write( info.LuckyJackpot + "," );
                    pw.write( info.netWin + "," );
                } );
    }


    private List< baseView.Jackport > getJackportData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.Jackport val = new baseView.Jackport();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.jackportPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_fish_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'N' ;", c );
            val.jackportCount = ServiceManager.getSqlService().queryLong( "select count( id ) " +
                    " from t_fish_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'N' ;", c );
            val.jackport = ServiceManager.getSqlService().queryLong( "select sum( gold ) " +
                    " from t_fish_jackpot " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "  and is_mobile = 'N' ;", c );
            return val;
        } );
    }

    public void queryJackport( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FISH, getJackportData( req, resp ),
                jackportTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.jackportPeople + "</td>" );
                    pw.write( "<td>" + info.jackportCount + "</td>" );
                    pw.write( "<td>" + info.jackport+ "</td>" );
                } );
    }

    public void downloadJackport( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FISH, getJackportData( req, resp ),
                jackportTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.jackportPeople+ "," );
                    pw.write( info.jackportCount + "," );
                    pw.write( info.jackport + "," );
                } );
    }

}