package com.matrix.dao;

import java.util.List;

import com.matrix.domain.Operate;

public interface OperateDao {
	
	/**
	 * delete operate from tb_operate by id
	 * 
	 * @param id, operate id
	 */
	public void deleteOpeById(int id);
	
	/**
	 * get first operate by each device
	 * 
	 * @return List<Operate>
	 */
	public List<Operate> getOpeByEachDevice();

}
