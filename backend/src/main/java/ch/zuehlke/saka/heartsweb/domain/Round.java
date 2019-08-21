package ch.zuehlke.saka.heartsweb.domain;

import java.util.ArrayList;
import java.util.List;

public class Round {
	private static final int CARDS_PER_TRICK = 4;
	private static final int CARDS_PER_ROUND = CARDS_PER_TRICK * 13;

	private final List<Trick> tricks = new ArrayList<>();
	private final Game game;
	private final PlayerId roundInitiator;
	private boolean heartsColorPlayed = false;
	private int currentCardIndex = -1;

	public Round(Game game, PlayerId roundInitiator) {
		this.game = game;
		this.roundInitiator = roundInitiator;
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
		currentTrick.playCard(card, playerId);

		heartsColorPlayed = Boolean.logicalOr(heartsColorPlayed, card.cardColor() == CardColor.HEARTS);
	}

	public boolean heartsColorPlayed() {
		return heartsColorPlayed;
	}

	public boolean roundFinished() {
		return (currentCardIndex + 1) >= CARDS_PER_ROUND;
	}

	private void createNewTrick() {
		if (tricks.isEmpty()) {
			tricks.add(new Trick(roundInitiator, game));
			return;
		}

		Trick lastTrick = tricks.get(tricks.size() - 1);
		PlayerId newTrickInitiator = lastTrick.determineWinner();
		Trick newTrick = new Trick(newTrickInitiator, game);
		tricks.add(newTrick);
	}
}
