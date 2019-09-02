package com.hoolai.ccgames.bifront.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoolai.ccgames.bifront.starter.Global;

public class MessageServlet extends HttpServlet {

	private static final long serialVersionUID = -8372309334481349608L;
	private static final Logger logger = LoggerFactory
			.getLogger( MessageServlet.class );

	@Override
	protected void service( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		resp.setCharacterEncoding( "UTF-8" );
		
		String input = req.getParameter( "input" );
		
		logger.info( "MessageServlet receive {} {}", req.getRequestURI().toString(), input );
		
		try {
			String[] args = input.split( "_" );
			if( args.length != 2 )
				return;
			String action = args[0];
			String method = args[1];
			Object control = Global.context.getBean( action + "Action" );
			Method actionMethod = control
					.getClass()
					.getMethod( method, HttpServletRequest.class, HttpServletResponse.class );
			actionMethod.invoke( control, req, resp );
		}
		catch( Exception e ) {
			logger.error( e.getMessage(), e );
		}
	}
}
