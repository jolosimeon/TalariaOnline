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
 * Servlet implementation class CreateMngrAccount
 */
@WebServlet("/CreateMngrAccount")
public class CreateMngrAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMngrAccount() {
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
		User c = new User();
		c.setEmail(request.getParameter("email"));
		c.setFirstName(request.getParameter("first_name"));
		c.setLastName(request.getParameter("last_name"));
		c.setMiddleInitial(request.getParameter("mid_initial"));
		c.setUsername(request.getParameter("username"));
		c.setPassword(request.getParameter("pwd"));
		if (Integer.valueOf(request.getParameter("type")) == 2)
			Model.addAccountingManagerAccount(c);
		else if (Integer.valueOf(request.getParameter("type")) == 1)
			Model.addProductManagerAccount(c);
        response.sendRedirect("index.jsp");
	}

}
