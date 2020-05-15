package ch.zuehlke.saka.heartsweb.infrastructure.dataaccess;

import ch.zuehlke.saka.heartsweb.domain.GameId;
import ch.zuehlke.saka.heartsweb.domain.Round;
import ch.zuehlke.saka.heartsweb.domain.RoundId;
import ch.zuehlke.saka.heartsweb.domain.RoundRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RoundInMemoryRepository implements RoundRepository {
	private final Map<GameId, Set<Round>> rounds = new HashMap<>();

	@Override
	public void add(Round round) {
		rounds.putIfAbsent(round.gameId(), new HashSet<>());
		rounds.get(round.gameId()).add(round);
	}

	@Override
	public void update(Round round) {
		// nothing to do in the in-memory implementation
	}

	@Override
	public Optional<Round> findById(GameId gameId, RoundId roundId) {
		rounds.putIfAbsent(gameId, new HashSet<>());
		return rounds.get(gameId).stream()
				.filter(round -> round.id().equals(roundId))
				.findFirst();
	}

	@Override
	public Optional<Round> findActiveRoundOfGame(GameId gameId) {
		rounds.putIfAbsent(gameId, new HashSet<>());
		return rounds.get(gameId).stream()
				.filter(round -> !round.roundFinished())
				.findFirst();
	}
}
