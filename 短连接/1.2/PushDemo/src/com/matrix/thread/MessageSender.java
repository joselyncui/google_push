package com.matrix.thread;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletResponse;

public class MessageSender implements Runnable {
	private boolean running = true;
	private Map<String, String> messageMap = new HashMap<String, String>();
	private Map<String, ServletResponse> connections = new HashMap<String, ServletResponse>();
	
	 public void stop() {  
         running = false;  
     } 
	 
	
	public synchronized void addResponse(String user, ServletResponse response) {
		synchronized (connections) {
			if (!connections.containsKey(user)) {
				connections.put(user, response);
			}
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

	public void send(String user, String message) {
		synchronized (messageMap) {
			messageMap.put(user, message);
			messageMap.notify();
		}
	}

	@Override
	public void run() {
		while (running) {
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
				
				System.out.println("connections.size: " + connections.size());
				System.out.println("message.size: " + messageMap.size());
				
				for (String user : connections.keySet()) {
					try {
						ServletResponse response = connections.get(user);
						if (response != null) {
							PrintWriter out = response.getWriter();
							if (out != null && null != messageMap.get(user)) {
								out.write(messageMap.get(user));
								System.out.println("user: " + user +", message: " + messageMap.get(user));
								out.flush();
								out.close();
								
								//removeResponse(user);
								//messageMap.remove(user);
							}
								
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("response miss");
					} finally {
						removeResponse(user);
						messageMap.remove(user);
							
						System.out.println("run finish");
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error occur");
			}
		}
	}

}
