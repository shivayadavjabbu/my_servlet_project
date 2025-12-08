package org.com;

import org.com.ConnectDatabase.ConnectionPoolManager;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MyservletContextListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
        // This method is called when the web application is deployed.
        System.out.println("Web application initialized. Performing the startup of Database");
        // Example: Initialize a database connection pool
        
        ServletContext context = sce.getServletContext(); 
		
		String databaseClassDriver = context.getInitParameter("DB_DRIVER"); 
		
		System.out.println(databaseClassDriver); 

		String dbUrl = context.getInitParameter("DB_URL"); 
		
		System.out.println(dbUrl); 
		
		String dbUsername = context.getInitParameter("DB_USERNAME"); 
		
		System.out.println(dbUsername); 
		
		String dbpassword = context.getInitParameter("DB_PASSWORD"); 
		
		System.out.println(dbpassword); 

		int poolSize = Integer.parseInt(context.getInitParameter("dbPoolsize")); 
		
		System.out.println(poolSize); 
		
		ConnectionPoolManager pool = new ConnectionPoolManager(); 
		
		pool.initPool(dbUrl, dbUsername, dbpassword, databaseClassDriver, poolSize);

		context.setAttribute("ConnectionPool", pool);
	}

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // This method is called when the web application is shut down.
    	
    	System.out.println(sce);
    	
    	System.out.println("calling of destroy method is succesfull");
    	
    	ConnectionPoolManager pool = (ConnectionPoolManager) sce.getServletContext().getAttribute("ConnectionPool");
    			
    	pool.shutDown();
    	
        System.out.println("Web application destroyed. Performing the close of Database");
        // Example: Close the database connection pool
    }
}
