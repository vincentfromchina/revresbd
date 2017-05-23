package com.db;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Servlet implementation class Weixin
 */
public class Weixin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  ServletConfig config=null;
		private String driverName="";                    
		     private String usname="";              
		     private String dbpwd="";                 
		     private String dbName="";    
		     private String dbip="";
		     private String dbport="";
		     private Connection conn;                      
		     private Statement stmt;                      
		     ResultSet rs=null;         
		     private CallableStatement cs;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Weixin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		 super.init(config);                          
	        this.config=config;                           
	        driverName=config.getInitParameter("driverName");
	        usname=config.getInitParameter("username");    
	         dbpwd=config.getInitParameter("password");    
	         dbName=config.getInitParameter("dbName"); 
	         dbip=config.getInitParameter("dbip");
	         dbport=config.getInitParameter("dbport");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.setCharacterEncoding("UTF-8");
			
				
				System.out.println("echostr:"+request.getParameter("echostr"));
				System.out.println("signature:"+request.getParameter("signature"));
				System.out.println("tolcount:"+request.getContentLength());
				
				InputStream is =  request.getInputStream();
				byte[] bs = new byte[request.getContentLength()+20];
				int g = 0;
				int tc = 0;
				
				while (( tc = is.read()) != -1) {
				//	System.out.println(tc);
					bs[g] =(byte)tc;
				//	System.out.println(bs[g]);
					g += 1;
					System.out.print(" "+tc);
				} 
				
				String recstr = new String(bs,"UTF-8");
				System.out.println(recstr);
			
			//	response.setContentType("text/xml");
			//	response.setCharacterEncoding("UTF-8");
				
				PrintWriter out=response.getWriter(); 
				
				if (request.getParameter("echostr")!=null)
				{
					out.println(request.getParameter("echostr"));
					return;
				}
				
				
				int FromUser_start_pos = recstr.indexOf("FromUserName");
				int FromUser_end_pos = recstr.indexOf("FromUserName",FromUser_start_pos+5);
				
				System.out.println("FromUser_start_pos:"+FromUser_start_pos+" FromUser_end_pos:"+FromUser_end_pos);
				
				String user_openid = recstr.substring(FromUser_start_pos+22, FromUser_end_pos-5);
				
				int url_start_pos = recstr.indexOf("EventKey");
				int url_end_pos = recstr.indexOf("EventKey",url_start_pos+5);
				System.out.println("url_start_pos:"+url_start_pos+" url_end_pos:"+url_end_pos);
				
				String Redirect_url = recstr.substring(url_start_pos+40, url_end_pos-5);
			    
			    if(request.isRequestedSessionIdValid())
 		          {
 		             if(request.isRequestedSessionIdFromCookie())
 		             {
 		            	System.out.println("回话ID来自COOKIE");
 		             }
 		             if(request.isRequestedSessionIdFromURL())
 		             {
 		            	System.out.println("回话ID来自URL");
 		             }
 		          }else
 		          	{
 		        	 System.out.println("本次请求不包含COOKIE");
 		          	}
			    
			    Cookie cookies[] =   request.getCookies();
			    if(cookies==null||cookies.length==0)
			    {
			    	System.out.println("没有任何COOKIE");
			    }else {
					for (int i = 0; i < cookies.length; i++)
					{
						System.out.print(cookies[i].getName()+" ");
						System.out.print(cookies[i].getValue()+" ");
						System.out.println(cookies[i].getMaxAge());
					}
				}
			    
			    
			    System.out.println(user_openid);
			//	System.out.println(session.getId());
							    
			//	response.sendRedirect("/dbserver/handle.jsp");

			     
			    
			    
				RequestDispatcher rq =  getServletContext().getRequestDispatcher(Redirect_url);
				
				request.setAttribute("weixin_openid", user_openid);
				rq.forward(request, response);
				
			//	out.print(new String(sbBuilder.toString().getBytes("UTF-8"),"ISO-8859-1"));
				/*
				System.out.println();
				StringBuilder sbBuilder = new StringBuilder("<xml>\n");
				sbBuilder.append("<ToUserName><![CDATA[od2GKxM4L-nK1RC0gXuRc9wzElyc]]></ToUserName>\n");
				sbBuilder.append("<FromUserName><![CDATA[gh_73b63d7fbbb1]]></FromUserName>\n");
				sbBuilder.append("<CreateTime>1490061816</CreateTime>\n");
				sbBuilder.append("<MsgType><![CDATA[text]]></MsgType>\n");
				sbBuilder.append("<Content><![CDATA[欢迎光临\n");
				sbBuilder.append(user_openid);
				sbBuilder.append("]]></Content>\n<FuncFlag>0</FuncFlag>\n");
				sbBuilder.append("</xml>");
			//	out.print(new String(sbBuilder.toString().getBytes("UTF-8"),"ISO-8859-1"));
				out.print(sbBuilder);
				*/
	}

}
