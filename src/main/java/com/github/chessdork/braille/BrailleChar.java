package com.github.chessdork.braille;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A class representing a single braille character.  Per the UEB,
 * a braille character is any one of the 64 distinct patterns of
 * six dots that can be expressed in braille.
 * 
 * @author Michael Nguyen
 *
 */
public class BrailleChar {
	
	public static final int UNICODE_BLOCK_START = 0x2800;
	public static final int UNICODE_BLOCK_END = 0x28ff;
	
	private static final Map<Integer, BrailleChar> ALL_CHARS;
	private static final Set<BrailleChar> PREFIXES;
	
	// bitmasks representing braille dots.
	public static final int DOT_ONE = 0x01;
	public static final int DOT_TWO = 0x02;
	public static final int DOT_THREE = 0x04;
	public static final int DOT_FOUR = 0x08;
	public static final int DOT_FIVE = 0x10;
	public static final int DOT_SIX = 0x20;
	
	private static Map<String, BrailleChar> dotStringCache = new HashMap<>();
	
	static {
		Map<Integer, BrailleChar> allChars = new HashMap<>();		
		// 64 distinct arrangements of 6 braille dots.
		for (int value = 0; value < 64; value++) {
			allChars.put(value, new BrailleChar(value));
		}
		ALL_CHARS = Collections.unmodifiableMap(allChars);

		// cache the empty braille character.
		dotStringCache.put("0", ALL_CHARS.get(0));
		
		// Per UEB 2.1, the prefixes consist of the numeric indicator and 
		// braille characters having only right-sided dots.
		Set<BrailleChar> prefixes = new HashSet<>();
		prefixes.addAll(Arrays.asList(
				BrailleChar.fromDots("4"), BrailleChar.fromDots("45"),
				BrailleChar.fromDots("456"), BrailleChar.fromDots("5"),
				BrailleChar.fromDots("46"), BrailleChar.fromDots("56"),
				BrailleChar.fromDots("6"), BrailleChar.fromDots("3456")));
		PREFIXES = Collections.unmodifiableSet(prefixes);
	}
	
	private final int value;
	
	private BrailleChar(int value) {
		this.value = value;
	}
	
	/**
	 * Returns true if this BrailleChar is the numeric indicator or consists
	 * of only right-hand dots.
	 * @return true if this BrailleChar is a prefix
	 */
	public boolean isPrefix() {
		return PREFIXES.contains(this);
	}
	
	/**
	 * Returns true if this BrailleChar is any of the 56 characters that is not 
	 * a prefix.
	 * @return true if this BrailleChar is a root
	 */
	public boolean isRoot() {
		return !isPrefix();
	}
	
	/**
	 * Returns true if this BrailleChar has the dot(s) specified by the dot mask.
	 * @param dotMask
	 * @return true if this BrailleChar has the specified dots
	 */
	public boolean hasDots(int dotMask) {
		return (value & dotMask) != 0;
	}
	
	/**
	 * Returns true if this BrailleChar does not contain the dots 1 or 4.
	 * @return true if this BrailleChar does not contain the dots 1 or 4
	 */
	public boolean isLower() {
		return !hasDots(DOT_ONE | DOT_FOUR);
	}
	
	/**
	 * Returns true if this BrailleChar contains the dots 1 or 4.
	 * @return true if this BrailleChar contains the dots 1 or 4.
	 */
	public boolean isUpper() {
		return !isLower();
	}
	
	/**
	 * A convenience method for fetching the BrailleChar having the specified
	 * dots.  The input should comprise any of the digits 1-6, indicating the
	 * dots that should be raised.  For example, {@code fromDots("135")} returns
	 * the BrailleChar displayed as "⠕".
	 * @param dots a string indicating which dots should be raised
	 * @return the BrailleChar having the specified raised dots
	 */
	public static BrailleChar fromDots(String dots) {
		BrailleChar bChar = dotStringCache.get(dots);
		
		if (bChar == null) {
			// The "0" input case should be handled by the cache.
			assert !dots.equals("0");
			int value = 0;
						
			for (int i = 0; i < dots.length(); i++) {
				int shift = Integer.parseInt(dots.substring(i, i+1));
				value += 1 << (shift-1);
			}
			assert value > 0 && value < 64;
			bChar = ALL_CHARS.get(value);
			dotStringCache.put(dots,  bChar);
		}
		return bChar;
	}
	
	/**
	 * Returns the BrailleChar associated with the specified Unicode point.
	 * @param codePoint between 0x2800 and 0x28ff
	 * @return the BrailelChar associated with {@code codePoint}
	 */
	public static BrailleChar fromUnicodeValue(int codePoint) {
		assert codePoint >= UNICODE_BLOCK_START && codePoint <= UNICODE_BLOCK_END;
		return ALL_CHARS.get(codePoint - UNICODE_BLOCK_START);
	}
	
	/**
	 * Returns a unicode representation of this BrailleChar.
	 */
	@Override
	public String toString() {
		return Character.toString((char) (UNICODE_BLOCK_START + value));
	}
}