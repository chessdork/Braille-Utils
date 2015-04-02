package com.github.chessdork.braille;

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
	
	public BrailleToken nextToken() {
		if (!hasMoreTokens()) {
			return null;
		}
		setState(State.INITIAL);
		TokenType type = state.getTokenType();
		Queue<Character> queue = new LinkedList<>();
		
		// ensure we append at least one character
		char c = input.charAt(index++);
		String tokenContent = "" + c;
		setState(state.nextState(c));
		
		// because token type-determining states are disjoint, the correct token type
		// is simply the last visited state with a token type other than NONE.
		type = (state.getTokenType() != TokenType.NONE ? state.getTokenType() : type);
		
		while (state != State.TOKEN_COMPLETED && index < input.length()) {
			c = input.charAt(index++);
			setState(state.nextState(c));
			type = (state.getTokenType() != TokenType.NONE ? state.getTokenType() : type);
			// if we reach an auto-append state, all characters that were previously
			// indeterminate should be added to the current token.
			if (state.isAutoAppend()) {
				while (!queue.isEmpty()) {
					tokenContent += queue.poll();
				}
				tokenContent += c;
			} else {
				queue.add(c);
			}
		}
		// revisit any characters that were formerly indeterminate and are not
		// part of the token.
		index -= queue.size();
		return new BrailleToken(tokenContent, type);
	}
	
	protected void setState(State newState) {
		this.state = newState;
	}
	
	protected enum State {
		INITIAL(true, TokenType.SYMBOLIC) {
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
		NUMERIC(true, TokenType.NUMERIC) {
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
		NUMERIC_INDETERMINATE(false, TokenType.NONE) {
			@Override
			public State nextState(char c) {
				if (Character.isDigit(c)) {
					return NUMERIC;
				} else {
					return TOKEN_COMPLETED;
				}
			}
		},
		ALPHABETIC(true, TokenType.ALPHABETIC) {
			@Override
			public State nextState(char c) {
				if (Character.isLetter(c)) {
					return ALPHABETIC;
				} else {
					return TOKEN_COMPLETED;
				}
			}
		},
		TOKEN_COMPLETED(false, TokenType.NONE) {
			@Override
			public State nextState(char c) {
				return INITIAL;
			}
		};
		
		private final boolean autoAppend;
		private final TokenType type;
		
		State(boolean autoAppend, TokenType type) {
			this.autoAppend = autoAppend;
			this.type = type;
		}

		public boolean isAutoAppend() {
			return autoAppend;
		}
		
		public TokenType getTokenType() {
			return type;
		}
		
		public abstract State nextState(char c);
	}
}
