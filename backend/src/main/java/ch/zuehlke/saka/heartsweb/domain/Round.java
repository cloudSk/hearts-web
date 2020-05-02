package ch.zuehlke.saka.heartsweb.domain;

import java.util.ArrayList;
import java.util.List;

public class Round {
	private static final int CARDS_PER_TRICK = Game.PLAYERS_PER_GAME;
	private static final int CARDS_PER_ROUND = CARDS_PER_TRICK * 13;

	private final RoundId roundId;
	private final List<Trick> tricks = new ArrayList<>();
	private final GameId gameId;
	private final PlayerId roundInitiator;
	private final SittingOrder sittingOrder;

	private boolean heartsColorPlayed = false;
	private int currentCardIndex = -1;

	public Round(GameId gameId, PlayerId roundInitiator, SittingOrder sittingOrder) {
		this(RoundId.generate(), gameId, roundInitiator, sittingOrder);
	}

	Round(RoundId roundId, GameId gameId, PlayerId roundInitiator, SittingOrder sittingOrder) {
		this.roundId = roundId;
		this.gameId = gameId;
		this.roundInitiator = roundInitiator;
		this.sittingOrder = sittingOrder;
	}

	public RoundId id() {
		return roundId;
	}

	public void playCard(Card card, PlayerId playerId) {
		if (roundFinished()) {
			throw new IllegalStateException("Round already finished");
		}

		++currentCardIndex;
		int cardIndexInTrick = currentCardIndex % CARDS_PER_TRICK;
		int trickIndex = currentCardIndex / CARDS_PER_TRICK;

		if (cardIndexInTrick == 0) {
			createNewTrick();
		}
		Trick currentTrick = tricks.get(trickIndex);
		currentTrick.playCard(card, playerId, sittingOrder);

		heartsColorPlayed = Boolean.logicalOr(heartsColorPlayed, card.cardColor() == CardColor.HEARTS);
	}

	public PlayerId nextPlayer() {
		if (tricks.isEmpty()) {
			return roundInitiator;
		}

		int trickIndex = currentCardIndex / CARDS_PER_TRICK;
		Trick currentTrick = tricks.get(trickIndex);
		return currentTrick.nextPlayer(sittingOrder);
	}

	public boolean heartsColorPlayed() {
		return heartsColorPlayed;
	}

	public boolean roundFinished() {
		return (currentCardIndex + 1) >= CARDS_PER_ROUND;
	}

	public SittingOrder sittingOrder() {
		return sittingOrder;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (getClass() != o.getClass()) {
			return false;
		}

		Round round = (Round) o;
		return roundId.equals(round.roundId);
	}

	@Override
	public int hashCode() {
		return roundId.hashCode();
	}

	private void createNewTrick() {
		if (tricks.isEmpty()) {
			tricks.add(new Trick(roundInitiator));
			return;
		}

		Trick lastTrick = tricks.get(tricks.size() - 1);
		PlayerId newTrickInitiator = lastTrick.determineWinner();
		Trick newTrick = new Trick(newTrickInitiator);
		tricks.add(newTrick);
	}
}
