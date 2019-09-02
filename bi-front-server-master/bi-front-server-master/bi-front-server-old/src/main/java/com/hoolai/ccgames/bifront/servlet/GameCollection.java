package com.hoolai.ccgames.bifront.servlet;

import com.hoolai.centersdk.sdk.AppSdk;
import com.hoolai.centersdk.sdk.CenterGateSdk;

import java.util.*;

public class GameCollection {

	public static List< String > mobileGames = Arrays.asList( "1167896519", "184", "175" ,"234","252","264","190","300","302","1106207849","9889988");

	public static class GameGroup {
		public String id;
		public String name;
		public Map< String, String > games;


		public GameGroup( String id, String name ) {
			this.id = id;
			this.name = name;
			games = new TreeMap< String, String >();
		}
		
		public GameGroup add( String key, String val ) {
			games.put( key, val );
			return this;
		}
		
		public String getId() {
			return id;
		}

		public GameGroup setId( String id ) {
			this.id = id;
			return this;
		}

		public String getName() {
			return name;
		}

		public GameGroup setName( String name ) {
			this.name = name;
			return this;
		}

		public Map< String, String > getGames() {
			return games;
		}

		public GameGroup setGames( Map< String, String > games ) {
			this.games = games;
			return this;
		}
	}
	
	private static List< GameGroup > gameList = new ArrayList< GameGroup >();
	
	public static List< GameGroup > getAll() {
		return gameList;
	}
	
	public static String getName( String key ) {
		for( GameGroup gg : gameList ) {
			if( gg.games.containsKey( key ) ) return gg.games.get( key );
		}
		return null;
	}
	
	public static Map< String, String > getGames( String id ) {
		for( GameGroup gg : gameList ) {
			if( gg.id.equals( id ) ) {
				return gg.games;
			}
		}
		return null;
	}
	
	static {
		GameGroup group = new GameGroup( "TPG", "德州游戏" );
		AppSdk.APPID_APPNAME.forEach( ( k, v ) -> {
			group.add( "TPG-TENCENT-" + k, v );
		} );
		gameList.add( group );
	}
}
