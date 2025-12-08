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
 * Servlet implementation class Products
 */
@WebServlet("/Products")
public class Products extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
		
		pool.returnConnection(connection); //this will return the connection.
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        PrintWriter out = response.getWriter();
        
        String tmpId = request.getParameter("catId");
        
        String userName = request.getParameter("userName");
        
		 HttpSession session = request.getSession(false); 
		 
		 if(session == null) {
			 
			 response.sendRedirect("index.html");
			
			 return;
		 }
		 

        if (tmpId == null) {
            out.println("Category id missing");
            return;
        }

        int categoryId;
        
        try {
            categoryId = Integer.parseInt(tmpId);
        } catch (NumberFormatException e) {
            out.println("Invalid category id");
            return;
        }

        String sql = "SELECT productName, productDescription, productprice, productimageUrl FROM products WHERE categoryId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {

                out.println("<html><body>");
                out.println("Welcome <b>" + (userName == null ? "Guest" : userName) + "</b><br/>");
                out.println("<table border='1'>");
                out.println("<tr><td>Name</td><td>Description</td><td>Price</td><td>Image</td></tr>");

                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getString("productName") + "</td>");
                    out.println("<td>" + rs.getString("productDescription") + "</td>");
                    out.println("<td>" + rs.getFloat("productprice") + "</td>");
                    out.println("<td><img src='Images/" + rs.getString("productimageUrl") + "' height='60' width='60'/></td>");
                    out.println("</tr>");
                }

                out.println("</table>");
                out.println("</body></html>");
            }
        } catch (SQLException e) {
            e.printStackTrace(out);
        }
		
	}

}
