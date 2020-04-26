package ch.zuehlke.saka.heartsweb.domain;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private static final int MAX_PLAYER_AMOUNT = 4;

	private final List<PlayerId> playerIds = new ArrayList<>();
	private final GameId gameId;

    public Game() {
        this.gameId = GameId.generate();
    }

    public GameId id() {
	    return gameId;
    }

    public synchronized void joinGame(Player player) {
		if (playerIds.size() >= MAX_PLAYER_AMOUNT) {
			throw new IllegalStateException("Game already contains " + playerIds.size() + " players.");
		}
		if (playerIds.contains(player.id())) {
			throw new IllegalArgumentException("Player with playerId=" + player.id() + "already joined game.");
		}

	    playerIds.add(player.id());
	}

	public SittingOrder sittingOrder() {
    	if (playerIds.size() != 4) {
		    throw new IllegalStateException("Sitting order can currently only be determined, if all players joined the game");
	    }

		return new SittingOrder(playerIds.get(0), playerIds.get(1), playerIds.get(2), playerIds.get(3));
	}
}
