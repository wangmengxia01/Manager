package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bifront.net.BiClient;
import com.hoolai.ccgames.bifront.net.BiClientFactory;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.servlet.interfaces.DataListProducer;
import com.hoolai.ccgames.bifront.servlet.interfaces.DataProducer;
import com.hoolai.ccgames.bifront.servlet.interfaces.DataWriter;
import com.hoolai.ccgames.bifront.util.CommonUtil;
import com.hoolai.ccgames.bifront.util.HttpRequest;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BaseAction {

    protected static final Logger logger = LoggerFactory
            .getLogger( BaseAction.class );

    public static class TimeRange {

        public long begin;
        public long end;
        public List< Long > timePoints;

        public TimeRange() {
        }

        public TimeRange( long begin, long end, String intervalType ) {
            super();
            this.begin = begin;
            this.end = end;
            this.timePoints = TimeUtil.splitTime( begin, end, TimeUtil.defaultZone, intervalType );
        }

        public static TimeRange parseFrom( HttpServletRequest req ) {

            boolean addDay = false;
            SimpleDateFormat sdFmt = null;

            if( req.getParameter( "notAddDay" ) != null ) {
                sdFmt = TimeUtil.ymdhmFormat();
                addDay = false;
            }
            else {
                sdFmt = TimeUtil.ymdFormat();
                addDay = true;
            }

            String timeBegin = req.getParameter( "timeBegin" );
            String timeEnd = req.getParameter( "timeEnd" );
            String offsetTimeZone = req.getParameter( "offsetTimeZone" );
            String timeInterval = req.getParameter( "timeInterval" );

            if( CommonUtil.isAnyEmpty( timeBegin, timeEnd, offsetTimeZone, timeInterval ) ) {
                return null;
            }

            logger.debug( "==== {} {} {} {}", timeBegin, timeEnd, offsetTimeZone, timeInterval );

            try {
                long beginGMT = sdFmt.parse( timeBegin ).getTime();
                long endGMT = sdFmt.parse( timeEnd ).getTime();
                // 对于只精确到日的请求，左右都用闭区间
                if( addDay ) endGMT += TimeUtil.DAY_MILLIS;
                return new TimeRange( beginGMT, endGMT, timeInterval );
            }
            catch( Exception e ) {
                logger.error( e.getMessage(), e );
                return null;
            }
        }
    }

    public static String safeString( String s ) {
        return s == null ? "" : s;
    }

    public static String boolString( boolean b ) {
        return b ? "Y" : "N";
    }

    public static String formatDouble( double x, int decimals ) {
        if( Double.isInfinite( x ) ) return "Inf";
        if( Double.isNaN( x ) ) return "NaN";
        return String.format( "%." + decimals + "f", x );
    }

    public static String formatRatio( double x ) {
        if( Double.isInfinite( x ) ) return "Inf";
        if( Double.isNaN( x ) ) return "NaN";
        return String.format( "%.2f%%", x * 100.0 );
    }

    public static double safeDiv( double x, double y ) {
        if( y == 0 ) return x == 0 ? 0 : x > 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        return x / y;
    }

    public void alert( String msg, HttpServletRequest req, HttpServletResponse resp ) throws IOException {
        req.setAttribute( "alert", msg );
    }

    public void popup( String msg, HttpServletRequest req, HttpServletResponse resp ) throws IOException {
        req.setAttribute( "popup", msg );

    }

    public BiClient getBiClient( HttpSession session ) {
        BiClient c = (BiClient) session.getAttribute( Constants.KEY_BI_CLIENT );
        if( c != null ) return c;
        c = BiClientFactory.createAndStart();
        session.setAttribute( Constants.KEY_BI_CLIENT, c );
        return c;
    }

    public long getUserId( HttpSession session ) {
        UserInfo userInfo = (UserInfo) session.getAttribute( Constants.KEY_USER_INFO );
        return userInfo == null ? -1L : userInfo.uid;
    }

    public boolean checkAuth( HttpServletRequest req, int cmd ) {
        long uid = getUserId( req.getSession() );
        if( uid == 1 ) { // admin
            return true;
        }
        BiClient c = getBiClient( req.getSession() );
        List< String > auth = ServiceManager.getAdminService().getAuth( uid, c );
        return auth.contains( String.valueOf( cmd ) );
    }

    public String getFishUserNameScript( String tag, String prefix ) {
        return "<script>"
                + "var userids = $( \"" + tag + "[name^='" + prefix + "']\" );"
                + "for (var i=0;i<userids.length;i++){"
                + "  $.getJSON('http://fishcommonhall.gz.1251415748.clb.myqcloud.com/username?userId='+$(userids[i]).attr('name').split('_')[1]+'&jsonp=?',function(json){"
                + "  	$(\"" + tag + "[name^='" + prefix + "\" + json.userId + \"']\").html(json.userName);;"
                + "  });"
                + "}"
                + "</script>";
    }

    public String getFishUserName( long userId ) {
        return HttpRequest.sendGet( "http://fishcommonhall.gz.1251415748.clb.myqcloud.com/username", "input=item_findUserName&userId=" + userId );
    }

    public String getUserNameScript( String tag, String prefix ) {
        return "<script>"
                + "var userids = $( \"" + tag + "[name^='" + prefix + "']\" );"
                + "for (var i=0;i<userids.length;i++){"
                + "  $.getJSON('http://texaspoker.gzopen.1251415748.clb.myqcloud.com/managemobile?input=item_findUserName&userId='+$(userids[i]).attr('name').split('_')[1]+'&jsonp=?',function(json){"
                + "  	$(\"" + tag + "[name^='" + prefix + "\" + json.userId + \"']\").html(json.userName);;"
                + "  });"
                + "}"
                + "</script>";
    }

    public String getUserName( long userId ) {
        return HttpRequest.sendGet( "http://10.190.236.31/managemobile", "input=item_findUserName&userId=" + userId );
    }

    public < T > long getLong( Map< T, Long > map, T key ) {
        Long val = map.get( key );
        return val == null ? 0L : val;
    }
    public long getLong( Map< Long, Long > map, long key ) {
        Long val = map.get( key );
        return val == null ? 0L : val;
    }
    public long getCount( Map< Long, Long > map, long key ) {
        Long val = map.get( key );
        long a = 0 ;
        if(val == key)
        {
            a = a + 1 ;
        }
        return val == null ? 0L : a;
    }

    /**
     * 生成用户自定义类型，只迭代一次
     *
     * @param req
     * @param resp
     * @param worker
     * @param <T>
     * @return
     * @throws ServletException
     * @throws IOException
     */
    protected < T > T getData( HttpServletRequest req, HttpServletResponse resp,
                               DataProducer< T > worker )
            throws ServletException, IOException {

        TimeRange tr = TimeRange.parseFrom( req );
        BiClient c = getBiClient( req.getSession() );

        if( CommonUtil.isNoneEmpty( tr, c ) ) {
            return worker.produce( tr.begin, tr.end, c );
        }
        else {
            logger.error( "Tr or BiClient is null" );
        }
        return null;
    }

    /**
     * 每次迭代生成一条数据，加入list
     *
     * @param req
     * @param resp
     * @param worker
     * @param <T>
     * @return
     * @throws Exception
     */
    protected < T > List< T > getDataList( HttpServletRequest req, HttpServletResponse resp,
                                           DataProducer< T > worker )
            throws Exception {

        List< T > rv = new LinkedList<>();
        TimeRange tr = TimeRange.parseFrom( req );
        BiClient c = getBiClient( req.getSession() );

        if( CommonUtil.isNoneEmpty( tr, c ) ) {

            tr.timePoints.stream().reduce( ( beg, end ) -> {
                rv.add( worker.produce( beg, end, c ) );
                return end;
            } );

        }
        else {
            logger.error( "Tr or BiClient is null" );
        }

        return rv;
    }

    /**
     * 每次迭代生成一个list，最后返回所有list的合集
     *
     * @param req
     * @param resp
     * @param worker
     * @param <T>
     * @return
     * @throws Exception
     */
    protected < T > List< T > getDataList2( HttpServletRequest req, HttpServletResponse resp,
                                            DataListProducer< T > worker )
            throws Exception {

        List< T > rv = new LinkedList<>();
        TimeRange tr = TimeRange.parseFrom( req );
        BiClient c = getBiClient( req.getSession() );

        if( CommonUtil.isNoneEmpty( tr, c ) ) {

            tr.timePoints.stream().reduce( ( beg, end ) -> {
                rv.addAll( worker.produce( beg, end, c ) );
                return end;
            } );

        }
        else {
            logger.error( "Tr or BiClient is null" );
        }

        return rv;
    }

    protected < T > void queryData( HttpServletRequest req, HttpServletResponse resp,
                                    int cmdId, T data,
                                    List< String > tableHead, DataWriter< T > writer, String addStr )
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }

        resp.setCharacterEncoding( "utf-8" );
        PrintWriter pw = resp.getWriter();

        pw.write( "<thead><tr>" );
        tableHead.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        pw.write( "<tr>" );
        writer.write( data, pw );
        pw.write( "</tr>" );

        pw.write( "</tbody>" );
        if( addStr != null ) pw.write( addStr );
        pw.flush();
        pw.close();
    }

    protected < T > void queryList( HttpServletRequest req, HttpServletResponse resp,
                                    int cmdId, List< T > data,
                                    List< String > tableHead, DataWriter< T > writer, String addStr )
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }

        logger.debug( "======size = {}", data.size() );
        resp.setCharacterEncoding( "utf-8" );
        PrintWriter pw = resp.getWriter();

        pw.write( "<thead><tr>" );
        tableHead.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );

        data.forEach( info -> {
            pw.write( "<tr>" );
            writer.write( info, pw );
            pw.write( "</tr>" );
        } );

        pw.write( "</tbody>" );
        if( addStr != null ) pw.write( addStr );
        pw.flush();
        pw.close();
    }

    protected < T > void queryList( HttpServletRequest req, HttpServletResponse resp,
                                    int cmdId, List< T > data,
                                    List< String > tableHead, DataWriter< T > writer )
            throws Exception {
        queryList( req, resp, cmdId, data, tableHead, writer, null );
    }

    protected < T > void queryMultiList( HttpServletRequest req, HttpServletResponse resp,
                                         int cmdId, List< List< T > > dataList,
                                         List< String > tableHead, DataWriter< T > writer, String addStr )
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }

        logger.debug( "======size = {}", dataList.size() );
        resp.setCharacterEncoding( "utf-8" );
        PrintWriter pw = resp.getWriter();

        dataList.forEach( data -> {
            pw.write( "<thead><tr>" );
            tableHead.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
            pw.write( "</tr></thead><tbody>" );

            data.forEach( info -> {
                pw.write( "<tr class = 'btn1' onlick = 'aaa'>" );
                writer.write( info, pw );
                pw.write( "</tr>" );
            } );

            pw.write( "</tbody>\n" );
        } );


        if( addStr != null ) pw.write( addStr );
        pw.flush();
        pw.close();
    }

    protected < T1, T2 > void queryMultiList( HttpServletRequest req, HttpServletResponse resp,
                                                 int cmdId,
                                                 List< T1 > data1, List< String > tableHead1, DataWriter< T1 > writer1,
                                                 List< T2 > data2, List< String > tableHead2, DataWriter< T2 > writer2,
                                                 String addStr )
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }

        logger.debug( "======size = {} {}", data1.size(), data2.size() );
        resp.setCharacterEncoding( "utf-8" );
        PrintWriter pw = resp.getWriter();

        pw.write( "<thead><tr>" );
        tableHead1.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data1.forEach( info -> {
            pw.write( "<tr>" );
            writer1.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );

        pw.write( "\n" );

        pw.write( "<thead><tr>" );
        tableHead2.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data2.forEach( info -> {
            pw.write( "<tr>" );
            writer2.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );

        if( addStr != null ) pw.write( addStr );
        pw.flush();
        pw.close();
    }

    protected < T1, T2 ,T3> void queryMultiList( HttpServletRequest req, HttpServletResponse resp,
                                              int cmdId,
                                              List< T1 > data1, List< String > tableHead1, DataWriter< T1 > writer1,
                                              List< T2 > data2, List< String > tableHead2, DataWriter< T2 > writer2,
                                              List< T3 > data3, List< String > tableHead3, DataWriter< T3 > writer3,
                                              String addStr )
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }
        logger.debug( "======size = {} {}", data1.size(), data2.size() );
        resp.setCharacterEncoding( "utf-8" );
        PrintWriter pw = resp.getWriter();
        pw.write( "<thead><tr>" );
        tableHead1.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data1.forEach( info -> {
            pw.write( "<tr>" );
            writer1.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead2.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data2.forEach( info -> {
            pw.write( "<tr>" );
            writer2.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead3.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data3.forEach( info -> {
            pw.write( "<tr>" );
            writer3.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        if( addStr != null ) pw.write( addStr );
        pw.flush();
        pw.close();
    }

    protected < T1, T2 ,T3 ,T4> void queryMultiList( HttpServletRequest req, HttpServletResponse resp,
                                                 int cmdId,
                                                 List< T1 > data1, List< String > tableHead1, DataWriter< T1 > writer1,
                                                 List< T2 > data2, List< String > tableHead2, DataWriter< T2 > writer2,
                                                 List< T3 > data3, List< String > tableHead3, DataWriter< T3 > writer3,
                                                 List< T4 > data4, List< String > tableHead4, DataWriter< T4 > writer4,
                                                 String addStr )
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }
        logger.debug( "======size = {} {}", data1.size(), data2.size() );
        resp.setCharacterEncoding( "utf-8" );
        PrintWriter pw = resp.getWriter();
        pw.write( "<thead><tr>" );
        tableHead1.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data1.forEach( info -> {
            pw.write( "<tr>" );
            writer1.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead2.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data2.forEach( info -> {
            pw.write( "<tr>" );
            writer2.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead3.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data3.forEach( info -> {
            pw.write( "<tr>" );
            writer3.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead4.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data4.forEach( info -> {
            pw.write( "<tr>" );
            writer4.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );

        if( addStr != null ) pw.write( addStr );
        pw.flush();
        pw.close();
    }

    protected < T1, T2 ,T3, T4, T5 > void queryMultiList( HttpServletRequest req, HttpServletResponse resp,
                                                 int cmdId,
                                                 List< T1 > data1, List< String > tableHead1, DataWriter< T1 > writer1,
                                                 List< T2 > data2, List< String > tableHead2, DataWriter< T2 > writer2,
                                                 List< T3 > data3, List< String > tableHead3, DataWriter< T3 > writer3,
                                                 List< T4 > data4, List< String > tableHead4, DataWriter< T4 > writer4,
                                                 List< T5 > data5, List< String > tableHead5, DataWriter< T5 > writer5,
                                                 String addStr )
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }
        logger.debug( "======size = {} {}", data1.size(), data2.size() );
        resp.setCharacterEncoding( "utf-8" );
        PrintWriter pw = resp.getWriter();
        pw.write( "<thead><tr>" );
        tableHead1.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data1.forEach( info -> {
            pw.write( "<tr>" );
            writer1.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead2.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data2.forEach( info -> {
            pw.write( "<tr>" );
            writer2.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead3.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data3.forEach( info -> {
            pw.write( "<tr>" );
            writer3.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        if( addStr != null ) pw.write( addStr );
        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead4.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data4.forEach( info -> {
            pw.write( "<tr>" );
            writer4.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        if( addStr != null ) pw.write( addStr );
        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead5.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data5.forEach( info -> {
            pw.write( "<tr>" );
            writer5.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        if( addStr != null ) pw.write( addStr );
        pw.flush();
        pw.close();
    }
    protected < T1, T2 ,T3, T4, T5 ,T6 ,T7> void queryMultiList( HttpServletRequest req, HttpServletResponse resp,
                                                          int cmdId,
                                                          List< T1 > data1, List< String > tableHead1, DataWriter< T1 > writer1,
                                                          List< T2 > data2, List< String > tableHead2, DataWriter< T2 > writer2,
                                                          List< T3 > data3, List< String > tableHead3, DataWriter< T3 > writer3,
                                                          List< T4 > data4, List< String > tableHead4, DataWriter< T4 > writer4,
                                                          List< T5 > data5, List< String > tableHead5, DataWriter< T5 > writer5,
                                                          List< T6 > data6, List< String > tableHead6, DataWriter< T6 > writer6,
                                                          List< T7 > data7, List< String > tableHead7, DataWriter< T7 > writer7,
                                                          String addStr )
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }
        logger.debug( "======size = {} {}", data1.size(), data2.size() );
        resp.setCharacterEncoding( "utf-8" );
        PrintWriter pw = resp.getWriter();
        pw.write( "<thead><tr>" );
        tableHead1.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data1.forEach( info -> {
            pw.write( "<tr>" );
            writer1.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead2.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data2.forEach( info -> {
            pw.write( "<tr>" );
            writer2.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );
        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead3.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data3.forEach( info -> {
            pw.write( "<tr>" );
            writer3.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );

        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead4.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data4.forEach( info -> {
            pw.write( "<tr>" );
            writer4.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );

        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead5.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data5.forEach( info -> {
            pw.write( "<tr>" );
            writer5.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );

        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead6.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data6.forEach( info -> {
            pw.write( "<tr>" );
            writer6.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );

        pw.write( "\n" );
        pw.write( "<thead><tr>" );
        tableHead7.forEach( h -> pw.write( "<th>" + h + "</th>" ) );
        pw.write( "</tr></thead><tbody>" );
        data7.forEach( info -> {
            pw.write( "<tr>" );
            writer7.write( info, pw );
            pw.write( "</tr>" );
        } );
        pw.write( "</tbody>" );

        if( addStr != null ) pw.write( addStr );
        pw.flush();
        pw.close();
    }


    protected < T > void downloadData( HttpServletRequest req, HttpServletResponse resp,
                                       int cmdId, T data,
                                       List< String > tableHead, DataWriter< T > writer )
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }

        String fileName = req.getParameter( "fileName" );

        resp.setStatus( 200 );
        resp.setContentType( "application/csv;charset=utf-8" );
        resp.setHeader( "Content-Disposition", "attachment;filename=\"" + fileName + "\"" );

        PrintWriter pw = resp.getWriter();
        tableHead.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );

        writer.write( data, pw );
        pw.write( "\n" );

        pw.flush();
        pw.close();

    }

    protected < T > void downloadList( HttpServletRequest req, HttpServletResponse resp,
                                       int cmdId, List< T > data,
                                       List< String > tableHead, DataWriter< T > writer )
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }

        logger.debug( "======size = {}", data.size() );
        String fileName = req.getParameter( "fileName" );

        resp.setStatus( 200 );
        resp.setContentType( "application/csv;charset=utf-8" );
        resp.setHeader( "Content-Disposition", "attachment;filename=\"" + fileName + "\"" );

        PrintWriter pw = resp.getWriter();
        tableHead.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );

        data.forEach( info -> {
            writer.write( info, pw );
            pw.write( "\n" );
        } );

        pw.flush();
        pw.close();

    }

    protected < T > void downloadMultiList( HttpServletRequest req, HttpServletResponse resp,
                                            int cmdId, List< List< T > > dataList,
                                            List< String > tableHead, DataWriter< T > writer )
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }

        logger.debug( "======size = {}", dataList.size() );
        String fileName = req.getParameter( "fileName" );

        resp.setStatus( 200 );
        resp.setContentType( "application/csv;charset=utf-8" );
        resp.setHeader( "Content-Disposition", "attachment;filename=\"" + fileName + "\"" );

        PrintWriter pw = resp.getWriter();

        dataList.forEach( data -> {
            tableHead.forEach( h -> pw.write( h + "," ) );
            pw.write( "\n" );
            data.forEach( info -> {
                writer.write( info, pw );
                pw.write( "\n" );
            } );
        } );

        pw.flush();
        pw.close();

    }

    protected < T1, T2 > void downloadMultiList( HttpServletRequest req, HttpServletResponse resp,
                                                 int cmdId,
                                                 List< T1 > data1, List< String > tableHead1, DataWriter< T1 > writer1,
                                                 List< T2 > data2, List< String > tableHead2, DataWriter< T2 > writer2 )
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }

        logger.debug( "======size = {} {}", data1.size(), data2.size() );
        String fileName = req.getParameter( "fileName" );

        resp.setStatus( 200 );
        resp.setContentType( "application/csv;charset=utf-8" );
        resp.setHeader( "Content-Disposition", "attachment;filename=\"" + fileName + "\"" );

        PrintWriter pw = resp.getWriter();

        tableHead1.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data1.forEach( info -> {
            writer1.write( info, pw );
            pw.write( "\n" );
        } );

        pw.write( "\n" );

        tableHead2.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data2.forEach( info -> {
            writer2.write( info, pw );
            pw.write( "\n" );
        } );

        pw.flush();
        pw.close();

    }

    protected < T1, T2 , T3 > void downloadMultiList( HttpServletRequest req, HttpServletResponse resp,
                                                 int cmdId,
                                                 List< T1 > data1, List< String > tableHead1, DataWriter< T1 > writer1,
                                                 List< T2 > data2, List< String > tableHead2, DataWriter< T2 > writer2,
                                                 List< T3 > data3, List< String > tableHead3, DataWriter< T3 > writer3)
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }

        logger.debug( "======size = {} {}", data1.size(), data2.size() );
        String fileName = req.getParameter( "fileName" );

        resp.setStatus( 200 );
        resp.setContentType( "application/csv;charset=utf-8" );
        resp.setHeader( "Content-Disposition", "attachment;filename=\"" + fileName + "\"" );

        PrintWriter pw = resp.getWriter();

        tableHead1.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data1.forEach( info -> {
            writer1.write( info, pw );
            pw.write( "\n" );
        } );

        pw.write( "\n" );

        tableHead2.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data2.forEach( info -> {
            writer2.write( info, pw );
            pw.write( "\n" );
        } );

        pw.write( "\n" );

        tableHead3.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data3.forEach( info -> {
            writer3.write( info, pw );
            pw.write( "\n" );
        } );

        pw.flush();
        pw.close();

    }


    protected < T1, T2 , T3 ,T4 > void downloadMultiList( HttpServletRequest req, HttpServletResponse resp,
                                                      int cmdId,
                                                      List< T1 > data1, List< String > tableHead1, DataWriter< T1 > writer1,
                                                      List< T2 > data2, List< String > tableHead2, DataWriter< T2 > writer2,
                                                      List< T3 > data3, List< String > tableHead3, DataWriter< T3 > writer3,
                                                      List< T4 > data4, List< String > tableHead4, DataWriter< T4 > writer4)
            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }

        logger.debug( "======size = {} {}", data1.size(), data2.size() );
        String fileName = req.getParameter( "fileName" );

        resp.setStatus( 200 );
        resp.setContentType( "application/csv;charset=utf-8" );
        resp.setHeader( "Content-Disposition", "attachment;filename=\"" + fileName + "\"" );

        PrintWriter pw = resp.getWriter();

        tableHead1.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data1.forEach( info -> {
            writer1.write( info, pw );
            pw.write( "\n" );
        } );

        pw.write( "\n" );

        tableHead2.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data2.forEach( info -> {
            writer2.write( info, pw );
            pw.write( "\n" );
        } );

        pw.write( "\n" );

        tableHead3.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data3.forEach( info -> {
            writer3.write( info, pw );
            pw.write( "\n" );
        } );

        pw.write( "\n" );

        tableHead4.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data4.forEach( info -> {
            writer4.write( info, pw );
            pw.write( "\n" );
        } );

        pw.flush();
        pw.close();

    }

    protected < T1, T2 , T3 , T4 , T5 > void downloadMultiList( HttpServletRequest req, HttpServletResponse resp,
                                                      int cmdId,
                                                      List< T1 > data1, List< String > tableHead1, DataWriter< T1 > writer1,
                                                      List< T2 > data2, List< String > tableHead2, DataWriter< T2 > writer2,
                                                      List< T3 > data3, List< String > tableHead3, DataWriter< T3 > writer3,
                                                      List< T4 > data4, List< String > tableHead4, DataWriter< T4 > writer4,
                                                      List< T5 > data5, List< String > tableHead5, DataWriter< T5 > writer5)


            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }

        logger.debug( "======size = {} {}", data1.size(), data2.size() );
        String fileName = req.getParameter( "fileName" );

        resp.setStatus( 200 );
        resp.setContentType( "application/csv;charset=utf-8" );
        resp.setHeader( "Content-Disposition", "attachment;filename=\"" + fileName + "\"" );

        PrintWriter pw = resp.getWriter();

        tableHead1.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data1.forEach( info -> {
            writer1.write( info, pw );
            pw.write( "\n" );
        } );

        pw.write( "\n" );

        tableHead2.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data2.forEach( info -> {
            writer2.write( info, pw );
            pw.write( "\n" );
        } );

        pw.write( "\n" );

        tableHead3.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data3.forEach( info -> {
            writer3.write( info, pw );
            pw.write( "\n" );
        } );

        pw.write( "\n" );

        tableHead4.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data4.forEach( info -> {
            writer4.write( info, pw );
            pw.write( "\n" );
        } );
        pw.write( "\n" );

        tableHead5.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data5.forEach( info -> {
            writer5.write( info, pw );
            pw.write( "\n" );
        } );

        pw.flush();
        pw.close();

    }

    protected < T1, T2 , T3 , T4 , T5 ,T6 ,T7> void downloadMultiList( HttpServletRequest req, HttpServletResponse resp,
                                                                int cmdId,
                                                                List< T1 > data1, List< String > tableHead1, DataWriter< T1 > writer1,
                                                                List< T2 > data2, List< String > tableHead2, DataWriter< T2 > writer2,
                                                                List< T3 > data3, List< String > tableHead3, DataWriter< T3 > writer3,
                                                                List< T4 > data4, List< String > tableHead4, DataWriter< T4 > writer4,
                                                                List< T5 > data5, List< String > tableHead5, DataWriter< T5 > writer5,
                                                                List< T6 > data6, List< String > tableHead6, DataWriter< T6 > writer6,
                                                                List< T7 > data7, List< String > tableHead7, DataWriter< T7 > writer7)


            throws Exception {
        if( !checkAuth( req, cmdId ) ) {
            req.getRequestDispatcher( Constants.URL_NO_AUTH ).forward( req, resp );
            return;
        }

        logger.debug( "======size = {} {}", data1.size(), data2.size() );
        String fileName = req.getParameter( "fileName" );

        resp.setStatus( 200 );
        resp.setContentType( "application/csv;charset=utf-8" );
        resp.setHeader( "Content-Disposition", "attachment;filename=\"" + fileName + "\"" );

        PrintWriter pw = resp.getWriter();

        tableHead1.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data1.forEach( info -> {
            writer1.write( info, pw );
            pw.write( "\n" );
        } );

        pw.write( "\n" );

        tableHead2.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data2.forEach( info -> {
            writer2.write( info, pw );
            pw.write( "\n" );
        } );

        pw.write( "\n" );

        tableHead3.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data3.forEach( info -> {
            writer3.write( info, pw );
            pw.write( "\n" );
        } );

        pw.write( "\n" );

        tableHead4.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data4.forEach( info -> {
            writer4.write( info, pw );
            pw.write( "\n" );
        } );
        pw.write( "\n" );

        tableHead5.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data5.forEach( info -> {
            writer5.write( info, pw );
            pw.write( "\n" );
        } );
        pw.write( "\n" );

        tableHead6.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data6.forEach( info -> {
            writer6.write( info, pw );
            pw.write( "\n" );
        } );pw.write( "\n" );

        tableHead7.forEach( h -> pw.write( h + "," ) );
        pw.write( "\n" );
        data7.forEach( info -> {
            writer7.write( info, pw );
            pw.write( "\n" );
        } );
        pw.flush();
        pw.close();

    }

    public void mergeMap( Map< Long, Long > from, Map< Long, Long > to ) {
        from.forEach( ( k, v ) -> to.compute( k, ( k2, v2 ) -> v2 == null ? v : v + v2 ) );
    }

    public void mergeStringMap( Map< Long, Long > from, Map< Long, Long > to ) {
        from.forEach( ( k, v ) -> to.compute( k, ( k2, v2 ) -> v2 == null ? v : v + v2 ) );
    }


    public Map< Long, Long > splitMap( String s ) {
        return splitMap( s, ";", "," );
    }

    /**
     *
     * @param s
     * @param splitter1 分割不同子项
     * @param splitter2 分割子项内部
     * @return
     */
    public Map< Long, Long > splitMap( String s, String splitter1, String splitter2 ) {
        Map< Long, Long > rv = new TreeMap<>();
        if( s.contains( splitter1 ) ) {
            String[] s1 = s.split( splitter1 );
            for( String s2 : s1 ) {
                if( s2.contains( splitter2 ) ) {
                    String[] s3 = s2.split( splitter2 );
//                    rv.put( Long.parseLong( s3[0] ), Long.parseLong( s3[1] ) );
                    long key = Long.parseLong( s3[0] );
                    long val = Long.parseLong( s3[1] );
                    rv.compute( key, ( k, v ) -> { return v == null ? val : val + v; } );
                }
            }
        }
        else if( s.contains( splitter2 ) ) {
            String[] s3 = s.split( splitter2 );
            rv.put( Long.parseLong( s3[0] ), Long.parseLong( s3[1] ) );

        }

        return rv;
    }
}
