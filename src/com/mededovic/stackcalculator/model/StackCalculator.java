package com.mededovic.stackcalculator.model;

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
	 * The constructor for the StackCalculator class. Used for setting the stack to the passed argument.
	 * 
	 * @param stack The Stack which is to be added to the StackCalculator.
	 * 
	 */
	public StackCalculator(Stack<Integer> stack) {
		this.stack = stack;
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
