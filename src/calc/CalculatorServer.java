package calc;

import java.util.LinkedHashMap;
import java.util.Stack;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	 * @return    The resource in a HTML format.
	 * 
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Path("/push/{n}")
	public String push(@PathParam("id") int id, @PathParam("n") int n) {
		String resource = null;
		if (calculators.containsKey(id)) {
			calculators.get(id).getStack().push(n);
			resource = "<h1>Push " + n + ", size " + calculators.get(id).getStack().size() + "</h1>";
		} else {
			StackCalculator newCalculator = new StackCalculator();
			newCalculator.getStack().push(n);
			calculators.put(id, newCalculator);
			resource = "<h1>New number " + n + ", size " + calculators.get(id).getStack().size() + "</h1>";
		}
		return resource;
	}
	
	/**
	 * 
	 * Returns the top of the stack of the calculator with the given id.
	 * 
	 * @param  id The id of the calculator which is to be used.
	 * @return    The resource in a HTML format.
	 * 
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/peek")
	public String peek(@PathParam("id") int id) {
		String resource = null;
		if (calculators.containsKey(id) && (!calculators.get(id).getStack().isEmpty())) {
			resource = "<h1>Stack[top] " + calculators.get(id).getStack().peek() + "</h1>";
		} else {
			resource = "<h1>Returns an error</h1>";
		}
		return resource;
	}
	
	/**
	 * 
	 * Returns the top of the stack of the calculator with the given id, and removes it.
	 * 
	 * @param  id The id of the calculator which is to be used.
	 * @return    The resource in a HTML format.
	 * 
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Path("/pop")
	public String pop(@PathParam("id") int id) {
		String resource = null;
		if ((calculators.containsKey(id)) && (!calculators.get(id).getStack().isEmpty())) {
			int top = calculators.get(id).getStack().pop();			
			resource = "<h1>Removed stack[top] " + top + "</h1>";
		} else {
			resource = "<h1>Returns an error</h1>";
		}
		return resource;
	}
	
	/**
	 * 
	 * Removes the top and top-1 of the stack of the calculator with the given id, and replaces 
	 * them with their sum (i.e.<!-- --> the expression stack[top-1]+stack[top]).
	 * 
	 * @param  id The id of the calculator which is to be used.
	 * @return    The resource in a HTML format.
	 * 
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Path("/add")
	public String add(@PathParam("id") int id) {
		String resource = null;
		if ((calculators.containsKey(id)) && (calculators.get(id).getStack().size() >= 2)) {		
			Stack<Integer> currStack = calculators.get(id).getStack();		
			int op1 = currStack.pop();
			int op2 = currStack.pop();
			int sum = op1 + op2;		
			currStack.push(sum);
			resource = "<h1>Pushed " + sum + ", size " + calculators.get(id).getStack().size() + "</h1>";
		} else {
			resource = "<h1>Returns an error</h1>";
		}
		return resource;
	}
	
	/**
	 * 
	 * Removes the top and top-1 of the stack of the calculator with the given id, and replaces 
	 * them with their difference (i.e.<!-- --> the expression stack[top-1]-stack[top]).
	 * 
	 * @param  id The id of the calculator which is to be used.
	 * @return    The resource in a HTML format.
	 * 
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Path("/subtract")
	public String subtract(@PathParam("id") int id) {
		String resource = null;
		if ((calculators.containsKey(id)) && (calculators.get(id).getStack().size() >= 2)) {
			Stack<Integer> currStack = calculators.get(id).getStack();			
			int op1 = currStack.pop();
			int op2 = currStack.pop();
			int diff = op2 - op1;		
			currStack.push(diff);
			resource = "<h1>Pushed " + diff + ", size "+ calculators.get(id).getStack().size() + "</h1>";
		} else {
			resource = "<h1>Returns an error</h1>";
		}
		return resource;
	}
	
	/**
	 * 
	 * Removes the top and top-1 of the stack of the calculator with the given id, and replaces 
	 * them with their product (i.e.<!-- --> the expression stack[top-1]*stack[top]).
	 * 
	 * @param  id The id of the calculator which is to be used.
	 * @return    The resource in a HTML format.
	 * 
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Path("/multiply")
	public String multiply(@PathParam("id") int id) {
		String resource = null;
		if ((calculators.containsKey(id)) && (calculators.get(id).getStack().size() >= 2)) {			
			Stack<Integer> currStack = calculators.get(id).getStack();		
			int op1 = currStack.pop();
			int op2 = currStack.pop();
			int product = op1 * op2;		
			currStack.push(product);
			resource = "<h1>Pushed " + product +  ", size "+ calculators.get(id).getStack().size() + "</h1>";
		} else {
			resource = "<h1>Returns an error</h1>";
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
	 * @return    The resource in a HTML format.
	 * 
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Path("/divide")
	public String divide(@PathParam("id") int id) {
		String resource = null;
		if ((calculators.containsKey(id)) && (calculators.get(id).getStack().size() >= 2)) {			
			Stack<Integer> currStack = calculators.get(id).getStack();		
			int op1 = currStack.pop();
			int op2 = currStack.pop();
			int quotient = op2 / op1;		
			currStack.push(quotient);
			resource = "<h1>Pushed " + quotient + ", size "+ calculators.get(id).getStack().size() + "</h1>";
		} else {
			resource = "<h1>Returns an error</h1>";
		}
		return resource;
	}
}
