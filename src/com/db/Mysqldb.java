package com.db;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Mysqldb
 */
public class Mysqldb extends HttpServlet {
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
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mysqldb() {
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
		String lc_street = request.getParameter("lc_street");
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");
		String imei = request.getParameter("imei");
		String gpstime = request.getParameter("gpstime");
		String gpsno = request.getParameter("gpsno");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		String address = request.getParameter("address");
		String locationtype = request.getParameter("locationtype");
		String provider = request.getParameter("provider");
		String accuracy = request.getParameter("accuracy");
		System.out.println(lc_street);
		System.out.println(imei);
		System.out.println(new Date());
		resp.setContentType("text/plain;charset=UTF-8");   
		  PrintWriter out=resp.getWriter(); 
		  
			String url ="jdbc:oracle:thin:@127.0.0.1:1521:xe";
		  try
			{
				// Class.forName("com.mysql.jdbc.Driver");
				  Class.forName("oracle.jdbc.driver.OracleDriver");
				conn=DriverManager.getConnection(url,usname,dbpwd);
				 stmt=conn.createStatement();
		          String sql="INSERT INTO gpsinfo(serialno,lc,lat,lng,gpstime,gpsno,province,city,district,address,locationtype,provider,accuracy) VALUES "
		          		+ "('"+imei+"','"+lc_street+"',"+lat+","+lng+",'"+gpstime+"','"+gpsno+"','"+province+"','"+city+"','"+district+"','"+address+"','"+locationtype+"','"+provider+"','"+accuracy+"')";
		          stmt.execute(sql);
		          
			       
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
			  
			  out.print(gpsno);
	//	System.out.print(Float.parseFloat(password));
		//这行非常重要，不设置中文会乱码
		/*response.setContentType("text/html;charset=UTF-8");   
		  PrintWriter out=response.getWriter();                //实例化对象，用于页面输出
          out.println("<html><head><title>参数接收页面</title>");                    //实现生成静态Html
          out.println("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"></head>");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().write("Hello, world 你好jsp!<br>");
		response.getWriter().write("username= "+username+"<br>");
		response.getWriter().write("password= "+password+"<br>");
		 response.getWriter().print(new Date());
		  out.println("</html>");		
		*/
		
	/*	resp.setContentType("text/html;charset=UTF-8");
		 PrintWriter out=resp.getWriter();                
		           out.println("<html>");                    
		           out.println("<head>");
		           out.println("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">");
		           out.println("<title>DataBase链接</title>");
		           out.println("</head>");
		           out.println("<body bgcolor=\"white\">");
		           out.println("<center>");
		           String url="jdbc:mysql://"+dbip+":"+dbport+"/"+dbName;
		           System.out.println(url);
		           try{
		                 Class.forName("com.mysql.jdbc.Driver");
		                 conn=DriverManager.getConnection(url,usname,dbpwd);
		                 stmt=conn.createStatement();
		                 String sql="select * from gpsinfo";
		                 rs=stmt.executeQuery(sql);
		                   out.println("Servlet链接成功");
		                   out.println("<table border=1 bordercolorlight=#000000>");
		                   out.println("<tr><td width=40>地址</td>");
		                   out.println("<td>经度</td>");
		                   out.println("<td>纬度</td>");
		                   //out.println("<td></td>");
		                   //out.println("<td></td></tr>");
		                    while(rs.next()){
		                     out.println("<tr><td>"+rs.getString(3)+"</td>");
		                     out.println("<td>"+rs.getInt(4)+"</td>");
		                     out.println("<td>"+rs.getString(5)+"</td>");
		                     //out.println("<td>"+rs.getString(4)+"</td>");
		                     //out.println("<td>"+rs.getString(5)+"</td>");
		                     out.println("</tr>");
		                    }
		                    out.println("</table><br>");
		                    out.println("username= "+username+"<br>");
		                    out.println("password= "+password+"<br>");
		                    rs.close();
		                    stmt.close();
		                    conn.close();    
		                   
		               }catch(Exception e){
		               e.printStackTrace();
		               out.println(e.toString());    
		               }
		               out.println("</center>");
		           out.println("</body>");
		           out.println("</html>");*/
		
		//  String url="jdbc:mysql://"+dbip+":"+dbport+"/"+dbName;
	
	}

}
