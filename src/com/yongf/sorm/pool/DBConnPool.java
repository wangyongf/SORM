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
package com.yongf.sorm.pool; 

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yongf.sorm.core.DBManager;


/**
 * 连接池的类
 *  
 * @version 1.0 
 * 2015年5月31日 上午11:56:01       
 * @author ScottWang www.i466.cn
 */
public class DBConnPool
{
	/**
	 * 连接池对象
	 */
	private List<Connection> pool;	
	
	/**
	 * 最大连接数
	 */
	private static final int POOL_MAX_SIZE=DBManager.getConf().getPoolMaxSize();	
	
	/**
	 * 最小连接数
	 */
	private static final int POOL_MIN_SIZE=DBManager.getConf().getPoolMinSize();	
	
	/**
	 * 用来初始化连接池,使池中的连接数达到最小值
	 *  
	 * @author Scott Wang
	 */
	public void initPool()
	{
		if(null==pool)
		{
			pool=new ArrayList();
		}
		while(pool.size()<DBConnPool.POOL_MIN_SIZE)
		{
			pool.add(DBManager.createConn());
			System.out.println("初始化池,池中连接数:"+pool.size());
		}
	}
	
	/**
	 * 从连接池中取出一个连接
	 * @return 
	 * @author Scott Wang
	 */
	public synchronized Connection getConneciton()
	{
		int last_index=pool.size()-1;
		Connection conn=pool.get(last_index);
		pool.remove(last_index);
		return conn;
	}
	
	/**
	 * 将连接放回池中
	 * @param conn 
	 * @author Scott Wang
	 */
	public synchronized void close(Connection conn)
	{
		if(pool.size()>=POOL_MAX_SIZE)
		{
			try
			{
				if(conn!=null)
				{
					conn.close();
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			pool.add(conn);
		}
	}
	
	public DBConnPool()
	{
		initPool();
	}
}
 