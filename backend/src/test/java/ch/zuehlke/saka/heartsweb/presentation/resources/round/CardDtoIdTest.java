package ch.zuehlke.saka.heartsweb.presentation.resources.round;


import ch.zuehlke.saka.heartsweb.domain.CardColor;
import ch.zuehlke.saka.heartsweb.domain.CardRank;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CardDtoIdTest {
	@Test
	public void of_validCardColorAndRank_objectInstantiatedProperly() {
		String validCardId = "SPADES-NUMBER_02";

		CardDtoId result = CardDtoId.of(validCardId);

		assertThat(result.getColor()).isEqualTo(CardColor.SPADES);
		assertThat(result.getRank()).isEqualTo(CardRank.NUMBER_02);
	}

	@Test
	public void of_illegalFormat_throwsIllegalArgumentException() {
		String illegalCardId = "ILLEGAL_FORMAT";

		Throwable result = catchThrowable(() -> CardDtoId.of(illegalCardId));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void of_unknownCardColor_throwsIllegalArgumentException() {
		String illegalCardColor = "ILLEGAL-NUMBER_02";

		Throwable result = catchThrowable(() -> CardDtoId.of(illegalCardColor));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void of_unknownCardRank_throwsIllegalArgumentException() {
		String illegalCardRank = "SPADES-ILLEGAL_02";

		Throwable result = catchThrowable(() -> CardDtoId.of(illegalCardRank));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}
}
