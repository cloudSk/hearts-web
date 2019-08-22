package ch.zuehlke.saka.heartsweb.domain;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
	List<Game> findAll();

	Optional<Game> findById(GameId gameId);

	void add(Game game);
}
