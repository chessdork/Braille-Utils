package com.github.chessdork.braille;

public class BrailleToken {
	private final String content;
	private final TokenType type;
	
	public BrailleToken(String content, TokenType type) {
		this.content = content;
		this.type = type;
	}
	
	public String getContent() {
		return content;
	}
	
	public TokenType getTokenType() {
		return type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BrailleToken) {
			BrailleToken other = (BrailleToken) obj;
			return this.type == other.type && 
					this.content.equals(other.content);			
		} else {
			return false;
		}
	}
}
