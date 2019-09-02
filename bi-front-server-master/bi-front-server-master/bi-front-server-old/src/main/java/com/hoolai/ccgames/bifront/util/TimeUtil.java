package com.hoolai.ccgames.bifront.util;

import com.hoolai.ccgames.bifront.vo.ZhuboView;

import java.text.SimpleDateFormat;
import java.util.*;

public class TimeUtil {
	
	// private static Object lock = new Object();
	// private static ThreadLocal< SimpleDateFormat > ymdhmFormat = new ThreadLocal< SimpleDateFormat >();
	// private static ThreadLocal< SimpleDateFormat > ymdFormat = new ThreadLocal< SimpleDateFormat >();
	
	public static TimeZone defaultZone = TimeZone.getDefault();
	public static final long DAY_MILLIS = 24L * 60 * 60 * 1000;
	public static final long MINUTE_MILLIS = 60L * 1000;

	public static SimpleDateFormat ymdhmFormat() {
		return new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
	}
	
	public static SimpleDateFormat ymdhmsFormat() {
		return new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	}
	
	public static SimpleDateFormat ymdFormat() {
		return new SimpleDateFormat( "yyyy-MM-dd" );
	}
	
	public static List< Long >  splitDays( long begGMT, long endGMT, TimeZone zone ) {
		
		if( zone == null ) zone = defaultZone;
		
		List< Long > rv = new LinkedList< Long >();
		
		Calendar ca = GregorianCalendar.getInstance( zone );
		ca.setTimeInMillis( begGMT );
		ca.add( Calendar.DATE, 1 );
		ca.set( Calendar.HOUR_OF_DAY, 0 );
		ca.set( Calendar.MINUTE, 0 );
		ca.set( Calendar.SECOND, 0 );
		ca.set( Calendar.MILLISECOND, 0 );
		
		rv.add( begGMT );
		long tmp = ca.getTimeInMillis();
		while( tmp < endGMT ) {
			rv.add( tmp );
			ca.add( Calendar.DATE, 1 );
			tmp = ca.getTimeInMillis();
		}
		rv.add( endGMT );
		return rv;
	}
	
	public static List< Long >  splitWeeks( long begGMT, long endGMT, TimeZone zone ) {
		
		if( zone == null ) zone = defaultZone;
		
		List< Long > rv = new LinkedList< Long >();
		
		Calendar ca = GregorianCalendar.getInstance( zone );
		ca.setTimeInMillis( begGMT );
		ca.add( Calendar.WEEK_OF_MONTH, 1 );
		ca.set( Calendar.DAY_OF_WEEK, Calendar.MONDAY );
		ca.set( Calendar.HOUR_OF_DAY, 0 );
		ca.set( Calendar.MINUTE, 0 );
		ca.set( Calendar.SECOND, 0 );
		ca.set( Calendar.MILLISECOND, 0 );
		
		rv.add( begGMT );
		long tmp = ca.getTimeInMillis();
		while( tmp < endGMT ) {
			rv.add( tmp );
			ca.add( Calendar.WEEK_OF_MONTH, 1 );
			tmp = ca.getTimeInMillis();
		}
		rv.add( endGMT );
		return rv;
	}
	
	public static List< Long >  splitMonths( long begGMT, long endGMT, TimeZone zone ) {

		if( zone == null ) zone = defaultZone;
		
		List< Long > rv = new LinkedList< Long >();
		
		Calendar ca = GregorianCalendar.getInstance( zone );
		ca.setTimeInMillis( begGMT );
		ca.add( Calendar.MONTH, 1 );
		ca.set( Calendar.DAY_OF_MONTH, 1 );
		ca.set( Calendar.HOUR_OF_DAY, 0 );
		ca.set( Calendar.MINUTE, 0 );
		ca.set( Calendar.SECOND, 0 );
		ca.set( Calendar.MILLISECOND, 0 );
		
		rv.add( begGMT );
		long tmp = ca.getTimeInMillis();
		while( tmp < endGMT ) {
			rv.add( tmp );
			ca.add( Calendar.MONTH, 1 );
			tmp = ca.getTimeInMillis();
		}
		rv.add( endGMT );
		return rv;
	}
	
	public static List< Long >  splitHours( long begGMT, long endGMT, TimeZone zone ) {

		if( zone == null ) zone = defaultZone;
		
		List< Long > rv = new LinkedList< Long >();
		
		Calendar ca = GregorianCalendar.getInstance( zone );
		ca.setTimeInMillis( begGMT );
		ca.add( Calendar.HOUR_OF_DAY, 1 );
		ca.set( Calendar.MINUTE, 0 );
		ca.set( Calendar.SECOND, 0 );
		ca.set( Calendar.MILLISECOND, 0 );
		
		rv.add( begGMT );
		long tmp = ca.getTimeInMillis();
		while( tmp < endGMT ) {
			rv.add( tmp );
			ca.add( Calendar.HOUR_OF_DAY, 1 );
			tmp = ca.getTimeInMillis();
		}
		rv.add( endGMT );
		return rv;
	}
	
	public static List< Long >  splitTime( long begGMT, long endGMT, TimeZone zone, String type ) {
		switch( type ) {
			case "DAY": return splitDays( begGMT, endGMT, zone );
			case "WEEK": return splitWeeks( begGMT, endGMT, zone );
			case "MONTH": return splitMonths( begGMT, endGMT, zone );
			case "HOUR": return splitHours( begGMT, endGMT, zone );
			case "NONE": return Arrays.asList( (Long) begGMT, (Long) endGMT );
		}
		return null;
	}
	
	public static void main( String[] args ) {
		String a = "[\"1AEF0-B716B-C3AAB-41D38-DB53D\"]";
		a = a.replace("[","").replace("]","").replace("\"","").replace("\"","");
		List<String> list1 = new ArrayList<String>();
		list1.add(a);
		list1.retainAll(ZhuboView.baixiaoheiList);
//		ZhuboView.baixiaoheiList.forEach( k -> System.out.println(k));
		System.out.println(a);
		System.out.println(list1);
		System.out.println(list1.size());

	}
}
