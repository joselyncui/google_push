package com.matrix.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ConPool {
	private static final int NUM_OF_CONS = 2;
	private List<Connection> pool= Collections.synchronizedList(new ArrayList<Connection>()); 
	private static ConPool cp = new ConPool();
	
	private ConPool(){
		try {
			String driver = ResourceRead.getResource("jdbc", "driver");
			String url = ResourceRead.getResource("jdbc", "url");
			Class.forName(driver);
			String user = ResourceRead.getResource("jdbc", "user");
			String pwd=ResourceRead.getResource("jdbc", "password");
			for(int i=0; i<NUM_OF_CONS; i++){
				Connection conn = DriverManager.getConnection(url,user,pwd);
				conn.setAutoCommit(true);
				pool.add(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized Connection getConnection(){
		while(pool.size()==0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return pool.remove(pool.size()-1);
	}
	
	public synchronized void free(Connection con){
		pool.add(con);
		notify();
	}
	
	public static ConPool getInstance(){
		return cp;
	}
}
