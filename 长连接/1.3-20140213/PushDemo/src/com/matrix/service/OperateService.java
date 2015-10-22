package com.matrix.service;

import java.util.List;

import com.matrix.domain.Operate;

public interface OperateService {
	
	/**
	 * delete operate from tb_operate by id
	 * 
	 * @param id
	 */
	public void deleteOpeById(int id);
	
	/**
	 * get first operate by each device
	 * 
	 * @return List<Operate>, see <code>Operate</code>
	 */
	public List<Operate> getOpeByEachDevice();

}
