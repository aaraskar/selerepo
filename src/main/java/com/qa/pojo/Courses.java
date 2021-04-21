package com.qa.pojo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Courses {
	
	List<WebAutomation>webAutomation=new ArrayList<>();
	List<Api>api=new ArrayList<>();
	List<Mobile>mobile=new ArrayList<>();
	
	/*public Courses()
	{
		
	}*/
	public List<WebAutomation> getwebAutomation()
	{
		return webAutomation;
	}

	public List<Api> getapi()
	{
		return api;
	}
	public List<Mobile> getmobile()
	{
		return mobile;
	}
	
}
