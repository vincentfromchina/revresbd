package com.db;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Timestamp;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Mysqldb
 */
public class Ack extends HttpServlet {
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
    public Ack() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	
	public void init(ServletConfig config)throws ServletException{
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
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//这行非常重要，不设置中文会乱码
		request.setCharacterEncoding("GBK");
		String ack = request.getParameter("ACK");
		String imei = request.getParameter("imei");
		String batter = request.getParameter("batter");
				
		//System.out.println(new Date());
	
		
		//  String url="jdbc:mysql://"+dbip+":"+dbport+"/"+dbName;
		  String url ="jdbc:oracle:thin:@127.0.0.1:1521:xe";
		  try
		{
			// Class.forName("com.mysql.jdbc.Driver");
			  Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(url,usname,dbpwd);
		//	con = DiverManager.getConnection("jdbc:oracle:thin:ndb/ndb@192.168.1.6:1521:PC6");
		//	 stmt=conn.createStatement();
	       if (ack.equals("CHECKOK") && imei!=null)		 
	       { /*String sql="INSERT INTO ack(serialno,logtime,batter) VALUES ('"+imei+"',to_char( sysdate,'yyyy-mm-dd hh24:mi:ss'),"+Integer.valueOf(batter)+")";
	          stmt.execute(sql);*/
	    	   cs = conn.prepareCall("{call fzd_ack(?,?)}");  
				 cs.setString("imei", imei);  
				 cs.setInt("batter", Integer.valueOf(batter));
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
