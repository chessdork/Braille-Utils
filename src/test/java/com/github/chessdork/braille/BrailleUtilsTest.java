package com.github.chessdork.braille;

import static org.junit.Assert.*;

import org.junit.Test;

public class BrailleUtilsTest {

	@Test
	public void testLetterA() {
		String a = BrailleUtils.cellFromDots("1");
		assertEquals(a, "⠁");
	}
	
	@Test
	public void testNumericIndicator() {
		String s = BrailleUtils.cellFromDots("3456");
		assertEquals(s, "⠼");
	}
	
	@Test
	public void testSpace() {
		String s = BrailleUtils.cellFromDots("0");
		assertEquals(s, " ");
	}
	
	@Test
	public void testMultiCharString() {
		String s = BrailleUtils.stringFromDots("1", "12", "14");
		assertEquals(s, "⠁⠃⠉");
	}
}
