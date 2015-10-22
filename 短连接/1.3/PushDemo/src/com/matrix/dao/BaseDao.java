package com.matrix.dao;

import java.util.List;

public interface BaseDao {
	/**
	 * basic function to insert or update the database information
	 * @param obj the Object you want to insert
	 * @param key the key in the sql.properties file
	 * @return if success return true else return false
	 */
	public boolean insertOrUpdate(Object obj, String key);
	
	/**
	 * basic function to delete a row of the database table
	 * @param id 
	 * @param key the key in the sql.properties file
	 * @return if success return true else return false
	 */
	public boolean delete(int id, String key);
	
	/**
	 * basic function to query from the database
	 * @param key the key in the sql.properties file
	 * @param t
	 * @param ids select parameters
	 * @return return a List 
	 */
	public <T> List<T> query(String key, T t, Object... ids);
	
	public int insertOrUpdateWithId(Object obj, String key);
}
