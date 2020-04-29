package ch.zuehlke.saka.heartsweb.domain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Trick {
	private final PlayerId trickInitiator;
	private PlayerId lastPlayedPlayer;

	private final Map<PlayerId, Card> trickPot = new HashMap<>();
	private final GameId gameId;

	public Trick(PlayerId trickInitiator, GameId gameId) {
		this.trickInitiator = trickInitiator;
		this.gameId = gameId;
	}

	public void playCard(Card cardToPlay, PlayerId playerId, SittingOrder sittingOrder) {
		if (isFinished()) {
			throw new IllegalStateException("Trick contains already " + trickPot.size() + " cards.");
		}
		if (trickPot.containsKey(playerId)) {
			throw new IllegalArgumentException("Player with playerId=" + playerId +
					" already played a card in this trick");
		}

		PlayerId nextPlayerIdToPlay = nextPlayer(sittingOrder);
		if (!nextPlayerIdToPlay.equals(playerId)) {
			throw new IllegalArgumentException("Current playerId=" + playerId +
					" does not match nextPlayerId=" + nextPlayerIdToPlay + " to play a card.");
		}

		trickPot.put(playerId, cardToPlay);
		lastPlayedPlayer = playerId;
	}

	public PlayerId determineWinner() {
		if (!isFinished()) {
			throw new IllegalStateException("Current trick is not finished yet.");
		}

		CardColor trickInitiationColor = trickPot.get(trickInitiator).cardColor();

		return trickPot.entrySet().stream()
				.filter(entry -> entry.getValue().cardColor() == trickInitiationColor)
				.max(Comparator.comparing(entry -> entry.getValue().cardRank().rank()))
				.map(Map.Entry::getKey)
				.orElseThrow(() -> new IllegalStateException("This should never happen"));
    }

    public int determinePoints() {
	    int numberOfHeartsCards = (int) trickPot.values().stream()
			    .filter(card -> card.cardColor() == CardColor.HEARTS)
			    .count();

	    boolean containsSpadesQueen = trickPot.containsValue(new Card(CardColor.SPADES, CardRank.QUEEN));
	    int spadesPoints = containsSpadesQueen ? 13 : 0;

	    return numberOfHeartsCards + spadesPoints;
    }

    public boolean isFinished() {
	    return trickPot.size() >= Game.PLAYERS_PER_GAME;
    }

	public PlayerId nextPlayer(SittingOrder sittingOrder) {
		return lastPlayedPlayer == null
				? trickInitiator
				: sittingOrder.nextPlayerAfter(lastPlayedPlayer);
	}
}
