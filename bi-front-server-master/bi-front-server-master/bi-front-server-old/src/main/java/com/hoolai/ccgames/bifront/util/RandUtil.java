package com.hoolai.ccgames.bifront.util;

import java.util.Random;

public class RandUtil {

	private static Random rand;
	static {
		rand = new Random();
		rand.setSeed( System.nanoTime() );
	}
	
	public static int getInt() {
		return rand.nextInt();
	}
	
	public static int nextInt( int max ) {
		return rand.nextInt( max );
	}
	
	public static int getInt( int min, int max ) {
		if( min > max ) {
			int tmp = min;
			min = max;
			max = tmp;
		}
		return rand.nextInt( max - min + 1 ) + min;
	}
	
	public static String getString( String range, int length ) {
		StringBuilder sb = new StringBuilder();
		for( int i = 0; i < length; ++i )
			sb.append( range.charAt( rand.nextInt( range.length() ) ) );
		return sb.toString();
	}
}
