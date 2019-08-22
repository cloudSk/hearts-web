package ch.zuehlke.saka.heartsweb.domain;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
	List<Player> findAllInGame(GameId gameId);

	Optional<Player> findById(GameId gameId, PlayerId playerId);

	void add(GameId gameId, Player player);
}
