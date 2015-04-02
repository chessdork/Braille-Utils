package com.github.chessdork.braille;

public enum BrailleMode {
		
	NUMERIC {
		@Override
		public BrailleMode nextMode(String transcribedChar) {
			if (BrailleTranscriber.NUMERIC_MAP.containsValue(transcribedChar)) {
				return NUMERIC;
			} else {
				return NONE;
			}
		}
	}, GRADE_ONE {
		@Override
		public BrailleMode nextMode(String transcribedChar) {
			return NONE;
		}
	}, NONE {
		@Override
		public BrailleMode nextMode(String transcribedChar) {
			return NONE;
		}
	};
	
	public abstract BrailleMode nextMode(String transcribedChar);
}
