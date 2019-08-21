package ch.zuehlke.saka.heartsweb.domain;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class RoundTest {
	private static final Player FIRST_PLAYER = new Player("First");
	private static final Player SECOND_PLAYER = new Player("Second");
	private static final Player THIRD_PLAYER = new Player("Third");
	private static final Player FOURTH_PLAYER = new Player("Fourth");

	@Test
	public void playCard_roundAlreadyFinished_throwsIllegalStateException() {
		Round testee = new Round(gameWith4Players(), FIRST_PLAYER.id());
		playFullRound(testee);

		Throwable result = catchThrowable(
				() -> testee.playCard(new Card(CardColor.SPADES, CardRank.ACE), FIRST_PLAYER.id())
		);

		assertThat(result).isInstanceOf(IllegalStateException.class);
	}

	@Test
	public void heartsColorPlayed_noCardsOfColorHeartsPlayed_returnsFalse() {
		Round testee = new Round(gameWith4Players(), FIRST_PLAYER.id());
		testee.playCard(new Card(CardColor.SPADES, CardRank.ACE), FIRST_PLAYER.id());

		boolean result = testee.heartsColorPlayed();

		assertThat(result).isFalse();
	}

	@Test
	public void heartsColorPlayed_twoCardsOfColorHeartsPlayed_returnsTrue() {
		Round testee = new Round(gameWith4Players(), FIRST_PLAYER.id());
		testee.playCard(new Card(CardColor.SPADES, CardRank.ACE), FIRST_PLAYER.id());
		testee.playCard(new Card(CardColor.HEARTS, CardRank.ACE), SECOND_PLAYER.id());
		testee.playCard(new Card(CardColor.HEARTS, CardRank.KING), THIRD_PLAYER.id());
		testee.playCard(new Card(CardColor.SPADES, CardRank.KING), FOURTH_PLAYER.id());

		boolean result = testee.heartsColorPlayed();

		assertThat(result).isTrue();
	}

	@Test
	public void heartsColorPlayed_oneCardOfColorHeartsPlayed_returnsTrue() {
		Round testee = new Round(gameWith4Players(), FIRST_PLAYER.id());
		testee.playCard(new Card(CardColor.SPADES, CardRank.ACE), FIRST_PLAYER.id());
		testee.playCard(new Card(CardColor.HEARTS, CardRank.ACE), SECOND_PLAYER.id());
		testee.playCard(new Card(CardColor.SPADES, CardRank.KING), THIRD_PLAYER.id());
		testee.playCard(new Card(CardColor.SPADES, CardRank.QUEEN), FOURTH_PLAYER.id());

		boolean result = testee.heartsColorPlayed();

		assertThat(result).isTrue();
	}

	@Test
	public void roundFinished_fullRoundPlayed_returnsTrue() {
		Round testee = new Round(gameWith4Players(), FIRST_PLAYER.id());
		playFullRound(testee);

		boolean result = testee.roundFinished();

		assertThat(result).isTrue();
	}

	@Test
	public void roundFinished_oneTrickPlayed_returnsFalse() {
		Round testee = new Round(gameWith4Players(), FIRST_PLAYER.id());
		testee.playCard(new Card(CardColor.SPADES, CardRank.ACE), FIRST_PLAYER.id());
		testee.playCard(new Card(CardColor.SPADES, CardRank.KING), SECOND_PLAYER.id());
		testee.playCard(new Card(CardColor.SPADES, CardRank.QUEEN), THIRD_PLAYER.id());
		testee.playCard(new Card(CardColor.SPADES, CardRank.JACK), FOURTH_PLAYER.id());

		boolean result = testee.roundFinished();

		assertThat(result).isFalse();
	}

	private void playFullRound(Round round) {
		IntStream.range(0, 13).forEach(i -> {
			round.playCard(new Card(CardColor.DIAMONDS, CardRank.ACE), FIRST_PLAYER.id());
			round.playCard(new Card(CardColor.DIAMONDS, CardRank.KING), SECOND_PLAYER.id());
			round.playCard(new Card(CardColor.DIAMONDS, CardRank.QUEEN), THIRD_PLAYER.id());
			round.playCard(new Card(CardColor.DIAMONDS, CardRank.JACK), FOURTH_PLAYER.id());
		});
	}

	private Game gameWith4Players() {
		Game game = new Game();
		game.joinGame(FIRST_PLAYER);
		game.joinGame(SECOND_PLAYER);
		game.joinGame(THIRD_PLAYER);
		game.joinGame(FOURTH_PLAYER);
		return game;
	}
}