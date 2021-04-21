package com.qa.tests;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.utils.TestUtils;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
public class JIRAApi extends TestBase {
	
	Map<String,String>sessionMap;
	public Response rawResponse;
	public static String sessionId;
	public static SessionFilter sessionFilter;
	@BeforeMethod
	public void setup()
	{
		TestBase testbase=new TestBase();
		sessionFilter=new SessionFilter();
		RestAssured.baseURI=prop.getProperty("JIRAAPIBaseURL");
		sessionMap=TestUtils.createSessionPayLoad();
		rawResponse=given().log().all().filter(sessionFilter).
				body(sessionMap).
				header("Content-Type","application/json").// if you dont include this then you will get error code 415
				
		when().post(prop.getProperty("creatsessionresource")).
		then().assertThat().statusCode(200).and().
		extract().response();
		System.out.println("Status code is : " +rawResponse.statusCode());
		sessionId=TestUtils.fetchValueFromResponse(rawResponse, "session.value");
		System.out.println("Session id is : " +sessionId);
		
	}
	@Test(enabled=false,priority=1)
	public void createIssueUsingSessionFilter()
	{
		Map<String,Object>createIssueMap=TestUtils.createIssuePayload();
		rawResponse=given().log().all().
				filter(sessionFilter).//here we passed cookie information using sessionFilter
				header("Content-Type","application/json").
		 
		body(createIssueMap).
		when().post(prop.getProperty("createissueresource")).
		then().log().all().and().
		assertThat().statusCode(201).and().
		extract().response();
		
	}
	
	@Test(enabled=false,priority=2)
	public void createIssueWithoutUsingSessionFilter()
	{
		Map<String,Object>createIssueMap=TestUtils.createIssuePayload();
		String jsessionValue="JSESSIONID=".concat(sessionId);
		rawResponse=given().log().all().header("Content-Type","application/json").
		header("cookie",jsessionValue). //here we passed cookie information using cookie header
		body(createIssueMap).
		when().post(prop.getProperty("createissueresource")).
		then().log().all().and().
		assertThat().statusCode(201).and().
		extract().response();
		
	}
	
	@Test
	public void addAttachment()
	{
		
		String temp="/".concat("10004/attachments");
		rawResponse=given().log().all().
		header("Content-Type", "multipart/form-data").
		filter(sessionFilter).
		header("X-Atlassian-Token","no-check").
		multiPart("file",new File("C:\\Users\\araskar\\Desktop\\1.jpg")).
		when().post(prop.getProperty("createissueresource").concat(temp)).
		then().log().all().and().assertThat().statusCode(200).and().
		extract().response();
		
	}
	
	/*@Test
	public void createSession()
	{
		sessionMap=TestUtils.createSessionPayLoad();
		rawResponse=given().log().all().body(sessionMap).
				header("Content-Type","application/json").// if you dont include this then you will get error code 415
		when().post(prop.getProperty("creatsessionresource")).
		then().assertThat().statusCode(200).and().
		extract().response();
		System.out.println("Status code is : " +rawResponse.statusCode());
	}*/
	
	
	

}
