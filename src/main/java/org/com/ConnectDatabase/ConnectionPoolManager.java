package org.com.ConnectDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPoolManager {

	private BlockingQueue<Connection> connectionPool; 
	
	private String url;
	
    private String username;
    
    private String password;
    
    private String driverClass;
    
    public void initPool(String url, String username, String password, String driverClass, int poolSize) {
        
    	this.url = url;
    	
    	this.username = username;
    	
        this.password = password;
        
        this.driverClass = driverClass;
        
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver: " + driverClass, e);
        }
        
        connectionPool = new ArrayBlockingQueue<Connection>(poolSize); 
        
        for(int i =0; i < poolSize;  i++) {
        	
        	try {
				Connection conn = DriverManager.getConnection(url, username, password);
				
				connectionPool.add(conn); 
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("Error creating initial connection", e);
			} 
        	
        	System.out.println("Custom Connection Pool initialized with " + poolSize + " connections");
        
        }
         
    }   
    
    public Connection getConnection() throws SQLException {
    	
    	try {
    		
    		return connectionPool.take(); 
    		
    	} catch (InterruptedException e) {
  
    		throw new SQLException("interrupted while waiting for a DB Connection", e);
    	}
    }
    
    public void returnConnection(Connection conn) {
    	
    	if(conn != null) {
    		connectionPool.offer(conn); 
    	}
    }
    
    public void shutDown() {
    	for(Connection conn: connectionPool) {
    		
    		try {
    			conn.close();
    		} catch(SQLException e) {
    			System.out.println("Error closing connection: "+e.getMessage());
    		}
    	}
    	
    	System.out.println("Custom Connection Pool of Database shutdown");
    }
    
}
