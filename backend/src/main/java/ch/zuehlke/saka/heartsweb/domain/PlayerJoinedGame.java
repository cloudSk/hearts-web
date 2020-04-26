package ch.zuehlke.saka.heartsweb.domain;

import java.time.LocalDateTime;

public class PlayerJoinedGame implements DomainEvent {
	private final LocalDateTime occurredOn;
	private final PlayerId playerId;
	private final GameId gameId;

	public PlayerJoinedGame(PlayerId playerId, GameId gameId) {
		this.occurredOn = LocalDateTime.now();
		this.playerId = playerId;
		this.gameId = gameId;
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
}
