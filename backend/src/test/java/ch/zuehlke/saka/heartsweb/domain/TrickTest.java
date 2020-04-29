package ch.zuehlke.saka.heartsweb.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class TrickTest {
	private static final Game GAME = GameFixture.gameWith4Players();
	private static final SittingOrder SITTING_ORDER = GAME.sittingOrder();
	private static final PlayerId FIRST_PLAYER = SITTING_ORDER.north();
	private static final PlayerId SECOND_PLAYER = SITTING_ORDER.east();
	private static final PlayerId THIRD_PLAYER = SITTING_ORDER.south();
	private static final PlayerId FOURTH_PLAYER = SITTING_ORDER.west();

	@Test
	public void playCard_4CardsInCorrectPlayerOrder_winnerCanBeDetermined() {
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_08), SECOND_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.JACK), THIRD_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_10), FOURTH_PLAYER, SITTING_ORDER);

		PlayerId result = testee.determineWinner();
		assertThat(result).isNotNull();
	}

	@Test
	public void playCard_samePlayerPlays2CardsIntoTrick_throwsIllegalArgumentException() {
		PlayerId playerId = PlayerId.generate();
		Trick testee = new Trick(playerId, new Game().id());
		Card card = new Card(CardColor.DIAMONDS, CardRank.KING);

		testee.playCard(card, playerId, null);
		Throwable result = catchThrowable(() -> testee.playCard(card, playerId, null));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void playCard_5CardsIntoTrick_throwsIllegalStateException() {
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_08), SECOND_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.JACK), THIRD_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_10), FOURTH_PLAYER, SITTING_ORDER);

		Throwable result = catchThrowable(
				() -> testee.playCard(new Card(CardColor.DIAMONDS, CardRank.ACE), PlayerId.generate(), SITTING_ORDER)
		);

		assertThat(result).isInstanceOf(IllegalStateException.class);
	}

	@Test
	public void playCard_wrongPlayerPlaysCardIntoTrick_throwsIllegalArgumentException() {
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER, SITTING_ORDER);
		Throwable result = catchThrowable(
				() -> testee.playCard(new Card(CardColor.DIAMONDS, CardRank.ACE), FOURTH_PLAYER, SITTING_ORDER)
		);

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void determineWinner_allPlayersPlayedTheSameCardColor_highestCardWins() {
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_08), SECOND_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.JACK), THIRD_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_10), FOURTH_PLAYER, SITTING_ORDER);
		PlayerId result = testee.determineWinner();

		assertThat(result).isEqualTo(THIRD_PLAYER);
	}

	@Test
	public void determineWinner_2PlayersPlayedTheSameCardColor_highestCardOfInitialColorWins() {
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_08), SECOND_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.JACK), THIRD_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_10), FOURTH_PLAYER, SITTING_ORDER);
		PlayerId result = testee.determineWinner();

		assertThat(result).isEqualTo(FOURTH_PLAYER);
	}

	@Test
	public void determineWinner_noPlayerPlayedTheSameCardColor_initiatorWins() {
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.NUMBER_09), FIRST_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.NUMBER_08), SECOND_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.JACK), THIRD_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.NUMBER_10), FOURTH_PLAYER, SITTING_ORDER);
		PlayerId result = testee.determineWinner();

		assertThat(result).isEqualTo(FIRST_PLAYER);
	}

	@Test
	public void determineWinner_3cardsPlayed_throwsIllegalStateException() {
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.ACE), FIRST_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.JACK), SECOND_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.DIAMONDS, CardRank.QUEEN), THIRD_PLAYER, SITTING_ORDER);
		Throwable result = catchThrowable(testee::determineWinner);

		assertThat(result).isInstanceOf(IllegalStateException.class);
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
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.SPADES, CardRank.QUEEN), FIRST_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.ACE), SECOND_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.KING), THIRD_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.HEARTS, CardRank.NUMBER_02), FOURTH_PLAYER, SITTING_ORDER);
		int result = testee.determinePoints();

		assertThat(result).isEqualTo(16);
	}

	@Test
	public void determinePoints_trickContainsNoPoints_returns0() {
		Trick testee = new Trick(FIRST_PLAYER, GAME.id());

		testee.playCard(new Card(CardColor.SPADES, CardRank.ACE), FIRST_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.SPADES, CardRank.KING), SECOND_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.SPADES, CardRank.JACK), THIRD_PLAYER, SITTING_ORDER);
		testee.playCard(new Card(CardColor.CLUBS, CardRank.QUEEN), FOURTH_PLAYER, SITTING_ORDER);
		int result = testee.determinePoints();

		assertThat(result).isEqualTo(0);
	}
}