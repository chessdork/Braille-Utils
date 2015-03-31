package com.github.chessdork.braille.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BrailleLexer {
	private String input;
	private int index;
	private State state;
	
	public BrailleLexer(String input) {
		if (input == null) {
			throw new IllegalArgumentException("BrailleLexer input may not be null");
		}
		this.input = input;
		this.index = 0;
	}
	
	public String nextToken() {
		if (index >= input.length()) {
			return null;
		}
		String token = "";
		char c;
		
		setState(State.INITIAL);
		
		while (state != State.TOKEN_COMPLETED && index < input.length()) {
			c = input.charAt(index++);
			token += c;
			setState(state.nextState(c));
		}
		return token;
	}
	
	public void setState(State newState) {
		this.state = newState;
	}
	
	protected enum State {
		INITIAL {
			@Override
			public State nextState(char c) {
				if (Character.isDigit(c)) {
					return NUMERIC;
				} else {
					throw new IllegalStateException();
				}
			}
		},
		NUMERIC {
			@Override
			public State nextState(char c) {
				if (Character.isDigit(c) || c == ',' || c == '.') {
					return NUMERIC;
				} 
				else {
					return TOKEN_COMPLETED;
				}
			}
		},
		TOKEN_COMPLETED {
			@Override
			public State nextState(char c) {
				return INITIAL;
			}
		};
		
		public abstract State nextState(char c);
		//public abstract String process(String input, int index);
	}

}
