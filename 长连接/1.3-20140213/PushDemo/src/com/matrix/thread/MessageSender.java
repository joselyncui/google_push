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
						System.out.println("wait connection");
						connections.wait();
					}
				}
				if (messageMap.size() == 0) {
					synchronized (messageMap) {
						System.out.println("wait message");
						messageMap.wait();
					}
				}
				
				List<String> userList = new ArrayList<>();
				
				for (String user : receiveMap.keySet()) {
					if ("0".equals(receiveMap.get(user))) {
						//send(user, messageMap.get(user));
						//messageMap.put(user, messageMap.get(user));
					} else {
						messageMap.remove(user);
					}
					userList.add(user);
				}
				
				System.out.println("receiveMap before remove: " + receiveMap.size());
				for (String user : receiveMap.keySet()) {
					System.out.println("user: " + user + ", " +receiveMap.get(user));
				}
				
				for (int i = 0; i < userList.size(); i++) {
					removeReceive(userList.get(i));
				}
				System.out.println("receiveMap after remove: " + receiveMap.size());
				
				userList.clear();
				userList = null;
				
				System.out.println("connections.size: " + connections.size());
				System.out.println("message.size: " + messageMap.size());
				
				synchronized (connections) {
					for (String user : connections.keySet()) {
						try {
							ServletResponse response = connections.get(user);
							if (response != null) {
								PrintWriter out = response.getWriter();
								if (out != null && null != messageMap.get(user) && !"".equals(messageMap.get(user))) {
									out.write(messageMap.get(user));
										
									System.out.println("user: " + user +", message: " + messageMap.get(user));
									out.flush();
									out.close();
									keys.add(user);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("response miss");
						} /*finally {
							
							removeResponse(user);
							messageMap.remove(user);
								
							System.out.println("run finish");
						}*/
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error occur");
			} finally {
				if (keys.size()>0) {
					System.out.println("keys.size: " + keys.size());
				}
				
				for (int i = 0, size = keys.size(); i< size; i++) {
					removeResponse(keys.get(i));
					//messageMap.remove(keys.get(i));
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
