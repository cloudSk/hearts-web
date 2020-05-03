package ch.zuehlke.saka.heartsweb.domain;

import java.util.Optional;
import java.util.Set;

public interface GameRepository {
	Set<Game> findAll();

	Optional<Game> findById(GameId gameId);

	void add(Game game);

	void update(Game game);
}
