package com.qa.utils;

import com.qa.pojo.Book;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestUtility {
	
	
	public static String fetchValueFromResponse(Response rawResponse,String key)
	{
		String stringResponse=rawResponse.asString();
		JsonPath jsonPath=new JsonPath(stringResponse);
		String value=jsonPath.get(key);
		return value;
		
	}
	public static String fetchValueFromResponse1(JsonPath jsonResponse,String key)
	{
		String value=jsonResponse.get(key);
		return value;
	}
	
	public static JsonPath convertResponseIntoJSON(Response rawResponse)
	{
		String stringResponse=rawResponse.asString();
		JsonPath jsonResponse=new JsonPath(stringResponse);
		return jsonResponse;
	}
	
	public static Book generatePayLoadforAddBook()
	{
		
		Book book=new Book("fhgaad","ttvgwe","552","MWiprobookautho12344");
		return book;
		
	}

	
}
