package com.hoolai.ccgames.bifront.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger( LoginFilter.class );

	@Override
	public void doFilter( ServletRequest req, ServletResponse resp,
			FilterChain chain ) throws IOException, ServletException {

		resp.setCharacterEncoding( "UTF-8" );
		logger.info( "doFilter receive {}", req.toString() );

		String input = req.getParameter( "input" );

		if( ( input == null || Constants.ACT_SYSTEM_LOGIN.equals( input ) )
				&& isLoginSuccess( req ) ) {
			req.getRequestDispatcher( Constants.URL_TPG_MAIN ).forward( req, resp );
			return;
		}

		if( isLoginRequest( input ) || isLoginSuccess( req ) ) {
			chain.doFilter( req, resp );
			return;
		}
		req.getRequestDispatcher( Constants.URL_LOGIN ).forward( req, resp );
	}

	public boolean isLoginRequest( String req ) {
		if( req == null )
			return false;
		switch (req) {
			case Constants.ACT_SYSTEM_LOGIN:
			case Constants.ACT_SYSTEM_LOGOUT:
			case Constants.ACT_SYSTEM_CAPTCHA:
				return true;
		}
		return false;
	}

	public boolean isLoginSuccess( ServletRequest req ) {
		HttpServletRequest req0 = (HttpServletRequest) req;
		return req0.getSession().getAttribute( Constants.KEY_USER_INFO ) != null;
	}

	@Override
	public void init( FilterConfig arg0 ) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	public static void main( String[] args ) {
		System.out.println( new LoginFilter().isLoginRequest( null ) );

	}
}
