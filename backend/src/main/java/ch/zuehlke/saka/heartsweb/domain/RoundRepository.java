package ch.zuehlke.saka.heartsweb.domain;

import java.util.Optional;

public interface RoundRepository {
	void add(Round round);

	void update(Round round);

	Optional<Round> findById(RoundId roundId);
}
