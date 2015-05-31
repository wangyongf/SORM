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

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.yongf.sorm.bean.Configuration;
import com.yongf.sorm.pool.DBConnPool;


/**
 * 根据配置信息:维持连接对象的管理(增加连接池功能)
 *  
 * @version 1.0 
 * 2015年5月26日 上午12:54:16       
 * @author ScottWang www.i466.cn
 */
public class DBManager
{
	/**
	 * 配置信息
	 */
	private static Configuration conf;
	
	/**
	 * 连接池对象
	 */
	private static DBConnPool pool;
	static	//静态代码块==>只在加载类的时候执行一次!!!
	{
		Properties pros=new Properties();
		try
		{
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		conf=new Configuration();
		conf.setDriver(pros.getProperty("driver"));
		conf.setPoPackage(pros.getProperty("poPackage"));
		conf.setPwd(pros.getProperty("pwd"));
		conf.setSrcPath(pros.getProperty("srcPath"));
		conf.setUrl(pros.getProperty("url"));
		conf.setUser(pros.getProperty("user"));
		conf.setUsingDB(pros.getProperty("usingDB"));
		conf.setQueryClass(pros.getProperty("queryClass"));
		conf.setPoolMaxSize(Integer.parseInt(pros.getProperty("poolMaxSize")));
		conf.setPoolMinSize(Integer.parseInt(pros.getProperty("poolMinSize")));
		
		
	}

	/**
	 * 获得Connection对象
	 * @return 
	 * @author Scott Wang
	 */
	public static Connection getConn()
	{
//		try
//		{
//			Class.forName(conf.getDriver());
//			return DriverManager.getConnection(conf.getUrl(),conf.getUser(),conf.getPwd());	//目前直接建立连接,后期增加连接池处理,提高效率!!!
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			return null;
//		}
		if(pool==null)
		{
			pool=new DBConnPool();
		}
		return pool.getConneciton();
		
	}
	
	/**
	 * 创建新的Connection对象
	 * @return 
	 * @author Scott Wang
	 */
	public static Connection createConn()
	{
		try
		{
			Class.forName(conf.getDriver());
			return DriverManager.getConnection(conf.getUrl(),conf.getUser(),conf.getPwd());	//目前直接建立连接,后期增加连接池处理,提高效率!!!
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 关闭传入的ResultSet,PreparedStatement,Connection对象
	 * @param rs
	 * @param ps
	 * @param conn 
	 * @author Scott Wang
	 */
	public static void close(ResultSet rs,Statement ps,Connection conn)
	{
		try
		{
			if(rs!=null)
			{
				rs.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		try
		{
			if(ps!=null)
			{
				ps.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
//		try
//		{
//			if(conn!=null)
//			{
//				conn.close();
//			}
//			
//		}
//		catch(SQLException e)
//		{
//			e.printStackTrace();
//		}
		pool.close(conn);
	}

	/**
	 * 关闭传入的PreparedStatement,Connection对象
	 * @param ps
	 * @param conn 
	 * @author Scott Wang
	 */
	public static void close(Statement ps,Connection conn)
	{
		try
		{
			if(ps!=null)
			{
				ps.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
//		try
//		{
//			if(conn!=null)
//			{
//				conn.close();
//			}
//		}
//		catch(SQLException e)
//		{
//			e.printStackTrace();
//		}
		pool.close(conn);
	}
	
	/**
	 * 关闭传入的Connection对象
	 * @param conn 
	 * @author Scott Wang
	 */
	public static void close(Connection conn)
	{
//		try
//		{
//			if(conn!=null)
//			{
//				conn.close();
//			}
//		}
//		catch(SQLException e)
//		{
//			e.printStackTrace();
//		}
		pool.close(conn);
	}
	
	/**
	 * 返回Configuration对象
	 * @return 
	 * @author Scott Wang
	 */
	public static Configuration getConf()
	{
		return conf;
	}
}
 