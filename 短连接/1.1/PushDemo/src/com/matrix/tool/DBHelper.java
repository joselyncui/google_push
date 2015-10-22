package com.matrix.tool;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Like
 * @version 1.0
 * @date 2013-7-30
 * @time 15:51
 */
public class DBHelper {
	/*public static String DRIVER;
	public static String URL;
	public static String USER_NAME;
	public static String PASSWORD;*/
	/*static{
		ResourceBundle rb = ResourceBundle.getBundle("jdbc");
		URL = rb.getString("url");
		DRIVER = rb.getString("driver");
		USER_NAME = rb.getString("user");
		PASSWORD = rb.getString("password");
	}
	*//**
	 * Get the Connection to execute the database
	 * @return return a Connection
	 */
/*	public static Connection getCon(){
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}*/
	/**
	 * Close all the resource
	 * @param pstmt PreparedStatement
	 * @param rs ResultSet
	 * @param con Connection
	 */
	public static Map<ResultSet, Connection> connections = new HashMap<ResultSet, Connection>();
	
	public static void closeAll(Statement stmt, ResultSet rs, Connection con){
		try{
			if(stmt != null){
				stmt.close();
			}
			if(rs != null){
				rs.close();
			}
			if(con != null){
				ConPool.getInstance().free(con);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param sql the sql sentence to execute database
	 * @param list the params that sql sentence need
	 * @return if execute success return else return false
	 */
	public static <T> boolean execPreSql(String sql, List<T> list){
		
		boolean flag = true;
		PreparedStatement pstmt = null;
		Connection con = null;
		System.out.println("sql-->" + sql);
		System.out.println("size-->" + list.size());
		try {
			con = ConPool.getInstance().getConnection();
			pstmt = con.prepareStatement(sql);
			for(int i=0; i<list.size(); i++){
				pstmt.setObject(i+1, list.get(i));
			}
			pstmt.execute();
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		}finally{
			closeAll(pstmt, null, con);
		}
		return flag;
	}
	
	public static <T> int execPreSqlWithId(String sql, List<T> list){
		int id = -1;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = ConPool.getInstance().getConnection();
			pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			for(int i=0; i<list.size(); i++){
				pstmt.setObject(i+1, list.get(i));
			}
			pstmt.execute();
			ResultSet newid = pstmt.getGeneratedKeys();
			newid.next();
			id = newid.getInt(1);
		} catch (SQLException e) {
			id = -1;
			e.printStackTrace();
		}finally{
			closeAll(pstmt, null, con);
		}
		return id;
	}
	/**
	 * 
	 * @param sqls a List of sql sentence
	 * @param list sql sentence's params
	 * @return if success return ture else return false
	 */
	public static <T> boolean executeTransaction(List<String> sqls, List<List<T>> list){
		boolean flag = true;
		Connection con = null;
		try{
			con = ConPool.getInstance().getConnection();
			con.setAutoCommit(false);
			for(int i=0; i<sqls.size(); i++){
				if(!execPreSql(sqls.get(i),list.get(i))){
					flag = false;
					con.rollback();
				}
			}
			con.commit();
		}catch(Exception e){
			flag = false;
			if(con != null){
				try{
					con.rollback();
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		} finally{
			closeAll(null, null, con);
		}
		return flag;
	}
	/**
	 * this method is to query information from the database
	 * @param sql select sql sentence
	 * @return return ResultSet to get the result
	 */
	public synchronized static ResultSet query(String sql){
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConPool.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		connections.put(rs, con);
		return rs;
	}
	/**
	 * 
	 * @param sql sql sentence need to be execute
	 * @param list params
	 * @return return a ResultSet object
	 */
	public synchronized static ResultSet query(String sql, List<Object> list){
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConPool.getInstance().getConnection();
			 pstmt= con.prepareStatement(sql);
			for(int i=0; i<list.size(); i++){
				pstmt.setObject(i+1, list.get(i));
			}
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connections.put(rs, con);
		return rs;
	}
	public synchronized static <T> List<T> getListByRS(ResultSet rs, T t){
		List<T> ts = new ArrayList<T>();
		String className = t.getClass().getName();
		List<String> names = ReflectUtil.getFieldNames(className);
		Field[] fields = ReflectUtil.getFields(className);
		try {
			while(rs.next()){
				@SuppressWarnings("unchecked")
				T tmpT = (T)ReflectUtil.getClassInstance(className);
				for(int i=0; i<names.size(); i++){
					if(rs.getObject(i+1)!=null && !rs.getObject(i+1).equals("noNeed")){
						ReflectUtil.setter(tmpT, names.get(i), rs.getObject(i+1), fields[i].getType());
					}
				}
				ts.add(tmpT);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
//			closeAll(null, rs, connections.get(rs));
			ConPool.getInstance().free(connections.get(rs));
			connections.remove(rs);
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return ts;
	}
	
}
