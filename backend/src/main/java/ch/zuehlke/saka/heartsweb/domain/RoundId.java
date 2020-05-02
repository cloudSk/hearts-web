package ch.zuehlke.saka.heartsweb.domain;

import java.util.UUID;

public class RoundId {
	private final UUID internalId;

	public static RoundId generate() {
		return new RoundId(UUID.randomUUID());
	}

	public static RoundId of(String id) {
		return new RoundId(UUID.fromString(id));
	}

	private RoundId(UUID internalId) {
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

		RoundId roundId = (RoundId) o;
		return internalId.equals(roundId.internalId);
	}

	@Override
	public int hashCode() {
		return internalId.hashCode();
	}
}
