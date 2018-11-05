package com.mededovic.stackcalculator.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class TestCalculatorServerIntegration {
	
	 @Before
	 public void setUp() throws IOException, JSONException {
		 URL url = new URL("http://localhost:8080/StackCalculator/calc/1/clear");
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("DELETE");
		 con.getResponseCode();
		
		 String inputLine;
		 BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content.append(inputLine);
		 }
		 content.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responseClear = new JSONObject(content.toString());
		 String responseMessageClear = responseClear.getString("responseMessage");
		 int statusCodeClear = responseClear.getInt("statusCode");
		 
		 assertEquals("All calculators cleared", responseMessageClear);
		 assertEquals(Status.OK.getStatusCode(), statusCodeClear);		 
	 }
	 
	 @AfterClass
	 public static void tearDown() throws IOException, JSONException {
		 URL url = new URL("http://localhost:8080/StackCalculator/calc/1/clear");
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("DELETE");
		 con.getResponseCode();
		
		 String inputLine;
		 BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content.append(inputLine);
		 }
		 content.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responseClear = new JSONObject(content.toString());
		 String responseMessageClear = responseClear.getString("responseMessage");
		 int statusCodeClear = responseClear.getInt("statusCode");
		 
		 assertEquals("All calculators cleared", responseMessageClear);
		 assertEquals(Status.OK.getStatusCode(), statusCodeClear);		 
	 }
	 
	 @Test
	 public void testPush() throws IOException, JSONException {
		 //The calculator with id 1 does not exist
		 //Push 1 onto the stack of the calculator with id 1 (create stack)
		 StringBuffer content = makeAPIcall("http://localhost:8080/StackCalculator/calc/1/push/1", "PUT", Status.CREATED.getStatusCode());
		 	 
		 JSONObject response = new JSONObject(content.toString());
		 String responseMessage = response.getString("responseMessage");
		 int statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 1.", responseMessage);
		 assertEquals(Status.CREATED.getStatusCode(), statusCode);
		 
		 //Push 5 onto the stack of the calculator with id 2 (the stack contains {1})
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/1/push/5", "PUT", Status.OK.getStatusCode());
	 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 5.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
	 }
	
	 @Test
	 public void testPeekAndPush() throws IOException, JSONException {
		 //The calculator with id 2 does not exist
		 StringBuffer content = makeAPIcall("http://localhost:8080/StackCalculator/calc/2/peek", "GET", Status.NOT_FOUND.getStatusCode());
		 
		 JSONObject response = new JSONObject(content.toString());
		 String responseMessage = response.getString("responseMessage");
		 int statusCode = response.getInt("statusCode");
		 
		 assertEquals("The stack is empty or the calculator with id 2 does not exist.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);
		 
		 //Push 7 onto the stack of the calculator with id 2 (create stack)
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/2/push/7", "PUT", Status.CREATED.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 7.", responseMessage);
		 assertEquals(Status.CREATED.getStatusCode(), statusCode);
		 		 
		 //The stack contains {7}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/2/peek", "GET", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 7.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //Push 3 onto the stack of the calculator with id 2 (the stack contains {7} before pushing)
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/2/push/3", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //The stack contains {7,3}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/2/peek", "GET", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
	 }
	
	 @Test
	 public void testPop() throws IOException, JSONException {
		 //The calculator with id 3 does not exist
		 StringBuffer content = makeAPIcall("http://localhost:8080/StackCalculator/calc/3/pop", "PUT", Status.NOT_FOUND.getStatusCode());
		 
		 JSONObject response = new JSONObject(content.toString());
		 String responseMessage = response.getString("responseMessage");
		 int statusCode = response.getInt("statusCode");
		 
		 assertEquals("The stack is empty or the calculator with the id 3 does not exist.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);
		 
		 //Push 3 onto the stack of the calculator with id 3 (create stack)
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/3/push/3", "PUT", Status.CREATED.getStatusCode());	 
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessage);
		 assertEquals(Status.CREATED.getStatusCode(), statusCode);
		 
		 //The stack contains {3}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/3/pop", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("Popped top of the stack is 3.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //The stack is empty 
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/3/pop", "PUT", Status.NOT_FOUND.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The stack is empty or the calculator with the id 3 does not exist.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);	 
	 }
	
	 @Test
	 public void testAdd() throws IOException, JSONException {
		 //The calculator with id 4 does not exist
		 StringBuffer content = makeAPIcall("http://localhost:8080/StackCalculator/calc/4/add", "PUT", Status.NOT_FOUND.getStatusCode());
		 		 
		 JSONObject response = new JSONObject(content.toString());
		 String responseMessage = response.getString("responseMessage");
		 int statusCode = response.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);
		 
		 //Push 8 onto the stack of the calculator with id 4 (create stack)
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/4/push/8", "PUT", Status.CREATED.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 8.", responseMessage);
		 assertEquals(Status.CREATED.getStatusCode(), statusCode);	 
		 
		 //Push 5 onto the stack of the calculator with id 4 (the stack contains {8})
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/4/push/5", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 5.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //Push 10 onto the stack of the calculator with id 4 (the stack contains {8,5})
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/4/push/10", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 10.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //	The stack contains {8,5,10}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/4/add", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 15.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //The stack contains {8,15}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/4/add", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 23.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
	 
		 //The stack contains {23}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/4/add", "PUT", Status.NOT_FOUND.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);
	 }
	
	 @Test
	 public void testSubtract() throws IOException, JSONException {
		 //The calculator with id 5 does not exist
		 StringBuffer content = makeAPIcall("http://localhost:8080/StackCalculator/calc/5/subtract", "PUT", Status.NOT_FOUND.getStatusCode());
		 		 
		 JSONObject response = new JSONObject(content.toString());
		 String responseMessage = response.getString("responseMessage");
		 int statusCode = response.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);
		 
		 //Push 6 onto the stack of the calculator with id 5 (create stack)
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/5/push/6", "PUT", Status.CREATED.getStatusCode()); 
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 6.", responseMessage);
		 assertEquals(Status.CREATED.getStatusCode(), statusCode);
		 
		 //Push 28 onto the stack of the calculator with id 5 (the stack contains {6})
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/5/push/28", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 28.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //Push 18 onto the stack of the calculator with id 5 (the stack contains {6,28})
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/5/push/18", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 18.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //	The stack contains {6,28,18}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/5/subtract", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 10.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);

		 
		 //The stack contains {6,10}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/5/subtract", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -4.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
	 
		 //The stack contains {-4}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/5/subtract", "PUT", Status.NOT_FOUND.getStatusCode());

		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);
	 }
	
	 @Test
	 public void testMultiply() throws IOException, JSONException {
		 //The calculator with id 6 does not exist
		 StringBuffer content = makeAPIcall("http://localhost:8080/StackCalculator/calc/6/multiply", "PUT", Status.NOT_FOUND.getStatusCode());
		 		 
		 JSONObject response = new JSONObject(content.toString());
		 String responseMessage = response.getString("responseMessage");
		 int statusCode = response.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);
		 
		 //Push 7 onto the stack of the calculator with id 6 (create stack)
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/6/push/7", "PUT", Status.CREATED.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 7.", responseMessage);
		 assertEquals(Status.CREATED.getStatusCode(), statusCode);	 
		 
		 //Push 2 onto the stack of the calculator with id 6 (the stack contains {7})
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/6/push/2", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 2.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //Push -4 onto the stack of the calculator with id 6 (the stack contains {7,2})
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/6/push/-4", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -4.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //	The stack contains {7,2,-4}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/6/multiply", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -8.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
 
		 //The stack contains {7,-8}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/6/multiply", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -56.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
 
		 //The stack contains {-56}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/6/multiply", "PUT", Status.NOT_FOUND.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);
	 }
	
	 @Test
	 public void testDivide() throws IOException, JSONException {
		 //The calculator with id 7 does not exist
		 StringBuffer content = makeAPIcall("http://localhost:8080/StackCalculator/calc/7/divide", "PUT", Status.NOT_FOUND.getStatusCode());
		 		 
		 JSONObject response = new JSONObject(content.toString());
		 String responseMessage = response.getString("responseMessage");
		 int statusCode = response.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);
		 
		 //Push 7 onto the stack of the calculator with id 7 (create stack)
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/7/push/7", "PUT", Status.CREATED.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 7.", responseMessage);
		 assertEquals(Status.CREATED.getStatusCode(), statusCode);
		 	 
		 //Push 8 onto the stack of the calculator with id 7 (the stack contains {7})
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/7/push/8", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 8.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //Push -4 onto the stack of the calculator with id 7 (the stack contains {7,8})
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/7/push/-4", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -4.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //	The stack contains {7,8,-4}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/7/divide", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -2.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //The stack contains {7,-2}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/7/divide", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -3.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
	 
		 //The stack contains {-3}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/7/divide", "PUT", Status.NOT_FOUND.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);
		 
		 //Push 0 onto the stack of the calculator with id 7 (the stack contains {-3})
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/7/push/0", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 0.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //The stack contains {-3,0}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/7/divide", "PUT", Status.FORBIDDEN.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("Division by zero is forbidden.", responseMessage);
		 assertEquals(Status.FORBIDDEN.getStatusCode(), statusCode);
	 }
	
	 @Test
	 public void testEverythingCombined() throws IOException, JSONException {
		 //The calculator with id 8 does not exist
		 StringBuffer content = makeAPIcall("http://localhost:8080/StackCalculator/calc/8/peek", "GET", Status.NOT_FOUND.getStatusCode());
		 
		 JSONObject response = new JSONObject(content.toString());
		 String responseMessage = response.getString("responseMessage");
		 int statusCode = response.getInt("statusCode");
		 
		 assertEquals("The stack is empty or the calculator with id 8 does not exist.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);
		 
		 //Push 9 onto the stack of the calculator with id 8 (create stack)
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/8/push/9", "PUT", Status.CREATED.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 9.", responseMessage);
		 assertEquals(Status.CREATED.getStatusCode(), statusCode);
		 
		 //Push 3 onto the stack of the calculator with id 8 (the stack contains {9} before pushing)
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/8/push/3", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);	 
		 
		 //The stack of the calculator with id 8 contains {9,3}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/8/pop", "GET", Status.METHOD_NOT_ALLOWED.getStatusCode());

		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("HTTP 405 Method Not Allowed", responseMessage);
		 assertEquals(Status.METHOD_NOT_ALLOWED.getStatusCode(), statusCode);	 
		 
		 //The stack of the calculator with id 8 contains {9,3}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/8/peek", "GET", Status.OK.getStatusCode());
	 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //The stack of the calculator with id 8 contains {9,3}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/8/divide", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);

		 //Push 3 onto the stack of the calculator with id 9 (create stack)
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/9/push/3", "PUT", Status.CREATED.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessage);
		 assertEquals(Status.CREATED.getStatusCode(), statusCode);
		 
		 //Push 7 onto the stack of the calculator with id 9 (the stack contains {3} before pushing)
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/9/push/7", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 7.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //Push -2 onto the stack of the calculator with id 9 (the stack contains {3,7} before pushing)
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/9/push/-2", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -2.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
		 
		 //The stack of the calculator with id 9 contains {3,7,-2}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/9/po", "PUT", Status.NOT_FOUND.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("HTTP 404 Not Found", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);
		 	 
		 //The stack of the calculator with id 9 contains {3,7,-2}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/9/add", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 5.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);

		 //The stack of the calculator with id 9 contains {3,5}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/9/subtract", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -2.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);

		 //The stack of the calculator with id 8 contains {3}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/8/multiply", "PUT", Status.NOT_FOUND.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessage);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCode);

		 //The stack of the calculator with id 8 contains {3}
		 content = makeAPIcall("http://localhost:8080/StackCalculator/calc/8/pop", "PUT", Status.OK.getStatusCode());
		 
		 response = new JSONObject(content.toString());
		 responseMessage = response.getString("responseMessage");
		 statusCode = response.getInt("statusCode");
		 
		 assertEquals("Popped top of the stack is 3.", responseMessage);
		 assertEquals(Status.OK.getStatusCode(), statusCode);
	 }
	 
	 private StringBuffer makeAPIcall(String urlLoc, String method, int statusCode) throws IOException {
		 URL url = new URL(urlLoc);
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod(method);
		 
		 int responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, statusCode);
		 
		 String inputLine;	 
		 InputStream inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		 StringBuffer content = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content.append(inputLine);
		 }
		 content.append("\n");
		 in.close();
		 con.disconnect();
		 return content;	 
	 }
}
