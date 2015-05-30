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

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.yongf.po.Emp;
import com.yongf.sorm.bean.ColumnInfo;
import com.yongf.sorm.bean.TableInfo;
import com.yongf.sorm.utils.JDBCUtils;
import com.yongf.sorm.utils.ReflectUtils;
import com.yongf.vo.EmpVO;


/**
 * 负责针对mysql数据库的查询
 *  
 * @version 1.0 
 * 2015年5月27日 下午2:46:45       
 * @author ScottWang www.i466.cn
 */
public class MysqlQuery implements Query
{
	public static void testDML()
	{
		Emp e=new Emp();
//		e.setId(4);
		
		e.setEmpname("tom");
		e.setBirthday(new java.sql.Date(System.currentTimeMillis()));
		
		e.setAge(2);
		e.setSalary(4000.8);
		e.setId(1);
		
		new MysqlQuery().update(e,new String[]{"empname","age","salary"});
	}
	
	public static void testQueryRows()
	{
		List<Emp> list=new MysqlQuery().queryRows("select id, empname,age from emp where age>? and salary<?",Emp.class
				,new Object[]{10,5000});
		System.out.println(list);
		for(Emp e:list)
		{
			System.out.println(e.getEmpname());
		}
		
		String sql2="select e.id,e.empname,salary+bonus 'wage',age, d.dname 'deptName',d.address 'deptAddr' from emp e "+
"join dept d on e.deptId=d.id ";
		List<EmpVO> list2=new MysqlQuery().queryRows(sql2,EmpVO.class
				,null);
		System.out.println(list);
		for(EmpVO e:list2)
		{
			System.out.println(e.getEmpname()+"-"+e.getDeptAddr()+"-"+e.getWage());
		}

	}
	
	public static void main(String[] args)
	{
				
	}

	@Override
	public int executeDML(String sql, Object[] params)
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

	@Override
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

	@Override
	public void delete(Class clazz, Object id)
	{
		//Emp.class,2-->delete from emp where id=2;
		//通过Class对象找TableInfo   
		TableInfo tableInfo=TableContext.poClassTableMap.get(clazz);
		//获得主键
		ColumnInfo onlyPriKey=tableInfo.getOnlyPriKey();
		
		String sql="delete from"+tableInfo.getTname()+" where"+onlyPriKey.getName()+"=? ";
		
		executeDML(sql, new Object[]{id});
		
	}

	@Override
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

	@Override
	public int update(Object obj, String[] fieldNames)
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

	@Override
	public List queryRows(String sql, Class clazz, Object[] params)
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
		}finally
		{
			DBManager.close(ps,conn);
		}
		
		return list;
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
 