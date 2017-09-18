package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class FactoryDao {
	private static FactoryDao instance = new FactoryDao();
	private DataSource ds;
	
	private FactoryDao() {
		
		try {
			Context context = new InitialContext();
			ds=(DataSource)context.lookup("java:comp/env/jdbc/Oracle");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static FactoryDao getInstance() {
		return instance;
	}
	
	public Connection getConnection() {
		try {
			System.out.println(ds.getConnection());
			return ds.getConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//cud
	/**
	 * cud 자원해제 메서드
	 * @param conn
	 * @param stmt
	 */
	public void close(Connection conn , Statement stmt) {

		close(conn,stmt,null);
	}
	
	/**
	 * select 자원해제
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public void close(Connection conn , Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {	
				conn.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("error : 자원해제 오류");
		}
	}
}
