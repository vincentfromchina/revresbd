package com.db;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class RequestURL
 */
public class RequestURL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestURL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String urlStr = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxe357fff743a3bc9b&secret=c721cc4610bd5a3cd3b9cfc27e1f27b8";
		StringBuffer sb = new StringBuffer(1024*30); //30k

		URL url = new URL(urlStr);

		URLConnection urlConn = (URLConnection)(url.openConnection());
		urlConn.setDoOutput(true); //表示要读取返回页面

		InputStream is = urlConn.getInputStream();
		

		byte[] b = new byte[256];
		int i = 0;
		while ( (i = is.read(b)) > 0) 
		sb.append(new String(b,0,i,"utf-8"));
		
		System.out.println(sb.toString());
		
		JSONObject jsObject = JSONObject.fromObject(sb.toString());
		String  access_token = null;
		access_token = (String)jsObject.get("access_token");
		
		System.out.println(access_token);
		
	    /*
	     * 首先在servlet中写入如下代码将str放入request中并跳转到jsp页面：
       request.setAttribute("sign", str);
        request.getRequestDispatcher("jsp页面名.jsp").forward(request, response);

                    然后再jsp页面中用如下语句来接收即可：
        String str = (String)request.getAttribute("sign"); 
	     */
		
		ServletContext application=this.getServletContext(); 
		application.setAttribute("access_token", access_token);
		
		  HttpSession session =request.getSession();
	      session.setAttribute("weixinpara_sess", access_token);
	      
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
