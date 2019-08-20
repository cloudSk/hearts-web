package ch.zuehlke.saka.heartsweb.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class TrickTest {
	private static final Player FIRST_PLAYER = new Player("First");
	private static final Player SECOND_PLAYER = new Player("Second");
	private static final Player THIRD_PLAYER = new Player("Third");
	private static final Player FOURTH_PLAYER = new Player("Fourth");

	@Test
	public void playCard_4CardsInCorrectPlayerOrder_winnerCanBeDetermined() {
		Game game = gameWith4Players();
		Trick testee = new Trick(FIRST_PLAYER.id(), game);

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_08), SECOND_PLAYER.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.JACK), THIRD_PLAYER.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_10), FOURTH_PLAYER.id());

		PlayerId result = testee.determineWinner();
		assertThat(result).isNotNull();
	}

	@Test
	public void playCard_playerPlays2CardsIntoTrick_throwsIllegalArgumentException() {
		PlayerId playerId = PlayerId.generate();
		Trick testee = new Trick(playerId, new Game());
		Card card = new Card(CardColor.DIAMONDS, CardRank.KING);

		testee.playCard(card, playerId);
		Throwable result = catchThrowable(() -> testee.playCard(card, playerId));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void playCard_5CardsIntoTrick_throwsIllegalStateException() {
		Game game = gameWith4Players();
		Trick testee = new Trick(FIRST_PLAYER.id(), game);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_08), SECOND_PLAYER.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.JACK), THIRD_PLAYER.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_10), FOURTH_PLAYER.id());

		Throwable result = catchThrowable(
				() -> testee.playCard(new Card(CardColor.DIAMONDS, CardRank.ACE), PlayerId.generate())
		);

		assertThat(result).isInstanceOf(IllegalStateException.class);
	}

	@Test
	public void determineWinner_allPlayersPlayedTheSameCardColor_highestCardWins() {
		Game game = gameWith4Players();
		Trick testee = new Trick(FIRST_PLAYER.id(), game);

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_08), SECOND_PLAYER.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.JACK), THIRD_PLAYER.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_10), FOURTH_PLAYER.id());
		PlayerId result = testee.determineWinner();

		assertThat(result).isEqualTo(THIRD_PLAYER.id());
	}

	@Test
	public void determineWinner_2PlayersPlayedTheSameCardColor_highestCardOfInitialColorWins() {
		Game game = gameWith4Players();
		Trick testee = new Trick(FIRST_PLAYER.id(), game);

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_08), SECOND_PLAYER.id());
		testee.playCard(new Card(CardColor.HEARTS, CardRank.JACK), THIRD_PLAYER.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_10), FOURTH_PLAYER.id());
		PlayerId result = testee.determineWinner();

		assertThat(result).isEqualTo(FOURTH_PLAYER.id());
	}

	@Test
	public void determineWinner_noPlayerPlayedTheSameCardColor_initiatorWins() {
		Game game = gameWith4Players();
		Trick testee = new Trick(FIRST_PLAYER.id(), game);

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER.id());
		testee.playCard(new Card(CardColor.HEARTS, CardRank.NUMBER_08), SECOND_PLAYER.id());
		testee.playCard(new Card(CardColor.HEARTS, CardRank.JACK), THIRD_PLAYER.id());
		testee.playCard(new Card(CardColor.HEARTS, CardRank.NUMBER_10), FOURTH_PLAYER.id());
		PlayerId result = testee.determineWinner();

		assertThat(result).isEqualTo(FIRST_PLAYER.id());
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