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
	public void playCardToTrick_cardAvailableInHand_cardPlayedToTrick() {
		Player testee = new Player("Player");
		Card card = new Card(CardColor.HEARTS, CardRank.ACE);
		testee.assignHand(Arrays.asList(card));
		Trick trickMock = mock(Trick.class);

		testee.playCardToTrick(card, trickMock);

		verify(trickMock).playCard(card, testee.id());
	}

	@Test
	public void playCardToTrick_emptyHand_throwsIllegalArgumentException() {
		Player testee = new Player("Player");
		testee.assignHand(new ArrayList<>());
		Card card = new Card(CardColor.HEARTS, CardRank.ACE);

		Throwable result = catchThrowable(() -> testee.playCardToTrick(card, null));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void playCardToTrick_sameCardTwice_throwsIllegalArgumentException() {
		Player testee = new Player("Player");
		Card card = new Card(CardColor.HEARTS, CardRank.ACE);
		testee.assignHand(Arrays.asList(card));
		Trick trick = new Trick(testee.id(), new Game());

		testee.playCardToTrick(card, trick);
		Throwable result = catchThrowable(() -> testee.playCardToTrick(card, trick));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}
}