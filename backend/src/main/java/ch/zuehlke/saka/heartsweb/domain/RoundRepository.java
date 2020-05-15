package ch.zuehlke.saka.heartsweb.domain;

import java.util.Optional;

public interface RoundRepository {
	void add(Round round);

	void update(Round round);

	Optional<Round> findById(GameId gameId, RoundId roundId);

	Optional<Round> findActiveRoundOfGame(GameId gameId);
}
