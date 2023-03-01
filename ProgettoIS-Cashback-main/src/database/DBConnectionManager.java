package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionManager {
	
	public static String url = "jdbc:mysql://localhost:3306/";
	public static String dbName = "CASHBACK";
	public static String driver = "com.mysql.cj.jdbc.Driver";
	public static String userName = "root"; 
	public static String password = "teamrosso";
	
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		
		Connection conn = null;		
		Class.forName(driver); //com.mysql.cj.jdbc.Driver
		
		conn = DriverManager.getConnection(url+dbName,userName,password);
		
		return conn;
	}
	
	public static void closeConnection(Connection c) throws SQLException {
		
		c.close();
	}
	
	public static ResultSet selectQuery(String query) throws ClassNotFoundException, SQLException {
		
		Connection conn = getConnection();
		
		Statement statment = conn.createStatement();
		
		ResultSet ret = statment.executeQuery(query);
		
		return ret;
	}
	
	public static int updateQuery(String query) throws ClassNotFoundException, SQLException {
		
		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		int ret = statement.executeUpdate(query);
		conn.close();
		return ret;
	}
	
	public static Integer updateQueryReturnGeneratedKey(String query) throws ClassNotFoundException, SQLException {
		Integer ret = null;
		
		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()){
		    ret = rs.getInt(1);
		}
		
		conn.close();
		
		return ret;
	}
}