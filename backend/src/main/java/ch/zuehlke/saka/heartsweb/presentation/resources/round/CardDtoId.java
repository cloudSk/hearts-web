package ch.zuehlke.saka.heartsweb.presentation.resources.round;

import ch.zuehlke.saka.heartsweb.domain.CardColor;
import ch.zuehlke.saka.heartsweb.domain.CardRank;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonSerialize(using = ToStringSerializer.class)
@JsonDeserialize(using = CardDtoId.CardDtoIdDeserializer.class)
public class CardDtoId {
	private final CardColor color;
	private final CardRank rank;

	private CardDtoId(CardColor color, CardRank rank) {
		this.color = color;
		this.rank = rank;
	}

	static CardDtoId of(CardColor color, CardRank rank) {
		return new CardDtoId(color, rank);
	}

	@Override
	public String toString() {
		return color + "-" + rank;
	}


	static class CardDtoIdDeserializer extends FromStringDeserializer<CardDtoId> {
		private static final Pattern ID_PATTERN = Pattern.compile("^([A-Z])+-([A-Z_0-9]+)$");

		protected CardDtoIdDeserializer() {
			super(CardDto.class);
		}

		@Override
		protected CardDtoId _deserialize(String value, DeserializationContext ctxt) throws IOException {
			Matcher matcher = ID_PATTERN.matcher(value);
			if (!matcher.matches()) {
				throw new InvalidFormatException(ctxt.getParser(), "The identifier does not match the id pattern",
						value, CardDto.class);
			}

			try {
				CardColor cardColor = CardColor.valueOf(matcher.group(1));
				CardRank cardRank = CardRank.valueOf(matcher.group(2));
				return CardDtoId.of(cardColor, cardRank);
			} catch (IllegalArgumentException e) {
				throw new InvalidFormatException(ctxt.getParser(), "Card color or rank is not valid", value, CardDtoId.class);
			}
		}
	}
}
