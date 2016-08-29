package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;
import objects.ShoppingCart;
import objects.Transaction;
import objects.TransactionLineItem;
import objects.User;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
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
		User user = (User) session.getAttribute("user");
		if (Model.checkAuthentication(user.getUsername(), "purchaseproduct")) {
			ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
			Transaction t = new Transaction();
			t.setTotal(cart.getTotal());
			t.setUsername(user.getUsername());
			t = Model.addTransaction(t, user.getUsername());
			ArrayList<TransactionLineItem> products = cart.getProducts();
			for (int i = 0; i < products.size(); i++) {
				products.get(i).setTransactionId(t.getId());
				Model.addTransactionLineItem(products.get(i), user.getUsername());
			}
			cart.empty();
			response.sendRedirect("checkout.jsp");
		} else {
			response.sendRedirect("errorpage.jsp");
		}
	}

}
