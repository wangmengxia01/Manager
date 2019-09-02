package com.hoolai.ccgames.bifront.servlet.action;

import com.hoolai.ccgames.bifront.servlet.Constants;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service( "scmjAction" )
public class ScmjAction extends BaseAction {
	
	public void getPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_SCMJ ).forward( req, resp );
	}
	
	public void getBasicPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_SCMJ_BASIC ).forward( req, resp );
	}
	
	public void getRankPage( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getRequestDispatcher( Constants.URL_SCMJ_RANK ).forward( req, resp );
	}
	

}