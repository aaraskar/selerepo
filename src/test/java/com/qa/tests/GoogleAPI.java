package com.qa.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.utils.AddPlace;
import com.qa.utils.Location;
import com.qa.utils.TestUtils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GoogleAPI extends TestBase {

	public Response rawResponse;
	public JsonPath jsonResponse;
	public Map<String,Object>payLoad=new HashMap<>();
	
	@BeforeMethod
	public void setup()
	{
		TestBase testbase=new TestBase();
		RestAssured.baseURI=prop.getProperty("GoogleAPIBaseURL");
		
	}
	
	@Test
	public void testAddPlace()
	{
		
		/*Location location=new Location(-38.383494,33.427362);
		String[] types= {"shoe park","shop"};
		AddPlace addPlace=new AddPlace(50,"ajFrontline house4Dec","(+91) 983 893 3937","29, side layout, cohen 09",
			types,"http://google.com","French-IN",location);*/
		AddPlace addPlace=TestUtils.javaPayLoad();
		
		
		payLoad=TestUtils.createPayLoadForAddPlace();
		rawResponse=given().log().all().
		queryParam("key", prop.getProperty("GoogleAPIKey")).
		//header("Content-Type","application/json").
		//body(payLoad).
		body(addPlace).
		when().post(prop.getProperty("Addplaceresource")).  // prop.getProperty("Addplaceresource") is resource name
		then().log().all().and().
		assertThat().statusCode(200).and().
		extract().response();
		//String res=rawResponse.toString();
		
		/*String res=rawResponse.asString();
		JsonPath js=new JsonPath(res);*/
		jsonResponse=TestUtils.convertToJsonResponse(rawResponse);
		String status=TestUtils.fetchValueFromResponse1(jsonResponse, "status");
		
		
		//String status=js.get("status");
		System.out.println("Status is : " +status);
		System.out.println("Status code is : " +rawResponse.statusCode());
		
	}
}
