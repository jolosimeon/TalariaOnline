package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.User;
import model.Model;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUser() {
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
		c.setCardNumber(request.getParameter("credit_card"));
		c.setUserType(1);
		String billingAddrss = request.getParameter("bill_housenum") + " " + request.getParameter("bill_stname") + ", " + request.getParameter("bill_subd") + ", " + request.getParameter("bill_city") + ", " + request.getParameter("bill_postcode") + " " + request.getParameter("bill_country");
		c.setBillingAddrss(billingAddrss);
		String shippingAddress = request.getParameter("ship_housenum") + " " + request.getParameter("ship_stname") + ", " + request.getParameter("ship_subd") + ", " + request.getParameter("ship_city") + ", " + request.getParameter("ship_postcode") + " " + request.getParameter("ship_country");
		c.setShippingAddress(shippingAddress);

        Model.addCustomerAccount(c);
        response.sendRedirect("index.jsp");
	}

}
