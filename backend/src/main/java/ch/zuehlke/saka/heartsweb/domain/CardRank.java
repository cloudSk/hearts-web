package ch.zuehlke.saka.heartsweb.domain;

public enum CardRank {
	ACE(14),
	KING(13),
	QUEEN(12),
	JACK(11),
	NUMBER_10(10),
	NUMBER_09(9),
	NUMBER_08(8),
	NUMBER_07(7),
	NUMBER_06(6),
	NUMBER_05(5),
	NUMBER_04(4),
	NUMBER_03(3),
	NUMBER_02(2);

	private final int internalRank;

	CardRank(int internalRank) {
		this.internalRank = internalRank;
	}

	public int rank() {
		return internalRank;
	}
}
