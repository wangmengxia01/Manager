package com.hoolai.ccgames.bifront.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

	/**
	 * 给定的参数列表中。全部都不为空。才返回true.否则情况返回false。和isAnyEmpty()作用相反
	 * 
	 * @param objects 对象列表
	 * @return boolean 返回类型
	 */
	public static boolean isNoneEmpty( Object... objects ) {
		if( objects == null ) {
			return false;
		}
		for( Object object : objects ) {
			if( isEmpty( object ) ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 给定的参数列表中。有一个为空。就返回true.否则情况返回false。和isNoneEmpty()作用相反.
	 * 
	 * @param objects 对象列表
	 * @return boolean 返回类型
	 */
	public static boolean isAnyEmpty( Object... objects ) {
		return !isNoneEmpty( objects );
	}

	/**
	 * 检查对象是否为空，和notEmpty()作用相反
	 * 
	 * @param obj
	 *            要检查的数据(数据类型:
	 *            String、Number、Boolean、Collection、Map、Object[]、Object)
	 * @return true: 为空; false: 不为空 <li>String：值为 null、""、并且在trim()后还为空时返回 true
	 *         <li>Number：值为 null、0 时返回 true <li>Boolean：值为 null、false 时返回 true
	 *         <li>Collection：值为 null、size=0 时返回 true <li>Map：值为 null、size=0 时返回
	 *         true <li>Object[]：值为 null、length=0 时返回 true <li>Object：值为 null
	 *         时返回 true
	 */
	public static boolean isEmpty( Object obj ) {
		if( obj == null ) {
			return true;
		}
		else if( obj instanceof String
				&& ( obj.equals( "" ) || obj.toString().trim().equals( "" ) ) ) {
			return true;
		}
		else if( obj instanceof Number && ( (Number) obj ).doubleValue() == 0 ) {
			return true;
		}
		else if( obj instanceof Boolean && !( (Boolean) obj ) ) {
			return true;
		}
		else if( obj instanceof Collection
				&& ( (Collection< ? >) obj ).isEmpty() ) {
			return true;
		}
		else if( obj instanceof Map && ( (Map< ?, ? >) obj ).isEmpty() ) {
			return true;
		}
		else if( obj instanceof Object[] && ( (Object[]) obj ).length == 0 ) {
			return true;
		}
		return false;
	}

	/**
	 * 检查对象是否不为空,和isEmpty()作用相反
	 * 
	 * @param obj
	 *            要检查的数据(数据类型:
	 *            String、Number、Boolean、Collection、Map、Object[]、Object)
	 * @return false: 为空; true: 不为空 <li>String：值为 null、""、"0"，并且在trim()后还为空时返回
	 *         false <li>Number：值为 null、0 时返回 false <li>Boolean：值为 null、false
	 *         时返回 false <li>Collection：值为 null、size=0 时返回 false <li>Map：值为
	 *         null、size=0 时返回 false <li>Object[]：值为 null、length=0 时返回 false <li>
	 *         Object：值为 null 时返回 false
	 */
	public static boolean isNotEmpty( Object obj ) {
		return !isEmpty( obj );
	}

	/**
	 * 给定的参数列表中。全部都不为Blank。才返回true.否则情况返回false。内层调用的是StringUtils.isBlank()
	 * 
	 * @param strs
	 * @return boolean 返回类型
	 */
	public static boolean isNoneBlank( String... strs ) {
		if( strs == null ) {
			return false;
		}
		for( String str : strs ) {
			if( StringUtils.isBlank( str ) ) {
				return false;
			}
		}
		return true;
	}
}
