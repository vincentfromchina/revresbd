package com.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.*;
import java.security.Policy.Parameters;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.util.ByteArrayBuilder;

/**
 * Servlet implementation class Upfile
 */
public class Upfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		InputStream is =  request.getInputStream();
		System.out.println("ContentLength"+request.getContentLength());
	
		int tolcount = request.getContentLength();
		String ContentType = request.getContentType();

		
		byte[] bs = new byte[tolcount+100];
		
	/* 
	    String result = new String(bs,"GBK");
	    int findstr = result.indexOf("sid");
	 
		System.out.println(result.substring(findstr+6,findstr+28));
		int filename = result.indexOf("filename");
		System.out.println(result.substring(result.indexOf("\"",filename)+1,result.indexOf("\"",filename+12)));
	//	System.out.println(bs);
		int blank = result.indexOf("\r\n\r\n",filename);
		String txtfile = result.substring(blank+4,result.indexOf("--------",blank+1)-2);
		System.out.println(result.length());
      */  
	  
		 
	 	//创建一个FileWriter  内存数据 ->  磁盘文件(写入,输出)
	 	//内存数据 <-  磁盘文件(读入,输入)
	//	FileWriter  f=new FileWriter("f:\\myCounter.txt");
	//	FileWriter  f=new FileWriter("f:\\mypic.png");
   // 	BufferedWriter bw=new BufferedWriter(f);
		File ff = new File(this.getServletConfig().getServletContext().getRealPath("/")+"img\\mypic.png");
		FileOutputStream bw= new FileOutputStream(ff);
    	//在文件中写入数据	
    //	int bs[] = new int[request.getContentLength()];
    	
   
		int g = 0;
		int tc = 0;
		
		while (( tc = is.read()) != -1) {
		//	System.out.println(tc);
			bs[g] =(byte)tc;
		//	System.out.println(bs[g]);
			g += 1;
		} 
		
		System.out.println(new String(bs,"utf-8"));
		
	int str_begin = 0;
	int str_end = 0;
	
	/*
		for (int i=bs.length-1;i>0;i--)
		{
			if (str_begin!=0) break;
			if(bs[i]=='\n')
			{
			  if(bs[i-1]=='\r')
			  {
				  if(bs[i-2]=='\n')
				  {
					  if(bs[i-3]=='\r')
					  {
						  str_begin = i+1;
						  System.out.println("str_begin is:"+str_begin);
						  break;
					  }else
					  { continue;}
				  }else
				  {
					 continue;}
			  }else
			  {  continue;}
			}else {
				continue;
			}
			
		}  */
	
  for (int i = 0; i < bs.length; i++)
	{
		
		if (bs[i]=='f')
		{
			if (bs[i+1]=='i')
			{
				if (bs[i+2]=='l')
				{
					if (bs[i+3]=='e')
					{
						if (bs[i+4]=='n')
						{
							str_begin = i + 5;
							break;
						}
					}
				}
			}
		}
		
		if (str_begin!=0) break;
	}
  
     if (str_begin!=0)
     {
    	 for (int i = str_begin; i < bs.length; i++)
		{
    		 if(bs[i]=='\r')
    		 {
    			 if(bs[i+1]=='\n')
        		 {
    				 if(bs[i+2]=='\r')
            		 {
    					 if(bs[i+3]=='\n')
    	        		 {
    						 str_begin = i + 4;
    						 break;
    	        		 }
            		 }
        		 }
    		 }
		}
     }
	
		
		for(int j=bs.length-2;j>str_begin;j--)
		{
			int k = 1;
			if(bs[j]=='-')
			{
				for(;k<7;k++)
				{
				  
				  if ( k>=6 )
					  {
					  System.out.println("bs[j-k]:"+bs[j-k]+" j:"+j+" k:"+k);
					  
					   if(bs[j-k]=='\n')
					   { 
					    str_end = j -k;
					    break;
					   }
					//    break;
					  }
				  
				  if(bs[j-k]!='-') break;
				}
			}

			if (str_end!=0) break;
		}
		System.out.println("str_start is:"+str_begin);
		System.out.println("str_end is:"+str_end);
		

		bw.write(bs,str_begin,str_end-str_begin);
    // 	bw.write(bs);
    	//关闭文件流
    	bw.close();
		
	}

}
