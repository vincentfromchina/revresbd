package com.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class Jmpush
{
	 protected static final Logger LOG = LoggerFactory.getLogger(Jmpush.class);
	 public  static JPushClient jpushClient=null;
	 
	 public static void SendPush(String masterSecret  ,String appKey) 
	 {	 
	 
		 jpushClient = new JPushClient(masterSecret, appKey);
		 PushPayload payload = buildPushObject_all_alias_alert();
				 
		 try
		{
			PushResult result = jpushClient.sendPush(payload);
			
			System.out.println("Got result - " + result);
		} catch (APIConnectionException e)
		{
			System.out.println("Connection error, should retry later"+e);
			e.printStackTrace();
		} catch (APIRequestException e)
		{
			System.out.println("Should review the error, and fix the request"+e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			e.printStackTrace();
		}
	
	 }
	 
	private static PushPayload buildPushObject_all_alias_alert()
	{
		  return PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.alias("867451023222273"))
	                .setNotification(Notification.alert("天气炎热，做好防暑工作"))
	                .build();
	}
	 
	 
}
