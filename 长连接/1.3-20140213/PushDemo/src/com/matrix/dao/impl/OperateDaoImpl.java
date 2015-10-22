package com.matrix.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.matrix.dao.OperateDao;
import com.matrix.domain.Operate;

public class OperateDaoImpl extends BaseDaoImpl implements OperateDao{
	Operate operate = new Operate();

	@Override
	public void deleteOpeById(int id) {
		delete(id, "deleteOpeById");
	}
	
	@Override
	public List<Operate> getOpeByEachDevice() {
		return query("getOpeByEachDevice", operate);
	}
	
	public void addOpe(Operate operate) {
		insertOrUpdate(operate,"addOperate");
	}
	public static void main(String args[]) {
		List<String> deviceid = new ArrayList<>();
		
		//deviceid.add("11:11:11:11:11:11");
		deviceid.add("12:12:12:12:12:12");
		
		deviceid.add("50:3c:c4:a6:f7:b5");
		deviceid.add("34:23:BA:A8:67:E1");
		for (int i = 0; i < deviceid.size(); i++) {
			Operate operate = new Operate();
			operate.setDeviceId(deviceid.get(i));
			operate.setOperation("Delete");
			new OperateDaoImpl().addOpe(operate);
		}
		
		/*Map<String, String> text = new HashMap<String, String>();
		text.put("1", "1");
		text.put("2", "2");
		text.put("1", "3");
		System.out.println(text.size());
		System.out.println(text.get("1"));*/
		
	}
}
