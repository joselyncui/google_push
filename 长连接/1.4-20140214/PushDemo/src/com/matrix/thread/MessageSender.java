package com.matrix.thread;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;

public class MessageSender implements Runnable {
	private boolean running = true;
	private Map<String, String> messageMap = new HashMap<String, String>();
	private Map<String, ServletResponse> connections = new HashMap<String, ServletResponse>();
	private Map<String, String> receiveMap = new HashMap<>();
	
	 public void stop() {  
         running = false;  
     } 
	 
	public synchronized void addResponse(String user, ServletResponse response, String isReceive) {
		synchronized (connections) {
			if (!connections.containsKey(user)) {
				connections.put(user, response);
			}
			
			receiveMap.put(user, isReceive);
			connections.notify();
		}
	}

	public synchronized void removeResponse(String user) {
		synchronized (connections) {
			if (connections.containsKey(user)) {
				connections.remove(user);
			}
			connections.notify();
		}
	}
	
	public synchronized void removeReceive(String user) {
		synchronized (receiveMap) {
			if (receiveMap.containsKey(user)) {
				receiveMap.remove(user);
			}
		}
	}

	public void send(String user, String message) {
		synchronized (messageMap) {
			messageMap.put(user, message);
			messageMap.notify();
		}
	}

	@Override
	public void run() {
		while (running) {
			
			List<String> keys = new ArrayList<>();
			try {
				if (connections.size() == 0) {
					synchronized (connections) {
						connections.wait();
					}
				}
				if (messageMap.size() == 0) {
					synchronized (messageMap) {
						messageMap.wait();
					}
				}
				
				List<String> userList = new ArrayList<>();
				
				for (String user : receiveMap.keySet()) {
					if ("0".equals(receiveMap.get(user))) {
						//do something here
					} else {
						messageMap.remove(user);
					}
					userList.add(user);
				}
				
				for (int i = 0; i < userList.size(); i++) {
					removeReceive(userList.get(i));
				}
				
				userList.clear();
				userList = null;
				
				synchronized (connections) {
					for (String user : connections.keySet()) {
						try {
							ServletResponse response = connections.get(user);
							if (response != null) {
								PrintWriter out = response.getWriter();
								if (out != null && null != messageMap.get(user) && !"".equals(messageMap.get(user))) {
									out.write(messageMap.get(user));
									out.flush();
									out.close();
									keys.add(user);
								}
							}
						} catch (Exception e) {
							System.out.println("response miss");
						}
					}
				}
				
				
			} catch (Exception e) {
				System.out.println("error occur");
			} finally {
				for (int i = 0, size = keys.size(); i< size; i++) {
					removeResponse(keys.get(i));
				}
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
