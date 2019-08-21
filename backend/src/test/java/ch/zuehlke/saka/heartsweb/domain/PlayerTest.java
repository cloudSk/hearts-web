package ch.zuehlke.saka.heartsweb.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PlayerTest {

	@Test
	public void playCardToRound_cardAvailableInHand_cardPlayedToTrick() {
		Player testee = new Player("Player");
		Card card = new Card(CardColor.HEARTS, CardRank.ACE);
		testee.assignHand(Arrays.asList(card));
		Round roundMock = mock(Round.class);

		testee.playCardToRound(card, roundMock);

		verify(roundMock).playCard(card, testee.id());
	}

	@Test
	public void playCardToRound_emptyHand_throwsIllegalArgumentException() {
		Player testee = new Player("Player");
		testee.assignHand(new ArrayList<>());
		Card card = new Card(CardColor.HEARTS, CardRank.ACE);

		Throwable result = catchThrowable(() -> testee.playCardToRound(card, null));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void playCardToRound_sameCardTwice_throwsIllegalArgumentException() {
		Player testee = new Player("Player");
		Card card = new Card(CardColor.HEARTS, CardRank.ACE);
		testee.assignHand(Arrays.asList(card));
		Round round = new Round(new Game(), testee.id());

		testee.playCardToRound(card, round);
		Throwable result = catchThrowable(() -> testee.playCardToRound(card, round));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}
}