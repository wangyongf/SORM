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
package com.yongf.vo; 

public class EmpVO
{
//	select e.id,e.empname,salary+bonus 'wage',age, d.dname 'deptName',d.address 'deptAddress' from emp e
//	join dept d on e.deptId=d.id;

	private Integer id;
	private String empname;
	private Double wage;
	private Integer age;
	private String deptName;
	private String deptAddr;
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getEmpname()
	{
		return empname;
	}
	public void setEmpname(String empname)
	{
		this.empname = empname;
	}
	public Double getWage()
	{
		return wage;
	}
	public void setWage(Double wage)
	{
		this.wage = wage;
	}
	public Integer getAge()
	{
		return age;
	}
	public void setAge(Integer age)
	{
		this.age = age;
	}
	public String getDeptName()
	{
		return deptName;
	}
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}
	public String getDeptAddr()
	{
		return deptAddr;
	}
	public void setDeptAddr(String deptAddr)
	{
		this.deptAddr = deptAddr;
	}
	public EmpVO(Integer id, String empname, Double wage, Integer age,
			String deptName, String deptAddr)
	{
		super();
		this.id = id;
		this.empname = empname;
		this.wage = wage;
		this.age = age;
		this.deptName = deptName;
		this.deptAddr = deptAddr;
	}
	
	public EmpVO()
	{
		super();
	}
	
	
}
 