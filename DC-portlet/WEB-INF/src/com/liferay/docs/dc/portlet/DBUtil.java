package com.liferay.docs.dc.portlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	public Connection getConnection(){
		String url = "jdbc:mysql://localhost:3306/dcim";
		String driver = "com.mysql.jdbc.Driver";
		String username = "root";
		String password = "junyiliu";
		
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void close(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
