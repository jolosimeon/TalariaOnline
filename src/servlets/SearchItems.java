package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import objects.Product;
import objects.User;
import model.Model;

/**
 * Servlet implementation class SearchItems
 */
@WebServlet("/SearchItems")
public class SearchItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItems() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("searchText").toString();
		int categ = 0;
		System.out.println("AYo: " + search);
		if (!request.getParameter("categ").equals(""))
			categ = Integer.parseInt(request.getParameter("categ"));
		System.out.println(categ); 
		ArrayList<Product> listItems = Model.getAllProductsByNameAndCateg(search, categ);
		StringBuffer sb = new StringBuffer();
		HttpSession session = request.getSession();
		String userName = null;
		User user = (User) session.getAttribute("user");
		boolean itemsAdded = false;
		
		if (user != null)
			userName = user.getUsername();
		
		sb.append("<items>");
		sb.append("<userName>" + userName + "</userName>");
		for (int i = 0; i < listItems.size(); i++)
		{
			sb.append("<item>");
			sb.append("<itemName>" + listItems.get(i).getName() + "</itemName>");
			sb.append("<itemDesc>" + listItems.get(i).getDescription() + "</itemDesc>" );
			sb.append("<itemID>" + listItems.get(i).getId() + "</itemID>");
			sb.append("</item>");
			itemsAdded = true;
		}
		sb.append("</items>");
		
		
		if (itemsAdded) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(sb.toString());
            System.out.println("items!");
        } else {
            //nothing to show
        	System.out.println("Hello");
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
