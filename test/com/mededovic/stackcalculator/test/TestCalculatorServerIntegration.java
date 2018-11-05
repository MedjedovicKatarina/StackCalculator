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
		 URL url = new URL("http://localhost:8080/StackCalculator/calc/1/push/1");
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 
		 String inputLine;
		 StringBuffer content1 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content1.append(inputLine);
		 }
		 content1.append("\n");
		 in.close();
		 con.disconnect();
		 
		 
		 JSONObject responsePush1 = new JSONObject(content1.toString());
		 String responseMessagePush1 = responsePush1.getString("responseMessage");
		 int statusCodePush1 = responsePush1.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 1.", responseMessagePush1);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush1);
		 
		 //Push 5 onto the stack of the calculator with id 2 (the stack contains {1})
		 url = new URL("http://localhost:8080/StackCalculator/calc/1/push/5");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content2 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content2.append(inputLine);
		 }
		 content2.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush2 = new JSONObject(content2.toString());
		 String responseMessagePush2 = responsePush2.getString("responseMessage");
		 int statusCodePush2 = responsePush2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 5.", responseMessagePush2);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush2);

		 con.disconnect();
	 }
	
	 @Test
	 public void testPeekAndPush() throws IOException, JSONException {
		 //The calculator with id 2 does not exist
		 URL url = new URL("http://localhost:8080/StackCalculator/calc/2/peek");
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("GET");
		 
		 int responseCode = con.getResponseCode();		 
		 assertEquals(Status.NOT_FOUND.getStatusCode(), responseCode);
		 
		 String inputLine;	 
		 InputStream inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		 StringBuffer content1 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content1.append(inputLine);
		 }
		 content1.append("\n");
		 System.out.println(content1.toString());
		 in.close();
		 
		 JSONObject responsePeek1 = new JSONObject(content1.toString());
		 String responseMessagePeek1 = responsePeek1.getString("responseMessage");
		 int statusCodePeek1 = responsePeek1.getInt("statusCode");
		 
		 assertEquals("The stack is empty or the calculator with id 2 does not exist.", responseMessagePeek1);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodePeek1);
		 
		 //Push 7 onto the stack of the calculator with id 2 (create stack)
		 url = new URL("http://localhost:8080/StackCalculator/calc/2/push/7");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content2 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content2.append(inputLine);
		 }
		 content2.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush1 = new JSONObject(content2.toString());
		 String responseMessagePush1 = responsePush1.getString("responseMessage");
		 int statusCodePush1 = responsePush1.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 7.", responseMessagePush1);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush1);
		 
		 
		 //The stack contains {7}
		 url = new URL("http://localhost:8080/StackCalculator/calc/2/peek");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("GET");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content3 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content3.append(inputLine);
		 }
		 content3.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePeek2 = new JSONObject(content3.toString());
		 String responseMessagePeek2 = responsePeek2.getString("responseMessage");
		 int statusCodePeek2 = responsePeek2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 7.", responseMessagePeek2);
		 assertEquals(Status.OK.getStatusCode(), statusCodePeek2);
		 
		 //Push 3 onto the stack of the calculator with id 2 (the stack contains {7} before pushing)
		 url = new URL("http://localhost:8080/StackCalculator/calc/2/push/3");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content4 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content4.append(inputLine);
		 }
		 content4.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush2 = new JSONObject(content4.toString());
		 String responseMessagePush2 = responsePush2.getString("responseMessage");
		 int statusCodePush2 = responsePush2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessagePush2);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush2);
		 
		 //The stack contains {7,3}
		 url = new URL("http://localhost:8080/StackCalculator/calc/2/peek");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("GET");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content5 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content5.append(inputLine);
		 }
		 content5.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePeek3 = new JSONObject(content5.toString());
		 String responseMessagePeek3 = responsePeek3.getString("responseMessage");
		 int statusCodePeek3 = responsePeek3.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessagePeek3);
		 assertEquals(Status.OK.getStatusCode(), statusCodePeek3);

	 }
	
	 @Test
	 public void testPop() throws IOException, JSONException {
		 //The calculator with id 3 does not exist
		 URL url = new URL("http://localhost:8080/StackCalculator/calc/3/pop");
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("GET");
		 
		 int responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.NOT_FOUND.getStatusCode());
		 
		 String inputLine;	 
		 InputStream inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		 StringBuffer content1 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content1.append(inputLine);
		 }
		 content1.append("\n");
		 System.out.println(content1.toString());
		 in.close();
		 
		 JSONObject responsePop1 = new JSONObject(content1.toString());
		 String responseMessagePop1 = responsePop1.getString("responseMessage");
		 int statusCodePop1 = responsePop1.getInt("statusCode");
		 
		 assertEquals("The stack is empty or the calculator with the id 3 does not exist.", responseMessagePop1);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodePop1);
		 
		 //Push 3 onto the stack of the calculator with id 3 (create stack)
		 url = new URL("http://localhost:8080/StackCalculator/calc/3/push/3");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content2 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content2.append(inputLine);
		 }
		 content2.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush1 = new JSONObject(content2.toString());
		 String responseMessagePush1 = responsePush1.getString("responseMessage");
		 int statusCodePush1 = responsePush1.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessagePush1);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush1);
		 
		 //The stack contains {3}
		 url = new URL("http://localhost:8080/StackCalculator/calc/3/pop");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("GET");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content3 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content3.append(inputLine);
		 }
		 content3.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePop2 = new JSONObject(content3.toString());
		 String responseMessagePop2 = responsePop2.getString("responseMessage");
		 int statusCodePop2 = responsePop2.getInt("statusCode");
		 
		 assertEquals("Popped top of the stack is 3.", responseMessagePop2);
		 assertEquals(Status.OK.getStatusCode(), statusCodePop2);
		 
		 //The stack is empty {}
		 url = new URL("http://localhost:8080/StackCalculator/calc/3/pop");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("GET");
		 responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.NOT_FOUND.getStatusCode());
		 
		 inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 in = new BufferedReader(new InputStreamReader(inputStream));

		 StringBuffer content4 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content4.append(inputLine);
		 }
		 content4.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePop3 = new JSONObject(content4.toString());
		 String responseMessagePop3 = responsePop3.getString("responseMessage");
		 int statusCodePop3 = responsePop3.getInt("statusCode");
		 
		 assertEquals("The stack is empty or the calculator with the id 3 does not exist.", responseMessagePop3);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodePop3);	 
	 }
	
	 @Test
	 public void testAdd() throws IOException, JSONException {
		//The calculator with id 4 does not exist
		 URL url = new URL("http://localhost:8080/StackCalculator/calc/4/add");
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 int responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.NOT_FOUND.getStatusCode());
		 
		 String inputLine;	 
		 InputStream inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		 StringBuffer content1 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content1.append(inputLine);
		 }
		 content1.append("\n");
		 in.close();
		 con.disconnect();
		 		 
		 JSONObject responseAdd1 = new JSONObject(content1.toString());
		 String responseMessageAdd1 = responseAdd1.getString("responseMessage");
		 int statusCodeAdd1 = responseAdd1.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessageAdd1);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodeAdd1);
		 
		 //Push 8 onto the stack of the calculator with id 4 (create stack)
		 url = new URL("http://localhost:8080/StackCalculator/calc/4/push/8");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content2 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content2.append(inputLine);
		 }
		 content2.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush1 = new JSONObject(content2.toString());
		 String responseMessagePush1 = responsePush1.getString("responseMessage");
		 int statusCodePush1 = responsePush1.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 8.", responseMessagePush1);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush1);
		 
		 
		 //Push 5 onto the stack of the calculator with id 4 (the stack contains {8})
		 url = new URL("http://localhost:8080/StackCalculator/calc/4/push/5");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content3 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content3.append(inputLine);
		 }
		 content3.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush2 = new JSONObject(content3.toString());
		 String responseMessagePush2 = responsePush2.getString("responseMessage");
		 int statusCodePush2 = responsePush2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 5.", responseMessagePush2);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush2);
		 
		 //Push 10 onto the stack of the calculator with id 4 (the stack contains {8,5})
		 url = new URL("http://localhost:8080/StackCalculator/calc/4/push/10");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content4 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content4.append(inputLine);
		 }
		 content4.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush3 = new JSONObject(content4.toString());
		 String responseMessagePush3 = responsePush3.getString("responseMessage");
		 int statusCodePush3 = responsePush3.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 10.", responseMessagePush3);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush3);
		 
		 //	The stack contains {8,5,10}
		 url = new URL("http://localhost:8080/StackCalculator/calc/4/add");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content5 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content5.append(inputLine);
		 }
		 content5.append("\n");
		 in.close();
		 
		 JSONObject responseAdd2 = new JSONObject(content5.toString());
		 String responseMessageAdd2 = responseAdd2.getString("responseMessage");
		 int statusCodeAdd2 = responseAdd2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 15.", responseMessageAdd2);
		 assertEquals(Status.OK.getStatusCode(), statusCodeAdd2);

		 con.disconnect();
		 
		 //The stack contains {8,15}
		 url = new URL("http://localhost:8080/StackCalculator/calc/4/add");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content6 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content6.append(inputLine);
		 }
		 content6.append("\n");
		 in.close();
		 
		 JSONObject responseAdd3 = new JSONObject(content6.toString());
		 String responseMessageAdd3 = responseAdd3.getString("responseMessage");
		 int statusCodeAdd3 = responseAdd3.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 23.", responseMessageAdd3);
		 assertEquals(Status.OK.getStatusCode(), statusCodeAdd3);

		 con.disconnect();
		 
		 
		 //The stack contains {23}
		 url = new URL("http://localhost:8080/StackCalculator/calc/4/add");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.NOT_FOUND.getStatusCode());
		 
		 inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 in = new BufferedReader(new InputStreamReader(inputStream));

		 StringBuffer content7 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content7.append(inputLine);
		 }
		 content7.append("\n");
		 in.close();
		 
		 JSONObject responseAdd4 = new JSONObject(content7.toString());
		 String responseMessageAdd4 = responseAdd4.getString("responseMessage");
		 int statusCodeAdd4 = responseAdd4.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessageAdd4);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodeAdd4);

		 con.disconnect();
	 }
	
	 @Test
	 public void testSubtract() throws IOException, JSONException {
		 //The calculator with id 5 does not exist
		 URL url = new URL("http://localhost:8080/StackCalculator/calc/5/subtract");
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 int responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.NOT_FOUND.getStatusCode());
		 
		 String inputLine;	 
		 InputStream inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		 StringBuffer content1 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content1.append(inputLine);
		 }
		 content1.append("\n");
		 in.close();
		 con.disconnect();
		 		 
		 JSONObject responseSubtract1 = new JSONObject(content1.toString());
		 String responseMessageSubtract1 = responseSubtract1.getString("responseMessage");
		 int statusCodeSubtract1 = responseSubtract1.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessageSubtract1);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodeSubtract1);
		 
		 //Push 6 onto the stack of the calculator with id 5 (create stack)
		 url = new URL("http://localhost:8080/StackCalculator/calc/5/push/6");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content2 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content2.append(inputLine);
		 }
		 content2.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush1 = new JSONObject(content2.toString());
		 String responseMessagePush1 = responsePush1.getString("responseMessage");
		 int statusCodePush1 = responsePush1.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 6.", responseMessagePush1);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush1);
		 
		 
		 //Push 28 onto the stack of the calculator with id 5 (the stack contains {6})
		 url = new URL("http://localhost:8080/StackCalculator/calc/5/push/28");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content3 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content3.append(inputLine);
		 }
		 content3.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush2 = new JSONObject(content3.toString());
		 String responseMessagePush2 = responsePush2.getString("responseMessage");
		 int statusCodePush2 = responsePush2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 28.", responseMessagePush2);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush2);
		 
		 //Push 18 onto the stack of the calculator with id 5 (the stack contains {6,28})
		 url = new URL("http://localhost:8080/StackCalculator/calc/5/push/18");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content4 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content4.append(inputLine);
		 }
		 content4.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush3 = new JSONObject(content4.toString());
		 String responseMessagePush3 = responsePush3.getString("responseMessage");
		 int statusCodePush3 = responsePush3.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 18.", responseMessagePush3);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush3);
		 
		 //	The stack contains {6,28,18}
		 url = new URL("http://localhost:8080/StackCalculator/calc/5/subtract");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content5 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content5.append(inputLine);
		 }
		 content5.append("\n");
		 in.close();
		 
		 JSONObject responseSubtract2 = new JSONObject(content5.toString());
		 String responseMessageSubtract2 = responseSubtract2.getString("responseMessage");
		 int statusCodeSubtract2 = responseSubtract2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 10.", responseMessageSubtract2);
		 assertEquals(Status.OK.getStatusCode(), statusCodeSubtract2);

		 con.disconnect();
		 
		 //The stack contains {6,10}
		 url = new URL("http://localhost:8080/StackCalculator/calc/5/subtract");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content6 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content6.append(inputLine);
		 }
		 content6.append("\n");
		 in.close();
		 
		 JSONObject responseSubtract3 = new JSONObject(content6.toString());
		 String responseMessageSubtract3 = responseSubtract3.getString("responseMessage");
		 int statusCodeSubtract3 = responseSubtract3.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -4.", responseMessageSubtract3);
		 assertEquals(Status.OK.getStatusCode(), statusCodeSubtract3);

		 con.disconnect();
		 
		 
		 //The stack contains {-4}
		 url = new URL("http://localhost:8080/StackCalculator/calc/5/subtract");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.NOT_FOUND.getStatusCode());
		 
		 inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 in = new BufferedReader(new InputStreamReader(inputStream));

		 StringBuffer content7 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content7.append(inputLine);
		 }
		 content7.append("\n");
		 in.close();
		 
		 JSONObject responseSubtract4 = new JSONObject(content7.toString());
		 String responseMessageSubtract4 = responseSubtract4.getString("responseMessage");
		 int statusCodeSubtract4 = responseSubtract4.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessageSubtract4);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodeSubtract4);

		 con.disconnect();
	 }
	
	 @Test
	 public void testMultiply() throws IOException, JSONException {
		 //The calculator with id 6 does not exist
		 URL url = new URL("http://localhost:8080/StackCalculator/calc/6/multiply");
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 int responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.NOT_FOUND.getStatusCode());
		 
		 String inputLine;	 
		 InputStream inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		 StringBuffer content1 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content1.append(inputLine);
		 }
		 content1.append("\n");
		 in.close();
		 con.disconnect();
		 		 
		 JSONObject responseMultiply1 = new JSONObject(content1.toString());
		 String responseMessageMultiply1 = responseMultiply1.getString("responseMessage");
		 int statusCodeMultiply1 = responseMultiply1.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessageMultiply1);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodeMultiply1);
		 
		 //Push 7 onto the stack of the calculator with id 6 (create stack)
		 url = new URL("http://localhost:8080/StackCalculator/calc/6/push/7");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content2 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content2.append(inputLine);
		 }
		 content2.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush1 = new JSONObject(content2.toString());
		 String responseMessagePush1 = responsePush1.getString("responseMessage");
		 int statusCodePush1 = responsePush1.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 7.", responseMessagePush1);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush1);
		 
		 
		 //Push 2 onto the stack of the calculator with id 6 (the stack contains {7})
		 url = new URL("http://localhost:8080/StackCalculator/calc/6/push/2");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content3 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content3.append(inputLine);
		 }
		 content3.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush2 = new JSONObject(content3.toString());
		 String responseMessagePush2 = responsePush2.getString("responseMessage");
		 int statusCodePush2 = responsePush2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 2.", responseMessagePush2);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush2);
		 
		 //Push -4 onto the stack of the calculator with id 6 (the stack contains {7,2})
		 url = new URL("http://localhost:8080/StackCalculator/calc/6/push/-4");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content4 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content4.append(inputLine);
		 }
		 content4.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush3 = new JSONObject(content4.toString());
		 String responseMessagePush3 = responsePush3.getString("responseMessage");
		 int statusCodePush3 = responsePush3.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -4.", responseMessagePush3);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush3);
		 
		 //	The stack contains {7,2,-4}
		 url = new URL("http://localhost:8080/StackCalculator/calc/6/multiply");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content5 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content5.append(inputLine);
		 }
		 content5.append("\n");
		 in.close();
		 
		 JSONObject responseMultiply2 = new JSONObject(content5.toString());
		 String responseMessageMultiply2 = responseMultiply2.getString("responseMessage");
		 int statusCodeMultiply2 = responseMultiply2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -8.", responseMessageMultiply2);
		 assertEquals(Status.OK.getStatusCode(), statusCodeMultiply2);

		 con.disconnect();
		 
		 //The stack contains {7,-8}
		 url = new URL("http://localhost:8080/StackCalculator/calc/6/multiply");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content6 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content6.append(inputLine);
		 }
		 content6.append("\n");
		 in.close();
		 
		 JSONObject responseMultiply3 = new JSONObject(content6.toString());
		 String responseMessageMultiply3 = responseMultiply3.getString("responseMessage");
		 int statusCodeMultiply3 = responseMultiply3.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -56.", responseMessageMultiply3);
		 assertEquals(Status.OK.getStatusCode(), statusCodeMultiply3);

		 con.disconnect();
		 
		 
		 //The stack contains {-56}
		 url = new URL("http://localhost:8080/StackCalculator/calc/6/multiply");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.NOT_FOUND.getStatusCode());
		 
		 inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 in = new BufferedReader(new InputStreamReader(inputStream));

		 StringBuffer content7 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content7.append(inputLine);
		 }
		 content7.append("\n");
		 in.close();
		 
		 JSONObject responseMultiply4 = new JSONObject(content7.toString());
		 String responseMessageMultiply4 = responseMultiply4.getString("responseMessage");
		 int statusCodeMultiply4 = responseMultiply4.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessageMultiply4);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodeMultiply4);

		 con.disconnect();
	 }
	
	 @Test
	 public void testDivide() throws IOException, JSONException {
		 //The calculator with id 7 does not exist
		 URL url = new URL("http://localhost:8080/StackCalculator/calc/7/divide");
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 int responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.NOT_FOUND.getStatusCode());
		 
		 String inputLine;	 
		 InputStream inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		 StringBuffer content1 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content1.append(inputLine);
		 }
		 content1.append("\n");
		 in.close();
		 con.disconnect();
		 		 
		 JSONObject responseDivide1 = new JSONObject(content1.toString());
		 String responseMessageDivide1 = responseDivide1.getString("responseMessage");
		 int statusCodeDivide1 = responseDivide1.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessageDivide1);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodeDivide1);
		 
		 //Push 7 onto the stack of the calculator with id 7 (create stack)
		 url = new URL("http://localhost:8080/StackCalculator/calc/7/push/7");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content2 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content2.append(inputLine);
		 }
		 content2.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush1 = new JSONObject(content2.toString());
		 String responseMessagePush1 = responsePush1.getString("responseMessage");
		 int statusCodePush1 = responsePush1.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 7.", responseMessagePush1);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush1);
		 
		 
		 //Push 8 onto the stack of the calculator with id 7 (the stack contains {7})
		 url = new URL("http://localhost:8080/StackCalculator/calc/7/push/8");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content3 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content3.append(inputLine);
		 }
		 content3.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush2 = new JSONObject(content3.toString());
		 String responseMessagePush2 = responsePush2.getString("responseMessage");
		 int statusCodePush2 = responsePush2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 8.", responseMessagePush2);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush2);
		 
		 //Push -4 onto the stack of the calculator with id 7 (the stack contains {7,8})
		 url = new URL("http://localhost:8080/StackCalculator/calc/7/push/-4");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content4 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content4.append(inputLine);
		 }
		 content4.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush3 = new JSONObject(content4.toString());
		 String responseMessagePush3 = responsePush3.getString("responseMessage");
		 int statusCodePush3 = responsePush3.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -4.", responseMessagePush3);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush3);
		 
		 //	The stack contains {7,8,-4}
		 url = new URL("http://localhost:8080/StackCalculator/calc/7/divide");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content5 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content5.append(inputLine);
		 }
		 content5.append("\n");
		 in.close();
		 
		 JSONObject responseDivide2 = new JSONObject(content5.toString());
		 String responseMessageDivide2 = responseDivide2.getString("responseMessage");
		 int statusCodeDivide2 = responseDivide2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -2.", responseMessageDivide2);
		 assertEquals(Status.OK.getStatusCode(), statusCodeDivide2);

		 con.disconnect();
		 
		 //The stack contains {7,-2}
		 url = new URL("http://localhost:8080/StackCalculator/calc/7/divide");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content6 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content6.append(inputLine);
		 }
		 content6.append("\n");
		 in.close();
		 
		 JSONObject responseDivide3 = new JSONObject(content6.toString());
		 String responseMessageDivide3 = responseDivide3.getString("responseMessage");
		 int statusCodeDivide3 = responseDivide3.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -3.", responseMessageDivide3);
		 assertEquals(Status.OK.getStatusCode(), statusCodeDivide3);

		 con.disconnect();
		 
		 
		 //The stack contains {-3}
		 url = new URL("http://localhost:8080/StackCalculator/calc/7/divide");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.NOT_FOUND.getStatusCode());
		 
		 inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 in = new BufferedReader(new InputStreamReader(inputStream));

		 StringBuffer content7 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content7.append(inputLine);
		 }
		 content7.append("\n");
		 in.close();
		 
		 JSONObject responseDivide4 = new JSONObject(content7.toString());
		 String responseMessageDivide4 = responseDivide4.getString("responseMessage");
		 int statusCodeDivide4 = responseDivide4.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessageDivide4);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodeDivide4);

		 con.disconnect();
		 
		 //Push 0 onto the stack of the calculator with id 7 (the stack contains {-3})
		 url = new URL("http://localhost:8080/StackCalculator/calc/7/push/0");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content8 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content8.append(inputLine);
		 }
		 content8.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush4 = new JSONObject(content8.toString());
		 String responseMessagePush4 = responsePush4.getString("responseMessage");
		 int statusCodePush4 = responsePush4.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 0.", responseMessagePush4);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush4);
		 
		 //The stack contains {-3,0}
		 url = new URL("http://localhost:8080/StackCalculator/calc/7/divide");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.FORBIDDEN.getStatusCode());
		 
		 inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 in = new BufferedReader(new InputStreamReader(inputStream));

		 StringBuffer content9 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content9.append(inputLine);
		 }
		 content9.append("\n");
		 in.close();
		 
		 JSONObject responseDivide5 = new JSONObject(content9.toString());
		 String responseMessageDivide5 = responseDivide5.getString("responseMessage");
		 int statusCodeDivide5 = responseDivide5.getInt("statusCode");
		 
		 assertEquals("Division by zero is forbidden.", responseMessageDivide5);
		 assertEquals(Status.FORBIDDEN.getStatusCode(), statusCodeDivide5);

		 con.disconnect();
	 }
	
	 @Test
	 public void testFunctionality() throws IOException, JSONException {
		 //The calculator with id 8 does not exist
		 URL url = new URL("http://localhost:8080/StackCalculator/calc/8/peek");
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("GET");
		 
		 int responseCode = con.getResponseCode();		 
		 assertEquals(Status.NOT_FOUND.getStatusCode(), responseCode);
		 
		 String inputLine;	 
		 InputStream inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		 StringBuffer content1 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content1.append(inputLine);
		 }
		 content1.append("\n");
		 System.out.println(content1.toString());
		 in.close();
		 
		 JSONObject responsePeek1 = new JSONObject(content1.toString());
		 String responseMessagePeek1 = responsePeek1.getString("responseMessage");
		 int statusCodePeek1 = responsePeek1.getInt("statusCode");
		 
		 assertEquals("The stack is empty or the calculator with id 8 does not exist.", responseMessagePeek1);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodePeek1);
		 
		 //Push 9 onto the stack of the calculator with id 8 (create stack)
		 url = new URL("http://localhost:8080/StackCalculator/calc/8/push/9");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content2 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content2.append(inputLine);
		 }
		 content2.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush1 = new JSONObject(content2.toString());
		 String responseMessagePush1 = responsePush1.getString("responseMessage");
		 int statusCodePush1 = responsePush1.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 9.", responseMessagePush1);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush1);
		 
		 //Push 3 onto the stack of the calculator with id 8 (the stack contains {9} before pushing)
		 url = new URL("http://localhost:8080/StackCalculator/calc/8/push/3");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content3 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content3.append(inputLine);
		 }
		 content3.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush2 = new JSONObject(content3.toString());
		 String responseMessagePush2 = responsePush2.getString("responseMessage");
		 int statusCodePush2 = responsePush2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessagePush2);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush2);
		 
		 
		 //The stack of the calculator with id 8 contains {9,3}
		 url = new URL("http://localhost:8080/StackCalculator/calc/8/pop");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.METHOD_NOT_ALLOWED.getStatusCode());
		 
		 inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 in = new BufferedReader(new InputStreamReader(inputStream));

		 StringBuffer content4 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content4.append(inputLine);
		 }
		 content4.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePop1 = new JSONObject(content4.toString());
		 String responseMessagePop1 = responsePop1.getString("responseMessage");
		 int statusCodePop1 = responsePop1.getInt("statusCode");
		 
		 assertEquals("HTTP 405 Method Not Allowed", responseMessagePop1);
		 assertEquals(Status.METHOD_NOT_ALLOWED.getStatusCode(), statusCodePop1);
		 
		 
		 
		 //The stack of the calculator with id 8 contains {9,3}
		 url = new URL("http://localhost:8080/StackCalculator/calc/8/peek");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("GET");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content5 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content5.append(inputLine);
		 }
		 content5.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePeek2 = new JSONObject(content5.toString());
		 String responseMessagePeek2 = responsePeek2.getString("responseMessage");
		 int statusCodePeek2 = responsePeek2.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessagePeek2);
		 assertEquals(Status.OK.getStatusCode(), statusCodePeek2);
		 
		 //The stack of the calculator with id 8 contains {9,3}
		 url = new URL("http://localhost:8080/StackCalculator/calc/8/divide");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content6 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content6.append(inputLine);
		 }
		 content6.append("\n");
		 in.close();
		 
		 JSONObject responseDivide1 = new JSONObject(content6.toString());
		 String responseMessageDivide1 = responseDivide1.getString("responseMessage");
		 int statusCodeDivide1 = responseDivide1.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessageDivide1);
		 assertEquals(Status.OK.getStatusCode(), statusCodeDivide1);

		 con.disconnect();
		 
		
		 //Push 3 onto the stack of the calculator with id 9 (create stack)
		 url = new URL("http://localhost:8080/StackCalculator/calc/9/push/3");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content7 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content7.append(inputLine);
		 }
		 content7.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush3 = new JSONObject(content7.toString());
		 String responseMessagePush3 = responsePush3.getString("responseMessage");
		 int statusCodePush3 = responsePush3.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 3.", responseMessagePush3);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush3);
		 
		 //Push 7 onto the stack of the calculator with id 9 (the stack contains {3} before pushing)
		 url = new URL("http://localhost:8080/StackCalculator/calc/9/push/7");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content8 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content8.append(inputLine);
		 }
		 content8.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush4 = new JSONObject(content8.toString());
		 String responseMessagePush4 = responsePush4.getString("responseMessage");
		 int statusCodePush4 = responsePush4.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 7.", responseMessagePush4);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush4);
		 
		 //Push -2 onto the stack of the calculator with id 9 (the stack contains {3,7} before pushing)
		 url = new URL("http://localhost:8080/StackCalculator/calc/9/push/-2");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content9 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content9.append(inputLine);
		 }
		 content9.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePush5 = new JSONObject(content9.toString());
		 String responseMessagePush5 = responsePush5.getString("responseMessage");
		 int statusCodePush5 = responsePush5.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -2.", responseMessagePush5);
		 assertEquals(Status.OK.getStatusCode(), statusCodePush5);
		 
		 //The stack of the calculator with id 9 contains {3,7,-2}
		 url = new URL("http://localhost:8080/StackCalculator/calc/9/po");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.NOT_FOUND.getStatusCode());
		 
		 inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 in = new BufferedReader(new InputStreamReader(inputStream));

		 StringBuffer content10 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content10.append(inputLine);
		 }
		 content4.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responseBadRequest = new JSONObject(content10.toString());
		 String responseMessageBadRequest = responseBadRequest.getString("responseMessage");
		 int statusCodeBadRequest = responseBadRequest.getInt("statusCode");
		 
		 assertEquals("HTTP 404 Not Found", responseMessageBadRequest);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodeBadRequest);
		 
		 
		 //The stack of the calculator with id 9 contains {3,7,-2}
		 url = new URL("http://localhost:8080/StackCalculator/calc/9/add");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content11 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content11.append(inputLine);
		 }
		 content11.append("\n");
		 in.close();
		 
		 JSONObject responseAdd1 = new JSONObject(content11.toString());
		 String responseMessageAdd1 = responseAdd1.getString("responseMessage");
		 int statusCodeAdd1 = responseAdd1.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 5.", responseMessageAdd1);
		 assertEquals(Status.OK.getStatusCode(), statusCodeAdd1);

		 con.disconnect();
		 
		 //The stack of the calculator with id 9 contains {3,5}
		 url = new URL("http://localhost:8080/StackCalculator/calc/9/subtract");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content12 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content12.append(inputLine);
		 }
		 content12.append("\n");
		 in.close();
		 
		 JSONObject responseSubtract1 = new JSONObject(content12.toString());
		 String responseMessageSubtract1 = responseSubtract1.getString("responseMessage");
		 int statusCodeSubtract1 = responseSubtract1.getInt("statusCode");
		 
		 assertEquals("The top of the stack is -2.", responseMessageSubtract1);
		 assertEquals(Status.OK.getStatusCode(), statusCodeSubtract1);

		 con.disconnect();
		 
		 //The stack of the calculator with id 8 contains {3}
		 url = new URL("http://localhost:8080/StackCalculator/calc/8/multiply");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 responseCode = con.getResponseCode();		 
		 assertEquals(responseCode, Status.NOT_FOUND.getStatusCode());
		 
		 inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 in = new BufferedReader(new InputStreamReader(inputStream));
		 StringBuffer content13 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content13.append(inputLine);
		 }
		 content13.append("\n");
		 in.close();
		 
		 JSONObject responseMultiply1 = new JSONObject(content13.toString());
		 String responseMessageMultiply1 = responseMultiply1.getString("responseMessage");
		 int statusCodeMultiply1 = responseMultiply1.getInt("statusCode");
		 
		 assertEquals("There are not enough elements on the stack.", responseMessageMultiply1);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodeMultiply1);

		 con.disconnect();
		 
		 //The stack of the calculator with id 8 contains {3}
		 url = new URL("http://localhost:8080/StackCalculator/calc/8/pop");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("GET");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content14 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content14.append(inputLine);
		 }
		 content14.append("\n");
		 in.close();
		 con.disconnect();
		 
		 JSONObject responsePop2 = new JSONObject(content14.toString());
		 String responseMessagePop2 = responsePop2.getString("responseMessage");
		 int statusCodePop2 = responsePop2.getInt("statusCode");
		 
		 assertEquals("Popped top of the stack is 3.", responseMessagePop2);
		 assertEquals(Status.OK.getStatusCode(), statusCodePop2);
	 }
}
