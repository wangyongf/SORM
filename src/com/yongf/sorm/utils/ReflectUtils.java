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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 封装了反射常用的操作
 *  
 * @version 1.0 
 * 2015年5月26日 上午12:56:44       
 * @author ScottWang www.i466.cn
 */
public class ReflectUtils
{
	/**
	 * 调用obj对象对应属性fieldName的get方法
	 * @param fieldName
	 * @param obj
	 * @return 
	 * @author Scott Wang
	 */
	public static Object invokeGet(String fieldName,Object obj)
	{
		try
		{
			Class c=obj.getClass();
			Method m=c.getDeclaredMethod("get"+StringUtils.firstChar2UpperCase(fieldName), null);
			return m.invoke(obj, null);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static void invokeSet(Object obj,String columnName,Object columnValue)
	{
		try
		{
			if(columnValue!=null)
			{
				Method m=obj.getClass().getDeclaredMethod("set"+StringUtils.firstChar2UpperCase(columnName), columnValue.getClass());
				m.invoke(obj, columnValue);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

}
 