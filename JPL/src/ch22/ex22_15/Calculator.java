/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_15;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * @author Ken Miura
 *
 */
public final class Calculator {

	public static double calculate (String expression) throws IOException, ParseException {
		Objects.requireNonNull(expression, "expression must not be null");
		String RPN = toRPN(expression);
		StringReader reader = new StringReader(RPN);
		StreamTokenizer in = new StreamTokenizer(reader);
		in.ordinaryChar('/');
		Deque<Double> stack = new ArrayDeque<>();
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			if (in.ttype == StreamTokenizer.TT_NUMBER) {
				stack.addLast(in.nval);
			} else {
				double rightOperand = stack.pollLast();
				double leftOperand = stack.pollLast();
				switch (in.ttype) {
					case '+':
						stack.addLast(leftOperand + rightOperand);
					break;
					case '-':
						stack.addLast(leftOperand - rightOperand);
					break;
					case '/':
						stack.addLast(leftOperand / rightOperand);
					break;
					case '*':
						stack.addLast(leftOperand * rightOperand);						
					break;
					case '%':
						stack.addLast(leftOperand % rightOperand);
					break;
					default:
						throw new ParseException(expression, 0);
				}
			}
		}
		double result = stack.pollLast();
		if (stack.size() != 0) {
			throw new AssertionError();
		}
		return result;
	}

	private static String toRPN(String expression) throws IOException {
		StreamTokenizer st = new StreamTokenizer(new StringReader(expression));
		st.ordinaryChar('/');
		st.ordinaryChar('-');
		StringBuilder sb = new StringBuilder();
		Deque<String> opecodes = new ArrayDeque<>();
		while (st.nextToken() != StreamTokenizer.TT_EOF) {
			if (st.ttype == StreamTokenizer.TT_NUMBER) {
				sb.append(st.nval + " ");
			} else if (st.ttype == '+' || st.ttype == '-') {
				if (opecodes.size() > 0 && !(opecodes.peekLast().equals("+") || opecodes.peekLast().equals("-"))) {
					String opecode = null;
					while ((opecode=opecodes.pollLast()) != null) {
						sb.append(opecode + " ");		
					}
				}
				opecodes.addLast(String.valueOf((char)st.ttype));
			} else if (st.ttype == '*' || st.ttype == '/' || st.ttype == '%') {
				if (opecodes.size() > 0 && !(opecodes.peekLast().equals("+") || opecodes.peekLast().equals("-"))) {
					sb.append(opecodes.removeLast() + " ");	
				}
				opecodes.addLast(String.valueOf((char)st.ttype));
			} else {
				throw new AssertionError();
			}
		}
		String opecode = null;
		while ((opecode=opecodes.pollLast()) != null) {
			sb.append(opecode + " ");
		}
		return sb.toString();
	}
}
