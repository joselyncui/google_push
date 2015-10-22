package com.matrix.domain;

public class Operate {
	private int id;
	private String operation;
	private String deviceId;
	
	public Operate(){}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "Operate [id=" + id + ", operation=" + operation + ", deviceId="
				+ deviceId + "]";
	}
	
}
