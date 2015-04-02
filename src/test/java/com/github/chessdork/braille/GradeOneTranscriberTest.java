package com.github.chessdork.braille;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GradeOneTranscriberTest {
	
	@Test
	public void testUEB_5_2_1_5() {
		String s = new GradeOneTranscriber().transcribe("22B");
		assertEquals(BrailleUtils.stringFromDots("3456","12","12", "6", "12"), s);		
	}
	
	@Test
	public void testUEB_6_2_1_1() {
		String s = new GradeOneTranscriber().transcribe("62");
		assertEquals(BrailleUtils.stringFromDots("3456","124","12"), s);
	}
	
	@Test
	public void testUEB_6_2_1_2() {
		String s = new GradeOneTranscriber().transcribe("1959");
		assertEquals(BrailleUtils.stringFromDots("3456","1","24","15","24"), s);
	}
	
	@Test
	public void testUEB_6_2_1_3() {
		String s = new GradeOneTranscriber().transcribe("3,500");
		assertEquals(BrailleUtils.stringFromDots("3456","14","2","15","245","245"), s);
	}
	
	@Test
	public void testUEB_6_2_1_11() {
		String s = new GradeOneTranscriber().transcribe("4 500 000");
		assertEquals(BrailleUtils.stringFromDots("3456","145","5","15","245","245",
				"5","245","245","245"), s);		
	}
	
	@Test
	public void testUEB_6_3_1_1() {
		String s = new GradeOneTranscriber().transcribe("7:30 a.m.");
		assertEquals(BrailleUtils.stringFromDots("3456","1245","25","3456","14","245",
				"0","1","256","134","256"), s);
	}
	
	@Test
	public void testUEB_6_3_1_6() {
		String s = new GradeOneTranscriber().transcribe("8-cab fleet");
		assertEquals(BrailleUtils.stringFromDots("3456","125","36","14","1","12","0",
				"124","123","15","15","2345"), s);
	}
	
	@Test
	public void testUEB_6_3_1_17() {
		String s = new GradeOneTranscriber().transcribe("4—7");
		assertEquals(BrailleUtils.stringFromDots("3456","145","6","36","3456","1245"), s);
	}
	
	@Test
	public void testUEB_6_3_1_18() {
		String s = new GradeOneTranscriber().transcribe("4..7");
		assertEquals(BrailleUtils.stringFromDots("3456","145","256","256","1245"), s);
	}
	
	@Test
	public void testUEB_6_5_2_2() {
		String s = new GradeOneTranscriber().transcribe("3b");
		assertEquals(BrailleUtils.stringFromDots("3456","14","56","12"), s);		
	}
	
	@Test
	public void testUEB_6_5_2_3() {
		String s = new GradeOneTranscriber().transcribe("3B");
		assertEquals(BrailleUtils.stringFromDots("3456","14","6","12"), s);
	}
	
	@Test
	public void testUEB_6_5_2_9() {
		String s = new GradeOneTranscriber().transcribe("report3.doc");
		assertEquals(BrailleUtils.stringFromDots("1235","15","1234","135", "1235",
				"2345","3456","14","256","56","145","135","14"), s);		
	}
	
	@Test
	public void tesUEB_6_5_2_10() {
		String s = new GradeOneTranscriber().transcribe("report3.xls");
		assertEquals(BrailleUtils.stringFromDots("1235","15","1234","135", "1235",
				"2345","3456","14","256","1346","123","234"), s);			
	}
}
