package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.PropsView;
import com.hoolai.centersdk.item.ItemIdUtils;
import com.hoolai.centersdk.item.ItemManager;
import com.hoolai.centersdk.sdk.AppSdk;
import com.hoolai.centersdk.sdk.UserSdk;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2016/8/19.
 */

@Service( "propsAction" )
public class PropsAction extends BaseAction{

    private static List< String > SendTableHead= Arrays.asList( "日期","赠送者ID",
            "道具","道具ID", "数量", "被赠送ID", "赠送时间");
    private static List< String > MaiFenBossTableHead = Arrays.asList( "时间","召唤人id","召唤人昵称","卖分人id","卖分人昵称", "出金币数" );

    private static List< String > BiShangSendTableHead = Arrays.asList(  "日期", "用户id","用户名","贝壳赠送","青铜赠送","白银赠送","黄金赠送","贝壳回收","青铜回收","白银回收","黄金回收");

    private static List< String > HuefeiZongTableHead= Arrays.asList( "日期","一元话费卡","二元话费卡","五元话费卡","十元话费卡",
            "二十元话费卡","五十元话费卡","一百元元话费卡");

    private static List< String > HuefeiTableHead= Arrays.asList( "日期","APP","使用者ID","充值手机号",
            "话费卡","充值时间");

    private static List< String > Huefei1TableHead= Arrays.asList( "日期","APP","充值手机号");

    private static List< String > zongliangTableHead   = Arrays.asList( "日期",
            "内容");

    private static List< String > BuyRankTableHead = Arrays.asList( "日期","用户id","用户姓名","购买道具","购买数量","拜神数量","拜神次数");
    private static List< String > BaiShenTableHead = Arrays.asList( "日期","拜神人数","拜神道具数量","随机赠送产出");


    public void getPropsPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PROPS).forward( req, resp );
    }
    public void getBaiShenPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PROPS_BAISHEN).forward( req, resp );
    }
    public void getZongLiangPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PROPS_ZONGLIANG).forward( req, resp );
    }
    public void getHuafeiPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PROPS_HUAFEI).forward( req, resp );
    }
    public void getHuafeiOutPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PROPS_HUAFEIOUT).forward( req, resp );
    }

    public void getHuafei1Page(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PROPS_1HUAFEI).forward( req, resp );
    }
    public void getMaiFenBossPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PROPS_MAIFEN).forward( req, resp );
    }

    public void getSendPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PROPS_SEND).forward( req, resp );
    }
    public void getBuyRankPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PROPS_BUYRANK).forward( req, resp );
    }
    public void getBiShangPage(HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_PROPS_BISHANG).forward( req, resp );
    }

    private List< PropsView.BaiShen >  getBaiShenData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        long userId = req.getParameter( "actId" ).trim() != "" ? Long.parseLong( req.getParameter( "actId" ).trim() ) : 0;
        return getDataList( req, resp, ( beg, end, c ) -> {
            PropsView.BaiShen val = new PropsView.BaiShen();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            if (userId == 0){
                val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                        " from t_use_props " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and item_source = 151;", c );
                Map< Long, Long > baishenMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                        " from t_use_props " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and item_source = 151  group by item_id;", c );
                Map< Long, Long > suijiMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                        " from t_use_props " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and item_source = 153 group by item_id;", c );
                val.baishenDaoju = baishenMap;
                val.suijiDaoju2 = suijiMap;
            }else
            {
                val.People = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                        " from t_use_props " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and item_source = 151 and user_id = " + userId +" ;", c );
                Map< Long, Long > baishenMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                        " from t_use_props " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and item_source = 151 and user_id = " + userId +"  group by item_id;", c );
                Map< Long, Long > suijiMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                        " from t_use_props " +
                        " where " + beg + " <= timestamp and timestamp < " + end +
                        " and item_source = 153 and user_id = " + userId +"  group by item_id;", c );
                val.baishenDaoju = baishenMap;
                val.suijiDaoju2 = suijiMap;
            }

            return val;
        } );
    }

    public void queryBaiShen( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PROPS, getBaiShenData( req, resp ),
                BaiShenTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.People + "</td>" );
                    pw.write( "<td>" );
                    info.baishenDaoju.forEach( ( itemId, itemCount ) -> {
                        pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                    } );
                    pw.write( "</td>" );
                    pw.write( "<td>" );
                    info.suijiDaoju2.forEach( ( itemId, itemCount ) -> {
                        if (itemId != 0)
                        {
                            if (itemId == -1 )
                                pw.write("金币:" + itemCount + "<br>" );
                            else
                                pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                        }
                    } );
                    pw.write( "</td>" );
                },null);
    }

    public void downloadBaiShen( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PROPS, getBaiShenData( req, resp ),
                BaiShenTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.People + "," );
                    info.baishenDaoju.forEach( ( itemId, itemCount ) -> {
                        pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                    } );
                    info.suijiDaoju2.forEach( ( itemId, itemCount ) -> {
                        pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                    } );
                } );
    }

    private List< PropsView.BuyRank >  getBuyRankData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        long itemId = Long.parseLong( req.getParameter( "actId" ).trim() );
        return getData( req, resp, ( beg, end, c ) -> {

            Map< Long, Long > buyMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( item_count ) " +
                    " from t_use_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and use_info = 6 and item_source = 5 and item_id = "+ itemId +"  group by user_id;", c );
            Map< Long, Long > baishenMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, sum( item_count ) " +
                    " from t_use_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and item_source = 151 and item_id = "+ itemId +" and use_info = 4 group by user_id;", c );
            Map< Long, Long > countMap = ServiceManager.getSqlService().queryMapLongLong( "select user_id, count( id ) " +
                    " from t_use_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and item_source = 151 and item_id = "+ itemId +" and use_info = 4 group by user_id;", c );

            Map< Long, PropsView.BuyRank > sum = new HashMap<>();

            buyMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new PropsView.BuyRank() );
                sum.get( k ).BuyCount = v;
            } );

            countMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new PropsView.BuyRank() );
                sum.get( k ).count = v;
            } );

            baishenMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new PropsView.BuyRank() );
                sum.get( k ).baishenCount = v;
            } );

            sum.forEach( ( k, v ) -> {
                v.begin = TimeUtil.ymdFormat().format( beg );
                v.userId = k;
                v.itemName = ItemManager.getInstance().getItemName( (int)itemId );
            } );

            ArrayList< PropsView.BuyRank > rv = new ArrayList<>( sum.values() );
            rv.sort( ( a, b ) -> {
                if( a.BuyCount > b.BuyCount ) return -1;
                if( a.BuyCount < b.BuyCount ) return 1;
                if( a.baishenCount > b.baishenCount ) return -1;
                if( a.baishenCount < b.baishenCount ) return 1;
                if( a.count > b.count) return -1;
                if( a.count < b.count) return 1;
                return 0;
            } );
            return rv;
        } );
    }

    public void queryBuyRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PROPS, getBuyRankData( req, resp ),
                BuyRankTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.userId + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.userId + "'></td>" );
                    pw.write( "<td>" + info.itemName + "</td>" );
                    pw.write( "<td>" + info.BuyCount + "</td>" );
                    pw.write( "<td>" + info.baishenCount + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                }, getUserNameScript( "td", "tdUserName_" ) );
    }

    public void downloadBuyRank( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PROPS, getBuyRankData( req, resp ),
                BuyRankTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.userId + "," );
                    pw.write( getUserName( info.userId ) + "," );
                    pw.write( info.itemName + "," );
                    pw.write( info.BuyCount + "," );
                    pw.write( info.baishenCount + "," );
                    pw.write( info.count + "," );
                } );
    }



    private List< PropsView.Send > getCardFromData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            List<PropsView.Send> a = new LinkedList<>();

            Map<Long, String> sendMap = ServiceManager.getSqlService().queryMapLongString("select timestamp,send_user_id ,get_user_id  ,item_id,item_count " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c);

            sendMap.forEach((k, v) -> {
                PropsView.Send val = new PropsView.Send();
                val.begin = TimeUtil.ymdhmsFormat().format(k);
                String[] params = v.split("\n", 4);
                val.sendID = Long.parseLong(params[0]);
                val.getID  = Long.parseLong(params[1]);
                val.itemID = Integer.parseInt(params[2]);
                val.props  = ItemManager.getInstance().getItemName(Integer.parseInt(params[2]));
                val.count  = Long.parseLong(params[3]);
                val.time   = TimeUtil.ymdhmsFormat().format(k);
                a.add(val);
            });

            return a;
        } );
    }

    public void querySCardFrom( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PROPS, getSendData( req, resp ),
                SendTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.sendID + "</td>" );
                    pw.write( "<td>" + info.props + "</td>" );
                    pw.write( "<td>" + info.itemID + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.getID + "</td>" );
                    pw.write( "<td>" + info.time + "</td>" );
                }, null );
    }

    public void downloadCardFrom( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PROPS, getSendData( req, resp ),
                SendTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.sendID + "," );
                    pw.write( info.props + "," );
                    pw.write( info.itemID + "," );
                    pw.write( info.count + "," );
                    pw.write( info.getID + "," );
                    pw.write( info.time + "," );
                } );
    }


    private List< PropsView.HuafeiOut > getHuafeiOutData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            PropsView.HuafeiOut val = new PropsView.HuafeiOut();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );

            val.card1   = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10100 ,c );
            val.card2   = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10101 ,c );
            val.card5   = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10102 ,c );
            val.card10  = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10103 ,c );
            val.card20  = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10104 ,c );
            val.card50  = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10105 ,c );
            val.card100 = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10106 ,c );
            val.card200 = ServiceManager.getCommonService().getHuaFeiOutCount( beg , end , 10107 ,c );

            return val;
        } );
    }

    public void queryHuafeiOut( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List<PropsView.HuafeiOut> data  = getHuafeiOutData( req, resp );

        queryList( req, resp,
                CommandList.GET_PROPS,data,
                HuefeiZongTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.card1 + "</td>" );
                    pw.write( "<td>" + info.card2 + "</td>" );
                    pw.write( "<td>" + info.card5 + "</td>" );
                    pw.write( "<td>" + info.card10 + "</td>" );
                    pw.write( "<td>" + info.card20 + "</td>" );
                    pw.write( "<td>" + info.card50 + "</td>" );
                    pw.write( "<td>" + info.card100 + "</td>" );
                } ,null );
    }

    public void downloadHuafeiOut( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List<PropsView.HuafeiOut> data  = getHuafeiOutData( req, resp );

        downloadList( req, resp,
                CommandList.GET_PROPS,
                data,
                HuefeiZongTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.card1 + "," );
                    pw.write( info.card2 + "," );
                    pw.write( info.card5 + "," );
                    pw.write( info.card10 + "," );
                    pw.write( info.card20 + "," );
                    pw.write( info.card50 + "," );
                    pw.write( info.card100 + "," );

                });
    }


    private List< PropsView.Huafei > getHuafeiZongData( HttpServletRequest req, HttpServletResponse resp, boolean isMobile )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            PropsView.Huafei val = new PropsView.Huafei();
            val.begin   = TimeUtil.ymdFormat().format( beg );
            val.end     = TimeUtil.ymdFormat().format( end );
            Map<Long, Long> val2count = ServiceManager.getSqlService().queryMapLongLong( "select card_val, count(id) " +
                    " from t_use_huafei " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    "   and user_info != 'message'" +
                    " group by card_val;", c );

            val.card1   = getLong( val2count, 1 );
            val.card2   = getLong( val2count, 2 );
            val.card5   = getLong( val2count, 5 );
            val.card10  = getLong( val2count, 10 );
            val.card20  = getLong( val2count, 20 );
            val.card50  = getLong( val2count, 50 );
            val.card100 = getLong( val2count, 100 );
            val.card200 = getLong( val2count, 200 );

            return val;
        } );

    }



    private List< PropsView.Huafei > getHuafeiData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            List<PropsView.Huafei> a = new LinkedList<>();
            Map<Long, String> sendMap = ServiceManager.getSqlService().queryMapLongString("select timestamp,user_id ,item_id,phone_num,appid" +
                    " from t_use_huafei " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " and user_info !='message';", c);

            sendMap.forEach((k, v) -> {
                PropsView.Huafei val = new PropsView.Huafei();
                val.begin = TimeUtil.ymdhmsFormat().format(k);
                String[] params = v.split("\n", 4);
                val.userID = Long.parseLong(params[0]);
                if (Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10100 || Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10515)
                {
                    val.card = "1元话费卡";
                }else if (Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10101 || Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10516)
                {
                    val.card = "2元话费卡";
                }else if (Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10102 || Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10517)
                {
                    val.card = "5元话费卡";
                }else if (Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10103 || Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10518)
                {
                    val.card = "10元话费卡";
                }else if (Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10104 || Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10519)
                {
                    val.card = "20元话费卡";
                }else if (Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10105 || Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10520)
                {
                    val.card = "50元话费卡";
                }else if (Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10106 || Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10521)
                {
                    val.card = "100元话费卡";
                }
                val.time = TimeUtil.ymdhmsFormat().format(k);

                val.phonenumber = params[2];
                val.appid = AppSdk.getAppName(params[3]);
                a.add(val);
            });

            return a;
        } );
    }

    public void queryHuafei( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List<PropsView.Huafei> data  = getHuafeiZongData( req, resp, false );

        queryMultiList( req, resp,
                CommandList.GET_PROPS, data,
                HuefeiZongTableHead, ( info, pw ) -> {
                    pw.write("<td>" + info.begin + "</td>");
                    pw.write("<td>" + info.card1 + "</td>");
                    pw.write("<td>" + info.card2 + "</td>");
                    pw.write("<td>" + info.card5 + "</td>");
                    pw.write("<td>" + info.card10 + "</td>");
                    pw.write("<td>" + info.card20 + "</td>");
                    pw.write("<td>" + info.card50 + "</td>");
                    pw.write("<td>" + info.card100 + "</td>");
                    pw.write("<td>" + info.card200 + "</td>");
                },getHuafeiData( req, resp ),
                HuefeiTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write("<td>" + info.appid + "</td>");
                    pw.write( "<td>" + info.userID + "</td>" );
                    pw.write( "<td>" + info.phonenumber + "</td>" );
                    pw.write( "<td>" + info.card+ "</td>" );
                    pw.write( "<td>" + info.time + "</td>" );} ,null );
    }

    public void downloadHuafei( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List<PropsView.Huafei> data  = getHuafeiZongData( req, resp, false );

        downloadMultiList( req, resp,
                CommandList.GET_PROPS, data,
                HuefeiZongTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.card1 + "," );
                    pw.write( info.card2 + "," );
                    pw.write( info.card5 + "," );
                    pw.write( info.card10 + "," );
                    pw.write( info.card20 + "," );
                    pw.write( info.card50 + "," );
                    pw.write( info.card100 + "," );
                    pw.write( info.card200 + "," );
                },getHuafeiData( req, resp ),
                HuefeiTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.appid + "," );
                    pw.write( info.userID + "," );
                    pw.write( info.phonenumber + "," );
                    pw.write( info.card + "," );
                    pw.write( info.time + "," );
                });
    }


    private List< PropsView.Huafei > get1HuafeiData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            List<PropsView.Huafei> a = new LinkedList<>();
            Map<Long, String> sendMap = ServiceManager.getSqlService().queryMapLongString("select timestamp,user_id ,item_id,phone_num,appid" +
                    " from t_use_huafei " +
                    " where " + beg + " <= timestamp and timestamp < " + end + "" +
                    " and user_info ='message';", c);

            sendMap.forEach((k, v) -> {
                PropsView.Huafei val = new PropsView.Huafei();
                val.begin = TimeUtil.ymdhmsFormat().format(k);
                String[] params = v.split("\n", 4);

                if (Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10515 || Long.parseLong(params[1]) == ItemIdUtils.ITEM_ID_10100 )
                {
                    val.card = "1元话费卡";
                    val.userID = Long.parseLong(params[0]);
                    val.time = TimeUtil.ymdhmsFormat().format(k);

                    val.phonenumber = params[2];
                    val.appid = AppSdk.getAppName(params[3]);


                    a.add(val);
                }
            });

            return a;
        } );
    }

    public void query1Huafei( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List<PropsView.Huafei> data  = getHuafeiZongData( req, resp, false );

        queryList( req, resp,
                CommandList.GET_PROPS,get1HuafeiData( req, resp ),
                Huefei1TableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.appid + "</td>" );
                    pw.write( "<td>" + info.phonenumber + "</td>" );
                } ,null );
    }

    public void download1Huafei( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        List<PropsView.Huafei> data  = getHuafeiZongData( req, resp, false );

        downloadList( req, resp,
                CommandList.GET_PROPS,
                get1HuafeiData( req, resp ),
                Huefei1TableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.appid + "," );
                    pw.write( info.phonenumber + "," );
                });
    }

    private List< PropsView.Send > getSendData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getData( req, resp, ( beg, end, c ) -> {
            List<PropsView.Send> a = new LinkedList<>();

            Map<Long, String> sendMap = ServiceManager.getSqlService().queryMapLongString("select timestamp,send_user_id ,get_user_id  ,item_id,item_count " +
                    " from t_important_props " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c);

            sendMap.forEach((k, v) -> {
                PropsView.Send val = new PropsView.Send();
                val.begin = TimeUtil.ymdhmsFormat().format(k);
                String[] params = v.split("\n", 4);
                val.sendID = Long.parseLong(params[0]);
                val.getID = Long.parseLong(params[1]);
                val.itemID = Integer.parseInt(params[2]);
                val.props = ItemManager.getInstance().getItemName(Integer.parseInt(params[2]));
                val.count = Long.parseLong(params[3]);
                val.time = TimeUtil.ymdhmsFormat().format(k);
                a.add(val);
            });

            return a;
        } );
    }

    public void querySend( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PROPS, getSendData( req, resp ),
                SendTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.sendID + "</td>" );
                    pw.write( "<td>" + info.props + "</td>" );
                    pw.write( "<td>" + info.itemID + "</td>" );
                    pw.write( "<td>" + info.count + "</td>" );
                    pw.write( "<td>" + info.getID + "</td>" );
                    pw.write( "<td>" + info.time + "</td>" );
                }, null );
    }

    public void downloadSend( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PROPS, getSendData( req, resp ),
                SendTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.sendID + "," );
                    pw.write( info.props + "," );
                    pw.write( info.itemID + "," );
                    pw.write( info.count + "," );
                    pw.write( info.getID + "," );
                    pw.write( info.time + "," );
                } );
    }


    private List< PropsView.ZongLiang > getZongLiangData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            PropsView.ZongLiang val = new PropsView.ZongLiang();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            // t_possession
            List<String> a = ServiceManager.getSqlService().queryListString( "select items " +
                    " from t_possession " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );

            a.forEach( info -> mergeMap( splitMap( info, ";", "," ), val.daoju ) );

            return val;
        } );
    }

    public void queryZongLiang( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PROPS, getZongLiangData( req, resp ),
                zongliangTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" );
                    info.daoju.forEach( ( itemId, itemCount ) -> {
                        if (itemId == -1 )
                            pw.write("金币:" + itemCount / 10000 + "万<br>" );
                        else
                        if (itemId == -4  )
                            pw.write("钻石:" + itemCount  + "<br>" );
                        else
                            pw.write(ItemManager.getInstance().getItemName( Integer.parseInt( itemId.toString() ))+ ":" + itemCount + "<br>" );
                    } );
                    pw.write( "</td>" );
                } );
    }

    public void downloadZongLiang( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_PROPS, getZongLiangData( req, resp ),
                zongliangTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );

                } );

    }

    private List< PropsView.BiShang > getBiShangSendData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        String limit =  req.getParameter( "limit" ).replace(" ", "");
        if (limit == null) return  new ArrayList<>();
        return getDataList2( req, resp, ( beg, end, c ) -> {
            List< PropsView.BiShang > userList = new ArrayList<>();
            String [] p  = limit.split(",");
            for (int i = 0; i < p.length; i++)
            {
                PropsView.BiShang val = new PropsView.BiShang();
                val.begin = TimeUtil.ymdFormat().format( beg );
                val.user_id  = Long.parseLong(p[i]);
                Map<Long, Long> SHOUMap = ServiceManager.getSqlService().queryMapLongLong("select item_id, coalesce( sum( item_count ), 0 ) " +
                        " from t_important_props " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and get_user_id = " + p[i] + " and send_user_id not in ("+ limit +") group by item_id;", c);
                val.beikeSHOU = getLong( SHOUMap , 20014 );
                val.qingtongSHOU = getLong( SHOUMap , 20015 );
                val.baiyinSHOU = getLong( SHOUMap , 20016 );
                val.huangjinSHOU = getLong( SHOUMap , 20017 );

                Map<Long, Long> CHUMap = ServiceManager.getSqlService().queryMapLongLong("select item_id, coalesce( sum( item_count ), 0 ) " +
                        " from t_important_props " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and send_user_id = " + p[i] + " and get_user_id not in ("+ limit +") group by item_id;", c);
                val.beikeCHU = getLong( CHUMap , 20014 );
                val.qingtongCHU = getLong( CHUMap , 20015 );
                val.baiyinCHU = getLong( CHUMap , 20016 );
                val.huangjinCHU = getLong( CHUMap , 20017 );

                userList.add(val);
            }


            Collections.sort( userList, ( o1, o2 ) -> {
                if( o1.huangjinCHU > o2.huangjinCHU) return -1;
                if( o1.huangjinCHU < o2.huangjinCHU ) return 1;
                if( o1.huangjinSHOU > o2.huangjinSHOU) return -1;
                if( o1.huangjinSHOU < o2.huangjinSHOU ) return 1;
                if( o1.baiyinCHU > o2.baiyinCHU) return -1;
                if( o1.baiyinCHU < o2.baiyinCHU ) return 1;
                return 0;
            } );

            return userList;

        } );
    }
    public void queryBiShangSend( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        queryList( req, resp,
                CommandList.GET_PROPS, getBiShangSendData( req, resp ),
                BiShangSendTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.user_id + "</td>" );
                    pw.write( "<td name='tdUserName_" + info.user_id + "'></td>" );
                    pw.write( "<td>" + info.beikeCHU + "</td>" );
                    pw.write( "<td>" + info.qingtongCHU+ "</td>" );
                    pw.write( "<td>" + info.baiyinCHU + "</td>" );
                    pw.write( "<td>" + info.huangjinCHU + "</td>" );
                    pw.write( "<td>" + info.beikeSHOU + "</td>" );
                    pw.write( "<td>" + info.qingtongSHOU+ "</td>" );
                    pw.write( "<td>" + info.baiyinSHOU + "</td>" );
                    pw.write( "<td>" + info.huangjinSHOU + "</td>" );
                },getFishUserNameScript( "td", "tdUserName_" ) );
    }
    public void downloadBiShangSend( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        downloadList( req, resp,
                CommandList.GET_PROPS, getBiShangSendData( req, resp ),
                BiShangSendTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.user_id + "," );
                    pw.write(  getUserName(info.user_id ) + "," );
                    pw.write( info.beikeCHU + "," );
                    pw.write( info.qingtongCHU+ "," );
                    pw.write( info.baiyinCHU + "," );
                    pw.write( info.huangjinCHU + "," );
                    pw.write( info.beikeSHOU + "," );
                    pw.write( info.qingtongSHOU+ "," );
                    pw.write( info.baiyinSHOU + "," );
                    pw.write( info.huangjinSHOU + "," );
                } );
    }

    private List< PropsView.maifen > getMaiFenBossData(HttpServletRequest req, HttpServletResponse resp )
            throws Exception {
        String limit =  req.getParameter( "limit" );
//        limit=limit.replace(" ", "");
        return getData( req, resp, ( beg, end, c ) -> {
            List<PropsView.maifen> a = new LinkedList<>();

                Map<Long, String> daBossMap = ServiceManager.getSqlService().queryMapLongString("select timestamp, other_info, last_hit,user_get_gold " +
                        " from t_fish_boss " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and boss_id = 0 and level = 0 ;", c);

            if ( !limit.equals("") )
            {
                 daBossMap.clear();
                 daBossMap = ServiceManager.getSqlService().queryMapLongString("select timestamp,other_info ,last_hit, user_get_gold " +
                        " from t_fish_boss " +
                        " where " + beg + " <= timestamp and timestamp < " + end + " and boss_id = 0 and level = 0 and other_info in (" + limit +");", c);
            }

            daBossMap.forEach((k, v) -> {
                PropsView.maifen val = new PropsView.maifen();
                val.begin = TimeUtil.ymdhmsFormat().format(k);
                String[] params = v.split("\n", 3);
                val.zhaohuanID = Long.parseLong(params[0]);
                val.zhaohuanName = UserSdk.getUserName(val.zhaohuanID);
                val.maiId = Integer.parseInt(params[1]);
                val.maiName = UserSdk.getUserName(val.maiId);
                val.gold = Integer.parseInt(params[2]);
                a.add(val);
            });

            return a;
        } );
    }

    public void queryMaiFenBoss( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_PROPS, getMaiFenBossData( req, resp ),
                MaiFenBossTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.zhaohuanID + "</td>" );
                    pw.write( "<td>" + info.zhaohuanName + "</td>" );
                    pw.write( "<td>" + info.maiId + "</td>" );
                    pw.write( "<td>" + info.maiName + "</td>" );
                    pw.write( "<td>" + info.gold + "</td>" );
                }, null );
    }


}


