package ch.zuehlke.saka.heartsweb.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;
import java.util.List;

public class SittingOrder {
	private static final int PLAYER_AMOUNT = 4;

	private final PlayerId north;
	private final PlayerId east;
	private final PlayerId south;
	private final PlayerId west;

	private final List<PlayerId> playerIds;

	public SittingOrder(PlayerId north, PlayerId east, PlayerId south, PlayerId west) {
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;

		this.playerIds = Arrays.asList(north, east, south, west);
	}

	public PlayerId nextPlayerAfter(PlayerId playerId) {
		int currentIndex = playerIds.indexOf(playerId);
		int nextIndex = (currentIndex + 1) % PLAYER_AMOUNT;

		return playerIds.get(nextIndex);
	}

	public PlayerId north() {
		return north;
	}

	public PlayerId east() {
		return east;
	}

	public PlayerId south() {
		return south;
	}

	public PlayerId west() {
		return west;
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

		SittingOrder that = (SittingOrder) o;
		return new EqualsBuilder()
				.append(north, that.north)
				.append(east, that.east)
				.append(south, that.south)
				.append(west, that.west)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(north)
				.append(east)
				.append(south)
				.append(west)
				.toHashCode();
	}
}
