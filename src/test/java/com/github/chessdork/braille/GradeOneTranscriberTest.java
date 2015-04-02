package com.github.chessdork.braille;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GradeOneTranscriberTest {

	@Test
	public void testUEB_6_2_1_1() {
		String s = new GradeOneTranscriber().transcribe("62");
		assertEquals(s, BrailleUtils.stringFromDots("3456","124","12"));
	}
	
	@Test
	public void testUEB_6_2_1_2() {
		String s = new GradeOneTranscriber().transcribe("1959");
		assertEquals(s, BrailleUtils.stringFromDots("3456","1","24","15","24"));
	}
	
	@Test
	public void testUEB_6_2_1_3() {
		String s = new GradeOneTranscriber().transcribe("3,500");
		assertEquals(s, BrailleUtils.stringFromDots("3456","14","2","15","245","245"));
	}
	
	@Test
	public void testUEB_6_2_1_11() {
		String s = new GradeOneTranscriber().transcribe("4 500 000");
		assertEquals(s, BrailleUtils.stringFromDots("3456","145","5","15","245","245",
				"5","245","245","245"));		
	}
	
	@Test
	public void testUEB_6_3_1_1() {
		String s = new GradeOneTranscriber().transcribe("7:30 a.m.");
		assertEquals(s, BrailleUtils.stringFromDots("3456","1245","25","3456","14","245",
				"0","1","256","134","256"));
	}
}
