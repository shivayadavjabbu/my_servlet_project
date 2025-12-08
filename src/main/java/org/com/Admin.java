package org.com;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

    Connection connection = null; 
	PreparedStatement psAuth = null; 
	ServletContext context = null; 
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		context = config.getServletContext();
		connection = (Connection) context.getAttribute("dbConnection"); 
		
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
	    String userName = request.getParameter("userName");
	 
	    out.println("<html> <body>"); 
		out.println("welcome <b>" + (userName == null ? "Guest": userName) + "<b></br>");
		out.println("<table border = '1'>"); 
		out.println("<tr><td>Select the Choice</td</tr>");
		out.println("<tr><td><a href=AddCategory?userName="+userName +"> Add Category </a></td></tr>");
		out.println("<tr><td><a href=AddCategory?userName"+userName +"> Delete Category </a></td></tr>");
		out.println("<tr><td><a href=AddCategory?userName="+userName +"> Add product </a></td></tr>");
		out.println("<tr><td><a href=AddCategory?userName="+userName +"> Delete product </a></td></tr>");
		out.println("</table></body></html> "); 
	}

}
