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
package com.yongf.sorm.core; 


/**
 * 负责java数据类型和数据库数据类型的互相转化
 *  
 * @version 1.0 
 * 2015年5月26日 上午12:48:57       
 * @author ScottWang www.i466.cn
 */
public interface TypeConvertor
{
	/**
	 * 将数据库数据类型转化成java的数据类型
	 * @param columnType 数据库字段的数据类型
	 * @return java的数据类型
	 * @author Scott Wang
	 */
	public String databaseType2JavaType(String columnType);

	/**
	 * 负责将java数据类型转化成数据库数据类型
	 * @param javaDataType java数据类型
	 * @return 数据库类型
	 * @author Scott Wang
	 */
	public String javaType2DatabaseType(String javaDataType);
}
 