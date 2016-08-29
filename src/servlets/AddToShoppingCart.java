package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;
import objects.ShoppingCart;
import objects.TransactionLineItem;
import objects.User;

/**
 * Servlet implementation class AddToShoppingCart
 */
@WebServlet("/AddToShoppingCart")
public class AddToShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToShoppingCart() {
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
		HttpSession session = request.getSession();
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		TransactionLineItem item = new TransactionLineItem();
		item.setProductId(Integer.valueOf(request.getParameter("prod_id")));
		item.setQuantity(Integer.valueOf(request.getParameter("prod_qty")));
		item.setUnitPrice(Double.valueOf(request.getParameter("prod_price")));
		cart.add(item);
		
		User requester = (User) session.getAttribute("user");
		if (Model.checkAuthentication(requester.getUsername(), "purchaseproduct")) {
			response.sendRedirect("index.jsp");
		} else {
			response.sendRedirect("errorpage.jsp");
		}
	}

}
