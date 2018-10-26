package calc;

import java.util.Stack;

public class StackCalculator {
	
	private Stack<Integer> stack;
	
	public StackCalculator() {
		this.stack = new Stack<Integer>();
	}

	public Stack<Integer> getStack() {
		return stack;
	}
	
	public void setStack(Stack<Integer> stack) {
		this.stack = stack;
	}
	
}
