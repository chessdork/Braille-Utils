package com.github.chessdork.braille.lang;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.chessdork.braille.lang.BrailleLexer;

public class BrailleLexerTest {

	@Test
	public void testUEB1() {
		BrailleLexer lexer = new BrailleLexer("62");
		String token = lexer.nextToken();
		assertEquals(token, "62");
	}
	
	@Test
	public void testUEB2() {
		BrailleLexer lexer = new BrailleLexer("1959");
		String token = lexer.nextToken();
		assertEquals(token, "1959");
	}
	
	@Test
	public void testUEB3() {
		BrailleLexer lexer = new BrailleLexer("3,500");
		String token = lexer.nextToken();
		assertEquals(token, "3,500");
	}
	
	@Test
	public void testUEB4() {
		BrailleLexer lexer = new BrailleLexer("8.93");
		String token = lexer.nextToken();
		assertEquals(token, "8.93");
	}
	
	@Test
	public void testUEB5() {
		BrailleLexer lexer = new BrailleLexer(".7");
		String token = lexer.nextToken();
		assertEquals(token, ".7");
	}
	
	@Test
	public void testUEB6() {
		BrailleLexer lexer = new BrailleLexer("0.7");
		String token = lexer.nextToken();
		assertEquals(token, "0.7");
	}
	
	@Test
	public void testUEB7() {
		BrailleLexer lexer = new BrailleLexer("8,93");
		String token = lexer.nextToken();
		assertEquals(token, "8,93");
	}

	@Test
	public void testUEB8() {
		BrailleLexer lexer = new BrailleLexer(",7");
		String token = lexer.nextToken();
		assertEquals(token, ",7");
	}
	
	@Test
	public void testUEB9() {
		BrailleLexer lexer = new BrailleLexer("0,7");
		String token = lexer.nextToken();
		assertEquals(token, "0,7");
	}
	
	@Test
	public void testUEB10() {
		BrailleLexer lexer = new BrailleLexer("4.2.2");
		String token = lexer.nextToken();
		assertEquals(token, "4.2.2");
	}
	
	@Test
	public void testUEB11() {
		BrailleLexer lexer = new BrailleLexer("4 500 000");
		String token = lexer.nextToken();
		assertEquals(token, "4 500 000");
	}
	
	@Test
	public void testTrailingSpaces() {
		BrailleLexer lexer = new BrailleLexer("62    ");
		String token = lexer.nextToken();
		assertEquals(token, "62");
	}
	
	@Test
	public void testMultiTokenInput() {
		BrailleLexer lexer = new BrailleLexer("62 a");
		String token = lexer.nextToken();
		String token2 = lexer.nextToken();
		System.out.println("TOKEN 2: " + token2);
		assertArrayEquals(new String[] {token, token2}, new String [] {"62", " "});
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

}
