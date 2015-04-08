package com.github.chessdork.braille;

import static org.junit.Assert.*;

import org.junit.Test;

public class BrailleCharTest {

	@Test
	public void testLetterA() {
		String a = BrailleChar.fromDots("1").toString();
		assertEquals("⠁", a);
	}
	
	@Test
	public void testNumericIndicator() {
		String s = BrailleChar.fromDots("3456").toString();
		assertEquals("⠼", s);
	}
	
	@Test
	public void testSpace() {
		String s = BrailleChar.fromDots("0").toString();
		assertEquals("\u2800", s);
	}
	
	@Test
	public void testPrefix() {
		boolean b = BrailleChar.fromDots("46").isPrefix();
		assertTrue(b);
	}
	
	@Test
	public void testRoot() {
		boolean b = BrailleChar.fromDots("13").isRoot();
		assertTrue(b);
	}
	
	@Test
	public void testLower() {
		boolean b = BrailleChar.fromDots("2356").isLower();
		assertTrue(b);
	}
	
	@Test
	public void testUpper() {
		boolean b = BrailleChar.fromDots("16").isUpper();
		assertTrue(b);
	}
	
	@Test
	public void testHasDots() {
		boolean b = BrailleChar.fromDots("34").hasDots(BrailleChar.DOT_FIVE);
		assertFalse(b);
	}
	
	@Test
	public void testMultipleDotMask() {
		BrailleChar c = BrailleChar.fromDots("45");
		boolean b = c.hasDots(BrailleChar.DOT_FOUR) && c.hasDots(BrailleChar.DOT_FIVE);
		assertTrue(b);
	}
	
	@Test
	public void testStrong() {
		boolean b = BrailleChar.fromDots("16").isStrong();
		assertTrue(b);
	}
}
