package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bi.protocol.CommandList;
import com.hoolai.ccgames.bifront.service.ServiceManager;
import com.hoolai.ccgames.bifront.servlet.Constants;
import com.hoolai.ccgames.bifront.starter.Global;
import com.hoolai.ccgames.bifront.util.TimeUtil;
import com.hoolai.ccgames.bifront.vo.RonglianView;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service( "ronglianAction" )
public class RonglianAction extends BaseAction {
	
	private static List< String > summaryTableHead = Arrays.asList( "日期",
			"道具ID", "道具名称", "掉落&合成数量" );
	
	public void getPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_RONGLIAN ).forward( req, resp );
	}
	
	public void getSummaryPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_RONGLIAN_SUMMARY ).forward( req, resp );
	}
	
	private List< RonglianView.Summary > getSummaryData( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

		return getData( req, resp, ( beg, end, c ) -> {

			Map< Long, Long > joinMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                    " from t_ronglian_join " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by item_id;", c );
            Map< Long, Long > dropMap = ServiceManager.getSqlService().queryMapLongLong( "select item_id, sum( item_count ) " +
                    " from t_ronglian_drop " +
                    " where " + beg + " <= timestamp and timestamp < " + end +
                    " group by item_id;", c );

            Map< Long, RonglianView.Summary > sum = new TreeMap();
            joinMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new RonglianView.Summary() );
                sum.get( k ).joinCount = v;
            } );
            dropMap.forEach( ( k, v ) -> {
                if( sum.get( k ) == null ) sum.put( k, new RonglianView.Summary() );
                sum.get( k ).dropCount = v;
            } );

			List< RonglianView.Summary > list = new LinkedList();
            sum.forEach( ( k, v ) -> {
                v.begin = TimeUtil.ymdFormat().format( beg );
                v.end = TimeUtil.ymdFormat().format( end );
                v.itemId = k;
                v.itemName = Global.itemRegistry.getOrId( k );
                list.add( v );
            } );
			return list;
		} );
	}
	
	public void querySummary( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

	    queryList( req, resp,
                CommandList.GET_RONGLIAN_SUMMARY, getSummaryData( req, resp ),
                summaryTableHead, ( info, pw ) -> {
                    pw.write( "<td>" + info.begin + "~" + info.end + "</td>" );
                    pw.write( "<td>" + info.itemId + "</td>" );
                    pw.write( "<td>" + info.itemName + "</td>" );
                    pw.write( "<td>" + (info.dropCount + info.joinCount) + "</td>" );
                } );
	}
	
	public void downloadSummary( HttpServletRequest req, HttpServletResponse resp )
			throws Exception {

        downloadList( req, resp,
                CommandList.GET_RONGLIAN_SUMMARY, getSummaryData( req, resp ),
                summaryTableHead, ( info, pw ) -> {
                    pw.write( info.begin + "~" + info.end + "," );
                    pw.write( info.itemId + "," );
                    pw.write( info.itemName + "," );
                    pw.write( (info.dropCount + info.joinCount) + "," );
                } );
	}
	
}