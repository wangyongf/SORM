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
package com.yongf.test; 

import java.util.List;

import com.yongf.po.Emp;
import com.yongf.sorm.core.MysqlQuery;
import com.yongf.sorm.core.Query;
import com.yongf.sorm.core.QueryFactory;
import com.yongf.vo.EmpVO;


/**
 * 测试连接池的效率
 *  
 * @version 1.0 
 * 2015年5月31日 下午4:22:10       
 * @author ScottWang www.i466.cn
 */
public class Test2
{
	public static void test01()
	{
		Query q=QueryFactory.createQuery();
		
		List<Emp> list=q.queryRows("select id, empname,age from emp where age>? and salary<?",Emp.class
				,new Object[]{10,5000});
		System.out.println(list);
		for(Emp e:list)
		{
			System.out.println(e.getEmpname());
		}
	}
	
	public static void test02()
	{
		Query q=QueryFactory.createQuery();
		
		String sql2="select e.id,e.empname,salary+bonus 'wage',age, d.dname 'deptName',d.address 'deptAddr' from emp e "+
"join dept d on e.deptId=d.id ";
		List<EmpVO> list2=q.queryRows(sql2,EmpVO.class
				,null);
		System.out.println(list2);
		for(EmpVO e:list2)
		{
			System.out.println(e.getEmpname()+"-"+e.getDeptAddr()+"-"+e.getWage());
		}

	}
	
	public static void main(String[] args)
	{
		long a=System.currentTimeMillis();
		for(int i=0;i<3000;i++)
		{
			test01();
		}
		long b=System.currentTimeMillis();
		System.out.println((b-a));	//不加连接池的耗时:25146ms	增加连接池之后,耗时为:4223	效率增加很多!!!
	}
	
}
 