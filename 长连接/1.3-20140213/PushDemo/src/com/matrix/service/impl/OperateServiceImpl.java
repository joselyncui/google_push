package com.matrix.service.impl;

import java.util.List;

import com.matrix.dao.OperateDao;
import com.matrix.dao.impl.OperateDaoImpl;
import com.matrix.domain.Operate;
import com.matrix.service.OperateService;

public class OperateServiceImpl implements OperateService{
	OperateDao operateDao = new OperateDaoImpl();

	@Override
	public void deleteOpeById(int id) {
		operateDao.deleteOpeById(id);
	}

	@Override
	public List<Operate> getOpeByEachDevice() {
		return operateDao.getOpeByEachDevice();
	}

}
