package com.qa.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pojo1.CoursesDetails;
import com.qa.pojo1.webAutomation;
import com.qa.utils.TestUtility;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class OauthAPITest extends TestBase {

	public static Response rawResponse;
	@BeforeMethod
	public void setup()
	{
		initialization();
		RestAssured.baseURI=prop.getProperty("courseBaseURI");
		
	}
	
	@Test
	public void validateOauthAPI()
	{
		
		//1:Get AuthZ code by manually accessing. Get Call
		//2:Generate access token. Post call
		//3:Hit the end point by supplying the access token
		
		String url="https://rahulshettyacademy.com/getCourse.php?state=abcd&code=4%2F0AY0e-g77Mp3bpoDahomkHjYl63Le7L6ohagDbFMx15uN5PxQkYmq_ggcu_CmagMCFsMo6g&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
		String a[]=url.split("code=");
		for(String element:a)
			System.out.println(element);
		String[]b=a[1].split("&");
		System.out.println("Printing final array");
		for(String element:b)
			System.out.println(element);
		System.out.println("Status code is : "+b[0]);
		String authZCode=b[0];
		
		rawResponse=given().log().all().urlEncodingEnabled(false).
		
		  queryParam("code", authZCode).and(). queryParam("client_secret",
		  "erZOWM9g3UtwNRj340YYaK_W").and(). queryParam("client_id",
		  "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
		  and(). queryParam("redirect_uri",
		  "https://rahulshettyacademy.com/getCourse.php").and().
		  queryParam("grant_type", "authorization_code").and().
		  when().post(prop.getProperty("tokenResource")).
		  then().assertThat().statusCode(200).and().extract().response();
		 
	
		
		String accessToken=TestUtility.fetchValueFromResponse(rawResponse, "access_token");
		System.out.println("Access_Token is: " +accessToken);
		
		RestAssured.baseURI=prop.getProperty("coursbaseuri1");
		//Below approach uses JsonPath to fecth response
		/*
		 * rawResponse=given().log().all().queryParam("access_token", accessToken).
		 * when().get(prop.getProperty("getCourseRes")).
		 * then().assertThat().statusCode(200).and(). extract().response(); String
		 * instructor=TestUtility.fetchValueFromResponse(rawResponse, "instructor");
		 * System.out.println("Instructor is : " +instructor); String
		 * title=TestUtility.fetchValueFromResponse(rawResponse,
		 * "courses.webAutomation[0].courseTitle"); System.out.println("Title is : "
		 * +title);
		 */
		
		//Below approach uses deserialization
		CoursesDetails cd=new CoursesDetails();
		
		cd=given().log().all().queryParam("access_token", accessToken).
				expect().defaultParser(Parser.JSON). //OOTB type is text/html.We have to instruct RestAssured to treat response as Json 
				when().get(prop.getProperty("getCourseRes")).
				then().log().all().statusCode(200).and().
				extract().response().as(CoursesDetails.class);
		
		System.out.println("Instrctor name from deserilization is : "+cd.getinstructor());
		//Find number of courses in each category
		List<webAutomation>webAutomationList=cd.getcourses().getwebAutomation();
		System.out.println("Number of webAutomation courses are: "+webAutomationList.size());
		for(webAutomation wc:webAutomationList)
			System.out.println(wc.getcourseTitle());
		
		
		
		
		
		
	}
}
