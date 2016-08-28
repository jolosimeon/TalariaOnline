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

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				Cookie idCookie = null;
		        Cookie nameCookie = null;
		        
		        Cookie[] cookies = request.getCookies();
		        if (cookies != null)
		        {
		            for (Cookie cookie : cookies)
		            {
		                if (cookie.getName().equals("user_name"))
		                {
		                    nameCookie = cookie;
		                }
		                
		                if (cookie.getName().equals("user_id"))
		                {
		                    idCookie = cookie;
		                }
		            }
		        }
				HttpSession session = request.getSession();
		        session.invalidate();
		        if (idCookie != null && nameCookie != null)
		        {
			        idCookie.setMaxAge(0);
			        nameCookie.setMaxAge(0);
			        response.addCookie(idCookie);
			        response.addCookie(nameCookie);
		        }
		        response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
