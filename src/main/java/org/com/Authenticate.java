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
		
		System.out.println("Do Post fired");
		
			try(PreparedStatement psAuth = connection.prepareStatement("select * from usertable where username=? and password=?"))
			{ 
				PrintWriter out = response.getWriter(); 
				
				String userName = request.getParameter("userName"); 
				
				System.out.println(userName);
				
				String password = request.getParameter("password"); 
				
				System.out.println(password);
				
				psAuth.setString(1, userName);
				
				psAuth.setString(2, password);
				
				ResultSet resultAuth = psAuth.executeQuery(); 
				
				

				if(resultAuth.next()) {
					HttpSession session = request.getSession(true);
					
					session.setAttribute("username", userName);
					
					String role = getRole(connection, userName);
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
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
	
	private String getRole(Connection connection, String userName) {
		String query = "select role from userTable where username = ?"; 
		
		try(PreparedStatement psGetRole = connection.prepareStatement(query)){
			psGetRole.setString(1, userName);
			
			
			ResultSet reGetRole = psGetRole.executeQuery(); 
			
			if(reGetRole.next()) {
				String role = reGetRole.getString("role");
				return role; 
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}

}
