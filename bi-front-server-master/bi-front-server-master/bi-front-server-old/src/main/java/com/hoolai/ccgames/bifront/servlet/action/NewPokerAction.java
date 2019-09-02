package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.NewpokerView;
import com.hoolai.ccgames.bifront.vo.baseView;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/9.
 */
@Service( "newpokerAction" )
public class NewPokerAction extends BaseAction {

    private  static String APPID = "1104754063";

    private static List< String > PokerJichuTableHead = Arrays.asList( "日期", "初级人数", "初级手数", "初级抽水"
            , "中级人数", "中级手数", "中级抽水", "高级人数", "高级手数", "高级抽水", "大师人数", "大师手数", "大师抽水"
            , "超级人数", "超级手数", "超级抽水", "至尊人数", "至尊手数", "至尊抽水","总抽水");

    private static List< String > huiZongTableHead = Arrays.asList( "时间",
            "新德州人数", "新德州抽水", "新德州总收入");

    private static List< Integer > innings = Arrays.asList( 0, 1, 2, 3, 4, 7, 11, 16, 21, 26, 31, Integer.MAX_VALUE );
    private static List< String > timeTableHead = new ArrayList<>();
    private static List< String > peopleTableHead = new ArrayList<>();

    static {
        timeTableHead.add( "日期" );
        innings.stream().reduce( ( beg, end ) -> {
            if( end == Integer.MAX_VALUE ) timeTableHead.add( ( beg - 1 ) + "+分钟" );
            else if( beg + 1 == end ) timeTableHead.add( beg + "分钟" );
            else timeTableHead.add( beg + "-" + ( end - 1 ) + "分钟" );
            return end;
        } );
    }
    static {
        peopleTableHead.add( "日期" );
        innings.stream().reduce( ( beg, end ) -> {
            if( end == Integer.MAX_VALUE ) peopleTableHead.add( ( beg - 1 ) + "+手" );
            else if( beg + 1 == end ) peopleTableHead.add( beg + "手" );
            else peopleTableHead.add( beg + "-" + ( end - 1 ) + "手" );
            return end;
        } );
    }

    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_NEWPOKER ).forward( req, resp );
    }
    public void getHuizongPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_NEWPOKER_HUIZONG ).forward( req, resp );
    }
    public void getInningPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_NEWPOKER_INNING ).forward( req, resp );
    }
    public void getjichuPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_NEWPOKER_JICHU ).forward( req, resp );
    }

    public void getLiushuiPage( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        req.getRequestDispatcher( Constants.URL_NEWPOKER_LIUSHUI ).forward( req, resp );
    }
    private static List< String > LiushuiTableHead = Arrays.asList( "日期",
            "初级场流水", "中级场流水", "高级场流水", "大师场流水","超级场流水","至尊场流水", "总流水" );
    private List< baseView.Liushui > getLiushuiData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            baseView.Liushui val = new baseView.Liushui();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            Map< String, Long > dataMap = ServiceManager.getSqlService().queryMapStringLong( "select desk_type, sum( chips ) " +
                    " from t_gdesk_win  " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and appid not in ( 100632434 ,1104737759 ) and is_robot ='N' and chips > 0" +
                    " group by desk_type;", c );

            val.changci1 = getLong( dataMap, "chuji" );
            val.changci2 = getLong( dataMap, "zhongji" );
            val.changci3 = getLong( dataMap, "gaoji" );
            val.changci4 = getLong( dataMap, "dashi" );
            val.changci5 = getLong( dataMap, "chaoji" );
            val.changci6 = getLong( dataMap, "zhizun" );

            return val;
        } );
    }

    public void queryLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp ),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.changci1 + "</td>" );
                    pw.write( "<td>" + info.changci2 + "</td>" );
                    pw.write( "<td>" + info.changci3 + "</td>" );
                    pw.write( "<td>" + info.changci4 + "</td>" );
                    pw.write( "<td>" + info.changci5 + "</td>" );
                    pw.write( "<td>" + info.changci6 + "</td>" );
                    pw.write( "<td>" + (info.changci1 + info.changci2 + info.changci3 + info.changci4+ info.changci5+ info.changci6 ) + "</td>" );
                } );
    }

    public void downloadLiushui( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_LIUSHUI, getLiushuiData( req, resp),
                LiushuiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.changci1 + "," );
                    pw.write( info.changci2 + "," );
                    pw.write( info.changci3 + "," );
                    pw.write( info.changci4 + "," );
                    pw.write( info.changci5 + "," );
                    pw.write( info.changci6 + "," );
                    pw.write( (info.changci1 + info.changci2 + info.changci3 + info.changci4+ info.changci5 + info.changci6 ) + "," );
                } );
    }
    private List< NewpokerView.huizong > gethuiZongData(HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            NewpokerView.huizong val = new NewpokerView.huizong();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.People     = ServiceManager.getSqlService().queryLong( "select count( distinct user_id  ) " +
                    " from t_gdesk_play " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and desk_type in ('chuji','dashi', 'zhongji' , 'gaoji');", c );

            val.shouru = val.Rake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and is_robot ='N' and desk_type in ('chuji','dashi', 'zhongji' , 'gaoji','chaoji','zhizun');", c );



            return val;
        } );
    }

    public void queryHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< NewpokerView.huizong > data = gethuiZongData( req, resp, false );

        queryList( req, resp,
                CommandList.GET_NEWPOKER,
                data, huiZongTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin           + "</td>" );
                    pw.write( "<td>" + info.People   + "</td>" );
                    pw.write( "<td>" + info.Rake     + "</td>" );
                    pw.write( "<td>" + info.shouru     + "</td>" );

                });
    }

    public void downloadHuizong( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        List< NewpokerView.huizong > data = gethuiZongData( req, resp, false );
        downloadList( req, resp,
                CommandList.GET_NEWPOKER,
                data, huiZongTableHead, ( info, pw ) -> {
                    pw.write( info.begin            + "," );
                    pw.write( info.People      + "," );
                    pw.write( info.Rake         + "," );
                    pw.write( info.shouru        + "," );
                });
    }


    private List< NewpokerView.people > getPeopleData(HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            NewpokerView.people val = new NewpokerView.people();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            Map< Long, Long > inningMap = ServiceManager.getSqlService().queryMapLongLong(
                    "select user_id, count( id ) as cnt " +
                    "        from t_gdesk_play " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "          and is_ccgames = 'Y' and is_robot = 'N' and appid not in ( 100632434 ,1104737759 )" +
                    "        group by user_id;", c );
            innings.stream().reduce( ( b, e ) -> {
                long people = inningMap.values().stream().filter( v -> b <= v && v < e ).count();
                val.inningPeople.add( people );
                return e;
            } );

            return val;
        } );
    }

    public void queryPeople( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< NewpokerView.people > data = getPeopleData( req, resp, false );

        queryList( req, resp,
                CommandList.GET_NEWPOKER,
                data, peopleTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    info.inningPeople.forEach( i -> pw.write( "<td>" + i + "</td>" ) );
                });
    }

    public void downloadPeople( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< NewpokerView.people > data = getPeopleData( req, resp, false );

        downloadList( req, resp,
                CommandList.GET_NEWPOKER,
                data, peopleTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    info.inningPeople.forEach( i -> pw.write( i + "," ) );
                });
    }


    private List< NewpokerView.online > getOnlineData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            NewpokerView.online val = new NewpokerView.online();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            Map< Long, Long > inningMap = ServiceManager.getSqlService().queryMapLongLong( " select inning_id, count( id ) as cnt " +
                    "        from t_online " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "        and appid not in ( 100632434 ,1104737759 ) and game_id = '1003' group by inning_id;", c );
            innings.stream().reduce( ( b, e ) -> {
                long online = inningMap.values().stream().filter( v -> b <= v && v < e ).count();
                val.online.add( online );
                return e;
            } );

            return val;
        } );
    }

    public void queryOnline( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< NewpokerView.online > data = getOnlineData( req, resp, false );

        queryList( req, resp,
                CommandList.GET_NEWPOKER,
                data, timeTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    info.online.forEach( i -> pw.write( "<td>" + i + "</td>" ) );
                });
    }

    public void downloadOnline( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< NewpokerView.online > data = getOnlineData( req, resp, false );

        downloadList( req, resp,
                CommandList.GET_NEWPOKER,
                data, timeTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    info.online.forEach( i -> pw.write( i + "," ) );
                });
    }


    private List< NewpokerView.jichu > getjichuData(HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            NewpokerView.jichu val = new NewpokerView.jichu();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.chujiPeople     = ServiceManager.getNewPokerService().getchangciPeople("chuji",APPID,beg,end,false , c);
            val.zhongjiPeople   = ServiceManager.getNewPokerService().getchangciPeople("zhongji",APPID,beg,end,false , c);
            val.gaojiPeople     = ServiceManager.getNewPokerService().getchangciPeople("gaoji",APPID,beg,end,false , c);
            val.dashiPeople     = ServiceManager.getNewPokerService().getchangciPeople("dashi",APPID,beg,end,false , c);
            val.chaojiPeople    = ServiceManager.getNewPokerService().getchangciPeople("chaoji",APPID,beg,end,false , c);
            val.zhizunPeople    = ServiceManager.getNewPokerService().getchangciPeople("zhizun",APPID,beg,end,false , c);


            val.chujiNum    = ServiceManager.getNewPokerService().getchangciNum("chuji",APPID,beg,end,false , c);
            val.zhongjiNum  = ServiceManager.getNewPokerService().getchangciNum("zhongji",APPID,beg,end,false , c);
            val.gaojiNum    = ServiceManager.getNewPokerService().getchangciNum("gaoji",APPID,beg,end,false , c);
            val.dashiNum    = ServiceManager.getNewPokerService().getchangciNum("dashi",APPID,beg,end,false , c);
            val.chaojiNum   = ServiceManager.getNewPokerService().getchangciNum("chaoji",APPID,beg,end,false , c);
            val.zhizunNum   = ServiceManager.getNewPokerService().getchangciNum("zhizun",APPID,beg,end,false , c);

            val.chujiRake   = ServiceManager.getNewPokerService().getchangciRake("chuji",APPID,beg,end,false , c);
            val.zhongjiRake = ServiceManager.getNewPokerService().getchangciRake("zhongji",APPID,beg,end,false , c);
            val.gaojiRake   = ServiceManager.getNewPokerService().getchangciRake("gaoji",APPID,beg,end,false , c);
            val.dashiRake   = ServiceManager.getNewPokerService().getchangciRake("dashi",APPID,beg,end,false , c);
            val.chaojiRake  = ServiceManager.getNewPokerService().getchangciRake("chaoji",APPID,beg,end,false , c);
            val.zhizunRake  = ServiceManager.getNewPokerService().getchangciRake("zhizun",APPID,beg,end,false , c);

            val.zongRake = ServiceManager.getSqlService().queryLong( "select sum( chips ) " +
                    " from t_gdesk_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and desk_type  in ( 'chuji','gaoji','zhongji','chaoji','zhizun','dashi' ) and is_robot ='N';", c );

//            Map< Long, Long > inningMap = ServiceManager.getSqlService().queryMapLongLong( "select A.user_id, A.cnt " +
//                    " from ( select user_id, count( id ) as cnt " +
//                    "        from t_gdesk_play " +
//                    "        where " + beg + " <= timestamp and timestamp < " + end +
//                    "          and is_ccgames = 'Y' and is_robot = 'N'" +
//                    "        group by user_id ) as A;", c );
//
//            innings.stream().reduce( ( b, e ) -> {
//                long people = inningMap.values().stream().filter( v -> b <= v && v < e ).count();
//                val.inningPeople.add( people );
//                return e;
//            } );
//
            return val;
        } );
    }

    public void queryJichu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< NewpokerView.jichu > data = getjichuData( req, resp, false );

        queryList( req, resp,
                CommandList.GET_NEWPOKER,
                data, PokerJichuTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin           + "</td>" );
                    pw.write( "<td>" + info.chujiPeople     + "</td>" );
                    pw.write( "<td>" + info.chujiNum        + "</td>" );
                    pw.write( "<td>" + info.chujiRake       + "</td>" );
                    pw.write( "<td>" + info.zhongjiPeople   + "</td>" );
                    pw.write( "<td>" + info.zhongjiNum      + "</td>" );
                    pw.write( "<td>" + info.zhongjiRake     + "</td>" );
                    pw.write( "<td>" + info.gaojiPeople     + "</td>" );
                    pw.write( "<td>" + info.gaojiNum        + "</td>" );
                    pw.write( "<td>" + info.gaojiRake       + "</td>" );
                    pw.write( "<td>" + info.dashiPeople     + "</td>" );
                    pw.write( "<td>" + info.dashiNum        + "</td>" );
                    pw.write( "<td>" + info.dashiRake       + "</td>" );
                    pw.write( "<td>" + info.chaojiPeople    + "</td>" );
                    pw.write( "<td>" + info.chaojiNum       + "</td>" );
                    pw.write( "<td>" + info.chaojiRake      + "</td>" );
                    pw.write( "<td>" + info.zhizunPeople    + "</td>" );
                    pw.write( "<td>" + info.zhizunNum       + "</td>" );
                    pw.write( "<td>" + info.zhizunRake      + "</td>" );
                    pw.write( "<td>" + info.zongRake        + "</td>" );

                });
    }

    public void downloadJichu( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List< NewpokerView.jichu > data = getjichuData( req, resp, false );

        downloadList( req, resp,
                CommandList.GET_NEWPOKER,
                data, PokerJichuTableHead, ( info, pw ) -> {
                    pw.write( info.begin            + "," );
                    pw.write( info.chujiPeople      + "," );
                    pw.write( info.chujiNum         + "," );
                    pw.write( info.chujiRake        + "," );
                    pw.write( info.zhongjiPeople    + "," );
                    pw.write( info.zhongjiNum       + "," );
                    pw.write( info.zhongjiRake      + "," );
                    pw.write( info.gaojiPeople      + "," );
                    pw.write( info.gaojiNum         + "," );
                    pw.write( info.gaojiRake        + "," );
                    pw.write( info.dashiPeople      + "," );
                    pw.write( info.dashiNum         + "," );
                    pw.write( info.dashiRake        + "," );
                    pw.write( info.chaojiPeople     + "," );
                    pw.write( info.chaojiNum        + "," );
                    pw.write( info.chaojiRake       + "," );
                    pw.write( info.zhizunPeople     + "," );
                    pw.write( info.zhizunNum        + "," );
                    pw.write( info.zhizunRake       + "," );
                    pw.write( info.zongRake         + "," );

                });
    }

}
