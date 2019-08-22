package ch.zuehlke.saka.heartsweb.domain;

import java.util.UUID;

public class PlayerId {
	private final UUID internalId;

	public static PlayerId generate() {
		return new PlayerId(UUID.randomUUID());
	}

	public static PlayerId of(String id) {
		return new PlayerId(UUID.fromString(id));
	}

	private PlayerId(UUID internalId) {
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

		PlayerId playerId = (PlayerId) o;
		return internalId.equals(playerId.internalId);
	}

	@Override
	public int hashCode() {
		return internalId.hashCode();
	}
}
