/* 
 * Copyright © 1990-2090 www.i466.cn. All Rights Reserved. 
 * 
 * This software is the confidential and proprietary information of 
 * Founder. You shall not disclose such Confidential Information 
 * and shall use it only in accordance with the terms of the agreements 
 * you entered into with Founder. 
 * 
 * by ScottWang www.i466.cn
 */ 
package com.yongf.sorm.utils; 


/**
 * 封装了字符串常用的操作
 *  
 * @version 1.0 
 * 2015年5月26日 上午12:56:08       
 * @author ScottWang www.i466.cn
 */
public class StringUtils
{
	/**
	 * 将目标字符串首字母变为大写
	 * @param str 目标字符串
	 * @return 首字母变为大写的字符串
	 * @author Scott Wang
	 */
	public static String firstChar2UpperCase(String str)
	{
		//abcd-->Abcd
		//abcd-->ABCD-->A
		return str.toUpperCase().substring(0, 1)+str.substring(1);
	}

}
 