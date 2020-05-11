package ch.zuehlke.saka.heartsweb.infrastructure.dataaccess;

import ch.zuehlke.saka.heartsweb.domain.GameId;
import ch.zuehlke.saka.heartsweb.domain.Round;
import ch.zuehlke.saka.heartsweb.domain.RoundId;
import ch.zuehlke.saka.heartsweb.domain.RoundRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class RoundInMemoryRepository implements RoundRepository {
	private final Set<Round> rounds = new HashSet<>();

	@Override
	public void add(Round round) {
		rounds.add(round);
	}

	@Override
	public void update(Round round) {
		// nothing to do in the in-memory implementation
	}

	@Override
	public Optional<Round> findById(GameId gameId, RoundId roundId) {
		return rounds.stream()
				.filter(round -> round.id().equals(roundId))
				.findFirst();
	}
}
