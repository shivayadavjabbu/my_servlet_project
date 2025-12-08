package org.com.ConnectDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private final String url;
	
	private final String username;
	
	private final String password;
	
	private final String driverClass;

	public ConnectionFactory(String url, String uesrName, String password, String driverClass) {

		this.url = url;
		
		this.username = uesrName;
		
		this.password = password;
		
		this.driverClass = driverClass;
		
		try {
            Class.forName(driverClass);  // Load JDBC driver
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver: " + driverClass, e);
        }
		
		
	}
	
	
	public Connection createConnection() throws SQLException {
		
		return DriverManager.getConnection(url, username, password);
		
	}
	
}
