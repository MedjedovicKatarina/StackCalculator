package model;

import java.util.Stack;

/**
 * 
 * This class handles the data of a single Calculator. It contains its stack which is used
 * for storing the operands.
 * 
 * @author Katarina Mededovic
 * 
 */
public class StackCalculator {
	
	private Stack<Integer> stack;
	
	/**
	 * 
	 * The constructor for the StackCalculator class. This constructor initializes the stack field
	 * without any arguments being passed to it.
	 * 
	 */
	public StackCalculator() {
		this.stack = new Stack<Integer>();
	}

	/**
	 * 
	 * Returns the stack of the calculator in question.
	 * 
	 * @return Stack of the calculator in question.
	 * 
	 */
	public Stack<Integer> getStack() {
		return stack;
	}
	
}
