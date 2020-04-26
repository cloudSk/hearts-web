package ch.zuehlke.saka.heartsweb.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class TrickTest {
	private static final Game GAME = GameFixture.gameWith4Players();
	private static final PlayerId FIRST_PLAYER = GAME.sittingOrder().north();
	private static final PlayerId SECOND_PLAYER = GAME.sittingOrder().east();
	private static final PlayerId THIRD_PLAYER = GAME.sittingOrder().south();
	private static final PlayerId FOURTH_PLAYER = GAME.sittingOrder().west();

	@Test
	public void playCard_4CardsInCorrectPlayerOrder_winnerCanBeDetermined() {
		SittingOrder sittingOrder = GAME.sittingOrder();
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_08), SECOND_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.JACK), THIRD_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_10), FOURTH_PLAYER, sittingOrder);

		PlayerId result = testee.determineWinner();
		assertThat(result).isNotNull();
	}

	@Test
	public void playCard_playerPlays2CardsIntoTrick_throwsIllegalArgumentException() {
		PlayerId playerId = PlayerId.generate();
		Trick testee = new Trick(playerId, new Game().id());
		Card card = new Card(CardColor.DIAMONDS, CardRank.KING);

		testee.playCard(card, playerId, null);
		Throwable result = catchThrowable(() -> testee.playCard(card, playerId, null));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void playCard_5CardsIntoTrick_throwsIllegalStateException() {
		SittingOrder sittingOrder = GAME.sittingOrder();
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_08), SECOND_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.JACK), THIRD_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_10), FOURTH_PLAYER, sittingOrder);

		Throwable result = catchThrowable(
				() -> testee.playCard(new Card(CardColor.DIAMONDS, CardRank.ACE), PlayerId.generate(), sittingOrder)
		);

		assertThat(result).isInstanceOf(IllegalStateException.class);
	}

	@Test
	public void playCard_wrongPlayerPlaysCardIntoTrick_throwsIllegalArgumentException() {
		SittingOrder sittingOrder = GAME.sittingOrder();
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER, sittingOrder);
		Throwable result = catchThrowable(
				() -> testee.playCard(new Card(CardColor.DIAMONDS, CardRank.ACE), FOURTH_PLAYER, sittingOrder)
		);

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void determineWinner_allPlayersPlayedTheSameCardColor_highestCardWins() {
		SittingOrder sittingOrder = GAME.sittingOrder();
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_08), SECOND_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.JACK), THIRD_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_10), FOURTH_PLAYER, sittingOrder);
		PlayerId result = testee.determineWinner();

		assertThat(result).isEqualTo(THIRD_PLAYER);
	}

	@Test
	public void determineWinner_2PlayersPlayedTheSameCardColor_highestCardOfInitialColorWins() {
		SittingOrder sittingOrder = GAME.sittingOrder();
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_08), SECOND_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.JACK), THIRD_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_10), FOURTH_PLAYER, sittingOrder);
		PlayerId result = testee.determineWinner();

		assertThat(result).isEqualTo(FOURTH_PLAYER);
	}

	@Test
	public void determineWinner_noPlayerPlayedTheSameCardColor_initiatorWins() {
		SittingOrder sittingOrder = GAME.sittingOrder();
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.NUMBER_08), SECOND_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.JACK), THIRD_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.NUMBER_10), FOURTH_PLAYER, sittingOrder);
		PlayerId result = testee.determineWinner();

		assertThat(result).isEqualTo(FIRST_PLAYER);
	}

	@Test
	public void determinePoints_trickContainsOneCardOfColorHearts_returns1() {
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.HEARTS, CardRank.NUMBER_08), FIRST_PLAYER, null);
		int result = testee.determinePoints();

		assertThat(result).isEqualTo(1);
	}

	@Test
	public void determinePoints_trickContainsSpadesQueen_returns13() {
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.SPADES, CardRank.QUEEN), FIRST_PLAYER, null);
		int result = testee.determinePoints();

		assertThat(result).isEqualTo(13);
	}

	@Test
	public void determinePoints_trickContainsSpadesQueenAnd3CardsOfColorHearts_returns16() {
		SittingOrder sittingOrder = GAME.sittingOrder();
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.SPADES, CardRank.QUEEN), FIRST_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.ACE), SECOND_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.KING), THIRD_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.NUMBER_02), FOURTH_PLAYER, sittingOrder);
		int result = testee.determinePoints();

		assertThat(result).isEqualTo(16);
	}

	@Test
	public void determinePoints_trickContainsNoPoints_returns0() {
		SittingOrder sittingOrder = GAME.sittingOrder();
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.SPADES, CardRank.ACE), FIRST_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.SPADES, CardRank.KING), SECOND_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.SPADES, CardRank.JACK), THIRD_PLAYER, sittingOrder);
		testee.playCard(new Card(CardColor.CLUBS, CardRank.QUEEN), FOURTH_PLAYER, sittingOrder);
		int result = testee.determinePoints();

		assertThat(result).isEqualTo(0);
	}
}