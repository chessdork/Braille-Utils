package com.github.chessdork.braille;

import java.util.HashMap;
import java.util.Map;

public final class BrailleUtils {
	
	private static Map<String, String> cellCache = new HashMap<>();
	
	static {
		cellCache.put("0", " ");
	}
	
	public static String stringFromDots(String... strings) {
		String brailleString = "";
		
		for (String dots : strings) {
			brailleString += cellFromDots(dots);
		}
		return brailleString;
	}
	
	public static String cellFromDots(String dots) {
		String cell = cellCache.get(dots);
		
		if (cell == null) {
			// The "0" input case should be handled by the cache.
			assert !dots.contains("0");
			int codePoint = 0x2800;
			
			for (int i = 0; i < dots.length(); i++) {
				int shift = Integer.parseInt(dots.substring(i, i+1));
				codePoint += 1 << (shift-1);
			}
			cell = Character.toString((char) codePoint);
			cellCache.put(dots,  cell);
		}
		return cell;
	}
}
