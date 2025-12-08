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
import java.util.List;

import org.com.ConnectDatabase.ConnectionPoolManager;
import org.com.DaoCategory.CategoryException;
import org.com.DaoCategory.CategoryImpl;
import org.com.DaoCategory.CategoryModel;

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

	     CategoryImpl categoryDAO = new CategoryImpl(connection);
	     
	     List<CategoryModel> categories = null;
		try {
			categories = categoryDAO.getAllCategories();
		} catch (CategoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	     out.println("<html>");
	     out.println("<body>");
	     out.println("Welcome <b>" + (userName == null ? "Guest" : userName) + "</b><br><br>");

	     out.println("<table border='1'>");
	     out.println("<tr><th>Name</th><th>Description</th><th>Images</th></tr>");

	     // Iterate over the list of categories
	     for (CategoryModel category : categories) {
	         int categoryId = category.getCategoryId();
	         out.println("<tr>");
	         out.println("<td><a href='Products?userName=" + userName + "&catId=" + categoryId + "'>"
	                     + category.getCategoryName() + "</a></td>");
	         out.println("<td>" + category.getCategoryDescription() + "</td>");
	         out.println("<td><img src='Images/" + category.getImageLink() + "' height='60' width='60'/></td>");
	         out.println("</tr>");
	     }

	     out.println("</table>");
	     out.println("</body>");
	     out.println("</html>");
			
			
	}

}
