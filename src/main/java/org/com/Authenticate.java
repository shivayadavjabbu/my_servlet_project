package org.com;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.com.ConnectDatabase.ConnectionPoolManager;
import org.com.DaoAuthenticate.AuthenticateException;
import org.com.DaoAuthenticate.AuthenticateInterface;
import org.com.DaoAuthenticate.AuthenticationImpl;

@WebServlet("/Authenticate")
public class Authenticate extends HttpServlet {
	private static final long serialVersionUID = 1L;
		
	Connection connection = null; 
	
	PreparedStatement psAuth = null; 

	ServletContext context; 
	
	ConnectionPoolManager pool; 
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		context = config.getServletContext();

		pool = (ConnectionPoolManager) context.getAttribute("ConnectionPool"); 
		
		try {
			
			connection = pool.getConnection();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		
		pool.returnConnection(connection); //this will return the connection once the connection is done. 
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter(); 
		
		String userName = request.getParameter("userName"); 
		
		System.out.println(userName);
		
		String password = request.getParameter("password"); 
		
		System.out.println(password);
			
		AuthenticationImpl userAuth = new AuthenticationImpl(connection); 
			

		try {
			if(userAuth.authenticateUser(userName, password)) {
				
				HttpSession session = request.getSession(true);
				
				session.setAttribute("username", userName);
				
				String role = userAuth.getRole(userName);
				
				if(role.equals("admin")) {
					
					System.out.println("Redirected to the admin");
					
					response.sendRedirect("Admin?userName=" + userName); 
					
				}else {
					
					System.out.println("redirected to the Category page");
					
					response.sendRedirect("Category?userName=" + userName); 
				}
				
			}else {
				out.println("Authentication failed");
			}
		} catch (AuthenticateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
