package com.qa.tests;
import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pojo.Api;
import com.qa.pojo.GetCourseDetails;
import com.qa.pojo.WebAutomation;
import com.qa.utils.TestUtils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class GetCourseOauthAPI extends TestBase {

	/*
	 * 1:Ask devloper what is the url to generate Authorization Code
	 * 2:Ask dev what is the url to generate Access token:POST CALL
	 * 3:Hit the endpoint by passing Access token:GET CALL
	 * 
	 */
	public static Response rawResponse;
	@BeforeMethod
	public void setup()
	{
		TestBase testbase=new TestBase();
		RestAssured.baseURI=prop.getProperty("GetCourseBaseURL");
	}
	
	@Test(enabled=false)
	public void accessOauthAPI()
	{
	
	String response="https://rahulshettyacademy.com/getCourse.php?state=abcd&code=4%2F0AY0e-g4qJc6No8TeNdCn57rjPcFwqpw-x7R8hwz0szIenjsUeVQupg9x2_ceybcZUzZJhw&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String s="abc-def-xyz";
		String []a=s.split("-");
		for(String element:a)
		{
			System.out.println(element);
		}
	
	String []b=response.split("code=");
	
	for(String element:b)
	{
		System.out.println(element);
		System.out.println();
	}
	String[]c=b[1].split("&");
	System.out.println("######Split on the basis of & ######## ");
	for(String element:c)
	{
		System.out.println(element);
		System.out.println();
	}
	System.out.println();
	System.out.println(c[0]);
	String authzCode=c[0];
	
	//Generate access token
rawResponse=given().log().all().urlEncodingEnabled(false).
	queryParam("code", authzCode).and().
	queryParam("client_secret", prop.getProperty("client_secret")).and().
	queryParam("client_id", prop.getProperty("client_id")).and().
	queryParam("redirect_uri", prop.getProperty("redirect_uri")).and().
	queryParam("grant_type", prop.getProperty("grant_type")).
	when().post(prop.getProperty("GoogleRes")).
	then().log().all().and().
	assertThat().statusCode(200).and().
	extract().response();

	String accessToken=TestUtils.fetchValueFromResponse(rawResponse, "access_token");
	System.out.println("Token is: " +accessToken);

	//Access resource by passing access token
	RestAssured.baseURI=prop.getProperty("rahulshettyacademybaseurl");
	rawResponse=given().log().all().queryParam("access_token", accessToken).
	when().get(prop.getProperty("getcourseres")).
	then().log().all().and().assertThat().statusCode(200).and().
	extract().response();
	System.out.println("Instructor is : " +TestUtils.fetchValueFromResponse(rawResponse, "instructor"));
	
	//Deserialization using POJO classes
	/*
	 *During Deserialization instruct Restassured to treat the response as JSON. 
	 *If you dont specify then you will get
	 *java.lang.IllegalStateException: Cannot parse object because no supported Content-Type was 
	 *specified in response. 
	 */
	
	GetCourseDetails gc=given().log().all().queryParam("access_token", accessToken).
			expect().defaultParser(Parser.JSON).
	when().get(prop.getProperty("getcourseres")).
	then().log().all().and().assertThat().statusCode(200).
	and().extract().response().as(GetCourseDetails.class);
	
	//extract().response().as(GetCourseDetails.class)  ==>This is deserialization.Convert Json into java object
	System.out.println("Instructor is : " + gc.getInstructor());
	
	List<WebAutomation>webAutomation=gc.getCourses().getwebAutomation();
	List<Api>api=gc.getCourses().getapi();
	/*System.out.println("Size is : " +webAutomation.size());
	String t=webAutomation.get(1).getCourseTitle();
	System.out.println(t);*/
	
	for(WebAutomation wb:webAutomation)
	{
		//System.out.println(1);
		if(wb.getCourseTitle().equalsIgnoreCase("Selenium Webdriver Java"))
			System.out.println("Selenium price is : "+wb.getPrice());
		//System.out.println(wb.getCourseTitle());
		
	}
	for(Api ap:api)
	{
		System.out.println(ap.getCourseTitle() +" :  " + ap.getPrice());
	}
	
	
	
	
	}
	
	
	
	@Test
	public void accessOauthAPIUsingRequestResponseSpec()
	{
	
	String response="https://rahulshettyacademy.com/getCourse.php?state=abcd&code=4%2F0AY0e-g7OeUA2SW4OXhSR8znNK9-x7MgMW6_YSTTTHkuefO10PdNhrgng5BYX_3bvm7Gfrg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		String s="abc-def-xyz";
		String []a=s.split("-");
		for(String element:a)
		{
			System.out.println(element);
		}
	
	String []b=response.split("code=");
	
	for(String element:b)
	{
		System.out.println(element);
		System.out.println();
	}
	String[]c=b[1].split("&");
	System.out.println("######Split on the basis of & ######## ");
	for(String element:c)
	{
		System.out.println(element);
		System.out.println();
	}
	System.out.println();
	System.out.println(c[0]);
	String authzCode=c[0];
	
	//Generate access token
rawResponse=given().log().all().urlEncodingEnabled(false).
	queryParam("code", authzCode).and().
	queryParam("client_secret", prop.getProperty("client_secret")).and().
	queryParam("client_id", prop.getProperty("client_id")).and().
	queryParam("redirect_uri", prop.getProperty("redirect_uri")).and().
	queryParam("grant_type", prop.getProperty("grant_type")).
	when().post(prop.getProperty("GoogleRes")).
	then().log().all().and().
	assertThat().statusCode(200).and().
	extract().response();

	String accessToken=TestUtils.fetchValueFromResponse(rawResponse, "access_token");
	System.out.println("Token is: " +accessToken);

	//Access resource by passing access token
	RestAssured.baseURI=prop.getProperty("rahulshettyacademybaseurl");
	rawResponse=given().log().all().queryParam("access_token", accessToken).
	when().get(prop.getProperty("getcourseres")).
	then().log().all().and().assertThat().statusCode(200).and().
	extract().response();
	System.out.println("Instructor is : " +TestUtils.fetchValueFromResponse(rawResponse, "instructor"));
	
	//Deserialization using POJO classes
	/*
	 *During Deserialization instruct Restassured to treat the response as JSON. 
	 *If you dont specify then you will get
	 *java.lang.IllegalStateException: Cannot parse object because no supported Content-Type was 
	 *specified in response. 
	 */
	
	RequestSpecification requestSpecification; // This is interface
	ResponseSpecification responseSpecification; // This is interface
	
	RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder(); // This is class for builder request
	requestSpecBuilder.setContentType(ContentType.JSON);
	requestSpecBuilder.addQueryParam("access_token", accessToken);
	requestSpecification=requestSpecBuilder.build();
	
	ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
	
	responseSpecBuilder.expectStatusCode(200);
	responseSpecification=responseSpecBuilder.build();
	
	GetCourseDetails gc=given().log().all().spec(requestSpecification).
			
			expect().defaultParser(Parser.JSON).
	when().get(prop.getProperty("getcourseres")).
	then().log().all().and().spec(responseSpecification).
	and().extract().response().as(GetCourseDetails.class);
	
	System.out.println("Instructor is : " + gc.getInstructor());
	
	List<WebAutomation>webAutomation=gc.getCourses().getwebAutomation();
	List<Api>api=gc.getCourses().getapi();
	/*System.out.println("Size is : " +webAutomation.size());
	String t=webAutomation.get(1).getCourseTitle();
	System.out.println(t);*/
	
	for(WebAutomation wb:webAutomation)
	{
		//System.out.println(1);
		if(wb.getCourseTitle().equalsIgnoreCase("Selenium Webdriver Java"))
			System.out.println("Selenium price is : "+wb.getPrice());
		//System.out.println(wb.getCourseTitle());
		
	}
	for(Api ap:api)
	{
		System.out.println(ap.getCourseTitle() +" :  " + ap.getPrice());
	}
	
	
	
	
	}
	
	
	
	
	
}
