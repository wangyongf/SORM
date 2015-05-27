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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.yongf.sorm.bean.ColumnInfo;
import com.yongf.sorm.bean.TableInfo;
import com.yongf.sorm.utils.JavaFileUtils;


/**
 * 负责获取管理数据库所有表结构和类结构的关系,并可以根据表结构生成类结构
 *  
 * @version 1.0 
 * 2015年5月26日 上午12:52:54       
 * @author ScottWang www.i466.cn
 */
public class TableContext
{
	/**
	 * 表名为key,表信息对象为value
	 */
	public static Map<String,TableInfo> tables=new HashMap<String,TableInfo>();
	
	/**
	 * 将po的class对象和表信息对象关联起来,便于重用!
	 */
	public static Map<Class,TableInfo> poClassTableMap=new HashMap<Class,TableInfo>();
	
	private TableContext()
	{
		super();
	}
	
	static 
	{
		try
		{
			//初始化获得表的信息
			Connection con=DBManager.getConn();
			DatabaseMetaData dbmd=con.getMetaData();
			
			ResultSet tableRet=dbmd.getTables(null, "%", "%", new String[] {"TABLE"});
			
			while(tableRet.next())
			{
				String tableName=(String)tableRet.getObject("TABLE_NAME");
				
				TableInfo ti=new TableInfo(tableName,new ArrayList<ColumnInfo>()
						,new HashMap<String,ColumnInfo>());
				tables.put(tableName, ti);
				
				ResultSet set=dbmd.getColumns(null, "%", tableName, "%");	//查询表中的所有字段
				while(set.next())
				{
					ColumnInfo ci=new ColumnInfo(set.getString("COLUMN_NAME")
							,set.getString("TYPE_NAME"),0);
					ti.getColumns().put(set.getString("COLUMN_NAME"), ci);
				}
				
				ResultSet set2=dbmd.getPrimaryKeys(null, "%", tableName);	//查询t_user表中的主键
				while(set2.next())
				{
					ColumnInfo ci2=(ColumnInfo)ti.getColumns().get(set2.getObject("COLUMN_NAME"));
					ci2.setKeyType(1);	//设置为主键类型
					ti.getPriKeys().add(ci2);
				}
				
				if(ti.getPriKeys().size()>0)	//取唯一主键.方便使用,如果是联合主键,则为空!
				{
					ti.setOnlyPriKey(ti.getPriKeys().get(0));
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		//更新类结构
		updateJavaPOFile();
		
	}

	/**
	 * 根据表结构,更新配置的po包下的java类
	 * 实现了从表结构转化到类结构
	 *  
	 * @author Scott Wang
	 */
	public static void updateJavaPOFile()
	{
		Map<String,TableInfo> map=TableContext.tables;
		for(TableInfo t:map.values())
		{
			JavaFileUtils.createJavaPOFile(t, new MySqlTypeConvertor());
		}
	}
	
	public static void main(String[] args)
	{
		Map<String,TableInfo> tables=TableContext.tables;
		System.out.println(tables);
	}
}
 