package com.matrix.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.matrix.dao.BaseDao;
import com.matrix.tool.DBHelper;
import com.matrix.tool.ReflectUtil;

public class BaseDaoImpl implements BaseDao {
	static ResourceBundle rb = ResourceBundle.getBundle("sql");
	
	
	public boolean insertOrUpdate(Object obj, String key){
		List<Object> params = ReflectUtil.getListByObject(obj);
		
		System.out.println("insertOrUpdate: params");
		return DBHelper.execPreSql(rb.getString(key), params);
	}
	
	public int insertOrUpdateWithId(Object obj, String key){
		List<Object> params = ReflectUtil.getListByObject(obj);
		params = params.subList(0, params.size()-2);
		return DBHelper.execPreSqlWithId(rb.getString(key), params);
	}
	
	public boolean delete(int id, String key){
		List<Object> params = ReflectUtil.getListByObjects(id);
		return DBHelper.execPreSql(rb.getString(key), params);
	}
	
	public <T> List<T> query(String key, T t, Object... ids){
		List<Object> params = new ArrayList<Object>();
		if(ids != null){
			params = ReflectUtil.getListByObjects(ids);
		}
		ResultSet rs = DBHelper.query(rb.getString(key), params);
		return DBHelper.getListByRS(rs, t);
	}
}
