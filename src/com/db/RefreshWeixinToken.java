package com.db;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class RefreshWeixinToken
 */
public class RefreshWeixinToken extends HttpServlet {
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
    public RefreshWeixinToken() {
        super();
               
        System.out.println("RefreshWeixinToken create");
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config)throws ServletException {
		System.out.println("RefreshWeixinToken init");
		 super.init(config); 
		 this.config=config;                           
	        driverName=config.getInitParameter("driverName");
	        usname=config.getInitParameter("username");    
	         dbpwd=config.getInitParameter("password");    
	         dbName=config.getInitParameter("dbName"); 
	         dbip=config.getInitParameter("dbip");
	         dbport=config.getInitParameter("dbport");
	    			
				try
				{
					doPost();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
	}
	
	

	@Override
	public void destroy()
	{
		System.out.println("RefreshWeixinToken will destroy");
		super.destroy();
	}
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		
		doPost(req,resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost() throws ServletException, IOException {
		
		String urlStr = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxe357fff743a3bc9b&secret=c721cc4610bd5a3cd3b9cfc27e1f27b8";
		StringBuffer sb = new StringBuffer(1024*2); //30k

		URL url = new URL(urlStr);

		URLConnection urlConn = (URLConnection)(url.openConnection());
		//urlConn.setDoOutput(true); //

		InputStream is = urlConn.getInputStream();
		

		byte[] b = new byte[256];
		int i = 0;
		while ( (i = is.read(b)) > 0) 
		sb.append(new String(b,0,i,"utf-8"));
		
	//	System.out.println(sb.toString());
    
     JSONObject jsObject = JSONObject.fromObject(sb.toString());
		String  weixin_token = null;
		Integer errcode = null;
		weixin_token = (String)jsObject.get("access_token");
    errcode = (Integer)jsObject.get("errcode");
    

    if(weixin_token!=null)
    {
    
      System.out.print("weixin_token is:");

      System.out.println(weixin_token);
     }
     
     if(errcode!=null)
    {
      System.out.print("errcode is:");

      System.out.println(errcode);
     }
     
     String dburl ="jdbc:oracle:thin:@127.0.0.1:1521:"+dbName;
	  try
	{
		// Class.forName("com.mysql.jdbc.Driver");
		  Class.forName("oracle.jdbc.driver.OracleDriver");
		conn=DriverManager.getConnection(dburl,usname,dbpwd);
	
      if (weixin_token!=null)
      { 
    	  Calendar gc = GregorianCalendar.getInstance();
    	  
   	   cs = conn.prepareCall("{call refresh_weixin_token(?,?)}");  
			 cs.setString("weixin_token", weixin_token);  
			 cs.setLong("timeout",gc.getTimeInMillis()/1000);
		     cs.execute();
      }  
	       
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		if( stmt!=null)
			try
			{
				stmt.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if( cs!=null)
			try
			{
				cs.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if( conn!=null)
			try
			{
				conn.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
     
     
	}
	
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String urlStr = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxe357fff743a3bc9b&secret=c721cc4610bd5a3cd3b9cfc27e1f27b8";
		StringBuffer sb = new StringBuffer(1024*2); //30k

		URL url = new URL(urlStr);

		URLConnection urlConn = (URLConnection)(url.openConnection());
		//urlConn.setDoOutput(true); //

		InputStream is = urlConn.getInputStream();
		

		byte[] b = new byte[256];
		int i = 0;
		while ( (i = is.read(b)) > 0) 
		sb.append(new String(b,0,i,"utf-8"));
		
		System.out.println(sb.toString());
    
     JSONObject jsObject = JSONObject.fromObject(sb.toString());
		String  weixin_token = null;
		Integer errcode = null;
		weixin_token = (String)jsObject.get("access_token");
    errcode = (Integer)jsObject.get("errcode");
    

    if(weixin_token!=null)
    {
    
      System.out.print("weixin_token is:");

      System.out.println(weixin_token);
     }
     
     if(errcode!=null)
    {
      System.out.print("errcode is:");

      System.out.println(errcode);
     }
     
     String dburl ="jdbc:oracle:thin:@127.0.0.1:1521:"+dbName;
	  try
	{
		// Class.forName("com.mysql.jdbc.Driver");
		  Class.forName("oracle.jdbc.driver.OracleDriver");
		conn=DriverManager.getConnection(dburl,usname,dbpwd);
	
      if (weixin_token!=null)
      { 
    	  Calendar gc = GregorianCalendar.getInstance();
    	  
   	   cs = conn.prepareCall("{call refresh_weixin_token(?,?)}");  
			 cs.setString("weixin_token", weixin_token);  
			 cs.setLong("timeout",gc.getTimeInMillis()/1000);
		     cs.execute();
      }  
	       
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		if( stmt!=null)
			try
			{
				stmt.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if( cs!=null)
			try
			{
				cs.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if( conn!=null)
			try
			{
				conn.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
     
     
	}
}
