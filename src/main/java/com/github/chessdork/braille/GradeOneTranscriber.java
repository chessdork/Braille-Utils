package com.github.chessdork.braille;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of a "grade 1" braille transcriber, 
 * in accordance with the Rules of Unified English Braille.
 *  
 * @author Michael Nguyen
 *
 */
public class GradeOneTranscriber extends BrailleTranscriber {
	
	protected static final Map<Character, String> ALPHABETIC_MAP;
	
	static {
		Map<Character, String> alphabetics = new HashMap<>();
		alphabetics.put('a', BrailleUtils.cellFromDots("1"));
		alphabetics.put('b', BrailleUtils.cellFromDots("12"));
		alphabetics.put('c', BrailleUtils.cellFromDots("14"));
		alphabetics.put('d', BrailleUtils.cellFromDots("145"));
		alphabetics.put('e', BrailleUtils.cellFromDots("15"));
		alphabetics.put('f', BrailleUtils.cellFromDots("124"));
		alphabetics.put('g', BrailleUtils.cellFromDots("1245"));
		alphabetics.put('h', BrailleUtils.cellFromDots("125"));
		alphabetics.put('i', BrailleUtils.cellFromDots("24"));
		alphabetics.put('j', BrailleUtils.cellFromDots("245"));
		alphabetics.put('k', BrailleUtils.cellFromDots("15"));
		alphabetics.put('l', BrailleUtils.cellFromDots("123"));
		alphabetics.put('m', BrailleUtils.cellFromDots("134"));
		alphabetics.put('n', BrailleUtils.cellFromDots("1345"));
		alphabetics.put('o', BrailleUtils.cellFromDots("135"));
		alphabetics.put('p', BrailleUtils.cellFromDots("1234"));
		alphabetics.put('q', BrailleUtils.cellFromDots("12345"));
		alphabetics.put('r', BrailleUtils.cellFromDots("1235"));
		alphabetics.put('s', BrailleUtils.cellFromDots("234"));
		alphabetics.put('t', BrailleUtils.cellFromDots("2345"));
		alphabetics.put('u', BrailleUtils.cellFromDots("136"));
		alphabetics.put('v', BrailleUtils.cellFromDots("1236"));
		alphabetics.put('w', BrailleUtils.cellFromDots("2456"));
		alphabetics.put('x', BrailleUtils.cellFromDots("1346"));
		alphabetics.put('y', BrailleUtils.cellFromDots("13456"));
		alphabetics.put('z', BrailleUtils.cellFromDots("1356"));
		
		ALPHABETIC_MAP = Collections.unmodifiableMap(alphabetics);
	}
	
	@Override
	public String transcribeAlphabetic(String input) {
		String brailleString = "";
		
		for (int index = 0; index < input.length(); index++) {
			char c = input.charAt(index);
			if (Character.isUpperCase(c)) {
				brailleString += BrailleConstants.CAPITAL_INDICATOR;
				c = Character.toLowerCase(c);
			}
			// c is ensured to be lower case
			assert Character.isLowerCase(c);
			brailleString += ALPHABETIC_MAP.get(c);
		}
		return brailleString;
	}
	
	/**
	 * A BrailleTranscriber that prefers to use the "allcaps" indicator
	 * as often as possible.  The result of this 
	 *
	 */
	protected class allcapsTranscriber extends BrailleTranscriber {

		@Override
		public String transcribeAlphabetic(String x) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
