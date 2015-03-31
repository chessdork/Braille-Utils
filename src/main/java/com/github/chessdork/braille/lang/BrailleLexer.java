package com.github.chessdork.braille.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BrailleLexer {
	private String input;
	private int index;
	private State state;
	private Queue<Character> queue;
	
	public BrailleLexer(String input) {
		if (input == null) {
			throw new IllegalArgumentException("BrailleLexer input may not be null");
		}
		this.input = input;
		this.index = 0;
		this.queue = new LinkedList<>();
	}
	
	public String nextToken() {
		if (index >= input.length() && queue.isEmpty()) {
			return null;
		}
		String token = "";
		char c;
		
		setState(State.INITIAL);
		
		while (!queue.isEmpty() && state != State.TOKEN_COMPLETED) {
			c = queue.poll();
			setState(state.nextState(c));
			token += c;
		}
		
		while (state != State.TOKEN_COMPLETED && index < input.length()) {
			c = input.charAt(index++);
			setState(state.nextState(c));
			if (state.isAutoAppend()) {
				while (!queue.isEmpty()) {
					token += queue.poll();
				}
				token += c;
			} else {
				queue.add(c);
			}
		}
		System.out.println(token);
		return token;
	}
	
	public void setState(State newState) {
		this.state = newState;
	}
	
	protected enum State {
		INITIAL(true) {
			@Override
			public State nextState(char c) {
				if (Character.isDigit(c)) {
					return NUMERIC;
				} else if (c == '.' || c == ',') {
					return NUMERIC_INDETERMINATE;
				} else if (c == ' ') {
					return TOKEN_COMPLETED;
				} else {
					throw new IllegalStateException();
				}
			}
		},
		NUMERIC(true) {
			@Override
			public State nextState(char c) {
				if (Character.isDigit(c) || c == ',' || c == '.') {
					return NUMERIC;
				} else if (c == ' ') {
					return NUMERIC_INDETERMINATE;
				} else {
					return TOKEN_COMPLETED;
				}
			}
		},
		NUMERIC_INDETERMINATE(false) {
			@Override
			public State nextState(char c) {
				if (Character.isDigit(c)) {
					return NUMERIC;
				} else {
					return TOKEN_COMPLETED;
				}
			}
		},
		TOKEN_COMPLETED(false) {
			@Override
			public State nextState(char c) {
				return INITIAL;
			}
		};
		
		private final boolean autoAppend;
		
		State(boolean autoAppend) {
			this.autoAppend = autoAppend;
		}

		public boolean isAutoAppend() {
			return autoAppend;
		}
		
		public abstract State nextState(char c);
	}
}
