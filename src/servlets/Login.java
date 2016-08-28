package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;
import objects.ShoppingCart;
import objects.User;


/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		//get request parameters for userID and password
		String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        User account = Model.getUser(user, pwd);
        
        if (account != null)
        {
            Cookie idCookie = new Cookie("user_id", String.valueOf(account.getUsername()));
            Cookie nameCookie = new Cookie("user_name", String.valueOf(account.getFirstName()));
            //setting cookie to expiry in 30 mins
            HttpSession session = request.getSession();
            session.setAttribute("userAccount", account);
            session.setAttribute("cart", new ShoppingCart());
            idCookie.setMaxAge(30 * 60);
            nameCookie.setMaxAge(30 * 60);
            response.addCookie(idCookie);
            response.addCookie(nameCookie);
            response.sendRedirect("index.jsp");
        }
        
        else
        {
        	response.sendRedirect("login.jsp?error=1");
        	/*
            RequestDispatcher rd
                    = getServletContext().getRequestDispatcher("/login.jsp");
            PrintWriter out = response.getWriter();
            //out.println("<font color = red>Either username or password is wrong.</font>");
            rd.include(request, response);
            
            rd.forward(request, response);*/
        }
	}

}
