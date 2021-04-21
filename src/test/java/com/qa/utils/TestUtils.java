package com.qa.utils;

import java.util.HashMap;
import java.util.Map;

import com.qa.base.TestBase;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestUtils extends TestBase {
	
	
	public static JsonPath convertToJsonResponse(Response rawResponse)
	{
			
		String stringResponse=rawResponse.asString();
		JsonPath jsonResponse=new JsonPath(stringResponse);
		return jsonResponse;
	}
	
	public static String fetchValueFromResponse1(JsonPath jsonResponse,String key)
	{
		String value=jsonResponse.get(key);
		return value;
	}
	
	public static String fetchValueFromResponse(Response rawResponse,String key)
	{
		String stringResponse=rawResponse.asString();
		JsonPath jsonResponse=new JsonPath(stringResponse);
		String value=jsonResponse.get(key);
		return value;
	}
	
	public static Map<String, Object> createPayLoadForAddPlace()
	{
		
		String []s= {"shoe park","shop"};
		Map<String,Object>locationMap=new HashMap<>();
		locationMap.put("lat", -38.383494);
		locationMap.put("lng", 33.427362);
		
		Map<String,Object>addPlaceMap=new HashMap<>();
		addPlaceMap.put("accuracy", 50);
		addPlaceMap.put("name", "ajplace4dec");
		addPlaceMap.put("phone_number", "(+91) 983 893 3937");
		addPlaceMap.put("address", "pune kothrud");
		addPlaceMap.put("website", "http://google.com");
		addPlaceMap.put("language", "French-IN");
		addPlaceMap.put("location", locationMap);
		addPlaceMap.put("types", s);
		return addPlaceMap;
	}
	
	public static AddPlace javaPayLoad()
	{
		
		Location location=new Location(-38.383494,33.427362);
		String[] types= {"shoe park","shop"};
		AddPlace addPlace=new AddPlace(50,"ajFrontline house4Dec","(+91) 983 893 3937","29 side layout cohen 09",
			types,"http://google.com","French-IN",location);
		return addPlace;
	}
	
	public static Map<String, String> createSessionPayLoad()
	{
		TestBase t=new TestBase();
		Map<String,String>sessionMap=new HashMap<>();
		sessionMap.put("username", prop.getProperty("jirausername"));
		sessionMap.put("password", prop.getProperty("jirapassword"));
		return sessionMap;
		
		
	}
	
	public static Map<String, Object> createIssuePayload()
	{
		Map<String,String>projMap=new HashMap<>();
		Map<String,String>issueTypeMap=new HashMap<>();
		Map<String,Object>createIssueMap=new HashMap<>();
		Map<String,Object>finalcreateIssueMap=new HashMap<>();
		
		projMap.put("key", "RES");
		issueTypeMap.put("name", "Bug");
		createIssueMap.put("project", projMap);
		createIssueMap.put("summary", "Can not create WG in OIM using sessioFilter");
		createIssueMap.put("description", "Creating sample issue for REST API automation");
		createIssueMap.put("issuetype",issueTypeMap);
		finalcreateIssueMap.put("fields", createIssueMap);
		return finalcreateIssueMap;
		
			
	}

}
