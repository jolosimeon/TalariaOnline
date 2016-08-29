package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;
import objects.Product;
import objects.User;

/**
 * Servlet implementation class EditProduct
 */
@WebServlet("/EditProduct")
public class EditProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProduct() {
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
		
		
		Product p = new Product();
		int id = Integer.valueOf(request.getParameter("prod_id"));
		p.setDescription(request.getParameter("desc"));
		p.setName(request.getParameter("prod_name"));
		p.setPrice(Double.valueOf(request.getParameter("price")));
		p.setType(Integer.valueOf(request.getParameter("type")));
		
		
		if (Model.checkAuthentication(request.getParameter("username"), "review")) {
			Model.editProduct(p.getType(), p.getName(), p.getDescription(), p.getPrice(), id, requester.getUsername());
			response.sendRedirect("items.jsp?item=" + id);
		} else {
			response.sendRedirect("errorpage.jsp");
		}
	}

}
