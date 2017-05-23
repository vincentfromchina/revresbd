package com.db;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Redirect
 */
public class Redirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Redirect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String user_openid = request.getParameter("user_openid");
		if (user_openid!=null)
		{
			System.out.println("para is:"+user_openid);
		}
		
		ServletContext application=this.getServletContext(); 
		
		String access_token = (String)application.getAttribute("access_token");
		
		System.out.println(access_token);
		
		RequestDispatcher rq =  getServletContext().getRequestDispatcher("/application.jsp");
		request.setAttribute("user_openid", user_openid);
		rq.forward(request, response);
		
		//response.sendRedirect("../weixin/handle.jsp?user_openid="+user_openid);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
