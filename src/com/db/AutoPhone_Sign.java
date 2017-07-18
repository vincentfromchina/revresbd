package com.db;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import net.sf.json.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Servlet implementation class autosms_reg
 */
public class AutoPhone_Sign extends HttpServlet {
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
    public AutoPhone_Sign() {
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
		request.setCharacterEncoding("gbk");
		String serialno = request.getParameter("serialno");
		System.out.println("base64: "+serialno);
		String basedecode_serialno = getFromBase64(serialno);
		System.out.println("decode: "+basedecode_serialno);
		  String url ="jdbc:oracle:thin:@127.0.0.1:1521:xe";
		  response.setContentType("text/plain;charset=UTF-8");   
		  PrintWriter out=response.getWriter();  
		
		  
	    try
		 {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn=DriverManager.getConnection(url,usname,dbpwd);
			 cs = conn.prepareCall("{call autophone_sign(?,?,?)}");  
			 cs.setString("imei", basedecode_serialno);  
		     cs.registerOutParameter("resp", Types.VARCHAR);  
		     cs.registerOutParameter("serialid", Types.VARCHAR);  
		     cs.execute();
		     
		     String resp = cs.getString("resp");  
		     String serialid = cs.getString("serialid"); 
		     System.out.println("resp: "+resp);
		     System.out.println("serialid: "+serialid);
		     JSONObject  mJSONObject = new JSONObject();
			 if (resp.equals("0"))
			 {
	       	     mJSONObject.put("resp",resp);
	       	     mJSONObject.put("baseencode_serialid",getBase64(getMD5(serialid)));
	       	 
	       	     out.println(mJSONObject);
			 }else {
				 mJSONObject.put("resp",resp);
	       	     mJSONObject.put("baseencode_serialid",getBase64(getMD5(serialid)));
	       	   out.println(mJSONObject);
			 }
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if( cs!=null)
				try
				{
					cs.close();
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		
	}
	
	   
   public static String getMD5(String val)
    {  
	        MessageDigest md5;
			try
			{
				md5 = MessageDigest.getInstance("MD5");
				 md5.update(val.getBytes());  
			        byte[] m = md5.digest();//º”√‹  
			        StringBuffer sb = new StringBuffer();  
			         for(int i = 0; i < m.length; i ++){  
			          sb.append(m[i]);  
			         }  
			         return sb.toString();  
			} catch (NoSuchAlgorithmException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	       return "1";
	 }  
	  
	
	 public static String getBase64(String str) {  
	        byte[] b = null;  
	        String s = null;  
	        try {  
	            b = str.getBytes("utf-8");  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	        if (b != null) {  
	            s = new BASE64Encoder().encode(b);  
	        }  
	        return s;  
	    }  
	  
	    // Ω‚√‹  
	    public static String getFromBase64(String s) {  
	        byte[] b = null;  
	        String result = null;  
	        if (s != null) {  
	            BASE64Decoder decoder = new BASE64Decoder();  
	            try {  
	                b = decoder.decodeBuffer(s);  
	                result = new String(b, "utf-8");  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return result;  
	    } 

}
