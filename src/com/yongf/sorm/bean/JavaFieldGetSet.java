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
 * 封装了java属性和get,set方法的源代码
 *  
 * @version 1.0 
 * 2015年5月26日 下午8:41:25       
 * @author ScottWang www.i466.cn
 */
public class JavaFieldGetSet
{
	/**
	 * 属性的源码信息.如:private int userId;
	 */
	private String fieldInfo;
	
	/**
	 * get方法的源码信息.如:public int getUserId(){}
	 */
	private String getInfo;
	
	/**
	 * set方法的源码信息.如:public void setUserId(int id){this.id=id;}
	 */
	private String setInfo;
	
	@Override
	public String toString()
	{
		System.out.println(fieldInfo);
		System.out.println(getInfo);
		System.out.println(setInfo);
		
		return super.toString();
	}

	public String getFileInfo()
	{
		return fieldInfo;
	}

	public void setFileInfo(String fileInfo)
	{
		this.fieldInfo = fileInfo;
	}

	public String getGetInfo()
	{
		return getInfo;
	}

	public void setGetInfo(String getInfo)
	{
		this.getInfo = getInfo;
	}

	public String getSetInfo()
	{
		return setInfo;
	}

	public void setSetInfo(String setInfo)
	{
		this.setInfo = setInfo;
	}

	public JavaFieldGetSet(String fileInfo, String getInfo, String setInfo)
	{
		super();
		this.fieldInfo = fileInfo;
		this.getInfo = getInfo;
		this.setInfo = setInfo;
	}

	public JavaFieldGetSet()
	{
		super();
	}
}
 