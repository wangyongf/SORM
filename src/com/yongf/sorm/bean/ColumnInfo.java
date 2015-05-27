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
package com.yongf.sorm.bean; 


/**
 * 封装表中一个字段的信息
 *  
 * @version 1.0 
 * 2015年5月26日 上午1:02:26       
 * @author ScottWang www.i466.cn
 */
public class ColumnInfo
{
	/**
	 * 字段名称
	 */
	private String name;

	/**
	 * 字段的数据类型
	 */
	private String dataType;
	
	/**
	 * 字段的键类型(0:普通	1:主键	2:外键)
	 */
	private int keyType;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDataType()
	{
		return dataType;
	}

	public void setDataType(String dataType)
	{
		this.dataType = dataType;
	}

	public int getKeyType()
	{
		return keyType;
	}

	public void setKeyType(int keyType)
	{
		this.keyType = keyType;
	}

	public ColumnInfo(String name, String dataType, int keyType)
	{
		super();
		this.name = name;
		this.dataType = dataType;
		this.keyType = keyType;
	}

	public ColumnInfo()
	{
		super();
	}
}
 