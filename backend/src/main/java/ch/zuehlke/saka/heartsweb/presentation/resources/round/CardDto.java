package ch.zuehlke.saka.heartsweb.presentation.resources.round;

import ch.zuehlke.saka.heartsweb.domain.CardColor;
import ch.zuehlke.saka.heartsweb.domain.CardRank;

public class CardDto {
	private CardColor color;
	private CardRank rank;
	private CardDtoId id;

	public CardDto() {
	}

	public CardDto(CardColor color, CardRank rank, CardDtoId id) {
		this.color = color;
		this.rank = rank;
		this.id = id;
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

	public CardDtoId getId() {
		return id;
	}

	public void setId(CardDtoId id) {
		this.id = id;
	}
}
