package ch.zuehlke.saka.heartsweb.presentation.resources.round;

import ch.zuehlke.saka.heartsweb.domain.CardColor;
import ch.zuehlke.saka.heartsweb.domain.CardRank;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonSerialize(using = ToStringSerializer.class)
public class CardDtoId {
	private static final Pattern ID_PATTERN = Pattern.compile("^([A-Z]+)-([A-Z_0-9]+)$");

	private final CardColor color;
	private final CardRank rank;

	private CardDtoId(CardColor color, CardRank rank) {
		this.color = color;
		this.rank = rank;
	}

	static CardDtoId of(CardColor color, CardRank rank) {
		return new CardDtoId(color, rank);
	}

	static CardDtoId of(String idString) {
		Matcher matcher = ID_PATTERN.matcher(idString);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("The identifier=" + idString + " does not match the id pattern");
		}

		CardColor cardColor = CardColor.valueOf(matcher.group(1));
		CardRank cardRank = CardRank.valueOf(matcher.group(2));
		return CardDtoId.of(cardColor, cardRank);
	}

	public CardColor getColor() {
		return color;
	}

	public CardRank getRank() {
		return rank;
	}

	@Override
	public String toString() {
		return color + "-" + rank;
	}
}
