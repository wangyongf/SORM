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
 * 管理配置信息
 *  
 * @version 1.0 
 * 2015年5月26日 上午1:06:29       
 * @author ScottWang www.i466.cn
 */
public class Configuration
{
	/**
	 * 驱动类
	 */
	private String driver;
	
	/**
	 * JDBC的url
	 */
	private String url;
	
	/**
	 * 数据库的用户名
	 */
	private String user;
	
	/**
	 * 数据库的密码
	 */
	private String pwd;
	
	/**
	 * 正在使用哪个数据库
	 */
	private String usingDB;
	
	/**
	 * 项目的源码路径
	 */
	private String srcPath;
	
	/**
	 * 扫描生成java类的包(po的意思是:Persistence Object--持久化对象)
	 */
	private String poPackage;
	
	/**
	 * 项目使用的查询类是哪一个类
	 */
	private String queryClass;
	
	/**
	 * 连接池中最小的连接数
	 */
	private int poolMinSize;
	
	/**
	 * 连接池中最大的连接数
	 */
	private int poolMaxSize;
	
	public int getPoolMinSize()
	{
		return poolMinSize;
	}
	public void setPoolMinSize(int poolMinSize)
	{
		this.poolMinSize = poolMinSize;
	}
	public int getPoolMaxSize()
	{
		return poolMaxSize;
	}
	public void setPoolMaxSize(int poolMaxSize)
	{
		this.poolMaxSize = poolMaxSize;
	}
	public String getDriver()
	{
		return driver;
	}
	public void setDriver(String driver)
	{
		this.driver = driver;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getUser()
	{
		return user;
	}
	public void setUser(String user)
	{
		this.user = user;
	}
	public String getPwd()
	{
		return pwd;
	}
	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}
	public String getUsingDB()
	{
		return usingDB;
	}
	public void setUsingDB(String usingDB)
	{
		this.usingDB = usingDB;
	}
	public String getSrcPath()
	{
		return srcPath;
	}
	public void setSrcPath(String srcPath)
	{
		this.srcPath = srcPath;
	}
	public String getPoPackage()
	{
		return poPackage;
	}
	public void setPoPackage(String poPackage)
	{
		this.poPackage = poPackage;
	}
	public Configuration(String driver, String url, String user, String pwd,
			String usingDB, String srcPath, String poPackage)
	{
		super();
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.usingDB = usingDB;
		this.srcPath = srcPath;
		this.poPackage = poPackage;
	}
	
	public Configuration(String driver, String url, String user, String pwd,
			String usingDB, String srcPath, String poPackage, String queryClass)
	{
		super();
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.usingDB = usingDB;
		this.srcPath = srcPath;
		this.poPackage = poPackage;
		this.queryClass = queryClass;
	}
	
	public String getQueryClass()
	{
		return queryClass;
	}
	public void setQueryClass(String queryClass)
	{
		this.queryClass = queryClass;
	}
	public Configuration()
	{
		super();
	}
	
}
 