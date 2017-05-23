package com.db;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class Updatebindinfo
 */
public class Updatebindinfo extends HttpServlet {
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
		     private ResultSet rs=null;  
		     private CallableStatement cs;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Updatebindinfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("GBK");
		String serialno = request.getParameter("serialno");
		String pic = request.getParameter("pic");
		String owner = request.getParameter("owner");
		String bieming = request.getParameter("bieming");
	
		response.setContentType("text/plain;charset=UTF-8");   
		  PrintWriter out=response.getWriter(); 
		  
		  String url ="jdbc:oracle:thin:@127.0.0.1:1521:xe";
		  try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(url,usname,dbpwd);
			 cs = conn.prepareCall("{call fzd_updatebindinfo(?,?,?,?,?)}");  
			 cs.setString("owner", owner);
			 cs.setString("serialno", serialno);  
		     cs.setString("bieming", bieming);  
		     cs.setString("pic", pic); 
		     cs.registerOutParameter("resp", Types.VARCHAR); 
		     cs.execute();
		     String resp = cs.getString("resp");  
		     
		     JSONObject  mJSONObject = new JSONObject();
       	     mJSONObject.put("resp",resp);
		     
       	   out.println(mJSONObject);
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

}
