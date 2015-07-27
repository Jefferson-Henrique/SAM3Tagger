package me.jhenrique.sam3;

public enum PatternsEnum {

	TRACK_NUMBER(1, "\\\\d+"), ARTIST_BAND(2, ".*?"), MUSIC_NAME(3, ".*?");
	
	private int refNumber;
	
	private String regularExp;
	
	private PatternsEnum(int refNumber, String regularExp) {
		this.refNumber = refNumber;
		this.regularExp = regularExp;
	}

	public int getRefNumber() {
		return refNumber;
	}

	public String getRegularExp() {
		return "("+regularExp+")";
	}

	public static PatternsEnum getPattern(int ref) {
		for (PatternsEnum pe : PatternsEnum.values()) {
			if (pe.refNumber == ref) {
				return pe;
			}
		}
		
		return null;
	}
	
}