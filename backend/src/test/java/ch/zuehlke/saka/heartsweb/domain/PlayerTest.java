package ch.zuehlke.saka.heartsweb.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

public class PlayerTest {

	@Test
	public void playCardToRound_cardAvailableInHand_cardPlayedToTrick() {
		Player testee = new Player("Player");
		Card card = new Card(CardColor.HEARTS, CardRank.ACE);
		Round round = roundMock();
		testee.assignHand(Arrays.asList(card), round.id());

		testee.playCardToRound(card, round);

		verify(round).playCard(card, testee.id());
	}

	@Test
	public void playCardToRound_emptyHand_throwsIllegalArgumentException() {
		Round round = roundMock();
		Player testee = new Player("Player");
		testee.assignHand(new ArrayList<>(), round.id());
		Card card = new Card(CardColor.HEARTS, CardRank.ACE);

		Throwable result = catchThrowable(() -> testee.playCardToRound(card, round));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void playCardToRound_sameCardTwice_throwsIllegalArgumentException() {
		Game game = GameFixture.gameWith4Players();
		Player testee = new Player(game.sittingOrder().north(), "Player");
		Card card = new Card(CardColor.HEARTS, CardRank.ACE);
		Round round = new Round(game.id(), testee.id(), game.sittingOrder());
		testee.assignHand(Arrays.asList(card), round.id());

		testee.playCardToRound(card, round);
		Throwable result = catchThrowable(() -> testee.playCardToRound(card, round));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	private Round roundMock() {
		Round mock = mock(Round.class);
		when(mock.id()).thenReturn(RoundId.generate());
		return mock;
	}
}