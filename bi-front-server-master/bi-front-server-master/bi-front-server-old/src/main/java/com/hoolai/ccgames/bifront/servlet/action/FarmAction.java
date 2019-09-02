package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.FarmView;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service( "farmAction" )
public class FarmAction extends BaseAction {

    private static List< String > basicTableHead = Arrays.asList( "日期", "净抽水", "开启玩法营收", "卖地营收", "矿石回收支出", "挖矿人数", "钻石产出", "钻石交易量", "钻石交易人数", "钻石交易税收" );

    public void getPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FARM ).forward( req, resp );
    }

    public void getBasicPage( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        req.getRequestDispatcher( Constants.URL_FARM_BASIC ).forward( req, resp );
    }

    private List< FarmView.Basic > getBasicData( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        return getDataList( req, resp, ( beg, end, c ) -> {
            FarmView.Basic val = new FarmView.Basic();
            val.begin = TimeUtil.ymdFormat().format( beg );
            val.end = TimeUtil.ymdFormat().format( end );
            val.openIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( cost ), 0 ) " +
                    " from t_farm_ticket " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.landextIncome = ServiceManager.getSqlService().queryLong( "select coalesce( sum( cost ), 0 ) " +
                    " from t_farm_landext " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.orePay = ServiceManager.getSqlService().queryLong( "select coalesce( sum( total ), 0 ) " +
                    " from t_farm_sell " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.miningPeople = ServiceManager.getSqlService().queryLong( "select count( distinct user_id ) " +
                    " from t_farm_mining " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.diamondProduce = ServiceManager.getSqlService().queryLong( "select coalesce( sum( amount ), 0 ) " +
                    " from t_farm_produce " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.diamondTradeNum = ServiceManager.getSqlService().queryLong( "select coalesce( sum( amount ), 0 ) " +
                    " from t_farm_exchange " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.diamondTradePeople = ServiceManager.getSqlService().queryLong( "select count( * ) " +
                    " from ( select distinct user_id " +
                    "        from t_farm_exchange_pump " +
                    "        where " + beg + " <= timestamp and timestamp < " + end +
                    "        union " +
                    "        select distinct buyer_id " +
                    "        from t_farm_exchange_pump " +
                    "        where " + beg + " <= timestamp and timestamp < " + end + " ) as tmp", c );
            val.diamondTradeTax = ServiceManager.getSqlService().queryLong( "select coalesce( sum( pump ) ) " +
                    " from t_farm_exchange_pump " +
                    " where " + beg + " <= timestamp and timestamp < " + end + ";", c );
            val.netPump = val.openIncome + val.landextIncome - val.orePay + val.diamondTradeTax;
            return val;
        } );
    }

    public void queryBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        queryList( req, resp,
                CommandList.GET_FARM_BASIC, getBasicData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "</td>" );
                    pw.write( "<td>" + info.netPump + "</td>" );
                    pw.write( "<td>" + info.openIncome + "</td>" );
                    pw.write( "<td>" + info.landextIncome + "</td>" );
                    pw.write( "<td>" + info.orePay + "</td>" );
                    pw.write( "<td>" + info.miningPeople + "</td>" );
                    pw.write( "<td>" + info.diamondProduce + "</td>" );
                    pw.write( "<td>" + info.diamondTradeNum + "</td>" );
                    pw.write( "<td>" + info.diamondTradePeople + "</td>" );
                    pw.write( "<td>" + info.diamondTradeTax + "</td>" );
                } );

    }

    public void downloadBasic( HttpServletRequest req, HttpServletResponse resp )
            throws Exception {

        downloadList( req, resp,
                CommandList.GET_FARM_BASIC, getBasicData( req, resp ),
                basicTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "," );
                    pw.write( info.netPump + "," );
                    pw.write( info.openIncome + "," );
                    pw.write( info.landextIncome + "," );
                    pw.write( info.orePay + "," );
                    pw.write( info.miningPeople + "," );
                    pw.write( info.diamondProduce + "," );
                    pw.write( info.diamondTradeNum + "," );
                    pw.write( info.diamondTradePeople + "," );
                    pw.write( info.diamondTradeTax + "," );
                } );
    }

}