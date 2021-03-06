package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Model;
import objects.User;

/**
 * Servlet implementation class AssignPassword
 */
@WebServlet("/AssignPassword")
public class AssignPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User requester = (User) request.getSession().getAttribute("user");
		
		if (Model.checkAuthentication(requester.getUsername(), "assignpassword")) {
			Model.assignTempPassword(request.getParameter("username"), request.getParameter("pw"), requester.getUsername());
	        response.sendRedirect("assign-pw.jsp");
		} else {
			response.sendRedirect("errorpage.jsp");
		}

	}

}
