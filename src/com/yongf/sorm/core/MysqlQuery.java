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

import com.yongf.po.Emp;
import com.yongf.vo.EmpVO;


/**
 * 负责针对mysql数据库的查询
 *  
 * @version 1.0 
 * 2015年5月27日 下午2:46:45       
 * @author ScottWang www.i466.cn
 */
public class MysqlQuery extends Query
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
		System.out.println(list2);
		for(EmpVO e:list2)
		{
			System.out.println(e.getEmpname()+"-"+e.getDeptAddr()+"-"+e.getWage());
		}
	}
	
	@Override
	public Object queryPagenate(int pageNum, int size)
	{
		return null;
	}
}
 