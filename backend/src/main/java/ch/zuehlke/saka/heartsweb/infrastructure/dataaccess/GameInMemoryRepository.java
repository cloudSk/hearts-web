package ch.zuehlke.saka.heartsweb.infrastructure.dataaccess;

import ch.zuehlke.saka.heartsweb.domain.Game;
import ch.zuehlke.saka.heartsweb.domain.GameId;
import ch.zuehlke.saka.heartsweb.domain.GameRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class GameInMemoryRepository implements GameRepository {
	private final List<Game> games = new ArrayList<>();

	@Override
	public List<Game> findAll() {
		return games;
	}

	@Override
	public Optional<Game> findById(GameId gameId) {
		return games.stream()
				.filter(game -> game.id().equals(gameId))
				.findFirst();
	}

	@Override
	public void add(Game game) {
		games.add(game);
	}
}
