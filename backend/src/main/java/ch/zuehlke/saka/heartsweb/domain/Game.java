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

	    DomainEventPublisher.instance()
				.playerJoinedGame()
				.publish(new PlayerJoinedGame(player.id(), id()));
	}

	public PlayerId nextPlayerAfter(PlayerId playerId) {
		int currentIndex = playerIds.indexOf(playerId);
		int nextIndex = (currentIndex + 1) % MAX_PLAYER_AMOUNT;

		return playerIds.get(nextIndex);
	}
}
