package com.github.chessdork.braille.lang;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.chessdork.braille.lang.BrailleLexer;

public class BrailleLexerTest {

	@Test
	public void testUEB_6_2_1_1() {
		BrailleLexer lexer = new BrailleLexer("62");
		String token = lexer.nextToken();
		assertEquals(token, "62");
	}
	
	@Test
	public void testUEB_6_2_1_2() {
		BrailleLexer lexer = new BrailleLexer("1959");
		String token = lexer.nextToken();
		assertEquals(token, "1959");
	}
	
	@Test
	public void testUEB_6_2_1_3() {
		BrailleLexer lexer = new BrailleLexer("3,500");
		String token = lexer.nextToken();
		assertEquals(token, "3,500");
	}
	
	@Test
	public void testUEB_6_2_1_4() {
		BrailleLexer lexer = new BrailleLexer("8.93");
		String token = lexer.nextToken();
		assertEquals(token, "8.93");
	}
	
	@Test
	public void testUEB_6_2_1_5() {
		BrailleLexer lexer = new BrailleLexer(".7");
		String token = lexer.nextToken();
		assertEquals(token, ".7");
	}
	
	@Test
	public void testUEB_6_2_1_6() {
		BrailleLexer lexer = new BrailleLexer("0.7");
		String token = lexer.nextToken();
		assertEquals(token, "0.7");
	}
	
	@Test
	public void testUEB_6_2_1_7() {
		BrailleLexer lexer = new BrailleLexer("8,93");
		String token = lexer.nextToken();
		assertEquals(token, "8,93");
	}

	@Test
	public void testUEB_6_2_1_8() {
		BrailleLexer lexer = new BrailleLexer(",7");
		String token = lexer.nextToken();
		assertEquals(token, ",7");
	}
	
	@Test
	public void testUEB_6_2_1_9() {
		BrailleLexer lexer = new BrailleLexer("0,7");
		String token = lexer.nextToken();
		assertEquals(token, "0,7");
	}
	
	@Test
	public void testUEB_6_2_1_10() {
		BrailleLexer lexer = new BrailleLexer("par. 4.2.2");
		assertArrayEquals(getAllTokens(lexer), new String[] {"par", ".", " ", "4.2.2"});
	}
	
	@Test
	public void testUEB_6_2_1_11() {
		BrailleLexer lexer = new BrailleLexer("4 500 000");
		String token = lexer.nextToken();
		assertEquals(token, "4 500 000");
	}

	@Test
	public void testUEB_6_2_1_14() {
		BrailleLexer lexer = new BrailleLexer("⅜");
		String token = lexer.nextToken();
		assertEquals(token, "⅜");
	}
	
	@Test
	public void testUEB_6_2_1_15() {
		BrailleLexer lexer = new BrailleLexer("5⅜");
		assertArrayEquals(getAllTokens(lexer), new String[] {"5", "⅜"});
	}
	
	@Test
	public void testTrailingSpaces() {
		BrailleLexer lexer = new BrailleLexer("62    ");
		assertArrayEquals(getAllTokens(lexer), new String[] {"62", " ", " ", " ", " "});
	}
	
	@Test
	public void testMultiTokenInput() {
		BrailleLexer lexer = new BrailleLexer("62 apples");
		assertArrayEquals(getAllTokens(lexer), new String[] {"62", " ", "apples"});
	}
	
	@Test
	public void testNumberListInput() {
		BrailleLexer lexer = new BrailleLexer("1, 2, and 3");
		assertArrayEquals(getAllTokens(lexer), new String[] {"1", ",", " ", "2", ",", " ", "and", " ", "3"});
	}
	@Test
	public void testEmptyStringInput() {
		BrailleLexer lexer = new BrailleLexer("");
		String token = lexer.nextToken();
		assertNull(token);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullStringInput() {
		new BrailleLexer(null);
	}
	
	// convenience method for extracting all tokens from a BrailleLexer.
	private static String[] getAllTokens(BrailleLexer lexer) {
		List<String> tokens = new ArrayList<>();
		while (lexer.hasMoreTokens()) {
			tokens.add(lexer.nextToken());
		}
		return tokens.toArray(new String[0]);
	}
}
