package test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;


public class TestCalculatorServerIntegration {
	
	@Test
	 public void testAdd() throws IOException, JSONException {
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
		 assertEquals(200, statusCodePush1);
		 
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
		 assertEquals(200, statusCodePush2);
		 
		 url = new URL("http://localhost:8080/StackCalculator/calc/1/add");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 
		 in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 StringBuffer content3 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content3.append(inputLine);
		 }
		 content3.append("\n");
		 in.close();
		 
		 JSONObject responseAdd = new JSONObject(content3.toString());
		 String responseMessageAdd = responseAdd.getString("responseMessage");
		 int statusCodeAdd = responseAdd.getInt("statusCode");
		 
		 assertEquals("The top of the stack is 6.", responseMessageAdd);
		 assertEquals(200, statusCodeAdd);

		 con.disconnect();
	 }
	
	@Test
	 public void testPeekAndPush() throws IOException, JSONException {
		 //The calculator with given id does not exist
		 URL url = new URL("http://localhost:8080/StackCalculator/calc/2/peek");
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("GET");
		 con.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
		 con.setRequestProperty("Accept","*/*");
		 
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
		 
		 JSONObject responsePeek1 = new JSONObject(content1.toString());
		 String responseMessagePeek1 = responsePeek1.getString("responseMessage");
		 int statusCodePeek1 = responsePeek1.getInt("statusCode");
		 
		 assertEquals("The stack is empty or the calculator with id 2 does not exist.", responseMessagePeek1);
		 assertEquals(Status.NOT_FOUND.getStatusCode(), statusCodePeek1);
		 
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
		 assertEquals(200, statusCodePush1);
		 
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
	 public void testSubtract() throws IOException, JSONException {
		 URL url = new URL("http://localhost:8080/StackCalculator/calc/7/push/3");
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
		 
		 assertEquals("The top of the stack is 3.", responseMessagePush1);
		 assertEquals(200, statusCodePush1);
		 

		 url = new URL("http://localhost:8080/StackCalculator/calc/7/subtract");
		 con = (HttpURLConnection) url.openConnection();
		 con.setRequestMethod("PUT");
		 con.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
		 con.setRequestProperty("Accept","*/*");
		 
		 int responseCode = con.getResponseCode();
		 
		 assertEquals(responseCode, 404);
		 
		 InputStream inputStream = con.getErrorStream();
		 if (inputStream == null) {
			 inputStream = con.getInputStream();
	     }
		 in = new BufferedReader(new InputStreamReader(inputStream));

		 StringBuffer content3 = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 content3.append(inputLine);
		 }
		 content3.append("\n");
		 System.out.println(content3.toString());
		 in.close();
		
			 
		 JSONObject responseSubtract = new JSONObject(content3.toString());
		 String responseMessageSubtract = responseSubtract.getString("responseMessage");
		 int statusCodeSubtract = responseSubtract.getInt("statusCode");
			 
		 assertEquals("There are not enough elements on the stack.", responseMessageSubtract);
		 assertEquals(404, statusCodeSubtract);

		 con.disconnect();
	 }
	
	

}
