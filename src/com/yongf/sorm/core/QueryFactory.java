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
 * 创建Query对象的工厂类
 *  
 * @version 1.0 
 * 2015年5月30日 下午11:32:53       
 * @author ScottWang www.i466.cn
 */
public class QueryFactory
{
	private static Query prototypeObj;	//原型对象
	static
	{
		
		
		try
		{
			Class c=Class.forName(DBManager.getConf().getQueryClass());	//加载指定的query类
			prototypeObj=(Query)c.newInstance();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 私有构造器
	 */
	private QueryFactory()
	{
		
	}
	
	
	
	public static Query createQuery()
	{
		try
		{
			return (Query)prototypeObj.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return null;
		} 
	}

}
 