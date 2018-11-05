package com.mededovic.stackcalculator.test;

import static org.junit.Assert.*;

import java.util.Stack;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mededovic.stackcalculator.model.ResponseMessage;
import com.mededovic.stackcalculator.model.StackCalculator;
import com.mededovic.stackcalculator.server.CalculatorServer;

public class TestCalculatorServer {
	private CalculatorServer calculatorServer;
	
	@Before
	public void setUp() {
        StackCalculator stackCalculator1 = new StackCalculator();
        calculatorServer = new CalculatorServer(1, stackCalculator1);
        
        Stack<Integer> stack2 = new Stack<Integer>();
        stack2.push(8);
        stack2.push(2);
        StackCalculator stackCalculator2 = new StackCalculator(stack2);
        calculatorServer.addCalculator(2, stackCalculator2);
        
        Stack<Integer> stack3 = new Stack<Integer>();
        stack3.push(3);
        stack3.push(5);
        stack3.push(0);
        StackCalculator stackCalculator3 = new StackCalculator(stack3);
        calculatorServer.addCalculator(5, stackCalculator3);
        
        Stack<Integer> stack4 = new Stack<Integer>();
        stack4.push(17);
        StackCalculator stackCalculator4 = new StackCalculator(stack4);
        calculatorServer.addCalculator(6, stackCalculator4);
        
	}
	
	@After
	public void tearDown() {
		calculatorServer = null;
	}
 
	@Test
	public void testPushSuccess() {
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(1));
		
		//This stack is initially empty
		Stack<Integer> stack = calculatorServer.getCalculators().get(1).getStack();
		assertTrue(stack.empty());
		Response pushResponse = calculatorServer.push(1, 3);
		
		//Make sure stack is correct
		assertTrue((stack.size() == 1) && (stack.peek() == 3));
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("The top of the stack is 3.", Response.Status.OK.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) pushResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) pushResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testPushNewCalcSuccess() {
		assertNotNull(calculatorServer);
		
		//Make sure that calculator 3 doesn't exist
		assertFalse(calculatorServer.getCalculators().containsKey(3));
		Response pushResponse = calculatorServer.push(3, 1);		
		assertNotNull(calculatorServer.getCalculators().get(3).getStack());
		
		//This stack is initially empty
		Stack<Integer> stack = calculatorServer.getCalculators().get(3).getStack();
		
		//Make sure that stack is correct
		assertTrue((stack.size() == 1) && stack.peek() == 1);
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("The top of the stack is 1.", Response.Status.CREATED.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) pushResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) pushResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testPeekSuccess() {
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(2));
		
		//This stack contains {8,2}
		Stack<Integer> stack = calculatorServer.getCalculators().get(2).getStack();
		assertTrue(stack.size() == 2);
		Response peekResponse = calculatorServer.peek(2);
		
		//Make sure it is correct item
		assertTrue(stack.peek() == 2);
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("The top of the stack is 2.", Response.Status.OK.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) peekResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) peekResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testPeekNotFound() {
		assertNotNull(calculatorServer);

		//Make sure that calculator doesn't exist
		assertFalse(calculatorServer.getCalculators().containsKey(4));
		Response peekResponse = calculatorServer.peek(4);
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("The stack is empty or the calculator with id 4 does not exist.", Response.Status.NOT_FOUND.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) peekResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) peekResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testPeekEmptyStack() {
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(1));
		
		//This stack is initially empty
		Stack<Integer> stack = calculatorServer.getCalculators().get(1).getStack();
		assertTrue(stack.isEmpty());
		Response peekResponse = calculatorServer.peek(1);
		
		//Make sure that stack is unchanged
		assertTrue(stack.isEmpty());
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("The stack is empty or the calculator with id 1 does not exist.", Response.Status.NOT_FOUND.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) peekResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) peekResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testPopSuccess() {
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(2));
		
		//This stack contains {8,2}
		Stack<Integer> stack = calculatorServer.getCalculators().get(2).getStack();
		assertTrue((stack.size() == 2) && (stack.peek() == 2));
		Response popResponse = calculatorServer.pop(2);
		
		//Make sure that stack has only 8 left - stack correct
		assertTrue((stack.size() == 1) && stack.peek() == 8);
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("Popped top of the stack is 2.", Response.Status.OK.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) popResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) popResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testPopNotFound() {
		assertNotNull(calculatorServer);
		
		//Make sure that calculator with id 4 doesn't exist
		assertFalse(calculatorServer.getCalculators().containsKey(4));
		Response popResponse = calculatorServer.pop(4);
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("The stack is empty or the calculator with the id 4 does not exist.", Response.Status.NOT_FOUND.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) popResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) popResponse.getEntity()).getStatusCode());		
	}
	
	@Test
	public void testPopEmptyStack() {
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(1));
		
		//stack empty
		Stack<Integer> stack = calculatorServer.getCalculators().get(1).getStack();
		assertTrue(stack.isEmpty());
		Response popResponse = calculatorServer.pop(1);
		
		//Make sure that stack is unchanged (empty)
		assertTrue(stack.isEmpty());
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("The stack is empty or the calculator with the id 1 does not exist.", Response.Status.NOT_FOUND.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) popResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) popResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testAddSuccess() {      
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(2));
		
		//The stack contains values {8,2}
		Stack<Integer> stack = calculatorServer.getCalculators().get(2).getStack();
		assertTrue(stack.size() == 2);
		Response addResponse = calculatorServer.add(2);
		
		//Make sure that stack is correct after add()
		assertTrue((stack.size() == 1) && stack.peek() == 10);
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("The top of the stack is 10.", Response.Status.OK.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) addResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) addResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testAddNotEnoughArguments() { 
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(6));
		
		//The stack contains value {17}
		Stack<Integer> stack = calculatorServer.getCalculators().get(6).getStack();
		assertTrue(stack.size() == 1);
		Response addResponse = calculatorServer.add(6);
		
		//Make sure no changes have been made
		assertTrue((stack.size() == 1) && (stack.peek() == 17));
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("There are not enough elements on the stack.", Response.Status.NOT_FOUND.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) addResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) addResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testAddNotFound() {  
		assertNotNull(calculatorServer);
		
		//Make sure that the calculator does not exist
		assertFalse(calculatorServer.getCalculators().containsKey(4));
		Response addResponse = calculatorServer.add(4);

		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("There are not enough elements on the stack.", Response.Status.NOT_FOUND.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) addResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) addResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testSubtractSuccess() {  
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(2));
		
		//The stack contains values {8,2}
		Stack<Integer> stack = calculatorServer.getCalculators().get(2).getStack();
		assertTrue(stack.size() == 2);
		Response subtractResponse = calculatorServer.subtract(2);
		
		//Make sure that stack is correct after subtract()
		assertTrue((stack.size() == 1) && stack.peek() == 6);
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("The top of the stack is 6.", Response.Status.OK.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) subtractResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) subtractResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testSubtractNotEnoughArguments() { 
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(6));
		
		//The stack contains value {17}
		Stack<Integer> stack = calculatorServer.getCalculators().get(6).getStack();
		assertTrue((stack.size() == 1) && (stack.peek() == 17));
		Response subtractResponse = calculatorServer.subtract(1);
		
		//Make sure stack didn't change
		assertTrue((stack.size() == 1) && (stack.peek() == 17));
				
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("There are not enough elements on the stack.", Response.Status.NOT_FOUND.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) subtractResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) subtractResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testSubtractNotFound() {   
		assertNotNull(calculatorServer);
		
		//Make sure the calculator doesn't exist
		assertFalse(calculatorServer.getCalculators().containsKey(4));
		Response subtractResponse = calculatorServer.subtract(4);

		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("There are not enough elements on the stack.", Response.Status.NOT_FOUND.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) subtractResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) subtractResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testMultiplySuccess() { 
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(2));
		
		//The stack contains values {8,2}
		Stack<Integer> stack = calculatorServer.getCalculators().get(2).getStack();
		assertTrue(stack.size() == 2);
		Response multiplyResponse = calculatorServer.multiply(2);
		
		//Make sure stack correct after multiply()
		assertTrue((stack.size() == 1) && stack.peek() == 16);
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("The top of the stack is 16.", Response.Status.OK.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) multiplyResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) multiplyResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testMultiplyNotEnoughArguments() {  
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(6));
		
		//The stack contains value {17}
		Stack<Integer> stack = calculatorServer.getCalculators().get(6).getStack();
		assertTrue((stack.size() == 1) && (stack.peek() == 17));
		Response multiplyResponse = calculatorServer.multiply(6);
		
		//Make sure stack didn't change
		assertTrue((stack.size() == 1) && (stack.peek() == 17));
				
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("There are not enough elements on the stack.", Response.Status.NOT_FOUND.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) multiplyResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) multiplyResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testMultiplyNotFound() {    
		assertNotNull(calculatorServer);
		
		//Make sure that the calculator doesn't exist
		assertFalse(calculatorServer.getCalculators().containsKey(4));	
		Response multiplyResponse = calculatorServer.multiply(4);

		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("There are not enough elements on the stack.", Response.Status.NOT_FOUND.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) multiplyResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) multiplyResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testDivideSuccess() {     
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(2));
		
		//The stack contains values {8,2}
		Stack<Integer> stack = calculatorServer.getCalculators().get(2).getStack();
		assertTrue(stack.size() == 2 && (stack.peek() != 0));
		Response divideResponse = calculatorServer.divide(2);
		
		//Make sure stack is correct
		assertTrue((stack.size() == 1) && (stack.peek() == 4));
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("The top of the stack is 4.", Response.Status.OK.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) divideResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) divideResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testDivideNotEnoughArguments() {   
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(6));
		
		//The stack contains value {17}
		Stack<Integer> stack = calculatorServer.getCalculators().get(6).getStack();
		assertTrue((stack.size() == 1) && (stack.peek() == 17));
		Response divideResponse = calculatorServer.divide(1);
		
		//Make sure stack didn't change
		assertTrue((stack.size() == 1) && (stack.peek() == 17));
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("There are not enough elements on the stack.", Response.Status.NOT_FOUND.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) divideResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) divideResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testDivideNotFound() {    
		assertNotNull(calculatorServer);
		
		//Make sure that the calculator doesn't exist
		assertFalse(calculatorServer.getCalculators().containsKey(4));		
		Response divideResponse = calculatorServer.divide(4);

		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("There are not enough elements on the stack.", Response.Status.NOT_FOUND.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) divideResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) divideResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testDivideByZero() {  
		assertNotNull(calculatorServer);
		assertTrue(calculatorServer.getCalculators().containsKey(5));
		
		//The stack contains values {3,5,0}
		Stack<Integer> stack = calculatorServer.getCalculators().get(5).getStack();
		assertTrue((stack.size() == 3) && (stack.peek() == 0));
		Response divideResponse = calculatorServer.divide(5);
		
		//Make sure stack didn't change
		assertTrue((stack.size() == 3) && stack.peek() == 0);
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("Division by zero is forbidden.", Response.Status.FORBIDDEN.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) divideResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) divideResponse.getEntity()).getStatusCode());
	}
	
	@Test
	public void testClear() {    
		assertNotNull(calculatorServer);	
		Response clearResponse = calculatorServer.clear();
		assertTrue(calculatorServer.getCalculators().isEmpty());
		
		//Make sure that the relevant response has been provided
		ResponseMessage rm = new ResponseMessage("All calculators cleared", Response.Status.OK.getStatusCode());		
		assertEquals(rm.getResponseMessage(), ((ResponseMessage) clearResponse.getEntity()).getResponseMessage());
		assertEquals(rm.getStatusCode(), ((ResponseMessage) clearResponse.getEntity()).getStatusCode());
	}
}
