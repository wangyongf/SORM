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

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yongf.sorm.bean.ColumnInfo;
import com.yongf.sorm.bean.TableInfo;
import com.yongf.sorm.utils.JDBCUtils;
import com.yongf.sorm.utils.ReflectUtils;


/**
 * 负责查询(对外提供服务的核心类)
 *  
 * @version 1.0 
 * 2015年5月25日 下午4:12:44       
 * @author ScottWang www.i466.cn
 */
@SuppressWarnings("all")
public abstract class Query
{
	/**
	 * 采用模板方法模式将JDBC操作封装成模板,便于重用
	 * @param sql sql语句
	 * @param params sql的参数
	 * @param clazz 记录要封装到的java类
	 * @param back CallBack的实现类实现回调
	 * @return 查询的结果
	 * @author Scott Wang
	 */
	public Object executeQueryTemplate(String sql,Object[] params,Class clazz,CallBack back)
	{
		Connection conn=DBManager.getConn();
		List list=null;	//存储查询结果的容器
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			ps=conn.prepareStatement(sql);
			//给sql设参
			JDBCUtils.handleParams(ps, params);
			System.out.println(ps);
			rs=ps.executeQuery();
			
			return back.doExecute(conn, ps, rs);
			
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}finally
		{
			DBManager.close(ps,conn);
		}
	}
	
	/**
	 * 直接执行一个sql语句
	 * @param sql  sql语句
	 * @param params  参数
	 * @return   执行sql语句后影响记录的行数
	 * @author Scott Wang
	 */
	public int executeDML(String sql,Object[] params)
	{
		Connection conn=DBManager.getConn();
		int count=0;
		PreparedStatement ps=null;
		try
		{
			ps=conn.prepareStatement(sql);
			
			//给sql设参
			JDBCUtils.handleParams(ps, params);
			
			count=ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			DBManager.close(ps,conn);
		}
		
		return count;
	}

	/**
	 * 将一个对象存储到数据库中
	 * 把对象中不为null的属性往数据库中存储!如果数字为null则存0
	 * @param obj 要存储的对象
	 * @author Scott Wang
	 */
	public void insert(Object obj)
	{
		//obj-->表中.      insert into 表名() (id,uname,pwd)  values (?,?,?)
		Class c=obj.getClass();
		List<Object> params=new ArrayList<Object>();	//存储sql的参数对象
		TableInfo tableInfo=TableContext.poClassTableMap.get(c);
		StringBuilder sql=new StringBuilder("insert into "+tableInfo.getTname()+" (");
		int countNotNullField=0;	//计算不为Null的属性值
		Field[] fs=c.getDeclaredFields();
		for(Field f:fs)
		{
			String fieldName=f.getName();
			Object fieldValue=ReflectUtils.invokeGet(fieldName, obj);
			
			if(fieldValue!=null)
			{
				countNotNullField++;
				sql.append(fieldName+",");
				params.add(fieldValue);
			}
		}
		sql.setCharAt(sql.length()-1, ')');
		sql.append(" values (");
		for(int i=0;i<countNotNullField;i++)
		{
			sql.append("?,");
		}
		sql.setCharAt(sql.length()-1, ')');
		
		System.out.println(sql.toString());
		
		executeDML(sql.toString(), params.toArray());
	}
	
	/**
	 * 删除clazz表示类对应的表中的记录(指定主键值id的记录)
	 * @param clazz 跟表对应的类的Class对象
	 * @param id 主键的值
	 * @return 
	 * @author Scott Wang
	 */
	public void delete(Class clazz,Object id)		//delete from User where id=2;
	{
		//Emp.class,2-->delete from emp where id=2;
		//通过Class对象找TableInfo   
		TableInfo tableInfo=TableContext.poClassTableMap.get(clazz);
		//获得主键
		ColumnInfo onlyPriKey=tableInfo.getOnlyPriKey();
		
		String sql="delete from"+tableInfo.getTname()+" where"+onlyPriKey.getName()+"=? ";
		
		executeDML(sql, new Object[]{id});
	}
	
	/**
	 * 删除对象在数据库中对应的记录(对象所在的类对应到表,对象主键的值对应到记录)
	 * @param obj 
	 * @author Scott Wang
	 */
	public void delete(Object obj)
	{
		Class c=obj.getClass();
		TableInfo tableInfo=TableContext.poClassTableMap.get(c);
		//获得主键
		ColumnInfo onlyPriKey=tableInfo.getOnlyPriKey();
		
		//通过反射机制,调用属性对应的get/set方法
		Object priKeyValue=ReflectUtils.invokeGet(onlyPriKey.getName(), obj);
		
		delete(c, priKeyValue);
	}
	
	/**
	 * 更新对象对应的记录,并且只更新指定的属性字段的值
	 * @param obj 所要更新的对象
	 * @param fieldNames 更新的对象的属性列表
	 * @return 执行sql语句后印象记录的行数
	 * @author Scott Wang
	 */
	public int update(Object obj,String[] fieldNames)		//update User set uname=?,pwd=?
	{
		//obj{"uname","pwd"}-->update  表名  set  uname=?,pwd=? where id=?
		Class c=obj.getClass();
		List<Object> params=new ArrayList<Object>();	//存储sql的参数对象
		TableInfo tableInfo=TableContext.poClassTableMap.get(c);
		ColumnInfo priKey=tableInfo.getOnlyPriKey();	//获得唯一的主键
		StringBuilder sql=new StringBuilder("update "+tableInfo.getTname()+" set ");
		
		for(String fname:fieldNames)
		{
			Object fvalue=ReflectUtils.invokeGet(fname,obj);
			params.add(fvalue);
			sql.append(fname+"=?,");
		}
		sql.setCharAt(sql.length()-1, ' ');
		sql.append(" where ");
		sql.append(priKey.getName()+"=? ");
		
		params.add(ReflectUtils.invokeGet(priKey.getName(),obj));	//主键的值
		
		return executeDML(sql.toString(), params.toArray());
	}
	
	/**
	 * 查询返回多行记录,并将每行记录封装到clazz指定的类的对象中
	 * @param sql 查询语句
	 * @param clazz 封装数据的javabean类的Class对象
	 * @param params sql的参数
	 * @return 查询到的结果
	 * @author Scott Wang
	 */
	public List queryRows(final String sql,final Class clazz,final Object[] params)
	{
//		final List list=new ArrayList();		//存储查询结果的容器
		return (List)executeQueryTemplate(sql, params, clazz, new CallBack()
		{
			@Override
			public Object doExecute(Connection conn, PreparedStatement ps,
					ResultSet rs)
			{
				List list=null;
				try
				{
					ResultSetMetaData metaData=rs.getMetaData();
					//多行
					while(rs.next())
					{
						if(null==list)
						{
							list=new ArrayList();
						}
						Object rowObj=clazz.newInstance();	//调用javabean的无参构造器
						
						//多列	select username,pwd,age from user where id>? and age>18
						for(int i=0;i<metaData.getColumnCount();i++)
						{
							String columnName=metaData.getColumnLabel(i+1);
							Object columnValue=rs.getObject(i+1);
							//调用rowObj对象的setUsername方法,将columnValue的值设置进去
							ReflectUtils.invokeSet(rowObj, columnName, columnValue);
							
						}
						list.add(rowObj);
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				return list;
			}
		});
	}
	
	/**
	 * 查询返回一行记录,并将该记录封装到clazz指定的类的对象中
	 * @param sql 查询语句
	 * @param clazz 封装数据的javabean类的Class对象
	 * @param params sql的参数
	 * @return 查询到的结果
	 * @author Scott Wang
	 */
	public Object queryUniqueRow(String sql,Class clazz,Object[] params)
	{
		List list=queryRows(sql,clazz,params);
		return (list==null&&list.size()>0)?null:list.get(0);
	}
	
	/**
	 * 查询返回一个值(一行一列),并将该值返回
	 * @param sql 查询语句
	 * @param params sql的参数
	 * @return 查询到的结果
	 * @author Scott Wang
	 */
	private Object queryValue(String sql,Object[] params)
	{
		return executeQueryTemplate(sql, params, null, new CallBack()
		{
			@Override
			public Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs)
			{
				Object value=null;
				try
				{
					while(rs.next())
					{
						//select count(*) from user
						value=rs.getObject(1);
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				return value;
			}
		});
	}

	
	/**
	 * 查询返回一个数字(一行一列),并将该值返回
	 * @param sql 查询语句
	 * @param params sql的参数
	 * @return 查询到的数字
	 * @author Scott Wang   ISO-8859-1
	 */
	public Number queryNumber(String sql,Object[] params)
	{
		return (Number)queryValue(sql,params);
	}

	/**
	 * 分页查询
	 * @param pageNum 第几页数据
	 * @param size 每页显示多少数据
	 * @return 
	 * @author Scott Wang
	 */
	public abstract Object queryPagenate(int pageNum,int size);
	
}
 