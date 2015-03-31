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
	public void testTrailingSpaces() {
		BrailleLexer lexer = new BrailleLexer("62    ");
		String token = lexer.nextToken();
		assertEquals(token, "62");
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
