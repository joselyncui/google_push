package com.matrix.timer;

import java.util.List;
import java.util.TimerTask;

import com.matrix.domain.Operate;
import com.matrix.service.OperateService;
import com.matrix.service.impl.OperateServiceImpl;
import com.matrix.servlet.PushServlet;

public class SearchTimer extends TimerTask{
	OperateService operateService = new OperateServiceImpl();
	String deviceId;
	
	public SearchTimer() {}
	public SearchTimer(String devivceId) {
		super();
		this.deviceId = devivceId;
	}
 
	@Override
	public void run() {	
		List<Operate> operates = operateService.getOpeByEachDevice();
		PushServlet.ms.send("0", "0");//avoid response miss
		for (int i = 0, size = operates.size(); i < size; i++ ) {
			PushServlet.ms.send(operates.get(i).getDeviceId(), operates.get(i).getOperation());
			operateService.deleteOpeById(operates.get(i).getId());
			
		}
	}
}

