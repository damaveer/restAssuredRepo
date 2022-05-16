/*
 * Author: Vasanth Dama
 * summary: Test Case 1 Add Place.
 */

/******************************************************
Test Add new place in database
URI:  https://rahulshettyacademy.com/maps/api/place/add/json?key=qaclick123
Request Type: POST
Request Payload(Body): 
{
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "VBR",
  "phone_number": "(+91) 983 893 3937",
  "address": "address",
  "types": [
    "shoe park",
    "shop"
  ],
  "website": "http://google.com",
  "language": "French-IN"
}


********* Validations **********
Response Payload(Body) : 
{
     "status": "OK",
     "place_id": "eed9234b7f79710c0f8e443771fcb88f",
     "scope": "APP",
     "reference": "6cfc1dbd28a2528f1aaf20799ab96b436cfc1dbd28a2528f1aaf20799ab96b43",
     "id": "6cfc1dbd28a2528f1aaf20799ab96b43"
 }

Status Code : 200
Status Line : HTTP/1.1 200 OK
Content Type : application/json
Content Encoding : gzip

**********************************************************/

package com.foxapi.testCases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import com.foxapi.base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RestAssuredSample extends TestBase {

	RequestSpecification httpRequest;
	Response response;

	@BeforeClass
	void createPlace() throws InterruptedException, ParseException {
		logger.info("********* Started TC001_ADD_Place  **********");

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		httpRequest = RestAssured.given();

		// JSONObject is a class that represents a simple JSON. We can add Key-Value
		// pairs using the put method
		String jsonBody = "{\"location\": {    \"lat\": -38.383494,    \"lng\": 33.427362  },  \"accuracy\": 50,  \"name\": \"VBR\",  \"phone_number\": \"(+91) 983 893 3937\",  \"address\": \"address\",  \"types\": [    \"shoe park\",    \"shop\"  ],  \"website\": \"http://google.com\",  \"language\": \"French-IN\"}";

		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");

		// Add the Json to the body of the request
		httpRequest.body(jsonBody);

		response = httpRequest.request(Method.POST, "/maps/api/place/add/json?key=qaclick123");

		JSONParser parser = new JSONParser();

		JSONObject jsonObject = (JSONObject) parser.parse(response.getBody().asString());
		
		placeID = (String) jsonObject.get("place_id");
		
		Thread.sleep(5000);

	}
	
	@Test
	void getPlaceID() throws InterruptedException {
		logger.info("********* Started GetPlaceID **********");
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		httpRequest = RestAssured.given();
		
		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");

	
		response = httpRequest.request(Method.GET, String.format("/maps/api/place/get/json?key=qaclick123&place_id=%s", placeID));
		Thread.sleep(7000);
		
		String responseBody = response.getBody().asString();
		System.out.println("responseBody is :" + responseBody);

	}
	
	

	@Test
	void updatePlaceID() throws InterruptedException {
		logger.info("********* Started UpdatePlaceID **********");
		
		String updateBody = String.format("{ \"place_id\": \"%s\", \"address\": \"abc\", \"key\":\"qaclick123\" }", placeID);
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		httpRequest = RestAssured.given();
		
		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");
		
		// Add the Json to the body of the request
		httpRequest.body(updateBody);

	
		response = httpRequest.request(Method.PUT, "/maps/api/place/update/json?key=qaclick123");
		Thread.sleep(7000);
		
		String responseBody = response.getBody().asString();
		System.out.println("responseBody is :" + responseBody);

	}
	

	@Test(dependsOnMethods = {"updatePlaceID"})
	void deletePlaceID() throws InterruptedException {
		logger.info("********* Started UpdatePlaceID **********");
		
		String updateBody = String.format("{ \"place_id\": \"%s\" }", placeID);
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		httpRequest = RestAssured.given();
		
		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");
		
		// Add the Json to the body of the request
		httpRequest.body(updateBody);

	
		response = httpRequest.request(Method.DELETE, "/maps/api/place/delete/json?key=qaclick123");
		Thread.sleep(7000);
		
		String responseBody = response.getBody().asString();
		System.out.println("responseBody is :" + responseBody);

	}


	@Test
	void checkResposeBody() throws ParseException

	{
		String responseBody = response.getBody().asString();
		System.out.println("responseBody is :" + responseBody);

	}

	@Test
	void checkStatusCode() {
		int statusCode = response.getStatusCode(); // Gettng status code
		Assert.assertEquals(statusCode, 200);
	}

	@Test
	void checkstatusLine() {
		String statusLine = response.getStatusLine(); // get the status Line
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");

	}

	@Test
	void checkContentType() {
		String contentType = response.header("Content-Type");
		Assert.assertEquals(contentType, "application/json;charset=UTF-8");
	}

	@AfterClass
	void tearDown() {
		logger.info("*********  Finished C001_REG_user **********");
	}

}
