package ch.zuehlke.saka.heartsweb.presentation.resources.round;

import ch.zuehlke.saka.heartsweb.domain.CardColor;
import ch.zuehlke.saka.heartsweb.domain.CardRank;

public class CardDto {
	private CardColor color;
	private CardRank rank;
	private CardDtoId id;

	public CardDto() {
	}

	public CardDto(CardColor color, CardRank rank) {
		this.color = color;
		this.rank = rank;
		this.id = CardDtoId.of(color, rank);
	}

	public CardColor getColor() {
		return color;
	}

	public CardRank getRank() {
		return rank;
	}

	public CardDtoId getId() {
		return id;
	}
}
