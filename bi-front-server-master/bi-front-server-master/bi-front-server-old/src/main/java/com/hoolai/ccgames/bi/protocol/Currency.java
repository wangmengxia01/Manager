package com.hoolai.ccgames.bi.protocol;

public class Currency {

	public static final String RMB_YUAN = "RMB_YUAN";  // 人民币元
	public static final String RMB_JIAO = "RMB_JIAO";  // 人民币角
	public static final String RMB_FEN = "RMB_FEN";    // 人民币分
	
	public static final String QQ_QB = "QQ_QB";           // Q币
	public static final String QQ_QPOINT = "QQ_QPOINT";   // Q点
	public static final String QQ_QP_1_10 = "QQ_QP_1_10"; // 0.1 Q点
	
	
	/**
	 * @param money 货币数量
	 * @param unit 货币单位
	 * @return 转换为基本单位，目前考虑用人民币0.0001元作为基本单位
	 */
	public static long getBaseAmount( long money, String unit ) {
		switch( unit ) {
			case RMB_YUAN: return money * 10000L;
			case RMB_JIAO: return money * 1000L;
			case RMB_FEN: return money * 100L;
			case QQ_QB: return money * 10000L;
			case QQ_QPOINT: return money * 1000L;
			case QQ_QP_1_10: return money * 100L;
		}
		throw new RuntimeException( "Currency unit " + unit + " is not supported" );
	}
	
	/**
	 * @param money 基本单位货币量
	 * @return 人民币计数表示
	 */
	public static String format( long money ) {
		return String.format( "%.2f", ( money / 100 ) / 100.0 );
	}
	
	public static void main( String[] args ){
		System.out.println( Currency.format( 30000000000L ) );
	} 
}
