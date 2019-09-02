package com.hoolai.ccgames.bifront.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName CaptchaUtils
 * @Description 验证码操作类,主要对外提供一个生成GIF验证码的方法
 * @author luolifeng@hoolai.com
 * @date 2014-1-16
 */
public class CaptchaUtil {
	// 定义生成的验证码的宽度、高度、
	private static final int width = 120;
	private static final int height = 40;
	private static int length = 5;// 默认随机字符个数
	private static Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
	private static int fontStyle = Font.BOLD | Font.ITALIC;
	private static int fontSize = 28;

	/**
	 * 
	 * @Description: 
	 *               在给定的OutputStream中生成一个带给定字符串的GIF流。要求在此方法之前完成对OutputStream的相关处理
	 *               。此方法处理后的OutputStream将被flush和关闭
	 * @param os
	 * @return String 返回类型
	 * @throws IOException
	 */
	public static boolean getRandGifCode( OutputStream os, String randString ) {
		try {
			length = StringUtils.length( randString );
			AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
			gifEncoder.start( os );
			gifEncoder.setQuality( 10 );
			gifEncoder.setDelay( 100 );
			gifEncoder.setRepeat( 0 );
			gifEncoder.setSize( width, height );
			for( int i = 0; i < length; i++ ) {
				BufferedImage frame = getImage( randString, getRandFont(), getRandColor( 250, 250 ) );
				gifEncoder.addFrame( frame );
				frame.flush();
			}
			gifEncoder.finish();
			os.flush();
			os.close();
			return true;
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return false;
	}

	private static BufferedImage getImage( String randString, Font font,
			Color bgColor ) {
		BufferedImage image = new BufferedImage( width, height,
				BufferedImage.TYPE_INT_RGB );
		Graphics2D g2d = image.createGraphics();// 获得图形上下文
		g2d.setColor( bgColor );// 生成随机背景颜色
		g2d.fillRect( 0, 0, width, height );
		g2d.setColor( getRandColor( 160, 200 ) );// 随机产生N条干扰线，并设置随机颜色，使图象中的认证码不易被探测到
		for( int i = 0; i < 200; i++ ) {
			int x = RandUtil.getInt( 0, width );
			int y = RandUtil.getInt( 0, height );
			int xl = RandUtil.getInt( 0, 10 );
			int yl = RandUtil.getInt( 0, 10 );
			g2d.drawLine( x, y, x + xl, y + yl );
		}
		// 以下生成验证码
		Color fontColor[] = new Color[length];// 随机生成验证码字符串的颜色
		for( int i = 0; i < length; i++ ) {
			fontColor[i] = getRandColor( 110, 130 );
		}
		char[] rands = randString.toCharArray();
		for( int i = 0; i < length; i++ ) {
			AlphaComposite ac = AlphaComposite
					.getInstance( AlphaComposite.SRC_IN, 1 );
			g2d.setFont( font );
			g2d.setComposite( ac );
			g2d.setColor( fontColor[i] );
			g2d.drawString( rands[i] + "", i == 0 ? 5 : 20 * i
					+ RandUtil.getInt( 0, 5 ), 20 + RandUtil.getInt( 5, 15 ) );
		}
		g2d.dispose();
		return image;
	}

	/**
	 * 
	 * @Description: 随机获取系统支持的字体
	 * @return Font 返回类型
	 */
	private static Font getRandFont() {
		Font font = fonts[ RandUtil.nextInt( fonts.length ) ];
		return new Font( font.getName(), fontStyle, fontSize );
	}

	/**
	 * 
	 * @返回值:
	 * @描述:获得随机色
	 */
	private static Color getRandColor( int fc, int bc ) {// 给定范围获得随机颜色
		if( fc < 0 )
			fc = 0;
		if( fc > 255 )
			fc = 255;
		if( bc < 0 )
			bc = 0;
		if( bc > 255 )
			bc = 255;
		int r = RandUtil.getInt( fc, bc );
		int g = RandUtil.getInt( fc, bc );
		int b = RandUtil.getInt( fc, bc );
		return new Color( r, g, b );
	}
}
