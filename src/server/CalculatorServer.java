package server;

import java.util.LinkedHashMap;
import java.util.Stack;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import model.ResponseMessage;
import model.StackCalculator;

/**
 * 
 * This class handles all of the GET and PUT requests needed for the execution of the Calculator.
 * The CalculatorServer contains multiple useful methods for the execution of the tasks at hand.
 * These methods are: push, peek, pop, add, subtract, multiply and divide.
 * 
 * @author Katarina Mededovic
 * 
 */
@Path("{id}")
public class CalculatorServer {
	
	
	private static LinkedHashMap<Integer, StackCalculator> calculators = new LinkedHashMap<Integer, StackCalculator>();
	
	/**
	 * 
	 * The default constructor for the class CalculatorServer.
	 * 
	 */
	public CalculatorServer() {}
	
	/**
	 * 
	 * Pushes the number n onto the stack of the calculator with the given id.
	 * 
	 * @param  id The id of the stack which is to be used.
	 * @param  n  The number which is to be pushed onto the stack.
	 * @return    The Response in a JSON format.
	 * 
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/push/{n}")
	public Response push(@PathParam("id") int id, @PathParam("n") int n) {
		Response resource = null;
		if (calculators.containsKey(id)) {
			calculators.get(id).getStack().push(n);							
		} else {
			StackCalculator newCalculator = new StackCalculator();
			newCalculator.getStack().push(n);
			calculators.put(id, newCalculator);
		}
		Stack<Integer> currStack = calculators.get(id).getStack();
		ResponseMessage rm = new ResponseMessage();
		rm.setStatusCode(Response.Status.OK.getStatusCode());
		rm.setResponseMessage("The top of the stack is " + currStack.peek() + ".");
		resource = Response.status(Status.OK)
				.entity(rm)
				.build();
		
		return resource;
	}
	
	/**
	 * 
	 * Returns the top of the stack of the calculator with the given id.
	 * 
	 * @param  id The id of the calculator which is to be used.
	 * @return    The Response in a JSON format.
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/peek")
	public Response peek(@PathParam("id") int id) {
		Response resource = null;
		ResponseMessage rm = new ResponseMessage();
		if (calculators.containsKey(id) && !calculators.get(id).getStack().isEmpty()) {
			Stack<Integer> currStack = calculators.get(id).getStack();
			rm.setStatusCode(Response.Status.OK.getStatusCode());
			rm.setResponseMessage("The top of the stack is " + currStack.peek() + ".");
			resource = Response.status(Status.OK)
					.entity(rm)
					.build();		
		} else {
			rm.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());
			rm.setResponseMessage("The calculator with id " + id + " does not exist.");
			resource = Response.status(Status.NOT_FOUND)
					.entity(rm)
					.build();
		}
		return resource;
	}
	
	/**
	 * 
	 * Returns the top of the stack of the calculator with the given id, and removes it.
	 * 
	 * @param  id The id of the calculator which is to be used.
	 * @return    The Response in a JSON format.
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pop")
	public Response pop(@PathParam("id") int id) {
		Response resource = null;
		ResponseMessage rm = new ResponseMessage();
		if (calculators.containsKey(id) && !calculators.get(id).getStack().isEmpty()) {
			int popped = calculators.get(id).getStack().pop();
			rm.setResponseMessage("Popped top of the stack is " + popped + ".");
			rm.setStatusCode(Response.Status.OK.getStatusCode());
			resource = Response.status(Status.OK)
					.entity(rm)
					.build();
		} else {
			rm.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());
			rm.setResponseMessage("The stack is empty or the calculator with the id " + id + " does not exist.");
			resource = Response.status(Status.NOT_FOUND)
					.entity(rm)
					.build();
		}
		return resource;
	}
	
	/**
	 * 
	 * Removes the top and top-1 of the stack of the calculator with the given id, and replaces 
	 * them with their sum (i.e.<!-- --> the expression stack[top-1]+stack[top]).
	 * 
	 * @param  id The id of the calculator which is to be used.
	 * @return    The Response in a JSON format.
	 * 
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/add")
	public Response add(@PathParam("id") int id) {
		Response resource = null;
		ResponseMessage rm = new ResponseMessage();
		if (calculators.containsKey(id) && (calculators.get(id).getStack().size() >= 2)) {		
			Stack<Integer> currStack = calculators.get(id).getStack();		
			int op1 = currStack.pop();
			int op2 = currStack.pop();
			int sum = op1 + op2;		
			currStack.push(sum);			
			rm.setStatusCode(Response.Status.OK.getStatusCode());
			rm.setResponseMessage("The top of the stack is " + currStack.peek() + ".");
			resource = Response.status(Status.OK)
					.entity(rm)
					.build();
		} else {
			rm.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());
			rm.setResponseMessage("There are not enough elements on the stack.");
			resource = Response.status(Status.NOT_FOUND)
					.entity(rm)
					.build();
		}
		return resource;
	}
	
	/**
	 * 
	 * Removes the top and top-1 of the stack of the calculator with the given id, and replaces 
	 * them with their difference (i.e.<!-- --> the expression stack[top-1]-stack[top]).
	 * 
	 * @param  id The id of the calculator which is to be used.
	 * @return    The Response in a JSON format.
	 * 
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/subtract")
	public Response subtract(@PathParam("id") int id) {
		Response resource = null;
		ResponseMessage rm = new ResponseMessage();
		if ((calculators.containsKey(id)) && (calculators.get(id).getStack().size() >= 2)) {
			Stack<Integer> currStack = calculators.get(id).getStack();			
			int op1 = currStack.pop();
			int op2 = currStack.pop();
			int diff = op2 - op1;		
			currStack.push(diff);
			rm.setStatusCode(Response.Status.OK.getStatusCode());
			rm.setResponseMessage("The top of the stack is " + currStack.peek() + ".");
			resource = Response.status(Status.OK)
					.entity(rm)
					.build();
		} else {
			rm.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());
			rm.setResponseMessage("There are not enough elements on the stack.");
			resource = Response.status(Status.NOT_FOUND)
					.entity(rm)
					.build();
		}
		return resource;
	}
	
	/**
	 * 
	 * Removes the top and top-1 of the stack of the calculator with the given id, and replaces 
	 * them with their product (i.e.<!-- --> the expression stack[top-1]*stack[top]).
	 * 
	 * @param  id The id of the calculator which is to be used.
	 * @return    The Response in a JSON format.
	 * 
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/multiply")
	public Response multiply(@PathParam("id") int id) {
		Response resource = null;
		ResponseMessage rm = new ResponseMessage();
		if ((calculators.containsKey(id)) && (calculators.get(id).getStack().size() >= 2)) {			
			Stack<Integer> currStack = calculators.get(id).getStack();		
			int op1 = currStack.pop();
			int op2 = currStack.pop();
			int product = op1 * op2;		
			currStack.push(product);
			rm.setStatusCode(Response.Status.OK.getStatusCode());
			rm.setResponseMessage("The top of the stack is " + currStack.peek() + ".");
			resource = Response.status(Status.OK)
					.entity(rm)
					.build();
		} else {
			rm.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());
			rm.setResponseMessage("There are not enough elements on the stack.");
			resource = Response.status(Status.NOT_FOUND)
					.entity(rm)
					.build();
		}
		return resource;
	}
	
	/**
	 * 
	 * Removes the top and top-1 of the stack of the calculator with the given id, and replaces 
	 * them with their quotient obtained by using integer division (i.e.<!-- --> the expression 
	 * stack[top-1]/stack[top]).
	 * 
	 * @param  id The id of the calculator which is to be used.
	 * @return    The Response in a JSON format.
	 * 
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/divide")
	public Response divide(@PathParam("id") int id) {
		Response resource = null;
		ResponseMessage rm = new ResponseMessage();
		if ((calculators.containsKey(id)) && (calculators.get(id).getStack().size() >= 2)) {
			Stack<Integer> currStack = calculators.get(id).getStack();	
			if (currStack.peek() == 0) {
				rm.setStatusCode(Response.Status.FORBIDDEN.getStatusCode());
				rm.setResponseMessage("Division by zero is forbidden.");
				resource = Response.status(Status.FORBIDDEN)
						.entity(rm)
						.build();
			} else {
				int op1 = currStack.pop();
				int op2 = currStack.pop();
				int quotient = op2 / op1;		
				currStack.push(quotient);
				rm.setStatusCode(Response.Status.OK.getStatusCode());
				rm.setResponseMessage("The top of the stack is " + currStack.peek() + ".");
				resource = Response.status(Status.OK)
						.entity(rm)
						.build();
			}
		} else {
			rm.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());
			rm.setResponseMessage("There are not enough elements on the stack.");
			resource = Response.status(Status.NOT_FOUND)
					.entity(rm)
					.build();
		}
		return resource;
	}
}
