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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.*;
import net.sf.json.util.JSONStringer;

/**
 * Servlet implementation class Getgps
 */
public class Getgps extends HttpServlet {
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
    public Getgps() {
        super();
        // TODO Auto-generated constructor stub
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("GBK");
		String serialno = request.getParameter("serialno");
	//	System.out.println(serialno);
		String owner = request.getParameter("owner");
		String wantnum = request.getParameter("wantnum");
	//	System.out.println(wantnum);
		response.setContentType("text/plain;charset=UTF-8");   
		  PrintWriter out=response.getWriter();                //实例化对象，用于页面输出
          
		 
	//	  out.print("{\"gps\":[{\"id\":\"1\",\"time\":\"2016-04-13 12:45:33\",\"lng\":\"113.351207\",\"lat\":\"23.012246\"},{\"id\":\"2\",\"time\":\"2016-04-14 14:45:33\",\"lng\":\"113.851207\",\"lat\":\"23.112246\"}]}");
	   
		//  String url="jdbc:mysql://"+dbip+":"+dbport+"/"+dbName;
		  String url ="jdbc:oracle:thin:@127.0.0.1:1521:xe";
		  try
		  {
			// Class.forName("com.mysql.jdbc.Driver");
			  Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(url,usname,dbpwd);
			 stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	          String sql="select * from (SELECT * FROM gpsinfo where serialno ='"+serialno+"'"
	          		+ " order by gpstime desc)  where rownum <= "+ wantnum ;
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
		        	 
		        	//  System.out.print(rs.getRow());
		        	//  System.out.println("ok\n");
		        	//  j1.setdata( rs.getString("lat"), rs.getString("lng"), rs.getString("time"));
		        	//  System.out.println("ok1\n");
		        	  
		        /*	  JSONObject1[i].put("time", rs.getString("logtime")) ;
		        	  JSONObject1[i].put("lat", rs.getString("lat"));
		        	  JSONObject1[i].put("lng", rs.getString("lng"));*/
		        	  
		        /*	  jsonArray1.add("time"+":"+rs.getString("logtime")+"");
		        	  
		        	  		        	  
		        	  jsonArray1.add("lat");
		        	  
		        	  jsonArray1.add(rs.getString("lat"));
		        	 
		        	  jsonArray1.add("lng");
		        	  
		        	  jsonArray1.add(rs.getString("lng"));*/
		        /*	  StringBuilder sb1 = new StringBuilder().append("time:\"")
		        			  .append(rs.getString("logtime")).append(",lat:\"")
		        			  .append(rs.getString("lat")).append(",lng:\"")
		        			  .append(rs.getString("lng"));*/
		        	  
		        //	  j1.setdata(rs.getString("lat"), rs.getString("lng"), rs.getString("logtime"));
		        /*	  list.add(rs.getString("logtime"));
		        	  list.add(rs.getString("lat"));
		        	  list.add(rs.getString("lng"));*/
		       // 	  jsonArray1.add(JSONObject1[i]);
		        	  JSONObject1[i] = new JSONObject();
		        	  JSONObject1[i].put("time",rs.getString("gpstime"));
		        	  JSONObject1[i].put("lat",rs.getString("lat"));
		        	  JSONObject1[i].put("lng", rs.getString("lng"));
		        	  String address =  rs.getString("address");
		        	  if (address == null)
		        	  {
		        		  JSONObject1[i].put("address","nogps" );
		        	  }else
		        	  {  JSONObject1[i].put("address",address );}
		        	  
		        	  String locationtype =  rs.getString("locationtype");
		        	  if (locationtype == null)
		        	  {
		        		  JSONObject1[i].put("locationtype","（网络定位-" );
		        	  }else
		        	  { switch (Integer.valueOf(locationtype))
							{
							case 1:
								locationtype = "（GPS定位-";
								break;
							case 2:
								locationtype = "（网络定位-";
								break;	
							case 4:
								locationtype = "（网络定位-";
								break;
							case 5:
								locationtype = "（wifi定位-";
								break;
							case 6:
								locationtype = "（基站定位-";
								break;
							case 8:
								locationtype = "（网络定位-";
								break;
							default:
								locationtype = "（网络定位-";
								break;
							}
		        		  JSONObject1[i].put("locationtype",locationtype );}
		        	  
		        	  String accuracy =  rs.getString("accuracy");
		        	  if (accuracy == null)
		        	  {
		        		  JSONObject1[i].put("accuracy","精度:500米）\n" );
		        	  }else
		        		  accuracy = "精度:"+ accuracy + "米）\n";
		        	  {  JSONObject1[i].put("accuracy",accuracy );}
		        	  
		        	//  list.addAll(j1); //添加User对象         
		        	  
		        	  i += 1;
				  }
		          
		          m.put("gps", jsonArray1); 
			//rs.getString("lat")+" "+rs.getString("lng")+" "+
		    //      System.out.println("ok2\n");
		      JSONArray jsonArray2 = JSONArray.fromObject( list );          
			           
			//      System.out.println("ok3\n");   
			     
	          //把java数组转化成转化成json对象         
	        JSONObject jo=JSONObject.fromObject(m);//转化Map对象     
	    //    System.out.println("ok4");
	   //     System.out.print(jsonArray1);//返给ajax请求        
	   //     System.out.print(jo);//返给ajax请求        
	        
	        
	      /*  JSONObject jo1= new JSONObject();
	        JSONObject jo2= new JSONObject();
	        JSONObject jo3= new JSONObject();
	        jo1.put("time", "2016-04-03 12:44:12");
	        jo1.put("lat", "23.0167");
	        jo1.put("lng", "113.353259");
	        jo2.put("time", "2016-04-13 18:44:12");
	        jo2.put("lat", "23.0267");
	        jo2.put("lng", "113.363259");*/
	        List<Object> list1 = new ArrayList<Object>();
	        for (int j = 0; j < JSONObject1.length; j++)
			{
	        	list1.add(JSONObject1[j]);
			}
	        System.out.println(JSONObject1.length);  
	        cs = conn.prepareCall("{call fzd_getackinfo(?,?,?,?,?,?)}");  
	        cs.setString("owner", owner); 
	        cs.setString("serialno", serialno);  
		     cs.registerOutParameter("batter", Types.VARCHAR);  
		     cs.registerOutParameter("lastack", Types.VARCHAR); 
		     cs.registerOutParameter("bieming", Types.VARCHAR);  
		     cs.registerOutParameter("pic", Types.VARCHAR); 
		     cs.execute();
		     
		     String lastack = cs.getString("lastack");  
		     String batter = cs.getString("batter"); 
		     String bieming = cs.getString("bieming");  
		     String pic = cs.getString("pic"); 
	      
	        JSONArray jsonArray3 = JSONArray.fromObject( list1 );     
	        JSONObject jo3= new JSONObject();
	        jo3.put("gps", jsonArray3);
	        jo3.put("batter", batter);
	        jo3.put("lastack", lastack);
	        jo3.put("bieming", bieming);
	        jo3.put("pic", pic);
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
	


}
