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

import java.util.List;

import com.yongf.sorm.bean.TableInfo;


/**
 * 负责针对mysql数据库的查询
 *  
 * @version 1.0 
 * 2015年5月27日 下午2:46:45       
 * @author ScottWang www.i466.cn
 */
public class MysqlQuery implements Query
{

	@Override
	public int executeDML(String sql, Object[] params)
	{
		return 0;
	}

	@Override
	public void insert(Object obj)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Class clazz, int id)
	{
		//Emp.class,2-->delete from emp where id=2;
		
		//通过Class对象找TableInfo   
		TableInfo tableInfo=TableContext.poClassTableMap.get(clazz);
		
	}

	@Override
	public void delete(Object obj)
	{
		
	}

	@Override
	public int update(Object obj, String[] fieldNames)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List queryRows(String sql, Class clazz, Object[] params)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object queryUniqueRow(String sql, Class clazz, Object[] params)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object queryValue(String sql, Object[] params)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number queryNumber(String sql, Object[] params)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
 