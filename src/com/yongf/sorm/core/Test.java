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
import com.yongf.sorm.core.MysqlQuery;
import com.yongf.sorm.core.Query;
import com.yongf.sorm.core.QueryFactory;
import com.yongf.vo.EmpVO;

public class Test
{
	public static void main(String[] args)
	{
		Query q=QueryFactory.createQuery();
		
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
	
//	public static void main(String[] args)
//	{
//		List<Emp> list=new MysqlQuery().queryRows("select id, empname,age from emp where age>? and salary<?",Emp.class
//				,new Object[]{10,5000});
//		System.out.println(list);
//		for(Emp e:list)
//		{
//			System.out.println(e.getEmpname());
//		}
//		
//		String sql2="select e.id,e.empname,salary+bonus 'wage',age, d.dname 'deptName',d.address 'deptAddr' from emp e "+
//"join dept d on e.deptId=d.id ";
//		List<EmpVO> list2=new MysqlQuery().queryRows(sql2,EmpVO.class
//				,null);
//		System.out.println(list2);
//		for(EmpVO e:list2)
//		{
//			System.out.println(e.getEmpname()+"-"+e.getDeptAddr()+"-"+e.getWage());
//		}
//	}

}
 