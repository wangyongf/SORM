package com.yongf.po;

import java.sql.*;
import java.util.*;

public class Dept
{
	private Integer id;
	private String address;
	private String dname;


	public Integer getId()
	{
		return id;
	}
	public String getAddress()
	{
		return address;
	}
	public String getDname()
	{
		return dname;
	}
	public void setId(Integer id)
	{
		this.id=id;
	}
	public void setAddress(String address)
	{
		this.address=address;
	}
	public void setDname(String dname)
	{
		this.dname=dname;
	}
}
