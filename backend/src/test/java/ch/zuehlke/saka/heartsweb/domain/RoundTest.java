package ch.zuehlke.saka.heartsweb.domain;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class RoundTest {

	@Test
	public void playCard_roundAlreadyFinished_throwsIllegalStateException() {
		Game game = GameFixture.gameWith4Players();
		PlayerId firstPlayer = game.sittingOrder().north();
		Round testee = new Round(game.id(), firstPlayer, game.sittingOrder());
		playFullRound(testee);

		Throwable result = catchThrowable(
				() -> testee.playCard(new Card(CardColor.SPADES, CardRank.ACE), firstPlayer)
		);

		assertThat(result).isInstanceOf(IllegalStateException.class);
	}

	@Test
	public void heartsColorPlayed_noCardsOfColorHeartsPlayed_returnsFalse() {
		Game game = GameFixture.gameWith4Players();
		PlayerId firstPlayer = game.sittingOrder().north();
		Round testee = new Round(game.id(), firstPlayer, game.sittingOrder());
		testee.playCard(new Card(CardColor.SPADES, CardRank.ACE), firstPlayer);

		boolean result = testee.heartsColorPlayed();

		assertThat(result).isFalse();
	}

	@Test
	public void heartsColorPlayed_twoCardsOfColorHeartsPlayed_returnsTrue() {
		Game game = GameFixture.gameWith4Players();
		Round testee = new Round(game.id(), game.sittingOrder().north(), game.sittingOrder());
		testee.playCard(new Card(CardColor.SPADES, CardRank.ACE), game.sittingOrder().north());
		testee.playCard(new Card(CardColor.HEARTS, CardRank.ACE), game.sittingOrder().east());
		testee.playCard(new Card(CardColor.HEARTS, CardRank.KING), game.sittingOrder().south());
		testee.playCard(new Card(CardColor.SPADES, CardRank.KING), game.sittingOrder().west());

		boolean result = testee.heartsColorPlayed();

		assertThat(result).isTrue();
	}

	@Test
	public void heartsColorPlayed_oneCardOfColorHeartsPlayed_returnsTrue() {
		Game game = GameFixture.gameWith4Players();
		Round testee = new Round(game.id(), game.sittingOrder().north(), game.sittingOrder());
		testee.playCard(new Card(CardColor.SPADES, CardRank.ACE), game.sittingOrder().north());
		testee.playCard(new Card(CardColor.HEARTS, CardRank.ACE), game.sittingOrder().east());
		testee.playCard(new Card(CardColor.SPADES, CardRank.KING), game.sittingOrder().south());
		testee.playCard(new Card(CardColor.SPADES, CardRank.QUEEN), game.sittingOrder().west());

		boolean result = testee.heartsColorPlayed();

		assertThat(result).isTrue();
	}

	@Test
	public void roundFinished_fullRoundPlayed_returnsTrue() {
		Game game = GameFixture.gameWith4Players();
		PlayerId firstPlayer = game.sittingOrder().north();
		Round testee = new Round(game.id(), firstPlayer, game.sittingOrder());
		playFullRound(testee);

		boolean result = testee.roundFinished();

		assertThat(result).isTrue();
	}

	@Test
	public void roundFinished_oneTrickPlayed_returnsFalse() {
		Game game = GameFixture.gameWith4Players();
		Round testee = new Round(game.id(), game.sittingOrder().north(), game.sittingOrder());
		testee.playCard(new Card(CardColor.SPADES, CardRank.ACE), game.sittingOrder().north());
		testee.playCard(new Card(CardColor.SPADES, CardRank.KING), game.sittingOrder().east());
		testee.playCard(new Card(CardColor.SPADES, CardRank.QUEEN), game.sittingOrder().south());
		testee.playCard(new Card(CardColor.SPADES, CardRank.JACK), game.sittingOrder().west());

		boolean result = testee.roundFinished();

		assertThat(result).isFalse();
	}

	private void playFullRound(Round round) {
		PlayerId firstPlayer = round.sittingOrder().north();
		PlayerId secondPlayer = round.sittingOrder().east();
		PlayerId thirdPlayer = round.sittingOrder().south();
		PlayerId fourthPlayer = round.sittingOrder().west();

		IntStream.range(0, 13).forEach(i -> {
			round.playCard(new Card(CardColor.DIAMONDS, CardRank.ACE), firstPlayer);
			round.playCard(new Card(CardColor.DIAMONDS, CardRank.KING), secondPlayer);
			round.playCard(new Card(CardColor.DIAMONDS, CardRank.QUEEN), thirdPlayer);
			round.playCard(new Card(CardColor.DIAMONDS, CardRank.JACK), fourthPlayer);
		});
	}
}