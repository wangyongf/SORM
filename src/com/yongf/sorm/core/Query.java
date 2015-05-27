/* 
* Copyright (c) 1990-2090 Founder Ltd. All Rights Reserved. 
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


/**
 * 负责查询(对外提供服务的核心类)
 *  
 * @version 1.0 
 * 2015年5月25日 下午4:12:44       
 * @author ScottWang www.i466.cn
 */
@SuppressWarnings("all")
public interface Query
{
	/**
	 * 直接执行一个sql语句
	 * @param sql  sql语句
	 * @param params  参数
	 * @return   执行sql语句后影响记录的行数
	 * @author Scott Wang
	 */
	public int executeDML(String sql,Object[] params);

	/**
	 * 将一个对象存储到数据库中
	 * @param obj 要存储的对象
	 * @author Scott Wang
	 */
	public void insert(Object obj);
	
	/**
	 * 删除clazz表示类对应的表中的记录(指定主键值id的记录)
	 * @param clazz 跟表对应的类的Class对象
	 * @param id 主键的值
	 * @return 
	 * @author Scott Wang
	 */
	public void delete(Class clazz,Object id);		//delete from User where id=2;
	
	/**
	 * 删除对象在数据库中对应的记录(对象所在的类对应到表,对象主键的值对应到记录)
	 * @param obj 
	 * @author Scott Wang
	 */
	public void delete(Object obj);
	
	/**
	 * 更新对象对应的记录,并且只更新指定的属性字段的值
	 * @param obj 所要更新的对象
	 * @param fieldNames 更新的对象的属性列表
	 * @return 执行sql语句后印象记录的行数
	 * @author Scott Wang
	 */
	public int update(Object obj,String[] fieldNames);		//update User set uname=?,pwd=?
	
	/**
	 * 查询返回多行记录,并将每行记录封装到clazz指定的类的对象中
	 * @param sql 查询语句
	 * @param clazz 封装数据的javabean类的Class对象
	 * @param params sql的参数
	 * @return 查询到的结果
	 * @author Scott Wang
	 */
	public List queryRows(String sql,Class clazz,Object[] params);
	
	/**
	 * 查询返回一行记录,并将该记录封装到clazz指定的类的对象中
	 * @param sql 查询语句
	 * @param clazz 封装数据的javabean类的Class对象
	 * @param params sql的参数
	 * @return 查询到的结果
	 * @author Scott Wang
	 */
	public Object queryUniqueRow(String sql,Class clazz,Object[] params);
	
	/**
	 * 查询返回一个值(一行一列),并将该值返回
	 * @param sql 查询语句
	 * @param params sql的参数
	 * @return 查询到的结果
	 * @author Scott Wang
	 */
	public Object queryValue(String sql,Object[] params);
	
	/**
	 * 查询返回一个数字(一行一列),并将该值返回
	 * @param sql 查询语句
	 * @param params sql的参数
	 * @return 查询到的数字
	 * @author Scott Wang   ISO-8859-1
	 */
	public Number queryNumber(String sql,Object[] params);
}
 