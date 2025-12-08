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

/**
 * Servlet implementation class Category
 */
@WebServlet("/Category")
public class Category extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
//	 private Connection getConnection() throws SQLException, ClassNotFoundException {
//	        Class.forName("com.mysql.cj.jdbc.Driver");
//	        return DriverManager.getConnection("jdbc:mysql://localhost:3306/my_schema", "shiva", "Shiva@123");
//	    }
	
	Connection connection = null; 
	
	PreparedStatement psAuth = null; 
	
	ServletContext context = null; 
	
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 PrintWriter out = response.getWriter();
		
		 HttpSession session = request.getSession(false); 
		 
		 if(session == null) {
			 
			 response.sendRedirect("index.html");
				
			 return;
			 
		 }
		 
	     String userName = request.getParameter("userName");

	     String sql = "SELECT categoryId, categoryName, categoryDescription, imageLink FROM category";
	    
	     
		try(PreparedStatement ps = connection.prepareStatement(sql); 
			ResultSet rs = ps.executeQuery()
			){
			out.println("<html> <body>"); 
			out.println("welcome <b>" + (userName == null ? "Guest": userName) + "<b></br>");
			out.println("<table border = '1'>"); 
			out.println("<tr><td>Name</td><td>Description</td><td>Images</td>"); 
			
			
			while(rs.next()) {
				int categoryId = rs.getInt("categoryid"); 
				out.println("<tr>");
				out.println("<td><a href='Products?userName=" + userName + "&catId=" + categoryId + "'>"
                        + rs.getString("categoryName") + "</a></td>");
                out.println("<td>" + rs.getString("categoryDescription") + "</td>");
                out.println("<td><img src='Images/"+rs.getString("imageLink")  + "' height='60' width='60'/></td>");
                out.println("</tr>");
			}
			
			 out.println("</table>");
	         out.println("</body></html>");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
