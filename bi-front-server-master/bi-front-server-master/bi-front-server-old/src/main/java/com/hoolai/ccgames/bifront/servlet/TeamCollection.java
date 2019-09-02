package com.hoolai.ccgames.bifront.servlet;

import java.util.Map;
import java.util.TreeMap;

public class TeamCollection {
	
	private static Map< String, String > teamMap = new TreeMap< String, String >();
	
	public static Map< String, String > getTeamMap() {
		return teamMap;
	}
	
	public static String getName( String key ) {
		return teamMap.get( key );
	}
	
	static {
		teamMap.put( "TPG", "德州项目" );
		teamMap.put( "DWBG", "电玩吧项目" );
	}
}
