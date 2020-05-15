package ch.zuehlke.saka.heartsweb.domain;

import java.time.LocalDateTime;

public class RoundCreated implements DomainEvent {
	private final LocalDateTime occurredOn;
	private final GameId gameId;
	private final RoundId roundId;

	public RoundCreated(GameId gameId, RoundId roundId) {
		this.occurredOn = LocalDateTime.now();
		this.gameId = gameId;
		this.roundId = roundId;
	}

	@Override
	public LocalDateTime occurredOn() {
		return occurredOn;
	}

	public GameId gameId() {
		return gameId;
	}

	public RoundId roundId() {
		return roundId;
	}
}
