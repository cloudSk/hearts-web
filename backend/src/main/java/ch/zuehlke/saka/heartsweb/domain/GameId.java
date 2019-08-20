package ch.zuehlke.saka.heartsweb.domain;

import java.util.UUID;

public class GameId {
	private final UUID internalId;

	public static GameId generate() {
		return new GameId(UUID.randomUUID());
	}

	public static GameId of(String id) {
		return new GameId(UUID.fromString(id));
	}

	private GameId(UUID internalId) {
		this.internalId = internalId;
	}

	@Override
	public String toString() {
		return internalId.toString();
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

		GameId gameId = (GameId) o;
		return internalId.equals(gameId.internalId);
	}

	@Override
	public int hashCode() {
		return internalId.hashCode();
	}
}
