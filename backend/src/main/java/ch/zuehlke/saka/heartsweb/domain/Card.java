package ch.zuehlke.saka.heartsweb.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Card {
	private final CardColor cardColor;
	private final CardRank cardRank;

	public Card(CardColor cardColor, CardRank cardRank) {
		this.cardColor = cardColor;
		this.cardRank = cardRank;
	}

	public CardColor cardColor() {
		return cardColor;
	}

	public CardRank cardRank() {
		return cardRank;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("color", cardColor)
				.append("rank", cardRank)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (getClass() != o.getClass()) {
			return false;
		}

		Card card = (Card) o;
		return new EqualsBuilder()
				.append(cardColor, card.cardColor)
				.append(cardRank, card.cardRank)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(cardColor)
				.append(cardRank)
				.toHashCode();
	}
}
