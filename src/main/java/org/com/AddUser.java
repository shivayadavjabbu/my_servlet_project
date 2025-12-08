package org.com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Do Post fired");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String query = "INSERT INTO userTable(username, password, number, name, email, role) VALUES(?, ?, ?, ?, ?, ?)";
		
			try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_schema", "shiva", "Shiva@123");
				PreparedStatement psAddUser = connection.prepareStatement(query); 
				Statement stCheckquery = connection.createStatement())
			{ 
				PrintWriter out = response.getWriter(); 
				
				String userName = request.getParameter("userName"); 
				
				
				String password = request.getParameter("password"); 
				
				
				String confirmPassword = request.getParameter("confirmPassword");
				
				String Name = request.getParameter("name"); 
				
				String email = request.getParameter("email");
				
				String Mobile = request.getParameter("mobile");
				
				String role = request.getParameter("role");
				
				if(password.equals(confirmPassword)) {
					psAddUser.setString(1, userName);
					
					psAddUser.setString(2, password);
					
					psAddUser.setString(3, Mobile);
					
					psAddUser.setString(4, Name);
					
					psAddUser.setString(5, email);
					
					psAddUser.setString(6, role);
					
					
					
					String checkquery = "select * from userTable where userName=?"; 
					
					PreparedStatement psCheck = connection.prepareStatement(checkquery);
					psCheck.setString(1, userName);
					ResultSet rs = psCheck.executeQuery(); 
					
					if(rs.next()) {
						out.println("The addition of user is failed as we have already shiva exsiting in the table");
					}else {
						psAddUser.executeUpdate(); 
						out.println("the addition of user is succesfull");
						System.out.println("done the vaues");
					}
					
				}else
					out.println("The password and confirm password are not equal");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

