package com.github.chessdork.braille.lang;

import java.util.LinkedList;
import java.util.Queue;

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
	
	public boolean hasMoreTokens() {
		return index < input.length();
	}
	
	public String nextToken() {
		if (!hasMoreTokens()) {
			return null;
		}
		setState(State.INITIAL);
		
		// ensure we append at least one character
		char c = input.charAt(index++);
		String token = "" + c;
		setState(state.nextState(c));
		Queue<Character> queue = new LinkedList<>();
		
		while (state != State.TOKEN_COMPLETED && index < input.length()) {
			c = input.charAt(index++);
			setState(state.nextState(c));
			// if we reach an auto-append state, all characters that were previously
			// indeterminate should be added to the current token.
			if (state.isAutoAppend()) {
				while (!queue.isEmpty()) {
					token += queue.poll();
				}
				token += c;
			} else {
				queue.add(c);
			}
		}
		// revisit any characters that were formerly indeterminate and are not
		// part of the token.
		index -= queue.size();
		return token;
	}
	
	protected void setState(State newState) {
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
				} else if (Character.isLetter(c)) {
					return ALPHABETIC;
				} else  {
					return TOKEN_COMPLETED;
				}
			}
		},
		NUMERIC(true) {
			@Override
			public State nextState(char c) {
				if (Character.isDigit(c)) {
					return NUMERIC;
				} else if (c == ' ' || c == ',' || c == '.') {
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
		ALPHABETIC(true) {
			@Override
			public State nextState(char c) {
				if (Character.isLetter(c)) {
					return ALPHABETIC;
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
