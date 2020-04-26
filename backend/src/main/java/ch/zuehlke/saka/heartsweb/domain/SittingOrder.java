package ch.zuehlke.saka.heartsweb.domain;

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
}
