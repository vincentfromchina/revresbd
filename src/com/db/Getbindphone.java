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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.*;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Getbindphone
 */
public class Getbindphone extends HttpServlet {
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
    public Getbindphone() {
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
		String owner = request.getParameter("owner");
		
		
		response.setContentType("text/plain;charset=UTF-8");   
		  PrintWriter out=response.getWriter();  
		  
		  String url ="jdbc:oracle:thin:@127.0.0.1:1521:xe";
		  try
		  {
			// Class.forName("com.mysql.jdbc.Driver");
			  Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(url,usname,dbpwd);
			 stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	          String sql="select * from (SELECT * FROM bindphone where owner ='"+owner+"')b,serialno s where b.imei=s.imei";
	          System.out.println(sql);
	          
	          rs=stmt.executeQuery(sql);
	         
	          List<Object> list = new ArrayList<Object>();//传递List        
              Map m=new HashMap();//传递Map            
		//	    Jsondata j1=new Jsondata(); 
			    
			    rs.last();
			   
			    JSONObject[] JSONObject1 = new JSONObject[rs.getRow()];
			    
			    rs.beforeFirst();
			  
			    
		          JSONArray jsonArray1 = new JSONArray();
		          int i = 0;
				while(rs.next())
				  {
		        	
		        	  JSONObject1[i] = new JSONObject();
		        	  JSONObject1[i].put("serialno",rs.getString("serialno"));
		        	  JSONObject1[i].put("imei",rs.getString("imei"));
		        	  JSONObject1[i].put("pic",rs.getString("pic"));
		        	  String exptime = rs.getString("exptime");
		        	  Date currentTime = new Date(); 
		        	  String nowtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime);
		        	  if (bijiaotime(nowtime, exptime))
		        		  {
		        		    JSONObject1[i].put("isexp","y" );  //已过期
		        		  }else
		        		  {
		        			JSONObject1[i].put("isexp","n" );  //有效
		        		   };
		        	  String bieming = rs.getString("bieming");
		        	  if (bieming == null)
		        	  {
		        		  JSONObject1[i].put("bieming","" );
		        	  }else
		        	  {  JSONObject1[i].put("bieming",bieming );}
		        
		        	  i += 1;
				  }
		          
		          m.put("imeis", jsonArray1); 
			
		      JSONArray jsonArray2 = JSONArray.fromObject( list );          
			           
			
	        JSONObject jo=JSONObject.fromObject(m);//转化Map对象     
	 
	        List<Object> list1 = new ArrayList<Object>();
	        for (int j = 0; j < JSONObject1.length; j++)
			{
	        	list1.add(JSONObject1[j]);
			}
	        System.out.println(JSONObject1.length);  
	     /*   cs = conn.prepareCall("{call fzd_getackinfo(?,?,?)}");  
			 cs.setString("serialno", serialno);  
		     cs.registerOutParameter("batter", Types.VARCHAR);  
		     cs.registerOutParameter("lastack", Types.VARCHAR);  
		     cs.execute();
		     
		     String lastack = cs.getString("lastack");  
		     String batter = cs.getString("batter"); */
	      
	        JSONArray jsonArray3 = JSONArray.fromObject( list1 );     
	        JSONObject jo3= new JSONObject();
	        jo3.put("imeis", jsonArray3);
	        jo3.put("remain1", "remain1");
	        jo3.put("remain2", "remain2");
	        System.out.println(jo3);
	        out.print(jo3);
	        
	          
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if( rs!=null)
				try
				{
					rs.close();
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
	
	public boolean bijiaotime(String time1,String time2) //比较当前系统时间与传进来的时间大小
	{
		   
		   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    
		   Date dat1,dat2 = null;
		try
		{
			dat1 = df.parse(time1);
			dat2 = df.parse(time2);
			
			if ((dat1.getTime()-dat2.getTime())>0)
			   {
				   return true;
			   }
			   else {
				  return false;
			   }
		} catch (ParseException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return false;
	}

}
