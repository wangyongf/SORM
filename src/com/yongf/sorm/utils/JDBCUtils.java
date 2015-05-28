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

import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * 封装了JDBC查询常用的操作
 *  
 * @version 1.0 
 * 2015年5月26日 上午12:55:27       
 * @author ScottWang www.i466.cn
 */
public class JDBCUtils
{
	/**
	 * 给sql设参
	 * @param ps 预编译sql语句对象
	 * @param params 参数 
	 * @author Scott Wang
	 */
	public static void handleParams(PreparedStatement ps,Object[] params)
	{
		if(params!=null)
		{
			for(int i=0;i<params.length;i++)
			{
				try
				{
					ps.setObject(1+i, params[i]);
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

}
 