package com.hoolai.ccgames.bifront.servlet;

import com.google.gson.reflect.TypeToken;
import com.hoolai.ccgames.bifront.starter.Global;
import com.hoolai.ccgames.skeleton.codec.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReportServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory
			.getLogger( ReportServlet.class );

	private static final String key = "EvenGivenEvenSoho";

	private boolean verify( String key, String sign ) {
		return key.equals( sign );
	}

	@Override
	protected void service( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		resp.setCharacterEncoding( "UTF-8" );
		
		String op = req.getParameter( "op" );
		String args = req.getParameter( "args" );
		String sign = req.getParameter( "sign" );

		boolean ret = verify( key, sign );

		logger.info( "ReportServlet receive {} {} verify {}", op, args, ret );

		try {
			List< String > params = JsonUtil.decode( args, new TypeToken< ArrayList< String > >() {}.getType() );
			String[] arr = new String[params.size() + 1];
			arr[0] = op;
			for( int i = 0; i < params.size(); ++i ) {
				arr[i + 1] = params.get( i );
			}
			Global.biService.send( arr );
		}
		catch( Exception e ) {
			logger.error( e.getMessage(), e );
		}
	}
}
