package ch.zuehlke.saka.heartsweb.presentation.resources.round;

import ch.zuehlke.saka.heartsweb.domain.CardColor;
import ch.zuehlke.saka.heartsweb.domain.CardRank;

public class CardDto {
	private CardColor color;
	private CardRank rank;

	public CardDto() {
	}

	public CardDto(CardColor color, CardRank rank) {
		this.color = color;
		this.rank = rank;
	}

	public CardColor getColor() {
		return color;
	}

	public void setColor(CardColor color) {
		this.color = color;
	}

	public CardRank getRank() {
		return rank;
	}

	public void setRank(CardRank rank) {
		this.rank = rank;
	}
}
