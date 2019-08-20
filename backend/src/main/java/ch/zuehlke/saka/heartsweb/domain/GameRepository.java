package ch.zuehlke.saka.heartsweb.domain;

import java.util.Optional;

public interface GameRepository {
	Optional<Game> findById(GameId gameId);

	void add(Game game);
}
