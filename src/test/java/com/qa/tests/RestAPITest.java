package com.qa.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pojo.AddBookResponse;
import com.qa.pojo.Book;
import com.qa.utils.TestUtility;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RestAPITest extends TestBase {
	public static Response rawResponse;
	
	@BeforeMethod
	public void setup()
	{
		initialization();
		
		RestAssured.baseURI=prop.getProperty("baseuri");
	}
	
	@Test(enabled=false)
	public void verifyGetCallOnLibraryAPI()
	{
		rawResponse=given().log().all().queryParam("ID", "rcqzd227").
		//when().get("/Library/GetBook.php").
		when().get(prop.getProperty("getBookuri")).
		then().log().all().and().statusCode(200).and().header("Server", "Apache").and().
		extract().response();
		//String stringResponse=rawResponse.asString();
		String headerVal=rawResponse.header("Server");
		System.out.println("Header value for server is : " +headerVal);
		int statusCode=rawResponse.getStatusCode();
		System.out.println("Status code is : "+statusCode);
		JsonPath js=TestUtility.convertResponseIntoJSON(rawResponse);
		String name=js.get("[0].book_name");
		System.out.println(name);
		Assert.assertEquals(statusCode, 200);
		Assert.assertEquals(name, "CI Appium Automation with Java");
		System.out.println();
		String bkName=TestUtility.fetchValueFromResponse(rawResponse,"[0].book_name");
		System.out.println(bkName);
		
		
		//String bookName=TestUtility.fetchValueFromResponse1(jsonResponse, jsonResponse[0].book_name);
		
		
		
		//System.out.println(bookName);
		
	}
	
	
	@Test(enabled=false)
	public void addBookUsingHashMap()
	{
		Map<String,String>addbookMap=new HashMap<>();
		addbookMap.put("name", "Wiprobook12");
		addbookMap.put("isbn", "rrqzhb");
		addbookMap.put("aisle", "312");
		addbookMap.put("author", "Wiprobookauthor12");
		rawResponse=given().log().all().body(addbookMap).
		when().post(prop.getProperty("addBookuri")).
		then().log().all().and().assertThat().statusCode(200).and().contentType("application/json").
		extract().response();
		
		int statuCode=rawResponse.getStatusCode();
		Assert.assertEquals(statuCode, 200);
		String message= TestUtility.fetchValueFromResponse(rawResponse, "Msg");
		System.out.println("Message is :"+message);
		
		
		
	}
	
	@Test
	public void addBookUsingJavaObject()
	{
		
		  Map<String,String>addbookMap=new HashMap<>(); addbookMap.put("name",
		  "Wiprqqookk1212sa"); addbookMap.put("isbn", "rthsab"); addbookMap.put("aisle",
		  "943"); addbookMap.put("author", "Wiprobookaor12");
		 
		//Book AddBbookPayload=TestUtility.generatePayLoadforAddBook();
		/*
		 * rawResponse=given().log().all().body(AddBbookPayload).
		 * when().post(prop.getProperty("addBookuri")).
		 * then().log().all().and().assertThat().statusCode(200).and().contentType(
		 * "application/json"). extract().response();
		 */
		
		AddBookResponse res=given().log().all().body(addbookMap).expect().defaultParser(Parser.JSON).
				when().post(prop.getProperty("addBookuri")).
				then().log().all().and().assertThat().statusCode(200).and().contentType("application/json").
				extract().response().as(AddBookResponse.class);
		
		/*
		 * int statuCode=rawResponse.getStatusCode(); Assert.assertEquals(statuCode,
		 * 200); String message= TestUtility.fetchValueFromResponse(rawResponse, "Msg");
		 * System.out.println("Message is :"+message);
		 */
		System.out.println("Res Obj details are : " +res);
		System.out.println(res.getMsg());
		
		
	}


}

