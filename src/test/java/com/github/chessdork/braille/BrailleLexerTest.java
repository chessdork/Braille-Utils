package com.github.chessdork.braille;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.chessdork.braille.BrailleLexer;
import com.github.chessdork.braille.BrailleToken;
import com.github.chessdork.braille.TokenType;

/**
 * Ignores UEB 6.4.1.  It is assumed that having numbers immediately follow
 * a hard stop is a grammatical error in the original text.
 * 
 * @author Michael Nguyen
 *
 */
public class BrailleLexerTest {

	@Test
	public void testUEB_6_2_1_1() {
		BrailleLexer lexer = new BrailleLexer("62");
		BrailleToken token = lexer.nextToken();
		assertEquals(token, new BrailleToken("62", TokenType.NUMERIC));
	}
	
	@Test
	public void testUEB_6_2_1_2() {
		BrailleLexer lexer = new BrailleLexer("1959");
		BrailleToken token = lexer.nextToken();
		assertEquals(token, new BrailleToken("1959", TokenType.NUMERIC));
	}
	
	@Test
	public void testUEB_6_2_1_3() {
		BrailleLexer lexer = new BrailleLexer("3,500");
		BrailleToken token = lexer.nextToken();
		assertEquals(token, new BrailleToken("3,500", TokenType.NUMERIC));
	}
	
	@Test
	public void testUEB_6_2_1_4() {
		BrailleLexer lexer = new BrailleLexer("8.93");
		BrailleToken token = lexer.nextToken();
		assertEquals(token, new BrailleToken("8.93", TokenType.NUMERIC));
	}
	
	@Test
	public void testUEB_6_2_1_5() {
		BrailleLexer lexer = new BrailleLexer(".7");
		BrailleToken token = lexer.nextToken();
		assertEquals(token, new BrailleToken(".7", TokenType.NUMERIC));
	}
	
	@Test
	public void testUEB_6_2_1_6() {
		BrailleLexer lexer = new BrailleLexer("0.7");
		BrailleToken token = lexer.nextToken();
		assertEquals(token, new BrailleToken("0.7", TokenType.NUMERIC));
	}
	
	@Test
	public void testUEB_6_2_1_7() {
		BrailleLexer lexer = new BrailleLexer("8,93");
		BrailleToken token = lexer.nextToken();
		assertEquals(token, new BrailleToken("8,93", TokenType.NUMERIC));
	}

	@Test
	public void testUEB_6_2_1_8() {
		BrailleLexer lexer = new BrailleLexer(",7");
		BrailleToken token = lexer.nextToken();
		assertEquals(token, new BrailleToken(",7", TokenType.NUMERIC));
	}
	
	@Test
	public void testUEB_6_2_1_9() {
		BrailleLexer lexer = new BrailleLexer("0,7");
		BrailleToken token = lexer.nextToken();
		assertEquals(token, new BrailleToken("0,7", TokenType.NUMERIC));
	}
	
	@Test
	public void testUEB_6_2_1_10() {
		BrailleLexer lexer = new BrailleLexer("par. 4.2.2");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("par", TokenType.ALPHABETIC),
			new BrailleToken(".", TokenType.SYMBOLIC),
			new BrailleToken (" ", TokenType.SYMBOLIC),
			new BrailleToken ("4.2.2", TokenType.NUMERIC)});
	}
	
	@Test
	public void testUEB_6_2_1_11() {
		BrailleLexer lexer = new BrailleLexer("4 500 000");
		BrailleToken token = lexer.nextToken();
		assertEquals(token, new BrailleToken("4 500 000", TokenType.NUMERIC));
	}

	@Test
	public void testUEB_6_2_1_14() {
		BrailleLexer lexer = new BrailleLexer("⅜");
		BrailleToken token = lexer.nextToken();
		assertEquals(token, new BrailleToken("⅜", TokenType.SYMBOLIC));
	}
	
	@Test
	public void testUEB_6_2_1_15() {
		BrailleLexer lexer = new BrailleLexer("5⅜");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("5", TokenType.NUMERIC),
			new BrailleToken("⅜", TokenType.SYMBOLIC)});
	}
	
	@Test
	public void testUEB_6_3_1_1() {
		BrailleLexer lexer = new BrailleLexer("7:30 a.m.");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("7", TokenType.NUMERIC),
			new BrailleToken(":", TokenType.SYMBOLIC),
			new BrailleToken("30", TokenType.NUMERIC), 
			new BrailleToken(" ", TokenType.SYMBOLIC),
			new BrailleToken("a", TokenType.ALPHABETIC),
			new BrailleToken(".", TokenType.SYMBOLIC), 
			new BrailleToken("m", TokenType.ALPHABETIC),
			new BrailleToken(".", TokenType.SYMBOLIC)});
	}

	@Test
	public void testUEB_6_3_1_2() {
		BrailleLexer lexer = new BrailleLexer("10:12:2009");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("10", TokenType.NUMERIC), 
			new BrailleToken(":", TokenType.SYMBOLIC),
			new BrailleToken("12", TokenType.NUMERIC),
			new BrailleToken(":", TokenType.SYMBOLIC),
			new BrailleToken("2009", TokenType.NUMERIC)});
	}
	
	@Test
	public void testUEB_6_3_1_5() {
		BrailleLexer lexer = new BrailleLexer("2.5-5");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("2.5", TokenType.NUMERIC), 
			new BrailleToken("-", TokenType.SYMBOLIC), 
			new BrailleToken("5", TokenType.NUMERIC)});
	}
	
	@Test
	public void testUEB_6_3_1_8() {
		BrailleLexer lexer = new BrailleLexer("7−5 = 2");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("7", TokenType.NUMERIC), 
			new BrailleToken("−", TokenType.SYMBOLIC),
			new BrailleToken("5", TokenType.NUMERIC),
			new BrailleToken(" ", TokenType.SYMBOLIC),
			new BrailleToken("=", TokenType.SYMBOLIC),
			new BrailleToken(" ", TokenType.SYMBOLIC),
			new BrailleToken("2", TokenType.NUMERIC)});
	}
	
	@Test
	public void testUEB_6_3_1_15() {
		BrailleLexer lexer = new BrailleLexer("7(2)");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("7", TokenType.NUMERIC), 
			new BrailleToken("(", TokenType.SYMBOLIC),
			new BrailleToken("2", TokenType.NUMERIC),
			new BrailleToken(")", TokenType.SYMBOLIC)});
	}
	
	@Test
	public void testUEB_6_5_2_3() {
		BrailleLexer lexer = new BrailleLexer("3B");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("3", TokenType.NUMERIC), 
			new BrailleToken("B", TokenType.ALPHABETIC)});
	}
	
	@Test
	public void testUEB_6_5_2_7() {
		BrailleLexer lexer = new BrailleLexer("4.B");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("4", TokenType.NUMERIC),
			new BrailleToken(".", TokenType.SYMBOLIC), 
			new BrailleToken("B", TokenType.ALPHABETIC)});
	}
	
	@Test
	public void testUEB_6_5_2_8() {
		BrailleLexer lexer = new BrailleLexer("report3.xls");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("report", TokenType.ALPHABETIC), 
			new BrailleToken("3", TokenType.NUMERIC), 
			new BrailleToken(".", TokenType.SYMBOLIC),
			new BrailleToken("xls", TokenType.ALPHABETIC)});
	}
	
	@Test
	public void testUEB_6_6_1_3() {
		BrailleLexer lexer = new BrailleLexer("phone: (61) 3 1234 5678");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("phone", TokenType.ALPHABETIC),
			new BrailleToken(":", TokenType.SYMBOLIC), 
			new BrailleToken(" ", TokenType.SYMBOLIC),
			new BrailleToken("(", TokenType.SYMBOLIC),
			new BrailleToken("61", TokenType.NUMERIC), 
			new BrailleToken(")", TokenType.SYMBOLIC),
			new BrailleToken(" ", TokenType.SYMBOLIC), 
			new BrailleToken("3 1234 5678", TokenType.NUMERIC)});
	}
	
	@Test
	public void testTrailingSpaces() {
		BrailleLexer lexer = new BrailleLexer("62    ");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("62", TokenType.NUMERIC),
			new BrailleToken(" ", TokenType.SYMBOLIC),
			new BrailleToken(" ", TokenType.SYMBOLIC),
			new BrailleToken(" ", TokenType.SYMBOLIC),
			new BrailleToken(" ", TokenType.SYMBOLIC)});
	}
	
	@Test
	public void testNumberListInput() {
		BrailleLexer lexer = new BrailleLexer("1, 2, and 3");
		assertArrayEquals(getAllTokens(lexer), new BrailleToken[] {
			new BrailleToken("1", TokenType.NUMERIC), 
			new BrailleToken(",", TokenType.SYMBOLIC),
			new BrailleToken(" ", TokenType.SYMBOLIC), 
			new BrailleToken("2", TokenType.NUMERIC),
			new BrailleToken(",", TokenType.SYMBOLIC), 
			new BrailleToken(" ", TokenType.SYMBOLIC),
			new BrailleToken("and", TokenType.ALPHABETIC),
			new BrailleToken(" ", TokenType.SYMBOLIC),
			new BrailleToken("3", TokenType.NUMERIC)});
	}
	
	@Test
	public void testEmptyStringInput() {
		BrailleLexer lexer = new BrailleLexer("");
		BrailleToken token = lexer.nextToken();
		assertNull(token);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullStringInput() {
		new BrailleLexer(null);
	}
	
	// convenience method for extracting all tokens from a BrailleLexer.
	private static BrailleToken[] getAllTokens(BrailleLexer lexer) {
		List<BrailleToken> tokens = new ArrayList<>();
		while (lexer.hasMoreTokens()) {
			tokens.add(lexer.nextToken());
		}
		return tokens.toArray(new BrailleToken[0]);
	}
}
