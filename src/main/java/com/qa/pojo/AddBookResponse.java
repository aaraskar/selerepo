package com.qa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddBookResponse {

	
	private String Msg;
	private String ID;
	public String toString()
	{
		
		return "Message is :" +Msg + " and ID is : " +ID;
	}
	
	
	
	public String getMsg()
	{
		return Msg;
	}
	


	public String getID()
	{
		return ID;
	}
	
}
