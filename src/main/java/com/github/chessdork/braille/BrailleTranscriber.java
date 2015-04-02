package com.github.chessdork.braille;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class BrailleTranscriber {
	
	protected static final Map<Character, String> NUMERIC_MAP;
	protected static final Map<Character, String> SYMBOLIC_MAP;
	
	protected BrailleMode mode;
	
	static {
		Map<Character, String> numerics = new HashMap<>();
		// digits
		numerics.put('1', BrailleUtils.cellFromDots("1"));
		numerics.put('2', BrailleUtils.cellFromDots("12"));
		numerics.put('3', BrailleUtils.cellFromDots("14"));
		numerics.put('4', BrailleUtils.cellFromDots("145"));
		numerics.put('5', BrailleUtils.cellFromDots("15"));
		numerics.put('6', BrailleUtils.cellFromDots("124"));
		numerics.put('7', BrailleUtils.cellFromDots("1245"));
		numerics.put('8', BrailleUtils.cellFromDots("125"));
		numerics.put('9', BrailleUtils.cellFromDots("24"));
		numerics.put('0', BrailleUtils.cellFromDots("245"));
		
		// numeric symbols
		numerics.put(' ', BrailleUtils.cellFromDots("5"));
		numerics.put(',', BrailleUtils.cellFromDots("2"));
		numerics.put('.', BrailleUtils.cellFromDots("256"));
		
		NUMERIC_MAP = Collections.unmodifiableMap(numerics);
		
		Map<Character, String> symbolics = new HashMap<>();
		// TODO add the complete list of symbols
		symbolics.put(' ', BrailleUtils.cellFromDots("0"));
		symbolics.put('&', BrailleUtils.stringFromDots("4","12346"));
		symbolics.put('*', BrailleUtils.stringFromDots("5","35"));
		symbolics.put('•', BrailleUtils.stringFromDots("456","256"));
		symbolics.put('.', BrailleUtils.cellFromDots("256"));
		symbolics.put(':', BrailleUtils.cellFromDots("25"));
		
		
		SYMBOLIC_MAP = Collections.unmodifiableMap(symbolics);
	}
	
	public BrailleTranscriber() {
		this.mode = BrailleMode.NONE;
	}
	
	protected void setMode(BrailleMode newMode) {
		this.mode = newMode;
	}
	
	public abstract String transcribeAlphabetic(String input);
	
	public String transcribeSymbolic(String input) {
		String brailleString = "";
		
		for (int i = 0; i < input.length(); i++) {
			brailleString += SYMBOLIC_MAP.get(input.charAt(i));
		}
		return brailleString;
	}
	
	public String transcribeNumeric(String input) {
		String brailleString = BrailleConstants.NUMERIC_INDICATOR;
		
		for (int i = 0; i < input.length(); i++) {
			brailleString += NUMERIC_MAP.get(input.charAt(i));
		}
		return brailleString;
	}
	
	public final String transcribe(String input) {
		String brailleString = "";
		BrailleLexer lexer = new BrailleLexer(input);
		
		while (lexer.hasMoreTokens()) {
			BrailleToken token = lexer.nextToken();
			String content = token.getContent();
			
			switch(token.getTokenType()) {
			case ALPHABETIC:
				brailleString += transcribeAlphabetic(content);
				break;
			case NUMERIC:
				brailleString += transcribeNumeric(content);
				break;
			case SYMBOLIC: 
				brailleString += transcribeSymbolic(content);
				break;
			default:
				throw new IllegalStateException("Attempting to transcribe" +
						"token with TokenType NONE");
			}
		}
		return brailleString;
	}
}
