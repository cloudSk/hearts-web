package ch.zuehlke.saka.heartsweb.domain;

import java.time.LocalDateTime;

public class CardPlayed implements DomainEvent {
	private final LocalDateTime occurredOn;
	private final PlayerId playerId;
	private final GameId gameId;
	private final RoundId roundId;
	private final Card card;

	public CardPlayed(PlayerId playerId, GameId gameId, RoundId roundId, Card card) {
		this.occurredOn = LocalDateTime.now();
		this.playerId = playerId;
		this.gameId = gameId;
		this.roundId = roundId;
		this.card = card;
	}

	@Override
	public LocalDateTime occurredOn() {
		return occurredOn;
	}

	public PlayerId playerId() {
		return playerId;
	}

	public GameId gameId() {
		return gameId;
	}

	public RoundId roundId() {
		return roundId;
	}

	public Card card() {
		return card;
	}
}
